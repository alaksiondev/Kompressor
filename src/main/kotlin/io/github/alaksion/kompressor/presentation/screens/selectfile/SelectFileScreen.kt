package io.github.alaksion.kompressor.presentation.screens.selectfile

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.draganddrop.dragAndDropTarget
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposeWindow
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dashedBorder
import io.github.alaksion.kompressor.configs.SupportedFiles
import io.github.alaksion.kompressor.kompressor.generated.resources.Res
import io.github.alaksion.kompressor.kompressor.generated.resources.select_file_continue_cta
import io.github.alaksion.kompressor.kompressor.generated.resources.select_file_title
import io.github.alaksion.kompressor.presentation.components.ContentSurface
import io.github.alaksion.kompressor.presentation.components.Footer
import io.github.alaksion.kompressor.presentation.screens.selectfile.components.*
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
@Composable
internal fun SelectFileScreen(
    onNavigateToSelectOutput: (String) -> Unit,
    viewModel: SelectFileViewModel
) {
    val pickerState = viewModel.pickerState.collectAsStateWithLifecycle()
    val selectedFilePath = remember {
        derivedStateOf {
            if (pickerState.value is FilePickerBoxState.Selected) {
                (pickerState.value as FilePickerBoxState.Selected).filePath
            } else {
                ""
            }
        }
    }
    val fileTypeError = remember { mutableStateOf<String?>(null) }

    val window = remember { ComposeWindow() }
    val dropTarget = rememberDropTarget(
        onStartDrop = { event -> println("drop started: ${event.action}") },
        onEndDrop = { event -> println("drop ended: ${event.action}") },
        onDropResult = { event, result ->
            viewModel.updatePickerState(
                newState = FilePickerBoxState.Selected(
                    fileName = result.fileName,
                    size = formatFileSize(result.size),
                    filePath = result.filePath
                )
            )
        },
        onDropFailure = { event, err ->
            when (err) {
                is DropFailure.UnsupportedFileType -> {
                    fileTypeError.value = err.type
                }
            }
        }
    )

    val supportedFormats = remember {
        SupportedFiles.entries.joinToString(separator = ",") {
            it.extension
        }
    }

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
        ContentSurface(
            modifier = Modifier.padding(scaffoldPadding)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
            ) {
                FilePickerBox(
                    state = pickerState.value,
                    modifier = Modifier
                        .clickable(
                            enabled = pickerState.value is FilePickerBoxState.Unselected,
                            onClick = {
                                val file = openFileBrowser(window)
                                file?.let { selectedFile ->
                                    viewModel.updatePickerState(
                                        newState = FilePickerBoxState.Selected(
                                            fileName = selectedFile.name,
                                            size = formatFileSize(selectedFile.length()),
                                            filePath = selectedFile.absolutePath
                                        )
                                    )
                                }
                            }
                        )
                        .dragAndDropTarget(
                            shouldStartDragAndDrop = { event ->
                                pickerState.value is FilePickerBoxState.Unselected
                            },
                            target = dropTarget
                        )
                        .dashedBorder(
                            color = MaterialTheme.colors.onSurface,
                            shape = MaterialTheme.shapes.medium,
                            gapLength = 18.dp,
                            dashLength = 12.dp
                        )
                        .pointerHoverIcon(icon = PointerIcon.Hand)
                        .fillMaxWidth()
                        .height(300.dp),
                    onCancelFile = {
                        viewModel.updatePickerState(
                            newState = FilePickerBoxState.Unselected
                        )
                    },
                    supportedFormats = supportedFormats
                )
            }

            fileTypeError.value?.let {
                WrongFileTypeDialog(
                    visible = fileTypeError.value != null,
                    fileType = it,
                    onDismiss = { fileTypeError.value = null },
                    validFormats = supportedFormats
                )
            }
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