# Slider

`Slider` 是 Miuix 中的基础交互组件，用于在连续的数值范围内进行选择。用户可以通过拖动滑块来调整值，适用于诸如音量调节、亮度控制、进度显示等场景。

## 引入

```kotlin
import top.yukonga.miuix.kmp.basic.Slider
```

## 基本用法

```kotlin
var sliderValue by remember { mutableStateOf(0.5f) }
Slider(
    progress = sliderValue,
    onProgressChange = { sliderValue = it }
)
```

## 组件状态

### 禁用状态

```kotlin
var progress by remember { mutableStateOf(0.5f) }
Slider(
    progress = progress,
    onProgressChange = { progress = it },
    enabled = false
)
```

## 属性

### Slider 属性

| 属性名           | 类型                              | 默认值                             | 说明                     |
| ---------------- | --------------------------------- | ---------------------------------- | ------------------------ |
| progress         | Float                             | -                                  | 当前滑块的进度值         |
| onProgressChange | (Float) -> Unit                   | -                                  | 进度值变化时的回调函数   |
| modifier         | Modifier                          | Modifier                           | 应用于滑块的修饰符       |
| enabled          | Boolean                           | true                               | 是否启用滑块             |
| minValue         | Float                             | 0f                                 | 滑块的最小值             |
| maxValue         | Float                             | 1f                                 | 滑块的最大值             |
| height           | Dp                                | SliderDefaults.MinHeight           | 滑块的高度               |
| colors           | SliderColors                      | SliderDefaults.sliderColors()      | 滑块的颜色配置           |
| effect           | Boolean                           | false                              | 是否显示特殊效果         |
| decimalPlaces    | Int                               | 2                                  | 拖动指示器中显示的小数位 |
| hapticEffect     | SliderDefaults.SliderHapticEffect | SliderDefaults.DefaultHapticEffect | 滑块的触感反馈类型       |

### SliderDefaults 对象

SliderDefaults 对象提供了 Slider 组件的默认配置。

#### 常量

| 常量名              | 类型               | 值                      | 说明               |
| ------------------- | ------------------ | ----------------------- | ------------------ |
| MinHeight           | Dp                 | 30.dp                   | 滑块的默认高度     |
| DefaultHapticEffect | SliderHapticEffect | SliderHapticEffect.Edge | 默认的触感反馈类型 |

#### 枚举

**SliderHapticEffect**

| 值   | 说明                       |
| ---- | -------------------------- |
| None | 无触感反馈                 |
| Edge | 在 0% 和 100% 处有触感反馈 |
| Step | 在每个步长处有触感反馈     |

#### 方法

| 方法名         | 返回类型     | 说明                   |
| -------------- | ------------ | ---------------------- |
| sliderColors() | SliderColors | 创建滑块的默认颜色配置 |

### SliderColors 类

| 属性名                  | 类型  | 说明                     |
| ----------------------- | ----- | ------------------------ |
| foregroundColor         | Color | 滑块的前景颜色           |
| disabledForegroundColor | Color | 禁用状态时滑块的前景颜色 |
| backgroundColor         | Color | 滑块的背景颜色           |

## 进阶用法

### 自定义数值范围

```kotlin
    Column {
    var temperature by remember { mutableStateOf(25f) }
    Text("温度: $temperature°C")
    Slider(
        progress = temperature,
        onProgressChange = { temperature = it },
        minValue = 16f,
        maxValue = 32f
    )
}
```

### 自定义颜色

```kotlin
var volume by remember { mutableStateOf(0.7f) }
Slider(
    progress = volume,
    onProgressChange = { volume = it },
    colors = SliderDefaults.sliderColors(
        foregroundColor = Color.Red,
        backgroundColor = Color.LightGray
    )
)
```

### 自定义高度和效果

```kotlin
var brightness by remember { mutableStateOf(0.8f) }
Slider(
    progress = brightness,
    onProgressChange = { brightness = it },
    height = 40.dp,
    effect = true
)
```

### 带触感反馈的滑块

```kotlin
var progress by remember { mutableStateOf(0.5f) }
Slider(
    progress = progress,
    onProgressChange = { progress = it },
    hapticEffect = SliderDefaults.SliderHapticEffect.Step
)
```
