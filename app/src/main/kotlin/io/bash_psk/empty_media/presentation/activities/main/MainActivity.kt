package io.bash_psk.empty_media.presentation.activities.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import io.bash_psk.empty_media.presentation.navigation.NavigationDrawer
import io.bash_psk.empty_media.presentation.theme.EmptyMediaTheme
import io.bash_psk.preference.datastore.PreferenceData
import io.bash_psk.preference.datastore.getPreference
import io.bash_psk.preference.settings.rememberAppTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()
        enableEdgeToEdge()

        setContent {

            val appTheme by this.getPreference(
                key = PreferenceData.AppThemePreference.key,
                initial = PreferenceData.AppThemePreference.initial
            ).collectAsStateWithLifecycle(initialValue = PreferenceData.AppThemePreference.initial)

            EmptyMediaTheme(
                darkTheme = rememberAppTheme(theme = appTheme)
            ) {

                val navHostController = rememberNavController()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    contentWindowInsets = WindowInsets.systemBars
                        .only(sides = WindowInsetsSides.Horizontal)
                ) { innerPaddingValues: PaddingValues ->

                    NavigationDrawer(
                        modifier = Modifier.fillMaxSize().padding(paddingValues = innerPaddingValues),
                        mainViewModel = mainViewModel,
                        navHostController = navHostController
                    )
                }
            }
        }
    }
}