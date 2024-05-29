package io.bash_psk.downloader.youtubedl

import io.bash_psk.downloader.utils.SetLog
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.io.Reader
import java.nio.charset.StandardCharsets
import java.util.regex.Pattern

internal class StreamProcessExtractor(
    private val buffer: StringBuffer,
    private val stream: InputStream,
    private val callback: (
        progress: Float,
        eta: Long,
        line: String
    ) -> Unit
) : Thread() {

    private val progressPattern = Pattern.compile(
        "\\[download]\\s+(\\d+\\.\\d)% .* ETA (\\d+):(\\d+)"
    )

    private val aria2cPattern = Pattern.compile(
        "\\[#\\w{6}.*\\((\\d*\\.*\\d+)%\\).*?((\\d+)m)*((\\d+)s)*]"
    )

    private var progress = PERCENT
    private var eta = ETA

    companion object {

        private const val ETA = 0L
        private const val PERCENT = 0.0f
        private const val GROUP_PERCENT = 1
        private const val GROUP_MINUTES = 2
        private const val GROUP_SECONDS = 3
    }

    init {

        start()
    }

    override fun run() {

        try {

            val input: Reader = InputStreamReader(
                stream,
                StandardCharsets.UTF_8
            )

            val currentLine = StringBuilder()
            var nextChar: Int

            while (input.read().also { readByte: Int -> nextChar = readByte } != -1) {

                buffer.append(nextChar.toChar())

                when {

                    nextChar == '\r'.code || nextChar == '\n'.code -> {

                        val line = currentLine.toString()

                        when {

                            line.startsWith(prefix = "[") -> {

                                processOutputLine(line = line)
                            }
                        }

                        currentLine.setLength(0)

                        continue
                    }
                }

                currentLine.append(nextChar.toChar())
            }
        } catch (ioException: IOException) {

            SetLog.setError(
                 message = "Failed To Read Stream",
                throwable = ioException
            )
        }
    }

    private fun processOutputLine(
        line: String
    ) {

        callback(getProgress(line = line), getEta(line = line), line)
    }

    private fun getProgress(
        line: String
    ) : Float {

        val matcher = progressPattern.matcher(line)

        when {

            matcher.find() -> {

                return matcher.group(GROUP_PERCENT).toFloat().also { progressLatest: Float ->

                    progress = progressLatest
                }
            }

            else -> {

                val mAria2c = aria2cPattern.matcher(line)

                when {

                    mAria2c.find() -> {

                        return mAria2c.group(1).toFloat().also { progressLatest: Float ->

                            progress = progressLatest
                        }
                    }
                }
            }
        }

        return progress
    }

    private fun getEta(line: String) : Long {

        val matcher = progressPattern.matcher(line)

        when {

            matcher.find() -> {

                return convertToSeconds(
                    minutes = matcher.group(GROUP_MINUTES),
                    seconds = matcher.group(GROUP_SECONDS)
                ).also {

                    eta = it.toLong()
                }.toLong()
            }

            else -> {

                val mAria2c = aria2cPattern.matcher(line)

                when {

                    mAria2c.find() -> {

                        return convertToSeconds(
                            minutes = mAria2c.group(3),
                            seconds = mAria2c.group(5)
                        ).also {

                            eta = it.toLong()
                        }.toLong()
                    }
                }
            }
        }

        return eta
    }

    private fun convertToSeconds(
        minutes: String?,
        seconds: String?
    ) : Int {

        return when {

            seconds == null -> {

                0
            }

            minutes == null -> {

                seconds.toInt()
            }

            else -> {

                (minutes.toInt() * 60) + seconds.toInt()
            }
        }
    }
}