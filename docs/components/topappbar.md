# TopAppBar

`TopAppBar` 是 Miuix 中的顶部应用栏组件，用于在界面顶部提供导航、标题和操作按钮。支持大标题模式和普通模式，以及滚动时的动态效果。

此组件常常与 `Scaffold` 组件结合使用，以便在应用程序的不同页面中保持一致的布局和行为。

## 引入

```kotlin
import top.yukonga.miuix.kmp.basic.TopAppBar
import top.yukonga.miuix.kmp.basic.SmallTopAppBar
import top.yukonga.miuix.kmp.basic.MiuixScrollBehavior
import top.yukonga.miuix.kmp.basic.rememberTopAppBarState
```

## 基本用法

### 小标题顶部栏

```kotlin
SmallTopAppBar(
    title = "标题",
    navigationIcon = {
        IconButton(onClick = { /* 处理点击事件 */ }) {
            Icon(MiuixIcons.Useful.Back, contentDescription = "返回")
        }
    },
    actions = {
        IconButton(onClick = { /* 处理点击事件 */ }) {
            Icon(MiuixIcons.Useful.More, contentDescription = "更多")
        }
    }
)
```

### 大标题顶部栏

```kotlin
TopAppBar(
    title = "标题",
    largeTitle = "大标题", // 如果不指定，将使用 title 的值
    navigationIcon = {
        IconButton(onClick = { /* 处理点击事件 */ }) {
            Icon(MiuixIcons.Base.ArrowLeft, contentDescription = "返回")
        }
    },
    actions = {
        IconButton(onClick = { /* 处理点击事件 */ }) {
            Icon(MiuixIcons.Useful.More, contentDescription = "更多")
        }
    }
)
```

## 滚动行为

TopAppBar 支持随内容滚动时改变其显示状态：

```kotlin
val scrollBehavior = MiuixScrollBehavior()
Scaffold(
    topBar = {
        TopAppBar(
            title = "标题",
            largeTitle = "大标题",
            scrollBehavior = scrollBehavior
        )
    }
) { paddingValues ->
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        contentPadding = PaddingValues(top = paddingValues.calculateTopPadding()),
        topAppBarScrollBehavior = scrollBehavior
    ) {
        // 列表内容
    }
}
```

## 自定义样式

### 自定义颜色

```kotlin
TopAppBar(
    title = "标题",
    color = MiuixTheme.colorScheme.primary
)
```

### 自定义内容边距

```kotlin
TopAppBar(
    title = "标题",
    horizontalPadding = 32.dp
)
```

## 属性

### TopAppBar 属性

| 属性名                     | 类型                            | 默认值                            | 说明                           |
| -------------------------- | ------------------------------- | --------------------------------- | ------------------------------ |
| title                      | String                          | -                                 | 顶部栏标题                     |
| modifier                   | Modifier                        | Modifier                          | 应用于顶部栏的修饰符           |
| color                      | Color                           | MiuixTheme.colorScheme.background | 顶部栏背景颜色                 |
| largeTitle                 | String?                         | null                              | 大标题文本，不指定时使用 title |
| navigationIcon             | @Composable () -> Unit          | {}                                | 导航图标区域的可组合函数       |
| actions                    | @Composable RowScope.() -> Unit | {}                                | 操作按钮区域的可组合函数       |
| scrollBehavior             | ScrollBehavior?                 | null                              | 控制顶部栏滚动行为             |
| defaultWindowInsetsPadding | Boolean                         | true                              | 是否应用默认窗口边距           |
| horizontalPadding          | Dp                              | 26.dp                             | 水平内容边距                   |

### SmallTopAppBar 属性

| 属性名                     | 类型                            | 默认值                            | 说明                     |
| -------------------------- | ------------------------------- | --------------------------------- | ------------------------ |
| title                      | String                          | -                                 | 顶部栏标题               |
| modifier                   | Modifier                        | Modifier                          | 应用于顶部栏的修饰符     |
| color                      | Color                           | MiuixTheme.colorScheme.background | 顶部栏背景颜色           |
| navigationIcon             | @Composable () -> Unit          | {}                                | 导航图标区域的可组合函数 |
| actions                    | @Composable RowScope.() -> Unit | {}                                | 操作按钮区域的可组合函数 |
| scrollBehavior             | ScrollBehavior?                 | null                              | 控制顶部栏滚动行为       |
| defaultWindowInsetsPadding | Boolean                         | true                              | 是否应用默认窗口边距     |
| horizontalPadding          | Dp                              | 26.dp                             | 水平内容边距             |

### ScrollBehavior

MiuixScrollBehavior 是用于控制顶部栏滚动行为的配置对象。

#### rememberTopAppBarState

用于创建和记住 TopAppBarState：

```kotlin
val scrollBehavior = MiuixScrollBehavior(
    state = rememberTopAppBarState(),
    snapAnimationSpec = spring(stiffness = 3000f),
    canScroll = { true }
)
```

| 参数名             | 类型                        | 默认值                     | 说明                       |
| ------------------ | --------------------------- | -------------------------- | -------------------------- |
| state              | TopAppBarState              | rememberTopAppBarState()   | 控制滚动状态的状态对象     |
| canScroll          | () -> Boolean               | { true }                   | 控制是否允许滚动的回调     |
| snapAnimationSpec  | AnimationSpec\<Float>?      | spring(stiffness = 3000f)  | 定义顶部栏滚动后的吸附动画 |
| flingAnimationSpec | DecayAnimationSpec\<Float>? | rememberSplineBasedDecay() | 定义顶部栏滑动的衰减动画   |
