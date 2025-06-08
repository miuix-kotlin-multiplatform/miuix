# FloatingActionButton

`FloatingActionButton` 是 Miuix 中的悬浮按钮组件，通常用于展示页面中最重要或最常用的操作。它通常悬浮在界面上方，具有突出的视觉效果，便于用户快速访问。

此组件通常与 `Scaffold` 组件结合使用，以便在应用程序的不同页面中保持一致的布局和行为。

<div style="position: relative; max-width: 700px; height: 300px; border-radius: 10px; overflow: hidden; border: 1px solid #777;">
    <iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../../compose/index.html?id=floatingActionButton" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>
</div>

## 引入

```kotlin
import top.yukonga.miuix.kmp.basic.FloatingActionButton
```

## 基本用法

FloatingActionButton 组件可以用于触发重要的操作：

```kotlin
FloatingActionButton(
    onClick = { /* 处理点击事件 */ }
) {
    Icon(
        imageVector = MiuixIcons.Useful.New,
        contentDescription = "添加"
    )
}
```

## 组件状态

由于本组件通常用于最常用的操作，因此组件本身没有内置状态变化。

## 属性

### FloatingActionButton 属性

| 属性名                     | 类型                   | 说明                     | 默认值                         | 是否必须 |
| -------------------------- | ---------------------- | ------------------------ | ------------------------------ | -------- |
| onClick                    | () -> Unit             | 点击按钮时触发的回调     | -                              | 是       |
| modifier                   | Modifier               | 应用于按钮的修饰符       | Modifier                       | 否       |
| shape                      | Shape                  | 按钮的形状               | CircleShape                    | 否       |
| containerColor             | Color                  | 按钮的背景颜色           | MiuixTheme.colorScheme.primary | 否       |
| shadowElevation            | Dp                     | 按钮的阴影高度           | 4.dp                           | 否       |
| minWidth                   | Dp                     | 按钮的最小宽度           | 60.dp                          | 否       |
| minHeight                  | Dp                     | 按钮的最小高度           | 60.dp                          | 否       |
| defaultWindowInsetsPadding | Boolean                | 是否应用默认窗口插入填充 | true                           | 否       |
| content                    | @Composable () -> Unit | 按钮内容区域的可组合函数 | -                              | 是       |

## 进阶用法

### 自定义颜色

```kotlin
FloatingActionButton(
    onClick = { /* 处理点击事件 */ },
    containerColor = Color.Red
) {
    Icon(
        imageVector = MiuixIcons.Useful.Like,
        contentDescription = "喜欢",
        tint = Color.White
    )
}
```

### 扩展的悬浮按钮

```kotlin
FloatingActionButton(
    onClick = { /* 处理点击事件 */ },
    shape = RoundedCornerShape(16.dp),
    minWidth = 120.dp
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Icon(
            imageVector = MiuixIcons.Useful.New,
            contentDescription = "添加",
            tint = Color.White
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text("添加", color = Color.White)
    }
}
```

### 绑定到脚手架的悬浮按钮

```kotlin
Scaffold(
    floatingActionButton = {
        FloatingActionButton(
            onClick = { /* 处理点击事件 */ }
        ) {
            Icon(
                imageVector = MiuixIcons.Useful.Add,
                contentDescription = "添加"
            )
        }
    },
    floatingActionButtonPosition = FabPosition.End
) { paddingValues ->
    // 内容区域需要考虑 padding
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
        ) {
            // ...
    }
}
```

### 带动画效果的悬浮按钮

```kotlin
var expanded by remember { mutableStateOf(false) }
val animatedSize by animateDpAsState(
    targetValue = if (expanded) 65.dp else 60.dp,
    label = "FAB 尺寸动画",
)

FloatingActionButton(
    onClick = { expanded = !expanded },
    minWidth = animatedSize,
    minHeight = animatedSize
) {
    Icon(
        imageVector = if (expanded) MiuixIcons.Useful.Remove else MiuixIcons.Useful.New,
        contentDescription = if (expanded) "移除" else "添加",
        tint = Color.White
    )
}
```
