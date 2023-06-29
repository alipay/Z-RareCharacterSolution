import { isArray } from 'lodash';
import { useState } from 'react';

export default function useInputValue(): {
  value: string[];
  displayStr: string;
  addChar: (char: string) => void;
  addMutiChar: (char: string) => void;
  removeChar: () => void;
  removeAll: () => void;
} {
  const [value, setValue] = useState<string[]>([]);
  const displayStr = isArray(value) ? value.join('') : (value as string);

  /**
   * 添加一项
   * @param char
   */
  const addChar = (char: string) => {
    setValue([...value, char]);
  };

  /**
   * 添加多项
   * @param char
   */
  const addMutiChar = (char: string) => {
    const charArr = char.split('');
    setValue([...value, ...charArr]);
  };

  /**
   * 回退一项
   */
  const removeChar = () => {
    if (value.length === 0) return;
    value.pop();
    setValue([...value]);
  };

  /**
   * 清空
   */
  const removeAll = () => {
    setValue([]);
  };

  return { value, displayStr, addChar, addMutiChar, removeChar, removeAll };
}
