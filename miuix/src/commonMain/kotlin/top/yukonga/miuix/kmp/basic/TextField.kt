// Copyright 2025, miuix-kotlin-multiplatform contributors
// SPDX-License-Identifier: Apache-2.0

package top.yukonga.miuix.kmp.basic

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import top.yukonga.miuix.kmp.theme.MiuixTheme
import top.yukonga.miuix.kmp.utils.SmoothRoundedCornerShape

/**
 * A [TextField] component with Miuix style.
 *
 * @param value The input [TextFieldValue] to be shown in the text field.
 * @param onValueChange The callback that is triggered when the input service updates values in
 *   [TextFieldValue]. An updated [TextFieldValue] comes as a parameter of the callback.
 * @param modifier The modifier to be applied to the [TextField].
 * @param insideMargin The margin inside the [TextField].
 * @param backgroundColor The background color of the [TextField].
 * @param cornerRadius The corner radius of the [TextField].
 * @param label The label to be displayed when the [TextField] is empty.
 * @param labelColor The color of the label.
 * @param borderColor The color of the border when the [TextField] is focused.
 * @param useLabelAsPlaceholder Whether to use the label as a placeholder.
 * @param enabled Whether the [TextField] is enabled.
 * @param readOnly Whether the [TextField] is read-only.
 * @param textStyle The text style to be applied to the [TextField].
 * @param keyboardOptions The keyboard options to be applied to the [TextField].
 * @param keyboardActions The keyboard actions to be applied to the [TextField].
 * @param leadingIcon The leading icon to be displayed in the [TextField].
 * @param trailingIcon The trailing icon to be displayed in the [TextField].
 * @param singleLine Whether the text field is single line.
 * @param maxLines The maximum number of lines allowed to be displayed in [TextField].
 * @param minLines The minimum number of lines allowed to be displayed in [TextField]. It is required
 *   that 1 <= [minLines] <= [maxLines].
 * @param visualTransformation The visual transformation to be applied to the [TextField].
 * @param onTextLayout The callback to be called when the text layout changes.
 * @param interactionSource The interaction source to be applied to the [TextField].
 * @param cursorBrush The brush to be used for the cursor.
 */
