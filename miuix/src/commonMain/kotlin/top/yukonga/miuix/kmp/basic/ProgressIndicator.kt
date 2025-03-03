package top.yukonga.miuix.kmp.basic

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import top.yukonga.miuix.kmp.basic.ProgressIndicatorDefaults.ProgressIndicatorColors
import top.yukonga.miuix.kmp.theme.MiuixTheme
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

/**
 * A [LinearProgressIndicator] with Miuix style.
 *
 * @param progress The current progress value between 0.0 and 1.0, or null for indeterminate state.
 * @param modifier The modifier to be applied to the indicator.
 * @param colors The colors used for the indicator.
 * @param height The height of the indicator.
 * @param strokeCap The shape of the indicator ends.
 */
@Composable
fun LinearProgressIndicator(
    progress: Float? = null,
    modifier: Modifier = Modifier,
    colors: ProgressIndicatorColors = ProgressIndicatorDefaults.progressIndicatorColors(),
    height: Dp = ProgressIndicatorDefaults.DefaultLinearProgressIndicatorHeight,
    strokeCap: StrokeCap = StrokeCap.Round
) {
    val isIndeterminate = progress == null

    if (isIndeterminate) {
        val animatedValue = remember { Animatable(initialValue = 0f) }

        LaunchedEffect(Unit) {
            animatedValue.animateTo(
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(1200, easing = LinearEasing),
                    repeatMode = RepeatMode.Restart
                )
            )
        }

        Canvas(
            modifier = modifier
                .fillMaxWidth()
                .height(height)
        ) {
            drawLine(
                color = colors.backgroundColor(),
                start = Offset(0f, size.height / 2),
                end = Offset(size.width, size.height / 2),
                strokeWidth = size.height,
                cap = strokeCap
            )

            val value = animatedValue.value
            val segmentWidth = 0.45f
            val gap = 0.55f

            val positions = listOf(
                value,
                value - (segmentWidth + gap),
                value - 2 * (segmentWidth + gap)
            )

            positions.forEach { position ->
                val adjustedPos = (position % 1f + 1f) % 1f

                if (adjustedPos < 1f - segmentWidth) {
                    drawLine(
                        color = colors.foregroundColor(true),
                        start = Offset(adjustedPos * size.width, size.height / 2),
                        end = Offset((adjustedPos + segmentWidth) * size.width, size.height / 2),
                        strokeWidth = size.height,
                        cap = strokeCap
                    )
                } else {
                    drawLine(
                        color = colors.foregroundColor(true),
                        start = Offset(adjustedPos * size.width, size.height / 2),
                        end = Offset(size.width, size.height / 2),
                        strokeWidth = size.height,
                        cap = strokeCap
                    )

                    val remainingWidth = adjustedPos + segmentWidth - 1f
                    drawLine(
                        color = colors.foregroundColor(true),
                        start = Offset(0f, size.height / 2),
                        end = Offset(remainingWidth * size.width, size.height / 2),
                        strokeWidth = size.height,
                        cap = strokeCap
                    )
                }
            }
        }
    } else {
        val progressValue = progress.coerceIn(0f, 1f)

        Canvas(
            modifier = modifier
                .fillMaxWidth()
                .height(height)
        ) {
            drawLine(
                color = colors.backgroundColor(),
                start = Offset(0f, size.height / 2),
                end = Offset(size.width, size.height / 2),
                strokeWidth = size.height,
                cap = strokeCap
            )

            drawLine(
                color = colors.foregroundColor(true),
                start = Offset(0f, size.height / 2),
                end = Offset(size.width * progressValue, size.height / 2),
                strokeWidth = size.height,
                cap = strokeCap
            )
        }
    }
}

/**
 * A [CircularProgressIndicator] with Miuix style.
 *
 * @param progress The current progress value between 0.0 and 1.0, or null for indeterminate state.
 * @param modifier The modifier to be applied to the indicator.
 * @param colors The colors used for the indicator.
 * @param strokeWidth The width of the circular stroke.
 * @param size The size (diameter) of the circular indicator.
 * @param strokeCap The shape of the indicator ends.
 */
