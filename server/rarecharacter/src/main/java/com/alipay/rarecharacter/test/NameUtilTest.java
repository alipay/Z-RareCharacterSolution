package com.alipay.rarecharacter.test;

import com.alipay.rarecharacter.util.NameUtil;
import junit.framework.Assert;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.apache.commons.lang3.text.translate.CharSequenceTranslator.hex;

/**
 * @author huyibing
 */
public class NameUtilTest {

    @Test
    public void utf82gbkTest() throws IOException {
        Assert.assertEquals(charsetConvert("?", "UTF-8"), "?");
        Assert.assertEquals(charsetConvert("?", "GB18030"), "?");
        Assert.assertEquals(charsetConvert("?", "GBK"), "?");
    }

    /**
     * gbk与utf-8互转
     * 利用BASE64Encoder/BASE64Decoder实现互转
     *
     * @param str
     * @return
     */
    public String charsetConvert(String str, String charset) throws IOException {
        str = new sun.misc.BASE64Encoder().encode(str.getBytes(charset));
        byte[] bytes = new sun.misc.BASE64Decoder().decodeBuffer(str);
        str = new String(bytes, charset);
        return str;
    }

    @Test
    public void codePointTest() {
        String s0 = "𮧵";  //正式码字 韦华
        Assert.assertEquals(s0.codePointAt(0), 190965);  //正式码十进制 190965
        Assert.assertEquals(hex(s0.codePointAt(0)), "2E9F5");  // 正式码十六进制 2E9F5

        String s1 = "\uD87A\uDDF5";  //正式码字 韦华
        Assert.assertEquals(s1.codePointAt(0), 190965);  // 正式码十进制 190965
        Assert.assertEquals(hex(s1.codePointAt(0)), "2E9F5");  // 正式码十六进制 2E9F5

        String s2 = "\uE43B";   //pua码字 韦华
        Assert.assertEquals(s2.codePointAt(0), 58427);  // 正式码十进制 58427
        Assert.assertEquals(hex(s2.codePointAt(0)), "E43B");  // 正式码十六进制 E43B

        String s3 = "张";    // 正式码字 张
        Assert.assertEquals(s3.codePointAt(0), 24352);  // 正式码十进制 24352
        Assert.assertEquals(hex(s3.codePointAt(0)), "5F20");  //  正式码十进制 5F20
    }


