/**
 * 缓存方法封装
 */
import { WORDS_DATA_STORAGE_KEY } from '../contants';
import { IWordsData, IGetWordsDataStorage } from '../../types';
import { safeJSONparse } from './index';

/**
 * 设置缓存
 * @param {string} version 字库版本
 * @param {string} fontUrl 字体文件地址
 * @param {IWordsData} data 需要缓存的字库数据
 * @return {void}
 */
export function setWordsStorage(version: string, fontUrl: string, data: IWordsData): void {
  if (!window.localStorage) throw new Error('storage API 不支持');
  window.localStorage.setItem(WORDS_DATA_STORAGE_KEY, JSON.stringify({ version, fontUrl, data }));
}

/**
 * 获取字库缓存数据
 * @return {IGetWordsDataStorage} 返回字库版本和字库数据
 */
export function getWordsStorage(): IGetWordsDataStorage {
  if (!window.localStorage) throw new Error('storage API 不支持');
  const result = window.localStorage.getItem(WORDS_DATA_STORAGE_KEY);
  return safeJSONparse(result) as IGetWordsDataStorage;
}

/**
 * 清空字库缓存
 * @return {void}
 */
export function removeWordsStorage() {
  if (!window.localStorage) throw new Error('storage API 不支持');
  window.localStorage.removeItem(WORDS_DATA_STORAGE_KEY);
}
