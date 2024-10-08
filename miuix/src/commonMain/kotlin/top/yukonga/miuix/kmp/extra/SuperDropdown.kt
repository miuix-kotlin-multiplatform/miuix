package top.yukonga.miuix.kmp.extra

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
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
import top.yukonga.miuix.kmp.utils.createRipple
import top.yukonga.miuix.kmp.utils.getWindowSize
import top.yukonga.miuix.kmp.utils.squircleshape.SquircleShape
import kotlin.math.roundToInt

/**
 * Returns modifier to be used for the current platform.
 */
expect fun modifierPlatform(modifier: Modifier, isHovered: MutableState<Boolean>): Modifier

/**
 * A dropdown with a title and a summary.
 *
 * @param title The title of the [SuperDropdown].
 * @param titleColor The color of the title.
 * @param summary The summary of the [SuperDropdown].
 * @param summaryColor The color of the summary.
 * @param modifier The modifier to be applied to the [SuperDropdown].
 * @param items The options of the [SuperDropdown].
 * @param alwaysRight Whether the popup is always show on the right side.
 * @param insideMargin The margin inside the [SuperDropdown].
 * @param defaultWindowInsetsPadding Whether to apply default window insets padding to the [SuperDropdown].
 * @param selectedIndex The index of the selected option.
 * @param onSelectedIndexChange The callback when the index is selected.
 * @param enabled Whether the [SuperDropdown] is enabled.
 */
