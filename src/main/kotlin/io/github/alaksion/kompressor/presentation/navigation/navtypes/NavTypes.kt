package io.github.alaksion.kompressor.presentation.navigation.navtypes

import androidx.core.bundle.Bundle
import androidx.navigation.NavType
import io.github.alaksion.kompressor.domain.params.Codecs
import io.github.alaksion.kompressor.domain.params.Presets
import io.github.alaksion.kompressor.domain.params.Resolution

internal object ResolutionNavType : NavType<Resolution>(isNullableAllowed = false) {

    override fun get(
        bundle: Bundle,
        key: String
    ): Resolution? {
        val key = bundle.getString(key) ?: return null
        return Resolution.valueOf(key)
    }

    override fun parseValue(value: String): Resolution {
        return Resolution.valueOf(value)
    }

    override fun put(
        bundle: Bundle,
        key: String,
        value: Resolution
    ) {
        bundle.putString(key = key, value = value.name)
    }
}

internal object CodecsNavType : NavType<Codecs>(isNullableAllowed = false) {
    override fun get(
        bundle: Bundle,
        key: String
    ): Codecs? {
        val key = bundle.getString(key) ?: return null
        return Codecs.valueOf(key)
    }

    override fun parseValue(value: String): Codecs {
        return Codecs.valueOf(value)
    }

    override fun put(
        bundle: Bundle,
        key: String,
        value: Codecs
    ) {
        bundle.putString(key = key, value = value.name)
    }
}

internal object PresetsNavType : NavType<Presets>(isNullableAllowed = false) {
    override fun get(
        bundle: Bundle,
        key: String
    ): Presets? {
        val key = bundle.getString(key) ?: return null
        return Presets.valueOf(key)
    }

    override fun parseValue(value: String): Presets {
        return Presets.valueOf(value)
    }

    override fun put(
        bundle: Bundle,
        key: String,
        value: Presets
    ) {
        bundle.putString(key = key, value = value.name)
    }
}