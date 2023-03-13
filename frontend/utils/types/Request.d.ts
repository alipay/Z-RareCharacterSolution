export interface IHttpParams {
  url: string; // 请求地址
  method: string; // 请求类型
  data?: Record<string, any>; // 请求数据
  headers?: Record<string, any>; // 自定义请求头
  dataType?: string; // 请求类型
  jsonpCallback?: string; // jsonp请求方法 
  timeout?: number; // 自定义超时时间
}

export interface IHttpResult {
  isSuccess: boolean; // 是否成功
  errorCode?: string; // 错误码
  data?: Record<string, any>; // 返回数据
}


export interface IRpcParams {
  url: string; // 请求地址
  data?: Record<string, any>; // 请求数据
  headers?: Record<string, any>; // 自定义请求头
  options?: Record<string, any>; // 自定义选项
}

export interface IRpcResult {
  isSuccess: boolean; // 是否成功
  errorCode?: string; // 错误码
  data?: Record<string, any>; // 返回数据
}
