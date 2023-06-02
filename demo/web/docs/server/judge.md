---
mobile: false
title: 姓名判断
order: 5
---

## 姓名判断

### 接口定义

- java 接口：_com.alipay.rarecharacter.api.RareNameApi#isRareName_
- http 接口：*http://127.0.0.1:8088/rc/isRareName?principalId=2088&name=%E5%BC%A0yan*

```java
/**
 * 姓名是否生僻字
 * @param principalId 业务id
 * @param name 姓名
 * @param serviceContext 服务上下文
 * @return
 */
public RareNameCommonResult isRareName(String principalId, String name, ServiceContext serviceContext);
```

### 入参

| 参数           | 类型           | 含义                                                                 |
| -------------- | -------------- | -------------------------------------------------------------------- |
| principalId    | String         | 可选，主体 id，如：1 用户 user_id2 证件号码等                        |
| name           | String         | 主体姓名                                                             |
| serviceContext | ServiceContext | 可选，服务上下文可以放入申请的 scene，如{"scene":"alipay_bank_card"} |

### 出参

| 参数         | 类型                      | 含义                                                |
| ------------ | ------------------------- | --------------------------------------------------- |
| success      | boolean                   | 调用是否成功                                        |
| retCode      | String                    | 业务结果码， 见下方枚举 RareCharacterResultCodeEnum |
| extResult    | Map&lt;String, Object&gt; | 结果扩展                                            |
| errorContext | ErrorContext              | 错误上下文                                          |

```java
public enum RareCharacterResultCodeEnum {
  /** 是特殊名字 */
  IS_RARE_NAME("IS_RARE_NAME", "是特殊名字"),

  /** 不是特殊名字 */
  NOT_RARE_NAME("NOT_RARE_NAME", "不是特殊名字"),

  /** 可能是特殊名字 */
  POSSIBLE_RARE_NAME("POSSIBLE_RARE_NAME", "可能是特殊名字"),

  ILLEGAL_ARGUMENT("ILLEGAL_ARGUMENT", "参数错误"),
  SYSTEM_ERROR("SYSTEM_ERROR", "系统错误"),
}
```

### 结果示例

```json
{
  "retCode": "IS_RARE_NAME",
  "extResult": null,
  "success": true,
  "errorContext": null
}
```

## 演示示例

<code inline="true" src="./demo/judge/index.tsx" compact="true" iframe mobile="false"></code>

<style>
  .token.unit {
    border: none;
    padding: 0;
    display: inline;
    min-height: unset;
    min-width: unset;
    flex-direction: unset;
  }

  iframe[title="dumi-previewer"] {
    height: 600px !important;
  }
<style>
