# CheckBox

`CheckBox` 是 Miuix 中的基础选择组件，用于在选中与未选中状态间切换。它提供了具有动画效果的交互式选择控件，适用于多选场景和配置项的启用与禁用。

<div style="position: relative; max-width: 700px; height: 100px; border-radius: 10px; overflow: hidden; border: 1px solid #777;">
    <iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../../compose/index.html?id=checkbox" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>
</div>

## 引入

```kotlin
import top.yukonga.miuix.kmp.basic.Checkbox
```

## 基本用法

CheckBox 组件可以用于创建可选择的选项：

```kotlin
var checked by remember { mutableStateOf(false) }

Checkbox(
    checked = checked,
    onCheckedChange = { checked = it }
)
```

## 组件状态

### 禁用状态

```kotlin
var checked by remember { mutableStateOf(false) }

Checkbox(
    checked = checked,
    onCheckedChange = { checked = it },
    enabled = false
)
```

## 属性

### Checkbox 属性

| 属性名          | 类型                 | 说明                     | 默认值                            | 是否必须 |
| --------------- | -------------------- | ------------------------ | --------------------------------- | -------- |
| checked         | Boolean              | 复选框是否处于选中状态   | -                                 | 是       |
| onCheckedChange | ((Boolean) -> Unit)? | 选中状态变化时的回调函数 | -                                 | 是       |
| modifier        | Modifier             | 应用于复选框的修饰符     | Modifier                          | 否       |
| colors          | CheckboxColors       | 复选框的颜色配置         | CheckboxDefaults.checkboxColors() | 否       |
| enabled         | Boolean              | 复选框是否可交互         | true                              | 否       |

### CheckboxDefaults 对象

CheckboxDefaults 对象提供了 Checkbox 组件的默认颜色配置。

#### 方法

| 方法名           | 类型           | 说明                     |
| ---------------- | -------------- | ------------------------ |
| checkboxColors() | CheckboxColors | 创建复选框的默认颜色配置 |

### CheckboxColors 类

| 属性名                           | 类型  | 说明                         |
| -------------------------------- | ----- | ---------------------------- |
| checkedForegroundColor           | Color | 选中状态时前景色（对勾颜色） |
| uncheckedForegroundColor         | Color | 未选中状态时前景色           |
| disabledCheckedForegroundColor   | Color | 禁用且选中状态时前景色       |
| disabledUncheckedForegroundColor | Color | 禁用且未选中状态时前景色     |
| checkedBackgroundColor           | Color | 选中状态时背景色             |
| uncheckedBackgroundColor         | Color | 未选中状态时背景色           |
| disabledCheckedBackgroundColor   | Color | 禁用且选中状态时背景色       |
| disabledUncheckedBackgroundColor | Color | 禁用且未选中状态时背景色     |

## 进阶用法

### 自定义颜色

```kotlin
var checked by remember { mutableStateOf(false) }

Checkbox(
    checked = checked,
    onCheckedChange = { checked = it },
    colors = CheckboxDefaults.checkboxColors(
        checkedBackgroundColor = Color.Red,
        checkedForegroundColor = Color.White
    )
)
```

### 结合文本使用

```kotlin
var checked by remember { mutableStateOf(false) }

Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier = Modifier.padding(16.dp)
) {
    Checkbox(
        checked = checked,
        onCheckedChange = { checked = it }
    )
    Spacer(modifier = Modifier.width(8.dp))
    Text(text = "接受用户协议与隐私政策")
}
```

### 在列表中使用

```kotlin
val options = listOf("选项 A", "选项 B", "选项 C")
val checkedStates = remember { mutableStateListOf(false, true, false) }

LazyColumn {
    items(options.size) { index ->
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = checkedStates[index],
                onCheckedChange = { checkedStates[index] = it }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = options[index])
        }
    }
}
```

### 整行列表可点击

```kotlin
data class Option(val text: String, var isSelected: Boolean)
val options = remember {
    mutableStateListOf(
        Option("选项 1", false),
        Option("选项 2", true),
        Option("选项 3", false)
    )
}

LazyColumn {
    items(options.size) { index ->
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    options[index] = options[index].copy(
                        isSelected = !options[index].isSelected
                    )
                }
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = options[index].isSelected,
                onCheckedChange = { selected ->
                    options[index] = options[index].copy(isSelected = selected)
                }
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = options[index].text)
        }
    }
}
```
