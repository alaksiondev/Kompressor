package io.github.alaksion.kompressor.presentation.screens.selectoutput.components

import java.io.File
import javax.swing.JFileChooser

internal fun openDirectoryBrowser(
    defaultDirectory: String,
): String? {
    val chooser = JFileChooser().apply {
        dialogTitle = "Select output directory"
        fileSelectionMode = JFileChooser.DIRECTORIES_ONLY
        isMultiSelectionEnabled = false
        currentDirectory = File(defaultDirectory)
    }

    val result = chooser.showOpenDialog(null)

    if (result == JFileChooser.APPROVE_OPTION) {
        if (chooser.selectedFile.isDirectory) {
            return chooser.selectedFile.absolutePath
        }
    }

    return null
}
