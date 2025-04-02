import { defineConfig } from 'vitepress'

export default defineConfig({
  title: "Miuix",
  description: "A UI library for Compose MultiPlatform",
  lastUpdated: true,
  lang: 'zh-Hans',
  themeConfig: {
    logo: '/Icon.webp',

    docFooter: {
      prev: '上一页',
      next: '下一页'
    },

    outline: {
      label: '页面导航'
    },

    lastUpdated: {
      text: '最后更新于',
      formatOptions: {
        dateStyle: 'short',
        timeStyle: 'medium'
      }
    },

    langMenuLabel: '多语言',
    returnToTopLabel: '回到顶部',
    sidebarMenuLabel: '菜单',
    darkModeSwitchLabel: '主题',
    lightModeSwitchTitle: '切换到浅色模式',
    darkModeSwitchTitle: '切换到深色模式',
    skipToContentLabel: '跳转到内容',

    editLink: {
      pattern: 'https://github.com/miuix-kotlin-multiplatform/miuix/edit/main/docs/:path',
      text: '在 GitHub 上编辑此页面'
    },

    footer: {
      message: '基于 Apache-2.0 许可发布',
      copyright: `版权所有 © 2024-${new Date().getFullYear()} miuix-kotlin-multiplatform`
    },

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
            { text: 'BasicComponent', link: '/components/basiccomponent' },
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
