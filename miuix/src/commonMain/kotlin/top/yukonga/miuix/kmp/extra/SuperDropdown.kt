package top.yukonga.miuix.kmp.extra

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.captionBar
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
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
import kotlin.math.max
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
    val isDropdownExpanded = remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val held = remember { mutableStateOf<HoldDownInteraction.Hold?>(null) }
    val hapticFeedback = LocalHapticFeedback.current
    val actionColor = if (enabled) MiuixTheme.colorScheme.onSurfaceVariantActions else MiuixTheme.colorScheme.disabledOnSecondaryVariant
    var alignLeft by rememberSaveable { mutableStateOf(true) }
    var dropdownOffsetXPx by remember { mutableStateOf(0) }
    var dropdownOffsetYPx by remember { mutableStateOf(0) }
    var componentHeightPx by remember { mutableStateOf(0) }
    var componentWidthPx by remember { mutableStateOf(0) }

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
            },
        interactionSource = interactionSource,
        insideMargin = insideMargin,
        title = title,
        titleColor = titleColor,
        summary = summary,
        summaryColor = summaryColor,
        rightActions = {
            if (showValue) {
                Text(
                    modifier = Modifier.widthIn(max = 130.dp),
                    text = items[selectedIndex],
                    fontSize = MiuixTheme.textStyles.body2.fontSize,
                    color = actionColor,
                    textAlign = TextAlign.End,
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
        onClick = {
            if (enabled) {
                isDropdownExpanded.value = enabled
                hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                coroutineScope.launch {
                    interactionSource.emit(HoldDownInteraction.Hold().also {
                        held.value = it
                    })
                }
            }
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
        val textStyle = remember { TextStyle(fontWeight = FontWeight.Medium, fontSize = 16.sp) }
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
        val dropdownMaxHeight by rememberUpdatedState(with(density) {
            (windowHeightPx - statusBarPx - navigationBarPx - captionBarPx).toDp()
        })
        val dropdownElevation by rememberUpdatedState(with(density) {
            11.dp.toPx()
        })
        val insideLeftPx by rememberUpdatedState(with(density) {
            insideMargin.calculateLeftPadding(LayoutDirection.Ltr).toPx()
        }.roundToInt())
        val insideRightPx by rememberUpdatedState(with(density) {
            insideMargin.calculateRightPadding(LayoutDirection.Ltr).toPx()
        }.roundToInt())
        val insideTopPx by rememberUpdatedState(with(density) {
            insideMargin.calculateTopPadding().toPx()
        }.roundToInt())
        val insideBottomPx by rememberUpdatedState(with(density) {
            insideMargin.calculateBottomPadding().toPx()
        }.roundToInt())
        val displayCutoutLeftSize = rememberUpdatedState(with(density) {
            WindowInsets.displayCutout.asPaddingValues(density).calculateLeftPadding(LayoutDirection.Ltr).toPx()
        }.roundToInt())
        val paddingPx by rememberUpdatedState(with(density) { horizontalPadding.toPx() }.roundToInt())

        BackHandler(enabled = isDropdownExpanded.value) {
            dismissPopup(isDropdownExpanded)
        }

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
                                offsetXPx = if (mode == DropDownMode.AlwaysOnRight || !alignLeft) {
                                    dropdownOffsetXPx + componentWidthPx - insideRightPx - layoutCoordinates.size.width - paddingPx - if (defaultWindowInsetsPadding) displayCutoutLeftSize.value else 0
                                } else {
                                    dropdownOffsetXPx + paddingPx + insideLeftPx - if (defaultWindowInsetsPadding) displayCutoutLeftSize.value else 0
                                }
                                offsetYPx = calculateOffsetYPx(
                                    windowHeightPx,
                                    dropdownOffsetYPx,
                                    layoutCoordinates.size.height,
                                    componentHeightPx,
                                    insideTopPx,
                                    insideBottomPx,
                                    statusBarPx,
                                    navigationBarPx,
                                    captionBarPx
                                )
                            }
                            .heightIn(50.dp, dropdownMaxHeight)
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
    return if (windowHeightPx - captionBarPx - navigationBarPx - dropdownOffsetPx - componentHeightPx > dropdownHeightPx) {
        // Show below
        dropdownOffsetPx + componentHeightPx - insideBottomHeightPx / 2
    } else if (dropdownOffsetPx - statusBarPx > dropdownHeightPx) {
        // Show above
        dropdownOffsetPx - dropdownHeightPx + insideTopHeightPx / 2
    } else if (windowHeightPx - statusBarPx - captionBarPx - navigationBarPx <= dropdownHeightPx) {
        // Special handling when the height of the popup is maxsize (== windowHeightPx)
        statusBarPx
    } else {
        val maxInsideHeight = max(insideTopHeightPx, insideBottomHeightPx)
        if (windowHeightPx - dropdownOffsetPx < dropdownHeightPx / 2 + captionBarPx + navigationBarPx + maxInsideHeight + componentHeightPx / 2) {
            windowHeightPx - dropdownHeightPx - maxInsideHeight - captionBarPx - navigationBarPx
        } else {
            val offset = dropdownOffsetPx - dropdownHeightPx / 2 + componentHeightPx / 2
            if (offset > maxInsideHeight + statusBarPx) offset else maxInsideHeight + statusBarPx
        }
    }
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
