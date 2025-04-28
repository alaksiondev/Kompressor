package io.github.alaksion.kompressor.presentation.screens.selectfile.components.filepicker

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.draganddrop.DragAndDropEvent
import androidx.compose.ui.draganddrop.DragAndDropTarget
import androidx.compose.ui.draganddrop.awtTransferable
import java.awt.datatransfer.DataFlavor
import java.io.File

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun rememberDropTarget(
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

                if (transferable.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                    @Suppress("UNCHECKED_CAST")
                    val fileList = transferable.getTransferData(DataFlavor.javaFileListFlavor) as List<File>
                    val file = fileList.first()

                    onDropResult(
                        FileData(
                            path = "teste",
                            name = "teste"
                        )
                    )
                }
                return true
            }
        }
    }
}

internal data class FileData(
    val path: String,
    val name: String,
)

internal enum class DropFailure {
    UnsupportedFileType,
    FileTooLarge,
}