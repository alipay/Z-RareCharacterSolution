import { defineConfig } from 'dumi';

export default defineConfig({
  outputPath: 'docs-dist',
  themeConfig: {
    name: 'Z字库',
    logo: 'https://mdn.alipayobjects.com/huamei_2fq7mt/afts/img/A*AKR8RpLG-Y8AAAAAAAAAAAAADh58AQ/original',
    nav: [
      {
        title: '开始接入',
        link: '/guide',
      },
      {
        title: '组件测试',
        link: '/demo',
      },
    ],
  },
});
