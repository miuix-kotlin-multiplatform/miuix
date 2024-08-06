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
    colorScheme: MiuixColors = MiuixTheme.colorScheme,
    textStyles: MiuixTextStyles = MiuixTheme.textStyles,
    content: @Composable () -> Unit
) {
    val miuixTextStyles = remember(colorScheme.onPrimary, colorScheme.onPrimaryContainer) {
        miuixTextStyles(
            main = textStyles.main.copy(color = colorScheme.onPrimary),
            title = textStyles.title.copy(color = colorScheme.onPrimaryContainer),
            paragraph = textStyles.paragraph.copy(color = colorScheme.onPrimary.copy(alpha = 0.7f))
        )
    }
    CompositionLocalProvider(
        LocalMiuixColor provides colorScheme,
        LocalMiuixTextStyles provides miuixTextStyles
    ) {
        content()
    }
}

object MiuixTheme {
    val colorScheme: MiuixColors
        @Composable
        @ReadOnlyComposable
        get() = LocalMiuixColor.current

    val textStyles: MiuixTextStyles
        @Composable
        @ReadOnlyComposable
        get() = LocalMiuixTextStyles.current
}