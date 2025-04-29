package io.github.alaksion.kompressor.presentation.screens.compressionparams

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.github.alaksion.kompressor.domain.params.Codecs
import io.github.alaksion.kompressor.domain.params.CompressionParams
import io.github.alaksion.kompressor.domain.params.Presets
import io.github.alaksion.kompressor.domain.params.Resolution
import io.github.alaksion.kompressor.kompressor.generated.resources.*
import io.github.alaksion.kompressor.presentation.components.ContentSurface
import io.github.alaksion.kompressor.presentation.components.Footer
import io.github.alaksion.kompressor.presentation.screens.compressionparams.components.ParamCardWithSelector
import io.github.alaksion.kompressor.presentation.screens.compressionparams.components.ParamsCard
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun CompressionParamsScreen(
    inputPath: String,
    outputPath: String,
    onContinue: (CompressionParams) -> Unit,
    onBack: () -> Unit,
    viewModel: CompressionParamsViewModel
) {
    val fileFormat = remember(inputPath) {
        inputPath.substringAfterLast('.').uppercase()
    }

    val codec = viewModel.codec.collectAsStateWithLifecycle()
    val resolution = viewModel.resolution.collectAsStateWithLifecycle()
    val preset = viewModel.preset.collectAsStateWithLifecycle()
    val compressionRate = viewModel.compressionRate.collectAsStateWithLifecycle()
    val compressionRateFormatted = viewModel.compressionRateFormatted.collectAsStateWithLifecycle()

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
                    onContinue(
                        CompressionParams(
                            codecs = codec.value,
                            resolution = resolution.value,
                            preset = preset.value,
                            compressionRate = (compressionRate.value * 100).toInt(),
                            inputPath = inputPath,
                            outputPath = outputPath
                        )
                    )
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
                    label = stringResource(Res.string.compression_params_format_label),
                    content = {
                        Text(
                            text = fileFormat,
                            style = MaterialTheme.typography.body2
                        )
                    },
                    tooltipText = null
                )

                ParamsCard(
                    modifier = Modifier.fillMaxWidth(),
                    label = stringResource(Res.string.compression_params_rate_label),
                    content = {
                        Slider(
                            value = compressionRate.value,
                            onValueChange = {
                                viewModel.setCompressionRate(it)
                            },
                            valueRange = 0.1f..0.51f
                        )
                    },
                    tooltipText = stringResource(Res.string.compression_params_rate_tooltip)
                )

                ParamCardWithSelector(
                    label = stringResource(Res.string.compression_params_presets_label),
                    modifier = Modifier.fillMaxWidth(),
                    selected = preset.value,
                    options = Presets.entries.toList(),
                    onSelect = { viewModel.setPreset(it) },
                    itemLabelFactory = { it.name },
                    tooltipText = stringResource(Res.string.compression_params_presets_tooltip)
                )

                ParamCardWithSelector(
                    label = stringResource(Res.string.compression_params_codec_label),
                    modifier = Modifier.fillMaxWidth(),
                    selected = codec.value,
                    options = Codecs.entries.toList(),
                    onSelect = { viewModel.setCodec(it) },
                    itemLabelFactory = { it.name },
                    tooltipText = stringResource(Res.string.compression_params_codec_tooltip)
                )

                ParamCardWithSelector(
                    label = stringResource(Res.string.compression_params_resolution_label),
                    modifier = Modifier.fillMaxWidth(),
                    selected = resolution.value,
                    options = Resolution.entries.toList(),
                    onSelect = { viewModel.setResolution(it) },
                    itemLabelFactory = { it.label },
                    tooltipText = stringResource(Res.string.compression_params_resolution_tooltip)
                )

                Spacer(Modifier.weight(1f))
            }
        }
    }
}