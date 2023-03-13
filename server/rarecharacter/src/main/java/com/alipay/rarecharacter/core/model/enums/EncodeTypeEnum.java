package com.alipay.rarecharacter.core.model.enums;


/**
 * 生僻字编码类型枚举类
 *
 * @author huyibing
 * @version $Id: EncodeTypeEnum.java, v 0.1 2022-05-06 下午04:29:05 huyibing Exp $
 */
public enum EncodeTypeEnum {

    /** 人口信息PUA编码 */
    PUA("PUA", "人口信息PUA编码"),

    /** UNICODE统一编码 */
    UNICODE("UNICODE", "UNICODE统一编码"),

    /** 拼音方式 */
    PINYIN("PINYIN", "拼音方式"),

    /** 拆字方式 */
    SPLIT("SPLIT", "拆字方式"),

    /** 繁简异体码 */
    TRADITIONAL("TRADITIONAL", "繁简异体码"),

    ;

    /** 编码 */
    String code;

    /** 描述 */
    String desc;

    /**
     * @param code
     * @param desc
     */
    private EncodeTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
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
     * Getter method for property <tt>desc</tt>.
     *
     * @return property value of desc
     */
    public String getDesc() {
        return desc;
    }



}