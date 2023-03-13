import { defineConfig } from 'father';

export default defineConfig({
  // more father config: https://github.com/umijs/father/blob/master/docs/config.md
  esm: { output: 'dist' },
  umd: {
    output: 'dist',
    name: 'RareWordsInput',
    chainWebpack: (memo) => {
      memo.output.libraryExport('default');
      return memo;
    },
    extractCSS: false,
    externals: {
      react: 'React',
      'react-dom': 'ReactDOM',
    },
  },
});
