package top.yukonga.miuix.kmp

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import java.awt.KeyboardFocusManager
import java.awt.event.KeyEvent

@Composable
actual fun getRoundedCorner(): Dp = 0.dp

@Composable
actual fun BackHandler(
    dismiss: () -> Unit,
    onDismissRequest: () -> Unit
) {
    KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher { event ->
        if (event.keyCode == KeyEvent.VK_ESCAPE) {
            dismiss()
            onDismissRequest()
        }
        false
    }
}