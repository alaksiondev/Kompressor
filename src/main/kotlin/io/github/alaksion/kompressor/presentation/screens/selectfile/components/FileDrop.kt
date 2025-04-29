package io.github.alaksion.kompressor.presentation.screens.selectfile.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.draganddrop.DragAndDropEvent
import androidx.compose.ui.draganddrop.DragAndDropTarget
import androidx.compose.ui.draganddrop.awtTransferable
import io.github.alaksion.kompressor.configs.SupportedFiles
import java.awt.datatransfer.DataFlavor
import java.io.File

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun rememberDropTarget(
    onStartDrop: (event: DragAndDropEvent) -> Unit,
    onEndDrop: (event: DragAndDropEvent) -> Unit,
    onDropResult: (event: DragAndDropEvent, FileData) -> Unit,
    onDropFailure: (event: DragAndDropEvent, DropFailure) -> Unit,
): DragAndDropTarget {
    return remember {
        object : DragAndDropTarget {

            override fun onStarted(event: DragAndDropEvent) {
                onStartDrop(event)
            }

            override fun onEnded(event: DragAndDropEvent) {
                onEndDrop(event)
            }

            override fun onDrop(event: DragAndDropEvent): Boolean {
                val transferable = event.awtTransferable

                if (transferable.isDataFlavorSupported(DataFlavor.javaFileListFlavor).not()) return false

                @Suppress("UNCHECKED_CAST")
                val fileList = transferable.getTransferData(DataFlavor.javaFileListFlavor) as List<File>
                val file = fileList.first()
                val extensionAllowList = SupportedFiles.entries.map { it.extension }

                val extensionIsValid = extensionAllowList.any { extension ->
                    file.name.endsWith(extension, ignoreCase = true)
                }

                if (extensionIsValid.not()) {
                    onDropFailure(event, DropFailure.UnsupportedFileType(file.extension))
                    return false
                }

                onDropResult(
                    event,
                    FileData(
                        fileName = file.name,
                        filePath = file.absolutePath,
                        size = file.length()
                    )
                )
                return true
            }
        }
    }
}

internal data class FileData(
    val fileName: String,
    val filePath: String,
    val size: Long,
)

internal sealed interface DropFailure {
    data class UnsupportedFileType(val type: String) : DropFailure
}