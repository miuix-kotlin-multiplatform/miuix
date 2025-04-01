import { defineConfig } from 'vitepress'

export default defineConfig({
  title: "Miuix",
  description: "A UI library for Compose MultiPlatform",
  lastUpdated: true,
  themeConfig: {
    logo: '/Icon.webp',

    nav: [
      { text: '首页', link: '/' },
      { text: '快速开始', link: '/guide/getting-started' },
      { text: '组件库', link: '/components/' },
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
          text: '脚手架',
          items: [
            { text: 'Scaffold', link: '/components/scaffold' },
          ]
        },
        {
          text: '基础组件',
          items: [
            { text: 'Surface', link: '/components/surface' },
            { text: 'BasicComponent', link: '/components/component' },
            { text: 'Button', link: '/components/button' },
            { text: 'Text', link: '/components/text' },
            { text: 'SmallTitle', link: '/components/smalltitle' },
            { text: 'TextField', link: '/components/textfield' },
            { text: 'Switch', link: '/components/switch' },
            { text: 'Checkbox', link: '/components/checkbox' },
            { text: 'Icon', link: '/components/icon' },
            { text: 'FloatingActionButton', link: '/components/floatingactionbutton' },
            { text: 'Divider', link: '/components/divider' },
          ]
        },
        {
          text: '容器组件',
          items: [
            { text: 'Card', link: '/components/card' },
            { text: 'Dialog', link: '/components/dialog' },
            { text: 'ListPopup', link: '/components/listpopup' },
          ]
        },
        {
          text: '导航组件',
          items: [
            { text: 'TopAppBar', link: '/components/topappbar' },
            { text: 'NavigationBar', link: '/components/navigationbar' },
            { text: 'TabRow', link: '/components/tabrow' },
          ]
        },
        {
          text: '反馈组件',
          items: [
            { text: 'Slider', link: '/components/slider' },
            { text: 'ProgressIndicator', link: '/components/progress' },
          ]
        },
        {
          text: '刷新组件',
          items: [
            { text: 'PullToRefresh', link: '/components/pulltorefresh' },
          ]
        },
        {
          text: '其他组件',
          items: [
            { text: 'ColorPicker', link: '/components/colorpicker' },
            { text: 'SearchBar', link: '/components/searchbar' },
          ]
        }
      ]
    },

    socialLinks: [
      { icon: 'github', link: 'https://github.com/YuKongA/miuix-kotlin-multiplatform' }
    ]
  }
})
