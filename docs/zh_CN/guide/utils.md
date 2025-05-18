# 工具函数

Miuix 提供了一系列工具函数，帮助您更高效地开发应用程序。以下是主要工具函数的详细介绍和使用示例。

## 弹出窗口工具 (MiuixPopupUtils)

`MiuixPopupUtils` 是一个用于显示对话框布局和弹出窗口布局的工具类。该类已经集成到 `Scaffold` 组件中，可以直接使用。

如果你使用多个 Scaffold，则需要将下属 `Scaffold` 中的 `popupHost` 参数设为 `null`。

### 对话框布局

```kotlin
// 需要一个 MutableState<Boolean> 来控制显示状态
val showDialogState = remember { mutableStateOf(false) }

DialogLayout(
    visible = showDialogState,          // 控制对话框显示状态
    enterTransition = fadeIn(),         // 可选，自定义对话框进入动画
    exitTransition = fadeOut(),         // 可选，自定义对话框对话框退出动画
    enableWindowDim = true,             // 可选，是否启用遮罩层
    dimEnterTransition = fadeIn(),      // 可选，自定义遮罩层进入动画
    dimExitTransition = fadeOut()       // 可选，自定义遮罩层退出动画
) {
    // 对话框内容
}
```

正常情况下无需主动使用。详见 [SuperDialog](../components/superdialog.md) 文档。

### 弹出窗口布局

```kotlin
// 需要一个 MutableState<Boolean> 来控制显示状态
val showPopupState = remember { mutableStateOf(false) }

PopupLayout(
    visible = showPopupState,           // 控制弹出窗口显示状态
    enterTransition = fadeIn(),         // 可选，自定义对话框进入动画
    exitTransition = fadeOut(),         // 可选，自定义对话框对话框退出动画
    enableWindowDim = true,             // 可选，是否启用遮罩层
    dimEnterTransition = fadeIn(),      // 可选，自定义遮罩层进入动画
    dimExitTransition = fadeOut(),      // 可选，自定义遮罩层退出动画
    transformOrigin = { TransformOrigin.Center }, // 弹出窗口的起始位置
) {
    // 弹出窗口内容
}
```

正常情况下无需主动使用。详见 [ListPopup](../components/listpopup.md) 文档。

## 越界回弹效果 (Overscroll)

Miuix 提供了易于使用的越界回弹效果修饰符，让滚动体验更加流畅自然。

### 垂直越界回弹

```kotlin
LazyColumn(
    modifier = Modifier
        // 添加越界回弹效果
        .overScrollVertical()
        // 如需绑定 TopAppBar 滚动事件，则应在越界回弹效果之后添加
        .nestedScroll(scrollBehavior.nestedScrollConnection)
) {
    // 列表内容
}
```

### 水平越界回弹

```kotlin
LazyRow(
    modifier = Modifier.overScrollHorizontal()
) {
    // 列表内容
}
```

### 自定义越界回弹参数

您可以自定义越界回弹效果的参数，以满足特定的需求。

```kotlin
LazyColumn(
    modifier = Modifier.overScrollVertical(
        nestedScrollToParent = true, // 是否将嵌套滚动事件分发给父级，默认为 true
        scrollEasing = { currentOffset, newOffset -> // 自定义滚动缓动函数
            parabolaScrollEasing(currentOffset, newOffset, p = 25f, density = LocalDensity.current.density)
        },
        springStiff = 200f, // 回弹动画的弹簧刚度，默认为 200f
        springDamp = 1f,  // 回弹动画的弹簧阻尼，默认为 1f
        isEnabled = { platform() == Platform.Android || platform() == Platform.IOS } // 是否启用越界回弹效果，默认在 Android 和 iOS 上启用
    ), 
    overscrollEffect = null // 建议将此参数设置为 null，禁用默认效果
) {
    // 列表内容
}
```

**参数说明:**

*   `nestedScrollToParent`: 布尔值，控制是否将嵌套滚动事件（如父级滚动容器）传递给父级。默认为 `true`。
*   `scrollEasing`: 一个函数，用于定义滚动超出边界时的缓动效果。它接收当前偏移量 (`currentOffset`) 和新的偏移量增量 (`newOffset`) 作为参数，并返回计算后的新偏移量。默认使用 `parabolaScrollEasing`，提供类似 iOS 的阻尼效果。
*   `springStiff`: 浮点数，定义回弹动画的弹簧刚度。值越高，回弹越快越硬。默认为 `200f`。
*   `springDamp`: 浮点数，定义回弹动画的弹簧阻尼。值越高，振荡越小。默认为 `1f`。
*   `isEnabled`: 一个返回布尔值的 Lambda 表达式，用于动态控制是否启用越界回弹效果。默认情况下，仅在 Android 和 iOS 平台上启用。

