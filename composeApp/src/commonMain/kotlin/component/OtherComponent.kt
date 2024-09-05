package component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import top.yukonga.miuix.kmp.basic.MiuixButton
import top.yukonga.miuix.kmp.basic.MiuixCard
import top.yukonga.miuix.kmp.basic.MiuixSlider
import top.yukonga.miuix.kmp.basic.MiuixText
import top.yukonga.miuix.kmp.basic.MiuixTextField
import top.yukonga.miuix.kmp.theme.MiuixTheme

@Composable
fun OtherComponent(padding: PaddingValues) {

    var buttonText by remember { mutableStateOf("Button") }
    var submitButtonText by remember { mutableStateOf("Submit") }
    var clickCount by remember { mutableStateOf(0) }
    var submitClickCount by remember { mutableStateOf(0) }
    val focusManager = LocalFocusManager.current
    var text1 by remember { mutableStateOf("") }
    var text2 by remember { mutableStateOf("Text") }
    var progress by remember { mutableStateOf(0.5f) }
    val progressDisable by remember { mutableStateOf(0.5f) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        MiuixButton(
            modifier = Modifier.weight(1f),
            text = buttonText,
            onClick = {
                clickCount++
                buttonText = "Click: $clickCount"
            }
        )
        Spacer(Modifier.width(20.dp))
        MiuixButton(
            modifier = Modifier.weight(1f),
            text = submitButtonText,
            submit = true,
            onClick = {
                submitClickCount++
                submitButtonText = "Click: $submitClickCount"
            }
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        MiuixButton(
            modifier = Modifier.weight(1f),
            text = "Disabled",
            submit = true,
            enabled = false,
            onClick = {}
        )
        Spacer(Modifier.width(20.dp))
        MiuixButton(
            modifier = Modifier.weight(1f),
            text = "Disabled",
            submit = false,
            enabled = false,
            onClick = {}
        )
    }

    MiuixTextField(
        value = text1,
        onValueChange = { text1 = it },
        label = "Text Field",
        modifier = Modifier.padding(horizontal = 24.dp, vertical = 20.dp),
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
    )

    MiuixTextField(
        value = text2,
        onValueChange = { text2 = it },
        backgroundColor = MiuixTheme.colorScheme.primaryContainer,
        label = "Text Field",
        modifier = Modifier.padding(horizontal = 24.dp),
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
    )

    MiuixSlider(
        progress = progress,
        onProgressChange = { newProgress -> progress = newProgress },
        modifier = Modifier.padding(horizontal = 24.dp, vertical = 20.dp)
    )

    MiuixSlider(
        progress = progressDisable,
        onProgressChange = {},
        enabled = false,
        modifier = Modifier.padding(horizontal = 24.dp)
    )

    MiuixCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 20.dp),
        insideMargin = DpSize(18.dp, 18.dp)
    ) {
        CardView()
    }

    MiuixCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .padding(bottom = 20.dp + padding.calculateBottomPadding()),
        color = MiuixTheme.colorScheme.primary,
        insideMargin = DpSize(18.dp, 18.dp)
    ) {
        CardView(color = Color.White)
    }

}

@Composable
fun CardView(color: Color = MiuixTheme.colorScheme.onBackground) {
    MiuixText(
        color = color,
        text = "Card",
        style = MiuixTheme.textStyles.paragraph,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp
    )
    Spacer(Modifier.height(10.dp))
    MiuixText(
        color = color,
        text = "123456789",
        style = MiuixTheme.textStyles.paragraph
    )
    MiuixText(
        color = color,
        text = "一二三四五六七八九",
        style = MiuixTheme.textStyles.paragraph
    )
    MiuixText(
        color = color,
        text = "!@#$%^&*()_+-=",
        style = MiuixTheme.textStyles.paragraph
    )
}