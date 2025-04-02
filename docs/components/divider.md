# Divider

`Divider` 是 Miuix 中的基础布局组件，用于在列表和布局中分隔内容。提供了水平分割线和垂直分割线两种形式。

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

| 属性名    | 类型     | 默认值                       | 说明                 |
| --------- | -------- | ---------------------------- | -------------------- |
| modifier  | Modifier | Modifier                     | 应用于分割线的修饰符 |
| thickness | Dp       | DividerDefaults.Thickness    | 分割线的厚度         |
| color     | Color    | DividerDefaults.DividerColor | 分割线的颜色         |

### VerticalDivider 属性

| 属性名    | 类型     | 默认值                       | 说明                 |
| --------- | -------- | ---------------------------- | -------------------- |
| modifier  | Modifier | Modifier                     | 应用于分割线的修饰符 |
| thickness | Dp       | DividerDefaults.Thickness    | 分割线的厚度         |
| color     | Color    | DividerDefaults.DividerColor | 分割线的颜色         |

### DividerDefaults 对象

DividerDefaults 对象提供了分割线组件的默认值。

#### 常量

| 常量名       | 类型  | 值                                 | 说明             |
| ------------ | ----- | ---------------------------------- | ---------------- |
| Thickness    | Dp    | 0.75.dp                            | 分割线的默认厚度 |
| DividerColor | Color | MiuixTheme.colorScheme.dividerLine | 分割线的默认颜色 |

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
