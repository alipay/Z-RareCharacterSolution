package com.alipay.rarecharacter.core.model.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 生僻字姓名业务返回码
 *
 * @author gaunjin.hyb
 * @version $Id: RareCharacterResultCodeEnum.java, v 0.1 2022年05月06日 下午05:06 huyibing Exp $
 */
public enum RareCharacterResultCodeEnum {
    /** 是生僻字姓名 */
    IS_RARE_NAME("IS_RARE_NAME", "是生僻字姓名"),

    /** 不是生僻字姓名 */
    NOT_RARE_NAME("NOT_RARE_NAME", "不是生僻字姓名"),

    /** 可能是生僻字姓名 */
    POSSIBLE_RARE_NAME("POSSIBLE_RARE_NAME", "可能是生僻字姓名"),

    /** 是原字生僻字名字 */
    IS_ORIGINAL_RARE_NAME("IS_ORIGINAL_RARE_NAME", "是原字生僻字名字"),

    /** 是UNICODE原字生僻字名字 */
    IS_UNICODE_ORIGINAL_RARE_NAME("IS_UNICODE_ORIGINAL_RARE_NAME", "是UNICODE原字生僻字名字"),

    /** 是PUA原字生僻字名字 */
    IS_PUA_ORIGINAL_RARE_NAME("IS_PUA_ORIGINAL_RARE_NAME", "是PUA原字生僻字名字"),

    /** 是拼音生僻字名字 */
    IS_PINYIN_RARE_NAME("IS_PINYIN_RARE_NAME", "是拼音生僻字名字"),

    /** 是拆字生僻字名字 */
    IS_SPLIT_RARE_NAME("IS_SPLIT_RARE_NAME", "是拆字生僻字名字"),

    /** 可能是拆字生僻字名字 */
    POSSIBLE_SPLIT_RARE_NAME("POSSIBLE_SPLIT_RARE_NAME", "可能是拆字生僻字名字"),

    /** 姓名相同 */
    IS_SAME_RARE_NAME("IS_SAME_RARE_NAME", "姓名相同"),

    /** 姓名不相同 */
    NOT_SAME_RARE_NAME("NOT_SAME_RARE_NAME", "姓名不相同"),

    /** 可能相同 */
    POSSIBLE_SAME_RARE_NAME("POSSIBLE_SAME_RARE_NAME", "可能相同"),

    SUCCESS("SUCCESS", "业务成功"),

    ILLEGAL_ARGUMENT("ILLEGAL_ARGUMENT", "参数错误"),

    SYSTEM_ERROR("SYSTEM_ERROR", "系统错误"),
    ;

    /** 业务码 */
    private String code;

    /** 错误信息 */
    private String message;

    /**
     * 构造器
     *
     * @param code
     * @param message
     */
    private RareCharacterResultCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }


    /**
     * 通过错误码获取枚举
     *
     * @param code
     * @return
     */
    public static RareCharacterResultCodeEnum getByCode(String code) {
        for (RareCharacterResultCodeEnum enums : RareCharacterResultCodeEnum.values()) {
            if (StringUtils.equals(enums.code, code)) {
                return enums;
            }
        }
        return null;
    }

    /**
     * Getter method for property <tt>code</tt>.
     *
     * @return property value of code
     */
    public String getCode() {
        return code;
    }

    /**
     * Setter method for property <tt>code</tt>.
     *
     * @param code value to be assigned to property code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Getter method for property <tt>message</tt>.
     *
     * @return property value of message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Setter method for property <tt>message</tt>.
     *
     * @param message value to be assigned to property message
     */
    public void setMessage(String message) {
        this.message = message;
    }
}