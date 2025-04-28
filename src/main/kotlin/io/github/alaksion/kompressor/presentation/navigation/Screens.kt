package io.github.alaksion.kompressor.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface Screens {
    @Serializable
    object SelectFile : Screens

    @Serializable
    object ProcessingFile : Screens
}