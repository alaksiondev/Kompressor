package io.github.alaksion.kompressor.domain.params

enum class Resolution(val width: Int, val height: Int) {
    R_240(426, 240),
    R_480(854, 480),
    R_720(1280, 720),
    R_1080(1920, 1080),
}