package top.yukonga.miuix.kmp.extra

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.captionBar
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import top.yukonga.miuix.kmp.basic.BasicComponent
import top.yukonga.miuix.kmp.basic.BasicComponentColors
import top.yukonga.miuix.kmp.basic.BasicComponentDefaults
import top.yukonga.miuix.kmp.basic.Box
import top.yukonga.miuix.kmp.basic.Text
import top.yukonga.miuix.kmp.basic.TextButton
import top.yukonga.miuix.kmp.icon.MiuixIcons
import top.yukonga.miuix.kmp.icon.icons.ArrowUpDownIntegrated
import top.yukonga.miuix.kmp.icon.icons.Check
import top.yukonga.miuix.kmp.interfaces.HoldDownInteraction
import top.yukonga.miuix.kmp.theme.MiuixTheme
import top.yukonga.miuix.kmp.utils.BackHandler
import top.yukonga.miuix.kmp.utils.MiuixPopupUtil.Companion.dismissDialog
import top.yukonga.miuix.kmp.utils.MiuixPopupUtil.Companion.dismissPopup
import top.yukonga.miuix.kmp.utils.MiuixPopupUtil.Companion.showPopup
import top.yukonga.miuix.kmp.utils.SmoothRoundedCornerShape
import top.yukonga.miuix.kmp.utils.getWindowSize
import kotlin.math.roundToInt


/**
 * A [SuperSpinner] component with Miuix style.
 *
 * @param title the title of the [SuperSpinner].
 * @param items the list of [SpinnerEntry] to be shown in the [SuperSpinner].
 * @param selectedIndex the index of the selected item in the [SuperSpinner].
 * @param modifier the [Modifier] to be applied to the [SuperSpinner].
 * @param popupModifier the [Modifier] to be applied to the popup of the [SuperSpinner].
 * @param titleColor the color of the title of the [SuperSpinner].
 * @param summary the summary of the [SuperSpinner].
 * @param summaryColor the color of the summary of the [SuperSpinner].
 * @param mode the mode of the [SuperSpinner].
 * @param horizontalPadding the horizontal padding of the [SuperSpinner].
 * @param leftAction the action to be shown at the left side of the [SuperSpinner].
 * @param insideMargin the [PaddingValues] to be applied inside the [SuperSpinner].
 * @param defaultWindowInsetsPadding whether to apply the default window insets padding to the [SuperSpinner].
 * @param enabled whether the [SuperSpinner] is enabled.
 * @param showValue whether to show the value of the [SuperSpinner].
 * @param onSelectedIndexChange the callback to be invoked when the selected index of the [SuperSpinner] is changed.
 */
