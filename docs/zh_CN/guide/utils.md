# 工具函数

Miuix 提供了一系列实用的工具函数，帮助您更高效地开发应用。以下是主要工具函数的详细介绍与使用示例。

## 弹窗工具 (MiuixPopupUtils)

`MiuixPopupUtils` 是一个用于显示弹窗和对话框的工具类。该类已经集成到 `Scaffold` 组件中，可以直接使用。

### 显示对话框

```kotlin
showDialog {
    // 对话框内容
}
```
详见 [SuperDialog](../components/superdialog.md) 文档。

### 关闭对话框

```kotlin
dismissDialog(showDialog) 
```

详见 [SuperDialog](../components/superdialog.md) 文档。

### 显示弹窗

```kotlin
showPopup(
    transformOrigin = { TransformOrigin(0.5f, 0f) }, // 从顶部中间展开
    windowDimming = true // 背景变暗
) {
    // 弹窗内容
}
```

详见 [ListPopup](../components/listpopup.md) 文档。

### 关闭弹窗

```kotlin
dismissPopup(showPopup) 
```

详见 [ListPopup](../components/listpopup.md) 文档。

## 越界回弹效果 (Overscroll)

Miuix 提供了易于使用的越界回弹效果，让滚动体验更加流畅自然。

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
