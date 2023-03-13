/**
 * json转字符串
 * @param {string} data 需要转json的字符串
 * @return {object} json 字符串
 */
export function safeJSONparse(data: string): object {
  let result;
  try {
    result = JSON.parse(data);
  } catch {
    result = {};
  }
  return result || {};
}

/**
 * 判断数组是否为空
 */
export function isWordsDataEmpty(arr) {
  if (!arr) return true;
  if (!Array.isArray(arr)) return true;
  if (arr.length === 0) return true;
  // 数据合法性校验
  if (!arr?.[0]?.charId) return true;
  return false;
}

/**
 * 清除字符串里的数字
 */
export function clearNumberInStr(str: string) {
  return str.replace(/[0-9]/gi, '');
}

/**
 * 格式化字库数据
 * @param datas ZDatas 数据
 * @return {IWordsData} 字库
 */
export function formatZDatas(datas = []) {
  return datas.map(item => {
    return {
      ...item,
      pinYinChars: item.pinYinChars.map(i => i.char),
      splitChars: item.splitChars.map(i => i.char),
    };
  });
}
