/**
 * 字体加载器
 */
import { IFontLoaderProps } from '../types';
import { DEFAULT_FONT_URL } from './contants';

enum IFontLoaderStatus {
  ready = 'ready', // 准备加载
  loading = 'loading', // 加载中
  completed = 'completed', // 加载完成
  failed = 'failed', // 加载失败
}

export class FontLoader {
  public options: IFontLoaderProps;
  public status: IFontLoaderStatus;

  constructor(props: IFontLoaderProps) {
    this.options = {
      fontName: props.fontName || 'rare-words-font',
      fontSrc: props.fontSrc || DEFAULT_FONT_URL,
      autoSetFont: true,
      onError: props.onError,
      onSuccess: props.onSuccess,
    };
    this.status = IFontLoaderStatus.ready;
    this.init();
  }

  init() {
    this.startLoad();
  }

  startLoad() {
    if (!window.FontFace) {
      this.handleError(new Error('FontFace API 不支持'));
      return;
    }

    try {
      this.status = IFontLoaderStatus.loading;
      console.log('[Z-RareCharacterSolution] [utils] --> 开始加载字体: ' + this.options.fontSrc);
      const fontFace = new window.FontFace(
        this.options.fontName as string,
        `url(${this.options.fontSrc})`,
      );
      document.fonts.add(fontFace);
      fontFace
        .load()
        .then(() => {
          console.log('[Z-RareCharacterSolution] [utils] --> 加载字体成功');
          this.status = IFontLoaderStatus.completed;
          if (this.options.autoSetFont) {
            // 将字体设置的到body根节点
            const curBodyFontAttr = getComputedStyle(document.body).fontFamily;
            const newBodyFontAttr = `${curBodyFontAttr}, '${fontFace.family}'`;
            document.body.style.fontFamily = newBodyFontAttr;
            console.log('[Z-RareCharacterSolution] [utils] --> 已设置字体到body上');
          }
          if (this.options?.onSuccess) this.options.onSuccess(fontFace);
        })
        .catch(err => {
          this.handleError(err);
        });
    } catch (err) {
      this.handleError(err);
    }
  }

  handleError(err) {
    console.log('[Z-RareCharacterSolution] [utils] --> 加载字体失败', err);
    this.status = IFontLoaderStatus.failed;
    if (this.options?.onError) this.options.onError(err);
  }
}
