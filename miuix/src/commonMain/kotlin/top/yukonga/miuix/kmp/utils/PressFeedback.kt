// Copyright 2025, miuix-kotlin-multiplatform contributors
// SPDX-License-Identifier: Apache-2.0

package top.yukonga.miuix.kmp.utils

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.spring
import androidx.compose.foundation.IndicationNodeFactory
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.node.DelegatableNode
import androidx.compose.ui.node.LayoutModifierNode
import androidx.compose.ui.node.PointerInputModifierNode
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.IntSize
import kotlinx.coroutines.launch

@Immutable
data class SinkFeedback(
    val sinkAmount: Float = 0.94f,
    val animationSpec: AnimationSpec<Float> = spring(0.8f, 600f)
) : IndicationNodeFactory {
    override fun create(interactionSource: InteractionSource): DelegatableNode =
        SinkFeedbackNode(interactionSource, sinkAmount, animationSpec)

    private class SinkFeedbackNode(
        var interactionSource: InteractionSource?,
        var sinkAmount: Float,
        var animationSpec: AnimationSpec<Float>
    ) : Modifier.Node(), LayoutModifierNode {

        private val animatedScale = Animatable(1f)

        private fun animateToSink(target: Float) {
            coroutineScope.launch { animatedScale.animateTo(target, animationSpec) }
        }

        override fun onAttach() {
            coroutineScope.launch {
                interactionSource?.interactions?.collect { interaction: Interaction ->
                    when (interaction) {
                        is PressInteraction.Press -> animateToSink(sinkAmount)
                        is PressInteraction.Release -> animateToSink(1f)
                        is PressInteraction.Cancel -> animateToSink(1f)
                    }
                }
            }
        }

        override fun MeasureScope.measure(
            measurable: Measurable,
            constraints: Constraints
        ): MeasureResult {
            val placeable = measurable.measure(constraints)
            return layout(placeable.width, placeable.height) {
                placeable.placeWithLayer(0, 0) {
                    scaleX = animatedScale.value
                    scaleY = animatedScale.value
                }
            }
        }
    }
}

@Immutable
data class TiltFeedback(
    val tiltAmount: Float = 8f,
    val animationSpec: AnimationSpec<Float> = spring(0.6f, 400f)
) : IndicationNodeFactory {
    override fun create(interactionSource: InteractionSource): DelegatableNode =
        TiltFeedbackNode(interactionSource, tiltAmount, animationSpec)

    private class TiltFeedbackNode(
        var interactionSource: InteractionSource?,
        var tiltAmount: Float,
        var animationSpec: AnimationSpec<Float>
    ) : Modifier.Node(), LayoutModifierNode, PointerInputModifierNode {

        private var transformOrigin: TransformOrigin = TransformOrigin.Center
        private var targetX = 0f
        private var targetY = 0f
        private val animatedTiltX = Animatable(0f)
        private val animatedTiltY = Animatable(0f)

        private fun animateToTilt(
            x: Float,
            y: Float
        ) {
            coroutineScope.launch { animatedTiltX.animateTo(x, animationSpec) }
            coroutineScope.launch { animatedTiltY.animateTo(y, animationSpec) }
        }

        override fun onAttach() {
            coroutineScope.launch {
                interactionSource?.interactions?.collect { interaction: Interaction ->
                    when (interaction) {
                        is PressInteraction.Press -> animateToTilt(targetX, targetY)
                        is PressInteraction.Release -> animateToTilt(0f, 0f)
                        is PressInteraction.Cancel -> animateToTilt(0f, 0f)
                    }
                }
            }
        }

        override fun onPointerEvent(
            pointerEvent: PointerEvent,
            pass: PointerEventPass,
            bounds: IntSize
        ) {
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
        }

        override fun onCancelPointerInput() {
            transformOrigin = TransformOrigin.Center
            targetX = 0f
            targetY = 0f
        }

        override fun MeasureScope.measure(
            measurable: Measurable,
            constraints: Constraints
        ): MeasureResult {
            val placeable = measurable.measure(constraints)
            return layout(placeable.width, placeable.height) {
                placeable.placeWithLayer(0, 0) {
                    rotationX = animatedTiltX.value
                    rotationY = animatedTiltY.value
                    cameraDistance = 12 * density
                    this.transformOrigin = this@TiltFeedbackNode.transformOrigin
                }
            }
        }
    }
}

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
