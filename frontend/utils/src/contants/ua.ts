/**
 * 集团UA规范文档: https://lark.alipay.com/velocity_cross-end-web/docs/wiki_app-ua
 */
const matchedAli: Array<string> = window.navigator.userAgent.match(/AliApp\(([A-Z\-]+)\/([\d\.]+)\)/i) || [];
const appName: string | undefined = matchedAli[1];
/**
 * 是否为集团app
 */
export const isAliApp = !!appName;
/**
 * 是否在支付宝端内
 */
export const isAlipay = appName === 'AP';
