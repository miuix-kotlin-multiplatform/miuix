# Card

`Card` 是 Miuix 中的基础容器组件，用于承载相关内容和操作。它提供了具有 Miuix 风格的卡片容器，适用于信息展示、内容分组等场景。支持静态显示和交互式两种模式。

<div style="position: relative; max-width: 700px; height: 300px; border-radius: 10px; overflow: hidden; border: 1px solid #777;">
    <iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../../compose/index.html?id=card" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>
</div>

## 引入

```kotlin
import top.yukonga.miuix.kmp.basic.Card
import top.yukonga.miuix.kmp.utils.PressFeedbackType // 如果使用交互式卡片
```

## 基本用法

Card 组件可以用于包装和组织内容（静态卡片）：

```kotlin
Card {
    Text("这是卡片内容")
}
```

## 属性

### Card 属性


| 属性名            | 类型                               | 说明                     | 默认值                      | 是否必须 | 适用范围 |
| ----------------- | ---------------------------------- | ------------------------ | --------------------------- | -------- | -------- |
| modifier          | Modifier                           | 应用于卡片的修饰符       | Modifier                    | 否       | 所有     |
| cornerRadius      | Dp                                 | 卡片圆角半径             | CardDefaults.CornerRadius   | 否       | 所有     |
| insideMargin      | PaddingValues                      | 卡片内部边距             | CardDefaults.InsideMargin   | 否       | 所有     |
| color             | Color                              | 卡片背景颜色             | CardDefaults.DefaultColor() | 否       | 所有     |
| pressFeedbackType | PressFeedbackType                  | 按压反馈类型             | PressFeedbackType.None      | 否       | 交互式   |
| showIndication    | Boolean?                           | 显示点击指示效果         | false                       | 否       | 交互式   |
| onClick           | (() -> Unit)?                      | 点击事件回调             | null                        | 否       | 交互式   |
| onLongPress       | (() -> Unit)?                      | 长按事件回调             | null                        | 否       | 交互式   |
| content           | @Composable ColumnScope.() -> Unit | 卡片内容区域的可组合函数 | -                           | 是       | 所有     |

::: warning 注意
部分属性仅在创建可交互的卡片时可用！
:::

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
| DefaultColor() | Color | 卡片的默认背景颜色 |

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
            onClick = { /* 处理确认事件 */ }
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

### 可交互的卡片

```kotlin
Card(
    modifier = Modifier.padding(16.dp),
    pressFeedbackType = PressFeedbackType.Sink, // 设置按压反馈为下沉动画效果
    showIndication = true, // 显示点击时的视觉反馈效果
    onClick = { /* 处理点击事件 */ },
    onLongPress = { /* 处理长按事件 */ }
) {
    Text("可交互的卡片")
}
```
