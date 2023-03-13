package com.alipay.rarecharacter.core.manager;

import com.alipay.rarecharacter.core.model.RareNameInfo;
import com.alipay.rarecharacter.core.service.RareNameService;

import java.util.List;

/**
 * @author huyibing
 */
public interface RareNameTransferManager {

    /**
     * 姓名转码
     *
     * @param principalId      业务id
     * @param name             姓名
     * @param targetEncodeType 目标编码
     * @return
     */
    List<String> transferRareName(String principalId, String name, String targetEncodeType);


    /**
     * 姓名转码，仅支持unicode和pua互转
     * 若输入unicode姓名，则转成pua姓名，若输入pua姓名，则转成unicode姓名
     * 若输入姓名（不存在pua或unicode时）或（Z字库未收录时），将输入姓名原样返回
     *
     * @param principalId 业务id
     * @param name        姓名
     * @return
     */
    String transferUniAndPuaRareName(String principalId, String name);


    /**
     * unicode原字生僻字转码
     *
     * @param principalId      业务id
     * @param name             姓名
     * @param targetEncodeType 目标编码
     * @return
     */
    List<String> transferUnicodeRareName(String principalId, String name, String targetEncodeType);


    /**
     * pua原字生僻字转码
     *
     * @param principalId      业务id
     * @param name             姓名
     * @param targetEncodeType 目标编码
     * @return
     */
    List<String> transferPuaRareName(String principalId, String name, String targetEncodeType);


    /**
     * 拼音生僻字转码
     *
     * @param principalId      业务id
     * @param name             姓名
     * @param targetEncodeType 目标编码
     * @return
     */
    List<String> transferPinyinRareName(String principalId, String name, String targetEncodeType);


    /**
     * 拆字生僻字转码
     *
     * @param principalId      业务id
     * @param name             姓名
     * @param targetEncodeType 目标编码
     * @return
     */
    List<String> transferSplitRareName(String principalId, String name, String targetEncodeType);


    /**
     * 姓名联想
     *
     * @param principalId 业务id
     * @param name        姓名
     * @return
     */
    List<RareNameInfo> associateRareName(String principalId, String name);

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
     * Getter method for property <tt>RareNameService</tt>.
     *
     * @return property value of RareNameService
     */
    public RareNameService getRareNameService();


    /**
     * Setter method for property <tt>rareNameService</tt>.
     *
     * @param rareNameService value to be assigned to property rareNameService
     */
    public void setRareNameService(RareNameService rareNameService);

}
