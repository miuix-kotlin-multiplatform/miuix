# ProgressIndicator

`ProgressIndicator` is a progress indication component in Miuix used to display the progress status of operations. It provides three styles: linear progress bar, circular progress indicator, and infinite spinning indicator, suitable for different loading and progress display scenarios.

<div style="position: relative; max-width: 700px; height: 250px; border-radius: 10px; overflow: hidden; border: 1px solid #777;">
    <iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../compose/index.html?id=progressIndicator" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>
</div>

## Import

```kotlin
import top.yukonga.miuix.kmp.basic.LinearProgressIndicator // Linear progress bar
import top.yukonga.miuix.kmp.basic.CircularProgressIndicator // Circular progress indicator
import top.yukonga.miuix.kmp.basic.InfiniteProgressIndicator // Infinite spinning indicator
```

## Basic Usage

### Linear Progress Bar

Linear progress bar can be used to show operation progress:

```kotlin
// Linear progress bar with determinate progress
var progress by remember { mutableStateOf(0.3f) }

LinearProgressIndicator(progress = progress)
```

```kotlin
// Linear progress bar with indeterminate progress
LinearProgressIndicator()
```

### Circular Progress Indicator

Circular progress indicator is suitable for space-saving scenarios:

```kotlin
// Circular progress indicator with determinate progress
var progress by remember { mutableStateOf(0.7f) }

CircularProgressIndicator(progress = progress)
```

```kotlin
// Circular progress indicator with indeterminate progress
CircularProgressIndicator()
```

### Infinite Progress Indicator

Infinite progress indicator is suitable for scenarios where operation duration is uncertain:

```kotlin
InfiniteProgressIndicator()
```

## Component States

All progress indicator components support both determinate and indeterminate progress states:

### Determinate Progress State

When a specific progress value (float between 0.0-1.0) is provided, the progress indicator shows exact progress:

```kotlin
var progress by remember { mutableStateOf(0.6f) }

LinearProgressIndicator(progress = progress)
CircularProgressIndicator(progress = progress)
```

### Indeterminate Progress State

When the progress value is null, the progress indicator shows an animation indicating an ongoing operation with unknown progress:

```kotlin
LinearProgressIndicator(progress = null)
CircularProgressIndicator(progress = null)
```

## Properties

### LinearProgressIndicator Properties

| Property Name | Type                    | Description                                    | Default Value                                                  | Required |
| ------------- | ----------------------- | ---------------------------------------------- | -------------------------------------------------------------- | -------- |
| progress      | Float?                  | Current progress value, null for indeterminate | null                                                           | No       |
| modifier      | Modifier                | Modifier applied to the progress bar           | Modifier                                                       | No       |
| colors        | ProgressIndicatorColors | Color configuration for the progress bar       | ProgressIndicatorDefaults.progressIndicatorColors()            | No       |
| height        | Dp                      | Height of the progress bar                     | ProgressIndicatorDefaults.DefaultLinearProgressIndicatorHeight | No       |

### CircularProgressIndicator Properties

| Property Name | Type                    | Description                                    | Default Value                                                         | Required |
| ------------- | ----------------------- | ---------------------------------------------- | --------------------------------------------------------------------- | -------- |
| progress      | Float?                  | Current progress value, null for indeterminate | null                                                                  | No       |
| modifier      | Modifier                | Modifier applied to the progress indicator     | Modifier                                                              | No       |
| colors        | ProgressIndicatorColors | Color configuration for the progress indicator | ProgressIndicatorDefaults.progressIndicatorColors()                   | No       |
| strokeWidth   | Dp                      | Stroke width of the circular track             | ProgressIndicatorDefaults.DefaultCircularProgressIndicatorStrokeWidth | No       |
| size          | Dp                      | Size of the circular indicator                 | ProgressIndicatorDefaults.DefaultCircularProgressIndicatorSize        | No       |

### InfiniteProgressIndicator Properties

