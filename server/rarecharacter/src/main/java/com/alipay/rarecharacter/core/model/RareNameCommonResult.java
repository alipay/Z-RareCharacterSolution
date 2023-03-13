package com.alipay.rarecharacter.core.model;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import java.lang.reflect.Field;
import java.util.Map;


/**
 * 姓名服务接口返回
 *
 * @author huyibing
 * @version $Id: RareNameCommonResult.java, v 0.1 2022年05月06日 下午14:23 huyibing Exp $
 */
public class RareNameCommonResult {

    private static final long serialVersionUID = 2274166374427935599L;

    /** 是否成功 */
    private boolean           success;

    /**
     * 业务结果码，参考 RareCharacterResultCodeEnum
     */
    private String retCode;
    /**
     * 扩展出参
     */
    private Map<String, Object> extResult;


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public Map<String, Object> getExtResult() {
        return extResult;
    }

    public void setExtResult(Map<String, Object> extResult) {
        this.extResult = extResult;
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