# SuperSpinner

`SuperSpinner` 是 Miuix 中的下拉选择器组件，提供了标题、摘要和带有图标、文本的选项列表，支持点击交互和多种显示模式，常用于具有视觉辅助的选项设置中。该组件与 `SuperDropdown` 组件类似，但提供更丰富的功能和交互体验。

<div style="position: relative; max-width: 700px; height: 282px; border-radius: 10px; overflow: hidden; border: 1px solid #777;">
    <iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../../compose/index.html?id=superSpinner" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>
</div>

::: warning 注意
`SuperSpinner` 需要在 `Scaffold` 组件内使用！
:::

## 引入

```kotlin
import top.yukonga.miuix.kmp.extra.SuperSpinner
import top.yukonga.miuix.kmp.extra.SpinnerEntry
import top.yukonga.miuix.kmp.extra.SpinnerMode
```

## 基本用法

SuperSpinner 组件提供了基础的下拉选择器功能：

```kotlin
var selectedIndex by remember { mutableStateOf(0) }
val options = listOf(
    SpinnerEntry(title = "选项 1"),
    SpinnerEntry(title = "选项 2"),
    SpinnerEntry(title = "选项 3"),
)

Scaffold {
    SuperSpinner(
        title = "下拉选择器",
        items = options,
        selectedIndex = selectedIndex,
        onSelectedIndexChange = { selectedIndex = it }
    )
}
```

## 带图标和摘要的选项

```kotlin
// 创建一个圆角矩形的 Painter
class RoundedRectanglePainter(
    private val cornerRadius: Dp = 6.dp
) : Painter() {
    override val intrinsicSize = Size.Unspecified

    override fun DrawScope.onDraw() {
        drawRoundRect(
            color = Color.White,
            size = Size(size.width, size.height),
            cornerRadius = CornerRadius(cornerRadius.toPx(), cornerRadius.toPx())
        )
    }
}

var selectedIndex by remember { mutableStateOf(0) }
val options = listOf(
    SpinnerEntry(
        icon = { Icon(RoundedRectanglePainter(), "Icon", Modifier.padding(end = 12.dp), Color(0xFFFF5B29)) },
        title = "红色主题",
        summary = "活力四射的红色"
    ),
    SpinnerEntry(
        icon = { Icon(RoundedRectanglePainter(), "Icon", Modifier.padding(end = 12.dp), Color(0xFF3482FF)) },
        title = "蓝色主题",
        summary = "沉稳冷静的蓝色"
    ),
    SpinnerEntry(
        icon = { Icon(RoundedRectanglePainter(), "Icon", Modifier.padding(end = 12.dp), Color(0xFF36D167)) },
        title = "绿色主题",
        summary = "清新自然的绿色"
    ),
    SpinnerEntry(
        icon = { Icon(RoundedRectanglePainter(), "Icon", Modifier.padding(end = 12.dp), Color(0xFFFFB21D)) }, 
        title = "黄色主题",
        summary = "明亮活泼的黄色"
    )
)

Scaffold {
    SuperSpinner(
        title = "功能选择",
        summary = "选择您要执行的操作",
        items = options,
        selectedIndex = selectedIndex,
        onSelectedIndexChange = { selectedIndex = it }
    )
}
```

## 组件状态

### 禁用状态

```kotlin
SuperSpinner(
    title = "禁用选择器",
    summary = "此选择器当前不可用",
    items = listOf(SpinnerEntry(title = "选项 1")),
    selectedIndex = 0,
    onSelectedIndexChange = {},
    enabled = false
)
```

## 显示模式

SuperSpinner 支持不同的显示模式：

### 普通模式（根据点击位置自适应）

```kotlin
var selectedIndex by remember { mutableStateOf(0) }
val options = listOf(
    SpinnerEntry(title = "选项 1"),
    SpinnerEntry(title = "选项 2"),
    SpinnerEntry(title = "选项 3")
)

Scaffold {
    SuperSpinner(
        title = "普通模式",
        items = options,
        selectedIndex = selectedIndex,
        onSelectedIndexChange = { selectedIndex = it },
        mode = SpinnerMode.Normal // 默认值
    )
}
```

### 总是在右侧模式

```kotlin
var selectedIndex by remember { mutableStateOf(0) }
val options = listOf(
    SpinnerEntry(title = "选项 1"),
    SpinnerEntry(title = "选项 2"),
    SpinnerEntry(title = "选项 3")
)

Scaffold {
    SuperSpinner(
        title = "总是在右侧模式",
        items = options,
        selectedIndex = selectedIndex,
        onSelectedIndexChange = { selectedIndex = it },
        mode = SpinnerMode.AlwaysOnRight // 总是在右侧模式
    )
}
```

