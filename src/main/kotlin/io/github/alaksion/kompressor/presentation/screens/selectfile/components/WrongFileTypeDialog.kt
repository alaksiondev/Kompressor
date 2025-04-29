package io.github.alaksion.kompressor.presentation.screens.selectfile.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import io.github.alaksion.kompressor.kompressor.generated.resources.Res
import io.github.alaksion.kompressor.kompressor.generated.resources.select_file_invalid_type_dialog_button
import io.github.alaksion.kompressor.kompressor.generated.resources.select_file_invalid_type_dialog_description
import io.github.alaksion.kompressor.kompressor.generated.resources.select_file_invalid_type_dialog_title
import io.github.alaksion.kompressor.kompressor.generated.resources.select_file_picker_formats
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun WrongFileTypeDialog(
    visible: Boolean,
    fileType: String,
    validFormats: String,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    if (visible) {
        Dialog(
            onDismissRequest = onDismiss
        ) {
            Card(modifier = modifier) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ErrorOutline,
                        contentDescription = null,
                        tint = MaterialTheme.colors.error,
                        modifier = Modifier.size(64.dp).align(Alignment.CenterHorizontally),
                    )
                    Text(
                        text = stringResource(Res.string.select_file_invalid_type_dialog_title),
                        style = MaterialTheme.typography.h6
                    )

                    Text(
                        text = stringResource(
                            resource = Res.string.select_file_invalid_type_dialog_description,
                            fileType
                        ),
                    )

                    Text(
                        text = stringResource(Res.string.select_file_picker_formats, validFormats)
                    )
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = onDismiss,
                        shape = MaterialTheme.shapes.large
                    ) {
                        Text(stringResource(Res.string.select_file_invalid_type_dialog_button))
                    }
                }
            }
        }
    }
}