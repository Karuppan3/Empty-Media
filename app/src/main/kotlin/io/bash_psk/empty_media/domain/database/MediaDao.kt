package io.bash_psk.empty_media.domain.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface MediaDao {

    @Query("SELECT * FROM MediaEntity ORDER BY timeStamp ASC")
    fun getAllMediaEntityList() : Flow<List<MediaEntity>>

    @Query("SELECT * FROM MediaEntity WHERE status = :status ORDER BY timeStamp ASC")
    fun getDownloadMediaEntityList(status: String) : Flow<List<MediaEntity>>

    @Query("SELECT * FROM MediaEntity WHERE mediaId = :mediaId")
    fun getMediaEntityByMediaId(mediaId: String) : Flow<MediaEntity?>

    @Upsert
    suspend fun upsertMediaEntity(mediaEntity: MediaEntity)

    @Delete
    suspend fun deleteMediaEntity(mediaEntity: MediaEntity)
}