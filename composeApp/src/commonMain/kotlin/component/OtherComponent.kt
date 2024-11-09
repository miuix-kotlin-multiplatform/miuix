package component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import top.yukonga.miuix.kmp.basic.ButtonDefaults
import top.yukonga.miuix.kmp.basic.Card
import top.yukonga.miuix.kmp.basic.Slider
import top.yukonga.miuix.kmp.basic.SmallTitle
import top.yukonga.miuix.kmp.basic.Text
import top.yukonga.miuix.kmp.basic.TextButton
import top.yukonga.miuix.kmp.basic.TextField
import top.yukonga.miuix.kmp.theme.MiuixTheme

@Composable
fun OtherComponent(padding: PaddingValues) {
    var buttonText by remember { mutableStateOf("Cancel") }
    var submitButtonText by remember { mutableStateOf("Submit") }
    var clickCount by remember { mutableStateOf(0) }
    var submitClickCount by remember { mutableStateOf(0) }
    val focusManager = LocalFocusManager.current
    var text1 by remember { mutableStateOf("") }
    var text2 by remember { mutableStateOf(TextFieldValue("")) }
    var progress by remember { mutableStateOf(0.5f) }
    val progressDisable by remember { mutableStateOf(0.5f) }

    SmallTitle(text = "Button")
    Row(
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .padding(bottom = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TextButton(
            text = buttonText,
            onClick = {
                clickCount++
                buttonText = "Click: $clickCount"
            },
            modifier = Modifier.weight(1f)
        )
        Spacer(Modifier.width(12.dp))
        TextButton(
            text = submitButtonText,
            onClick = {
                submitClickCount++
                submitButtonText = "Click: $submitClickCount"
            },
            modifier = Modifier.weight(1f),
            colors = ButtonDefaults.textButtonColorsPrimary()
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
            .padding(bottom = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TextButton(
            text = "Disabled",
            onClick = {},
            modifier = Modifier.weight(1f),
            enabled = false
        )
        Spacer(Modifier.width(12.dp))
        TextButton(
            text = "Disabled",
            onClick = {},
            enabled = false,
            modifier = Modifier.weight(1f),
            colors = ButtonDefaults.textButtonColorsPrimary()
        )
    }

    SmallTitle(text = "TextField")
    TextField(
        value = text1,
        onValueChange = { text1 = it },
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .padding(bottom = 12.dp),
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
    )

    TextField(
        value = text2,
        onValueChange = { text2 = it },
        backgroundColor = MiuixTheme.colorScheme.secondaryContainer,
        label = "Text Field",
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .padding(bottom = 12.dp),
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
    )

    SmallTitle(text = "Slider")
    Slider(
        progress = progress,
        onProgressChange = { newProgress -> progress = newProgress },
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .padding(bottom = 12.dp)
    )

    Slider(
        progress = progressDisable,
        onProgressChange = {},
        enabled = false,
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .padding(bottom = 12.dp)
    )

    SmallTitle(text = "Card")
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
            .padding(bottom = 12.dp),
        color = MiuixTheme.colorScheme.primaryVariant,
        insideMargin = PaddingValues(16.dp)
    ) {
        Text(
            color = MiuixTheme.colorScheme.onPrimary,
            text = "Card",
            fontSize = 19.sp,
            fontWeight = FontWeight.SemiBold
        )
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
            .padding(bottom = 12.dp + padding.calculateBottomPadding()),
        insideMargin = PaddingValues(16.dp)
    ) {
        Text(
            color = MiuixTheme.colorScheme.onSurface,
            text = "Card\nCardCard\nCardCardCard",
            style = MiuixTheme.textStyles.paragraph
        )
    }
}