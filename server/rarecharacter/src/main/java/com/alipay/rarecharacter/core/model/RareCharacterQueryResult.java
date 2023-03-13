package com.alipay.rarecharacter.core.model;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import java.lang.reflect.Field;
import java.util.List;

/**
 * 生僻字查询返回
 *
 * @author huyibing
 * @version $Id: RareCharacterQueryRequest.java, v 0.1 2022年09月25日 下午14:23 huyibing Exp $
 */
public class RareCharacterQueryResult  {

    private static final long serialVersionUID = 6735570302836655651L;

    /** 是否成功 */
    private boolean           success;

    /**
     * 生僻字列表
     */
    private List<RareCharacterVO> rareCharacterVOList;


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<RareCharacterVO> getRareCharacterVOList() {
        return rareCharacterVOList;
    }

    public void setRareCharacterVOList(List<RareCharacterVO> rareCharacterVOList) {
        this.rareCharacterVOList = rareCharacterVOList;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return (new ReflectionToStringBuilder(this) {
            protected boolean accept(Field f) {
                return super.accept(f) && !f.getName().equals("resultObj");
            }
        }).toString();
    }
}
