# Text

`Text` 组件是 Miuix 中的基础文本组件，用于显示文字内容。支持自定义各种文本样式、对齐方式和装饰效果。

## 引入

```kotlin
import top.yukonga.miuix.kmp.basic.Text
```

## 基本用法

最简单的文本展示：

```kotlin
Text("这是一段基本文本")
```

## 文本样式

Miuix 提供了多种预定义的文本样式：

```kotlin
Text(
    text = "标题文本",
    style = MiuixTheme.textStyles.headline1
)

Text(
    text = "小标题文本",
    style = MiuixTheme.textStyles.subtitle
)
Text(
    text = "摘要文本",
    style = MiuixTheme.textStyles.body2
)

Text(
    text = "正文文本",
    style = MiuixTheme.textStyles.main
)

```

## 文本颜色

```kotlin
Text(
    text = "默认颜色文本",
    color = MiuixTheme.colorScheme.onBackground
)

Text(
    text = "主题色文本",
    color = MiuixTheme.colorScheme.primary
)

Text(
    text = "次要文本",
    color = MiuixTheme.colorScheme.onSurfaceVariant
)
```

## 属性

| 属性名         | 类型                           | 默认值                              | 说明                       |
| -------------- | ------------------------------ | ----------------------------------- | -------------------------- |
| text           | String / AnnotatedString       | -                                   | 要显示的文本内容           |
| modifier       | Modifier                       | Modifier                            | 应用于文本的修饰符         |
| color          | Color                          | MiuixTheme.colorScheme.onBackground | 文本颜色                   |
| fontSize       | TextUnit                       | TextUnit.Unspecified                | 文本字号                   |
| fontStyle      | FontStyle?                     | null                                | 文本字体风格（如斜体）     |
| fontWeight     | FontWeight?                    | null                                | 文本字重                   |
| fontFamily     | FontFamily?                    | null                                | 文本字体族                 |
| letterSpacing  | TextUnit                       | TextUnit.Unspecified                | 字母间距                   |
| textDecoration | TextDecoration?                | null                                | 文本装饰（如下划线）       |
| textAlign      | TextAlign?                     | null                                | 文本对齐方式               |
| lineHeight     | TextUnit                       | TextUnit.Unspecified                | 行高                       |
| overflow       | TextOverflow                   | TextOverflow.Clip                   | 文本溢出处理方式           |
| softWrap       | Boolean                        | true                                | 是否自动换行               |
| maxLines       | Int                            | Int.MAX_VALUE                       | 最大行数                   |
| minLines       | Int                            | 1                                   | 最小行数                   |
| onTextLayout   | (TextLayoutResult) -> Unit     | null                                | 文本布局完成后的回调       |
| style          | TextStyle                      | MiuixTheme.textStyles.main          | 文本样式                   |
| inlineContent  | Map<String, InlineTextContent> | mapOf()                             | 用于插入内联可组合项的映射 |

## 进阶用法

### 多行文本截断

```kotlin
Text(
    text = "这是一段很长很长的文本，当空间不足时会被截断并显示省略号，这在显示长内容摘要时很有用。",
    maxLines = 2,
    overflow = TextOverflow.Ellipsis
)
```

### 文本装饰

```kotlin
Text(
    text = "带下划线的文本",
    textDecoration = TextDecoration.Underline
)

Text(
    text = "带删除线的文本",
    textDecoration = TextDecoration.LineThrough
)
```

### 富文本混排

```kotlin
Text(
    buildAnnotatedString {
        withStyle(style = SpanStyle(color = MiuixTheme.colorScheme.primary)) {
            append("Miuix ")
        }
        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
            append("UI 库")
        }
        append("，这是一段富文本")
    }
)
```
