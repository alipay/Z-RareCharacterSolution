package com.alipay.rarecharacter.core.manager;

import com.alipay.rarecharacter.core.model.RareNameInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.alipay.rarecharacter.core.model.enums.EncodeTypeEnum;
import com.alipay.rarecharacter.core.model.enums.RareCharacterResultCodeEnum;
import com.alipay.rarecharacter.core.model.RareCharacter;
import com.alipay.rarecharacter.core.service.RareNameService;
import com.alipay.rarecharacter.util.EncodeTypeEnumUtil;
import com.alipay.rarecharacter.util.NameUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;

import static org.apache.commons.lang3.text.translate.CharSequenceTranslator.hex;

/**
 * @author huyibing
 */
@Component
public class RareNameTransferManagerImpl implements RareNameTransferManager {

    /**
     * logger
     */
    private final static Logger logger = LogManager
            .getLogger(RareNameTransferManagerImpl.class);


    @Autowired
    private RareNameManager rareNameManager;

    @Autowired
    private RareNameService rareNameService;

    private final String UNICODE_ORIGINAL = "UNICODE原字";
    private final String PUA_ORIGINAL = "PUA原字";
    private final String UPPER_PINYIN = "大写拼音";
    private final String LOWER_PINYIN = "小写拼音";
    private final String CHINESE_BRACKET = "中文括号";
    private final String ENGLISH_BRACKET = "英文括号";
    private final String TONE = "音调";
    private final String JOINT = "+";

    /**
     * 姓名转码
     *
     * @param principalId      业务id
     * @param name             姓名
     * @param targetEncodeType 目标编码
     * @return
     */
    @Override
    public List<String> transferRareName(String principalId, String name, String targetEncodeType) {
        List<String> retNames = new ArrayList<String>();

        // pua原字生僻字转码
        if (rareNameManager.isPuaRareName(principalId, name) == RareCharacterResultCodeEnum.IS_PUA_ORIGINAL_RARE_NAME) {
            retNames = transferPuaRareName(principalId, name, targetEncodeType);
            return retNames;
        }

        // unicode原字生僻字转码
        if (rareNameManager.isUnicodeRareName(principalId, name) == RareCharacterResultCodeEnum.IS_UNICODE_ORIGINAL_RARE_NAME) {
            retNames = transferUnicodeRareName(principalId, name, targetEncodeType);
            return retNames;
        }

        // 拼音生僻字转码
        if (rareNameManager.isPinyinRareName(principalId, name) == RareCharacterResultCodeEnum.IS_PINYIN_RARE_NAME) {
            retNames = transferPinyinRareName(principalId, name, targetEncodeType);
            return retNames;
        }

        // 拆字生僻字转码
        if (rareNameManager.isSplitRareName(principalId, name) == RareCharacterResultCodeEnum.IS_SPLIT_RARE_NAME ||
                rareNameManager.isSplitRareName(principalId, name) == RareCharacterResultCodeEnum.POSSIBLE_SPLIT_RARE_NAME) {
            retNames = transferSplitRareName(principalId, name, targetEncodeType);
            return retNames;
        }

        return retNames;
    }

    /**
     * 姓名转码，仅支持unicode和pua互转
     * 若输入unicode姓名，则转成pua姓名，若输入pua姓名，则转成unicode姓名
     * 若输入姓名（不存在pua或unicode时）或（Z字库未收录时），将输入姓名原样返回
     *
     * @param principalId 业务id
     * @param name        姓名
     * @return
     */
    @Override
    public String transferUniAndPuaRareName(String principalId, String name) {
        List<String> retNames = new ArrayList<String>();

        // pua原字生僻字转unicode
        if (rareNameManager.isPuaRareName(principalId, name) == RareCharacterResultCodeEnum.IS_PUA_ORIGINAL_RARE_NAME) {
            retNames = transferPuaRareName(principalId, name, EncodeTypeEnum.UNICODE.getCode());
        }

        // unicode原字生僻字转pua
        if (rareNameManager.isUnicodeRareName(principalId, name) == RareCharacterResultCodeEnum.IS_UNICODE_ORIGINAL_RARE_NAME) {
            retNames = transferUnicodeRareName(principalId, name, EncodeTypeEnum.PUA.getCode());
        }

        if (retNames.size() > 0) {
            return retNames.get(0);
        } else {
            return "";
        }
    }

