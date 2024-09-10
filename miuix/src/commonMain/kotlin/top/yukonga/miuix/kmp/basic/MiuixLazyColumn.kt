package top.yukonga.miuix.kmp.basic

import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import top.yukonga.miuix.kmp.MiuixScrollBehavior
import top.yukonga.miuix.kmp.utils.enableOverscroll
import top.yukonga.miuix.kmp.utils.overScrollVertical
import top.yukonga.miuix.kmp.utils.rememberOverscrollFlingBehavior

/**
 * A [LazyColumn] that supports over-scroll and top app bar scroll behavior.
 *
 * @param modifier The modifier to apply to this layout.
 * @param enableOverScroll Whether to enable over-scroll.
 * @param contentPadding The padding to apply to the content.
 * @param topAppBarScrollBehavior The scroll behavior of the top app bar.
 * @param content The [Composable] content of the [LazyColumn].
 */
@Composable
fun MiuixLazyColumn(
    modifier: Modifier = Modifier,
    enableOverScroll: Boolean = true,
    contentPadding: PaddingValues,
    topAppBarScrollBehavior: MiuixScrollBehavior? = null,
    content: LazyListScope.() -> Unit
) {
    val scrollLazyColumnState = rememberLazyListState()
    val firstModifier = remember(enableOverScroll, enableOverscroll()) {
        if (enableOverScroll && enableOverscroll()) {
            modifier.overScrollVertical(onOverscroll = { topAppBarScrollBehavior?.isPinned = it })
        } else {
            modifier
        }
    }
    val finalModifier = remember(topAppBarScrollBehavior) {
        topAppBarScrollBehavior?.let {
            firstModifier.nestedScroll(it.nestedScrollConnection)
        } ?: firstModifier
    }
    val flingBehavior =
        if (enableOverScroll && enableOverscroll()) {
            rememberOverscrollFlingBehavior { scrollLazyColumnState }
        } else {
            ScrollableDefaults.flingBehavior()
        }

    LazyColumn(
        state = scrollLazyColumnState,
        flingBehavior = flingBehavior,
        contentPadding = contentPadding,
        modifier = finalModifier,
        content = content
    )
}