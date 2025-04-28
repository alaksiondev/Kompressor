package io.github.alaksion.kompressor.presentation.screens.selectfile

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposeWindow
import androidx.compose.ui.unit.dp
import io.github.alaksion.kompressor.kompressor.generated.resources.Res
import io.github.alaksion.kompressor.kompressor.generated.resources.select_file_continue_cta
import io.github.alaksion.kompressor.kompressor.generated.resources.select_file_title
import io.github.alaksion.kompressor.presentation.components.Footer
import io.github.alaksion.kompressor.presentation.screens.selectfile.components.FilePickerBox
import io.github.alaksion.kompressor.presentation.screens.selectfile.components.FilePickerBoxState
import io.github.alaksion.kompressor.presentation.screens.selectfile.components.openFileBrowser
import io.github.alaksion.kompressor.presentation.theme.KompressorTheme
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun SelectFileScreen(
    onNavigateToSelectOutput: (String) -> Unit,
) {
    val pickerState = remember {
        mutableStateOf<FilePickerBoxState>(FilePickerBoxState.Unselected)
    }

    val selectedFilePath = remember {
        derivedStateOf {
            if (pickerState.value is FilePickerBoxState.Selected) {
                (pickerState.value as FilePickerBoxState.Selected).filePath
            } else {
                ""
            }
        }
    }
    val window = remember { ComposeWindow() }

    Scaffold(
        topBar = {
            TopAppBar {
                Text(
                    text = stringResource(Res.string.select_file_title),
                )
            }
        },
        bottomBar = {
            Footer(
                label = stringResource(Res.string.select_file_continue_cta),
                isActive = selectedFilePath.value.isNotBlank(),
                onClick = {
                    onNavigateToSelectOutput(selectedFilePath.value)
                }
            )
        }
    ) { scaffoldPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(scaffoldPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            FilePickerBox(
                state = pickerState.value,
                modifier = Modifier
                    .fillMaxWidth(0.80f)
                    .height(300.dp),
                onClick = {
                    val file = openFileBrowser(window)
                    file?.let { selectedFile ->
                        pickerState.value = FilePickerBoxState.Selected(
                            fileName = selectedFile.name,
                            size = formatFileSize(selectedFile.length()),
                            filePath = selectedFile.absolutePath
                        )
                    }
                },
                onCancelFile = {
                    pickerState.value = FilePickerBoxState.Unselected
                }
            )
        }
    }
}

fun formatFileSize(sizeInBytes: Long): String {
    val units = arrayOf("B", "KB", "MB", "GB", "TB", "PB")
    var size = sizeInBytes.toDouble()
    var unitIndex = 0

    while (size >= 1024 && unitIndex < units.size - 1) {
        size /= 1024
        unitIndex++
    }

    return String.format("%.2f %s", size, units[unitIndex])
}

@Composable
@Preview
private fun Preview() {
    KompressorTheme {
        SelectFileScreen { }
    }
}