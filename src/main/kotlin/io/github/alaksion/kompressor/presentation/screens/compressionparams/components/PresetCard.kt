package io.github.alaksion.kompressor.presentation.screens.compressionparams.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import io.github.alaksion.kompressor.domain.params.Presets

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun PresetCard(
    modifier: Modifier = Modifier,
    dropdownMenuState: DropdownMenuState,
    preset: Presets,
    onPresetChange: (Presets) -> Unit
) {
    var dialogVisible by remember { mutableStateOf(false) }

    ParamsCard(
        modifier = modifier,
        label = "Preset",
        content = {
            Text(
                text = preset.name
            )
        },
        onClick = {
            dialogVisible = true
        }
    )

    if (dialogVisible) {
        val scrollState = rememberScrollState()
        Dialog(
            onDismissRequest = { dialogVisible = false }
        ) {
            Card(
                modifier = Modifier
                    .height(300.dp)
                    .width(300.dp),
                shape = MaterialTheme.shapes.large,
                elevation = 2.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                ) {
                    Text(
                        text = "Select a preset",
                        style = MaterialTheme.typography.h6
                    )
                    Spacer(Modifier.height(12.dp))

                    Column(
                        modifier = Modifier
                            .verticalScroll(scrollState)
                    ) {
                        Presets.entries.forEach { preset ->
                            DropdownMenuItem(
                                onClick = {
                                    onPresetChange(preset)
                                    dialogVisible = false
                                }
                            ) {
                                Text(text = preset.name)
                            }
                        }
                    }
                }
            }
        }
    }
}