    @Test
    public void isAllChineseTest() {
        Assert.assertTrue(NameUtil.isAllChinese("\uD87A\uDDF5")); //韦华 unicode
        Assert.assertTrue(NameUtil.isAllChinese("张"));
        Assert.assertTrue(NameUtil.isAllChinese("张三"));
        Assert.assertTrue(NameUtil.isAllChinese("张是一只小小鸟"));
        Assert.assertTrue(NameUtil.isAllChinese("䶮")); //龙天 unicode
        Assert.assertTrue(NameUtil.isAllChinese("")); //龙天 pua
        Assert.assertTrue(NameUtil.isAllChinese("张\uD87A\uDDF5")); //韦华 unicode
        Assert.assertTrue(NameUtil.isAllChinese("")); //韦华pua
        Assert.assertTrue(NameUtil.isAllChinese("张𬱖")); //由页 unicode
        Assert.assertTrue(NameUtil.isAllChinese("张\uD870\uDF86")); //王莹 unicode
        Assert.assertTrue(NameUtil.isAllChinese("张\uD87A\uDDF5")); //韦华 unicode
        Assert.assertTrue(NameUtil.isAllChinese("张"));  //韦华pua
        Assert.assertTrue(NameUtil.isAllChinese("张䶮")); //龙天 unicode
        Assert.assertTrue(NameUtil.isAllChinese("张")); //龙天 pua
        Assert.assertTrue(NameUtil.isAllChinese("张·麦麦提"));

        Assert.assertFalse(NameUtil.isAllChinese("张？"));
        Assert.assertFalse(NameUtil.isAllChinese("张?"));
        Assert.assertFalse(NameUtil.isAllChinese("张？"));
        Assert.assertFalse(NameUtil.isAllChinese("张("));
        Assert.assertFalse(NameUtil.isAllChinese("张)"));
        Assert.assertFalse(NameUtil.isAllChinese("张（"));
        Assert.assertFalse(NameUtil.isAllChinese("张）"));
        Assert.assertFalse(NameUtil.isAllChinese("张{"));
        Assert.assertFalse(NameUtil.isAllChinese("张【"));
        Assert.assertFalse(NameUtil.isAllChinese("张*"));
        Assert.assertFalse(NameUtil.isAllChinese("张&"));
        Assert.assertFalse(NameUtil.isAllChinese("张^"));
        Assert.assertFalse(NameUtil.isAllChinese("张%"));
        Assert.assertFalse(NameUtil.isAllChinese("张$"));
        Assert.assertFalse(NameUtil.isAllChinese("张#"));
        Assert.assertFalse(NameUtil.isAllChinese("张@"));
        Assert.assertFalse(NameUtil.isAllChinese("张!"));
        Assert.assertFalse(NameUtil.isAllChinese("张~"));
        Assert.assertFalse(NameUtil.isAllChinese("张。"));
        Assert.assertFalse(NameUtil.isAllChinese("张，"));
        Assert.assertFalse(NameUtil.isAllChinese("张12"));
        Assert.assertFalse(NameUtil.isAllChinese("张wei"));
        Assert.assertFalse(NameUtil.isAllChinese("张wei3"));
        Assert.assertFalse(NameUtil.isAllChinese("张wei3。"));
        Assert.assertFalse(NameUtil.isAllChinese("123"));
        Assert.assertFalse(NameUtil.isAllChinese("abc"));
    }

    @Test
    public void isValidCertNameTest() {
        Assert.assertTrue(NameUtil.isValidCertName("张三"));
        Assert.assertTrue(NameUtil.isValidCertName("张san"));
        Assert.assertTrue(NameUtil.isValidCertName("zhang san"));
        Assert.assertTrue(NameUtil.isValidCertName("张？"));
        Assert.assertTrue(NameUtil.isValidCertName("张?"));
        Assert.assertTrue(NameUtil.isValidCertName("张??"));

        Assert.assertFalse(NameUtil.isValidCertName(""));
        Assert.assertFalse(NameUtil.isValidCertName("张@"));
        Assert.assertFalse(NameUtil.isValidCertName("张!"));
        Assert.assertFalse(NameUtil.isValidCertName("张%"));
        Assert.assertFalse(NameUtil.isValidCertName("张*"));
        Assert.assertFalse(NameUtil.isValidCertName("张`"));
        Assert.assertFalse(NameUtil.isValidCertName("张~"));
    }

    @Test
    public void isRareNameTest() {
        Assert.assertTrue(NameUtil.isRareName("张wei"));
        Assert.assertTrue(NameUtil.isRareName("张wei3"));
        Assert.assertTrue(NameUtil.isRareName("张wei3。"));
        Assert.assertTrue(NameUtil.isRareName("𮧵")); //韦华 unicode
        Assert.assertTrue(NameUtil.isRareName(""));  //韦华pua
        Assert.assertTrue(NameUtil.isRareName("䶮")); //龙天 unicode
        Assert.assertTrue(NameUtil.isRareName("")); //龙天 pua
        Assert.assertTrue(NameUtil.isRareName("张𮧵")); //韦华 unicode
        Assert.assertTrue(NameUtil.isRareName("张"));  //韦华pua
        Assert.assertTrue(NameUtil.isRareName("张䶮")); //龙天 unicode
        Assert.assertTrue(NameUtil.isRareName("张")); //龙天 pua
        Assert.assertTrue(NameUtil.isRareName("张（龙天）"));
        Assert.assertTrue(NameUtil.isRareName("张(龙天)"));
        Assert.assertTrue(NameUtil.isRareName("张(YAN)"));

        Assert.assertFalse(NameUtil.isRareName("张龙-天"));
        Assert.assertFalse(NameUtil.isRareName("张·三"));
        Assert.assertFalse(NameUtil.isRareName("张.三"));
        Assert.assertFalse(NameUtil.isRareName("张三。"));
        Assert.assertFalse(NameUtil.isRareName(""));
        Assert.assertFalse(NameUtil.isRareName("张韡"));
        Assert.assertFalse(NameUtil.isRareName("张龙+天"));
        Assert.assertFalse(NameUtil.isRareName("ABC NENDD"));
    }


