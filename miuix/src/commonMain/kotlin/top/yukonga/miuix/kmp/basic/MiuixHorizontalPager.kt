package top.yukonga.miuix.kmp.basic

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.windowInsetsPadding
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
    defaultWindowInsetsPadding: Boolean = true,
    pageContent: @Composable (pageIndex: Int) -> Unit
) {
    HorizontalPager(
        modifier =
        if (defaultWindowInsetsPadding) {
            modifier
                .windowInsetsPadding(WindowInsets.displayCutout.only(WindowInsetsSides.Horizontal))
                .windowInsetsPadding(WindowInsets.navigationBars.only(WindowInsetsSides.Horizontal))
        } else {
            modifier
        },
        state = pagerState,
        beyondViewportPageCount = beyondViewportPageCount,
        verticalAlignment = Alignment.Top,
        pageContent = { pageContent(it) }
    )
}