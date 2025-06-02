// Copyright 2025, miuix-kotlin-multiplatform contributors
// SPDX-License-Identifier: Apache-2.0

package top.yukonga.miuix.kmp.basic

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import top.yukonga.miuix.kmp.utils.overScrollHorizontal

/**
 * A [TabRow] with Miuix style.
 *
 * @param tabs The text to be displayed in the [TabRow].
 * @param selectedTabIndex The selected tab index of the [TabRow]
 * @param modifier The modifier to be applied to the [TabRow].
 * @param colors The colors of the [TabRow].
 * @param minWidth The minimum width of the tab in [TabRow].
 * @param maxWidth The maximum width of the tab in [TabRow].
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
    maxWidth: Dp = TabRowDefaults.TabRowMaxWidth,
    height: Dp = TabRowDefaults.TabRowHeight,
    cornerRadius: Dp = TabRowDefaults.TabRowCornerRadius,
    onTabSelected: ((Int) -> Unit)? = null,
) {
    val currentOnTabSelected by rememberUpdatedState(onTabSelected)

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
            .height(height)
    ) {
        val config = rememberTabRowConfig(tabs, minWidth, maxWidth, cornerRadius, 9.dp, this.maxWidth)

        LazyRow(
            state = config.listState,
            modifier = Modifier
                .fillMaxSize()
                .overScrollHorizontal()
                .clip(config.shape),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(9.dp),
            overscrollEffect = null
        ) {
            itemsIndexed(tabs) { index, tabText ->
                TabItem(
                    text = tabText,
                    isSelected = selectedTabIndex == index,
                    onClick = { currentOnTabSelected?.invoke(index) },
                    enabled = currentOnTabSelected != null,
                    colors = colors,
                    shape = config.shape,
                    width = config.tabWidth
                )
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
 * @param maxWidth The maximum width of the tab in [TabRow].
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
    maxWidth: Dp = TabRowDefaults.TabRowWithContourMaxWidth,
    height: Dp = TabRowDefaults.TabRowHeight,
    cornerRadius: Dp = TabRowDefaults.TabRowWithContourCornerRadius,
    onTabSelected: ((Int) -> Unit)? = null,
) {
    val currentOnTabSelected by rememberUpdatedState(onTabSelected)
    val contourPadding = 5.dp
    val outerClipShape = remember(cornerRadius) { SmoothRoundedCornerShape(cornerRadius + contourPadding) }
    val innerClipShape = remember(cornerRadius) { SmoothRoundedCornerShape(cornerRadius) }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
            .height(height)
    ) {
        val lazyRowAvailableWidth = this.maxWidth - (contourPadding * 2)
        val config = rememberTabRowConfig(tabs, minWidth, maxWidth, cornerRadius, contourPadding, lazyRowAvailableWidth)

        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(outerClipShape)
                .background(color = colors.backgroundColor(false))
                .padding(contourPadding)
        ) {
            LazyRow(
                state = config.listState,
                modifier = Modifier
                    .fillMaxSize()
                    .overScrollHorizontal()
                    .clip(innerClipShape),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(contourPadding),
                overscrollEffect = null
            ) {
                itemsIndexed(tabs) { index, tabText ->
                    TabItemWithContour(
                        text = tabText,
                        isSelected = selectedTabIndex == index,
                        onClick = { currentOnTabSelected?.invoke(index) },
                        enabled = currentOnTabSelected != null,
                        colors = colors,
                        shape = config.shape,
                        width = config.tabWidth
                    )
                }
            }
        }
    }
}

@Composable
private fun TabItem(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    enabled: Boolean,
    colors: TabRowColors,
    shape: SmoothRoundedCornerShape,
    width: Dp
) {
    Surface(
        shape = shape,
        onClick = onClick,
        enabled = enabled,
        color = colors.backgroundColor(isSelected),
        modifier = Modifier
            .fillMaxHeight()
            .width(width)
            .semantics { role = Role.Tab }
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                color = colors.contentColor(isSelected),
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun TabItemWithContour(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    enabled: Boolean,
    colors: TabRowColors,
    shape: SmoothRoundedCornerShape,
    width: Dp
) {
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .width(width)
            .clip(shape)
            .background(colors.backgroundColor(isSelected))
            .clickable(enabled = enabled) { onClick() }
            .semantics { role = Role.Tab },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = colors.contentColor(isSelected),
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
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
 * @param lazyRowAvailableWidth The actual width available for the LazyRow's content (tabs + inter-tab spacing).
 */
@Composable
private fun rememberTabRowConfig(
    tabs: List<String>,
    minWidth: Dp,
    maxWidth: Dp,
    cornerRadius: Dp,
    spacing: Dp,
    lazyRowAvailableWidth: Dp
): TabRowConfig {
    val listState = rememberLazyListState()
    val tabWidth = remember(tabs.size, minWidth, maxWidth, lazyRowAvailableWidth, spacing) {
        calculateTabWidth(tabs.size, minWidth, maxWidth, spacing, lazyRowAvailableWidth)
    }
    val shape = remember(cornerRadius) { SmoothRoundedCornerShape(cornerRadius) }

    return TabRowConfig(tabWidth, shape, listState)
}

private fun calculateTabWidth(
    tabCount: Int,
    minWidth: Dp,
    maxWidth: Dp,
    spacing: Dp,
    availableWidth: Dp
): Dp {
    if (tabCount == 0) return minWidth

    val totalSpacing = if (tabCount > 1) (tabCount - 1) * spacing else 0.dp
    val contentWidth = availableWidth - totalSpacing

    return if (contentWidth <= 0.dp) {
        minWidth
    } else {
        val idealWidth = contentWidth / tabCount
        when {
            idealWidth < minWidth -> minWidth
            idealWidth > maxWidth -> {
                val totalMaxWidth = maxWidth * tabCount + totalSpacing
                if (totalMaxWidth < availableWidth) {
                    idealWidth
                } else {
                    maxWidth
                }
            }

            else -> idealWidth
        }
    }
}

object TabRowDefaults {

    /**
     * The default height of the [TabRow].
     */
    val TabRowHeight = 42.dp

    /**
     * The default corner radius of the [TabRow].
     */
    val TabRowCornerRadius = 12.dp

    /**
     * The default corner radius of the [TabRow] with contour style.
     */
    val TabRowWithContourCornerRadius = 8.dp

    /**
     * The default minimum width of the [TabRow].
     */
    val TabRowMinWidth = 76.dp

    /**
     * The default minimum width of the [TabRow] with contour style.
     */
    val TabRowWithContourMinWidth = 62.dp

    /**
     * The default maximum width of the tab in [TabRow].
     */
    val TabRowMaxWidth = 98.dp

    /**
     * The default minimum width of the tab in [TabRow] with contour style.
     */
    val TabRowWithContourMaxWidth = 84.dp

    /**
     * The default colors for the [TabRow].
     */
    @Composable
    fun tabRowColors(
        backgroundColor: Color = MiuixTheme.colorScheme.background,
        contentColor: Color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
        selectedBackgroundColor: Color = MiuixTheme.colorScheme.surface,
        selectedContentColor: Color = MiuixTheme.colorScheme.onSurface
    ): TabRowColors = TabRowColors(
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        selectedBackgroundColor = selectedBackgroundColor,
        selectedContentColor = selectedContentColor
    )
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