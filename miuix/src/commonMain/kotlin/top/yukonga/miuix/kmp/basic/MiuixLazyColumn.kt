package top.yukonga.miuix.kmp.basic

import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import top.yukonga.miuix.kmp.MiuixScrollBehavior
import top.yukonga.miuix.kmp.utils.enableOverscroll
import top.yukonga.miuix.kmp.utils.overScrollVertical
import top.yukonga.miuix.kmp.utils.rememberOverscrollFlingBehavior

/**
 * A [LazyColumn] that supports over-scroll and top app bar scroll behavior.
 *
 * @param modifier The modifier to apply to this layout.
 * @param state The state of the [LazyColumn].
 * @param contentPadding The padding to apply to the content.
 * @param userScrollEnabled Whether the user can scroll the [LazyColumn].
 * @param enableOverScroll Whether to enable over-scroll.
 * @param topAppBarScrollBehavior The scroll behavior of the top app bar.
 * @param content The [Composable] content of the [LazyColumn].
 */
@Composable
fun MiuixLazyColumn(
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    userScrollEnabled: Boolean = true,
    enableOverScroll: Boolean = true,
    topAppBarScrollBehavior: MiuixScrollBehavior? = null,
    content: LazyListScope.() -> Unit
) {
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
            rememberOverscrollFlingBehavior { state }
        } else {
            ScrollableDefaults.flingBehavior()
        }

    LazyColumn(
        modifier = finalModifier,
        state = state,
        contentPadding = contentPadding,
        flingBehavior = flingBehavior,
        userScrollEnabled = userScrollEnabled,
        content = content
    )
}