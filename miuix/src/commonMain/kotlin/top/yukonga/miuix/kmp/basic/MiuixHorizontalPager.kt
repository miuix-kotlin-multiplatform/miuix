package top.yukonga.miuix.kmp.basic

import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun MiuixHorizontalPager(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    beyondViewportPageCount: Int = 1,
    pageContent: @Composable (pageIndex: Int) -> Unit
) {
    HorizontalPager(
        modifier = modifier,
        state = pagerState,
        beyondViewportPageCount = beyondViewportPageCount,
        verticalAlignment = Alignment.Top,
        pageContent = { pageContent(it) }
    )
}