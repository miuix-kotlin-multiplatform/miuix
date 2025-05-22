// Copyright 2025, miuix-kotlin-multiplatform contributors
// SPDX-License-Identifier: Apache-2.0

package top.yukonga.miuix.kmp.basic

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import top.yukonga.miuix.kmp.utils.LocalOverScrollState
import top.yukonga.miuix.kmp.utils.OverScrollState
import top.yukonga.miuix.kmp.utils.getWindowSize
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sin

/**
 * A [PullToRefresh] component with Miuix style.
 * modified from the example provided by @sd086.
 *
 * @param modifier The modifier to be applied to the [PullToRefresh].
 * @param pullToRefreshState The state of the pull-to-refresh.
 * @param contentPadding The padding to be applied to the content.
 *   Only [PaddingValues.calculateTopPadding] is required.
 * @param color The color of the refresh indicator.
 * @param circleSize The size of the refresh indicator circle.
 * @param refreshTexts The texts to show when refreshing.
 * @param refreshTextStyle The style of the refresh text.
 * @param onRefresh The callback to be called when the refresh is triggered.
 * @param content the content to be shown when the [PullToRefresh] is expanded.
 */
@Composable
fun PullToRefresh(
    modifier: Modifier = Modifier,
    pullToRefreshState: PullToRefreshState,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    color: Color = PullToRefreshDefaults.color,
    circleSize: Dp = PullToRefreshDefaults.circleSize,
    refreshTexts: List<String> = PullToRefreshDefaults.refreshTexts,
    refreshTextStyle: TextStyle = PullToRefreshDefaults.refreshTextStyle,
    onRefresh: () -> Unit = {},
    content: @Composable () -> Unit
) {
    val currentOnRefresh by rememberUpdatedState(onRefresh)

    LaunchedEffect(pullToRefreshState.rawDragOffset) {
        pullToRefreshState.syncDragOffsetWithRawOffset()
    }
    val overScrollState = LocalOverScrollState.current
    val nestedScrollConnection = remember(pullToRefreshState, overScrollState) {
        pullToRefreshState.createNestedScrollConnection(overScrollState)
    }
    val pointerModifier = remember {
        Modifier.pointerInput(Unit) {
            awaitPointerEventScope {
                while (true) {
                    val event = awaitPointerEvent()
                    if (event.changes.all { !it.pressed }) {
                        pullToRefreshState.onPointerRelease()
                    } else {
                        pullToRefreshState.resetPointerReleased()
                    }
                }
            }
        }
    }
    LaunchedEffect(pullToRefreshState.pointerReleasedValue, pullToRefreshState.isRefreshing, currentOnRefresh) {
        pullToRefreshState.handlePointerReleased(currentOnRefresh)
    }

    val boxModifier = remember(modifier, nestedScrollConnection, pointerModifier) {
        modifier
            .nestedScroll(nestedScrollConnection)
            .then(pointerModifier)
    }

    CompositionLocalProvider(LocalPullToRefreshState provides pullToRefreshState) {
        Box(modifier = boxModifier) {
            Column {
                val headerModifier = remember(contentPadding) {
                    Modifier.offset(y = contentPadding.calculateTopPadding())
                }
                RefreshHeader(
                    modifier = headerModifier,
                    pullToRefreshState = pullToRefreshState,
                    circleSize = circleSize,
                    color = color,
                    refreshTexts = refreshTexts,
                    refreshTextStyle = refreshTextStyle
                )
                content()
            }
        }
    }
}

/**
 * Refresh header
 *
 * @param modifier The modifier to be applied to the [RefreshHeader]
 * @param pullToRefreshState The state of the pull-to-refresh
 * @param circleSize The size of the refresh indicator circle
 * @param color The color of the refresh indicator
 * @param refreshTexts The texts to show when refreshing
 * @param refreshTextStyle The style of the refresh text
 */
