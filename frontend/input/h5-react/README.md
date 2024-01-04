# ant-rare-words-input-react

[![NPM version](https://img.shields.io/npm/v/ant-rare-words-input-react.svg?style=flat)](https://npmjs.org/package/ant-rare-words-input-react)
[![NPM downloads](http://img.shields.io/npm/dm/ant-rare-words-input-react.svg?style=flat)](https://npmjs.org/package/ant-rare-words-input-react)

生僻字输入组件 React 版

## 参数

| 参数            | 类型                                  | 说明                                                                             |
| --------------- | ------------------------------------- | -------------------------------------------------------------------------------- |
| visible         | boolean                               | 是否可见                                                                         |
| type            | string                                | 键盘类型, pinyin（拼音）、stroke（笔画）、handwriting（手写），目前只支持 pinyin |
| stopPropagation | string[]                              | 阻止某些事件的冒泡，默认阻止 click 事件                                          |
| destroyOnClose  | boolean                               | 组件不可见时卸载内容                                                             |
| forceRender     | boolean                               | 无论组件是隐藏或者显示，都会强制性的将组件渲染到 DOM 中                          |
| showMask        | boolean                               | 是否展示蒙层，默认展示                                                           |
| onClose         | () => void                            | 关闭的时候触发的回调                                                             |
| onFinish        | (value: string, word: object) => void | 输入完成的时候触发的回调                                                         |
| onShow          | () => void                            | 打开的时候触发的回调                                                             |
| onError         | (err) => void                         | 组件调用失败的时候的回调                                                         |
| onReady         | (fontUrl, fontFace) => void           | 组件准备好的时候的回调                                                           |

## 用法

### npm 包方式引用

1. 安装工具包

```bash
npm install ant-rare-words-input-react --save
```

2. 在页面初始化的逻辑里执行字体加载代码

```tsx
import RareWordsInput from 'ant-rare-words-input-react';

const Page = () => {
  const [value, setValue] = React.useState<string>('');
  const [visible, setVisible] = React.useState<boolean>(false);

  /**
   * 打开生僻字输入组件
   */
  const handleOpen = () => {
    setVisible(true);
  };

  /**
   * 监听输入事件
   */
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
      <span className="tips" onClick={handleOpen}>
        录入生僻字
      </span>

      <RareWordsInput
        visible={visible}
        type="pinyin"
        onFinish={(curValue) => {
          setValue((value) => value + curValue);
        }}
      />
    </div>
  );
};
```

### CDN 方式引用

在一些没有使用 webpack 此类打包工具的业务中，无法做到使用 npm 包，可以在 html 内通过引入 cdn script 链接的方式加载生僻字键盘组件

！！！这里注意的是此键盘组件基于 React 运行的，并且没有内置 React 库，如果你的系统也没有引入 React 相关的 script，请记得在此之前先引入：

```html
<script src="https://cdn.bootcdn.net/ajax/libs/react/18.2.0/umd/react.production.min.js" crossorigin></script>
<script src="https://cdn.bootcdn.net/ajax/libs/react-dom/18.2.0/umd/react-dom.production.min.js" crossorigin></script>
```

示例代码：

```html
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
    <script src="https://unpkg.com/ant-rare-words-input-react/dist/ant-rare-words-input-react.min.js" crossorigin></script>
    <script>
      const domNode = document.getElementById('app');
      const root = ReactDOM.createRoot(domNode);
      const { RareWordsInput } = window;
      root.render(React.createElement(RareWordsInput, { visible: true }));
    </script>
  </body>
</html>
```

> 如果不希望跟随版本更新，可以限制引入 cdn 时的版本，使用地址 https://unpkg.com/ant-rare-words-input-react@0.0.1/dist/ant-rare-words-input-react.min.js

## 开发

```bash
# 安装依赖
$ npm install

# 启动demo
$ npm start

# 构建产物
$ npm run build

# 观察模式构建差五
$ npm run build:watch

# 构建演示文档
$ npm run docs:build

# 检查代码
$ npm run doctor
```

## LICENSE

MIT
