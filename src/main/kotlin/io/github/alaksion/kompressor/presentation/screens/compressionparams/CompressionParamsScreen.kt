package io.github.alaksion.kompressor.presentation.screens.compressionparams

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.alaksion.kompressor.domain.params.Codecs
import io.github.alaksion.kompressor.domain.params.CompressionParams
import io.github.alaksion.kompressor.domain.params.Presets
import io.github.alaksion.kompressor.domain.params.Resolution
import io.github.alaksion.kompressor.kompressor.generated.resources.Res
import io.github.alaksion.kompressor.kompressor.generated.resources.compression_params_cta
import io.github.alaksion.kompressor.kompressor.generated.resources.compression_params_title
import io.github.alaksion.kompressor.presentation.components.ContentSurface
import io.github.alaksion.kompressor.presentation.components.Footer
import io.github.alaksion.kompressor.presentation.screens.compressionparams.components.ParamsCard
import io.github.alaksion.kompressor.presentation.screens.compressionparams.components.PresetCard
import io.github.alaksion.kompressor.presentation.theme.KompressorTheme
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun CompressionParamsScreen(
    inputPath: String,
    outputPath: String,
    onContinue: (CompressionParams) -> Unit,
    onBack: () -> Unit,
) {
    val fileFormat = remember(inputPath) {
        inputPath.substringAfterLast('.').uppercase()
    }

    val codec = remember { mutableStateOf(Codecs.Libx264) }
    val resolution = remember { mutableStateOf(Resolution.R_480) }
    val preset = remember { mutableStateOf(Presets.Medium) }
    // Goes from 0 to 51
    val compressionRate = remember { mutableStateOf(0.23f) }

    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(Res.string.compression_params_title)) },
                navigationIcon = {
                    IconButton(
                        onClick = onBack
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
        ContentSurface(
            modifier = Modifier.padding(scaffoldPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Spacer(Modifier.weight(1f))
                ParamsCard(
                    label = "Format",
                    content = {
                        Text(
                            text = fileFormat
                        )
                    }
                )

                ParamsCard(
                    modifier = Modifier.fillMaxWidth(),
                    label = "Compression rate",
                    content = {
                        Slider(
                            value = compressionRate.value,
                            onValueChange = {
                                compressionRate.value = it
                            },
                            valueRange = 0.0f..0.51f
                        )
                    }
                )

                PresetCard(
                    modifier = Modifier.fillMaxWidth(),
                    preset = preset.value,
                    onPresetChange = { preset.value = it },
                    dropdownMenuState = remember { DropdownMenuState() }
                )

                ParamsCard(
                    modifier = Modifier.fillMaxWidth(),
                    label = "Encoding codec",
                    content = {
                        Text(
                            text = codec.value.name
                        )
                    },
                    onClick = {

                    }
                )

                ParamsCard(
                    modifier = Modifier.fillMaxWidth(),
                    label = "Resolution",
                    content = {
                        Text(
                            text = resolution.value.label
                        )
                    },
                    onClick = {

                    }
                )

                Spacer(Modifier.weight(1f))
            }
        }
    }
}

@Composable
@Preview
private fun Preview() {
    KompressorTheme {
        CompressionParamsScreen(
            inputPath = "/Users/alaksion/IdeaProjects/Kompressor/python/video.mp4",
            outputPath = "/Users/alaksion/IdeaProjects/Kompressor/python/video_compressed.mp4",
            onContinue = {},
            onBack = {}
        )
    }
}