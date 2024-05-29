package io.bash_psk.storage.storage

import android.annotation.SuppressLint
import android.content.Context
import android.os.Environment
import android.os.StatFs
import android.os.storage.StorageManager
import android.os.storage.StorageVolume
import android.text.TextUtils
import io.bash_psk.storage.resource.ConstantString
import java.io.File

object StorageIO {

    private val EXTERNAL_STORAGE = System.getenv("EXTERNAL_STORAGE")

    private val SECONDARY_STORAGES = System.getenv("SECONDARY_STORAGE")

    private val EMULATED_STORAGE_TARGET = System.getenv("EMULATED_STORAGE_TARGET")

    @SuppressLint("SdCardPath")
    @Suppress("SpellCheckingInspection")
    private val physicalPaths = arrayOf(
        "/storage/sdcard0",
        "/storage/sdcard1",
        "/storage/extsdcard",
        "/storage/sdcard0/external_sdcard",
        "/mnt/extsdcard",
        "/mnt/sdcard/external_sd",
        "/mnt/sdcard/ext_sd",
        "/mnt/external_sd",
        "/mnt/media_rw/sdcard1",
        "/removable/microsd",
        "/mnt/emmc",
        "/storage/external_SD",
        "/storage/ext_sd",
        "/storage/removable/sdcard1",
        "/data/sdext",
        "/data/sdext2",
        "/data/sdext3",
        "/data/sdext4",
        "/sdcard1",
        "/sdcard2",
        "/storage/microsd"
    )

    fun getStorageVolumes(
        context: Context
    ) : List<VolumeData> {

        val volumeDataList = mutableListOf<VolumeData>()
        val storageManager = context.getSystemService(Context.STORAGE_SERVICE) as StorageManager
        val storagePaths = getStorageDirectories(context = context)

        storagePaths.forEach { path: String ->

            storageManager.getStorageVolume(File(path))?.let { storageVolume: StorageVolume ->

                val newVolumeData = VolumeData(
                    name = when (storageVolume.isPrimary) {

                        true -> {

                            ConstantString.PRIMARY_STORAGE
                        }

                        false -> {

                            ConstantString.REMOVABLE_STORAGE
                        }
                    },
                    path = path,
                    isPrimary = storageVolume.isPrimary,
                    isRemovable = storageVolume.isRemovable,
                    totalSize = getTotalMemory(path = path),
                    availableSize = getFreeMemory(path = path),
                    usedSize = getUsedMemory(path = path)
                )

                volumeDataList.add(element = newVolumeData)
            }
        }

        return volumeDataList.toList()
    }

    fun getDirectoryData(
        path: String
    ) : List<DirectoryData> {
        
        val directoryDataList = mutableListOf<DirectoryData>()
        val destinationFile = File(path)
        val directoryFileList = destinationFile.listFiles()?.toList() ?: emptyList()

        directoryFileList.forEach { directoryFile: File ->
            
            val newDirectoryData = DirectoryData(
                name = directoryFile.name,
                path = directoryFile.path,
                parent = directoryFile.parent ?: ConstantString.NONE,
                isFolder = directoryFile.isDirectory
            )

            directoryDataList.add(element = newDirectoryData)
        }
        
        return directoryDataList.toList()
    }

    private fun getStorageDirectories(
        context: Context
    ) : Array<String> {

        val availableDirectoriesSet = hashSetOf<String>()

        when {

            !TextUtils.isEmpty(EMULATED_STORAGE_TARGET) -> {

                availableDirectoriesSet.add(
                    element = getEmulatedStorageTarget()
                )
            }

            else -> {

                availableDirectoriesSet.addAll(
                    elements = getExternalStorage(context = context)
                )
            }
        }

        availableDirectoriesSet.addAll(
            elements = getAllSecondaryStorages().toList()
        )

        return availableDirectoriesSet.toTypedArray()
    }

    private fun getExternalStorage(
        context: Context
    ) : Set<String> {

        val availableDirectoriesSet = hashSetOf<String>()
        val files = getExternalFilesDirs(context = context)

        files.forEach { file: File ->

            val contextAbsolutePath = file.absolutePath

            val rootPath = contextAbsolutePath.substring(
                startIndex = 0,
                endIndex = contextAbsolutePath.indexOf(string = "Android/data")
            )

            availableDirectoriesSet.add(element = rootPath)
        }

        return availableDirectoriesSet
    }

    private fun getEmulatedStorageTarget() : String {

        var rawStorageId = ""

        val path = Environment.getExternalStorageDirectory().absolutePath
        val folders = path.split(File.separator)
        val lastSegment = folders.lastOrNull()

        when {

            !TextUtils.isEmpty(lastSegment) && TextUtils.isDigitsOnly(lastSegment) -> {

                when {

                    lastSegment != null -> {

                        rawStorageId = lastSegment
                    }
                }
            }
        }

        return when {

            TextUtils.isEmpty(rawStorageId) -> {

                EMULATED_STORAGE_TARGET
            }

            else -> {

                "$EMULATED_STORAGE_TARGET/$rawStorageId"
            }
        }
    }

    private fun getAllSecondaryStorages(): Array<String> {

        return when {

            !TextUtils.isEmpty(SECONDARY_STORAGES) -> {

                SECONDARY_STORAGES?.split(File.pathSeparator)?.toTypedArray() ?: emptyArray()
            }

            else -> {

                emptyArray()
            }
        }
    }

    private fun getAvailablePhysicalPaths(): List<String> {

        val availablePhysicalPaths = arrayListOf<String>()

        physicalPaths.forEach { physicalPath: String ->

            val physicalFile = File(physicalPath)

            when {

                physicalFile.exists() -> {

                    availablePhysicalPaths.add(element = physicalPath)
                }
            }
        }

        return availablePhysicalPaths
    }

    private fun getExternalFilesDirs(
        context: Context
    ) : Array<File> {

        return context.getExternalFilesDirs(null)
    }

    private fun getTotalMemory(path: String) : Long {

        val statFs = StatFs(path)

        return statFs.blockCountLong * statFs.blockSizeLong
    }

    private fun getFreeMemory(path: String) : Long {

        val statFs = StatFs(path)

        return statFs.availableBlocksLong * statFs.blockSizeLong
    }

    private fun getUsedMemory(path: String) : Long {

        val totalMemory = getTotalMemory(path = path)
        val freeMemory = getFreeMemory(path = path)

        return totalMemory - freeMemory
    }
}