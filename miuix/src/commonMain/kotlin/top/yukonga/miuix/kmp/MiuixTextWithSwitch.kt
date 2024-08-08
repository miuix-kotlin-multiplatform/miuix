package top.yukonga.miuix.kmp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import top.yukonga.miuix.kmp.theme.MiuixTheme

@Composable
fun MiuixTextWithSwitch(
    text: String,
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
    insideMargin: DpSize = DpSize(24.dp, 0.dp),
    enabled: Boolean = true,
) {
    var isChecked by remember { mutableStateOf(checked) }
    val interactionSource = remember { MutableInteractionSource() }
    val ripple = ripple(color = MiuixTheme.colorScheme.onBackground.copy(alpha = 0.8f))

    MiuixBox(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                enabled = enabled,
                interactionSource = interactionSource,
                indication = ripple,
                onClick = {
                    isChecked = !isChecked
                    onCheckedChange?.invoke(isChecked)
                }
            )
    ) {
        MiuixBox(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = insideMargin.width, vertical = insideMargin.height)
        ) {
            Row(
                modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                MiuixText(
                    text = text,
                    style = MiuixTheme.textStyles.semi,
                    modifier = Modifier.padding(end = 8.dp)
                )
                MiuixSwitch(
                    checked = checked,
                    onCheckedChange = onCheckedChange,
                    enabled = enabled
                )
            }
        }
    }
}