## 滚动到边界触觉反馈

Miuix 提供了用于在可滚动容器快速滑动到其开始或结束边界时触发触觉反馈的修饰符，通过触觉反馈确认已到达边界增强用户的交互体验。

```kotlin
LazyColumn(
    modifier = Modifier
        .fillMaxSize()
        // 添加滚动到边界触觉反馈
        .scrollEndHaptic(
            hapticFeedbackType = HapticFeedbackType.TextHandleMove // 默认值
        )
) {
    // 列表内容
}
```

**参数说明:**

*   `hapticFeedbackType`: 指定滚动到达末端时要执行的触觉反馈类型。默认为 `HapticFeedbackType.TextHandleMove`。您可以使用 `androidx.compose.ui.hapticfeedback.HapticFeedbackType` 中可用的其他类型。

## 按压反馈效果 (PressFeedback)

Miuix 提供了视觉反馈效果来增强用户交互体验，通过类似触觉的响应提升操作感。

### 下沉效果

`pressSink` 修饰符会在组件被按下时应用一种“下沉”视觉效果，通过平滑缩放组件实现。

```kotlin
val interactionSource = remember { MutableInteractionSource() }

Box(
    modifier = Modifier
        .clickable(interactionSource = interactionSource, indication = null)
        .pressSink(interactionSource)
        .background(Color.Blue)
        .size(100.dp)
)
```

### 倾斜效果

`pressTilt` 修饰符会根据用户按压组件的位置应用一种“倾斜”效果。倾斜方向由触摸偏移决定，使一角“下沉”而另一角保持“静止”。

```kotlin
val interactionSource = remember { MutableInteractionSource() }

Box(
    modifier = Modifier
        .clickable(interactionSource = interactionSource, indication = null)
        .pressTilt(interactionSource)
        .background(Color.Green)
        .size(100.dp)
)
```

### 触发按压反馈效果的前提

按压反馈效果需要检测 `interactionSource.collectIsPressedAsState()` 以触发。

可以使用 `Modifier.clickable()` 等响应式修饰符来为 `interactionSource` 添加 `PressInteraction.Press` 以触发按压反馈效果。

但更推荐使用下面的方法来为 `interactionSource` 添加 `PressInteraction.Press` 以获得更快响应的触发按压反馈效果。

```kotlin
val interactionSource = remember { MutableInteractionSource() }

Box(
    modifier = Modifier
        .pointerInput(Unit) {
            awaitEachGesture {
                val pressInteraction: PressInteraction.Press
                awaitFirstDown().also {
                    pressInteraction = PressInteraction.Press(it.position)
                    interactionSource.tryEmit(pressInteraction)
                }
                if (waitForUpOrCancellation() == null) {
                    interactionSource.tryEmit(PressInteraction.Cancel(pressInteraction))
                } else {
                    interactionSource.tryEmit(PressInteraction.Release(pressInteraction))
                }
            }
        }
)
```

### 按压反馈类型 (PressFeedbackType)

`PressFeedbackType` 枚举定义了组件被按下时可以应用的不同类型的视觉反馈。

| 类型 | 说明                                   |
| ---- | -------------------------------------- |
| None | 无视觉反馈                             |
| Sink | 应用下沉效果，组件在按下时轻微缩小     |
| Tilt | 应用倾斜效果，组件根据触摸位置轻微倾斜 |

## 平滑圆角 (SmoothRoundedCornerShape)

`SmoothRoundedCornerShape` 提供了比标准 `RoundedCornerShape` 更加平滑的圆角效果。

### 基本使用

```kotlin
Surface(
    shape = SmoothRoundedCornerShape(16.dp)
) {
    // 内容
}
```

### 自定义平滑程度和不同角度

```kotlin
Surface(
    shape = SmoothRoundedCornerShape(
        smoothing = 0.8f, // 平滑度，值越高越平滑
        topStart = 16.dp,
        topEnd = 16.dp,
        bottomStart = 8.dp,
        bottomEnd = 8.dp
    )
) {
    // 内容
}
```
