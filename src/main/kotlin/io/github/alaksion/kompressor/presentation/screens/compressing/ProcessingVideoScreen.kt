package io.github.alaksion.kompressor.presentation.screens.compressing

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.github.alaksion.kompressor.domain.params.CompressionParams
import io.github.alaksion.kompressor.kompressor.generated.resources.Res
import io.github.alaksion.kompressor.kompressor.generated.resources.compression_progress_title
import io.github.alaksion.kompressor.presentation.components.ContentSurface
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun ProcessingVideoScreen(
    params: CompressionParams,
    onExit: () -> Unit,
    viewModel: ProcessingVideoViewModel
) {
    val mode by viewModel.screenMode.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.compress(params)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(Res.string.compression_progress_title)
                    )
                }
            )
        },
        bottomBar = {
        }
    ) {
        ContentSurface {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
            ) {
                when (mode) {
                    ProcessingVideoScreenMode.Compressing -> {
                        CircularProgressIndicator(Modifier.align(Alignment.CenterHorizontally))
                    }

                    is ProcessingVideoScreenMode.Error -> TODO()

                    ProcessingVideoScreenMode.Finished -> {
                        Text("Done", Modifier.align(Alignment.CenterHorizontally))
                    }
                }
            }
        }
    }
}