package io.github.alaksion.kompressor.presentation.screens.selectfile.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import io.github.alaksion.kompressor.kompressor.generated.resources.*
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
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Info,
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

                    Row(
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.CheckCircle,
                            contentDescription = null,
                            tint = MaterialTheme.colors.primary,
                        )

                        Text(
                            text = stringResource(Res.string.select_file_picker_formats, validFormats)
                        )
                    }

                    Text(
                        text = stringResource(resource = Res.string.select_file_invalid_type_dialog_retry),
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