package com.example.rarecharacterdemo.dao;

import com.alipay.rarecharacter.dal.dao.spi.RareCharacterDAO;
import com.alipay.rarecharacter.dal.dataobject.RareCharacterDO;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * 生僻字DAO实现
 *
 * @author huyibing
 * @version $Id: RareCharacterDAOImpl.java, v 0.1 2022年05月06日 下午14:23 huyibing Exp $
 */
@Component
public class RareCharacterDAOImpl implements RareCharacterDAO {

    /**
     * jdbc连接工具类
     */
    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<RareCharacterDO> findByEncodeTypeAndCode(String encodeType, String code) throws DataAccessException {
        String sql = " select * from rare_characters where encode_type = ? and code = ?";
        Object[] args = new Object[]{encodeType, code};
        List<Map<String, Object>> lists = jdbcTemplate.queryForList(sql, args);
        return RareCharacterDO.toObject(lists);
    }

    @Override
    public List<RareCharacterDO> findByEncodeTypesAndCode(List<String> encodeTypes, String code) throws DataAccessException {
        String sql = " select * from rare_characters where encode_type in (:encodeTypes) and code = :code";
        Map<String, Object> args = new HashMap<>();
        args.put("encodeTypes", encodeTypes);
        args.put("code", code);
        NamedParameterJdbcTemplate givenParamJdbcTemp = new NamedParameterJdbcTemplate(jdbcTemplate);
        List<Map<String, Object>> lists = givenParamJdbcTemp.queryForList(sql, args);
        return RareCharacterDO.toObject(lists);
    }

    @Override
    public List<RareCharacterDO> findByEncodeTypesAndCodePoint(List<String> encodeTypes, String codePoint) throws DataAccessException {
        String sql = " select * from rare_characters where encode_type in (:encodeTypes) and code_point = :codePoint";
        Map<String, Object> args = new HashMap<>();
        args.put("encodeTypes", encodeTypes);
        args.put("codePoint", codePoint);
        NamedParameterJdbcTemplate givenParamJdbcTemp = new NamedParameterJdbcTemplate(jdbcTemplate);
        List<Map<String, Object>> lists = givenParamJdbcTemp.queryForList(sql, args);
        return RareCharacterDO.toObject(lists);
    }

    @Override
    public List<RareCharacterDO> findByCharIds(List<String> charIds) throws DataAccessException {
        String sql = " select * from rare_characters where char_id in (:charIds) ";
        Map<String, Object> args = new HashMap<>();
        args.put("charIds", charIds);
        NamedParameterJdbcTemplate givenParamJdbcTemp = new NamedParameterJdbcTemplate(jdbcTemplate);
        List<Map<String, Object>> lists = givenParamJdbcTemp.queryForList(sql, args);
        return RareCharacterDO.toObject(lists);
    }

    @Override
    public String insert(RareCharacterDO rareCharacterDO) throws DataAccessException {
        jdbcTemplate.update("insert into rare_characters(id,char_id,encode_type,code,ncr_code,tone,pic,ext_info,gmt_create,gmt_modified,code_point,font,weight) values(?,?,?,?,?,?,?,?,?,?,?,?,?)",
                rareCharacterDO.getId(),
                rareCharacterDO.getCharId(),
                rareCharacterDO.getEncodeType(),
                rareCharacterDO.getCode(),
                rareCharacterDO.getNcrCode(),
                rareCharacterDO.getTone(),
                rareCharacterDO.getPic(),
                rareCharacterDO.getExtInfo(),
                new Date(),
                new Date(),
                rareCharacterDO.getCodePoint(),
                rareCharacterDO.getFont(),
                rareCharacterDO.getWeight());

        return "";
    }

}