    @Test
    public void isContainSpecialCharacterTest() {
        Assert.assertTrue(NameUtil.isContainSpecialCharacter("张？"));
        Assert.assertTrue(NameUtil.isContainSpecialCharacter("张?"));
        Assert.assertTrue(NameUtil.isContainSpecialCharacter("张（"));
        Assert.assertTrue(NameUtil.isContainSpecialCharacter("张("));

        Assert.assertFalse(NameUtil.isContainSpecialCharacter("张")); // 韦华 pua
        Assert.assertFalse(NameUtil.isContainSpecialCharacter("张。"));
        Assert.assertFalse(NameUtil.isContainSpecialCharacter("张·"));
        Assert.assertFalse(NameUtil.isContainSpecialCharacter("张."));
    }

    @Test
    public void isContainRareCharacterTest() {
        Assert.assertTrue(NameUtil.isContainOriginalRareCharacter("张䶮")); //龙天 unicode
        Assert.assertTrue(NameUtil.isContainOriginalRareCharacter("张")); //龙天 pua
        Assert.assertTrue(NameUtil.isContainOriginalRareCharacter("张𮧵")); //韦华 unicode

        Assert.assertFalse(NameUtil.isContainOriginalRareCharacter("张?"));
        Assert.assertFalse(NameUtil.isContainOriginalRareCharacter("张三"));
        Assert.assertFalse(NameUtil.isContainOriginalRareCharacter("张wei"));
    }


    @Test
    public void splitNameEachOneCharacterTest() {
        List<String> nameList = NameUtil.splitNameEachOneCharacter("张w");
        Assert.assertEquals(nameList.size(), 2);
        Assert.assertEquals(nameList.get(0), "张");
        Assert.assertEquals(nameList.get(1), "w");
    }

    @Test
    public void splitNameEachTwoCharactersTest() {
        List<String> nameList = NameUtil.splitNameEachTwoCharacters("张");
        Assert.assertEquals(nameList.size(), 1);
        Assert.assertEquals(nameList.get(0), "张");

        nameList = NameUtil.splitNameEachTwoCharacters("w");
        Assert.assertEquals(nameList.size(), 1);
        Assert.assertEquals(nameList.get(0), "w");

        nameList = NameUtil.splitNameEachTwoCharacters("张大");
        Assert.assertEquals(nameList.size(), 1);
        Assert.assertEquals(nameList.get(0), "张大");

        nameList = NameUtil.splitNameEachTwoCharacters("张wei");
        Assert.assertEquals(nameList.size(), 3);
        Assert.assertEquals(nameList.get(0), "张w");
        Assert.assertEquals(nameList.get(1), "we");
        Assert.assertEquals(nameList.get(2), "ei");

        nameList = NameUtil.splitNameEachTwoCharacters("张韦华三");
        Assert.assertEquals(nameList.size(), 3);
        Assert.assertEquals(nameList.get(0), "张韦");
        Assert.assertEquals(nameList.get(1), "韦华");
        Assert.assertEquals(nameList.get(2), "华三");
    }

