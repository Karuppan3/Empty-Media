package io.bash_psk.empty_media.domain.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import io.bash_psk.empty_media.domain.resource.ConstantString
import java.util.UUID

@Entity
data class MediaEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val mediaId: String = UUID.randomUUID().toString(),
    val title: String = ConstantString.EMPTY,
    val url: String = ConstantString.EMPTY,
    val command: String = ConstantString.EMPTY,
    val path: String = ConstantString.EMPTY,
    val platform: String = ConstantString.EMPTY,
    val status: String = DownloadStatus.ENQUEUED.status,
    val size: Long = 0L,
    val progress: Int = 0,
    val timeStamp: Long = 0L
)