@Composable
fun RefreshHeader(
    modifier: Modifier = Modifier,
    pullToRefreshState: PullToRefreshState,
    circleSize: Dp,
    color: Color,
    refreshTexts: List<String>,
    refreshTextStyle: TextStyle
) {
    val hapticFeedback = LocalHapticFeedback.current
    val density = LocalDensity.current
    val dragOffset = pullToRefreshState.dragOffsetAnimatable.value
    val thresholdOffset = pullToRefreshState.refreshThresholdOffset
    val rotation by animateRotation()
    val refreshCompleteAnimProgress = pullToRefreshState.refreshCompleteAnimProgress

    LaunchedEffect(pullToRefreshState.refreshState) {
        if (pullToRefreshState.refreshState == RefreshState.ThresholdReached) {
            hapticFeedback.performHapticFeedback(HapticFeedbackType.GestureThresholdActivate)
        }
    }

    val refreshText by remember(pullToRefreshState.refreshState, pullToRefreshState.pullProgress) {
        derivedStateOf {
            when (pullToRefreshState.refreshState) {
                RefreshState.Idle -> ""
                RefreshState.Pulling -> if (pullToRefreshState.pullProgress > 0.5) refreshTexts[0] else ""
                RefreshState.ThresholdReached -> refreshTexts[1]
                RefreshState.Refreshing -> refreshTexts[2]
                RefreshState.RefreshComplete -> refreshTexts[3]
            }
        }
    }
    val textAlpha by remember(pullToRefreshState.refreshState, pullToRefreshState.pullProgress, refreshCompleteAnimProgress) {
        derivedStateOf {
            when (pullToRefreshState.refreshState) {
                RefreshState.Idle -> 0f
                RefreshState.Pulling -> if (pullToRefreshState.pullProgress > 0.6f) (pullToRefreshState.pullProgress - 0.5f) * 2f else 0f
                RefreshState.RefreshComplete -> (1f - refreshCompleteAnimProgress * 1.95f).coerceAtLeast(0f)
                else -> 1f
            }
        }
    }
    val sHeight by remember(
        density,
        pullToRefreshState.refreshState,
        circleSize,
        dragOffset,
        thresholdOffset,
        refreshCompleteAnimProgress
    ) {
        derivedStateOf {
            with(density) {
                when (pullToRefreshState.refreshState) {
                    RefreshState.Idle -> 0.dp
                    RefreshState.Pulling -> circleSize * pullToRefreshState.pullProgress
                    RefreshState.ThresholdReached -> circleSize + (dragOffset - thresholdOffset).toDp()
                    RefreshState.Refreshing -> circleSize
                    RefreshState.RefreshComplete -> circleSize.coerceIn(
                        0.dp,
                        circleSize - circleSize * refreshCompleteAnimProgress
                    )
                }
            }
        }
    }
    val headerHeight by remember(
        density,
        pullToRefreshState.refreshState,
        circleSize,
        dragOffset,
        thresholdOffset,
        refreshCompleteAnimProgress
    ) {
        derivedStateOf {
            with(density) {
                when (pullToRefreshState.refreshState) {
                    RefreshState.Idle -> 0.dp
                    RefreshState.Pulling -> (circleSize + 36.dp) * pullToRefreshState.pullProgress
                    RefreshState.ThresholdReached -> (circleSize + 36.dp) + (dragOffset - thresholdOffset).toDp()
                    RefreshState.Refreshing -> (circleSize + 36.dp)
                    RefreshState.RefreshComplete -> (circleSize + 36.dp).coerceIn(
                        0.dp,
                        (circleSize + 36.dp) - (circleSize + 36.dp) * refreshCompleteAnimProgress
                    )
                }
            }
        }
    }

    val columnModifier = remember(modifier, headerHeight) {
        modifier
            .fillMaxWidth()
            .height(headerHeight)
    }

    Column(
        modifier = columnModifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        val refreshContentModifier = remember(sHeight) {
            Modifier.height(sHeight)
        }
        RefreshContent(
            modifier = refreshContentModifier,
            circleSize = circleSize
        ) {
            val ringStrokeWidthPx = circleSize.toPx() / 11
            val indicatorRadiusPx = max(size.minDimension / 2, circleSize.toPx() / 3.5f)
            val center = Offset(circleSize.toPx() / 2, circleSize.toPx() / 1.8f)
            val alpha = (pullToRefreshState.pullProgress - 0.2f).coerceAtLeast(0f)
            when (pullToRefreshState.refreshState) {
                RefreshState.Idle -> return@RefreshContent

                RefreshState.Pulling -> drawInitialState(
                    center,
                    indicatorRadiusPx,
                    ringStrokeWidthPx,
                    color,
                    alpha
                )

                RefreshState.ThresholdReached -> drawThresholdExceededState(
                    center,
                    indicatorRadiusPx,
                    ringStrokeWidthPx,
                    color,
                    dragOffset,
                    thresholdOffset,
                    pullToRefreshState.maxDragDistancePx
                )

                RefreshState.Refreshing -> drawRefreshingState(
                    center,
                    indicatorRadiusPx,
                    ringStrokeWidthPx,
                    color,
                    rotation
                )

                RefreshState.RefreshComplete -> drawRefreshCompleteState(
                    center,
                    indicatorRadiusPx,
                    ringStrokeWidthPx,
                    color,
                    refreshCompleteAnimProgress
                )
            }
        }
        val textModifier = remember(textAlpha) {
            Modifier
                .padding(top = 6.dp)
                .alpha(textAlpha)
        }
        Text(
            text = refreshText,
            style = refreshTextStyle,
            color = color,
            modifier = textModifier
        )
    }
}

