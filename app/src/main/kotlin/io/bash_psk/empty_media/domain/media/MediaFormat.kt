package io.bash_psk.empty_media.domain.media

import io.bash_psk.empty_media.domain.resource.ConstantFormat

sealed class MediaFormat(
    val name: String,
    val formatPrimary: Int,
    val formatSecondary: Int,
    val formatTertiary: Int
) {

    data object INIT : MediaFormat(
        name = ConstantFormat.SELECT_FORMAT_NAME,
        formatPrimary = ConstantFormat.SELECT_FORMAT_PRIMARY,
        formatSecondary = ConstantFormat.SELECT_FORMAT_SECONDARY,
        formatTertiary = ConstantFormat.SELECT_FORMAT_TERTIARY
    )
    
    data object AUDIO_LOW : MediaFormat(
        name = ConstantFormat.AUDIO_LOW_FORMAT_NAME,
        formatPrimary = ConstantFormat.AUDIO_LOW_FORMAT_PRIMARY,
        formatSecondary = ConstantFormat.AUDIO_LOW_FORMAT_SECONDARY,
        formatTertiary = ConstantFormat.AUDIO_LOW_FORMAT_TERTIARY
    )
    
    data object AUDIO_MEDIUM : MediaFormat(
        name = ConstantFormat.AUDIO_MEDIUM_FORMAT_NAME,
        formatPrimary = ConstantFormat.AUDIO_MEDIUM_FORMAT_PRIMARY,
        formatSecondary = ConstantFormat.AUDIO_MEDIUM_FORMAT_SECONDARY,
        formatTertiary = ConstantFormat.AUDIO_MEDIUM_FORMAT_TERTIARY
    )
    
    data object VIDEO_144P : MediaFormat(
        name = ConstantFormat.VIDEO_144P_FORMAT_NAME,
        formatPrimary = ConstantFormat.VIDEO_144P_FORMAT_PRIMARY,
        formatSecondary = ConstantFormat.VIDEO_144P_FORMAT_SECONDARY,
        formatTertiary = ConstantFormat.VIDEO_144P_FORMAT_TERTIARY
    )
    
    data object VIDEO_144P60F_HDR : MediaFormat(
        name = ConstantFormat.VIDEO_144P60F_HDR_FORMAT_NAME,
        formatPrimary = ConstantFormat.VIDEO_144P60F_HDR_FORMAT_PRIMARY,
        formatSecondary = ConstantFormat.VIDEO_144P60F_HDR_FORMAT_SECONDARY,
        formatTertiary = ConstantFormat.VIDEO_144P60F_HDR_FORMAT_TERTIARY
    )
    
    data object VIDEO_240P : MediaFormat(
        name = ConstantFormat.VIDEO_240P_FORMAT_NAME,
        formatPrimary = ConstantFormat.VIDEO_240P_FORMAT_PRIMARY,
        formatSecondary = ConstantFormat.VIDEO_240P_FORMAT_SECONDARY,
        formatTertiary = ConstantFormat.VIDEO_240P_FORMAT_TERTIARY
    )
    
    data object VIDEO_240P60F_HDR : MediaFormat(
        name = ConstantFormat.VIDEO_240P60F_HDR_FORMAT_NAME,
        formatPrimary = ConstantFormat.VIDEO_240P60F_HDR_FORMAT_PRIMARY,
        formatSecondary = ConstantFormat.VIDEO_240P60F_HDR_FORMAT_SECONDARY,
        formatTertiary = ConstantFormat.VIDEO_240P60F_HDR_FORMAT_TERTIARY
    )
    
    data object VIDEO_360P : MediaFormat(
        name = ConstantFormat.VIDEO_360P_FORMAT_NAME,
        formatPrimary = ConstantFormat.VIDEO_360P_FORMAT_PRIMARY,
        formatSecondary = ConstantFormat.VIDEO_360P_FORMAT_SECONDARY,
        formatTertiary = ConstantFormat.VIDEO_360P_FORMAT_TERTIARY
    )

    data object VIDEO_360P60F_HDR : MediaFormat(
        name = ConstantFormat.VIDEO_360P60F_HDR_FORMAT_NAME,
        formatPrimary = ConstantFormat.VIDEO_360P60F_HDR_FORMAT_PRIMARY,
        formatSecondary = ConstantFormat.VIDEO_360P60F_HDR_FORMAT_SECONDARY,
        formatTertiary = ConstantFormat.VIDEO_360P60F_HDR_FORMAT_TERTIARY
    )
    
    data object VIDEO_480P : MediaFormat(
        name = ConstantFormat.VIDEO_480P_FORMAT_NAME,
        formatPrimary = ConstantFormat.VIDEO_480P_FORMAT_PRIMARY,
        formatSecondary = ConstantFormat.VIDEO_480P_FORMAT_SECONDARY,
        formatTertiary = ConstantFormat.VIDEO_480P_FORMAT_TERTIARY
    )

    data object VIDEO_480P60F_HDR : MediaFormat(
        name = ConstantFormat.VIDEO_480P60F_HDR_FORMAT_NAME,
        formatPrimary = ConstantFormat.VIDEO_480P60F_HDR_FORMAT_PRIMARY,
        formatSecondary = ConstantFormat.VIDEO_480P60F_HDR_FORMAT_SECONDARY,
        formatTertiary = ConstantFormat.VIDEO_480P60F_HDR_FORMAT_TERTIARY
    )
    
    data object VIDEO_720P : MediaFormat(
        name = ConstantFormat.VIDEO_720P_FORMAT_NAME,
        formatPrimary = ConstantFormat.VIDEO_720P_FORMAT_PRIMARY,
        formatSecondary = ConstantFormat.VIDEO_720P_FORMAT_SECONDARY,
        formatTertiary = ConstantFormat.VIDEO_720P_FORMAT_TERTIARY
    )
    
    data object VIDEO_720P60F : MediaFormat(
        name = ConstantFormat.VIDEO_720P60F_FORMAT_NAME,
        formatPrimary = ConstantFormat.VIDEO_720P60F_FORMAT_PRIMARY,
        formatSecondary = ConstantFormat.VIDEO_720P60F_FORMAT_SECONDARY,
        formatTertiary = ConstantFormat.VIDEO_720P60F_FORMAT_TERTIARY
    )

    data object VIDEO_720P60F_HDR : MediaFormat(
        name = ConstantFormat.VIDEO_720P60F_HDR_FORMAT_NAME,
        formatPrimary = ConstantFormat.VIDEO_720P60F_HDR_FORMAT_PRIMARY,
        formatSecondary = ConstantFormat.VIDEO_720P60F_HDR_FORMAT_SECONDARY,
        formatTertiary = ConstantFormat.VIDEO_720P60F_HDR_FORMAT_TERTIARY
    )
    
    data object VIDEO_1080P : MediaFormat(
        name = ConstantFormat.VIDEO_1080P_FORMAT_NAME,
        formatPrimary = ConstantFormat.VIDEO_1080P_FORMAT_PRIMARY,
        formatSecondary = ConstantFormat.VIDEO_1080P_FORMAT_SECONDARY,
        formatTertiary = ConstantFormat.VIDEO_1080P_FORMAT_TERTIARY
    )
    
    data object VIDEO_1080PREMIUM : MediaFormat(
        name = ConstantFormat.VIDEO_1080PREMIUM_FORMAT_NAME,
        formatPrimary = ConstantFormat.VIDEO_1080PREMIUM_FORMAT_PRIMARY,
        formatSecondary = ConstantFormat.VIDEO_1080PREMIUM_FORMAT_SECONDARY,
        formatTertiary = ConstantFormat.VIDEO_1080PREMIUM_FORMAT_TERTIARY
    )
    
    data object VIDEO_1080P60F : MediaFormat(
        name = ConstantFormat.VIDEO_1080P60F_FORMAT_NAME,
        formatPrimary = ConstantFormat.VIDEO_1080P60F_FORMAT_PRIMARY,
        formatSecondary = ConstantFormat.VIDEO_1080P60F_FORMAT_SECONDARY,
        formatTertiary = ConstantFormat.VIDEO_1080P60F_FORMAT_TERTIARY
    )

    data object VIDEO_1080P60F_HDR : MediaFormat(
        name = ConstantFormat.VIDEO_1080P60F_HDR_FORMAT_NAME,
        formatPrimary = ConstantFormat.VIDEO_1080P60F_HDR_FORMAT_PRIMARY,
        formatSecondary = ConstantFormat.VIDEO_1080P60F_HDR_FORMAT_SECONDARY,
        formatTertiary = ConstantFormat.VIDEO_1080P60F_HDR_FORMAT_TERTIARY
    )
    
    data object VIDEO_1440P : MediaFormat(
        name = ConstantFormat.VIDEO_1440P_FORMAT_NAME,
        formatPrimary = ConstantFormat.VIDEO_1440P_FORMAT_PRIMARY,
        formatSecondary = ConstantFormat.VIDEO_1440P_FORMAT_SECONDARY,
        formatTertiary = ConstantFormat.VIDEO_1440P_FORMAT_TERTIARY
    )
    
    data object VIDEO_1440P60F : MediaFormat(
        name = ConstantFormat.VIDEO_1440P60F_FORMAT_NAME,
        formatPrimary = ConstantFormat.VIDEO_1440P60F_FORMAT_PRIMARY,
        formatSecondary = ConstantFormat.VIDEO_1440P60F_FORMAT_SECONDARY,
        formatTertiary = ConstantFormat.VIDEO_1440P60F_FORMAT_TERTIARY
    )

    data object VIDEO_1440P60F_HDR : MediaFormat(
        name = ConstantFormat.VIDEO_1440P60F_HDR_FORMAT_NAME,
        formatPrimary = ConstantFormat.VIDEO_1440P60F_HDR_FORMAT_PRIMARY,
        formatSecondary = ConstantFormat.VIDEO_1440P60F_HDR_FORMAT_SECONDARY,
        formatTertiary = ConstantFormat.VIDEO_1440P60F_HDR_FORMAT_TERTIARY
    )
    
    data object VIDEO_2160P : MediaFormat(
        name = ConstantFormat.VIDEO_2160P_FORMAT_NAME,
        formatPrimary = ConstantFormat.VIDEO_2160P_FORMAT_PRIMARY,
        formatSecondary = ConstantFormat.VIDEO_2160P_FORMAT_SECONDARY,
        formatTertiary = ConstantFormat.VIDEO_2160P_FORMAT_TERTIARY
    )
    
    data object VIDEO_2160P60F : MediaFormat(
        name = ConstantFormat.VIDEO_2160P60F_FORMAT_NAME,
        formatPrimary = ConstantFormat.VIDEO_2160P60F_FORMAT_PRIMARY,
        formatSecondary = ConstantFormat.VIDEO_2160P60F_FORMAT_SECONDARY,
        formatTertiary = ConstantFormat.VIDEO_2160P60F_FORMAT_TERTIARY
    )

    data object VIDEO_2160P60F_HDR : MediaFormat(
        name = ConstantFormat.VIDEO_2160P60F_HDR_FORMAT_NAME,
        formatPrimary = ConstantFormat.VIDEO_2160P60F_HDR_FORMAT_PRIMARY,
        formatSecondary = ConstantFormat.VIDEO_2160P60F_HDR_FORMAT_SECONDARY,
        formatTertiary = ConstantFormat.VIDEO_2160P60F_HDR_FORMAT_TERTIARY
    )
    
    data object VIDEO_4320P : MediaFormat(
        name = ConstantFormat.VIDEO_4320P_FORMAT_NAME,
        formatPrimary = ConstantFormat.VIDEO_4320P_FORMAT_PRIMARY,
        formatSecondary = ConstantFormat.VIDEO_4320P_FORMAT_SECONDARY,
        formatTertiary = ConstantFormat.VIDEO_4320P_FORMAT_TERTIARY
    )
    
    data object VIDEO_4320P60F : MediaFormat(
        name = ConstantFormat.VIDEO_4320P60F_FORMAT_NAME,
        formatPrimary = ConstantFormat.VIDEO_4320P60F_FORMAT_PRIMARY,
        formatSecondary = ConstantFormat.VIDEO_4320P60F_FORMAT_SECONDARY,
        formatTertiary = ConstantFormat.VIDEO_4320P60F_FORMAT_TERTIARY
    )

    data object VIDEO_4320P60F_HDR : MediaFormat(
        name = ConstantFormat.VIDEO_4320P60F_HDR_FORMAT_NAME,
        formatPrimary = ConstantFormat.VIDEO_4320P60F_HDR_FORMAT_PRIMARY,
        formatSecondary = ConstantFormat.VIDEO_4320P60F_HDR_FORMAT_SECONDARY,
        formatTertiary = ConstantFormat.VIDEO_4320P60F_HDR_FORMAT_TERTIARY
    )
}