// Copyright 2025, miuix-kotlin-multiplatform contributors
// SPDX-License-Identifier: Apache-2.0

package top.yukonga.miuix.kmp.basic

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.KeyboardActionHandler
import androidx.compose.foundation.text.input.OutputTransformation
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import top.yukonga.miuix.kmp.theme.MiuixTheme

/**
 * A [TextField] component with Miuix style.
 *
 * @param state The [TextFieldState] to be shown in the text field.
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
 * @param inputTransformation The input transformation to be applied to the [TextField].
 * @param textStyle The text style to be applied to the [TextField].
 * @param keyboardOptions The keyboard options to be applied to the [TextField].
 * @param onKeyboardAction The keyboard action handler for the [TextField].
 * @param lineLimits The line limits for the [TextField].
 * @param leadingIcon The leading icon to be displayed in the [TextField].
 * @param trailingIcon The trailing icon to be displayed in the [TextField].
 * @param onTextLayout The callback to be called when the text layout changes.
 * @param interactionSource The interaction source to be applied to the [TextField].
 * @param cursorBrush The brush to be used for the cursor.
 * @param outputTransformation The output transformation for the text field.
 * @param scrollState The scroll state for the text field.
 */
@Composable
fun TextField(
    state: TextFieldState,
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
    inputTransformation: InputTransformation? = null,
    textStyle: TextStyle = MiuixTheme.textStyles.main,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onKeyboardAction: KeyboardActionHandler? = null,
    lineLimits: TextFieldLineLimits = TextFieldLineLimits.Default,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    onTextLayout: (Density.(getResult: () -> TextLayoutResult?) -> Unit)? = null,
    interactionSource: MutableInteractionSource? = null,
    cursorBrush: Brush = SolidColor(borderColor),
    outputTransformation: OutputTransformation? = null,
    scrollState: ScrollState = rememberScrollState(),
) {
    @Suppress("NAME_SHADOWING")
    val interactionSource = interactionSource ?: remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val borderWidth by animateDpAsState(if (isFocused) 2.dp else 0.dp)
    val animatedBorderColor by animateColorAsState(if (isFocused) borderColor else backgroundColor)
    val borderShape = remember(cornerRadius) { RoundedCornerShape(cornerRadius) }
    val finalModifier = Modifier.background(backgroundColor, borderShape).border(borderWidth, animatedBorderColor, borderShape)

    val labelState = when {
        label.isEmpty() -> LabelAnimState.Hidden
        useLabelAsPlaceholder && state.text.isNotEmpty() -> LabelAnimState.Placeholder
        state.text.isNotEmpty() -> LabelAnimState.Floating
        else -> LabelAnimState.Normal
    }
    val labelAnim by animateDpAsState(
        when (labelState) {
            LabelAnimState.Floating -> -insideMargin.height / 2
            LabelAnimState.Placeholder, LabelAnimState.Normal -> 0.dp
            LabelAnimState.Hidden -> 0.dp
        }
    )
    val labelFontSize by animateDpAsState(
        when (labelState) {
            LabelAnimState.Floating -> 10.dp
            else -> 17.dp
        }
    )
    val paddingModifier = when {
        leadingIcon == null && trailingIcon == null -> Modifier.padding(insideMargin.width, vertical = insideMargin.height)
        leadingIcon == null -> Modifier.padding(start = insideMargin.width).padding(vertical = insideMargin.height)
        trailingIcon == null -> Modifier.padding(end = insideMargin.width).padding(vertical = insideMargin.height)
        else -> Modifier.padding(vertical = insideMargin.height)
    }


    BasicTextField(
        state = state,
        modifier = modifier,
        enabled = enabled,
        readOnly = readOnly,
        textStyle = textStyle,
        cursorBrush = cursorBrush,
        keyboardOptions = keyboardOptions,
        onKeyboardAction = onKeyboardAction,
        lineLimits = lineLimits,
        onTextLayout = onTextLayout,
        interactionSource = interactionSource,
        inputTransformation = inputTransformation,
        outputTransformation = outputTransformation,
        scrollState = scrollState,
        decorator = @Composable { innerTextField ->
            textFieldDecorationBox(
                finalModifier = finalModifier,
                leadingIcon = leadingIcon,
                trailingIcon = trailingIcon,
                paddingModifier = paddingModifier,
                label = label,
                labelFontSize = labelFontSize,
                labelColor = labelColor,
                labelState = labelState,
                labelAnim = labelAnim,
                insideMargin = insideMargin,
                innerTextField = innerTextField
            )
        }
    )
}

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
    val isFocused by interactionSource.collectIsFocusedAsState()
    val borderWidth by animateDpAsState(if (isFocused) 2.dp else 0.dp)
    val animatedBorderColor by animateColorAsState(if (isFocused) borderColor else backgroundColor)
    val borderShape = remember(cornerRadius) { RoundedCornerShape(cornerRadius) }
    val finalModifier = Modifier.background(backgroundColor, borderShape).border(borderWidth, animatedBorderColor, borderShape)

    val labelState = when {
        label.isEmpty() -> LabelAnimState.Hidden
        useLabelAsPlaceholder && value.text.isNotEmpty() -> LabelAnimState.Placeholder
        value.text.isNotEmpty() -> LabelAnimState.Floating
        else -> LabelAnimState.Normal
    }
    val labelAnim by animateDpAsState(
        when (labelState) {
            LabelAnimState.Floating -> -insideMargin.height / 2
            LabelAnimState.Placeholder, LabelAnimState.Normal -> 0.dp
            LabelAnimState.Hidden -> 0.dp
        }
    )
    val labelFontSize by animateDpAsState(
        when (labelState) {
            LabelAnimState.Floating -> 10.dp
            else -> 17.dp
        }
    )
    val paddingModifier = when {
        leadingIcon == null && trailingIcon == null -> Modifier.padding(insideMargin.width, vertical = insideMargin.height)
        leadingIcon == null -> Modifier.padding(start = insideMargin.width).padding(vertical = insideMargin.height)
        trailingIcon == null -> Modifier.padding(end = insideMargin.width).padding(vertical = insideMargin.height)
        else -> Modifier.padding(vertical = insideMargin.height)
    }


    BasicTextField(
        value = value,
        onValueChange = onValueChange,
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
        decorationBox = @Composable { innerTextField ->
            textFieldDecorationBox(
                finalModifier = finalModifier,
                leadingIcon = leadingIcon,
                trailingIcon = trailingIcon,
                paddingModifier = paddingModifier,
                label = label,
                labelFontSize = labelFontSize,
                labelColor = labelColor,
                labelState = labelState,
                labelAnim = labelAnim,
                insideMargin = insideMargin,
                innerTextField = innerTextField
            )
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
    val isFocused by interactionSource.collectIsFocusedAsState()
    val borderWidth by animateDpAsState(if (isFocused) 2.dp else 0.dp)
    val animatedBorderColor by animateColorAsState(if (isFocused) borderColor else backgroundColor)
    val borderShape = remember(cornerRadius) { RoundedCornerShape(cornerRadius) }
    val finalModifier = Modifier.background(backgroundColor, borderShape).border(borderWidth, animatedBorderColor, borderShape)

    val labelState = when {
        label.isEmpty() -> LabelAnimState.Hidden
        useLabelAsPlaceholder && value.isNotEmpty() -> LabelAnimState.Placeholder
        value.isNotEmpty() -> LabelAnimState.Floating
        else -> LabelAnimState.Normal
    }
    val labelAnim by animateDpAsState(
        when (labelState) {
            LabelAnimState.Floating -> -insideMargin.height / 2
            LabelAnimState.Placeholder, LabelAnimState.Normal -> 0.dp
            LabelAnimState.Hidden -> 0.dp
        }
    )
    val labelFontSize by animateDpAsState(
        when (labelState) {
            LabelAnimState.Floating -> 10.dp
            else -> 17.dp
        }
    )
    val paddingModifier = when {
        leadingIcon == null && trailingIcon == null -> Modifier.padding(insideMargin.width, vertical = insideMargin.height)
        leadingIcon == null -> Modifier.padding(start = insideMargin.width).padding(vertical = insideMargin.height)
        trailingIcon == null -> Modifier.padding(end = insideMargin.width).padding(vertical = insideMargin.height)
        else -> Modifier.padding(vertical = insideMargin.height)
    }

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
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
        decorationBox = @Composable { innerTextField ->
            textFieldDecorationBox(
                finalModifier = finalModifier,
                leadingIcon = leadingIcon,
                trailingIcon = trailingIcon,
                paddingModifier = paddingModifier,
                label = label,
                labelFontSize = labelFontSize,
                labelColor = labelColor,
                labelState = labelState,
                labelAnim = labelAnim,
                insideMargin = insideMargin,
                innerTextField = innerTextField
            )
        }
    )
}

