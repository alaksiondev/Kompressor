package io.github.alaksion.kompressor

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.github.alaksion.kompressor.presentation.navigation.Screens
import io.github.alaksion.kompressor.presentation.screens.compressing.ProcessingVideoScreen
import io.github.alaksion.kompressor.presentation.screens.selectfile.SelectFileScreen
import io.github.alaksion.kompressor.presentation.theme.KompressorTheme


fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        resizable = false
    ) {
        val navigator = rememberNavController()
        KompressorTheme {
            NavHost(
                navController = navigator,
                startDestination = Screens.SelectFile,
            ) {
                composable<Screens.SelectFile> {
                    SelectFileScreen()
                }

                composable<Screens.ProcessingFile> {
                    ProcessingVideoScreen()
                }
            }
        }
    }
}
