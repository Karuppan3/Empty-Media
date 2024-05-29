package io.bash_psk.preference.color

internal fun pointHue(
    pointX: Float,
    panelWidth: Float,
    panelLeft: Float,
    panelRight: Float
) : Float {

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

    return x * 360.0f / panelWidth
}