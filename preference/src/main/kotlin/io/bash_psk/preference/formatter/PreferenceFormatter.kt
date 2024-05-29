package io.bash_psk.preference.formatter

import androidx.compose.ui.graphics.Color
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.Locale
import java.util.concurrent.TimeUnit

object PreferenceFormatter {

    fun decimalFormatter(
        decimal: Float,
        index: Int
    ) : Float {

        return BigDecimal(
            decimal.toDouble()
        ).setScale(
            index,
            RoundingMode.HALF_EVEN
        ).toFloat()
    }

    fun colorToHex(color: Color) : String {

        val alpha = (color.alpha * 255).toInt()
        val red = (color.red * 255).toInt()
        val green = (color.green * 255).toInt()
        val blue = (color.blue * 255).toInt()

        return String.format(
            Locale.getDefault(),
            "%02X%02X%02X%02X",
            alpha,
            red,
            green,
            blue
        )
    }

    fun hexToColor(hex: String) : Color {

        return Color(hex.toLong(radix = 16))
    }

    fun durationFormatter(
        duration: Long
    ) : String {

        return when {

            duration == 0L -> {

                "00"
            }

            duration < 3600000L -> {

                String.format(
                    "%02d:%02d",
                    TimeUnit.MILLISECONDS.toMinutes(duration) % 60,
                    TimeUnit.MILLISECONDS.toSeconds(duration) % 60
                )
            }

            else -> {

                String.format(
                    "%02d:%02d:%02d",
                    TimeUnit.MILLISECONDS.toHours(duration),
                    TimeUnit.MILLISECONDS.toMinutes(duration) % 60,
                    TimeUnit.MILLISECONDS.toSeconds(duration) % 60
                )
            }
        }
    }
}