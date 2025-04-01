import { defineConfig } from 'vitepress'

// https://vitepress.dev/reference/site-config
export default defineConfig({
  title: "Miuix",
  description: "A UI library for Compose MultiPlatform",
  themeConfig: {
    // https://vitepress.dev/reference/default-theme-config
    logo: 'public/Icon.webp',
    
    nav: [
      { text: '首页', link: '/' },
      { text: '指南', link: '/guide/getting-started' },
      { text: '组件', link: '/components/' },
    ],

    sidebar: {
      '/guide/': [
        {
          text: '入门',
          items: [
            { text: '快速开始', link: '/guide/getting-started' },
          ]
        },
        {
          text: '进阶',
          items: [
            { text: '多平台支持', link: '/guide/multiplatform' },
            { text: '最佳实践', link: '/guide/best-practices' },
          ]
        }
      ],
      '/components/': [
        {
          text: '基础组件',
          items: [
            { text: 'Button 按钮', link: '/components/button' },
            { text: 'Text 文本', link: '/components/text' },
            { text: 'TextField 输入框', link: '/components/textfield' },
            { text: 'Switch 开关', link: '/components/switch' },
            { text: 'Checkbox 复选框', link: '/components/checkbox' },
          ]
        },
        {
          text: '容器组件',
          items: [
            { text: 'Card 卡片', link: '/components/card' },
            { text: 'Dialog 对话框', link: '/components/dialog' },
            { text: 'BottomSheet 底部表单', link: '/components/bottomsheet' },
          ]
        },
        {
          text: '导航组件',
          items: [
            { text: 'TopAppBar 顶栏', link: '/components/topappbar' },
            { text: 'NavigationBar 导航栏', link: '/components/navigationbar' },
            { text: 'TabRow 标签栏', link: '/components/tabrow' },
          ]
        },
        {
          text: '反馈组件',
          items: [
            { text: 'ProgressIndicator 进度指示器', link: '/components/progress' },
            { text: 'Snackbar 提示条', link: '/components/snackbar' },
          ]
        }
      ]
    },

    socialLinks: [
      { icon: 'github', link: 'https://github.com/YuKongA/miuix-kotlin-multiplatform' }
    ]
  }
})
