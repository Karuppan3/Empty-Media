package io.bash_psk.empty_media.domain.map

import io.bash_psk.empty_media.domain.downloads.DownloadSection
import io.bash_psk.empty_media.domain.media.MediaFormat
import io.bash_psk.empty_media.domain.navigation.NavRoute

object ListData {

    val SCREEN_LIST = listOf(
        NavRoute.Home,
        NavRoute.Downloads,
        NavRoute.Commands,
        NavRoute.Settings
    )

    val MEDIA_FORMAT_LIST = listOf(
        MediaFormat.INIT,
        MediaFormat.AUDIO_LOW,
        MediaFormat.AUDIO_MEDIUM,
        MediaFormat.VIDEO_144P,
        MediaFormat.VIDEO_144P60F_HDR,
        MediaFormat.VIDEO_240P,
        MediaFormat.VIDEO_240P60F_HDR,
        MediaFormat.VIDEO_360P,
        MediaFormat.VIDEO_360P60F_HDR,
        MediaFormat.VIDEO_480P,
        MediaFormat.VIDEO_480P60F_HDR,
        MediaFormat.VIDEO_720P,
        MediaFormat.VIDEO_720P60F,
        MediaFormat.VIDEO_720P60F_HDR,
        MediaFormat.VIDEO_1080P,
        MediaFormat.VIDEO_1080PREMIUM,
        MediaFormat.VIDEO_1080P60F,
        MediaFormat.VIDEO_1080P60F_HDR,
        MediaFormat.VIDEO_1440P,
        MediaFormat.VIDEO_1440P60F,
        MediaFormat.VIDEO_1440P60F_HDR,
        MediaFormat.VIDEO_2160P,
        MediaFormat.VIDEO_2160P60F,
        MediaFormat.VIDEO_2160P60F_HDR,
        MediaFormat.VIDEO_4320P,
        MediaFormat.VIDEO_4320P60F,
        MediaFormat.VIDEO_4320P60F_HDR
    )

    val DOWNLOAD_SECTION_LIST = listOf(
        DownloadSection.ALL,
        DownloadSection.COMPLETED,
        DownloadSection.FAILED
    )
}