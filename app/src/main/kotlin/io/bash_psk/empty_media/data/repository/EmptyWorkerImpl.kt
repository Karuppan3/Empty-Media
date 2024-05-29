package io.bash_psk.empty_media.data.repository

import android.app.Application
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.workDataOf
import io.bash_psk.empty_media.data.worker.CommandWorker
import io.bash_psk.empty_media.data.worker.UpdateWorker
import io.bash_psk.empty_media.domain.database.MediaEntity
import io.bash_psk.empty_media.domain.repository.EmptyWorker
import io.bash_psk.empty_media.domain.resource.ConstantWorker
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

class EmptyWorkerImpl(
    private val application: Application
) : EmptyWorker {

    override val workManager: WorkManager
        get() = WorkManager.getInstance(application)

    override suspend fun downloadFiles(
        mediaList: List<MediaEntity>
    ) : Flow<WorkInfo> {

        return flow {

            mediaList.forEach { mediaEntity: MediaEntity ->

                val commandInputData = workDataOf(
                    ConstantWorker.MediaKey.MEDIA_ID to mediaEntity.mediaId,
                    ConstantWorker.MediaKey.TITLE to mediaEntity.title,
                    ConstantWorker.MediaKey.COMMAND to mediaEntity.command,
                    ConstantWorker.MediaKey.PATH to mediaEntity.path,
                    ConstantWorker.MediaKey.PLATFORM to mediaEntity.platform,
                    ConstantWorker.MediaKey.STATUS to mediaEntity.status,
                    ConstantWorker.MediaKey.SIZE to mediaEntity.size,
                    ConstantWorker.MediaKey.PROGRESS to mediaEntity.progress,
                    ConstantWorker.MediaKey.TIME_STAMP to mediaEntity.timeStamp
                )

                val commandWorker = OneTimeWorkRequestBuilder<CommandWorker>()
                    .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
                    .setInputData(inputData = commandInputData)
                    .build()

                workManager.enqueueUniqueWork(
                    ConstantWorker.ID.COMMAND_WORKER,
                    ExistingWorkPolicy.APPEND,
                    commandWorker
                )
            }
        }
    }

    override suspend fun runCommand(mediaEntity: MediaEntity) : Flow<WorkInfo> {

        return flow {

            val commandInputData = workDataOf(
                ConstantWorker.MediaKey.MEDIA_ID to mediaEntity.mediaId,
                ConstantWorker.MediaKey.TITLE to mediaEntity.title,
                ConstantWorker.MediaKey.COMMAND to mediaEntity.command,
                ConstantWorker.MediaKey.PATH to mediaEntity.path,
                ConstantWorker.MediaKey.PLATFORM to mediaEntity.platform,
                ConstantWorker.MediaKey.STATUS to mediaEntity.status,
                ConstantWorker.MediaKey.SIZE to mediaEntity.size,
                ConstantWorker.MediaKey.PROGRESS to mediaEntity.progress,
                ConstantWorker.MediaKey.TIME_STAMP to mediaEntity.timeStamp
            )

            val commandWorker = OneTimeWorkRequestBuilder<CommandWorker>()
                .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
                .setInputData(inputData = commandInputData)
                .build()

            workManager.enqueueUniqueWork(
                ConstantWorker.ID.COMMAND_WORKER,
                ExistingWorkPolicy.APPEND,
                commandWorker
            )

            val workInfoFlow = workManager.getWorkInfoByIdFlow(commandWorker.id)

            emitAll(flow = workInfoFlow)
        }
    }

    override suspend fun updateLibrary() : Flow<WorkInfo> {

        return flow {

            val updateWorker = OneTimeWorkRequestBuilder<UpdateWorker>()
                .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
                .build()

            workManager.enqueueUniqueWork(
                ConstantWorker.ID.UPDATE_WORKER,
                ExistingWorkPolicy.REPLACE,
                updateWorker
            )

            val workInfoFlow = workManager.getWorkInfoByIdFlow(updateWorker.id)

            emitAll(flow = workInfoFlow)
        }
    }
}