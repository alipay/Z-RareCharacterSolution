---
title: 组件接入
order: 100
---

# 组件接入

生僻字输入组件 React 版

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

```js
import { RareInput } from 'ant-rare-words-input-react-pc';

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
<RareInput
  onFinish={(curValue) => {
    setValue(value => value + curValue);
  }}
>
  <span className="tips">录入生僻字</span>
</RareInput>
```

### CDN 方式引入

待补充

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