private enum class LabelAnimState { Hidden, Placeholder, Normal, Floating }

/**
 * A Miuix style decoration box for the [TextField] component.
 */
@Composable
private fun textFieldDecorationBox(
    finalModifier: Modifier,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    paddingModifier: Modifier,
    label: String,
    labelFontSize: Dp,
    labelColor: Color,
    labelState: LabelAnimState,
    labelAnim: Dp = 0.dp,
    insideMargin: DpSize = DpSize(16.dp, 16.dp),
    innerTextField: @Composable () -> Unit,
) {
    Box(
        modifier = finalModifier,
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            leadingIcon?.invoke()
            Box(
                modifier = Modifier.weight(1f).then(paddingModifier),
                contentAlignment = Alignment.TopStart
            ) {
                if (labelState != LabelAnimState.Hidden && labelState != LabelAnimState.Placeholder) {
                    Text(
                        text = label,
                        fontSize = labelFontSize.value.sp,
                        color = labelColor,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.offset(y = labelAnim),
                        textAlign = TextAlign.Start
                    )
                }
                Box(
                    modifier = Modifier.offset(y = if (labelState == LabelAnimState.Floating) insideMargin.height / 2 else 0.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    innerTextField()
                }
            }
            trailingIcon?.invoke()
        }
    }
}
