package io.bash_psk.empty_media.data.repository

import io.bash_psk.empty_media.domain.database.MediaDao
import io.bash_psk.empty_media.domain.database.MediaEntity
import io.bash_psk.empty_media.domain.repository.EmptyDatabase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EmptyDatabaseImpl @Inject constructor(
    private val mediaDao: MediaDao
) : EmptyDatabase {

    override fun getAllMediaEntityList(): Flow<List<MediaEntity>> {

        return mediaDao.getAllMediaEntityList()
    }

    override fun getDownloadMediaEntityList(
        downloadStatus: String
    ) : Flow<List<MediaEntity>> {

        return mediaDao.getDownloadMediaEntityList(status = downloadStatus)
    }

    override fun getMediaEntityByMediaId(mediaId: String): Flow<MediaEntity?> {

        return mediaDao.getMediaEntityByMediaId(mediaId = mediaId)
    }

    override suspend fun upsertMediaEntity(mediaEntity: MediaEntity) {

        mediaDao.upsertMediaEntity(mediaEntity = mediaEntity)
    }

    override suspend fun deleteMediaEntity(mediaEntity: MediaEntity) {

        mediaDao.deleteMediaEntity(mediaEntity = mediaEntity)
    }
}