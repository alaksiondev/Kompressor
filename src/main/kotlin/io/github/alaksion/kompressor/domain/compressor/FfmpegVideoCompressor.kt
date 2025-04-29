package io.github.alaksion.kompressor.domain.compressor

import io.github.alaksion.kompressor.domain.params.Codecs
import io.github.alaksion.kompressor.domain.params.Presets
import io.github.alaksion.kompressor.domain.params.Resolution
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.io.path.Path

internal class FfmpegVideoCompressor : VideoCompressor {

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

        val process = ProcessBuilder(
            "python3",
            scriptPath,
            inputPath,
            outputPath,
            "--codec", codecs.id,
            "--crf", crf.toString(),
            "--preset", presets.id,
            "--scale", "${resolution.width}:${resolution.height}"
        ).redirectErrorStream(true).start()

        return flow {
            val exitCode = process.waitFor()
            println("exitCode: $exitCode")

            if (exitCode != 0) {
                emit(ProcessMessage.Failure)
            } else {
                emit(ProcessMessage.Success)
            }
        }
    }
}