    /**
     * unicode原字生僻字转码
     *
     * @param principalId      业务id
     * @param name             姓名
     * @param targetEncodeType 目标编码
     * @return
     */
    @Override
    public List<String> transferUnicodeRareName(String principalId, String name, String targetEncodeType) {
        List<String> transferRet = new ArrayList<String>();

        // unicode原字转unicode，直接返回
        if (StringUtils.equalsIgnoreCase(targetEncodeType, EncodeTypeEnum.UNICODE.getCode())) {
            transferRet.add(name);
            // unicode原字转pua，转码返回
        } else if (StringUtils.equalsIgnoreCase(targetEncodeType, EncodeTypeEnum.PUA.getCode())) {
            transferRet = transfer(principalId, name, EncodeTypeEnum.UNICODE.getCode(), EncodeTypeEnum.PUA.getCode());
        }
        return transferRet;
    }


    /**
     * unicode原字生僻字转码
     *
     * @param principalId      业务id
     * @param name             姓名
     * @param targetEncodeType 目标编码
     * @return
     */
    @Override
    public List<String> transferPuaRareName(String principalId, String name, String targetEncodeType) {
        List<String> transferRet = new ArrayList<String>();

        // pua原字转pua，直接返回
        if (StringUtils.equalsIgnoreCase(targetEncodeType, EncodeTypeEnum.PUA.getCode())) {
            transferRet.add(name);
            // pua原字转unicode，转码返回
        } else if (StringUtils.equalsIgnoreCase(targetEncodeType, EncodeTypeEnum.UNICODE.getCode())) {
            transferRet = transfer(principalId, name, EncodeTypeEnum.PUA.getCode(), EncodeTypeEnum.UNICODE.getCode());
        }

        return transferRet;
    }


    /**
     * 将姓名按指定码转码返回
     *
     * @param principalId      业务id
     * @param name             姓名
     * @param sourceEncodeType 姓名源编码
     * @param targetEncodeType 姓名目标编码
     * @return
     */
    private List<String> transfer(String principalId, String name, String sourceEncodeType, String targetEncodeType) {
        List<String> transferRet = new ArrayList<String>();

        // 用#标记非GBK汉字位置
        String sign = "#";
        String gbkChineseWithSign = NameUtil.signNotGBKChinesePosition(name, sign);
        int signCount = StringUtils.countMatches(gbkChineseWithSign, sign);

        // 去除GBK汉字
        String nameNoGBK = NameUtil.removeGBKChineseCharacters(name);
        String codePointAt0 = hex(nameNoGBK.codePointAt(0));

        // 姓名含一个生僻字
        if (signCount == 1) {
            List<String> codesAt0 = getCodesByEncodeTypeAndCodePoint(sourceEncodeType, targetEncodeType, codePointAt0);

            // 拼接姓名，按期望编码返回可能的原字姓名
            for (String code : codesAt0) {
                String tmpRet = StringUtils.replace(gbkChineseWithSign, "#", code);
                transferRet.add(tmpRet);
            }

            // 姓名含两个生僻字
        } else if (signCount == 2) {
            String codePointAt1 = hex(nameNoGBK.codePointAt(1));
            List<String> codesAt0 = getCodesByEncodeTypeAndCodePoint(sourceEncodeType, targetEncodeType, codePointAt0);
            List<String> codesAt1 = getCodesByEncodeTypeAndCodePoint(sourceEncodeType, targetEncodeType, codePointAt1);

            // 拼接姓名，按期望编码返回可能的原字姓名
            String tmpRet = "";
            if (!CollectionUtils.isEmpty(codesAt0) && codesAt0.size() >= 1) {
                tmpRet = StringUtils.replaceOnce(gbkChineseWithSign, "#", codesAt0.get(0));
            }
            if (!CollectionUtils.isEmpty(codesAt1) && codesAt1.size() >= 1) {
                tmpRet = StringUtils.replaceOnce(tmpRet, "#", codesAt1.get(0));
            }
            transferRet.add(tmpRet);
        }

        if (transferRet.size() == 0) {
            logger.info("RareNameTransferManagerImpl transfer default, principalId={}, name={}", principalId, name);
        } else {
            logger.info("RareNameTransferManagerImpl transfer, principalId={}, name={}, targetEncodeType={}, transferRet={}",
                    principalId, name, targetEncodeType, transferRet);
        }
        return transferRet;
    }

