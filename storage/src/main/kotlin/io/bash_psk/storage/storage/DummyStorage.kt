package io.bash_psk.storage.storage

import io.bash_psk.storage.resource.ConstantString

object DummyStorage {

    val DirectoryData = DirectoryData(
        name = ConstantString.NONE,
        path = ConstantString.NONE,
        parent = ConstantString.NONE,
        isFolder = false
    )

    val VolumeData = VolumeData(
        name = ConstantString.NONE,
        path = ConstantString.NONE,
        isPrimary = false,
        isRemovable = true,
        totalSize = 0L,
        availableSize = 0L,
        usedSize = 0L
    )
}