package io.github.alaksion.kompressor.params

enum class Codecs(
    val supportText: String,
    val id: String,
) {
    Libx264("Better compatibility", "libx264"),
    Libx265("Better compression", "libx265"),
    Libvpx_vp9("Open source video codec", "libvpx_vp9");
}