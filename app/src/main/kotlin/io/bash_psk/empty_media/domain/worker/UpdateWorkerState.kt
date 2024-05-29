package io.bash_psk.empty_media.domain.worker

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.work.WorkInfo
import io.bash_psk.empty_media.domain.resource.ConstantString
import java.util.UUID

@Stable
@Immutable
data class UpdateWorkerState(
    val uuid: UUID = UUID.randomUUID(),
    val version: String = ConstantString.EMPTY,
    val message: String = ConstantString.EMPTY,
    val state: WorkInfo.State = WorkInfo.State.ENQUEUED
)