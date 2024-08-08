package top.yukonga.miuix.kmp.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp

@Immutable
class MiuixTextStyles(
    val main: TextStyle,
    val title: TextStyle,
    val semi: TextStyle,
    val paragraph: TextStyle
)

fun miuixTextStyles(
    main: TextStyle = DefaultTextStyle,
    title: TextStyle = TitleTextStyle,
    semi: TextStyle = semiTextStyle,
    paragraph: TextStyle = ParagraphTextStyle
): MiuixTextStyles = MiuixTextStyles(
    main = main,
    title = title,
    semi = semi,
    paragraph = paragraph
)

private val DefaultTextStyle: TextStyle
    get() = TextStyle(
        fontSize = 16.sp,
    )

private val TitleTextStyle: TextStyle
    get() = TextStyle(
        fontSize = 12.sp
    )

private val semiTextStyle: TextStyle
    get() = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.SemiBold
    )

private val ParagraphTextStyle: TextStyle
    get() = TextStyle(
        fontSize = 16.sp,
        lineHeight = 1.2f.em
    )