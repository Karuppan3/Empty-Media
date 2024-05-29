package io.bash_psk.preference.color

import android.graphics.Bitmap
import android.graphics.RectF
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.core.graphics.toRect

internal fun DrawScope.drawBitmap(
    bitmap: Bitmap,
    panel: RectF
) {

    drawIntoCanvas { canvas: Canvas ->

        canvas.nativeCanvas.drawBitmap(
            bitmap,
            null,
            panel.toRect(),
            null
        )
    }
}