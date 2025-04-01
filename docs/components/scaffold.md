# Scaffold

`Scaffold` 是 Miuix 中的脚手架组件，用于实现基本的 MIUIX 设计视觉布局结构。它提供了应用程序界面的基本框架，包括顶部栏、底部栏、悬浮按钮等元素的容器。

## 引入

```kotlin
import top.yukonga.miuix.kmp.basic.Scaffold
```

## 基本用法

Scaffold 组件可以构建带有顶栏的页面布局：

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

| 属性名                       | 类型                                | 默认值                            | 说明                                         |
| ---------------------------- | ----------------------------------- | --------------------------------- | -------------------------------------------- |
| modifier                     | Modifier                            | Modifier                          | 应用于脚手架的修饰符                         |
| topBar                       | @Composable () -> Unit              | {}                                | 顶部栏，通常是 TopAppBar                     |
| bottomBar                    | @Composable () -> Unit              | {}                                | 底部栏，通常是 NavigationBar                 |
| floatingActionButton         | @Composable () -> Unit              | {}                                | 悬浮按钮                                     |
| floatingActionButtonPosition | MiuixFabPosition                    | MiuixFabPosition.End              | 显示悬浮按钮的位置                           |
| snackbarHost                 | @Composable () -> Unit              | {}                                | 用于显示 Snackbar 的容器，Miuix 不提供此组件 |
| popupHost                    | @Composable () -> Unit              | \{ MiuixPopupHost() }             | 用于显示弹出窗口的容器                       |
| containerColor               | Color                               | MiuixTheme.colorScheme.background | 脚手架的背景颜色                             |
| contentWindowInsets          | WindowInsets                        | WindowInsets.statusBars           | 传递给内容的窗口插入边距                     |
| content                      | @Composable (PaddingValues) -> Unit | -                                 | 脚手架的主要内容区域                         |

### MiuixFabPosition 选项

| 选项名     | 说明                                       |
| ---------- | ------------------------------------------ |
| Start      | 将悬浮按钮放置在屏幕底部左侧，在底栏上方   |
| Center     | 将悬浮按钮放置在屏幕底部中央，在底栏上方   |
| End        | 将悬浮按钮放置在屏幕底部右侧，在底栏上方   |
| EndOverlay | 将悬浮按钮放置在屏幕底部右侧，覆盖在底栏上 |

## 进阶用法

### 带有顶栏和底栏的页面布局

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
        // 绑定顶栏的滚动行为
        LazyColumn(
            contentPadding = paddingValues,
            topAppBarScrollBehavior = topAppBarScrollBehavior,
            modifier = Modifier.fillMaxSize()
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
    floatingActionButtonPosition = MiuixFabPosition.End,
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
