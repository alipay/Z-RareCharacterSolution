package com.alipay.rarecharacter.core.service;

import com.alipay.rarecharacter.dal.dao.spi.RareCharacterDAO;
import com.alipay.rarecharacter.core.model.enums.EncodeTypeEnum;
import com.alipay.rarecharacter.core.model.RareCharacter;

import java.util.List;
import java.util.Set;

/**
 * 生僻字service
 *
 * @author huyibing
 * @version $Id: RareNameService.java, v 0.1 2022年05月06日 下午14:23 huyibing Exp $
 */
public interface RareNameService {

    /**
     * Getter method for property <tt>RareCharacterDAO</tt>.
     *
     * @return property value of RareCharacterDAO
     */
    public RareCharacterDAO getRareCharacterDAO();


    /**
     * Setter method for property <tt>RareCharacterDAO</tt>.
     *
     * @param RareCharacterDAO value to be assigned to property RareCharacterDAO
     */
    public void setRareCharacterDAO(RareCharacterDAO RareCharacterDAO);

    /**
     * 根据编码类型和编码查找生僻字list
     *
     * @param encodeTypeEnum 编码类型
     * @param code           编码
     * @return
     */
    List<RareCharacter> findByEncodeTypeAndCode(EncodeTypeEnum encodeTypeEnum, String code);

    /**
     * 根据编码类型列表和码点查找生僻字list
     *
     * @param encodeTypeEnums 编码类型
     * @param codePoint       码点
     * @return
     */
    List<RareCharacter> findByEncodeTypesAndCodePoint(List<EncodeTypeEnum> encodeTypeEnums, String codePoint);

    /**
     * 根据编码类型和编码查找生僻字char_id set
     *
     * @param encodeTypeEnum 编码类型
     * @param code           编码
     * @return
     */
    Set<String> findCharIdsByEncodeTypeAndCode(EncodeTypeEnum encodeTypeEnum, String code);

    /**
     * 根据编码类型和编码查找生僻字char_id set
     *
     * @param encodeTypes 编码类型
     * @param code        编码
     * @return
     */
    Set<String> findCharIdsByEncodeTypesAndCode(List<EncodeTypeEnum> encodeTypes, String code);


    /**
     * 根据编码类型和码点查找生僻字char_id set
     *
     * @param encodeTypes 编码类型
     * @param codePoint   码点
     * @return
     */
    Set<String> findCharIdsByEncodeTypesAndCodePoint(List<EncodeTypeEnum> encodeTypes, String codePoint);

    /**
     * 根据char_id查询生僻字list
     *
     * @param charIds 字符id
     * @return
     */
    List<RareCharacter> findByCharIds(Set<String> charIds);


    /**
     * 根据编码类型从对象中查询对应的code
     *
     * @param rareCharacters 字符
     * @param encodeTypeEnum 编码类型
     * @return
     */
    List<String> getCodesFromRareCharacters(List<RareCharacter> rareCharacters, EncodeTypeEnum encodeTypeEnum);

}
