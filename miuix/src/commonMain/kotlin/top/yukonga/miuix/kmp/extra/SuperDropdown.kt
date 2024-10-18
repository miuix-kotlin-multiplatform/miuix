package top.yukonga.miuix.kmp.extra

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.FocusInteraction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.captionBar
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import top.yukonga.miuix.kmp.basic.BasicComponent
import top.yukonga.miuix.kmp.basic.Box
import top.yukonga.miuix.kmp.basic.Text
import top.yukonga.miuix.kmp.icon.MiuixIcons
import top.yukonga.miuix.kmp.icon.icons.ArrowUpDown
import top.yukonga.miuix.kmp.icon.icons.Check
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
 * @param modifier The modifier to be applied to the [SuperDropdown].
 * @param popupModifier The modifier to be applied to the popup of the [SuperDropdown].
 * @param title The title of the [SuperDropdown].
 * @param titleColor The color of the title.
 * @param summary The summary of the [SuperDropdown].
 * @param summaryColor The color of the summary.
 * @param items The options of the [SuperDropdown].
 * @param horizontalPadding The horizontal padding of the [SuperDropdown].
 * @param alwaysRight Whether the popup is always show on the right side.
 * @param insideMargin The margin inside the [SuperDropdown].
 * @param defaultWindowInsetsPadding Whether to apply default window insets padding to the [SuperDropdown].
 * @param selectedIndex The index of the selected option.
 * @param onSelectedIndexChange The callback when the index is selected.
 * @param enabled Whether the [SuperDropdown] is enabled.
 */
