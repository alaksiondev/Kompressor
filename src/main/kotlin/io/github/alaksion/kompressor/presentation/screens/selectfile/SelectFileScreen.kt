package io.github.alaksion.kompressor.presentation.screens.selectfile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.github.alaksion.kompressor.kompressor.generated.resources.Res
import io.github.alaksion.kompressor.kompressor.generated.resources.select_file_continue_cta
import io.github.alaksion.kompressor.kompressor.generated.resources.select_file_title
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun SelectFileScreen() {
    Scaffold(
        topBar = {
            TopAppBar {
                Text(
                    text = stringResource(Res.string.select_file_title),
                )
            }
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                modifier = Modifier.fillMaxWidth(0.4f),
                onClick = {}
            ) {
                Text(
                    text = stringResource(Res.string.select_file_continue_cta),
                )
            }
        }
    }
}