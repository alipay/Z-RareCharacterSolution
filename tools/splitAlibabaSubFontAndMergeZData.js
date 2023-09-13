/**
 * 根据Z字库数据生成字体脚本
 */
const fs = require('fs');
const path = require('path');
const fontCarrier = require('ant-font-carrier');
const he = require('he');

/** 生成字体文件 */
console.log('开始生成字体...');
const regularFilePath = path.join(
  __dirname,
  '../demo/web/public/fonts/AlibabaPuHuiTi-3-55-Regular.ttf',
);
const regularL3FilePath = path.join(
  __dirname,
  '../demo/web/public/fonts/AlibabaPuHuiTi-3-55-RegularL3.ttf',
);
const rareWordsFilePath = path.join(
  __dirname,
  '../demo/web/public/fonts/RareWordsFonts.ttf',
);

const newFont = fontCarrier.create({ id: 'RareWordsFont' });
const regularFont = fontCarrier.transfer(regularFilePath);
const regularL3Font = fontCarrier.transfer(regularL3FilePath);
const rareWordsFont = fontCarrier.transfer(rareWordsFilePath);
const fontGetFailArr = [];
const ZDATAS_FILE_PATH = path.join(
  __dirname,
  '../frontend/utils/src/contants/zdata-local.ts',
);

/**
 * 根据编码查找对应的字体
 * 查找顺序为 RareWordsFonts.ttf
 *           -> AlibabaPuHuiTi-3-55-Regular.ttf
 *           -> AlibabaPuHuiTi-3-55-RegularL3.ttf
 * @param {string} text 汉字原字
 * @param {string} code 汉字编码
 */
function getFontDataByCode(text = '', code = '') {
  const ncrCode = he.encode(text, { decimal: true });
  try {
    const rareWordsGs = rareWordsFont.getGlyph(ncrCode);
    if (!rareWordsGs) {
      const regularGs = regularFont.getGlyph(ncrCode);
      if (!regularGs) {
        const regularL3Gs = regularL3Font.getGlyph(ncrCode);
        if (!regularL3Gs) {
          throw new Error('字体不存在');
        } else {
          newFont.setGlyph(ncrCode, regularL3Gs);
        }
      } else {
        newFont.setGlyph(ncrCode, regularGs);
      }
    } else {
      newFont.setGlyph(ncrCode, rareWordsGs);
    }
  } catch (err) {
    fontGetFailArr.push({ text, code });
    console.log(
      `根据编码查找对应的字体失败, text: ${text}, code: ${code}, error: ${err.toString()}`,
    );
  }
}

/**
 * 解析zdata数据
 * @param {string} filePath zdata数据文件地址
 * @returns
 */
function readAndParseJson(filePath) {
  // 读取文件内容
  const fileContent = fs.readFileSync(filePath, 'utf-8');
  // 提取 JSON 数据
  const jsonData = fileContent.match(/export\s*const\s*ZDATAS\s*=\s*(.*);/s)[1];

  if (!jsonData || jsonData.length < 3) {
    throw new Error('Failed to parse JSON data');
  }

  // 解析 JSON 数据
  const parsedData = eval('(' + jsonData + ')');
  return parsedData || {};
}

const { datas = [] } = readAndParseJson(ZDATAS_FILE_PATH);
datas.forEach((word) => {
  getFontDataByCode(word.unicodeChar, word.unicodeCodePoint);
});

console.log(
  '生存的字体包含如下汉字：' +
    JSON.stringify(datas.map((item) => item.unicodeChar).join(' ')),
);
console.log(
  '存在以下字体生成失败：' +
    JSON.stringify(
      fontGetFailArr.map((item) => `${item.text} ${item.code}`).join(', '),
    ),
);
console.log('生成的字体存放地址：' + rareWordsFilePath);
newFont.setFontface({
  fontFamily: 'rare-words-font',
  unitsPerEm: 1024,
  ascent: 812,
  descent: -212,
});
newFont.output({ path: rareWordsFilePath, types: ['ttf'] });
