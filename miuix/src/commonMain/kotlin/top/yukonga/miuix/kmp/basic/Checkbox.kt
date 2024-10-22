package top.yukonga.miuix.kmp.basic

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
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
 * @param modifier The modifier to be applied to the [Checkbox].
 * @param checked The current state of the [Checkbox].
 * @param onCheckedChange The callback to be called when the state of the [Checkbox] changes.
 * @param enabled Whether the [Checkbox] is enabled.
 */
@Composable
fun Checkbox(
    modifier: Modifier = Modifier,
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    enabled: Boolean = true,
) {
    val isChecked by rememberUpdatedState(checked)
    var isPressed by remember { mutableStateOf(false) }
    val hapticFeedback = LocalHapticFeedback.current
    val backgroundColor by animateColorAsState(if (isChecked) MiuixTheme.colorScheme.primary else MiuixTheme.colorScheme.secondary)
    val disabledBackgroundColor by rememberUpdatedState(if (isChecked) MiuixTheme.colorScheme.disabledPrimary else MiuixTheme.colorScheme.disabledSecondary)
    val checkboxSize by animateDpAsState(if (!enabled) 22.dp else if (isPressed) 20.dp else 22.dp)
    val checkmarkColor by animateColorAsState(if (checked) MiuixTheme.colorScheme.onPrimary else backgroundColor)
    val disabledCheckmarkColor by animateColorAsState(if (checked) MiuixTheme.colorScheme.disabledOnPrimary else disabledBackgroundColor)
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
                    hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
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
                .drawBehind { drawRect(if (enabled) backgroundColor else disabledBackgroundColor) }
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
                drawPath(animatedPath, if (enabled) checkmarkColor else disabledCheckmarkColor)
            }
        }
    }
}