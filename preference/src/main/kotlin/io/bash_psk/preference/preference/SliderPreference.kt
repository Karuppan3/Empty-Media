package io.bash_psk.preference.preference

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.bash_psk.preference.datastore.PreferenceData
import io.bash_psk.preference.datastore.getPreference
import io.bash_psk.preference.datastore.setPreference
import io.bash_psk.preference.formatter.PreferenceFormatter

@Composable
fun SliderPreference(
    preferenceData: PreferenceData<Float, String, String>,
    valueRange: ClosedFloatingPointRange<Float>,
    steps: Int = 0,
    isEnabled: Boolean = true,
    isValueVisible: Boolean = false
) {

    val context = LocalContext.current

    val getPosition by context.getPreference(
        key = preferenceData.key,
        initial = preferenceData.initial
    ).collectAsStateWithLifecycle(initialValue = preferenceData.initial)

    PreferenceCard(
        preferenceTitle = preferenceData.title,
        preferenceSummary = {

            Slider(
                modifier = Modifier.fillMaxWidth(),
                value = getPosition,
                enabled = isEnabled,
                valueRange = valueRange,
                steps = steps,
                onValueChange = { position: Float ->

                    context.setPreference(key = preferenceData.key, value = position)
                }
            )
        },
        isEnabled = isEnabled,
        onClick = {

        },
        trailingContent = {

            when {

                isValueVisible -> {

                    Text(
                        text = "${
                            PreferenceFormatter.decimalFormatter(decimal = getPosition, index = 1)
                        }",
                        maxLines = 1,
                        style = MaterialTheme.typography.labelMedium,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    )
}