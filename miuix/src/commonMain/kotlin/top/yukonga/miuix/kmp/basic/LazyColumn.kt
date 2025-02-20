package top.yukonga.miuix.kmp.basic

import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import top.yukonga.miuix.kmp.utils.Platform
import top.yukonga.miuix.kmp.utils.overScrollVertical
import top.yukonga.miuix.kmp.utils.platform
import top.yukonga.miuix.kmp.utils.rememberOverscrollFlingBehavior

/**
 * A [LazyColumn] that supports over-scroll and top app bar scroll behavior.
 *
 * @param modifier The modifier to apply to this layout.
 * @param state The state of the [LazyColumn].
 * @param contentPadding The padding to apply to the content.
 * @param userScrollEnabled Whether the user can scroll the [LazyColumn].
 * @param isEnabledOverScroll Whether the Overscroll is enabled.
 * @param topAppBarScrollBehavior The scroll behavior of the top app bar.
 * @param content The [Composable] content of the [LazyColumn].
 */
@Composable
fun LazyColumn(
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    reverseLayout: Boolean = false,
    verticalArrangement: Arrangement.Vertical =
        if (!reverseLayout) Arrangement.Top else Arrangement.Bottom,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    userScrollEnabled: Boolean = true,
    isEnabledOverScroll: () -> Boolean = { platform() == Platform.Android },
    topAppBarScrollBehavior: ScrollBehavior? = null,
    content: LazyListScope.() -> Unit
) {
    val firstModifier = remember(isEnabledOverScroll) {
        if (isEnabledOverScroll.invoke()) {
            modifier.overScrollVertical(onOverscroll = { topAppBarScrollBehavior?.isPinned = it }, isEnabled = isEnabledOverScroll)
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
        if (isEnabledOverScroll.invoke()) {
            rememberOverscrollFlingBehavior { state }
        } else {
            ScrollableDefaults.flingBehavior()
        }

    LazyColumn(
        modifier = finalModifier,
        state = state,
        contentPadding = contentPadding,
        reverseLayout = reverseLayout,
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment,
        flingBehavior = flingBehavior,
        userScrollEnabled = userScrollEnabled,
        content = content
    )
}