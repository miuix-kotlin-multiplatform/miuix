# Surface

`Surface` 是 Miuix 中的基础容器组件，用于为应用内容提供一致的背景和边框效果，为界面元素提供统一的视觉基础。支持一些简单的自定义样式。

<div style="position: relative; max-width: 700px; height: 300px; border-radius: 10px; overflow: hidden; border: 1px solid #777;">
    <iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../../compose/index.html?id=surface" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>
</div>

## 引入

```kotlin
import top.yukonga.miuix.kmp.basic.Surface
```

## 基本用法

Surface 组件可以包裹其他内容，为它们提供背景、边框和阴影效果：

```kotlin
Surface(
    modifier = Modifier
        .size(200.dp)
        .padding(16.dp),
    color = MiuixTheme.colorScheme.background,
    shape = RoundedCornerShape(16.dp),
    shadowElevation = 4.dp
) {
    Text(
        text = "Surface 示例",
        modifier = Modifier.padding(16.dp)
    )
}
```

## 属性

### 基础 Surface 属性

| 属性名          | 类型                   | 说明                         | 默认值                            | 是否必须 |
| --------------- | ---------------------- | ---------------------------- | --------------------------------- | -------- |
| modifier        | Modifier               | 应用于 Surface 的修饰符      | Modifier                          | 否       |
| shape           | Shape                  | Surface 的形状               | RectangleShape                    | 否       |
| color           | Color                  | Surface 的背景颜色           | MiuixTheme.colorScheme.background | 否       |
| border          | BorderStroke?          | Surface 的边框样式           | null                              | 否       |
| shadowElevation | Dp                     | Surface 的阴影高度           | 0.dp                              | 否       |
| content         | @Composable () -> Unit | Surface 内容区域的可组合函数 | -                                 | 是       |

### 可点击 Surface 额外属性

| 属性名  | 类型       | 说明                      | 默认值 | 是否必须 |
| ------- | ---------- | ------------------------- | ------ | -------- |
| onClick | () -> Unit | 点击 Surface 时触发的回调 | -      | 是       |
| enabled | Boolean    | Surface 是否可点击        | true   | 否       |

## 进阶用法

### 可点击 Surface

创建可交互的 Surface，用于响应用户点击：

```kotlin
Surface(
    onClick = { /* 处理点击事件 */ },
    modifier = Modifier.size(200.dp).padding(16.dp),
    shape = RoundedCornerShape(16.dp),
    color = MiuixTheme.colorScheme.primaryContainer,
    shadowElevation = 4.dp
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "可点击，类似按钮",
            modifier = Modifier.padding(16.dp).fillMaxSize(),
            textAlign = TextAlign.Center,
        )
    }
}
```

### 自定义样式

使用不同形状、颜色和边框创建自定义样式：

```kotlin
Surface(
    modifier = Modifier.size(200.dp).padding(16.dp),
    shape = CircleShape,
    color = MiuixTheme.colorScheme.secondaryContainer,
    border = BorderStroke(2.dp, MiuixTheme.colorScheme.secondary),
    shadowElevation = 8.dp
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "圆形 Surface",
            modifier = Modifier.padding(16.dp),
            textAlign = TextAlign.Center,
        )
    }
}
```

### 组合使用

将 Surface 与其他组件结合使用，创建卡片式布局：

```kotlin
Surface(
    modifier = Modifier.fillMaxWidth().padding(16.dp),
    shape = RoundedCornerShape(16.dp),
    color = MiuixTheme.colorScheme.surface,
    border = BorderStroke(1.dp, MiuixTheme.colorScheme.outline.copy(alpha = 0.2f)),
    shadowElevation = 4.dp
) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = "卡片标题",
            style = MiuixTheme.textStyles.headline1
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "这是卡片内容区域，可以放置各种组件和信息。Surface 组件提供了统一的视觉容器。",
            style = MiuixTheme.textStyles.body1
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { /* 处理点击事件 */ }
        ) {
            Text("操作按钮")
        }
    }
}
```

### 禁用状态

创建可禁用状态的可点击 Surface：

```kotlin
var isEnabled by remember { mutableStateOf(false) }

Surface(
    onClick = { /* 处理点击事件 */ },
    enabled = isEnabled,
    modifier = Modifier.size(200.dp).padding(16.dp),
    shape = RoundedCornerShape(16.dp),
    color = MiuixTheme.colorScheme.surface.copy(alpha = 0.6f),
    shadowElevation = 1.dp
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "禁用点击状态",
            modifier = Modifier.padding(16.dp),
            textAlign = TextAlign.Center,
        )
    }
}
```
