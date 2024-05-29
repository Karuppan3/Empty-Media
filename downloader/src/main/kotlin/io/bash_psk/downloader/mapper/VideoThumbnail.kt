package io.bash_psk.downloader.mapper

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class VideoThumbnail {

    val url: String? = null
    val id: String? = null
}