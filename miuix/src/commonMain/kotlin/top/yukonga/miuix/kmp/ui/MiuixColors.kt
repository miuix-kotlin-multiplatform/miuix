package top.yukonga.miuix.kmp.ui

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.structuralEqualityPolicy
import androidx.compose.ui.graphics.Color

@Stable
class MiuixColors(
    primary: Color,
    onPrimary: Color,
    primaryContainer: Color,
    onPrimaryContainer: Color,
    background: Color,
    onBackground: Color,
    cursor: Color,
    subTextField: Color,
    subDropdown: Color,
    switchThumb: Color,
    sliderBackground: Color,
    dropdownBackground: Color,
    dropdownSelect: Color,
) {
    val primary by mutableStateOf(primary, structuralEqualityPolicy())
    val onPrimary by mutableStateOf(onPrimary, structuralEqualityPolicy())
    val primaryContainer by mutableStateOf(primaryContainer, structuralEqualityPolicy())
    val onPrimaryContainer by mutableStateOf(onPrimaryContainer, structuralEqualityPolicy())
    val background by mutableStateOf(background, structuralEqualityPolicy())
    val onBackground by mutableStateOf(onBackground, structuralEqualityPolicy())
    val cursor by mutableStateOf(cursor, structuralEqualityPolicy())
    val subTextField by mutableStateOf(subTextField, structuralEqualityPolicy())
    val subDropdown by mutableStateOf(subDropdown, structuralEqualityPolicy())
    val switchThumb by mutableStateOf(switchThumb, structuralEqualityPolicy())
    val sliderBackground by mutableStateOf(sliderBackground, structuralEqualityPolicy())
    val dropdownBackground by mutableStateOf(dropdownBackground, structuralEqualityPolicy())
    val dropdownSelect by mutableStateOf(dropdownSelect, structuralEqualityPolicy())
}

fun lightColorScheme() = MiuixColors(
    primary = Color(0xFF3482FF),
    onPrimary = Color.Black,
    primaryContainer = Color(0xFFF7F7F7),
    onPrimaryContainer = Color.Black,
    background = Color.White,
    onBackground = Color.Black,
    cursor = Color(0xFF0D84FF),
    subTextField = Color(0xFFA8A8A8),
    subDropdown = Color(0xFF999999),
    switchThumb = Color(0xFFE6E6E6),
    sliderBackground = Color(0xFFEEF1F5),
    dropdownBackground = Color(0xFFFFFFFF),
    dropdownSelect = Color(0xFFEAF2FF),
)

fun darkColorScheme() = MiuixColors(
    primary = Color(0xFF277AF7),
    onPrimary = Color.White,
    primaryContainer = Color(0xFF212121),
    onPrimaryContainer = Color.White,
    background = Color.Black,
    onBackground = Color.White,
    cursor = Color(0xFF0074ED),
    subTextField = Color(0xFF727272),
    subDropdown = Color(0xFF666666),
    switchThumb = Color(0xFF333333),
    sliderBackground = Color(0xFF333333),
    dropdownBackground = Color(0xFF242424),
    dropdownSelect = Color(0xFF23334E),
)