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
npm install ant-rare-words-input-react-pc --save
```

2. 在页面初始化的逻辑里执行字体加载代码

```jsx
import RareWordsInput from 'ant-rare-words-input-react-pc';

const Page = () => {
  const [value, setValue] = React.useState<string>('');

  const handleChange = (e) => {
    const value = e.target.value;
    setValue(value);
  };

  return (
    <div className="container">
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
    </div>
  );
};

```

### CDN 方式引入

在一些没有使用 webpack 此类打包工具的业务中，无法做到使用 npm 包，可以在 html 内通过引入 cdn script 链接的方式加载生僻字键盘组件

！！！这里注意的是此键盘组件基于 React 运行的，并且没有内置 React 库，如果你的系统也没有引入 React 相关的 script，请记得在此之前先引入：

```xml
<script src="https://cdn.bootcdn.net/ajax/libs/react/18.2.0/umd/react.production.min.js" crossorigin></script>
<script src="https://cdn.bootcdn.net/ajax/libs/react-dom/18.2.0/umd/react-dom.production.min.js" crossorigin></script>
```

示例代码：

```xml
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <script src="https://cdn.bootcdn.net/ajax/libs/react/18.2.0/umd/react.production.min.js" crossorigin></script>
    <script src="https://cdn.bootcdn.net/ajax/libs/react-dom/18.2.0/umd/react-dom.production.min.js" crossorigin></script>
    <style>
      :root {
        --px: 0.5px !important;
      }
    </style>
  </head>
  <body>
    <div id="app"></div>
    <script src="https://unpkg.com/ant-rare-words-input-react-pc/dist/ant-rare-words-input-react-pc.min.js" crossorigin></script>
    <script>
      const domNode = document.getElementById('app');
      const root = ReactDOM.createRoot(domNode);
      const { RareWordsInput } = window;
      root.render(React.createElement(RareWordsInput, { visible: true }));
    </script>
  </body>
</html>
```

> 如果 unpkg.com 被墙了，可以替换成 npm.elemecdn.com 的国内镜像，使用地址 https://npm.elemecdn.com/ant-rare-words-input-react-pc/dist/ant-rare-words-input-react-pc.min.js
>
> 如果不希望跟随版本更新，可以限制引入 cdn 时的版本，使用地址 https://unpkg.com/ant-rare-words-input-react-pc@0.0.1/dist/ant-rare-words-input-react-pc.min.js

## 开发

```bash
# 安装依赖
$ npm install

# 启动demo
$ npm start

# 打包
$ npm run build

# 观察模式打包
$ npm run build:watch

# 构建文档
$ npm run docs:build

# 检测潜在问题
$ npm run doctor
```

## LICENSE

MIT
