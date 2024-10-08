package top.yukonga.miuix.kmp.basic

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
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
 * @param modifier The modifier to be applied to the [Button].
 * @param text The text of the [Button].
 * @param onClick The callback when the [Button] is clicked.
 * @param enabled Whether the [Button] is enabled.
 * @param submit Whether the [Button] is a submit button.
 * @param cornerRadius The corner radius of the [Button].
 */
@Composable
fun Button(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true,
    submit: Boolean = false,
    cornerRadius: Dp = 18.dp
) {
    val hapticFeedback = LocalHapticFeedback.current
    val color by rememberUpdatedState(getButtonColor(enabled, submit))
    val textColor by rememberUpdatedState(getTextColor(enabled, submit))

    Surface(
        onClick = {
            onClick()
            hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
        },
        enabled = enabled,
        modifier = modifier.semantics { role = Role.Button },
        shape = SquircleShape(cornerRadius),
        color = color
    ) {
        Row(
            Modifier
                .defaultMinSize(minWidth = 58.dp, minHeight = 40.dp)
                .padding(16.dp, 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
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
        if (submit) MiuixTheme.colorScheme.primary else MiuixTheme.colorScheme.secondaryVariant
    } else {
        if (submit) MiuixTheme.colorScheme.disabledPrimaryButton else MiuixTheme.colorScheme.disabledSecondaryVariant
    }
}

@Composable
private fun getTextColor(enabled: Boolean, submit: Boolean): Color {
    return if (enabled) {
        if (submit) MiuixTheme.colorScheme.onPrimary else MiuixTheme.colorScheme.onSecondaryVariant
    } else {
        if (submit) MiuixTheme.colorScheme.disabledOnPrimaryButton else MiuixTheme.colorScheme.disabledOnSecondaryVariant
    }
}