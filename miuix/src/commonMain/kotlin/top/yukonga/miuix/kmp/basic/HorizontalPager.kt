package top.yukonga.miuix.kmp.basic

import androidx.compose.foundation.gestures.TargetedFlingBehavior
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

/**
 * A [HorizontalPager] component with Miuix style.
 *
 * @param pagerState The state of the [HorizontalPager].
 * @param modifier The modifier to be applied to the [HorizontalPager].
 * @param beyondViewportPageCount The count of pages beyond the viewport.
 * @param defaultWindowInsetsPadding Whether to apply default window insets padding to the [HorizontalPager].
 * @param pageContent The content of the [HorizontalPager].
 * @param userScrollEnabled Whether the scrolling via the user gestures or accessibility actions is allowed. Not recommended for use with overscroll.
 * @param flingBehavior The fling behavior of the [HorizontalPager].
 */
@Composable
fun HorizontalPager(
    pagerState: PagerState,
    modifier: Modifier = Modifier,
    beyondViewportPageCount: Int = 1,
    defaultWindowInsetsPadding: Boolean = true,
    userScrollEnabled: Boolean = false,
    flingBehavior: TargetedFlingBehavior = PagerDefaults.flingBehavior(state = pagerState),
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
        pageContent = { pageContent(it) },
        userScrollEnabled = userScrollEnabled,
        flingBehavior = flingBehavior
    )
}