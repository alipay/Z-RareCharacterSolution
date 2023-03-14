---
mobile: false
iframeAllow: 'fullscreen *'
title: 电脑端输入组件
order: 4
---

在电脑端，我们提供了一个虚拟的生僻字输入弹窗，在用户需要输入生僻字的时候可以唤起这个弹窗，通过输入生僻字的拼音、笔画、或者拆字的方式检索到想要输入的生僻字。

## 参数

| 参数     | 类型                                  | 说明                     |
| -------- | ------------------------------------- | ------------------------ |
| children | ReactNode                             | 生僻字输入提示           |
| onFinish | (value: string, word: object) => void | 输入完成的时候触发的回调 |

## 用法

1. 安装工具包

```bash
npm install ant-rare-words-input-react-pc --save
```

2. 在页面初始化的逻辑里执行字体加载代码

```js
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

## 示例

<code src="./demo/desktop.tsx" inline="true" compact="true" iframe mobile="false"></code>

<style>
  .token.unit {
    border: none;
    padding: 0;
    display: inline;
    min-height: unset;
    min-width: unset;
    flex-direction: unset;
  }
<style>
