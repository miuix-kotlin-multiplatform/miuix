package top.yukonga.miuix.kmp.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf

private val LocalMiuixColor = staticCompositionLocalOf { lightColorScheme() }
private val LocalMiuixTextStyles = staticCompositionLocalOf { miuixTextStyles() }

/**
 * The default theme that provides color and text styles for the Miuix components.
 *
 * @param colorScheme The color scheme for the Miuix components.
 * @param textStyles The text styles for the Miuix components.
 * @param content The content of the Miuix theme.
 */
@Composable
fun MiuixTheme(
    colorScheme: MiuixColor = MiuixTheme.colorScheme,
    textStyles: MiuixTextStyles = MiuixTheme.textStyles,
    content: @Composable () -> Unit
) {
    val miuixTextStyles = remember(colorScheme.onBackground) {
        miuixTextStyles(
            main = textStyles.main.copy(color = colorScheme.onBackground),
            title = textStyles.title.copy(color = colorScheme.onBackground),
            paragraph = textStyles.paragraph.copy(color = colorScheme.onBackground)
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
    val colorScheme: MiuixColor
        @Composable
        @ReadOnlyComposable
        get() = LocalMiuixColor.current

    val textStyles: MiuixTextStyles
        @Composable
        @ReadOnlyComposable
        get() = LocalMiuixTextStyles.current
}