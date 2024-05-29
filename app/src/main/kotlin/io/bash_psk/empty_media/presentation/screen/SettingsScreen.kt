package io.bash_psk.empty_media.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.work.WorkInfo
import io.bash_psk.empty_media.domain.events.MainUIEvent
import io.bash_psk.empty_media.domain.navigation.NavRoute
import io.bash_psk.empty_media.domain.resource.ConstantDesc
import io.bash_psk.empty_media.domain.resource.ConstantSetting
import io.bash_psk.empty_media.domain.resource.ConstantTitle
import io.bash_psk.empty_media.presentation.activities.main.MainViewModel
import io.bash_psk.preference.datastore.PreferenceData
import io.bash_psk.preference.preference.ItemPreference
import io.bash_psk.preference.preference.LibUpdatePreference
import io.bash_psk.preference.preference.SingleListPreference
import io.bash_psk.preference.preference.TextFieldPreference

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
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
    
    val updateWorkerState by mainViewModel.updateWorkerState.collectAsStateWithLifecycle()
    
    val isYtdlUpdating = remember(key1 = updateWorkerState) {
        
        when (updateWorkerState.state) {
            
            WorkInfo.State.ENQUEUED -> {

                false
            }
            
            WorkInfo.State.RUNNING -> {

                true
            }
            
            WorkInfo.State.SUCCEEDED -> {

                false
            }
            
            WorkInfo.State.FAILED -> {

                false
            }
            
            WorkInfo.State.BLOCKED -> {

                false
            }
            
            WorkInfo.State.CANCELLED -> {

                false
            }
        }
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

                    Text(text = ConstantTitle.SETTINGS_APP_TITLE)
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
                
                ItemPreference(
                    title = ConstantSetting.APPLICATION_SETTINGS
                ) {
                    
                    TextFieldPreference(
                        preferenceData = PreferenceData.ProfileNamePreference,
                        dialogTitle = PreferenceData.ProfileNamePreference.title,
                        fieldLabel = PreferenceData.ProfileNamePreference.title
                    )
                    
                    SingleListPreference(
                        preferenceData = PreferenceData.AppThemePreference,
                        dialogTitle = PreferenceData.AppThemePreference.title
                    )
                    
                    LibUpdatePreference(
                        preferenceData = PreferenceData.YtdlLibrary,
                        isUpdating = isYtdlUpdating,
                        onLibUpdate = { message: String ->
                            
                            val setToastMainUIEvent = MainUIEvent.SetToast(
                                context = context,
                                message = message
                            )

                            val updateLibraryMainUIEvent = MainUIEvent.UpdateLibrary

                            mainViewModel.onMainUIEvent(mainUIEvent = setToastMainUIEvent)
                            mainViewModel.onMainUIEvent(mainUIEvent = updateLibraryMainUIEvent)
                        }
                    )
                }
            }
        }
    }
}