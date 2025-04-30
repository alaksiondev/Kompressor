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

    val dependenciesEnabled = MutableStateFlow<DependencyState>(DependencyState.Partial(false, false))
    val isLoading = MutableStateFlow(false)

    fun checkDependencies() {
        viewModelScope.launch(context = dispatcher) {
            isLoading.update { true }
            val pythonEnabled = checkPython()
            val ffmpegEnabled = checkFfmpeg()
            delay(500.milliseconds)
            isLoading.update { false }
            dependenciesEnabled.update {
                if (pythonEnabled && ffmpegEnabled) {
                    DependencyState.Available
                } else {
                    DependencyState.Partial(pythonEnabled, ffmpegEnabled)
                }
            }
        }
    }

    private suspend fun checkPython(): Boolean {
        return processHandler.execute(
            process = ProcessType.Python,
            command = "--version"
        ).code == 0
    }

    private suspend fun checkFfmpeg(): Boolean {
        "-version | sed -n \"s/ffmpeg version \\([-0-9.]*\\).*/\\1/p;\""

        val output = processHandler.execute(
            process = ProcessType.Ffmpeg,
            command = listOf()
        ).output

        val regex = Regex("""ffmpeg version ([\d\.\-]+)""")
        val version = regex.find(output)?.groupValues?.get(1).orEmpty()

        return version.isNotEmpty()
    }
}

internal sealed interface DependencyState {
    data object Available : DependencyState
    data class Partial(val pythonEnabled: Boolean, val ffmpegEnabled: Boolean) : DependencyState
}