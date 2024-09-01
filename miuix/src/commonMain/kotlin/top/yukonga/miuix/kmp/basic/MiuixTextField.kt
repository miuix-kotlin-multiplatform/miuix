package top.yukonga.miuix.kmp.basic

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import top.yukonga.miuix.kmp.theme.MiuixTheme
import top.yukonga.miuix.kmp.utils.squircleshape.SquircleShape

/**
 * A text field component with Miuix style.
 *
 * @param value The text to be displayed in the text field.
 * @param onValueChange The callback to be called when the value changes.
 * @param modifier The modifier to be applied to the [MiuixTextField].
 * @param insideMargin The margin inside the [MiuixTextField].
 * @param backgroundColor The background color of the [MiuixTextField].
 * @param cornerRadius The corner radius of the [MiuixTextField].
 * @param label The label to be displayed when the [MiuixTextField] is empty.
 * @param labelColor The color of the label.
 * @param enabled Whether the [MiuixTextField] is enabled.
 * @param readOnly Whether the [MiuixTextField] is read-only.
 * @param textStyle The text style to be applied to the [MiuixTextField].
 * @param keyboardOptions The keyboard options to be applied to the [MiuixTextField].
 * @param keyboardActions The keyboard actions to be applied to the [MiuixTextField].
 * @param singleLine Whether the text field is single line.
 * @param maxLines The maximum number of lines allowed to be displayed in [MiuixTextField].
 * @param minLines The minimum number of lines allowed to be displayed in [MiuixTextField]. It is required
 *   that 1 <= [minLines] <= [maxLines].
 * @param visualTransformation The visual transformation to be applied to the [MiuixTextField].
 * @param onTextLayout The callback to be called when the text layout changes.
 * @param interactionSource The interaction source to be applied to the [MiuixTextField].
 */
@Composable
fun MiuixTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    insideMargin: DpSize = DpSize(18.dp, 18.dp),
    backgroundColor: Color = MiuixTheme.colorScheme.textFieldBg,
    cornerRadius: Dp = 18.dp,
    label: String = "",
    labelColor: Color = MiuixTheme.colorScheme.textFieldSub,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = MiuixTheme.textStyles.main,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    interactionSource: MutableInteractionSource? = null
) {
    @Suppress("NAME_SHADOWING")
    val interactionSource = interactionSource ?: remember { MutableInteractionSource() }
    val paddingModifier = remember(insideMargin) {
        Modifier.padding(horizontal = insideMargin.width, vertical = insideMargin.height)
    }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val borderWidth by animateDpAsState(if (isFocused) 1.6.dp else 0.dp)
    val borderColor by animateColorAsState(if (isFocused) MiuixTheme.colorScheme.primary else backgroundColor)
    val labelOffsetY by animateDpAsState(if (value.isNotEmpty()) -(insideMargin.height / 2) else 0.dp)
    val innerTextOffsetY by animateDpAsState(if (value.isNotEmpty()) (insideMargin.height / 2) else 0.dp)
    val labelFontSize by animateDpAsState(if (value.isNotEmpty()) 10.dp else 16.dp)

    val updatedOnValueChange by rememberUpdatedState(onValueChange)
    val updatedOnTextLayout by rememberUpdatedState(onTextLayout)

    BasicTextField(
        value = value,
        onValueChange = updatedOnValueChange,
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
        onTextLayout = updatedOnTextLayout,
        interactionSource = interactionSource,
        cursorBrush = SolidColor(MiuixTheme.colorScheme.primary),
        decorationBox = { innerTextField ->
            MiuixBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = backgroundColor,
                        shape = SquircleShape(cornerRadius)
                    )
                    .border(
                        width = borderWidth,
                        color = borderColor,
                        shape = SquircleShape(cornerRadius)
                    )
            ) {
                MiuixBox(
                    modifier = Modifier
                        .fillMaxWidth()
                        .then(paddingModifier)
                ) {
                    MiuixBox(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        MiuixText(
                            text = label,
                            textAlign = TextAlign.Start,
                            fontWeight = FontWeight.Medium,
                            fontSize = labelFontSize.value.sp,
                            modifier = Modifier.offset(y = labelOffsetY),
                            color = labelColor
                        )
                        MiuixBox(
                            modifier = Modifier
                                .offset(y = innerTextOffsetY),
                            contentAlignment = Alignment.BottomStart
                        ) {
                            innerTextField()
                        }
                    }
                }
            }
        }
    )
}