package com.example.rarecharacterdemo;

import com.alipay.rarecharacter.core.model.RareNameCommonResult;
import com.alipay.rarecharacter.core.model.ServiceContext;
import com.alipay.rarecharacter.core.model.enums.RareCharacterResultCodeEnum;
import com.example.rarecharacterdemo.controller.RareCharacterController;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Controller测试类
 *
 * @author huyibing
 * @version $Id: RareCharacterControllerTest.java, v 0.1 2022年05月06日 下午14:23 huyibing Exp $
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RarecharacterdemoApplication.class, RareCharacterControllerTest.class})
public class RareCharacterControllerTest {

    @Autowired
    private RareCharacterController rareCharacterController;

    @Test
    public void test() {

        rareCharacterController.register();

        testIsRareName();

        testIsSameRareName();
    }

    /**
     * 测试姓名是否含生僻字
     */
    private void testIsRareName(){
        // 拼音替代
        RareNameCommonResult result =  rareCharacterController.isRareName("2088","张wei", new ServiceContext());
        Assert.assertEquals(result.getRetCode(), RareCharacterResultCodeEnum.IS_RARE_NAME.getCode());

        // unicode原字 韦华
        result =  rareCharacterController.isRareName("2088","张𮧵", new ServiceContext());
        Assert.assertEquals(result.getRetCode(),RareCharacterResultCodeEnum.IS_RARE_NAME.getCode());

        // unicode原字 龙天
        result =  rareCharacterController.isRareName("2088","张䶮", new ServiceContext());
        Assert.assertEquals(result.getRetCode(),RareCharacterResultCodeEnum.IS_RARE_NAME.getCode());

        // pua原字 韦华
        result =  rareCharacterController.isRareName("2088","张", new ServiceContext());
        Assert.assertEquals(result.getRetCode(),RareCharacterResultCodeEnum.IS_RARE_NAME.getCode());

        // 可能拆字生僻字
        result =  rareCharacterController.isRareName("2088","张龙天", new ServiceContext());
        Assert.assertEquals(result.getRetCode(),RareCharacterResultCodeEnum.POSSIBLE_RARE_NAME.getCode());
    }


    /**
     * 测试生僻字姓名是否相同
     */
    private void testIsSameRareName(){
        // 拼音替代 vs 拼音替代
        RareNameCommonResult result =  rareCharacterController.isSameRareName("2088","张wei","张wei", new ServiceContext());
        Assert.assertEquals(result.getRetCode(),RareCharacterResultCodeEnum.POSSIBLE_SAME_RARE_NAME.getCode());

        // 拼音替代 vs unicode原字 韦华
        result =  rareCharacterController.isSameRareName("2088","张wei","张𮧵", new ServiceContext());
        Assert.assertEquals(result.getRetCode(),RareCharacterResultCodeEnum.POSSIBLE_SAME_RARE_NAME.getCode());

        // 拼音替代 vs unicode原字 龙天
        result =  rareCharacterController.isSameRareName("2088","张（YAN3）","张䶮", new ServiceContext());
        Assert.assertEquals(result.getRetCode(), RareCharacterResultCodeEnum.POSSIBLE_SAME_RARE_NAME.getCode());

        //unicode原字 vs pua原字 韦华
        result =  rareCharacterController.isSameRareName("2088","张","张𮧵", new ServiceContext());
        Assert.assertEquals(result.getRetCode(),RareCharacterResultCodeEnum.POSSIBLE_SAME_RARE_NAME.getCode());

        // 拆字替代 vs unicode原字
        result =  rareCharacterController.isSameRareName("2088","张龙天", "张䶮", new ServiceContext());
        Assert.assertEquals(result.getRetCode(),RareCharacterResultCodeEnum.POSSIBLE_SAME_RARE_NAME.getCode());
    }

    /**
     * 测试姓名unicode和pua互转
     */
    private void testTransferUniAndPuaRareName(){
        // unicode原字 韦华
        RareNameCommonResult  result =  rareCharacterController.transferUniAndPuaRareName("2088","张𮧵", new ServiceContext());
        Assert.assertEquals((String)result.getExtResult().get("retName"),"张");

        // pua原字 韦华
        result =  rareCharacterController.transferUniAndPuaRareName("2088","张", new ServiceContext());
        Assert.assertEquals((String)result.getExtResult().get("retName"),"张𮧵");
    }
}

