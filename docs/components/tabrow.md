# TabRow

`TabRow` 是 Miuix 中的导航组件，用于创建可横向滚动的标签页。提供了标准样式和带轮廓（Contour）样式两种变体，适用于内容分类和导航场景。

## 引入

```kotlin
import top.yukonga.miuix.kmp.basic.TabRow // 标准样式
import top.yukonga.miuix.kmp.basic.TabRowWithContour // 带轮廓样式
```

## 基本用法

### 标准样式

```kotlin
val tabs = listOf("推荐", "关注", "热门", "精选")
var selectedTabIndex by remember { mutableStateOf(0) }
TabRow(
    tabs = tabs,
    selectedTabIndex = selectedTabIndex,
    onTabSelected = { selectedTabIndex = it }
)
```

### 带轮廓样式

```kotlin
val tabs = listOf("全部", "照片", "视频", "文档")
var selectedTabIndex by remember { mutableStateOf(0) }
TabRowWithContour(
    tabs = tabs,
    selectedTabIndex = selectedTabIndex,
    onTabSelected = { selectedTabIndex = it }
)
```

## 属性

### TabRow 属性

| 属性名           | 类型             | 默认值                            | 说明                 |
| ---------------- | ---------------- | --------------------------------- | -------------------- |
| tabs             | List\<String>    | -                                 | 标签文本列表         |
| selectedTabIndex | Int              | -                                 | 当前选中的标签索引   |
| modifier         | Modifier         | Modifier                          | 应用于标签行的修饰符 |
| colors           | TabRowColors     | TabRowDefaults.tabRowColors()     | 标签行的颜色配置     |
| minWidth         | Dp               | TabRowDefaults.TabRowMinWidth     | 每个标签的最小宽度   |
| height           | Dp               | TabRowDefaults.TabRowHeight       | 标签行的高度         |
| cornerRadius     | Dp               | TabRowDefaults.TabRowCornerRadius | 标签的圆角半径       |
| onTabSelected    | ((Int) -> Unit)? | null                              | 标签选中时的回调函数 |

### TabRowWithContour 属性

| 属性名           | 类型             | 默认值                                       | 说明                 |
| ---------------- | ---------------- | -------------------------------------------- | -------------------- |
| tabs             | List\<String>    | -                                            | 标签文本列表         |
| selectedTabIndex | Int              | -                                            | 当前选中的标签索引   |
| modifier         | Modifier         | Modifier                                     | 应用于标签行的修饰符 |
| colors           | TabRowColors     | TabRowDefaults.tabRowColors()                | 标签行的颜色配置     |
| minWidth         | Dp               | TabRowDefaults.TabRowWithContourMinWidth     | 每个标签的最小宽度   |
| height           | Dp               | TabRowDefaults.TabRowHeight                  | 标签行的高度         |
| cornerRadius     | Dp               | TabRowDefaults.TabRowWithContourCornerRadius | 标签的圆角半径       |
| onTabSelected    | ((Int) -> Unit)? | null                                         | 标签选中时的回调函数 |

### TabRowDefaults 对象

TabRowDefaults 对象提供了 TabRow 组件的默认配置。

#### 常量

| 常量名                        | 类型 | 值    | 说明                         |
| ----------------------------- | ---- | ----- | ---------------------------- |
| TabRowHeight                  | Dp   | 42.dp | 标签行的默认高度             |
| TabRowCornerRadius            | Dp   | 8.dp  | 标准样式的默认圆角半径       |
| TabRowWithContourCornerRadius | Dp   | 10.dp | 带轮廓样式的默认圆角半径     |
| TabRowMinWidth                | Dp   | 76.dp | 标准样式的每个标签最小宽度   |
| TabRowWithContourMinWidth     | Dp   | 62.dp | 带轮廓样式的每个标签最小宽度 |

#### 方法

| 方法名         | 返回类型     | 说明                     |
| -------------- | ------------ | ------------------------ |
| tabRowColors() | TabRowColors | 创建标签行的默认颜色配置 |

### TabRowColors 类

| 属性名                  | 类型  | 说明                   |
| ----------------------- | ----- | ---------------------- |
| backgroundColor         | Color | 标签的默认背景色       |
| contentColor            | Color | 标签的默认内容色       |
| selectedBackgroundColor | Color | 选中状态时标签的背景色 |
| selectedContentColor    | Color | 选中状态时标签的内容色 |

## 进阶用法

### 自定义颜色

```kotlin
val tabs = listOf("最新", "热门", "关注")
var selectedTabIndex by remember { mutableStateOf(0) }
TabRow(
    tabs = tabs,
    selectedTabIndex = selectedTabIndex,
    onTabSelected = { selectedTabIndex = it },
    colors = TabRowDefaults.tabRowColors(
        backgroundColor = Color.LightGray.copy(alpha = 0.5f),
        contentColor = Color.Gray,
        selectedBackgroundColor = MiuixTheme.colorScheme.primary,
        selectedContentColor = Color.White
    )
)
```

### 自定义尺寸

```kotlin
val tabs = listOf("短视频", "直播", "图文")
var selectedTabIndex by remember { mutableStateOf(0) }
TabRowWithContour(
    tabs = tabs,
    selectedTabIndex = selectedTabIndex,
    onTabSelected = { selectedTabIndex = it },
    minWidth = 100.dp,
    height = 50.dp,
    cornerRadius = 15.dp
)
```

### 与 Pager 结合使用

```kotlin
val tabs = listOf("页面1", "页面2", "页面3")
val pagerState = rememberPagerState { tabs.size }
var selectedTabIndex by remember { mutableStateOf(0) }
LaunchedEffect(pagerState.currentPage) {
    selectedTabIndex = pagerState.currentPage
}
LaunchedEffect(selectedTabIndex) {
    pagerState.animateScrollToPage(selectedTabIndex)
}
Surface {
    Column {
        TabRow(
            tabs = tabs,
            selectedTabIndex = selectedTabIndex,
            onTabSelected = { selectedTabIndex = it }
        )
        HorizontalPager(
            pagerState = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("页面内容 ${page + 1}")
            }
        }
    }
}
```
