package io.github.alaksion.kompressor.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.alaksion.kompressor.compressor.FfmpegVideoCompressor
import io.github.alaksion.kompressor.compressor.VideoCompressor
import io.github.alaksion.kompressor.params.Codecs
import kotlinx.coroutines.launch

@Composable
internal fun CompressingVideoScreen() {
    val compressor: VideoCompressor = remember { FfmpegVideoCompressor() }
    val scope = rememberCoroutineScope()

    val inProgress = remember { mutableStateOf(false) }

    Scaffold {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            if (inProgress.value) {
                CircularProgressIndicator(Modifier.align(Alignment.CenterHorizontally))
            } else {
                Text("Done", Modifier.align(Alignment.CenterHorizontally))
            }
            Spacer(Modifier.height(16.dp))
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    scope.launch {
                        inProgress.value = true
                        compressor.compress(
                            inputPath = "/Users/alaksion/IdeaProjects/Kompressor/python/input.mp4",
                            outputPath = "/Users/alaksion/IdeaProjects/Kompressor/python/output.mp4",
                            codecs = Codecs.Libx264,
                        )
                        inProgress.value = false
                    }
                }
            ) {
                Text("Compress Video")
            }
        }
    }
}