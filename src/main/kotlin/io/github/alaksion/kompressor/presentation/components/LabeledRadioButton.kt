package io.github.alaksion.kompressor.presentation.components

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.github.alaksion.kompressor.presentation.theme.KompressorTheme

@Composable
internal fun LabeledRadioButton(
    label: String,
    isChecked: Boolean,
    onCheckedChange: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        RadioButton(
            selected = isChecked,
            onClick = onCheckedChange
        )

        Text(
            text = label,
            style = MaterialTheme.typography.body2
        )
    }
}

@Preview
@Composable
private fun Preview() {
    KompressorTheme {
        Column {
            LabeledRadioButton(
                label = "Test",
                onCheckedChange = {},
                isChecked = true
            )
            LabeledRadioButton(
                label = "Test",
                onCheckedChange = {},
                isChecked = true
            )
            LabeledRadioButton(
                label = "Test",
                onCheckedChange = {},
                isChecked = true
            )
            LabeledRadioButton(
                label = "Test",
                onCheckedChange = {},
                isChecked = true
            )
        }

    }
}