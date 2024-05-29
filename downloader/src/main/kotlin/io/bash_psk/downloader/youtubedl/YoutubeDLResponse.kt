package io.bash_psk.downloader.youtubedl

class YoutubeDLResponse(
    val command: List<String?>,
    val exitCode: Int,
    val elapsedTime: Long,
    val out: String,
    val error: String
)