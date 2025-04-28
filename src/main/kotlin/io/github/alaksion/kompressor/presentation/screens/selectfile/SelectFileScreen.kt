package io.github.alaksion.kompressor.presentation.screens.selectfile

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.awt.ComposeWindow
import androidx.compose.ui.window.WindowScope
import java.awt.FileDialog

@Composable
internal fun WindowScope.SelectFileScreen() {
    Scaffold {
        val dialog = FileDialog(
            window as ComposeWindow,
            "Select a file",
            FileDialog.LOAD
        )
    }
}