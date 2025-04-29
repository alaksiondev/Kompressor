package io.github.alaksion.kompressor.presentation.screens.compressionparams

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.alaksion.kompressor.domain.params.Codecs
import io.github.alaksion.kompressor.domain.params.Presets
import io.github.alaksion.kompressor.domain.params.Resolution
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlin.math.max
import kotlin.math.min

internal class CompressionParamsViewModel : ViewModel() {
    val codec = MutableStateFlow(Codecs.Libx264)
    val resolution = MutableStateFlow(Resolution.R_480)
    val preset = MutableStateFlow(Presets.Medium)
    val compressionRate = MutableStateFlow(0.23f)
    val compressionRateFormatted = compressionRate.map {
        it * 100
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = 23
    )

    fun setCodec(codec: Codecs) {
        this.codec.value = codec
    }

    fun setResolution(resolution: Resolution) {
        this.resolution.value = resolution
    }

    fun setPreset(preset: Presets) {
        this.preset.value = preset
    }

    fun setCompressionRate(rate: Float) {
        val topCap = max(0.1f, rate)
        val minCap = min(topCap, rate)
        this.compressionRate.value = minCap
    }
}