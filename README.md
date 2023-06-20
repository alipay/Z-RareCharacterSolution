# Z-RareCharacterSolution

Z-RareCharacterSolution 是蚂蚁集团开源生僻字解决方案，围绕生僻字领域长期存在的各种痛点，提供了最小粒度的基础能力，如：生僻字“输入法”，生僻字字体、生僻字姓名识别、转码等，同时针对典型场景，也会推荐不同的基础能力组合形成更适合自身业务的解决方案。

## 一、背景

中文是一种象形文字，所以姓氏用字显示的是更为古老的字形和字音，是历史和文化的延续，生僻字在当下的文化环境中不仅不会消失，相反还有复兴之势。具

- 输入问题：生僻字录入一直是困扰生僻字用户的一个问题，现有的输入法对生僻字输入的支持度参差不齐。
- 显示问题：由于操作系统支持的字体缺失，大部分生僻字都无法被正常显示。
- 通行问题：生僻字用户在生活中会遇到各种各样的问题，如
  - 无法在各类 APP 中完成实名认证等
  - 无法在 APP 中查询公积金，社保等
  - 无法线上购买火车票等

为了解决以上的问题，Z-RareCharacterSolution 应运而生，可解决用户在 APP 内的输入，显示和通行问题。

## 二、功能简介

为了解决生僻字姓名在社会各类系统中存在的问题，Z-RareCharacterSolution 提供了以下的能力：

### 2.1 输入能力
为了解决生僻字输入问题，我们需要了一个能提供多样化输入模式、适配多端的生僻字输入组件，嵌入在需要输入生僻字的页面中，可以帮助用户准确地输入生僻字。

### 2.2 显示能力

为了解决生僻字显示问题，我们和阿里巴巴普惠体合作，请他们帮忙设计了汉字字体。并通过向生僻字用户推送消息下载或者主动触发下载的方式，在 APP 端内页面加载了这份字体从而解决了生僻字显示问题。

在web端，我们也提供了显示组件，对接显示组件也能解决生僻字显示问题。

### 2.3 姓名服务能力

为了解决生僻字用户在各类场景下的姓名互通互认问题，Z-RareCharacterSolution 提供了各类关键姓名服务， 使用者接入姓名服务能力后，可实现用户姓名在系统间互通互认。

如业务需要做用户身份信息的联网核查，当使用用户输入的姓名无法核查通过时，可使用姓名转码服务，将转码后的姓名再次尝试联网核查。

## 三、目录介绍

### 3.1 [前端服务](https://github.com/alipay/Z-RareCharacterSolution/tree/main/frontend)

```javascript
frontend
├─utils  生僻字工具库
|   ├─types                      ts定义
|   ├─test                       单测
|   ├─src                        源码目录
|   |  ├─font-loader.ts          字体加载工具
|   |  ├─get-words-data.ts       字库数据获取工具
|   |  ├─index.ts                入口文件
|   |  ├─rare-words.ts           生僻字判断工具
|   |  ├─utils
|   |  |   ├─filter-and-sort.ts  查询候选字方法
|   |  |   ├─index.ts            工具类
|   |  |   └storage.ts           缓存工具
|   |  ├─contants                常量定义
├─input  输入组件
|   ├─pc-react                   输入组件电脑版
|   |    ├─src                   源码目录
|   |    |  ├─index.less         样式文件
|   |    |  ├─index.tsx          组件源码
|   |    |  ├─types
|   |    |  |   └font.ts         类型定义
|   |    |  ├─contants           常量定义
|   |    ├─docs                  文档说明
|   ├─h5-react                   输入组件手机版
|   |    ├─src                   源码目录
|   |    |  ├─index.less         样式文件
|   |    |  ├─index.tsx          组件源码
|   |    |  ├─utils              工具库
|   |    |  ├─typings            ts定义
|   |    |  ├─styles             样式库
|   |    |  ├─keyboard           键盘分类组件
|   |    |  |    ├─stroke        笔画键盘
|   |    |  |    ├─pinyin        拼音键盘
|   |    |  ├─hooks              自定义hooks
|   |    |  ├─context            共享数据
|   |    |  ├─contants           常量定义
|   |    |  ├─components         子组件库
|   |    |  |     ├─Popup        自定义弹窗组件
|   |    |  |     ├─OperationArea  操作按钮区域组件
|   |    |  |     ├─MatchWords     候选字区域组件
|   |    |  |     ├─MatchWordTips  信息提示其余组件
|   |    |  |     ├─InputValueDisplay  已输入字符区域组件
|   |    |  |     ├─ErrorPage    错误页组件
|   |    ├─docs                  文档以及demo
|   ├─apmini  输入组件支付宝小程序版本
|   |   └README.md
```

