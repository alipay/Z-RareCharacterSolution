/**
 * 字体合并到Z字库字体脚本
 */
const fs = require('fs');
const path = require('path');
const fontCarrier = require('ant-font-carrier');
const he = require('he');

const rareWordsFilePath = path.join(__dirname, '../demo/web/public/fonts/RareWordsFonts.ttf');
const tempFontFilePath = path.join(__dirname, './tempFont.ttf'); // 自己新增的字符字体文件地址


const rareWordsFont = fontCarrier.transfer(rareWordsFilePath);
const tempFont = fontCarrier.transfer(tempFontFilePath);
const allGlyphs = tempFont.allGlyph();

console.log('开始合并字体...');

Object.keys(allGlyphs).forEach(key => {
  rareWordsFont.setGlyph(key, allGlyphs[key]);
})

console.log('合并后的字体存放地址：' + rareWordsFilePath);
rareWordsFont.setFontface({
  fontFamily: 'rare-words-font',
  unitsPerEm: 1024,
  ascent: 812,
  descent: -212,
});
rareWordsFont.output({ path: rareWordsFilePath, types: ['ttf', 'woff'] });
