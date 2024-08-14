package top.yukonga.miuix.kmp

import androidx.compose.foundation.Image
import androidx.compose.foundation.Indication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.captionBar
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.BlendModeColorFilter
import androidx.compose.ui.graphics.Color
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
import getWindowSize
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import top.yukonga.miuix.kmp.basic.MiuixBasicComponent
import top.yukonga.miuix.kmp.basic.MiuixBox
import top.yukonga.miuix.kmp.basic.MiuixText
import top.yukonga.miuix.kmp.miuix.generated.resources.Res
import top.yukonga.miuix.kmp.miuix.generated.resources.ic_arrow_up_down
import top.yukonga.miuix.kmp.miuix.generated.resources.ic_dropdown_select
import top.yukonga.miuix.kmp.theme.MiuixTheme
import top.yukonga.miuix.kmp.utils.MiuixDialogUtil
import top.yukonga.miuix.kmp.utils.createRipple

@Composable
fun MiuixSuperDropdown(
    title: String,
    summary: String? = null,
    modifier: Modifier = Modifier,
    options: List<String>,
    alwaysRight: Boolean = false,
    selectedOption: MutableState<String>,
    insideMargin: DpSize = DpSize(28.dp, 14.dp),
    onOptionSelected: (String) -> Unit
) {
    val hapticFeedback = LocalHapticFeedback.current
    val density = LocalDensity.current
    val isDropdownExpanded = remember { mutableStateOf(false) }
    var alignLeft by remember { mutableStateOf(true) }
    val textMeasurer = rememberTextMeasurer()
    val textStyle = TextStyle(fontWeight = FontWeight.Medium, fontSize = 16.sp)
    val textWidthDp = remember(options) {
        options.maxOfOrNull { text ->
            with(density) { textMeasurer.measure(text = text, style = textStyle).size.width.toDp() }
        }
    }
    var dropdownHeightPx by remember { mutableStateOf(0) }
    var dropdownOffsetPx by remember { mutableStateOf(0) }
    var componentHeight by remember { mutableStateOf(0) }
    var offsetPx by remember { mutableStateOf(0) }
    val coroutineScope = rememberCoroutineScope()
    val interactionSource = remember { MutableInteractionSource() }
    val windowHeightPx = getWindowSize().height
    val statusBarPx = with(density) { WindowInsets.statusBars.asPaddingValues().calculateTopPadding().toPx() }.toInt()
    val navigationBarPx = with(density) { WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding().toPx() }.toInt()
    val captionBarPx = with(density) { WindowInsets.captionBar.asPaddingValues().calculateBottomPadding().toPx() }.toInt()
    val insideHeightPx = with(density) { insideMargin.height.toPx() }.toInt()

    MiuixBasicComponent(
        modifier = modifier
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
                        isDropdownExpanded.value = true
                        alignLeft = offset.x < (size.width / 2)
                    }
                )
            }
            .onGloballyPositioned { coordinates ->
                val positionInWindow = coordinates.positionInWindow()
                dropdownOffsetPx = positionInWindow.y.toInt()
                componentHeight = coordinates.size.height
            },
        insideMargin = insideMargin,
        title = title,
        summary = summary,
        rightActions = {
            MiuixText(
                text = selectedOption.value,
                fontSize = 15.sp,
                color = MiuixTheme.colorScheme.subTextBase,
                textAlign = TextAlign.End,
            )
            Image(
                modifier = Modifier
                    .size(15.dp)
                    .padding(start = 6.dp)
                    .align(Alignment.CenterVertically),
                painter = painterResource(Res.drawable.ic_arrow_up_down),
                colorFilter = BlendModeColorFilter(MiuixTheme.colorScheme.subDropdown, BlendMode.SrcIn),
                contentDescription = null
            )
        },
        enabledClick = false
    )

    if (isDropdownExpanded.value) hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)

    MiuixDialogUtil.showPopup(
        visible = isDropdownExpanded,
        content = {
            MiuixBox(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectTapGestures(onPress = {
                            isDropdownExpanded.value = false
                        })
                    }
                    .offset(y = offsetPx.dp / density.density)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .onGloballyPositioned { layoutCoordinates ->
                            dropdownHeightPx = layoutCoordinates.size.height
                            offsetPx = calculateOffsetPx(
                                windowHeightPx, dropdownOffsetPx, dropdownHeightPx, componentHeight,
                                insideHeightPx, statusBarPx, navigationBarPx, captionBarPx
                            )
                        }
                        .align(if (alignLeft && !alwaysRight) AbsoluteAlignment.TopLeft else AbsoluteAlignment.TopRight)
                        .clip(RoundedCornerShape(16.dp))
                        .background(MiuixTheme.colorScheme.dropdownBackground)
                ) {
                    item {
                        options.forEachIndexed { index, option ->
                            DropdownImpl(
                                option = option,
                                isSelected = selectedOption.value == option,
                                onOptionSelected = {
                                    onOptionSelected(it)
                                    isDropdownExpanded.value = false
                                },
                                textWidthDp = textWidthDp,
                                index = index,
                                optionsSize = options.size,
                                ripple = createRipple(),
                            )
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun DropdownImpl(
    option: String,
    isSelected: Boolean,
    index: Int,
    optionsSize: Int,
    onOptionSelected: (String) -> Unit,
    textWidthDp: Dp?,
    ripple: Indication
) {
    val dropdownInteractionSource = remember { MutableInteractionSource() }
    val additionalTopPadding = if (index == 0) 24.dp else 14.dp
    val additionalBottomPadding = if (index == optionsSize - 1) 24.dp else 14.dp
    val textColor = if (isSelected) {
        MiuixTheme.colorScheme.primary
    } else {
        MiuixTheme.colorScheme.onBackground
    }
    val selectColor = if (isSelected) {
        MiuixTheme.colorScheme.primary
    } else {
        Color.Transparent
    }
    val backgroundColor = if (isSelected) {
        MiuixTheme.colorScheme.dropdownSelect
    } else {
        MiuixTheme.colorScheme.dropdownBackground
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .clickable(
                interactionSource = dropdownInteractionSource,
                indication = ripple,
            ) {
                onOptionSelected(option)
            }
            .background(backgroundColor)
            .padding(horizontal = 24.dp)
            .padding(top = additionalTopPadding, bottom = additionalBottomPadding)
    ) {
        MiuixText(
            modifier = Modifier.width(textWidthDp ?: 50.dp),
            text = option,
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium,
            color = textColor,
        )
        Image(
            modifier = Modifier.padding(start = 50.dp).size(16.dp),
            painter = painterResource(Res.drawable.ic_dropdown_select),
            colorFilter = BlendModeColorFilter(selectColor, BlendMode.SrcIn),
            contentDescription = null,
        )
    }
}

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