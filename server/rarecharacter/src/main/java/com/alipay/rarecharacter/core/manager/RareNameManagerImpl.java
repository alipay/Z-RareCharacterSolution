package com.alipay.rarecharacter.core.manager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.alipay.rarecharacter.core.model.RareCharacter;
import com.alipay.rarecharacter.core.service.RareNameService;
import com.alipay.rarecharacter.util.EncodeTypeEnumUtil;
import com.alipay.rarecharacter.util.NameUtil;
import com.alipay.rarecharacter.core.model.enums.EncodeTypeEnum;
import com.alipay.rarecharacter.core.model.enums.RareCharacterResultCodeEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;

import static org.apache.commons.lang3.text.translate.CharSequenceTranslator.hex;


/**
 * 生僻字姓名manager实现
 *
 * @author huyibing
 * @version $Id: RareNameManagerImpl.java, v 0.1 2022年05月06日 下午14:23 huyibing Exp $
 */
@Component
public class RareNameManagerImpl implements RareNameManager {

    /**
     * logger
     */
    private final static Logger logger = LogManager
            .getLogger(RareNameManagerImpl.class);

    @Autowired
    private RareNameService rareNameService;

    /**
     * 姓名是否生僻字姓名
     *
     * @param principalId
     * @param name
     * @return
     */
    @Override
    public RareCharacterResultCodeEnum isRareName(String principalId, String name) {
        // 入参判断
        if (StringUtils.isBlank(name)) {
            return RareCharacterResultCodeEnum.ILLEGAL_ARGUMENT;
        }

        // 姓名含字母，生僻字UNICODE原字，PUA原字等，返回是生僻字姓名
        // 如：张wei3，张wei，张"韦华"（unicode）
        if (NameUtil.isRareName(name)) {
            return RareCharacterResultCodeEnum.IS_RARE_NAME;
        }

        // 姓名去除非汉字（仅保留汉字）
        String nameOnlyChineseCharacters = NameUtil.removeNotBMPChineseCharacters(name);
        if (StringUtils.isBlank(nameOnlyChineseCharacters)) {
            logger.info("姓名无汉字, principalId={}, name={}", principalId, name);
            return RareCharacterResultCodeEnum.ILLEGAL_ARGUMENT;
        }

        // 生僻字拆字：姓名顺序拆字后查字库，存在则可能是生僻字
        // 如: 张韦华，其中韦华可能是拆字生僻字
        List<String> splitNameList = NameUtil.splitNameEachTwoCharacters(nameOnlyChineseCharacters);
        for (String splitName : splitNameList) {
            List<RareCharacter> characterList = rareNameService.findByEncodeTypeAndCode(EncodeTypeEnum.SPLIT, splitName);
            if (characterList.size() > 0) {
                logger.info("姓名可能存在拆字生僻字, principalId={}, name={}, characterList={}",
                        principalId, name, characterList.toString());
                return RareCharacterResultCodeEnum.POSSIBLE_RARE_NAME;
            }
        }
        return RareCharacterResultCodeEnum.NOT_RARE_NAME;
    }

