package top.yukonga.miuix.kmp

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.BlendModeColorFilter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import top.yukonga.miuix.kmp.basic.MiuixBasicComponent
import top.yukonga.miuix.kmp.basic.MiuixText
import top.yukonga.miuix.kmp.miuix.generated.resources.Res
import top.yukonga.miuix.kmp.miuix.generated.resources.ic_arrow_right
import top.yukonga.miuix.kmp.theme.MiuixTheme
import top.yukonga.miuix.kmp.utils.createRipple

@Composable
fun MiuixSuperArrow(
    modifier: Modifier = Modifier,
    title: String,
    summary: String? = null,
    leftAction: @Composable (() -> Unit)? = null,
    rightText: String? = null,
    onClick: () -> Unit,
    insideMargin: DpSize = DpSize(28.dp, 14.dp),
    enabled: Boolean = true
) {
    val interactionSource = remember { MutableInteractionSource() }

    MiuixBasicComponent(
        modifier = modifier.clickable(
            interactionSource = interactionSource,
            indication = createRipple(),
            enabled = enabled
        ) {
            onClick()
        },
        insideMargin = insideMargin,
        title = title,
        summary = summary,
        leftAction = leftAction,
        rightActions = { createRightActions(rightText) }
    )
}

@Composable
private fun createRightActions(rightText: String?) {
    if (rightText != null) {
        MiuixText(
            text = rightText,
            fontSize = 15.sp,
            color = MiuixTheme.colorScheme.subTextBase,
            textAlign = TextAlign.End,
        )
    }
    Image(
        modifier = Modifier
            .size(15.dp)
            .padding(start = 6.dp),
        painter = painterResource(Res.drawable.ic_arrow_right),
        contentDescription = null,
        colorFilter = BlendModeColorFilter(MiuixTheme.colorScheme.subDropdown, BlendMode.SrcIn),
    )
}