import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
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
fun FourthPage(
    topAppBarScrollBehavior: MiuixScrollBehavior,
    padding: PaddingValues
) {
    val scrollLazyColumnState = rememberLazyListState()

    LazyColumn(
        state = scrollLazyColumnState,
        contentPadding = PaddingValues(top = padding.calculateTopPadding()),
        modifier = Modifier
            .nestedScroll(topAppBarScrollBehavior.nestedScrollConnection)
            .overScrollVertical(
                onOverscroll = {
                    topAppBarScrollBehavior.isPinned = it
                }
            )
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