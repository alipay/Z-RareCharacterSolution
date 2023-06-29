import { getWordsData, matchWordsRecommend } from '../../../utils/src/index';
import React, { useEffect, useRef, useState } from 'react';
import {
  ErrorPage,
  InputValueDisplay,
  KeyboardOptions,
  MatchWords,
  Popup,
} from './components';
import AppContext from './context';
import useInputValue from './hooks/InputValue';
import './index.less';
import { PinYinKeyboard, SplitKeyboard, StrokeKeyboard } from './keyboard';
import { ICommonError, IWordsData, InputTypes } from './typings';
import { mergeProps } from './utils';

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

enum IPageState {
  NORMAL = 'NORMAL', // 正常键盘界面
  ERROR = 'ERROR', // 出现错误
  SWITCH_KEYBOARD = 'SWITCH_KEYBOARD', // 切换键盘类型
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

const RareWordsInput = React.forwardRef<HTMLDivElement, IProps>((p, ref) => {
  const props = mergeProps(defaultProps, p);
  const { visible, type, onClose, onShow, destroyOnClose, forceRender } = props;

  const [showKeyboard, setShowKeyboard] = useState<boolean>(visible);
  const typeIsHandWriting = type === InputTypes.handwriting;
  const {
    value: inputValue,
    displayStr,
    addChar,
    addMutiChar,
    removeChar,
    removeAll,
  } = useInputValue();
  const [pageState, setPageState] = useState<IPageState>(IPageState.NORMAL);
  const [keyboardType, setKeyboardType] = useState<InputTypes>(
    InputTypes[props.type as InputTypes] || InputTypes.pinyin,
  );
  const [wordsData, setWordsData] = useState<IWordsData>([]);
  const [matchWords, setMatchWords] = useState<IWordsData>([]);
  const [errorInfo, setErrorInfo] = useState<ICommonError>();
  const hasFetchData = useRef(false);

  /**
   * 清空输入值和候选字列表
   */
  const handleClearInputValue = () => {
    // 清空候选项
    setMatchWords([]);
    // 清空输入值
    removeAll();
  };

  /**
   * 关闭键盘
   */
  const handleClose = () => {
    setShowKeyboard(false);
    handleClearInputValue();
  };

  /**
   * 处理输入完成
   * @param {string} value 输入值
   */
  const handleFinish = (word: string) => {
    handleClose();
    if (props.onFinish) props.onFinish(word);
  };

  /**
   * 切换键盘类型
   * @param {InputTypes} type 键盘类型
   */
  const handleChangeKeyboardType = (type: InputTypes) => {
    setKeyboardType(type);
    setPageState(IPageState.NORMAL);
  };

  /**
   * 设置候选字区域字体
   * 强制将字体设置为生僻字字体，避免外界样式影响了展示
   * @param {FontFace} fontFace 字体对象s
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
      console.log('[调试代码] ~ file: index.tsx:184 ~ returngetWordsData ~ res:', res);
      const { data = [], fontFace, fontUrl } = res;
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
      setPageState(IPageState.ERROR);
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
    addMutiChar,
    removeChar,
    removeAll,
    showKeyboard,
    setShowKeyboard,
    keyboardType,
  };

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
      <div ref={ref} className="rare-words-input__keybord">
        <div className="rare-words-input__header">
          {/* 输入值展示区 */}
          {!typeIsHandWriting && <InputValueDisplay value={inputValue} />}
          {/* 候选字展示区 */}
          <AppContext.Provider value={context}>
            <MatchWords
              value={matchWords}
              onClick={handleFinish}
              onClose={handleClose}
              onSwitch={() => {
                setPageState(IPageState.SWITCH_KEYBOARD);
                handleClearInputValue();
              }}
            />
          </AppContext.Provider>
        </div>
        {/* 操作区域 */}
        <div className="rare-words-input__main">
          {/* 键盘界面 */}
          {pageState === IPageState.NORMAL && (
            <>
              <AppContext.Provider value={context}>
                {/* 笔画输入键盘 */}
                {keyboardType === InputTypes.stroke && <StrokeKeyboard />}
                {/* 拼音输入键盘 */}
                {keyboardType === InputTypes.pinyin && <PinYinKeyboard />}
                {/* 拆字输入键盘 */}
                {keyboardType === InputTypes.split && <SplitKeyboard />}
              </AppContext.Provider>
            </>
          )}
          {/* 错误反馈 */}
          {pageState === IPageState.ERROR && (
            <ErrorPage {...errorInfo} onRetry={fetchWordsData} />
          )}
          {/* 键盘切换界面 */}
          {pageState === IPageState.SWITCH_KEYBOARD && (
            <KeyboardOptions
              defaultValue={keyboardType}
              onChange={handleChangeKeyboardType}
            />
          )}
        </div>
        <div className="rare-words-input__footer" />
      </div>
    </Popup>
  );
});

export default RareWordsInput;
