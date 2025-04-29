package io.github.alaksion.kompressor.presentation.screens.compressing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.alaksion.kompressor.domain.compressor.ProcessMessage
import io.github.alaksion.kompressor.domain.compressor.VideoCompressor
import io.github.alaksion.kompressor.domain.params.CompressionParams
import io.github.alaksion.kompressor.presentation.utils.formatFileSize
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.File

internal class ProcessingVideoViewModel(
    private val videoCompressor: VideoCompressor,
    private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {

    val originalFileSize = MutableStateFlow(0L)
    val compressedFileSize = MutableStateFlow(0L)

    val sizeDiff = originalFileSize.combine(compressedFileSize) { original, compressed ->
        (original - compressed).formatFileSize()
    }.stateIn(
        scope = viewModelScope,
        initialValue = 0,
        started = SharingStarted.Eagerly
    )

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
                    crf = params.compressionRate,
                ).collect { message ->
                    when (message) {
                        ProcessMessage.Failure ->
                            screenMode.update { ProcessingVideoScreenMode.Error(Exception()) }

                        ProcessMessage.Success -> {
                            originalFileSize.update { File(params.inputPath).length() }
                            compressedFileSize.update { File(params.outputPath).length() }
                            screenMode.update { ProcessingVideoScreenMode.Finished }
                        }
                    }
                }
            }.onFailure { error ->
                screenMode.update { ProcessingVideoScreenMode.Error(error) }
            }
        }
    }
}

internal sealed interface ProcessingVideoScreenMode {
    object Compressing : ProcessingVideoScreenMode
    object Finished : ProcessingVideoScreenMode
    data class Error(val error: Throwable) : ProcessingVideoScreenMode
}