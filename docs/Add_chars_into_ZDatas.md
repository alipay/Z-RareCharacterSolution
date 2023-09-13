# Z字库新增汉字指南

Z 字库关于汉字数据有三个方面：

1. 输入键盘元数据，录入汉字拼音和拆字信息 （`frontend/utils/src/contants/zdata-local.ts`）
2. Z字库字体文件，包含了所有汉字的字体 （`demo/web/public/fonts/RareWordsFonts.ttf`）
3. 后端数据库 `SQL` 文件，存放汉字插入 `SQL` 指令 （`demo/.docker/database/init.sql`）

因此新增字库也就是修改以上三个文件，下面我逐一介绍如何以上三个文件应该如何新增汉字。

## 输入键盘元数据

输入键盘元数据结构：

```json
{
  charId: '66c867',
  unicodeChar: '䶮',
  unicodeCodePoint: '4DAE',
  pinYinChars: [{ char: 'YAN3' }],
  splitChars: [{ char: '龙天' }],
  weight: '10',
}
```

属性说明：
| 属性名 | 类型 | 说明 |
| ---------- | ---------- | ---------------- |
| `charId` | string | 汉字唯一 ID |
| `unicodeChar` | string | 汉字原字，汉字可复制字符 |
| `unicodeCodePoint` | string | 汉字 unicode 编码 |
| `pinYinChars` | {char: string}[] | 拼音，支持多个 |
| `splitChars` | {char: string}[] | 拆字，支持多个 |
| `weight` | number | 汉字优先级 |

下面是各个属性的获取方法：

+ `charId` 生成可以使用项目目录下的`tools`工具，该工具会自动生成8位唯一`ID`，并且和已有数据做查重，确保ID唯一性，使用方法如下：

```bash
cd tools && npm install && npm run generate-char-id
# 输出结果：新的charId: 47ff0467
```
+ `unicodeCodePoint、pinYinChars、splitChars` 可以前往[字海网](http://www.yedict.com/)查询得到

![插入图片](https://mdn.alipayobjects.com/huamei_2fq7mt/afts/img/A*0zzHTqBj1xMAAAAAAAAAAAAADh58AQ/original)

+ `weight` 是字符优先级，优先级越高在用户输入的时候就会被优先推荐，这个值通常反映了字符的常用程度，默认是`10`


> ！！！Z字库数据还有一个`fontUrl`的属性，该字段是输入组件自动加载的生僻字字体cdn地址，在生成新的Z字库字体文件后，需要更新该地址（自行上传字体文件并替换该地址），否则输入组件无法加载最新的字体，新加的汉字就没法在输入组件中展示、

## Z字库字体文件
新增了汉字后需要生成这些新增汉字相应的字体，新增的大部分汉字在[阿里巴巴普惠体3.0](https://fonts.alibabagroup.com/#/font)里应该都是有的（可以在`word/wps`这些软件中设置文本字体为普惠体，看看新增的汉字是否能正常展示，以此确定该汉字是否在普惠体中）.

下面我分别阐述新增的汉字**在普惠体存在字体**和**在普惠体不存在字体**两种情况的处理办法：

### 新增汉字字体在普惠体中
在完成**输入键盘元数据**的补充以后， 可以使用项目目录下的`tools`工具，该工具会根据你修改好的Z字库元数据从普惠体分离出新增汉字的字体并合并到原有字体中，使用方法如下：
```bash
cd tools && npm run generate-font

# 输出结果：
# 开始生成字体...
# 生存的字体包含如下汉字："𮧵 𬀩 𤩽 㑇 𣲗 𰵞 𠇔 冄 䒟 𡛓 𡝗 𪻐 ...
# 存在以下字体生成失败：""
```
执行脚本后 `demo/web/public/fonts/RareWordsFonts.ttf` 会被更新

你可以将 `demo/web` 运行起来并修改需要预览的汉字（`demo/web/docs/display/index.tsx`）来检测生成的字体是否生效，如果汉字能在预览demo里正常展示即代表生成的字体是可以使用的。

![字体预览](https://mdn.alipayobjects.com/huamei_2fq7mt/afts/img/A*pGqiQq6I6j4AAAAAAAAAAAAADh58AQ/original)

也可以使用 [开源字体预览工具](http://blog.luckly-mjw.cn/tool-show/iconfont-preview/index.html) 或者 [Glyph3](https://glyphsapp.com/buy) 这类字体管理工具预览字体，检查新增的汉字是否在最新的字体中。


### 新增汉字字体不在普惠体中
需要新增的汉字不在阿里巴巴普惠体中（比如鸟甲nia这种字），处理办法稍微有些麻烦，你需要自己创建一个字体并合并到Z字库字体中。

创建字体可以使用 [Glyph3](https://glyphsapp.com/buy) 或者 [FontCreator](https://www.high-logic.com/font-editor/fontcreator) ，
有一些开源工具也是可以创建字体的，比如 [fonteditor](https://github.com/ecomfe/fonteditor)，甚至可以将svg或者图片转化成字体，编辑字体的字形数据，最后导出一份字体ttf文件。

![字体编辑](https://mdn.alipayobjects.com/huamei_2fq7mt/afts/img/A*PU60TpUkjg8AAAAAAAAAAAAADh58AQ/original)

创建字体文件以后，可以使用项目目录下的`tools`工具将新增的字体合并到Z字库字体中，使用之前您需要修改 `tools/mergeFont.js` 里的待合并字体的存储地址 `tempFontFilePath`，修改之后使用方法如下：
```bash
cd tools && npm run merge-font

# 输出结果：
# 开始合并字体...
# 合并后的字体存放地址：xxx
```

> !!! 如果自己添补了新的汉字，需要在生成新的字体后将字体上传到cdn，并在使用的时候替换掉官方的字体cdn地址。
> `https://mdn.alipayobjects.com/huamei_seif62/afts/file/A*63IqSLzYAdEAAAAAAAAAAAAADh18AQ/RareWordsFonts-v1.0.14.ttf` 的地址是蚂蚁Z字库的官方字体CDN地址，定期更新，不包含您自己添加的新增的汉字的字体。

## 后端数据库SQL文件

最后一步就是生成Z字库后端数据库SQL插入指令，然后删除`docker`运行起来的`mysql`实例，并重新启动`docker`即可，你可以使用项目目录下的 `tools/generateSql.js` 工具生成SQL指令，使用方法如下：
```bash
cd tools && npm run generate-sql

# 输出结果：
# 开始生成SQL...
# 新增字符的SQL：
# INSERT INTO `rare_characters` VALUES ('2023091200001','802cdf67','UNICODE','𫰠','2BC20','undefined','','','','10','"{""ncr_code_hex"":""2BC20""}"',2023-09-12 16:19:38.975,2023-09-12 16:19:38.975);
# INSERT INTO `rare_characters` VALUES ('2023091200002','802cdf67','PINYIN','DANG','','','1','','','10','',2023-09-12 16:19:38.975,2023-09-12 16:19:38.975);
# INSERT INTO `rare_characters` VALUES ('2023091200003','802cdf67','SPLIT','女当','','','','','','10','',2023-09-12 16:19:38.975,2023-09-12 16:19:38.975);

# SQL更新后存放地址：xxx
```

最后就是登录 `docker` 删除mysql实例并重新执行`docker-compose up`，这样本地数据库会重新执行最新的sql指令，达到更新本地数据库的目的：

![删除sql实例](https://mdn.alipayobjects.com/huamei_seif62/afts/img/A*BBwfTrquJXAAAAAAAAAAAAAADh18AQ/original)