@Composable
fun CircularProgressIndicator(
    progress: Float? = null,
    modifier: Modifier = Modifier,
    colors: ProgressIndicatorColors = ProgressIndicatorDefaults.progressIndicatorColors(),
    strokeWidth: Dp = ProgressIndicatorDefaults.DefaultCircularProgressIndicatorStrokeWidth,
    size: Dp = ProgressIndicatorDefaults.DefaultCircularProgressIndicatorSize,
    strokeCap: StrokeCap = StrokeCap.Round
) {
    val isIndeterminate = progress == null

    if (isIndeterminate) {
        val rotationAnim = remember { Animatable(0f) }
        val sweepAnim = remember { Animatable(30f) }

        LaunchedEffect(Unit) {
            rotationAnim.animateTo(
                targetValue = 360f,
                animationSpec = infiniteRepeatable(
                    animation = tween(1000, easing = LinearEasing),
                    repeatMode = RepeatMode.Restart
                )
            )
        }

        LaunchedEffect(Unit) {
            while (true) {
                sweepAnim.animateTo(
                    targetValue = 120f,
                    animationSpec = tween(600, easing = LinearEasing)
                )
                sweepAnim.animateTo(
                    targetValue = 30f,
                    animationSpec = tween(600, easing = LinearEasing)
                )
            }
        }

        Canvas(
            modifier = modifier.size(size)
        ) {
            val strokeWidthPx = strokeWidth.toPx()
            val diameter = size.toPx() - strokeWidthPx
            val topLeft = Offset(strokeWidthPx / 2, strokeWidthPx / 2)
            val arcSize = Size(diameter, diameter)

            drawArc(
                color = colors.backgroundColor(),
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = Stroke(
                    width = strokeWidthPx,
                    cap = strokeCap
                )
            )

            drawArc(
                color = colors.foregroundColor(true),
                startAngle = rotationAnim.value,
                sweepAngle = sweepAnim.value,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = Stroke(
                    width = strokeWidthPx,
                    cap = strokeCap
                )
            )
        }
    } else {
        val progressValue = progress.coerceIn(0f, 1f)

        Canvas(
            modifier = modifier.size(size)
        ) {
            val strokeWidthPx = strokeWidth.toPx()
            val diameter = size.toPx() - strokeWidthPx
            val topLeft = Offset(strokeWidthPx / 2, strokeWidthPx / 2)
            val arcSize = Size(diameter, diameter)

            drawArc(
                color = colors.backgroundColor(),
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = Stroke(
                    width = strokeWidthPx,
                    cap = strokeCap
                )
            )

            drawArc(
                color = colors.foregroundColor(true),
                startAngle = -90f, // Start from the top
                sweepAngle = 360f * progressValue,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = Stroke(
                    width = strokeWidthPx,
                    cap = strokeCap
                )
            )
        }
    }
}

/**
 * A [InfiniteProgressIndicator] with Miuix style.
 * The indicator is a circular indicator with an orbiting dot.
 *
 * @param modifier The modifier to be applied to the indicator.
 * @param color The color of the indicator.
 * @param size The size (diameter) of the circular indicator.
 * @param strokeWidth The width of the circular stroke.
 * @param orbitingDotSize The size of the orbiting dot.
 */
@Composable
fun InfiniteProgressIndicator(
    modifier: Modifier = Modifier,
    color: Color = Color.Gray,
    size: Dp = ProgressIndicatorDefaults.DefaultInfiniteProgressIndicatorSize,
    strokeWidth: Dp = ProgressIndicatorDefaults.DefaultInfiniteProgressIndicatorStrokeWidth,
    orbitingDotSize: Dp = ProgressIndicatorDefaults.DefaultInfiniteProgressIndicatorOrbitingDotSize
) {
    val rotationAnim = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        rotationAnim.animateTo(
            targetValue = 360f,
            animationSpec = infiniteRepeatable(
                animation = tween(800, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            )
        )
    }

    Canvas(modifier = modifier.size(size)) {
        val center = Offset(this.size.width / 2, this.size.height / 2)
        val radius = (size.toPx() - strokeWidth.toPx()) / 2

        drawCircle(
            color = color,
            radius = radius,
            center = center,
            style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
        )

        val orbitRadius = radius - 2 * orbitingDotSize.toPx()
        val angle = rotationAnim.value * PI.toFloat() / 180f
        val dotCenter = center + Offset(
            x = orbitRadius * cos(angle),
            y = orbitRadius * sin(angle)
        )

        drawCircle(
            color = color,
            radius = orbitingDotSize.toPx(),
            center = dotCenter
        )
    }
}

object ProgressIndicatorDefaults {
    /** The default height of [LinearProgressIndicator]. */
    val DefaultLinearProgressIndicatorHeight = 6.dp

    /** The default stroke width of [CircularProgressIndicator]. */
    val DefaultCircularProgressIndicatorStrokeWidth = 4.dp

    /** The default size of [CircularProgressIndicator]. */
    val DefaultCircularProgressIndicatorSize = 36.dp

    /** The default stroke width of [InfiniteProgressIndicator]. */
    val DefaultInfiniteProgressIndicatorStrokeWidth = 2.dp

    /** The default radius width of the orbiting dot in [InfiniteProgressIndicator]. */
    val DefaultInfiniteProgressIndicatorOrbitingDotSize = 2.dp

    /** The default size of [InfiniteProgressIndicator]. */
    val DefaultInfiniteProgressIndicatorSize = 20.dp

    /**
     * The default [ProgressIndicatorColors] used by [LinearProgressIndicator] and [CircularProgressIndicator].
     */
    @Composable
    fun progressIndicatorColors(
        foregroundColor: Color = MiuixTheme.colorScheme.primary,
        disabledForegroundColor: Color = MiuixTheme.colorScheme.disabledPrimarySlider,
        backgroundColor: Color = MiuixTheme.colorScheme.tertiaryContainerVariant
    ): ProgressIndicatorColors {
        return ProgressIndicatorColors(
            foregroundColor = foregroundColor,
            disabledForegroundColor = disabledForegroundColor,
            backgroundColor = backgroundColor
        )
    }

    @Immutable
    class ProgressIndicatorColors(
        private val foregroundColor: Color,
        private val disabledForegroundColor: Color,
        private val backgroundColor: Color
    ) {
        @Stable
        internal fun foregroundColor(enabled: Boolean): Color =
            if (enabled) foregroundColor else disabledForegroundColor

        @Stable
        internal fun backgroundColor(): Color = backgroundColor
    }
}