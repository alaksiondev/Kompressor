package io.github.alaksion.kompressor

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import io.github.alaksion.kompressor.screens.compressing.CompressingVideoScreen

@Composable
@Preview
fun App() {
    MaterialTheme {
        CompressingVideoScreen()
    }
}

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        resizable = false
    ) {
        App()
    }
}