@Composable
fun SuperDropdown(
    modifier: Modifier = Modifier,
    popupModifier: Modifier = Modifier,
    title: String,
    titleColor: Color = MiuixTheme.colorScheme.onSurface,
    summary: String? = null,
    summaryColor: Color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
    items: List<String>,
    alwaysRight: Boolean = false,
    horizontalPadding: Dp = 0.dp,
    insideMargin: DpSize = DpSize(16.dp, 16.dp),
    defaultWindowInsetsPadding: Boolean = true,
    selectedIndex: Int,
    onSelectedIndexChange: (Int) -> Unit,
    enabled: Boolean = true
) {
    val isDropdownExpanded = remember { mutableStateOf(false) }
    val hapticFeedback = LocalHapticFeedback.current
    val actionColor = if (enabled) MiuixTheme.colorScheme.onSurfaceVariantActions else MiuixTheme.colorScheme.disabledOnSecondaryVariant
    var alignLeft by rememberSaveable { mutableStateOf(true) }
    var dropdownOffsetXPx by remember { mutableStateOf(0) }
    var dropdownOffsetYPx by remember { mutableStateOf(0) }
    var componentHeightPx by remember { mutableStateOf(0) }
    var componentWidthPx by remember { mutableStateOf(0) }
    val interactionSource = remember { MutableInteractionSource() }
    val focus = remember { mutableStateOf<FocusInteraction.Focus?>(null) }

    LaunchedEffect(isDropdownExpanded.value) {
        if (isDropdownExpanded.value) {
            focus.value = FocusInteraction.Focus().also {
                interactionSource.emit(it)
            }
        } else {
            focus.value?.let { interactionSource.emit(FocusInteraction.Unfocus(it)) }
        }
    }

    BasicComponent(
        modifier = modifier
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    while (enabled) {
                        val event = awaitPointerEvent()
                        if (event.type != PointerEventType.Move) {
                            val eventChange = event.changes.first()
                            alignLeft = eventChange.position.x < (size.width / 2)
                        }
                    }
                }
            }
            .onGloballyPositioned { coordinates ->
                if (isDropdownExpanded.value) {
                    val positionInWindow = coordinates.positionInWindow()
                    dropdownOffsetXPx = positionInWindow.x.toInt()
                    dropdownOffsetYPx = positionInWindow.y.toInt()
                    componentHeightPx = coordinates.size.height
                    componentWidthPx = coordinates.size.width
                }
            }
            .clickable(
                interactionSource = interactionSource,
                indication = ripple()
            ) {
                if (enabled) {
                    isDropdownExpanded.value = enabled
                    hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                }
            },
        insideMargin = insideMargin,
        title = title,
        titleColor = titleColor,
        summary = summary,
        summaryColor = summaryColor,
        rightActions = {
            Text(
                modifier = Modifier.padding(end = 6.dp),
                text = items[selectedIndex],
                fontSize = 15.sp,
                color = actionColor,
                textAlign = TextAlign.End,
            )
            Image(
                modifier = Modifier
                    .size(15.dp)
                    .align(Alignment.CenterVertically),
                imageVector = MiuixIcons.ArrowUpDown,
                colorFilter = BlendModeColorFilter(actionColor, BlendMode.SrcIn),
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

        val density = LocalDensity.current
        var offsetXPx by remember { mutableStateOf(0) }
        var offsetYPx by remember { mutableStateOf(0) }
        val textMeasurer = rememberTextMeasurer()
        val textStyle = remember { TextStyle(fontWeight = FontWeight.Medium, fontSize = 17.sp) }
        val textWidthDp = remember(items) { items.maxOfOrNull { with(density) { textMeasurer.measure(text = it, style = textStyle).size.width.toDp() } } }
        val getWindowSize = rememberUpdatedState(getWindowSize())
        val windowHeightPx by rememberUpdatedState(getWindowSize.value.height)
        val statusBarPx by rememberUpdatedState(
            with(density) { WindowInsets.statusBars.asPaddingValues().calculateTopPadding().toPx() }.roundToInt()
        )
        val navigationBarPx by rememberUpdatedState(
            with(density) { WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding().toPx() }.roundToInt()
        )
        val captionBarPx by rememberUpdatedState(
            with(density) { WindowInsets.captionBar.asPaddingValues().calculateBottomPadding().toPx() }.roundToInt()
        )
        val insideWidthPx by rememberUpdatedState(with(density){ insideMargin.width.toPx() }.roundToInt())
        val insideHeightPx by rememberUpdatedState(with(density) { insideMargin.height.toPx() }.roundToInt())
        val displayCutoutLeftSize = rememberUpdatedState(with(density) {
            WindowInsets.displayCutout.asPaddingValues(density).calculateLeftPadding(LayoutDirection.Ltr).toPx()
        }.roundToInt())
        val paddingPx by rememberUpdatedState(with(density) { horizontalPadding.toPx() }.roundToInt())

        BackHandler(enabled = isDropdownExpanded.value) { dismissPopup(isDropdownExpanded) }

        showPopup(
            content = {
                Box(
                    modifier = if (defaultWindowInsetsPadding) {
                        popupModifier
                            .windowInsetsPadding(WindowInsets.displayCutout.only(WindowInsetsSides.Horizontal))
                    } else {
                        popupModifier
                    }
                        .fillMaxSize()
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onTap = {
                                    dismissPopup(isDropdownExpanded)
                                }
                            )
                        }
                        .offset(x = offsetXPx.dp / density.density, y = offsetYPx.dp / density.density)
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .onGloballyPositioned { layoutCoordinates ->
                                offsetXPx = if (alwaysRight || !alignLeft) {
                                    dropdownOffsetXPx + componentWidthPx - insideWidthPx - layoutCoordinates.size.width - paddingPx - if (defaultWindowInsetsPadding) displayCutoutLeftSize.value else 0
                                } else {
                                    dropdownOffsetXPx + paddingPx + insideWidthPx - if (defaultWindowInsetsPadding) displayCutoutLeftSize.value else 0
                                }
                                offsetYPx = calculateOffsetYPx(
                                    windowHeightPx,
                                    dropdownOffsetYPx,
                                    layoutCoordinates.size.height,
                                    componentHeightPx,
                                    insideHeightPx,
                                    statusBarPx,
                                    navigationBarPx,
                                    captionBarPx
                                )
                            }
                            .align(AbsoluteAlignment.TopLeft)
                            .graphicsLayer(
                                shadowElevation = 18f,
                                shape = SmoothRoundedCornerShape(18.dp)
                            )
                            .clip(SmoothRoundedCornerShape(18.dp))
                            .background(MiuixTheme.colorScheme.surface)
                    ) {
                        item {
                            items.forEachIndexed { index, option ->
                                DropdownImpl(
                                    text = option,
                                    optionSize = items.size,
                                    isSelected = items[selectedIndex] == option,
                                    onSelectedIndexChange = {
                                        hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                                        onSelectedIndexChange(it)
                                        dismissPopup(isDropdownExpanded)
                                    },
                                    textWidthDp = textWidthDp,
                                    index = index
                                )
                            }
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
    val additionalTopPadding = if (index == 0) 24.dp else 14.dp
    val additionalBottomPadding = if (index == optionSize - 1) 24.dp else 14.dp
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
            .padding(horizontal = 24.dp)
            .padding(top = additionalTopPadding, bottom = additionalBottomPadding)
    ) {
        Text(
            modifier = Modifier.width(textWidthDp ?: 50.dp),
            text = text,
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium,
            color = textColor,
        )
        Image(
            modifier = Modifier.padding(start = 50.dp).size(20.dp),
            imageVector = MiuixIcons.Check,
            colorFilter = BlendModeColorFilter(selectColor, BlendMode.SrcIn),
            contentDescription = null,
        )
    }
}

/**
 * Calculate the offset of the dropdown.
 *
 * @param windowHeightPx The height of the window.
 * @param dropdownOffsetPx The default offset of the dropdown.
 * @param dropdownHeightPx The height of the dropdown.
 * @param componentHeightPx The height of the component.
 * @param insideHeightPx The height of the component inside.
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
    insideHeightPx: Int,
    statusBarPx: Int,
    navigationBarPx: Int,
    captionBarPx: Int
): Int {
    return if (windowHeightPx - captionBarPx - navigationBarPx - dropdownOffsetPx - componentHeightPx > dropdownHeightPx) {
        // Show below
        dropdownOffsetPx + componentHeightPx - insideHeightPx / 2
    } else if (dropdownOffsetPx - statusBarPx > dropdownHeightPx) {
        // Show above
        dropdownOffsetPx - dropdownHeightPx  + insideHeightPx / 2
    } else if (windowHeightPx - statusBarPx - captionBarPx - navigationBarPx <= dropdownHeightPx)  {
        // Special handling when the height of the popup is maxsize (== windowHeightPx)
        0
    } else if (windowHeightPx - dropdownOffsetPx < dropdownHeightPx / 2 + captionBarPx + navigationBarPx + insideHeightPx + componentHeightPx / 2) {
        windowHeightPx - dropdownHeightPx - insideHeightPx - captionBarPx - navigationBarPx
    } else {
        val offset = dropdownOffsetPx - dropdownHeightPx / 2 + componentHeightPx / 2
        if (offset > insideHeightPx + statusBarPx) offset else insideHeightPx + statusBarPx
    }

}

/**
 * Only one dropdown is allowed to be displayed at a time.
 */
val dropdownStates = mutableStateListOf<MutableState<Boolean>>()
