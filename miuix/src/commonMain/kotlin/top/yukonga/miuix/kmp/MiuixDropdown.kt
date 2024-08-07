package top.yukonga.miuix.kmp

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
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ripple
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import getWindowSize
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import top.yukonga.miuix.kmp.miuix.generated.resources.Res
import top.yukonga.miuix.kmp.miuix.generated.resources.ic_arrow_up_down
import top.yukonga.miuix.kmp.miuix.generated.resources.ic_dropdown_select
import top.yukonga.miuix.kmp.ui.MiuixTheme

@Composable
fun MiuixDropdown(
    text: String,
    modifier: Modifier = Modifier,
    options: List<String>,
    selectedOption: MutableState<String>,
    paddingHorizontal: Dp = 24.dp,
    paddingVertical: Dp = 15.dp,
    onOptionSelected: (String) -> Unit
) {
    var isDropdownExpanded by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }
    val textWidthDp = options.maxOfOrNull { option ->
        with(LocalDensity.current) { rememberTextMeasurer().measure(text = option, style = MiuixTheme.textStyles.main).size.width.toDp() }
    }
    val ripple = ripple(color = MiuixTheme.colorScheme.onBackground.copy(alpha = 0.8f))
    var alignLeft by remember { mutableStateOf(true) }
    val density = LocalDensity.current.density
    val windowHeightPx = getWindowSize().height
    var dropdownHeightPx by remember { mutableStateOf(0) }
    var dropdownOffsetPx by remember { mutableStateOf(0) }
    var offsetPx by remember { mutableStateOf(0) }
    val navigationPx = dpToPx(WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()).toInt()
    val px24 = dpToPx(24.dp).toInt()

    val coroutineScope = rememberCoroutineScope()

    MiuixBox(
        modifier = modifier
            .indication(interactionSource, ripple)
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
                        isDropdownExpanded = true
                        alignLeft = offset.x < (size.width / 2)
                    }
                )
            }
            .onGloballyPositioned { coordinates ->
                val positionInRoot = coordinates.positionInRoot()
                dropdownOffsetPx = positionInRoot.y.toInt()
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = paddingVertical, horizontal = paddingHorizontal),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            MiuixText(
                text = text,
                fontWeight = FontWeight.SemiBold
            )
            Row {
                MiuixText(
                    text = selectedOption.value,
                    fontWeight = FontWeight.Normal,
                    color = MiuixTheme.colorScheme.subDropdown,
                    textAlign = TextAlign.End,
                )
                Image(
                    modifier = Modifier
                        .size(14.dp)
                        .padding(start = 6.dp)
                        .align(Alignment.CenterVertically),
                    painter = painterResource(Res.drawable.ic_arrow_up_down),
                    colorFilter = BlendModeColorFilter(MiuixTheme.colorScheme.subDropdown, BlendMode.SrcIn),
                    contentDescription = null
                )
            }
        }
    }

    if (isDropdownExpanded) {
        Dialog(
            onDismissRequest = { isDropdownExpanded = false },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            MiuixBox(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectTapGestures(onPress = { isDropdownExpanded = false })
                    }
                    .offset(y = offsetPx.dp / density),
            ) {
                LazyColumn(
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                        .onGloballyPositioned { layoutCoordinates ->
                            dropdownHeightPx = layoutCoordinates.size.height
                            offsetPx = if (windowHeightPx - dropdownOffsetPx < dropdownHeightPx) {
                                windowHeightPx - dropdownHeightPx - navigationPx - px24
                            } else if (dropdownOffsetPx - (windowHeightPx - dropdownHeightPx) > dropdownHeightPx) {
                                dropdownOffsetPx + dropdownHeightPx / 2
                            } else if (windowHeightPx - dropdownOffsetPx > dropdownHeightPx) {
                                val offset = dropdownOffsetPx - dropdownHeightPx / 2
                                if (offset > 0) offset else 0
                            } else {
                                0
                            }
                        }
                        .align(if (alignLeft) AbsoluteAlignment.TopLeft else AbsoluteAlignment.TopRight)
                        .clip(RoundedCornerShape(16.dp))
                        .background(MiuixTheme.colorScheme.dropdownBackground)
                ) {
                    item {
                        options.forEachIndexed { index, option ->
                            val dropdownInteractionSource = remember { MutableInteractionSource() }
                            val additionalTopPadding = if (index == 0) 22.dp else 14.dp
                            val additionalBottomPadding = if (index == options.size - 1) 22.dp else 14.dp
                            val textColor = if (selectedOption.value == option) {
                                MiuixTheme.colorScheme.primary
                            } else {
                                MiuixTheme.colorScheme.onBackground
                            }
                            val selectColor = if (selectedOption.value == option) {
                                MiuixTheme.colorScheme.primary
                            } else {
                                Color.Transparent
                            }
                            val backgroundColor = if (selectedOption.value == option) {
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
                                        selectedOption.value = option
                                        onOptionSelected(option)
                                        isDropdownExpanded = false
                                    }
                                    .background(backgroundColor)
                                    .padding(horizontal = 24.dp)
                                    .padding(top = additionalTopPadding, bottom = additionalBottomPadding)
                            ) {
                                MiuixText(
                                    modifier = Modifier.width(textWidthDp ?: 50.dp),
                                    text = option,
                                    color = textColor,
                                )
                                Image(
                                    modifier = Modifier.padding(start = 48.dp).size(26.dp),
                                    painter = painterResource(Res.drawable.ic_dropdown_select),
                                    colorFilter = BlendModeColorFilter(selectColor, BlendMode.SrcIn),
                                    contentDescription = null,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun dpToPx(dpValue: Dp): Float {
    val density = LocalDensity.current
    return with(density) { dpValue.toPx() }
}