    @Test
    public void removeSpecialCharactersTest() {
        Assert.assertEquals(NameUtil.removeSpecialCharacters("张（wei）"), "张wei");
        Assert.assertEquals(NameUtil.removeSpecialCharacters("张(wei)"), "张wei");
        Assert.assertEquals(NameUtil.removeSpecialCharacters("张 wei"), "张wei");
        Assert.assertEquals(NameUtil.removeSpecialCharacters("张.麦麦提"), "张麦麦提");
        Assert.assertEquals(NameUtil.removeSpecialCharacters("张·麦麦提"), "张麦麦提");
        Assert.assertEquals(NameUtil.removeSpecialCharacters("张。麦麦提"), "张麦麦提");
    }

    @Test
    public void removeLettersAndNumsTest() {
        Assert.assertEquals(NameUtil.removeLettersAndNums("张wei"), "张");
        Assert.assertEquals(NameUtil.removeLettersAndNums("张wei三"), "张三");
        Assert.assertEquals(NameUtil.removeLettersAndNums("张wei3三"), "张三");
    }

    @Test
    public void removeNumsTest() {
        Assert.assertEquals(NameUtil.removeNums("张wei3"), "张wei");
        Assert.assertEquals(NameUtil.removeNums("张(wei3)"), "张(wei)");
    }

    @Test
    public void removeLettersTest() {
        Assert.assertEquals(NameUtil.removeLetters("张wei3"), "张3");
        Assert.assertEquals(NameUtil.removeLetters("张(wei3)"), "张(3)");
    }

    @Test
    public void removeSpecialCharactersAndLettersAndNumsTest() {
        Assert.assertEquals(NameUtil.removeSpecialCharactersAndLettersAndNums("张wei3"), "张");
        Assert.assertEquals(NameUtil.removeSpecialCharactersAndLettersAndNums("张(wei3)"), "张");
        Assert.assertEquals(NameUtil.removeSpecialCharactersAndLettersAndNums("张.麦麦提"), "张麦麦提");
        Assert.assertEquals(NameUtil.removeSpecialCharactersAndLettersAndNums("张·麦麦提"), "张麦麦提");
        Assert.assertEquals(NameUtil.removeSpecialCharactersAndLettersAndNums("张。麦麦提"), "张麦麦提");
    }

    @Test
    public void removeNotBMPChineseCharactersTest() {
        Assert.assertEquals(NameUtil.removeNotBMPChineseCharacters(""), "");
        Assert.assertEquals(NameUtil.removeNotBMPChineseCharacters("张三"), "张三");
        Assert.assertEquals(NameUtil.removeNotBMPChineseCharacters("张wei3。"), "张");
        Assert.assertEquals(NameUtil.removeNotBMPChineseCharacters("张wei3"), "张");
        Assert.assertEquals(NameUtil.removeNotBMPChineseCharacters("张(wei3)"), "张");
        Assert.assertEquals(NameUtil.removeNotBMPChineseCharacters("张䶮"), "张䶮");
        Assert.assertEquals(NameUtil.removeNotBMPChineseCharacters("张"), "张");
        Assert.assertEquals(NameUtil.removeNotBMPChineseCharacters("张yan3"), "张");
    }

    @Test
    public void removeBMPChineseCharactersTest() {
        Assert.assertEquals(NameUtil.removeBMPChineseCharacters(" "), "");
        Assert.assertEquals(NameUtil.removeBMPChineseCharacters("张三"), "");
        Assert.assertEquals(NameUtil.removeBMPChineseCharacters("张wei"), "wei");
        Assert.assertEquals(NameUtil.removeBMPChineseCharacters("张wei3"), "wei3");
        Assert.assertEquals(NameUtil.removeBMPChineseCharacters("张(wei3)"), "(wei3)");

        Assert.assertEquals(NameUtil.removeBMPChineseCharacters("张?"), "?");
    }

