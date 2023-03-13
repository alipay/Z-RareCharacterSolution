export interface IFontLoaderProps {
  fontName?: string; // 字体名称
  fontSrc?: string; // 字体远程地址
  autoSetFont?: boolean; // 下载字体之后是否自动设置字体到body上
  onSuccess?: (font: FontFace) => void; // 字体加载成功回调
  onError?: (err: Error) => void; // 字体加载失败回调
}
