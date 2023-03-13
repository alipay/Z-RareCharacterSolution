package com.alipay.rarecharacter.util;

import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 生僻字姓名工具类
 *
 * @author huyibing
 * @version $Id: NameUtil.java, v 0.1 2022年05月06日 下午14:23 huyibing Exp $
 */
public class NameUtil {

    /**
     * 全量汉字正则
     * 基本汉字（20902个）4E00-9FA5
     * 基本汉字补充（74个）9FA6-9FEF
     * 扩展A	（6582个）	3400-4DB5
     * 扩展B	（42711个）	20000-2A6D6
     * 扩展C	（4149个）	2A700-2B734
     * 扩展D	（222个）	2B740-2B81D
     * 扩展E	（5762个）	2B820-2CEA1
     * 扩展F	（7473个）	2CEB0-2EBE0
     * 扩展G	（4939个）	30000-3134A
     */
    public static final Pattern ALL_CHINESE_PATTERN = Pattern.compile("([\u4e00-\u9fa5]|[\u9fa6-\u9fef]|[\u3400-\u4db5]|[\ue000-\uf8ff]|[\\x{20000}-\\x{2A6D6})] |[\\x{2A700}-\\x{2B734})]|[\\x{2B740}-\\x{2B81D})]|[\\x{2B820}-\\x{2CEA1})]|[\\x{2CEB0}-\\x{2EBE0})]|[\\x{30000}-\\x{3134A})])");

    /**
     * 字符串全部为汉字校验正则
     */
    public static final Pattern STRING_ALL_CHINESE_PATTERN = Pattern.compile("^([\u4e00-\u9fa5]|[\u9fa6-\u9fef]|[\u3400-\u4db5]|[\ue000-\uf8ff]|[\\x{20000}-\\x{2A6D6})] |[\\x{2A700}-\\x{2B734})]|[\\x{2B740}-\\x{2B81D})]|[\\x{2B820}-\\x{2CEA1})]|[\\x{2CEB0}-\\x{2EBE0})]|[\\x{30000}-\\x{3134A})])+$");

    /**
     * BMP平面汉字正则
     */
    public static final Pattern BMP_CHINESE_PATTERN = Pattern.compile("[\u4e00-\u9fef\u3400-\u4db5\ue000-\uf8ff]");

    /**
     * BMP平面汉字正则
     */
    public static String BMP_CHINESE = "[\u4e00-\u9fef\u3400-\u4dff\ue000-\uf8ff]";

    /**
     * GBK汉字正则
     */
    public static final Pattern GBK_CHINESE_PATTERN = Pattern.compile("[\u4e00-\u9fa5]");

    /**
     * GBK汉字正则
     */
    public static String GBK_CHINESE = "[\u4e00-\u9fa5]";

    /**
     * 非BMP平面汉字正则
     */
    public static String NOT_BMP_CHINESE = "[^\u4e00-\u9fef\u3400-\u4dff\ue000-\uf8ff]";

    /**
     * pua和unicode生僻字, 注释部分需要JAVA1.7及更高版本支持
     */
    public static String PUA_AND_UNICODE_RARE_CHINESE = "([\ue000-\ufad9]|[\u9fa6-\u9fef]|[\u3400-\u4dff]|[\\x{20000}-\\x{2FFFD}]|[\\x{30000}-\\x{3FFFD}])";

    /**
     * pua生僻字正则
     */
    public static Pattern PUA_RARE_CHINESE = Pattern.compile("[\ue000-\ufad9]");

    /**
     * unicode生僻字正则, 注释部分需要JAVA1.7及更高版本支持
     */
    public static Pattern UNICODE_RARE_CHINESE = Pattern.compile("([\u9fa6-\u9fef]|[\u3400-\u4dff]|[\\x{20000}-\\x{2FFFD}]|[\\x{30000}-\\x{3FFFD}])");

    /**
     * 大陆姓名不能包含的特殊字符正则
     * \u0040  --> @
     * \u0021  --> !     英文感叹号
     * \uff01 --> ！     中文感叹号
     * \u0025  --> %
     * \u002a  --> *
     * \u0060  --> `
     * \u007e  --> ~
     */
    public static final Pattern INVALID_MAINLAND_NAME_PATTERN = Pattern.compile(".*[\\*|\u0040|\u0021|\uff01|\u0025\u002a|\u0060|\u007e].*$");

    /**
     * 是生僻字特殊字符正则
     * \uff1f --> ？     中文问号
     * \u003f --> ?      英文问号
     * \uff08 --> （     中文括号
     * \uff09 --> ）     中文括号
     * \u0028 --> (      英文括号
     * \u0029 --> )      英文括号
     */
    public static final Pattern SPECIAL_CHARACTER_PATTERN = Pattern.compile(".*[\\?|\\*|\uff1f|\u003f|\u0028|\u0029|\uff08|\uff09].*$");


