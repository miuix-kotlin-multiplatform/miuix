package top.yukonga.miuix.kmp.basic

// This component is modified from the example provided by @sd086. Thanks for his work.

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import top.yukonga.miuix.kmp.utils.getWindowSize
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.time.TimeSource

/**
 * A [PullToRefresh] component with Miuix style.
 *
 * @param modifier The modifier to be applied to the [PullToRefresh].
 * @param pullToRefreshState pullToRefreshState
 * @param color The color of the refresh indicator.
 * @param circleSize The size of the refresh indicator circle.
 * @param textStyle The style of the refresh text.
 * @param onRefresh The callback to be called when the refresh is triggered.
 * @param content the content to be shown when the [PullToRefresh] is expanded.
 *
 */
@Composable
fun PullToRefresh(
    modifier: Modifier = Modifier,
    pullToRefreshState: PullToRefreshState,
    color: Color = PullToRefreshDefaults.color,
    circleSize: Dp = PullToRefreshDefaults.circleSize,
    textStyle: TextStyle = PullToRefreshDefaults.textStyle,
    onRefresh: () -> Unit = {},
    content: @Composable () -> Unit
) {
    LaunchedEffect(pullToRefreshState.rawDragOffset) {
        pullToRefreshState.updateDragOffsetAnimatable()
    }

    val nestedScrollConnection = remember(pullToRefreshState) {
        pullToRefreshState.createNestedScrollConnection()
    }

    val pointerModifier = Modifier.pointerInput(Unit) {
        awaitPointerEventScope {
            while (true) {
                val event = awaitPointerEvent()
                if (event.changes.all { !it.pressed }) {
                    pullToRefreshState.onPointerRelease()
                    continue
                }
            }
        }
    }

    LaunchedEffect(pullToRefreshState.pointerReleasedValue, pullToRefreshState.isRefreshing) {
        pullToRefreshState.handlePointerReleased(onRefresh)
    }

    Box(
        modifier = modifier
            .nestedScroll(nestedScrollConnection)
            .then(pointerModifier)
    ) {
        Column {
            RefreshHeader(
                pullToRefreshState = pullToRefreshState,
                circleSize = circleSize,
                color = color,
                textStyle = textStyle
            )
            content()
        }
    }
}

/**
 * 刷新头部
 *
 * @param modifier 修饰符
 * @param pullToRefreshState 下拉刷新状态
 * @param circleSize 指示器圆圈大小
 * @param color 指示器颜色
 * @param textStyle 刷新文本样式
 */
