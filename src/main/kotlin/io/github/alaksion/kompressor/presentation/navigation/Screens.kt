package io.github.alaksion.kompressor.presentation.navigation

import io.github.alaksion.kompressor.domain.params.Codecs
import io.github.alaksion.kompressor.domain.params.Presets
import io.github.alaksion.kompressor.domain.params.Resolution
import kotlinx.serialization.Serializable

sealed interface Screens {

    @Serializable
    object Setup: Screens

    @Serializable
    object SelectFile : Screens

    @Serializable
    data class SelectOutput(
        val inputPath: String,
    ) : Screens

    @Serializable
    data class Params(
        val inputPath: String,
        val outputPath: String
    )

    @Serializable
    data class ProcessingFile(
        val compressionRate: Int = 23,
        val codecs: Codecs = Codecs.Libx264,
        val preset: Presets = Presets.Fast,
        val resolution: Resolution = Resolution.R_480,
        val inputPath: String,
        val outputPath: String,
    ) : Screens
}