package top.yukonga.miuix.kmp.basic

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.LayoutScopeMarker
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.node.ParentDataModifierNode
import androidx.compose.ui.platform.InspectorInfo
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.util.fastForEachIndexed
import kotlin.math.max

/**
 * A box composable with Miuix style. A layout composable with [content].
 *
 * The [MiuixBox] will size itself to fit the content, subject to the incoming constraints.
 * When children are smaller than the parent, by default they will be positioned inside
 * the [MiuixBox] according to the [contentAlignment]. For individually specifying the alignments
 * of the children layouts, use the [MiuixBoxScope.align] modifier.
 * By default, the content will be measured without the [MiuixBox]'s incoming min constraints,
 * unless [propagateMinConstraints] is `true`. As an example, setting [propagateMinConstraints] to
 * `true` can be useful when the [MiuixBox] has content on which modifiers cannot be specified
 * directly and setting a min size on the content of the [MiuixBox] is needed. If
 * [propagateMinConstraints] is set to `true`, the min size set on the [MiuixBox] will also be
 * applied to the content, whereas otherwise the min size will only apply to the [MiuixBox].
 * When the content has more than one layout child the layout children will be stacked one
 * on top of the other (positioned as explained above) in the composition order.
 *
 * @param modifier The modifier to be applied to the layout.
 * @param contentAlignment The default alignment inside the MiuixBox.
 * @param propagateMinConstraints Whether the incoming min constraints should be passed to content.
 * @param content The content of the [MiuixBox].
 */
@Composable
inline fun MiuixBox(
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.TopStart,
    propagateMinConstraints: Boolean = false,
    content: @Composable MiuixBoxScope.() -> Unit
) {
    val measurePolicy = maybeCachedMiuixBoxMeasurePolicy(contentAlignment, propagateMinConstraints)
    Layout(
        content = { MiuixBoxScopeInstance.content() },
        measurePolicy = measurePolicy,
        modifier = modifier
    )
}

private fun cacheFor(
    propagateMinConstraints: Boolean
) = HashMap<Alignment, MeasurePolicy>(9).apply {
    fun putAlignment(it: Alignment) {
        put(it, MiuixBoxMeasurePolicy(it, propagateMinConstraints))
    }
    putAlignment(Alignment.TopStart)
    putAlignment(Alignment.TopCenter)
    putAlignment(Alignment.TopEnd)
    putAlignment(Alignment.CenterStart)
    putAlignment(Alignment.Center)
    putAlignment(Alignment.CenterEnd)
    putAlignment(Alignment.BottomStart)
    putAlignment(Alignment.BottomCenter)
    putAlignment(Alignment.BottomEnd)
}

private val cache1 = cacheFor(true)
private val cache2 = cacheFor(false)

@PublishedApi
internal fun maybeCachedMiuixBoxMeasurePolicy(
    alignment: Alignment,
    propagateMinConstraints: Boolean
): MeasurePolicy {
    val cache = if (propagateMinConstraints) cache1 else cache2
    return cache[alignment] ?: MiuixBoxMeasurePolicy(alignment, propagateMinConstraints)
}

private data class MiuixBoxMeasurePolicy(
    private val alignment: Alignment,
    private val propagateMinConstraints: Boolean
) : MeasurePolicy {
    override fun MeasureScope.measure(
        measurables: List<Measurable>,
        constraints: Constraints
    ): MeasureResult {
        if (measurables.isEmpty()) {
            return layout(
                constraints.minWidth,
                constraints.minHeight
            ) {}
        }

        val contentConstraints = if (propagateMinConstraints) {
            constraints
        } else {
            constraints.copy(minWidth = 0, minHeight = 0)
        }

        if (measurables.size == 1) {
            val measurable = measurables[0]
            val mWidth: Int
            val mHeight: Int
            val placeable: Placeable
            if (!measurable.matchesParentSize) {
                placeable = measurable.measure(contentConstraints)
                mWidth = max(constraints.minWidth, placeable.width)
                mHeight = max(constraints.minHeight, placeable.height)
            } else {
                mWidth = constraints.minWidth
                mHeight = constraints.minHeight
                placeable = measurable.measure(
                    Constraints.fixed(constraints.minWidth, constraints.minHeight)
                )
            }
            return layout(mWidth, mHeight) {
                placeInMiuixBox(placeable, measurable, layoutDirection, mWidth, mHeight, alignment)
            }
        }

        val placeables = arrayOfNulls<Placeable>(measurables.size)
        // First measure non match parent size children to get the size of the MiuixBox.
        var hasMatchParentSizeChildren = false
        var mWidth = constraints.minWidth
        var mHeight = constraints.minHeight
        measurables.fastForEachIndexed { index, measurable ->
            if (!measurable.matchesParentSize) {
                val placeable = measurable.measure(contentConstraints)
                placeables[index] = placeable
                mWidth = max(mWidth, placeable.width)
                mHeight = max(mHeight, placeable.height)
            } else {
                hasMatchParentSizeChildren = true
            }
        }

        // Now measure match parent size children, if any.
        if (hasMatchParentSizeChildren) {
            // The infinity check is needed for default intrinsic measurements.
            val matchParentSizeConstraints = Constraints(
                minWidth = if (mWidth != Constraints.Infinity) mWidth else 0,
                minHeight = if (mHeight != Constraints.Infinity) mHeight else 0,
                maxWidth = mWidth,
                maxHeight = mHeight
            )
            measurables.fastForEachIndexed { index, measurable ->
                if (measurable.matchesParentSize) {
                    placeables[index] = measurable.measure(matchParentSizeConstraints)
                }
            }
        }

        // Specify the size of the MiuixBox and position its children.
        return layout(mWidth, mHeight) {
            placeables.forEachIndexed { index, placeable ->
                placeable as Placeable
                val measurable = measurables[index]
                placeInMiuixBox(placeable, measurable, layoutDirection, mWidth, mHeight, alignment)
            }
        }
    }
}

