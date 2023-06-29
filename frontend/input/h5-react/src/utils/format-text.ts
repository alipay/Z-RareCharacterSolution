const ARRAY_INDEX_RE = /\{(\d+)\}/g;
const Object_INDEX_RE = /\{(.+?)\}/g;

/**
 * 使用数组格式化文本
 * @param text 需要格式化的文本
 * @param values 变量数组
 * @returns {string}
 */
function formatWithArray(text: string, values: string[]) {
  return text.replace(ARRAY_INDEX_RE, function (orignal, matched) {
    const index = parseInt(matched, 10);
    if (index < values.length) {
      return values[index];
    }
    // not match index, return orignal text
    return orignal;
  });
}

/**
 * 使用对象格式化文本
 * @param text 需要格式化的文本
 * @param values 变量对象
 * @returns {string}
 */
function formatWithObject(text: string, values: Record<string, any>) {
  return text.replace(Object_INDEX_RE, function (orignal, matched) {
    const value = values[matched];
    if (value) {
      return value;
    }
    // not match index, return orignal text
    return orignal;
  });
}

/**
 * 格式化字符串
 * @param text 需要格式化的文本
 * @param value 变量对象
 * @returns {string}
 */
export function formatText(text: string, value: any) {
  if (!text) {
    return '';
  }

  if (!value) {
    return text;
  }

  if (Array.isArray(value)) {
    // __(key, array)
    // __('{0} {1} {1} {0}', ['foo', 'bar'])
    // =>
    // foo bar bar foo
    return formatWithArray(text, value);
  }

  if (Object.prototype.toString.call(value) === '[object Object]') {
    // __(key, object)
    // __('{a} {b} {b} {a}', {a: 'foo', b: 'bar'})
    // =>
    // foo bar bar foo
    return formatWithObject(text, value);
  }
}
