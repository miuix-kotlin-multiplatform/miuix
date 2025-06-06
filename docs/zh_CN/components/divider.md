# Divider

`Divider` 是 Miuix 中的基础布局组件，用于在列表和布局中分隔内容。提供了水平分割线和垂直分割线两种形式。

<div style="position: relative; max-width: 700px; height: 160px; border-radius: 10px; overflow: hidden; border: 1px solid #777;">
    <iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../../compose/index.html?id=divider" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>
</div>

## 引入

```kotlin
import top.yukonga.miuix.kmp.basic.HorizontalDivider // 水平分割线
import top.yukonga.miuix.kmp.basic.VerticalDivider  // 垂直分割线
```

## 基本用法

### 水平分割线

水平分割线用于分隔垂直排列的内容：

```kotlin
Column {
    Text("上方内容")
    HorizontalDivider()
    Text("下方内容")
}
```

### 垂直分割线

垂直分割线用于分隔水平排列的内容：

```kotlin
Row {
    Text("左侧内容")
    VerticalDivider()
    Text("右侧内容")
}
```

## 属性

### HorizontalDivider 属性

| 属性名    | 类型     | 说明                 | 默认值                       | 是否必须 |
| --------- | -------- | -------------------- | ---------------------------- | -------- |
| modifier  | Modifier | 应用于分割线的修饰符 | Modifier                     | 否       |
| thickness | Dp       | 分割线的厚度         | DividerDefaults.Thickness    | 否       |
| color     | Color    | 分割线的颜色         | DividerDefaults.DividerColor | 否       |

### VerticalDivider 属性

| 属性名    | 类型     | 说明                 | 默认值                       | 是否必须 |
| --------- | -------- | -------------------- | ---------------------------- | -------- |
| modifier  | Modifier | 应用于分割线的修饰符 | Modifier                     | 否       |
| thickness | Dp       | 分割线的厚度         | DividerDefaults.Thickness    | 否       |
| color     | Color    | 分割线的颜色         | DividerDefaults.DividerColor | 否       |

### DividerDefaults 对象

DividerDefaults 对象提供了分割线组件的默认值。

#### 常量

| 常量名       | 类型  | 说明             | 默认值                             |
| ------------ | ----- | ---------------- | ---------------------------------- |
| Thickness    | Dp    | 分割线的默认厚度 | 0.75.dp                            |
| DividerColor | Color | 分割线的默认颜色 | MiuixTheme.colorScheme.dividerLine |

## 进阶用法

### 自定义厚度的分割线

```kotlin
Column {
    Text("上方内容")
    HorizontalDivider(
        thickness = 2.dp
    )
    Text("下方内容")
}
```

### 自定义颜色的分割线

```kotlin
Column {
    Text("上方内容")
    HorizontalDivider(
        color = Color.Red
    )
    Text("下方内容")
}
```

### 带内边距的分割线

```kotlin
Column {
    Text("上方内容")
    HorizontalDivider(
        modifier = Modifier.padding(horizontal = 16.dp)
    )
    Text("下方内容")
}
```

### 使用垂直分割线分隔按钮

```kotlin
Row(
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
) {
    Button(onClick = { /* 处理点击事件 */ }) {
        Text("取消")
    }
    VerticalDivider(
        modifier = Modifier.height(24.dp)
    )
    Button(onClick = { /* 处理点击事件 */ }) {
        Text("确认")
    }
}
```
