---
mobile: false
title: 姓名联想
order: 5
---

## 姓名联想

### 接口定义

- java 接口：_com.alipay.rarecharacter.api.RareNameApi#associateRareName_
- http 接口：*http://127.0.0.1:8088/rc/associateRareName?principalId=2088&name=%E5%BC%A0yan*

```java
/**
 * 姓名是否生僻字
 * @param principalId 业务id
 * @param name 姓名
 * @param serviceContext 服务上下文
 * @return
 */
public RareNameCommonResult associateRareName(String principalId, String name, ServiceContext serviceContext);
```

### 入参

| 参数           | 类型           | 含义                                                                 |
| -------------- | -------------- | -------------------------------------------------------------------- |
| principalId    | String         | 可选，主体 id，如：1 用户 user_id2 证件号码等                        |
| name           | String         | 主体姓名                                                             |
| serviceContext | ServiceContext | 可选，服务上下文可以放入申请的 scene，如{"scene":"alipay_bank_card"} |

### 出参

| 参数          | 类型                      | 含义                                                 |
| ------------- | ------------------------- | ---------------------------------------------------- |
| success       | boolean                   | 调用是否成功                                         |
| retCode       | String                    | 业务结果码， 见下方 RareCharacterResultCodeEnum 枚举 |
| rareNameInfos | List&lt;RareNameInfo&gt;  | 联想出的姓名列表                                     |
| extResult     | Map&lt;String, Object&gt; | 结果扩展                                             |
| errorContext  | ErrorContext              | 错误上下文                                           |

```java
public enum RareCharacterResultCodeEnum {
  ILLEGAL_ARGUMENT("ILLEGAL_ARGUMENT", "参数错误"),
  SYSTEM_ERROR("SYSTEM_ERROR", "系统错误"),
}
```

### 结果示例

```json
{
  "success": true,
  "retCode": null,
  "rareNameInfos": [
    { "name": "张YAN", "remarks": "大写拼音" },
    { "name": "张yan", "remarks": "小写拼音" },
    { "name": "张（YAN）", "remarks": "中文括号+大写拼音" },
    { "name": "张（yan）", "remarks": "中文括号+小写拼音" },
    { "name": "张(YAN)", "remarks": "英文括号+大写拼音" },
    { "name": "张(yan)", "remarks": "英文括号+小写拼音" }
  ],
  "extResult": null
}
```

## 演示示例

<code inline="true" src="./demo/recommend/index.tsx" compact="true" iframe mobile="false"></code>

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
