package io.github.alaksion.kompressor.domain.process

import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

internal interface ProcessHandler {
    suspend fun execute(
        process: ProcessType,
        command: List<String>,
    ): Int

    suspend fun execute(
        process: ProcessType,
        command: String,
    ): Int

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
    ): Int {
        return suspendCoroutine { continuation ->
            runCatching {
                val fullCommand = listOf(
                    process.command,
                    *command.toTypedArray()
                )

                val builder = ProcessBuilder(fullCommand).redirectErrorStream(true).start()

                builder.waitFor()
            }.fold(
                onSuccess = {
                    it
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
    ): Int {
        return execute(process = process, command = listOf(command))
    }
}