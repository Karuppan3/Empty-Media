package io.bash_psk.empty_media.domain.resource

import android.annotation.SuppressLint

@SuppressLint("SdCardPath")
object ConstantCommand {

    const val ROOT_PATH = "/sdcard/Download/PSK"
    const val FILE_NAME = "%(title)s.%(ext)s"
    const val RESTRICT_FILE_NAME = "--restrict-filenames"
    const val DOWNLOAD_SECTIONS = "--download-sections"
    const val BEST_AUDIO = "bestaudio"

    const val DOWNLOADER_OPTION = "--downloader"
    const val DOWNLOADER_ARGS = "libaria2c.so"

    const val EXTERNAL_DOWNLOADER_OPTION = "--external-downloader-args"
    const val EXTERNAL_DOWNLOADER_ARGS = "aria2c:\"--summary-interval=1\""
}