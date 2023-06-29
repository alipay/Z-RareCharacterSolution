import cls from 'classnames';
import React, { useContext, useEffect, useRef, useState } from 'react';
import AppContext from '../../context';
import { IWordsData } from '../../typings';
import { isWordEmpty } from '../../utils';
import { MatchWordTips } from '../MatchWordTips';
import './index.less';

interface IProps {
  value: IWordsData; // Z字库数据
  onClick: (word: string) => void; // 选择候选字
  onClose: () => void; // 点击收起键盘
  onSwitch: () => void; // 切换键盘
}

const MatchWords: React.FC<IProps> = (props) => {
  const wordsContainerRef = useRef<HTMLDivElement>(null);
  const wordRef = useRef<HTMLDivElement>(null);
  const { displayStr, keyboardType } = useContext(AppContext);
  const [showMore, setShowMore] = useState<boolean>(false); // 点击展示展示更多候选字
  const [maxDisplayNum, setMaxDisplayNum] = useState<number>(0); // 一行最大能展示的候选字

  // 过滤掉不应该展示出来的候选字
  const wordList = props.value.filter((item) => !isWordEmpty(item));
  // 图标类名
  let iconName = 'icon-arrow-down';
  if (wordList.length > maxDisplayNum) {
    if (showMore) {
      iconName = 'icon-arrow-up';
    } else {
      iconName = 'icon-arrow-right';
    }
  }
  const isInputValueEmpty = displayStr === ''; // 输入值为空
  const hasNoMacthWods = wordList.length === 0; // 匹配项为空
  const isWordsOverflow = wordList.length > maxDisplayNum; // 候选字是否超过了一行

  /**
   * 点击查看更多或者收起键盘
   */
  const handleLookMore = () => {
    // 字数不足一行，点击按钮关闭输入框
    if (!isWordsOverflow) {
      if (props.onClose) props.onClose();
      return;
    }
    setShowMore(!showMore);
  };

  /**
   * 点击候选字完成输入
   */
  const handleWordClick = (e: React.MouseEvent) => {
    const { value } = (e.target as HTMLDivElement).dataset as { value: string };
    if (props.onClick) props.onClick(value);
    setShowMore(false);
  };

  useEffect(() => {
    if (!wordsContainerRef?.current || !wordRef?.current) return;
    const containerWidth =
      wordsContainerRef?.current.getBoundingClientRect()?.width;
    const wordWidth = wordRef?.current.getBoundingClientRect()?.width;
    const maxDisplayNumInOneLine = parseInt(
      (containerWidth / wordWidth).toString(),
      10,
    );
    setMaxDisplayNum(maxDisplayNumInOneLine);
  }, [wordsContainerRef?.current, wordRef?.current]);

  return (
    <div id="match-words" className="rare-words-input__match-words-wrap">
      <div
        className={cls('rare-words-input__match-words', {
          'rare-words-input__match-words-absolute': showMore,
        })}
      >
        <div
          ref={wordsContainerRef}
          className={cls('rare-words-input__match-words-words', {
            'rare-words-input__match-words-words-all': showMore,
          })}
        >
          {/* 候选字列表 */}
          {wordList.map((word, index) => (
            <div
              ref={wordRef}
              key={`word-${index}`}
              className={cls(
                'rare-words-input__match-words-word',
                'rare-words',
                {
                  'rare-words-input__match-words-word-all': showMore,
                },
              )}
            >
              <div
                className="rare-words-input__match-words-word-inner"
                data-value={word.unicodeChar}
                data-code={word.unicodeCodePoint}
                onClick={handleWordClick}
              >
                {word.unicodeChar}
              </div>
            </div>
          ))}
          {/* 写入一个默认字，方便计算一行最大可显示的字数 */}
          {hasNoMacthWods && (
            <div ref={wordRef} className="rare-words-input__match-words-hidden">
              哈
            </div>
          )}
          {/* 未输入值的时候的提示 */}
          {isInputValueEmpty && hasNoMacthWods && (
            <MatchWordTips type={`${keyboardType}-placeholder`} />
          )}
          {/* 未找到匹配项提示 */}
          {!isInputValueEmpty && hasNoMacthWods && (
            <MatchWordTips
              type="match-words-empty"
              data={{ inputValue: displayStr }}
            />
          )}
        </div>
        <div
          className={cls('rare-words-input__match-words-right', {
            'rare-words-input__match-words-overflow':
              isWordsOverflow && !showMore,
            'rare-words-input__match-words-sticky': showMore,
          })}
        >
          {!showMore && (
            <i
              className={cls('iconfont', 'icon-keyboard-switch')}
              onClick={props.onSwitch}
            />
          )}
          <i className={cls('iconfont', iconName)} onClick={handleLookMore} />
        </div>
      </div>
    </div>
  );
};

export { MatchWords };
