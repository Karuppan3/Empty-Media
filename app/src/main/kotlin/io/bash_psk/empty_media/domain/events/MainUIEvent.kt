package io.bash_psk.empty_media.domain.events

import android.content.Context
import io.bash_psk.empty_media.domain.database.MediaEntity
import io.bash_psk.empty_media.domain.dialog.DownloadConfirmationState
import io.bash_psk.empty_media.domain.dialog.FormatSelectionState
import io.bash_psk.empty_media.domain.state.MainUIState

sealed class MainUIEvent {

    data class DeleteMediaEntities(
        val mediaEntities: List<MediaEntity>
    ) : MainUIEvent()

    data object DoNoting : MainUIEvent()

    data class DownloadConfirmation(
        val confirmation: DownloadConfirmationState
    ) : MainUIEvent()

    data class DownloadMediaList(
        val downloadMediaList: List<MediaEntity>
    ) : MainUIEvent()

    data class FormatSelection(
        val formatSelection: FormatSelectionState
    ) : MainUIEvent()

    data class RunCommand(
        val mediaEntity: MediaEntity
    ) : MainUIEvent()

    data class SearchMediaData(
        val url: String
    ) : MainUIEvent()

    data class SetMainUIState(
        val mainUIState: MainUIState
    ) : MainUIEvent()

    data class SetToast(
        val context: Context,
        val message: String
    ) : MainUIEvent()

    data object UpdateLibrary : MainUIEvent()

    data class UpsertMediaEntities(
        val mediaEntities: List<MediaEntity>
    ) : MainUIEvent()
}