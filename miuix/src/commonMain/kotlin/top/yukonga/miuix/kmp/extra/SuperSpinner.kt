// Copyright 2025, miuix-kotlin-multiplatform contributors
// SPDX-License-Identifier: Apache-2.0

package top.yukonga.miuix.kmp.extra

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.BlendModeColorFilter
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import top.yukonga.miuix.kmp.basic.BasicComponent
import top.yukonga.miuix.kmp.basic.BasicComponentColors
import top.yukonga.miuix.kmp.basic.BasicComponentDefaults
import top.yukonga.miuix.kmp.basic.ListPopup
import top.yukonga.miuix.kmp.basic.ListPopupColumn
import top.yukonga.miuix.kmp.basic.PopupPositionProvider
import top.yukonga.miuix.kmp.basic.Text
import top.yukonga.miuix.kmp.basic.TextButton
import top.yukonga.miuix.kmp.icon.MiuixIcons
import top.yukonga.miuix.kmp.icon.icons.basic.ArrowUpDownIntegrated
import top.yukonga.miuix.kmp.icon.icons.basic.Check
import top.yukonga.miuix.kmp.theme.MiuixTheme

/**
 * A spinner component with Miuix style. (Popup Mode)
 *
 * @param items The list of [SpinnerEntry] to be shown in the [SuperSpinner].
 * @param selectedIndex The index of the selected item in the [SuperSpinner].
 * @param title The title of the [SuperSpinner].
 * @param titleColor The color of the title of the [SuperSpinner].
 * @param summary The summary of the [SuperSpinner].
 * @param summaryColor The color of the summary of the [SuperSpinner].
 * @param leftAction The action to be shown at the left side of the [SuperSpinner].
 * @param mode The mode of the [SuperSpinner].
 * @param modifier The [Modifier] to be applied to the [SuperSpinner].
 * @param insideMargin The [PaddingValues] to be applied inside the [SuperSpinner].
 * @param maxHeight The maximum height of the [ListPopup].
 * @param enabled Whether the [SuperSpinner] is enabled.
 * @param showValue Whether to show the value of the [SuperSpinner].
 * @param onClick The callback when the [SuperSpinner] is clicked.
 * @param onSelectedIndexChange The callback to be invoked when the selected index of the [SuperSpinner] is changed.
 */
