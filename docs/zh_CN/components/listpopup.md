# ListPopup

`ListPopup` 是 Miuix 中的弹出列表组件，用于显示一个包含多个选项的弹出菜单。它提供了一个轻量级的、悬浮在界面上的临时列表，适用于各种下拉菜单、上下文菜单等场景。

<div style="position: relative; max-width: 700px; height: 250px; border-radius: 10px; overflow: hidden; border: 1px solid #777;">
    <iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../../compose/index.html?id=listPopup" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>
</div>

::: warning 注意
`ListPopup` 需要在 `Scaffold` 组件内使用！
:::

## 引入

```kotlin
import top.yukonga.miuix.kmp.basic.ListPopup
import top.yukonga.miuix.kmp.basic.ListPopupColumn
```

## 基本用法

ListPopup 组件可以用于创建简单的下拉菜单：

```kotlin
val showPopup = remember { mutableStateOf(false) }
var selectedIndex by remember { mutableStateOf(0) }
val items = listOf("选项 1", "选项 2", "选项 3")

Scaffold {
    Box {
        TextButton(
            text = "点击显示菜单",
            onClick = { showPopup.value = true }
        )
        ListPopup(
            show = showPopup,
            alignment = PopupPositionProvider.Align.Left,
            onDismissRequest = { showPopup.value = false } // 关闭弹出菜单
        ) {
            ListPopupColumn {
                items.forEachIndexed { index, string ->
                    DropdownImpl(
                        text = string,
                        optionSize = items.size,
                        isSelected = selectedIndex == index,
                        onSelectedIndexChange = {
                            selectedIndex = index
                            showPopup.value = false // 关闭弹出菜单
                        },
                        index = index
                    )
                }
            }
        }
    }
}
```

## 组件状态

### 不同位置对齐

ListPopup 可以设置不同的对齐方式：

```kotlin
var showPopup = remember { mutableStateOf(false) }

ListPopup(
    show = showPopup,
    onDismissRequest = { showPopup.value = false } // 关闭弹出菜单
    alignment = PopupPositionProvider.Align.Left
) {
    ListPopupColumn {
        // 自定义内容
    }
}
```


### 禁用窗口变暗效果

```kotlin
var showPopup = remember { mutableStateOf(false) }

ListPopup(
    show = showPopup,
    onDismissRequest = { showPopup.value = false } // 关闭弹出菜单
    enableWindowDim = false // 禁用遮罩层
) {
    ListPopupColumn {
        // 自定义内容
    }
}
```

## 属性

### ListPopup 属性

| 属性名                | 类型                        | 说明                           | 默认值                                     | 是否必须 |
| --------------------- | --------------------------- | ------------------------------ | ------------------------------------------ | -------- |
| show                  | MutableState\<Boolean>      | 控制弹出列表显示状态的状态对象 | -                                          | 是       |
| popupModifier         | Modifier                    | 应用于弹出列表的修饰符         | Modifier                                   | 否       |
| popupPositionProvider | PopupPositionProvider       | 弹出列表的位置提供者           | ListPopupDefaults.DropdownPositionProvider | 否       |
| alignment             | PopupPositionProvider.Align | 弹出列表的对齐方式             | PopupPositionProvider.Align.Right          | 否       |
| enableWindowDim       | Boolean                     | 是否启用遮罩层                 | true                                       | 否       |
| shadowElevation       | Dp                          | 弹出列表的阴影高度             | 11.dp                                      | 否       |
| onDismissRequest      | (() -> Unit)?               | 弹出列表消失时的回调函数       | null                                       | 否       |
| maxHeight             | Dp?                         | 弹出列表的最大高度             | null (自动计算)                            | 否       |
| minWidth              | Dp                          | 弹出列表的最小宽度             | 200.dp                                     | 否       |
| content               | @Composable () -> Unit      | 弹出列表的内容                 | -                                          | 是       |

### ListPopupDefaults 对象

ListPopupDefaults 对象提供了 ListPopup 组件的默认设置。

#### 属性

| 属性名                      | 类型                  | 说明                           |
| --------------------------- | --------------------- | ------------------------------ |
| DropdownPositionProvider    | PopupPositionProvider | 用于下拉菜单场景的位置提供者   |
| ContextMenuPositionProvider | PopupPositionProvider | 用于上下文菜单场景的位置提供者 |

### PopupPositionProvider 接口

PopupPositionProvider 接口定义了计算弹出列表位置的方法。

#### PopupPositionProvider.Align 选项

| 选项名      | 说明                 |
| ----------- | -------------------- |
| Left        | 相对于窗口左侧对齐   |
| Right       | 相对于窗口右侧对齐   |
| TopLeft     | 相对于窗口左上角对齐 |
| TopRight    | 相对于窗口右上角对齐 |
| BottomLeft  | 相对于窗口左下角对齐 |
| BottomRight | 相对于窗口右下角对齐 |

## 进阶用法

### 自定义内容布局

```kotlin
var showPopup = remember { mutableStateOf(false) }

Scaffold {
    Box {
        TextButton(
            text = "点击显示菜单",
            onClick = { showPopup.value = true }
        )
        ListPopup(
            show = showPopup,
            minWidth = 250.dp,
            maxHeight = 300.dp,
            onDismissRequest = { showPopup.value = false } // 关闭弹出菜单
            alignment = PopupPositionProvider.Align.BottomRight,
        ) {
            ListPopupColumn {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "自定义标题",
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    HorizontalDivider()
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("这是一个自定义内容的弹出菜单，您可以根据需要添加各种组件。")
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(
                            text = "确定",
                            onClick = { showPopup.value = false } // 关闭弹出菜单
                        )
                    }
                }
            }
        }
    }
}
```