    /**
     * 根据编码类型和码点查询编码
     *
     * @param sourceEncodeType 源编码
     * @param targetEncodeType 目标编码
     * @param codePoint        码点
     * @return
     */
    private List<String> getCodesByEncodeTypeAndCodePoint(String sourceEncodeType, String targetEncodeType, String codePoint) {
        List<EncodeTypeEnum> encodeTypeEnums = new ArrayList<EncodeTypeEnum>() {
        };
        encodeTypeEnums.add(EncodeTypeEnumUtil.convertString2EncodeTypeEnum(sourceEncodeType));
        Set<String> charIds = rareNameService.findCharIdsByEncodeTypesAndCodePoint(encodeTypeEnums, codePoint);
        List<RareCharacter> rareCharacters = rareNameService.findByCharIds(charIds);
        return rareNameService.getCodesFromRareCharacters(rareCharacters, EncodeTypeEnumUtil.convertString2EncodeTypeEnum(targetEncodeType));

    }

    /**
     * 根据编码类型和编码查询其他编码
     *
     * @param encodeTypeEnum       编码类型
     * @param code                 编码
     * @param targetEncodeTypeEnum 目标编码
     * @return
     */
    private List<String> getCodesByEncodeTypeAndCode(EncodeTypeEnum encodeTypeEnum, String code, EncodeTypeEnum targetEncodeTypeEnum) {
        Set<String> charIds = rareNameService.findCharIdsByEncodeTypeAndCode(encodeTypeEnum, code);
        List<RareCharacter> rareCharacters = rareNameService.findByCharIds(charIds);
        return rareNameService.getCodesFromRareCharacters(rareCharacters, targetEncodeTypeEnum);
    }

    /**
     * 拼音生僻字转码
     *
     * @param principalId      业务id
     * @param name             姓名
     * @param targetEncodeType 目标编码
     * @return
     */
    @Override
    public List<String> transferPinyinRareName(String principalId, String name, String targetEncodeType) {
        List<String> transferRet = new ArrayList<String>();

        // 用#标记非GBK汉字位置
        String sign = "#";
        String gbkChineseWithSign = NameUtil.signPinyinPosition(name, sign);
        int signCount = StringUtils.countMatches(gbkChineseWithSign, sign);

        // 去除GBK去除GBK汉字，仅剩拼音
        String nameNoGBK = NameUtil.removeGBKChineseCharacters(name);
        nameNoGBK = NameUtil.removeNums(nameNoGBK);
        nameNoGBK = NameUtil.removeSpecialCharacters(nameNoGBK);

        // 一个生僻字
        if (signCount == 1) {
            // 转码
            List<String> codes = getCodesByEncodeTypeAndCode(EncodeTypeEnum.PINYIN,
                    StringUtils.upperCase(nameNoGBK),
                    EncodeTypeEnumUtil.convertString2EncodeTypeEnum(targetEncodeType));

            // 拼接姓名，按期望编码返回可能的原字姓名
            for (String code : codes) {
                String tmpRet = StringUtils.replace(gbkChineseWithSign, "#", code);
                transferRet.add(tmpRet);
            }
            // 多个生僻字
        }

        if (transferRet.size() == 0) {
            logger.info("RareNameTransferManagerImpl transferPinyinRareName default, principalId={}, name={}",
                    principalId, name);
        } else {
            logger.info("RareNameTransferManagerImpl transferPinyinRareName, principalId={}, name={}, " +
                    "targetEncodeType={}, transferRet={}", principalId, name, targetEncodeType, transferRet);
        }
        return transferRet;

    }

