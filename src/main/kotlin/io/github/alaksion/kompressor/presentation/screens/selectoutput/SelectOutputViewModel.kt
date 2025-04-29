package io.github.alaksion.kompressor.presentation.screens.selectoutput

import androidx.lifecycle.ViewModel
import io.github.alaksion.kompressor.presentation.screens.selectoutput.components.CompressionFlow
import kotlinx.coroutines.flow.MutableStateFlow

internal class SelectOutputViewModel : ViewModel() {
    val compressionFlow = MutableStateFlow(CompressionFlow.Express)
    val outputPath = MutableStateFlow("")

    fun setCompressionFlow(flow: CompressionFlow) {
        compressionFlow.value = flow
    }

    fun setOutputPath(path: String, inputFileName: String) {
        val newPath = if (path.isNotBlank()) {
            val inputNameSplit = inputFileName.split(".")
            val outputFileName = inputNameSplit[0] + "_compressed" + ".${inputNameSplit[1]}"
            "${path}/${outputFileName}"
        } else {
            ""
        }
        outputPath.value = newPath
    }
}