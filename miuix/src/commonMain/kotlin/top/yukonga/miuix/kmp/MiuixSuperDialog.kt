package top.yukonga.miuix.kmp

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import top.yukonga.miuix.kmp.basic.MiuixBox
import top.yukonga.miuix.kmp.basic.MiuixText
import top.yukonga.miuix.kmp.theme.MiuixTheme
import top.yukonga.miuix.kmp.utils.squircleshape.SquircleShape

/**
 * Returns the rounded corner of the current device.
 */
@Composable
expect fun getRoundedCorner(): Dp

/**
 * A dialog with a title, a summary, and a content.
 *
 * @param title The title of the [MiuixSuperDialog].
 * @param summary The summary of the [MiuixSuperDialog].
 * @param onDismissRequest The callback when the [MiuixSuperDialog] is dismissed.
 * @param insideMargin The margin inside the [MiuixSuperDialog].
 * @param content The [Composable] content of the [MiuixSuperDialog].
 */
@Composable
fun MiuixSuperDialog(
    title: String? = null,
    summary: String? = null,
    onDismissRequest: () -> Unit,
    insideMargin: DpSize = DpSize(14.dp, 14.dp),
    content: @Composable () -> Unit
) {
    val bottomCornerRadius = if (getRoundedCorner() != 0.dp) getRoundedCorner() - insideMargin.width else 32.dp

    MiuixBox(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = insideMargin.width)
            .padding(bottom = insideMargin.height)
            .pointerInput(Unit) {
                detectTapGestures(onTap = { onDismissRequest() })
            }
    ) {
        Column(
            modifier = Modifier
                .pointerInput(Unit) {
                    detectTapGestures { /* Do nothing to consume the click */ }
                }
                .fillMaxWidth()
                .background(
                    color = MiuixTheme.colorScheme.dropdownBackground,
                    shape = SquircleShape(bottomCornerRadius)
                )
                .padding(24.dp),
        ) {
            title?.let {
                MiuixText(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp),
                    text = it,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center
                )
            }
            summary?.let {
                MiuixText(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp),
                    text = it,
                )
            }
            content()
        }
    }
}