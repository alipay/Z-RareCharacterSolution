package com.example.rarecharacterdemo.controller;

import com.alipay.rarecharacter.api.RareNameApi;
import com.alipay.rarecharacter.core.model.RareNameAssociateResult;
import com.alipay.rarecharacter.core.model.RareNameCommonResult;
import com.alipay.rarecharacter.core.model.ServiceContext;
import com.alipay.rarecharacter.dal.dao.spi.RareCharacterDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 生僻字服务Controller
 *
 * @author huyibing
 * @version $Id: RareCharacterController.java, v 0.1 2022年05月06日 下午14:23 huyibing Exp $
 */
@RestController
//@CrossOrigin(originPatterns = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,RequestMethod.PATCH, RequestMethod.DELETE, RequestMethod.OPTIONS, RequestMethod.TRACE, RequestMethod.HEAD, RequestMethod.DELETE})
@RequestMapping(value = "/rc")
public class RareCharacterController {

    /**
     * 生僻字服务api
     */
    @Autowired
    private RareNameApi rareNameApi;

    /**
     * 生僻字服务dao
     */
    @Autowired
    private RareCharacterDAO rareCharacterDAO;

    /**
     * 将dao实现装配至api
     */
    public void register() {
        rareNameApi.registerRareCharacterDao(rareCharacterDAO);
    }

    /**
     * 姓名是否生僻字
     *
     * @param principalId    业务id
     * @param name           姓名
     * @param serviceContext 服务上下文
     * @return
     */
    @RequestMapping(value = "/isRareName", method = RequestMethod.GET)
    public RareNameCommonResult isRareName(String principalId, String name, ServiceContext serviceContext) {
        register();
        return rareNameApi.isRareName(principalId, name, serviceContext);
    }

    /**
     * 生僻字姓名是否相同
     *
     * @param principalId    业务id
     * @param sourceName     源姓名
     * @param targetName     目标姓名
     * @param serviceContext 服务上下文
     * @return
     */
    @RequestMapping(value = "/isSameRareName", method = RequestMethod.GET)
    public RareNameCommonResult isSameRareName(String principalId, String sourceName, String targetName, ServiceContext serviceContext) {
        register();
        return rareNameApi.isSameRareName(principalId, sourceName, targetName, serviceContext);
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
    @RequestMapping(value = "/transferUniAndPuaRareName", method = RequestMethod.GET)
    public RareNameCommonResult transferUniAndPuaRareName(String principalId, String name, ServiceContext serviceContext) {
        register();
        return rareNameApi.transferUniAndPuaRareName(principalId, name, serviceContext);
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
    @RequestMapping(value = "/transferRareName", method = RequestMethod.GET)
    public RareNameCommonResult transferRareName(String principalId, String name, String targetEncodeType, ServiceContext serviceContext) {
        register();
        return rareNameApi.transferRareName(principalId, name, targetEncodeType, serviceContext);
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
    @RequestMapping(value = "/associateRareName", method = RequestMethod.GET)
    public RareNameAssociateResult associateRareName(String principalId, String name, ServiceContext serviceContext) {
        register();
        return rareNameApi.associateRareName(principalId, name, serviceContext);
    }

}

