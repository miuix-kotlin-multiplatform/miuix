// Copyright 2025, miuix-kotlin-multiplatform contributors
// SPDX-License-Identifier: Apache-2.0

package top.yukonga.miuix.kmp.utils

import androidx.compose.foundation.Indication
import androidx.compose.foundation.IndicationNodeFactory
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.gestures.GestureCancellationException
import androidx.compose.foundation.gestures.PressGestureScope
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.PointerInputScope
import androidx.compose.ui.input.pointer.SuspendingPointerInputModifierNode
import androidx.compose.ui.node.DelegatableNode
import androidx.compose.ui.node.DelegatingNode
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.node.PointerInputModifierNode
import androidx.compose.ui.node.SemanticsModifierNode
import androidx.compose.ui.node.TraversableNode
import androidx.compose.ui.node.invalidateSemantics
import androidx.compose.ui.platform.InspectorInfo
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.SemanticsPropertyReceiver
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.role
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.center
import androidx.compose.ui.unit.toOffset
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex

/**
 * Configure component to receive press via accessibility "press" event.
 *
 * Add this modifier to the element to make it pressable within its bounds and show a default
 * indication when it's pressed.
 *
 * This version has no [MutableInteractionSource] or [Indication] parameters, the default indication
 * will use `SinkFeedback()`. To specify [MutableInteractionSource] or [Indication], use
 * the other overload.
 *
 * If you are only creating this clickable modifier inside composition, consider using the other
 * overload and explicitly passing `LocalIndication.current` for improved performance. For more
 * information see the documentation on the other overload.
 *
 *
 * @param enabled Controls the enabled state. When `false`, this modifier will appear
 *   disabled for accessibility services
 * @param role the type of user interface element. Accessibility services might use this to describe
 *   the element or do customizations
 * @param delay how long to wait before appearing 'pressed' (emitting [PressInteraction.Press]).
 *   If `null`, even if the animation is subsequently scrolled or consumed, a "pressed" appears directly.
 */
fun Modifier.pressable(
    enabled: Boolean = true,
    role: Role? = null,
    delay: Long? = TapIndicationDelay
) = composed(
    inspectorInfo = debugInspectorInfo {
        name = "clickable"
        properties["enabled"] = enabled
        properties["role"] = role
        properties["delay"] = delay
    }) {
    Modifier.pressable(
        interactionSource = remember { MutableInteractionSource() },
        indication = SinkFeedback(),
        enabled = enabled,
        role = role,
        delay = delay
    )
}

/**
 * Configure component to receive press via accessibility "press" event.
 *
 * Add this modifier to the element to make it pressable within its bounds and show an indication as
 * specified in [indication] parameter.
 *
 * If [interactionSource] is `null`, and [indication] is an [IndicationNodeFactory], an internal
 * [MutableInteractionSource] will be lazily created along with the [indication] only when needed.
 * This reduces the performance cost of clickable during composition, as creating the [indication]
 * can be delayed until there is an incoming [androidx.compose.foundation.interaction.Interaction].
 * If you are only passing a remembered [MutableInteractionSource] and you are never using it
 * outside of clickable, it is recommended to instead provide `null` to enable lazy creation. If you
 * need [indication] to be created eagerly, provide a remembered [MutableInteractionSource].
 *
 * If [indication] is _not_ an [IndicationNodeFactory], and instead implements the deprecated
 * [Indication.rememberUpdatedInstance] method, you should explicitly pass a remembered
 * [MutableInteractionSource] as a parameter for [interactionSource] instead of `null`, as this
 * cannot be lazily created inside pressable.
 *
 *
 * @param interactionSource [MutableInteractionSource] that will be used to dispatch
 *   [PressInteraction.Press] when this pressable is pressed. If `null`, an internal
 *   [MutableInteractionSource] will be created if needed.
 * @param indication indication to be shown when modified element is pressed. By default, indication
 *   from [LocalIndication] will be used. Pass `null` to show no indication, or current value from
 *   [LocalIndication] to show theme default
 * @param enabled Controls the enabled state. When `false`, this modifier will appear disabled for
 *   accessibility services
 * @param role the type of user interface element. Accessibility services might use this to describe
 *   the element or do customizations
 * @param delay how long to wait before appearing 'pressed' (emitting [PressInteraction.Press]).
 *   If `null`, even if the animation is subsequently scrolled or consumed, a "pressed" appears directly.
 */
