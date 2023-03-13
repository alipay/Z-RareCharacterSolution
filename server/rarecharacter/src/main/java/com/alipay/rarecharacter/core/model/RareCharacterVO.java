package com.alipay.rarecharacter.core.model;


import java.util.List;

/**
 * 生僻字VO
 *
 * @author huyibing
 * @version $Id: RareCharacterVO.java, v 0.1 2022年09月25日 下午14:23 huyibing Exp $
 */
public class RareCharacterVO {

    private static final long serialVersionUID = 741231858441822688L;

    //========== properties ==========

    /**
     * 唯一标识一个字符
     */
    private String charId;

    /**
     * unicode字符
     */
    private String unicodeChar;

    /**
     * unicode码点
     */
    private String unicodeCodePoint;

    /**
     * unicode字体
     */
    private String unicodeFont;

    /**
     * pua字符
     */
    private String puaChar;

    /**
     * pua码点
     */
    private String puaCodePoint;

    /**
     * pua字体
     */
    private String puaFont;

    /**
     * 拼音
     */
    private List<String> pinYinChars;

    /**
     * 拆字
     */
    private List<String> splitChars;

    /**
     * 权重
     */
    private String weight;

    /**
     * 扩展字段
     */
    private String extInfo;

    @Override
    public String toString() {
        return "RareCharacter{" +
                "charId='" + charId + '\'' +
                ", unicodeChar='" + unicodeChar + '\'' +
                ", unicodeCodePoint=" + unicodeCodePoint +
                ", unicodeFont='" + unicodeFont + '\'' +
                ", puaChar='" + puaChar + '\'' +
                ", puaCodePoint='" + puaCodePoint + '\'' +
                ", puaFont='" + puaFont + '\'' +
                ", pinYinChars='" + pinYinChars + '\'' +
                ", splitChars='" + splitChars + '\'' +
                ", extInfo='" + extInfo + '\'' +
                '}';
    }

//========== getters and setters ==========

    public String getCharId() {
        return charId;
    }

    public void setCharId(String charId) {
        this.charId = charId;
    }

    public String getUnicodeCodePoint() {
        return unicodeCodePoint;
    }

    public void setUnicodeCodePoint(String unicodeCodePoint) {
        this.unicodeCodePoint = unicodeCodePoint;
    }

    public String getUnicodeChar() {
        return unicodeChar;
    }

    public void setUnicodeChar(String unicodeChar) {
        this.unicodeChar = unicodeChar;
    }

    public String getPuaCodePoint() {
        return puaCodePoint;
    }

    public void setPuaCodePoint(String puaCodePoint) {
        this.puaCodePoint = puaCodePoint;
    }

    public String getPuaChar() {
        return puaChar;
    }

    public void setPuaChar(String puaChar) {
        this.puaChar = puaChar;
    }

    public List<String> getPinYinChars() {
        return pinYinChars;
    }

    public void setPinYinChars(List<String> pinYinChars) {
        this.pinYinChars = pinYinChars;
    }

    public List<String> getSplitChars() {
        return splitChars;
    }

    public void setSplitChars(List<String> splitChars) {
        this.splitChars = splitChars;
    }

    public String getExtInfo() {
        return extInfo;
    }

    public void setExtInfo(String extInfo) {
        this.extInfo = extInfo;
    }

    public String getUnicodeFont() {
        return unicodeFont;
    }

    public void setUnicodeFont(String unicodeFont) {
        this.unicodeFont = unicodeFont;
    }

    public String getPuaFont() {
        return puaFont;
    }

    public void setPuaFont(String puaFont) {
        this.puaFont = puaFont;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}
