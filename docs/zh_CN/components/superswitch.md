# SuperSwitch

`SuperSwitch` 是 Miuix 中的开关组件，提供了标题、摘要和右侧开关控件，支持点击交互，常用于设置项和偏好切换中。

<div style="position: relative; max-width: 700px; height: 231px; border-radius: 10px; overflow: hidden; border: 1px solid #777;">
    <iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../../compose/index.html?id=superSwitch" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>
</div>

## 引入

```kotlin
import top.yukonga.miuix.kmp.extra.SuperSwitch
```

## 基本用法

SuperSwitch 组件提供了基本的开关功能：

```kotlin
var isChecked by remember { mutableStateOf(false) }

SuperSwitch(
    title = "开关选项",
    checked = isChecked,
    onCheckedChange = { isChecked = it }
)
```

## 带摘要的开关

```kotlin
var wifiEnabled by remember { mutableStateOf(false) }

SuperSwitch(
    title = "WiFi",
    summary = "打开以连接到无线网络",
    checked = wifiEnabled,
    onCheckedChange = { wifiEnabled = it }
)
```

## 组件状态

### 禁用状态

```kotlin
SuperSwitch(
    title = "禁用开关",
    summary = "此开关当前不可用",
    checked = true,
    onCheckedChange = {},
    enabled = false
)
```

## 属性

### SuperSwitch 属性

| 属性名          | 类型                            | 说明                       | 默认值                                | 是否必须 |
| --------------- | ------------------------------- | -------------------------- | ------------------------------------- | -------- |
| checked         | Boolean                         | 开关的选中状态             | -                                     | 是       |
| onCheckedChange | ((Boolean) -> Unit)?            | 开关状态变化时的回调       | -                                     | 是       |
| title           | String                          | 开关项的标题               | -                                     | 是       |
| titleColor      | BasicComponentColors            | 标题文本的颜色配置         | BasicComponentDefaults.titleColor()   | 否       |
| summary         | String?                         | 开关项的摘要说明           | null                                  | 否       |
| summaryColor    | BasicComponentColors            | 摘要文本的颜色配置         | BasicComponentDefaults.summaryColor() | 否       |
| leftAction      | @Composable (() -> Unit)?       | 左侧自定义内容             | null                                  | 否       |
| rightActions    | @Composable RowScope.() -> Unit | 右侧自定义内容（开关前）   | {}                                    | 否       |
| switchColors    | SwitchColors                    | 开关控件的颜色配置         | SwitchDefaults.switchColors()         | 否       |
| modifier        | Modifier                        | 应用于组件的修饰符         | Modifier                              | 否       |
| insideMargin    | PaddingValues                   | 组件内部内容的边距         | BasicComponentDefaults.InsideMargin   | 否       |
| onClick         | (() -> Unit)?                   | 点击开关项时触发的额外回调 | null                                  | 否       |
| holdDownState   | Boolean                         | 组件是否处于按下状态       | false                                 | 否       |
| enabled         | Boolean                         | 组件是否可交互             | true                                  | 否       |

## 进阶用法

### 带左侧图标

```kotlin
var enabled by remember { mutableStateOf(false) }

SuperSwitch(
    title = "测试",
    summary = "启用以允许测试",
    checked = enabled,
    onCheckedChange = { enabled = it },
    leftAction = {
        Icon(
            imageVector = MiuixIcons.Useful.Order,
            contentDescription = "命令图标",
            tint = MiuixTheme.colorScheme.onBackground,
            modifier = Modifier.padding(end = 12.dp)
        )
    }
)
```

### 带右侧额外内容

```kotlin
var locationEnabled by remember { mutableStateOf(false) }

SuperSwitch(
    title = "位置服务",
    summary = "允许应用获取位置信息",
    checked = locationEnabled,
    onCheckedChange = { locationEnabled = it },
    rightActions = {
        Text(
            text = if (locationEnabled) "已开启" else "已关闭",
            color = MiuixTheme.colorScheme.onSurfaceVariantActions,
            modifier = Modifier.padding(end = 6.dp)
        )
    }
)
```

### 带动画显示/隐藏的内容

```kotlin
var parentEnabled by remember { mutableStateOf(false) }
var childEnabled by remember { mutableStateOf(false) }
Column {
    SuperSwitch(
        title = "主设置",
        summary = "打开以显示更多选项",
        checked = parentEnabled,
        onCheckedChange = { parentEnabled = it }
    )
    AnimatedVisibility(
        visible = parentEnabled
    ) {
        SuperSwitch(
            title = "子设置",
            summary = "仅当主设置开启时可用",
            checked = childEnabled,
            onCheckedChange = { childEnabled = it }
        )
    }
}
```

### 自定义颜色

```kotlin
var customEnabled by remember { mutableStateOf(false) }

SuperSwitch(
    title = "自定义颜色",
    titleColor = BasicComponentDefaults.titleColor(
        color = MiuixTheme.colorScheme.primary
    ),
    summary = "使用自定义颜色的开关",
    summaryColor = BasicComponentDefaults.summaryColor(
        color = MiuixTheme.colorScheme.secondary
    ),
    checked = customEnabled,
    onCheckedChange = { customEnabled = it },
    switchColors = SwitchDefaults.switchColors(
        checkedThumbColor = MiuixTheme.colorScheme.primary,
        checkedTrackColor = MiuixTheme.colorScheme.primary.copy(alpha = 0.2f)
    )
)
```

### 结合对话框使用

```kotlin
var showDialog = remember { mutableStateOf(false) }
var option by remember { mutableStateOf(false) }

Scaffold {
    SuperArrow(
        title = "高级设置",
        onClick = { showDialog.value = true },
        holdDownState = showDialog.value
    )
    SuperDialog(
        title = "高级设置",
        show = showDialog,
        onDismissRequest = { showDialog.value = false }
    ) {
        Card {
            SuperSwitch(
                title = "实验性功能",
                summary = "启用尚在开发中的功能",
                checked = option,
                onCheckedChange = { option = it }
            )
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(top = 12.dp)
        ) {
            TextButton(
                text = "取消",
                onClick = { showDialog.value = false }, // 关闭对话框
                modifier = Modifier.weight(1f)
            )
            Spacer(Modifier.width(16.dp))
            TextButton(
                text = "确认",
                onClick = { showDialog.value = false }, // 关闭对话框
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.textButtonColorsPrimary() // 使用主题颜色
            )
        }
    }
}
```
