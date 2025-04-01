# 组件库

Miuix 提供了丰富的 UI 组件，严格遵循 Xiaomi HyperOS 设计规范。每个组件都经过精心设计，确保视觉与交互效果与小米原生体验一致。

## 基础组件

| 组件 | 描述 | 常见用途 |
| --- | --- | --- |
| [Button 按钮](/components/button) | 触发操作的交互元素 | 表单提交、操作确认 |
| [Text 文本](/components/text) | 展示各种样式的文字内容 | 标题、正文、描述文本 |
| [TextField 输入框](/components/textfield) | 接收用户文本输入 | 表单填写、搜索框 |
| [Switch 开关](/components/switch) | 双态切换控件 | 设置项开关、功能启用/禁用 |
| [Checkbox 复选框](/components/checkbox) | 多选控件 | 多项选择、条款同意 |

## 容器组件

| 组件 | 描述 | 常见用途 |
| --- | --- | --- |
| [Card 卡片](/components/card) | 包含相关信息的容器 | 信息展示、内容分组 |
| [Dialog 对话框](/components/dialog) | 模态弹窗 | 重要提示、操作确认 |
| [BottomSheet 底部表单](/components/bottomsheet) | 从屏幕底部弹出的面板 | 选项菜单、补充信息 |

## 导航组件

| 组件 | 描述 | 常见用途 |
| --- | --- | --- |
| [TopAppBar 顶栏](/components/topappbar) | 应用顶部的导航栏 | 页面标题、主要操作 |
| [NavigationBar 导航栏](/components/navigationbar) | 底部导航组件 | 主要页面切换 |
| [TabRow 标签栏](/components/tabrow) | 水平标签页切换栏 | 内容分类浏览 |

## 反馈组件

| 组件 | 描述 | 常见用途 |
| --- | --- | --- |
| [ProgressIndicator 进度指示器](/components/progress) | 展示操作进度状态 | 加载中、进度展示 |
| [Snackbar 提示条](/components/snackbar) | 临时性反馈信息 | 操作确认、轻量提示 |

## 组件用法示例

以下是 MiuixButton 的基本使用示例：

```kotlin
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import top.yukonga.miuix.kmp.components.MiuixButton
import top.yukonga.miuix.kmp.components.MiuixButtonStyle
import top.yukonga.miuix.kmp.theme.MiuixTheme

@Composable
fun ButtonExample() {
    MiuixTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            // 默认按钮
            MiuixButton(
                onClick = { /* 处理点击 */ }
            ) {
                Text("默认按钮")
            }
            
            // 强调按钮
            MiuixButton(
                onClick = { /* 处理点击 */ },
                style = MiuixButtonStyle.Emphasized
            ) {
                Text("强调按钮")
            }
            
            // 辅助按钮
            MiuixButton(
                onClick = { /* 处理点击 */ },
                style = MiuixButtonStyle.Secondary
            ) {
                Text("辅助按钮")
            }
            
            // 图标按钮
            MiuixButton(
                onClick = { /* 处理点击 */ },
                leadingIcon = Icons.Rounded.Add
            ) {
                Text("图标按钮")
            }
        }
    }
}

@Preview
@Composable
fun ButtonPreview() {
    ButtonExample()
}
```

每个组件页面都包含详细的参数说明、可定制选项和多个使用示例，帮助您快速上手。
