package io.github.alaksion.kompressor.presentation.screens.selectfile.components.filepicker

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material.icons.outlined.Upload
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import dashedBorder
import io.github.alaksion.kompressor.configs.SupportedFiles
import io.github.alaksion.kompressor.kompressor.generated.resources.Res
import io.github.alaksion.kompressor.kompressor.generated.resources.file_upload
import io.github.alaksion.kompressor.kompressor.generated.resources.select_file_picker_bottom_label
import io.github.alaksion.kompressor.kompressor.generated.resources.select_file_picker_formats
import io.github.alaksion.kompressor.kompressor.generated.resources.select_file_picker_upper_label
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun FilePickerBox(
    modifier: Modifier = Modifier
) {
    val supportedFormats = remember {
        SupportedFiles.entries.joinToString(separator = ",") {
            it.extension
        }
    }

    Column {
        Box(
            modifier = modifier
                .dashedBorder(
                    color = MaterialTheme.colors.onSurface,
                    shape = MaterialTheme.shapes.medium,
                    gapLength = 18.dp,
                    dashLength = 12.dp
                )
                .pointerHoverIcon(icon = PointerIcon.Hand),
            contentAlignment = Alignment.Center
        ) {
            FilePickerText()
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