@Composable
fun SuperSpinner(
    items: List<SpinnerEntry>,
    selectedIndex: Int,
    title: String,
    titleColor: BasicComponentColors = BasicComponentDefaults.titleColor(),
    summary: String? = null,
    summaryColor: BasicComponentColors = BasicComponentDefaults.summaryColor(),
    leftAction: @Composable (() -> Unit)? = null,
    mode: SpinnerMode = SpinnerMode.Normal,
    modifier: Modifier = Modifier,
    insideMargin: PaddingValues = BasicComponentDefaults.InsideMargin,
    maxHeight: Dp? = null,
    enabled: Boolean = true,
    showValue: Boolean = true,
    onClick: (() -> Unit)? = null,
    onSelectedIndexChange: ((Int) -> Unit)?,
) {
    val currentOnClick = rememberUpdatedState(onClick)
    val currentOnSelectedIndexChange = rememberUpdatedState(onSelectedIndexChange)
    val currentLeftAction = rememberUpdatedState(leftAction)

    val interactionSource = remember { MutableInteractionSource() }
    val isDropdownExpanded = remember { mutableStateOf(false) }
    val hapticFeedback = LocalHapticFeedback.current

    val itemsNotEmpty = items.isNotEmpty()
    val actualEnabled = enabled && itemsNotEmpty

    val onSurfaceVariantActionsColor = MiuixTheme.colorScheme.onSurfaceVariantActions
    val disabledOnSecondaryVariantColor = MiuixTheme.colorScheme.disabledOnSecondaryVariant

    val actionColor = remember(actualEnabled, onSurfaceVariantActionsColor, disabledOnSecondaryVariantColor) {
        if (actualEnabled) onSurfaceVariantActionsColor
        else disabledOnSecondaryVariantColor
    }

    var alignLeft by rememberSaveable { mutableStateOf(true) }

    val basicComponentModifier = remember(modifier, actualEnabled) {
        modifier
            .pointerInput(actualEnabled) {
                if (!actualEnabled) return@pointerInput
                awaitPointerEventScope {
                    while (true) {
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
    }

    val rememberedLeftActionPopup: @Composable () -> Unit = remember(
        itemsNotEmpty,
        isDropdownExpanded,
        mode,
        alignLeft,
        maxHeight,
        items,
        selectedIndex,
        hapticFeedback,
        currentOnSelectedIndexChange,
        currentLeftAction
    ) {
        @Composable {
            if (itemsNotEmpty) {
                ListPopup(
                    show = isDropdownExpanded, // Pass the MutableState
                    alignment = if ((mode == SpinnerMode.AlwaysOnRight || !alignLeft))
                        PopupPositionProvider.Align.Right
                    else
                        PopupPositionProvider.Align.Left,
                    onDismissRequest = {
                        isDropdownExpanded.value = false
                    },
                    maxHeight = maxHeight
                ) {
                    ListPopupColumn {
                        items.forEachIndexed { index, spinnerEntry ->
                            SpinnerItemImpl(
                                entry = spinnerEntry,
                                entryCount = items.size,
                                isSelected = selectedIndex == index,
                                index = index,
                                dialogMode = false
                            ) { selectedIdx ->
                                hapticFeedback.performHapticFeedback(HapticFeedbackType.Confirm)
                                currentOnSelectedIndexChange.value?.invoke(selectedIdx)
                                isDropdownExpanded.value = false
                            }
                        }
                    }
                }
            }
            currentLeftAction.value?.invoke()
        }
    }

    val rememberedRightActions: @Composable RowScope.() -> Unit =
        remember(showValue, itemsNotEmpty, items, selectedIndex, actionColor) {
            @Composable {
                if (showValue && itemsNotEmpty) {
                    val valueTextModifier = remember { Modifier.widthIn(max = 130.dp) }
                    Text(
                        modifier = valueTextModifier,
                        text = items[selectedIndex].title ?: "",
                        fontSize = MiuixTheme.textStyles.body2.fontSize,
                        color = actionColor,
                        textAlign = TextAlign.End,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2
                    )
                }
                val imageColorFilter = remember(actionColor) { ColorFilter.tint(actionColor) }
                val arrowImageModifier = remember {
                    Modifier
                        .padding(start = 8.dp)
                        .size(10.dp, 16.dp)
                        .align(Alignment.CenterVertically)
                }
                Image(
                    modifier = arrowImageModifier,
                    imageVector = MiuixIcons.Basic.ArrowUpDownIntegrated,
                    colorFilter = imageColorFilter,
                    contentDescription = null
                )
            }
        }

    val rememberedOnClickPopup: () -> Unit = remember(actualEnabled, currentOnClick, isDropdownExpanded, hapticFeedback) {
        {
            if (actualEnabled) {
                currentOnClick.value?.invoke()
                isDropdownExpanded.value = !isDropdownExpanded.value
                if (isDropdownExpanded.value) {
                    hapticFeedback.performHapticFeedback(HapticFeedbackType.ContextClick)
                }
            }
        }
    }

    BasicComponent(
        modifier = basicComponentModifier,
        interactionSource = interactionSource,
        insideMargin = insideMargin,
        title = title,
        titleColor = titleColor,
        summary = summary,
        summaryColor = summaryColor,
        leftAction = rememberedLeftActionPopup,
        rightActions = rememberedRightActions,
        onClick = rememberedOnClickPopup,
        holdDownState = isDropdownExpanded.value,
        enabled = actualEnabled
    )
}

/**
 * A [SuperSpinner] component with Miuix style, show Spinner as dialog. (Dialog Mode)
 *
 * @param items the list of [SpinnerEntry] to be shown in the [SuperSpinner].
 * @param selectedIndex the index of the selected item in the [SuperSpinner].
 * @param title the title of the [SuperSpinner].
 * @param titleColor the color of the title of the [SuperSpinner].
 * @param summary the summary of the [SuperSpinner].
 * @param summaryColor the color of the summary of the [SuperSpinner].
 * @param leftAction the action to be shown at the left side of the [SuperSpinner].
 * @param dialogButtonString the string of the button in the dialog.
 * @param popupModifier the [Modifier] to be applied to the popup of the [SuperSpinner].
 * @param modifier the [Modifier] to be applied to the [SuperSpinner].
 * @param insideMargin the [PaddingValues] to be applied inside the [SuperSpinner].
 * @param enabled whether the [SuperSpinner] is enabled.
 * @param showValue whether to show the value of the [SuperSpinner].
 * @param onClick the callback when the [SuperSpinner] is clicked.
 * @param onSelectedIndexChange the callback to be invoked when the selected index of the [SuperSpinner] is changed.
 */
@Composable
fun SuperSpinner(
    items: List<SpinnerEntry>,
    selectedIndex: Int,
    title: String,
    titleColor: BasicComponentColors = BasicComponentDefaults.titleColor(),
    summary: String? = null,
    summaryColor: BasicComponentColors = BasicComponentDefaults.summaryColor(),
    leftAction: @Composable (() -> Unit)? = null,
    dialogButtonString: String,
    popupModifier: Modifier = Modifier,
    modifier: Modifier = Modifier,
    insideMargin: PaddingValues = BasicComponentDefaults.InsideMargin,
    enabled: Boolean = true,
    showValue: Boolean = true,
    onClick: (() -> Unit)? = null,
    onSelectedIndexChange: ((Int) -> Unit)?,
) {
    val currentOnClick = rememberUpdatedState(onClick)
    val currentOnSelectedIndexChange = rememberUpdatedState(onSelectedIndexChange)
    val currentLeftAction = rememberUpdatedState(leftAction)

    val interactionSource = remember { MutableInteractionSource() }
    val isDropdownExpanded = remember { mutableStateOf(false) }
    val hapticFeedback = LocalHapticFeedback.current

    val itemsNotEmpty = items.isNotEmpty()
    val actualEnabled = enabled && itemsNotEmpty

    val onSurfaceVariantActionsColor = MiuixTheme.colorScheme.onSurfaceVariantActions
    val disabledOnSecondaryVariantColor = MiuixTheme.colorScheme.disabledOnSecondaryVariant

    val actionColor = remember(actualEnabled, onSurfaceVariantActionsColor, disabledOnSecondaryVariantColor) {
        if (actualEnabled) onSurfaceVariantActionsColor
        else disabledOnSecondaryVariantColor
    }

    val basicComponentModifier = remember(
        modifier,
        actualEnabled
    ) {
        modifier
            .pointerInput(actualEnabled) {
                if (!actualEnabled) return@pointerInput
            }
    }
    val rememberedRightActionsDialog: @Composable RowScope.() -> Unit =
        remember(showValue, itemsNotEmpty, items, selectedIndex, actionColor) {
            @Composable {
                if (showValue && itemsNotEmpty) {
                    val valueTextModifier = remember { Modifier.widthIn(max = 130.dp) }
                    Text(
                        modifier = valueTextModifier,
                        text = items[selectedIndex].title ?: "",
                        fontSize = MiuixTheme.textStyles.body2.fontSize,
                        color = actionColor,
                        textAlign = TextAlign.End,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2
                    )
                }
                val imageColorFilter = remember(actionColor) { ColorFilter.tint(actionColor) }
                val arrowImageModifier = remember {
                    Modifier
                        .padding(start = 8.dp)
                        .size(10.dp, 16.dp)
                        .align(Alignment.CenterVertically)
                }
                Image(
                    modifier = arrowImageModifier,
                    imageVector = MiuixIcons.Basic.ArrowUpDownIntegrated,
                    colorFilter = imageColorFilter,
                    contentDescription = null
                )
            }
        }

    val rememberedOnClickDialog: () -> Unit = remember(actualEnabled, currentOnClick, isDropdownExpanded, hapticFeedback) {
        {
            if (actualEnabled) {
                currentOnClick.value?.invoke()
                isDropdownExpanded.value = true
                hapticFeedback.performHapticFeedback(HapticFeedbackType.ContextClick)
            }
        }
    }

    BasicComponent(
        modifier = basicComponentModifier,
        interactionSource = interactionSource,
        insideMargin = insideMargin,
        title = title,
        titleColor = titleColor,
        summary = summary,
        summaryColor = summaryColor,
        leftAction = currentLeftAction.value,
        rightActions = rememberedRightActionsDialog,
        onClick = rememberedOnClickDialog,
        holdDownState = isDropdownExpanded.value,
        enabled = actualEnabled,
    )

    val dialogContent =
        remember(items, selectedIndex, dialogButtonString, hapticFeedback, currentOnSelectedIndexChange, isDropdownExpanded) {
            @Composable {
                Layout(
                    content = {
                        LazyColumn {
                            items(items.size) { index ->
                                SpinnerItemImpl(
                                    entry = items[index],
                                    entryCount = items.size,
                                    isSelected = selectedIndex == index,
                                    index = index,
                                    dialogMode = true
                                ) { selectedIdx ->
                                    hapticFeedback.performHapticFeedback(HapticFeedbackType.Confirm)
                                    currentOnSelectedIndexChange.value?.let { it1 -> it1(selectedIdx) }
                                    isDropdownExpanded.value = false
                                }
                            }
                        }
                        val textButtonModifier = remember(dialogButtonString) {
                            Modifier.padding(start = 24.dp, top = 12.dp, end = 24.dp).fillMaxWidth()
                        }
                        TextButton(
                            modifier = textButtonModifier,
                            text = dialogButtonString,
                            minHeight = 50.dp,
                            onClick = {
                                isDropdownExpanded.value = false
                            }
                        )
                    }
                ) { measurables, constraints ->
                    if (measurables.size != 2) {
                        layout(0, 0) { }
                    }
                    val button = measurables[1].measure(constraints)
                    val lazyList = measurables[0].measure(
                        constraints.copy(
                            maxHeight = constraints.maxHeight - button.height
                        )
                    )
                    layout(constraints.maxWidth, lazyList.height + button.height) {
                        lazyList.place(0, 0)
                        button.place(0, lazyList.height)
                    }
                }
            }
        }

    SuperDialog(
        modifier = popupModifier,
        title = title,
        show = isDropdownExpanded,
        onDismissRequest = {
            isDropdownExpanded.value = false
        },
        insideMargin = DpSize(0.dp, 24.dp),
        content = dialogContent
    )
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
    val currentOnSelectedIndexChange = rememberUpdatedState(onSelectedIndexChange)
    val additionalTopPadding = if (!dialogMode && index == 0) 20f.dp else 12f.dp
    val additionalBottomPadding = if (!dialogMode && index == entryCount - 1) 20f.dp else 12f.dp

    val onTertiaryContainerColor = MiuixTheme.colorScheme.onTertiaryContainer
    val tertiaryContainerColor = MiuixTheme.colorScheme.tertiaryContainer
    val onSurfaceColor = MiuixTheme.colorScheme.onSurface
    val onSurfaceVariantSummaryColor = MiuixTheme.colorScheme.onSurfaceVariantSummary
    val surfaceColor = MiuixTheme.colorScheme.surface

    val itemColors = remember(
        isSelected,
        onTertiaryContainerColor,
        tertiaryContainerColor,
        onSurfaceColor,
        onSurfaceVariantSummaryColor,
        surfaceColor
    ) {
        if (isSelected) {
            Triple(onTertiaryContainerColor, onTertiaryContainerColor, tertiaryContainerColor)
        } else {
            Triple(onSurfaceColor, onSurfaceVariantSummaryColor, surfaceColor)
        }
    }
    val titleColor = itemColors.first
    val summaryColor = itemColors.second
    val backgroundColor = itemColors.third
    val selectColor = remember(isSelected, onTertiaryContainerColor) {
        if (isSelected) onTertiaryContainerColor else Color.Transparent
    }


    val itemModifier = remember(
        dialogMode,
        additionalTopPadding,
        additionalBottomPadding,
        backgroundColor,
        currentOnSelectedIndexChange,
        index
    ) {
        Modifier
            .clickable {
                currentOnSelectedIndexChange.value(index)
            }
            .background(backgroundColor)
            .then(
                if (dialogMode) Modifier
                    .heightIn(min = 56.dp)
                    .widthIn(min = 200.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 28.dp)
                else Modifier.padding(horizontal = 20.dp)
            )
            .padding(top = additionalTopPadding, bottom = additionalBottomPadding)
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = itemModifier
    ) {
        val contentRowModifier = remember(dialogMode) {
            if (dialogMode) Modifier else Modifier.widthIn(max = 216.dp)
        }
        Row(
            modifier = contentRowModifier,
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
        val checkColorFilter = remember(selectColor) { BlendModeColorFilter(selectColor, BlendMode.SrcIn) }
        val checkImageModifier = remember { Modifier.padding(start = 12.dp).size(20.dp) }
        Image(
            modifier = checkImageModifier,
            imageVector = MiuixIcons.Basic.Check,
            colorFilter = checkColorFilter,
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