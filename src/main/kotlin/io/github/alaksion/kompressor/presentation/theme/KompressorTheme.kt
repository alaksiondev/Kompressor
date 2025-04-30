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
    primary = Color(0xFF1F1F1F),           // Preto Uber moderno
    primaryVariant = Color(0xFF000000),     // Preto puro
    secondary = Color(0xFFFF6D00),          // Laranja Kotlin vibrante
    secondaryVariant = Color(0xFFDD4B00),   // Laranja escuro
    background = Color(0xFFFFFFFF),         // Branco limpo
    surface = Color(0xFFF5F5F5),            // Cinza claro suave
    error = Color(0xFFB00020),              // Padrão Material para erro

    onPrimary = Color.White,                // Texto branco sobre preto
    onSecondary = Color.Black,              // Texto preto sobre laranja
    onBackground = Color.Black,             // Texto padrão sobre fundo branco
    onSurface = Color.Black,                // Texto sobre superfícies cinza
    onError = Color.White                   // Texto branco sobre fundo de erro
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