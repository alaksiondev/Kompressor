package io.github.alaksion.kompressor.presentation.screens.compressing

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
import io.github.alaksion.kompressor.domain.compressor.FfmpegVideoCompressor
import io.github.alaksion.kompressor.domain.compressor.VideoCompressor
import io.github.alaksion.kompressor.domain.params.Codecs
import io.github.alaksion.kompressor.domain.params.Presets
import io.github.alaksion.kompressor.domain.params.Resolution
import kotlinx.coroutines.launch

@Composable
internal fun ProcessingVideoScreen(
    compressionRate: Int,
    codecs: Codecs,
    preset: Presets,
    resolution: Resolution,
    inputPath: String,
    outputPath: String,
    onBack: () -> Unit,
) {
    val compressor: VideoCompressor = remember { FfmpegVideoCompressor() }
    val scope = rememberCoroutineScope()

    val inProgress = remember { mutableStateOf(false) }

    Scaffold {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Button(onBack) {
                Text("Back")
            }
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
                            inputPath = inputPath,
                            outputPath = outputPath,
                            codecs = codecs,
                            resolution = resolution,
                            presets = preset,
                            crf = compressionRate
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