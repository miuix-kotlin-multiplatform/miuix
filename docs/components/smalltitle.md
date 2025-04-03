# SmallTitle

`SmallTitle` 是 Miuix 中的基础小标题组件，用于快速创建小型标题文本。采用 Miuix 的设计风格，具有预设的字体大小、字重和内边距。

## 引入

```kotlin
import top.yukonga.miuix.kmp.basic.SmallTitle
```

## 基本用法

SmallTitle 组件可以用于展示小型标题文本：

```kotlin
SmallTitle(
    text = "小标题"
)
```

## 自定义外观

### 自定义颜色

```kotlin
SmallTitle(
    text = "自定义颜色的小标题",
    textColor = Color.Red
)
```

### 自定义内边距

```kotlin
SmallTitle(
    text = "自定义内边距的小标题",
    insideMargin = PaddingValues(16.dp, 4.dp)
)
```

## 属性

### SmallTitle 属性

| 属性名       | 类型          | 说明               | 默认值                                     | 是否必须 |
| ------------ | ------------- | ------------------ | ------------------------------------------ | -------- |
| text         | String        | 要显示的文本内容   | -                                          | 是       |
| modifier     | Modifier      | 应用于组件的修饰符 | Modifier                                   | 否       |
| textColor    | Color         | 标题文本颜色       | MiuixTheme.colorScheme.onBackgroundVariant | 否       |
| insideMargin | PaddingValues | 组件内部边距       | PaddingValues(28.dp, 8.dp)                 | 否       |

## 进阶用法

### 与其他组件组合使用

```kotlin
Column {
    SmallTitle(text = "设置")
    Card(
        modifier = Modifier.padding(horizontal = 12.dp).padding(bottom = 12.dp)
    ) {
        var checked by remember { mutableStateOf(false) }
        SuperSwitch(
            title = "蓝牙",
            checked = checked,
               onCheckedChange = { checked = it }
        )
    }
    HorizontalDivider(
        modifier = Modifier.padding(horizontal = 12.dp)
    )
    SmallTitle(text = "高级设置")
    // 其他设置项
}
```

### 自定义样式

```kotlin
SmallTitle(
    text = "完全自定义样式",
    modifier = Modifier
        .fillMaxWidth()
        .background(Color.LightGray),
    textColor = Color.Blue,
    insideMargin = PaddingValues(32.dp, 12.dp)
)
```