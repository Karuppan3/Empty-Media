package io.bash_psk.empty_media.domain.repository

import io.bash_psk.storage.storage.DirectoryData
import io.bash_psk.storage.storage.VolumeData
import kotlinx.coroutines.flow.Flow

interface EmptyStorage {

    suspend fun getStorageVolumes() : Flow<List<VolumeData>>

    suspend fun getDirectoryList(
        path: String
    ) : Flow<List<DirectoryData>>

    suspend fun makeDirectory(path: String, newDirectory: String)
}