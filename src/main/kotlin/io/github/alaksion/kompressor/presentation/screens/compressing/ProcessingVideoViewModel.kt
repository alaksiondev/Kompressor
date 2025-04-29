package io.github.alaksion.kompressor.presentation.screens.compressing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.alaksion.kompressor.domain.compressor.VideoCompressor
import io.github.alaksion.kompressor.domain.params.CompressionParams
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class ProcessingVideoViewModel(
    private val videoCompressor: VideoCompressor,
    private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {

    val screenMode = MutableStateFlow<ProcessingVideoScreenMode>(
        value = ProcessingVideoScreenMode.Compressing
    )

    fun compress(params: CompressionParams) {
        viewModelScope.launch(ioDispatcher) {
            screenMode.update { ProcessingVideoScreenMode.Compressing }
            runCatching {
                videoCompressor.compress(
                    inputPath = params.inputPath,
                    outputPath = params.outputPath,
                    codecs = params.codecs,
                    resolution = params.resolution,
                    presets = params.preset,
                    crf = params.compressionRate
                )
            }.fold(
                onSuccess = {
                    screenMode.update { ProcessingVideoScreenMode.Finished }
                },
                onFailure = { error ->
                    screenMode.update { ProcessingVideoScreenMode.Error(error) }
                }
            )
        }
    }
}

internal sealed interface ProcessingVideoScreenMode {
    object Compressing : ProcessingVideoScreenMode
    object Finished : ProcessingVideoScreenMode
    data class Error(val error: Throwable) : ProcessingVideoScreenMode
}