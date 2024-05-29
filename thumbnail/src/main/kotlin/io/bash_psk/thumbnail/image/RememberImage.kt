package io.bash_psk.thumbnail.image

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
fun rememberImage(
    contentId: Int,
    contentPath: String
) : ImageBitmap? {

    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val cachedImageBitmap = remember {
        mutableStateOf<ImageBitmap?>(value = null)
    }

    DisposableEffect(
        key1 = contentId,
        key2 = lifecycleOwner.lifecycle,
        key3 = configuration.orientation
    ) {

        val thumbnailJob = CoroutineScope(
            context = Dispatchers.IO
        ).launch {

            cachedImageBitmap.value = ImageIO.loadImage(
                context = context,
                thumbnailName = contentId,
                contentPath = contentPath
            )?.asImageBitmap()
        }

        onDispose {

            thumbnailJob.cancel()
        }
    }

    return cachedImageBitmap.value
}