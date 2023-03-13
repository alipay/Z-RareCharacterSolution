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