@Composable
fun TextField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    insideMargin: DpSize = DpSize(16.dp, 16.dp),
    backgroundColor: Color = MiuixTheme.colorScheme.secondaryContainer,
    cornerRadius: Dp = 16.dp,
    label: String = "",
    labelColor: Color = MiuixTheme.colorScheme.onSecondaryContainer,
    borderColor: Color = MiuixTheme.colorScheme.primary,
    useLabelAsPlaceholder: Boolean = false,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = MiuixTheme.textStyles.main,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    interactionSource: MutableInteractionSource? = null,
    cursorBrush: Brush = SolidColor(MiuixTheme.colorScheme.primary)
) {
    @Suppress("NAME_SHADOWING")
    val interactionSource = interactionSource ?: remember { MutableInteractionSource() }
    val currentOnValueChange by rememberUpdatedState(onValueChange)

    val paddingModifier = remember(insideMargin, leadingIcon, trailingIcon) {
        if (leadingIcon == null && trailingIcon == null) Modifier.padding(
            horizontal = insideMargin.width,
            vertical = insideMargin.height
        )
        else if (leadingIcon == null) Modifier.padding(start = insideMargin.width).padding(vertical = insideMargin.height)
        else if (trailingIcon == null) Modifier.padding(end = insideMargin.width).padding(vertical = insideMargin.height)
        else Modifier.padding(vertical = insideMargin.height)
    }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val borderWidth by animateDpAsState(if (isFocused) 2.dp else 0.dp)
    val animatedBorderColor by animateColorAsState(if (isFocused) borderColor else backgroundColor)
    val labelOffsetY by animateDpAsState(if (value.text.isNotEmpty() && !useLabelAsPlaceholder) -(insideMargin.height / 2.2f) else 0.dp)
    val innerTextOffsetY by animateDpAsState(if (value.text.isNotEmpty() && !useLabelAsPlaceholder) (insideMargin.height / 1.8f) else 0.dp)
    val borderShape = remember(cornerRadius) { RoundedCornerShape(cornerRadius) }
    val borderModifier = Modifier.border(borderWidth, animatedBorderColor, borderShape)
    val labelOffset = if (label != "" && !useLabelAsPlaceholder) Modifier.offset(y = labelOffsetY) else Modifier
    val innerTextOffset = if (label != "" && !useLabelAsPlaceholder) Modifier.offset(y = innerTextOffsetY) else Modifier
    val labelFontSize by animateDpAsState(
        if (value.text.isNotEmpty() && !useLabelAsPlaceholder) max(
            textStyle.fontSize.value.dp - 7.dp,
            0.dp
        ) else textStyle.fontSize.value.dp
    )

    BasicTextField(
        value = value,
        onValueChange = currentOnValueChange,
        modifier = modifier,
        enabled = enabled,
        readOnly = readOnly,
        textStyle = textStyle,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines,
        visualTransformation = visualTransformation,
        onTextLayout = onTextLayout,
        interactionSource = interactionSource,
        cursorBrush = cursorBrush,
        decorationBox =
            @Composable { innerTextField ->
                val backgroundShape = remember(cornerRadius) { SmoothRoundedCornerShape(cornerRadius) }
                val enterTransition = remember { fadeIn(spring(stiffness = 2500f)) }
                val exitTransition = remember { fadeOut(spring(stiffness = 5000f)) }
                Box(
                    modifier = Modifier
                        .background(
                            color = backgroundColor,
                            shape = backgroundShape
                        )
                        .then(borderModifier),
                    contentAlignment = Alignment.CenterStart
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
                            contentAlignment = Alignment.TopStart
                        ) {
                            androidx.compose.animation.AnimatedVisibility(
                                visible = if (useLabelAsPlaceholder) value.text.isEmpty() else true,
                                enter = enterTransition,
                                exit = exitTransition
                            ) {
                                Text(
                                    text = label,
                                    textAlign = TextAlign.Start,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = labelFontSize.value.sp,
                                    modifier = Modifier.then(labelOffset),
                                    color = labelColor
                                )
                            }
                            Box(
                                modifier = Modifier.then(innerTextOffset),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                innerTextField()
                            }
                        }
                        if (trailingIcon != null) {
                            trailingIcon()
                        }
                    }
                }
            }
    )
}

/**
 * A text field component with Miuix style.
 *
 * @param value The text to be displayed in the text field.
 * @param onValueChange The callback to be called when the value changes.
 * @param modifier The modifier to be applied to the [TextField].
 * @param insideMargin The margin inside the [TextField].
 * @param backgroundColor The background color of the [TextField].
 * @param cornerRadius The corner radius of the [TextField].
 * @param label The label to be displayed when the [TextField] is empty.
 * @param labelColor The color of the label.
 * @param borderColor The color of the border when the [TextField] is focused.
 * @param useLabelAsPlaceholder Whether to use the label as a placeholder.
 * @param enabled Whether the [TextField] is enabled.
 * @param readOnly Whether the [TextField] is read-only.
 * @param textStyle The text style to be applied to the [TextField].
 * @param keyboardOptions The keyboard options to be applied to the [TextField].
 * @param keyboardActions The keyboard actions to be applied to the [TextField].
 * @param leadingIcon The leading icon to be displayed in the [TextField].
 * @param trailingIcon The trailing icon to be displayed in the [TextField].
 * @param singleLine Whether the text field is single line.
 * @param maxLines The maximum number of lines allowed to be displayed in [TextField].
 * @param minLines The minimum number of lines allowed to be displayed in [TextField]. It is required
 *   that 1 <= [minLines] <= [maxLines].
 * @param visualTransformation The visual transformation to be applied to the [TextField].
 * @param onTextLayout The callback to be called when the text layout changes.
 * @param interactionSource The interaction source to be applied to the [TextField].
 * @param cursorBrush The brush to be used for the cursor.
 */
