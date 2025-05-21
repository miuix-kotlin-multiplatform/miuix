// Copyright 2025, miuix-kotlin-multiplatform contributors
// SPDX-License-Identifier: Apache-2.0

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
import androidx.compose.ui.geometry.CornerRadius
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
 * @param progress The current progress value between 0.0f and 1.0f, or null for indeterminate state.
 * @param modifier The modifier to be applied to the indicator.
 * @param colors The colors used for the indicator.
 * @param height The height of the indicator.
 */
@Composable
fun LinearProgressIndicator(
    progress: Float? = null,
    modifier: Modifier = Modifier,
    colors: ProgressIndicatorColors = ProgressIndicatorDefaults.progressIndicatorColors(),
    height: Dp = ProgressIndicatorDefaults.DefaultLinearProgressIndicatorHeight
) {
    if (progress == null) {
        val animatedValue = remember { Animatable(initialValue = 0f) }

        LaunchedEffect(Unit) {
            animatedValue.animateTo(
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(durationMillis = 1250, easing = LinearEasing),
                    repeatMode = RepeatMode.Restart
                )
            )
        }

        Canvas(modifier = modifier.fillMaxWidth().height(height)) {
            drawRoundRect(
                color = colors.backgroundColor(),
                size = Size(size.width, size.height),
                cornerRadius = CornerRadius(size.height / 2)
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
                    val startX = size.width * adjustedPos
                    val width = size.width * segmentWidth

                    drawRoundRect(
                        color = colors.foregroundColor(true),
                        topLeft = Offset(startX, 0f),
                        size = Size(width, size.height),
                        cornerRadius = CornerRadius(size.height / 2)
                    )
                } else {
                    val startX = size.width * adjustedPos
                    val width = size.width * (1f - adjustedPos)

                    drawRoundRect(
                        color = colors.foregroundColor(true),
                        topLeft = Offset(startX, 0f),
                        size = Size(width, size.height),
                        cornerRadius = CornerRadius(size.height / 2)
                    )

                    val remainingWidth = adjustedPos + segmentWidth - 1f
                    if (remainingWidth > 0) {
                        drawRoundRect(
                            color = colors.foregroundColor(true),
                            topLeft = Offset(0f, 0f),
                            size = Size(size.width * remainingWidth, size.height),
                            cornerRadius = CornerRadius(size.height / 2)
                        )
                    }
                }
            }
        }
    } else {
        val progressValue = progress.coerceIn(0f, 1f)

        Canvas(modifier = modifier.fillMaxWidth().height(height)) {
            val cornerRadius = size.height / 2

            drawRoundRect(
                color = colors.backgroundColor(),
                size = Size(size.width, size.height),
                cornerRadius = CornerRadius(cornerRadius)
            )

            val minWidth = cornerRadius * 2
            val progressWidth = if (progressValue == 0f) {
                minWidth
            } else {
                minWidth + (size.width - minWidth) * progressValue
            }

            drawRoundRect(
                color = colors.foregroundColor(true),
                topLeft = Offset(0f, 0f),
                size = Size(progressWidth, size.height),
                cornerRadius = CornerRadius(cornerRadius)
            )
        }
    }
}

/**
 * A [CircularProgressIndicator] with Miuix style.
 *
 * @param progress The current progress value between 0.0f and 1.0f, or null for indeterminate state.
 * @param modifier The modifier to be applied to the indicator.
 * @param colors The colors used for the indicator.
 * @param strokeWidth The width of the circular stroke.
 * @param size The size (diameter) of the circular indicator.
 */
@Composable
fun CircularProgressIndicator(
    progress: Float? = null,
    modifier: Modifier = Modifier,
    colors: ProgressIndicatorColors = ProgressIndicatorDefaults.progressIndicatorColors(),
    strokeWidth: Dp = ProgressIndicatorDefaults.DefaultCircularProgressIndicatorStrokeWidth,
    size: Dp = ProgressIndicatorDefaults.DefaultCircularProgressIndicatorSize
) {
    if (progress == null) {
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
                    animationSpec = tween(800, easing = LinearEasing)
                )
                sweepAnim.animateTo(
                    targetValue = 30f,
                    animationSpec = tween(800, easing = LinearEasing)
                )
            }
        }

        Canvas(
            modifier = modifier.size(size)
        ) {
            val strokeWidthPx = strokeWidth.toPx()
            val radius = (size.toPx() - strokeWidthPx) / 2
            val center = Offset(size.toPx() / 2, size.toPx() / 2)

            drawCircle(
                color = colors.backgroundColor(),
                radius = radius,
                center = center,
                style = Stroke(width = strokeWidthPx)
            )

            val startAngle = rotationAnim.value
            val sweepAngle = sweepAnim.value

            drawArc(
                color = colors.foregroundColor(true),
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = false,
                topLeft = Offset(strokeWidthPx / 2, strokeWidthPx / 2),
                size = Size(2 * radius, 2 * radius),
                style = Stroke(width = strokeWidthPx)
            )

            val halfStroke = strokeWidthPx / 2

            val startRadians = startAngle * (PI / 180f).toFloat()
            val startCapCenter = Offset(
                center.x + radius * cos(startRadians),
                center.y + radius * sin(startRadians)
            )

            val endRadians = (startAngle + sweepAngle) * (PI / 180f).toFloat()
            val endCapCenter = Offset(
                center.x + radius * cos(endRadians),
                center.y + radius * sin(endRadians)
            )

            drawCircle(
                color = colors.foregroundColor(true),
                radius = halfStroke,
                center = startCapCenter
            )

            drawCircle(
                color = colors.foregroundColor(true),
                radius = halfStroke,
                center = endCapCenter
            )
        }
    } else {
        val progressValue = progress.coerceIn(0f, 1f)

        Canvas(
            modifier = modifier.size(size)
        ) {
            val strokeWidthPx = strokeWidth.toPx()
            val radius = (size.toPx() - strokeWidthPx) / 2
            val center = Offset(size.toPx() / 2, size.toPx() / 2)

            drawCircle(
                color = colors.backgroundColor(),
                radius = radius,
                center = center,
                style = Stroke(width = strokeWidthPx)
            )

            val minSweepAngle = 0.1f

            val sweepAngle = if (progressValue == 0f) {
                minSweepAngle
            } else {
                minSweepAngle + (360f - minSweepAngle) * progressValue
            }

            drawArc(
                color = colors.foregroundColor(true),
                startAngle = -90f,
                sweepAngle = sweepAngle,
                useCenter = false,
                topLeft = Offset(strokeWidthPx / 2, strokeWidthPx / 2),
                size = Size(2 * radius, 2 * radius),
                style = Stroke(width = strokeWidthPx)
            )

            // Draw start and end caps
            val startCapCenter = Offset(center.x, center.y - radius)

            val endRadians = (-90f + sweepAngle) * (PI / 180f).toFloat()
            val endCapCenter = Offset(
                center.x + radius * cos(endRadians),
                center.y + radius * sin(endRadians)
            )

            drawCircle(
                color = colors.foregroundColor(true),
                radius = strokeWidthPx / 2,
                center = startCapCenter
            )

            drawCircle(
                color = colors.foregroundColor(true),
                radius = strokeWidthPx / 2,
                center = endCapCenter
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
    val DefaultCircularProgressIndicatorSize = 30.dp

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