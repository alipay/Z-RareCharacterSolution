import { IWordsItem } from '../typings';

/**
 * 判断字是否为空
 */
export function isWordEmpty(word: IWordsItem) {
  if (!word) return true;
  if (!word.unicodeChar) return true;
  if (word.unicodeChar === 'null') return true;
  return false;
}

export * from './format-text';
export * from './merge-props';
