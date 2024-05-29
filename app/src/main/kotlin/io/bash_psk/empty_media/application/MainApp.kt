package io.bash_psk.empty_media.application

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.WorkManager
import dagger.hilt.android.HiltAndroidApp
import io.bash_psk.aria2c.Aria2c
import io.bash_psk.downloader.youtubedl.YoutubeDL
import io.bash_psk.empty_media.domain.repository.EmptyNotification
import io.bash_psk.empty_media.domain.resource.ConstantError
import io.bash_psk.empty_media.domain.resource.ConstantNotification
import io.bash_psk.empty_media.domain.resource.ConstantToast
import io.bash_psk.ffmpeg.FFmpeg
import io.bash_psk.preference.datastore.dataStore
import io.bash_psk.utils.log.SetLog
import io.bash_psk.utils.toast.SetToast
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class MainApp : Application() {

    @Inject
    lateinit var hiltWorkerFactory: HiltWorkerFactory

    @Inject
    lateinit var emptyNotification: EmptyNotification

    override fun onCreate() {
        super.onCreate()

        val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable: Throwable ->

            SetLog.setError(
                message = ConstantError.APP_ERROR,
                throwable = throwable
            )

            CoroutineScope(
                context = Dispatchers.Main
            ).launch {

                SetToast.setToastLong(
                    context = this@MainApp,
                    message = ConstantToast.INITIALIZATION_FAILED
                )
            }
        }

        WorkManager.initialize(
            this,
            Configuration.Builder().setWorkerFactory(hiltWorkerFactory).build()
        )

        CoroutineScope(
            context = Dispatchers.IO
        ).launch(
            context = coroutineExceptionHandler
        ) {

            YoutubeDL.getInstance().init(appContext = this@MainApp)
            FFmpeg.getInstance().init(appContext = this@MainApp)
            Aria2c.getInstance().init(appContext = this@MainApp)
            this@MainApp.dataStore.data.first()
        }

        emptyNotification.createNotificationChannel(
            channelId = ConstantNotification.EMPTY_MEDIA_CHANNEL_ID,
            channelName = ConstantNotification.EMPTY_MEDIA_CHANNEL_NAME,
            channelImportance = ConstantNotification.EMPTY_MEDIA_CHANNEL_IMPORTANCE,
            channelDescription = ConstantNotification.EMPTY_MEDIA_CHANNEL_DESCRIPTION
        )
    }
}