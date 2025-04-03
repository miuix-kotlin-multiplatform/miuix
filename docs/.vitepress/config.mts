import { defineConfig } from 'vitepress'

export default defineConfig({
  title: "Miuix",
  head: [
    ['link', { rel: 'icon', href: '/Icon.webp' }],
    ['link', { rel: 'preconnect', href: 'https://cdn-font.hyperos.mi.com/font/css?family=MiSans_VF:VF:Chinese_Simplify,Latin&display=swap' }],
  ],
  description: "A UI library for Compose MultiPlatform",
  markdown: {
    image: {
      lazyLoading: true
    },
  },
  lastUpdated: true,
  lang: 'zh-Hans',
  themeConfig: {
    logo: '/Icon.webp',

    search: {
      provider: 'local',
      options: {
        translations: {
          button: {
            buttonText: '搜索文档',
            buttonAriaLabel: '搜索文档'
          },
          modal: {
            noResultsText: '无法找到相关结果',
            resetButtonTitle: '清除查询条件',
            footer: {
              selectText: '选择',
              navigateText: '切换',
              closeText: '关闭',
            },
          },
        },
      },
    },

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

    socialLinks: [
      { icon: 'github', link: 'https://github.com/YuKongA/miuix-kotlin-multiplatform' }
    ],

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
          collapsed: false,
          items: [
            { text: '快速开始', link: '/guide/getting-started' },
          ]
        },
        {
          text: '进阶',
          collapsed: false,
          items: [
            { text: '多平台支持', link: '/guide/multiplatform' },
            { text: '最佳实践', link: '/guide/best-practices' },
          ]
        }
      ],
      '/components/': [
        {
          text: '组件库',
          collapsed: false,
          items: [
            { text: '总览', link: '/components/' },
          ]
        },
        {
          text: '脚手架',
          collapsed: false,
          items: [
            { text: 'Scaffold', link: '/components/scaffold' },
          ]
        },
        {
          text: '基础组件',
          collapsed: false,
          items: [
            { text: 'Surface', link: '/components/surface' },
            { text: 'TopAppBar', link: '/components/topappbar' },
            { text: 'NavigationBar', link: '/components/navigationbar' },
            { text: 'TabRow', link: '/components/tabrow' },
            { text: 'Card', link: '/components/card' },
            { text: 'BasicComponent', link: '/components/basiccomponent' },
            { text: 'Button', link: '/components/button' },
            { text: 'IconButton', link: '/components/iconbutton' },
            { text: 'Text', link: '/components/text' },
            { text: 'SmallTitle', link: '/components/smalltitle' },
            { text: 'TextField', link: '/components/textfield' },
            { text: 'Switch', link: '/components/switch' },
            { text: 'Checkbox', link: '/components/checkbox' },
            { text: 'Slider', link: '/components/slider' },
            { text: 'ProgressIndicator', link: '/components/progressindicator' },
            { text: 'Icon', link: '/components/icon' },
            { text: 'FloatingActionButton', link: '/components/floatingactionbutton' },
            { text: 'Divider', link: '/components/divider' },
            { text: 'PullToRefresh', link: '/components/pulltorefresh' },
            { text: 'SearchBar', link: '/components/searchbar' },
            { text: 'ColorPicker', link: '/components/colorpicker' },
            { text: 'ListPopup', link: '/components/listpopup' },
          ]
        },
        {
          text: '特殊组件',
          collapsed: false,
          items: [
            { text: 'LazyColumn', link: '/components/lazycolumn' },
          ]
        },
        {
          text: '扩展组件',
          collapsed: false,
          items: [
            { text: 'SuperArrow', link: '/components/superarrow' },
            { text: 'SuperSwitch', link: '/components/superswitch' },
            { text: 'SuperCheckBox', link: '/components/supercheckbox' },
            { text: 'SuperDropdown', link: '/components/superdropdown' },
            { text: 'SuperSpinner', link: '/components/superspinner' },
            { text: 'SuperDialog', link: '/components/superdialog' },
          ]
        },
      ]
    },

  }
})
