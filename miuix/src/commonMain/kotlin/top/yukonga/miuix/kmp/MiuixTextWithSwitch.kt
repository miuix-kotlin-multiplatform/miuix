package top.yukonga.miuix.kmp

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import top.yukonga.miuix.kmp.ui.MiuixTheme

@Composable
fun MiuixTextWithSwitch(
    text: String,
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource? = null,
) {
    var isPressed by remember { mutableStateOf(false) }
    var isChecked by remember { mutableStateOf(checked) }
    val defaultBackgroundColor: Color = MiuixTheme.colorScheme.background
    val pressBackgroundColor: Color = MiuixTheme.colorScheme.pressBackground
    val backgroundColor by animateColorAsState(if (isPressed) pressBackgroundColor else defaultBackgroundColor)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        isChecked = !isChecked
                        onCheckedChange?.invoke(isChecked)
                        isPressed = false
                    },
                    onPress = {
                        coroutineScope {
                            launch {
                                isPressed = true
                            }
                        }
                    }
                )
            },
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Row(
                modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                MiuixText(
                    text = text,
                    modifier = Modifier.padding(end = 8.dp)
                )
                MiuixSwitch(
                    checked = checked,
                    onCheckedChange = onCheckedChange,
                    enabled = enabled,
                    interactionSource = interactionSource
                )
            }
        }
    }
}