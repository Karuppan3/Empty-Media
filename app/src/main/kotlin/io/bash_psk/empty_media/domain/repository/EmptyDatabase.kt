package io.bash_psk.empty_media.domain.repository

import io.bash_psk.empty_media.domain.database.MediaEntity
import kotlinx.coroutines.flow.Flow

interface EmptyDatabase {

    fun getAllMediaEntityList() : Flow<List<MediaEntity>>

    fun getDownloadMediaEntityList(downloadStatus: String) : Flow<List<MediaEntity>>

    fun getMediaEntityByMediaId(mediaId: String) : Flow<MediaEntity?>

    suspend fun upsertMediaEntity(mediaEntity: MediaEntity)

    suspend fun deleteMediaEntity(mediaEntity: MediaEntity)
}