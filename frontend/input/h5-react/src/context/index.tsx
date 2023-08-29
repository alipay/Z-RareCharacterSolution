import React from 'react';

interface IContext {
  inputValue: string[]; // 用户当前输入值-数组
  displayStr: string; // 用户当前输入值-字符串
  addChar: (char: string) => void; // 添加一项进输入值
  removeChar: () => void; // 从输入值里移除一项
  removeAll: () => void; // 清空输入值
  showKeyboard: boolean; // 是否展示键盘
  setShowKeyboard: React.Dispatch<React.SetStateAction<boolean>>; // 设置是否展示键盘
}

const appContext = React.createContext<IContext>({
  inputValue: [],
  displayStr: '',
  addChar: () => {},
  removeChar: () => {},
  removeAll: () => {},
  showKeyboard: false,
  setShowKeyboard: () => {},
});

export default appContext;
