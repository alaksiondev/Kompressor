package io.github.alaksion.kompressor

import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import io.github.alaksion.kompressor.domain.compressor.VideoCompressor
import io.github.alaksion.kompressor.domain.params.Codecs
import io.github.alaksion.kompressor.domain.params.CompressionParams
import io.github.alaksion.kompressor.domain.params.Presets
import io.github.alaksion.kompressor.domain.params.Resolution
import io.github.alaksion.kompressor.domain.process.ProcessHandler
import io.github.alaksion.kompressor.kompressor.generated.resources.Res
import io.github.alaksion.kompressor.kompressor.generated.resources.app_name
import io.github.alaksion.kompressor.kompressor.generated.resources.logo
import io.github.alaksion.kompressor.presentation.navigation.Screens
import io.github.alaksion.kompressor.presentation.navigation.navtypes.CodecsNavType
import io.github.alaksion.kompressor.presentation.navigation.navtypes.PresetsNavType
import io.github.alaksion.kompressor.presentation.navigation.navtypes.ResolutionNavType
import io.github.alaksion.kompressor.presentation.screens.compressing.ProcessingVideoScreen
import io.github.alaksion.kompressor.presentation.screens.compressing.ProcessingVideoViewModel
import io.github.alaksion.kompressor.presentation.screens.compressionparams.CompressionParamsScreen
import io.github.alaksion.kompressor.presentation.screens.compressionparams.CompressionParamsViewModel
import io.github.alaksion.kompressor.presentation.screens.selectfile.SelectFileScreen
import io.github.alaksion.kompressor.presentation.screens.selectfile.SelectFileViewModel
import io.github.alaksion.kompressor.presentation.screens.selectoutput.SelectOutputScreen
import io.github.alaksion.kompressor.presentation.screens.selectoutput.SelectOutputViewModel
import io.github.alaksion.kompressor.presentation.screens.setup.SetupScreen
import io.github.alaksion.kompressor.presentation.screens.setup.SetupViewModel
import io.github.alaksion.kompressor.presentation.theme.KompressorTheme
import kotlinx.coroutines.Dispatchers
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import kotlin.reflect.typeOf


fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        resizable = false,
        title = stringResource(Res.string.app_name),
        state = rememberWindowState(
            size = DpSize(600.dp, 750.dp)
        ),
        icon = painterResource(Res.drawable.logo)
    ) {
        val navigator = rememberNavController()
        KompressorTheme {
            NavHost(
                navController = navigator,
                startDestination = Screens.Setup,
            ) {
                composable<Screens.SelectFile> { backStackEntry ->
                    SelectFileScreen(
                        onNavigateToSelectOutput = {
                            navigator.navigate(Screens.SelectOutput(it))
                        },
                        viewModel = viewModel(viewModelStoreOwner = backStackEntry) {
                            SelectFileViewModel()
                        }
                    )
                }

                composable<Screens.ProcessingFile>(
                    typeMap = mapOf(
                        typeOf<Resolution>() to ResolutionNavType,
                        typeOf<Presets>() to PresetsNavType,
                        typeOf<Codecs>() to CodecsNavType
                    )
                ) { backStackEntry ->
                    val args = backStackEntry.toRoute<Screens.ProcessingFile>()
                    ProcessingVideoScreen(
                        params = CompressionParams(
                            compressionRate = args.compressionRate,
                            codecs = args.codecs,
                            preset = args.preset,
                            resolution = args.resolution,
                            inputPath = args.inputPath,
                            outputPath = args.outputPath,
                        ),
                        onExit = {
                            navigator.navigate(Screens.SelectFile) {
                                popUpTo(Screens.ProcessingFile) {
                                    inclusive = true
                                }
                            }
                        },
                        viewModel = viewModel(viewModelStoreOwner = backStackEntry) {
                            ProcessingVideoViewModel(
                                videoCompressor = VideoCompressor.getInstance(),
                                ioDispatcher = Dispatchers.IO
                            )
                        },
                    )
                }

                composable<Screens.SelectOutput> { backStackEntry ->
                    val route = backStackEntry.toRoute<Screens.SelectOutput>()
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
                        onBack = { navigator.popBackStack() },
                        viewModel = viewModel(viewModelStoreOwner = backStackEntry) {
                            SelectOutputViewModel()
                        }
                    )
                }

                composable<Screens.Params> { backStackEntry ->
                    val route = backStackEntry.toRoute<Screens.Params>()
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
                        onBack = { navigator.popBackStack() },
                        viewModel = viewModel(viewModelStoreOwner = backStackEntry) {
                            CompressionParamsViewModel()
                        }
                    )
                }

                composable<Screens.Setup> { backStackEntry ->
                    SetupScreen(
                        onSetupSuccess = {
                            navigator.navigate(Screens.SelectFile) {
                                popUpTo(Screens.Setup) {
                                    inclusive = true
                                }
                            }
                        },
                        onExit = { exitApplication() },
                        viewModel = viewModel(viewModelStoreOwner = backStackEntry) {
                            SetupViewModel(
                                processHandler = ProcessHandler.instance,
                                dispatcher = Dispatchers.IO
                            )
                        }
                    )
                }
            }
        }
    }
}