### 3.2 后端服务

Z-RareCharacterSolution 项目服务端部分，即 rareCharacter（java 版本），主要包含提供各类关键生僻字姓名服务的实现。
- **api**: 生僻字姓名接口服务
- **core**: 核心业务模块
- **dal**：数据访问模块
- **util**: 工具类
- **test**: 测试类

### 3.2 [demo演示](https://github.com/alipay/Z-RareCharacterSolution/tree/main/demo)

通过demo演示，使用者可以一键在本地部署并运行，体验 Z-RareCharacterSolution 提供的各类生僻字能力。
+ [.docker](https://github.com/alipay/Z-RareCharacterSolution/tree/main/demo/.docker): docker 相关配置， 包括服务启动，字库创建和初始化等
+ [server/src](https://github.com/alipay/Z-RareCharacterSolution/tree/main/demo/server/src)：服务端调用 rareCharacter 的示例
+ [web](https://github.com/alipay/Z-RareCharacterSolution/tree/main/demo/web): 可以体验生僻字输入和显示组件

## 四、快速开始
为帮助用户快速了解Z-RareCharacterSolution的服务能力，我们搭建了docker的示例，用户在几分钟内通过几个命令将docker在本地运行起来，以快速了解生僻字处理的各项能力。

### 4.1 环境准备
1. [docker](https://www.docker.com/)
2. [JDK8](https://www.oracle.com/java/technologies/javase/javase8-archive-downloads.html)
3. [Apache Maven 3.2.5 或者更高版本](https://archive.apache.org/dist/maven/maven-3/3.2.5/binaries/)

### 4.2 下载代码
进入蚂蚁开源网站 https://github.com/alipay/Z-RareCharacterSolution，下载代码至本地
```
git clone https://github.com/alipay/Z-RareCharacterSolution.git
```
> 公安系统PUA映射关系可联系方正字库获取

### 4.3 启动demo
1. 检查本地是否安装docker，输入
```
docker version
```
若未安装，请至https://www.docker.com/ 完成安装

2. 本地进入Z-RareCharacterSolution/demo目录，执行
```
cd Z-RareCharacterSolution/demo
docker-compose up 
```

3. 打开本地的浏览器，输入
```
127.0.0.1:80
```
正常展示页面，恭喜你已正常启动demo，可以体验Z字库生僻字解决方案能力了。


## 五、接入文档

+ [前端显示组件](https://rare-words-solution-doc.alipay.com/display#%E7%94%9F%E5%83%BB%E5%AD%97%E6%98%BE%E7%A4%BA-sdk)
+ [前端输入组件](https://rare-words-solution-doc.alipay.com/input/mobile)
+ [服务端姓名服务接口](https://rare-words-solution-doc.alipay.com/server)

## 六、联系我们
您可以通过提交[issues](https://github.com/alipay/Z-RareCharacterSolution/issues)、扫描下方二维码加入生僻字交流群

![联系我们](https://mdn.alipayobjects.com/huamei_2fq7mt/afts/img/A*g8GRQZsMwYYAAAAAAAAAAAAADh58AQ/original)

## 七、感谢
+ Z字库生僻字解决方案使用的字体是由阿里巴巴普惠体提供的，感谢阿里巴巴普惠体对此项目的大力支持。

## 八、开源许可

[Apache License 2.0 协议](https://www.apache.org/licenses/LICENSE-2.0)

## 九、已知用户

此处列出了已知在生产环境使用了 Z-RareCharacterSolution 全部或者部分组件的公司或组织。

![已知用户](https://mdn.alipayobjects.com/huamei_2fq7mt/afts/img/A*oviHR46XARAAAAAAAAAAAAAADh58AQ/original)
