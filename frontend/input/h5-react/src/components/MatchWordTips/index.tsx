import React from 'react';
import { formatText } from '../../utils';
import './index.less';

interface IProps {
  type: IKeys; // 提示类型
  data?: object; // 动态参数
}

type IKeys =
  | 'stroke-placeholder'
  | 'split-placeholder'
  | 'pinyin-placeholder'
  | 'handwriting-placeholder'
  | 'match-words-empty';

const TEMPLATE = {
  'pinyin-placeholder': '请输入生僻字对应的拼音',
  'split-placeholder': '请输入生僻字对应的拆字形式',
  'stroke-placeholder': '请输入生僻字对应的笔画顺序',
  'handwriting-placeholder': '请在下方面板书写生僻字',
  // 'match-words-empty': '未找到和”{inputValue}“匹配的生僻字'
  'match-words-empty': '更多生僻字完善中，敬请期待',
};

const MatchWordTips: React.FC<IProps> = (props) => {
  const text = formatText(TEMPLATE[props.type], props.data || {});
  return <div className="rare-words-input__tips">{text}</div>;
};

export { MatchWordTips };