    /**
     * 拆字生僻字连接符
     * \uff08 --> （     中文括号
     * \uff09 --> ）     中文括号
     * \u0028 --> (      英文括号
     * \u0029 --> )      英文括号
     * \u002B --> +      加号
     */
    public static final Pattern SPLIT_SIGN_PATTERN = Pattern.compile(".*[\\?|\\*|\u0028|\u0029|\uff08|\uff09|\u002B].*$");


    /**
     * 字母数字正则
     */
    public static final Pattern LETTER_NUM_PATTERN = Pattern.compile("^.*[a-zA-Z]{2,}[0-9]*.*$");

    /**
     * 字母正则
     */
    public static final Pattern LETTER_PATTERN = Pattern.compile("[a-zA-Z]");


    /**
     * 大写字母正则
     */
    public static final Pattern UPPER_LETTER_PATTERN = Pattern.compile("[A-Z]");


    /**
     * 包含小写字母正则
     */
    public static final Pattern LOWER_LETTERS_PATTERN = Pattern.compile("[a-z]");

    /**
     * 字母，数字正则
     */
    public static String LETTER_NUM = "[A-Za-z0-9]";

    /**
     * 字母正则
     */
    public static String LETTER = "[A-Za-z]";

    /**
     * 数字正则
     */
    public static String NUM = "[0-9]";

    /**
     * 数字正则
     */
    public static final Pattern NUM_PATTERN = Pattern.compile("[0-9]");

    /**
     * 空格
     */
    private final static String BLANK = " ";

    /**
     * 中圆点
     */
    private final static String MIDDLE_DOT = "·";

    /**
     * 下圆点
     */
    private final static String LOWER_DOT = ".";

    /**
     * 中文句号
     */
    private final static String FULL_STOP = "。";

    /**
     * 字符串是否全部为汉字
     *
     * @param in
     * @return
     */
    public static boolean isAllChinese(String in) {
        if (StringUtils.isBlank(in)) {
            return false;
        }
        return STRING_ALL_CHINESE_PATTERN.matcher(in).find();
    }

    /**
     * 是否合法的大陆姓名
     *
     * @param certName
     * @return
     */
    public static boolean isValidCertName(String certName) {
        if (StringUtils.isBlank(certName)) {
            return false;
        }

        // 不含非法字符
        return !INVALID_MAINLAND_NAME_PATTERN.matcher(certName).find();
    }

    /**
     * 是否生僻字姓名
     *
     * @param certName
     * @return
     */
    public static boolean isRareName(String certName) {
        if (StringUtils.isBlank(certName)) {
            return false;
        }
        return isContainOneChinese(certName)
                && (isContainSpecialCharacter(certName)
                || isContainOriginalRareCharacter(certName));
    }

    /**
     * 是否包含生僻字特殊字符
     *
     * @param certName
     * @return
     */
    public static boolean isContainSpecialCharacter(String certName) {
        return (SPECIAL_CHARACTER_PATTERN.matcher(certName).find()
                || LETTER_NUM_PATTERN.matcher(certName).find());
    }

    /**
     * 是否包含生僻字原字汉字字符， 如?
     *
     * @param certName
     * @return
     */
    public static boolean isContainOriginalRareCharacter(String certName) {
        return isContainUnicodeOriginalRareCharacter(certName) || isContainPuaOriginalRareCharacter(certName);
    }

    /**
     * 是否包含unicode生僻字原字汉字字符， 如?
     *
     * @param certName
     * @return
     */
    public static boolean isContainUnicodeOriginalRareCharacter(String certName) {
        if (StringUtils.isBlank(certName)) {
            return false;
        }

        return UNICODE_RARE_CHINESE.matcher(certName).find();
    }

    /**
     * 是否包含pua生僻字原字汉字字符， 如?
     *
     * @param certName
     * @return
     */
    public static boolean isContainPuaOriginalRareCharacter(String certName) {
        if (StringUtils.isBlank(certName)) {
            return false;
        }

        return PUA_RARE_CHINESE.matcher(certName).find();
    }

    /**
     * 是否包含一个GBK常规汉字
     *
     * @param certName
     * @return
     */
    public static boolean isContainGBKChinese(String certName) {
        if (StringUtils.isBlank(certName)) {
            return false;
        }
        return GBK_CHINESE_PATTERN.matcher(certName).find();
    }

