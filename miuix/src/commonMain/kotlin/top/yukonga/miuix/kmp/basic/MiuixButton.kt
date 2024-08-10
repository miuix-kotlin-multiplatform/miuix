package top.yukonga.miuix.kmp.basic

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
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
import androidx.compose.ui.unit.dp
import top.yukonga.miuix.kmp.theme.MiuixTheme

@Composable
fun MiuixButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    submit: Boolean = false,
    interactionSource: MutableInteractionSource? = remember { MutableInteractionSource() }
) {
    val hapticFeedback = LocalHapticFeedback.current
    val color = getButtonColor(enabled, submit)
    val textColor = getTextColor(enabled, submit)

    Surface(
        onClick = {
            onClick()
            hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
        },
        enabled = enabled,
        modifier = modifier.semantics { role = Role.Button },
        shape = RoundedCornerShape(14.dp),
        color = color,
        interactionSource = interactionSource
    ) {
        Row(
            Modifier.defaultMinSize(
                minWidth = ButtonDefaults.MinWidth,
                minHeight = ButtonDefaults.MinHeight
            ).padding(16.dp, 16.dp),
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
        if (submit) MiuixTheme.colorScheme.primary else MiuixTheme.colorScheme.buttonBg
    } else {
        if (submit) MiuixTheme.colorScheme.disabledBg else MiuixTheme.colorScheme.switchThumb
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