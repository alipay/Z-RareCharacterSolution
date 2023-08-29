import React, { Fragment, useEffect, useRef, useState } from 'react';
import { ErrorPage, InputValueDisplay, MatchWords, Popup } from './components';
import AppContext from './context';
import useInputValue from './hooks/InputValue';
import PinYinKeyboard from './keyboard/pinyin';
import StrokeKeyboard from './keyboard/stroke';
import { ICommonError, InputTypes, IWordsData } from './typings';
import { getWordsData, matchWordsRecommend } from '../../../utils/src/index';
import { mergeProps } from './utils';
import './index.less';

export interface IProps {
  /**
   * @title 是否可见
   */
  visible?: boolean;

  /**
   * @title 键盘类型
   */
  type?: InputTypes | string;

  /**
   * @title 阻止某些事件的冒泡
   * @description 默认阻止 click 事件
   */
  stopPropagation?: string[];

  /**
   * @title 不可见时卸载内容
   * @description 默认 false
   */
  destroyOnClose?: boolean;

  /**
   * @title 强制渲染内容
   * @description 默认 false
   */
  forceRender?: boolean;

  /**
   * @title 是否展示蒙层
   * @description 默认 true
   */
  showMask?: boolean;

  /**
   * @title 关闭的时候触发的回调
   */
  onClose?: () => void;

  /**
   * @title 输入完成的时候触发的回调
   */
  onFinish?: (value: string) => void;

  /**
   * @title 展示的时候触发的回调
   */
  onShow?: () => void;

  /**
   * @title 组件错误的时候的回调
   */
  onError?: (err: ICommonError) => void;

  /**
   * @title 组件加载好之后的回调
   */
  onReady?: (fontUrl: string, fontFace: object) => void;
}

const defaultProps = {
  visible: false,
  type: 'pinyin',
  stopPropagation: ['click'],
  destroyOnClose: false,
  forceRender: false,
  showMask: true,
  onClose: () => {},
  onShow: () => {},
  onFinish: () => {},
  onError: () => {},
  onReady: () => {},
};

const RareWordsInput = React.forwardRef<HTMLDivElement, IProps>((p: IProps) => {
  const props = mergeProps(defaultProps, p);
  const { visible, type, onClose, onShow, destroyOnClose, forceRender } = props;

  const [showKeyboard, setShowKeyboard] = useState<boolean>(visible);
  const typeIsHandWriting = type === InputTypes.handwriting;
  const {
    value: inputValue,
    displayStr,
    addChar,
    removeChar,
    removeAll,
  } = useInputValue();
  const [wordsData, setWordsData] = useState<IWordsData>([]);
  const [matchWords, setMatchWords] = useState<IWordsData>([]);
  const [errorInfo, setErrorInfo] = useState<ICommonError | null>(null);
  const hasFetchData = useRef(false);

  /**
   * 关闭键盘
   */
  const handleClose = () => {
    // 关闭键盘
    setShowKeyboard(false);
    // 清空候选项
    setMatchWords([]);
    // 清空输入值
    removeAll();
  };

  /**
   * 处理输入完成
   * @param value 输入值
   */
  const handleFinish = (word: string) => {
    handleClose();
    if (props.onFinish) props.onFinish(word);
  };

  /**
   * 设置候选字区域字体
   * 强制将字体设置为生僻字字体，避免外界样式影响了展示
   */
  const setMatchWordsFont = (fontFace: FontFace) => {
    try {
      const matchWordsElement = document.getElementById(
        'match-words',
      ) as HTMLElement;
      const curBodyFontAttr = getComputedStyle(
        document.getElementById('match-words') as Element,
      ).fontFamily;
      if (curBodyFontAttr.indexOf(fontFace.family) < 0) {
        const newBodyFontAttr = `${curBodyFontAttr}, '${fontFace.family}'`;
        matchWordsElement.style.fontFamily = newBodyFontAttr;
      }
    } catch (err) {
      // handle err
    }
  };

  /**
   * 加载字库数据
   */
  const fetchWordsData = () => {
    return getWordsData().then((res) => {
      const { data = [], fontFace, fontUrl } = res;
      setErrorInfo(null);
      setWordsData(data);
      hasFetchData.current = true;
      setMatchWordsFont(fontFace);
      if (props.onReady) props.onReady(fontUrl, fontFace);
    });
  };

  useEffect(() => {
    setShowKeyboard(!!props.visible);
  }, [props.visible]);

  useEffect(() => {
    if (!showKeyboard || hasFetchData.current) return;
    fetchWordsData().catch((err: ICommonError) => {
      if (props.onError) props.onError(err);
      setErrorInfo(err);
    });
  }, [showKeyboard]);

  // 监听输入值的变化修改候选字
  useEffect(() => {
    const curMatchWords = matchWordsRecommend(wordsData, displayStr);
    setMatchWords(curMatchWords);
  }, [inputValue]);

  const context = {
    inputValue,
    displayStr,
    addChar,
    removeChar,
    removeAll,
    showKeyboard,
    setShowKeyboard,
  };
  const showErrorPage = !!errorInfo?.errorCode; // 是否展示错误页

  return (
    <Popup
      className="rare-words-input"
      visible={showKeyboard}
      mask={props.showMask}
      maskClassName="rare-words-input__mask"
      position="bottom"
      afterClose={onClose}
      afterShow={onShow}
      destroyOnClose={destroyOnClose}
      forceRender={forceRender}
      closeOnMaskClick
      onMaskClick={() => {
        setShowKeyboard(false);
      }}
    >
      <div
        className="rare-words-input__keybord"
        onMouseDown={(e) => e.preventDefault()}
      >
        <div className="rare-words-input__header">
          {/* 输入值展示区 */}
          {!typeIsHandWriting && <InputValueDisplay value={inputValue} />}
          {/* 候选字展示区 */}
          <AppContext.Provider value={context}>
            <MatchWords
              value={matchWords}
              onClick={handleFinish}
              onClose={handleClose}
            />
          </AppContext.Provider>
        </div>
        {/* 操作区域 */}
        <div className="rare-words-input__main">
          {/* 错误反馈 */}
          {showErrorPage && (
            <ErrorPage {...errorInfo} onRetry={fetchWordsData} />
          )}
          {!showErrorPage && (
            <Fragment>
              {/* 笔画输入键盘 */}
              {type === InputTypes.stroke && (
                <AppContext.Provider value={context}>
                  <StrokeKeyboard />
                </AppContext.Provider>
              )}
              {/* 拼音输入键盘 */}
              {type === InputTypes.pinyin && (
                <AppContext.Provider value={context}>
                  <PinYinKeyboard />
                </AppContext.Provider>
              )}
            </Fragment>
          )}
        </div>
        <div className="rare-words-input__footer" />
      </div>
    </Popup>
  );
});

export default RareWordsInput;
