package com.alipay.rarecharacter.dal.dataobject;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author huyibing
 * @version $Id: RareCharacterDO.java, v 0.1 2022年12月28日 上午11:36:06 huyibing Exp $
 */
public class RareCharacterDO {

    private static final long serialVersionUID = 741231858441822688L;

    //========== properties ==========

    /**
     * This property corresponds to db column <tt>id</tt>.
     */
    private String id;

    /**
     * 唯一标识一个字符
     * This property corresponds to db column <tt>char_id</tt>.
     */
    private String charId;

    /**
     * 字符编码类型
     * This property corresponds to db column <tt>encode_type</tt>.
     */
    private String encodeType;

    /**
     * 编码
     * This property corresponds to db column <tt>code</tt>.
     */
    private String code;

    /**
     * 原字对应的码点
     * This property corresponds to db column <tt>codePoint</tt>.
     */
    private String codePoint;

    /**
     * 编码对应的ncrCode
     * This property corresponds to db column <tt>ncrCode</tt>.
     */
    private String ncrCode;

    /**
     * 拼音音调
     * This property corresponds to db column <tt>tone</tt>.
     */
    private String tone;

    /**
     * 字符图片地址
     * This property corresponds to db column <tt>pic</tt>.
     */
    private String pic;

    /**
     * 字体
     * This property corresponds to db column <tt>font</tt>.
     */
    private String font;

    /**
     * 权重
     * This property corresponds to db column <tt>weight</tt>.
     */
    private String weight;

    /**
     * 扩展字段
     * This property corresponds to db column <tt>ext_info</tt>.
     */
    private String extInfo;

    /**
     * 创建时间
     * This property corresponds to db column <tt>gmt_create</tt>.
     */
    private Date gmtCreate;

    /**
     * 修改时间
     * This property corresponds to db column <tt>gmt_modified</tt>.
     */
    private Date gmtModified;

    //========== getters and setters ==========

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCharId() {
        return charId;
    }

    public void setCharId(String charId) {
        this.charId = charId;
    }

    public String getEncodeType() {
        return encodeType;
    }

    public void setEncodeType(String encodeType) {
        this.encodeType = encodeType;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCodePoint() {
        return codePoint;
    }

    public void setCodePoint(String codePoint) {
        this.codePoint = codePoint;
    }

    public String getNcrCode() {
        return ncrCode;
    }

    public void setNcrCode(String ncrCode) {
        this.ncrCode = ncrCode;
    }

    public String getTone() {
        return tone;
    }

    public void setTone(String tone) {
        this.tone = tone;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getExtInfo() {
        return extInfo;
    }

    public void setExtInfo(String extInfo) {
        this.extInfo = extInfo;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public String getFont() {
        return font;
    }

    public void setFont(String font) {
        this.font = font;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return ReflectionToStringBuilder.reflectionToString(this);
    }

    /**
     * 对象转换
     *
     * @param data
     * @return
     */
    public static RareCharacterDO toObject(Map<String, Object> data) {
        RareCharacterDO rareCharacterDO = new RareCharacterDO();
        if (data == null) {
            return rareCharacterDO;
        }

        rareCharacterDO.setId((String) data.get("id"));
        rareCharacterDO.setCharId((String) data.get("char_id"));
        rareCharacterDO.setEncodeType((String) data.get("encode_type"));
        rareCharacterDO.setCode((String) data.get("code"));
        rareCharacterDO.setNcrCode((String) data.get("ncr_code"));
        rareCharacterDO.setTone((String) data.get("tone"));
        rareCharacterDO.setPic((String) data.get("pic"));
        rareCharacterDO.setExtInfo((String) data.get("ext_info"));
        rareCharacterDO.setCodePoint((String) data.get("code_point"));
        rareCharacterDO.setFont((String) data.get("font"));
        rareCharacterDO.setWeight((String) data.get("weight"));

        try {
            Date createDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").parse((String) data.get("gmt_create"));
            rareCharacterDO.setGmtCreate(createDate);
            Date modifyDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").parse((String) data.get("gmt_modified"));
            rareCharacterDO.setGmtCreate(modifyDate);
        } catch (Exception e) {
            rareCharacterDO.setGmtCreate(new Date());
            rareCharacterDO.setGmtModified(new Date());
        }
        return rareCharacterDO;
    }

    /**
     * 对象转换
     *
     * @param dataList
     * @return
     */
    public static List<RareCharacterDO> toObject(List<Map<String, Object>> dataList) {
        List<RareCharacterDO> rareCharacterDOList = new ArrayList<>();
        if (dataList == null) {
            return rareCharacterDOList;
        }

        for (Map<String, Object> data : dataList) {
            RareCharacterDO rareCharacterDO = toObject(data);
            rareCharacterDOList.add(rareCharacterDO);
        }

        return rareCharacterDOList;
    }
}

