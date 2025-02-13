package top.yukonga.miuix.kmp.basic

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Matrix
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import top.yukonga.miuix.kmp.theme.MiuixTheme

/**
 * A [Checkbox] component with Miuix style.
 *
 * @param checked The current state of the [Checkbox].
 * @param onCheckedChange The callback to be called when the state of the [Checkbox] changes.
 * @param modifier The modifier to be applied to the [Checkbox].
 * @param colors The [CheckboxColors] of the [Checkbox].
 * @param enabled Whether the [Checkbox] is enabled.
 */
@Composable
fun Checkbox(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
    colors: CheckboxColors = CheckboxDefaults.checkboxColors(),
    enabled: Boolean = true,
) {
    val isChecked by rememberUpdatedState(checked)
    var isPressed by remember { mutableStateOf(false) }
    val hapticFeedback = LocalHapticFeedback.current
    val checkboxSize by animateDpAsState(
        if (!enabled) 22.dp else if (isPressed) 20.dp else 22.dp
    )
    val backgroundColor by animateColorAsState(
        if (isChecked) colors.checkedBackgroundColor(enabled) else colors.uncheckedBackgroundColor(enabled)
    )
    val rotationAngle by animateFloatAsState(
        if (checked) 0f else 25f,
        animationSpec = tween(durationMillis = 200)
    )
    val pathProgress by animateFloatAsState(
        if (checked) 1f else 0f,
        animationSpec = tween(durationMillis = 400)
    )
    val toggleableModifier = remember(onCheckedChange, isChecked, enabled) {
        if (onCheckedChange != null) {
            Modifier.toggleable(
                value = isChecked,
                onValueChange = {
                    onCheckedChange(it)
                    if (it) hapticFeedback.performHapticFeedback(HapticFeedbackType.ToggleOn)
                    else hapticFeedback.performHapticFeedback(HapticFeedbackType.ToggleOff)
                },
                enabled = enabled,
                role = Role.Checkbox,
                indication = null,
                interactionSource = null
            )
        } else {
            Modifier
        }
    }

    Box(
        modifier = modifier
            .wrapContentSize(Alignment.Center)
            .size(22.dp)
            .clip(RoundedCornerShape(100.dp))
            .requiredSize(checkboxSize)
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        isPressed = true
                    },
                    onTap = {
                        isPressed = false
                        if (enabled) {
                            onCheckedChange?.invoke(!isChecked)
                        }
                    }
                )
            }
            .then(toggleableModifier)
    ) {
        Canvas(
            modifier = Modifier
                .requiredSize(checkboxSize)
                .drawBehind { drawRect(backgroundColor) }
        ) {
            val svgPath =
                "m400-416 236-236q11-11 28-11t28 11q11 11 11 28t-11 28L428-332q-12 12-28 12t-28-12L268-436q-11-11-11-28t11-28q11-11 28-11t28 11l76 76Z"
            val path = PathParser().parsePathString(svgPath).toPath()
            val scaleFactor = size.minDimension / 960f
            path.transform(Matrix().apply {
                scale(scaleFactor, scaleFactor)
                translate(0f, 960f)
            })
            rotate(rotationAngle, pivot = Offset(size.width / 2, size.height / 2)) {
                val pathMeasure = PathMeasure()
                pathMeasure.setPath(path, false)
                val length = pathMeasure.length
                val animatedPath = Path()
                pathMeasure.getSegment(length * (1 - pathProgress), length, animatedPath, true)
                drawPath(animatedPath, colors.foregroundColor(enabled))
            }
        }
    }
}

object CheckboxDefaults {

    @Composable
    fun checkboxColors(
        foregroundColor: Color = MiuixTheme.colorScheme.onPrimary,
        disabledForegroundColor: Color = MiuixTheme.colorScheme.disabledOnPrimary,
        checkedBackgroundColor: Color = MiuixTheme.colorScheme.primary,
        uncheckedBackgroundColor: Color = MiuixTheme.colorScheme.secondary,
        disabledCheckedBackgroundColor: Color = MiuixTheme.colorScheme.disabledPrimary,
        disabledUncheckedBackgroundColor: Color = MiuixTheme.colorScheme.disabledSecondary
    ): CheckboxColors {
        return CheckboxColors(
            foregroundColor = foregroundColor,
            disabledForegroundColor = disabledForegroundColor,
            checkedBackgroundColor = checkedBackgroundColor,
            uncheckedBackgroundColor = uncheckedBackgroundColor,
            disabledCheckedBackgroundColor = disabledCheckedBackgroundColor,
            disabledUncheckedBackgroundColor = disabledUncheckedBackgroundColor
        )
    }
}

@Immutable
class CheckboxColors(
    private val foregroundColor: Color,
    private val disabledForegroundColor: Color,
    private val checkedBackgroundColor: Color,
    private val uncheckedBackgroundColor: Color,
    private val disabledCheckedBackgroundColor: Color,
    private val disabledUncheckedBackgroundColor: Color
) {
    @Stable
    internal fun foregroundColor(enabled: Boolean): Color = if (enabled) foregroundColor else disabledForegroundColor

    @Stable
    internal fun checkedBackgroundColor(enabled: Boolean): Color = if (enabled) checkedBackgroundColor else disabledCheckedBackgroundColor

    @Stable
    internal fun uncheckedBackgroundColor(enabled: Boolean): Color = if (enabled) uncheckedBackgroundColor else disabledUncheckedBackgroundColor
}