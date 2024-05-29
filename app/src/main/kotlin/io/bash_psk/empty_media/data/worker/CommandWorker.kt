package io.bash_psk.empty_media.data.worker

import android.content.Context
import android.content.pm.ServiceInfo
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.webkit.MimeTypeMap
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import io.bash_psk.downloader.youtubedl.YoutubeDL
import io.bash_psk.downloader.youtubedl.YoutubeDLRequest
import io.bash_psk.empty_media.R
import io.bash_psk.empty_media.domain.database.DownloadStatus
import io.bash_psk.empty_media.domain.database.MediaEntity
import io.bash_psk.empty_media.domain.repository.EmptyDatabase
import io.bash_psk.empty_media.domain.repository.EmptyNotification
import io.bash_psk.empty_media.domain.resource.ConstantCommand
import io.bash_psk.empty_media.domain.resource.ConstantNotification
import io.bash_psk.empty_media.domain.resource.ConstantString
import io.bash_psk.empty_media.domain.resource.ConstantWorker
import io.bash_psk.utils.log.SetLog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.util.Collections
import java.util.regex.Pattern

@HiltWorker
class CommandWorker @AssistedInject constructor(
    @Assisted val context: Context,
    @Assisted val workerParameters: WorkerParameters,
    private val emptyNotification: EmptyNotification,
    private val emptyDatabase: EmptyDatabase
) : CoroutineWorker(
    appContext = context,
    params = workerParameters
) {

    override suspend fun doWork() : Result = withContext(
        context = Dispatchers.IO
    ) {

        val mediaIdInput = inputData.getString(
            ConstantWorker.MediaKey.MEDIA_ID
        ) ?: return@withContext Result.failure()

        val titleInput = inputData.getString(
            ConstantWorker.MediaKey.TITLE
        ) ?: return@withContext Result.failure()

        val commandInput = inputData.getString(
            ConstantWorker.MediaKey.COMMAND
        ) ?: return@withContext Result.failure()

        val pathInput = inputData.getString(
            ConstantWorker.MediaKey.PATH
        ) ?: return@withContext Result.failure()

        val platformInput = inputData.getString(
            ConstantWorker.MediaKey.PLATFORM
        ) ?: return@withContext Result.failure()

        val statusInput = inputData.getString(
            ConstantWorker.MediaKey.STATUS
        ) ?: return@withContext Result.failure()

        val sizeInput = inputData.getLong(
            ConstantWorker.MediaKey.SIZE,
            0L
        )

        val progressInput = inputData.getInt(
            ConstantWorker.MediaKey.PROGRESS,
            0
        )

        val timeStampInput = inputData.getLong(
            ConstantWorker.MediaKey.TIME_STAMP,
            0L
        )

        val newMediaEntity = MediaEntity(
            mediaId = mediaIdInput,
            title = titleInput,
            command = commandInput,
            path = pathInput,
            platform = platformInput,
            status = statusInput,
            size = sizeInput,
            progress = progressInput,
            timeStamp = timeStampInput
        )

        val commandRegex = "\"([^\"]*)\"|(\\S+)"

        val downloadsDirectory = Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_DOWNLOADS
        )

        val rootDirectory = File(downloadsDirectory, ConstantString.ROOT_PATH)

        val request = YoutubeDLRequest(urls = Collections.emptyList())

        val matcher = Pattern.compile(commandRegex).matcher(commandInput)

        when {

            rootDirectory.exists().not() -> {

                rootDirectory.mkdirs()
            }
        }

        while (matcher.find()) {

            when (matcher.group(1) != null) {

                true -> {

                    matcher.group(1)?.let { matcherCommand: String ->

                        request.addOption(option = matcherCommand)

                        request.addOption(
                            option = ConstantCommand.DOWNLOADER_OPTION,
                            argument = ConstantCommand.DOWNLOADER_ARGS
                        )

                        request.addOption(
                            option = ConstantCommand.EXTERNAL_DOWNLOADER_OPTION,
                            argument = ConstantCommand.EXTERNAL_DOWNLOADER_ARGS
                        )
                    }
                }

                false -> {

                    matcher.group(2)?.let { matcherCommand: String ->

                        request.addOption(option = matcherCommand)

                        request.addOption(
                            option = ConstantCommand.DOWNLOADER_OPTION,
                            argument = ConstantCommand.DOWNLOADER_ARGS
                        )

                        request.addOption(
                            option = ConstantCommand.EXTERNAL_DOWNLOADER_OPTION,
                            argument = ConstantCommand.EXTERNAL_DOWNLOADER_ARGS
                        )
                    }
                }
            }
        }

        val cancelIntent = WorkManager.getInstance(context).createCancelPendingIntent(id)

        val notificationBuilder = emptyNotification
            .notificationBuilder(channelId = ConstantNotification.EMPTY_MEDIA_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(titleInput)
            .setPriority(ConstantNotification.COMMAND_NOTIFICATION_PRIORITY)
            .setProgress(100, 0, true)
            .setOnlyAlertOnce(true)
            .setOngoing(true)
            .setAutoCancel(true)
            .addAction(R.drawable.ic_close, ConstantWorker.Intent.CANCEL, cancelIntent)

        val completeNotification = emptyNotification
            .notificationBuilder(channelId = ConstantNotification.EMPTY_MEDIA_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(ConstantWorker.Message.COMPLETED)
            .setStyle(NotificationCompat.BigTextStyle().bigText(titleInput))
            .setPriority(ConstantNotification.COMMAND_NOTIFICATION_PRIORITY)
            .setOngoing(false)
            .build()

        val failedNotification = emptyNotification
            .notificationBuilder(channelId = ConstantNotification.EMPTY_MEDIA_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(ConstantWorker.Message.FAILED)
            .setStyle(NotificationCompat.BigTextStyle().bigText(titleInput))
            .setPriority(ConstantNotification.COMMAND_NOTIFICATION_PRIORITY)
            .setOngoing(false)
            .build()

        val commandResponse = YoutubeDL.getInstance().execute(
            request = request,
            processId = commandInput,
            callback = { progress: Float, eta: Long, line: String ->

                val contentText = "${progress.toInt()} %"

                val progressData = workDataOf(
                    ConstantWorker.Key.COMMAND_PROGRESS to progress.toInt(),
                    ConstantWorker.Key.COMMAND_ELAPSED_TIME to eta,
                    ConstantWorker.Key.COMMAND_LINE to line,
                )

                notificationBuilder
                    .setStyle(NotificationCompat.BigTextStyle().bigText(contentText))
                    .setProgress(100, progress.toInt(), progress.toInt() == 0)

                emptyNotification.setNotification(
                    notificationId = ConstantNotification.COMMAND_NOTIFICATION_ID,
                    notification = notificationBuilder.build()
                )

                CoroutineScope(context = Dispatchers.IO).launch {

                    setProgress(data = progressData)
                }
            }
        )

        val commandOutputFile = File(context.cacheDir, "${System.currentTimeMillis()}.txt")

        return@withContext try {

            BufferedOutputStream(
                FileOutputStream(commandOutputFile)
            ).use { bufferedOutputStream: BufferedOutputStream ->

                bufferedOutputStream.write(commandResponse.out.toByteArray())
            }

            rootDirectory.listFiles()?.forEach { file: File? ->

                file?.let { destinationFile: File ->

                    val mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(
                        destinationFile.extension
                    ) ?: "*/*"

                    MediaScannerConnection.scanFile(
                        context,
                        arrayOf(destinationFile.path),
                        arrayOf(mimeType)
                    ) { path: String, uri: Uri ->

                    }
                }
            }

            val completedMediaEntity = newMediaEntity.copy(
                status = DownloadStatus.COMPLETED.status,
                progress = 100,
                timeStamp = System.currentTimeMillis()
            )

            emptyDatabase.upsertMediaEntity(mediaEntity = completedMediaEntity)

            val resultData = workDataOf(
                ConstantWorker.Key.COMMAND_OUTPUT_FILE to commandOutputFile.path
            )

            emptyNotification.setNotification(
                notificationId = commandInput.hashCode(),
                notification = completeNotification
            )

            Result.success(resultData)
        } catch (exception: Exception) {

            val failedMediaEntity = newMediaEntity.copy(
                status = DownloadStatus.FAILED.status,
                timeStamp = System.currentTimeMillis()
            )

            emptyDatabase.upsertMediaEntity(mediaEntity = failedMediaEntity)

            SetLog.setError(message = exception.localizedMessage, throwable = exception)

            emptyNotification.setNotification(
                notificationId = commandInput.hashCode(),
                notification = failedNotification
            )

            Result.failure()
        }
    }

    override suspend fun getForegroundInfo() : ForegroundInfo {

        return createForegroundInfo()
    }

    private fun createForegroundInfo() : ForegroundInfo {

        val cancelIntent = WorkManager.getInstance(context).createCancelPendingIntent(id)

        val notification = emptyNotification
            .notificationBuilder(channelId = ConstantNotification.EMPTY_MEDIA_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(ConstantWorker.Message.PROCESS_STARTING)
            .setPriority(ConstantNotification.COMMAND_NOTIFICATION_PRIORITY)
            .setOnlyAlertOnce(true)
            .setOngoing(true)
            .setAutoCancel(true)
            .addAction(R.drawable.ic_close, ConstantWorker.Intent.CANCEL, cancelIntent)
            .build()

        return when {

            Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q -> {

                ForegroundInfo(
                    ConstantNotification.COMMAND_NOTIFICATION_ID,
                    notification,
                    ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC
                )
            }

            else -> {

                ForegroundInfo(
                    ConstantNotification.COMMAND_NOTIFICATION_ID,
                    notification
                )
            }
        }
    }
}