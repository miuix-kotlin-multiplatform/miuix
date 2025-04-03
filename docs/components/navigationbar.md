# NavigationBar

`NavigationBar` 是 Miuix 中的底部导航栏组件，用于在应用底部创建导航菜单，支持 2 到 5 个导航项，每个导航项包含图标和文本标签。

此组件通常与 `Scaffold` 组件结合使用，以便在应用程序的不同页面中保持一致的布局和行为。

## 引入

```kotlin
import top.yukonga.miuix.kmp.basic.NavigationBar
import top.yukonga.miuix.kmp.basic.NavigationItem
```

## 基本用法

NavigationBar 组件可用于创建底部导航菜单：

```kotlin
val items = listOf(
    NavigationItem("首页", MiuixIcons.Useful.NavigatorSwitch),
    NavigationItem("我的", MiuixIcons.Useful.Personal),
    NavigationItem("设置", MiuixIcons.Useful.Settings)
)
var selectedIndex by remember { mutableStateOf(0) }
NavigationBar(
    items = items,
    selected = selectedIndex,
    onClick = { selectedIndex = it }
)
```

## 组件状态

### 选中状态

NavigationBar 会自动处理选中项的视觉样式，选中项将显示粗体文本并高亮。

## 属性

### NavigationBar 属性

| 属性名                     | 类型                  | 说明                     | 默认值                                  | 是否必须 |
| -------------------------- | --------------------- | ------------------------ | --------------------------------------- | -------- |
| items                      | List\<NavigationItem> | 导航项列表               | -                                       | 是       |
| selected                   | Int                   | 当前选中项的索引         | -                                       | 是       |
| onClick                    | (Int) -> Unit         | 点击导航项时的回调       | -                                       | 是       |
| modifier                   | Modifier              | 应用于导航栏的修饰符     | Modifier                                | 否       |
| color                      | Color                 | 导航栏背景颜色           | MiuixTheme.colorScheme.surfaceContainer | 否       |
| showDivider                | Boolean               | 是否显示顶部分割线       | true                                    | 否       |
| defaultWindowInsetsPadding | Boolean               | 是否应用默认窗口嵌入边距 | true                                    | 否       |

### NavigationItem 属性

| 属性名 | 类型        | 说明             | 默认值 | 是否必须 |
| ------ | ----------- | ---------------- | ------ | -------- |
| label  | String      | 导航项的文本标签 | -      | 是       |
| icon   | ImageVector | 导航项的图标     | -      | 是       |

### 常量

| 常量名              | 类型 | 说明             | 值                                         | 是否必须 |
| ------------------- | ---- | ---------------- | ------------------------------------------ | -------- |
| NavigationBarHeight | Dp   | 导航栏的默认高度 | 在非 iOS 平台为 64.dp，在 iOS 平台为 48.dp | 是       |

## 进阶用法

### 自定义颜色

```kotlin
val items = listOf(
    NavigationItem("首页", MiuixIcons.Useful.NavigatorSwitch),
    NavigationItem("我的", MiuixIcons.Useful.Personal),
    NavigationItem("设置", MiuixIcons.Useful.Settings)
)
var selectedIndex by remember { mutableStateOf(0) }
NavigationBar(
    items = items,
    selected = selectedIndex,
    onClick = { selectedIndex = it },
    color = Color.Red.copy(alpha = 0.3f)
)
```

### 无分割线

```kotlin
val items = listOf(
    NavigationItem("首页", MiuixIcons.Useful.NavigatorSwitch),
    NavigationItem("我的", MiuixIcons.Useful.Personal),
    NavigationItem("设置", MiuixIcons.Useful.Settings)
)
var selectedIndex by remember { mutableStateOf(0) }
NavigationBar(
    items = items,
    selected = selectedIndex,
    onClick = { selectedIndex = it },
    showDivider = false
)
```

### 处理窗口边距

```kotlin
NavigationBar(
    items = items,
    selected = selectedIndex,
    onClick = { selectedIndex = it },
    defaultWindowInsetsPadding = false // 自行处理窗口嵌入边距
)
```

### 结合页面切换使用（使用脚手架）

```kotlin
val pages = listOf("首页", "我的", "设置")
val items = listOf(
    NavigationItem("首页", MiuixIcons.Useful.NavigatorSwitch),
    NavigationItem("我的", MiuixIcons.Useful.Personal),
    NavigationItem("设置", MiuixIcons.Useful.Settings)
)
var selectedIndex by remember { mutableStateOf(0) }
Scaffold(
    bottomBar = {
        NavigationBar(
            items = items,
            selected = selectedIndex,
            onClick = { selectedIndex = it }
        )
    }
) { paddingValues ->
    // 内容区域需要考虑 padding
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "当前页面：${pages[selectedIndex]}",
            style = MiuixTheme.textStyles.title1
        )
    }
}
```
