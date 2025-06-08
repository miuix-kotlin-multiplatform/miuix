# Slider

`Slider` is a basic interactive component in Miuix used for selecting values within a continuous range. Users can adjust values by dragging the slider, making it suitable for scenarios such as volume adjustment, brightness control, and progress display.

<div style="position: relative; max-width: 700px; height: 150px; border-radius: 10px; overflow: hidden; border: 1px solid #777;">
    <iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../compose/index.html?id=slider" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>
</div>

## Import

```kotlin
import top.yukonga.miuix.kmp.basic.Slider
```

## Basic Usage

```kotlin
var sliderValue by remember { mutableStateOf(0.5f) }

Slider(
    progress = sliderValue,
    onProgressChange = { sliderValue = it }
)
```

## Component States

### Disabled State

```kotlin
var progress by remember { mutableStateOf(0.5f) }

Slider(
    progress = progress,
    onProgressChange = { progress = it },
    enabled = false
)
```

## Haptic Feedback

Slider supports haptic feedback, which can be customized through the `hapticEffect` parameter. See [SliderHapticEffect](../components/slider#sliderhapticeffect) for details.

```kotlin
var progress by remember { mutableStateOf(0.5f) }

Slider(
    progress = progress,
    onProgressChange = { progress = it },
    hapticEffect = SliderHapticEffect.Step
)
```

## Properties

### Slider Properties

| Property Name    | Type                              | Description                            | Default Value                      | Required |
| ---------------- | --------------------------------- | -------------------------------------- | ---------------------------------- | -------- |
| progress         | Float                             | Current slider progress value          | -                                  | Yes      |
| onProgressChange | (Float) -> Unit                   | Callback when progress value changes   | -                                  | Yes      |
| modifier         | Modifier                          | Modifier applied to the slider         | Modifier                           | No       |
| enabled          | Boolean                           | Whether the slider is enabled          | true                               | No       |
| minValue         | Float                             | Minimum value of the slider            | 0f                                 | No       |
| maxValue         | Float                             | Maximum value of the slider            | 1f                                 | No       |
| height           | Dp                                | Height of the slider                   | SliderDefaults.MinHeight           | No       |
| colors           | SliderColors                      | Color configuration of the slider      | SliderDefaults.sliderColors()      | No       |
| effect           | Boolean                           | Whether to show special effects        | false                              | No       |
| decimalPlaces    | Int                               | Decimal places shown in drag indicator | 2                                  | No       |
| hapticEffect     | SliderDefaults.SliderHapticEffect | Type of haptic feedback                | SliderDefaults.DefaultHapticEffect | No       |

### SliderDefaults Object

The SliderDefaults object provides default configurations for the Slider component.

#### Constants

| Constant Name       | Type               | Default Value           | Description                  |
| ------------------- | ------------------ | ----------------------- | ---------------------------- |
| MinHeight           | Dp                 | 30.dp                   | Default height of the slider |
| DefaultHapticEffect | SliderHapticEffect | SliderHapticEffect.Edge | Default haptic feedback type |

### SliderHapticEffect

| Value | Description                    |
| ----- | ------------------------------ |
| None  | No haptic feedback             |
| Edge  | Haptic feedback at 0% and 100% |
| Step  | Haptic feedback at each step   |

#### Methods

| Method Name    | Return Type  | Description                         |
| -------------- | ------------ | ----------------------------------- |
| sliderColors() | SliderColors | Creates default color configuration |

### SliderColors Class

| Property Name           | Type  | Description                    |
| ----------------------- | ----- | ------------------------------ |
| foregroundColor         | Color | Foreground color of the slider |
| disabledForegroundColor | Color | Foreground color when disabled |
| backgroundColor         | Color | Background color of the slider |

## Advanced Usage

### Custom Value Range

```kotlin
var temperature by remember { mutableStateOf(25f) }

Column {
    Text("Temperature: $temperature°C")
    Slider(
        progress = temperature,
        onProgressChange = { temperature = it },
        minValue = 16f,
        maxValue = 32f
    )
}
```

### Custom Colors

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

### Custom Height and Effects

```kotlin
var brightness by remember { mutableStateOf(0.8f) }

Slider(
    progress = brightness,
    onProgressChange = { brightness = it },
    height = 40.dp,
    effect = true
)
```

### Slider with Haptic Feedback

```kotlin
var progress by remember { mutableStateOf(0.5f) }

Slider(
    progress = progress,
    onProgressChange = { progress = it },
    hapticEffect = SliderDefaults.SliderHapticEffect.Step
)
```
