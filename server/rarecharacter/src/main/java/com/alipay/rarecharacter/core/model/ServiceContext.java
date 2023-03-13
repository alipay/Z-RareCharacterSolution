package com.alipay.rarecharacter.core.model;

import java.io.Serializable;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 服务上下文
 *
 * @author jiangzhen.jz
 * @version $Id: ServiceContext.java, v 0.1 2014年11月6日 下午2:03:14 jiangzhen.jz Exp $
 */
public class ServiceContext implements Serializable {

    /** serialVersionUID */
    private static final long   serialVersionUID = -830740263284277364L;

    /** 上下文变量 */
    private Map<String, String> context;

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public Map<String, String> getContext() {
        return context;
    }

    public void setContext(Map<String, String> context) {
        this.context = context;
    }
}