    /**
     * 拆字生僻字转码
     *
     * @param principalId      业务id
     * @param name             姓名
     * @param targetEncodeType 目标编码
     * @return
     */
    @Override
    public List<String> transferSplitRareName(String principalId, String name, String targetEncodeType) {
        List<String> transferRet = new ArrayList<String>();
        if (StringUtils.isBlank(name)) {
            return transferRet;
        }

        String sign = "#";

        // 有拆字生僻字连接符，用连接汉字查询生僻字原字
        if (NameUtil.isContainSplitSign(name)) {
            String linedChars = NameUtil.getSplitSignLinkedChars(name);
            List<String> targetChars = getCodesByEncodeTypeAndCode(EncodeTypeEnum.SPLIT, linedChars,
                    EncodeTypeEnumUtil.convertString2EncodeTypeEnum(targetEncodeType));

            String gbkChineseWithSign = NameUtil.signSplitPosition(name, sign);
            for (String targetChar : targetChars) {
                transferRet.add(StringUtils.replace(gbkChineseWithSign, sign, targetChar));
            }
        } else {
            // 无括号，姓名顺序拆字后查字库
            String nameOnlyChineseCharacters = NameUtil.removeNotBMPChineseCharacters(name);
            List<String> splitNameList = NameUtil.splitNameEachTwoCharacters(nameOnlyChineseCharacters);
            for (String splitName : splitNameList) {
                List<String> targetChars = getCodesByEncodeTypeAndCode(EncodeTypeEnum.SPLIT,
                        splitName, EncodeTypeEnumUtil.convertString2EncodeTypeEnum(targetEncodeType));
                if (!CollectionUtils.isEmpty(targetChars)) {
                    String gbkChineseWithSign = StringUtils.replace(nameOnlyChineseCharacters, splitName, sign);
                    for (String targetChar : targetChars) {
                        transferRet.add(StringUtils.replace(gbkChineseWithSign, sign, targetChar));
                    }
                }
            }
        }


        if (transferRet.size() == 0) {
            logger.info("RareNameTransferManagerImpl transferSplitRareName default, principalId={}, name={}",
                    principalId, name);
        } else {
            logger.info("RareNameTransferManagerImpl transferSplitRareName, principalId={}, name={}, transferRet={}",
                    principalId, name, transferRet);
        }
        return transferRet;
    }

    /**
     * 姓名联想
     *
     * @param principalId 业务id
     * @param name 姓名
     * @return
     */
    @Override
    public List<RareNameInfo> associateRareName(String principalId, String name) {
        List<RareNameInfo> retNames = new ArrayList<RareNameInfo>();

        // 输入拼音，生僻字联想
        if (rareNameManager.isPinyinRareName(principalId, name) == RareCharacterResultCodeEnum.IS_PINYIN_RARE_NAME) {
            return associateRareNameFromPinyin(principalId, name);
        }

        // 输入pua原字，生僻字联想
        if (rareNameManager.isPuaRareName(principalId, name) == RareCharacterResultCodeEnum.IS_PUA_ORIGINAL_RARE_NAME) {
            return associateRareNameFromPua(principalId, name);
        }

        // 输入unicode原字，生僻字联想
        if (rareNameManager.isUnicodeRareName(principalId, name) == RareCharacterResultCodeEnum.IS_UNICODE_ORIGINAL_RARE_NAME) {
            return associateRareNameFromUnicode(principalId, name);
        }

        // 输入拆字，生僻字联想
        if (rareNameManager.isSplitRareName(principalId, name) == RareCharacterResultCodeEnum.IS_SPLIT_RARE_NAME ||
                rareNameManager.isSplitRareName(principalId, name) == RareCharacterResultCodeEnum.POSSIBLE_SPLIT_RARE_NAME) {
            return associateRareNameFromSplit(principalId, name);
        }

        return retNames;
    }

