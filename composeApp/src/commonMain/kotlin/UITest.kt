import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import top.yukonga.miuix.kmp.MiuixBasicComponent
import top.yukonga.miuix.kmp.MiuixButton
import top.yukonga.miuix.kmp.MiuixCard
import top.yukonga.miuix.kmp.MiuixDropdown
import top.yukonga.miuix.kmp.MiuixScaffold
import top.yukonga.miuix.kmp.MiuixScrollBehavior
import top.yukonga.miuix.kmp.MiuixSlider
import top.yukonga.miuix.kmp.MiuixSuperArrow
import top.yukonga.miuix.kmp.MiuixSuperSwitch
import top.yukonga.miuix.kmp.MiuixSurface
import top.yukonga.miuix.kmp.MiuixSwitch
import top.yukonga.miuix.kmp.MiuixText
import top.yukonga.miuix.kmp.MiuixTextField
import top.yukonga.miuix.kmp.MiuixTopAppBar
import top.yukonga.miuix.kmp.rememberMiuixTopAppBarState
import top.yukonga.miuix.kmp.theme.MiuixTheme
import top.yukonga.miuix.kmp.utils.overScrollVertical

@Composable
fun UITest() {
    val scrollBehavior = MiuixScrollBehavior(rememberMiuixTopAppBarState())
    val scrollLazyColumnState = rememberLazyListState()

    var switch by remember { mutableStateOf(false) }
    var switchTrue by remember { mutableStateOf(true) }
    var textWithSwitch by remember { mutableStateOf("state: false") }
    var textWishSwitchTrue by remember { mutableStateOf(true) }
    var text by remember { mutableStateOf("") }
    var progress by remember { mutableStateOf(0.2f) }
    var progressEffect by remember { mutableStateOf(0.4f) }
    var progressFloat by remember { mutableStateOf(0.6f) }
    var progressEffectFloat by remember { mutableStateOf(50f) }
    var buttonText by remember { mutableStateOf("Button") }
    var submitButtonText by remember { mutableStateOf("Submit") }
    val dropdownOptions = listOf("Option 1", "Option 2", "Option 3", "Option 4")
    val dropdownSelectedOption = remember { mutableStateOf("Option 1") }
    val dropdownSelectedOptionRight = remember { mutableStateOf("Option 1") }
    var clickCount by remember { mutableStateOf(0) }
    var submitClickCount by remember { mutableStateOf(0) }
    val focusManager = LocalFocusManager.current

    MiuixSurface {
        MiuixScaffold(
            modifier = Modifier
                .fillMaxSize()
                .displayCutoutPadding()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                MiuixTopAppBar(
                    title = "Miuix",
                    scrollBehavior = scrollBehavior
                )
            }
        ) { padding ->
            LazyColumn(
                state = scrollLazyColumnState,
                modifier = Modifier
                    .padding(top = padding.calculateTopPadding())
                    .navigationBarsPadding()
                    .overScrollVertical()
            ) {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 28.dp, vertical = 15.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        MiuixText(
                            text = "Text",
                            fontWeight = FontWeight.Medium
                        )
                        MiuixText(
                            text = "Text",
                            modifier = Modifier.padding(start = 24.dp)
                        )
                        MiuixText(
                            text = "Text",
                            fontSize = 15.sp,
                            color = MiuixTheme.colorScheme.subTextBase,
                            modifier = Modifier.padding(start = 24.dp)
                        )
                        MiuixText(
                            text = "Text",
                            color = MiuixTheme.colorScheme.subTextField,
                            modifier = Modifier.padding(start = 24.dp)
                        )
                        MiuixText(
                            text = "Text",
                            style = MiuixTheme.textStyles.title,
                            color = MiuixTheme.colorScheme.subDropdown,
                            modifier = Modifier.padding(start = 24.dp)
                        )
                    }

                    MiuixBasicComponent(
                        title = "Title",
                        summary = "Summary",
                        leftAction = {
                            MiuixText(text = "Left")
                        },
                        rightActions = {
                            MiuixText(text = "Right1")
                            Spacer(Modifier.width(8.dp))
                            MiuixText(text = "Right2")
                        }
                    )

                    MiuixSuperArrow(
                        leftAction = {
                            MiuixText(text = "Left")
                        },
                        title = "Title",
                        summary = "Summary",
                        onClick = {}
                    )

                    MiuixSuperArrow(
                        title = "Title",
                        summary = "Summary",
                        rightText = "Right",
                        onClick = {}
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 28.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        MiuixSwitch(
                            checked = switchTrue,
                            onCheckedChange = { switchTrue = it }
                        )
                        MiuixSwitch(
                            modifier = Modifier.padding(start = 8.dp),
                            checked = switch,
                            onCheckedChange = { switch = it }
                        )
                        MiuixSwitch(
                            modifier = Modifier.padding(start = 8.dp),
                            enabled = false,
                            checked = true,
                            onCheckedChange = { }
                        )
                        MiuixSwitch(
                            modifier = Modifier.padding(start = 8.dp),
                            enabled = false,
                            checked = false,
                            onCheckedChange = { }
                        )
                    }

                    MiuixSuperSwitch(
                        title = "TextWithSwitch",
                        summary = textWithSwitch,
                        checked = textWishSwitchTrue,
                        onCheckedChange = {
                            textWishSwitchTrue = it
                            textWithSwitch = "state: $it"
                        },
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 28.dp)
                            .padding(bottom = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        MiuixButton(
                            modifier = Modifier.weight(1f),
                            text = buttonText,
                            onClick = {
                                clickCount++
                                buttonText = "Click: $clickCount"
                            }
                        )
                        MiuixButton(
                            modifier = Modifier.weight(1f).padding(start = 12.dp),
                            text = submitButtonText,
                            submit = true,
                            onClick = {
                                submitClickCount++
                                submitButtonText = "Click: $submitClickCount"
                            }
                        )

                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 28.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        MiuixButton(
                            modifier = Modifier.weight(1f),
                            text = "Disabled",
                            submit = true,
                            enabled = false,
                            onClick = {}
                        )
                        MiuixButton(
                            modifier = Modifier.weight(1f).padding(start = 12.dp),
                            text = "Disabled",
                            submit = false,
                            enabled = false,
                            onClick = {}
                        )
                    }

                    MiuixDropdown(
                        title = "Dropdown",
                        summary = "Summary",
                        options = dropdownOptions,
                        selectedOption = dropdownSelectedOption,
                        onOptionSelected = { newOption -> dropdownSelectedOption.value = newOption },
                    )

                    MiuixDropdown(
                        title = "Dropdown",
                        summary = "AlwaysRight",
                        alwaysRight = true,
                        options = dropdownOptions,
                        selectedOption = dropdownSelectedOptionRight,
                        onOptionSelected = { newOption -> dropdownSelectedOptionRight.value = newOption },
                    )

                    MiuixTextField(
                        value = text,
                        onValueChange = { text = it },
                        label = "Text Field",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 28.dp)
                            .padding(bottom = 15.dp),
                        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    )

                    MiuixSlider(
                        progress = progress,
                        onProgressChange = { newProgress -> progress = newProgress },
                        dragShow = true,
                        modifier = Modifier
                            .padding(horizontal = 28.dp)
                            .padding(bottom = 15.dp)
                    )

                    MiuixSlider(
                        progress = progressFloat,
                        minValue = 0f,
                        maxValue = 1f,
                        height = 26.dp,
                        onProgressChange = { newProgress -> progressFloat = newProgress },
                        modifier = Modifier
                            .padding(horizontal = 28.dp)
                            .padding(bottom = 2.dp)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        MiuixText(
                            text = "0.0",
                            modifier = Modifier
                                .padding(horizontal = 28.dp)
                                .padding(bottom = 15.dp)
                        )
                        MiuixText(
                            text = progressFloat.toString(),
                            modifier = Modifier
                                .padding(horizontal = 28.dp)
                                .padding(bottom = 15.dp)
                        )
                        MiuixText(
                            text = "1.0",
                            modifier = Modifier
                                .padding(horizontal = 28.dp)
                                .padding(bottom = 15.dp)
                        )
                    }

                    MiuixSlider(
                        effect = true,
                        progress = progressEffect,
                        onProgressChange = { newProgress -> progressEffect = newProgress },
                        modifier = Modifier
                            .padding(horizontal = 28.dp)
                            .padding(bottom = 15.dp)
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 28.dp)
                            .padding(bottom = 15.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        MiuixText(text = "0")
                        MiuixSlider(
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 12.dp),
                            progress = progressEffectFloat,
                            minValue = 0f,
                            maxValue = 100f,
                            height = 24.dp,
                            effect = true,
                            dragShow = true,
                            decimalPlaces = 0,
                            onProgressChange = { newProgress -> progressEffectFloat = newProgress },
                        )
                        MiuixText(text = "100")
                    }

                    MiuixCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 28.dp)
                            .padding(bottom = 28.dp)
                    ) {
                        MiuixText(
                            text = "Card",
                            style = MiuixTheme.textStyles.paragraph,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp
                        )
                        Spacer(Modifier.height(10.dp))
                        MiuixText(
                            text = "123456789",
                            style = MiuixTheme.textStyles.paragraph
                        )
                        MiuixText(
                            text = "一二三四五六七八九",
                            style = MiuixTheme.textStyles.paragraph
                        )
                        MiuixText(
                            text = "!@#$%^&*()_+-=",
                            style = MiuixTheme.textStyles.paragraph
                        )
                    }
                }
            }
        }
    }
}