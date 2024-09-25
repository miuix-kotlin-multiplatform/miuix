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
 * @param background The background color.
 * @param onBackground The color of the text on background.
 * @param subTextBase The base color of the sub text.
 * @param secondary The secondary color.
 * @param dropdownBackground The background color of the dropdown.
 * @param dropdownSelect The color of the selected item in the dropdown.
 * @param textFieldBg The background color of the text field.
 * @param textFieldSub The color of the sub text field.
 * @param disabledSecondary The background color of the disabled button.
 * @param disabledPrimary The background color of the disabled submit button.
 * @param onDisabledSecondary The text color of the disabled button.
 * @param onDisabledPrimary The text color of the disabled submit button.
 * @param smallTitle The color of the small title.
 */
@Stable
class MiuixColor(
    primary: Color,
    onPrimary: Color,
    primaryContainer: Color,
    background: Color,
    onBackground: Color,
    subTextBase: Color,
    secondary: Color,
    dropdownBackground: Color,
    dropdownSelect: Color,
    searchBarBg: Color,
    searchBarSub: Color,
    textFieldBg: Color,
    textFieldSub: Color,
    disabledPrimary: Color,
    disabledSecondary: Color,
    onDisabledPrimary: Color,
    onDisabledSecondary: Color,
    smallTitle: Color
) {
    val primary by mutableStateOf(primary, structuralEqualityPolicy())
    val onPrimary by mutableStateOf(onPrimary, structuralEqualityPolicy())
    val primaryContainer by mutableStateOf(primaryContainer, structuralEqualityPolicy())
    val background by mutableStateOf(background, structuralEqualityPolicy())
    val onBackground by mutableStateOf(onBackground, structuralEqualityPolicy())
    val subTextBase by mutableStateOf(subTextBase, structuralEqualityPolicy())
    val secondary by mutableStateOf(secondary, structuralEqualityPolicy())
    val dropdownBackground by mutableStateOf(dropdownBackground, structuralEqualityPolicy())
    val dropdownSelect by mutableStateOf(dropdownSelect, structuralEqualityPolicy())
    val searchBarBg by mutableStateOf(searchBarBg, structuralEqualityPolicy())
    val searchBarSub by mutableStateOf(searchBarSub, structuralEqualityPolicy())
    val textFieldBg by mutableStateOf(textFieldBg, structuralEqualityPolicy())
    val textFieldSub by mutableStateOf(textFieldSub, structuralEqualityPolicy())
    val disabledPrimary by mutableStateOf(disabledPrimary, structuralEqualityPolicy())
    val disabledSecondary by mutableStateOf(disabledSecondary, structuralEqualityPolicy())
    val onDisabledPrimary by mutableStateOf(onDisabledPrimary, structuralEqualityPolicy())
    val onDisabledSecondary by mutableStateOf(onDisabledSecondary, structuralEqualityPolicy())
    val smallTitle by mutableStateOf(smallTitle, structuralEqualityPolicy())
}

fun lightColorScheme() = MiuixColor(
    primary = Color(0xFF3482FF),
    onPrimary = Color.Black,
    primaryContainer = Color.White,
    background = Color(0xFFF7F7F7),
    onBackground = Color.Black,
    subTextBase = Color(0xFF666666),
    secondary = Color(0xFFE6E6E6),
    dropdownBackground = Color(0xFFFFFFFF),
    dropdownSelect = Color(0xFFEAF2FF),
    searchBarBg = Color(0xFFEDEDED),
    searchBarSub = Color(0xFFA5A5A5),
    textFieldBg = Color(0xFFF0F0F0),
    textFieldSub = Color(0xFFA8A8A8),
    disabledPrimary = Color(0xFFC2D9FF),
    disabledSecondary = Color(0xFFF2F2F2),
    onDisabledPrimary = Color(0xFFEDF4FF),
    onDisabledSecondary = Color(0xFFFCFCFC),
    smallTitle = Color(0xFF8C93B0)
)

fun darkColorScheme() = MiuixColor(
    primary = Color(0xFF277AF7),
    onPrimary = Color.White,
    primaryContainer = Color(0xFF242424),
    background = Color(0xFF101010),
    onBackground = Color(0xFFE9E9E9),
    subTextBase = Color(0xFF929292),
    secondary = Color(0xFF505050),
    dropdownBackground = Color(0xFF2C2C2C),
    dropdownSelect = Color(0xFF2B3B54),
    searchBarBg = Color(0xFF2D2D2D),
    searchBarSub = Color(0xFF6C6C6C),
    textFieldBg = Color(0xFF2C2C2C),
    textFieldSub = Color(0xFF6C6C6C),
    disabledPrimary = Color(0xFF253E64),
    disabledSecondary = Color(0xFF404040),
    onDisabledPrimary = Color(0xFF677993),
    onDisabledSecondary = Color(0xFF707070),
    smallTitle = Color(0xFF787E96)
)