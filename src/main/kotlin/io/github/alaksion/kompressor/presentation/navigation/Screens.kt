package io.github.alaksion.kompressor.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface Screens {
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
    object ProcessingFile : Screens
}