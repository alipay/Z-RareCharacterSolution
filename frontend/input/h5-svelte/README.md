# ant-rare-words-svelte

[![NPM version](https://img.shields.io/npm/v/ant-rare-words-svelte.svg?style=flat)](https://npmjs.org/package/ant-rare-words-svelte)
[![NPM downloads](http://img.shields.io/npm/dm/ant-rare-words-svelte.svg?style=flat)](https://npmjs.org/package/ant-rare-words-svelte)

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
| onError         | (err) => void                         | 组件调用失败的时候的回调                                                         |
| onReady         | (fontUrl, fontFace) => void           | 组件准备好的时候的回调                                                           |

## 用法

### 在vue项目里的用法

1. 安装工具包

```bash
npm install ant-rare-words-svelte --save
```

2. 创建一个vue组件 RareWordsInput.vue

```html
<template>
  <div ref="input"></div>
</template>

<script>
import { RareWordsInput } from 'ant-rare-words-svelte';
import 'ant-rare-words-svelte/dist/index.css';

export default {
  props: {
    visible: {
      type: Boolean,
      required: false,
    },
    type: {
      type: String,
      required: false,
      default: 'pinyin',
    },
    closeOnMaskClick: {
      type: Boolean,
      default: true,
    },
    onFinish: {
      type: Object,
      default: (word) => {},
    },
    onReady: {
      type: Object,
      default: (fontUrl, fontFace) => {},
    },
    onShow: {
      type: Object,
      default: () => {},
    },
    onClose: {
      type: Object,
      default: () => {},
    },
  },
  watch: {
    visible(newValue) {
      if (newValue) {
        this.instance = new RareWordsInput({
          target: this.$refs.input,
          props: {
            visible: this.visible,
            type: this.type,
            closeOnMaskClick: this.closeOnMaskClick,
            onFinish: this.onFinish,
            onReady: this.onReady,
            onShow: this.onShow,
            onClose: this.onClose,
          },
        });
      } else {
        this.instance.$destroy();
      }
    },
  },
};
</script>
```

3. 在vue的页面中引用这个组件

```html
<script lang="ts">
import RareWordsInput from './components/RareWordsInput.vue';

export default {
  components: {
    RareWordsInput
  },

  data() {
    return {
      visible: false,
    };
  },

  methods: {
    handleClick() {
      this.visible = true;
    },

    handleClose() {
      this.visible = false;
    },
  },
};
</script>

<template>
  <div class="container">
    <RareWordsInput :visible="visible" :onClose="handleClose" />
    <button @click="handleClick">打开</button>
  </div>
</template>
```

### CDN 方式引用

在一些没有使用 webpack 此类打包工具的业务中，无法做到使用 npm 包，可以在 html 内通过引入 cdn script 链接的方式加载生僻字键盘组件。


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
    <script src="https://unpkg.com/ant-rare-words-svelte@0.0.1/dist/index.umd.js" crossorigin></script>
    <link rel="stylesheet" href="https://unpkg.com/ant-rare-words-svelte@0.0.1/dist/index.css">
  </head>
  <body>
    <div class="content">
      <input id="input" class="input" type="text" placeholder="请输入姓名" />
      <button id="open-input-btn" class="btn">输入生僻字</button>
    </div>
    <script type="text/javascript">
      const inputBtnEle = document.getElementById('open-input-btn');
      const inputEle = document.getElementById('input');
      inputBtnEle.addEventListener('click', function () {
        const { RareWordsInput } = window.RareWordsSvelte;
        const input = new RareWordsInput({
          target: document.body,
          props: {
            visible: true,
            onFinish: function (word) {
              inputEle.value = inputEle.value + word;
            },
            onReady: function () {
              const curBodyFontAttr = getComputedStyle(inputEle).fontFamily;
              inputEle.style.fontFamily = `${curBodyFontAttr}, "rare-words-font"`;
            }
          },
          intro: true,
        });
      });

      inputEle.addEventListener('input', function (event) {
        const inputValue = event.target.value;
        inputEle.value = inputValue;
      });
    </script>
  </body>
</html>
```
> 如果不希望跟随版本更新，可以限制引入 cdn 时的版本，使用地址 https://unpkg.com/ant-rare-words-svelte@0.0.1/dist/ant-rare-words-svelte.min.js

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
