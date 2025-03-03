package top.yukonga.miuix.kmp.basic

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import top.yukonga.miuix.kmp.theme.MiuixTheme
import top.yukonga.miuix.kmp.utils.SmoothRoundedCornerShape
import top.yukonga.miuix.kmp.utils.getWindowSize
import top.yukonga.miuix.kmp.utils.overScrollHorizontal

/**
 * A [TabRow] with Miuix style.
 *
 * @param tabs The text to be displayed in the [TabRow].
 * @param selectedTabIndex The selected tab index of the [TabRow]
 * @param modifier The modifier to be applied to the [TabRow].
 * @param backgroundColor The background color of the tab in [TabRow].
 * @param contentColor The text color of the tab in [TabRow].
 * @param selectedBackgroundColor The background color of the selected tab in [TabRow].
 * @param selectedColor The text color of the selected tab in [TabRow].
 * @param cornerRadius The round corner radius of the tab in [TabRow].
 * @param onSelect The callback when the tab of the [TabRow] is clicked.
 */
@Composable
fun TabRow(
    tabs: List<String>,
    selectedTabIndex: Int,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MiuixTheme.colorScheme.background,
    contentColor: Color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
    selectedBackgroundColor: Color = MiuixTheme.colorScheme.surface,
    selectedColor: Color = MiuixTheme.colorScheme.onSurface,
    cornerRadius: Dp = 8.dp,
    onSelect: ((Int) -> Unit)? = null,
) {
    val listState = rememberLazyListState()
    val windowSize = getWindowSize()
    val density = LocalDensity.current
    var tabWidth: Dp
    with(density) {
        tabWidth = ((windowSize.width.toDp() - (tabs.size - 1) * 9.dp) / tabs.size).coerceAtLeast(62.dp)
    }

    val shape = remember { derivedStateOf { SmoothRoundedCornerShape(cornerRadius) } }

    LazyRow(
        state = listState,
        modifier = modifier
            .fillMaxWidth().height(42.dp)
            .overScrollHorizontal(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(9.dp)
    ) {
        itemsIndexed(tabs) { index, tabText ->
            Surface(
                shape = shape.value,
                onClick = {
                    onSelect?.invoke(index)
                },
                enabled = onSelect != null,
                color = if (selectedTabIndex == index) selectedBackgroundColor else backgroundColor,
                modifier = Modifier
                    .fillMaxHeight()
                    .width(tabWidth)
                    .semantics { role = Role.Tab }
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = tabText,
                        color = if (selectedTabIndex == index) selectedColor else contentColor,
                        fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Normal,
                        maxLines = 1
                    )
                }
            }
        }
    }
}