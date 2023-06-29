import cls from 'classnames';
import React from 'react';
import { BTN_TEXT, STROKE_MAP } from '../../contants';
import AppContext from '../../context';
import './index.less';

const StrokeKeyboard: React.FC = () => {
  const { addChar } = React.useContext(AppContext);
  /**
   * 处理字母点击事件
   */
  const handleClick = (e: React.MouseEvent) => {
    const { value } = (e.target as HTMLDivElement).dataset as { value: string };
    addChar(value);
  };

  return (
    <div className="rare-words-input__stroke">
      <div className="rare-words-input__stroke-left">
        {/* 笔画 */}
        {STROKE_MAP.map(({ label, value, extraClassName }) => (
          <div
            key={value}
            className={cls('rare-words-input__stroke-key', extraClassName)}
            data-value={value}
            onClick={handleClick}
          >
            {label}
          </div>
        ))}
        {/* 底部操作区 */}
        <div className={cls('rare-words-input__stroke-key', 'text-btn')}>
          {BTN_TEXT.SYMBOL}
        </div>
        <div
          className="rare-words-input__stroke-key"
          data-value=" "
          onClick={handleClick}
        >
          <i className="iconfont icon-space" />
        </div>
        <div className="rare-words-input__stroke-key">
          <i className="iconfont icon-mode" />
        </div>
      </div>
      <div className="rare-words-input__stroke-operation">
        <div className="rare-words-input__stroke-key">
          <i className="iconfont icon-delete" />
        </div>
        <div className={cls('rare-words-input__stroke-key', 'text-btn')}>
          {BTN_TEXT.RETRY}
        </div>
        <div
          className={cls(
            'rare-words-input__stroke-key',
            'text-btn',
            'confirm-btn',
          )}
        >
          {BTN_TEXT.CONFIRM}
        </div>
      </div>
    </div>
  );
};

export { StrokeKeyboard };