### 将下拉菜单显示在对话框中

```kotlin
var selectedIndex by remember { mutableStateOf(0) }
val options = listOf(
    SpinnerEntry(title = "选项 1"),
    SpinnerEntry(title = "选项 2"),
    SpinnerEntry(title = "选项 3")
)

Scaffold {
    SuperSpinner(
        title = "对话框模式",
        dialogButtonString = "取消",
        items = options,
        selectedIndex = selectedIndex,
        onSelectedIndexChange = { selectedIndex = it }
    )
}
```

## 属性

### SuperSpinner 属性（下拉列表模式）

| 属性名                | 类型                      | 说明                   | 默认值                                | 是否必须 |
| --------------------- | ------------------------- | ---------------------- | ------------------------------------- | -------- |
| items                 | List\<SpinnerEntry>       | 选项列表               | -                                     | 是       |
| selectedIndex         | Int                       | 当前选中项的索引       | -                                     | 是       |
| title                 | String                    | 选择器的标题           | -                                     | 是       |
| titleColor            | BasicComponentColors      | 标题文本的颜色配置     | BasicComponentDefaults.titleColor()   | 否       |
| summary               | String?                   | 选择器的摘要说明       | null                                  | 否       |
| summaryColor          | BasicComponentColors      | 摘要文本的颜色配置     | BasicComponentDefaults.summaryColor() | 否       |
| leftAction            | @Composable (() -> Unit)? | 左侧显示的自定义内容   | null                                  | 否       |
| mode                  | SpinnerMode               | 选择器的显示模式       | SpinnerMode.Normal                    | 否       |
| modifier              | Modifier                  | 应用于组件的修饰符     | Modifier                              | 否       |
| insideMargin          | PaddingValues             | 组件内部内容的边距     | BasicComponentDefaults.InsideMargin   | 否       |
| maxHeight             | Dp?                       | 下拉菜单的最大高度     | null                                  | 否       |
| enabled               | Boolean                   | 组件是否可交互         | true                                  | 否       |
| showValue             | Boolean                   | 是否显示当前选中的值   | true                                  | 否       |
| onClick               | (() -> Unit)?             | 点击选择器时的额外回调 | null                                  | 否       |
| onSelectedIndexChange | ((Int) -> Unit)?          | 选中项变化时的回调     | -                                     | 是       |

### SuperSpinner 属性（对话框模式）

| 属性名                | 类型                      | 说明                     | 默认值                                | 是否必须 |
| --------------------- | ------------------------- | ------------------------ | ------------------------------------- | -------- |
| items                 | List\<SpinnerEntry>       | 选项列表                 | -                                     | 是       |
| selectedIndex         | Int                       | 当前选中项的索引         | -                                     | 是       |
| title                 | String                    | 选择器的标题             | -                                     | 是       |
| titleColor            | BasicComponentColors      | 标题文本的颜色配置       | BasicComponentDefaults.titleColor()   | 否       |
| summary               | String?                   | 选择器的摘要说明         | null                                  | 否       |
| summaryColor          | BasicComponentColors      | 摘要文本的颜色配置       | BasicComponentDefaults.summaryColor() | 否       |
| leftAction            | @Composable (() -> Unit)? | 左侧显示的自定义内容     | null                                  | 否       |
| dialogButtonString    | String                    | 对话框底部按钮的文本     | -                                     | 是       |
| popupModifier         | Modifier                  | 应用于弹出对话框的修饰符 | Modifier                              | 否       |
| modifier              | Modifier                  | 应用于组件的修饰符       | Modifier                              | 否       |
| insideMargin          | PaddingValues             | 组件内部内容的边距       | BasicComponentDefaults.InsideMargin   | 否       |
| enabled               | Boolean                   | 组件是否可交互           | true                                  | 否       |
| showValue             | Boolean                   | 是否显示当前选中的值     | true                                  | 否       |
| onClick               | (() -> Unit)?             | 点击选择器时的额外回调   | null                                  | 否       |
| onSelectedIndexChange | ((Int) -> Unit)?          | 选中项变化时的回调       | -                                     | 是       |

### SpinnerEntry 属性

| 属性名  | 类型                              | 说明           |
| ------- | --------------------------------- | -------------- |
| icon    | @Composable ((Modifier) -> Unit)? | 选项的图标组件 |
| title   | String?                           | 选项的标题     |
| summary | String?                           | 选项的摘要描述 |

