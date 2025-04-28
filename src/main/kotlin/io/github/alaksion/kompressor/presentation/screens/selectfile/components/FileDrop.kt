package io.github.alaksion.kompressor.presentation.screens.selectfile.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.draganddrop.*
import java.awt.datatransfer.DataFlavor

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

internal data class FileData(
    val path: String,
    val name: String,
)

internal enum class DropFailure {
    UnsupportedFileType,
    FileTooLarge,
}