    /**
     * 输入拼音姓名，联想生僻字姓名
     *
     * @param principalId 业务id
     * @param name 姓名
     * @return
     */
    public List<RareNameInfo> associateRareNameFromPinyin(String principalId, String name) {
        List<RareNameInfo> associateRet = new ArrayList<RareNameInfo>();

        // 用#标记非GBK汉字位置
        String sign = "#";
        String gbkChineseWithSign = NameUtil.signPinyinPosition(name, sign);

        // 去除GBK汉字，仅剩拼音
        String nameNoGBK = NameUtil.removeGBKChineseCharacters(name);
        String pinyin = NameUtil.removeSpecialCharacters(nameNoGBK);

        // 是否是生僻字拼音
        if (isRareCharacterPinyin(pinyin)) {
            // 联想的拼音姓名(大小写拼音，中英文括号，音调)
            List<RareNameInfo> associatePinyins = addUpperAndLower(pinyin);
            associatePinyins = addBrackets(associatePinyins);

            // 姓名拼接
            for (RareNameInfo py : associatePinyins) {
                RareNameInfo tmpRi = new RareNameInfo();
                tmpRi.setName(StringUtils.replace(gbkChineseWithSign, sign, py.getName()));
                tmpRi.setRemarks(py.getRemarks());
                if (!associateRet.contains(tmpRi)) {
                    associateRet.add(tmpRi);
                }
            }
        }
        logger.info("RareNameTransferManagerImpl associateRareNameFromPinyin, principalId={}, name={}, " +
                "associateRet={}", principalId, name, associateRet);
        return associateRet;
    }

    /**
     * 是否生僻字拼音
     *
     * @param pinyin 拼音
     * @return
     */
    private boolean isRareCharacterPinyin(String pinyin) {
        if (StringUtils.isBlank(pinyin)) {
            return false;
        }
        pinyin = NameUtil.removeNums(pinyin);
        Set<String> charIds = rareNameService.findCharIdsByEncodeTypeAndCode(EncodeTypeEnum.PINYIN, pinyin);
        if (!CollectionUtils.isEmpty(charIds)) {
            return true;
        }
        return false;
    }

    /**
     * 拼音返回大小写，兼容音调
     *
     * @param pinyin 拼音
     * @return
     */
    private List<RareNameInfo> addUpperAndLower(String pinyin) {
        List<RareNameInfo> associateRet = new LinkedList<RareNameInfo>();
        if (StringUtils.isBlank(pinyin)) {
            return associateRet;
        }

        // 带音调
        if (NameUtil.isContainNum(pinyin)) {
            String pinyinNoTone = NameUtil.removeNums(pinyin);
            RareNameInfo tmpRi = new RareNameInfo();
            tmpRi.setName(pinyinNoTone.toUpperCase());
            tmpRi.setRemarks(UPPER_PINYIN);
            associateRet.add(tmpRi);

            tmpRi = new RareNameInfo();
            tmpRi.setName(pinyinNoTone.toLowerCase());
            tmpRi.setRemarks(LOWER_PINYIN);
            associateRet.add(tmpRi);

            tmpRi = new RareNameInfo();
            tmpRi.setName(pinyin.toUpperCase());
            tmpRi.setRemarks(UPPER_PINYIN + JOINT + TONE);
            associateRet.add(tmpRi);

            tmpRi = new RareNameInfo();
            tmpRi.setName(pinyin.toLowerCase());
            tmpRi.setRemarks(LOWER_PINYIN + JOINT + TONE);
            associateRet.add(tmpRi);
        } else {
            // 不带音调
            RareNameInfo tmpRi = new RareNameInfo();
            tmpRi.setName(pinyin.toUpperCase());
            tmpRi.setRemarks(UPPER_PINYIN);
            associateRet.add(tmpRi);

            tmpRi = new RareNameInfo();
            tmpRi.setName(pinyin.toLowerCase());
            tmpRi.setRemarks(LOWER_PINYIN);
            associateRet.add(tmpRi);
        }

        return associateRet;
    }

