---
mobile: false
title: 姓名服务
order: 4
---

## 介绍

为了解决生僻字用户在各类场景下的姓名互通互认问题，Z 字库生僻字解决方案提供了各类关键姓名服务，使用者接入姓名服务能力后，可实现用户姓名在系统间互通互认。如业务需要做用户身份信息的联网核查，当使用用户输入的姓名无法核查通过时，可使用姓名转码服务，将转码后的姓名再次尝试联网核查。

## 包含的服务内容

- [姓名判断](./server/judge)，判断姓名是否含生僻字
- [姓名判同](./server/recommend)，判断姓名是否相同
- [姓名联想](./server/same)，比如输入张 yan，可联想出张（YAN），张 YAN 等
- [姓名转码](./server/transfer)，比如 Unicode 和 PUA 互转

## 通过 docker 快速部署

为帮助用户快速了解 Z-RareCharacterSolution 的服务能力，我们搭建了 docker 的示例，用户在几分钟内通过几个命令将 docker 在本地运行起来，以快速了解生僻字处理的各项能力。

### 下载代码

进入蚂蚁[开源网站](https://github.com/alipay/Z-RareCharacterSolution)，下载代码至本地

```
git clone https://github.com/alipay/Z-RareCharacterSolution.git
```

### 启动 demo

1. 检查本地是否安装 docker
   输入命令，若未安装，请至[docker 官网](https://www.docker.com)完成安装

```
docker version
```

2. 进入本地目录 Z-RareCharacterSolution/demo 目录，执行命令

```
docker-compose up
```

3. 浏览器打开本地地址 *http://127.0.0.1:80*
   正常展示页面，恭喜你已正常启动 demo，可体验 Z 字库生僻字解决方案提供的能力
   <br />
   demo 启动的同时我们也提供了一份本地的姓名服务 http 接口，比如判断是否为生僻字接口地址为 *http://127.0.0.1:8088/rc/isRareName?principalId=2088&name=张䶮*

## 自主集成接入

### 1. 环境准备

- JAVA： 1.7 及以上
- 数据库字符集设置，需支持 unicode 扩 A~G 的汉字存储：
  如 Mysql，CHARSET 设置为 utf8mb4
  如 OceanBase，CHARSET 设置为 utf8mb4

### 2. 数据准备

- 建立多编码映射字库表（纵表），设置 CHARSET = utf8mb4 ，建表语句示例：

```sql
CREATE TABLE `rare_characters` (
  `id` varchar(48) NOT NULL COMMENT '主键id',
  `char_id` varchar(48) NOT NULL COMMENT '唯一标识一个生僻字',
  `encode_type` varchar(48) NOT NULL COMMENT '编码类型',
  `code` varchar(48) NOT NULL COMMENT '编码',
  `code_point` varchar(48) DEFAULT NULL COMMENT '码点',
  `ncr_code` varchar(48) DEFAULT NULL COMMENT 'NCR编码',
  `tone` varchar(48) DEFAULT NULL COMMENT '编码类型为PINYIN时的音调',
  `pic` varchar(1024) DEFAULT NULL COMMENT '生僻字图片地址',
  `font` varchar(2048) DEFAULT NULL COMMENT '字体文件',
  `weight` varchar(10) DEFAULT NULL COMMENT '字符权重',
  `ext_info` varchar(1536) DEFAULT NULL COMMENT '扩展',
  `gmt_create` timestamp(6) NOT NULL COMMENT '创建时间',
  `gmt_modified` timestamp(6) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_code_encode_type` (`code`, `encode_type`, `tone`, `char_id`),
  KEY `idx_char_id` (`char_id`),
  KEY `idx_ncr_code_encode_type` (`ncr_code`, `encode_type`) ,
  KEY `idx_code_point_encode_type` (`code_point`, `encode_type`)
) DEFAULT CHARSET = utf8mb4 COMMENT = '常见姓名生僻字字库'
```

字库数据示例：

| id  | char_id  | encode_type | code | code_point | ncr_code         | tone | pic | font | weight | ext_info | gmt_create                 | gmt_modified               |
| --- | -------- | ----------- | ---- | ---------- | ---------------- | ---- | --- | ---- | ------ | -------- | -------------------------- | -------------------------- |
| 1   | d88cab09 | UNICODE     | 𮧵   | 2E9F5      | &#55418;&#56821; |      |     |      | 10     |          | 2020-01-01 10:00:00.000000 | 2020-01-01 10:00:00.000000 |
| 2   | d88cab09 | PUA         | 韦华 | E43B       | &#58427;         |      |     |      | 10     |          | 2020-01-01 10:00:00.000000 | 2020-01-01 10:00:00.000000 |
| 3   | d88cab09 | PINYIN      | WEI  |            |                  | 3    |     |      | 10     |          | 2020-01-01 10:00:00.000000 | 2020-01-01 10:00:00.000000 |
| 4   | d88cab09 | SPLIT       | 韦华 |            |                  |      |     |      | 10     |          | 2020-01-01 10:00:00.000000 | 2020-01-01 10:00:00.000000 |
| 5   | d88cab09 | TRADITIONAL | 韡   |            | &#38881;         |      |     |      | 10     |          | 2020-01-01 10:00:00.000000 | 2020-01-01 10:00:00.000000 |

> 公安系统 PUA 映射关系可联系方正字库获取

### 3. dal 层适配

因使用方采用的数据库差异，_rarecharacter_ 项目中的数据访问层为 SPI RareCharacterDAO，需使用方在自己系统中实现，实现方式参考[demo dal 实现](https://github.com/alipay/Z-RareCharacterSolution/blob/main/demo/server/src/main/java/com/example/rarecharacterdemo/dao/RareCharacterDAOImpl.java)。

### 4. 初始化字库数据

使用开源代码 rarecharacterDemo 中的[script](https://github.com/alipay/Z-RareCharacterSolution/blob/main/demo/server/src/main/java/com/example/rarecharacterdemo/InitRareCharactersData.java)，将字库 csv 文件导入到上面新建的表 rare_characters 中，点击[这里](https://mdn.alipayobjects.com/huamei_2fq7mt/afts/file/A*bHBUS76FOcUAAAAAAAAAAAAADh58AQ/z_data.v1.0.0.csv)下载字库 demo 数据。

### 5. 使用姓名服务

使用者在自己的业务项目中引入 rarecharacter 项目 jar 包，即可调用使用 RareNameApi。 使用方式可参考 rarecharacterdemo 示例项目

```ts
import com.alipay.rarecharacter.api.RareNameApi;

/**
 * Demo Controller
 */
@RestController
public class DemoController {
  /**
   * 生僻字服务api
   */
  @Autowired
  private RareNameApi rareNameApi;

   /**
    * 姓名是否生僻字
    */
  @RequestMapping(value = "/isRareName", method = RequestMethod.GET)
  public RareNameCommonResult isRareName(String name) {
      register();
      return rareNameApi.isRareName('001', name, {});
  }
}
```
