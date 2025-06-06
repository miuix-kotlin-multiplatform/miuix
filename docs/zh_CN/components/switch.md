# Switch

`Switch` 是 Miuix 中的基础切换组件，用于在两种状态之间进行切换。它提供了具有动画效果的交互式开关控件，适用于设置项的启用与禁用场景。

<div style="position: relative; max-width: 700px; height: 100px; border-radius: 10px; overflow: hidden; border: 1px solid #777;">
    <iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../../compose/index.html?id=switch" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>
</div>

## 引入

```kotlin
import top.yukonga.miuix.kmp.basic.Switch
```

## 基本用法

Switch 组件可以用于在两种状态间切换：

```kotlin
var checked by remember { mutableStateOf(false) }

Switch(
    checked = checked,
    onCheckedChange = { checked = it }
)
```

## 组件状态

### 禁用状态

```kotlin
var checked by remember { mutableStateOf(false) }

Switch(
    checked = checked,
    onCheckedChange = { checked = it },
    enabled = false
)
```


## 属性

### Switch 属性

| 属性名          | 类型                 | 说明                     | 默认值                        | 是否必须 |
| --------------- | -------------------- | ------------------------ | ----------------------------- | -------- |
| checked         | Boolean              | 开关是否处于选中状态     | -                             | 是       |
| onCheckedChange | ((Boolean) -> Unit)? | 开关状态变化时的回调函数 | -                             | 否       |
| modifier        | Modifier             | 应用于开关的修饰符       | Modifier                      | 否       |
| colors          | SwitchColors         | 开关的颜色配置           | SwitchDefaults.switchColors() | 否       |
| enabled         | Boolean              | 开关是否可交互           | true                          | 否       |

### SwitchDefaults 对象

SwitchDefaults 对象提供了 Switch 组件的默认颜色配置。

#### 方法

| 方法名         | 类型         | 说明                   |
| -------------- | ------------ | ---------------------- |
| switchColors() | SwitchColors | 创建开关的默认颜色配置 |

### SwitchColors 类

| 属性名                      | 类型  | 说明                         | 默认值 | 是否必须 |
| --------------------------- | ----- | ---------------------------- | ------ | -------- |
| checkedThumbColor           | Color | 选中状态时滑块的颜色         | -      | 是       |
| uncheckedThumbColor         | Color | 未选中状态时滑块的颜色       | -      | 是       |
| disabledCheckedThumbColor   | Color | 禁用且选中状态时滑块的颜色   | -      | 是       |
| disabledUncheckedThumbColor | Color | 禁用且未选中状态时滑块的颜色 | -      | 是       |
| checkedTrackColor           | Color | 选中状态时轨道的颜色         | -      | 是       |
| uncheckedTrackColor         | Color | 未选中状态时轨道的颜色       | -      | 是       |
| disabledCheckedTrackColor   | Color | 禁用且选中状态时轨道的颜色   | -      | 是       |
| disabledUncheckedTrackColor | Color | 禁用且未选中状态时轨道的颜色 | -      | 是       |

## 进阶用法

### 自定义颜色

```kotlin
var checked by remember { mutableStateOf(false) }

Switch(
    checked = checked,
    onCheckedChange = { checked = it },
    colors = SwitchDefaults.switchColors(
        checkedTrackColor = Color.Red,
        checkedThumbColor = Color.White
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
    Switch(
        checked = checked,
        onCheckedChange = { checked = it }
    )
    Spacer(modifier = Modifier.width(8.dp))
    Text(text = if (checked) "已开启" else "已关闭")
}
```

### 在列表中使用

```kotlin
val options = listOf("飞行模式", "蓝牙", "位置服务")
val checkedStates = remember { mutableStateListOf(false, true, false) }

LazyColumn {
    items(options.size) { index ->
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = options[index])
            Switch(
                checked = checkedStates[index],
                onCheckedChange = { checkedStates[index] = it }
            )
        }
    }
}
```

### 整行列表可点击

```kotlin
data class Option(val text: String, var isSelected: Boolean)
val options = remember {
    mutableStateListOf(
        Option("飞行模式", false),
        Option("蓝牙", true),
        Option("位置服务", false)
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
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = options[index].text)
            Switch(
                checked = options[index].isSelected,
                onCheckedChange = { 
                    options[index] = options[index].copy(
                        isSelected = !options[index].isSelected
                    )
                }
            )
        }
    }
}
````