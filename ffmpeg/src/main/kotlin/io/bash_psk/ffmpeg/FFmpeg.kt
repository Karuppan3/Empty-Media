package io.bash_psk.ffmpeg

import android.content.Context
import io.bash_psk.commons.SharedPrefsHelper
import io.bash_psk.commons.SharedPrefsHelper.update
import io.bash_psk.commons.utils.ZipUtils.unzip
import io.bash_psk.downloader.youtubedl.YoutubeDLException
import org.apache.commons.io.FileUtils
import java.io.File

object FFmpeg {

    @JvmStatic
    fun getInstance() = this

    private const val BASE_NAME = "empty media"
    private const val PACKAGES_ROOT = "packages"
    private const val FFMPEG_DIRECTORY_NAME = "ffmpeg"
    private const val FFMPEG_LIB_NAME = "libffmpeg.zip.so"
    private const val FFMPEG_LIB_VERSION = "ffmpegLibVersion"

    private var isInitialized = false
    private var binDirectory: File? = null

    @Synchronized
    fun init(appContext: Context) {

        when (isInitialized) {

            true -> {

                return
            }

            false -> {

                val baseDir = File(appContext.noBackupFilesDir, BASE_NAME)

                when {

                    !baseDir.exists() -> {

                        baseDir.mkdir()
                    }
                }

                binDirectory = File(appContext.applicationInfo.nativeLibraryDir)

                val packagesDir = File(baseDir, PACKAGES_ROOT)

                val ffmpegDir = File(packagesDir, FFMPEG_DIRECTORY_NAME)

                initFFmpeg(
                    appContext = appContext,
                    ffmpegDir = ffmpegDir
                )

                isInitialized = true
            }
        }

    }

    private fun initFFmpeg(
        appContext: Context,
        ffmpegDir: File
    ) {

        val ffmpegLib = File(binDirectory, FFMPEG_LIB_NAME)
        val ffmpegSize = ffmpegLib.length().toString()

        when {

            !ffmpegDir.exists() || shouldUpdateFFmpeg(
                appContext = appContext,
                version = ffmpegSize
            ) -> {

                FileUtils.deleteQuietly(ffmpegDir)
                ffmpegDir.mkdirs()

                try {

                    unzip(
                        sourceFile = ffmpegLib,
                        targetDirectory = ffmpegDir
                    )
                } catch (e: Exception) {

                    FileUtils.deleteQuietly(ffmpegDir)

                    throw YoutubeDLException(
                        message = "Failed To Initialize",
                        e = e
                    )
                }

                updateFFmpeg(
                    appContext = appContext,
                    version = ffmpegSize
                )
            }
        }
    }

    private fun shouldUpdateFFmpeg(
        appContext: Context,
        version: String
    ): Boolean {

        return version != SharedPrefsHelper.get(
            appContext = appContext,
            key = FFMPEG_LIB_VERSION
        )
    }

    private fun updateFFmpeg(
        appContext: Context,
        version: String
    ) {

        update(
            appContext = appContext,
            key = FFMPEG_LIB_VERSION,
            value = version
        )
    }
}