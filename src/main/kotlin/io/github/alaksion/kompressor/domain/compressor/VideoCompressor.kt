package io.github.alaksion.kompressor.domain.compressor

import io.github.alaksion.kompressor.domain.params.Codecs
import io.github.alaksion.kompressor.domain.params.Presets
import io.github.alaksion.kompressor.domain.params.Resolution

interface VideoCompressor {
    /***
     * Compresses a video file using the specified codec, CRF, preset, and resolution.
     *
     * @param inputPath Path to the input video file.
     * @param outputPath Path to the output video file.
     * @param codecs The codec to use for compression.
     * @param crf The Constant Rate Factor (CRF) value for compression (Goes from 1 to 51) Defaults to 23.
     * @param presets The [Presets] entry to use for compression. Defaults to [Presets.Medium].
     * @param resolution The [Resolution] entry to use for compression. Defaults to [Resolution.R_720].
     */
    suspend fun compress(
        inputPath: String,
        outputPath: String,
        codecs: Codecs,
        crf: Int = 23,
        presets: Presets = Presets.Medium,
        resolution: Resolution = Resolution.R_720
    )

    companion object {
        fun getInstance(): VideoCompressor = FfmpegVideoCompressor()
    }
}