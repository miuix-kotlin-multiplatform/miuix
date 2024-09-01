import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import component.OtherComponent
import component.TextComponent
import top.yukonga.miuix.kmp.MiuixScrollBehavior
import top.yukonga.miuix.kmp.basic.MiuixLazyColumn

@Composable
fun MainPage(
    topAppBarScrollBehavior: MiuixScrollBehavior,
    padding: PaddingValues,
    enableOverScroll: Boolean
) {
    MiuixLazyColumn(
        enableOverScroll = enableOverScroll,
        contentPadding = PaddingValues(top = padding.calculateTopPadding()),
        topAppBarScrollBehavior = topAppBarScrollBehavior
    ) {
        item {
            TextComponent()
        }
        item {
            OtherComponent(padding)
        }
    }
}