@Composable
fun TextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    insideMargin: DpSize = DpSize(16.dp, 16.dp),
    backgroundColor: Color = MiuixTheme.colorScheme.secondaryContainer,
    cornerRadius: Dp = 16.dp,
    label: String = "",
    labelColor: Color = MiuixTheme.colorScheme.onSecondaryContainer,
    borderColor: Color = MiuixTheme.colorScheme.primary,
    useLabelAsPlaceholder: Boolean = false,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = MiuixTheme.textStyles.main,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    interactionSource: MutableInteractionSource? = null,
    cursorBrush: Brush = SolidColor(MiuixTheme.colorScheme.primary)
) {
    @Suppress("NAME_SHADOWING")
    val interactionSource = interactionSource ?: remember { MutableInteractionSource() }
    val currentOnValueChange by rememberUpdatedState(onValueChange)

    val paddingModifier = remember(insideMargin, leadingIcon, trailingIcon) {
        if (leadingIcon == null && trailingIcon == null) Modifier.padding(insideMargin.width, vertical = insideMargin.height)
        else if (leadingIcon == null) Modifier.padding(start = insideMargin.width).padding(vertical = insideMargin.height)
        else if (trailingIcon == null) Modifier.padding(end = insideMargin.width).padding(vertical = insideMargin.height)
        else Modifier.padding(vertical = insideMargin.height)
    }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val borderWidth by animateDpAsState(if (isFocused) 2.0.dp else 0.dp)
    val animatedBorderColor by animateColorAsState(if (isFocused) borderColor else backgroundColor)
    val labelOffsetY by animateDpAsState(if (value.isNotEmpty() && !useLabelAsPlaceholder) -(insideMargin.height / 2) else 0.dp)
    val innerTextOffsetY by animateDpAsState(if (value.isNotEmpty() && !useLabelAsPlaceholder) (insideMargin.height / 2) else 0.dp)
    val labelFontSize by animateDpAsState(if (value.isNotEmpty() && !useLabelAsPlaceholder) 10.dp else 17.dp)
    val borderShape = remember(cornerRadius) { RoundedCornerShape(cornerRadius) }
    val borderModifier = Modifier.border(borderWidth, animatedBorderColor, borderShape)
    val labelOffset = if (label != "" && !useLabelAsPlaceholder) Modifier.offset(y = labelOffsetY) else Modifier
    val innerTextOffset = if (label != "" && !useLabelAsPlaceholder) Modifier.offset(y = innerTextOffsetY) else Modifier

    BasicTextField(
        value = value,
        onValueChange = currentOnValueChange,
        modifier = modifier,
        enabled = enabled,
        readOnly = readOnly,
        textStyle = textStyle,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines,
        visualTransformation = visualTransformation,
        onTextLayout = onTextLayout,
        interactionSource = interactionSource,
        cursorBrush = cursorBrush,
        decorationBox =
            @Composable { innerTextField ->
                val backgroundShape = remember(cornerRadius) { SmoothRoundedCornerShape(cornerRadius) }
                val enterTransition = remember { fadeIn(spring(stiffness = 2500f)) }
                val exitTransition = remember { fadeOut(spring(stiffness = 5000f)) }
                Box(
                    modifier = Modifier
                        .background(
                            color = backgroundColor,
                            shape = backgroundShape
                        )
                        .then(borderModifier),
                    contentAlignment = Alignment.CenterStart
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
                            androidx.compose.animation.AnimatedVisibility(
                                visible = if (useLabelAsPlaceholder) value.isEmpty() else true,
                                enter = enterTransition,
                                exit = exitTransition
                            ) {
                                Text(
                                    text = label,
                                    textAlign = TextAlign.Start,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = labelFontSize.value.sp,
                                    modifier = Modifier.then(labelOffset),
                                    color = labelColor
                                )
                            }
                            Box(
                                modifier = Modifier.then(innerTextOffset),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                innerTextField()
                            }
                        }
                        if (trailingIcon != null) {
                            trailingIcon()
                        }
                    }
                }
            }
    )
}