package io.bash_psk.empty_media.domain.state

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.lifecycle.Lifecycle
import io.bash_psk.empty_media.domain.downloads.DownloadSection
import io.bash_psk.empty_media.domain.map.ListData
import io.bash_psk.empty_media.domain.media.MediaData
import io.bash_psk.empty_media.domain.media.MediaFormatData
import io.bash_psk.empty_media.domain.media.PlaylistMediaData
import io.bash_psk.empty_media.domain.navigation.NavRoute
import io.bash_psk.empty_media.domain.resource.ConstantTest
import io.bash_psk.permission.permission.Permissions
import io.bash_psk.permission.permission.REQUIRED_PERMISSIONS
import java.util.UUID

@Stable
@Immutable
data class MainUIState(
    val uuid: UUID = UUID.randomUUID(),
    val isSplashScreen: Boolean = true,
    val lifecycle: Lifecycle.Event = Lifecycle.Event.ON_CREATE,
    val isDrawerOpenable: Boolean = false,
    val permissionList: List<Permissions> = REQUIRED_PERMISSIONS,
    val isPermissionDialog: Boolean = false,
    val screenList: List<NavRoute> = ListData.SCREEN_LIST,
    val downloadSectionList: List<DownloadSection> = ListData.DOWNLOAD_SECTION_LIST,
    val enteredUrl: String = ConstantTest.EMPTY,
    val enteredCommand: String = ConstantTest.TEST_COMMAND,
    val isEmptyMedia: Boolean = false,
    val isLoadingMedia: Boolean = false,
    val mediaData: MediaData = MediaData(),
    val playlistMediaData: PlaylistMediaData = PlaylistMediaData(),
    val isMediaSelect: Boolean = false,
    val isMediaFormatSelect: Boolean = false,
    val selectedMediaFormatList: List<MediaFormatData> = emptyList(),
    val selectedMediaList: List<MediaData> = emptyList(),
)