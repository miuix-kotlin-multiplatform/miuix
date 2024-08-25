package top.yukonga.miuix.kmp.basic

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import top.yukonga.miuix.kmp.theme.MiuixTheme
import top.yukonga.miuix.kmp.utils.squircleshape.SquircleShape

/**
 * A button component with Miuix style.
 *
 * @param text The text of the [MiuixButton].
 * @param onClick The callback when the [MiuixButton] is clicked.
 * @param modifier The modifier to be applied to the [MiuixButton].
 * @param enabled Whether the [MiuixButton] is enabled.
 * @param submit Whether the [MiuixButton] is a submit button.
 * @param cornerRadius The corner radius of the [MiuixButton].
 * @param interactionSource The interaction source to be applied to the [MiuixButton].
 */
@Composable
fun MiuixButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    submit: Boolean = false,
    cornerRadius: Dp = 18.dp,
    interactionSource: MutableInteractionSource? = remember { MutableInteractionSource() }
) {
    val hapticFeedback = LocalHapticFeedback.current
    val color = getButtonColor(enabled, submit)
    val textColor = getTextColor(enabled, submit)
    MiuixSurface(
        onClick = {
            onClick()
            hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
        },
        enabled = enabled,
        modifier = modifier.semantics { role = Role.Button },
        shape = SquircleShape(cornerRadius),
        color = color,
        interactionSource = interactionSource
    ) {
        Row(
            Modifier
                .defaultMinSize(minWidth = 58.dp, minHeight = 40.dp)
                .padding(16.dp, 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            MiuixText(
                text = text,
                color = textColor,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
private fun getButtonColor(enabled: Boolean, submit: Boolean): Color {
    return if (enabled) {
        if (submit) MiuixTheme.colorScheme.primary else MiuixTheme.colorScheme.secondary
    } else {
        if (submit) MiuixTheme.colorScheme.submitDisabledBg else MiuixTheme.colorScheme.disabledBg
    }
}

@Composable
private fun getTextColor(enabled: Boolean, submit: Boolean): Color {
    return if (enabled) {
        if (submit) Color.White else MiuixTheme.colorScheme.onBackground
    } else {
        if (submit) MiuixTheme.colorScheme.submitButtonDisabledText else MiuixTheme.colorScheme.buttonDisableText
    }
}