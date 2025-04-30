package io.github.alaksion.kompressor.domain.process

internal interface ProcessHandler {
    suspend fun execute(
        process: ProcessType,
        command: List<String>,
    ): Int

    companion object {
        val instance: ProcessHandler = ProcessHandlerImpl()
    }
}

internal enum class ProcessType(val command: String) {
    Python("python")
}

internal class ProcessHandlerImpl : ProcessHandler {

    override suspend fun execute(
        process: ProcessType,
        command: List<String>,
    ): Int {
        val fullCommand = listOf(
            process.command,
            *command.toTypedArray()
        )

        val builder = ProcessBuilder(fullCommand).redirectErrorStream(true).start()

        return builder.waitFor()
    }

}