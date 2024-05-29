package io.bash_psk.thumbnail.thumbnail

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.net.Uri
import io.bash_psk.thumbnail.resource.ConstantAgent
import io.bash_psk.thumbnail.resource.ConstantLogs
import io.bash_psk.thumbnail.utils.SetLog
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.UserAgent
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.statement.readBytes
import io.ktor.http.Url
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import java.io.File
import java.io.FileOutputStream

object ThumbnailIO {

    private suspend fun retrieveThumbnail(
        context: Context,
        contentUri: Uri,
        thumbnailName: Int
    ) : Bitmap? {

        return CoroutineScope(
            context = Dispatchers.IO
        ).async {

            val metadataRetriever = MediaMetadataRetriever()

            try {

                metadataRetriever.setDataSource(context, contentUri)

                val bitmapResult = metadataRetriever.frameAtTime

                bitmapResult?.let { bitmap: Bitmap ->

                    saveThumbnail(
                        context = context,
                        bitmap = bitmap,
                        thumbnailName = thumbnailName
                    )
                }

                return@async bitmapResult

            } catch (e: Exception) {

                e.printStackTrace()

                return@async null
            } finally {

                metadataRetriever.release()
            }
        }.await()
    }

    private suspend fun downloadThumbnail(
        context: Context,
        contentUrl: String,
        thumbnailName: Int
    ) : Bitmap? {

        return CoroutineScope(
            context = Dispatchers.IO
        ).async {

            val downloadClient = HttpClient(engineFactory = Android) {

                install(plugin = Logging) {

                    level = LogLevel.ALL
                }

                install(plugin = UserAgent) {

                    agent = ConstantAgent.USER_AGENT
                }
            }

            val downloadedBytes = downloadClient.get(Url(urlString = contentUrl)).readBytes()

            val bitmapResult = BitmapFactory.decodeByteArray(downloadedBytes,0,downloadedBytes.size)

            bitmapResult?.let { bitmap: Bitmap ->

                saveThumbnail(
                    context = context,
                    bitmap = bitmap,
                    thumbnailName = thumbnailName
                )
            }

            return@async bitmapResult
        }.await()
    }

    private fun saveThumbnail(
        context: Context,
        bitmap: Bitmap,
        thumbnailName: Int
    ) {

        val fileName = "$thumbnailName.jpg"
        val thumbnailFile = File(context.cacheDir, fileName)
        val outputStream = FileOutputStream(thumbnailFile)

        val isThumbnailSaved = bitmap.compress(
            Bitmap.CompressFormat.JPEG,
            50,
            outputStream
        )

        when (isThumbnailSaved) {

            true -> {

                SetLog.setInfo(message = ConstantLogs.THUMBNAIL_SAVED)
            }

            false -> {

                SetLog.setInfo(message = ConstantLogs.THUMBNAIL_NOT_SAVED)
            }
        }

        outputStream.flush()
        outputStream.close()
    }

    private fun getThumbnail(
        context: Context,
        thumbnailName: Int
    ) : Bitmap? {

        val fileName = "$thumbnailName.jpg"
        val thumbnailFile = File(context.cacheDir, fileName)

        return BitmapFactory.decodeFile(thumbnailFile.absolutePath)
    }

    suspend fun loadThumbnail(
        context: Context,
        thumbnailName: Int,
        contentUri: Uri
    ) : Bitmap? {

        val fileName = "$thumbnailName.jpg"
        val thumbnailFile = File(context.cacheDir, fileName)

        val thumbnailBitmap = when (thumbnailFile.exists()) {

            true -> {

                getThumbnail(
                    context = context,
                    thumbnailName = thumbnailName
                )
            }

            false -> {

                retrieveThumbnail(
                    context = context,
                    contentUri = contentUri,
                    thumbnailName = thumbnailName
                )
            }
        }

        return thumbnailBitmap
    }

    suspend fun loadThumbnail(
        context: Context,
        thumbnailName: Int,
        contentUrl: String
    ) : Bitmap? {

        val fileName = "$thumbnailName.jpg"
        val thumbnailFile = File(context.cacheDir, fileName)

        val thumbnailBitmap = when (thumbnailFile.exists()) {

            true -> {

                getThumbnail(
                    context = context,
                    thumbnailName = thumbnailName
                )
            }

            false -> {

                downloadThumbnail(
                    context = context,
                    contentUrl = contentUrl,
                    thumbnailName = thumbnailName
                )
            }
        }

        return thumbnailBitmap
    }
}