import { defineConfig } from 'vitepress'

export default defineConfig({
    description: "一个适用于 Compose MultiPlatform 的 UI 库",
    lang: 'zh_CN',
    themeConfig: {

        docFooter: {
            prev: '上一页',
            next: '下一页'
        },

        outline: {
            label: '页面导航'
        },

        lastUpdated: {
            text: '最后更新于',
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
            { text: '首页', link: '/zh_CN/' },
            { text: '快速开始', link: '/zh_CN/guide/getting-started' },
            { text: '组件库', link: '/zh_CN/components/' },
        ],

        sidebar: {
            '/zh_CN/guide/': [
                {
                    text: '入门',
                    collapsed: false,
                    items: [
                        { text: '快速开始', link: '/zh_CN/guide/getting-started' },
                    ]
                },
                {
                    text: '进阶',
                    collapsed: false,
                    items: [
                        { text: '主题系统', link: '/zh_CN/guide/theme' },
                        { text: '图标系统', link: '/zh_CN/guide/icons' },
                        { text: '工具函数', link: '/zh_CN/guide/utils' },
                        { text: '平台支持', link: '/zh_CN/guide/multiplatform' },
                        { text: '最佳实践', link: '/zh_CN/guide/best-practices' },
                    ]
                }
            ],
            '/zh_CN/components/': [
                {
                    text: '组件库',
                    collapsed: false,
                    items: [
                        { text: '总览', link: '/zh_CN/components/' },
                    ]
                },
                {
                    text: '脚手架',
                    collapsed: false,
                    items: [
                        { text: 'Scaffold', link: '/zh_CN/components/scaffold' },
                    ]
                },
                {
                    text: '基础组件',
                    collapsed: false,
                    items: [
                        { text: 'Surface', link: '/zh_CN/components/surface' },
                        { text: 'TopAppBar', link: '/zh_CN/components/topappbar' },
                        { text: 'NavigationBar', link: '/zh_CN/components/navigationbar' },
                        { text: 'TabRow', link: '/zh_CN/components/tabrow' },
                        { text: 'Card', link: '/zh_CN/components/card' },
                        { text: 'BasicComponent', link: '/zh_CN/components/basiccomponent' },
                        { text: 'Button', link: '/zh_CN/components/button' },
                        { text: 'IconButton', link: '/zh_CN/components/iconbutton' },
                        { text: 'Text', link: '/zh_CN/components/text' },
                        { text: 'SmallTitle', link: '/zh_CN/components/smalltitle' },
                        { text: 'TextField', link: '/zh_CN/components/textfield' },
                        { text: 'Switch', link: '/zh_CN/components/switch' },
                        { text: 'Checkbox', link: '/zh_CN/components/checkbox' },
                        { text: 'Slider', link: '/zh_CN/components/slider' },
                        { text: 'ProgressIndicator', link: '/zh_CN/components/progressindicator' },
                        { text: 'Icon', link: '/zh_CN/components/icon' },
                        { text: 'FloatingActionButton', link: '/zh_CN/components/floatingactionbutton' },
                        { text: 'FloatingToolbar', link: '/zh_CN/components/floatingtoolbar' },
                        { text: 'Divider', link: '/zh_CN/components/divider' },
                        { text: 'PullToRefresh', link: '/zh_CN/components/pulltorefresh' },
                        { text: 'SearchBar', link: '/zh_CN/components/searchbar' },
                        { text: 'ColorPicker', link: '/zh_CN/components/colorpicker' },
                        { text: 'ListPopup', link: '/zh_CN/components/listpopup' },
                    ]
                },
                {
                    text: '扩展组件',
                    collapsed: false,
                    items: [
                        { text: 'SuperArrow', link: '/zh_CN/components/superarrow' },
                        { text: 'SuperSwitch', link: '/zh_CN/components/superswitch' },
                        { text: 'SuperCheckBox', link: '/zh_CN/components/supercheckbox' },
                        { text: 'SuperDropdown', link: '/zh_CN/components/superdropdown' },
                        { text: 'SuperSpinner', link: '/zh_CN/components/superspinner' },
                        { text: 'SuperDialog', link: '/zh_CN/components/superdialog' },
                    ]
                },
            ]
        },
    }
})
