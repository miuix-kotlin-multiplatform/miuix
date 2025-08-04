// Copyright 2025, miuix-kotlin-multiplatform contributors
// SPDX-License-Identifier: Apache-2.0

package top.yukonga.miuix.kmp.basic

import androidx.compose.animation.core.Animatable
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
import androidx.compose.ui.unit.Velocity
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
import kotlin.math.sin

/**
 * A container that supports the "pull-to-refresh" gesture.
 *
 * This composable follows a hoisted state pattern, where the logical `isRefreshing` state
 * is managed by the caller (e.g., a ViewModel). It coordinates nested scrolling to enable a
 * pull-to-refresh action, displays a customizable indicator, and triggers a callback when a
 * refresh is requested.
 *
 * @param isRefreshing A boolean state representing whether a refresh is currently in progress.
 *   This state should be hoisted and is the source of truth for the refresh operation.
 * @param onRefresh A lambda to be invoked when a refresh is triggered by the user. This lambda
 *   should initiate the data loading and is responsible for eventually setting `isRefreshing`
 *   back to `false` upon completion.
 * @param modifier The modifier to be applied to this container.
 * @param pullToRefreshState The state object that manages the UI and animations of the indicator.
 *   See [rememberPullToRefreshState].
 * @param contentPadding The padding to be applied to the content. The top padding is used to
 *   correctly offset the refresh indicator.
 * @param topAppBarScrollBehavior An optional [ScrollBehavior] for a `TopAppBar` to coordinate
 *   scrolling between the app bar and the pull-to-refresh gesture.
 * @param color The color of the refresh indicator.
 * @param circleSize The size of the refresh indicator's animated circle.
 * @param refreshTexts A list of strings representing the text shown in different states.
 * @param refreshTextStyle The [TextStyle] for the refresh indicator text.
 * @param content The content to be displayed inside the container.
 */