@Composable
fun SuperSpinner(
    title: String,
    items: List<SpinnerEntry>,
    selectedIndex: Int,
    modifier: Modifier = Modifier,
    popupModifier: Modifier = Modifier,
    titleColor: BasicComponentColors = BasicComponentDefaults.titleColor(),
    summary: String? = null,
    summaryColor: BasicComponentColors = BasicComponentDefaults.summaryColor(),
    mode: SpinnerMode = SpinnerMode.Normal,
    horizontalPadding: Dp = 0.dp,
    leftAction: @Composable (() -> Unit)? = null,
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
    var dropdownOffsetXPx by remember { mutableIntStateOf(0) }
    var dropdownOffsetYPx by remember { mutableIntStateOf(0) }
    var componentHeightPx by remember { mutableIntStateOf(0) }
    var componentWidthPx by remember { mutableIntStateOf(0) }

    val getWindowSize = rememberUpdatedState(getWindowSize())
    val windowHeightPx by rememberUpdatedState(getWindowSize.value.height)
    val windowWidthPx by rememberUpdatedState(getWindowSize.value.width)
    var transformOrigin by mutableStateOf(TransformOrigin.Center)

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
                            if (eventChange.pressed) {
                                alignLeft = eventChange.position.x < (size.width / 2)
                            }
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
                    val xInWindow = dropdownOffsetXPx + if (mode == SpinnerMode.AlwaysOnRight || !alignLeft) componentWidthPx else 0
                    val yInWindow = dropdownOffsetYPx + componentHeightPx / 2
                    transformOrigin = TransformOrigin(
                        xInWindow / windowWidthPx.toFloat(),
                        yInWindow / windowHeightPx.toFloat()
                    )
                }
            },
        interactionSource = interactionSource,
        insideMargin = insideMargin,
        title = title,
        titleColor = titleColor,
        summary = summary,
        summaryColor = summaryColor,
        leftAction = leftAction,
        rightActions = {
            if (showValue) {
                Text(
                    modifier = Modifier.widthIn(max = 130.dp),
                    text = items[selectedIndex].title ?: "",
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
        onClick = {
            if (enabled) {
                isDropdownExpanded.value = true
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
        var offsetXPx by remember { mutableIntStateOf(0) }
        var offsetYPx by remember { mutableIntStateOf(0) }
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
        val displayCutoutLeftSize by rememberUpdatedState(with(density) {
            WindowInsets.displayCutout.asPaddingValues(density).calculateLeftPadding(LayoutDirection.Ltr).toPx()
        }.roundToInt())
        val paddingPx by rememberUpdatedState(with(density) {
            horizontalPadding.toPx()
        }.roundToInt())

        BackHandler(enabled = isDropdownExpanded.value) {
            dismissPopup(isDropdownExpanded)
        }

        showPopup(
            transformOrigin = { transformOrigin },
            content = {
                Box(
                    modifier = if (defaultWindowInsetsPadding) {
                        popupModifier
                            .windowInsetsPadding(
                                WindowInsets.displayCutout.only(
                                    WindowInsetsSides.Horizontal
                                )
                            )
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
                        .offset {
                            IntOffset(offsetXPx, offsetYPx)
                        }
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .onGloballyPositioned { coordinates ->
                                offsetXPx = if (mode == SpinnerMode.AlwaysOnRight || !alignLeft) {
                                    dropdownOffsetXPx + componentWidthPx - insideRightPx - coordinates.size.width - paddingPx - if (defaultWindowInsetsPadding) displayCutoutLeftSize else 0
                                } else {
                                    dropdownOffsetXPx + paddingPx + insideLeftPx - if (defaultWindowInsetsPadding) displayCutoutLeftSize else 0
                                }
                                offsetYPx = calculateOffsetYPx(
                                    windowHeightPx,
                                    dropdownOffsetYPx,
                                    coordinates.size.height,
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
                            SpinnerItemImpl(
                                entry = items[index],
                                entryCount = items.size,
                                isSelected = selectedIndex == index,
                                index = index,
                                dialogMode = false
                            ) {
                                hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                                onSelectedIndexChange?.let { it1 -> it1(it) }
                                dismissPopup(isDropdownExpanded)
                            }
                        }
                    }
                }
            }
        )
    }
}

/**
 * A [SuperSpinner] component with Miuix style, show Spinner as dialog.
 *
 * @param title the title of the [SuperSpinner].
 * @param items the list of [SpinnerEntry] to be shown in the [SuperSpinner].
 * @param selectedIndex the index of the selected item in the [SuperSpinner].
 * @param dialogButtonString the string of the button in the dialog.
 * @param modifier the [Modifier] to be applied to the [SuperSpinner].
 * @param popupModifier the [Modifier] to be applied to the popup of the [SuperSpinner].
 * @param titleColor the color of the title of the [SuperSpinner].
 * @param summary the summary of the [SuperSpinner].
 * @param summaryColor the color of the summary of the [SuperSpinner].
 * @param leftAction the action to be shown at the left side of the [SuperSpinner].
 * @param insideMargin the [PaddingValues] to be applied inside the [SuperSpinner].
 * @param enabled whether the [SuperSpinner] is enabled.
 * @param showValue whether to show the value of the [SuperSpinner].
 * @param onSelectedIndexChange the callback to be invoked when the selected index of the [SuperSpinner] is changed.
 */
@Composable
fun SuperSpinner(
    title: String,
    items: List<SpinnerEntry>,
    selectedIndex: Int,
    dialogButtonString: String,
    modifier: Modifier = Modifier,
    popupModifier: Modifier = Modifier,
    titleColor: BasicComponentColors = BasicComponentDefaults.titleColor(),
    summary: String? = null,
    summaryColor: BasicComponentColors = BasicComponentDefaults.summaryColor(),
    leftAction: @Composable (() -> Unit)? = null,
    insideMargin: PaddingValues = BasicComponentDefaults.InsideMargin,
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
    var dropdownOffsetXPx by remember { mutableIntStateOf(0) }
    var dropdownOffsetYPx by remember { mutableIntStateOf(0) }
    var componentHeightPx by remember { mutableIntStateOf(0) }
    var componentWidthPx by remember { mutableIntStateOf(0) }

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
        leftAction = leftAction,
        rightActions = {
            if (showValue) {
                Text(
                    modifier = Modifier.widthIn(max = 130.dp),
                    text = items[selectedIndex].title ?: "",
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
        onClick = {
            if (enabled) {
                isDropdownExpanded.value = true
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
        SuperDialog(
            modifier = popupModifier,
            title = title,
            show = isDropdownExpanded,
            onDismissRequest = {
                dismissDialog(isDropdownExpanded)
            },
            insideMargin = DpSize(0.dp, 24.dp)
        ) {
            Column {
                LazyColumn {
                    items(items.size) { index ->
                        SpinnerItemImpl(
                            entry = items[index],
                            entryCount = items.size,
                            isSelected = selectedIndex == index,
                            index = index,
                            dialogMode = true
                        ) {
                            hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                            onSelectedIndexChange?.let { it1 -> it1(it) }
                            dismissDialog(isDropdownExpanded)
                        }
                    }
                }
                TextButton(
                    modifier = Modifier.padding(start = 24.dp, top = 12.dp, end = 24.dp).fillMaxWidth(),
                    text = dialogButtonString,
                    minHeight = 50.dp,
                    onClick = {
                        dismissDialog(isDropdownExpanded)
                    }
                )
            }
        }
    }
}

/**
 * The implementation of the spinner.
 *
 * @param entry the [SpinnerEntry] to be shown in the spinner.
 * @param entryCount the count of the entries in the spinner.
 * @param isSelected whether the entry is selected.
 * @param index the index of the entry.
 * @param dialogMode whether the spinner is in dialog mode.
 * @param onSelectedIndexChange the callback to be invoked when the selected index of the spinner is changed.
 */
@Composable
fun SpinnerItemImpl(
    entry: SpinnerEntry,
    entryCount: Int,
    isSelected: Boolean,
    index: Int,
    dialogMode: Boolean = false,
    onSelectedIndexChange: (Int) -> Unit,
) {
    val additionalTopPadding = if (!dialogMode && index == 0) 20f.dp else 12f.dp
    val additionalBottomPadding = if (!dialogMode && index == entryCount - 1) 20f.dp else 12f.dp
    val titleColor: Color
    val summaryColor: Color
    val selectColor: Color
    val backgroundColor: Color
    if (isSelected) {
        titleColor = MiuixTheme.colorScheme.onTertiaryContainer
        summaryColor = MiuixTheme.colorScheme.onTertiaryContainer
        selectColor = MiuixTheme.colorScheme.onTertiaryContainer
        backgroundColor = MiuixTheme.colorScheme.tertiaryContainer
    } else {
        titleColor = MiuixTheme.colorScheme.onSurface
        summaryColor = MiuixTheme.colorScheme.onSurfaceVariantSummary
        selectColor = Color.Transparent
        backgroundColor = MiuixTheme.colorScheme.surface
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .clickable {
                onSelectedIndexChange(index)
            }
            .background(backgroundColor)
            .then(
                if (dialogMode) Modifier.heightIn(min = 56.dp).widthIn(min = 200.dp).fillMaxWidth().padding(horizontal = 28.dp)
                else Modifier.widthIn(200.dp, 288.dp).padding(horizontal = 20.dp)
            )
            .padding(top = additionalTopPadding, bottom = additionalBottomPadding)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            entry.icon?.let {
                it(
                    Modifier.sizeIn(minWidth = 26.dp, minHeight = 26.dp).padding(end = 12.dp)
                )
            }
            Column {
                entry.title?.let {
                    Text(
                        text = it,
                        fontSize = MiuixTheme.textStyles.body1.fontSize,
                        fontWeight = FontWeight.Medium,
                        color = titleColor
                    )
                }
                entry.summary?.let {
                    Text(
                        text = it,
                        fontSize = MiuixTheme.textStyles.body2.fontSize,
                        color = summaryColor
                    )
                }
            }
        }
        Image(
            modifier = Modifier.padding(start = 12.dp).size(20.dp),
            imageVector = MiuixIcons.Check,
            colorFilter = BlendModeColorFilter(selectColor, BlendMode.SrcIn),
            contentDescription = null,
        )
    }
}

/**
 * The spinner entry.
 */
data class SpinnerEntry(
    val icon: @Composable ((Modifier) -> Unit)? = null,
    val title: String? = null,
    val summary: String? = null
)

/**
 * The spinner show mode.
 */
enum class SpinnerMode {
    Normal,
    AlwaysOnRight
}