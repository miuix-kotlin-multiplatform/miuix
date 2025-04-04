# 多平台支持

Miuix 是一个支持多种平台的 Compose Multiplatform UI 框架，允许您使用相同的代码库在不同平台上构建应用程序。

## 支持的平台

目前，Miuix 支持以下平台：

- **Android**：适用于 Android 移动设备
- **iOS**：适用于 iPhone 和 iPad 设备
- **Desktop(JVM)**：适用于基于 JVM 的桌面应用
- **WasmJs**：适用于 WebAssembly(Web) 环境
- **MacOS**：适用于 macOS 原生应用
- **Js**：适用于 JavaScript(Web) 环境

## 平台检测与适配

您可以使用 `platform()` 函数检测当前运行的平台，并据此调整 UI 或功能：

```kotlin
when (platform()) {
    Platform.Android -> {
        // Android 特定代码
    }
    Platform.IOS -> {
        // iOS 特定代码
    }
    Platform.Desktop -> {
        // JVM 特定代码
    }
    Platform.WasmJs -> {
        // WebAssembly 特定代码
    }
    Platform.MacOS -> {
        // macOS 特定代码
    }
    Platform.Js -> {
        // JavaScript 特定代码
    }
}
```

## 窗口尺寸管理

Miuix 提供了跨平台的窗口尺寸获取功能：

```kotlin
@Composable
fun MyResponsiveContent() {
    val windowSize = getWindowSize()
    val width = windowSize.width
    val height = windowSize.height
    // 根据窗口尺寸调整 UI 布局
    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {
        if (width > 600) {
            Text("宽屏布局")
        } else {
            Text("窄屏布局")
        }
        Text("\n高度：$height")
    }
}
```

## 设备特性获取

### 设备圆角

安卓设备的屏幕圆角不同且其他平台不存在屏幕圆角获取，您可以使用 `getRoundedCorner()` 函数获取设备的圆角大小（不存在时使用预设值）：

```kotlin
@Composable
fun AdaptiveRoundedComponent() {
    val cornerRadius = getRoundedCorner()
    Surface(
        shape = RoundedCornerShape(cornerRadius),
        // 其他属性
    ) {
        // 内容
    }
}
```

## 平台特定行为处理

### 返回操作处理

Compose MultiPlatform 没有提供完整的跨平台 BackHandler 接口，因此 Miuix 提供了统一的 `BackHandler` 接口：

```kotlin
@Composable
fun Screen() {
    // 处理返回操作
    BackHandler(enabled = true) {
        // 返回操作的处理逻辑
        // 例如：导航到前一个屏幕或关闭对话框
    }
}
```

## 最佳实践

1. **预先适配检查**：在开发过程中定期在各目标平台上测试您的应用
2. **响应式设计**：可以使用 `getWindowSize()` 创建适应不同屏幕尺寸的布局
3. **条件渲染**：根据 `platform()` 的结果有条件地渲染组件或功能
4. **平台特定扩展**：为特定平台创建扩展函数，使代码更整洁

```kotlin
@Composable
fun PlatformSpecificFeature() {
    when (platform()) {
        Platform.Android, Platform.IOS -> MobileView()
        Platform.Desktop, Platform.MacOS -> DesktopView()
        Platform.WasmJs, Platform.Js -> WebView()
    }
}
```

## 平台特定依赖

为了优化应用体积，您可以只引入特定平台所需的依赖。请参考[快速开始](/guide/getting-started)部分了解如何添加特定平台的依赖。
