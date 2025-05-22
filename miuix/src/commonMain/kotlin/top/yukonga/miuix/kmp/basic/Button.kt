// Copyright 2025, miuix-kotlin-multiplatform contributors
// SPDX-License-Identifier: Apache-2.0

package top.yukonga.miuix.kmp.basic

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import top.yukonga.miuix.kmp.theme.MiuixTheme
import top.yukonga.miuix.kmp.utils.SmoothRoundedCornerShape

/**
 * A [Button] component with Miuix style.
 *
 * @param onClick The callback when the [Button] is clicked.
 * @param modifier The modifier to be applied to the [Button].
 * @param enabled Whether the [Button] is enabled.
 * @param cornerRadius The corner radius of the [Button].
 * @param minWidth The minimum width of the [Button].
 * @param minHeight The minimum height of the [Button].
 * @param colors The [ButtonColors] of the [Button].
 * @param insideMargin The margin inside the [Button].
 * @param content The [Composable] content of the [Button].
 */
@Composable
fun Button(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    cornerRadius: Dp = ButtonDefaults.CornerRadius,
    minWidth: Dp = ButtonDefaults.MinWidth,
    minHeight: Dp = ButtonDefaults.MinHeight,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    insideMargin: PaddingValues = ButtonDefaults.InsideMargin,
    content: @Composable RowScope.() -> Unit
) {
    val shape = remember(cornerRadius) { SmoothRoundedCornerShape(cornerRadius) }
    val currentOnClick by rememberUpdatedState(onClick)
    val surfaceColor by colors.colorState(enabled)

    Surface(
        onClick = currentOnClick,
        enabled = enabled,
        modifier = modifier.semantics { role = Role.Button },
        shape = shape,
        color = surfaceColor
    ) {
        val rowModifier = remember(minWidth, minHeight, insideMargin) {
            Modifier
                .defaultMinSize(minWidth = minWidth, minHeight = minHeight)
                .padding(insideMargin)
        }
        Row(
            rowModifier,
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            content = content
        )
    }
}

/**
 * A [TextButton] component with Miuix style.
 *
 * @param text The text of the [TextButton].
 * @param onClick The callback when the [TextButton] is clicked.
 * @param modifier The modifier to be applied to the [TextButton].
 * @param enabled Whether the [TextButton] is enabled.
 * @param colors The [TextButtonColors] of the [TextButton].
 * @param cornerRadius The corner radius of the [TextButton].
 * @param minWidth The minimum width of the [TextButton].
 * @param minHeight The minimum height of the [TextButton].
 * @param insideMargin The margin inside the [TextButton].
 */
@Composable
fun TextButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: TextButtonColors = ButtonDefaults.textButtonColors(),
    cornerRadius: Dp = ButtonDefaults.CornerRadius,
    minWidth: Dp = ButtonDefaults.MinWidth,
    minHeight: Dp = ButtonDefaults.MinHeight,
    insideMargin: PaddingValues = ButtonDefaults.InsideMargin
) {
    val currentOnClick by rememberUpdatedState(onClick)
    val shape = remember(cornerRadius) { SmoothRoundedCornerShape(cornerRadius) }
    val surfaceColor by colors.colorState(enabled)
    val textColor by colors.textColorState(enabled)

    Surface(
        onClick = currentOnClick,
        enabled = enabled,
        modifier = modifier.semantics { role = Role.Button },
        shape = shape,
        color = surfaceColor
    ) {
        val rowModifier = remember(minWidth, minHeight, insideMargin) {
            Modifier
                .defaultMinSize(minWidth = minWidth, minHeight = minHeight)
                .padding(insideMargin)
        }
        Row(
            rowModifier,
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            content = {
                Text(
                    text = text,
                    color = textColor,
                    style = MiuixTheme.textStyles.button
                )
            }
        )
    }
}

object ButtonDefaults {

    /**
     * The default min width applied for all buttons. Note that you can override it by applying
     * Modifier.widthIn directly on the button composable.
     */
    val MinWidth = 58.dp

    /**
     * The default min height applied for all buttons. Note that you can override it by applying
     * Modifier.heightIn directly on the button composable.
     */
    val MinHeight = 40.dp

    /**
     * The default corner radius applied for all buttons.
     */
    val CornerRadius = 16.dp

    /**
     * The default inside margin applied for all buttons.
     */
    val InsideMargin = PaddingValues(16.dp)

    /**
     * The default [ButtonColors] for all buttons.
     */
    @Composable
    fun buttonColors(
        color: Color = MiuixTheme.colorScheme.secondaryVariant,
        disabledColor: Color = MiuixTheme.colorScheme.disabledSecondaryVariant
    ): ButtonColors {
        return ButtonColors(
            color = color,
            disabledColor = disabledColor
        )
    }

    /**
     * The [ButtonColors] for primary buttons.
     */
    @Composable
    fun buttonColorsPrimary() = ButtonColors(
        color = MiuixTheme.colorScheme.primary,
        disabledColor = MiuixTheme.colorScheme.disabledPrimaryButton
    )

    /**
     * The default [TextButtonColors] for all text buttons.
     */
    @Composable
    fun textButtonColors(
        color: Color = MiuixTheme.colorScheme.secondaryVariant,
        disabledColor: Color = MiuixTheme.colorScheme.disabledSecondaryVariant,
        textColor: Color = MiuixTheme.colorScheme.onSecondaryVariant,
        disabledTextColor: Color = MiuixTheme.colorScheme.disabledOnSecondaryVariant
    ): TextButtonColors = TextButtonColors(
        color = color,
        disabledColor = disabledColor,
        textColor = textColor,
        disabledTextColor = disabledTextColor
    )

    /**
     * The [TextButtonColors] for primary text buttons.
     */
    @Composable
    fun textButtonColorsPrimary() = TextButtonColors(
        color = MiuixTheme.colorScheme.primary,
        disabledColor = MiuixTheme.colorScheme.disabledPrimaryButton,
        textColor = MiuixTheme.colorScheme.onPrimary,
        disabledTextColor = MiuixTheme.colorScheme.disabledOnPrimaryButton
    )
}

@Immutable
class ButtonColors(
    private val color: Color,
    private val disabledColor: Color
) {
    @Stable
    internal fun color(enabled: Boolean): Color = if (enabled) color else disabledColor

    @Composable
    internal fun colorState(enabled: Boolean) = rememberUpdatedState(if (enabled) color else disabledColor)
}

@Immutable
class TextButtonColors(
    private val color: Color,
    private val disabledColor: Color,
    private val textColor: Color,
    private val disabledTextColor: Color
) {
    @Stable
    internal fun color(enabled: Boolean): Color = if (enabled) color else disabledColor

    @Stable
    internal fun textColor(enabled: Boolean): Color = if (enabled) textColor else disabledTextColor

    @Composable
    internal fun colorState(enabled: Boolean) = rememberUpdatedState(if (enabled) color else disabledColor)

    @Composable
    internal fun textColorState(enabled: Boolean) = rememberUpdatedState(if (enabled) textColor else disabledTextColor)
}