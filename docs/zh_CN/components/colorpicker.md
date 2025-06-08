# ColorPicker

`ColorPicker` 是 Miuix 中的颜色选择组件，允许用户通过调整色相、饱和度、明度和透明度来选择颜色。组件提供了直观的滑块界面，支持触觉反馈和实时颜色预览。

<div style="position: relative; max-width: 700px; height: 260px; border-radius: 10px; overflow: hidden; border: 1px solid #777;">
    <iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../../compose/index.html?id=colorPicker" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>
</div>

## 引入

```kotlin
import top.yukonga.miuix.kmp.basic.ColorPicker
```

## 基本用法

ColorPicker 组件可以让用户选择自定义颜色：

```kotlin
var selectedColor by remember { mutableStateOf(Color.Red) }

ColorPicker(
    initialColor = selectedColor,
    onColorChanged = { newColor ->
        selectedColor = newColor
    }
)
```

## 组件变体

### 不带颜色预览

默认情况下，ColorPicker 会显示当前选择的颜色预览，如果不想显示默认的颜色预览，可以将 `showPreview` 设置为 `false`：

```kotlin
ColorPicker(
    initialColor = Color.Blue,
    onColorChanged = { /* 处理颜色变化 */ },
    showPreview = false
)
```

## 触觉反馈

ColorPicker 支持触觉反馈，可以通过 `hapticEffect` 参数自定义反馈效果，详见 [SliderHapticEffect](../components/slider#sliderhapticeffect)。

```kotlin
ColorPicker(
    initialColor = Color.Green,
    onColorChanged = { /* 处理颜色变化 */ },
    hapticEffect = SliderHapticEffect.Step
)
```

## 属性

### ColorPicker 属性

| 属性名         | 类型                              | 说明                 | 默认值                             | 是否必须 |
| -------------- | --------------------------------- | -------------------- | ---------------------------------- | -------- |
| initialColor   | Color                             | 初始颜色             | -                                  | 是       |
| onColorChanged | (Color) -> Unit                   | 颜色变化时的回调函数 | -                                  | 是       |
| showPreview    | Boolean                           | 是否显示颜色预览     | true                               | 否       |
| hapticEffect   | SliderDefaults.SliderHapticEffect | 滑块的触觉反馈效果   | SliderDefaults.DefaultHapticEffect | 否       |
| modifier       | Modifier                          | 应用于组件的修饰符   | Modifier                           | 否       |

## 单独使用滑块组件

ColorPicker 提供了四种不同的滑块组件，可以单独使用：

### HueSlider - 色相滑块

```kotlin
var hue by remember { mutableStateOf(0f) }

HueSlider(
    currentHue = hue,
    onHueChanged = { newHue -> 
        hue = newHue * 360f 
    }
)
```

### SaturationSlider - 饱和度滑块

```kotlin
var saturation by remember { mutableStateOf(0.5f) }

SaturationSlider(
    currentHue = 180f, // 当前色相
    currentSaturation = saturation,
    onSaturationChanged = { newSaturation ->
        saturation = newSaturation
    }
)
```

### ValueSlider - 明度滑块

```kotlin
var value by remember { mutableStateOf(0.5f) }

ValueSlider(
    currentHue = 180f, // 当前色相
    currentSaturation = 0.5f, // 当前饱和度
    currentValue = value,
    onValueChanged = { newValue ->
        value = newValue
    }
)
```

### AlphaSlider - 透明度滑块

```kotlin
var alpha by remember { mutableStateOf(1f) }

AlphaSlider(
    currentHue = 180f, // 当前色相
    currentSaturation = 0.5f, // 当前饱和度
    currentValue = 0.5f, // 当前明度
    currentAlpha = alpha,
    onAlphaChanged = { newAlpha ->
        alpha = newAlpha
    }
)
```

## 进阶用法

### 在表单中使用

结合其他输入控件，创建颜色选择表单：

```kotlin
var currentColor by remember { mutableStateOf(Color.Red) }
var hexValue by remember(currentColor) {
    mutableStateOf(
            "#${(currentColor.red * 255).toInt().toString(16).padStart(2, '0').uppercase()}" +
                (currentColor.green * 255).toInt().toString(16).padStart(2, '0').uppercase() +
                (currentColor.blue * 255).toInt().toString(16).padStart(2, '0').uppercase()
    )
}

Surface {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "选择颜色",
            style = MiuixTheme.textStyles.title2
        )
        Spacer(modifier = Modifier.height(16.dp))
        ColorPicker(
            initialColor = currentColor,
            onColorChanged = {
                currentColor = it
                hexValue = "#${(it.red * 255).toInt().toString(16).padStart(2, '0').uppercase()}" +
                        (it.green * 255).toInt().toString(16).padStart(2, '0').uppercase() +
                        (it.blue * 255).toInt().toString(16).padStart(2, '0').uppercase()
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = hexValue,
            onValueChange = { /* 可以添加十六进制值解析逻辑 */ },
            label = "十六进制值",
            modifier = Modifier.fillMaxWidth()
        )
    }
}
```

### 与对话框结合使用

在对话框中使用 ColorPicker 创建颜色选择器：

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
                        // 例如：保存选中的颜色
                    })
            }
        }
    }
}
```
