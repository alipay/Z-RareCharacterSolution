import cls from 'classnames';
import React, { ChangeEvent, CompositionEvent } from 'react';
import { BTN_TEXT, SPLIT_MAP } from '../../contants';
import AppContext from '../../context';
import { IPinYinMapItem } from '../../typings';
import './index.less';

const SplitKeyboard: React.FC = () => {
  const { displayStr, addChar, addMutiChar, removeChar } =
    React.useContext(AppContext);
  const [strokeNum, setStrokeNum] = React.useState<number>(0);
  const [inputValue, setInputValue] = React.useState<string>('');
  const inputLock = React.useRef(false);

  /**
   * 处理字母点击事件
   */
  const handleClick = (value: string) => {
    addChar(value);
  };

  /**
   * 处理删除事件
   */
  const handleDelete = () => {
    removeChar();
    if (
      displayStr.indexOf(inputValue) ===
      displayStr.length - inputValue.length
    ) {
      setInputValue(inputValue.substring(0, inputValue.length - 1));
    }
  };

  /**
   * 处理删除事件
   */
  const handleSelectStrokeNum = (index: number) => {
    setStrokeNum(index);
  };

  /**
   * 处理输入框输入事件
   */
  const handleInputChange = (e: ChangeEvent<HTMLInputElement>) => {
    const newValue = e?.target?.value;
    setInputValue(newValue);
  };

  /**
   * 处理中文输入完成事件
   */
  const handleInputFinished = (e: CompositionEvent<HTMLInputElement>) => {
    const newValue = e?.data;
    addMutiChar(newValue);
  };

  const renderKey = ({ label, value, extraClassName }: IPinYinMapItem) => {
    return (
      <div
        key={value}
        className={cls('rare-words-input__split-key', 'word', extraClassName)}
        data-value={value}
        onClick={() => handleClick(value)}
      >
        <div className="rare-words-input__split-key-inner">{label}</div>
      </div>
    );
  };

  let curWords = SPLIT_MAP[strokeNum].words;
  if (curWords.length <= 12 && SPLIT_MAP[strokeNum + 1]) {
    curWords = curWords.concat(
      SPLIT_MAP[strokeNum + 1]?.words?.slice(0, 12 - curWords.length),
    );
  }

  return (
    <div className="rare-words-input__split">
      <div className="rare-words-input__split-input">
        <input
          type="text"
          value={inputValue}
          placeholder="䶮可以输入龙+天"
          onChange={handleInputChange}
          onCompositionStart={() => (inputLock.current = true)}
          onCompositionEnd={handleInputFinished}
        />
      </div>
      <div className="rare-words-input__split-main">
        {/* 拼音键盘 */}
        <div className="rare-words-input__split-left">
          {SPLIT_MAP.map((row, index) => {
            return (
              <div
                key={`row-${index}`}
                className="rare-words-input__split-key category"
                data-index={index}
                onClick={() => handleSelectStrokeNum(index)}
              >
                <div className="rare-words-input__split-key-inner">
                  {row?.category}
                </div>
              </div>
            );
          })}
        </div>
        <div className="rare-words-input__split-middle">
          {curWords.map((word) => renderKey(word))}
        </div>
        {/* 操作区域 */}
        <div className="rare-words-input__split-operation">
          <div className="rare-words-input__split-key">
            <div
              className="rare-words-input__split-key-inner"
              onClick={handleDelete}
            >
              <i className="iconfont icon-delete" />
            </div>
          </div>
          <div className={cls('rare-words-input__split-key', 'text-btn')}>
            <div className="rare-words-input__split-key-inner">
              {BTN_TEXT.RETRY}
            </div>
          </div>
          <div
            className={cls(
              'rare-words-input__split-key',
              'text-btn',
              'confirm-btn',
            )}
          >
            <div className="rare-words-input__split-key-inner">
              {BTN_TEXT.CONFIRM}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export { SplitKeyboard };