@Composable
fun PullToRefresh(
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
    pullToRefreshState: PullToRefreshState = rememberPullToRefreshState(),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    topAppBarScrollBehavior: ScrollBehavior? = null,
    color: Color = PullToRefreshDefaults.color,
    circleSize: Dp = PullToRefreshDefaults.circleSize,
    refreshTexts: List<String> = PullToRefreshDefaults.refreshTexts,
    refreshTextStyle: TextStyle = PullToRefreshDefaults.refreshTextStyle,
    content: @Composable () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val overScrollState = LocalOverScrollState.current

    // This effect acts as the bridge between the hoisted `isRefreshing` logical state
    // and the `pullToRefreshState` UI state object. It ensures the UI state always
    // reflects the logical state, even after lifecycle events.
    LaunchedEffect(isRefreshing) {
        if (isRefreshing) {
            pullToRefreshState.startRefreshing()
        } else {
            pullToRefreshState.finishRefreshing()
        }
    }

    // This connection establishes the chain of responsibility for nested scroll events.
    val nestedScrollConnection =
        remember(pullToRefreshState, topAppBarScrollBehavior, overScrollState) {
            createPullToRefreshConnection(
                pullToRefreshState,
                topAppBarScrollBehavior,
                overScrollState
            )
        }

    // A modifier to detect when the user releases their finger and trigger the refresh logic.
    val pointerModifier = Modifier.pointerInput(Unit) {
        awaitPointerEventScope {
            while (true) {
                val event = awaitPointerEvent()
                if (pullToRefreshState.refreshState != RefreshState.RefreshComplete
                    && event.changes.all { !it.pressed }
                ) {
                    coroutineScope.launch {
                        pullToRefreshState.handlePointerRelease(onRefresh)
                    }
                }
            }
        }
    }

    CompositionLocalProvider(
        LocalPullToRefreshState provides pullToRefreshState,
    ) {
        val boxModifier = modifier
            .nestedScroll(nestedScrollConnection)
            .then(pointerModifier)

        Box(modifier = boxModifier) {
            Column {
                RefreshHeader(
                    modifier = Modifier.offset(y = contentPadding.calculateTopPadding()),
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
 * Creates and remembers a [PullToRefreshState] across recompositions.
 *
 * This state object is responsible for managing the visual aspects of the refresh indicator,
 * such as its position and animation. The logical `isRefreshing` state should be hoisted and
 * managed separately.
 *
 * @return A remembered instance of [PullToRefreshState].
 */
@Composable
fun rememberPullToRefreshState(): PullToRefreshState {
    val coroutineScope = rememberCoroutineScope()

    // The state object is created using `remember` as it's a runtime UI state manager.
    // The logical `isRefreshing` state, which survives process death, is hoisted.
    val state = remember {
        PullToRefreshState(coroutineScope)
    }

    // Update context-dependent properties on the state instance to ensure it's always current.
    val windowSize by rememberUpdatedState(getWindowSize())
    state.maxDragDistancePx = windowSize.height.toFloat()// * maxDragRatio
    state.refreshThresholdOffset = windowSize.height.toFloat() * maxDragRatio * thresholdRatio

    return state
}


/** Represents the various visual states of the pull-to-refresh indicator. */
sealed interface RefreshState {
    data object Idle : RefreshState
    data object Pulling : RefreshState
    data object ThresholdReached : RefreshState
    data object Refreshing : RefreshState
    data object RefreshComplete : RefreshState
}

/**
 * A UI state holder for the [PullToRefresh] composable.
 *
 * This class manages the internal state machine for animations and nested scroll interactions,
 * driven by the hoisted `isRefreshing` boolean.
 *
 * @param coroutineScope A [CoroutineScope] used to launch animations and state updates.
 */
class PullToRefreshState(
    internal var coroutineScope: CoroutineScope
) {
    internal var maxDragDistancePx: Float = 0f
    internal var refreshThresholdOffset: Float = 0f

    /** The raw drag offset in pixels, before any animation or resistance is applied. */
    var rawDragOffset by mutableFloatStateOf(0f)

    /** An animatable value for the drag offset, used to drive smooth transitions. */
    val dragOffsetAnimatable = Animatable(0f)
    private var internalRefreshState by mutableStateOf<RefreshState>(RefreshState.Idle)

    /** The current visual [RefreshState] of the component. */
    val refreshState: RefreshState get() = internalRefreshState

    /** A derived state that is true if the component is in the [RefreshState.Refreshing] state. */
    val isRefreshing: Boolean by derivedStateOf { refreshState is RefreshState.Refreshing }

    /** The progress of the pull gesture, from 0.0 to 1.0, until the threshold is reached. */
    val pullProgress: Float by derivedStateOf {
        if (refreshThresholdOffset > 0f) {
            (dragOffsetAnimatable.value / refreshThresholdOffset).coerceIn(0f, 1f)
        } else 0f
    }

    private var isRebounding by mutableStateOf(false)
    private val _refreshCompleteAnimProgress = mutableFloatStateOf(1f)
    internal val refreshCompleteAnimProgress: Float by derivedStateOf { _refreshCompleteAnimProgress.floatValue }
    private var isRefreshingInProgress by mutableStateOf(false)
    private var isTriggerRefresh by mutableStateOf(false)

    init {
        // This flow observes the animated drag offset and updates the state machine accordingly.
        coroutineScope.launch {
            snapshotFlow { dragOffsetAnimatable.value }.collectLatest { offset ->
                if (!isRefreshing && !isRebounding) {
                    internalRefreshState = when {
                        refreshThresholdOffset > 0f && offset >= refreshThresholdOffset -> RefreshState.ThresholdReached
                        offset > 0 -> RefreshState.Pulling
                        else -> RefreshState.Idle
                    }
                }
            }
        }
    }

    /**
     * Called when the hoisted `isRefreshing` state becomes true.
     * Forces the state machine into the refreshing state and moves the indicator.
     */
    internal fun startRefreshing() {
        if (!isRefreshingInProgress) {
            isRefreshingInProgress = true
            coroutineScope.launch {
                try {
                    dragOffsetAnimatable.animateTo(
                        refreshThresholdOffset,
                        animationSpec = tween(easing = CubicBezierEasing(0.33f, 0f, 0.67f, 1f))
                    )
                    rawDragOffset = refreshThresholdOffset
                } finally {
                    internalRefreshState = RefreshState.Refreshing
                }
            }
        }
    }

    /**
     * Called when the hoisted `isRefreshing` state becomes false.
     * Triggers the completion animation and resets the state.
     */
    internal fun finishRefreshing() {
        if (isRefreshingInProgress) {
            performAsyncReset()
        }
    }

    /** Handles the pointer release event to either trigger a refresh or rebound the indicator. */
    internal suspend fun handlePointerRelease(onRefresh: () -> Unit) {
        if (isRefreshing) return

        if (rawDragOffset >= refreshThresholdOffset) {
            // If pulled past threshold, trigger the onRefresh callback.
            // The hoisted state will change, which will then call startRefreshing().
            isTriggerRefresh = true
            onRefresh()
        } else {
            // If not pulled past threshold, rebound to the resting state.
            isRebounding = true
            try {
                dragOffsetAnimatable.animateTo(
                    0f,
                    animationSpec = tween(easing = CubicBezierEasing(0.33f, 0f, 0.67f, 1f))
                )
                rawDragOffset = 0f
            } finally {
                isRebounding = false
            }
        }
    }

    private fun performAsyncReset() {
        coroutineScope.launch {
            internalRefreshState = RefreshState.RefreshComplete
            startManualRefreshCompleteAnimation()
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
        dragOffsetAnimatable.snapTo(0f)
        rawDragOffset = 0f
        isTriggerRefresh = false
        isRefreshingInProgress = false
        internalRefreshState = RefreshState.Idle
    }

    /** Creates a [NestedScrollConnection] for the pull-to-refresh logic itself. */
    internal fun createNestedScrollConnection(
        overScrollState: OverScrollState
    ): NestedScrollConnection = object : NestedScrollConnection {
        override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
            if (overScrollState.isOverScrollActive) return Offset.Zero

            // If the refresh is in progress, consume all scroll events.
            if (refreshState == RefreshState.RefreshComplete
                || refreshState == RefreshState.Refreshing
                || isTriggerRefresh
            ) {
                return available
            }

            // When pulling up while the indicator is visible, consume the scroll to hide it.
            if (source == NestedScrollSource.UserInput && available.y < 0 && rawDragOffset > 0f) {
                if (isRebounding && dragOffsetAnimatable.isRunning) {
                    coroutineScope.launch { dragOffsetAnimatable.stop() }
                    isRebounding = false
                }
                val delta = available.y.coerceAtLeast(-rawDragOffset)
                rawDragOffset += delta
                coroutineScope.launch { dragOffsetAnimatable.snapTo(rawDragOffset) }
                return Offset(0f, delta)
            }
            return Offset.Zero
        }

        override fun onPostScroll(
            consumed: Offset, available: Offset, source: NestedScrollSource
        ): Offset {

            // If the refresh is in progress, consume all scroll events.
            if (refreshState == RefreshState.RefreshComplete
                || refreshState == RefreshState.Refreshing
                || isTriggerRefresh
            ) {
                return available
            }

            // When pulling down after the content is at its top, consume the scroll to show the indicator.
            if (source == NestedScrollSource.UserInput && available.y > 0f && consumed.y == 0f) {
                if (isRebounding && dragOffsetAnimatable.isRunning) {
                    coroutineScope.launch { dragOffsetAnimatable.stop() }
                    isRebounding = false
                }
                val resistanceFactor = 1f / (1f + rawDragOffset / refreshThresholdOffset * 0.8f)
                val effectiveY = available.y * resistanceFactor
                rawDragOffset += effectiveY
                coroutineScope.launch { dragOffsetAnimatable.snapTo(rawDragOffset) }
                return Offset(0f, effectiveY)
            }
            return Offset.Zero
        }
    }
}

/**
 * A factory function to create the main [NestedScrollConnection] for the [PullToRefresh] component.
 */
private fun createPullToRefreshConnection(
    pullToRefreshState: PullToRefreshState,
    topAppBarScrollBehavior: ScrollBehavior?,
    overScrollState: OverScrollState
): NestedScrollConnection = object : NestedScrollConnection {
    override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
        when (pullToRefreshState.refreshState) {
            RefreshState.Idle -> {
                val consumedByAppBar =
                    topAppBarScrollBehavior?.nestedScrollConnection
                        ?.onPreScroll(available, source) ?: Offset.Zero
                val remaining = available - consumedByAppBar
                val consumedByRefresh = pullToRefreshState
                    .createNestedScrollConnection(overScrollState)
                    .onPreScroll(remaining, source)
                return consumedByAppBar + consumedByRefresh
            }

            RefreshState.RefreshComplete, RefreshState.Refreshing -> {
                return available
            }

            else -> {
                val consumedByRefresh = pullToRefreshState.createNestedScrollConnection(overScrollState)
                    .onPreScroll(available, source)
                val remaining = available - consumedByRefresh
                val consumedByAppBar =
                    topAppBarScrollBehavior?.nestedScrollConnection
                        ?.onPreScroll(remaining, source) ?: Offset.Zero
                return consumedByRefresh + consumedByAppBar
            }
        }
    }

    override fun onPostScroll(
        consumed: Offset, available: Offset, source: NestedScrollSource
    ): Offset {
        when (pullToRefreshState.refreshState) {
            RefreshState.RefreshComplete, RefreshState.Refreshing -> {
                return available
            }

            else -> {
                val consumedByAppBar = topAppBarScrollBehavior?.nestedScrollConnection
                    ?.onPostScroll(consumed, available, source) ?: Offset.Zero
                val remaining = available - consumedByAppBar
                val consumedByRefresh = pullToRefreshState
                    .createNestedScrollConnection(overScrollState)
                    .onPostScroll(consumed, remaining, source)
                return consumedByAppBar + consumedByRefresh
            }
        }
    }

    override suspend fun onPreFling(available: Velocity): Velocity {
        if (pullToRefreshState.refreshState != RefreshState.Idle) {
            return available
        }
        return topAppBarScrollBehavior?.nestedScrollConnection
            ?.onPreFling(available) ?: Velocity.Zero
    }

    override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
        if (pullToRefreshState.refreshState != RefreshState.Idle) {
            return available
        }
        return topAppBarScrollBehavior?.nestedScrollConnection
            ?.onPostFling(consumed, available) ?: Velocity.Zero
    }
}

@Composable
private fun RefreshHeader(
    modifier: Modifier = Modifier,
    pullToRefreshState: PullToRefreshState,
    circleSize: Dp,
    color: Color,
    refreshTexts: List<String>,
    refreshTextStyle: TextStyle
) {
    val hapticFeedback = LocalHapticFeedback.current
    val density = LocalDensity.current

    LaunchedEffect(pullToRefreshState.refreshState) {
        if (pullToRefreshState.refreshState == RefreshState.ThresholdReached) {
            hapticFeedback.performHapticFeedback(HapticFeedbackType.GestureThresholdActivate)
        }
    }

    val refreshDisplayInfo by remember(pullToRefreshState) {
        derivedStateOf {
            when (pullToRefreshState.refreshState) {
                RefreshState.Idle -> "" to 0f
                RefreshState.Pulling -> {
                    val progress = pullToRefreshState.pullProgress
                    val text = refreshTexts.getOrElse(0) { "" }
                    val alpha = if (progress > 0.6f) (progress - 0.5f) * 2f else 0f
                    if (progress > 0.5) text to alpha else "" to 0f
                }

                RefreshState.ThresholdReached -> refreshTexts.getOrElse(1) { "" } to 1f
                RefreshState.Refreshing -> refreshTexts.getOrElse(2) { "" } to 1f
                RefreshState.RefreshComplete -> {
                    val text = refreshTexts.getOrElse(3) { "" }
                    val alpha = (1f - pullToRefreshState.refreshCompleteAnimProgress * 1.95f)
                        .coerceAtLeast(0f)
                    text to alpha
                }
            }
        }
    }

    val heightInfo by remember(pullToRefreshState) {
        derivedStateOf {
            val dragOffset = pullToRefreshState.dragOffsetAnimatable.value
            val threshold = pullToRefreshState.refreshThresholdOffset
            val progress = pullToRefreshState.pullProgress
            val completeProgress = pullToRefreshState.refreshCompleteAnimProgress

            val indicatorHeight: Dp
            val headerHeight: Dp

            when (pullToRefreshState.refreshState) {
                RefreshState.Idle -> 0.dp to 0.dp
                RefreshState.Pulling -> {
                    indicatorHeight = circleSize * progress
                    headerHeight = (circleSize + 36.dp) * progress
                    indicatorHeight to headerHeight
                }

                RefreshState.ThresholdReached -> {
                    val offsetDp = with(density) { (dragOffset - threshold).toDp() }
                    indicatorHeight = circleSize + offsetDp
                    headerHeight = (circleSize + 36.dp) + offsetDp
                    indicatorHeight to headerHeight
                }

                RefreshState.Refreshing -> circleSize to (circleSize + 36.dp)
                RefreshState.RefreshComplete -> {
                    indicatorHeight = circleSize * (1 - completeProgress)
                    headerHeight = (circleSize + 36.dp) * (1 - completeProgress)
                    indicatorHeight to headerHeight
                }
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(heightInfo.second),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        RefreshIndicator(
            modifier = Modifier.height(heightInfo.first),
            pullToRefreshState = pullToRefreshState,
            circleSize = circleSize,
            color = color
        )
        Text(
            text = refreshDisplayInfo.first,
            style = refreshTextStyle,
            color = color,
            modifier = Modifier.padding(top = 6.dp).alpha(refreshDisplayInfo.second)
        )
    }
}

@Composable
private fun RefreshIndicator(
    modifier: Modifier = Modifier,
    pullToRefreshState: PullToRefreshState,
    circleSize: Dp,
    color: Color
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        val rotation by animateRotation()
        Canvas(modifier = Modifier.size(circleSize)) {
            val ringStrokeWidthPx = circleSize.toPx() / 11
            val indicatorRadiusPx = max(size.minDimension / 2, circleSize.toPx() / 3.5f)
            val center = Offset(circleSize.toPx() / 2, circleSize.toPx() / 1.8f)

            when (pullToRefreshState.refreshState) {
                RefreshState.Idle -> return@Canvas
                RefreshState.Pulling -> {
                    val alpha = (pullToRefreshState.pullProgress - 0.2f).coerceAtLeast(0f)
                    drawPullingIndicator(center, indicatorRadiusPx, ringStrokeWidthPx, color, alpha)
                }

                RefreshState.ThresholdReached -> {
                    drawThresholdIndicator(
                        center, indicatorRadiusPx, ringStrokeWidthPx, color,
                        pullToRefreshState.dragOffsetAnimatable.value,
                        pullToRefreshState.refreshThresholdOffset,
                        pullToRefreshState.maxDragDistancePx
                    )
                }

                RefreshState.Refreshing -> {
                    drawRefreshingIndicator(
                        center,
                        indicatorRadiusPx,
                        ringStrokeWidthPx,
                        color,
                        rotation
                    )
                }

                RefreshState.RefreshComplete -> {
                    drawRefreshCompleteIndicator(
                        center, indicatorRadiusPx, ringStrokeWidthPx, color,
                        pullToRefreshState.refreshCompleteAnimProgress
                    )
                }
            }
        }
    }
}

@Composable
private fun animateRotation(): State<Float> {
    val infiniteTransition = rememberInfiniteTransition()
    return infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )
}

private fun DrawScope.drawPullingIndicator(
    center: Offset, radius: Float, strokeWidth: Float, color: Color, alpha: Float
) {
    drawCircle(
        color = color.copy(alpha = alpha),
        radius = radius,
        center = center,
        style = Stroke(strokeWidth, cap = StrokeCap.Round)
    )
}

private fun DrawScope.drawThresholdIndicator(
    center: Offset, radius: Float, strokeWidth: Float, color: Color,
    dragOffset: Float, thresholdOffset: Float, maxDrag: Float
) {
    val lineLength = (dragOffset - thresholdOffset).coerceIn(0f, maxDrag - thresholdOffset)
    val topY = center.y
    val bottomY = center.y + lineLength
    drawArc(
        color = color, startAngle = 180f, sweepAngle = 180f, useCenter = false,
        topLeft = Offset(center.x - radius, topY - radius),
        size = Size(radius * 2, radius * 2),
        style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
    )
    drawArc(
        color = color, startAngle = 0f, sweepAngle = 180f, useCenter = false,
        topLeft = Offset(center.x - radius, bottomY - radius),
        size = Size(radius * 2, radius * 2),
        style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
    )
    drawLine(
        color = color,
        start = Offset(center.x - radius, topY), end = Offset(center.x - radius, bottomY),
        strokeWidth = strokeWidth, cap = StrokeCap.Round
    )
    drawLine(
        color = color,
        start = Offset(center.x + radius, topY), end = Offset(center.x + radius, bottomY),
        strokeWidth = strokeWidth, cap = StrokeCap.Round
    )
}

private fun DrawScope.drawRefreshingIndicator(
    center: Offset, radius: Float, strokeWidth: Float, color: Color, rotation: Float
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
        x = (orbitRadius * cos(angle)).toFloat(),
        y = (orbitRadius * sin(angle)).toFloat()
    )
    drawCircle(color = color, radius = strokeWidth, center = dotCenter)
}

private fun DrawScope.drawRefreshCompleteIndicator(
    center: Offset, radius: Float, strokeWidth: Float, color: Color, progress: Float
) {
    val animatedRadius = radius * (1f - progress).coerceAtLeast(0.9f)
    val alphaColor = color.copy(alpha = (1f - progress - 0.35f).coerceAtLeast(0f))
    val y = center.y - radius - strokeWidth + animatedRadius
    drawCircle(
        color = alphaColor,
        radius = animatedRadius,
        center = Offset(center.x, y),
        style = Stroke(strokeWidth, cap = StrokeCap.Round)
    )
}

private const val maxDragRatio = 1 / 6f
private const val thresholdRatio = 1 / 4f

internal val LocalPullToRefreshState = compositionLocalOf<PullToRefreshState?> { null }

/** Default values for the [PullToRefresh] component. */
object PullToRefreshDefaults {
    val color: Color = Color.Gray
    val circleSize: Dp = 20.dp
    val refreshTexts = listOf(
        "Pull down to refresh",
        "Release to refresh",
        "Refreshing...",
        "Refreshed successfully"
    )
    val refreshTextStyle = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        color = color
    )
}