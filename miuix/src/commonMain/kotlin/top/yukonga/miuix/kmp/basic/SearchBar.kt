// Copyright 2025, miuix-kotlin-multiplatform contributors
// SPDX-License-Identifier: Apache-2.0

package top.yukonga.miuix.kmp.basic

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import top.yukonga.miuix.kmp.icon.MiuixIcons
import top.yukonga.miuix.kmp.icon.icons.basic.Search
import top.yukonga.miuix.kmp.icon.icons.basic.SearchCleanup
import top.yukonga.miuix.kmp.theme.MiuixTheme
import top.yukonga.miuix.kmp.utils.BackHandler
import top.yukonga.miuix.kmp.utils.SmoothRoundedCornerShape

/**
 * A [SearchBar] component with Miuix style.
 *
 * @param inputField the input field to input a query in the [SearchBar].
 * @param onExpandedChange the callback to be invoked when the [SearchBar]'s expanded state is
 *   changed.
 * @param insideMargin The margin inside the [SearchBar].
 * @param modifier the [Modifier] to be applied to the [SearchBar].
 * @param expanded whether the [SearchBar] is expanded and showing search results.
 * @param outsideRightAction the action to be shown at the right side of the [SearchBar] when it is
 *   expanded.
 * @param content the content to be shown when the [SearchBar] is expanded.
 */
@Composable
fun SearchBar(
    inputField: @Composable () -> Unit,
    onExpandedChange: (Boolean) -> Unit,
    insideMargin: DpSize = DpSize(12.dp, 0.dp),
    modifier: Modifier = Modifier,
    expanded: Boolean = false,
    outsideRightAction: @Composable (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = insideMargin.height, horizontal = insideMargin.width)
            ) {
                inputField()
            }
            AnimatedVisibility(
                visible = expanded,
                enter = expandHorizontally() + slideInHorizontally(initialOffsetX = { it }),
                exit = shrinkHorizontally() + slideOutHorizontally(targetOffsetX = { it })
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
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    interactionSource: MutableInteractionSource? = null,
) {
    val internalInteractionSource = interactionSource ?: remember { MutableInteractionSource() }

    val actualLeadingIcon = leadingIcon ?: remember {
        @Composable {
            Icon(
                modifier = Modifier.padding(start = 16.dp, end = 8.dp),
                imageVector = MiuixIcons.Basic.Search,
                tint = MiuixTheme.colorScheme.onSurfaceContainerHigh,
                contentDescription = "Search"
            )
        }
    }

    val actualTrailingIcon = trailingIcon ?: remember(onQueryChange) {
        @Composable {
            AnimatedVisibility(
                visible = query.isNotEmpty(),
                enter = fadeIn(),
                exit = fadeOut(),
            ) {
                Box(
                    modifier = Modifier.padding(start = 8.dp, end = 16.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Icon(
                        modifier = Modifier.clip(CircleShape).clickable { onQueryChange("") },
                        imageVector = MiuixIcons.Basic.SearchCleanup,
                        tint = MiuixTheme.colorScheme.onSurfaceContainerHighest,
                        contentDescription = "Search Cleanup"
                    )
                }
            }
        }
    }

    val focused = internalInteractionSource.collectIsFocusedAsState().value
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    val currentMainTextStyleFromTheme = MiuixTheme.textStyles.main
    val inputTextStyle = remember(currentMainTextStyleFromTheme) {
        currentMainTextStyleFromTheme.copy(fontWeight = FontWeight.Bold)
    }
    val currentPrimaryColorFromTheme = MiuixTheme.colorScheme.primary
    val rememberedCursorBrush = remember(currentPrimaryColorFromTheme) {
        SolidColor(currentPrimaryColorFromTheme)
    }

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
        textStyle = inputTextStyle,
        cursorBrush = rememberedCursorBrush,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = { onSearch(query) }),
        interactionSource = internalInteractionSource,
        decorationBox =
            @Composable { innerTextField ->
                val shape = remember { derivedStateOf { SmoothRoundedCornerShape(50.dp) } }
                val labelTextStyle = remember {
                    TextStyle(fontSize = 17.sp, fontWeight = FontWeight.Bold)
                }
                Box(
                    modifier = Modifier
                        .background(
                            color = MiuixTheme.colorScheme.surfaceContainerHigh,
                            shape = shape.value
                        ),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        actualLeadingIcon()
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .heightIn(min = 45.dp),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            Text(
                                text = if (!(query.isNotEmpty() || expanded)) label else "",
                                style = labelTextStyle,
                                color = MiuixTheme.colorScheme.onSurfaceContainerHigh
                            )
                            innerTextField()
                        }
                        actualTrailingIcon()
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