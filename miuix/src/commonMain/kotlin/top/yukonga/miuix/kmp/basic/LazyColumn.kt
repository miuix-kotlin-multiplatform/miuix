package top.yukonga.miuix.kmp.basic

import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import top.yukonga.miuix.kmp.utils.Platform
import top.yukonga.miuix.kmp.utils.overScrollVertical
import top.yukonga.miuix.kmp.utils.platform

/**
 * A [LazyColumn] that supports over-scroll by default.
 *
 * @param modifier The modifier to apply to this layout.
 * @param state the state object to be used to control or observe the list's state.
 * @param contentPadding a padding around the whole content. This will add padding for the. content
 *   after it has been clipped, which is not possible via [modifier] param. You can use it to add a
 *   padding before the first item or after the last one. If you want to add a spacing between each
 *   item use [verticalArrangement].
 * @param reverseLayout reverse the direction of scrolling and layout. When `true`, items are laid
 *   out in the reverse order and [LazyListState.firstVisibleItemIndex] == 0 means that column is
 *   scrolled to the bottom. Note that [reverseLayout] does not change the behavior of
 *   [verticalArrangement], e.g. with [Arrangement.Top] (top) 123### (bottom) becomes (top) 321###
 *   (bottom).
 * @param verticalArrangement The vertical arrangement of the layout's children. This allows to add
 *   a spacing between items and specify the arrangement of the items when we have not enough of
 *   them to fill the whole minimum size.
 * @param horizontalAlignment the horizontal alignment applied to the items.
 * @param flingBehavior logic describing fling behavior.
 * @param userScrollEnabled whether the scrolling via the user gestures or accessibility actions is
 *   allowed. You can still scroll programmatically using the state even when it is disabled.
 * @param isEnabledOverScroll whether the over-scroll effect is enabled. By default, it is enabled on Android.
 * @param content a block which describes the content. Inside this block you can use methods like
 *   [LazyListScope.item] to add a single item or [LazyListScope.items] to add a list of items.
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
    flingBehavior: FlingBehavior = ScrollableDefaults.flingBehavior(),
    userScrollEnabled: Boolean = true,
    isEnabledOverScroll: () -> Boolean = { platform() == Platform.Android },
    content: LazyListScope.() -> Unit
) {
    val firstModifier = remember(isEnabledOverScroll) {
        if (isEnabledOverScroll.invoke()) {
            modifier.overScrollVertical(isEnabled = isEnabledOverScroll)
        } else {
            modifier
        }
    }
    LazyColumn(
        modifier = firstModifier,
        state = state,
        contentPadding = contentPadding,
        reverseLayout = reverseLayout,
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment,
        flingBehavior = flingBehavior,
        userScrollEnabled = userScrollEnabled,
        overscrollEffect = null,
        content = content
    )
}