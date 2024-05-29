package io.bash_psk.downloader.youtubedl

import android.content.Context
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ArrayNode
import io.bash_psk.commons.SharedPrefsHelper
import io.bash_psk.commons.SharedPrefsHelper.update
import io.bash_psk.downloader.youtubedl.YoutubeDL.UpdateChannel
import io.bash_psk.downloader.youtubedl.YoutubeDL.UpdateStatus
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.IOException
import java.net.URL

internal object YoutubeDLUpdater {

    private const val ytdlStableUrl = "https://api.github.com/repos/yt-dlp/yt-dlp/releases/latest"
    private const val ytdlNightlyUrl = "https://api.github.com/repos/yt-dlp/yt-dlp-nightly-builds/releases/latest"
    private const val dlpBinaryName = "yt-dlp"
    private const val dlpVersionKey = "dlpVersion"
    private const val dlpVersionNameKey = "dlpVersionName"

    @Throws(
        IOException::class,
        YoutubeDLException::class
    )
    internal fun update(
        appContext: Context?,
        youtubeDLChannel: UpdateChannel = UpdateChannel.STABLE
    ) : UpdateStatus {

        val json = appContext?.let { context: Context ->

            checkForUpdate(
                appContext = context,
                youtubeDLChannel = youtubeDLChannel
            )
        } ?: return UpdateStatus.ALREADY_UP_TO_DATE

        val downloadUrl = getDownloadUrl(json = json)

        val file = download(
            appContext = appContext,
            url = downloadUrl
        )

        val ytdlpDir = getYoutubeDLDir(
            appContext = appContext
        )

        val binary = File(ytdlpDir, dlpBinaryName)

        try {

            when {

                ytdlpDir.exists() -> {

                    FileUtils.deleteDirectory(ytdlpDir)
                }
            }

            ytdlpDir.mkdirs()
            FileUtils.copyFile(file, binary)
        } catch (e: Exception) {

            FileUtils.deleteQuietly(ytdlpDir)

            YoutubeDL.initYtdlp(
                appContext = appContext,
                ytdlpDir = ytdlpDir
            )

            throw YoutubeDLException(e = e)
        } finally {

            file.delete()
        }

        updateSharedPrefs(
            appContext = appContext,
            tag = getTag(json = json),
            name = getName(json = json)
        )

        return UpdateStatus.DONE
    }

    private fun updateSharedPrefs(
        appContext: Context,
        tag: String,
        name: String
    ) {

        update(
            appContext = appContext,
            key = dlpVersionKey,
            value = tag
        )

        update(
            appContext = appContext,
            key = dlpVersionNameKey,
            value = name
        )
    }

    @Throws(IOException::class)
    private fun checkForUpdate(
        appContext: Context,
        youtubeDLChannel: UpdateChannel
    ) : JsonNode? {

        val url = URL(youtubeDLChannel.apiUrl)
        val json = ObjectMapper().readTree(url)
        val newVersion = getTag(json = json)

        val oldVersion = SharedPrefsHelper.get(
            appContext = appContext,
            key = dlpVersionKey
        )

        return when (newVersion == oldVersion) {

            true -> {

                null
            }

            false -> {

                json
            }
        }
    }

    private fun getTag(
        json: JsonNode
    ) : String {

        return json["tag_name"].asText()
    }

    private fun getName(
        json: JsonNode
    ) : String {

        return json["name"].asText()
    }

    @Throws(YoutubeDLException::class)
    private fun getDownloadUrl(
        json: JsonNode
    ) : String {

        val assets = json["assets"] as ArrayNode
        var downloadUrl = ""

        assets.forEach { asset: JsonNode ->

            when (YoutubeDL.YTDLP_BIN) {

                asset["name"].asText() -> {

                    downloadUrl = asset["browser_download_url"].asText()
                }
            }
        }

        when (downloadUrl.isEmpty()) {

            true -> {

                throw YoutubeDLException(
                    message = "Unable To Get Download Url"
                )
            }

            false -> {

                return downloadUrl
            }
        }
    }

    @Throws(IOException::class)
    private fun download(
        appContext: Context,
        url: String
    ) : File {

        val downloadUrl = URL(url)

        val file = File.createTempFile(
            dlpBinaryName,
            null,
            appContext.cacheDir
        )

        FileUtils.copyURLToFile(
            downloadUrl,
            file,
            5000,
            10000
        )

        return file
    }

    private fun getYoutubeDLDir(
        appContext: Context
    ) : File {

        val baseDir = File(
            appContext.noBackupFilesDir,
            YoutubeDL.BASE_NAME
        )

        return File(
            baseDir,
            YoutubeDL.YTDLP_DIRECTORY_NAME
        )
    }

    fun version(
        appContext: Context?
    ) : String? {

        return appContext?.let { context: Context ->

            SharedPrefsHelper.get(
                appContext = context,
                key = dlpVersionKey
            )
        }
    }

    fun versionName(
        appContext: Context?
    ) : String? {

        return appContext?.let { context: Context ->

            SharedPrefsHelper.get(
                appContext = context,
                key = dlpVersionNameKey
            )
        }
    }
}