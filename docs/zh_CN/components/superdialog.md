# SuperDialog

`SuperDialog` 是 Miuix 中的对话框组件，用于显示重要信息、收集用户输入或确认用户操作。对话框会在当前界面上层显示，并支持自定义样式和内容布局。

<div style="position: relative; max-width: 700px; height: 210px; border-radius: 10px; overflow: hidden; border: 1px solid #777;">
    <iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../../compose/index.html?id=superDialog" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>
</div>

::: warning 注意
`SuperDialog` 需要在 `Scaffold` 组件内使用！
:::

## 引入

```kotlin
import top.yukonga.miuix.kmp.extra.SuperDialog
```

## 基本用法

SuperDialog 组件提供了基础的对话框功能：

```kotlin
var showDialog = remember { mutableStateOf(false) }

Scaffold {
    TextButton(
        text = "显示对话框",
        onClick = { showDialog.value = true }
    )

    SuperDialog(
        title = "对话框标题",
        summary = "这是一个基本的对话框示例，可以包含各种内容。",
        show = showDialog,
        onDismissRequest = { showDialog.value = false } // 关闭对话框
    ) {
        TextButton(
            text = "确定",
            onClick = { showDialog.value = false }, // 关闭对话框
            modifier = Modifier.fillMaxWidth()
        )
    }
}
```

## 属性

### SuperDialog 属性

| 属性名                     | 类型                   | 说明                         | 默认值                                | 是否必须 |
| -------------------------- | ---------------------- | ---------------------------- | ------------------------------------- | -------- |
| show                       | MutableState\<Boolean> | 控制对话框显示状态的状态对象 | -                                     | 是       |
| modifier                   | Modifier               | 应用于对话框的修饰符         | Modifier                              | 否       |
| title                      | String?                | 对话框的标题                 | null                                  | 否       |
| titleColor                 | Color                  | 标题文本的颜色               | SuperDialogDefaults.titleColor()      | 否       |
| summary                    | String?                | 对话框的摘要说明             | null                                  | 否       |
| summaryColor               | Color                  | 摘要文本的颜色               | SuperDialogDefaults.summaryColor()    | 否       |
| backgroundColor            | Color                  | 对话框背景色                 | SuperDialogDefaults.backgroundColor() | 否       |
| enableWindowDim            | Boolean                | 是否启用遮罩层               | true                                  | 否       |
| onDismissRequest           | (() -> Unit)?          | 对话框关闭时的回调函数       | null                                  | 否       |
| outsideMargin              | DpSize                 | 对话框外部边距               | SuperDialogDefaults.outsideMargin     | 否       |
| insideMargin               | DpSize                 | 对话框内部内容的边距         | SuperDialogDefaults.insideMargin      | 否       |
| defaultWindowInsetsPadding | Boolean                | 是否应用默认窗口插入内边距   | true                                  | 否       |
| content                    | @Composable () -> Unit | 对话框的内容                 | -                                     | 是       |

### SuperDialogDefaults 对象

SuperDialogDefaults 对象提供了 SuperDialog 组件的默认设置。

#### 属性

| 属性名        | 类型   | 说明               |
| ------------- | ------ | ------------------ |
| outsideMargin | DpSize | 对话框外部默认边距 |
| insideMargin  | DpSize | 对话框内部默认边距 |

#### 函数

| 函数名            | 返回类型 | 说明                   |
| ----------------- | -------- | ---------------------- |
| titleColor()      | Color    | 获取默认标题颜色       |
| summaryColor()    | Color    | 获取默认摘要颜色       |
| backgroundColor() | Color    | 获取默认对话框背景颜色 |

## 进阶用法

### 自定义样式对话框

```kotlin
var showDialog = remember { mutableStateOf(false) }

Scaffold {
    TextButton(
        text = "显示自定义样式对话框",
        onClick = { showDialog.value = true }
    )

    SuperDialog(
        title = "自定义样式",
        summary = "这个对话框使用了自定义颜色和边距",
        show = showDialog,
        onDismissRequest = { showDialog.value = false }, // 关闭对话框
        titleColor = Color.Blue,
        summaryColor = Color.Gray,
        backgroundColor = Color(0xFFF5F5F5),
        outsideMargin = DpSize(20.dp, 20.dp),
        insideMargin = DpSize(30.dp, 30.dp)
    ) {
        Text(
            text = "自定义内容区域",
            modifier = Modifier.padding(vertical = 16.dp)
        )
        
        TextButton(
            text = "关闭",
            onClick = { showDialog.value = false }, // 关闭对话框
            modifier = Modifier.fillMaxWidth()
        )
    }
}
```

