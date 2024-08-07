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
    subText: Color
) {
    val primary by mutableStateOf(primary, structuralEqualityPolicy())
    val onPrimary by mutableStateOf(onPrimary, structuralEqualityPolicy())
    val primaryContainer by mutableStateOf(primaryContainer, structuralEqualityPolicy())
    val onPrimaryContainer by mutableStateOf(onPrimaryContainer, structuralEqualityPolicy())
    val background by mutableStateOf(background, structuralEqualityPolicy())
    val onBackground by mutableStateOf(onBackground, structuralEqualityPolicy())
    val cursor by mutableStateOf(cursor, structuralEqualityPolicy())
    val subText by mutableStateOf(subText, structuralEqualityPolicy())
}

fun lightColorScheme() = MiuixColors(
    primary = Color(0xFF3482FF),
    onPrimary = Color.Black,
    primaryContainer = Color(0xFFF7F7F7),
    onPrimaryContainer = Color.Black,
    background = Color.White,
    onBackground = Color.Black,
    cursor = Color(0xFF0D84FF),
    subText = Color(0xFFA8A8A8)
)

fun darkColorScheme() = MiuixColors(
    primary = Color(0xFF277AF7),
    onPrimary = Color.White,
    primaryContainer = Color(0xFF212121),
    onPrimaryContainer = Color.White,
    background = Color.Black,
    onBackground = Color.White,
    cursor = Color(0xFF0074ED),
    subText = Color(0xFF727272)
)