    @Test
    public void isOneBPMChineseTest() {
        Assert.assertFalse(NameUtil.isOneBMPChinese(""));
        Assert.assertFalse(NameUtil.isOneBMPChinese(" "));
        Assert.assertFalse(NameUtil.isOneBMPChinese("w"));
        Assert.assertFalse(NameUtil.isOneBMPChinese("2"));
        Assert.assertFalse(NameUtil.isOneBMPChinese("（"));
        Assert.assertFalse(NameUtil.isOneBMPChinese("("));
        Assert.assertFalse(NameUtil.isOneBMPChinese("？"));
        Assert.assertFalse(NameUtil.isOneBMPChinese("."));
        Assert.assertFalse(NameUtil.isOneBMPChinese("。"));
        Assert.assertFalse(NameUtil.isOneBMPChinese("𮧵"));


        Assert.assertTrue(NameUtil.isOneBMPChinese("张"));
        Assert.assertTrue(NameUtil.isOneBMPChinese("韡"));
        Assert.assertTrue(NameUtil.isOneBMPChinese(""));
    }

    @Test
    public void removeSameChineseCharacterTest() {
        handleRemoveSameChineseCharacterAndCompare("张wei", "张wei", "wei", "wei");
        handleRemoveSameChineseCharacterAndCompare("张wei", "张wei3", "wei", "wei3");
        handleRemoveSameChineseCharacterAndCompare("张wei", "张韦华", "wei", "韦华");
        handleRemoveSameChineseCharacterAndCompare("张三wei", "张三韦华", "wei", "韦华");
        handleRemoveSameChineseCharacterAndCompare("张wei三", "张韦华三", "wei", "韦华");
    }

    private void handleRemoveSameChineseCharacterAndCompare(String sourceName, String targetName, String retSourceName, String retTargetName) {
        sourceName = NameUtil.removeSpecialCharacters(sourceName);
        targetName = NameUtil.removeSpecialCharacters(targetName);
        List<String> ret = NameUtil.removeSameChineseCharacter(sourceName, targetName);
        Assert.assertEquals(ret.get(0), retSourceName);
        Assert.assertEquals(ret.get(1), retTargetName);
    }


    @Test
    public void signNotGBKChinese2Test() {
        // unicode生僻字
        Assert.assertEquals(NameUtil.signNotGBKChinesePosition("张𮧵", "#"), "张#");
        Assert.assertEquals(NameUtil.signNotGBKChinesePosition("张䶮", "#"), "张#");
        Assert.assertEquals(NameUtil.signNotGBKChinesePosition("张𬱖", "#"), "张#");

        // pua生僻字
        Assert.assertEquals(NameUtil.signNotGBKChinesePosition("张", "#"), "张#");
        Assert.assertEquals(NameUtil.signNotGBKChinesePosition("张", "#"), "张#");
        Assert.assertEquals(NameUtil.signNotGBKChinesePosition("张", "#"), "张#");
    }

    @Test
    public void signPinyinPositionTest() {
        Assert.assertEquals(NameUtil.signPinyinPosition("张yan", "#"), "张#");
        Assert.assertEquals(NameUtil.signPinyinPosition("张yan3", "#"), "张#");
        Assert.assertEquals(NameUtil.signPinyinPosition("张(yan3)", "#"), "张#");
        Assert.assertEquals(NameUtil.signPinyinPosition("张（yan3）", "#"), "张#");
        Assert.assertEquals(NameUtil.signPinyinPosition("张（YAN3）", "#"), "张#");
    }