fun Modifier.pressable(
    interactionSource: MutableInteractionSource?,
    indication: Indication? = null,
    enabled: Boolean = true,
    role: Role? = null,
    delay: Long? = TapIndicationDelay
) = pressableWithIndicationIfNeeded(
    interactionSource = interactionSource, indication = indication
) { intSource, indicationNodeFactory ->
    PressableElement(
        interactionSource = intSource,
        indicationNodeFactory = indicationNodeFactory,
        enabled = enabled,
        role = role,
        delay = delay
    )
}

/**
 * Utility Modifier factory that handles edge cases for [interactionSource], and [indication].
 * [createPressable] is the lambda that creates the actual clickable element, which will be chained
 * with [Modifier.indication] if needed.
 */
internal inline fun Modifier.pressableWithIndicationIfNeeded(
    interactionSource: MutableInteractionSource?,
    indication: Indication?,
    crossinline createPressable: (MutableInteractionSource?, IndicationNodeFactory?) -> Modifier
): Modifier {
    return this.then(
        when {
        // Fast path - indication is managed internally
        indication is IndicationNodeFactory -> createPressable(
            interactionSource, indication
        )
        // Fast path - no need for indication
        indication == null -> createPressable(interactionSource, null)
        // Non-null Indication (not IndicationNodeFactory) with a non-null InteractionSource
        interactionSource != null -> Modifier.indication(interactionSource, indication)
            .then(createPressable(interactionSource, null))
        // Non-null Indication (not IndicationNodeFactory) with a null InteractionSource, so we
        // need
        // to use composed to create an InteractionSource that can be shared. This should be a
        // rare
        // code path and can only be hit from new callers.
        else -> Modifier.composed {
            val newInteractionSource = remember { MutableInteractionSource() }
            Modifier.indication(newInteractionSource, indication).then(createPressable(newInteractionSource, null))
        }
    })
}

/**
 * How long to wait before appearing 'pressed' (emitting [PressInteraction.Press]) - if a touch down
 * will quickly become a drag / scroll, this timeout means that we don't show a press effect.
 */
internal const val TapIndicationDelay: Long = 150L

private data class PressableElement(
    private val interactionSource: MutableInteractionSource?,
    private val indicationNodeFactory: IndicationNodeFactory?,
    private val enabled: Boolean,
    private val role: Role?,
    private val delay: Long?
) : ModifierNodeElement<PressableNode>() {
    override fun create() = PressableNode(
        interactionSource, indicationNodeFactory, enabled, role, delay
    )

    override fun update(node: PressableNode) {
        node.update(interactionSource, indicationNodeFactory, enabled, role, delay)
    }

    override fun InspectorInfo.inspectableProperties() {
        name = "pressable"
        properties["enabled"] = enabled
        properties["delay"] = delay
        properties["role"] = role
        properties["interactionSource"] = interactionSource
        properties["indicationNodeFactory"] = indicationNodeFactory
    }
}

internal open class PressableNode(
    interactionSource: MutableInteractionSource?,
    indicationNodeFactory: IndicationNodeFactory?,
    enabled: Boolean,
    role: Role?,
    delay: Long?
) : AbstractPressableNode(
    interactionSource, indicationNodeFactory, enabled, role, delay
) {
    fun update(
        interactionSource: MutableInteractionSource?,
        indicationNodeFactory: IndicationNodeFactory?,
        enabled: Boolean,
        role: Role?,
        delay: Long?
    ) {
        updateCommon(interactionSource, indicationNodeFactory, enabled, role, delay)
    }
}

