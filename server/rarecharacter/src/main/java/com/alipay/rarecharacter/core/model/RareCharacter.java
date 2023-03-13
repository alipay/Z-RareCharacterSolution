package com.alipay.rarecharacter.core.model;


import com.alipay.rarecharacter.core.model.enums.EncodeTypeEnum;

import java.util.Date;

/**
 * 生僻字model
 *
 * @author huyibing
 * @version $Id: RareCharacter.java, v 0.1 2022年05月06日 下午14:23 huyibing Exp $
 */
public class RareCharacter {

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
    private EncodeTypeEnum encodeType;

    /**
     * 编码
     * This property corresponds to db column <tt>code</tt>.
     */
    private String code;

    /**
     * 编码对应的ncrCode
     * This property corresponds to db column <tt>ncrCodes</tt>.
     */
    private String ncrCode;

    /**
     * 拼音音调
     * This property corresponds to db column <tt>tone</tt>.
     */
    private String tone;

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

    /**
     * 码点
     * This property corresponds to db column <tt>code_point</tt>.
     */
    private String codePoint;

    /**
     * 字体
     * This property corresponds to db column <tt>font</tt>.
     */
    private String font;

    /**
     * 字体权重
     * This property corresponds to db column <tt>weight</tt>.
     */
    private String weight;

    @Override
    public String toString() {
        return "RareCharacter{" +
                "id='" + id + '\'' +
                ", charId='" + charId + '\'' +
                ", encodeType=" + encodeType +
                ", code='" + code + '\'' +
                ", ncrCode='" + ncrCode + '\'' +
                ", tone='" + tone + '\'' +
                ", extInfo='" + extInfo + '\'' +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", codePoint=" + codePoint +
                ", font=" + font +
                ", weight=" + weight +
                '}';
    }

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

    public EncodeTypeEnum getEncodeType() {
        return encodeType;
    }

    public void setEncodeType(EncodeTypeEnum encodeType) {
        this.encodeType = encodeType;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public String getCodePoint() {
        return codePoint;
    }

    public void setCodePoint(String codePoint) {
        this.codePoint = codePoint;
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
}

