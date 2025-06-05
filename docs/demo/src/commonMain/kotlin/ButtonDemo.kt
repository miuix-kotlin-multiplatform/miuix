import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import top.yukonga.miuix.kmp.basic.Button
import top.yukonga.miuix.kmp.basic.Icon
import top.yukonga.miuix.kmp.basic.TextButton
import top.yukonga.miuix.kmp.icon.MiuixIcons
import top.yukonga.miuix.kmp.icon.icons.useful.Like

@Composable
fun ButtonDemo() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.linearGradient(listOf(Color(0xFFFF6F61), Color(0xFFFF8A65)))),
        contentAlignment = Alignment.Center
    ) {
        var buttonText by remember { mutableStateOf("Click") }
        var clickCount1 by remember { mutableStateOf(0) }
        var clickCount2 by remember { mutableStateOf(0) }
        Button(
            onClick = {
                clickCount1++
                buttonText = "Click: $clickCount1"
            }
        ) {
            Icon(
                imageVector = MiuixIcons.Useful.Like,
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier.size(24.dp)
            )
        }
        TextButton(
            text = buttonText,
            onClick = {
                clickCount2++
                buttonText = "Click: $clickCount2"
            }
        )
    }
}
