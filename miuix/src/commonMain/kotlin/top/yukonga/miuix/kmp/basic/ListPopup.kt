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
import androidx.compose.ui.graphics.Shape
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

    val currentOnDismissRequest by rememberUpdatedState(onDismissRequest)
    val currentContent by rememberUpdatedState(content)

    var offset by remember { mutableStateOf(IntOffset.Zero) }
    val density = LocalDensity.current
    val layoutDirection = LocalLayoutDirection.current

    val getWindowSizeState = rememberUpdatedState(getWindowSize())
    var windowSize by remember { mutableStateOf(IntSize(getWindowSizeState.value.width, getWindowSizeState.value.height)) }
    var parentBounds by remember { mutableStateOf(IntRect.Zero) }

    val windowBounds by rememberUpdatedState(
        with(density) {
            IntRect(
                left = WindowInsets.displayCutout.asPaddingValues(density).calculateLeftPadding(layoutDirection).roundToPx(),
                top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding().roundToPx(),
                right = windowSize.width -
                        WindowInsets.displayCutout.asPaddingValues(density).calculateRightPadding(layoutDirection).roundToPx(),
                bottom = windowSize.height -
                        WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding().roundToPx() -
                        WindowInsets.captionBar.asPaddingValues().calculateBottomPadding().roundToPx()
            )
        }
    )

    var popupContentSize by remember { mutableStateOf(IntSize.Zero) }

    val rememberedPopupMargin = remember(popupPositionProvider, density, layoutDirection) {
        val popupMarginsPd = popupPositionProvider.getMargins()
        with(density) {
            IntRect(
                left = popupMarginsPd.calculateLeftPadding(layoutDirection).roundToPx(),
                top = popupMarginsPd.calculateTopPadding().roundToPx(),
                right = popupMarginsPd.calculateRightPadding(layoutDirection).roundToPx(),
                bottom = popupMarginsPd.calculateBottomPadding().roundToPx()
            )
        }
    }

    val rememberedTransformOrigin = remember(parentBounds, rememberedPopupMargin, windowSize, density, alignment) {
        val xInWindow = if (alignment in listOf(
                PopupPositionProvider.Align.Right,
                PopupPositionProvider.Align.TopRight,
                PopupPositionProvider.Align.BottomRight,
            )
        ) {
            parentBounds.right - rememberedPopupMargin.right - with(density) { 64.dp.roundToPx() }
        } else {
            parentBounds.left + rememberedPopupMargin.left + with(density) { 64.dp.roundToPx() }
        }
        val yInWindow = parentBounds.top + parentBounds.height / 2 - with(density) { 56.dp.roundToPx() }
        safeTransformOrigin(
            xInWindow / windowSize.width.toFloat(),
            yInWindow / windowSize.height.toFloat()
        )
    }

    // Anchor point for the popup
    Layout(
        modifier = Modifier.onGloballyPositioned { childCoordinates ->
            val parentLayoutCoordinates = childCoordinates.parentLayoutCoordinates
            if (parentLayoutCoordinates != null) {
                val positionInWindow = parentLayoutCoordinates.positionInWindow()
                parentBounds = IntRect(
                    left = positionInWindow.x.toInt(),
                    top = positionInWindow.y.toInt(),
                    right = positionInWindow.x.toInt() + parentLayoutCoordinates.size.width,
                    bottom = positionInWindow.y.toInt() + parentLayoutCoordinates.size.height
                )
            }
            val newUtilWindowSize = getWindowSizeState.value
            val newIntSize = IntSize(newUtilWindowSize.width, newUtilWindowSize.height)
            if (windowSize != newIntSize) {
                windowSize = newIntSize
            }
        }
    ) { _, _ ->
        layout(0, 0) {}
    }

    PopupLayout(
        visible = show,
        enableWindowDim = enableWindowDim,
        transformOrigin = { rememberedTransformOrigin },
    ) {
        val currentOffset by rememberUpdatedState(offset)
        val shape: Shape = remember { SmoothRoundedCornerShape(16.dp) }
        val rememberedElevationPx = remember(density, shadowElevation) { with(density) { shadowElevation.toPx() } }

        val popupContentBoxModifier = remember(
            popupModifier,
            currentOnDismissRequest,
            minWidth,
            maxHeight,
            popupPositionProvider,
            parentBounds,
            windowBounds,
            layoutDirection,
            rememberedPopupMargin,
            alignment,
            windowSize
        ) {
            popupModifier
                .pointerInput(currentOnDismissRequest) {
                    detectTapGestures {
                        currentOnDismissRequest?.invoke()
                    }
                }
                .layout { measurable, constraints ->
                    val placeable = measurable.measure(
                        constraints.copy(
                            minWidth = if (minWidth.roundToPx() <= windowSize.width) minWidth.roundToPx() else windowSize.width,
                            minHeight = if (50.dp.roundToPx() <= windowSize.height) 50.dp.roundToPx() else windowSize.height,
                            maxHeight = maxHeight?.roundToPx()?.coerceAtLeast(50.dp.roundToPx())
                                ?: (windowBounds.height - rememberedPopupMargin.top - rememberedPopupMargin.bottom).coerceAtLeast(
                                    50.dp.roundToPx()
                                ),
                            maxWidth = if (minWidth.roundToPx() <= windowSize.width) windowSize.width else minWidth.roundToPx()
                        )
                    )
                    val measuredSize = IntSize(placeable.width, placeable.height)
                    if (popupContentSize != measuredSize) {
                        popupContentSize = measuredSize
                    }

                    val calculatedOffset = popupPositionProvider.calculatePosition(
                        parentBounds,
                        windowBounds,
                        layoutDirection,
                        measuredSize,
                        rememberedPopupMargin,
                        alignment
                    )
                    if (offset != calculatedOffset) {
                        offset = calculatedOffset
                    }

                    layout(constraints.maxWidth, constraints.maxHeight) {
                        placeable.place(currentOffset)
                    }
                }
        }

        Box(modifier = popupContentBoxModifier) {
            val surfaceColor = MiuixTheme.colorScheme.surface
            val windowDimmingColor = MiuixTheme.colorScheme.windowDimming
            val graphicsAndBackgroundModifier = remember(shape, rememberedElevationPx, surfaceColor, windowDimmingColor) {
                Modifier
                    .graphicsLayer(
                        clip = true,
                        shape = shape,
                        shadowElevation = rememberedElevationPx,
                        ambientShadowColor = windowDimmingColor,
                        spotShadowColor = windowDimmingColor
                    )
                    .background(surfaceColor)
            }
            Box(modifier = graphicsAndBackgroundModifier) {
                currentContent()
            }
        }
    }

    BackHandler(enabled = show.value) {
        currentOnDismissRequest?.invoke()
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