    /**
     * 在拼音两侧加上中/英括号，兼容音调
     *
     * @param associatePinyins 联想的拼音
     * @return
     */
    private List<RareNameInfo> addBrackets(List<RareNameInfo> associatePinyins) {
        if (CollectionUtils.isEmpty(associatePinyins)) {
            return new LinkedList<RareNameInfo>();
        }
        List<RareNameInfo> inputPy = new ArrayList<RareNameInfo>(associatePinyins);

        List<RareNameInfo> tmpAssociateRet = Collections.synchronizedList(new ArrayList<RareNameInfo>());
        tmpAssociateRet.addAll(inputPy);

        // 加上中文括号
        for (RareNameInfo input : inputPy) {
            RareNameInfo tmpRi = new RareNameInfo();
            tmpRi.setName(NameUtil.addChineseBrackets(input.getName()));
            if (NameUtil.isContainUpperPinyin(tmpRi.getName())) {
                if (NameUtil.isContainNum(tmpRi.getName())) {
                    tmpRi.setRemarks(CHINESE_BRACKET + JOINT + UPPER_PINYIN + JOINT + TONE);
                } else {
                    tmpRi.setRemarks(CHINESE_BRACKET + JOINT + UPPER_PINYIN);
                }
                tmpAssociateRet.add(tmpRi);
            }

            if (NameUtil.isContainLowerPinyin(tmpRi.getName())) {
                if (NameUtil.isContainNum(tmpRi.getName())) {
                    tmpRi.setRemarks(CHINESE_BRACKET + JOINT + LOWER_PINYIN + JOINT + TONE);
                } else {
                    tmpRi.setRemarks(CHINESE_BRACKET + JOINT + LOWER_PINYIN);
                }
                tmpAssociateRet.add(tmpRi);
            }
        }

        // 加上英文括号
        for (RareNameInfo input : inputPy) {
            RareNameInfo tmpRi = new RareNameInfo();
            tmpRi.setName(NameUtil.addEnglishBrackets(input.getName()));
            if (NameUtil.isContainUpperPinyin(tmpRi.getName())) {
                if (NameUtil.isContainNum(tmpRi.getName())) {
                    tmpRi.setRemarks(ENGLISH_BRACKET + JOINT + UPPER_PINYIN + JOINT + TONE);
                } else {
                    tmpRi.setRemarks(ENGLISH_BRACKET + JOINT + UPPER_PINYIN);
                }
                tmpAssociateRet.add(tmpRi);
            }
            if (NameUtil.isContainLowerPinyin(tmpRi.getName())) {
                if (NameUtil.isContainNum(tmpRi.getName())) {
                    tmpRi.setRemarks(ENGLISH_BRACKET + JOINT + LOWER_PINYIN + JOINT + TONE);
                } else {
                    tmpRi.setRemarks(ENGLISH_BRACKET + JOINT + LOWER_PINYIN);
                }
                tmpAssociateRet.add(tmpRi);
            }
        }
        return new ArrayList<RareNameInfo>(tmpAssociateRet);
    }

