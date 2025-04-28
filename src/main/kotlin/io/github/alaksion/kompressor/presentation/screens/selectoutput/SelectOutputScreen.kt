package io.github.alaksion.kompressor.presentation.screens.selectoutput

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.runtime.Composable
import io.github.alaksion.kompressor.kompressor.generated.resources.Res
import io.github.alaksion.kompressor.kompressor.generated.resources.select_output_title
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun SelectOutputScreen(
    inputPath: String,
    onClick: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(Res.string.select_output_title)) },
                navigationIcon = {
                    IconButton(
                        onClick = onClick
                    ) {
                        Icon(
                            imageVector = Icons.Default.ChevronLeft,
                            contentDescription = null
                        )
                    }
                },
            )
        }
    ) {
        Text(inputPath)
    }
}