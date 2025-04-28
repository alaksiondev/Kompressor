package io.github.alaksion.kompressor.presentation.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.Typography
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import io.github.alaksion.kompressor.kompressor.generated.resources.*
import org.jetbrains.compose.resources.Font

private val colors = lightColors(
    primary = Color(0xFF6200EE),       // Roxo Material Design padrão
    primaryVariant = Color(0xFF3700B3),
    secondary = Color(0xFF03DAC6),
    secondaryVariant = Color(0xFF018786),
    background = Color(0xFFFAFAFA),    // Sua cor de fundo desejada
    surface = Color(0xFFFFFFFF),
    error = Color(0xFFB00020),
    onPrimary = Color(0xFFFFFFFF),
    onSecondary = Color(0xFF000000),
    onBackground = Color(0xFF000000),
    onSurface = Color(0xFF000000),
    onError = Color(0xFFFFFFFF)
)

private val fontFamily: FontFamily
    @Composable
    get() = FontFamily(
        Font(
            resource = Res.font.inter_black,
            weight = FontWeight.Black,
        ),
        Font(
            resource = Res.font.inter_bold,
            weight = FontWeight.Bold,
        ),
        Font(
            resource = Res.font.inter_extrabold,
            weight = FontWeight.ExtraBold,
        ),
        Font(
            resource = Res.font.inter_extralight,
            weight = FontWeight.ExtraLight,
        ),
        Font(
            resource = Res.font.inter_light,
            weight = FontWeight.Light,
        ),
        Font(
            resource = Res.font.inter_medium,
            weight = FontWeight.Medium,
        ),
        Font(
            resource = Res.font.inter_regular,
            weight = FontWeight.Normal,
        ),
        Font(
            resource = Res.font.inter_semibold,
            weight = FontWeight.SemiBold,
        ),
        Font(
            resource = Res.font.inter_thin,
            weight = FontWeight.Thin,
        ),
    )

private val shapes = Shapes(
    small = RoundedCornerShape(2.dp),
    medium = RoundedCornerShape(4.dp),
    large = RoundedCornerShape(8.dp)
)

@Composable
internal fun KompressorTheme(
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colors = colors,
        typography = Typography(
            defaultFontFamily = fontFamily
        ),
        shapes = shapes
    ) {
        content()
    }
}