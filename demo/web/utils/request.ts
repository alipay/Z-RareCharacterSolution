import axios, { AxiosResponse } from 'axios';
import { safeJSONparse } from './tools';

const instance = axios.create({
  headers: { 'Content-Type': 'json' },
  timeout: 5000,
});

/**
 * 请求库
 * @param {IBaseParams} params 请求参数
 * @returns {Promise<IBaseResult>} 返回值
 */
export function request(params: IHttpParams): Promise<IHttpResult> {
  return instance
    .request({
      method: params.method,
      url: params.url,
      ...(params.method === 'GET' ? { params: params?.data || {} } : { data: params?.data || {} }),
      ...(params.headers ? { headers: params.headers } : {}),
      ...(params.timeout ? { timeout: params.timeout } : {}),
      ...(params.dataType ? { dataType: params.dataType } : {}),
      ...(params.jsonpCallback ? { jsonpCallback: params.jsonpCallback } : {}),
    })
    .then((res) => {
      // jsonp 格式化
      if (params.dataType === 'jsonp') {
        const jsonpResStr = res?.data.replace(new RegExp(`${params.jsonpCallback}\\((.*)\\)`), '$1');
        const jsonpResult = safeJSONparse(jsonpResStr) as {
          resultStatus: number;
          memo: string;
          result: Record<string, any>;
        };

        // RPC调用成功
        if (jsonpResult?.resultStatus === 1000) {
          return {
            ...res,
            data: jsonpResult.result,
          };
        } else {
          return {
            ...res,
            data: {
              success: false,
              message: jsonpResult?.result?.message || jsonpResult?.memo || '',
            },
          };
        }
      }
      return res;
    })
    .then((res) => formatRes(res))
    .catch((err) => {
      // 通用错误处理
      throw err;
    });
}

/**
 * 返回值参数格式化函数
 */
function formatRes(res: AxiosResponse<IHttpResult>) {
  const success = res?.status === 200 && res?.data?.success;
  return {
    ...res.data,
    success,
  };
}
