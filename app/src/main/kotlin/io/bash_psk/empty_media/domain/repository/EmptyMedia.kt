package io.bash_psk.empty_media.domain.repository

import io.bash_psk.empty_media.domain.media.MediaData
import io.bash_psk.empty_media.domain.media.PlaylistMediaData
import kotlinx.coroutines.flow.Flow

interface EmptyMedia {

    suspend fun getMediaData(searchUrl: String) : Flow<MediaData>

    suspend fun getPlaylistMediaData(searchUrl: String) : Flow<PlaylistMediaData>
}