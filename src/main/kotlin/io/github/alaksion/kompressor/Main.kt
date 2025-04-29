package io.github.alaksion.kompressor

import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import io.github.alaksion.kompressor.kompressor.generated.resources.Res
import io.github.alaksion.kompressor.kompressor.generated.resources.app_name
import io.github.alaksion.kompressor.presentation.navigation.Screens
import io.github.alaksion.kompressor.presentation.screens.compressing.ProcessingVideoScreen
import io.github.alaksion.kompressor.presentation.screens.compressionparams.CompressionParamsScreen
import io.github.alaksion.kompressor.presentation.screens.selectfile.SelectFileScreen
import io.github.alaksion.kompressor.presentation.screens.selectoutput.SelectOutputScreen
import io.github.alaksion.kompressor.presentation.theme.KompressorTheme
import org.jetbrains.compose.resources.stringResource


fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        resizable = false,
        title = stringResource(Res.string.app_name),
        state = rememberWindowState(
            size = DpSize(600.dp, 750.dp)
        )
    ) {
        val navigator = rememberNavController()
        KompressorTheme {
            NavHost(
                navController = navigator,
                startDestination = Screens.SelectFile,
            ) {
                composable<Screens.SelectFile> {
                    SelectFileScreen(
                        onNavigateToSelectOutput = {
                            navigator.navigate(Screens.SelectOutput(it))
                        }
                    )
                }

                composable<Screens.ProcessingFile> {
                    val args = it.toRoute<Screens.ProcessingFile>()
                    ProcessingVideoScreen(
                        compressionRate = args.compressionRate,
                        codecs = args.codecs,
                        preset = args.preset,
                        resolution = args.resolution,
                        inputPath = args.inputPath,
                        outputPath = args.outputPath
                    )
                }

                composable<Screens.SelectOutput> {
                    val route = it.toRoute<Screens.SelectOutput>()
                    SelectOutputScreen(
                        inputPath = route.inputPath,
                        onExpressClick = { inputPath, outputPath ->
                            navigator.navigate(
                                route = Screens.ProcessingFile(
                                    inputPath = inputPath,
                                    outputPath = outputPath
                                )
                            )
                        },
                        onCustomClick = { inputPath, outputPath ->
                            navigator.navigate(
                                Screens.Params(
                                    outputPath = outputPath,
                                    inputPath = inputPath
                                )
                            )
                        },
                        onBack = { navigator.popBackStack() }
                    )
                }

                composable<Screens.Params> {
                    val route = it.toRoute<Screens.Params>()
                    CompressionParamsScreen(
                        inputPath = route.inputPath,
                        outputPath = route.outputPath,
                        onContinue = { params ->
                            navigator.navigate(
                                route = Screens.ProcessingFile(
                                    inputPath = params.inputPath,
                                    outputPath = params.outputPath,
                                    compressionRate = params.compressionRate,
                                    resolution = params.resolution,
                                    codecs = params.codecs,
                                    preset = params.preset,
                                )
                            )
                        },
                        onBack = { navigator.popBackStack() }
                    )
                }
            }
        }
    }
}
