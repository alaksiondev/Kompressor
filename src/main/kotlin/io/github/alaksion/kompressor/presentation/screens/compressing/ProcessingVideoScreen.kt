package io.github.alaksion.kompressor.presentation.screens.compressing

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.github.alaksion.kompressor.domain.params.CompressionParams
import io.github.alaksion.kompressor.kompressor.generated.resources.Res
import io.github.alaksion.kompressor.kompressor.generated.resources.compression_progress_title
import io.github.alaksion.kompressor.presentation.components.ContentSurface
import io.github.alaksion.kompressor.presentation.components.Footer
import io.github.alaksion.kompressor.presentation.screens.compressing.components.SuccessState
import io.github.alaksion.kompressor.presentation.utils.formatFileSize
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun ProcessingVideoScreen(
    params: CompressionParams,
    onExit: () -> Unit,
    viewModel: ProcessingVideoViewModel
) {
    val mode by viewModel.screenMode.collectAsStateWithLifecycle()

    val originalFileSize by viewModel.originalFileSize.collectAsStateWithLifecycle()
    val compressedFileSize by viewModel.compressedFileSize.collectAsStateWithLifecycle()
    val sizeDiff by viewModel.sizeDiff.collectAsStateWithLifecycle()
    val compressedFileName = remember(params.outputPath) {
        params.outputPath.substringAfterLast("/")
    }

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
            Footer(
                label = when (mode) {
                    ProcessingVideoScreenMode.Compressing -> ("Done")
                    is ProcessingVideoScreenMode.Error -> ("Retry")
                    ProcessingVideoScreenMode.Finished -> ("Done")
                },
                isActive = mode !is ProcessingVideoScreenMode.Compressing,
                onClick = {
                    when (mode) {
                        is ProcessingVideoScreenMode.Error -> viewModel.compress(params)
                        ProcessingVideoScreenMode.Compressing -> Unit
                        ProcessingVideoScreenMode.Finished -> onExit()
                    }
                }
            )
        }
    ) { scaffoldPadding ->
        ContentSurface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(scaffoldPadding),
                verticalArrangement = Arrangement.Center,
            ) {
                when (mode) {
                    ProcessingVideoScreenMode.Compressing -> {
                        CircularProgressIndicator(Modifier.align(Alignment.CenterHorizontally))
                    }

                    is ProcessingVideoScreenMode.Error -> Text(
                        text = "failed"
                    )

                    ProcessingVideoScreenMode.Finished -> {
                        SuccessState(
                            inputFileSize = originalFileSize.formatFileSize(),
                            outputFileSize = compressedFileSize.formatFileSize(),
                            sizeDiff = sizeDiff,
                            outputFileDir = params.outputPath.substringBeforeLast("/"),
                            outputFileName = compressedFileName
                        )
                    }
                }
            }
        }
    }
}