    /**
     * 姓名是否相同
     *
     * @param principalId 业务id
     * @param sourceName  源姓名
     * @param targetName  目标姓名
     * @return
     */
    @Override
    public RareCharacterResultCodeEnum isSameRareName(String principalId, String sourceName, String targetName) {
        // 姓名全是汉字且完全相同, 返回相同。
        // 如：（张三，张三）相同， （张"韦华"unicode，张"韦华"unicode）相同
        if (StringUtils.equalsIgnoreCase(sourceName, targetName)) {
            logger.info("姓名全是汉字且完全相同, principalId={}, sourceName={}, targetName={}",
                    principalId, sourceName, targetName);
            return RareCharacterResultCodeEnum.IS_SAME_RARE_NAME;
        }

        // 姓名中均不含生僻字，返回不相同
        if (isRareName(principalId, sourceName) == RareCharacterResultCodeEnum.NOT_RARE_NAME
                && isRareName(principalId, targetName) == RareCharacterResultCodeEnum.NOT_RARE_NAME) {
            logger.info("姓名都不含生僻字, principalId={}, sourceName={}, targetName={}",
                    principalId, sourceName, targetName);
            return RareCharacterResultCodeEnum.NOT_SAME_RARE_NAME;
        }

        // 姓名去除前后相同的汉字, 剩余部分查找生僻字字库，判断是否是同一个汉字
        // 1）姓名去除特殊字符，如括号等
        String sourceNameNoSpecialCharacters = NameUtil.removeSpecialCharacters(sourceName);
        String targetNameNoSpecialCharacters = NameUtil.removeSpecialCharacters(targetName);

        // 去除括号等特殊字符后完全相同
        // case 张龙天  张（龙天）
        if (StringUtils.isNotBlank(sourceNameNoSpecialCharacters)
                && StringUtils.equalsIgnoreCase(sourceNameNoSpecialCharacters, targetNameNoSpecialCharacters)) {
            logger.info("姓名去除括号等特殊字符后完全相同, principalId={}, sourceName={}, targetName={}, sourceNameNoSpecialCharacters={}",
                    principalId, sourceName, targetName, sourceNameNoSpecialCharacters);
            return RareCharacterResultCodeEnum.POSSIBLE_SAME_RARE_NAME;
        }

        //  2）姓名去除前后相同的汉字
        List<String> names = NameUtil.removeSameChineseCharacter(sourceNameNoSpecialCharacters, targetNameNoSpecialCharacters);
        if (names.size() != 2) {
            return RareCharacterResultCodeEnum.SYSTEM_ERROR;
        }
        sourceNameNoSpecialCharacters = names.get(0);
        targetNameNoSpecialCharacters = names.get(1);
        if (StringUtils.isBlank(sourceNameNoSpecialCharacters) || StringUtils.isBlank(targetNameNoSpecialCharacters)) {
            logger.info("姓名去除顺序相同汉字后为空, principalId={}, sourceName={}, targetName={}",
                    principalId, sourceName, targetName);
            return RareCharacterResultCodeEnum.NOT_SAME_RARE_NAME;
        }

        // 3）查可能的原字charId是否有交集
        Set<String> charIdsForSourceName = new HashSet<String>();
        Set<String> charIdsForTargetName = new HashSet<String>();
        sourceNameNoSpecialCharacters = NameUtil.removeNums(sourceNameNoSpecialCharacters);
        targetNameNoSpecialCharacters = NameUtil.removeNums(targetNameNoSpecialCharacters);
        if (StringUtils.isNotBlank(sourceNameNoSpecialCharacters) && StringUtils.isNotBlank(targetNameNoSpecialCharacters)) {
            String sourceCodePoint = hex(sourceNameNoSpecialCharacters.codePointAt(0));
            String targetCodePoint = hex(targetNameNoSpecialCharacters.codePointAt(0));

            Set<String> charIdsForSourceNameOriginal = rareNameService.findCharIdsByEncodeTypesAndCodePoint(
                    EncodeTypeEnumUtil.getAllOriginalEncodeTypeEnums(),
                    sourceCodePoint);
            if (!CollectionUtils.isEmpty(charIdsForSourceNameOriginal) && charIdsForSourceNameOriginal.size() > 0) {
                charIdsForSourceName.addAll(charIdsForSourceNameOriginal);
            }
            Set<String> charIdsForTargetNameOriginal = rareNameService.findCharIdsByEncodeTypesAndCodePoint(
                    EncodeTypeEnumUtil.getAllOriginalEncodeTypeEnums(),
                    targetCodePoint);
            if (!CollectionUtils.isEmpty(charIdsForTargetNameOriginal) && charIdsForTargetNameOriginal.size() > 0) {
                charIdsForTargetName.addAll(charIdsForTargetNameOriginal);
            }
            // 可能的原字char_id是否有交集，有交集返回可能相同
            if (charIdsForSourceName.size() > 0 && charIdsForTargetName.size() > 0
                    && !Collections.disjoint(charIdsForSourceName, charIdsForTargetName)) {
                logger.info("码点char_id相同,是相同的生僻字姓名, principalId={}, sourceName={}, targetName={}, " +
                                "charIdsForSourceName={}, charIdsForTargetName={}",
                        principalId, sourceName, targetName, charIdsForSourceName, charIdsForTargetName);
                return RareCharacterResultCodeEnum.POSSIBLE_SAME_RARE_NAME;
            }

            Set<String> charIdsForSourceNameReplace = rareNameService.findCharIdsByEncodeTypesAndCode(
                    EncodeTypeEnumUtil.getAllReplaceEncodeTypeEnums(),
                    sourceNameNoSpecialCharacters);
            if (!CollectionUtils.isEmpty(charIdsForSourceNameReplace) && charIdsForSourceNameReplace.size() > 0) {
                charIdsForSourceName.addAll(charIdsForSourceNameReplace);
            }
            Set<String> charIdSetForTargetNameReplace = rareNameService.findCharIdsByEncodeTypesAndCode(
                    EncodeTypeEnumUtil.getAllReplaceEncodeTypeEnums(),
                    targetNameNoSpecialCharacters);
            if (!CollectionUtils.isEmpty(charIdSetForTargetNameReplace) && charIdSetForTargetNameReplace.size() > 0) {
                charIdsForTargetName.addAll(charIdSetForTargetNameReplace);
            }

            // 可能的原字char_id是否有交集，有交集返回可能相同
            if (charIdsForSourceName.size() > 0 && charIdsForTargetName.size() > 0
                    && !Collections.disjoint(charIdsForSourceName, charIdsForTargetName)) {
                logger.info("可能是相同的生僻字姓名, principalId={}, sourceName={}, targetName={}, " +
                                "charIdsForSourceName={},charIdsForTargetName={}",
                        principalId, sourceName, targetName, charIdsForSourceName, charIdsForTargetName);
                return RareCharacterResultCodeEnum.POSSIBLE_SAME_RARE_NAME;
            }
        }

        return RareCharacterResultCodeEnum.NOT_SAME_RARE_NAME;
    }

