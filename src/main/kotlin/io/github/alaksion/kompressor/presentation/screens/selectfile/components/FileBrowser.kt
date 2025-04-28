package io.github.alaksion.kompressor.presentation.screens.selectfile.components

import androidx.compose.ui.awt.ComposeWindow
import io.github.alaksion.kompressor.configs.SupportedFiles
import java.awt.FileDialog
import java.io.File
import java.io.FilenameFilter

internal fun openFileBrowser(
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
