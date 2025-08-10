// Copyright 2025, miuix-kotlin-multiplatform contributors
// SPDX-License-Identifier: Apache-2.0

package top.yukonga.miuix.kmp.utils

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.spring
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.node.LayoutModifierNode
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.node.PointerInputModifierNode
import androidx.compose.ui.platform.InspectorInfo
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.IntSize
import kotlinx.coroutines.launch

private class PressSinkNode(
    var interactionSource: MutableInteractionSource,
    var sinkAmount: Float,
    var animationSpec: AnimationSpec<Float>,
    var immediate: Boolean = false
) : Modifier.Node(), LayoutModifierNode, PointerInputModifierNode {

    private lateinit var pressInteraction: PressInteraction.Press
    private var isPressed = false
    private val animatedScale = Animatable(1f)

    private fun animateToSink(target: Float) {
        coroutineScope.launch { animatedScale.animateTo(target, animationSpec) }
    }

    override fun onAttach() {
        coroutineScope.launch {
            interactionSource.interactions.collect { interaction: Interaction ->
                when (interaction) {
                    is PressInteraction.Press -> {
                        if (immediate && interaction != pressInteraction) return@collect
                        animateToSink(sinkAmount)
                    }

                    is PressInteraction.Release -> {
                        if (immediate && interaction.press != pressInteraction) return@collect
                        animateToSink(1f)
                    }

                    is PressInteraction.Cancel -> animateToSink(1f)
                }
            }
        }
    }

    override fun onPointerEvent(pointerEvent: PointerEvent, pass: PointerEventPass, bounds: IntSize) {
        if (!immediate || pass != PointerEventPass.Main) return
        val currentPressed = pointerEvent.changes.any { it.pressed }
        if (currentPressed != isPressed) {
            isPressed = currentPressed
            if (isPressed) {
                pressInteraction = PressInteraction.Press(pointerEvent.changes.first().position)
                interactionSource.tryEmit(pressInteraction)
            } else interactionSource.tryEmit(PressInteraction.Release(pressInteraction))
        }
    }

    override fun onCancelPointerInput() {
        if (!immediate) return
        interactionSource.tryEmit(PressInteraction.Cancel(pressInteraction))
    }

    override fun MeasureScope.measure(measurable: Measurable, constraints: Constraints): MeasureResult {
        val placeable = measurable.measure(constraints)
        return layout(placeable.width, placeable.height) {
            placeable.placeWithLayer(0, 0) {
                scaleX = animatedScale.value
                scaleY = animatedScale.value
            }
        }
    }
}

private data class PressSinkElement(
    val interactionSource: MutableInteractionSource,
    val sinkAmount: Float,
    val animationSpec: AnimationSpec<Float>,
    val immediate: Boolean
) : ModifierNodeElement<PressSinkNode>() {
    override fun create() = PressSinkNode(interactionSource, sinkAmount, animationSpec, immediate)
    override fun update(node: PressSinkNode) {
        node.interactionSource = interactionSource
        node.sinkAmount = sinkAmount
        node.animationSpec = animationSpec
        node.immediate = immediate
    }

    override fun InspectorInfo.inspectableProperties() {
        name = "PressSinkNode"
        properties["sinkAmount"] = sinkAmount
    }
}

/**
 * Applies a "sink" visual feedback when the component is pressed,
 * by scaling the component down smoothly using animation.
 *
 * The impact of [immediate] on feedback effect:
 * `false` - Press Compose default logic and wait for touch confirmation before starting the animation (consistent with Indication).
 * `true` - The animation is started directly when touched, even if it is subsequently scrolled or consumed.
 *
 * @param interactionSource The interaction source to use to detect presses.
 * @param sinkAmount The target scale when pressed (less than 1f).
 * @param animationSpec The animation spec to use when scaling.
 * @param immediate Whether to trigger the press effect and Indication immediately.
 *
 */
fun Modifier.pressSink(
    interactionSource: MutableInteractionSource,
    sinkAmount: Float = 0.94f,
    animationSpec: AnimationSpec<Float> = spring(0.8f, 600f),
    immediate: Boolean = false
): Modifier = this then PressSinkElement(
    interactionSource, sinkAmount, animationSpec, immediate
)

