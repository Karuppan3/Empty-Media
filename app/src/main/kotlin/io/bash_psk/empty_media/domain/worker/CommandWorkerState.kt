package io.bash_psk.empty_media.domain.worker

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.work.WorkInfo
import io.bash_psk.empty_media.domain.resource.ConstantString
import java.util.UUID

@Stable
@Immutable
data class CommandWorkerState(
    val uuid: UUID = UUID.randomUUID(),
    val title: String = ConstantString.EMPTY,
    val message: String = ConstantString.EMPTY,
    val state: WorkInfo.State = WorkInfo.State.ENQUEUED,
    val progress: Int = 0,
    val elapsedTime: Long = 0L,
    val line: String = ConstantString.EMPTY,
    val output: String = ConstantString.EMPTY
)