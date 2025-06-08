# SuperCheckbox

`SuperCheckbox` 是 Miuix 中的复选框组件，提供了标题、摘要和复选框控件，支持点击交互，常用于多选项设置和选择列表中。

<div style="position: relative; max-width: 700px; height: 293px; border-radius: 10px; overflow: hidden; border: 1px solid #777;">
    <iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../../compose/index.html?id=superCheckbox" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>
</div>

## 引入

```kotlin
import top.yukonga.miuix.kmp.extra.SuperCheckbox
import top.yukonga.miuix.kmp.extra.CheckboxLocation
```

## 基本用法

SuperCheckbox 组件提供了基本的复选框功能：

```kotlin
var isChecked by remember { mutableStateOf(false) }

SuperCheckbox(
    title = "复选框选项",
    checked = isChecked,
    onCheckedChange = { isChecked = it }
)
```

## 带摘要的复选框

```kotlin
var notificationsEnabled by remember { mutableStateOf(false) }

SuperCheckbox(
    title = "通知",
    summary = "接收来自应用的推送通知",
    checked = notificationsEnabled,
    onCheckedChange = { notificationsEnabled = it }
)
```

## 组件状态

### 禁用状态

```kotlin
SuperCheckbox(
    title = "禁用复选框",
    summary = "此复选框当前不可用",
    checked = true,
    onCheckedChange = {},
    enabled = false
)
```

## 复选框位置

SuperCheckbox 支持将复选框放置在左侧或右侧：

### 左侧复选框（默认）

```kotlin
var leftChecked by remember { mutableStateOf(false) }

SuperCheckbox(
    title = "左侧复选框",
    summary = "复选框位于左侧（默认）",
    checked = leftChecked,
    onCheckedChange = { leftChecked = it },
    checkboxLocation = CheckboxLocation.Left // 默认值
)
```

### 右侧复选框

```kotlin
var rightChecked by remember { mutableStateOf(false) }

SuperCheckbox(
    title = "右侧复选框",
    summary = "复选框位于右侧",
    checked = rightChecked,
    onCheckedChange = { rightChecked = it },
    checkboxLocation = CheckboxLocation.Right
)
```

## 属性

### SuperCheckbox 属性

| 属性名           | 类型                            | 说明                       | 默认值                                | 是否必须 |
| ---------------- | ------------------------------- | -------------------------- | ------------------------------------- | -------- |
| title            | String                          | 复选框项的标题             | -                                     | 是       |
| checked          | Boolean                         | 复选框的选中状态           | -                                     | 是       |
| onCheckedChange  | ((Boolean) -> Unit)?            | 复选框状态变化时的回调     | -                                     | 是       |
| modifier         | Modifier                        | 应用于组件的修饰符         | Modifier                              | 否       |
| titleColor       | BasicComponentColors            | 标题文本的颜色配置         | BasicComponentDefaults.titleColor()   | 否       |
| summary          | String?                         | 复选框项的摘要说明         | null                                  | 否       |
| summaryColor     | BasicComponentColors            | 摘要文本的颜色配置         | BasicComponentDefaults.summaryColor() | 否       |
| checkboxColors   | CheckboxColors                  | 复选框控件的颜色配置       | CheckboxDefaults.checkboxColors()     | 否       |
| rightActions     | @Composable RowScope.() -> Unit | 右侧自定义内容（复选框前） | {}                                    | 否       |
| checkboxLocation | CheckboxLocation                | 复选框的位置               | CheckboxLocation.Left                 | 否       |
| onClick          | (() -> Unit)?                   | 点击选项时触发的额外回调   | null                                  | 否       |
| insideMargin     | PaddingValues                   | 组件内部内容的边距         | BasicComponentDefaults.InsideMargin   | 否       |
| enabled          | Boolean                         | 组件是否可交互             | true                                  | 否       |

## 进阶用法

### 带右侧额外内容

```kotlin
var backupEnabled by remember { mutableStateOf(false) }

SuperCheckbox(
    title = "自动备份",
    summary = "定期备份您的数据",
    checked = backupEnabled,
    onCheckedChange = { backupEnabled = it },
    rightActions = {
        Text(
            text = if (backupEnabled) "已启用" else "未启用",
            color = MiuixTheme.colorScheme.onSurfaceVariantActions,
            modifier = Modifier.padding(end = 6.dp)
        )
    }
)
```

### 嵌套复选框

```kotlin
var allSelected by remember { mutableStateOf(false) }
var option1 by remember { mutableStateOf(false) }
var option2 by remember { mutableStateOf(false) }
var option3 by remember { mutableStateOf(false) }

Column {
    SuperCheckbox(
        title = "选择全部",
        checked = allSelected,
        onCheckedChange = { newState ->
            allSelected = newState
            option1 = newState
            option2 = newState
            option3 = newState
        }
    )
    SuperCheckbox(
        title = "选项 1",
        checked = option1,
        onCheckedChange = { 
            option1 = it 
            allSelected = option1 && option2 && option3
        },
        modifier = Modifier.padding(start = 24.dp)
    )
    SuperCheckbox(
        title = "选项 2",
        checked = option2,
        onCheckedChange = { 
            option2 = it 
            allSelected = option1 && option2 && option3
        },
        modifier = Modifier.padding(start = 24.dp)
    )
    SuperCheckbox(
        title = "选项 3",
        checked = option3,
        onCheckedChange = { 
            option3 = it 
            allSelected = option1 && option2 && option3
        },
        modifier = Modifier.padding(start = 24.dp)
    )
}
```

### 自定义颜色

```kotlin
var customChecked by remember { mutableStateOf(false) }

SuperCheckbox(
    title = "自定义颜色",
    titleColor = BasicComponentDefaults.titleColor(
        color = MiuixTheme.colorScheme.primary
    ),
    summary = "使用自定义颜色的复选框",
    summaryColor = BasicComponentDefaults.summaryColor(
        color = MiuixTheme.colorScheme.secondary
    ),
    checked = customChecked,
    onCheckedChange = { customChecked = it },
    checkboxColors = CheckboxDefaults.checkboxColors(
        checkedForegroundColor = Color.Red,
        checkedBackgroundColor = MiuixTheme.colorScheme.secondary
    )
)
```

### 结合对话框使用

```kotlin
var showDialog = remember { mutableStateOf(false) }
var privacyOption by remember { mutableStateOf(false) }
var analyticsOption by remember { mutableStateOf(false) }

Scaffold {
    SuperArrow(
        title = "隐私设置",
        onClick = { showDialog.value = true },
        holdDownState = showDialog.value
    )
    
    SuperDialog(
        title = "隐私设置",
        show = showDialog,
        onDismissRequest = { showDialog.value = false } // 关闭对话框
    ) {
        Card {
            SuperCheckbox(
                title = "隐私政策",
                summary = "同意隐私政策条款",
                checked = privacyOption,
                onCheckedChange = { privacyOption = it }
            )
            
            SuperCheckbox(
                title = "分析数据",
                summary = "允许收集匿名使用数据以改进服务",
                checked = analyticsOption,
                onCheckedChange = { analyticsOption = it }
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
