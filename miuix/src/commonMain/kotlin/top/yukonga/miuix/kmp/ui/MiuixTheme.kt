package top.yukonga.miuix.kmp.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf

private val LocalMiuixColor = staticCompositionLocalOf { lightColorScheme() }
private val LocalMiuixTextStyles = staticCompositionLocalOf { miuixTextStyles() }

@Composable
fun MiuixTheme(
    colors: MiuixColors = MiuixTheme.colors,
    textStyles: MiuixTextStyles = MiuixTheme.textStyles,
    content: @Composable () -> Unit
) {
    val miuixTextStyles = remember(colors.onPrimary, colors.onPrimaryContainer) {
        miuixTextStyles(
            main = textStyles.main.copy(color = colors.onPrimary),
            title = textStyles.title.copy(color = colors.onPrimaryContainer),
            paragraph = textStyles.paragraph.copy(color = colors.onPrimary.copy(alpha = 0.7f))
        )
    }
    CompositionLocalProvider(
        LocalMiuixColor provides colors,
        LocalMiuixTextStyles provides miuixTextStyles
    ) {
        content()
    }
}

object MiuixTheme {
    val colors: MiuixColors
        @Composable
        @ReadOnlyComposable
        get() = LocalMiuixColor.current

    val textStyles: MiuixTextStyles
        @Composable
        @ReadOnlyComposable
        get() = LocalMiuixTextStyles.current
}