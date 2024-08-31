package top.yukonga.miuix.kmp

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import getWindowSize
import top.yukonga.miuix.kmp.basic.MiuixBox
import top.yukonga.miuix.kmp.basic.MiuixText
import top.yukonga.miuix.kmp.theme.MiuixTheme
import top.yukonga.miuix.kmp.utils.MiuixPopupUtil.Companion.dismissDialog
import top.yukonga.miuix.kmp.utils.squircleshape.SquircleShape

/**
 * Returns the rounded corner of the current device.
 */
@Composable
expect fun getRoundedCorner(): Dp

@Composable
expect fun BackHandler(
    dismiss: () -> Unit,
    onDismissRequest: () -> Unit
)

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
    val bottomCornerRadius = rememberUpdatedState(if (getRoundedCorner() != 0.dp) getRoundedCorner() - insideMargin.width else 32.dp)
    val getWindowSize = rememberUpdatedState(getWindowSize())
    val contentAlignment = if (getWindowSize.value.width > getWindowSize.value.height) Alignment.Center else Alignment.BottomCenter

    BackHandler(
        dismiss = { dismissDialog() },
        onDismissRequest = onDismissRequest
    )

    MiuixBox(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = insideMargin.width)
            .padding(bottom = insideMargin.height)
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    dismissDialog()
                    onDismissRequest()
                })
            }
    ) {
        Column(
            modifier = Modifier
                .then(if (contentAlignment != Alignment.Center) Modifier.fillMaxWidth() else Modifier.widthIn(max = 400.dp))
                .pointerInput(Unit) {
                    detectTapGestures { /* Do nothing to consume the click */ }
                }
                .align(contentAlignment)
                .background(
                    color = MiuixTheme.colorScheme.dropdownBackground,
                    shape = SquircleShape(bottomCornerRadius.value)
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
                    textAlign = TextAlign.Center
                )
            }
            content()
        }
    }
}