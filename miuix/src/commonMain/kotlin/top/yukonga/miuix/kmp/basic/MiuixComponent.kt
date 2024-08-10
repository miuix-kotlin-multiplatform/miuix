package top.yukonga.miuix.kmp.basic

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import top.yukonga.miuix.kmp.theme.MiuixTheme

@Composable
fun MiuixBasicComponent(
    modifier: Modifier = Modifier,
    insideMargin: DpSize = DpSize(28.dp, 16.dp),
    title: String? = null,
    summary: String? = null,
    leftAction: @Composable (() -> Unit?)? = null,
    rightActions: @Composable RowScope.() -> Unit = {},
    content: @Composable (() -> Unit?)? = null
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = insideMargin.width, vertical = insideMargin.height),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        leftAction?.let {
            Box(
                modifier = Modifier.padding(end = 16.dp)
            ) {
                it()
            }
        }
        Column(
            modifier = Modifier.weight(1f)
        ) {
            title?.let {
                MiuixText(
                    text = it,
                    fontWeight = FontWeight.Medium
                )
            }
            summary?.let {
                MiuixText(
                    text = it,
                    fontSize = MiuixTheme.textStyles.title.fontSize,
                    color = MiuixTheme.colorScheme.subTextBase
                )
            }
        }
        Box(
            modifier = Modifier.padding(start = 8.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
                content = rightActions
            )
        }
    }
    content?.let {
        it()
    }
}