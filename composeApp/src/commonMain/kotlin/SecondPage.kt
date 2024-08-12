import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import top.yukonga.miuix.kmp.MiuixScrollBehavior
import top.yukonga.miuix.kmp.MiuixSuperArrow
import top.yukonga.miuix.kmp.basic.MiuixText
import top.yukonga.miuix.kmp.utils.overScrollVertical

@Composable
fun SecondPage(
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
        items(200) {
            MiuixSuperArrow(
                leftAction = {
                    MiuixText(text = "Left")
                },
                title = "Title",
                summary = "Summary",
                rightText = "Right",
                onClick = {}
            )
        }
        item {
            Spacer(modifier = Modifier.height(padding.calculateBottomPadding()))
        }
    }
}