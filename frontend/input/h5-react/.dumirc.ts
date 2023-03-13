import { defineConfig } from 'dumi';
import px2rem from 'postcss-plugin-px2rem';

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
    hd: {
      // umi-hd 的 750 高清方案（默认值）
      rules: [{ mode: 'vw', options: [100, 750] }],
    },
  },
  // 将px转成rem预览
  extraPostCSSPlugins: [px2rem({ rootValue: 100, exclude: /node_modules/i })],
});
