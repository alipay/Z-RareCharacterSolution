---
mobile: false
title: 输入
order: 3
---

### 生僻字输入问题

生僻字录入一直是困扰生僻字用户的一个问题，现有的输入法对生僻字输入的支持度参差不齐，比如搜狗输入可以通过 U+h 横、s 竖、p 撇、n 捺、z 折的笔画输入生僻字，手机模式里搜狗输入法新增了一项生僻字键盘，其他的输入法暂时还未看到对生僻字支持比较好的。

<div style="display: flex; flex-flow: row nowrap; align-items: center; justify-content: flex-start;">
  <img style="width: 400px; height: 130px" src="https://mdn.alipayobjects.com/huamei_2fq7mt/afts/img/A*ol9jSZZGJjYAAAAAAAAAAAAADh58AQ/original" alt="搜狗PC端生僻字输入模式" />
  <img style="width: 400px; height: 221px; margin-left: 30px;" src="https://mdn.alipayobjects.com/huamei_2fq7mt/afts/img/A*KNhERYhg78oAAAAAAAAAAAAADh58AQ/original" alt="搜狗手机端生僻字键盘">
</div>

在各大输入法厂商普遍支持生僻字输入法之前，我们需要一个能提供多样化输入模式、适配多端的生僻字输入组件，嵌入在我们的页面，以帮助用户在各种场景准确地输入生僻字。

### 输入组件是如何实现的？

在大多数场景下我们看到了一个不知道怎么读，也不知道是啥含义的生僻字，当我们想要在电脑里输入这个字的时候，我们多数会选择通过笔画或者拆字的方式输入。在其他一些场景下，比如我姓名里有这个生僻字，我知道如何拼写，我可能会采用拼音输入的方式。无论是笔画、拆字还是拼音，这些都是字符的输入方式，他们与字符形成了一对多的对应关系，很多字符形成一个庞大的关系映射，也就形成了输入法的字库。输入法支持生僻字输入首先要做的就是拓展字库，让字库中存有生僻字字符和输入方式的映射关系。

<div style="width: 500px">
  <img src="https://mdn.alipayobjects.com/huamei_2fq7mt/afts/img/A*YLqJSbVxzkwAAAAAAAAAAAAADh58AQ/original" alt="䶮的各种输入形态" />
</div>

有了字库还不够，输入的生僻字得能够被显示出来，因子还需要给这些生僻字设计能够显示他们的字体，然后输入法需要加载或者内置这些字体。

<div style="width: 500px">
  <img src="https://mdn.alipayobjects.com/huamei_2fq7mt/afts/img/A*iresTZOV8O4AAAAAAAAAAAAADh58AQ/original" alt="䶮的各种输入形态" />
</div>

总结上面的话，生僻字输入组件的实现其实依赖几个关键的技术：字库、候选字推荐算法、生僻字字体。

- **字库**：将用户输入的字符（拼音和笔画，以及其他输入信息）和字符对应的编码关联来形成映射
- **候选字推荐算法**：根据用户输入的字符，在字库中查询字符编码，将查到的编码作为候选字呈现在输入界面上
- **生僻字字体**：输入组件查找候选字以后返回的其实是候选字符的编码，如何将编码显示成用户能看到的字就需要使用到字体。

### 生僻字输入组件示例

<div style="width: 80%">
  <img src="https://mdn.alipayobjects.com/huamei_2fq7mt/afts/img/A*qlulS6Cl55QAAAAAAAAAAAAADh58AQ/original" alt="支付宝生僻字输入组件" />
</div>
