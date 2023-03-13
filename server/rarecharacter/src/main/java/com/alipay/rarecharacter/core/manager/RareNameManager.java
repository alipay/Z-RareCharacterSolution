package com.alipay.rarecharacter.core.manager;

import com.alipay.rarecharacter.core.model.enums.RareCharacterResultCodeEnum;
import com.alipay.rarecharacter.core.service.RareNameService;

/**
 * 生僻字manager
 *
 * @author huyibing
 * @version $Id: RareNameManager.java, v 0.1 2022年05月06日 下午14:23 huyibing Exp $
 */
public interface RareNameManager {


    /**
     * 姓名是否生僻字
     *
     * @param principalId 业务id
     * @param name        姓名
     * @return
     */
    RareCharacterResultCodeEnum isRareName(String principalId, String name);


    /**
     * 姓名是否相同
     *
     * @param principalId 业务id
     * @param sourceName  源姓名
     * @param targetName  目标姓名
     * @return
     */
    RareCharacterResultCodeEnum isSameRareName(String principalId, String sourceName, String targetName);


    /**
     * 姓名是否含unicode 或 pua原字生僻字
     *
     * @param principalId 业务id
     * @param name        姓名
     * @return
     */
    RareCharacterResultCodeEnum isOriginalRareName(String principalId, String name);

    /**
     * 姓名是否含unicode原字生僻字
     *
     * @param principalId 业务id
     * @param name        姓名
     * @return
     */
    RareCharacterResultCodeEnum isUnicodeRareName(String principalId, String name);

    /**
     * 姓名是否含pua原字生僻字
     *
     * @param principalId 业务id
     * @param name        姓名
     * @return
     */
    RareCharacterResultCodeEnum isPuaRareName(String principalId, String name);

    /**
     * 姓名是否含拼音生僻字
     *
     * @param principalId 业务id
     * @param name        姓名
     * @return
     */
    RareCharacterResultCodeEnum isPinyinRareName(String principalId, String name);

    /**
     * 姓名是否含拆字生僻字
     *
     * @param principalId 业务id
     * @param name        姓名
     * @return
     */
    RareCharacterResultCodeEnum isSplitRareName(String principalId, String name);

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
