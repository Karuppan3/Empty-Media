package io.bash_psk.empty_media.domain.database

enum class DownloadStatus(
    val status: String
) {

    COMPLETED(status = "Completed"),
    ENQUEUED(status = "Enqueued"),
    FAILED(status = "Failed"),
    RUNNING(status = "Running")
}