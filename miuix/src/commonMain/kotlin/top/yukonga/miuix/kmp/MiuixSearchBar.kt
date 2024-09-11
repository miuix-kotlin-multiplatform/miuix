package top.yukonga.miuix.kmp

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import top.yukonga.miuix.kmp.basic.MiuixSurface
import top.yukonga.miuix.kmp.basic.MiuixTextField

@Composable
fun MiuixSearchBar(
    modifier: Modifier = Modifier,
    searchValue: MutableState<String>,
    insideMargin: DpSize = DpSize(24.dp, 14.dp),
    expanded: Boolean = false,
    onExpandedChange: (Boolean) -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    MiuixSurface(
        modifier = modifier.zIndex(1f),
    ) {
        Column {
            MiuixTextField(
                insideMargin = insideMargin,
                value = searchValue.value,
                onValueChange = { searchValue.value = it },
                cornerRadius = 50.dp,
                enableBorder = false,
                enableOffset = false,
                maxLines = 1
            )
            AnimatedVisibility(
                visible = expanded
            ) {
                Column(
                    Modifier.padding(top = insideMargin.height)
                ) {
                    content()
                }
            }
        }
    }

    BackHandler(
        dismiss = {
            if (expanded) {
                onExpandedChange(false)
                searchValue.value = ""
            }
        },
        onDismissRequest = {}
    )
}