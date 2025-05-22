// Copyright 2025, miuix-kotlin-multiplatform contributors
// SPDX-License-Identifier: Apache-2.0

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
 * @param colors The color scheme for the Miuix components.
 * @param textStyles The text styles for the Miuix components.
 * @param content The content of the Miuix theme.
 */
@Composable
fun MiuixTheme(
    colors: Colors = MiuixTheme.colorScheme,
    textStyles: TextStyles = MiuixTheme.textStyles,
    content: @Composable () -> Unit
) {
    val miuixColors = remember(colors) {
        colors.copy().apply { updateColorsFrom(colors) }
    }
    val miuixTextStyles = remember(textStyles, colors) {
        textStyles.copy().apply { updateColorsFrom(colors) }
    }
    val miuixIndication = remember(colors.onBackground) {
        MiuixIndication(color = colors.onBackground)
    }
    CompositionLocalProvider(
        LocalColors provides miuixColors,
        LocalTextStyles provides miuixTextStyles,
        LocalIndication provides miuixIndication
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