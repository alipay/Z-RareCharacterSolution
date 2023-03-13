package com.alipay.rarecharacter.dal.mapper;

import com.alipay.rarecharacter.core.model.RareCharacter;
import com.alipay.rarecharacter.dal.dataobject.RareCharacterDO;
import ma.glasnost.orika.MapperFactory;

/**
 * @author huyibing
 */
public class ObjectMapperImpl extends BaseOrikaMapperImpl {

    @Override
    protected void registerMapClass(MapperFactory mapperFactory) {
        // 生僻字记录
        mapperFactory.classMap(RareCharacter.class, RareCharacterDO.class).byDefault()
                .register();

    }
}

