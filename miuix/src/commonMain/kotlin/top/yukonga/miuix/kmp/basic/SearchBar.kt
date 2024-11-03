package top.yukonga.miuix.kmp.basic

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import kotlinx.coroutines.delay
import top.yukonga.miuix.kmp.theme.MiuixTheme
import top.yukonga.miuix.kmp.utils.BackHandler
import top.yukonga.miuix.kmp.utils.SmoothRoundedCornerShape

/**
 * A [SearchBar] component with Miuix style.
 *
 * @param modifier the [Modifier] to be applied to the [SearchBar].
 * @param inputField the input field to input a query in the [SearchBar].
 * @param expanded whether the [SearchBar] is expanded and showing search results.
 * @param onExpandedChange the callback to be invoked when the [SearchBar]'s expanded state is
 *   changed.
 * @param outsideRightAction the action to be shown at the right side of the [SearchBar] when it is
 *   expanded.
 * @param content the content to be shown when the [SearchBar] is expanded.
 */
@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    inputField: @Composable () -> Unit,
    expanded: Boolean = false,
    onExpandedChange: (Boolean) -> Unit,
    outsideRightAction: @Composable (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    Surface(
        modifier = modifier.zIndex(1f),
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    inputField()
                }
                AnimatedVisibility(
                    visible = expanded
                ) {
                    outsideRightAction?.invoke()
                }
            }

            AnimatedVisibility(
                visible = expanded
            ) {
                content()
            }
        }
    }

    BackHandler(enabled = expanded) {
        onExpandedChange(false)
    }
}

/**
 * A text field to input a query in a search bar with Miuix style.
 *
 * @param query the query text to be shown in the input field.
 * @param onQueryChange the callback to be invoked when the input service updates the query. An
 *   updated text comes as a parameter of the callback.
 * @param label the label to be shown when the input field is not focused.
 * @param onSearch the callback to be invoked when the input service triggers the
 *   [ImeAction.Search] action. The current [query] comes as a parameter of the callback.
 * @param expanded whether the search bar is expanded and showing search results.
 * @param onExpandedChange the callback to be invoked when the search bar's expanded state is
 *   changed.
 * @param modifier the [Modifier] to be applied to this input field.
 * @param enabled the enabled state of this input field. When `false`, this component will not
 *   respond to user input, and it will appear visually disabled and disabled to accessibility
 *   services.
 * @param insideMargin the margin inside the [InputField].
 * @param leadingIcon the leading icon to be displayed at the start of the input field.
 * @param trailingIcon the trailing icon to be displayed at the end of the input field.
 * @param interactionSource an optional hoisted [MutableInteractionSource] for observing and
 *   emitting [Interaction]s for this input field. You can use this to change the search bar's
 *   appearance or preview the search bar in different states. Note that if `null` is provided,
 *   interactions will still happen internally.
 */
@Composable
fun InputField(
    query: String,
    onQueryChange: (String) -> Unit,
    label: String = "",
    onSearch: (String) -> Unit,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    insideMargin: DpSize = DpSize(12.dp, 12.dp),
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    interactionSource: MutableInteractionSource? = null,
) {
    @Suppress("NAME_SHADOWING")
    val interactionSource = interactionSource ?: remember { MutableInteractionSource() }

    val paddingModifier = remember(insideMargin, leadingIcon, trailingIcon) {
        if (leadingIcon == null && trailingIcon == null) Modifier.padding(insideMargin.width, vertical = insideMargin.height)
        else if (leadingIcon == null) Modifier.padding(start = insideMargin.width).padding(vertical = insideMargin.height)
        else if (trailingIcon == null) Modifier.padding(end = insideMargin.width).padding(vertical = insideMargin.height)
        else Modifier.padding(vertical = insideMargin.height)
    }

    val focused = interactionSource.collectIsFocusedAsState().value
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    BasicTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier
            .focusRequester(focusRequester)
            .onFocusChanged { if (it.isFocused) onExpandedChange(true) }
            .semantics {
                onClick {
                    focusRequester.requestFocus()
                    true
                }
            },
        enabled = enabled,
        singleLine = true,
        textStyle = MiuixTheme.textStyles.main,
        cursorBrush = SolidColor(MiuixTheme.colorScheme.primary),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = { onSearch(query) }),
        interactionSource = interactionSource,
        decorationBox =
        @Composable { innerTextField ->
            Box(
                modifier = Modifier
                    .background(
                        color = MiuixTheme.colorScheme.surfaceContainerHigh,
                        shape = SmoothRoundedCornerShape(50.dp)
                    )
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (leadingIcon != null) {
                        leadingIcon()
                    }
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .then(paddingModifier),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            text = if (!(query.isNotEmpty() || expanded)) label else "",
                            color = MiuixTheme.colorScheme.onSurfaceContainerHigh
                        )

                        innerTextField()
                    }
                    if (trailingIcon != null) {
                        trailingIcon()
                    }
                }
            }
        }
    )

    val shouldClearFocus = !expanded && focused
    LaunchedEffect(expanded) {
        if (shouldClearFocus) {
            delay(100)
            focusManager.clearFocus()
        }
    }
}