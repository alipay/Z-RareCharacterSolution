interface IHttpParams {
  url: string; // 请求地址
  method: string; // 请求类型
  data?: Record<string, any>; // 请求数据
  headers?: Record<string, any>; // 自定义请求头
  dataType?: string; // 请求类型
  jsonpCallback?: string; // jsonp请求方法
  timeout?: number; // 自定义超时时间
}

interface IHttpResult {
  success: boolean; // 是否成功
  retCode: string; // 返回状态码
  extResult: null|Record<string, unknown>; // 额外返回值参数
  message: string; // 提示信息
}

interface IBaseParams {
  principalId: string; // 主体ID，例如userId这些
  serviceContext: null|Record<string, unknown>; // 额外参数
}

interface IBaseResult {
  success: boolean; // 是否成功
  retCode: string; // 返回状态码
  extResult: null|Record<string, unknown>; // 额外返回值参数
}

interface IIsRareNameParams extends IBaseParams {
  name: string; // 需要判断的姓名
  certType: string; // 证件类型
}

interface IIsRareNameResult extends IBaseResult {
  retCode: 'IS_RARE_NAME' | 'POSSIBLE_RARE_NAME' | 'NOT_RARE_NAME'; // 判断结果
}

interface IIsSameNameParams extends IBaseParams {
  sourceName: string; // 需要判断的姓名1
  targetName: string; // 需要判断的姓名2
  certType: string; // 证件类型
}

interface IIsSameNameResult extends IBaseResult {
  retCode: 'POSSIBLE_SAME_RARE_NAME' | 'IS_SAME_RARE_NAME' | 'NOT_SAME_RARE_NAME'; // 判断结果
}

interface IRecommendNameParams extends IBaseParams {
  name: string; // 需要联想的姓名
}

interface IRecommendItem {
  name: string; // 各式各样的姓名
  remarks: string; // 类型备注
}

interface IRecommendNameResult extends IBaseResult {
  rareNameInfos?: IRecommendItem[]; // 联想结果
}

interface ITransferNameParams extends IBaseParams {
  name: string; // 需要转换的姓名
  targetEncodeType: string; // 目标编码方式
}

interface ITransferNameResult extends IBaseResult {
  extResult: {
    retNameList?: string; // 转换结果（可能包含多个结果）
  }
}

interface ITransferNameUnicodeAndPuaParams extends IBaseParams {
  name: string; // 需要转换的姓名
}

interface ITransferNameUnicodeAndPuaResult extends IBaseResult {
  extResult: {
    retName?: string; // 转换结果
  }
}
