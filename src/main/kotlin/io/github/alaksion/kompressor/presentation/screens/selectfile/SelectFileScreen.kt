package io.github.alaksion.kompressor.presentation.screens.selectfile

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.draganddrop.dragAndDropTarget
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposeWindow
import androidx.compose.ui.draganddrop.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.github.alaksion.kompressor.configs.SupportedFiles
import io.github.alaksion.kompressor.kompressor.generated.resources.Res
import io.github.alaksion.kompressor.kompressor.generated.resources.select_file_continue_cta
import io.github.alaksion.kompressor.kompressor.generated.resources.select_file_title
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import java.awt.FileDialog
import java.awt.datatransfer.DataFlavor
import java.io.File
import java.io.FilenameFilter

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun SelectFileScreen() {
    var selectedFiles by remember { mutableStateOf("") }
    val window = remember { ComposeWindow() }
    var dropZoneColor by remember { mutableStateOf(Color.Cyan) }
    val scope = rememberCoroutineScope()

    val dropTarget = rememberDropTarget(
        onEndDrop = {
            scope.launch {
                delay(1000)
                dropZoneColor = Color.Cyan
            }
        },
        onStartDrop = {
            dropZoneColor = Color.Yellow
        },
        onDropResult = { file ->
            dropZoneColor = Color.Green
            selectedFiles = file.name
        },
        onDropFailure = {
            dropZoneColor = Color.Red
        }
    )

    Scaffold(
        topBar = {
            TopAppBar {
                Text(
                    text = stringResource(Res.string.select_file_title),
                )
            }
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    val file = openFileChooser(window)
                    file?.let {
                        selectedFiles = it.name
                    }

                }
            ) {
                Text("Open chooser")
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .height(150.dp)
                    .background(Color.Cyan)
                    .dragAndDropTarget(
                        shouldStartDragAndDrop = { true },
                        target = dropTarget
                    )
            )

            Text(
                text = selectedFiles.ifEmpty { "Arquivo sem nome porra" },
                modifier = Modifier.fillMaxWidth(0.8f)
            )

            Button(
                modifier = Modifier.fillMaxWidth(0.4f),
                onClick = {}
            ) {
                Text(
                    text = stringResource(Res.string.select_file_continue_cta),
                )
            }
        }
    }
}

private fun openFileChooser(
    window: ComposeWindow
): File? {
    val chooser = FileDialog(window).apply {
        title = "Select file"
        mode = FileDialog.LOAD
        isMultipleMode = false
        filenameFilter = FilenameFilter { _, name ->
            val extensionAllowList = SupportedFiles.entries.map { it.extension }

            extensionAllowList.any { extension ->
                name.endsWith(extension, ignoreCase = true)
            }
        }
    }

    chooser.isVisible = true

    return chooser.files.firstOrNull()
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun rememberDropTarget(
    onStartDrop: () -> Unit,
    onEndDrop: () -> Unit,
    onDropResult: (FileData) -> Unit,
    onDropFailure: (DropFailure) -> Unit,
): DragAndDropTarget {

    return remember {
        object : DragAndDropTarget {

            override fun onStarted(event: DragAndDropEvent) {
                onStartDrop()
            }

            override fun onEnded(event: DragAndDropEvent) {
                onEndDrop()
            }

            override fun onDrop(event: DragAndDropEvent): Boolean {
                println("Action at the target: ${event.action}")

                val transferable = event.awtTransferable

                val fileList = event.dragData() as DragData.FilesList
                val aa = fileList.readFiles().joinToString { "" }
                onDropResult(
                    FileData(
                        path = aa,
                        name = aa
                    )
                )

                if (transferable.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                    @Suppress("UNCHECKED_CAST")
                    val fileList = transferable.getTransferData(DataFlavor.stringFlavor) as String
                    val file = fileList

                    onDropResult(
                        FileData(
                            path = aa,
                            name = aa
                        )
                    )
                }
                return true
            }
        }
    }
}

data class FileData(
    val path: String,
    val name: String,
)

enum class DropFailure {
    UnsupportedFileType,
    FileTooLarge,
}