    /**
     * 输入pua，生僻字联想
     *
     * @param principalId 业务id
     * @param name 姓名
     * @return
     */
    public List<RareNameInfo> associateRareNameFromPua(String principalId, String name) {
        List<RareNameInfo> associateRet = new ArrayList<RareNameInfo>();
        String sign = "#";
        String gbkChineseWithSign = NameUtil.signNotGBKChinesePosition(name, sign);

        // 联想的unicode姓名
        List<String> tmpUnicodeRet = transfer(principalId, name, EncodeTypeEnum.PUA.getCode(), EncodeTypeEnum.UNICODE.getCode());
        for (String tmpUnicode : tmpUnicodeRet) {
            RareNameInfo tmpRi = new RareNameInfo();
            tmpRi.setName(tmpUnicode);
            tmpRi.setRemarks(UNICODE_ORIGINAL);
            if (!associateRet.contains(tmpRi)) {
                associateRet.add(tmpRi);
            }
        }

        // 查询pua字的拼音
        List<RareNameInfo> tmpPinyinRet = new LinkedList<RareNameInfo>();
        String nameNoGBK = NameUtil.removeGBKChineseCharacters(name);
        List<String> pinYinCodes = getCodesByEncodeTypeAndCode(EncodeTypeEnum.PUA, nameNoGBK, EncodeTypeEnum.PINYIN);
        List<String> tmpPinyins = new LinkedList<String>();
        for (String py : pinYinCodes) {
            String tmpPinyin = StringUtils.replace(gbkChineseWithSign, sign, py);
            if (!tmpPinyins.contains(tmpPinyin)) {
                tmpPinyins.add(tmpPinyin);
            }
        }

        // 联想出其他拼音姓名
        for (String tmpPinyin : tmpPinyins) {
            List<RareNameInfo> associatePinyins = associateRareNameFromPinyin(principalId, tmpPinyin);
            for (RareNameInfo tmpRi : associatePinyins) {
                if (!tmpPinyinRet.contains(tmpRi)) {
                    tmpPinyinRet.add(tmpRi);
                }
            }
        }
        for (RareNameInfo tmpRi : tmpPinyinRet) {
            if (!associateRet.contains(tmpRi)) {
                associateRet.add(tmpRi);
            }
        }
        logger.info("RareNameTransferManagerImpl associateRareNameFromPua, principalId={}, name={}, " +
                "associateRet={}", principalId, name, associateRet);
        return associateRet;
    }


    /**
     * 输入unicode，生僻字联想
     *
     * @param principalId 业务id
     * @param name 姓名
     * @return
     */
    public List<RareNameInfo> associateRareNameFromUnicode(String principalId, String name) {
        List<RareNameInfo> associateRet = new ArrayList<RareNameInfo>();
        String sign = "#";
        String gbkChineseWithSign = NameUtil.signNotGBKChinesePosition(name, sign);

        // 查询unicode字的拼音
        List<RareNameInfo> tmpPinyinRet = new LinkedList<RareNameInfo>();
        String nameNoGBK = NameUtil.removeGBKChineseCharacters(name);
        String codePointAt0 = hex(nameNoGBK.codePointAt(0));
        List<String> pinYinCodes = getCodesByEncodeTypeAndCodePoint(EncodeTypeEnum.UNICODE.getCode(), EncodeTypeEnum.PINYIN.getCode(), codePointAt0);
        List<String> tmpPinyins = new LinkedList<String>();
        for (String py : pinYinCodes) {
            String tmpPinyin = StringUtils.replace(gbkChineseWithSign, sign, py);
            if (!tmpPinyins.contains(tmpPinyin)) {
                tmpPinyins.add(tmpPinyin);
            }
        }

        // 联想出其他拼音姓名
        for (String tmpPinyin : tmpPinyins) {
            List<RareNameInfo> associatePinyins = associateRareNameFromPinyin(principalId, tmpPinyin);
            for (RareNameInfo tmpRi : associatePinyins) {
                if (!tmpPinyinRet.contains(tmpRi)) {
                    tmpPinyinRet.add(tmpRi);
                }
            }
        }
        for (RareNameInfo tmpRi : tmpPinyinRet) {
            if (!associateRet.contains(tmpRi)) {
                associateRet.add(tmpRi);
            }
        }
        logger.info("RareNameTransferManagerImpl associateRareNameFromUnicode, principalId={}, name={}, " +
                "associateRet={}", principalId, name, associateRet);
        return associateRet;
    }

