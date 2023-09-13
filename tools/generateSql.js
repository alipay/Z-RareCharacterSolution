/**
 * 根据Z字库数据生成对应的SQL文件
 */
const { format } = require('date-fns');
const fs = require('fs');
const path = require('path');

const currentDate = new Date();
const formattedDate = format(currentDate, 'yyyyMMdd');
const formattedDateAndTime = format(currentDate, 'yyyy-MM-dd HH:mm:ss.SSS');



let SQLIDStartIndex = 0;
const newSQLDataArr = [];
const ZDATAS_FILE_PATH = path.join(__dirname, '../frontend/utils/src/contants/zdata-local.ts');
const SQL_FILE_PATH = path.join(__dirname, '../demo/.docker/database/init.sql');



// 将顺序转化成主键ID
function transIndexToSqlId(index) {
  return formattedDate + index.toString().padStart(5, '0');
}

// 生成插入UNICODE项的SQL
function generateUNICODEInsert(word) {
  SQLIDStartIndex += 1;
  return `INSERT INTO \`rare_characters\` VALUES ('${transIndexToSqlId(SQLIDStartIndex)}','${
    word.charId
  }','UNICODE','${word.unicodeChar}','${word.unicodeCodePoint}','${
    word.unicodeNcrCode
  }','','','','${word.weight}','\"{\"\"ncr_code_hex\"\":\"\"${
    word.unicodeCodePoint
  }\"\"}\"',${formattedDateAndTime},${formattedDateAndTime});`;
}

// 生成插入PUA项的SQL
function generatePUAInsert(word) {
  SQLIDStartIndex += 1;
  // return `INSERT INTO \`rare_characters\` VALUES ('${transIndexToSqlId(SQLIDStartIndex)}','${
  //   word.charId
  // }','PUA','${word.puaChar}','${
  //   word.puaCodePoint
  // }','','','','','${word.weight}','',${formattedDateAndTime},${formattedDateAndTime});`;
  return `INSERT INTO \`rare_characters\` VALUES ('${transIndexToSqlId(SQLIDStartIndex)}','${
    word.charId
  }','PUA','','','','','','','${word.weight}','',${formattedDateAndTime},${formattedDateAndTime});`;
}

// 生成插入PINYIN项的SQL
function generatePINYINInsert(word) {
  const result = [];
  word.pinYinChars.forEach(item => {
    if (item.char) {
      SQLIDStartIndex += 1;
      const letter = item.char.match(/[a-zA-Z]+/) ? item.char.match(/[a-zA-Z]+/)[0] : '';
      const number = item.char.match(/\d+/) ? item.char.match(/\d+/)[0] : '';
      result.push(
        `INSERT INTO \`rare_characters\` VALUES ('${transIndexToSqlId(SQLIDStartIndex)}','${
          word.charId
        }','PINYIN','${letter}','','','${number}','','','${word.weight}','',${formattedDateAndTime},${formattedDateAndTime});`
      );
    }
  });
  return result;
}

// 生成插入SPLIT项的SQL
function generateSPLITInsert(word) {
  const result = [];
  word.splitChars.forEach(item => {
    SQLIDStartIndex += 1;
    result.push(
      `INSERT INTO \`rare_characters\` VALUES ('${transIndexToSqlId(SQLIDStartIndex)}','${
        word.charId
      }','SPLIT','${
        item.char
      }','','','','','','${word.weight}','',${formattedDateAndTime},${formattedDateAndTime});`
    );
  });
  return result;
}

// 生成插入TRADITIONAL项的SQL
function generateTRADITIONALInsert(word) {
  const result = [];
  word.tranditional.forEach(item => {
    SQLIDStartIndex += 1;
    const unicode = getChineseUnicode(item.char);
    result.push(
      `INSERT INTO \`rare_characters\` VALUES ('${transIndexToSqlId(SQLIDStartIndex)}','${
        word.charId
      }','TRADITIONAL','${item.char}','${item.code || unicode}','${getChineseNcrCode(
        item.char
      )}','','','','${word.weight}','\"{\"\"ncr_code_hex\"\":\"\"${
        item.code || unicode
      }\"\"}\"',${formattedDateAndTime},${formattedDateAndTime});`
    );
  });
  return result;
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

console.log('开始生成SQL...');

const sqlContent = fs.readFileSync(SQL_FILE_PATH, 'utf-8');
const { datas = [] } = readAndParseJson(ZDATAS_FILE_PATH);
const newAddedWords = datas.filter(item => sqlContent.indexOf(item.charId) < 0);

newAddedWords.forEach((word, index) => {
  if (word.unicodeChar && word.unicodeCodePoint) {
    newSQLDataArr.push(generateUNICODEInsert(word));
  }
  if (word.puaChar && word.puaCodePoint) {
    newSQLDataArr.push(generatePUAInsert(word));
  }
  if (word.pinYinChars.length > 0) {
    generatePINYINInsert(word).forEach(item => {
      newSQLDataArr.push(item);
    });
  }
  if (word.splitChars.length > 0) {
    generateSPLITInsert(word).forEach(item => {
      newSQLDataArr.push(item);
    });
  }
  if (word.tranditional) {
    generateTRADITIONALInsert(word).forEach(item => {
      newSQLDataArr.push(item);
    });
  }
  newSQLDataArr.push('');
});

console.log(`新增字符的SQL：\n` + newSQLDataArr.join('\n'))

fs.appendFileSync(SQL_FILE_PATH, '\n' + newSQLDataArr.join('\n'));

console.log('SQL更新后存放地址：' + SQL_FILE_PATH);
