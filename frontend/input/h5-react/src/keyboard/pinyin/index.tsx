import cls from 'classnames';
import React from 'react';
import { PINYIN_MAP } from '../../contants';
import AppContext from '../../context';
import { IPinYinMapItem } from '../../typings';
import './index.less';

const PinYinKeybord: React.FC = () => {
  const { addChar, removeChar } = React.useContext(AppContext);
  /**
   * 处理字母点击事件
   */
  const handleClick = (e: React.MouseEvent) => {
    const { value } = (e.target as HTMLDivElement).dataset as { value: string };
    addChar(value);
  };

  /**
   * 处理删除事件
   */
  const handleDelete = () => {
    removeChar();
  };

  const renderKey = ({ label, value, extraClassName }: IPinYinMapItem) => {
    return (
      <div
        key={value}
        className={cls(
          'rare-words-input__pinyin-key',
          extraClassName,
        )}
        data-value={value}
        onClick={handleClick}
      >
        {label}
      </div>
    );
  };

  return (
    <div className="rare-words-input__pinyin">
      {/* 拼音键盘 */}
      {PINYIN_MAP.map((row, index) => {
        return (
          <div key={`row-${index}`} className="rare-words-input__pinyin-row">
            {row.map((item) => renderKey(item))}
            {index === PINYIN_MAP.length - 1 && (
              // 删除按钮
              <div
                key="delete"
                className={cls(
                  'rare-words-input__pinyin-key',
                  'rare-words-input__pinyin-delete-btn',
                )}
                onClick={handleDelete}
              >
                <i className={cls('iconfont', 'icon-delete')} />
              </div>
            )}
          </div>
        );
      })}
    </div>
  );
};

export default PinYinKeybord;
