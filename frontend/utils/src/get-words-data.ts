/**
 * 获取字库工具
 * 1. 调用后端接口检查字库是否需要更新
 * 2. 检查本地是否存在字库数据缓存，存在缓存直接返回缓存信息
 * 3. 重新从远程下载字库到本地，并做好缓存
 */
import { formatZDatas } from './utils';
import { IGetWordsDataResult } from '../types';
import { FontLoader } from './font-loader';
import { DEFAULT_FONT_URL, ERROR_CODES, ZDATAS } from './contants';

export function getWordsData(): Promise<IGetWordsDataResult> {
  return new Promise((resolve, reject) => {
    const { fontUrl, datas } = ZDATAS;
    // 加载字体
    loadFont(fontUrl)
      .then(font => {
        resolve({
          fontFace: font,
          fontUrl,
          data: formatZDatas(datas),
        });
      })
      .catch(error => {
        reject(generateError('加载字体失败', error));
      });
  });
}

/**
 * 加载字体
 * @param {string} fontUrl 字体下载地址
 */
function loadFont(fontUrl: string): Promise<FontFace> {
  return new Promise((resolve, reject) => {
    new FontLoader({
      fontName: 'rare-words-font',
      fontSrc: fontUrl || DEFAULT_FONT_URL,
      onSuccess: (font: FontFace) => {
        resolve(font);
      },
      onError: err => {
        reject(err);
      },
    });
  });
}

/**
 * 错误对象统一生成
 */
function generateError(message: string, error?: Error, errorCode?: string) {
  return {
    message: message || '出错了',
    errorCode: errorCode || ERROR_CODES.SYSTEM_ERROR,
    detail: error || new Error(message),
  };
}