    /**
     * 姓名是否含unicode原字生僻字
     *
     * @param principalId 业务id
     * @param name        姓名
     * @return
     */
    @Override
    public RareCharacterResultCodeEnum isUnicodeRareName(String principalId, String name) {
        RareCharacterResultCodeEnum resultCodeEnum = null;

        // 正则判断
        if (NameUtil.isContainUnicodeOriginalRareCharacter(name)) {
            resultCodeEnum = RareCharacterResultCodeEnum.IS_UNICODE_ORIGINAL_RARE_NAME;
        } else {
            // 码点判断
            String nameNoCommonChinese = NameUtil.removeBMPChineseCharacters(name);
            if (StringUtils.isNotBlank(nameNoCommonChinese)) {
                String nameNoCommonChineseCodePoint = hex(nameNoCommonChinese.codePointAt(0));
                List<RareCharacter> characterList = rareNameService.findByEncodeTypesAndCodePoint(
                        EncodeTypeEnumUtil.getUnicodeOriginalEncodeTypeEnums(),
                        nameNoCommonChineseCodePoint);
                if (characterList.size() > 0) {
                    resultCodeEnum = RareCharacterResultCodeEnum.IS_UNICODE_ORIGINAL_RARE_NAME;
                }
            }
        }

        if (resultCodeEnum == RareCharacterResultCodeEnum.IS_UNICODE_ORIGINAL_RARE_NAME) {
            logger.info("姓名含unicode原字生僻字, principalId={}, name={}", principalId, name);
        }
        return resultCodeEnum;
    }


    /**
     * 姓名是否含pua原字生僻字
     *
     * @param principalId 业务id
     * @param name        姓名
     * @return
     */
    @Override
    public RareCharacterResultCodeEnum isPuaRareName(String principalId, String name) {
        RareCharacterResultCodeEnum resultCodeEnum = null;

        // 正则判断
        if (NameUtil.isContainPuaOriginalRareCharacter(name)) {
            resultCodeEnum = RareCharacterResultCodeEnum.IS_PUA_ORIGINAL_RARE_NAME;
        } else {
            // 码点判断
            String nameNoCommonChinese = NameUtil.removeBMPChineseCharacters(name);
            if (StringUtils.isNotBlank(nameNoCommonChinese)) {
                String nameNoCommonChineseCodePoint = hex(nameNoCommonChinese.codePointAt(0));
                List<RareCharacter> characterList = rareNameService.findByEncodeTypesAndCodePoint(
                        EncodeTypeEnumUtil.getPuaOriginalEncodeTypeEnums(),
                        nameNoCommonChineseCodePoint);
                if (characterList.size() > 0) {
                    resultCodeEnum = RareCharacterResultCodeEnum.IS_PUA_ORIGINAL_RARE_NAME;
                }
            }
        }

        if (resultCodeEnum == RareCharacterResultCodeEnum.IS_PUA_ORIGINAL_RARE_NAME) {
            logger.info("姓名含pua原字生僻字, principalId={}, name={}", principalId, name);
        }
        return resultCodeEnum;
    }

