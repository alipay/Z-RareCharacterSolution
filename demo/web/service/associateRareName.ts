import {
  API_NAME_RECOMMEND,
  API_ORIGIN,
} from '../constants';
import { request } from '@/utils';

/**
 * 姓名推荐
 */
export function associateRareNameService(params: IRecommendNameParams) {
  const requestPamras = {
    method: 'GET',
    url: `${API_ORIGIN}${API_NAME_RECOMMEND}`,
    data: params,
  };
  return request(requestPamras) as Promise<IRecommendNameResult>;
}
