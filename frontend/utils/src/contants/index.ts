/**
 * 默认字体下载地址
 */
export const DEFAULT_FONT_URL = 'https://mdn.alipayobjects.com/huamei_2fq7mt/afts/file/A*kPXwSqFoRTQAAAAAAAAAAAAADh58AQ/contains-pua-v1.0.2.ttf';

/**
 * 字库缓存key值
 */
export const WORDS_DATA_STORAGE_KEY = 'rare-words-data';

/**
 * 白名单内页面使用jsonp请求
 */
export const USE_JSONP_WHITELIST = [
  /180020010000522005/g,
  /local\.alipay\.net/g,
];

export * from './ua';
export * from './interface';
export * from './error-codes';
export * from './zdata-local';
