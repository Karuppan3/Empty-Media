package io.bash_psk.preference.picker

import android.graphics.Color.colorToHSV
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import io.bash_psk.preference.color.HuePanel
import io.bash_psk.preference.color.SaturationPanel
import io.bash_psk.preference.resource.ConstantButton
import io.bash_psk.preference.resource.ConstantDesc
import io.bash_psk.preference.resource.ConstantTitle

@Composable
internal fun ColorPickDialog(
    isColorPickDialog: Boolean,
    onColorPickDialog: (
        isPicker: Boolean
    ) -> Unit,
    onSelectedColor: (
        color: Color
    ) -> Unit
) {

    val hsv = remember {

        val hsv = floatArrayOf(0.0f, 0.0f, 0.0f)

        colorToHSV(Color.Blue.toArgb(), hsv)

        mutableStateOf(value = Triple(first = hsv[0], second = hsv[1], third = hsv[2]))
    }

    val selectedColor = remember(key1 = hsv.value) {
        mutableStateOf(
            value = Color.hsv(
                hue = hsv.value.first,
                saturation = hsv.value.second,
                value = hsv.value.third
            )
        )
    }

    AnimatedVisibility(
        visible = isColorPickDialog,
        enter = fadeIn(),
        exit = fadeOut()
    ) {

        AlertDialog(
            modifier = Modifier.fillMaxSize(),
            onDismissRequest = {

                onColorPickDialog(false)
            },
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = false,
                usePlatformDefaultWidth = false,
                decorFitsSystemWindows = false
            ),
            shape = MaterialTheme.shapes.medium,
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.70f),
            title = {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Text(
                        text = ConstantTitle.SELECT_COLOR,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    IconButton(
                        onClick = {

                            onColorPickDialog(false)
                        }
                    ) {

                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = ConstantDesc.CLOSE_BUTTON_DESC
                        )
                    }
                }
            },
            text = {

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(space = 12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    item {

                        SaturationPanel(
                            hue = hsv.value.first,
                            onSaturation = { saturation: Float, value2: Float ->

                                val newHsv = Triple(
                                    first = hsv.value.first,
                                    second = saturation,
                                    third = value2
                                )

                                hsv.value = newHsv
                            }
                        )
                    }

                    item {

                        HuePanel(
                            onHueColor = { hue: Float ->

                                val newHsv = Triple(
                                    first = hue,
                                    second = hsv.value.second,
                                    third = hsv.value.third
                                )

                                hsv.value = newHsv
                            }
                        )
                    }

                    item {

                        Box(
                            modifier = Modifier
                                .size(size = 100.dp)
                                .clip(shape = MaterialTheme.shapes.medium)
                                .background(color = selectedColor.value)
                        )
                    }
                }
            },
            confirmButton = {

                Button(
                    onClick = {

                        onSelectedColor(selectedColor.value)
                        onColorPickDialog(false)
                    }
                ) {

                    Text(text = ConstantButton.OK_BUTTON)
                }
            },
            dismissButton = {

                OutlinedButton(
                    onClick = {

                        onColorPickDialog(false)
                    }
                ) {

                    Text(text = ConstantButton.CANCEL_BUTTON)
                }
            }
        )
    }
}