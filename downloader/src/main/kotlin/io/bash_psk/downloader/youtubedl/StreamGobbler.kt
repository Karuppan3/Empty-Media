package io.bash_psk.downloader.youtubedl

import io.bash_psk.downloader.utils.SetLog
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets

internal class StreamGobbler(
    private val buffer: StringBuffer,
    private val stream: InputStream
) : Thread() {

    init {

        start()
    }

    override fun run() {

        try {

            val inputStreamReader = InputStreamReader(
                stream,
                StandardCharsets.UTF_8
            )

            var nextChar: Int

            while (inputStreamReader.read().also { readByte: Int -> nextChar = readByte } != -1) {

                buffer.append(nextChar.toChar())
            }
        } catch (exception: IOException) {

            SetLog.setError(
                message = exception.localizedMessage,
                throwable = exception
            )
        }
    }
}