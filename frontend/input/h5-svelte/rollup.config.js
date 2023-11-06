import svelte from 'rollup-plugin-svelte';
import css from 'rollup-plugin-css-only';
import { terser } from 'rollup-plugin-terser';
import resolve from '@rollup/plugin-node-resolve';
import serve from 'rollup-plugin-serve';
import livereload from 'rollup-plugin-livereload';
import pkg from './package.json';


export default {
  input: 'src/index.js',
  output: [
    { file: pkg.module, 'format': 'es' },
    { file: pkg.main, 'format': 'umd', name: 'RareWordsSvelte' }
  ],
  plugins: [
    svelte(),
    css({ output: 'index.css' }),
    resolve({
      extensions: ['.js', '.ts', '.svelte'], // 包括 .ts 扩展名
    }),
    ...(process.env.ROLLUP_WATCH ? [
      serve({
        open: true, // 自动打开浏览器窗口
        contentBase: ['public', 'dist'], // 指定静态文件目录
        port: 8081, // 指定端口号
      }),
      livereload({
        watch: ['public', 'dist'], // 监听 public 目录中的文件变化
      }),
    ] : [terser()]),
  ],
};
