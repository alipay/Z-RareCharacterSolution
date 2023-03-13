package com.alipay.rarecharacter.api;

import com.alipay.rarecharacter.core.manager.*;
import com.alipay.rarecharacter.core.model.*;
import com.alipay.rarecharacter.core.model.enums.RareCharacterResultCodeEnum;
import com.alipay.rarecharacter.core.service.RareNameServiceImpl;
import com.alipay.rarecharacter.dal.dao.spi.RareCharacterDAO;
import com.alipay.rarecharacter.util.EncodeTypeEnumUtil;
import com.alipay.rarecharacter.util.NameUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 生僻字姓名服务接口实现
 *
 * @author huyibing
 * @version $Id: RareNameFacadeImpl.java, v 0.1 2022年05月06日 下午14:23 huyibing Exp $
 */
@Component
public class RareNameApiImpl implements RareNameApi {

    /**
     * 生僻字姓名manager
     */
    @Autowired
    private RareNameManager rareNameManager;

    /**
     * 生僻字转换manager
     */
    @Autowired
    private RareNameTransferManager rareNameTransferManager;

    @Override
    public void registerRareCharacterDao(RareCharacterDAO rareCharacterDAO) {
        this.rareNameManager.setRareNameService(new RareNameServiceImpl());
        this.rareNameManager.getRareNameService().setRareCharacterDAO(rareCharacterDAO);

        this.rareNameTransferManager.setRareNameService(new RareNameServiceImpl());
        this.rareNameTransferManager.getRareNameService().setRareCharacterDAO(rareCharacterDAO);
    }

    /**
     * 姓名是否生僻字
     *
     * @param principalId    业务id
     * @param name           姓名
     * @param serviceContext 服务上下文
     * @return
     */
    @Override
    public RareNameCommonResult isRareName(final String principalId,
                                           final String name,
                                           final ServiceContext serviceContext) {

        RareNameCommonResult result = new RareNameCommonResult();

        // 参数检查
        if (!NameUtil.isValidCertName(name)) {
            result.setSuccess(true);
            result.setRetCode(RareCharacterResultCodeEnum.ILLEGAL_ARGUMENT.getCode());
        }

        // 执行
        RareCharacterResultCodeEnum rareCharacterResultCodeEnum = rareNameManager.isRareName(principalId, name);

        // 返回结果
        result.setSuccess(true);
        result.setRetCode(rareCharacterResultCodeEnum.getCode());
        return result;
    }


    /**
     * 姓名是否相同
     *
     * @param principalId    业务id
     * @param sourceName     源姓名
     * @param targetName     目标姓名
     * @param serviceContext 服务上下文
     * @return
     */
    @Override
    public RareNameCommonResult isSameRareName(final String principalId,
                                               final String sourceName,
                                               final String targetName,
                                               final ServiceContext serviceContext) {
        RareNameCommonResult result = new RareNameCommonResult();

        // 参数检查
        if (!NameUtil.isValidCertName(sourceName) || !NameUtil.isValidCertName(targetName)) {
            result.setSuccess(true);
            result.setRetCode(RareCharacterResultCodeEnum.ILLEGAL_ARGUMENT.getCode());
        }

        // 执行
        RareCharacterResultCodeEnum rareCharacterResultCodeEnum = rareNameManager.isSameRareName(principalId, sourceName, targetName);

        // 返回结果
        result.setSuccess(true);
        result.setRetCode(rareCharacterResultCodeEnum.getCode());
        return result;
    }


    /**
     * 姓名转码，仅支持unicode和pua互转
     * 若输入unicode姓名，则转成pua姓名
     * 若输入pua姓名，则转成unicode姓名
     *
     * @param principalId    业务id
     * @param name           姓名
     * @param serviceContext 服务上下文
     * @return
     */
    @Override
    public RareNameCommonResult transferUniAndPuaRareName(final String principalId,
                                                          final String name,
                                                          final ServiceContext serviceContext) {
        RareNameCommonResult result = new RareNameCommonResult();

        // 参数检查
        if (StringUtils.isBlank(name)) {
            result.setSuccess(true);
            result.setRetCode(RareCharacterResultCodeEnum.ILLEGAL_ARGUMENT.getCode());
        }

        // 执行
        String retName = rareNameTransferManager.transferUniAndPuaRareName(principalId, name);

        // 返回结果
        result.setSuccess(true);
        Map<String, Object> extResult = new HashMap<String, Object>();
        extResult.put("retName", retName);
        result.setExtResult(extResult);
        return result;
    }


    /**
     * 姓名转码
     *
     * @param principalId      业务id
     * @param name             姓名
     * @param targetEncodeType 期望编码
     * @param serviceContext   服务上下文
     * @return
     */
    @Override
    public RareNameCommonResult transferRareName(final String principalId,
                                                 final String name,
                                                 final String targetEncodeType,
                                                 final ServiceContext serviceContext) {
        RareNameCommonResult result = new RareNameCommonResult();

        // 参数检查
        if (StringUtils.isBlank(name)
                || !EncodeTypeEnumUtil.isRareNameSupportEncodeType(targetEncodeType)) {
            result.setSuccess(true);
            result.setRetCode(RareCharacterResultCodeEnum.ILLEGAL_ARGUMENT.getCode());
        }

        // 执行
        List<String> retNames = rareNameTransferManager.transferRareName(principalId, name, targetEncodeType);

        // 返回结果
        result.setSuccess(true);
        Map<String, Object> extResult = new HashMap<String, Object>();
        String retNameStr = org.apache.commons.lang.StringUtils.join(retNames, ",");
        extResult.put("retNameList", retNameStr);
        result.setExtResult(extResult);
        return result;
    }

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
    @Override
    public RareNameAssociateResult associateRareName(final String principalId,
                                                     final String name,
                                                     final ServiceContext serviceContext) {

        RareNameAssociateResult result = new RareNameAssociateResult();

        // 参数检查
        if (StringUtils.isBlank(name)) {
            result.setSuccess(true);
            result.setRetCode(RareCharacterResultCodeEnum.ILLEGAL_ARGUMENT.getCode());
        }

        // 执行
        List<RareNameInfo> retNameInfos = rareNameTransferManager.associateRareName(principalId, name);

        // 返回结果
        result.setSuccess(true);
        result.setRareNameInfos(retNameInfos);
        return result;
    }

    /**
     * Getter method for property <tt>rareNameManager</tt>.
     *
     * @return property value of rareNameManager
     */
    @Override
    public RareNameManager getRareNameManager() {
        return this.rareNameManager;
    }

    /**
     * Setter method for property <tt>rareNameManager</tt>.
     *
     * @param rareNameManager value to be assigned to property rareNameManager
     */
    @Override
    public void setRareNameManager(RareNameManager rareNameManager) {
        this.rareNameManager = rareNameManager;
    }


    /**
     * Getter method for property <tt>rareNameTransferManager</tt>.
     *
     * @return property value of rareNameTransferManager
     */
    @Override
    public RareNameTransferManager getRareNameTransferManager() {
        return this.rareNameTransferManager;
    }

    /**
     * Setter method for property <tt>rareNameTransferManager</tt>.
     *
     * @param rareNameTransferManager value to be assigned to property rareNameTransferManager
     */
    @Override
    public void setRareNameTransferManager(RareNameTransferManager rareNameTransferManager) {
        this.rareNameTransferManager = rareNameTransferManager;
    }

}

