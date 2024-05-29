package io.bash_psk.empty_media.presentation.activities.main

import android.app.Activity
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.WorkInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import io.bash_psk.empty_media.domain.database.DownloadStatus
import io.bash_psk.empty_media.domain.database.MediaEntity
import io.bash_psk.empty_media.domain.dialog.DownloadConfirmationState
import io.bash_psk.empty_media.domain.dialog.FormatSelectionState
import io.bash_psk.empty_media.domain.events.MainUIEvent
import io.bash_psk.empty_media.domain.media.MediaData
import io.bash_psk.empty_media.domain.media.PlaylistMediaData
import io.bash_psk.empty_media.domain.repository.EmptyDatabase
import io.bash_psk.empty_media.domain.repository.EmptyDatastore
import io.bash_psk.empty_media.domain.repository.EmptyMedia
import io.bash_psk.empty_media.domain.repository.EmptyNotification
import io.bash_psk.empty_media.domain.repository.EmptyPermission
import io.bash_psk.empty_media.domain.repository.EmptyStorage
import io.bash_psk.empty_media.domain.repository.EmptyWorker
import io.bash_psk.empty_media.domain.resource.ConstantString
import io.bash_psk.empty_media.domain.resource.ConstantWorker
import io.bash_psk.empty_media.domain.state.MainUIState
import io.bash_psk.empty_media.domain.worker.CommandWorkerState
import io.bash_psk.empty_media.domain.worker.UpdateWorkerState
import io.bash_psk.permission.permission.PermissionState
import io.bash_psk.permission.permission.Permissions
import io.bash_psk.utils.toast.SetToast
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.File
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@HiltViewModel
class MainViewModel @Inject constructor(
    private val emptyPermission: EmptyPermission,
    private val emptyStorage: EmptyStorage,
    private val emptyDatabase: EmptyDatabase,
    private val emptyDatastore: EmptyDatastore,
    private val emptyNotification: EmptyNotification,
    private val emptyMedia: EmptyMedia,
    private val emptyWorker: EmptyWorker
) : ViewModel() {

    private val _mainUIState = MutableStateFlow(
        value = MainUIState()
    )

    private val _lifecycle = MutableStateFlow(
        value = Lifecycle.Event.ON_CREATE
    )

    private val _downloadConfirmationState = MutableStateFlow(
        value = DownloadConfirmationState()
    )

    private val _formatSelectionState = MutableStateFlow(
        value = FormatSelectionState()
    )

    private val _downloadWorkerState = MutableStateFlow(
        value = CommandWorkerState()
    )

    private val _commandWorkerState = MutableStateFlow(
        value = CommandWorkerState()
    )

    private val _updateWorkerState = MutableStateFlow(
        value = UpdateWorkerState()
    )

    val mainUIState = _mainUIState.asStateFlow()
    val lifecycle = _lifecycle.asStateFlow()
    val downloadConfirmationState = _downloadConfirmationState.asStateFlow()
    val formatSelectionState = _formatSelectionState.asStateFlow()
    val downloadWorkerState = _downloadWorkerState.asStateFlow()
    val commandWorkerState = _commandWorkerState.asStateFlow()
    val updateWorkerState = _updateWorkerState.asStateFlow()

    val allMediaEntityList = emptyDatabase.getAllMediaEntityList().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 3000L),
        initialValue = emptyList()
    )

    val completedMediaEntityList = emptyDatabase.getDownloadMediaEntityList(
        downloadStatus = DownloadStatus.COMPLETED.status
    ).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 3000L),
        initialValue = emptyList()
    )

    val failedMediaEntityList = emptyDatabase.getDownloadMediaEntityList(
        downloadStatus = DownloadStatus.FAILED.status
    ).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 3000L),
        initialValue = emptyList()
    )

    val runningMediaEntityList = emptyDatabase.getDownloadMediaEntityList(
        downloadStatus = DownloadStatus.RUNNING.status
    ).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 3000L),
        initialValue = emptyList()
    )

    init {

        onSplashScreen()
    }

    private fun onSplashScreen() {

        viewModelScope.launch {

            val setSplashScreenMainUIState = mainUIState.value.copy(isSplashScreen = true)

            delay(duration = 100.milliseconds)

            _mainUIState.emit(value = setSplashScreenMainUIState)
        }
    }

    fun onCheckPermission(
        activity: Activity,
        permissionList: List<Permissions>
    ) : StateFlow<List<PermissionState>> {

        val permissionResult = MutableStateFlow(
            value = emptyList<PermissionState>()
        )

        viewModelScope.launch {

            emptyPermission.allPermissions(
                activity = activity,
                permissions = permissionList
            ).collectLatest { permissionResultLatest: List<PermissionState> ->

                permissionResult.emit(value = permissionResultLatest)
            }
        }

        return permissionResult.asStateFlow()
    }

    fun onMainUIEvent(mainUIEvent: MainUIEvent) {

        viewModelScope.launch {

            when (mainUIEvent) {

                is MainUIEvent.DeleteMediaEntities -> {

                    mainUIEvent.mediaEntities.forEach { mediaEntity: MediaEntity ->

                        emptyDatabase.deleteMediaEntity(mediaEntity = mediaEntity)
                    }
                }

                is MainUIEvent.DoNoting -> {

                }

                is MainUIEvent.DownloadConfirmation -> {

                    _downloadConfirmationState.emit(value = mainUIEvent.confirmation)
                }

                is MainUIEvent.DownloadMediaList -> {

                    emptyWorker.downloadFiles(
                        mediaList = mainUIEvent.downloadMediaList
                    ).collectLatest { workInfoLatest: WorkInfo ->
                    }
                }

                is MainUIEvent.FormatSelection -> {

                    _formatSelectionState.emit(value = mainUIEvent.formatSelection)
                }

                is MainUIEvent.RunCommand -> {

                    emptyWorker.runCommand(
                        mediaEntity = mainUIEvent.mediaEntity
                    ).collectLatest { workInfoLatest: WorkInfo ->

                        val title = workInfoLatest.progress.getString(
                            ConstantWorker.Key.TITLE
                        ) ?: ConstantString.EMPTY

                        val progress = workInfoLatest.progress.getInt(
                            ConstantWorker.Key.COMMAND_PROGRESS,
                            0
                        )

                        val elapsedTime = workInfoLatest.progress.getLong(
                            ConstantWorker.Key.COMMAND_ELAPSED_TIME,
                            0L
                        )

                        val line = workInfoLatest.progress.getString(
                            ConstantWorker.Key.COMMAND_LINE
                        ) ?: ConstantString.EMPTY

                        val outputFile = workInfoLatest.outputData.getString(
                            ConstantWorker.Key.COMMAND_OUTPUT_FILE
                        )

                        val output = when (outputFile != null) {

                            true -> {

                                File(
                                    outputFile
                                ).bufferedReader().use { bufferedReader: BufferedReader ->

                                    bufferedReader.readText()
                                }
                            }

                            false -> {

                                ConstantString.EMPTY
                            }
                        }

                        val message = when (workInfoLatest.state) {

                            WorkInfo.State.ENQUEUED -> {

                                ConstantWorker.Message.ENQUEUED
                            }

                            WorkInfo.State.RUNNING -> {

                                ConstantWorker.Message.RUNNING
                            }

                            WorkInfo.State.SUCCEEDED -> {

                                ConstantWorker.Message.COMPLETED
                            }

                            WorkInfo.State.FAILED -> {

                                ConstantWorker.Message.FAILED
                            }

                            WorkInfo.State.BLOCKED -> {

                                ConstantWorker.Message.FAILED
                            }

                            WorkInfo.State.CANCELLED -> {

                                ConstantWorker.Message.CANCELLED
                            }
                        }

                        val newCommandWorkerState = CommandWorkerState(
                            title = title,
                            message = message,
                            state = workInfoLatest.state,
                            progress = progress,
                            elapsedTime = elapsedTime,
                            line = line,
                            output = output
                        )

                        _commandWorkerState.emit(value = newCommandWorkerState)
                    }
                }

                is MainUIEvent.SearchMediaData -> {

                    when (mainUIEvent.url.startsWith(ConstantString.YOUTUBE_PLAYLIST_TEMPLATE)) {

                        true -> {

                            emptyMedia.getPlaylistMediaData(
                                searchUrl = mainUIEvent.url
                            ).collectLatest { mediaLatest: PlaylistMediaData ->

                                val completeSearchMediaState = mainUIState.value.copy(
                                    isEmptyMedia = mediaLatest.isEmpty,
                                    isLoadingMedia = mediaLatest.isSearch,
                                    mediaData = MediaData(),
                                    playlistMediaData = mediaLatest
                                )

                                _mainUIState.emit(value = completeSearchMediaState)
                            }
                        }

                        false -> {

                            emptyMedia.getMediaData(
                                searchUrl = mainUIEvent.url
                            ).collectLatest { mediaLatest: MediaData ->

                                val completeSearchMediaState = mainUIState.value.copy(
                                    isEmptyMedia = mediaLatest.isEmpty,
                                    isLoadingMedia = mediaLatest.isSearch,
                                    mediaData = mediaLatest
                                )

                                _mainUIState.emit(value = completeSearchMediaState)
                            }
                        }
                    }
                }

                is MainUIEvent.SetMainUIState -> {

                    _mainUIState.emit(value = mainUIEvent.mainUIState)
                }

                is MainUIEvent.SetToast -> {

                    SetToast.setToastShort(
                        context = mainUIEvent.context,
                        message = mainUIEvent.message
                    )
                }

                is MainUIEvent.UpdateLibrary -> {

                    emptyWorker.updateLibrary().collectLatest { workInfoLatest: WorkInfo ->

                        val version = workInfoLatest.outputData.getString(
                            ConstantWorker.Key.UPDATE_OUTPUT_FILE
                        ) ?: ConstantString.EMPTY

                        val message = when (workInfoLatest.state) {

                            WorkInfo.State.ENQUEUED -> {

                                ConstantWorker.Message.ENQUEUED
                            }

                            WorkInfo.State.RUNNING -> {

                                ConstantWorker.Message.RUNNING
                            }

                            WorkInfo.State.SUCCEEDED -> {

                                ConstantWorker.Message.COMPLETED
                            }

                            WorkInfo.State.FAILED -> {

                                ConstantWorker.Message.FAILED
                            }

                            WorkInfo.State.BLOCKED -> {

                                ConstantWorker.Message.FAILED
                            }

                            WorkInfo.State.CANCELLED -> {

                                ConstantWorker.Message.CANCELLED
                            }
                        }

                        val newUpdateWorkerState = UpdateWorkerState(
                            version = version,
                            message = message,
                            state = workInfoLatest.state
                        )

                        _updateWorkerState.emit(value = newUpdateWorkerState)
                    }
                }

                is MainUIEvent.UpsertMediaEntities -> {

                    mainUIEvent.mediaEntities.forEach { mediaEntity: MediaEntity ->

                        emptyDatabase.upsertMediaEntity(mediaEntity = mediaEntity)
                    }
                }
            }
        }
    }

    private fun <T> getPreference(
        key: Preferences.Key<T>,
        initial: T
    ) : StateFlow<T> {

        val retrievedData = MutableStateFlow(
            value = initial
        )

        viewModelScope.launch {

            emptyDatastore.getPreference(
                key = key,
                initial = initial
            ).collectLatest { valueLatest: T ->

                retrievedData.emit(value = valueLatest)
            }
        }

        return retrievedData.asStateFlow()
    }

    private fun <T> setPreference(
        key: Preferences.Key<T>,
        value: T
    ) {

        viewModelScope.launch {

            emptyDatastore.setPreference(
                key = key,
                value = value
            )
        }
    }
}