# SuperDropdown

`SuperDropdown` 是 Miuix 中的下拉菜单组件，提供了标题、摘要和下拉选项列表，支持点击交互，常用于选项设置和列表选择中。

<div style="position: relative; max-width: 700px; height: 285px; border-radius: 10px; overflow: hidden; border: 1px solid #777;">
    <iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../../compose/index.html?id=superDropdown" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>
</div>

::: warning 注意
`SuperDropdown` 需要在 `Scaffold` 组件内使用！
:::

## 引入

```kotlin
import top.yukonga.miuix.kmp.extra.SuperDropdown
import top.yukonga.miuix.kmp.extra.DropDownMode
```

## 基本用法

SuperDropdown 组件提供了基础的下拉菜单功能：

```kotlin
var selectedIndex by remember { mutableStateOf(0) }
val options = listOf("选项 1", "选项 2", "选项 3")

Scaffold {
    SuperDropdown(
        title = "下拉菜单",
        items = options,
        selectedIndex = selectedIndex,
        onSelectedIndexChange = { selectedIndex = it }
    )
}
```

## 带摘要的下拉菜单

```kotlin
var selectedIndex by remember { mutableStateOf(0) }
val options = listOf("中文", "English", "日本語")

Scaffold {
    SuperDropdown(
        title = "语言设置",
        summary = "选择您的首选语言",
        items = options,
        selectedIndex = selectedIndex,
        onSelectedIndexChange = { selectedIndex = it }
    )
}
```

## 组件状态

### 禁用状态

```kotlin
SuperDropdown(
    title = "禁用下拉菜单",
    summary = "此下拉菜单当前不可用",
    items = listOf("选项 1"),
    selectedIndex = 0,
    onSelectedIndexChange = {},
    enabled = false
)
```

## 下拉菜单位置

SuperDropdown 支持不同的下拉位置模式：

### 普通模式（根据点击位置自适应）

```kotlin
var selectedIndex by remember { mutableStateOf(0) }
val options = listOf("选项 1", "选项 2", "选项 3")

Scaffold {
    SuperDropdown(
        title = "普通模式",
        items = options,
        selectedIndex = selectedIndex,
        onSelectedIndexChange = { selectedIndex = it },
        mode = DropDownMode.Normal // 默认值
    )
}
```

### 总是在右侧模式

```kotlin
var selectedIndex by remember { mutableStateOf(0) }
val options = listOf("选项 1", "选项 2", "选项 3")

Scaffold {
    SuperDropdown(
        title = "总是在右侧模式",
        items = options,
        selectedIndex = selectedIndex,
        onSelectedIndexChange = { selectedIndex = it },
        mode = DropDownMode.AlwaysOnRight
    )
}
```

## 属性

### SuperDropdown 属性

| 属性名                | 类型                 | 说明                     | 默认值                                | 是否必须 |
| --------------------- | -------------------- | ------------------------ | ------------------------------------- | -------- |
| items                 | List\<String>        | 下拉选项列表             | -                                     | 是       |
| selectedIndex         | Int                  | 当前选中项的索引         | -                                     | 是       |
| title                 | String               | 下拉菜单的标题           | -                                     | 是       |
| titleColor            | BasicComponentColors | 标题文本的颜色配置       | BasicComponentDefaults.titleColor()   | 否       |
| summary               | String?              | 下拉菜单的摘要说明       | null                                  | 否       |
| summaryColor          | BasicComponentColors | 摘要文本的颜色配置       | BasicComponentDefaults.summaryColor() | 否       |
| dropdownColors        | DropdownColors       | 下拉菜单的颜色配置       | DropdownDefaults.dropdownColors()     | 否       |
| mode                  | DropDownMode         | 下拉菜单的显示模式       | DropDownMode.Normal                   | 否       |
| modifier              | Modifier             | 应用于组件的修饰符       | Modifier                              | 否       |
| insideMargin          | PaddingValues        | 组件内部内容的边距       | BasicComponentDefaults.InsideMargin   | 否       |
| maxHeight             | Dp?                  | 下拉菜单的最大高度       | null                                  | 否       |
| enabled               | Boolean              | 组件是否可交互           | true                                  | 否       |
| showValue             | Boolean              | 是否显示当前选中的值     | true                                  | 否       |
| onClick               | (() -> Unit)?        | 点击下拉菜单时的额外回调 | null                                  | 否       |
| onSelectedIndexChange | ((Int) -> Unit)?     | 选中项变化时的回调       | -                                     | 是       |

### DropdownColors 属性

| 属性名                 | 类型  | 说明           |
| ---------------------- | ----- | -------------- |
| contentColor           | Color | 选项文本颜色   |
| containerColor         | Color | 选项背景颜色   |
| selectedContentColor   | Color | 选中项文本颜色 |
| selectedContainerColor | Color | 选中项背景颜色 |

## 进阶用法

### 自定义颜色

```kotlin
var selectedIndex by remember { mutableStateOf(0) }
val options = listOf("红色", "绿色", "蓝色")

Scaffold {
    SuperDropdown(
        title = "自定义颜色",
        items = options,
        selectedIndex = selectedIndex,
        onSelectedIndexChange = { selectedIndex = it },
        dropdownColors = DropdownDefaults.dropdownColors(
            selectedContentColor = Color.Red,
            selectedContainerColor = Color.Red.copy(alpha = 0.2f)
        )
    )
}
```

### 限制下拉菜单高度

```kotlin
var selectedIndex by remember { mutableStateOf(0) }
val options = List(20) { "选项 ${it + 1}" }

Scaffold {
    SuperDropdown(
        title = "限制高度",
        items = options,
        selectedIndex = selectedIndex,
        onSelectedIndexChange = { selectedIndex = it },
        maxHeight = 200.dp // 限制下拉菜单最大高度为 200dp
    )
}
```

### 隐藏选中值显示

```kotlin
var selectedIndex by remember { mutableStateOf(0) }
val options = listOf("选项 1", "选项 2", "选项 3")

Scaffold {
    SuperDropdown(
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
var showDialog = remember { mutableStateOf(false) }
var selectedIndex by remember { mutableStateOf(0) }
val themes = listOf("浅色模式", "深色模式", "跟随系统")

Scaffold {
    SuperArrow(
        title = "主题设置",
        onClick = { showDialog.value = true },
        holdDownState = showDialog.value
    )
    
    SuperDialog(
        title = "主题设置",
        show = showDialog,
        onDismissRequest = { showDialog.value = false } // 关闭对话框
    ) {
        Card {
            SuperDropdown(
                title = "主题选择",
                summary = "选择您喜欢的主题外观",
                items = themes,
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