    /**
     * 是否包含至少一个汉字
     *
     * @param certName
     * @return
     */
    public static boolean isContainOneChinese(String certName) {
        if (StringUtils.isBlank(certName)) {
            return false;
        }
        return ALL_CHINESE_PATTERN.matcher(certName).find();
    }


    /**
     * 是否包含PINYIN
     *
     * @param certName
     * @return
     */
//    public static boolean isContainPinyin(String certName) {
//        if (StringUtils.isBlank(certName)) {
//            return false;
//        }
//        return INCLUDE_LETTER_REGEX.matcher(certName).find();
//    }

    /**
     * 是否包含PINYIN
     *
     * @param certName
     * @return
     */
    public static boolean isContainPinyin(String certName) {
        if (StringUtils.isBlank(certName)) {
            return false;
        }
        return LETTER_PATTERN.matcher(certName).find();
    }

    /**
     * 是否包含大写拼音
     *
     * @param certName
     * @return
     */
    public static boolean isContainUpperPinyin(String certName) {
        if (StringUtils.isBlank(certName)) {
            return false;
        }
        return UPPER_LETTER_PATTERN.matcher(certName).find();
    }

    /**
     * 是否包含小写拼音
     *
     * @param certName
     * @return
     */
    public static boolean isContainLowerPinyin(String certName) {
        if (StringUtils.isBlank(certName)) {
            return false;
        }
        return LOWER_LETTERS_PATTERN.matcher(certName).find();
    }


    /**
     * 是否包含数字
     *
     * @param certName
     * @return
     */
    public static boolean isContainNum(String certName) {
        if (StringUtils.isBlank(certName)) {
            return false;
        }
        return NUM_PATTERN.matcher(certName).find();
    }

    /**
     * 是否包含拆字连接符号
     *
     * @param certName
     * @return
     */
    public static boolean isContainSplitSign(String certName) {
        if (StringUtils.isBlank(certName)) {
            return false;
        }
        return SPLIT_SIGN_PATTERN.matcher(certName).find();
    }

    /**
     * 拆字连接符连接的汉字
     *
     * @param certName
     * @return
     */
    public static String getSplitSignLinkedChars(String certName) {
        if (StringUtils.isBlank(certName)) {
            return "";
        }

        String retName = certName;

        if (retName.contains("(") && retName.contains(")")) {
            if (StringUtils.indexOf(retName, "(") + 1 <= retName.length() && StringUtils.indexOf(retName, ")") <= retName.length()) {
                retName = StringUtils.substring(retName, StringUtils.indexOf(retName, "(") + 1, StringUtils.indexOf(retName, ")"));
            }
        }

        if (retName.contains("（") && retName.contains("）")) {
            if (StringUtils.indexOf(retName, "（") + 1 <= retName.length() && StringUtils.indexOf(retName, "）") <= retName.length()) {
                retName = StringUtils.substring(retName, StringUtils.indexOf(retName, "（") + 1, StringUtils.indexOf(retName, "）"));
            }
        }

        if (retName.contains("+")) {
            if ((StringUtils.indexOf(retName, "+") - 1 >= 0) && (StringUtils.indexOf(retName, "+") + 1 <= retName.length())) {
                String tmp = StringUtils.substring(retName, StringUtils.indexOf(retName, "+") - 1, StringUtils.indexOf(retName, "+") + 2);
                retName = StringUtils.replace(tmp, "+", "");
            }
        }
        return retName;
    }

    /**
     * 将拆字部分替换成sign
     *
     * @param certName
     * @return
     */
    public static String signSplitPosition(String certName, String sign) {
        if (StringUtils.isBlank(certName)) {
            return "";
        }
        String retName = certName;
        String splitedChars = getSplitSignLinkedChars(retName);

        retName = removeSpecialCharacters(retName);
        if (!StringUtils.equalsIgnoreCase(retName, splitedChars)) {
            return StringUtils.replace(retName, splitedChars, sign);
        }
        return certName;
    }

    /**
     * 汉字姓名按顺序拆开，1个字符一组
     * 如 张wei3, 拆成[张,w,e,i,3]
     *
     * @param name
     * @return
     */
    public static List<String> splitNameEachOneCharacter(String name) {
        List<String> retList = new ArrayList<String>();
        char[] chars = name.toCharArray();
        for (int i = 0; i < chars.length; i += 1) {
            String s1 = String.valueOf(chars[i]);
            retList.add(s1);
        }
        return retList;
    }


