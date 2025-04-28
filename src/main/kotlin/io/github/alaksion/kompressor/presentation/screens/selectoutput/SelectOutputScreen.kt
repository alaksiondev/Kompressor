package io.github.alaksion.kompressor.presentation.screens.selectoutput

import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.alaksion.kompressor.kompressor.generated.resources.*
import io.github.alaksion.kompressor.presentation.screens.selectoutput.components.openDirectoryBrowser
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun SelectOutputScreen(
    inputPath: String,
    onClick: () -> Unit,
) {
    val inputDirectory = remember(inputPath) {
        inputPath.substringBeforeLast("/")
    }

    val inputName = remember(inputPath) {
        inputPath.substringAfterLast("/")
    }

    val outputDirectory = remember { mutableStateOf(inputDirectory) }

    val outputPath = remember {
        derivedStateOf {
            if (outputDirectory.value.isNotBlank()) {
                val inputNameSplit = inputName.split(".")
                val outputFileName = inputNameSplit[0] + "_compressed" + ".${inputNameSplit[1]}"
                "${outputDirectory.value}/${outputFileName}"
            } else {
                null
            }
        }
    }

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
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(0.80f),
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
                }
            )

            Spacer(Modifier.height(32.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(0.80f),
                value = outputPath.value.orEmpty(),
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
                }
            )

            Spacer(Modifier.height(32.dp))

            Button(
                modifier = Modifier
                    .fillMaxWidth(0.80f)
                    .height(64.dp),
                onClick = {

                },
                enabled = outputPath.value.orEmpty().isNotBlank(),
                shape = MaterialTheme.shapes.large
            ) {
                Text(
                    text = stringResource(Res.string.select_file_continue_cta),
                )
            }
        }
    }
}