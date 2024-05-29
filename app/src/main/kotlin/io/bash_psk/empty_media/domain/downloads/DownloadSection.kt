package io.bash_psk.empty_media.domain.downloads

import io.bash_psk.empty_media.domain.resource.ConstantDownload

sealed class DownloadSection(
    val id: Int,
    val label: String
) {
    
    data object ALL : DownloadSection(
        id = ConstantDownload.ALL_SECTION_ID,
        label = ConstantDownload.ALL_SECTION_LABEL
    )

    data object COMPLETED : DownloadSection(
        id = ConstantDownload.COMPLETED_SECTION_ID,
        label = ConstantDownload.COMPLETED_SECTION_LABEL
    )

    data object FAILED : DownloadSection(
        id = ConstantDownload.FAILED_SECTION_ID,
        label = ConstantDownload.FAILED_SECTION_LABEL
    )

    data object RUNNING : DownloadSection(
        id = ConstantDownload.RUNNING_SECTION_ID,
        label = ConstantDownload.RUNNING_SECTION_LABEL
    )
}