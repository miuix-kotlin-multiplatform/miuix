package top.yukonga.miuix.kmp

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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

@Composable
expect fun getRoundedCorner(): Dp

@Composable
fun MiuixSuperDialog(
    title: String? = null,
    summary: String? = null,
    onDismissRequest: () -> Unit,
    insideMargin: DpSize = DpSize(14.dp, 14.dp),
    content: @Composable () -> Unit
) {
    val bottomCornerRadius = if (getRoundedCorner() != 0.dp) getRoundedCorner() - insideMargin.width else 30.dp

    MiuixBox(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = insideMargin.width)
            .padding(bottom = insideMargin.height)
            .pointerInput(Unit) {
                detectTapGestures(onPress = {
                    onDismissRequest.invoke()
                })
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MiuixTheme.colorScheme.dropdownBackground,
                    shape = RoundedCornerShape(bottomCornerRadius)
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