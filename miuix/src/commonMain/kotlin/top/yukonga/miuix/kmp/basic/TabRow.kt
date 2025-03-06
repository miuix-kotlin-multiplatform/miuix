package top.yukonga.miuix.kmp.basic

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
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
 * @param colors The colors of the [TabRow].
 * @param minWidth The minimum width of the tab in [TabRow].
 * @param height The height of the [TabRow].
 * @param cornerRadius The round corner radius of the tab in [TabRow].
 * @param onTabSelected The callback when a tab is selected.
 */
@Composable
fun TabRow(
    tabs: List<String>,
    selectedTabIndex: Int,
    modifier: Modifier = Modifier,
    colors: TabRowColors = TabRowDefaults.tabRowColors(),
    minWidth: Dp = TabRowDefaults.TabRowMinWidth,
    height: Dp = TabRowDefaults.TabRowHeight,
    cornerRadius: Dp = TabRowDefaults.TabRowCornerRadius,
    onTabSelected: ((Int) -> Unit)? = null,
) {
    val config = rememberTabRowConfig(tabs, minWidth, cornerRadius)

    LazyRow(
        state = config.listState,
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .clip(config.shape)
            .overScrollHorizontal(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(9.dp)
    ) {
        itemsIndexed(tabs) { index, tabText ->
            Surface(
                shape = config.shape,
                onClick = { onTabSelected?.invoke(index) },
                enabled = onTabSelected != null,
                color = colors.backgroundColor(selectedTabIndex == index),
                modifier = Modifier
                    .fillMaxHeight()
                    .width(config.tabWidth)
                    .semantics { role = Role.Tab }
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = tabText,
                        color = colors.contentColor(selectedTabIndex == index),
                        fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Normal,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

/**
 * A [TabRowWithContour] with Miuix style.
 *
 * @param tabs The text to be displayed in the [TabRow].
 * @param selectedTabIndex The selected tab index of the [TabRow]
 * @param modifier The modifier to be applied to the [TabRow].
 * @param colors The colors of the [TabRow].
 * @param minWidth The minimum width of the tab in [TabRow].
 * @param height The height of the [TabRow].
 * @param cornerRadius The round corner radius of the tab in [TabRow].
 * @param onTabSelected The callback when a tab is selected.
 */
@Composable
fun TabRowWithContour(
    tabs: List<String>,
    selectedTabIndex: Int,
    modifier: Modifier = Modifier,
    colors: TabRowColors = TabRowDefaults.tabRowColors(),
    minWidth: Dp = TabRowDefaults.TabRowWithContourMinWidth,
    height: Dp = TabRowDefaults.TabRowHeight,
    cornerRadius: Dp = TabRowDefaults.TabRowWithContourCornerRadius,
    onTabSelected: ((Int) -> Unit)? = null,
) {
    val config = rememberTabRowConfig(tabs, minWidth, cornerRadius)

    LazyRow(
        state = config.listState,
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .clip(SmoothRoundedCornerShape(cornerRadius + 5.dp))
            .background(color = colors.backgroundColor(false))
            .padding(5.dp)
            .clip(SmoothRoundedCornerShape(cornerRadius))
            .overScrollHorizontal(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        itemsIndexed(tabs) { index, tabText ->
            val isSelected = index == selectedTabIndex

            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(config.tabWidth)
                    .clip(config.shape)
                    .background(colors.backgroundColor(selectedTabIndex == index))
                    .clickable(enabled = onTabSelected != null) {
                        onTabSelected?.invoke(index)
                    }
                    .semantics { role = Role.Tab },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = tabText,
                    color = colors.contentColor(selectedTabIndex == index),
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

/**
 * Base configuration for TabRow implementations.
 */
private data class TabRowConfig(
    val tabWidth: Dp,
    val shape: SmoothRoundedCornerShape,
    val listState: androidx.compose.foundation.lazy.LazyListState
)

/**
 * Prepare common TabRow configuration.
 */
@Composable
private fun rememberTabRowConfig(tabs: List<String>, minWidth: Dp, cornerRadius: Dp): TabRowConfig {
    val listState = rememberLazyListState()
    val windowSize = getWindowSize()
    val density = LocalDensity.current
    val tabWidth = with(density) {
        ((windowSize.width.toDp() - (tabs.size - 1) * 9.dp) / tabs.size).coerceAtLeast(minWidth)
    }
    val shape = remember { derivedStateOf { SmoothRoundedCornerShape(cornerRadius) } }

    return TabRowConfig(tabWidth, shape.value, listState)
}

object TabRowDefaults {

    /**
     * The default height of the [TabRow].
     */
    val TabRowHeight = 42.dp

    /**
     * The default corner radius of the [TabRow].
     */
    val TabRowCornerRadius = 8.dp

    /**
     * The default corner radius of the [TabRow] with contour style.
     */
    val TabRowWithContourCornerRadius = 10.dp

    /**
     * The default minimum width of the [TabRow].
     */
    val TabRowMinWidth = 76.dp

    /**
     * The default minimum width of the [TabRow] with contour style.
     */
    val TabRowWithContourMinWidth = 62.dp

    /**
     * The default colors for the [TabRow].
     */
    @Composable
    fun tabRowColors(
        backgroundColor: Color = MiuixTheme.colorScheme.background,
        contentColor: Color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
        selectedBackgroundColor: Color = MiuixTheme.colorScheme.surface,
        selectedContentColor: Color = MiuixTheme.colorScheme.onSurface
    ): TabRowColors {
        return TabRowColors(
            backgroundColor = backgroundColor,
            contentColor = contentColor,
            selectedBackgroundColor = selectedBackgroundColor,
            selectedContentColor = selectedContentColor
        )
    }
}

@Immutable
class TabRowColors(
    private val backgroundColor: Color,
    private val contentColor: Color,
    private val selectedBackgroundColor: Color,
    private val selectedContentColor: Color
) {
    @Stable
    internal fun backgroundColor(selected: Boolean): Color =
        if (selected) selectedBackgroundColor else backgroundColor

    @Stable
    internal fun contentColor(selected: Boolean): Color =
        if (selected) selectedContentColor else contentColor
}