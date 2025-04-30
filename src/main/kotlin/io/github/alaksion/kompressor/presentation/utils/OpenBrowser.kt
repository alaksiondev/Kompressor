package io.github.alaksion.kompressor.presentation.utils

import java.awt.Desktop
import java.net.URI


fun openWebpage(uri: URI?): Boolean {
    return runCatching {
        val desktop = Desktop.getDesktop()
        if (desktop.isSupported(Desktop.Action.BROWSE)) {
            desktop.browse(uri)
        }
    }.fold(
        onSuccess = {
            true
        },
        onFailure = { error ->
            error.printStackTrace()
            false
        }
    )
}