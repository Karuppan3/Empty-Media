package io.bash_psk.downloader.youtubedl

interface DownloadProgressCallback {

    fun onProgressUpdate(
        progress: Float,
        etaInSeconds: Long,
        line: String?
    )
}