package top.yukonga.miuix.kmp.extra

import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.captionBar
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.BlendModeColorFilter
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import top.yukonga.miuix.kmp.basic.BasicComponent
import top.yukonga.miuix.kmp.basic.BasicComponentColors
import top.yukonga.miuix.kmp.basic.BasicComponentDefaults
import top.yukonga.miuix.kmp.basic.Box
import top.yukonga.miuix.kmp.basic.Text
import top.yukonga.miuix.kmp.icon.MiuixIcons
import top.yukonga.miuix.kmp.icon.icons.ArrowUpDownIntegrated
import top.yukonga.miuix.kmp.icon.icons.Check
import top.yukonga.miuix.kmp.interfaces.HoldDownInteraction
import top.yukonga.miuix.kmp.theme.MiuixTheme
import top.yukonga.miuix.kmp.utils.BackHandler
import top.yukonga.miuix.kmp.utils.MiuixPopupUtil.Companion.dismissPopup
import top.yukonga.miuix.kmp.utils.MiuixPopupUtil.Companion.showPopup
import top.yukonga.miuix.kmp.utils.SmoothRoundedCornerShape
import top.yukonga.miuix.kmp.utils.getWindowSize
import kotlin.math.roundToInt

/**
 * A dropdown with a title and a summary.
 *
 * @param title The title of the [SuperDropdown].
 * @param items The options of the [SuperDropdown].
 * @param selectedIndex The index of the selected option.
 * @param modifier The modifier to be applied to the [SuperDropdown].
 * @param popupModifier The modifier to be applied to the popup of the [SuperDropdown].
 * @param titleColor The color of the title.
 * @param summary The summary of the [SuperDropdown].
 * @param summaryColor The color of the summary.
 * @param mode The dropdown show mode of the [SuperDropdown].
 * @param horizontalPadding The horizontal padding of the [SuperDropdown].
 * @param insideMargin The margin inside the [SuperDropdown].
 * @param defaultWindowInsetsPadding Whether to apply default window insets padding to the [SuperDropdown].
 * @param enabled Whether the [SuperDropdown] is enabled.
 * @param showValue Whether to show the selected value of the [SuperDropdown].
 * @param onSelectedIndexChange The callback when the selected index of the [SuperDropdown] is changed.
 */
