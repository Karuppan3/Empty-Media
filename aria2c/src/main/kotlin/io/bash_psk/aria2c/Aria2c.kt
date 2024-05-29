package io.bash_psk.aria2c

import android.content.Context
import io.bash_psk.commons.SharedPrefsHelper
import io.bash_psk.commons.utils.ZipUtils
import io.bash_psk.downloader.youtubedl.YoutubeDLException
import org.apache.commons.io.FileUtils
import java.io.File

object Aria2c {

    @JvmStatic
    fun getInstance() = this

    private const val BASE_NAME = "empty media"
    private const val PACKAGES_ROOT = "packages"
    private const val ARIA2C_DIRECTORY_NAME = "aria2c"
    private const val ARIA2C_LIB_NAME = "libaria2c.zip.so"
    private const val ARIA2C_LIB_VERSION = "aria2cLibVersion"

    private var isInitialized = false
    private var binDirectory: File? = null

    @Synchronized
    fun init(
        appContext: Context
    ) {

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
                val aria2cDir = File(packagesDir, ARIA2C_DIRECTORY_NAME)

                initAria2c(
                    appContext = appContext,
                    aria2cDir = aria2cDir
                )

                isInitialized = true
            }
        }

    }

    private fun initAria2c(
        appContext: Context,
        aria2cDir: File
    ) {

        val aria2cLib = File(binDirectory, ARIA2C_LIB_NAME)
        val aria2cSize = aria2cLib.length().toString()

        when {

            !aria2cDir.exists() || shouldUpdateAria2c(
                appContext = appContext,
                version = aria2cSize
            ) -> {

                FileUtils.deleteQuietly(aria2cDir)
                aria2cDir.mkdirs()

                try {

                    ZipUtils.unzip(
                        sourceFile = aria2cLib,
                        targetDirectory = aria2cDir
                    )
                } catch (e: Exception) {

                    FileUtils.deleteQuietly(aria2cDir)

                    throw YoutubeDLException(
                        message = "Failed To Initialize",
                        e = e
                    )
                }

                updateAria2c(
                    appContext = appContext,
                    version = aria2cSize
                )
            }
        }
    }

    private fun shouldUpdateAria2c(
        appContext: Context,
        version: String
    ) : Boolean {

        return version != SharedPrefsHelper.get(
            appContext = appContext,
            key = ARIA2C_LIB_VERSION
        )
    }

    private fun updateAria2c(
        appContext: Context,
        version: String
    ) {

        SharedPrefsHelper.update(
            appContext = appContext,
            key = ARIA2C_LIB_VERSION,
            value = version
        )
    }
}