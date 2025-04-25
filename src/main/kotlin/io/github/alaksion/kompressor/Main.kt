package io.github.alaksion.kompressor

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import io.github.alaksion.kompressor.compressor.FfmpegVideoCompressor
import io.github.alaksion.kompressor.compressor.VideoCompressor
import io.github.alaksion.kompressor.params.Codecs
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
@Preview
fun App() {
    var text by remember { mutableStateOf("Hello, World!") }
    val compressor: VideoCompressor = remember { FfmpegVideoCompressor() }

    MaterialTheme {
        Button(onClick = {
            CoroutineScope(Dispatchers.IO).launch {
                compressor.compress(
                    inputPath = "/Users/alaksion/IdeaProjects/Kompressor/python/input.mp4",
                    outputPath = "/Users/alaksion/IdeaProjects/Kompressor/python/output.mp4",
                    codecs = Codecs.Libx264,
                )
            }
        }) {
            Text(text)
        }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
