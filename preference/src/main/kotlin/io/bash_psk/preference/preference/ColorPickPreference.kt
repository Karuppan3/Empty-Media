package io.bash_psk.preference.preference

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.bash_psk.preference.datastore.PreferenceData
import io.bash_psk.preference.datastore.getPreference
import io.bash_psk.preference.datastore.setPreference
import io.bash_psk.preference.formatter.PreferenceFormatter
import io.bash_psk.preference.picker.ColorPickDialog

@Composable
fun ColorPickPreference(
    preferenceData: PreferenceData<String, String, String>,
    isEnabled: Boolean = true
) {

    val context = LocalContext.current

    val getColorHex by context.getPreference(
        key = preferenceData.key,
        initial = preferenceData.initial
    ).collectAsStateWithLifecycle(initialValue = preferenceData.initial)

    val isDialog = rememberSaveable {
        mutableStateOf(value = false)
    }

    ColorPickDialog(
        isColorPickDialog = isDialog.value,
        onColorPickDialog = { isPicker: Boolean ->

            isDialog.value = isPicker
        },
        onSelectedColor = { color: Color ->

            context.setPreference(
                key = preferenceData.key,
                value = PreferenceFormatter.colorToHex(color = color)
            )
        }
    )

    PreferenceCard(
        preferenceTitle = preferenceData.title,
        preferenceSummary = getColorHex,
        isEnabled = isEnabled,
        onClick = {

            isDialog.value = isDialog.value.not()
        },
        trailingContent = {

            Box(
                modifier = Modifier
                    .size(width = 45.dp, height = 30.dp)
                    .clip(shape = MaterialTheme.shapes.extraSmall)
                    .border(
                        border = BorderStroke(
                            width = 0.5.dp,
                            color = MaterialTheme.colorScheme.surfaceTint
                        ),
                        shape = MaterialTheme.shapes.extraSmall
                    )
                    .background(color = PreferenceFormatter.hexToColor(hex = getColorHex))
            )
        }
    )
}