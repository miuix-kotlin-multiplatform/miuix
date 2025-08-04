// Copyright 2025, miuix-kotlin-multiplatform contributors
// SPDX-License-Identifier: Apache-2.0

package top.yukonga.miuix.kmp.basic

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.captionBar
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.layout.layout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import top.yukonga.miuix.kmp.theme.MiuixTheme
import top.yukonga.miuix.kmp.utils.BackHandler
import top.yukonga.miuix.kmp.utils.MiuixPopupUtils.Companion.PopupLayout
import top.yukonga.miuix.kmp.utils.SmoothRoundedCornerShape
import top.yukonga.miuix.kmp.utils.getWindowSize
import kotlin.math.min

/**
 * A popup with a list of items.
 *
 * @param show The show state of the [ListPopup].
 * @param popupModifier The modifier to be applied to the [ListPopup].
 * @param popupPositionProvider The [PopupPositionProvider] of the [ListPopup].
 * @param alignment The alignment of the [ListPopup].
 * @param enableWindowDim Whether to enable window dimming when the [ListPopup] is shown.
 * @param shadowElevation The elevation of the shadow of the [ListPopup].
 * @param onDismissRequest The callback when the [ListPopup] is dismissed.
 * @param maxHeight The maximum height of the [ListPopup]. If null, the height will be calculated automatically.
 * @param minWidth The minimum width of the [ListPopup].
 * @param content The [Composable] content of the [ListPopup]. You should use the [ListPopupColumn] in general.
 */
@Composable
fun ListPopup(
    show: MutableState<Boolean>,
    popupModifier: Modifier = Modifier,
    popupPositionProvider: PopupPositionProvider = ListPopupDefaults.DropdownPositionProvider,
    alignment: PopupPositionProvider.Align = PopupPositionProvider.Align.Right,
    enableWindowDim: Boolean = true,
    shadowElevation: Dp = 11.dp,
    onDismissRequest: (() -> Unit)? = null,
    maxHeight: Dp? = null,
    minWidth: Dp = 200.dp,
    content: @Composable () -> Unit
) {
    if (!show.value) return

    val windowSize by rememberUpdatedState(getWindowSize())
    var parentBounds by remember { mutableStateOf(IntRect.Zero) }

    Layout(
        modifier = Modifier
            .onGloballyPositioned { childCoordinates ->
                childCoordinates.parentLayoutCoordinates?.let { parentLayoutCoordinates ->
                    val positionInWindow = parentLayoutCoordinates.positionInWindow()
                    parentBounds = IntRect(
                        left = positionInWindow.x.toInt(),
                        top = positionInWindow.y.toInt(),
                        right = positionInWindow.x.toInt() + parentLayoutCoordinates.size.width,
                        bottom = positionInWindow.y.toInt() + parentLayoutCoordinates.size.height
                    )
                }
            }
    ) { _, _ -> layout(0, 0) {} }
    if (parentBounds == IntRect.Zero) return

    val density = LocalDensity.current
    val layoutDirection = LocalLayoutDirection.current
    val displayCutout = WindowInsets.displayCutout.asPaddingValues()
    val statusBars = WindowInsets.statusBars.asPaddingValues()
    val navigationBars = WindowInsets.navigationBars.asPaddingValues()
    val captionBar = WindowInsets.captionBar.asPaddingValues()

    val popupMargin = remember(windowSize, density) {
        with(density) {
            IntRect(
                left = popupPositionProvider.getMargins().calculateLeftPadding(layoutDirection).roundToPx(),
                top = popupPositionProvider.getMargins().calculateTopPadding().roundToPx(),
                right = popupPositionProvider.getMargins().calculateRightPadding(layoutDirection).roundToPx(),
                bottom = popupPositionProvider.getMargins().calculateBottomPadding().roundToPx()
            )
        }
    }

    val windowBounds = remember(windowSize, density) {
        with(density) {
            IntRect(
                left = displayCutout.calculateLeftPadding(layoutDirection).roundToPx(),
                top = statusBars.calculateTopPadding().roundToPx(),
                right = windowSize.width - displayCutout.calculateRightPadding(layoutDirection).roundToPx(),
                bottom = windowSize.height - navigationBars.calculateBottomPadding()
                    .roundToPx() - captionBar.calculateBottomPadding().roundToPx()
            )
        }
    }

    val transformOrigin = remember(windowSize, alignment, density) {
        val xInWindow = when (alignment) {
            PopupPositionProvider.Align.Right,
            PopupPositionProvider.Align.TopRight,
            PopupPositionProvider.Align.BottomRight -> parentBounds.right - popupMargin.right - with(density) { 64.dp.roundToPx() }

            else -> parentBounds.left + popupMargin.left + with(density) { 64.dp.roundToPx() }
        }
        val yInWindow = parentBounds.top + parentBounds.height / 2 - with(density) { 56.dp.roundToPx() }
        safeTransformOrigin(
            xInWindow / windowSize.width.toFloat(),
            yInWindow / windowSize.height.toFloat()
        )
    }

    PopupLayout(
        visible = show,
        enableWindowDim = enableWindowDim,
        transformOrigin = { transformOrigin },
    ) {
        val shape = remember { SmoothRoundedCornerShape(16.dp) }
        val elevationPx = with(density) { shadowElevation.toPx() }

        Box(
            modifier = popupModifier
                .pointerInput(onDismissRequest) {
                    detectTapGestures(
                        onTap = { onDismissRequest?.invoke() }
                    )
                }
                .layout { measurable, constraints ->
                    val placeable = measurable.measure(
                        constraints.copy(
                            minWidth = if (minWidth.roundToPx() <= windowSize.width) minWidth.roundToPx() else windowSize.width,
                            minHeight = if (50.dp.roundToPx() <= windowSize.height) 50.dp.roundToPx() else windowSize.height,
                            maxHeight = maxHeight?.roundToPx()?.coerceAtLeast(50.dp.roundToPx())
                                ?: (windowBounds.height - popupMargin.top - popupMargin.bottom).coerceAtLeast(
                                    50.dp.roundToPx()
                                ),
                            maxWidth = if (minWidth.roundToPx() <= windowSize.width) windowSize.width else minWidth.roundToPx()
                        )
                    )
                    val measuredSize = IntSize(placeable.width, placeable.height)

                    val calculatedOffset = popupPositionProvider.calculatePosition(
                        parentBounds,
                        windowBounds,
                        layoutDirection,
                        measuredSize,
                        popupMargin,
                        alignment
                    )

                    layout(constraints.maxWidth, constraints.maxHeight) {
                        placeable.place(calculatedOffset)
                    }
                }
        ) {
            Box(
                modifier = Modifier
                    .graphicsLayer(
                        clip = true,
                        shape = shape,
                        shadowElevation = elevationPx,
                        ambientShadowColor = MiuixTheme.colorScheme.windowDimming,
                        spotShadowColor = MiuixTheme.colorScheme.windowDimming
                    )
                    .background(MiuixTheme.colorScheme.surface)
            ) {
                content()
            }
        }
    }

    BackHandler(enabled = show.value) {
        onDismissRequest?.invoke()
    }
}

