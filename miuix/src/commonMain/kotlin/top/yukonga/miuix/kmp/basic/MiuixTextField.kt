package top.yukonga.miuix.kmp.basic

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import top.yukonga.miuix.kmp.theme.MiuixTheme

@Composable
fun MiuixTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "",
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
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    val isFocused by interactionSource.collectIsFocusedAsState()
    val borderWidth by animateDpAsState(if (isFocused) 2.dp else 0.dp)
    val borderColor by animateColorAsState(
        if (isFocused) MiuixTheme.colorScheme.primary else MiuixTheme.colorScheme.primaryContainer
    )

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
        cursorBrush = SolidColor(MiuixTheme.colorScheme.cursor),
        decorationBox = { innerTextField ->
            MiuixBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = borderWidth,
                        color = borderColor,
                        shape = RoundedCornerShape(16.dp)
                    )
            ) {
                MiuixBox(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = MiuixTheme.colorScheme.primaryContainer,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(16.dp)
                ) {
                    MiuixBox(
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (value.isEmpty()) {
                            MiuixText(
                                text = label,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.fillMaxWidth(),
                                color = MiuixTheme.colorScheme.subTextField
                            )
                        }
                        innerTextField()
                    }
                }
            }
        }
    )
}