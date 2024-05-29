package io.bash_psk.empty_media.domain.repository

import androidx.work.WorkInfo
import androidx.work.WorkManager
import io.bash_psk.empty_media.domain.database.MediaEntity
import kotlinx.coroutines.flow.Flow

interface EmptyWorker {

    val workManager: WorkManager

    suspend fun downloadFiles(mediaList: List<MediaEntity>) : Flow<WorkInfo>

    suspend fun runCommand(mediaEntity: MediaEntity) : Flow<WorkInfo>

    suspend fun updateLibrary() : Flow<WorkInfo>
}