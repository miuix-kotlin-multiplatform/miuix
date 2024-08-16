package top.yukonga.miuix.kmp.theme

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.structuralEqualityPolicy
import androidx.compose.ui.graphics.Color

/**
 * The default color scheme for the Miuix components.
 *
 * @param primary The primary color.
 * @param onPrimary The color of the text on primary color.
 * @param primaryContainer The color of the primary container.
 * @param onPrimaryContainer The color of the text on primary container.
 * @param background The background color.
 * @param onBackground The color of the text on background.
 * @param cursor The cursor color.
 * @param subTextBase The base color of the sub text.
 * @param subTextField The color of the sub text field.
 * @param subDropdown The color of the sub dropdown.
 * @param switchThumb The color of the switch thumb.
 * @param sliderBackground The background color of the slider.
 * @param dropdownBackground The background color of the dropdown.
 * @param dropdownSelect The color of the selected item in the dropdown.
 * @param buttonBg The background color of the button.
 * @param disabledBg The background color of the disabled button.
 * @param buttonDisableText The text color of the disabled button.
 * @param submitButtonDisabledText The text color of the disabled submit button.
 */
@Stable
class MiuixColor(
    primary: Color,
    onPrimary: Color,
    primaryContainer: Color,
    onPrimaryContainer: Color,
    background: Color,
    onBackground: Color,
    cursor: Color,
    subTextBase: Color,
    subTextField: Color,
    subDropdown: Color,
    switchThumb: Color,
    sliderBackground: Color,
    dropdownBackground: Color,
    dropdownSelect: Color,
    buttonBg: Color,
    disabledBg: Color,
    buttonDisableText: Color,
    submitButtonDisabledText: Color
) {
    val primary by mutableStateOf(primary, structuralEqualityPolicy())
    val onPrimary by mutableStateOf(onPrimary, structuralEqualityPolicy())
    val primaryContainer by mutableStateOf(primaryContainer, structuralEqualityPolicy())
    val onPrimaryContainer by mutableStateOf(onPrimaryContainer, structuralEqualityPolicy())
    val background by mutableStateOf(background, structuralEqualityPolicy())
    val onBackground by mutableStateOf(onBackground, structuralEqualityPolicy())
    val cursor by mutableStateOf(cursor, structuralEqualityPolicy())
    val subTextBase by mutableStateOf(subTextBase, structuralEqualityPolicy())
    val subTextField by mutableStateOf(subTextField, structuralEqualityPolicy())
    val subDropdown by mutableStateOf(subDropdown, structuralEqualityPolicy())
    val switchThumb by mutableStateOf(switchThumb, structuralEqualityPolicy())
    val sliderBackground by mutableStateOf(sliderBackground, structuralEqualityPolicy())
    val dropdownBackground by mutableStateOf(dropdownBackground, structuralEqualityPolicy())
    val dropdownSelect by mutableStateOf(dropdownSelect, structuralEqualityPolicy())
    val buttonBg by mutableStateOf(buttonBg, structuralEqualityPolicy())
    val disabledBg by mutableStateOf(disabledBg, structuralEqualityPolicy())
    val buttonDisableText by mutableStateOf(buttonDisableText, structuralEqualityPolicy())
    val submitButtonDisabledText by mutableStateOf(submitButtonDisabledText, structuralEqualityPolicy())
}

fun lightColorScheme() = MiuixColor(
    primary = Color(0xFF3482FF),
    onPrimary = Color.Black,
    primaryContainer = Color(0xFFF7F7F7),
    onPrimaryContainer = Color.Black,
    background = Color.White,
    onBackground = Color.Black,
    cursor = Color(0xFF0D84FF),
    subTextBase = Color(0xFF666666),
    subTextField = Color(0xFFA8A8A8),
    subDropdown = Color(0xFF999999),
    switchThumb = Color(0xFFE6E6E6),
    sliderBackground = Color(0xFFEEF1F5),
    dropdownBackground = Color(0xFFFFFFFF),
    dropdownSelect = Color(0xFFEAF2FF),
    buttonBg = Color(0xFFF0F0F0),
    disabledBg = Color(0xFFC2D9FF),
    buttonDisableText = Color.White,
    submitButtonDisabledText = Color(0xFFFFFFFF)
)

fun darkColorScheme() = MiuixColor(
    primary = Color(0xFF277AF7),
    onPrimary = Color.White,
    primaryContainer = Color(0xFF212121),
    onPrimaryContainer = Color.White,
    background = Color.Black,
    onBackground = Color.White,
    cursor = Color(0xFF0074ED),
    subTextBase = Color(0xFF808080),
    subTextField = Color(0xFF727272),
    subDropdown = Color(0xFF666666),
    switchThumb = Color(0xFF333333),
    sliderBackground = Color(0xFF333333),
    dropdownBackground = Color(0xFF242424),
    dropdownSelect = Color(0xFF23334E),
    buttonBg = Color(0xFF454545),
    disabledBg = Color(0xFF253E64),
    buttonDisableText = Color.Gray,
    submitButtonDisabledText = Color(0xFF677893)
)