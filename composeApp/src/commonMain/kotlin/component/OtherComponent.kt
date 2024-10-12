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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import top.yukonga.miuix.kmp.basic.Button
import top.yukonga.miuix.kmp.basic.Card
import top.yukonga.miuix.kmp.basic.Slider
import top.yukonga.miuix.kmp.basic.Text
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

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(
            modifier = Modifier.weight(1f),
            text = buttonText,
            onClick = {
                clickCount++
                buttonText = "Click: $clickCount"
            }
        )
        Spacer(Modifier.width(12.dp))
        Button(
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
            .padding(horizontal = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(
            modifier = Modifier.weight(1f),
            text = "Disabled",
            submit = false,
            enabled = false,
            onClick = {}
        )
        Spacer(Modifier.width(12.dp))
        Button(
            modifier = Modifier.weight(1f),
            text = "Disabled",
            submit = true,
            enabled = false,
            onClick = {}
        )
    }

    TextField(
        value = text1,
        onValueChange = { text1 = it },
        modifier = Modifier.padding(horizontal = 12.dp, vertical = 12.dp),
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
    )

    TextField(
        value = text2,
        onValueChange = { text2 = it },
        backgroundColor = MiuixTheme.colorScheme.secondaryContainer,
        label = "Text Field",
        modifier = Modifier.padding(horizontal = 12.dp),
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
    )

    Slider(
        progress = progress,
        onProgressChange = { newProgress -> progress = newProgress },
        modifier = Modifier.padding(horizontal = 12.dp, vertical = 12.dp)
    )

    Slider(
        progress = progressDisable,
        onProgressChange = {},
        enabled = false,
        modifier = Modifier.padding(horizontal = 12.dp)
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 12.dp),
        color = MiuixTheme.colorScheme.primaryVariant,
        insideMargin = DpSize(16.dp, 16.dp)
    ) {
        Text(
            color = MiuixTheme.colorScheme.onPrimary,
            text = "Card 123456789",
            fontSize = 19.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            color = MiuixTheme.colorScheme.onPrimaryVariant,
            text = "一二三四五六七八九",
            fontSize = 15.sp,
            fontWeight = FontWeight.Normal
        )
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
            .padding(bottom = 12.dp + padding.calculateBottomPadding()),
        insideMargin = DpSize(16.dp, 16.dp)
    ) {
        val color = MiuixTheme.colorScheme.onSurface
        Text(
            color = color,
            text = "Card",
            style = MiuixTheme.textStyles.paragraph,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp
        )
        Spacer(Modifier.height(6.dp))
        Text(
            color = color,
            text = "123456789",
            style = MiuixTheme.textStyles.paragraph
        )
        Text(
            color = color,
            text = "一二三四五六七八九",
            style = MiuixTheme.textStyles.paragraph
        )
        Text(
            color = color,
            text = "!@#$%^&*()_+-=",
            style = MiuixTheme.textStyles.paragraph
        )
    }
}