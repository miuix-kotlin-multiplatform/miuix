package top.yukonga.miuix.kmp.basic

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import top.yukonga.miuix.kmp.basic.ProgressIndicatorDefaults.ProgressIndicatorColors
import top.yukonga.miuix.kmp.theme.MiuixTheme

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
    height: Dp = ProgressIndicatorDefaults.DefaultProgressIndicatorHeight,
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

object ProgressIndicatorDefaults {
    /** The default height of [LinearProgressIndicator]. */
    val DefaultProgressIndicatorHeight = 6.dp

    /**
     * The default [ProgressIndicatorColors] used in [LinearProgressIndicator].
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