## 进阶用法

### 自定义左侧内容

```kotlin
var selectedIndex by remember { mutableStateOf(0) }
val options = listOf(
    SpinnerEntry(title = "红色"),
    SpinnerEntry(title = "绿色"),
    SpinnerEntry(title = "蓝色")
)

Scaffold {
    SuperSpinner(
        title = "自定义左侧内容",
        items = options,
        selectedIndex = selectedIndex,
        onSelectedIndexChange = { selectedIndex = it },
        leftAction = {
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .background(
                        when (selectedIndex) {
                            0 -> Color.Red
                            1 -> Color.Green
                            else -> Color.Blue
                        },
                        shape = CircleShape
                    )
            )
            Spacer(Modifier.width(8.dp))
        }
    )
}
```

### 限制下拉菜单高度

```kotlin
var selectedIndex by remember { mutableStateOf(0) }
val options = List(20) { SpinnerEntry(title = "选项 ${it + 1}") }

Scaffold {
    SuperSpinner(
        title = "限制高度",
        items = options,
        selectedIndex = selectedIndex,
        onSelectedIndexChange = { selectedIndex = it },
        maxHeight = 300.dp // 限制下拉菜单最大高度为300dp
    )
}
```

### 隐藏选中值显示

```kotlin
var selectedIndex by remember { mutableStateOf(0) }
val options = listOf(
    SpinnerEntry(title = "选项 1"),
    SpinnerEntry(title = "选项 2"),
    SpinnerEntry(title = "选项 3")
)

Scaffold {
    SuperSpinner(
        title = "隐藏选中值",
        items = options,
        selectedIndex = selectedIndex,
        onSelectedIndexChange = { selectedIndex = it },
        showValue = false // 隐藏选中值显示
    )
}
```

### 结合对话框使用

```kotlin
// 创建一个圆角矩形的 Painter
class RoundedRectanglePainter(
    private val cornerRadius: Dp = 6.dp
) : Painter() {
    override val intrinsicSize = Size.Unspecified

    override fun DrawScope.onDraw() {
        drawRoundRect(
            color = Color.White,
            size = Size(size.width, size.height),
            cornerRadius = CornerRadius(cornerRadius.toPx(), cornerRadius.toPx())
        )
    }
}

var showDialog = remember { mutableStateOf(false) }
var selectedIndex by remember { mutableStateOf(0) }
val colorOptions = listOf(
    SpinnerEntry(
        icon = { Icon(RoundedRectanglePainter(), "Icon", Modifier.padding(end = 12.dp), Color(0xFFFF5B29)) },
        title = "红色主题",
        summary = "活力四射的红色"
    ),
    SpinnerEntry(
        icon = { Icon(RoundedRectanglePainter(), "Icon", Modifier.padding(end = 12.dp), Color(0xFF3482FF)) },
        title = "蓝色主题",
        summary = "沉稳冷静的蓝色"
    ),
    SpinnerEntry(
        icon = { Icon(RoundedRectanglePainter(), "Icon", Modifier.padding(end = 12.dp), Color(0xFF36D167)) },
        title = "绿色主题",
        summary = "清新自然的绿色"
    ),
    SpinnerEntry(
        icon = { Icon(RoundedRectanglePainter(), "Icon", Modifier.padding(end = 12.dp), Color(0xFFFFB21D)) }, 
        title = "黄色主题",
        summary = "明亮活泼的黄色"
    )
)

Scaffold {
    SuperArrow(
        title = "主题颜色",
        onClick = { showDialog.value = true },
        holdDownState = showDialog.value
    )
    
    SuperDialog(
        title = "主题颜色设置",
        show = showDialog,
        onDismissRequest = { showDialog.value = false } // 关闭对话框
    ) {
        Card {
            SuperSpinner(
                title = "选择主题颜色",
                summary = "选择您喜欢的主题颜色",
                items = colorOptions,
                selectedIndex = selectedIndex,
                onSelectedIndexChange = { selectedIndex = it }
            )
        }
        
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(top = 12.dp)
        ) {
            TextButton(
                text = "取消",
                onClick = { showDialog.value = false }, // 关闭对话框
                modifier = Modifier.weight(1f)
            )
            Spacer(Modifier.width(16.dp))
            TextButton(
                text = "确认",
                onClick = { showDialog.value = false }, // 关闭对话框
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.textButtonColorsPrimary() // 使用主题颜色
            )
        }
    }
}
```
