package io.bash_psk.empty_media.presentation.tabs

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TabPosition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun FancyAnimated(
    tabPosition: List<TabPosition>,
    selectedTab: Int
) {

    val colors = listOf(
        MaterialTheme.colorScheme.primary,
        MaterialTheme.colorScheme.primaryContainer,
        MaterialTheme.colorScheme.tertiary,
        MaterialTheme.colorScheme.tertiaryContainer,
    )

    val tabTransition = updateTransition(
        targetState = selectedTab,
        label = ""
    )

    val tabIndicationStart by tabTransition.animateDp(
        transitionSpec = {

            when {

                initialState < targetState -> {

                    spring(dampingRatio = 1f, stiffness = 50f)
                }

                else -> {

                    spring(dampingRatio = 1f, stiffness = 1000f)
                }
            }
        },
        label = ""
    ) { targetValue: Int ->

        tabPosition[targetValue].left
    }

    val tabIndicationEnd by tabTransition.animateDp(
        transitionSpec = {

            when {

                initialState < targetState -> {

                    spring(
                        dampingRatio = 1f,
                        stiffness = 1000f
                    )
                }

                else -> {

                    spring(
                        dampingRatio = 1f,
                        stiffness = 50f
                    )
                }
            }
        },
        label = ""
    ) { targetValue ->

        tabPosition[targetValue].right
    }

    val indicatorColor by tabTransition.animateColor(
        label = ""
    ) { targetValue: Int ->

        colors[targetValue % colors.size]
    }

    FancyIndicator(
        modifier = Modifier.fillMaxSize()
            .wrapContentSize(align = Alignment.BottomStart)
            .offset(x = tabIndicationStart)
            .width(width = tabIndicationEnd - tabIndicationStart),
        color = indicatorColor
    )
}