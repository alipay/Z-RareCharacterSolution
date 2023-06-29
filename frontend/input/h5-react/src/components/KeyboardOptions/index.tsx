/**
 * 切换键盘类型界面
 */
import cls from 'classnames';
import React, { useState } from 'react';
import { InputTypes } from '../../typings';
import './index.less';

interface IProps {
  defaultValue: InputTypes;
  onChange: (type: InputTypes) => void;
}

const KeyboardOptions: React.FC<IProps> = (props) => {
  // 当前键盘类型
  const [value, setValue] = useState<InputTypes>(
    props.defaultValue || InputTypes.pinyin,
  );
  // 处理切换键盘事件
  const handleChange = (type: InputTypes) => {
    setValue(type);
    if (props.onChange) props.onChange(type);
  };

  return (
    <div className="rare-words-input__keyboard-options">
      <div
        className={cls('rare-words-input__keyboard-option', {
          checked: value === InputTypes.pinyin,
        })}
        onClick={() => handleChange(InputTypes.pinyin)}
      >
        <div className="rare-words-input__keyboard-option-icon">
          <i className="iconfont icon-pinyin" />
        </div>
        <p>拼音</p>
      </div>
      <div
        className={cls('rare-words-input__keyboard-option', {
          checked: value === InputTypes.split,
        })}
        onClick={() => handleChange(InputTypes.split)}
      >
        <div className="rare-words-input__keyboard-option-icon">
          <i className="iconfont icon-chaizi" />
        </div>
        <p>拆字</p>
      </div>
      {/* <div
        className={cls('rare-words-input__keyboard-option', {
          checked: value === InputTypes.stroke,
        })}
        onClick={() => handleChange(InputTypes.stroke)}
      >
        <div className="rare-words-input__keyboard-option-icon">
          <i className="iconfont icon-bihua" />
        </div>
        <p>笔画</p>
      </div> */}
    </div>
  );
};

export { KeyboardOptions };