@Composable
fun RefreshHeader(
    modifier: Modifier = Modifier,
    pullToRefreshState: PullToRefreshState,
    circleSize: Dp,
    color: Color,
    textStyle: TextStyle
) {
    val dragOffset = pullToRefreshState.dragOffsetAnimatable.value
    val thresholdOffset = pullToRefreshState.refreshThresholdOffset
    val maxDrag = pullToRefreshState.maxDragDistancePx
    val density = LocalDensity.current
    val pullProgress = pullToRefreshState.pullProgress
    val rotation by animateRotation()
    val refreshCompleteAnimProgress = pullToRefreshState.refreshCompleteAnimProgress

    val headerHeight = with(density) {
        when (pullToRefreshState.refreshState) {
            RefreshState.Idle -> 0.dp
            RefreshState.Pulling -> circleSize * pullProgress
            RefreshState.ThresholdReached -> circleSize + (dragOffset - thresholdOffset).toDp()
            RefreshState.Refreshing -> circleSize
            RefreshState.RefreshComplete -> circleSize
        }.coerceAtMost(maxDrag.toDp())
    }

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RefreshContent(
            modifier = Modifier
                .height(headerHeight)
                .padding(top = 6.dp),
            circleSize = circleSize
        ) {
            val ringStrokeWidthPx = circleSize.toPx() / 10
            val indicatorRadiusPx = size.minDimension / 2
            val center = Offset(size.width / 2, size.height / 2)

            when (pullToRefreshState.refreshState) {
                RefreshState.Idle -> return@RefreshContent
                RefreshState.Pulling -> {
                    if (pullProgress > 0.3f) {
                        drawInitialState(
                            center = center,
                            radius = indicatorRadiusPx,
                            strokeWidth = ringStrokeWidthPx,
                            color = color,
                            alpha = pullProgress,
                            refreshProgress = pullProgress
                        )
                    }
                }

                RefreshState.ThresholdReached -> drawThresholdExceededState(
                    center = center,
                    radius = indicatorRadiusPx,
                    strokeWidth = ringStrokeWidthPx,
                    color = color,
                    dragOffset = dragOffset,
                    thresholdOffset = thresholdOffset,
                    maxDrag = maxDrag
                )

                RefreshState.Refreshing -> drawRefreshingState(
                    center = center,
                    radius = indicatorRadiusPx,
                    strokeWidth = ringStrokeWidthPx,
                    color = color,
                    rotation = rotation
                )

                RefreshState.RefreshComplete -> drawRefreshCompleteState(
                    center = center,
                    radius = indicatorRadiusPx,
                    strokeWidth = ringStrokeWidthPx,
                    color = color,
                    refreshCompleteProgress = refreshCompleteAnimProgress
                )
            }
        }

        val refreshText = when (pullToRefreshState.refreshState) {
            RefreshState.Idle -> ""
            RefreshState.Pulling -> if (pullToRefreshState.pullProgress > 0.5) "下拉刷新" else ""
            RefreshState.ThresholdReached -> "松开刷新"
            RefreshState.Refreshing -> "正在刷新"
            RefreshState.RefreshComplete -> "刷新完成"
        }

        val textAlpha = when (pullToRefreshState.refreshState) {
            RefreshState.Pulling -> {
                if (pullToRefreshState.pullProgress > 0.5f) (pullToRefreshState.pullProgress - 0.5f) * 2 else 0f
            }

            else -> 1f
        }

        AnimatedVisibility(
            visible = pullToRefreshState.refreshState != RefreshState.Idle
        ) {
            Text(
                text = refreshText,
                style = textStyle,
                color = color,
                modifier = Modifier
                    .padding(vertical = 6.dp)
                    .alpha(textAlpha)
            )
        }
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
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Canvas(modifier = Modifier.size(circleSize)) {
            drawContent()
        }
    }
}

/**
 * Create rotation animation state
 *
 * @return rotation angle state
 */
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

/**
 * Draw the circle that expands to the initial state
 */
