import {
  API_NAME_TRANSFER_UNICODE_AND_PUA,
  API_ORIGIN,
} from '../constants';
import { request } from '@/utils';

/**
 * 编码转换
 * unicode 和 pua互转
 */
export function transferUniAndPuaRareNameService(params: ITransferNameUnicodeAndPuaParams) {
  const requestPamras = {
    method: 'GET',
    url: `${API_ORIGIN}${API_NAME_TRANSFER_UNICODE_AND_PUA}`,
    data: params,
  };
  return request(requestPamras) as Promise<ITransferNameUnicodeAndPuaResult>;
}