/**
 * Refresh content container
 *
 * @param modifier modifier
 * @param circleSize circle size, used for Canvas size
 * @param drawContent Canvas drawing lambda
 */
@Composable
private fun RefreshContent(
    modifier: Modifier = Modifier,
    circleSize: Dp,
    drawContent: DrawScope.() -> Unit
) {
    val boxModifier = remember(modifier) {
        modifier.fillMaxSize()
    }
    Box(
        modifier = boxModifier,
        contentAlignment = Alignment.TopCenter
    ) {
        val canvasModifier = remember(circleSize) {
            Modifier.size(circleSize)
        }
        Canvas(modifier = canvasModifier) {
            drawContent()
        }
    }
}

/**
 * Animate the rotation angle
 *
 * @return rotation angle state
 */
@Composable
private fun animateRotation(): State<Float> {
    val infiniteTransition = rememberInfiniteTransition(label = "rotationAnimation")
    return infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotationValue"
    )
}

/**
 * Draw the circle that expands to the initial state
 */
private fun DrawScope.drawInitialState(
    center: Offset,
    radius: Float,
    strokeWidth: Float,
    color: Color,
    alpha: Float
) {
    drawCircle(
        color = color.copy(alpha = alpha),
        radius = radius,
        center = center,
        style = Stroke(strokeWidth, cap = StrokeCap.Round)
    )
}

/**
 * Draw the circle that expands to the threshold exceeded state
 */
private fun DrawScope.drawThresholdExceededState(
    center: Offset,
    radius: Float,
    strokeWidth: Float,
    color: Color,
    dragOffset: Float,
    thresholdOffset: Float,
    maxDrag: Float
) {
    val lineLength = if (dragOffset > thresholdOffset) {
        min(max(dragOffset - thresholdOffset, 0f), maxDrag - thresholdOffset)
    } else 0f
    val topY = center.y
    val bottomY = center.y + lineLength
    drawArc(
        color = color,
        startAngle = 180f,
        sweepAngle = 180f,
        useCenter = false,
        topLeft = Offset(center.x - radius, topY - radius),
        size = Size(radius * 2, radius * 2),
        style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
    )
    drawArc(
        color = color,
        startAngle = 0f,
        sweepAngle = 180f,
        useCenter = false,
        topLeft = Offset(center.x - radius, bottomY - radius),
        size = Size(radius * 2, radius * 2),
        style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
    )
    drawLine(
        color = color,
        start = Offset(center.x - radius, topY),
        end = Offset(center.x - radius, bottomY),
        strokeWidth = strokeWidth,
        cap = StrokeCap.Round
    )
    drawLine(
        color = color,
        start = Offset(center.x + radius, topY),
        end = Offset(center.x + radius, bottomY),
        strokeWidth = strokeWidth,
        cap = StrokeCap.Round
    )
}