private fun DrawScope.drawInitialState(
    center: Offset,
    radius: Float,
    strokeWidth: Float,
    color: Color,
    alpha: Float,
    refreshProgress: Float
) {
    val alphaColor = color.copy(alpha = alpha)
    drawCircle(
        color = alphaColor,
        radius = radius * refreshProgress,
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
    val lineLength =
        if (dragOffset > thresholdOffset) {
            (dragOffset - thresholdOffset)
                .coerceAtMost(maxDrag - thresholdOffset)
                .coerceAtLeast(0f)
        } else {
            0f
        }
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
 * Draw the circle that shrinks to the refresh complete state
 */
private fun DrawScope.drawRefreshCompleteState(
    center: Offset,
    radius: Float,
    strokeWidth: Float,
    color: Color,
    refreshCompleteProgress: Float
) {
    val animatedRadius = radius * (1f - refreshCompleteProgress)
    if (animatedRadius > 0) {
        drawCircle(
            color = color,
            radius = animatedRadius,
            center = center,
            style = Stroke(strokeWidth, cap = StrokeCap.Round)
        )
    }
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
 * Remember the PullToRefreshState state object
 *
 * @return PullToRefreshState state object
 */
@Composable
fun rememberPullToRefreshState(): PullToRefreshState {
    val coroutineScope = rememberCoroutineScope()
    val density = LocalDensity.current
    val screenHeightDp = getWindowSize().height

    val maxDragDistancePx = with(density) { (screenHeightDp.dp * maxDragRatio).toPx() }
    val refreshThresholdOffset = maxDragDistancePx * thresholdRatio

    return remember {
        PullToRefreshState(
            coroutineScope,
            maxDragDistancePx,
            refreshThresholdOffset
        )
    }
}

/**
 * PullToRefreshState
 *
 * @param coroutineScope CoroutineScope
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

    /** Pull progress */
    val pullProgress: Float by derivedStateOf {
        (dragOffsetAnimatable.value / refreshThresholdOffset).coerceIn(0f, 1f)
    }
    private val _refreshCompleteAnimProgress = mutableFloatStateOf(1f)

    /** Refresh complete animation progress */
    val refreshCompleteAnimProgress: Float by derivedStateOf { _refreshCompleteAnimProgress.floatValue }

    init {
        coroutineScope.launch {
            snapshotFlow { dragOffsetAnimatable.value }.collectLatest { offset ->
                internalRefreshState = when {
                    offset >= refreshThresholdOffset && !isRefreshing -> RefreshState.ThresholdReached
                    offset > 0 && !isRefreshing -> RefreshState.Pulling
                    isRefreshing -> RefreshState.Refreshing
                    else -> RefreshState.Idle
                }
            }
        }
    }

    private fun startRefreshing() {
        internalRefreshState = RefreshState.Refreshing
    }

    suspend fun updateDragOffsetAnimatable() {
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

    private fun resetPointerReleased() {
        pointerReleased = false
    }

    val pointerReleasedValue: Boolean get() = pointerReleased

    fun completeRefreshing(block: suspend () -> Unit) {
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
                launch {
                    startManualRefreshCompleteAnimation()
                }
            }
        }
    }

    private suspend fun startManualRefreshCompleteAnimation() {
        _refreshCompleteAnimProgress.floatValue = 0f
        val durationMillis = 100
        val startTime = TimeSource.Monotonic.markNow()
        val easing: Easing = LinearEasing

        while (true) {
            val elapsedTime = startTime.elapsedNow().inWholeMilliseconds
            val linearProgress = (elapsedTime.toFloat() / durationMillis).coerceIn(0f, 1f)
            val smoothProgress = easing.transform(linearProgress)
            _refreshCompleteAnimProgress.floatValue = smoothProgress
            if (linearProgress >= 1f) {
                _refreshCompleteAnimProgress.floatValue = 1f
                break
            }
            delay(16)
        }
        internalResetState()
    }

    private suspend fun internalResetState() {
        internalRefreshState = RefreshState.Idle
        dragOffsetAnimatable.snapTo(0f)
        rawDragOffset = 0f
    }

    fun createNestedScrollConnection(): NestedScrollConnection =
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                if (source == NestedScrollSource.UserInput && available.y < 0 && rawDragOffset > 0f) {
                    val delta = available.y.coerceAtLeast(-rawDragOffset)
                    rawDragOffset += delta
                    coroutineScope.launch {
                        dragOffsetAnimatable.snapTo(rawDragOffset)
                    }
                    return Offset(0f, delta)
                }
                return Offset.Zero
            }

            override fun onPostScroll(
                consumed: Offset,
                available: Offset,
                source: NestedScrollSource
            ): Offset = when {
                isRefreshing -> Offset.Zero
                source == NestedScrollSource.UserInput -> {
                    if (available.y > 0f && consumed.y == 0f) {
                        val newOffset = (rawDragOffset + available.y).coerceAtMost(maxDragDistancePx)
                        val consumedY = newOffset - rawDragOffset
                        rawDragOffset = newOffset
                        coroutineScope.launch {
                            dragOffsetAnimatable.snapTo(newOffset)
                        }
                        Offset(0f, consumedY)
                    } else if (available.y < 0f) {
                        val newOffset = (rawDragOffset + available.y).coerceAtLeast(0f)
                        val consumedY = rawDragOffset - newOffset
                        rawDragOffset = newOffset
                        Offset(0f, -consumedY)
                    } else {
                        Offset.Zero
                    }
                }

                else -> Offset.Zero
            }
        }

    suspend fun handlePointerReleased(
        onRefresh: () -> Unit
    ) {
        if (pointerReleasedValue && !isRefreshing) {
            if (rawDragOffset >= refreshThresholdOffset) {
                animateDragOffset(
                    targetValue = refreshThresholdOffset,
                    animationSpec = tween(
                        durationMillis = 100,
                        easing = LinearEasing
                    )
                )
                rawDragOffset = refreshThresholdOffset
                startRefreshing()
                onRefresh()
            } else {
                animateDragOffset(
                    targetValue = 0f,
                    animationSpec = snap()
                )
                rawDragOffset = 0f
            }
            resetPointerReleased()
        }
    }

    fun onPointerRelease() {
        pointerReleased = true
    }
}

/** Maximum drag ratio */
internal const val maxDragRatio = 1 / 5f

/** Threshold ratio */
internal const val thresholdRatio = 1 / 4f

/**
 * PullToRefreshDefaults
 */
object PullToRefreshDefaults {
    val color: Color = Color.Gray
    val circleSize: Dp = 24.dp
    val textStyle = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        color = color
    )
}