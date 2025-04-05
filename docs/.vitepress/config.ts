import { defineConfig } from 'vitepress'
import locales from './locales'
import zh_CN from './locales/zh_CN'

export default defineConfig({
  base: '/miuix/',
  title: "Miuix",
  locales: locales.locales,
  head: [
    ['link', { rel: 'icon', href: '/miuix/Icon.webp' }],
    ['link', { rel: 'preconnect', href: 'https://cdn-font.hyperos.mi.com/font/css?family=MiSans_VF:VF:Chinese_Simplify,Latin&display=swap' }],
  ],
  markdown: {
    image: {
      lazyLoading: true,
    },
  },
  lastUpdated: true,
  cleanUrls: true,
  themeConfig: {

    logo: '/Icon.webp',

    search: {
      provider: 'local',
      options: {
        locales: {
          zh_CN: {
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
      },
    },

  }
})
