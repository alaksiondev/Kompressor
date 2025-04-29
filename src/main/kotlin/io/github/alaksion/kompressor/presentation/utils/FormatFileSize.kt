package io.github.alaksion.kompressor.presentation.utils

internal fun Long.formatFileSize(): String {
    val units = arrayOf("B", "KB", "MB", "GB", "TB", "PB")
    var size = toDouble()
    var unitIndex = 0

    while (size >= 1024 && unitIndex < units.size - 1) {
        size /= 1024
        unitIndex++
    }

    return String.format("%.2f %s", size, units[unitIndex])
}