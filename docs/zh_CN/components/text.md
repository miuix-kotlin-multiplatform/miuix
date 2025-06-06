# Text

`Text` 组件是 Miuix 中的基础文本组件，用于显示文字内容。支持自定义各种文本样式、对齐方式和装饰效果。

<div style="position: relative; max-width: 700px; height: 300px; border-radius: 10px; overflow: hidden; border: 1px solid #777;">
    <iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../../compose/index.html?id=text" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>
</div>

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
    color = MiuixTheme.colorScheme.onSurfaceContainerVariant
)
```

## 属性

| 属性名         | 类型                           | 说明                       | 默认值                              | 是否必须 |
| -------------- | ------------------------------ | -------------------------- | ----------------------------------- | -------- |
| text           | String / AnnotatedString       | 要显示的文本内容           | -                                   | 是       |
| modifier       | Modifier                       | 应用于文本的修饰符         | Modifier                            | 否       |
| color          | Color                          | 文本颜色                   | MiuixTheme.colorScheme.onBackground | 否       |
| fontSize       | TextUnit                       | 文本字号                   | TextUnit.Unspecified                | 否       |
| fontStyle      | FontStyle?                     | 文本字体风格（如斜体）     | null                                | 否       |
| fontWeight     | FontWeight?                    | 文本字重                   | null                                | 否       |
| fontFamily     | FontFamily?                    | 文本字体族                 | null                                | 否       |
| letterSpacing  | TextUnit                       | 字母间距                   | TextUnit.Unspecified                | 否       |
| textDecoration | TextDecoration?                | 文本装饰（如下划线）       | null                                | 否       |
| textAlign      | TextAlign?                     | 文本对齐方式               | null                                | 否       |
| lineHeight     | TextUnit                       | 行高                       | TextUnit.Unspecified                | 否       |
| overflow       | TextOverflow                   | 文本溢出处理方式           | TextOverflow.Clip                   | 否       |
| softWrap       | Boolean                        | 是否自动换行               | true                                | 否       |
| maxLines       | Int                            | 最大行数                   | Int.MAX_VALUE                       | 否       |
| minLines       | Int                            | 最小行数                   | 1                                   | 否       |
| onTextLayout   | (TextLayoutResult) -> Unit     | 文本布局完成后的回调       | null                                | 否       |
| style          | TextStyle                      | 文本样式                   | MiuixTheme.textStyles.main          | 否       |
| inlineContent  | Map<String, InlineTextContent> | 用于插入内联可组合项的映射 | mapOf()                             | 否       |

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
