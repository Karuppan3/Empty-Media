package io.bash_psk.preference.preference

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.bash_psk.preference.datastore.PreferenceData
import io.bash_psk.preference.datastore.getPreference
import io.bash_psk.preference.resource.ConstantString

@Composable
fun <T> LibUpdatePreference(
    preferenceData: PreferenceData<T, String, T>,
    isEnabled: Boolean = true,
    isUpdating: Boolean,
    onLibUpdate: (
        message: String
    ) -> Unit
) {

    val context = LocalContext.current

    val getLibVersion by context.getPreference(
        key = preferenceData.key,
        initial = preferenceData.initial
    ).collectAsStateWithLifecycle(initialValue = preferenceData.initial)

    PreferenceCard(
        preferenceTitle = preferenceData.title,
        preferenceSummary = "$getLibVersion",
        isEnabled = isEnabled,
        onClick = {

            when (isUpdating) {

                true -> {

                    onLibUpdate(ConstantString.UPDATE_IS_RUNNING)
                }

                false -> {

                    onLibUpdate(ConstantString.LIBRARY_IS_UPDATING)
                }
            }
        }
    )
}