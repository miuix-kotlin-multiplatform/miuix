package top.yukonga.miuix.kmp

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.BlendModeColorFilter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import kotlinx.coroutines.delay
import top.yukonga.miuix.kmp.basic.MiuixSurface
import top.yukonga.miuix.kmp.basic.MiuixText
import top.yukonga.miuix.kmp.basic.MiuixTextField
import top.yukonga.miuix.kmp.icon.MiuixIcons
import top.yukonga.miuix.kmp.icon.icons.Search
import top.yukonga.miuix.kmp.theme.MiuixTheme
import top.yukonga.miuix.kmp.utils.BackHandler

@Composable
fun MiuixSearchBar(
    modifier: Modifier = Modifier,
    searchValue: MutableState<String>,
    rightText: String = "Cancel",
    insideMargin: DpSize = DpSize(24.dp, 14.dp),
    expanded: Boolean = false,
    onExpandedChange: (Boolean) -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    MiuixSurface(
        modifier = modifier.zIndex(1f),
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                MiuixTextField(
                    modifier = Modifier
                        .weight(1f)
                        .focusRequester(focusRequester)
                        .onFocusChanged { if (it.isFocused) onExpandedChange(true) },
                    leadingIcon = {
                        Image(
                            modifier = Modifier
                                .padding(horizontal = 14.dp)
                                .align(Alignment.CenterVertically),
                            imageVector = MiuixIcons.Search,
                            colorFilter = BlendModeColorFilter(
                                MiuixTheme.colorScheme.onPrimary,
                                BlendMode.SrcIn
                            ),
                            contentDescription = "Search"
                        )
                    },
                    insideMargin = insideMargin,
                    value = searchValue.value,
                    onValueChange = { searchValue.value = it },
                    cornerRadius = 50.dp,
                    enableBorder = false,
                    enableOffset = false,
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
                )
                AnimatedVisibility(
                    visible = expanded
                ) {
                    MiuixText(
                        modifier = Modifier
                            .padding(start = insideMargin.width)
                            .clickable(
                                interactionSource = null,
                                indication = null
                            ) {
                                onExpandedChange(false)
                                focusManager.clearFocus()
                                searchValue.value = ""
                            },
                        text = rightText,
                        color = MiuixTheme.colorScheme.primary
                    )
                }
            }
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

    BackHandler(enabled = expanded) {
        onExpandedChange(false)
        focusManager.clearFocus()
    }

    LaunchedEffect(expanded) {
        if (!expanded) {
            delay(100)
            focusManager.clearFocus()
        }
    }
}