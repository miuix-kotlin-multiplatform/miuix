# Button

`Button` 是 Miuix 中的交互基础组件，用于触发操作或事件。提供了多种风格选择，包括主要按钮、次要按钮和文本按钮。

## 引入

```kotlin
import top.yukonga.miuix.kmp.basic.Button
```

## 基本用法

基础按钮组件，用于触发操作：

```kotlin
Button(
    onClick = { /* 处理点击事件 */ }
) {
    Text("按钮")
}
```

## 按钮类型

Miuix 提供了多种类型的按钮，适用于不同的场景和重要程度：

### 主要按钮（Primary Button）

```kotlin
Button(
    onClick = { /* 处理点击事件 */ },
    colors = ButtonDefaults.buttonColorsPrimary()
) {
    Text("主要按钮")
}
```

### 次要按钮（Secondary Button）

```kotlin
Button(
    onClick = { /* 处理点击事件 */ },
    colors = ButtonDefaults.buttonColors()
) {
    Text("次要按钮")
}
```

### 禁用状态

```kotlin
Button(
    onClick = { /* 处理点击事件 */ },
    enabled = false
) {
    Text("禁用按钮")
}
```

### 文本按钮（Text Button）

```kotlin
TextButton(
    text = "文本按钮",
    onClick = { /* 处理点击事件 */ }
)
```

## 属性

### Button 属性

| 属性名       | 类型                            | 默认值                        | 说明                     |
| ------------ | ------------------------------- | ----------------------------- | ------------------------ |
| onClick      | () -> Unit                      | -                             | 点击按钮时触发的回调     |
| modifier     | Modifier                        | Modifier                      | 应用于按钮的修饰符       |
| enabled      | Boolean                         | true                          | 按钮是否可点击           |
| cornerRadius | Dp                              | ButtonDefaults.CornerRadius   | 按钮圆角半径             |
| minWidth     | Dp                              | ButtonDefaults.MinWidth       | 按钮最小宽度             |
| minHeight    | Dp                              | ButtonDefaults.MinHeight      | 按钮最小高度             |
| colors       | ButtonColors                    | ButtonDefaults.buttonColors() | 按钮颜色配置             |
| insideMargin | PaddingValues                   | ButtonDefaults.InsideMargin   | 按钮内部边距             |
| content      | @Composable RowScope.() -> Unit | -                             | 按钮内容区域的可组合函数 |

### TextButton 属性

| 属性名       | 类型             | 默认值                            | 说明                 |
| ------------ | ---------------- | --------------------------------- | -------------------- |
| text         | String           | -                                 | 按钮显示的文本       |
| onClick      | () -> Unit       | -                                 | 点击按钮时触发的回调 |
| modifier     | Modifier         | Modifier                          | 应用于按钮的修饰符   |
| enabled      | Boolean          | true                              | 按钮是否可点击       |
| colors       | TextButtonColors | ButtonDefaults.textButtonColors() | 文本按钮颜色配置     |
| cornerRadius | Dp               | ButtonDefaults.CornerRadius       | 按钮圆角半径         |
| minWidth     | Dp               | ButtonDefaults.MinWidth           | 按钮最小宽度         |
| minHeight    | Dp               | ButtonDefaults.MinHeight          | 按钮最小高度         |
| insideMargin | PaddingValues    | ButtonDefaults.InsideMargin       | 按钮内部边距         |

### ButtonDefaults 对象

ButtonDefaults 对象提供了按钮组件的默认值和颜色配置。

#### 常量

| 常量名       | 类型          | 值                   | 说明           |
| ------------ | ------------- | -------------------- | -------------- |
| MinWidth     | Dp            | 58.dp                | 按钮的最小宽度 |
| MinHeight    | Dp            | 40.dp                | 按钮的最小高度 |
| CornerRadius | Dp            | 16.dp                | 按钮的圆角半径 |
| InsideMargin | PaddingValues | PaddingValues(16.dp) | 按钮的内部边距 |

#### 方法

| 方法名                    | 返回类型         | 说明                       |
| ------------------------- | ---------------- | -------------------------- |
| buttonColors()            | ButtonColors     | 创建次要按钮的颜色配置     |
| buttonColorsPrimary()     | ButtonColors     | 创建主要按钮的颜色配置     |
| textButtonColors()        | TextButtonColors | 创建次要文本按钮的颜色配置 |
| textButtonColorsPrimary() | TextButtonColors | 创建主要文本按钮的颜色配置 |

## 进阶用法

### 带图标按钮

```kotlin
Button(
    onClick = { /* 处理点击事件 */ }
) {
    Icon(
        imageVector = MiuixIcons.Useful.Like,
        contentDescription = "图标"
    )
    Spacer(modifier = Modifier.width(8.dp))
    Text("带图标按钮")
    }
```

### 自定义样式按钮

```kotlin
Button(
    onClick = { /* 处理点击事件 */ },
    colors = ButtonDefaults.buttonColors(
        color = Color.Red.copy(alpha = 0.7f)
    ),
    cornerRadius = 8.dp
) {
    Text("自定义按钮")
}
```

### 加载状态按钮

```kotlin
var isLoading by remember { mutableStateOf(false) }
val scope = rememberCoroutineScope()

Button(
    onClick = {
        isLoading = true
        // 模拟操作
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
    Text("提交")
}
```
