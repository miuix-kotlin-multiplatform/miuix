package component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import top.yukonga.miuix.kmp.basic.MiuixCard
import top.yukonga.miuix.kmp.basic.MiuixSlider
import top.yukonga.miuix.kmp.basic.MiuixText
import top.yukonga.miuix.kmp.basic.MiuixTextField
import top.yukonga.miuix.kmp.theme.MiuixTheme

@Composable
fun OtherComponent(padding: PaddingValues) {
    val focusManager = LocalFocusManager.current
    var text by remember { mutableStateOf("") }
    var progress by remember { mutableStateOf(0.2f) }
    var progressFloat by remember { mutableStateOf(0.6f) }

    MiuixTextField(
        value = text,
        onValueChange = { text = it },
        label = "Text Field",
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 28.dp)
            .padding(vertical = 28.dp),
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
    )

    MiuixSlider(
        progress = progress,
        onProgressChange = { newProgress -> progress = newProgress },
        dragShow = true,
        modifier = Modifier
            .padding(horizontal = 28.dp)
            .padding(bottom = 28.dp)
    )

    MiuixSlider(
        progress = progressFloat,
        onProgressChange = { newProgress -> progressFloat = newProgress },
        modifier = Modifier
            .padding(horizontal = 28.dp)
            .padding(bottom = 28.dp)
    )

    MiuixCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 28.dp)
            .padding(bottom = 28.dp + padding.calculateBottomPadding())
    ) {
        MiuixText(
            text = "Card",
            style = MiuixTheme.textStyles.paragraph,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp
        )
        Spacer(Modifier.height(10.dp))
        MiuixText(
            text = "123456789",
            style = MiuixTheme.textStyles.paragraph
        )
        MiuixText(
            text = "一二三四五六七八九",
            style = MiuixTheme.textStyles.paragraph
        )
        MiuixText(
            text = "!@#$%^&*()_+-=",
            style = MiuixTheme.textStyles.paragraph
        )
    }
}