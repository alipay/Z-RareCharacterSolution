/**
 * 候选字推荐方法测试用例
 */
import { matchWordsRecommend } from '../src/utils/filter-and-sort';
import { describe, expect, test } from '@jest/globals';

const wordsData = [
  { charId: 'fa95d0', unicodeChar: '𡹇', unicodeFont: 'null', unicodeCodePoint: '21E47', puaChar: '', puaFont: 'null', puaCodePoint: 'EA7D', pinYinChars: ['LIN2'], splitChars: ['山林'], weight: 8, extInfo: 'null' },
  { charId: 'f87ad0', unicodeChar: '𤊟', unicodeFont: 'null', unicodeCodePoint: '2429F', puaChar: '', puaFont: 'null', puaCodePoint: 'EC0B', pinYinChars: ['LI'], splitChars: ['火定'], weight: 8, extInfo: 'null' },
  { charId: 'fd1326', unicodeChar: '𨱑', unicodeFont: 'null', unicodeCodePoint: '28C51', puaChar: '', puaFont: 'null', puaCodePoint: 'E24F', pinYinChars: ['LEI2'], splitChars: ['钅黄'], weight: 10, extInfo: 'null' },
  { charId: '50d365', unicodeChar: '㗊', unicodeFont: 'null', unicodeCodePoint: '35CA', puaChar: '', puaFont: 'null', puaCodePoint: 'E193', pinYinChars: ['LEI2', 'JI2'], splitChars: ['氵无'], weight: 12, extInfo: 'null' }
];

describe('font-loader.test.ts', () => {
  test('根据拼音推荐候选字成功', () => {
    const matchResult = matchWordsRecommend(wordsData, 'LIN', 'pinyin');
    expect(matchResult.length).toBe(1);
    expect(matchResult[0].charId).toBe('fa95d0');
  });
  test('根据拼音推荐候选字为空', () => {
    const matchResult = matchWordsRecommend(wordsData, 'LINN', 'pinyin');
    expect(matchResult.length).toBe(0);
    expect(matchResult[0]).toBe(undefined);
  });

  test('根据拆字推荐候选字成功', () => {
    const matchResult = matchWordsRecommend(wordsData, '定', 'split');
    expect(matchResult.length).toBe(1);
    expect(matchResult[0].charId).toBe('f87ad0');
  });
  test('根据拆字推荐候选字为空', () => {
    const matchResult = matchWordsRecommend(wordsData, '你', 'split');
    expect(matchResult.length).toBe(0);
    expect(matchResult[0]).toBe(undefined);
  });

  test('根据拆字或者拼音推荐候选字成功', () => {
    const matchResult = matchWordsRecommend(wordsData, '定', 'all');
    expect(matchResult.length).toBe(1);
    expect(matchResult[0].charId).toBe('f87ad0');
  });
  test('根据拆字或者拼音推荐候选字为空', () => {
    const matchResult = matchWordsRecommend(wordsData, '你', 'all');
    expect(matchResult.length).toBe(0);
    expect(matchResult[0]).toBe(undefined);
  });

  test('候选字根据匹配程度排序', () => {
    const matchResult = matchWordsRecommend(wordsData, 'LI', 'all');
    expect(matchResult.length).toBe(2);
    expect(matchResult[0].charId).toBe('f87ad0');
    expect(matchResult[1].charId).toBe('fa95d0');
  });

  test('候选字根据权重排序', () => {
    const matchResult = matchWordsRecommend(wordsData, 'LEI2', 'all');
    expect(matchResult.length).toBe(2);
    expect(matchResult[0].charId).toBe('50d365');
    expect(matchResult[1].charId).toBe('fd1326');
  });
});
