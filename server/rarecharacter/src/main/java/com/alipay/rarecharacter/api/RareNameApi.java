package com.alipay.rarecharacter.api;

import com.alipay.rarecharacter.core.manager.RareNameManager;
import com.alipay.rarecharacter.core.manager.RareNameTransferManager;
import com.alipay.rarecharacter.core.model.RareNameAssociateResult;
import com.alipay.rarecharacter.core.model.RareNameCommonResult;
import com.alipay.rarecharacter.core.model.ServiceContext;
import com.alipay.rarecharacter.dal.dao.spi.RareCharacterDAO;

/**
 * 姓名服务接口
 *
 * @author huyibing
 * @version $Id: RareNameApi.java, v 0.1 2022年05月06日 下午7:36 huyibing Exp $
 */
public interface RareNameApi {

    /**
     * 使用方注册RareCharacterDAO实现
     *
     * @param dao
     */
    public void registerRareCharacterDao(RareCharacterDAO dao);


    /**
     * 姓名是否生僻字
     *
     * @param principalId    业务id
     * @param name           姓名
     * @param serviceContext 服务上下文
     * @return
     */
    public RareNameCommonResult isRareName(String principalId,
                                           String name,
                                           ServiceContext serviceContext);

    /**
     * 姓名是否相同
     *
     * @param principalId    业务id
     * @param sourceName     源姓名
     * @param targetName     目标姓名
     * @param serviceContext 服务上下文
     * @return
     */
    public RareNameCommonResult isSameRareName(String principalId,
                                               String sourceName,
                                               String targetName,
                                               ServiceContext serviceContext);


    /**
     * 姓名转码
     * 若输入unicode姓名，则转成pua姓名，若输入pua姓名，则转成unicode姓名
     * 若输入姓名（不存在pua或unicode时）或（Z字库未收录时），将输入姓名原样返回
     *
     * @param principalId    业务id
     * @param name           姓名
     * @param serviceContext 服务上下文
     * @return
     */
    public RareNameCommonResult transferUniAndPuaRareName(String principalId,
                                                          String name,
                                                          ServiceContext serviceContext);


    /**
     * 姓名转码，指定目标编码
     *
     * @param principalId      业务id
     * @param name             姓名
     * @param targetEncodeType 期望编码
     * @param serviceContext   服务上下文
     * @return
     */
    public RareNameCommonResult transferRareName(String principalId,
                                                 String name,
                                                 String targetEncodeType,
                                                 ServiceContext serviceContext);


    /**
     * 姓名联想
     * 如：输入张yan，可联想出张（YAN），张YAN等
     * 输入张?，可联想出张（YAN），张YAN，张YAN3等
     *
     * @param principalId    业务id
     * @param name           姓名
     * @param serviceContext 服务上下文
     * @return
     */
    public RareNameAssociateResult associateRareName(String principalId,
                                                     String name,
                                                     ServiceContext serviceContext);

    /**
     * Getter method for property <tt>RareNameManager</tt>.
     *
     * @return property value of RareNameManager
     */
    public RareNameManager getRareNameManager();

    /**
     * Setter method for property <tt>rareNameManager</tt>.
     *
     * @param rareNameManager value to be assigned to property rareNameManager
     */
    public void setRareNameManager(RareNameManager rareNameManager);

    /**
     * Getter method for property <tt>RareNameTransferManager</tt>.
     *
     * @return property value of RareNameTransferManager
     */
    public RareNameTransferManager getRareNameTransferManager();

    /**
     * Setter method for property <tt>rareNameTransferManager</tt>.
     *
     * @param rareNameTransferManager value to be assigned to property rareNameTransferManager
     */
    public void setRareNameTransferManager(RareNameTransferManager rareNameTransferManager);

}