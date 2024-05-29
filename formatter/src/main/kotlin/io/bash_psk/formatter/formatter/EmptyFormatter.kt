package io.bash_psk.formatter.formatter

import android.content.Context
import android.icu.text.SimpleDateFormat
import android.text.format.Formatter
import androidx.compose.ui.graphics.Color
import io.bash_psk.formatter.utils.SetLog
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.ParseException
import java.util.Locale
import java.util.concurrent.TimeUnit

object EmptyFormatter {

    fun dateTime(
        dateTime: Long,
        pattern: String
    ) : String {

        return SimpleDateFormat(pattern, Locale.getDefault()).format(dateTime)
    }

    fun decimalFormatter(
        decimalValue: Double,
        afterIndex: Int
    ) : Double {

        return BigDecimal(
            decimalValue
        ).setScale(
            afterIndex,
            RoundingMode.HALF_EVEN
        ).toDouble()
    }

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

    fun fileSize(
        context: Context,
        size: Long
    ) : String {

        return Formatter.formatFileSize(context, size)
    }

    fun dateTimeToLong(
        dateTime: String,
        pattern: String
    ) : Long {

        return try {

            SimpleDateFormat(
                pattern,
                Locale.getDefault()
            ).parse(dateTime).time
        } catch (parseException: ParseException) {

            SetLog.setError(
                message = parseException.localizedMessage,
                throwable = parseException.cause
            )

            0L
        }
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
}