    @Test
    public void isContainUnicodeOriginalRareCharacterTest() {
        // 含unicode生僻字
        Assert.assertTrue(NameUtil.isContainUnicodeOriginalRareCharacter("张𮧵"));
        Assert.assertTrue(NameUtil.isContainUnicodeOriginalRareCharacter("张䶮"));
        Assert.assertTrue(NameUtil.isContainUnicodeOriginalRareCharacter("张𬱖"));
        Assert.assertTrue(NameUtil.isContainUnicodeOriginalRareCharacter("张𬎆"));
        Assert.assertTrue(NameUtil.isContainUnicodeOriginalRareCharacter("张𬎆ying"));
        Assert.assertTrue(NameUtil.isContainUnicodeOriginalRareCharacter("张𬎆ying3"));


        // 不含unicode生僻字
        Assert.assertFalse(NameUtil.isContainUnicodeOriginalRareCharacter("张"));
        Assert.assertFalse(NameUtil.isContainUnicodeOriginalRareCharacter("张"));
        Assert.assertFalse(NameUtil.isContainUnicodeOriginalRareCharacter("张"));
        Assert.assertFalse(NameUtil.isContainUnicodeOriginalRareCharacter("张三"));
        Assert.assertFalse(NameUtil.isContainUnicodeOriginalRareCharacter("张测试"));
        Assert.assertFalse(NameUtil.isContainUnicodeOriginalRareCharacter("胡义兵"));
        Assert.assertFalse(NameUtil.isContainUnicodeOriginalRareCharacter("张（龙天）"));
        Assert.assertFalse(NameUtil.isContainUnicodeOriginalRareCharacter("张(龙天)"));
        Assert.assertFalse(NameUtil.isContainUnicodeOriginalRareCharacter("张(龙+天)"));
    }


    @Test
    public void getSplitSignLinkedCharsTest() {
        Assert.assertEquals(NameUtil.getSplitSignLinkedChars("张三"), "张三");
        Assert.assertEquals(NameUtil.getSplitSignLinkedChars("张日韦"), "张日韦");
        Assert.assertEquals(NameUtil.getSplitSignLinkedChars("张（日韦）"), "日韦");
        Assert.assertEquals(NameUtil.getSplitSignLinkedChars("张(日韦)"), "日韦");
        Assert.assertEquals(NameUtil.getSplitSignLinkedChars("张日+韦"), "日韦");
        Assert.assertEquals(NameUtil.getSplitSignLinkedChars("张（日韦）三"), "日韦");
        Assert.assertEquals(NameUtil.getSplitSignLinkedChars("张(日韦)三"), "日韦");
        Assert.assertEquals(NameUtil.getSplitSignLinkedChars("张日+韦三"), "日韦");
        Assert.assertEquals(NameUtil.getSplitSignLinkedChars("张(日+韦)三"), "日韦");
        Assert.assertEquals(NameUtil.getSplitSignLinkedChars("张（日+韦）三"), "日韦");
    }

    @Test
    public void signSplitPositionTest() {
        Assert.assertEquals(NameUtil.signSplitPosition("张三", "#"), "张三");
        Assert.assertEquals(NameUtil.signSplitPosition("张日韦", "#"), "张日韦");
        Assert.assertEquals(NameUtil.signSplitPosition("张（日韦）", "#"), "张#");
        Assert.assertEquals(NameUtil.signSplitPosition("张(日韦)", "#"), "张#");
        Assert.assertEquals(NameUtil.signSplitPosition("张日+韦", "#"), "张#");
        Assert.assertEquals(NameUtil.signSplitPosition("张（日韦）三", "#"), "张#三");
        Assert.assertEquals(NameUtil.signSplitPosition("张(日韦)三", "#"), "张#三");
        Assert.assertEquals(NameUtil.signSplitPosition("张日+韦三", "#"), "张#三");
        Assert.assertEquals(NameUtil.signSplitPosition("张(日+韦)三", "#"), "张#三");
        Assert.assertEquals(NameUtil.signSplitPosition("张（日+韦）三", "#"), "张#三");
    }

    @Test
    public void isContainUpperPinyinTest() {
        Assert.assertTrue(NameUtil.isContainUpperPinyin("张YAN"));
        Assert.assertTrue(NameUtil.isContainUpperPinyin("张YAN3"));
        Assert.assertTrue(NameUtil.isContainUpperPinyin("张(YAN)"));
        Assert.assertTrue(NameUtil.isContainUpperPinyin("张（YAN）"));

        Assert.assertFalse(NameUtil.isContainUpperPinyin("张三"));
        Assert.assertFalse(NameUtil.isContainUpperPinyin("张yan"));
        Assert.assertFalse(NameUtil.isContainUpperPinyin("张yan3"));
        Assert.assertFalse(NameUtil.isContainUpperPinyin("张(yan)"));
        Assert.assertFalse(NameUtil.isContainUpperPinyin("张（yan）"));
    }

