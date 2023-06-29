import React from 'react';
import { InputTypes } from '../typings';

interface IContext {
  inputValue: string[]; // 用户当前输入值-数组
  displayStr: string; // 用户当前输入值-字符串
  addChar: (char: string) => void; // 添加一项进输入值
  addMutiChar: (char: string) => void; // 添加多项进输入值
  removeChar: () => void; // 从输入值里移除一项
  removeAll: () => void; // 清空输入值
  showKeyboard: boolean; // 是否展示键盘
  keyboardType: InputTypes; // 键盘类型
  setShowKeyboard: React.Dispatch<React.SetStateAction<boolean>>; // 设置是否展示键盘
}

const appContext = React.createContext<IContext>({
  inputValue: [],
  displayStr: '',
  addChar: () => {},
  addMutiChar: () => {},
  removeChar: () => {},
  removeAll: () => {},
  showKeyboard: false,
  keyboardType: InputTypes.pinyin,
  setShowKeyboard: () => {},
});

export default appContext;
