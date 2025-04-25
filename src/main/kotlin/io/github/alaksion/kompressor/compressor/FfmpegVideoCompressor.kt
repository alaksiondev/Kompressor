package io.github.alaksion.kompressor.compressor

import io.github.alaksion.kompressor.params.Codecs
import io.github.alaksion.kompressor.params.Presets
import io.github.alaksion.kompressor.params.Resolution
import java.io.IOException
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

        // Read output to prevent buffer filling and deadlocks
        val output = process.inputStream.bufferedReader().use { it.readText() }
        val errorOutput = process.errorStream.bufferedReader().use { it.readText() }

        val exitCode = process.waitFor()

        if (exitCode != 0) {
            throw IOException(
                """
                Python script failed with exit code $exitCode
                Output: $output
                Error: $errorOutput
            """.trimIndent()
            )
        }

        println("Compression successful. Output: $output")
    }
}