@Composable
fun SuperDropdown(
    title: String,
    items: List<String>,
    selectedIndex: Int,
    modifier: Modifier = Modifier,
    popupModifier: Modifier = Modifier,
    titleColor: BasicComponentColors = BasicComponentDefaults.titleColor(),
    summary: String? = null,
    summaryColor: BasicComponentColors = BasicComponentDefaults.summaryColor(),
    mode: DropDownMode = DropDownMode.Normal,
    horizontalPadding: Dp = 0.dp,
    insideMargin: PaddingValues = BasicComponentDefaults.InsideMargin,
    defaultWindowInsetsPadding: Boolean = true,
    enabled: Boolean = true,
    showValue: Boolean = true,
    onSelectedIndexChange: ((Int) -> Unit)?,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isDropdownPreExpand = remember { mutableStateOf(false) }
    val isDropdownExpanded = remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val held = remember { mutableStateOf<HoldDownInteraction.Hold?>(null) }
    val hapticFeedback = LocalHapticFeedback.current
    val actionColor = if (enabled) MiuixTheme.colorScheme.onSurfaceVariantActions else MiuixTheme.colorScheme.disabledOnSecondaryVariant
    var alignLeft by rememberSaveable { mutableStateOf(true) }
    var componentInnerOffsetXPx by remember { mutableIntStateOf(0) }
    var componentInnerOffsetYPx by remember { mutableIntStateOf(0) }
    var componentInnerHeightPx by remember { mutableIntStateOf(0) }
    var componentInnerWidthPx by remember { mutableIntStateOf(0) }

    val density = LocalDensity.current
    val getWindowSize = rememberUpdatedState(getWindowSize())
    val windowHeightPx by rememberUpdatedState(getWindowSize.value.height)
    val windowWidthPx by rememberUpdatedState(getWindowSize.value.width)
    var transformOrigin by remember { mutableStateOf(TransformOrigin.Center) }

    DisposableEffect(Unit) {
        onDispose {
            dismissPopup(isDropdownExpanded)
        }
    }

    if (!isDropdownExpanded.value) {
        held.value?.let { oldValue ->
            coroutineScope.launch {
                interactionSource.emit(HoldDownInteraction.Release(oldValue))
            }
            held.value = null
        }
    }

    BasicComponent(
        modifier = modifier
            .indication(
                interactionSource = interactionSource,
                indication = LocalIndication.current
            )
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = { position ->
                        if (enabled) {
                            alignLeft = position.x < (size.width / 2)
                            isDropdownPreExpand.value = true
                            val pressInteraction = PressInteraction.Press(position)
                            coroutineScope.launch {
                                interactionSource.emit(pressInteraction)
                            }
                            val released = tryAwaitRelease()
                            isDropdownPreExpand.value = false
                            if (released) {
                                isDropdownExpanded.value = enabled
                                hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                                coroutineScope.launch {
                                    interactionSource.emit(HoldDownInteraction.Hold().also {
                                        held.value = it
                                    })
                                    interactionSource.emit(PressInteraction.Release(pressInteraction))
                                }
                            } else {
                                coroutineScope.launch {
                                    interactionSource.emit(PressInteraction.Cancel(pressInteraction))
                                }
                            }
                        }
                    }
                )
            },
        insideMargin = insideMargin,
        title = title,
        titleColor = titleColor,
        summary = summary,
        summaryColor = summaryColor,
        leftAction = {
            if (isDropdownPreExpand.value) {
                Layout(
                    content = {},
                    modifier = Modifier.onGloballyPositioned { childCoordinates ->
                        val parentCoordinates =
                            childCoordinates.parentLayoutCoordinates ?: return@onGloballyPositioned
                        val positionInWindow = parentCoordinates.positionInWindow()
                        componentInnerOffsetXPx = positionInWindow.x.toInt()
                        componentInnerOffsetYPx = positionInWindow.y.toInt()
                        componentInnerHeightPx = parentCoordinates.size.height
                        componentInnerWidthPx = parentCoordinates.size.width
                        with(density) {
                            val xInWindow = componentInnerOffsetXPx + if (mode == DropDownMode.AlwaysOnRight || !alignLeft)
                                componentInnerWidthPx - 64.dp.roundToPx()
                            else
                                64.dp.roundToPx()
                            val yInWindow = componentInnerOffsetYPx + componentInnerHeightPx / 2 - 56.dp.roundToPx()
                            transformOrigin = TransformOrigin(
                                xInWindow / windowWidthPx.toFloat(),
                                yInWindow / windowHeightPx.toFloat()
                            )
                        }
                    }
                ) { _,_ ->
                    layout(0,0) {}
                }
            }
        },
        rightActions = {
            if (showValue) {
                Text(
                    modifier = Modifier.widthIn(max = 130.dp),
                    text = items[selectedIndex],
                    fontSize = MiuixTheme.textStyles.body2.fontSize,
                    color = actionColor,
                    textAlign = TextAlign.End,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2
                )
            }
            Image(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .size(10.dp, 16.dp)
                    .align(Alignment.CenterVertically),
                imageVector = MiuixIcons.ArrowUpDownIntegrated,
                colorFilter = ColorFilter.tint(actionColor),
                contentDescription = null
            )
        },
        enabled = enabled
    )
    if (isDropdownExpanded.value) {
        if (!dropdownStates.contains(isDropdownExpanded)) dropdownStates.add(isDropdownExpanded)
        LaunchedEffect(isDropdownExpanded.value) {
            if (isDropdownExpanded.value) {
                dropdownStates.forEach { state -> if (state != isDropdownExpanded) state.value = false }
            }
        }

        val textMeasurer = rememberTextMeasurer()
        val textStyle = remember { TextStyle(fontWeight = FontWeight.Medium, fontSize = 16.sp) }
        val textWidthDp = remember(items) { items.maxOfOrNull { with(density) { textMeasurer.measure(text = it, style = textStyle).size.width.toDp() } } }
        val statusBarPx by rememberUpdatedState(
            with(density) { WindowInsets.statusBars.asPaddingValues().calculateTopPadding().toPx() }.roundToInt()
        )
        val navigationBarPx by rememberUpdatedState(
            with(density) { WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding().toPx() }.roundToInt()
        )
        val captionBarPx by rememberUpdatedState(
            with(density) { WindowInsets.captionBar.asPaddingValues().calculateBottomPadding().toPx() }.roundToInt()
        )
        val dropdownElevation by rememberUpdatedState(with(density) {
            11.dp.toPx()
        })
        val insideTopPx by rememberUpdatedState(with(density) {
            insideMargin.calculateTopPadding().toPx()
        }.roundToInt())
        val insideBottomPx by rememberUpdatedState(with(density) {
            insideMargin.calculateBottomPadding().toPx()
        }.roundToInt())
        val displayCutoutLeftSize by rememberUpdatedState(with(density) {
            WindowInsets.displayCutout.asPaddingValues(density).calculateLeftPadding(LayoutDirection.Ltr).toPx()
        }.roundToInt())
        val paddingPx by rememberUpdatedState(with(density) { horizontalPadding.toPx() }.roundToInt())

        BackHandler(enabled = isDropdownExpanded.value) {
            dismissPopup(isDropdownExpanded)
        }

        showPopup(
            transformOrigin = { transformOrigin },
            content = {
                Box(
                    modifier = if (defaultWindowInsetsPadding) {
                        popupModifier
                            .windowInsetsPadding(WindowInsets.displayCutout.only(WindowInsetsSides.Horizontal))
                    } else {
                        popupModifier
                    }
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onTap = {
                                    dismissPopup(isDropdownExpanded)
                                }
                            )
                        }
                        .layout { measurable, constraints ->
                            val placeable = measurable.measure(constraints.copy(
                                minWidth = 200.dp.roundToPx(),
                                minHeight = 50.dp.roundToPx(),
                                maxHeight = windowHeightPx - statusBarPx - navigationBarPx - captionBarPx
                            ))
                            layout(constraints.maxWidth, constraints.maxHeight) {
                                val xCoordinate = calculateOffsetXPx(
                                    componentInnerOffsetXPx,
                                    componentInnerWidthPx,
                                    placeable.width,
                                    paddingPx,
                                    displayCutoutLeftSize,
                                    defaultWindowInsetsPadding,
                                    (mode == DropDownMode.AlwaysOnRight || !alignLeft)
                                )
                                val yCoordinate = calculateOffsetYPx(
                                    windowHeightPx,
                                    componentInnerOffsetYPx,
                                    placeable.height,
                                    componentInnerHeightPx,
                                    insideTopPx,
                                    insideBottomPx,
                                    statusBarPx,
                                    navigationBarPx,
                                    captionBarPx
                                )
                                placeable.place(xCoordinate, yCoordinate)
                            }
                        }
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .align(AbsoluteAlignment.TopLeft)
                            .graphicsLayer(
                                shadowElevation = dropdownElevation,
                                shape = SmoothRoundedCornerShape(16.dp),
                                ambientShadowColor = Color.Black.copy(alpha = 0.3f),
                                spotShadowColor = Color.Black.copy(alpha = 0.3f)
                            )
                            .clip(SmoothRoundedCornerShape(16.dp))
                            .background(MiuixTheme.colorScheme.surface)
                    ) {
                        items(items.size) { index ->
                            DropdownImpl(
                                text = items[index],
                                optionSize = items.size,
                                isSelected = selectedIndex == index,
                                onSelectedIndexChange = {
                                    hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                                    onSelectedIndexChange?.let { it1 -> it1(it) }
                                    dismissPopup(isDropdownExpanded)
                                },
                                textWidthDp = textWidthDp,
                                index = index
                            )
                        }
                    }
                }
            }
        )
    }
}

