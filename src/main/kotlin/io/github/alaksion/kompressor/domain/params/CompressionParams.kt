package io.github.alaksion.kompressor.domain.params

internal data class CompressionParams(
    val compressionRate: Int,
    val codecs: Codecs,
    val preset: Presets,
    val resolution: Resolution,
    val inputPath: String,
    val outputPath: String,
)
