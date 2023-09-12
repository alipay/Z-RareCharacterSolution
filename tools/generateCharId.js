/**
 * Z字库唯一ID生成脚本
 */
const fs = require('fs');
const path = require('path');
const crypto = require('crypto');

const ZDATAS_FILE_PATH = path.join(__dirname, '../frontend/utils/src/contants/zdata-local.ts')

// 生成新的charId
function generateId() {
  const randomBytes = crypto.randomBytes(4); // 生成4个字节的随机字节数组
  const uniqueId = randomBytes.toString('hex').slice(0, 8); // 将字节数组转换成16进制字符串，并截取前8位
  return uniqueId;
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

/**
 * 检查和已有的charId是否重复
 * @returns charId 
 */
function generateUniqueId() {
  const charId = generateId();
  const { datas = [] } = readAndParseJson(ZDATAS_FILE_PATH);
  const existedItem = datas.find(item => item.charId === charId);
  return existedItem ? generateUniqueId() : charId;
}

console.log(`新的charId: ${generateUniqueId()}`);
