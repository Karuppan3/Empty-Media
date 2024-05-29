package io.bash_psk.thumbnail.thumbnail

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun rememberThumbnail(
    contentId: Int,
    contentUri: Uri
) : ImageBitmap? {

    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val cachedImageBitmap = remember {
        mutableStateOf<ImageBitmap?>(value = null)
    }

    DisposableEffect(
        contentId,
        contentUri,
        lifecycleOwner.lifecycle,
        configuration.orientation
    ) {

        val thumbnailJob = CoroutineScope(
            context = Dispatchers.IO
        ).launch {

            when {

                contentUri != Uri.EMPTY -> {

                    cachedImageBitmap.value = ThumbnailIO.loadThumbnail(
                        context = context,
                        thumbnailName = contentId,
                        contentUri = contentUri
                    )?.asImageBitmap()
                }
            }
        }

        onDispose {

            thumbnailJob.cancel()
        }
    }

    return cachedImageBitmap.value
}

@Composable
fun rememberThumbnail(
    contentId: Int,
    contentUrl: String
) : ImageBitmap? {

    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val cachedImageBitmap = remember {
        mutableStateOf<ImageBitmap?>(value = null)
    }

    DisposableEffect(
        key1 = contentUrl,
        key2 = lifecycleOwner.lifecycle,
        key3 = configuration.orientation
    ) {

        val thumbnailJob = CoroutineScope(
            context = Dispatchers.IO
        ).launch {

            when {

                contentUrl.isNotEmpty() -> {

                    cachedImageBitmap.value = ThumbnailIO.loadThumbnail(
                        context = context,
                        thumbnailName = contentId,
                        contentUrl = contentUrl
                    )?.asImageBitmap()
                }
            }
        }

        onDispose {

            thumbnailJob.cancel()
        }
    }

    return cachedImageBitmap.value
}