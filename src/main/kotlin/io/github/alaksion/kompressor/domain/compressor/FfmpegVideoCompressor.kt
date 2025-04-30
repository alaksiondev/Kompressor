package io.github.alaksion.kompressor.domain.compressor

import io.github.alaksion.kompressor.domain.params.Codecs
import io.github.alaksion.kompressor.domain.params.Presets
import io.github.alaksion.kompressor.domain.params.Resolution
import io.github.alaksion.kompressor.domain.process.ProcessHandler
import io.github.alaksion.kompressor.domain.process.ProcessType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.io.path.Path

internal class FfmpegVideoCompressor(
    private val processHandler: ProcessHandler
) : VideoCompressor {

    override suspend fun compress(
        inputPath: String,
        outputPath: String,
        codecs: Codecs,
        crf: Int,
        presets: Presets,
        resolution: Resolution,
    ): Flow<ProcessMessage> {
        require(crf in 1..51) { "CRF must be between 1 and 51" }

        val scriptPath = Path("").toAbsolutePath().toString() + "/python/video_compressor.py"

        return flow {
            val exitCode = processHandler.execute(
                process = ProcessType.Python,
                command = listOf(
                    scriptPath,
                    inputPath,
                    outputPath,
                    "--codec", codecs.id,
                    "--crf", crf.toString(),
                    "--preset", presets.id,
                    "--scale", "${resolution.width}:${resolution.height}"
                )
            )
            println("exitCode: $exitCode")

            if (exitCode.code != 0) {
                emit(ProcessMessage.Failure)
            } else {
                emit(ProcessMessage.Success)
            }
        }
    }
}