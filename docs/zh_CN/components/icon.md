# Icon

`Icon` 是 Miuix 中的基础图标组件，用于在界面中展示各种矢量图标、位图图标或自定义绘制内容。它提供了多种绘制图标的方式，适应不同的图标资源类型。

如果要跟随夜间模式或主题变化，需主动使用 `Icon` 组件的 `tint` 属性来设置图标的颜色，常用颜色为 `MiuixTheme.colorScheme.onBackground`。

<div style="position: relative; max-width: 700px; height: 120px; border-radius: 10px; overflow: hidden; border: 1px solid #777;">
    <iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../../compose/index.html?id=icon" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>
</div>

## 引入

```kotlin
import top.yukonga.miuix.kmp.basic.Icon
```

## 基本用法

Icon 组件可以用于显示图标：

```kotlin
Icon(
    imageVector = MiuixIcons.Useful.Like,
    contentDescription = "点赞图标"
)
```

## 图标类型

Miuix Icon 支持多种类型的图标资源：

### 矢量图标（Vector Icon）

```kotlin
Icon(
    imageVector = MiuixIcons.Useful.Settings,
    contentDescription = "设置图标"
)
```

### 位图图标（Bitmap Icon）

```kotlin
val bitmap = ImageBitmap(...)
Icon(
    bitmap = bitmap,
    contentDescription = "位图图标"
)
```

### 自定义绘制图标（Custom Painter）

```kotlin
val customPainter = remember { /* 自定义 Painter */ }

Icon(
    painter = customPainter,
    contentDescription = "自定义图标"
)
```

## 组件状态

### 自定义颜色

```kotlin
Icon(
    imageVector = MiuixIcons.Useful.Personal,
    contentDescription = "人像图标",
    tint = Color.Red
)
```

### 原始颜色（不进行着色）

```kotlin
Icon(
    imageVector = MiuixIcons.Useful.Like,
    contentDescription = "点赞图标",
    tint = Color.Unspecified // 默认情况
)
```

## 属性

### Icon 属性（ImageVector 版本）

| 属性名             | 类型        | 说明                 | 默认值                     | 是否必须 |
| ------------------ | ----------- | -------------------- | -------------------------- | -------- |
| imageVector        | ImageVector | 要绘制的矢量图标     | -                          | 是       |
| contentDescription | String?     | 图标的无障碍描述文本 | -                          | 否       |
| modifier           | Modifier    | 应用于图标的修饰符   | Modifier                   | 否       |
| tint               | Color       | 应用于图标的着色颜色 | IconDefaults.DefaultTint() | 否       |

### Icon 属性（ImageBitmap 版本）

| 属性名             | 类型        | 说明                 | 默认值                     | 是否必须 |
| ------------------ | ----------- | -------------------- | -------------------------- | -------- |
| bitmap             | ImageBitmap | 要绘制的位图图标     | -                          | 是       |
| contentDescription | String?     | 图标的无障碍描述文本 | -                          | 否       |
| modifier           | Modifier    | 应用于图标的修饰符   | Modifier                   | 否       |
| tint               | Color       | 应用于图标的着色颜色 | IconDefaults.DefaultTint() | 否       |

### Icon 属性（Painter 版本）

| 属性名             | 类型     | 说明                 | 默认值                     | 是否必须 |
| ------------------ | -------- | -------------------- | -------------------------- | -------- |
| painter            | Painter  | 要使用的绘制器       | -                          | 是       |
| contentDescription | String?  | 图标的无障碍描述文本 | -                          | 否       |
| modifier           | Modifier | 应用于图标的修饰符   | Modifier                   | 否       |
| tint               | Color    | 应用于图标的着色颜色 | IconDefaults.DefaultTint() | 否       |

### IconDefaults 对象

IconDefaults 对象提供了 Icon 组件的默认配置。

#### 方法

| 方法名      | 返回类型 | 说明                                        |
| ----------- | -------- | ------------------------------------------- |
| DefaultTint | Color    | 返回图标的默认着色颜色（Color.Unspecified） |

## 进阶用法

### 自定义大小

```kotlin
Icon(
    imageVector = MiuixIcons.Useful.Like,
    contentDescription = "点赞图标",
    modifier = Modifier.size(32.dp)
)
```

### 结合其他组件使用

```kotlin
Button(
    onClick = { /* 处理点击事件 */ }
) {
    Icon(
        imageVector = MiuixIcons.Useful.Save,
        contentDescription = "下载图标"
    )
    Spacer(modifier = Modifier.width(8.dp))
    Text("下载")
}
```

### 自定义样式

```kotlin
Icon(
    imageVector = MiuixIcons.Useful.Info,
    contentDescription = "警告图标",
    tint = Color(0xFFFFA500),
    modifier = Modifier
        .size(48.dp)
        .background(
            color = Color.LightGray.copy(alpha = 0.3f),
            shape = CircleShape
        )
        .padding(8.dp)
)
```

### 动态变化图标

```kotlin
var isSelected by remember { mutableStateOf(false) }

Icon(
    imageVector = if (isSelected) MiuixIcons.Useful.Like else MiuixIcons.Useful.Unlike,
    contentDescription = "喜欢图标",
    modifier = Modifier.clickable { isSelected = !isSelected }
)
```
