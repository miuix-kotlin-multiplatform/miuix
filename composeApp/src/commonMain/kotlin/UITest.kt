import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import top.yukonga.miuix.kmp.MiuixBox
import top.yukonga.miuix.kmp.MiuixCard
import top.yukonga.miuix.kmp.MiuixSlider
import top.yukonga.miuix.kmp.MiuixSurface
import top.yukonga.miuix.kmp.MiuixSwitch
import top.yukonga.miuix.kmp.MiuixText
import top.yukonga.miuix.kmp.MiuixTextField
import top.yukonga.miuix.kmp.MiuixTextWithSwitch

@Composable
fun UITest() {
    var switch by remember { mutableStateOf(false) }
    var textWishSwitch by remember { mutableStateOf(true) }
    var text by remember { mutableStateOf("") }
    var progress by remember { mutableStateOf(0.5f) }
    var progressEffect by remember { mutableStateOf(0.5f) }
    val focusManager = LocalFocusManager.current

    MiuixSurface(
        modifier = Modifier.fillMaxSize()
    ) {
        MiuixBox(
            modifier = Modifier
                .displayCutoutPadding()
                .systemBarsPadding()
                .padding(top = 18.dp)
        ) {
            Column {
                MiuixText(
                    text = "Text",
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                )

                MiuixSwitch(
                    checked = switch,
                    onCheckedChange = { switch = it },
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                )

                MiuixTextWithSwitch(
                    text = "Text with Switch",
                    checked = textWishSwitch,
                    onCheckedChange = { textWishSwitch = it },
                )

                MiuixTextField(
                    value = text,
                    onValueChange = { text = it },
                    label = "Text Field",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .padding(bottom = 12.dp),
                    keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                )

                MiuixSlider(
                    progress = progress,
                    onProgressChange = { newProgress -> progress = newProgress },
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                        .padding(bottom = 12.dp)
                )

                MiuixSlider(
                    effect = true,
                    progress = progressEffect,
                    onProgressChange = { newProgress -> progressEffect = newProgress },
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                        .padding(bottom = 12.dp)
                )

                MiuixCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .padding(bottom = 12.dp)
                ) {
                    MiuixText("Card")
                }
            }
        }
    }
}