internal abstract class AbstractPressableNode(
    private var interactionSource: MutableInteractionSource?,
    private var indicationNodeFactory: IndicationNodeFactory?,
    enabled: Boolean,
    private var role: Role?,
    private var delay: Long?
) : DelegatingNode(), PointerInputModifierNode, SemanticsModifierNode, TraversableNode {
    protected var enabled = enabled
        private set

    final override val shouldAutoInvalidate: Boolean = false


    private var pointerInputNode: SuspendingPointerInputModifierNode? = null
    private var indicationNode: DelegatableNode? = null

    private var pressInteraction: PressInteraction.Press? = null
    private var centerOffset: Offset = Offset.Zero

    // Track separately from interactionSource, as we will create our own internal
    // InteractionSource if needed
    private var userProvidedInteractionSource: MutableInteractionSource? = interactionSource

    private var lazilyCreateIndication = shouldLazilyCreateIndication()

    private fun shouldLazilyCreateIndication() = userProvidedInteractionSource == null && indicationNodeFactory != null

    open fun SemanticsPropertyReceiver.applyAdditionalSemantics() {}

    protected fun updateCommon(
        interactionSource: MutableInteractionSource?,
        indicationNodeFactory: IndicationNodeFactory?,
        enabled: Boolean,
        role: Role?,
        delay: Long?
    ) {
        var isIndicationNodeDirty = false
        // Compare against userProvidedInteractionSource, as we will create a new InteractionSource
        // lazily if the userProvidedInteractionSource is null, and assign it to interactionSource
        if (userProvidedInteractionSource != interactionSource) {
            disposeInteractions()
            userProvidedInteractionSource = interactionSource
            this.interactionSource = interactionSource
            isIndicationNodeDirty = true
        }
        if (this.indicationNodeFactory != indicationNodeFactory) {
            this.indicationNodeFactory = indicationNodeFactory
            isIndicationNodeDirty = true
        }
        if (this.enabled != enabled) {
            if (!enabled) {
                disposeInteractions()
            }
            invalidateSemantics()
            this.enabled = enabled
        }
        if (this.role != role) {
            this.role = role
            invalidateSemantics()
        }
        if (this.delay != delay) {
            this.delay = delay
            invalidateSemantics()
        }
        if (lazilyCreateIndication != shouldLazilyCreateIndication()) {
            lazilyCreateIndication = shouldLazilyCreateIndication()
            // If we are no longer lazily creating the node, and we haven't created the node yet,
            // create it
            if (!lazilyCreateIndication && indicationNode == null) isIndicationNodeDirty = true
        }
        // Create / recreate indication node
        if (isIndicationNodeDirty) {
            // If we already created a node lazily, or we are not lazily creating the node, create
            if (indicationNode != null || !lazilyCreateIndication) {
                indicationNode?.let { undelegate(it) }
                indicationNode = null
                initializeIndicationAndInteractionSourceIfNeeded()
            }
        }
    }

    final override fun onAttach() {
        if (!lazilyCreateIndication) {
            initializeIndicationAndInteractionSourceIfNeeded()
        }
    }

    final override fun onDetach() {
        disposeInteractions()
        // If we lazily created an interaction source, reset it in case we are reused / moved. Note
        // that we need to do it here instead of onReset() - since onReset won't be called in the
        // movableContent case but we still want to dispose for that case
        if (userProvidedInteractionSource == null) {
            interactionSource = null
        }
        // Remove indication in case we are reused / moved - we will create a new node when needed
        indicationNode?.let { undelegate(it) }
        indicationNode = null
    }

    protected fun disposeInteractions() {
        interactionSource?.let { interactionSource ->
            pressInteraction?.let { oldValue ->
                val interaction = PressInteraction.Cancel(oldValue)
                interactionSource.tryEmit(interaction)
            }
        }
        pressInteraction = null
    }

    private fun initializeIndicationAndInteractionSourceIfNeeded() {
        // We have already created the node, no need to do any work
        if (indicationNode != null) return
        indicationNodeFactory?.let { indicationNodeFactory ->
            if (interactionSource == null) {
                interactionSource = MutableInteractionSource()
            }
            val node = indicationNodeFactory.create(interactionSource!!)
            delegate(node)
            indicationNode = node
        }
    }

    final override fun onPointerEvent(
        pointerEvent: PointerEvent,
        pass: PointerEventPass,
        bounds: IntSize
    ) {
        centerOffset = bounds.center.toOffset()
        initializeIndicationAndInteractionSourceIfNeeded()
        if (pointerInputNode == null) {
            pointerInputNode = delegate(SuspendingPointerInputModifierNode { clickPointerInput() })
        }
        pointerInputNode?.onPointerEvent(pointerEvent, pass, bounds)
        if (indicationNode is PointerInputModifierNode) {
            (indicationNode as? PointerInputModifierNode)?.onPointerEvent(pointerEvent, pass, bounds)
        }
    }

    final override fun onCancelPointerInput() {
        // Press cancellation is handled as part of detecting presses
        pointerInputNode?.onCancelPointerInput()
        if (indicationNode is PointerInputModifierNode) {
            (indicationNode as? PointerInputModifierNode)?.onCancelPointerInput()
        }
    }

    final override val shouldMergeDescendantSemantics: Boolean
        get() = true

    final override fun SemanticsPropertyReceiver.applySemantics() {
        if (this@AbstractPressableNode.role != null) {
            role = this@AbstractPressableNode.role!!
        }
        if (!enabled) {
            disabled()
        }
        applyAdditionalSemantics()
    }

    protected suspend fun PointerInputScope.clickPointerInput() {
        val onPress: suspend PressGestureScope.(Offset) -> Unit = { offset ->
            if (enabled) {
                handlePressInteraction(offset)
            }
        }
        coroutineScope {
            val pressScope = PressGestureScopeImpl(this@clickPointerInput)
            awaitEachGesture {
                val down = awaitFirstDown(requireUnconsumed = false)
                val resetJob = launch { pressScope.reset() }
                launchAwaitingReset(resetJob) { pressScope.onPress(down.position) }
                val upOrCancel = waitForUpOrCancellation()
                if (upOrCancel == null) {
                    launchAwaitingReset(resetJob) { pressScope.cancel() }
                } else {
                    launchAwaitingReset(resetJob) { pressScope.release() }
                }
            }
        }
    }

    private fun CoroutineScope.launchAwaitingReset(
        resetJob: Job,
        block: suspend CoroutineScope.() -> Unit
    ): Job = launch {
        resetJob.join()
        block()
    }

    protected suspend fun PressGestureScope.handlePressInteraction(offset: Offset) {
        interactionSource?.let { interactionSource ->
            coroutineScope {
                val delayJob = launch {
                    delay?.let { delay(it) }
                    val press = PressInteraction.Press(offset)
                    interactionSource.emit(press)
                    pressInteraction = press
                }
                val success = tryAwaitRelease()
                if (delayJob.isActive) {
                    delayJob.cancelAndJoin()
                    // The press released successfully, before the timeout duration - emit the press
                    // interaction instantly. No else branch - if the press was cancelled before the
                    // timeout, we don't want to emit a press interaction.
                    if (success) {
                        val press = PressInteraction.Press(offset)
                        val release = PressInteraction.Release(press)
                        interactionSource.emit(press)
                        interactionSource.emit(release)
                    }
                } else {
                    pressInteraction?.let { pressInteraction ->
                        val endInteraction = if (success) {
                            PressInteraction.Release(pressInteraction)
                        } else {
                            PressInteraction.Cancel(pressInteraction)
                        }
                        interactionSource.emit(endInteraction)
                    }
                }
                pressInteraction = null
            }
        }
    }

    override val traverseKey: Any = TraverseKey

    companion object TraverseKey
}

/** [detectTapGestures]'s implementation of [PressGestureScope]. */
internal class PressGestureScopeImpl(density: Density) : PressGestureScope, Density by density {
    private var isReleased = false
    private var isCanceled = false
    private val mutex = Mutex(locked = false)

    /** Called when a gesture has been canceled. */
    fun cancel() {
        isCanceled = true
        if (mutex.isLocked) {
            mutex.unlock()
        }
    }

    /** Called when all pointers are up. */
    fun release() {
        isReleased = true
        if (mutex.isLocked) {
            mutex.unlock()
        }
    }

    /** Called when a new gesture has started. */
    suspend fun reset() {
        mutex.lock()
        isReleased = false
        isCanceled = false
    }

    override suspend fun awaitRelease() {
        if (!tryAwaitRelease()) {
            throw GestureCancellationException("The press gesture was canceled.")
        }
    }

    override suspend fun tryAwaitRelease(): Boolean {
        if (!isReleased && !isCanceled) {
            mutex.lock()
            mutex.unlock()
        }
        return isReleased
    }
}