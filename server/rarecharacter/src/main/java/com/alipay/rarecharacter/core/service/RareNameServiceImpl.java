package com.alipay.rarecharacter.core.service;

import com.alipay.rarecharacter.dal.dao.spi.RareCharacterDAO;
import com.alipay.rarecharacter.dal.dataobject.RareCharacterDO;
import com.alipay.rarecharacter.dal.mapper.ObjectMapper;
import com.alipay.rarecharacter.dal.mapper.ObjectMapperImpl;
import com.alipay.rarecharacter.util.EncodeTypeEnumUtil;
import org.apache.commons.lang3.StringUtils;

import com.alipay.rarecharacter.core.model.enums.EncodeTypeEnum;
import com.alipay.rarecharacter.core.model.RareCharacter;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * 生僻字service实现
 *
 * @author huyibing
 * @version $Id: RareNameServiceImpl.java, v 0.1 2022年05月06日 下午14:23 huyibing Exp $
 */
@Component
public class RareNameServiceImpl implements RareNameService {

    private RareCharacterDAO rareCharacterDAO;

    ObjectMapper objectMapper = new ObjectMapperImpl();

    @Override
    public RareCharacterDAO getRareCharacterDAO() {
        return this.rareCharacterDAO;
    }

    @Override
    public void setRareCharacterDAO(RareCharacterDAO rareCharacterDAO) {
        this.rareCharacterDAO = rareCharacterDAO;
    }

    /**
     * 根据编码类型和编码查找生僻字list
     *
     * @param encodeTypeEnum 编码类型
     * @param code           编码
     * @return
     */
    @Override
    public List<RareCharacter> findByEncodeTypeAndCode(EncodeTypeEnum encodeTypeEnum, String code) {
        if (encodeTypeEnum == null || StringUtils.isBlank(code)) {
            return new ArrayList<RareCharacter>();
        }

        List<RareCharacterDO> rareCharacterDOList = rareCharacterDAO.findByEncodeTypeAndCode(encodeTypeEnum.getCode(), code);
        return objectMapper.map(rareCharacterDOList, RareCharacter.class);
    }

    /**
     * 根据编码类型和码点查找生僻字list
     *
     * @param encodeTypeEnums 编码类型
     * @param codePoint       码点
     * @return
     */
    @Override
    public List<RareCharacter> findByEncodeTypesAndCodePoint(List<EncodeTypeEnum> encodeTypeEnums, String codePoint) {
        if (CollectionUtils.isEmpty(encodeTypeEnums) || StringUtils.isBlank(codePoint)) {
            return new ArrayList<RareCharacter>();
        }

        List<String> encodeTypes = EncodeTypeEnumUtil.convertEncodeTypeEnums2Strings(encodeTypeEnums);
        List<RareCharacterDO> rareCharacterDOList = rareCharacterDAO.findByEncodeTypesAndCodePoint(encodeTypes, codePoint);
        return objectMapper.map(rareCharacterDOList, RareCharacter.class);
    }


    /**
     * 根据编码类型和编码查找生僻字char_id set
     *
     * @param encodeTypeEnum 编码类型
     * @param code           编码
     * @return
     */
    @Override
    public Set<String> findCharIdsByEncodeTypeAndCode(EncodeTypeEnum encodeTypeEnum, String code) {
        Set<String> charIds = new HashSet<String>();
        if (encodeTypeEnum == null || StringUtils.isBlank(code)) {
            return charIds;
        }

        List<RareCharacterDO> rareCharacterDOList = rareCharacterDAO.findByEncodeTypeAndCode(encodeTypeEnum.getCode(), code);
        for (RareCharacterDO rc : rareCharacterDOList) {
            charIds.add(rc.getCharId());
        }
        return charIds;
    }

    /**
     * 根据编码类型和编码查找生僻字char_id set
     *
     * @param encodeTypeEnums 编码类型
     * @param code            编码
     * @return
     */
    @Override
    public Set<String> findCharIdsByEncodeTypesAndCode(List<EncodeTypeEnum> encodeTypeEnums, String code) {
        Set<String> charIds = new HashSet<String>();
        if (CollectionUtils.isEmpty(encodeTypeEnums) || StringUtils.isBlank(code)) {
            return charIds;
        }

        List<String> encodeTypes = EncodeTypeEnumUtil.convertEncodeTypeEnums2Strings(encodeTypeEnums);
        List<RareCharacterDO> rareCharacterDOList = rareCharacterDAO.findByEncodeTypesAndCode(encodeTypes, code);
        for (RareCharacterDO rc : rareCharacterDOList) {
            charIds.add(rc.getCharId());
        }
        return charIds;
    }

    /**
     * 根据编码类型和码点查找生僻字char_id set
     *
     * @param encodeTypeEnums 编码类型
     * @param codePoint       码点
     * @return
     */
    @Override
    public Set<String> findCharIdsByEncodeTypesAndCodePoint(List<EncodeTypeEnum> encodeTypeEnums, String codePoint) {
        Set<String> charIds = new HashSet<String>();
        if (CollectionUtils.isEmpty(encodeTypeEnums) || StringUtils.isBlank(codePoint)) {
            return charIds;
        }

        List<String> encodeTypes = EncodeTypeEnumUtil.convertEncodeTypeEnums2Strings(encodeTypeEnums);
        List<RareCharacterDO> rareCharacterDOList = rareCharacterDAO.findByEncodeTypesAndCodePoint(encodeTypes, codePoint);
        for (RareCharacterDO rc : rareCharacterDOList) {
            charIds.add(rc.getCharId());
        }
        return charIds;
    }

    /**
     * 根据char_id查询生僻字list
     *
     * @param charIdSet 字符id集合
     * @return
     */
    @Override
    public List<RareCharacter> findByCharIds(Set<String> charIdSet) {
        if (CollectionUtils.isEmpty(charIdSet)) {
            return new ArrayList<RareCharacter>();
        }
        List<String> charIds = new ArrayList<String>();
        charIds.addAll(charIdSet);

        List<List<String>> charIdsList = new ArrayList<List<String>>();

        // 数组太大分批查询
        if (charIdSet.size() < 20) {
            charIdsList.add(charIds);
        } else {
            charIdsList = Lists.partition(charIds, 20);
        }

        List<RareCharacterDO> rareCharacterDOList = new ArrayList<RareCharacterDO>();
        for (List<String> tmpCharIds : charIdsList) {
            List<RareCharacterDO> tmpRareCharacterDOList = rareCharacterDAO.findByCharIds(tmpCharIds);
            rareCharacterDOList.addAll(tmpRareCharacterDOList);
        }
        return objectMapper.map(rareCharacterDOList, RareCharacter.class);
    }

    /**
     * 根据编码类型从对象中查询对应的code
     *
     * @param rareCharacters 字符
     * @param encodeTypeEnum 编码类型
     * @return
     */
    @Override
    public List<String> getCodesFromRareCharacters(List<RareCharacter> rareCharacters, EncodeTypeEnum encodeTypeEnum) {
        List<String> ret = new ArrayList<String>();

        for (RareCharacter rc : rareCharacters) {
            if (rc.getEncodeType() == encodeTypeEnum) {
                if (encodeTypeEnum == EncodeTypeEnum.PINYIN) {
                    ret.add(rc.getCode() + rc.getTone());
                } else {
                    ret.add(rc.getCode());
                }

            }
        }
        return ret;
    }

    /**
     * Setter method for property <tt>objectMapper</tt>.
     *
     * @param objectMapper value to be assigned to property objectMapper
     */
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
}

