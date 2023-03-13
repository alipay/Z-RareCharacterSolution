package com.alipay.rarecharacter.dal.mapper;

import java.util.List;

/**
 * 模型映射接口
 *
 * @author yanpeng.yp
 * @version $Id: ObjectMapper.java, v 0.1 2014年12月26日 下午2:14:36 yanpeng.yp Exp $
 */
public interface ObjectMapper {

    /**
     * 映射
     *
     * @param source
     * @param clazz
     * @return
     */
    <A, B> B map(A source, Class<B> clazz);

    /**
     * 映射
     *
     * @param source
     * @param destination
     * @return
     */
    <A, B> void map(A source, B destination);

    /**
     * 映射
     *
     * @param sourceList
     * @param clazz
     */
    <A, B> List<B> map(List<A> sourceList, Class<B> clazz);
}
