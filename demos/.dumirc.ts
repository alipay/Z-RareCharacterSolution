import { defineConfig } from 'dumi';

export default defineConfig({
  themeConfig: {
    name: 'Z字库',
    // 配置高清方案，默认为 750 高清方案
    hd: { rules: [{ mode: 'vw', options: [100, 750] }] },
    // 配置 demo 预览器的设备宽度，默认为 375px
    deviceWidth: 375,
  },
});
