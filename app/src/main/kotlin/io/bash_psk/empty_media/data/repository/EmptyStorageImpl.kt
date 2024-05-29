package io.bash_psk.empty_media.data.repository

import android.annotation.SuppressLint
import android.app.Application
import io.bash_psk.empty_media.domain.repository.EmptyStorage
import io.bash_psk.storage.storage.DirectoryData
import io.bash_psk.storage.storage.StorageIO
import io.bash_psk.storage.storage.VolumeData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File

@SuppressLint("Range")
class EmptyStorageImpl(
    private val application: Application
) : EmptyStorage {

    override suspend fun getStorageVolumes() : Flow<List<VolumeData>> {

        return flow {

            val newStorageVolumes = StorageIO.getStorageVolumes(context = application)

            emit(value = newStorageVolumes)
        }
    }

    override suspend fun getDirectoryList(
        path: String
    ) : Flow<List<DirectoryData>> {

        return flow {

            val newDirectoryList = StorageIO.getDirectoryData(path = path)

            emit(value = newDirectoryList)
        }
    }

    override suspend fun makeDirectory(path: String, newDirectory: String) {

        val directoryPathFile = File(path, newDirectory)

        directoryPathFile.mkdirs()
    }
}