    /**
     * 是否原字生僻字 unicode / pua
     *
     * @param principalId 业务id
     * @param name 姓名
     * @return
     */
    @Override
    public RareCharacterResultCodeEnum isOriginalRareName(String principalId, String name) {
        if ((isUnicodeRareName(principalId, name) == RareCharacterResultCodeEnum.IS_UNICODE_ORIGINAL_RARE_NAME)
                || isPuaRareName(principalId, name) == RareCharacterResultCodeEnum.IS_PUA_ORIGINAL_RARE_NAME) {
            logger.info("姓名含原字生僻字, name={}", name);
            return RareCharacterResultCodeEnum.IS_ORIGINAL_RARE_NAME;
        }
        return null;
    }

    /**
     * 是否拼音生僻字
     *
     * @param principalId 业务id
     * @param name 姓名
     * @return
     */
    @Override
    public RareCharacterResultCodeEnum isPinyinRareName(String principalId, String name) {
        RareCharacterResultCodeEnum resultCodeEnum = null;

        // 正则判断
        if (NameUtil.isContainOneChinese(name) && NameUtil.isContainPinyin(name)) {
            resultCodeEnum = RareCharacterResultCodeEnum.IS_PINYIN_RARE_NAME;
        }

        if (resultCodeEnum == RareCharacterResultCodeEnum.IS_PINYIN_RARE_NAME) {
            logger.info("姓名存在拼音生僻字, principalId={}, name={}", principalId, name);
        }
        return resultCodeEnum;
    }

    /**
     * 是否拆字生僻字
     *
     * @param principalId 业务id
     * @param name 姓名
     * @return
     */
    @Override
    public RareCharacterResultCodeEnum isSplitRareName(String principalId, String name) {
        RareCharacterResultCodeEnum resultCodeEnum = null;
        if (!NameUtil.isContainOneChinese(name)) {
            return null;
        }

        //是否含拆字特殊连接符号，如中英文括号，加号等
        boolean isSplitSign = NameUtil.isContainSplitSign(name);

        // 姓名去除非汉字（仅保留汉字）
        String nameOnlyChineseCharacters = NameUtil.removeNotBMPChineseCharacters(name);
        if (StringUtils.isBlank(nameOnlyChineseCharacters)) {
            resultCodeEnum = RareCharacterResultCodeEnum.ILLEGAL_ARGUMENT;
        }

        // 生僻字拆字：姓名顺序拆字后查字库，存在则可能是生僻字
        // case: 张韦华，其中韦华可能是拆字生僻字
        List<String> splitNameList = NameUtil.splitNameEachTwoCharacters(nameOnlyChineseCharacters);
        for (String splitName : splitNameList) {
            List<RareCharacter> characterList = rareNameService.findByEncodeTypeAndCode(EncodeTypeEnum.SPLIT, splitName);
            if (characterList.size() > 0) {
                resultCodeEnum = RareCharacterResultCodeEnum.POSSIBLE_SPLIT_RARE_NAME;
                if (isSplitSign) {
                    resultCodeEnum = RareCharacterResultCodeEnum.IS_SPLIT_RARE_NAME;
                }
            }

        }

        if (resultCodeEnum == RareCharacterResultCodeEnum.IS_SPLIT_RARE_NAME) {
            logger.info("姓名含拆字生僻字, principalId={}, name={}", principalId, name);
        } else if (resultCodeEnum == RareCharacterResultCodeEnum.POSSIBLE_SPLIT_RARE_NAME) {
            logger.info("姓名可能含拆字生僻字, principalId={}, name={}", principalId, name);
        }
        return resultCodeEnum;
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