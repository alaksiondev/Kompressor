package io.github.alaksion.kompressor.domain.process

import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

internal interface ProcessHandler {
    suspend fun execute(
        process: ProcessType,
        command: List<String>,
    ): ProcessResult

    suspend fun execute(
        process: ProcessType,
        command: String,
    ): ProcessResult

    companion object {
        val instance: ProcessHandler = ProcessHandlerImpl()
    }
}

internal enum class ProcessType(val command: String) {
    Python("python"),
    Ffmpeg("ffmpeg")
}

internal class ProcessHandlerImpl : ProcessHandler {

    override suspend fun execute(
        process: ProcessType,
        command: List<String>,
    ): ProcessResult {
        return suspendCoroutine { continuation ->
            runCatching {
                val fullCommand = listOf(
                    process.command,
                    *command.toTypedArray()
                )

                val result = ProcessBuilder(fullCommand).redirectErrorStream(true).start()
                val output = result.inputStream.bufferedReader().readText()
                val exitCode = result.waitFor()
                Pair(exitCode, output)
            }.fold(
                onSuccess = {
                    continuation.resume(
                        ProcessResult(
                            code = it.first,
                            output = it.second
                        )
                    )
                },
                onFailure = { error ->
                    continuation.resumeWithException(error)
                }
            )
        }
    }

    override suspend fun execute(
        process: ProcessType,
        command: String
    ): ProcessResult {
        return execute(process = process, command = listOf(command))
    }
}

internal data class ProcessResult(
    val code: Int,
    val output: String,
)