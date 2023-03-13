import {
  API_IS_RARE_NAME,
  API_ORIGIN,
} from '../constants';
import { request } from '@/utils';

/**
 * 姓名判断
 */
export function isRareNameService(params: IIsRareNameParams) {
  const requestPamras = {
    method: 'GET',
    url: `${API_ORIGIN}${API_IS_RARE_NAME}`,
    data: params,
  };
  return request(requestPamras) as Promise<IIsRareNameResult>;
}