/**
 * The implementation of the dropdown.
 *
 * @param text The text of the current option.
 * @param optionSize The size of the options.
 * @param isSelected Whether the option is selected.
 * @param index The index of the current option in the options.
 * @param onSelectedIndexChange The callback when the index is selected.
 * @param textWidthDp The maximum width of text in options.
 */
@Composable
fun DropdownImpl(
    text: String,
    optionSize: Int,
    isSelected: Boolean,
    index: Int,
    onSelectedIndexChange: (Int) -> Unit,
    textWidthDp: Dp?
) {
    val additionalTopPadding = if (index == 0) 20f.dp else 12f.dp
    val additionalBottomPadding = if (index == optionSize - 1) 20f.dp else 12f.dp
    val textColor = if (isSelected) {
        MiuixTheme.colorScheme.onTertiaryContainer
    } else {
        MiuixTheme.colorScheme.onSurface
    }
    val selectColor = if (isSelected) {
        MiuixTheme.colorScheme.onTertiaryContainer
    } else {
        Color.Transparent
    }
    val backgroundColor = if (isSelected) {
        MiuixTheme.colorScheme.tertiaryContainer
    } else {
        MiuixTheme.colorScheme.surface
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .clickable {
                onSelectedIndexChange(index)
            }
            .background(backgroundColor)
            .widthIn(200.dp, 288.dp)
            .padding(horizontal = 20.dp)
            .padding(top = additionalTopPadding, bottom = additionalBottomPadding)
    ) {
        Text(
            modifier = Modifier.width(textWidthDp ?: 50.dp),
            text = text,
            fontSize = MiuixTheme.textStyles.body1.fontSize,
            fontWeight = FontWeight.Medium,
            color = textColor,
        )
        Image(
            modifier = Modifier.padding(start = 12.dp).size(20.dp),
            imageVector = MiuixIcons.Check,
            colorFilter = BlendModeColorFilter(selectColor, BlendMode.SrcIn),
            contentDescription = null,
        )
    }
}

