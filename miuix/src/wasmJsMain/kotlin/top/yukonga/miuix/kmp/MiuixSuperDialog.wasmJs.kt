package top.yukonga.miuix.kmp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.browser.window
import org.w3c.dom.events.KeyboardEvent

@Composable
actual fun getRoundedCorner(): Dp = 0.dp

@Composable
actual fun BackHandler(
    dismiss: () -> Unit,
    onDismissRequest: () -> Unit
) {
    LaunchedEffect(Unit) {
        window.addEventListener("keydown") { event ->
            if ((event as KeyboardEvent).key == "Escape") {
                dismiss()
                onDismissRequest()
            }
        }
    }
}