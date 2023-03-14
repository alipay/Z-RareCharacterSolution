package com.example.rarecharacterdemo.controller;

import com.alipay.rarecharacter.dal.dao.spi.RareCharacterDAO;
import com.alipay.rarecharacter.dal.dataobject.RareCharacterDO;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 初始化controller
 *
 * @author huyibing
 * @version $Id: InitDataController.java, v 0.1 2022年05月06日 下午14:23 huyibing Exp $
 */
@RestController
//@CrossOrigin(originPatterns = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,RequestMethod.PATCH, RequestMethod.DELETE, RequestMethod.OPTIONS, RequestMethod.TRACE, RequestMethod.HEAD, RequestMethod.DELETE})
@RequestMapping(value = "/init")
public class InitDataController {

    /**
     * logger
     */
    private final static Logger logger = LogManager
            .getLogger(InitDataController.class);

    /**
     * 字库初始化文件位置
     */
    private static final String FILE_PATH = "../../src/main/resources/static/z_data.csv";

    @Autowired
    private RareCharacterDAO rareCharacterDAO;

    /**
     * hello 测试
     *
     * @return
     */
    @RequestMapping(value = "/helloworld")
    public String helloWorld() {
        return "Hello World";
    }

    /**
     * 初始化Z字库数据
     *
     * @return
     */
    @RequestMapping(value = "/initData")
    public void initData() {
        try {
            String curDir = System.getProperty("user.dir");
            System.out.println("my direct is :" + curDir);
            logger.info("my direct is " + curDir);

            File csv = new File(FILE_PATH);
            csv.setReadable(true);
            csv.setWritable(true);
            InputStreamReader isr = new InputStreamReader(new FileInputStream(csv), "UTF-8");
            BufferedReader br = new BufferedReader(isr);

            String line = "";
            int lineNum = 0;
            while ((line = br.readLine()) != null) {
                if (lineNum != 0) {
                    RareCharacterDO rareCharacterDO = parseCsvLine(line, lineNum);
                    insert(rareCharacterDO);
                    logger.info("initData insert one line, lineNum = " + lineNum);
                }
                lineNum++;
            }
            logger.info("initData finish, lineNum = " + lineNum);
        } catch (IOException e) {
            logger.info("initData exception, e = ", e);
        }
    }

    /**
     * 解析csv文件，转换为数据模型
     *
     * @param line
     * @param lineNum
     * @return
     */
    public RareCharacterDO parseCsvLine(String line, int lineNum) {
        if (StringUtils.isBlank(line)) {
            logger.info("parseCsvLine line is blank, lineNum = " + lineNum);
            return null;
        }

        String[] columns = line.split("[,]", -1);
        if (columns.length < 13) {
            logger.info("parseCsvLine line lack column, lineNum = " + lineNum);
            return null;
        }

        RareCharacterDO rareCharacterDO = new RareCharacterDO();
        rareCharacterDO.setId(String.valueOf(lineNum));
        rareCharacterDO.setCharId(columns[1]);
        rareCharacterDO.setEncodeType(columns[2]);
        rareCharacterDO.setCode(columns[3]);
        rareCharacterDO.setNcrCode(columns[4]);
        rareCharacterDO.setTone(columns[5]);
        rareCharacterDO.setPic(columns[6]);
        rareCharacterDO.setExtInfo(columns[7]);
        rareCharacterDO.setGmtCreate(new Date());
        rareCharacterDO.setGmtModified(new Date());
        rareCharacterDO.setCodePoint(columns[10]);
        rareCharacterDO.setFont(columns[11]);
        rareCharacterDO.setWeight(columns[12]);

        if (StringUtils.isBlank(columns[1])) {
            rareCharacterDO.setCharId(genCharId());
        }

        return rareCharacterDO;
    }

    private String genCharId() {
        String charId = "";
        while (true) {
            charId = UUID.randomUUID().toString();
            if (StringUtils.isNotBlank(charId)) {
                charId = charId.substring(0, 10);
            }

            List<String> charIds = new ArrayList<>();
            charIds.add(charId);
            List<RareCharacterDO> rareCharacters = rareCharacterDAO.findByCharIds(charIds);
            if (CollectionUtils.isEmpty(rareCharacters)) {
                break;
            }
        }
        return charId;
    }

    /**
     * 写入数据库
     *
     * @param rareCharacterDO
     */
    public void insert(RareCharacterDO rareCharacterDO) {
        if (rareCharacterDO == null) {
            logger.info("insert, rareCharacterDO is null");
            return;
        }

        try {
            rareCharacterDAO.insert(rareCharacterDO);
        } catch (Exception e) {
            logger.info("insert rareCharacterDO exception, e = ", e);
        }
    }
}

