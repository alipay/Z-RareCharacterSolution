export enum InputTypes {
  stroke = 'stroke', // 笔画输入
  pinyin = 'pinyin', // 拼音输入
  handwriting = 'handwriting', // 手写输入
}

export interface IPinYinMapItem {
  label: string; // 展示的文案
  value: string; // 实际输入的值
  extraClassName?: string; // 额外域名，用来定制样式
}

export interface IWordsItem {
  charId?: string; // 生僻字唯一ID
  unicodeChar: string; // unicode 字符
  unicodeCodePoint: string; // unicode 码点
  unicodeFont: string | null; // 单个字字体文件地址
  pinYinChars: string[]; // 拼音
  splitChars: string[]; // 拆字
  weight?: number | null; // 权重
  type?: string; // 类型
  extInfo?: string | null; // 其他信息
  sort?: number; // 排序得分
}

export type IWordsData = IWordsItem[];

export interface ICommonError {
  detail: Error; // 错误详情
  errorCode: string; // 错误码
  message: string; // 错误文案
  onRetry?: () => Promise<void>; // 重试的错误方法
}