    /**
     * 输入拆字，生僻字联想
     *
     * @param principalId 业务id
     * @param name 姓名
     * @return
     */
    public List<RareNameInfo> associateRareNameFromSplit(String principalId, String name) {
        List<RareNameInfo> associateRet = new ArrayList<RareNameInfo>();
        String sign = "#";

        // 联想的unicode姓名
        List<String> tmpUnicodeRet = transferSplitRareName(principalId, name, EncodeTypeEnum.UNICODE.getCode());
        for (String tmpUnicode : tmpUnicodeRet) {
            RareNameInfo tmpRi = new RareNameInfo();
            tmpRi.setName(tmpUnicode);
            tmpRi.setRemarks(UNICODE_ORIGINAL);
            if (!associateRet.contains(tmpRi)) {
                associateRet.add(tmpRi);
            }
        }

        // 查询拆字生僻字的拼音
        List<RareNameInfo> tmpPinyinRet = new LinkedList<RareNameInfo>();
        String gbkChineseWithSign = NameUtil.signSplitPosition(name, sign);
        String linkedChars = NameUtil.getSplitSignLinkedChars(name);
        List<String> pinYinCodes = getCodesByEncodeTypeAndCode(EncodeTypeEnum.SPLIT, linkedChars, EncodeTypeEnum.PINYIN);
        List<String> tmpPinyins = new LinkedList<String>();
        for (String py : pinYinCodes) {
            String tmpPinyin = StringUtils.replace(gbkChineseWithSign, sign, py);
            if (!tmpPinyins.contains(tmpPinyin)) {
                tmpPinyins.add(tmpPinyin);
            }
        }

        // 联想出其他拼音姓名
        for (String tmpPinyin : tmpPinyins) {
            List<RareNameInfo> associatePinyins = associateRareNameFromPinyin(principalId, tmpPinyin);
            for (RareNameInfo tmpRi : associatePinyins) {
                if (!tmpPinyinRet.contains(tmpRi)) {
                    tmpPinyinRet.add(tmpRi);
                }
            }
        }
        for (RareNameInfo tmpRi : tmpPinyinRet) {
            if (!associateRet.contains(tmpRi)) {
                associateRet.add(tmpRi);
            }
        }
        logger.info("RareNameTransferManagerImpl associateRareNameFromSplit, principalId={}, name={}, associateRet={}",
                principalId, name, associateRet);
        return associateRet;
    }

    /**
     * Getter method for property <tt>rareNameManager</tt>.
     *
     * @return property value of rareNameManager
     */
    @Override
    public RareNameManager getRareNameManager() {
        return rareNameManager;
    }

    /**
     * Setter method for property <tt>rareNameManager</tt>.
     *
     * @param rareNameManager value to be assigned to property rareNameManager
     */
    @Override
    public void setRareNameManager(RareNameManager rareNameManager) {
        this.rareNameManager = rareNameManager;
    }

    /**
     * Getter method for property <tt>rareNameService</tt>.
     *
     * @return property value of rareNameService
     */
    @Override
    public RareNameService getRareNameService() {
        return rareNameService;
    }

    /**
     * Setter method for property <tt>rareNameService</tt>.
     *
     * @param rareNameService value to be assigned to property rareNameService
     */
    @Override
    public void setRareNameService(RareNameService rareNameService) {
        this.rareNameService = rareNameService;
    }
}