/**
 * A column that automatically aligns the width to the widest item
 * @param content The items
 */
@Composable
fun ListPopupColumn(
    content: @Composable () -> Unit
) {
    val scrollState = rememberScrollState()
    val currentContent by rememberUpdatedState(content)

    SubcomposeLayout(
        modifier = Modifier.verticalScroll(scrollState)
    ) { constraints ->
        var listHeight = 0
        val tempConstraints = constraints.copy(minWidth = 200.dp.roundToPx(), maxWidth = 288.dp.roundToPx(), minHeight = 0)

        // Measure pass to find the widest item
        val listWidth = subcompose("miuixPopupListFake", currentContent).map {
            it.measure(tempConstraints)
        }.maxOfOrNull { it.width }?.coerceIn(200.dp.roundToPx(), 288.dp.roundToPx()) ?: 200.dp.roundToPx()

        val childConstraints = constraints.copy(minWidth = listWidth, maxWidth = listWidth, minHeight = 0)

        // Actual measure and layout pass
        val placeables = subcompose("miuixPopupListReal", currentContent).map {
            val placeable = it.measure(childConstraints)
            listHeight += placeable.height
            placeable
        }
        layout(listWidth, min(constraints.maxHeight, listHeight)) {
            var currentY = 0
            placeables.forEach {
                it.place(0, currentY)
                currentY += it.height
            }
        }
    }
}

interface PopupPositionProvider {
    /**
     * Calculate the position (offset) of Popup
     *
     * @param anchorBounds Bounds of the anchored (parent) component
     * @param windowBounds Bounds of the safe area of window (excluding the [WindowInsets.Companion.statusBars], [WindowInsets.Companion.navigationBars] and [WindowInsets.Companion.captionBar])
     * @param layoutDirection [LayoutDirection]
     * @param popupContentSize Actual size of the popup content
     * @param popupMargin (Extra) Margins for the popup content. See [PopupPositionProvider.getMargins]
     * @param alignment Alignment of the popup (relative to the window). See [PopupPositionProvider.Align]
     */
    fun calculatePosition(
        anchorBounds: IntRect,
        windowBounds: IntRect,
        layoutDirection: LayoutDirection,
        popupContentSize: IntSize,
        popupMargin: IntRect,
        alignment: Align
    ): IntOffset

    /**
     * (Extra) Margins for the popup content.
     */
    fun getMargins(): PaddingValues

