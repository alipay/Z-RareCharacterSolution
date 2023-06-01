---
title: 手机端输入组件
iframeAllow: 'fullscreen *'
mobile: false
order: 3
---

在 H5 端，我们提供了一个虚拟的生僻字输入键盘，在用户需要输入生僻字的时候可以唤起这个键盘，通过输入生僻字的拼音或者笔画检索到想要输入的生僻字。

## 参数

| 参数            | 类型                                  | 说明                                                                          |
| --------------- | ------------------------------------- | ----------------------------------------------------------------------------- |
| visible         | boolean                               | 是否可见                                                                      |
| type            | string                                | 键盘类型, pinyin（拼音）、stroke（笔画）、handwriting（手写），目前只支持拼音 |
| stopPropagation | string[]                              | 阻止某些事件的冒泡，默认阻止 click 事件                                       |
| destroyOnClose  | boolean                               | 组件不可见时卸载内容                                                          |
| forceRender     | boolean                               | 无论组件是隐藏或者显示，都会强制性的将组件渲染到 DOM 中                       |
| showMask        | boolean                               | 是否展示蒙层，默认展示                                                        |
| onClose         | () => void                            | 关闭的时候触发的回调                                                          |
| onFinish        | (value: string, word: object) => void | 输入完成的时候触发的回调                                                      |
| onShow          | () => void                            | 打开的时候触发的回调                                                          |
| onError         | (err) => void                         | 组件调用失败的时候的回调                                                      |
| onReady         | (fontUrl, fontFace) => void           | 组件准备好的时候的回调                                                        |

## 用法

1. 安装工具包

```bash
npm install ant-rare-words-input-react --save
```

2. 在页面初始化的逻辑里执行字体加载代码

```javascript
import RareWordsInput from 'ant-rare-words-input-react';

const [value, setValue] = React.useState < string > '';
const [visible, setVisible] = React.useState < boolean > false;

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

<input
  className="input"
  type="text"
  value={value}
  onChange={handleChange}
/>
<span className='tips' onClick={handleOpen}>录入生僻字</span>
<RareWordsInput
  visible={visible}
  type="pinyin"
  onFinish={(curValue) => {
    setValue(value => value + curValue);
  }}
/>;
```

## 示例
切换手机端访问体验更佳

<code src="./demo/mobile.tsx" compact="true" height="600px" iframe mobile="true"></code>

<style>
  .dumi-default-previewer-demo {
    max-width: 375px;
    height: 664px;
  }

  .dumi-default-previewer-demo iframe {
    height: 640px;
  }
</style>
