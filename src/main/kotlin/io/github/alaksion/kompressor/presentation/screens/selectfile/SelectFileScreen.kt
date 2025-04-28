package io.github.alaksion.kompressor.presentation.screens.selectfile

import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily

@Composable
internal fun SelectFileScreen() {
    Scaffold {
        Column {
            Text(
                text = "Inter",
                fontStyle = MaterialTheme.typography.h1.fontStyle
            )

            Text(
                text = "Other",
                fontStyle = MaterialTheme.typography.h1.copy(
                    fontFamily = FontFamily.Cursive
                ).fontStyle
            )
        }

    }
}