# Scaffold

`Scaffold` 是 Miuix 中的脚手架组件，用于实现基本设计视觉布局结构。它提供了应用程序界面的基本框架，包括顶部栏、底部栏、悬浮按钮等元素的容器。

<div style="position: relative; max-width: 700px; height: 350px; border-radius: 10px; overflow: hidden; border: 1px solid #777;">
    <iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../../compose/index.html?id=scaffold" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>
</div>

::: warning 注意
Scaffold 组件为跨平台提供了一个合适的弹出窗口的容器。`SuperDialog`、`SuperDropdown`、`SuperSpinner`、`ListPopup` 等组件都基于此实现弹出窗口，因此都需要被该组件包裹。
:::

::: info 信息
为什么不用官方的 `Popup` 和 `Dialog` 而选择自行创建弹出窗口的容器？因为它们目前在跨平台支持中实现不完整，有些参数无法更改。
:::

## 引入

```kotlin
import top.yukonga.miuix.kmp.basic.Scaffold
```

## 基本用法

Scaffold 组件可以构建带有顶部栏的页面布局：

```kotlin
Scaffold(
    topBar = {
        SmallTopAppBar(title = "标题" )
    },
    content = { paddingValues ->
        // 内容区域需要考虑 padding
        Box(
            modifier = Modifier
                .padding(top = paddingValues.calculateTopPadding(), start = 26.dp)
                .fillMaxSize()
        ) {
            Text("内容区域")
        }
    }
)
```

## 属性

### Scaffold 属性

| 属性名                       | 类型                                | 说明                                         | 默认值                            | 是否必须 |
| ---------------------------- | ----------------------------------- | -------------------------------------------- | --------------------------------- | -------- |
| modifier                     | Modifier                            | 应用于脚手架的修饰符                         | Modifier                          | 否       |
| topBar                       | @Composable () -> Unit              | 顶部栏，通常是 TopAppBar                     | {}                                | 否       |
| bottomBar                    | @Composable () -> Unit              | 底部栏，通常是 NavigationBar                 | {}                                | 否       |
| floatingActionButton         | @Composable () -> Unit              | 悬浮按钮                                     | {}                                | 否       |
| floatingActionButtonPosition | FabPosition                         | 显示悬浮按钮的位置                           | FabPosition.End                   | 否       |
| floatingToolbar              | @Composable () -> Unit              | 悬浮工具栏                                   | {}                                | 否       |
| floatingToolbarPosition      | ToolbarPosition                     | 显示悬浮工具栏的位置                         | ToolbarPosition.BottomCenter      | 否       |
| snackbarHost                 | @Composable () -> Unit              | 用于显示 Snackbar 的容器，Miuix 不提供此组件 | {}                                | 否       |
| popupHost                    | @Composable () -> Unit              | 用于显示弹出窗口的容器                       | \{ MiuixPopupHost() }             | 否       |
| containerColor               | Color                               | 脚手架的背景颜色                             | MiuixTheme.colorScheme.background | 否       |
| contentWindowInsets          | WindowInsets                        | 传递给内容的窗口插入边距                     | WindowInsets.statusBars           | 否       |
| content                      | @Composable (PaddingValues) -> Unit | 脚手架的主要内容区域                         | -                                 | 是       |

### FabPosition 选项

| 选项名     | 说明                                         |
| ---------- | -------------------------------------------- |
| Start      | 将悬浮按钮放置在屏幕底部左侧，在底部栏上方   |
| Center     | 将悬浮按钮放置在屏幕底部中央，在底部栏上方   |
| End        | 将悬浮按钮放置在屏幕底部右侧，在底部栏上方   |
| EndOverlay | 将悬浮按钮放置在屏幕底部右侧，覆盖在底部栏上 |

### ToolbarPosition 选项

