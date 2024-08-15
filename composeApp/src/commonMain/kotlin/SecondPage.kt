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
import top.yukonga.miuix.kmp.utils.rememberOverscrollFlingBehavior

@Composable
fun SecondPage(
    topAppBarScrollBehavior: MiuixScrollBehavior,
    padding: PaddingValues
) {
    val scrollLazyColumnState = rememberLazyListState()

    LazyColumn(
        state = scrollLazyColumnState,
        flingBehavior = rememberOverscrollFlingBehavior { scrollLazyColumnState },
        contentPadding = PaddingValues(top = padding.calculateTopPadding()),
        modifier = Modifier
            .overScrollVertical(onOverscroll = { topAppBarScrollBehavior.isPinned = it })
            .nestedScroll(topAppBarScrollBehavior.nestedScrollConnection)
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