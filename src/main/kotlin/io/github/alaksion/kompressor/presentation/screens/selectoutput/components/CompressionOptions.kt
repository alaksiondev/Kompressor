package io.github.alaksion.kompressor.presentation.screens.selectoutput.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.alaksion.kompressor.kompressor.generated.resources.Res
import io.github.alaksion.kompressor.kompressor.generated.resources.select_output_compression_custom
import io.github.alaksion.kompressor.kompressor.generated.resources.select_output_compression_express
import io.github.alaksion.kompressor.kompressor.generated.resources.select_output_compression_option_title
import io.github.alaksion.kompressor.presentation.components.LabeledCheckbox
import org.jetbrains.compose.resources.stringResource

internal enum class CompressionFlow {
    Express,
    Custom
}

@Composable
internal fun CompressionOptions(
    currentFlow: CompressionFlow,
    onFlowChanged: (CompressionFlow) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(Res.string.select_output_compression_option_title),
            style = MaterialTheme.typography.h6
        )

        LabeledCheckbox(
            label = stringResource(Res.string.select_output_compression_express),
            isChecked = currentFlow == CompressionFlow.Express,
            onCheckedChange = {
                onFlowChanged(CompressionFlow.Express)
            },
        )

        LabeledCheckbox(
            label = stringResource(Res.string.select_output_compression_custom),
            isChecked = currentFlow == CompressionFlow.Custom,
            onCheckedChange = {
                onFlowChanged(CompressionFlow.Custom)
            },
        )
    }
}