---
mobile: false
iframeAllow: 'fullscreen *'
title: 支付宝小程序输入组件
order: 5
---

我们在[ant-design-mini](https://mini.ant.design/components/rare-words-keyboard)支付宝官方小程序组件库中提供了一个虚拟的生僻字输入键盘组件，在用户需要输入生僻字的时候可以唤起这个键盘，通过输入生僻字的拼音、笔画、或者拆字的方式检索到想要输入的生僻字。

## 参数

| 属性     | 说明                                                                             | 类型                    | 默认值 |
| -------- | -------------------------------------------------------------------------------- | ----------------------- | ------ |
| visible  | 是否可见                                                                         | boolean                 | false  |
| type     | 键盘类型, pinyin（拼音）、stroke（笔画）、handwriting（手写），目前只支持 pinyin | string                  | pinyin |
| showMask | 是否展示背景蒙层                                                                 | boolean                 | true   |
| safeArea | 安全距离                                                                         | boolean                 | true   |
| onClose  | 关闭的时候触发的回调                                                             | () => void              | -      |
| onChange | 输入完成的时候触发的回调                                                         | (value: string) => void | -      |
| onShow   | 打开的时候触发的回调                                                             | () => void              | -      |
| onError  | 打开键盘出错的回调                                                               | (err: Error) => void    | -      |

## 用法

1. 安装依赖

```bash
npm i antd-mini --save
```

2. 在 json 文件中配置

```json
{
  "usingComponents": {
    "rare-words-keyboard": "antd-mini/es/RareWordsKeyboard/index"
  }
}
```

2. 在 axml 文件中使用

```xml
<rare-words-keyboard
  visible="{{visible}}"
  onClose="onClose"
  onChange="onChange"
/>
```

```js
Page({
  data: {
    visible: false,
  },
  // 打开键盘
  onTap() {
    this.setData({ visible: true });
  },
  // 关闭键盘
  onClose() {
    this.setData({ visible: false });
  },
  onChange(value) {
    my.showToast({ content: 'onChange ' + value });
  },
});
```

## 示例

请前往 [ant-design-mini](https://mini.ant.design/components/rare-words-keyboard) 官网查看
