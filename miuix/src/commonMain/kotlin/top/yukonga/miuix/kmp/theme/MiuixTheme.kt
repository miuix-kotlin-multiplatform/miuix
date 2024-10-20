package top.yukonga.miuix.kmp.theme

import androidx.compose.foundation.LocalIndication
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import top.yukonga.miuix.kmp.utils.MiuixIndication

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
    val miuixColors = remember { colorScheme.copy() }.apply { updateColorsFrom(colorScheme) }
    val miuixTextStyles = remember(colorScheme.onBackground) {
        defaultTextStyles(
            main = textStyles.main.copy(color = colorScheme.onBackground),
            title = textStyles.title.copy(color = colorScheme.onBackground),
            paragraph = textStyles.paragraph.copy(color = colorScheme.onBackground),
            body1 = textStyles.body1.copy(color = colorScheme.onBackground),
            body2 = textStyles.body2.copy(color = colorScheme.onBackground),
            button = textStyles.button.copy(color = colorScheme.onBackground),
            footnote1 = textStyles.footnote1.copy(color = colorScheme.onBackground),
            footnote2 = textStyles.footnote2.copy(color = colorScheme.onBackground),
            headline1 = textStyles.headline1.copy(color = colorScheme.onBackground),
            headline2 = textStyles.headline2.copy(color = colorScheme.onBackground),
            subtitle = textStyles.subtitle.copy(color = colorScheme.onBackground),
            title1 = textStyles.title1.copy(color = colorScheme.onBackground),
            title2 = textStyles.title2.copy(color = colorScheme.onBackground),
            title3 = textStyles.title3.copy(color = colorScheme.onBackground),
            title4 = textStyles.title4.copy(color = colorScheme.onBackground)
        )
    }
    val miuixRipple = remember(colorScheme.onBackground) {
        MiuixIndication(backgroundColor = colorScheme.onBackground)
    }
    CompositionLocalProvider(
        LocalColors provides miuixColors,
        LocalTextStyles provides miuixTextStyles,
        LocalIndication provides miuixRipple
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