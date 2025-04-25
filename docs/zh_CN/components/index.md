# 组件库

Miuix 提供了丰富的 UI 组件，严格遵循 Xiaomi HyperOS 设计规范。每个组件都经过精心设计，确保视觉与交互效果与小米原生体验一致。

## 脚手架

| 组件                               | 描述           | 常见用途           |
| ---------------------------------- | -------------- | ------------------ |
| [Scaffold](../components/scaffold) | 应用的基础布局 | 页面结构、内容展示 |

::: warning 注意
Scaffold 组件为跨平台提供了一个合适的弹出窗口的容器。`SuperDialog`、`SuperDropdown`、`SuperSpinner`、`ListPopup` 等组件都基于此实现弹出窗口，因此都需要被该组件包裹。
:::

## 基础组件

| 组件                                                       | 描述                   | 常见用途             |
| ---------------------------------------------------------- | ---------------------- | -------------------- |
| [Surface](../components/surface)                           | 基础容器组件           | 内容展示、背景容器   |
| [TopAppBar](../components/topappbar)                       | 应用顶部的导航栏       | 页面标题、主要操作   |
| [NavigationBar](../components/navigationbar)               | 底部导航组件           | 主要页面切换         |
| [TabRow](../components/tabrow)                             | 水平标签页切换栏       | 内容分类浏览         |
| [Card](../components/card)                                 | 包含相关信息的容器     | 信息展示、内容分组   |
| [BasicComponent](../components/basiccomponent)             | 通用基础组件           | 自定义组件开发       |
| [Button](../components/button)                             | 触发操作的交互元素     | 表单提交、操作确认   |
| [Text](../components/text)                                 | 展示各种样式的文字内容 | 标题、正文、描述文本 |
| [SmallTitle](../components/smalltitle)                     | 小型标题组件           | 辅助标题、分类标识   |
| [TextField](../components/textfield)                       | 接收用户文本输入       | 表单填写、搜索框     |
| [Switch](../components/switch)                             | 双态切换控件           | 设置项开关、功能启用 |
| [Checkbox](../components/checkbox)                         | 多选控件               | 多项选择、条款同意   |
| [Slider](../components/slider)                             | 调节值的滑动控件       | 音量调节、范围选择   |
| [ProgressIndicator](../components/progressindicator)       | 展示操作进度状态       | 加载中、进度展示     |
| [Icon](../components/icon)                                 | 图标展示组件           | 图标按钮、状态指示   |
| [FloatingActionButton](../components/floatingactionbutton) | 悬浮操作按钮           | 主要操作、快捷功能   |
| [FloatingToolbar](../components/floatingtoolbar)           | 悬浮工具栏             | 快捷操作、信息展示   |
| [Divider](../components/divider)                           | 内容分隔线             | 区块分隔、层次划分   |
| [PullToRefresh](../components/pulltorefresh)               | 下拉触发刷新操作       | 数据更新、页面刷新   |
| [SearchBar](../components/searchbar)                       | 执行搜索的输入框       | 内容搜索、快速查找   |
| [ColorPicker](../components/colorpicker)                   | 选择颜色的控件         | 主题设置、颜色选择   |
| [ListPopup](../components/listpopup)                       | 列表弹出窗口组件       | 选项选择、功能列表   |

## 扩展组件

| 组件                                         | 描述                               | 常见用途               |
| -------------------------------------------- | ---------------------------------- | ---------------------- |
| [SuperArrow](../components/superarrow)       | 基于 BasicComponent 的带箭头组件   | 指示可点击、导航提示   |
| [SuperSwitch](../components/superswitch)     | 基于 BasicComponent 的开关组件     | 设置项开关、功能启用   |
| [SuperCheckBox](../components/supercheckbox) | 基于 BasicComponent 的复选框组件   | 多项选择、条款同意     |
| [SuperDropdown](../components/superdropdown) | 基于 BasicComponent 的下拉菜单组件 | 选项选择、功能列表     |
| [SuperSpinner](../components/superspinner)   | 基于 BasicComponent 的高级菜单组件 | 进阶选项选择、功能列表 |
| [SuperDialog](../components/superdialog)     | 基于 BasicComponent 的对话弹窗组件 | 提示、确认操作         |
