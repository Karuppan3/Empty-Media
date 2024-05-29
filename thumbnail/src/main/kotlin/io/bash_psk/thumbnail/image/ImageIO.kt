package io.bash_psk.thumbnail.image

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import io.bash_psk.thumbnail.resource.ConstantLogs
import io.bash_psk.thumbnail.utils.SetLog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import java.io.File
import java.io.FileOutputStream

object ImageIO {

    private suspend fun retrieveImage(
        context: Context,
        contentPath: String,
        thumbnailName: Int
    ) : Bitmap? {

        return CoroutineScope(
            context = Dispatchers.IO
        ).async {

            val bitmapResult = BitmapFactory.decodeFile(contentPath)

            bitmapResult?.let { bitmap: Bitmap ->

                saveImage(
                    context = context,
                    bitmap = bitmap,
                    thumbnailName = thumbnailName
                )
            }

            return@async bitmapResult
        }.await()
    }

    private fun saveImage(
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

    private fun getImage(
        context: Context,
        thumbnailName: Int
    ) : Bitmap? {

        val fileName = "$thumbnailName.jpg"
        val thumbnailFile = File(context.cacheDir, fileName)

        return BitmapFactory.decodeFile(thumbnailFile.absolutePath)
    }

    suspend fun loadImage(
        context: Context,
        thumbnailName: Int,
        contentPath: String
    ) : Bitmap? {

        val fileName = "$thumbnailName.jpg"
        val thumbnailFile = File(context.cacheDir, fileName)

        val thumbnailBitmap = when (thumbnailFile.exists()) {

            true -> {

                getImage(
                    context = context,
                    thumbnailName = thumbnailName
                )
            }

            false -> {

                retrieveImage(
                    context = context,
                    contentPath = contentPath,
                    thumbnailName = thumbnailName
                )
            }
        }

        return thumbnailBitmap
    }
}