# Card

`Card` 是 Miuix 中的基础容器组件，用于承载相关内容和操作。它提供了具有 Miuix 风格的卡片容器，适用于信息展示、内容分组等场景。

## 引入

```kotlin
import top.yukonga.miuix.kmp.basic.Card
```

## 基本用法

Card 组件可以用于包装和组织内容：

```kotlin
Card {
    Text("这是卡片内容")
}
```

## 属性

### Card 属性

| 属性名       | 类型                               | 说明                     | 默认值                      | 是否必须 |
| ------------ | ---------------------------------- | ------------------------ | --------------------------- | -------- |
| modifier     | Modifier                           | 应用于卡片的修饰符       | Modifier                    | 否       |
| cornerRadius | Dp                                 | 卡片圆角半径             | CardDefaults.CornerRadius   | 否       |
| insideMargin | PaddingValues                      | 卡片内部边距             | CardDefaults.InsideMargin   | 否       |
| color        | Color                              | 卡片背景颜色             | CardDefaults.DefaultColor() | 否       |
| content      | @Composable ColumnScope.() -> Unit | 卡片内容区域的可组合函数 | -                           | 是       |

### CardDefaults 对象

CardDefaults 对象提供了卡片组件的默认值和颜色配置。

#### 常量

| 常量名       | 类型          | 说明           | 默认值              |
| ------------ | ------------- | -------------- | ------------------- |
| CornerRadius | Dp            | 卡片的圆角半径 | 16.dp               |
| InsideMargin | PaddingValues | 卡片的内部边距 | PaddingValues(0.dp) |

#### 方法

| 方法名         | 类型  | 说明               |
| -------------- | ----- | ------------------ |
| DefaultColor() | Color | 创建卡片的默认颜色 |

## 进阶用法

### 自定义样式卡片

```kotlin
Card(
    cornerRadius = 8.dp,
    insideMargin = PaddingValues(16.dp),
    color = Color.LightGray.copy(alpha = 0.5f)
) {
    Text("自定义样式卡片")
}
```

### 内容丰富的卡片

```kotlin
Card(
    modifier = Modifier.padding(16.dp),
    insideMargin = PaddingValues(16.dp)
) {
    Text(
        text = "卡片标题",
        style = MiuixTheme.textStyles.title2
    )
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = "这是卡片的详细内容描述，可以包含多行文本信息。"
    )
    Spacer(modifier = Modifier.height(16.dp))
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        TextButton(
            text = "取消",
            onClick = { /* 处理取消事件 */ }
        )
        Spacer(modifier = Modifier.width(8.dp))
        TextButton(
            text = "确定",
            colors = ButtonDefaults.textButtonColorsPrimary(), // 使用主题颜色
            onClick = { /* 处理取消事件 */ }
        )
    }
}
```

### 列表中的卡片

```kotlin
LazyColumn {
    items(5) { index ->
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            insideMargin = PaddingValues(16.dp)
        ) {
            Text("列表项 ${index + 1}")
        }
    }
}
```
