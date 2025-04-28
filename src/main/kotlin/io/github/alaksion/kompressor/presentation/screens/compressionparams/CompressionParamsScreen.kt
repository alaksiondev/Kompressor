package io.github.alaksion.kompressor.presentation.screens.compressionparams

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import io.github.alaksion.kompressor.domain.params.Codecs
import io.github.alaksion.kompressor.domain.params.CompressionParams
import io.github.alaksion.kompressor.domain.params.Presets
import io.github.alaksion.kompressor.domain.params.Resolution
import io.github.alaksion.kompressor.kompressor.generated.resources.Res
import io.github.alaksion.kompressor.kompressor.generated.resources.compression_params_cta
import io.github.alaksion.kompressor.kompressor.generated.resources.compression_params_title
import io.github.alaksion.kompressor.presentation.components.Footer
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun CompressionParamsScreen(
    inputPath: String,
    outputPath: String,
    onContinue: (CompressionParams) -> Unit
) {
    val codec = remember { mutableStateOf(Codecs.Libx264) }
    val resolution = remember { mutableStateOf(Resolution.R_480) }
    val preset = remember { mutableStateOf(Presets.Medium) }
    val compressionRate = remember { mutableStateOf(23) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(Res.string.compression_params_title)) },
                navigationIcon = {
                    IconButton(
                        onClick = { onContinue }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ChevronLeft,
                            contentDescription = null
                        )
                    }
                },
            )
        },
        bottomBar = {
            Footer(
                label = stringResource(Res.string.compression_params_cta),
                isActive = true,
                onClick = {
                }
            )
        }
    ) { scaffoldPadding ->

    }
}