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
    modifier = Modifier.overScrollVertical()
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

```kotlin
LazyColumn(
    modifier = Modifier.overScrollVertical(
        nestedScrollToParent = true,
        springStiff = 150f, // 弹性系数
        springDamp = 0.8f,  // 阻尼系数
        isEnabled = { true } // 是否启用
    )
) {
    // 列表内容
}
```

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
