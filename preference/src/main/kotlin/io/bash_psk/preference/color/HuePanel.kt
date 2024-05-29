package io.bash_psk.preference.color

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color.HSVToColor
import android.graphics.Paint
import android.graphics.RectF
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
internal fun HuePanel(
    onHueColor: (
        hue: Float
    ) -> Unit
) {

    val coroutineScope = rememberCoroutineScope()

    val interactionSource = remember {
        MutableInteractionSource()
    }

    val pressOffset = remember {
        mutableStateOf(value = Offset.Zero)
    }

    Canvas(
        modifier = Modifier
            .width(width = 300.dp)
            .height(height = 40.dp)
            .clip(shape = MaterialTheme.shapes.medium)
            .emitDragGesture(interactionSource = interactionSource)
    ) {

        val drawScopeSize = size

        val bitmap = Bitmap.createBitmap(
            size.width.toInt(),
            size.height.toInt(),
            Bitmap.Config.ARGB_8888
        )

        val huePanel = RectF(
            0f,
            0f,
            bitmap.width.toFloat(),
            bitmap.height.toFloat()
        )

        val hueColors = IntArray(size = (huePanel.width()).toInt())

        var hue = 0f

        for (hueColor in hueColors.indices) {

            hueColors[hueColor] = HSVToColor(floatArrayOf(hue, 1f, 1f))
            hue += 360f / hueColors.size
        }

        for (hueColor in hueColors.indices) {

            Canvas(bitmap).drawLine(
                hueColor.toFloat(),
                0F,
                hueColor.toFloat(),
                huePanel.bottom,
                Paint().apply {

                    strokeWidth = 0F
                    color = hueColors[hueColor]
                }
            )
        }

        drawBitmap(bitmap = bitmap, panel = huePanel)

        coroutineScope.pressCollector(
            interactionSource = interactionSource
        ) { offset: Offset ->

            val pressPosition = offset.x.coerceIn(range = 0f..drawScopeSize.width)

            val selectedHue = pointHue(
                pointX = pressPosition,
                panelWidth = huePanel.width(),
                panelLeft = huePanel.left,
                panelRight = huePanel.right
            )

            pressOffset.value = Offset(x= pressPosition, y = 0f)
            onHueColor(selectedHue)
        }

        drawCircle(
            color = Color.White,
            radius = size.height / 2,
            center = Offset(x = pressOffset.value.x, y = size.height / 2),
            style = Stroke(width = 2.dp.toPx())
        )
    }
}