    /**
     * Position relative to the window, not relative to the anchor!
     */
    enum class Align {
        Left,
        Right,
        TopLeft,
        TopRight,
        BottomLeft,
        BottomRight
    }
}

object ListPopupDefaults {
    val DropdownPositionProvider = object : PopupPositionProvider {
        override fun calculatePosition(
            anchorBounds: IntRect,
            windowBounds: IntRect,
            layoutDirection: LayoutDirection,
            popupContentSize: IntSize,
            popupMargin: IntRect,
            alignment: PopupPositionProvider.Align
        ): IntOffset {
            val offsetX = if (alignment == PopupPositionProvider.Align.Right) {
                anchorBounds.right - popupContentSize.width - popupMargin.right
            } else {
                anchorBounds.left + popupMargin.left
            }
            val offsetY = if (windowBounds.bottom - anchorBounds.bottom > popupContentSize.height) {
                // Show below
                anchorBounds.bottom + popupMargin.bottom
            } else if (anchorBounds.top - windowBounds.top > popupContentSize.height) {
                // Show above
                anchorBounds.top - popupContentSize.height - popupMargin.top
            } else {
                // Middle
                anchorBounds.top + anchorBounds.height / 2 - popupContentSize.height / 2
            }
            return IntOffset(
                x = offsetX.coerceIn(
                    windowBounds.left,
                    (windowBounds.right - popupContentSize.width - popupMargin.right).coerceAtLeast(windowBounds.left)
                ),
                y = offsetY.coerceIn(
                    (windowBounds.top + popupMargin.top).coerceAtMost(windowBounds.bottom - popupContentSize.height - popupMargin.bottom),
                    windowBounds.bottom - popupContentSize.height - popupMargin.bottom
                )
            )
        }

        override fun getMargins(): PaddingValues {
            return PaddingValues(horizontal = 0.dp, vertical = 8.dp)
        }
    }
    val ContextMenuPositionProvider = object : PopupPositionProvider {
        override fun calculatePosition(
            anchorBounds: IntRect,
            windowBounds: IntRect,
            layoutDirection: LayoutDirection,
            popupContentSize: IntSize,
            popupMargin: IntRect,
            alignment: PopupPositionProvider.Align
        ): IntOffset {
            val offsetX: Int
            val offsetY: Int
            when (alignment) {
                PopupPositionProvider.Align.TopLeft -> {
                    offsetX = anchorBounds.left + popupMargin.left
                    offsetY = anchorBounds.bottom + popupMargin.top
                }

                PopupPositionProvider.Align.TopRight -> {
                    offsetX = anchorBounds.right - popupContentSize.width - popupMargin.right
                    offsetY = anchorBounds.bottom + popupMargin.top
                }

                PopupPositionProvider.Align.BottomLeft -> {
                    offsetX = anchorBounds.left + popupMargin.left
                    offsetY = anchorBounds.top - popupContentSize.height - popupMargin.bottom
                }

                PopupPositionProvider.Align.BottomRight -> {
                    offsetX = anchorBounds.right - popupContentSize.width - popupMargin.right
                    offsetY = anchorBounds.top - popupContentSize.height - popupMargin.bottom
                }

                else -> {
                    // Fallback
                    offsetX = if (alignment == PopupPositionProvider.Align.Right) {
                        anchorBounds.right - popupContentSize.width - popupMargin.right
                    } else {
                        anchorBounds.left + popupMargin.left
                    }
                    offsetY = if (windowBounds.bottom - anchorBounds.bottom > popupContentSize.height) {
                        // Show below
                        anchorBounds.bottom + popupMargin.bottom
                    } else if (anchorBounds.top - windowBounds.top > popupContentSize.height) {
                        // Show above
                        anchorBounds.top - popupContentSize.height - popupMargin.top
                    } else {
                        // Middle
                        anchorBounds.top + anchorBounds.height / 2 - popupContentSize.height / 2
                    }
                }
            }
            return IntOffset(
                x = offsetX.coerceIn(
                    windowBounds.left,
                    (windowBounds.right - popupContentSize.width - popupMargin.right).coerceAtLeast(windowBounds.left)
                ),
                y = offsetY.coerceIn(
                    (windowBounds.top + popupMargin.top).coerceAtMost(windowBounds.bottom - popupContentSize.height - popupMargin.bottom),
                    windowBounds.bottom - popupContentSize.height - popupMargin.bottom
                )
            )
        }

        override fun getMargins(): PaddingValues {
            return PaddingValues(horizontal = 20.dp, vertical = 0.dp)
        }
    }
}

/**
 * Ensure TransformOrigin is available.
 */
fun safeTransformOrigin(x: Float, y: Float): TransformOrigin {
    val safeX = if (x.isNaN() || x < 0f) 0f else x
    val safeY = if (y.isNaN() || y < 0f) 0f else y
    return TransformOrigin(safeX, safeY)
}