private fun Placeable.PlacementScope.placeInMiuixBox(
    placeable: Placeable,
    measurable: Measurable,
    layoutDirection: LayoutDirection,
    mWidth: Int,
    mHeight: Int,
    alignment: Alignment
) {
    val childAlignment = measurable.MiuixBoxChildDataNode?.alignment ?: alignment
    val position = childAlignment.align(
        IntSize(placeable.width, placeable.height),
        IntSize(mWidth, mHeight),
        layoutDirection
    )
    placeable.place(position)
}

/**
 * A MiuixBox with no content that can participate in layout, drawing, pointer input
 * due to the [modifier] applied to it.
 *
 * @param modifier The modifier to be applied to the layout.
 */
@Composable
fun MiuixBox(modifier: Modifier) {
    Layout(measurePolicy = EmptyMiuixBoxMeasurePolicy, modifier = modifier)
}

internal val EmptyMiuixBoxMeasurePolicy = MeasurePolicy { _, constraints ->
    layout(constraints.minWidth, constraints.minHeight) {}
}

/**
 * A MiuixBoxScope provides a scope for the children of [MiuixBox] and [BoxWithConstraints].
 */
@LayoutScopeMarker
@Immutable
interface MiuixBoxScope {
    /**
     * Pull the content element to a specific [Alignment] within the [MiuixBox]. This alignment will
     * have priority over the [MiuixBox]'s `alignment` parameter.
     */
    @Stable
    fun Modifier.align(alignment: Alignment): Modifier

    /**
     * Size the element to match the size of the [MiuixBox] after all other content elements have
     * been measured.
     *
     * The element using this modifier does not take part in defining the size of the [MiuixBox].
     * Instead, it matches the size of the [MiuixBox] after all other children (not using
     * matchParentSize() modifier) have been measured to obtain the [MiuixBox]'s size.
     * In contrast, a general-purpose [Modifier.fillMaxSize] modifier, which makes an element
     * occupy all available space, will take part in defining the size of the [MiuixBox]. Consequently,
     * using it for an element inside a [MiuixBox] will make the [MiuixBox] itself always fill the
     * available space.
     */
    @Stable
    fun Modifier.matchParentSize(): Modifier
}

internal object MiuixBoxScopeInstance : MiuixBoxScope {
    @Stable
    override fun Modifier.align(alignment: Alignment) = this.then(
        MiuixBoxChildDataElement(
            alignment = alignment,
            matchParentSize = false,
            inspectorInfo = debugInspectorInfo {
                name = "align"
                value = alignment
            }
        ))

    @Stable
    override fun Modifier.matchParentSize() = this.then(
        MiuixBoxChildDataElement(
            alignment = Alignment.Center,
            matchParentSize = true,
            inspectorInfo = debugInspectorInfo {
                name = "matchParentSize"
            }
        ))
}

private val Measurable.MiuixBoxChildDataNode: MiuixBoxChildDataNode? get() = parentData as? MiuixBoxChildDataNode
private val Measurable.matchesParentSize: Boolean get() = MiuixBoxChildDataNode?.matchParentSize ?: false

private class MiuixBoxChildDataElement(
    val alignment: Alignment,
    val matchParentSize: Boolean,
    val inspectorInfo: InspectorInfo.() -> Unit

) : ModifierNodeElement<MiuixBoxChildDataNode>() {
    override fun create(): MiuixBoxChildDataNode {
        return MiuixBoxChildDataNode(alignment, matchParentSize)
    }

    override fun update(node: MiuixBoxChildDataNode) {
        node.alignment = alignment
        node.matchParentSize = matchParentSize
    }

    override fun InspectorInfo.inspectableProperties() {
        inspectorInfo()
    }

    override fun hashCode(): Int {
        var result = alignment.hashCode()
        result = 31 * result + matchParentSize.hashCode()
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        val otherModifier = other as? MiuixBoxChildDataElement ?: return false
        return alignment == otherModifier.alignment &&
                matchParentSize == otherModifier.matchParentSize
    }
}

private class MiuixBoxChildDataNode(
    var alignment: Alignment,
    var matchParentSize: Boolean,
) : ParentDataModifierNode, Modifier.Node() {
    override fun Density.modifyParentData(parentData: Any?) = this@MiuixBoxChildDataNode
}

@Stable
private fun Modifier.surface(
    shape: Shape,
    backgroundColor: Color,
    border: BorderStroke?,
    shadowElevation: Float,
) =
    this.then(
        if (shadowElevation > 0f) {
            Modifier.graphicsLayer(
                shadowElevation = shadowElevation,
                shape = shape,
                clip = false
            )
        } else {
            Modifier
        }
    )
        .then(if (border != null) Modifier.border(border, shape) else Modifier)
        .background(color = backgroundColor, shape = shape)
        .clip(shape)