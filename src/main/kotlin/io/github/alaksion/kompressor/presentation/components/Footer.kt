package io.github.alaksion.kompressor.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
internal fun Footer(
    label: String,
    isActive: Boolean,
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 16.dp),
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Button(
            modifier = Modifier.fillMaxWidth(0.80f),
            onClick = onClick,
            enabled = isActive,
            shape = MaterialTheme.shapes.large
        ) {
            Text(
                text = label,
            )
        }
    }
}