private class PressTiltNode(
    var interactionSource: MutableInteractionSource,
    var tiltAmount: Float,
    var animationSpec: AnimationSpec<Float>,
    var immediate: Boolean
) : Modifier.Node(), LayoutModifierNode, PointerInputModifierNode {

    private lateinit var pressInteraction: PressInteraction.Press
    private var isPressed = false
    private var transformOrigin: TransformOrigin = TransformOrigin.Center
    private var targetX = 0f
    private var targetY = 0f
    private val animatedTiltX = Animatable(0f)
    private val animatedTiltY = Animatable(0f)

    private fun animateToTilt(x: Float, y: Float) {
        coroutineScope.launch { animatedTiltX.animateTo(x, animationSpec) }
        coroutineScope.launch { animatedTiltY.animateTo(y, animationSpec) }
    }

    override fun onAttach() {
        coroutineScope.launch {
            interactionSource.interactions.collect { interaction: Interaction ->
                when (interaction) {
                    is PressInteraction.Press -> {
                        if (immediate && interaction != pressInteraction) return@collect
                        animateToTilt(targetX, targetY)
                    }

                    is PressInteraction.Release -> {
                        if (immediate && interaction.press != pressInteraction) return@collect
                        animateToTilt(0f, 0f)
                    }

                    is PressInteraction.Cancel -> animateToTilt(0f, 0f)
                }
            }
        }
    }

    override fun onPointerEvent(pointerEvent: PointerEvent, pass: PointerEventPass, bounds: IntSize) {
        if (pass != PointerEventPass.Main) return
        if (pointerEvent.type == PointerEventType.Press) {
            val offset = pointerEvent.changes.first().position

            transformOrigin = TransformOrigin(
                pivotFractionX = if (offset.x < bounds.width / 2f) 1f else 0f,
                pivotFractionY = if (offset.y < bounds.height / 2f) 1f else 0f
            )

            targetX = if (offset.y < bounds.height / 2f) tiltAmount else -tiltAmount
            targetY = if (offset.x < bounds.width / 2f) -tiltAmount else tiltAmount
        }
        if (!immediate) return
        val currentPressed = pointerEvent.changes.any { it.pressed }
        if (currentPressed != isPressed) {
            isPressed = currentPressed
            if (isPressed) {
                pressInteraction = PressInteraction.Press(pointerEvent.changes.first().position)
                interactionSource.tryEmit(pressInteraction)
            } else interactionSource.tryEmit(PressInteraction.Release(pressInteraction))
        }
    }

    override fun onCancelPointerInput() {
        if (!immediate) return
        interactionSource.tryEmit(PressInteraction.Cancel(pressInteraction))
    }

    override fun MeasureScope.measure(measurable: Measurable, constraints: Constraints): MeasureResult {
        val placeable = measurable.measure(constraints)
        return layout(placeable.width, placeable.height) {
            placeable.placeWithLayer(0, 0) {
                rotationX = animatedTiltX.value
                rotationY = animatedTiltY.value
                cameraDistance = 12 * density
                this.transformOrigin = this@PressTiltNode.transformOrigin
            }
        }
    }
}

private data class PressTiltElement(
    val interactionSource: MutableInteractionSource,
    val tiltAmount: Float,
    val animationSpec: AnimationSpec<Float>,
    val immediate: Boolean
) : ModifierNodeElement<PressTiltNode>() {
    override fun create() = PressTiltNode(interactionSource, tiltAmount, animationSpec, immediate)
    override fun update(node: PressTiltNode) {
        node.interactionSource = interactionSource
        node.tiltAmount = tiltAmount
        node.animationSpec = animationSpec
        node.immediate = immediate
    }

    override fun InspectorInfo.inspectableProperties() {
        name = "PressTiltNode"
        properties["tiltAmount"] = tiltAmount
    }
}

/**
 * Applies a "tilt" effect based on the position where the user pressed the component.
 * The tilt direction is determined by touch offset,
 * giving the effect that one corner "sinks" while the other "static".
 *
 * The impact of [immediate] on feedback effect:
 * `false` - Press Compose default logic and wait for touch confirmation before starting the animation (consistent with Indication).
 * `true` - The animation is started directly when touched, even if it is subsequently scrolled or consumed.
 *
 * @param interactionSource The interaction source to use to detect presses.
 * @param tiltAmount Maximum rotation (in degrees) to apply along X and Y axes.
 * @param animationSpec The animation spec to use when rotating.
 * @param immediate Whether to trigger the press effect and Indication immediately.
 */
fun Modifier.pressTilt(
    interactionSource: MutableInteractionSource,
    tiltAmount: Float = 8f,
    animationSpec: AnimationSpec<Float> = spring(0.6f, 400f),
    immediate: Boolean = false
): Modifier = this then PressTiltElement(
    interactionSource, tiltAmount, animationSpec, immediate
)

/**
 * The type of visual feedback to apply when the component is pressed.
 */
enum class PressFeedbackType {
    /** No feedback effect. */
    None,

    /** Sinks slightly when pressed. */
    Sink,

    /** Tilts based on touch position when pressed. */
    Tilt
}
