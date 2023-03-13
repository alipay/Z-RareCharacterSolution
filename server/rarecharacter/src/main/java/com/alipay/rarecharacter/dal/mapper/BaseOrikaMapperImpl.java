package com.alipay.rarecharacter.dal.mapper;

import java.util.List;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.MappingContextFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.impl.MapperFacadeImpl;
import ma.glasnost.orika.metadata.ClassMapBuilder;
import ma.glasnost.orika.unenhance.BaseUnenhancer;

/**
 * 模型映射基类
 *
 * @author yanpeng.yp
 * @version $Id: BaseOrikaMapperImpl.java, v 0.1 2014年12月26日 下午2:15:48 yanpeng.yp Exp $
 */
public abstract class BaseOrikaMapperImpl implements ObjectMapper {
    /**
     * mapperFacade
     */
    private MapperFacade mapperFacade = new MapperFacadeImpl(new DefaultMapperFactory.Builder().build(), new MappingContext.Factory(), new BaseUnenhancer());

    /**
     * 初始化
     */
    public void init() {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();

        registerMapClass(mapperFactory);

        mapperFacade = mapperFactory.getMapperFacade();
    }

    /**
     * 注册需要映射的类
     *
     * @param mapperFactory
     */
    protected abstract void registerMapClass(MapperFactory mapperFactory);

    /**
     * @see ObjectMapper#map(java.lang.Object, java.lang.Class)
     */
    public <A, B> B map(A source, Class<B> clazz) {
        if (source == null) {
            return null;
        }

        return mapperFacade.map(source, clazz);
    }

    /**
     * @see ObjectMapper#map(java.lang.Object, java.lang.Object)
     */
    public <A, B> void map(A source, B destination) {
        if (source == null || destination == null) {
            return;
        }
        mapperFacade.map(source, destination);
    }

    /**
     * @see ObjectMapper#map(java.util.List, java.lang.Class)
     */
    public <A, B> List<B> map(List<A> sourceList, Class<B> clazz) {
        if (sourceList == null) {
            return null;
        }

        return mapperFacade.mapAsList(sourceList, clazz);
    }

    /**
     * Getter method for property <tt>mapperFacade</tt>.
     *
     * @return property value of mapperFacade
     */
    protected MapperFacade getMapperFacade() {
        return mapperFacade;
    }
}