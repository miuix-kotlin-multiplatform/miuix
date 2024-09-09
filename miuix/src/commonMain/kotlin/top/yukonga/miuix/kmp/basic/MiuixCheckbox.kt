package top.yukonga.miuix.kmp.basic

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.selection.toggleable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Matrix
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import top.yukonga.miuix.kmp.theme.MiuixTheme
import top.yukonga.miuix.kmp.utils.squircleshape.SquircleShape

/**
 * A checkbox component with Miuix style.
 *
 * @param checked The current state of the [MiuixCheckbox].
 * @param onCheckedChange The callback to be called when the state of the [MiuixCheckbox] changes.
 * @param modifier The modifier to be applied to the [MiuixCheckbox].
 * @param enabled Whether the [MiuixCheckbox] is enabled.
 * @param interactionSource The interaction source to be applied to the [MiuixCheckbox].
 */
@Composable
fun MiuixCheckbox(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource? = null
) {
    @Suppress("NAME_SHADOWING")
    val interactionSource = interactionSource ?: remember { MutableInteractionSource() }
    val isChecked by rememberUpdatedState(checked)
    var isPressed by remember { mutableStateOf(false) }
    val backgroundColor by animateColorAsState(if (isChecked) MiuixTheme.colorScheme.primary else MiuixTheme.colorScheme.secondary)
    val disabledBackgroundColor by rememberUpdatedState(if (isChecked) MiuixTheme.colorScheme.submitDisabledBg else MiuixTheme.colorScheme.disabledBg)
    val checkboxSize by animateDpAsState(if (isPressed) 20.dp else 22.dp)
    val checkmarkColor by animateColorAsState(if (checked) Color.White else backgroundColor)
    val rotationAngle by animateFloatAsState(if (checked) 0f else 25f, animationSpec = tween(durationMillis = 200))
    val pathProgress by animateFloatAsState(if (checked) 1f else 0f, animationSpec = tween(durationMillis = 400))
    val toggleableModifier = remember(onCheckedChange, isChecked, enabled) {
        if (onCheckedChange != null) {
            Modifier.toggleable(
                value = isChecked,
                onValueChange = {
                    onCheckedChange(it)
                },
                enabled = enabled,
                role = Role.Checkbox,
                interactionSource = interactionSource,
                indication = null
            )
        } else {
            Modifier
        }
    }

    MiuixBox(
        modifier = modifier
            .then(toggleableModifier)
            .wrapContentSize(Alignment.Center)
            .size(22.dp)
            .requiredSize(checkboxSize)
            .clip(SquircleShape(100.dp))
            .background(if (enabled) backgroundColor else disabledBackgroundColor)
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
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragEnd = {
                        isPressed = false
                    },
                    onDragCancel = {
                        isPressed = false
                    },
                    onDrag = { change, _ ->
                        change.consume()
                    }
                )
            }
    ) {
        Canvas(
            modifier = Modifier.requiredSize(checkboxSize)
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
                drawPath(animatedPath, checkmarkColor)
            }
        }
    }
}