@Composable
fun SuperDropdown(
    title: String,
    titleColor: Color = MiuixTheme.colorScheme.onSurface,
    summary: String? = null,
    summaryColor: Color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
    modifier: Modifier = Modifier,
    items: List<String>,
    alwaysRight: Boolean = false,
    insideMargin: DpSize = DpSize(16.dp, 16.dp),
    popupHorizontalPadding: Dp = 12.dp,
    defaultWindowInsetsPadding: Boolean = true,
    selectedIndex: Int,
    onSelectedIndexChange: (Int) -> Unit,
    enabled: Boolean = true
) {
    val hapticFeedback = LocalHapticFeedback.current
    val density = LocalDensity.current
    val coroutineScope = rememberCoroutineScope()
    val interactionSource = remember { MutableInteractionSource() }
    val isDropdownExpanded = remember { mutableStateOf(false) }
    var alignLeft by rememberSaveable { mutableStateOf(true) }
    val textMeasurer = rememberTextMeasurer()
    val textStyle = remember { TextStyle(fontWeight = FontWeight.Medium, fontSize = 17.sp) }
    val textWidthDp = remember(items) {
        items.maxOfOrNull { text ->
            with(density) { textMeasurer.measure(text = text, style = textStyle).size.width.toDp() }
        }
    }
    val isHovered = remember { mutableStateOf(false) }
    var dropdownHeightPx by remember { mutableStateOf(0) }
    var dropdownOffsetPx by remember { mutableStateOf(0) }
    var componentHeightPx by remember { mutableStateOf(0) }
    var offsetPx by remember { mutableStateOf(0) }
    val windowHeightPx by rememberUpdatedState(getWindowSize().height)
    val statusBarPx by rememberUpdatedState(
        with(density) {
            WindowInsets.statusBars.asPaddingValues().calculateTopPadding().toPx()
        }.roundToInt()
    )
    val navigationBarPx by rememberUpdatedState(
        with(density) {
            WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding().toPx()
        }.roundToInt()
    )
    val captionBarPx by rememberUpdatedState(
        with(density) {
            WindowInsets.captionBar.asPaddingValues().calculateBottomPadding().toPx()
        }.roundToInt()
    )
    val insideHeightPx by rememberUpdatedState(
        with(density) { insideMargin.height.toPx() }.roundToInt()
    )

    BasicComponent(
        modifier = modifierPlatform(modifier = Modifier, isHovered = isHovered)
            .background(if (isHovered.value) MiuixTheme.colorScheme.onBackground.copy(0.08f) else Color.Transparent)
            .indication(interactionSource, createRipple())
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = { offset ->
                        coroutineScope.launch {
                            val press = PressInteraction.Press(offset)
                            interactionSource.emit(press)
                            interactionSource.emit(PressInteraction.Release(press))
                        }
                    },
                    onTap = { offset ->
                        isDropdownExpanded.value = enabled
                        alignLeft = offset.x < (size.width / 2)
                    }
                )
            }
            .onGloballyPositioned { coordinates ->
                val positionInWindow = coordinates.positionInWindow()
                dropdownOffsetPx = positionInWindow.y.toInt()
                componentHeightPx = coordinates.size.height
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
                color = if (enabled) MiuixTheme.colorScheme.onSurfaceVariantActions else MiuixTheme.colorScheme.disabledOnSecondaryVariant,
                textAlign = TextAlign.End,
            )
            Image(
                modifier = Modifier
                    .size(15.dp)
                    .align(Alignment.CenterVertically),
                imageVector = MiuixIcons.ArrowUpDown,
                colorFilter = BlendModeColorFilter(
                    if (enabled) MiuixTheme.colorScheme.onSurfaceVariantActions else MiuixTheme.colorScheme.disabledOnSecondaryVariant,
                    BlendMode.SrcIn
                ),
                contentDescription = null
            )
        },
        enabled = enabled
    )
    BackHandler(
        enabled = isDropdownExpanded.value
    ) {
        dismissPopup()
        isDropdownExpanded.value = false
    }

    if (isDropdownExpanded.value) {
        hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
        showPopup(
            content = {
                Box(
                    modifier = if (defaultWindowInsetsPadding) {
                        modifier
                            .windowInsetsPadding(WindowInsets.displayCutout.only(WindowInsetsSides.Horizontal))
                            .windowInsetsPadding(WindowInsets.captionBar.only(WindowInsetsSides.Top))
                    } else {
                        modifier
                    }
                        .fillMaxSize()
                        .pointerInput(Unit) {
                            detectTapGestures(onTap = {
                                dismissPopup()
                                isDropdownExpanded.value = false
                            })
                        }
                        .offset(y = offsetPx.dp / density.density)
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .padding(horizontal = popupHorizontalPadding)
                            .onGloballyPositioned { layoutCoordinates ->
                                dropdownHeightPx = layoutCoordinates.size.height
                                offsetPx = calculateOffsetPx(
                                    windowHeightPx,
                                    dropdownOffsetPx,
                                    dropdownHeightPx,
                                    componentHeightPx,
                                    insideHeightPx,
                                    statusBarPx,
                                    navigationBarPx,
                                    captionBarPx
                                )
                            }
                            .align(if (alignLeft && !alwaysRight) AbsoluteAlignment.TopLeft else AbsoluteAlignment.TopRight)
                            .graphicsLayer(
                                shadowElevation = 18f,
                                shape = SquircleShape(18.dp),
                                clip = false
                            )
                            .clip(SquircleShape(18.dp))
                            .background(MiuixTheme.colorScheme.surface)
                    ) {
                        item {
                            items.forEachIndexed { index, option ->
                                DropdownImpl(
                                    options = items,
                                    isSelected = items[selectedIndex] == option,
                                    onSelectedIndexChange = {
                                        hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                                        onSelectedIndexChange(it)
                                        dismissPopup()
                                        isDropdownExpanded.value = false
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
 * @param options The options of the dropdown.
 * @param isSelected Whether the option is selected.
 * @param index The index of the current option in the options.
 * @param onSelectedIndexChange The callback when the index is selected.
 * @param textWidthDp The maximum width of text in options.
 */
@Composable
fun DropdownImpl(
    options: List<String>,
    isSelected: Boolean,
    index: Int,
    onSelectedIndexChange: (Int) -> Unit,
    textWidthDp: Dp?
) {
    val dropdownInteractionSource = remember { MutableInteractionSource() }
    val additionalTopPadding = if (index == 0) 24.dp else 14.dp
    val additionalBottomPadding = if (index == options.size - 1) 24.dp else 14.dp
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
            .clickable(
                interactionSource = dropdownInteractionSource,
                indication = createRipple(),
            ) {
                onSelectedIndexChange(index)
            }
            .background(backgroundColor)
            .padding(horizontal = 24.dp)
            .padding(top = additionalTopPadding, bottom = additionalBottomPadding)
    ) {
        Text(
            modifier = Modifier.width(textWidthDp ?: 50.dp),
            text = options[index],
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
 * @param componentHeight The height of the click component.
 * @param insideHeightPx The height of the component inside.
 * @param statusBarPx The height of the status bar padding.
 * @param navigationBarPx The height of the navigation bar padding.
 * @param captionBarPx The height of the caption bar padding.
 * @return The offset of the current dropdown.
 */
fun calculateOffsetPx(
    windowHeightPx: Int,
    dropdownOffsetPx: Int,
    dropdownHeightPx: Int,
    componentHeight: Int,
    insideHeightPx: Int,
    statusBarPx: Int,
    navigationBarPx: Int,
    captionBarPx: Int
): Int {
    return if (windowHeightPx - dropdownOffsetPx < dropdownHeightPx / 2 + captionBarPx + navigationBarPx + insideHeightPx + componentHeight / 2) {
        windowHeightPx - dropdownHeightPx - insideHeightPx - captionBarPx - navigationBarPx
    } else {
        val offset = dropdownOffsetPx - dropdownHeightPx / 2 + componentHeight / 2
        if (offset > insideHeightPx + statusBarPx) offset else insideHeightPx + statusBarPx
    }
}