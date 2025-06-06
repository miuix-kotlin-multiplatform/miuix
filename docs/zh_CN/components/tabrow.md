# TabRow

`TabRow` 是 Miuix 中的导航组件，用于创建可横向滚动的标签页。提供了标准样式和带轮廓（Contour）样式两种变体，适用于内容分类和导航场景。

<div style="position: relative; max-width: 700px; height: 180px; border-radius: 10px; overflow: hidden; border: 1px solid #777;">
    <iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../../compose/index.html?id=tabRow" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>
</div>

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

| 属性名           | 类型             | 说明                 | 默认值                            | 是否必须 |
| ---------------- | ---------------- | -------------------- | --------------------------------- | -------- |
| tabs             | List\<String>    | 标签文本列表         | -                                 | 是       |
| selectedTabIndex | Int              | 当前选中的标签索引   | -                                 | 是       |
| modifier         | Modifier         | 应用于标签行的修饰符 | Modifier                          | 否       |
| colors           | TabRowColors     | 标签行的颜色配置     | TabRowDefaults.tabRowColors()     | 否       |
| minWidth         | Dp               | 每个标签的最小宽度   | TabRowDefaults.TabRowMinWidth     | 否       |
| maxWidth         | Dp               | 每个标签的最大宽度   | TabRowDefaults.TabRowMaxWidth     | 否       |
| height           | Dp               | 标签行的高度         | TabRowDefaults.TabRowHeight       | 否       |
| cornerRadius     | Dp               | 标签的圆角半径       | TabRowDefaults.TabRowCornerRadius | 否       |
| onTabSelected    | ((Int) -> Unit)? | 标签选中时的回调函数 | null                              | 否       |

### TabRowWithContour 属性

| 属性名           | 类型             | 说明                 | 默认值                                       | 是否必须 |
| ---------------- | ---------------- | -------------------- | -------------------------------------------- | -------- |
| tabs             | List\<String>    | 标签文本列表         | -                                            | 是       |
| selectedTabIndex | Int              | 当前选中的标签索引   | -                                            | 是       |
| modifier         | Modifier         | 应用于标签行的修饰符 | Modifier                                     | 否       |
| colors           | TabRowColors     | 标签行的颜色配置     | TabRowDefaults.tabRowColors()                | 否       |
| minWidth         | Dp               | 每个标签的最小宽度   | TabRowDefaults.TabRowWithContourMinWidth     | 否       |
| maxWidth         | Dp               | 每个标签的最大宽度   | TabRowDefaults.TabRowWithContourMaxWidth     | 否       |
| height           | Dp               | 标签行的高度         | TabRowDefaults.TabRowHeight                  | 否       |
| cornerRadius     | Dp               | 标签的圆角半径       | TabRowDefaults.TabRowWithContourCornerRadius | 否       |
| onTabSelected    | ((Int) -> Unit)? | 标签选中时的回调函数 | null                                         | 否       |

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
| TabRowMaxWidth                | Dp   | 98.dp | 标准样式的每个标签最大宽度   |
| TabRowWithContourMaxWidth     | Dp   | 84.dp | 带轮廓样式的每个标签最大宽度 |

#### 方法

| 方法名         | 类型         | 说明                     |
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