/**
 * Draw the circle that expands to the refreshing state
 */
private fun DrawScope.drawRefreshingState(
    center: Offset,
    radius: Float,
    strokeWidth: Float,
    color: Color,
    rotation: Float
) {
    drawCircle(
        color = color,
        radius = radius,
        center = center,
        style = Stroke(strokeWidth, cap = StrokeCap.Round)
    )
    val orbitRadius = radius - 2 * strokeWidth
    val angle = rotation * PI / 180.0
    val dotCenter = center + Offset(
        x = orbitRadius * cos(angle).toFloat(),
        y = orbitRadius * sin(angle).toFloat()
    )
    drawCircle(
        color = color,
        radius = strokeWidth,
        center = dotCenter
    )
}

/**
 * Draw the circle that expands to the refresh complete state
 */
private fun DrawScope.drawRefreshCompleteState(
    center: Offset,
    radius: Float,
    strokeWidth: Float,
    color: Color,
    refreshCompleteProgress: Float
) {
    val animatedRadius = radius * ((1f - refreshCompleteProgress).coerceAtLeast(0.9f))
    val alphaColor = color.copy(alpha = (1f - refreshCompleteProgress - 0.35f).coerceAtLeast(0f))
    val y = center.y - radius - strokeWidth + animatedRadius
    drawCircle(
        color = alphaColor,
        radius = animatedRadius,
        center = Offset(center.x, y),
        style = Stroke(strokeWidth, cap = StrokeCap.Round)
    )
}

/**
 * Refresh status
 */
sealed class RefreshState {
    /** Idle state */
    data object Idle : RefreshState()

    /** Pulling state */
    data object Pulling : RefreshState()

    /** Threshold reached state */
    data object ThresholdReached : RefreshState()

    /** Refreshing state */
    data object Refreshing : RefreshState()

    /** Refresh complete state */
    data object RefreshComplete : RefreshState()
}

/**
 * Remember the [PullToRefreshState] state object
 *
 * @return [PullToRefreshState] state object
 */
@Composable
fun rememberPullToRefreshState(): PullToRefreshState {
    val coroutineScope = rememberCoroutineScope()
    val currentWindowSize = getWindowSize()
    return remember(coroutineScope, currentWindowSize.height) {
        val screenHeight = currentWindowSize.height.toFloat()
        val maxDragDistancePx = screenHeight * maxDragRatio
        val refreshThresholdOffset = maxDragDistancePx * thresholdRatio
        PullToRefreshState(
            coroutineScope,
            screenHeight,
            refreshThresholdOffset
        )
    }
}

/**
 * The PullToRefreshState class
 *
 * @param coroutineScope Coroutine scope
 * @param maxDragDistancePx Maximum drag distance
 * @param refreshThresholdOffset Refresh threshold offset
 */
