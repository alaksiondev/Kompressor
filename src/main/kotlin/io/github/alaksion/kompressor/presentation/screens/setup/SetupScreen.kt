package io.github.alaksion.kompressor.presentation.screens.setup

import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.github.alaksion.kompressor.presentation.components.ContentSurface
import io.github.alaksion.kompressor.presentation.components.Footer
import io.github.alaksion.kompressor.presentation.screens.setup.components.MissingDependencyState
import io.github.alaksion.kompressor.presentation.screens.setup.components.SetupLoadingState

@Composable
internal fun SetupScreen(
    onSetupSuccess: () -> Unit,
    onExit: () -> Unit,
    viewModel: SetupViewModel
) {
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val dependencyState by viewModel.dependenciesEnabled.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) { viewModel.checkDependencies() }

    LaunchedEffect(dependencyState) {
        if (dependencyState is DependencyState.Available) {
            onSetupSuccess()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Kompressor") }
            )
        },
        bottomBar = {
            if (isLoading.not() && dependencyState !is DependencyState.Available) {
                Footer(
                    label = "Exit",
                    isActive = true,
                    onClick = onExit
                )
            }
        }
    ) { scaffoldPadding ->
        ContentSurface(modifier = Modifier.padding(scaffoldPadding)) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (isLoading) {
                    SetupLoadingState()
                } else {
                    when (val dependency = dependencyState) {
                        DependencyState.Available -> Unit

                        is DependencyState.Partial -> MissingDependencyState(
                            modifier = Modifier.fillMaxWidth(),
                            pythonEnabled = dependency.pythonEnabled,
                            ffmpegEnabled = dependency.ffmpegEnabled,
                        )
                    }
                }
            }
        }
    }
}