package io.github.alaksion.kompressor.presentation.screens.selectoutput

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.FileDownload
import androidx.compose.material.icons.filled.FileOpen
import androidx.compose.material.icons.filled.FileUpload
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.alaksion.kompressor.kompressor.generated.resources.*
import io.github.alaksion.kompressor.presentation.components.ContentSurface
import io.github.alaksion.kompressor.presentation.components.Footer
import io.github.alaksion.kompressor.presentation.screens.selectoutput.components.CompressionFlow
import io.github.alaksion.kompressor.presentation.screens.selectoutput.components.CompressionOptions
import io.github.alaksion.kompressor.presentation.screens.selectoutput.components.openDirectoryBrowser
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun SelectOutputScreen(
    inputPath: String,
    onBack: () -> Unit,
    onExpressClick: (String, String) -> Unit,
    onCustomClick: (String, String) -> Unit
) {
    val inputDirectory = remember(inputPath) {
        inputPath.substringBeforeLast("/")
    }

    val inputName = remember(inputPath) {
        inputPath.substringAfterLast("/")
    }

    val compressionFlow = remember { mutableStateOf(CompressionFlow.Express) }

    val outputDirectory = remember { mutableStateOf(inputDirectory) }

    val buttonTextResource = remember {
        derivedStateOf {
            if (compressionFlow.value == CompressionFlow.Express) {
                Res.string.select_output_express_cta
            } else {
                Res.string.select_output_custom_cta
            }
        }
    }

    val outputPath = remember {
        derivedStateOf {
            if (outputDirectory.value.isNotBlank()) {
                val inputNameSplit = inputName.split(".")
                val outputFileName = inputNameSplit[0] + "_compressed" + ".${inputNameSplit[1]}"
                "${outputDirectory.value}/${outputFileName}"
            } else {
                ""
            }
        }
    }

    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(Res.string.select_output_title)) },
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
                label = stringResource(buttonTextResource.value),
                isActive = outputPath.value.isNotBlank(),
                onClick = {
                    if (compressionFlow.value == CompressionFlow.Express) {
                        onExpressClick(inputPath, outputPath.value)
                    } else {
                        onCustomClick(inputPath, outputPath.value)
                    }
                }
            )
        }
    ) { scaffoldPadding ->
        ContentSurface(
            modifier = Modifier
                .padding(scaffoldPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.Center
            ) {

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = inputPath,
                    onValueChange = {},
                    readOnly = true,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.FileUpload,
                            contentDescription = null
                        )
                    },
                    label = {
                        Text(
                            text = stringResource(Res.string.select_output_input_label)
                        )
                    },
                )

                Spacer(Modifier.height(32.dp))

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = outputPath.value,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                openDirectoryBrowser(
                                    defaultDirectory = inputDirectory,
                                )?.let {
                                    outputDirectory.value = it
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.FileOpen,
                                contentDescription = null
                            )
                        }
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.FileDownload,
                            contentDescription = null
                        )
                    },
                    label = {
                        Text(
                            text = stringResource(Res.string.select_output_output_label)
                        )
                    },
                )

                Spacer(Modifier.height(32.dp))

                CompressionOptions(
                    modifier = Modifier.fillMaxWidth(),
                    currentFlow = compressionFlow.value,
                    onFlowChanged = { compressionFlow.value = it }
                )
            }
        }
    }
}