class PullToRefreshState(
    private val coroutineScope: CoroutineScope,
    val maxDragDistancePx: Float,
    val refreshThresholdOffset: Float
) {
    /** Original drag offset */
    var rawDragOffset by mutableFloatStateOf(0f)

    /** Drag offset animatable */
    val dragOffsetAnimatable = Animatable(0f)
    private var internalRefreshState by mutableStateOf<RefreshState>(RefreshState.Idle)

    /** Refresh state */
    val refreshState: RefreshState get() = internalRefreshState

    /** Whether it is refreshing */
    val isRefreshing: Boolean by derivedStateOf { refreshState is RefreshState.Refreshing }
    private var pointerReleased by mutableStateOf(false)

    /**  Whether it is rebounding */
    private var isRebounding by mutableStateOf(false)

    /** Pull progress */
    val pullProgress: Float by derivedStateOf {
        if (refreshThresholdOffset > 0f) {
            (dragOffsetAnimatable.value / refreshThresholdOffset).coerceIn(0f, 1f)
        } else 0f
    }
    private val _refreshCompleteAnimProgress = mutableFloatStateOf(1f)

    /** Refresh complete animation progress */
    val refreshCompleteAnimProgress: Float by derivedStateOf { _refreshCompleteAnimProgress.floatValue }

    /** Refresh in progress */
    private var isRefreshingInProgress by mutableStateOf(false)

    init {
        coroutineScope.launch {
            snapshotFlow { dragOffsetAnimatable.value }.collectLatest { offset ->
                internalRefreshState = when {
                    isRefreshing -> RefreshState.Refreshing
                    offset >= refreshThresholdOffset -> RefreshState.ThresholdReached
                    offset > 0 -> RefreshState.Pulling
                    else -> RefreshState.Idle
                }
            }
        }
    }

    private fun startRefreshing() {
        internalRefreshState = RefreshState.Refreshing
    }

    suspend fun syncDragOffsetWithRawOffset() {
        if (!dragOffsetAnimatable.isRunning) {
            dragOffsetAnimatable.snapTo(rawDragOffset)
        }
    }

    private suspend fun animateDragOffset(targetValue: Float, animationSpec: AnimationSpec<Float>) {
        dragOffsetAnimatable.animateTo(
            targetValue = targetValue,
            animationSpec = animationSpec
        )
    }

    internal fun resetPointerReleased() {
        pointerReleased = false
    }

    internal fun onPointerRelease() {
        pointerReleased = true
    }

    val pointerReleasedValue: Boolean get() = pointerReleased

    fun completeRefreshing(block: suspend () -> Unit) {
        if (!isRefreshingInProgress) return
        resetState(block)
    }

    private fun resetState(block: suspend () -> Unit) {
        performReset(block)
    }

    private fun performReset(block: suspend () -> Unit) {
        performAsyncReset(block)
    }

    private fun performAsyncReset(block: suspend () -> Unit) {
        coroutineScope.launch {
            try {
                block()
            } finally {
                internalRefreshState = RefreshState.RefreshComplete
                launch { startManualRefreshCompleteAnimation() }
            }
        }
    }

    private suspend fun startManualRefreshCompleteAnimation() {
        _refreshCompleteAnimProgress.floatValue = 0f
        val animatedValue = Animatable(0f)
        animatedValue.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 200,
                easing = CubicBezierEasing(0f, 0f, 0f, 0.37f)
            )
        ) {
            _refreshCompleteAnimProgress.floatValue = this.value
        }
        internalResetState()
    }

    private suspend fun internalResetState() {
        internalRefreshState = RefreshState.Idle
        dragOffsetAnimatable.snapTo(0f)
        rawDragOffset = 0f
        isRefreshingInProgress = false
    }

    fun createNestedScrollConnection(
        overScrollState: OverScrollState
    ): NestedScrollConnection = object : NestedScrollConnection {
        override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
            if (overScrollState.isOverScrollActive) return Offset.Zero
            if (isRefreshingInProgress || refreshState == RefreshState.Refreshing || refreshState == RefreshState.RefreshComplete) return Offset.Zero
            return if (source == NestedScrollSource.UserInput && available.y < 0 && rawDragOffset > 0f) {
                if (isRebounding && dragOffsetAnimatable.isRunning) {
                    coroutineScope.launch { dragOffsetAnimatable.stop() }
                    isRebounding = false
                }
                val delta = available.y.coerceAtLeast(-rawDragOffset)
                rawDragOffset += delta
                coroutineScope.launch { dragOffsetAnimatable.snapTo(rawDragOffset) }
                Offset(0f, delta)
            } else Offset.Zero
        }

        override fun onPostScroll(consumed: Offset, available: Offset, source: NestedScrollSource): Offset = when {
            overScrollState.isOverScrollActive || isRefreshingInProgress || refreshState == RefreshState.Refreshing || refreshState == RefreshState.RefreshComplete -> Offset.Zero
            source == NestedScrollSource.UserInput -> {
                if (available.y > 0f && consumed.y == 0f) {
                    if (isRebounding && dragOffsetAnimatable.isRunning) {
                        coroutineScope.launch { dragOffsetAnimatable.stop() }
                        isRebounding = false
                    }
                    val resistanceFactor = calculateResistanceFactor(rawDragOffset)
                    val effectiveY = available.y * resistanceFactor
                    val newOffset = rawDragOffset + effectiveY
                    val consumedY = effectiveY
                    rawDragOffset = newOffset
                    coroutineScope.launch { dragOffsetAnimatable.snapTo(newOffset) }
                    Offset(0f, consumedY)
                } else if (available.y < 0f) {
                    val newOffset = max(rawDragOffset + available.y, 0f)
                    val consumedY = rawDragOffset - newOffset
                    rawDragOffset = newOffset
                    Offset(0f, -consumedY)
                } else Offset.Zero
            }

            else -> Offset.Zero
        }
    }

    fun handlePointerReleased(onRefresh: () -> Unit) {
        if (isRefreshingInProgress) {
            resetPointerReleased()
            return
        }
        if (pointerReleasedValue && !isRefreshing) {
            if (rawDragOffset >= refreshThresholdOffset) {
                isRefreshingInProgress = true
                coroutineScope.launch {
                    try {
                        animateDragOffset(
                            targetValue = refreshThresholdOffset,
                            animationSpec = tween(
                                durationMillis = 200,
                                easing = CubicBezierEasing(0f, 0f, 0f, 0.37f)
                            )
                        )
                        rawDragOffset = refreshThresholdOffset
                        startRefreshing()
                        onRefresh()
                    } catch (_: Exception) {
                        internalResetState()
                    }
                }
            } else {
                isRebounding = true
                coroutineScope.launch {
                    try {
                        animateDragOffset(
                            targetValue = 0f,
                            animationSpec = tween(
                                durationMillis = 250,
                                easing = CubicBezierEasing(0.33f, 0f, 0.67f, 1f)
                            )
                        )
                        rawDragOffset = 0f
                    } finally {
                        isRebounding = false
                    }
                }
            }
            resetPointerReleased()
        }
    }

    private fun calculateResistanceFactor(offset: Float): Float {
        if (offset < refreshThresholdOffset) return 1.0f
        val overThreshold = offset - refreshThresholdOffset
        return 1.0f / (1.0f + overThreshold / refreshThresholdOffset * 0.8f)
    }
}

/** Maximum drag ratio */
internal const val maxDragRatio = 1 / 6f

/** Threshold ratio */
internal const val thresholdRatio = 1 / 4f

/**
 * [LocalPullToRefreshState] is used to provide the [PullToRefreshState] instance to the composition.
 *
 * @see PullToRefreshState
 */
val LocalPullToRefreshState = compositionLocalOf<PullToRefreshState?> { null }

/**
 * The default values of the [PullToRefresh] component.
 */
object PullToRefreshDefaults {

    /** The default color of the refresh indicator */
    val color: Color = Color.Gray

    /** The default size of the refresh indicator circle */
    val circleSize: Dp = 20.dp

    /** The default texts to show when refreshing */
    val refreshTexts = listOf(
        "Pull down to refresh",
        "Release to refresh",
        "Refreshing...",
        "Refreshed successfully"
    )

    /** The default style of the refresh text */
    val refreshTextStyle = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        color = color
    )
}