fun calculateOffsetXPx(
    componentInnerOffsetXPx: Int,
    componentInnerWidthPx: Int,
    dropdownWidthPx: Int,
    extraPaddingPx: Int,
    displayCutoutLeftSizePx: Int,
    defaultWindowInsetsPadding: Boolean,
    alignRight: Boolean
): Int {
    return if (alignRight) {
        componentInnerOffsetXPx + componentInnerWidthPx - dropdownWidthPx - extraPaddingPx - if (defaultWindowInsetsPadding) displayCutoutLeftSizePx else 0
    } else {
        componentInnerOffsetXPx + extraPaddingPx - if (defaultWindowInsetsPadding) displayCutoutLeftSizePx else 0
    }
}

/**
 * Calculate the offset of the dropdown.
 *
 * @param windowHeightPx The height of the window.
 * @param dropdownOffsetPx The default offset of the dropdown.
 * @param dropdownHeightPx The height of the dropdown.
 * @param componentHeightPx The height of the component.
 * @param insideTopHeightPx The height of the top component inside.
 * @param insideBottomHeightPx The height of the bottom component inside.
 * @param statusBarPx The height of the status bar padding.
 * @param navigationBarPx The height of the navigation bar padding.
 * @param captionBarPx The height of the caption bar padding.
 * @return The offset of the current dropdown.
 */
fun calculateOffsetYPx(
    windowHeightPx: Int,
    dropdownOffsetPx: Int,
    dropdownHeightPx: Int,
    componentHeightPx: Int,
    insideTopHeightPx: Int,
    insideBottomHeightPx: Int,
    statusBarPx: Int,
    navigationBarPx: Int,
    captionBarPx: Int
): Int {
    return (if (windowHeightPx - captionBarPx - navigationBarPx - dropdownOffsetPx - componentHeightPx > dropdownHeightPx) {
        // Show below
        dropdownOffsetPx + componentHeightPx + insideBottomHeightPx / 2
    } else if (dropdownOffsetPx - statusBarPx > dropdownHeightPx) {
        // Show above
        dropdownOffsetPx - dropdownHeightPx - insideTopHeightPx / 2
    } else {
        // Middle
        dropdownOffsetPx + componentHeightPx / 2 - dropdownHeightPx / 2
    }).coerceIn(statusBarPx, windowHeightPx - captionBarPx - navigationBarPx)
}

/**
 * Only one dropdown is allowed to be displayed at a time.
 */
val dropdownStates = mutableStateListOf<MutableState<Boolean>>()

/**
 * The dropdown show mode.
 */
enum class DropDownMode {
    Normal,
    AlwaysOnRight
}
