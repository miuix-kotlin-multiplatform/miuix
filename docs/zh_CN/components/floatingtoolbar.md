# FloatingToolbar

`FloatingToolbar` 是 Miuix 中的悬浮工具栏组件，它将其内容渲染在一个具有圆角背景的容器中，可以水平或垂直排列。实际的屏幕放置由父组件（通常是 `Scaffold`）处理。

此组件通常与 `Scaffold` 结合使用，放置在页面的特定位置，提供快捷操作或信息展示。

<div style="position: relative; max-width: 700px; height: 300px; border-radius: 10px; overflow: hidden; border: 1px solid #777;">
    <iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../../compose/index.html?id=floatingToolbar" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>
</div>

## 引入

```kotlin
import top.yukonga.miuix.kmp.basic.FloatingToolbar
import top.yukonga.miuix.kmp.basic.FloatingToolbarDefaults
import top.yukonga.miuix.kmp.basic.ToolbarPosition // 用于 Scaffold
```

## 基本用法

```kotlin
Scaffold(
    floatingToolbar = {
        FloatingToolbar {
            Row(
                modifier = Modifier.padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) { // 或 Column
                IconButton(onClick = { /* 操作 1 */ }) {
                    Icon(MiuixIcons.Useful.Edit, contentDescription = "编辑")
                }
                IconButton(onClick = { /* 操作 2 */ }) {
                    Icon(MiuixIcons.Useful.Delete, contentDescription = "删除")
                }
            }
        }
    },
    floatingToolbarPosition = ToolbarPosition.BottomCenter // 设置位置
)
```

## 属性

### FloatingToolbar 属性

| 属性名                     | 类型                   | 说明                       | 默认值                                 | 是否必须 |
| -------------------------- | ---------------------- | -------------------------- | -------------------------------------- | -------- |
| modifier                   | Modifier               | 应用于工具栏的修饰符       | Modifier                               | 否       |
| color                      | Color                  | 工具栏背景颜色             | FloatingToolbarDefaults.DefaultColor() | 否       |
| cornerRadius               | Dp                     | 工具栏圆角半径             | FloatingToolbarDefaults.CornerRadius   | 否       |
| outSidePadding             | PaddingValues          | 工具栏外部的内边距         | FloatingToolbarDefaults.OutSidePadding | 否       |
| shadowElevation            | Dp                     | 工具栏的阴影高度           | 4.dp                                   | 否       |
| showDivider                | Boolean                | 是否显示工具栏周围的分割线 | false                                  | 否       |
| defaultWindowInsetsPadding | Boolean                | 是否应用默认窗口嵌入边距   | true                                   | 否       |
| content                    | @Composable () -> Unit | 工具栏内容区域的可组合函数 | -                                      | 是       |

### FloatingToolbarDefaults 对象

| 属性名         | 类型                    | 说明           | 值                                      |
| -------------- | ----------------------- | -------------- | --------------------------------------- |
| CornerRadius   | Dp                      | 默认圆角半径   | 50.dp                                   |
| DefaultColor() | @Composable () -> Color | 默认背景颜色   | MiuixTheme.colorScheme.surfaceContainer |
| OutSidePadding | PaddingValues           | 默认外部内边距 | PaddingValues(12.dp, 8.dp)              |

### ToolbarPosition (用于 Scaffold)

请参考 [Scaffold](../components/scaffold#toolbarposition-选项) 文档中的 `ToolbarPosition` 选项。

## 进阶用法

### 自定义样式

```kotlin
FloatingToolbar(
    color = MiuixTheme.colorScheme.primaryContainer,
    cornerRadius = 16.dp,
    outSidePadding = PaddingValues(24.dp),
    showDivider = false
) {
    Row(
        modifier = Modifier.padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) { // 或 Column
        IconButton(onClick = { /* 操作 1 */ }) {
            Icon(MiuixIcons.Useful.Edit, contentDescription = "编辑", tint = MiuixTheme.colorScheme.onPrimaryContainer)
        }
        IconButton(onClick = { /* 操作 2 */ }) {
            Icon(MiuixIcons.Useful.Delete, contentDescription = "删除", tint = MiuixTheme.colorScheme.onPrimaryContainer)
        }
    }
}
```

### 垂直排列内容

```kotlin
FloatingToolbar {
    Column(
        modifier = Modifier.padding(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        IconButton(onClick = { /* 操作 1 */ }) {
            Icon(MiuixIcons.Useful.Edit, contentDescription = "编辑")
        }
        IconButton(onClick = { /* 操作 2 */ }) {
            Icon(MiuixIcons.Useful.Delete, contentDescription = "删除")
        }
    }
}
```

### 结合 Scaffold 改变位置

```kotlin
Scaffold(
    floatingToolbar = {
        FloatingToolbar {
            // 工具栏内容
        }
    },
    floatingToolbarPosition = ToolbarPosition.CenterEnd // 放置在右侧居中
)
```

### 处理窗口边距

```kotlin
FloatingToolbar(
    defaultWindowInsetsPadding = false // 自行处理窗口嵌入边距
) {
    // 工具栏内容
}
```
