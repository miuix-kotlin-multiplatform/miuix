import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import component.OtherComponent
import component.SecondComponent
import top.yukonga.miuix.kmp.MiuixScrollBehavior
import top.yukonga.miuix.kmp.utils.overScrollVertical

@Composable
fun ThirdPage(
    topAppBarScrollBehavior: MiuixScrollBehavior,
    padding: PaddingValues
) {
    val scrollLazyColumnState = rememberLazyListState()

    LazyColumn(
        state = scrollLazyColumnState,
        modifier = Modifier
            .padding(top = padding.calculateTopPadding())
            .nestedScroll(topAppBarScrollBehavior.nestedScrollConnection)
            .overScrollVertical()
    ) {
        items(20) {
            SecondComponent()
            OtherComponent(PaddingValues())
        }
        item {
            Spacer(modifier = Modifier.height(padding.calculateBottomPadding()))
        }
    }
}