    @Test
    public void isContainLowerPinyinTest() {
        Assert.assertTrue(NameUtil.isContainLowerPinyin("张yan3"));
        Assert.assertTrue(NameUtil.isContainLowerPinyin("张(yan)"));
        Assert.assertTrue(NameUtil.isContainLowerPinyin("张（yan）"));

        Assert.assertFalse(NameUtil.isContainLowerPinyin("张YAN3"));
        Assert.assertFalse(NameUtil.isContainLowerPinyin("张(YAN)"));
        Assert.assertFalse(NameUtil.isContainLowerPinyin("张（YAN）"));
        Assert.assertFalse(NameUtil.isContainLowerPinyin("张三"));
    }

    @Test
    public void isContainPuaOriginalRareCharacterTest() {
        // 含pua生僻字
        Assert.assertTrue(NameUtil.isContainPuaOriginalRareCharacter("张"));
        Assert.assertTrue(NameUtil.isContainPuaOriginalRareCharacter("张"));
        Assert.assertTrue(NameUtil.isContainPuaOriginalRareCharacter("张"));

        // 不含pua生僻字
        Assert.assertFalse(NameUtil.isContainPuaOriginalRareCharacter("张?"));
        Assert.assertFalse(NameUtil.isContainPuaOriginalRareCharacter("张?"));
        Assert.assertFalse(NameUtil.isContainPuaOriginalRareCharacter("张?"));
        Assert.assertFalse(NameUtil.isContainPuaOriginalRareCharacter("张?"));
        Assert.assertFalse(NameUtil.isContainPuaOriginalRareCharacter("张三"));
        Assert.assertFalse(NameUtil.isContainPuaOriginalRareCharacter("张测试"));
        Assert.assertFalse(NameUtil.isContainPuaOriginalRareCharacter("胡义兵"));
    }


    @Test
    public void codePoint2Test() {
        String name = "徐𮧵";

        // 用#标记非GBK汉字位置
        String sign = "#";
        String gbkChineseWithSign = NameUtil.signNotGBKChinesePosition(name, sign);
        int signCount = StringUtils.countMatches(gbkChineseWithSign, sign);

        // 去除GBK
        String nameNoGBK = NameUtil.removeGBKChineseCharacters(name);
        String codePoint = hex(nameNoGBK.codePointAt(0));
        Assert.assertEquals(signCount, 2);

        // 一个生僻字
        if (signCount == 1) {
            Assert.assertEquals(codePoint, "E863");
        } else if (signCount == 2) {
            Assert.assertEquals(hex(nameNoGBK.codePointAt(1)), "2E9F5");
        }
    }

    @Test
    public void isContainOneChineseTest() {
        Assert.assertTrue(NameUtil.isContainOneChinese("张"));
        Assert.assertTrue(NameUtil.isContainOneChinese("张"));
        Assert.assertTrue(NameUtil.isContainOneChinese("张𮧵"));
        Assert.assertTrue(NameUtil.isContainOneChinese("张"));
        Assert.assertTrue(NameUtil.isContainOneChinese(""));
        Assert.assertTrue(NameUtil.isContainOneChinese(""));
        Assert.assertTrue(NameUtil.isContainOneChinese("𮧵"));
        Assert.assertTrue(NameUtil.isContainOneChinese("1"));
        Assert.assertTrue(NameUtil.isContainOneChinese("yan"));
        Assert.assertTrue(NameUtil.isContainOneChinese("𮧵wei3"));

        Assert.assertFalse(NameUtil.isContainOneChinese("w"));
        Assert.assertFalse(NameUtil.isContainOneChinese("13"));
        Assert.assertFalse(NameUtil.isContainOneChinese("wei3"));
    }
}
