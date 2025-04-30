package io.github.alaksion.kompressor.presentation.screens.setup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.alaksion.kompressor.domain.process.ProcessHandler
import io.github.alaksion.kompressor.domain.process.ProcessType
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

internal class SetupViewModel(
    private val processHandler: ProcessHandler,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    val dependenciesEnabled = MutableStateFlow(false)
    val isLoading = MutableStateFlow(false)

    fun checkDependencies() {
        viewModelScope.launch(context = dispatcher) {
            isLoading.update { true }
            val pythonEnabled = checkPython()
            val ffmpegEnabled = checkFfmpeg()
            dependenciesEnabled.update { pythonEnabled && ffmpegEnabled }
            delay(500.milliseconds)
            isLoading.update { false }
        }
    }

    private suspend fun checkPython(): Boolean {
        return processHandler.execute(
            process = ProcessType.Python,
            command = "--version"
        ) == 0
    }

    private suspend fun checkFfmpeg(): Boolean {
        return processHandler.execute(
            process = ProcessType.Ffmpeg,
            command = "-version | sed -n \"s/ffmpeg version \\([-0-9.]*\\).*/\\1/p;\""
        ) == 0
    }
}