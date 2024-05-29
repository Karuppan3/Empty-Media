package io.bash_psk.empty_media.presentation.screen

import android.os.Environment
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SelectAll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.bash_psk.empty_media.domain.database.DownloadStatus
import io.bash_psk.empty_media.domain.database.MediaEntity
import io.bash_psk.empty_media.domain.dialog.DownloadConfirmationState
import io.bash_psk.empty_media.domain.dialog.FormatSelectionState
import io.bash_psk.empty_media.domain.events.MainUIEvent
import io.bash_psk.empty_media.domain.media.MediaData
import io.bash_psk.empty_media.domain.media.MediaFormat
import io.bash_psk.empty_media.domain.media.MediaFormatData
import io.bash_psk.empty_media.domain.navigation.NavRoute
import io.bash_psk.empty_media.domain.resource.ConstantCommand
import io.bash_psk.empty_media.domain.resource.ConstantDesc
import io.bash_psk.empty_media.domain.resource.ConstantString
import io.bash_psk.empty_media.domain.resource.ConstantTitle
import io.bash_psk.empty_media.domain.resource.ConstantToast
import io.bash_psk.empty_media.presentation.activities.main.MainViewModel
import io.bash_psk.empty_media.presentation.dialog.DownloadConfirmationDialog
import io.bash_psk.empty_media.presentation.dialog.FormatSelectionDialog
import io.bash_psk.empty_media.presentation.media.EmptyMediaView
import io.bash_psk.empty_media.presentation.media.LoadingMediaView
import io.bash_psk.empty_media.presentation.media.MediaFormatView
import io.bash_psk.empty_media.presentation.media.MediaView
import io.bash_psk.empty_media.presentation.media.MessageMediaView
import java.io.File
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    mainViewModel: MainViewModel,
    isDrawerOpen: Boolean,
    onDrawerMenu: (
        isOpen: Boolean
    ) -> Unit,
    onNavigateScreen: (
        navRoute: NavRoute
    ) -> Unit
) {

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val mainUIState by mainViewModel.mainUIState.collectAsStateWithLifecycle()
    val downloadConfirmationState by mainViewModel.downloadConfirmationState.collectAsStateWithLifecycle()
    val formatSelectionState by mainViewModel.formatSelectionState.collectAsStateWithLifecycle()

    val isAllMediaSelected = remember(
        key1 = mainUIState.playlistMediaData.mediaList,
        key2 = mainUIState.selectedMediaList
    ) {
        mainUIState.playlistMediaData.mediaList.size == mainUIState.selectedMediaList.size
    }

    DownloadConfirmationDialog(
        downloadConfirmationState = downloadConfirmationState,
        onDownloadConfirmationState = { confirmationState: DownloadConfirmationState ->

            val downloadConfirmationMainUIEvent = MainUIEvent.DownloadConfirmation(
                confirmation = confirmationState
            )

            mainViewModel.onMainUIEvent(mainUIEvent = downloadConfirmationMainUIEvent)
        },
        onMediaFormatData = { mediaFormat: MediaFormatData ->

            val downloadsDirectory = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS
            )

            val rootDirectory = File(downloadsDirectory, ConstantString.ROOT_PATH)

            val command = when (mediaFormat.isVideoOnly) {

                true -> {

                    "${
                        mediaFormat.actualUrl
                    } -c -f ${
                        mediaFormat.formatId
                    }+${
                        ConstantCommand.BEST_AUDIO
                    } ${
                        ConstantCommand.RESTRICT_FILE_NAME
                    } -o ${
                        rootDirectory.path
                    }/${
                        ConstantCommand.FILE_NAME
                    }"
                }

                false -> {

                    "${
                        mediaFormat.actualUrl
                    } -c -f ${
                        mediaFormat.formatId
                    } ${
                        ConstantCommand.RESTRICT_FILE_NAME
                    } -o ${
                        rootDirectory.path
                    }/${
                        ConstantCommand.FILE_NAME
                    }"
                }
            }

            val newMediaEntity = MediaEntity(
                mediaId = UUID.randomUUID().toString(),
                title = mainUIState.mediaData.title,
                url = mediaFormat.actualUrl,
                command = command,
                path = ConstantString.EMPTY,
                platform = ConstantString.EMPTY,
                status = DownloadStatus.ENQUEUED.status,
                size = 0L,
                progress = 0,
                timeStamp = System.currentTimeMillis()
            )

            val newMediaEntities = listOf(element = newMediaEntity)

            val downloadMediaListMainUIEvent = MainUIEvent.DownloadMediaList(
                downloadMediaList = newMediaEntities
            )

            mainViewModel.onMainUIEvent(mainUIEvent = downloadMediaListMainUIEvent)
        }
    )

    FormatSelectionDialog(
        formatSelectionState = formatSelectionState,
        onFormatSelectionState = { formatState: FormatSelectionState ->

            val formatSelectionMainUIEvent = MainUIEvent.FormatSelection(
                formatSelection = formatState
            )

            mainViewModel.onMainUIEvent(mainUIEvent = formatSelectionMainUIEvent)
        },
        onMediaDownload = { mediaList: List<MediaData>, format: MediaFormat ->

            val downloadsDirectory = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS
            )

            val rootDirectory = File(downloadsDirectory, ConstantString.ROOT_PATH)

            val newMediaEntities = mediaList.map { mediaData: MediaData ->

                val command = "${
                    mediaData.actualUrl
                } -c -f ${
                    format.formatPrimary
                }/${
                    format.formatSecondary
                }/${
                    format.formatTertiary
                }+${
                    ConstantCommand.BEST_AUDIO
                } -o ${
                    rootDirectory.path
                }/%(title)s.%(ext)s --restrict-filenames"

                MediaEntity(
                    mediaId = UUID.randomUUID().toString(),
                    title = mediaData.title,
                    url = mediaData.actualUrl,
                    command = command,
                    path = ConstantString.EMPTY,
                    platform = ConstantString.EMPTY,
                    status = DownloadStatus.ENQUEUED.status,
                    size = 0L,
                    progress = 0,
                    timeStamp = System.currentTimeMillis()
                )
            }

            val downloadMediaListMainUIEvent = MainUIEvent.DownloadMediaList(
                downloadMediaList = newMediaEntities
            )

            mainViewModel.onMainUIEvent(mainUIEvent = downloadMediaListMainUIEvent)
        }
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {

            TopAppBar(
                navigationIcon = {

                    IconButton(
                        onClick = {

                            onDrawerMenu(isDrawerOpen.not())
                        }
                    ) {

                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = ConstantDesc.MENU_BUTTON
                        )
                    }
                },
                title = {

                    Text(text = ConstantTitle.HOME_APP_TITLE)
                },
                actions = {

                    AnimatedVisibility(
                        visible = mainUIState.playlistMediaData.mediaList.isNotEmpty(),
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {

                        IconButton(
                            onClick = {

                                val mediaSelectMainUIState = when (isAllMediaSelected) {

                                    true -> {

                                        mainUIState.copy(
                                            isMediaSelect = false,
                                            selectedMediaList = emptyList()
                                        )
                                    }

                                    false -> {

                                        mainUIState.copy(
                                            isMediaSelect = true,
                                            selectedMediaList = mainUIState.playlistMediaData.mediaList
                                        )
                                    }
                                }

                                val setMainUIStateMainUIEvent = MainUIEvent.SetMainUIState(
                                    mainUIState = mediaSelectMainUIState
                                )

                                mainViewModel.onMainUIEvent(mainUIEvent = setMainUIStateMainUIEvent)
                            }
                        ) {

                            Icon(
                                imageVector = when (isAllMediaSelected) {

                                    true -> {

                                        Icons.Filled.Clear
                                    }

                                    false -> {

                                        Icons.Filled.SelectAll
                                    }
                                },
                                contentDescription = ConstantDesc.SELECTED
                            )
                        }
                    }

                    AnimatedVisibility(
                        visible = mainUIState.isMediaSelect,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {

                        IconButton(
                            enabled = mainUIState.selectedMediaList.isNotEmpty(),
                            onClick = {

                                val mediaSelectMainUIState = mainUIState.copy(isMediaSelect = false)

                                val setMainUIStateMainUIEvent = MainUIEvent.SetMainUIState(
                                    mainUIState = mediaSelectMainUIState
                                )

                                val formatSelectionMainUIEvent = MainUIEvent.FormatSelection(
                                    formatSelection = formatSelectionState.copy(
                                        isFormatSelectionDialog = true,
                                        mediaList = mainUIState.selectedMediaList
                                    )
                                )

                                mainViewModel.onMainUIEvent(mainUIEvent = setMainUIStateMainUIEvent)
                                mainViewModel.onMainUIEvent(mainUIEvent = formatSelectionMainUIEvent)
                            }
                        ) {

                            Icon(
                                imageVector = Icons.Filled.Check,
                                contentDescription = ConstantDesc.SELECTED
                            )
                        }
                    }
                }
            )
        },
        contentWindowInsets = WindowInsets.safeDrawing
    ) { paddingValues: PaddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = paddingValues),
            contentPadding = PaddingValues(
                vertical = 8.dp,
                horizontal = 8.dp
            ),
            verticalArrangement = Arrangement.spacedBy(space = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            item {

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(fraction = 0.95f),
                    value = mainUIState.enteredUrl,
                    onValueChange = { newUrl: String ->

                        val enterUrlSearchMediaState = mainUIState.copy(
                            enteredUrl = newUrl
                        )

                        val setMainUIStateMainUIEvent = MainUIEvent.SetMainUIState(
                            mainUIState = enterUrlSearchMediaState
                        )

                        mainViewModel.onMainUIEvent(mainUIEvent = setMainUIStateMainUIEvent)
                    },
                    label = {

                        Text(text = ConstantString.LINK_LABEL)
                    },
                    placeholder = {

                        Text(text = ConstantString.ENTER_LINK_HERE_PLACE_HOLDER)
                    },
                    trailingIcon = {

                        IconButton(
                            enabled = mainUIState.enteredUrl.isNotEmpty()
                                    && mainUIState.isLoadingMedia.not(),
                            onClick = {

                                val searchMediaMainUIEvent = when (mainUIState.enteredUrl.isNotEmpty()) {

                                    true -> {

                                        MainUIEvent.SearchMediaData(url = mainUIState.enteredUrl)
                                    }

                                    false -> {

                                        MainUIEvent.SetToast(
                                            context = context,
                                            message = ConstantToast.LINK_EMPTY
                                        )
                                    }
                                }

                                mainViewModel.onMainUIEvent(mainUIEvent = searchMediaMainUIEvent)
                            }
                        ) {

                            Icon(
                                imageVector = Icons.Filled.Search,
                                contentDescription = ConstantDesc.SEARCH
                            )
                        }
                    },
                    maxLines = 1,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        autoCorrect = false,
                        keyboardType = KeyboardType.Uri,
                        imeAction = ImeAction.Search
                    ),
                    keyboardActions = KeyboardActions(
                        onSearch = {

                            val searchMediaMainUIEvent = when (mainUIState.enteredUrl.isNotEmpty()) {

                                true -> {

                                    MainUIEvent.SearchMediaData(
                                        url = mainUIState.enteredUrl
                                    )
                                }

                                false -> {

                                    MainUIEvent.SetToast(
                                        context = context,
                                        message = ConstantToast.LINK_EMPTY
                                    )
                                }
                            }

                            mainViewModel.onMainUIEvent(mainUIEvent = searchMediaMainUIEvent)
                        }
                    )
                )
            }

            item {

                AnimatedVisibility(
                    visible = mainUIState.mediaData.title.isNotEmpty(),
                    enter = slideInVertically(),
                    exit = slideOutVertically()
                ) {

                    MediaView(
                        mediaData = mainUIState.mediaData,
                        isMediaSelect = false,
                        selectedMediaList = emptyList(),
                        onMediaData = {}
                    )
                }
            }

            items(
                items = mainUIState.mediaData.mediaFormatList,
                key = { mediaFormatData: MediaFormatData ->

                    mediaFormatData.uuid
                }
            ) { mediaFormatData: MediaFormatData ->

                MediaFormatView(
                    mediaFormatData = mediaFormatData,
                    onMediaFormatData = { mediaFormat: MediaFormatData ->

                        val newDownloadConfigurationState = downloadConfirmationState.copy(
                            isDownloadConfirmationDialog = true,
                            mediaFormatData = mediaFormat
                        )

                        val downloadConfirmationMainUIEvent = MainUIEvent.DownloadConfirmation(
                            confirmation = newDownloadConfigurationState
                        )

                        mainViewModel.onMainUIEvent(mainUIEvent = downloadConfirmationMainUIEvent)
                    }
                )
            }

            item {

                Text(
                    text = mainUIState.playlistMediaData.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }

            items(
                items = mainUIState.playlistMediaData.mediaList,
                key = { media: MediaData ->

                    media.uuid
                }
            ) { mediaData: MediaData ->

                MediaView(
                    mediaData = mediaData,
                    isMediaSelect = mainUIState.isMediaSelect,
                    selectedMediaList = mainUIState.selectedMediaList,
                    onMediaData = { media: MediaData ->

                        val isExist = mainUIState.selectedMediaList.contains(element = media)

                        val newSelectedMedias = when (isExist) {

                            true -> {

                                mainUIState.selectedMediaList.toMutableList() - media
                            }

                            false -> {

                                mainUIState.selectedMediaList.toMutableList() + media
                            }
                        }

                        val mediaSelectMainUIState = mainUIState.copy(
                            isMediaSelect = true,
                            selectedMediaList = newSelectedMedias.toList()
                        )

                        val setMainUIStateMainUIEvent = MainUIEvent.SetMainUIState(
                            mainUIState = mediaSelectMainUIState
                        )

                        mainViewModel.onMainUIEvent(mainUIEvent = setMainUIStateMainUIEvent)
                    }
                )
            }

            item {

                EmptyMediaView(isEmptyMediaView = mainUIState.isEmptyMedia)
            }

            item {

                LoadingMediaView(isLoadingMediaView = mainUIState.isLoadingMedia)
            }

            item {

                MessageMediaView(
                    isMessageMediaView = mainUIState.mediaData.message.isNotEmpty(),
                    message = mainUIState.mediaData.message
                )
            }
        }
    }
}