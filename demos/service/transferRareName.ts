import {
  API_NAME_TRANSFER,
  API_ORIGIN,
} from '../constants';
import { request } from '@/utils';

/**
 * 编码转换
 */
export function transferRareNameService(params: ITransferNameParams) {
  const requestPamras = {
    method: 'GET',
    url: `${API_ORIGIN}${API_NAME_TRANSFER}`,
    data: params,
  };
  return request(requestPamras) as Promise<ITransferNameResult>;
}
