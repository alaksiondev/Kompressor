package io.github.alaksion.kompressor.presentation.screens.compressing.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.FolderOpen
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.github.alaksion.kompressor.kompressor.generated.resources.*
import io.github.alaksion.kompressor.presentation.components.LabeledCard
import org.jetbrains.compose.resources.stringResource
import java.awt.Desktop
import java.io.File

@Composable
internal fun SuccessState(
    inputFileSize: String,
    outputFileSize: String,
    outputFileDir: String,
    outputFileName: String,
    sizeDiff: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Icon(
            imageVector = Icons.Outlined.CheckCircle,
            contentDescription = null,
            tint = MaterialTheme.colors.primary,
            modifier = Modifier.size(98.dp)
        )

        Text(
            text = stringResource(Res.string.compression_success_title),
            style = MaterialTheme.typography.h4,
            textAlign = TextAlign.Center
        )

        LabeledCard(
            label = stringResource(Res.string.compression_success_filename)
        ) {
            Text(
                text = outputFileName
            )
        }

        Spacer(Modifier.height(32.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            LabeledCard(
                modifier = Modifier.weight(1f),
                label = stringResource(Res.string.compression_success_original_size)
            ) {

                Text(
                    text = inputFileSize,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }

            LabeledCard(
                modifier = Modifier.weight(1f),
                label = stringResource(Res.string.compression_success_compressed_size)
            ) {
                Text(
                    text = outputFileSize,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }

            LabeledCard(
                modifier = Modifier.weight(1f),
                label = stringResource(Res.string.compression_success_compression_rate)
            ) {
                Text(
                    text = sizeDiff,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        }


        TextButton(
            onClick = {
                Desktop.getDesktop().open(File(outputFileDir))
            }
        ) {
            Text(
                text = stringResource(Res.string.compression_success_cta),
            )
            Icon(
                imageVector = Icons.Outlined.FolderOpen,
                contentDescription = null
            )
        }
    }
}
