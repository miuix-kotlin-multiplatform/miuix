# ColorPicker

`ColorPicker` is a color selection component in Miuix that allows users to pick colors by adjusting hue, saturation, brightness, and transparency. The component provides an intuitive slider interface with haptic feedback and real-time color preview.

<div style="position: relative; max-width: 700px; height: 260px; border-radius: 10px; overflow: hidden; border: 1px solid #777;">
    <iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../compose/index.html?id=colorPicker" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>
</div>

## Import

```kotlin
import top.yukonga.miuix.kmp.basic.ColorPicker
```

## Basic Usage

The ColorPicker component enables users to select custom colors:

```kotlin
var selectedColor by remember { mutableStateOf(Color.Red) }

ColorPicker(
    initialColor = selectedColor,
    onColorChanged = { newColor ->
        selectedColor = newColor
    }
)
```

## Component Variants

### Without Color Preview

By default, ColorPicker displays a preview of the currently selected color. If you don't want to show the default color preview, set `showPreview` to `false`:

```kotlin
ColorPicker(
    initialColor = Color.Blue,
    onColorChanged = { /* Handle color change */ },
    showPreview = false
)
```

## Haptic Feedback

ColorPicker supports haptic feedback, which can be customized using the `hapticEffect` parameter. See [SliderHapticEffect](../components/slider#sliderhapticeffect) for details.

```kotlin
ColorPicker(
    initialColor = Color.Green,
    onColorChanged = { /* Handle color change */ },
    hapticEffect = SliderHapticEffect.Step
)
```

## Properties

### ColorPicker Properties

| Property Name  | Type                              | Description                   | Default Value                      | Required |
| -------------- | --------------------------------- | ----------------------------- | ---------------------------------- | -------- |
| initialColor   | Color                             | Initial color                 | -                                  | Yes      |
| onColorChanged | (Color) -> Unit                   | Callback for color changes    | -                                  | Yes      |
| showPreview    | Boolean                           | Show color preview            | true                               | No       |
| hapticEffect   | SliderDefaults.SliderHapticEffect | Slider haptic feedback effect | SliderDefaults.DefaultHapticEffect | No       |
| modifier       | Modifier                          | Modifier for the component    | Modifier                           | No       |

## Individual Slider Components

ColorPicker provides four different slider components that can be used independently:

### HueSlider

```kotlin
var hue by remember { mutableStateOf(0f) }

HueSlider(
    currentHue = hue,
    onHueChanged = { newHue -> 
        hue = newHue * 360f 
    }
)
```

### SaturationSlider

```kotlin
var saturation by remember { mutableStateOf(0.5f) }

SaturationSlider(
    currentHue = 180f, // Current hue
    currentSaturation = saturation,
    onSaturationChanged = { newSaturation ->
        saturation = newSaturation
    }
)
```

### ValueSlider

```kotlin
var value by remember { mutableStateOf(0.5f) }

ValueSlider(
    currentHue = 180f, // Current hue
    currentSaturation = 0.5f, // Current saturation
    currentValue = value,
    onValueChanged = { newValue ->
        value = newValue
    }
)
```

### AlphaSlider

```kotlin
var alpha by remember { mutableStateOf(1f) }

AlphaSlider(
    currentHue = 180f, // Current hue
    currentSaturation = 0.5f, // Current saturation
    currentValue = 0.5f, // Current value
    currentAlpha = alpha,
    onAlphaChanged = { newAlpha ->
        alpha = newAlpha
    }
)
```

## Advanced Usage

### Using in Forms

Combine with other input controls to create a color selection form:

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
            text = "Select Color",
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
            onValueChange = { /* Add hex value parsing logic */ },
            label = "Hex Value",
            modifier = Modifier.fillMaxWidth()
        )
    }
}
```

### Using with Dialog

Use ColorPicker in a dialog to create a color selector:

```kotlin
var showColorDialog = remember { mutableStateOf(false) }
var selectedColor by remember { mutableStateOf(Color.Red) }

Scaffold { 
    TextButton(
        text = "Select Color",
        onClick = { showColorDialog.value = true }
    )
    SuperDialog(
        title = "Select Color",
        show = showColorDialog,
        onDismissRequest = { showColorDialog.value = false }
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
                    text = "Cancel",
                    onClick = { showColorDialog.value = false }
                )
                TextButton(
                    modifier = Modifier.weight(1f),
                    text = "Confirm",
                    colors = ButtonDefaults.textButtonColorsPrimary(),
                    onClick = {
                        showColorDialog.value = false
                        // Handle confirmation logic
                        // For example: save the selected color
                    })
            }
        }
    }
}
```
