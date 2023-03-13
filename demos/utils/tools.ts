/**
 * 将原始数组按照数量顺序分组
 * @param array 原始数组
 * @param count 分组数
 * @returns
 */
export function groupArrayByCount<T>(array: T[], count: number): T[][] {
  if (count < 2) return array.map((item) => [item]);
  const result: T[][] = [];
  array.reduce((prev, t) => {
    const theLastItem = result[result.length - 1];
    if (!theLastItem || theLastItem.length >= count) {
      prev.push([t]);
    } else {
      theLastItem.push(t);
    }
    return prev;
  }, result);
  return result;
}

type KeyType = string | number | symbol;
type IEmptyObject = {};
export function reverseMap<K extends KeyType, V extends KeyType>(
  map: Record<K, V>,
): Record<V, K> | IEmptyObject {
  const result = {};
  Object.keys(map).forEach((key: KeyType) => {
    const value = (map as any)[key];
    // @ts-ignore
    result[value] = key;
  });

  return result;
}


/**
 * json转字符串
 * @param {string} data 需要转json的字符串
 * @return {object} json 字符串
 */
export function safeJSONparse(data: string): object {
  let result;
  try {
    result = JSON.parse(data);
  } catch {
    result = {};
  }
  return result || {};
}
