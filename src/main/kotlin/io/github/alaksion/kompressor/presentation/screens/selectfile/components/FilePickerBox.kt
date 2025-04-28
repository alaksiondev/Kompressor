package io.github.alaksion.kompressor.presentation.screens.selectfile.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.VideoFile
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import dashedBorder
import io.github.alaksion.kompressor.configs.SupportedFiles
import io.github.alaksion.kompressor.kompressor.generated.resources.*
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

internal sealed interface FilePickerBoxState {
    data object Unselected : FilePickerBoxState
    data class Selected(
        val fileName: String,
        val size: String,
        val filePath: String,
    ) : FilePickerBoxState
}

@Composable
internal fun FilePickerBox(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    onCancelFile: () -> Unit,
    state: FilePickerBoxState
) {
    val supportedFormats = remember {
        SupportedFiles.entries.joinToString(separator = ",") {
            it.extension
        }
    }

    Column {
        Column(
            modifier = modifier
                .clickable(
                    enabled = state is FilePickerBoxState.Unselected,
                    onClick = onClick
                )
                .dashedBorder(
                    color = MaterialTheme.colors.onSurface,
                    shape = MaterialTheme.shapes.medium,
                    gapLength = 18.dp,
                    dashLength = 12.dp
                )
                .pointerHoverIcon(icon = PointerIcon.Hand),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AnimatedVisibility(
                visible = state is FilePickerBoxState.Unselected,
            ) {
                FilePickerText()
            }

            AnimatedVisibility(
                visible = state is FilePickerBoxState.Selected
            ) {
                val internalState = remember { state as FilePickerBoxState.Selected }
                SelectedFileDetails(
                    name = internalState.fileName,
                    size = internalState.size,
                    modifier = Modifier.fillMaxWidth(0.5f),
                    onCancelFile = onCancelFile
                )
            }
        }
        Spacer(Modifier.height(16.dp))
        Text(
            text = stringResource(Res.string.select_file_picker_formats, supportedFormats),
            style = MaterialTheme.typography.caption
        )
    }
}


@Composable
private fun FilePickerText(
    modifier: Modifier = Modifier
) {
    val upperText = stringResource(Res.string.select_file_picker_upper_label)
    val bottomText = stringResource(Res.string.select_file_picker_bottom_label)

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Icon(
            painter = painterResource(Res.drawable.file_upload),
            contentDescription = null,
            modifier = Modifier.size(48.dp),
            tint = Color.Unspecified
        )

        Spacer(Modifier.height(16.dp))

        Text(
            text = buildAnnotatedString {
                withStyle(MaterialTheme.typography.h6.toSpanStyle()) {
                    append(upperText)
                }
                append("\n")
                withStyle(MaterialTheme.typography.body1.toSpanStyle()) {
                    append(bottomText)
                }
            },
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun SelectedFileDetails(
    name: String,
    size: String,
    onCancelFile: () -> Unit,
    modifier: Modifier = Modifier
) {

    Card(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(
                imageVector = Icons.Default.VideoFile,
                contentDescription = null
            )

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    text = size,
                    style = MaterialTheme.typography.caption,
                )
            }

            IconButton(
                onClick = onCancelFile
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = null,
                )
            }
        }
    }
}