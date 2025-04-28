package io.github.alaksion.kompressor.presentation.screens.compressionparams.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
internal fun ParamsCard(
    label: String,
    content: @Composable RowScope.() -> Unit,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
) {
    val cardModifier = remember {
        if (onClick != null) {
            Modifier.clickable(
                onClick = onClick
            )
        } else {
            Modifier
        }
    }

    Column(modifier = modifier) {
        Text(
            text = label
        )
        Spacer(Modifier.height(8.dp))
        Card(modifier = cardModifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    content()
                }
                Spacer(Modifier.width(16.dp))
                onClick?.let { acton ->
                    Icon(
                        imageVector = Icons.Default.ChevronRight,
                        contentDescription = null
                    )
                }
            }
        }
    }
}