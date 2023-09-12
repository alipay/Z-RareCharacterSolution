# Z字库新增字符指南

Z 字库关于字符数据有三个方面：

1. 输入键盘元数据，录入字符拼音和拆字信息 （`frontend/utils/src/contants/zdata-local.ts`）
2. Z字库字体文件，包含了所有字符的字体 （`demo/web/public/fonts/RareWordsFonts.ttf`）
3. 后端数据库 `SQL` 文件，存放新的字符插入 `SQL` 代码 （`demo/.docker/database/init.sql`）

因此新增字库也就是修改以上三个文件，下面我逐一介绍如何以上三个文件应该如何新增字符。

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
| `charId` | string | 字符唯一 ID |
| `unicodeChar` | string | 字符原字 |
| `unicodeCodePoint` | string | 字符 unicode 编码 |
| `pinYinChars` | {char: string}[] | 拼音，支持多个 |
| `splitChars` | {char: string}[] | 拆字，支持多个 |
| `weight` | number | 字符优先级 |

下面是各个属性的获取方法：

+ `charId` 生成可以使用项目目录下的`tools`工具，该工具会自动生成8位唯一`ID`，并且和已有数据做查重，确保ID唯一性，使用方法如下：

```bash
cd tools && npm install && npm run generate-char-id
# 输出结果：新的charId: 47ff0467
```
+ `unicodeCodePoint、pinYinChars、splitChars` 可以前往[字海网](http://www.yedict.com/)查询得到

![插入图片](https://mdn.alipayobjects.com/huamei_2fq7mt/afts/img/A*0zzHTqBj1xMAAAAAAAAAAAAADh58AQ/original)

+ `weight` 是字符优先级，优先级越高在用户输入的时候就会被优先推荐，这个值通常反映了字符的常用程度，默认是`10`


> Z字库还有一个`fontUrl`的属性，该字段是输入法自动加载的生僻字字体cdn地址，这个在生成新的Z字库字体文件后，需要更新该地址

## Z字库字体文件
因为新增了字符，就需要找到这些新增字符相应的字体，新增的大部分字符在[阿里巴巴普惠体3.0](https://fonts.alibabagroup.com/#/font)里应该都是有的，下面我分别阐述在普惠体存在字体和在普惠体不存在字体两种情况的处理办法：

### 新增字符字体在普惠体中
在完成**输入键盘元数据**的补充以后， 可以使用项目目录下的`tools`工具，该工具会从普惠体分离出新增字符的字体并合并到原有字体中，使用方法如下：
```bash
cd tools && npm run generate-font

# 输出结果：
# 开始生成字体...
# 生存的字体包含如下汉字："𮧵 𬀩 𤩽 㑇 𣲗 𰵞 𠇔 冄 䒟 𡛓 𡝗 𪻐 ...
# 存在以下字体生成失败：""
```
执行脚本后 `demo/web/public/fonts/RareWordsFonts.ttf` 会被更新

你可以将 `demo/web` 运行起来并修改需要预览的字符（`demo/web/docs/display/index.tsx`）来检测生成的字体是否生效，字符能在预览demo里正常展示即可。

![字体预览](https://mdn.alipayobjects.com/huamei_2fq7mt/afts/img/A*pGqiQq6I6j4AAAAAAAAAAAAADh58AQ/original)

也可以使用 [开源字体预览工具](http://blog.luckly-mjw.cn/tool-show/iconfont-preview/index.html) 或者 [Glyph3](https://glyphsapp.com/buy) 这类字体管理工具预览字体，检查新增的字符是否在字体中


### 新增字符字体不在普惠体中
需要新增的字符不在阿里巴巴普惠体中（比如鸟甲nia这种字），这个会稍微有些麻烦，你需要自己创建一个字体并合并到Z字库字体中。

创建字体可以使用 [Glyph3](https://glyphsapp.com/buy) 或者 [FontCreator](https://www.high-logic.com/font-editor/fontcreator) ，
有一些开源工具也是可以创建字体的，比如 [fonteditor](https://github.com/ecomfe/fonteditor)，甚至可以将svg或者图片转化成字体，编辑字体的字形数据，总之你需要办法得到一份字体数据。

![字体编辑](https://mdn.alipayobjects.com/huamei_2fq7mt/afts/img/A*PU60TpUkjg8AAAAAAAAAAAAADh58AQ/original)

创建字体以后，可以使用项目目录下的`tools`工具将新增的字体合并到Z字库字体中，使用之前需要自己修改下 `tempFontFilePath`，使用方法如下：
```bash
cd tools && npm run merge-font

# 输出结果：
# 开始合并字体...
# 合并后的字体存放地址：xxx
```

> !!! 生成最新的字体后需要自行将字体上传到cdn，如果自己添补了新的字符，需要在使用的时候替换掉官方的字体cdn地址。
> `https://mdn.alipayobjects.com/huamei_2fq7mt/afts/file/A*hHI6SahdSqMAAAAAAAAAAAAADh58AQ/RareWordsFonts-v1.0.13.ttf` 的地址是蚂蚁Z字库的官方字体CDN地址，定期更新，不会那么及时。

## 后端数据库SQL文件

最后一步就是生成Z字库后端数据库SQL插入指令，然后登录本地sql服务器执行SQL命令即可，你可以使用项目目录 `tools` 工具生成SQL指令，使用方法如下：
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
