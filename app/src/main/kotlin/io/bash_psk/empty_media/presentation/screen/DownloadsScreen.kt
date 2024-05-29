package io.bash_psk.empty_media.presentation.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.bash_psk.empty_media.domain.database.MediaEntity
import io.bash_psk.empty_media.domain.downloads.DownloadSection
import io.bash_psk.empty_media.domain.events.MainUIEvent
import io.bash_psk.empty_media.domain.navigation.NavRoute
import io.bash_psk.empty_media.domain.resource.ConstantDesc
import io.bash_psk.empty_media.domain.resource.ConstantTitle
import io.bash_psk.empty_media.domain.resource.ConstantToast
import io.bash_psk.empty_media.presentation.activities.main.MainViewModel
import io.bash_psk.empty_media.presentation.database.MediaEntityView
import io.bash_psk.empty_media.presentation.tabs.FancyAnimated
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun DownloadsScreen(
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
    val coroutineScope = rememberCoroutineScope()

    val mainUIState by mainViewModel.mainUIState.collectAsStateWithLifecycle()
    val downloadWorkerState by mainViewModel.downloadWorkerState.collectAsStateWithLifecycle()
    val allMediaEntityList by mainViewModel.allMediaEntityList.collectAsStateWithLifecycle()
    val completedMediaEntityList by mainViewModel.completedMediaEntityList.collectAsStateWithLifecycle()
    val failedMediaEntityList by mainViewModel.failedMediaEntityList.collectAsStateWithLifecycle()
    val runningMediaEntityList by mainViewModel.runningMediaEntityList.collectAsStateWithLifecycle()

    val downloadsPagerState = rememberPagerState(
        pageCount = {

            mainUIState.downloadSectionList.size
        }
    )

    val tabIndicator = @Composable { tabPositions: List<TabPosition> ->

        FancyAnimated(
            tabPosition = tabPositions,
            selectedTab = downloadsPagerState.currentPage
        )
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

                    Text(text = ConstantTitle.DOWNLOADS_APP_TITLE)
                }
            )
        }
    ) { paddingValues: PaddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = paddingValues),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            ScrollableTabRow(
                modifier = Modifier.fillMaxWidth(),
                selectedTabIndex = downloadsPagerState.currentPage,
                indicator = tabIndicator
            ) {

                mainUIState.downloadSectionList.forEachIndexed { pageIndex, downloadSection ->

                    Tab(
                        selected = downloadsPagerState.currentPage == pageIndex,
                        text = {

                            Text(
                                text = downloadSection.label,
                                maxLines = 1
                            )
                        },
                        onClick = {

                            coroutineScope.launch {

                                downloadsPagerState.animateScrollToPage(page = pageIndex)
                            }
                        }
                    )
                }
            }

            HorizontalPager(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(
                    vertical = 0.dp,
                    horizontal = 8.dp
                ),
                state = downloadsPagerState,
                key = { page: Int ->

                    page
                }
            ) { page: Int ->

                when (page) {

                    DownloadSection.ALL.id -> {

                        LazyColumn(
                            modifier = Modifier.fillMaxSize()
                                .padding(
                                    paddingValues = PaddingValues(
                                        vertical = 0.dp,
                                        horizontal = 8.dp
                                    )
                                ),
                            verticalArrangement = Arrangement.spacedBy(space = 8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            items(
                                items = allMediaEntityList
                            ) { mediaEntity: MediaEntity ->

                                MediaEntityView(
                                    mediaEntity = mediaEntity,
                                    onMediaEntity = {}
                                )
                            }
                        }
                    }

                    DownloadSection.COMPLETED.id -> {

                        LazyColumn(
                            modifier = Modifier.fillMaxSize()
                                .padding(
                                    paddingValues = PaddingValues(
                                        vertical = 0.dp,
                                        horizontal = 8.dp
                                    )
                                ),
                            verticalArrangement = Arrangement.spacedBy(space = 8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            items(
                                items = completedMediaEntityList
                            ) { mediaEntity: MediaEntity ->

                                MediaEntityView(
                                    mediaEntity = mediaEntity,
                                    onMediaEntity = {}
                                )
                            }
                        }
                    }

                    DownloadSection.FAILED.id -> {

                        LazyColumn(
                            modifier = Modifier.fillMaxSize()
                                .padding(
                                    paddingValues = PaddingValues(
                                        vertical = 0.dp,
                                        horizontal = 8.dp
                                    )
                                ),
                            verticalArrangement = Arrangement.spacedBy(space = 8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            items(
                                items = failedMediaEntityList
                            ) { mediaEntity: MediaEntity ->

                                MediaEntityView(
                                    mediaEntity = mediaEntity,
                                    onMediaEntity = { entity: MediaEntity ->

                                        val enterUrlSearchMediaState = mainUIState.copy(
                                            enteredUrl = entity.url
                                        )

                                        val setMainUIStateMainUIEvent = MainUIEvent.SetMainUIState(
                                            mainUIState = enterUrlSearchMediaState
                                        )

                                        val searchMediaMainUIEvent = when (
                                            mainUIState.enteredUrl.isNotEmpty()
                                        ) {

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

                                        mainViewModel.onMainUIEvent(
                                            mainUIEvent = setMainUIStateMainUIEvent
                                        )

                                        mainViewModel.onMainUIEvent(mainUIEvent = searchMediaMainUIEvent)

                                        onNavigateScreen(NavRoute.Home)
                                    }
                                )
                            }
                        }
                    }

                    DownloadSection.RUNNING.id -> {

                        LazyColumn(
                            modifier = Modifier.fillMaxSize()
                                .padding(
                                    paddingValues = PaddingValues(
                                        vertical = 0.dp,
                                        horizontal = 8.dp
                                    )
                                ),
                            verticalArrangement = Arrangement.spacedBy(space = 8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            items(
                                items = runningMediaEntityList
                            ) { mediaEntity: MediaEntity ->

                                MediaEntityView(
                                    mediaEntity = mediaEntity,
                                    onMediaEntity = {}
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}