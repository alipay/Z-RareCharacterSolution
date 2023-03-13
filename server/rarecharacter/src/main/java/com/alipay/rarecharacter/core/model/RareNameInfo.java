package com.alipay.rarecharacter.core.model;

/**
 * @author huyibing
 * @version $Id: RareNameInfo.java, v 0.1 2022年12月16日 下午22:28 huyibing Exp $
 */
public class RareNameInfo {

    /**
     * 姓名
     */
    String name;

    /**
     * 备注
     */
    String remarks;

    /**
     * Getter method for property <tt>name</tt>.
     *
     * @return property value of name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter method for property <tt>name</tt>.
     *
     * @param name value to be assigned to property name
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * Getter method for property <tt>remarks</tt>.
     *
     * @return property value of remarks
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * Setter method for property <tt>remarks</tt>.
     *
     * @param remarks value to be assigned to property remarks
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

}

