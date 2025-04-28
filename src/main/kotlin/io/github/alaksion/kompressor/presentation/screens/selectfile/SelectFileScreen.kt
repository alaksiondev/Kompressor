package io.github.alaksion.kompressor.presentation.screens.selectfile

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposeWindow
import androidx.compose.ui.unit.dp
import io.github.alaksion.kompressor.kompressor.generated.resources.Res
import io.github.alaksion.kompressor.kompressor.generated.resources.select_file_continue_cta
import io.github.alaksion.kompressor.kompressor.generated.resources.select_file_title
import io.github.alaksion.kompressor.presentation.screens.selectfile.components.filepicker.FilePickerBox
import io.github.alaksion.kompressor.presentation.screens.selectfile.components.filepicker.openFileBrowser
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun SelectFileScreen() {
    val selectedFilePath = remember { mutableStateOf("") }
    val window = remember { ComposeWindow() }

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
            FilePickerBox(
                Modifier
                    .fillMaxWidth(0.80f)
                    .height(300.dp)
                    .clickable(
                        enabled = true,
                        onClick = {
                            val file = openFileBrowser(window)
                            file?.let {
                                selectedFilePath.value = it.absolutePath
                            }
                        }
                    )
            )

            Spacer(Modifier.height(16.dp))

            Button(
                modifier = Modifier
                    .fillMaxWidth(0.80f)
                    .height(64.dp),
                onClick = {},
                enabled = selectedFilePath.value.isNotBlank(),
                shape = MaterialTheme.shapes.large
            ) {
                Text(
                    text = stringResource(Res.string.select_file_continue_cta),
                )
            }
        }
    }
}