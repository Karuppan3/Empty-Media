package io.bash_psk.preference.color

internal fun pointSaturation(
    pointX: Float,
    pointY: Float,
    panelWidth: Float,
    panelHeight: Float,
    panelLeft: Float,
    panelRight: Float,
    panelTop: Float,
    panelBottom: Float
) : Pair<Float, Float> {

    val x = when {

        pointX < panelLeft -> {

            0.0f
        }

        pointX > panelRight -> {

            panelWidth
        }

        else -> {

            pointX - panelLeft
        }
    }

    val y = when {

        pointY < panelTop -> {

            0.0f
        }

        pointY > panelBottom -> {

            panelHeight
        }

        else -> {

            pointY - panelTop
        }
    }

    val saturationPoint = 1.0f / panelWidth * x
    val valuePoint = 1.0f - 1.0f / panelHeight * y

    return saturationPoint to valuePoint
}