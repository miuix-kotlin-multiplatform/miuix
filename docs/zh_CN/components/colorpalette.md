# ColorPalette 调色盘

ColorPalette 是一个 HSV 带有透明度滑条的网格调色盘组件，可选在右侧显示灰度列。实现采用单 Canvas 渲染、拖拽过程最小重组，支持实时颜色预览。

<div style="position: relative; max-width: 700px; height: 300px; border-radius: 10px; overflow: hidden; border: 1px solid #777;">
    <iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../../compose/index.html?id=colorPalette" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>
</div>

## 引入

```kotlin
import top.yukonga.miuix.kmp.basic.ColorPalette
```

## 基本用法

```kotlin
var selectedColor by remember { mutableStateOf(Color.Red) }

ColorPalette(
    initialColor = selectedColor,
    onColorChanged = { newColor ->
        selectedColor = newColor
    }
)
```

## 变体

### 隐藏颜色预览

通过 `showPreview = false` 隐藏顶部预览面板：

```kotlin
ColorPalette(
    initialColor = selectedColor,
    onColorChanged = { newColor ->
        selectedColor = newColor
    },
    showPreview = false
)
```

### 自定义网格密度

可调整行数、色相列数，并控制是否显示右侧灰度列：

```kotlin
ColorPalette(
    initialColor = selectedColor,
    onColorChanged = { newColor ->
        selectedColor = newColor
    },
    rows = 9,
    hueColumns = 18,
    includeGrayColumn = false
)
```

### 自定义形状与指示器

可控制容器圆角与选中指示器半径：

```kotlin
ColorPalette(
    initialColor = selectedColor,
    onColorChanged = { newColor ->
        selectedColor = newColor
    },
    cornerRadius = 20.dp,
    indicatorRadius = 12.dp
)
```

> 说明：组件内置透明度滑条，目前不可单独关闭。

## 属性

| 属性              | 类型            | 说明                                        | 默认   | 必填 |
| ----------------- | --------------- | ------------------------------------------- | ------ | ---- |
| initialColor      | Color           | 初始颜色                                    | -      | 是   |
| onColorChanged    | (Color) -> Unit | 颜色变化回调                                | -      | 是   |
| modifier          | Modifier        | 组件修饰符                                  | Modifier | 否 |
| rows              | Int             | 网格行数（上亮低饱和 → 下暗高饱和）         | 7      | 否   |
| hueColumns        | Int             | 色相列数                                    | 12     | 否   |
| includeGrayColumn | Boolean         | 是否显示右侧灰度列                          | true   | 否   |
| showPreview       | Boolean         | 是否显示顶部颜色预览面板                    | true   | 否   |
| cornerRadius      | Dp              | 容器圆角                                    | 16.dp  | 否   |
| indicatorRadius   | Dp              | 选中指示器半径                              | 10.dp  | 否   |

## 进阶用法

### 表单场景

与其它输入控件组合，维护 HEX 字符串（如需可扩展到包含 alpha）：

```kotlin
var currentColor by remember { mutableStateOf(Color(0xFF2196F3)) }
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
        ColorPalette(
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

### 弹窗选择

在对话框中使用 `ColorPalette`：

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
            ColorPalette(
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
                    text = "确定",
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