    /**
     * 汉字姓名按顺序拆开，2个字符一组
     * 如 张韦华三, 拆成 [张韦,韦华,华三]
     *
     * @param name
     * @return
     */
    public static List<String> splitNameEachTwoCharacters(String name) {
        List<String> retList = new ArrayList<String>();

        char[] chars = name.toCharArray();
        if (chars.length == 1) {
            retList.add(name);
            return retList;
        }

        for (int i = 0; i < chars.length; i += 1) {
            String s1 = String.valueOf(chars[i]);
            if (i + 1 < chars.length) {
                s1 = s1.concat(String.valueOf(chars[i + 1]));
            }
            if (s1.length() == 2) {
                retList.add(s1);
            }
        }
        return retList;
    }

    /**
     * 去除中文姓名中的特殊字符
     * 如 中文括号，英文括号，空格，中圆点，下圆点等
     *
     * @param name
     * @return
     */
    public static String removeSpecialCharacters(String name) {
        if (StringUtils.isBlank(name)) {
            return "";
        }
        name = StringUtils.replace(name, "-", "");
        name = StringUtils.replace(name, "+", "");
        name = StringUtils.replace(name, "(", "");
        name = StringUtils.replace(name, ")", "");
        name = StringUtils.replace(name, "（", "");
        name = StringUtils.replace(name, "）", "");
        name = StringUtils.replace(name, ".", "");
        name = StringUtils.replace(name, "·", "");
        name = StringUtils.replace(name, "。", "");
        name = StringUtils.replace(name, " ", "");
        return name;
    }


    /**
     * 去除中文姓名中的字母，数字
     *
     * @param name
     * @return
     */
    public static String removeLettersAndNums(String name) {
        if (StringUtils.isBlank(name)) {
            return "";
        }
        name = name.replaceAll(LETTER_NUM, "");
        return name;
    }

    /**
     * 去除中文姓名中的数字
     *
     * @param name
     * @return
     */
    public static String removeNums(String name) {
        if (StringUtils.isBlank(name)) {
            return "";
        }
        name = name.replaceAll(NUM, "");
        return name;
    }

    /**
     * 去除中文姓名中的字母
     *
     * @param name
     * @return
     */
    public static String removeLetters(String name) {
        if (StringUtils.isBlank(name)) {
            return "";
        }
        name = name.replaceAll(LETTER, "");
        return name;
    }

    /**
     * 去除中文姓名中的特殊字符
     * 如 字母，数字，中文括号，英文括号，空格，中圆点，下圆点等
     *
     * @param name
     * @return
     */
    public static String removeSpecialCharactersAndLettersAndNums(String name) {
        if (StringUtils.isBlank(name)) {
            return "";
        }
        name = removeLettersAndNums(name);
        name = removeSpecialCharacters(name);
        return name;
    }

    /**
     * 去除非汉字（保留汉字部分）
     * 如去除 字母，数字，中文括号，英文括号，空格，中圆点，下圆点等
     *
     * @param name
     * @return
     */
    public static String removeNotBMPChineseCharacters(String name) {
        if (StringUtils.isBlank(name)) {
            return "";
        }
        return name.replaceAll(NOT_BMP_CHINESE, "");
    }

    /**
     * 去除BPM平面汉字
     *
     * @param name
     * @return
     */
    public static String removeBMPChineseCharacters(String name) {
        if (StringUtils.isBlank(name)) {
            return "";
        }
        return name.replaceAll(BMP_CHINESE, "");
    }

    /**
     * 去除GBK汉字
     *
     * @param name
     * @return
     */
    public static String removeGBKChineseCharacters(String name) {
        if (StringUtils.isBlank(name)) {
            return "";
        }
        return name.replaceAll(GBK_CHINESE, "");
    }

    /**
     * 用sign标记非GBK汉字位置
     *
     * @param name
     * @return
     */
    public static String signNotGBKChinesePosition(String name, String sign) {
        if (StringUtils.isBlank(name)) {
            return "";
        }
        return name.replaceAll(PUA_AND_UNICODE_RARE_CHINESE, sign);
    }


    /**
     * 用sign标记拼音位置
     *
     * @param name
     * @return
     */
    public static String signPinyinPosition(String name, String sign) {
        if (StringUtils.isBlank(name)) {
            return "";
        }

        name = removeSpecialCharacters(name);
        name = name.replaceAll(LETTER_NUM, sign);
        while (name.contains(sign + sign)) {
            name = name.replaceAll(sign + sign, sign);
        }
        return name;
    }

    /**
     * 字符串是否全是汉字
     *
     * @param in
     * @return
     */
    public static boolean isStringAllChinese(String in) {
        if (StringUtils.isBlank(in)) {
            return false;
        }

        Matcher matcher = ALL_CHINESE_PATTERN.matcher(in);
        if (matcher.find()) {
            return true;
        }
        return false;
    }

