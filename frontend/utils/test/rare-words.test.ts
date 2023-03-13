/**
 * 生僻字的方法测试用例
 */
import { isContainRareWords, isChineseNameValid } from '../src/rare-words';
import { describe, expect, test } from '@jest/globals';

describe('rare-words.test.ts isContainRareWords', () => {
  test('传空返回false', () => {
    expect(isContainRareWords('')).toBe(false);
  });
  test('传入正常汉字返回false', () => {
    expect(isContainRareWords('你')).toBe(false);
  });
  test('传入包含生僻字的汉字返回true', () => {
    expect(isContainRareWords('䶮')).toBe(true);
  });
  test('传入简单繁体字', () => {
    expect(isContainRareWords('寶')).toBe(false);
  });
  test('传入GBK编码范围之外的繁体字', () => {
    expect(isContainRareWords('𪚔')).toBe(true);
  });
  test('传入英文字符或者标点', () => {
    expect(isContainRareWords('asd.><')).toBe(false);
  });
  test('传入中文字符或者标点', () => {
    expect(isContainRareWords('吃饭了？')).toBe(false);
  });
  test('传入特殊符号', () => {
    expect(isContainRareWords('①㊎◆◇⊙■□△▽卐꧔')).toBe(false);
  });
  test('传入\u3401GBK编码范围之外的字返回true', () => {
    expect(isContainRareWords('㐁')).toBe(true);
  });
  test('传入\u9FBCGBK编码范围之外的字返回true', () => {
    expect(isContainRareWords('龼')).toBe(true);
  });
  test('传入\u9FA6GBK编码范围之外的字返回true', () => {
    expect(isContainRareWords('龦')).toBe(true);
  });
  test('传入\uE863PUA自造字', () => {
    expect(isContainRareWords('')).toBe(true);
  });
  test('传入多个字', () => {
    expect(isContainRareWords('刘彦')).toBe(true);
  });
  test('生僻字在首位', () => {
    expect(isContainRareWords('彦')).toBe(true);
  });
  test('生僻字在结尾', () => {
    expect(isContainRareWords('彦')).toBe(true);
  });
});

describe('rare-words.test.ts isChineseNameValid', () => {
  test('传空返回false', () => {
    expect(isChineseNameValid('')).toBe(false);
  });
  test('传入正常姓名返回false', () => {
    expect(isChineseNameValid('刘彦')).toBe(true);
  });
  test('传入包含生僻字的姓名返回true', () => {
    expect(isChineseNameValid('刘䶮')).toBe(true);
  });
  test('传入简单繁体姓名返回true', () => {
    expect(isChineseNameValid('刘寶')).toBe(true);
  });
  test('传入GBK编码范围之外的繁体姓名', () => {
    expect(isChineseNameValid('刘𪚔')).toBe(true);
  });
  test('传入少数名族姓名', () => {
    expect(isChineseNameValid('穆拉提·马木提')).toBe(true);
  });
  test('传入英文姓名', () => {
    expect(isChineseNameValid('LIU YAN')).toBe(false);
  });
  test('传入携带特殊符号姓名', () => {
    expect(isChineseNameValid('刘①㊎◆◇⊙■□△▽卐꧔')).toBe(false);
  });
  test('传入包含\u3401GBK编码范围之外的字的姓名返回true', () => {
    expect(isChineseNameValid('㐁')).toBe(true);
  });
  test('传入包含\u9FBCGBK编码范围之外的字的姓名返回true', () => {
    expect(isChineseNameValid('龼')).toBe(true);
  });
  test('传入包含\u9FA6GBK编码范围之外的字的姓名返回true', () => {
    expect(isChineseNameValid('龦')).toBe(true);
  });
  test('传入包含\uE863PUA自造字的姓名返回true', () => {
    expect(isChineseNameValid('')).toBe(true);
  });
  test('生僻字在首位', () => {
    expect(isChineseNameValid('彦')).toBe(true);
  });
  test('生僻字在结尾', () => {
    expect(isChineseNameValid('彦')).toBe(true);
  });
});
