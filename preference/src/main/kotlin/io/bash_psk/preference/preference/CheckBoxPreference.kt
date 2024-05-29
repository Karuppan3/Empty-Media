package io.bash_psk.preference.preference

import androidx.compose.material3.Checkbox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.bash_psk.preference.datastore.PreferenceData
import io.bash_psk.preference.datastore.getPreference
import io.bash_psk.preference.datastore.setPreference
import io.bash_psk.preference.resource.ConstantString

@Composable
fun CheckBoxPreference(
    preferenceData: PreferenceData<Boolean, String, String>,
    isEnabled: Boolean = true
) {

    val context = LocalContext.current

    val getChecked by context.getPreference(
        key = preferenceData.key,
        initial = preferenceData.initial
    ).collectAsStateWithLifecycle(initialValue = preferenceData.initial)

    PreferenceCard(
        preferenceTitle = preferenceData.title,
        preferenceSummary = when (getChecked) {

            true -> {

                ConstantString.ENABLED
            }

            false -> {

                ConstantString.DISABLED
            }
        },
        isEnabled = isEnabled,
        onClick = {

            context.setPreference(key = preferenceData.key, value = getChecked.not())
        },
        trailingContent = {

            Checkbox(
                enabled = isEnabled,
                checked = getChecked,
                onCheckedChange = { isChecked: Boolean ->

                    context.setPreference(key = preferenceData.key, value = isChecked)
                }
            )
        }
    )
}