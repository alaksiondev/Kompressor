package io.github.alaksion.kompressor.presentation.screens.setup.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.OpenInBrowser
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.github.alaksion.kompressor.kompressor.generated.resources.*
import io.github.alaksion.kompressor.presentation.utils.openWebpage
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import java.net.URI

private val CARD_HEIGHT = 480.dp
const val PYTHON_WEB_PAGE = "https://www.python.org/downloads/"
const val FFMPEG_WEB_PAGE = "https://www.ffmpeg.org/download.html"

@Composable
internal fun MissingDependencyState(
    pythonEnabled: Boolean,
    ffmpegEnabled: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = stringResource(Res.string.setup_failed_title),
            style = MaterialTheme.typography.h5,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            DependencyCard(
                resource = painterResource(Res.drawable.ic_python),
                name = stringResource(Res.string.python),
                isEnabled = pythonEnabled,
                modifier = Modifier.weight(1f).height(CARD_HEIGHT),
                description = stringResource(Res.string.python_description),
                webPage = PYTHON_WEB_PAGE
            )

            DependencyCard(
                resource = painterResource(Res.drawable.ic_ffmpeg),
                name = stringResource(Res.string.ffmpeg),
                isEnabled = ffmpegEnabled,
                modifier = Modifier.weight(1f).height(CARD_HEIGHT),
                description = stringResource(Res.string.ffmpeg_description),
                webPage = FFMPEG_WEB_PAGE
            )
        }
    }
}

@Composable
private fun DependencyCard(
    resource: Painter,
    name: String,
    description: String,
    webPage: String,
    isEnabled: Boolean,
    modifier: Modifier = Modifier,
) {
    val icon = remember(isEnabled) {
        if (isEnabled) {
            Icons.Filled.CheckCircle
        } else {
            Icons.Filled.Block
        }
    }
    val colors = MaterialTheme.colors

    val iconTint = remember {
        if (isEnabled) colors.primary else colors.error
    }

    val enabledText = if (isEnabled) {
        stringResource(Res.string.enabled)
    } else {
        stringResource(Res.string.disabled)
    }

    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.large,
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = resource,
                contentDescription = null,
                modifier = Modifier
                    .size(128.dp)
            )
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconTint,
                modifier = Modifier.size(64.dp)
            )
            Text(
                text = "$name is $enabledText",
                textAlign = TextAlign.Center
            )
            Text(
                text = description,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.weight(1f))
            TextButton(
                onClick = { openWebpage(URI.create(webPage)) }
            ) {
                Text(
                    text = stringResource(Res.string.download),
                    color = MaterialTheme.colors.primary
                )
                Spacer(Modifier.width(16.dp))
                Icon(
                    imageVector = Icons.Outlined.OpenInBrowser,
                    contentDescription = null
                )
            }
        }
    }
}