package io.bash_psk.empty_media.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import io.bash_psk.empty_media.domain.events.MainUIEvent
import io.bash_psk.empty_media.domain.navigation.NavRoute
import io.bash_psk.empty_media.presentation.activities.main.MainViewModel
import io.bash_psk.empty_media.presentation.utils.DrawerUtils
import io.bash_psk.permission.permission.PermissionDialog
import io.bash_psk.permission.permission.PermissionState
import io.bash_psk.preference.datastore.PreferenceData
import io.bash_psk.preference.datastore.getPreference
import io.bash_psk.utils.activity.getActivity
import kotlinx.coroutines.launch

@Composable
fun NavigationDrawer(
    modifier: Modifier,
    mainViewModel: MainViewModel,
    navHostController: NavHostController
) {

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val coroutineScope = rememberCoroutineScope()
    val activity = context.getActivity()

    val drawerState = rememberDrawerState(
        initialValue = DrawerValue.Closed
    )

    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val mainUIState by mainViewModel.mainUIState.collectAsStateWithLifecycle()
    val allMediaEntityList by mainViewModel.allMediaEntityList.collectAsStateWithLifecycle()

    val getDrawerHead by context.getPreference(
        key = PreferenceData.ProfileNamePreference.key,
        initial = PreferenceData.ProfileNamePreference.initial
    ).collectAsStateWithLifecycle(initialValue = PreferenceData.ProfileNamePreference.initial)

    val navDestination = navBackStackEntry?.destination

    val allPermissionState by mainViewModel.onCheckPermission(
        activity = activity,
        permissionList = mainUIState.permissionList
    ).collectAsStateWithLifecycle()

    DisposableEffect(
        key1 = lifecycleOwner
    ) {

        val observer = LifecycleEventObserver { _, event: Lifecycle.Event ->

            val lifecycleMainUIState = mainUIState.copy(lifecycle = event)

            val setMainUIStateMainUIEvent = MainUIEvent.SetMainUIState(
                mainUIState = lifecycleMainUIState
            )

            mainViewModel.onMainUIEvent(mainUIEvent = setMainUIStateMainUIEvent)
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {

            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    LaunchedEffect(
        key1 = navDestination?.route,
        key2 = mainUIState.isDrawerOpenable
    ) {

        val isOpenDrawer = DrawerUtils.isDrawerOpenable(navDestination = navDestination)
        val drawerMainUIState = mainUIState.copy(isDrawerOpenable = isOpenDrawer)

        val setMainUIStateMainUIEvent = MainUIEvent.SetMainUIState(mainUIState = drawerMainUIState)

        mainViewModel.onMainUIEvent(mainUIEvent = setMainUIStateMainUIEvent)
    }

    LaunchedEffect(
        key1 = mainUIState.lifecycle,
        key2 = mainUIState.permissionList,
        key3 = mainUIState.isPermissionDialog
    ) {

        val isPermission = allPermissionState.all { permissionState: PermissionState ->

            permissionState.isGranted.not()
        }

        val permissionMainUIState = mainUIState.copy(isPermissionDialog = isPermission)

        val setMainUIStateMainUIEvent = MainUIEvent.SetMainUIState(
            mainUIState = permissionMainUIState
        )

        when (mainUIState.lifecycle) {

            Lifecycle.Event.ON_CREATE -> {

                mainViewModel.onMainUIEvent(mainUIEvent = setMainUIStateMainUIEvent)
            }

            Lifecycle.Event.ON_START -> {

            }

            Lifecycle.Event.ON_RESUME -> {

            }

            Lifecycle.Event.ON_PAUSE -> {

            }

            Lifecycle.Event.ON_STOP -> {

            }

            Lifecycle.Event.ON_DESTROY -> {

            }

            Lifecycle.Event.ON_ANY -> {

            }
        }
    }

    PermissionDialog(
        isPermissionDialog = mainUIState.isPermissionDialog,
        onPermissionDialog = { isPermission: Boolean ->

            val permissionMainUIState = mainUIState.copy(isPermissionDialog = isPermission)

            val setMainUIStateMainUIEvent = MainUIEvent.SetMainUIState(
                mainUIState = permissionMainUIState
            )

            mainViewModel.onMainUIEvent(mainUIEvent = setMainUIStateMainUIEvent)
        },
        permissionStateList = allPermissionState,
        onPermissionResult = {

        }
    )

    ModalNavigationDrawer(
        modifier = modifier.fillMaxHeight(),
        drawerState = drawerState,
        gesturesEnabled = mainUIState.isDrawerOpenable,
        drawerContent = {

            ModalDrawerSheet(
                drawerShape = MaterialTheme.shapes.small,
                drawerContainerColor = MaterialTheme.colorScheme.background.copy(alpha = 0.50f)
            ) {

                Spacer(modifier = Modifier.height(height = 20.dp))

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .alpha(alpha = 1.00f),
                    text = getDrawerHead,
                    fontSize = 40.sp,
                    fontFamily = FontFamily.Cursive,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onBackground,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(height = 16.dp))

                HorizontalDivider()

                Spacer(modifier = Modifier.height(height = 16.dp))

                mainUIState.screenList.forEach { navRoute: NavRoute ->

                    val isSelected = DrawerUtils.isDrawerSelected(
                        navDestination = navDestination,
                        navRoute = navRoute
                    )

                    NavigationDrawerItem(
                        modifier = Modifier
                            .wrapContentWidth()
                            .alpha(alpha = 1.00f)
                            .padding(
                                paddingValues = PaddingValues(
                                    vertical = 0.dp,
                                    horizontal = 16.dp
                                )
                            ),
                        colors = NavigationDrawerItemDefaults.colors(
                            unselectedContainerColor = Color.Transparent
                        ),
                        icon = {

                            Icon(
                                imageVector = when (isSelected) {

                                    true -> {

                                        navRoute.selectedIcon
                                    }

                                    false -> {

                                        navRoute.unSelectedIcon
                                    }
                                },
                                contentDescription = navRoute.name
                            )
                        },
                        label = {

                            Text(text = navRoute.name)
                        },
                        badge = {

                            Text(
                                text = when (navRoute.route) {

                                    NavRoute.Downloads.route -> {

                                        "${allMediaEntityList.size}"
                                    }

                                    else -> {

                                        ""
                                    }
                                }
                            )
                        },
                        shape = MaterialTheme.shapes.extraLarge,
                        selected = isSelected,
                        onClick = {

                            coroutineScope.launch {

                                navHostController.navigate(
                                    route = navRoute.route
                                ) {

                                    popUpTo(
                                        route = navHostController
                                            .graph
                                            .startDestinationRoute
                                            .toString()
                                    ) {

                                        saveState = true
                                    }

                                    launchSingleTop = true
                                    restoreState = true
                                }

                                drawerState.close()
                            }
                        }
                    )
                }
            }
        }
    ) {

        MainNavigation(
            mainViewModel = mainViewModel,
            navHostController = navHostController,
            isDrawerOpen = drawerState.isOpen,
            onDrawerMenu = { isOpen: Boolean ->

                coroutineScope.launch {

                    when (isOpen) {

                        true -> {

                            drawerState.open()
                        }

                        false -> {

                            drawerState.close()
                        }
                    }
                }
            }
        )
    }
}