package top.yukonga.miuix.kmp.ui

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp

@Immutable
class MiuixTextStyles(
    val main: TextStyle,
    val title: TextStyle,
    val paragraph: TextStyle
)

fun miuixTextStyles(
    main: TextStyle = DefaultTextStyle,
    title: TextStyle = TitleTextStyle,
    paragraph: TextStyle = ParagraphTextStyle
): MiuixTextStyles = MiuixTextStyles(
    main = main,
    title = title,
    paragraph = paragraph
)

private val DefaultTextStyle: TextStyle
    get() = TextStyle(
        fontSize = 16.sp
    )

private val TitleTextStyle: TextStyle
    get() = TextStyle(
        fontSize = 12.sp
    )

private val ParagraphTextStyle: TextStyle
    get() = TextStyle(
        fontSize = 16.sp,
        lineHeight = 1.2f.em
    )