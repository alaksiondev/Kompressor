package io.github.alaksion.kompressor.domain.params

enum class Resolution(val width: Int, val height: Int, val label: String) {
    R_240(426, 240, "240p"),
    R_480(854, 480, "480p"),
    R_720(1280, 720, "720p"),
    R_1080(1920, 1080, "1080p"),
}