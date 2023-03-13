# ant-rare-words-input-react-pc

[![NPM version](https://img.shields.io/npm/v/ant-rare-words-input-react-pc.svg?style=flat)](https://npmjs.org/package/ant-rare-words-input-react-pc)
[![NPM downloads](http://img.shields.io/npm/dm/ant-rare-words-input-react-pc.svg?style=flat)](https://npmjs.org/package/ant-rare-words-input-react-pc)

# 组件接入

生僻字输入键盘电脑版

## 参数

| 参数     | 类型                                  | 说明                     |
| -------- | ------------------------------------- | ------------------------ |
| children | ReactNode                             | 生僻字输入提示           |
| onFinish | (value: string, word: object) => void | 输入完成的时候触发的回调 |

## 用法

### npm 包方式引用

1. 安装工具包

```bash
tnpm install ant-rare-words-input-react-pc --save
```

2. 在页面初始化的逻辑里执行字体加载代码

```jsx
import RareWordsInput from 'ant-rare-words-input-react-pc';

const [value, setValue] = React.useState < string > '';

const handleChange = (e) => {
  const value = e.target.value;
  setValue(value);
};

<input
  className="input"
  type="text"
  value={value}
  onChange={handleChange}
/>

<RareWordsInput
  onFinish={(curValue) => {
    setValue(value => value + curValue);
  }}
>
  <span className="tips">录入生僻字</span>
</RareInput>
```

### CDN 方式引入

在一些没有使用 webpack 此类打包工具的业务中，无法做到使用 npm 包，可以在 html 内通过引入 cdn script 链接的方式加载生僻字键盘组件

！！！这里注意的是此键盘组件基于 React 运行的，并且没有内置 React 库，如果你的系统也没有引入 React 相关的 script，请记得在此之前先引入：

```html
<script
  src="https://cdn.bootcdn.net/ajax/libs/react/18.2.0/umd/react.production.min.js"
  crossorigin
></script>
<script
  src="https://cdn.bootcdn.net/ajax/libs/react-dom/18.2.0/umd/react-dom.production.min.js"
  crossorigin
></script>
```

示例代码：

```html
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <script
      src="https://cdn.bootcdn.net/ajax/libs/react/18.2.0/umd/react.production.min.js"
      crossorigin
    ></script>
    <script
      src="https://cdn.bootcdn.net/ajax/libs/react-dom/18.2.0/umd/react-dom.production.min.js"
      crossorigin
    ></script>
    <style>
      :root {
        --px: 0.5px !important;
      }
    </style>
  </head>
  <body>
    <div id="app"></div>
    <script
      src="https://npm.elemecdn.com/ant-rare-words-input-react/dist/ant-rare-words-input-react.min.js"
      crossorigin
    ></script>
    <script>
      const domNode = document.getElementById('app');
      const root = ReactDOM.createRoot(domNode);
      const { RareWordsInput } = window;
      root.render(React.createElement(RareWordsInput, { visible: true }));
    </script>
  </body>
</html>
```

## 开发

```bash
# 安装依赖
$ cnpm install

# 启动demo
$ cnpm start

# 打包
$ cnpm run build

# 观察模式打包
$ cnpm run build:watch

# 构建文档
$ cnpm run docs:build

# 检测潜在问题
$ cnpm run doctor
```

## LICENSE

MIT
