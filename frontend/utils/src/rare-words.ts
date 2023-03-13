/**
 * 检查给定的字符串是否是包含生僻字的字符串

 * @since 3.52.1
 * @param {string} str 需要检查的字符串
 * @returns {boolean} 字符串中包含生僻字则返回 true
 * @example

 * import { isContainRareWords } from 'ant-rare-words-utils'
 * isContainRareWords('hello')
 * // => false
 *
 * isContainRareWords('䶮')
 * // => true
 */
export function isContainRareWords(str: string): boolean {
  if (!str) return false;
  const RARE_NAME_REG =
    /(?:[\u3400-\u4DB5\u9FA6-\u9FB3\u9FBC-\u9FEF\uE000-\uF8FF]|[\uD840-\uD868\uD86A-\uD86C\uD86F-\uD872\uD874-\uD879][\uDC00-\uDFFF]|\uD869[\uDC00-\uDED6\uDF00-\uDFFF]|\uD86D[\uDC00-\uDF34\uDF40-\uDFFF]|\uD86E[\uDC00-\uDC1D\uDC20-\uDFFF]|\uD873[\uDC00-\uDEA1\uDEB0-\uDFFF]|\uD87A[\uDC00-\uDFE0])/;
  return RARE_NAME_REG.test(str);
}

/**
 * 用于校验中文姓名是否符合规范，包含GB18030-2022全部汉字，少数民族 ·，以及PUA码段E000-F8FF
 * @param {string} name 中文姓名
 * @returns {boolean} 合法中文姓名则返回 true
 */
export const isChineseNameValid = (name: string = '') => {
  // const reg =
  //   /^[\uE000-\uF8FF\u3400-\u4DB5\u4E00-\u9FA5\u9FA6-\u9FB3\u9FBC-\u9FEF\u00B7\u{20000}-\u{2A6D6}\u{2A700}-\u{2B734}\u{2B740}-\u{2B81D}\u{2B820}-\u{2CEA1}\u{2CEB0}-\u{2EBE0}]+$/u;
  const regEs5 =
    /^(?:[\xB7\u3400-\u4DB5\u4E00-\u9FB3\u9FBC-\u9FEF\uE000-\uF8FF]|[\uD840-\uD868\uD86A-\uD86C\uD86F-\uD872\uD874-\uD879][\uDC00-\uDFFF]|\uD869[\uDC00-\uDED6\uDF00-\uDFFF]|\uD86D[\uDC00-\uDF34\uDF40-\uDFFF]|\uD86E[\uDC00-\uDC1D\uDC20-\uDFFF]|\uD873[\uDC00-\uDEA1\uDEB0-\uDFFF]|\uD87A[\uDC00-\uDFE0])+$/;
  return regEs5.test(name);
};
