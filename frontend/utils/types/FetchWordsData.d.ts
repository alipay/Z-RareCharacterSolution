import { IWordsData } from "./WordsData";

export interface IGetWordsDataParams {
  forceUpdate?: boolean; // 是否强制更新字库
}

export interface IFetchWordsDataResult {
  version: string; // 字库版本
  needUpdate: boolean; // 是否需要更新
  fontFamily: string; // 字体下载地址
  dataUrl: string; // 字库CDN地址
}

export interface IGetWordsDataResult {
  fontFace: FontFace,
  fontUrl: string,
  data: IWordsData,
}

export interface IGetWordsDataError {
  detail: Error; // 错误详情
  errorCode: string; // 错误码
  message: string; // 错误文案
}

export interface IGetWordsDataStorage {
  version: string; // 字库版本
  fontUrl: string; // 字体文件地址
  data: IWordsData; // 字库数据
}
