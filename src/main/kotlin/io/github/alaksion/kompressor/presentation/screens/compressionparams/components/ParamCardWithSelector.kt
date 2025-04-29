package io.github.alaksion.kompressor.presentation.screens.compressionparams.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import io.github.alaksion.kompressor.presentation.components.LabeledCard

@Composable
internal fun <T> ParamCardWithSelector(
    label: String,
    tooltipText: String,
    options: List<T>,
    selected: T,
    onSelect: (T) -> Unit,
    itemLabelFactory: (T) -> String,
    modifier: Modifier = Modifier
) {
    var dialogVisible by remember { mutableStateOf(false) }

    LabeledCard(
        modifier = modifier,
        label = label,
        content = {
            Text(
                text = itemLabelFactory(selected),
                style = MaterialTheme.typography.body2
            )
        },
        onClick = {
            dialogVisible = true
        },
        tooltipText = tooltipText
    )

    if (dialogVisible) {
        val scrollState = rememberScrollState()
        Dialog(
            onDismissRequest = { dialogVisible = false }
        ) {
            Card(
                modifier = Modifier
                    .heightIn(max = 300.dp)
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
                        options.forEach { item ->
                            DropdownMenuItem(
                                onClick = {
                                    onSelect(item)
                                    dialogVisible = false
                                }
                            ) {
                                Text(text = itemLabelFactory(item))
                            }
                        }
                    }
                }
            }
        }
    }
}