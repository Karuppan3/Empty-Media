package io.bash_psk.empty_media.data.worker

import android.content.Context
import android.content.pm.ServiceInfo
import android.os.Build
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import io.bash_psk.downloader.youtubedl.YoutubeDL
import io.bash_psk.empty_media.R
import io.bash_psk.empty_media.domain.repository.EmptyNotification
import io.bash_psk.empty_media.domain.resource.ConstantNotification
import io.bash_psk.empty_media.domain.resource.ConstantWorker
import io.bash_psk.preference.datastore.PreferenceData
import io.bash_psk.preference.datastore.setPreference
import io.bash_psk.utils.log.SetLog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@HiltWorker
class UpdateWorker @AssistedInject constructor(
    @Assisted val context: Context,
    @Assisted val workerParameters: WorkerParameters,
    private val emptyNotification: EmptyNotification
) : CoroutineWorker(
    appContext = context,
    params = workerParameters
) {

    override suspend fun doWork() : Result = withContext(context = Dispatchers.IO) {

        return@withContext try {

            val updateOutput = YoutubeDL.getInstance().updateYoutubeDL(
                appContext = context,
                updateChannel = YoutubeDL.UpdateChannel.STABLE
            )?.name

            val resultData = workDataOf(
                ConstantWorker.Key.UPDATE_OUTPUT_FILE to updateOutput
            )

            Result.success(resultData)
        } catch (exception: Exception) {

            SetLog.setError(message = exception.localizedMessage, throwable = exception)

            Result.failure()
        } finally {

            YoutubeDL.getInstance().version(
                appContext = context
            )?.let { libVersion: String ->

                context.setPreference(
                    key = PreferenceData.YtdlLibrary.key,
                    value = libVersion
                )
            }
        }
    }

    override suspend fun getForegroundInfo() : ForegroundInfo {

        return createForegroundInfo()
    }

    private fun createForegroundInfo() : ForegroundInfo {

        val cancelIntent = WorkManager.getInstance(context).createCancelPendingIntent(id)

        val notificationBuilder = emptyNotification
            .notificationBuilder(channelId = ConstantNotification.EMPTY_MEDIA_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(ConstantWorker.Message.LIBRARY_UPDATING)
            .setPriority(ConstantNotification.LIBRARY_UPDATE_NOTIFICATION_PRIORITY)
            .setOnlyAlertOnce(true)
            .setOngoing(true)
            .setAutoCancel(true)
            .addAction(R.drawable.ic_close, ConstantWorker.Intent.CANCEL, cancelIntent)

        return when {

            Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q -> {

                ForegroundInfo(
                    ConstantNotification.LIBRARY_UPDATE_NOTIFICATION_ID,
                    notificationBuilder.build(),
                    ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC
                )
            }

            else -> {

                ForegroundInfo(
                    ConstantNotification.LIBRARY_UPDATE_NOTIFICATION_ID,
                    notificationBuilder.build()
                )
            }
        }
    }
}