| Property Name   | Type     | Description                        | Default Value                                                             | Required |
| --------------- | -------- | ---------------------------------- | ------------------------------------------------------------------------- | -------- |
| modifier        | Modifier | Modifier applied to the indicator  | Modifier                                                                  | No       |
| color           | Color    | Color of the progress indicator    | Color.Gray                                                                | No       |
| size            | Dp       | Size of the indicator              | ProgressIndicatorDefaults.DefaultInfiniteProgressIndicatorSize            | No       |
| strokeWidth     | Dp       | Stroke width of the circular track | ProgressIndicatorDefaults.DefaultInfiniteProgressIndicatorStrokeWidth     | No       |
| orbitingDotSize | Dp       | Size of the orbiting dot           | ProgressIndicatorDefaults.DefaultInfiniteProgressIndicatorOrbitingDotSize | No       |

### ProgressIndicatorDefaults Object

The ProgressIndicatorDefaults object provides default values and color configurations for progress indicator components.

#### Constants

| Constant Name                                   | Type | Default Value | Description                                     |
| ----------------------------------------------- | ---- | ------------- | ----------------------------------------------- |
| DefaultLinearProgressIndicatorHeight            | Dp   | 6.dp          | Default height of linear progress bar           |
| DefaultCircularProgressIndicatorStrokeWidth     | Dp   | 4.dp          | Default stroke width of circular indicator      |
| DefaultCircularProgressIndicatorSize            | Dp   | 30.dp         | Default size of circular indicator              |
| DefaultInfiniteProgressIndicatorStrokeWidth     | Dp   | 2.dp          | Default stroke width of infinite indicator      |
| DefaultInfiniteProgressIndicatorOrbitingDotSize | Dp   | 2.dp          | Default orbiting dot size of infinite indicator |
| DefaultInfiniteProgressIndicatorSize            | Dp   | 20.dp         | Default size of infinite indicator              |

#### Methods

| Method Name               | Type                    | Description                                        |
| ------------------------- | ----------------------- | -------------------------------------------------- |
| progressIndicatorColors() | ProgressIndicatorColors | Creates default color configuration for indicators |

### ProgressIndicatorColors Class

| Property Name           | Type  | Description                                 |
| ----------------------- | ----- | ------------------------------------------- |
| foregroundColor         | Color | Foreground color of the progress indicator  |
| disabledForegroundColor | Color | Foreground color when indicator is disabled |
| backgroundColor         | Color | Background color of the progress indicator  |

## Advanced Usage

### Custom Colored Linear Progress Bar

```kotlin
var progress by remember { mutableStateOf(0.4f) }

LinearProgressIndicator(
    progress = progress,
    colors = ProgressIndicatorDefaults.progressIndicatorColors(
        foregroundColor = Color.Red,
        backgroundColor = Color.LightGray
    )
)
```

### Resized Circular Progress Indicator

```kotlin
var progress by remember { mutableStateOf(0.75f) }

CircularProgressIndicator(
    progress = progress,
    size = 50.dp,
    strokeWidth = 6.dp
)
```

### Loading State with Button

```kotlin
var isLoading by remember { mutableStateOf(false) }
val scope = rememberCoroutineScope()

Button(
    onClick = {
        isLoading = true
        // Simulate operation
        scope.launch {
            delay(2000)
            isLoading = false
        }
    },
    enabled = !isLoading
) {
     AnimatedVisibility(
        visible = isLoading
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .padding(end = 8.dp),
            size = 20.dp,
            strokeWidth = 4.dp
        )
    }
    Text("Submit")
}
```

### Custom Infinite Progress Indicator

```kotlin
InfiniteProgressIndicator(
    color = MiuixTheme.colorScheme.primary,
    size = 40.dp,
    strokeWidth = 3.dp,
    orbitingDotSize = 4.dp
)
```

### Loading State with Card

```kotlin
var isLoading by remember { mutableStateOf(true) }

Card(
    modifier = Modifier
        .fillMaxWidth()
        .height(200.dp)
        .padding(16.dp)
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (isLoading) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
                Spacer(modifier = Modifier.height(16.dp))
                Text("Loading...")
            }
        } else {
            Text("Content Loaded")
        }
    }
}
// Control loading state
LaunchedEffect(Unit) {
    delay(3000)
    isLoading = false
}
```
