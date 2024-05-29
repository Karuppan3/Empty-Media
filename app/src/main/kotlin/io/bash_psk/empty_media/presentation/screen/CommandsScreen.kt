package io.bash_psk.empty_media.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Start
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.work.WorkInfo
import io.bash_psk.empty_media.domain.database.DownloadStatus
import io.bash_psk.empty_media.domain.database.MediaEntity
import io.bash_psk.empty_media.domain.events.MainUIEvent
import io.bash_psk.empty_media.domain.navigation.NavRoute
import io.bash_psk.empty_media.domain.resource.ConstantButton
import io.bash_psk.empty_media.domain.resource.ConstantDesc
import io.bash_psk.empty_media.domain.resource.ConstantString
import io.bash_psk.empty_media.domain.resource.ConstantTitle
import io.bash_psk.empty_media.domain.resource.ConstantToast
import io.bash_psk.empty_media.presentation.activities.main.MainViewModel
import io.bash_psk.empty_media.presentation.worker.CommandWorkerView
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommandsScreen(
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
    val commandWorkerState by mainViewModel.commandWorkerState.collectAsStateWithLifecycle()

    val isCommandRunnable = remember(
        key1 = mainUIState.enteredCommand,
        key2 = commandWorkerState.state
    ) {
        mainUIState.enteredCommand.isNotEmpty() && commandWorkerState.state != WorkInfo.State.RUNNING
    }

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

                    Text(text = ConstantTitle.COMMANDS_APP_TITLE)
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
                    value = mainUIState.enteredCommand,
                    onValueChange = { newCommand: String ->

                        val enteredCommandMainUIState = mainUIState.copy(
                            enteredCommand = newCommand
                        )

                        val setMainUIStateMainUIEvent = MainUIEvent.SetMainUIState(
                            mainUIState = enteredCommandMainUIState
                        )

                        mainViewModel.onMainUIEvent(mainUIEvent = setMainUIStateMainUIEvent)
                    },
                    label = {

                        Text(text = ConstantString.COMMAND_LABEL)
                    },
                    placeholder = {

                        Text(text = ConstantString.ENTER_COMMAND_HERE_PLACE_HOLDER)
                    },
                    maxLines = 4,
                    keyboardOptions = KeyboardOptions(
                        autoCorrect = false,
                        keyboardType = KeyboardType.Uri,
                        imeAction = ImeAction.Go
                    ),
                    keyboardActions = KeyboardActions(
                        onGo = {

                        }
                    )
                )
            }

            item {

                TextButton(
                    enabled = isCommandRunnable,
                    onClick = {

                        val newMediaEntity = MediaEntity(
                            mediaId = UUID.randomUUID().toString(),
                            title = ConstantString.CUSTOM_COMMAND,
                            command = mainUIState.enteredCommand,
                            path = ConstantString.EMPTY,
                            platform = ConstantString.EMPTY,
                            status = DownloadStatus.ENQUEUED.status,
                            size = 0L,
                            progress = 0,
                            timeStamp = System.currentTimeMillis()
                        )

                        val runCommandMainUIEvent = when (mainUIState.enteredCommand.isNotEmpty()) {

                            true -> {

                                MainUIEvent.RunCommand(mediaEntity = newMediaEntity)
                            }

                            false -> {

                                MainUIEvent.SetToast(
                                    context = context,
                                    message = ConstantToast.COMMAND_EMPTY
                                )
                            }
                        }

                        mainViewModel.onMainUIEvent(mainUIEvent = runCommandMainUIEvent)
                    }
                ) {

                    Icon(
                        imageVector = Icons.Filled.Start,
                        contentDescription = ConstantDesc.START
                    )

                    Text(text = ConstantButton.RUN_COMMAND)
                }
            }

            item {

                CommandWorkerView(commandWorkerState = commandWorkerState)
            }
        }
    }
}