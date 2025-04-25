package io.github.alaksion.kompressor.compressor

import io.github.alaksion.kompressor.params.Codecs
import io.github.alaksion.kompressor.params.Presets
import io.github.alaksion.kompressor.params.Resolution
import java.io.BufferedReader
import java.io.InputStreamReader
import kotlin.io.path.Path

internal class FfmpegVideoCompressor : VideoCompressor {

    override suspend fun compress(
        inputPath: String,
        outputPath: String,
        codecs: Codecs,
        crf: Int,
        presets: Presets,
        resolution: Resolution
    ) {
        require(crf in 1..51) { "CRF must be between 1 and 51" }

        val scriptPath = Path("").toAbsolutePath().toString() + "/python/video_compressor.py"

        val processBuilder = ProcessBuilder(
            scriptPath,
            inputPath,
            outputPath,
            "--code ${codecs.id}",
            "--crf $crf",
            "--preset ${presets.id}",
            "--scale ${resolution.width}:${resolution.height}"
        ).start()

        val stream = BufferedReader(InputStreamReader(processBuilder.inputStream))

        var line: String?
        while (stream.readLine().also { line = it } != null) {
            println(line)
        }

        processBuilder.waitFor()
        processBuilder.exitValue()
    }
}