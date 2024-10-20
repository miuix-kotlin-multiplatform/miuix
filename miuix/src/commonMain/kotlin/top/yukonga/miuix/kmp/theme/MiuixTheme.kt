package top.yukonga.miuix.kmp.theme

import androidx.compose.foundation.LocalIndication
import androidx.compose.material.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember

/**
 * The default theme that provides color and text styles for the Miuix components.
 *
 * @param colorScheme The color scheme for the Miuix components.
 * @param textStyles The text styles for the Miuix components.
 * @param content The content of the Miuix theme.
 */
@Composable
fun MiuixTheme(
    colorScheme: Colors = MiuixTheme.colorScheme,
    textStyles: TextStyles = MiuixTheme.textStyles,
    content: @Composable () -> Unit
) {
    val miuixColors = remember(colorScheme) { colorScheme }
    val miuixTextStyles = remember(colorScheme.onBackground) {
        defaultTextStyles(
            main = textStyles.main.copy(color = colorScheme.onBackground),
            title = textStyles.title.copy(color = colorScheme.onBackground),
            paragraph = textStyles.paragraph.copy(color = colorScheme.onBackground)
        )
    }
    val miuixRipple = remember(colorScheme.onBackground) {
        ripple(color = colorScheme.onBackground)
    }
    CompositionLocalProvider(
        LocalColors provides miuixColors,
        LocalTextStyles provides miuixTextStyles,
        LocalIndication provides miuixRipple,
    ) {
        content()
    }
}

object MiuixTheme {
    val colorScheme: Colors
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current

    val textStyles: TextStyles
        @Composable
        @ReadOnlyComposable
        get() = LocalTextStyles.current
}