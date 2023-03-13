import {
  API_IS_NAME_SAME,
  API_ORIGIN,
} from '../constants';
import { request } from '@/utils';

/**
 * 姓名判同
 */
export function isSameRareNameService(params: IIsSameNameParams) {
  const requestPamras = {
    method: 'GET',
    url: `${API_ORIGIN}${API_IS_NAME_SAME}`,
    data: params,
  };
  return request(requestPamras) as Promise<IIsSameNameResult>;
}