### 创建确认对话框

```kotlin
var showConfirmDialog = remember { mutableStateOf(false) }
var result by remember { mutableStateOf("") }

Scaffold {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        TextButton(
            text = "显示确认对话框",
            onClick = { showConfirmDialog.value = true }
        )
        
        Text("结果: $result")
    }
    
    SuperDialog(
        title = "确认操作",
        summary = "此操作不可撤销，是否继续？",
        show = showConfirmDialog,
        onDismissRequest = { showConfirmDialog.value = false } // 关闭对话框
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextButton(
                text = "取消",
                onClick = { 
                    result = "用户取消了操作"
                    showConfirmDialog.value = false // 关闭对话框
                },
                modifier = Modifier.weight(1f)
            )
            Spacer(Modifier.width(20.dp))
            TextButton(
                text = "确认",
                onClick = { 
                    result = "用户确认了操作"
                    showConfirmDialog.value = false // 关闭对话框
                },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.textButtonColorsPrimary() // 使用主题颜色
            )
        }
    }
}
```

### 带有输入框的对话框

```kotlin
var showDialog = remember { mutableStateOf(false) }
var textFieldValue by remember { mutableStateOf("") }

Scaffold {
    TextButton(
        text = "显示输入对话框",
        onClick = { showDialog.value = true }
    )

    SuperDialog(
        title = "请输入内容",
        show = showDialog,
        onDismissRequest = { showDialog.value = false } // 关闭对话框
    ) {
        TextField(
            modifier = Modifier.padding(bottom = 16.dp),
            value = textFieldValue,
            maxLines = 1,
            onValueChange = { textFieldValue = it }
        )
        
        Row(
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextButton(
                text = "取消",
                onClick = { showDialog.value = false }, // 关闭对话框
                modifier = Modifier.weight(1f)
            )
            Spacer(Modifier.width(20.dp))
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

### 带有表单的对话框

```kotlin
var showDialog = remember { mutableStateOf(false) }
var dropdownSelectedOption by remember { mutableStateOf(0) }
var switchState by remember { mutableStateOf(false) }
val dropdownOptions = listOf("选项 1", "选项 2")

Scaffold {
    TextButton(
        text = "显示表单对话框",
        onClick = { showDialog.value = true }
    )

    SuperDialog(
        title = "表单对话框",
        show = showDialog,
        onDismissRequest = { showDialog.value = false } // 关闭对话框
    ) {
        Card(
            color = MiuixTheme.colorScheme.secondaryContainer,
        ) {
            SuperDropdown(
                title = "下拉选择",
                items = dropdownOptions,
                selectedIndex = dropdownSelectedOption,
                onSelectedIndexChange = { dropdownSelectedOption = it }
            )
            
            SuperSwitch(
                title = "开关选项",
                checked = switchState,
                onCheckedChange = { switchState = it }
            )
        }
        
        Spacer(Modifier.height(12.dp))
        
        Row(
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextButton(
                text = "取消",
                onClick = { showDialog.value = false }, // 关闭对话框
                modifier = Modifier.weight(1f)
            )
            Spacer(Modifier.width(20.dp))
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

### 带有颜色选择器的对话框

```kotlin
var showColorDialog = remember { mutableStateOf(false) }
var selectedColor by remember { mutableStateOf(Color.Red) }

Scaffold {
    TextButton(
        text = "选择颜色",
        onClick = { showColorDialog.value = true }
    )
    
    SuperDialog(
        title = "选择颜色",
        show = showColorDialog,
        onDismissRequest = { showColorDialog.value = false } // 关闭对话框
    ) {
        Column {
            ColorPicker(
                initialColor = selectedColor,
                onColorChanged = { selectedColor = it }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                TextButton(
                    modifier = Modifier.weight(1f),
                    text = "取消",
                    onClick = { showColorDialog.value = false } // 关闭对话框
                )
                TextButton(
                    modifier = Modifier.weight(1f),
                    text = "确认",
                    colors = ButtonDefaults.textButtonColorsPrimary(), // 使用主题颜色
                    onClick = {
                        showColorDialog.value = false // 关闭对话框
                        // 处理确认逻辑
                    }
                )
            }
        }
    }
}
```
