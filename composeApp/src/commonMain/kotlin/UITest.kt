import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import top.yukonga.miuix.kmp.MiuixBox
import top.yukonga.miuix.kmp.MiuixCard
import top.yukonga.miuix.kmp.MiuixSurface
import top.yukonga.miuix.kmp.MiuixSwitch
import top.yukonga.miuix.kmp.MiuixText
import top.yukonga.miuix.kmp.MiuixTextWithSwitch

@Composable
fun UITest() {
    var checked by remember { mutableStateOf(true) }
    var checked1 by remember { mutableStateOf(true) }

    MiuixSurface(
        modifier = Modifier.fillMaxSize()
    ) {
        MiuixBox(
            modifier = Modifier
                .displayCutoutPadding()
                .systemBarsPadding()
                .padding(top = 18.dp)
        ) {
            Column(
                modifier = Modifier
            ) {
                MiuixText(
                    text = "Text",
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                )

                MiuixSwitch(
                    checked = checked,
                    onCheckedChange = { checked = it },
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                )

                MiuixTextWithSwitch(
                    text = "Text with Switch",
                    checked = checked1,
                    onCheckedChange = { checked1 = it },
                )

                MiuixCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    MiuixText("Card")
                }
            }
        }
    }
}