| 选项名       | 说明                               |
| ------------ | ---------------------------------- |
| TopStart     | 将悬浮工具栏放置在屏幕顶部左侧     |
| CenterStart  | 将悬浮工具栏放置在屏幕左侧垂直居中 |
| BottomStart  | 将悬浮工具栏放置在屏幕底部左侧     |
| TopEnd       | 将悬浮工具栏放置在屏幕顶部右侧     |
| CenterEnd    | 将悬浮工具栏放置在屏幕右侧垂直居中 |
| BottomEnd    | 将悬浮工具栏放置在屏幕底部右侧     |
| TopCenter    | 将悬浮工具栏放置在屏幕顶部水平居中 |
| BottomCenter | 将悬浮工具栏放置在屏幕底部水平居中 |

## 进阶用法

### 带有顶部栏和底部栏的页面布局

```kotlin
val topAppBarScrollBehavior = MiuixScrollBehavior(rememberTopAppBarState())

Scaffold(
    topBar = {
        TopAppBar(
            title = "标题",
            navigationIcon = {
                IconButton(onClick = { /* 处理导航点击 */ }) {
                    Icon(MiuixIcons.Useful.Back, contentDescription = "返回")
                }
            },
            scrollBehavior = topAppBarScrollBehavior
        )
    },
    bottomBar = {
        val items = listOf(
            NavigationItem("首页", MiuixIcons.Useful.NavigatorSwitch),
            NavigationItem("设置", MiuixIcons.Useful.Settings)
        )
        var selectedItem by remember { mutableStateOf(0) }
        NavigationBar(
            items = items,
            selected = selectedItem,
            onClick = { index ->
                selectedItem = index
            },
        )
    },
    content = { paddingValues ->
        // 内容区域需要考虑 padding
        LazyColumn(
            contentPadding = paddingValues,
            modifier = Modifier
                .fillMaxSize()
                // 绑定顶部栏的滚动行为
                .nestedScroll(topAppBarScrollBehavior.nestedScrollConnection)
        ) {
            items(20) { index ->
                SuperArrow(
                    title = "Item $index",
                    onClick = { /* 处理点击 */ }
                )
                if (index < 19) HorizontalDivider()
            }
        }
    }
)
```

### 带有悬浮按钮的页面布局

```kotlin
Scaffold(
    topBar = {
        SmallTopAppBar(title = "标题")
    },
    floatingActionButton = {
        FloatingActionButton(
            onClick = { /* 处理点击事件 */ }
        ) {
            Icon(MiuixIcons.Useful.New, contentDescription = "添加")
        }
    },
    floatingActionButtonPosition = FabPosition.End,
    content = { paddingValues ->
        // 内容区域需要考虑 padding
        Box(
            modifier = Modifier
                .padding(top = paddingValues.calculateTopPadding(), start = 26.dp)
                .fillMaxSize()
        ) {
               Text("点击右下角按钮添加内容")
        }
    }
)
```

### 带有 Snackbar 的页面布局（需要使用 Material 组件）

```kotlin
val snackbarHostState = remember { SnackbarHostState() }
val scope = rememberCoroutineScope()

Scaffold(
    snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
    topBar = {
        SmallTopAppBar(title = "标题")
    },
    floatingActionButton = {
        FloatingActionButton(
            onClick = {
                scope.launch {
                    snackbarHostState.showSnackbar("这是一条消息！")
                }
            }
        ) {
            Icon(MiuixIcons.Useful.Info, contentDescription = "显示消息")
        }
    },
    content = { paddingValues ->
        // 内容区域需要考虑 padding
        Box(
            modifier = Modifier
                .padding(top = paddingValues.calculateTopPadding(), start = 26.dp)
                .fillMaxSize()
        ) {
            Text("点击按钮显示 Snackbar")
        }
    }
)
```

### 自定义窗口插入边距

```kotlin
Scaffold(
    contentWindowInsets = WindowInsets.systemBars,
    topBar = {
        SmallTopAppBar(title = "标题")
    },
    content = { paddingValues ->
        // 内容区域需要考虑 padding
        Box(
            modifier = Modifier
                .padding(top = paddingValues.calculateTopPadding(), start = 26.dp)
                .fillMaxSize()
        ) {
            Text("考虑系统栏的内容区域")
        }
    }
)
```
