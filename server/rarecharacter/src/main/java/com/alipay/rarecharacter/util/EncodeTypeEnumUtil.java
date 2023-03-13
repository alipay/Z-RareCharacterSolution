package com.alipay.rarecharacter.util;

import org.apache.commons.lang3.StringUtils;
import com.alipay.rarecharacter.core.model.enums.EncodeTypeEnum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author huyibing
 */
public class EncodeTypeEnumUtil {

    /**
     * 原字的编码类型
     */
    private static final List<EncodeTypeEnum> ALL_ORIGINAL_ENCODE_TYPE_ENUMS = Arrays
            .asList(EncodeTypeEnum.PUA,
                    EncodeTypeEnum.UNICODE,
                    EncodeTypeEnum.TRADITIONAL
            );

    /**
     * 原字的UNICODE编码类型
     */
    private static final List<EncodeTypeEnum> UNICODE_ORIGINAL_ENCODE_TYPE_ENUMS = Arrays
            .asList( EncodeTypeEnum.UNICODE);

    /**
     * 原字的PUA编码类型
     */
    private static final List<EncodeTypeEnum> PUA_ORIGINAL_ENCODE_TYPE_ENUMS = Arrays
            .asList( EncodeTypeEnum.PUA);

    /**
     * 替代字的编码类型
     */
    private static final List<EncodeTypeEnum> ALL_REPLACE_ENCODE_TYPE_ENUMS = Arrays
            .asList(EncodeTypeEnum.PINYIN,
                    EncodeTypeEnum.SPLIT
            );


    /**
     * 获取所有的编码类型
     *
     * @return
     */
    public static List<EncodeTypeEnum> getAllEncodeTypeEnums() {
        List<EncodeTypeEnum> allEncodeTypeEnums = new ArrayList<EncodeTypeEnum>();
        allEncodeTypeEnums.addAll(getAllOriginalEncodeTypeEnums());
        allEncodeTypeEnums.addAll(getAllReplaceEncodeTypeEnums());
        return allEncodeTypeEnums;
    }

    /**
     * 获取所有原字的编码类型
     *
     * @return
     */
    public static List<EncodeTypeEnum> getAllOriginalEncodeTypeEnums() {
        return ALL_ORIGINAL_ENCODE_TYPE_ENUMS;
    }


    /**
     * 获取所有unicode原字的编码类型
     *
     * @return
     */
    public static List<EncodeTypeEnum> getUnicodeOriginalEncodeTypeEnums() {
        return UNICODE_ORIGINAL_ENCODE_TYPE_ENUMS;
    }

    /**
     * 获取所有pua原字的编码类型
     *
     * @return
     */
    public static List<EncodeTypeEnum> getPuaOriginalEncodeTypeEnums() {
        return PUA_ORIGINAL_ENCODE_TYPE_ENUMS;
    }



    /**
     * 获取所有替代字的编码类型
     *
     * @return
     */
    public static List<EncodeTypeEnum> getAllReplaceEncodeTypeEnums() {
        return ALL_REPLACE_ENCODE_TYPE_ENUMS;
    }



    public static List<String> convertEncodeTypeEnums2Strings(List<EncodeTypeEnum> encodeTypeEnums) {
        List<String> encodeTypes = new LinkedList<String>();
        for (EncodeTypeEnum encodeType : encodeTypeEnums) {
            encodeTypes.add(encodeType.getCode());
        }
        return encodeTypes;
    }

    public static EncodeTypeEnum convertString2EncodeTypeEnum(String encodeType) {
        for (EncodeTypeEnum encodeTypeEnum : getAllEncodeTypeEnums()) {
            if (StringUtils.equalsIgnoreCase(encodeTypeEnum.getCode(), encodeType)) {
                return encodeTypeEnum;
            }
        }
        return null;
    }

    /**
     * 是否姓名服务支持的编码类型
     *
     * @return
     */
    public static boolean isRareNameSupportEncodeType(String encodeType) {
        for (EncodeTypeEnum value : EncodeTypeEnum.values()) {
            if (StringUtils.equalsIgnoreCase(value.getCode(), encodeType)) {
                return true;
            }
        }
        return false;
    }
}

