# SuperArrow

`SuperArrow` 是 Miuix 中的箭头指示组件，通常用于导航或展示更多内容。提供了标题、摘要和右侧箭头图标，支持点击交互，常用于设置项、菜单项或列表项中。

<div style="position: relative; max-width: 700px; height: 280px; border-radius: 10px; overflow: hidden; border: 1px solid #777;">
    <iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../../compose/index.html?id=superArrow" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>
</div>

## 引入

```kotlin
import top.yukonga.miuix.kmp.extra.SuperArrow
```

## 基本用法

SuperArrow 组件提供了基本的点击导航功能：

```kotlin
SuperArrow(
    title = "设置项",
    onClick = { /* 处理点击事件 */ }
)
```

## 带摘要的箭头

```kotlin
SuperArrow(
    title = "无线网络",
    summary = "已连接到 WIFI-HOME",
    onClick = { /* 处理点击事件 */ }
)
```

## 组件状态

### 禁用状态

```kotlin
SuperArrow(
    title = "禁用项",
    summary = "此项目当前不可用",
    enabled = false,
    onClick = { /* 不会被触发 */ }
)
```

### 按下状态

SuperArrow 支持通过 `holdDownState` 参数控制按下状态，通常用于显示弹出对话框时的视觉反馈：

```kotlin
val showDialog = remember { mutableStateOf(false) }

Scaffold {
    SuperArrow(
        title = "打开对话框",
        summary = "点击显示对话框",
        onClick = { showDialog.value = true },
        holdDownState = showDialog.value
    )
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

### SuperArrow 属性

| 属性名           | 类型                      | 说明                     | 默认值                                 | 是否必须 |
| ---------------- | ------------------------- | ------------------------ | -------------------------------------- | -------- |
| title            | String                    | 箭头项的标题             | -                                      | 是       |
| titleColor       | BasicComponentColors      | 标题文本的颜色配置       | BasicComponentDefaults.titleColor()    | 否       |
| summary          | String?                   | 箭头项的摘要说明         | null                                   | 否       |
| summaryColor     | BasicComponentColors      | 摘要文本的颜色配置       | BasicComponentDefaults.summaryColor()  | 否       |
| leftAction       | @Composable (() -> Unit)? | 左侧自定义内容           | null                                   | 否       |
| rightText        | String?                   | 右侧文本内容             | null                                   | 否       |
| rightActionColor | RightActionColors         | 右侧文本和箭头的颜色配置 | SuperArrowDefaults.rightActionColors() | 否       |
| modifier         | Modifier                  | 应用于组件的修饰符       | Modifier                               | 否       |
| insideMargin     | PaddingValues             | 组件内部内容的边距       | BasicComponentDefaults.InsideMargin    | 否       |
| onClick          | (() -> Unit)?             | 点击箭头时触发的回调     | null                                   | 否       |
| holdDownState    | Boolean                   | 组件是否处于按下状态     | false                                  | 否       |
| enabled          | Boolean                   | 组件是否可交互           | true                                   | 否       |

### SuperArrowDefaults 对象

SuperArrowDefaults 对象提供了箭头组件的默认值和颜色配置。

#### 方法

| 方法名            | 类型              | 说明                         |
| ----------------- | ----------------- | ---------------------------- |
| rightActionColors | RightActionColors | 创建右侧文本和箭头的颜色配置 |

### RightActionColors 类

| 参数          | 类型  | 说明             |
| ------------- | ----- | ---------------- |
| color         | Color | 正常状态下的颜色 |
| disabledColor | Color | 禁用状态下的颜色 |

## 进阶用法

### 带左侧图标

```kotlin
SuperArrow(
    title = "个人信息",
    summary = "查看和修改您的个人资料",
    leftAction = {
        Icon(
            imageVector = MiuixIcons.Useful.Personal,
            contentDescription = "个人图标",
            tint = MiuixTheme.colorScheme.onBackground,
            modifier = Modifier.padding(end = 16.dp)
        )
    },
    onClick = { /* 处理点击事件 */ }
)
```

### 带右侧文本

```kotlin
SuperArrow(
    title = "存储空间",
    summary = "管理应用存储空间",
    rightText = "12.5 GB",
    onClick = { /* 处理点击事件 */ }
)
```

### 结合对话框使用

```kotlin
val showDialog = remember { mutableStateOf(false) }
var language by remember { mutableStateOf("简体中文") }

Scaffold {
    SuperArrow(
        title = "语言设置",
        summary = "选择应用显示语言",
        rightText = language,
        onClick = { showDialog.value = true },
        holdDownState = showDialog.value
    )
    SuperDialog(
        title = "选择语言",
        show = showDialog,
        onDismissRequest = { showDialog.value = false } // 关闭对话框
    ) {
        // 对话框内容
        Card {
            SuperArrow(
                title = "简体中文",
                onClick = {
                    language = "简体中文"
                    showDialog.value = false // 关闭对话框
                }
            )
            SuperArrow(
                title = "English",
                onClick = {
                    language = "English"
                    showDialog.value = false // 关闭对话框
                }
            )
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextButton(
                text = "取消",
                onClick = { showDialog.value = false },  // 关闭对话框
                modifier = Modifier.weight(1f).padding(top = 8.dp)
            )
        }
    }
}
```

### 自定义颜色

```kotlin
SuperArrow(
    title = "自定义颜色",
    titleColor = BasicComponentDefaults.titleColor(
        color = MiuixTheme.colorScheme.primary
    ),
    summary = "使用自定义颜色",
    summaryColor = BasicComponentDefaults.summaryColor(
        color = MiuixTheme.colorScheme.secondary
    ),
    rightActionColor = RightActionColors(
        color = MiuixTheme.colorScheme.primary,
        disabledColor = MiuixTheme.colorScheme.disabledOnSecondaryVariant
    ),
    onClick = { /* 处理点击事件 */ }
)
```
