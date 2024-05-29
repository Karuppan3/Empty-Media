package io.bash_psk.preference.color

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color.HSVToColor
import android.graphics.ComposeShader
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.RectF
import android.graphics.Shader
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.size
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
internal fun SaturationPanel(
    hue: Float,
    onSaturation: (
        saturation: Float,
        value2: Float
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
            .size(size = 300.dp)
            .clip(shape = MaterialTheme.shapes.medium)
            .emitDragGesture(interactionSource = interactionSource)
    ) {

        val drawScopeSize = size

        val bitmap = Bitmap.createBitmap(
            size.width.toInt(),
            size.height.toInt(),
            Bitmap.Config.ARGB_8888
        )

        val saturationPanel = RectF(
            0f,
            0f,
            bitmap.width.toFloat(),
            bitmap.height.toFloat()
        )

        val rgb = HSVToColor(floatArrayOf(hue, 1f, 1f))

        val saturationShader =  LinearGradient(
            saturationPanel.left,
            saturationPanel.top,
            saturationPanel.right,
            saturationPanel.top,
            -0x1,
            rgb,
            Shader.TileMode.CLAMP
        )

        val valueShader = LinearGradient(
            saturationPanel.left,
            saturationPanel.top,
            saturationPanel.left,
            saturationPanel.bottom,
            -0x1,
            -0x1000000,
            Shader.TileMode.CLAMP
        )

        Canvas(bitmap).drawRoundRect(
            saturationPanel,
            12.dp.toPx(),
            12.dp.toPx(),
            Paint().apply {

                shader = ComposeShader(
                    valueShader,
                    saturationShader,
                    PorterDuff.Mode.MULTIPLY
                )
            }
        )

        drawBitmap(bitmap = bitmap, panel = saturationPanel)

        coroutineScope.pressCollector(
            interactionSource = interactionSource
        ) { offset: Offset ->

            val pressPositionOffset = Offset(
                x = offset.x.coerceIn(range = 0f..drawScopeSize.width),
                y = offset.y.coerceIn(range = 0f..drawScopeSize.height)
            )

            val (satPoint, valuePoint) = pointSaturation(
                pointX = pressPositionOffset.x,
                pointY = pressPositionOffset.y,
                panelWidth = saturationPanel.width(),
                panelHeight = saturationPanel.height(),
                panelLeft = saturationPanel.left,
                panelRight = saturationPanel.right,
                panelTop = saturationPanel.top,
                panelBottom = saturationPanel.bottom
            )

            pressOffset.value = pressPositionOffset
            onSaturation(satPoint, valuePoint)
        }

        drawCircle(
            color = Color.White,
            radius = 8.dp.toPx(),
            center = pressOffset.value,
            style = Stroke(width = 2.dp.toPx())
        )

        drawCircle(
            color = Color.White,
            radius = 2.dp.toPx(),
            center = pressOffset.value,
        )
    }
}