    /**
     * 是否一个汉字
     *
     * @param character
     * @return
     */
    public static boolean isOneBMPChinese(String character) {
        if (StringUtils.isBlank(character) || character.length() != 1) {
            return false;
        }

        Matcher matcher = BMP_CHINESE_PATTERN.matcher(character);
        if (matcher.find()) {
            return true;
        }
        return false;
    }


    /**
     * 是否一个GBK汉字
     *
     * @param character
     * @return
     */
    public static boolean isOneGBKChinese(String character) {
        if (StringUtils.isBlank(character) || character.length() != 1) {
            return false;
        }

        Matcher matcher = GBK_CHINESE_PATTERN.matcher(character);
        if (matcher.find()) {
            return true;
        }
        return false;
    }

    /**
     * 去除两个姓名前后位置相同的汉字
     * 如 张wei，张韦华  =>  wei，韦华
     *
     * @param sourceName
     * @param targetName
     * @return
     */
    public static List<String> removeSameChineseCharacter(String sourceName, String targetName) {
        List<String> retList = new ArrayList<String>();
        if (StringUtils.isBlank(sourceName) || StringUtils.isBlank(targetName)) {
            retList.add(sourceName);
            retList.add(targetName);
            return retList;
        }

        //记录从前开始的位置相同的汉字
        List<String> sameChinese = new ArrayList<String>();
        for (int i = 0, j = 0; i < sourceName.length() && j < targetName.length(); i++, j++) {
            if (StringUtils.isNotBlank(String.valueOf(sourceName.charAt(i)))
                    && isOneBMPChinese(String.valueOf(sourceName.charAt(i)))
                    && sourceName.charAt(i) == targetName.charAt(j)) {
                sameChinese.add(String.valueOf(sourceName.charAt(i)));
            }
        }

        //记录从后开始的位置相同的汉字
        for (int i = sourceName.length() - 1, j = targetName.length() - 1; i > 0 && j > 0; i--, j--) {
            if (StringUtils.isNotBlank(String.valueOf(sourceName.charAt(i)))
                    && isOneBMPChinese(String.valueOf(sourceName.charAt(i)))
                    && sourceName.charAt(i) == targetName.charAt(j)) {
                sameChinese.add(String.valueOf(sourceName.charAt(i)));
            }
        }

        //去除从前后开始的位置相同的汉字
        String retSourceName = sourceName;
        String retTargetName = targetName;
        if (sameChinese.size() > 0) {
            for (String same : sameChinese) {
                retSourceName = StringUtils.replace(retSourceName, same, "");
                retTargetName = StringUtils.replace(retTargetName, same, "");
            }
        }
        retList.add(retSourceName);
        retList.add(retTargetName);
        return retList;
    }


    /**
     * 少数民族名字是否模糊相等
     *
     * @param targetName
     * @param currentName
     * @return
     */
    public static boolean isMatchMinorityName(String targetName, String currentName) {
        targetName = StringUtils.replace(targetName, BLANK, "");
        targetName = StringUtils.replace(targetName, MIDDLE_DOT, "");
        targetName = StringUtils.replace(targetName, LOWER_DOT, "");
        targetName = StringUtils.replace(targetName, FULL_STOP, "");

        currentName = StringUtils.replace(currentName, BLANK, "");
        currentName = StringUtils.replace(currentName, MIDDLE_DOT, "");
        currentName = StringUtils.replace(currentName, LOWER_DOT, "");
        currentName = StringUtils.replace(currentName, FULL_STOP, "");

        if (StringUtils.isBlank(targetName) || StringUtils.isBlank(currentName)) {
            return false;
        }

        return StringUtils.equals(targetName, currentName);
    }

    /**
     * 是否包含英文括号
     *
     * @param name
     * @return
     */
    public static boolean isContainEnglishBrackets(String name) {
        if (StringUtils.isBlank(name)) {
            return false;
        }

        return name.contains("(") && name.contains(")");
    }

    /**
     * 是否包含中文括号
     *
     * @param name
     * @return
     */
    public static boolean isContainChineseBrackets(String name) {
        if (StringUtils.isBlank(name)) {
            return false;
        }

        return name.contains("（") && name.contains("）");
    }

    /**
     * 加英文括号
     *
     * @param name
     * @return
     */
    public static String addEnglishBrackets(String name) {
        if (StringUtils.isBlank(name)) {
            return "";
        }
        return "(" + name + ")";
    }

    /**
     * 加中文括号
     *
     * @param name
     * @return
     */
    public static String addChineseBrackets(String name) {
        if (StringUtils.isBlank(name)) {
            return "";
        }
        return "（" + name + "）";
    }
}

