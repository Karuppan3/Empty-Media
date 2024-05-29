package io.bash_psk.preference.preference

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
internal fun PreferenceCard(
    preferenceTitle: String,
    preferenceSummary: String,
    isEnabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onClick: () -> Unit = {},
    leadingContent: @Composable () -> Unit = {},
    trailingContent: @Composable () -> Unit = {}
) {

    Surface(
        modifier = Modifier.fillMaxWidth(),
        enabled = isEnabled,
        shape = PreferenceCardDefaults.shape,
        onClick = onClick,
        color = PreferenceCardDefaults.containerColor,
        contentColor = PreferenceCardDefaults.contentColor(isEnabled = isEnabled),
        tonalElevation = PreferenceCardDefaults.tonalElevation(isEnabled = isEnabled),
        shadowElevation = PreferenceCardDefaults.shadowElevation(isEnabled = isEnabled),
        interactionSource = interactionSource,
    ) {

        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(
                    paddingValues = PaddingValues(
                        vertical = 0.dp,
                        horizontal = 12.dp
                    )
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(space = 8.dp)
        ) {

            leadingContent()

            Column(
                modifier = Modifier.weight(weight = 1.0f),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.Start
            ) {

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(height = 12.dp)
                )

                Text(
                    text = preferenceTitle,
                    maxLines = 1,
                    style = MaterialTheme.typography.bodyMedium,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(height = 8.dp)
                )

                Text(
                    modifier = Modifier.alpha(alpha = 0.45f),
                    text = preferenceSummary,
                    maxLines = 1,
                    style = MaterialTheme.typography.labelMedium,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(height = 12.dp)
                )
            }

            trailingContent()
        }
    }
}

@Composable
internal fun PreferenceCard(
    preferenceTitle: String,
    preferenceSummary: @Composable () -> Unit = {},
    isEnabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onClick: () -> Unit = {},
    leadingContent: @Composable () -> Unit = {},
    trailingContent: @Composable () -> Unit = {}
) {

    Surface(
        modifier = Modifier.fillMaxWidth(),
        enabled = isEnabled,
        shape = PreferenceCardDefaults.shape,
        onClick = onClick,
        color = PreferenceCardDefaults.containerColor,
        contentColor = PreferenceCardDefaults.contentColor(isEnabled = isEnabled),
        tonalElevation = PreferenceCardDefaults.tonalElevation(isEnabled = isEnabled),
        shadowElevation = PreferenceCardDefaults.shadowElevation(isEnabled = isEnabled),
        interactionSource = interactionSource,
    ) {

        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(
                    paddingValues = PaddingValues(
                        vertical = 0.dp,
                        horizontal = 12.dp
                    )
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(space = 8.dp)
        ) {

            leadingContent()

            Column(
                modifier = Modifier.weight(weight = 1.0f),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.Start
            ) {

                HorizontalDivider()

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(height = 8.dp)
                )

                Text(
                    text = preferenceTitle,
                    maxLines = 1,
                    style = MaterialTheme.typography.bodyMedium,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(height = 8.dp)
                )

                preferenceSummary()

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(height = 8.dp)
                )

                HorizontalDivider()
            }

            trailingContent()
        }
    }
}