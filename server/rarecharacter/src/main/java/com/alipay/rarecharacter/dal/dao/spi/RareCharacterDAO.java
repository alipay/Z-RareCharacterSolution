package com.alipay.rarecharacter.dal.dao.spi;

import com.alipay.rarecharacter.dal.dataobject.RareCharacterDO;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * @author huyibing
 * @version $Id: RareCharacterDAO.java, v 0.1 2022年05月06日 下午07:36:06 huyibing Exp $
 */
public interface RareCharacterDAO {

    /**
     * Query DB table <tt>rare_characters</tt> for records.
     *
     * <p>
     * Description for this operation is<br>
     * <tt></tt>
     * <p>
     * The sql statement for this operation is <br>
     * <tt>select id, char_id, encode_type, code, code_point, ncr_code, tone, pic, ext_info, gmt_create, gmt_modified from rare_characters where (encode_type = ? and code = ?)</tt>
     *
     * @param encodeType
     * @param code
     * @return List<RareCharacterDO>
     * @throws DataAccessException
     */
    public List<RareCharacterDO> findByEncodeTypeAndCode(String encodeType, String code) throws DataAccessException;


    /**
     * Query DB table <tt>rare_characters</tt> for records.
     *
     * <p>
     * Description for this operation is<br>
     * <tt></tt>
     * <p>
     * The sql statement for this operation is <br>
     * <tt>select id, char_id, encode_type, code, code_point,ncr_code, tone, pic, ext_info, gmt_create, gmt_modified from rare_characters where (encode_type in (?) and code = ?)</tt>
     *
     * @param encodeTypes
     * @param code
     * @return List<RareCharacterDO>
     * @throws DataAccessException
     */
    public List<RareCharacterDO> findByEncodeTypesAndCode(List<String> encodeTypes, String code) throws DataAccessException;

    /**
     * Query DB table <tt>rare_characters</tt> for records.
     *
     * <p>
     * Description for this operation is<br>
     * <tt></tt>
     * <p>
     * The sql statement for this operation is <br>
     * <tt>select id, char_id, encode_type, code, code_point, ncr_code, tone, pic, ext_info, gmt_create, gmt_modified from rare_characters where (encode_type in (?) and ncr_code = ?)</tt>
     *
     * @param encodeTypes
     * @param codePoint
     * @return List<RareCharacterDO>
     * @throws DataAccessException
     */
    public List<RareCharacterDO> findByEncodeTypesAndCodePoint(List<String> encodeTypes, String codePoint) throws DataAccessException;


    /**
     * Query DB table <tt>rare_characters</tt> for records.
     *
     * <p>
     * Description for this operation is<br>
     * <tt></tt>
     * <p>
     * The sql statement for this operation is <br>
     * <tt>select id, char_id, encode_type, code, code_point, ncr_code, tone, pic, ext_info, gmt_create, gmt_modified from rare_characters where (char_id in (?) )</tt>
     *
     * @param charId
     * @return List<RareCharacterDO>
     * @throws DataAccessException
     */
    public List<RareCharacterDO> findByCharIds(List<String> charId) throws DataAccessException;

    /**
     * INSERT DB table <tt>rare_characters</tt> for records.
     *
     * <p>
     * Description for this operation is<br>
     * <tt></tt>
     * <p>
     * The sql statement for this operation is <br>
     * <tt></tt>
     *
     * @param rareCharacterDO
     * @return List<RareCharacterDO>
     * @throws DataAccessException
     */
    public String insert(RareCharacterDO rareCharacterDO) throws DataAccessException;
}
