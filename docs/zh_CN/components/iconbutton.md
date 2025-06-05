# IconButton

`IconButton` 是 Miuix 中的图标按钮组件，用于提供辅助操作的交互点。它们通常用于需要紧凑按钮的场景，如工具栏或图片列表中。

<div style="position: relative; max-width: 700px; height: 160px; border-radius: 10px; overflow: hidden; border: 1px solid #777;">
    <iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../../compose/index.html?id=iconButton" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>
</div>

## 引入

```kotlin
import top.yukonga.miuix.kmp.basic.IconButton
```

## 基本用法

IconButton 组件可以用于触发操作或事件：

```kotlin
IconButton(
    onClick = { /* 处理点击事件 */ }
) {
    Icon(
        imageVector = MiuixIcons.Useful.Like,
        contentDescription = "点赞"
    )
}
```

## 组件状态

### 禁用状态

```kotlin
IconButton(
    onClick = { /* 处理点击事件 */ },
    enabled = false
) {
    Icon(
        imageVector = MiuixIcons.Useful.Like,
        contentDescription = "点赞"
    )
}
```

### 按下状态

IconButton 支持通过 `holdDownState` 参数控制按下状态，通常用于显示弹出对话框时的视觉反馈：

```kotlin
var showDialog = remember { mutableStateOf(false) }

Scaffold {
    IconButton(
        onClick = { showDialog.value = true },
        holdDownState = showDialog.value
    ) {
        Icon(
            imageVector = MiuixIcons.Useful.Like,
            contentDescription = "点赞"
        )
    }
    // 在其他地方定义对话框
    SuperDialog(
        title = "对话框",
        show = showDialog,
        onDismissRequest = { showDialog.value = false } // 关闭对话框
    ) {
        // 对话框内容
    }
}
```

## 属性

### IconButton 属性

| 属性名          | 类型                   | 说明                  | 默认值                          | 是否必须 |
| --------------- | ---------------------- | --------------------- | ------------------------------- | -------- |
| onClick         | () -> Unit             | 点击按钮时触发的回调  | -                               | 是       |
| modifier        | Modifier               | 应用于按钮的修饰符    | Modifier                        | 否       |
| enabled         | Boolean                | 按钮是否可点击        | true                            | 否       |
| holdDownState   | Boolean                | 是否处于按下状态      | false                           | 否       |
| backgroundColor | Color                  | 按钮的背景颜色        | Color.Unspecified               | 否       |
| cornerRadius    | Dp                     | 按钮圆角半径          | IconButtonDefaults.CornerRadius | 否       |
| minHeight       | Dp                     | 按钮最小高度          | IconButtonDefaults.MinHeight    | 否       |
| minWidth        | Dp                     | 按钮最小宽度          | IconButtonDefaults.MinWidth     | 否       |
| content         | @Composable () -> Unit | 按钮内容，通常是 Icon | -                               | 是       |

### IconButtonDefaults 对象

IconButtonDefaults 对象提供了图标按钮组件的默认值。

#### 常量

| 常量名       | 类型 | 说明           | 默认值 |
| ------------ | ---- | -------------- | ------ |
| MinWidth     | Dp   | 按钮的最小宽度 | 40.dp  |
| MinHeight    | Dp   | 按钮的最小高度 | 40.dp  |
| CornerRadius | Dp   | 按钮的圆角半径 | 40.dp  |

## 进阶用法

### 自定义背景颜色

```kotlin
IconButton(
    onClick = { /* 处理点击事件 */ },
    backgroundColor = Color.LightGray.copy(alpha = 0.3f)
) {
    Icon(
        imageVector = MiuixIcons.Useful.Like,
        contentDescription = "点赞"
    )
}
```

### 自定义大小和圆角

```kotlin
IconButton(
    onClick = { /* 处理点击事件 */ },
    minWidth = 48.dp,
    minHeight = 48.dp,
    cornerRadius = 12.dp
) {
    Icon(
        imageVector = MiuixIcons.Useful.Like,
        contentDescription = "点赞"
    )
}
```

### 结合其他组件使用

```kotlin
Surface {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = { /* 处理点击事件 */ }
        ) {
            Icon(
                imageVector = MiuixIcons.Useful.New,
                tint = MiuixTheme.colorScheme.onBackground,
                contentDescription = "添加"
            )
        }
        Text("添加新项目")
    }
}
```

### 动态图标按钮

```kotlin
var isLiked by remember { mutableStateOf(false) }

IconButton(
    onClick = { isLiked = !isLiked }
) {
    Icon(
        imageVector = if (isLiked) MiuixIcons.Useful.Like else MiuixIcons.Useful.Unlike,
        contentDescription = if (isLiked) "已点赞" else "点赞",
        tint = if (isLiked) Color.Red else MiuixTheme.colorScheme.onBackground
    )
}
```

