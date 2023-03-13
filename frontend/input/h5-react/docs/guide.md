---
title: 组件接入
order: 100
---
# 组件接入

生僻字输入组件 React 版

## 参数

| 参数            | 类型                                  | 说明                                                          |
| --------------- | ------------------------------------- | ------------------------------------------------------------- |
| visible         | boolean                               | 是否可见                                                      |
| type            | string                                | 键盘类型, pinyin（拼音）、stroke（笔画）、handwriting（手写） |
| stopPropagation | string[]                              | 阻止某些事件的冒泡，默认阻止 click 事件                       |
| destroyOnClose  | boolean                               | 组件不可见时卸载内容                                          |
| forceRender     | boolean                               | 无论组件是隐藏或者显示，都会强制性的将组件渲染到 DOM 中       |
| showMask        | boolean                               | 是否展示蒙层，默认展示                                        |
| onClose         | () => void                            | 关闭的时候触发的回调                                          |
| onFinish        | (value: string, word: object) => void | 输入完成的时候触发的回调                                      |
| onShow          | () => void                            | 打开的时候触发的回调                                          |
| onError         | (err) => void                         | 组件调用失败的时候的回调                                      |
| onReady         | (fontUrl, fontFace) => void                         | 组件准备好的时候的回调                                      |

## 用法

### npm 包方式引用

1. 安装工具包

```bash
tnpm install ant-rare-words-input-react --save
```

2. 在页面初始化的逻辑里执行字体加载代码

```js
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

