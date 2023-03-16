import { getWordsData, matchWordsRecommend } from 'ant-rare-words-utils';
import { Button, Input, Modal, Result } from 'antd';
import cls from 'classnames';
import React, { useEffect, useState } from 'react';
import { ICON_DOWN } from './contants';
import './index.less';
import { FontLibrary, FontLibraryArray } from './types/font';

export interface RareWordsInputProps {
  children?: React.ReactNode;
  onFinish: (result: string) => void;
}

const RareWordsInput: React.FC<RareWordsInputProps> = ({
  onFinish,
  children,
}) => {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [key, setKey] = useState('');
  const [searchData, setSearchData] = useState<FontLibraryArray>([]);
  const [showAllSearchData, setShowAllSearchData] = useState(false);
  const [inputWords, setInputWords] = useState('');
  const [wordsLibrary, setWordsLibrary] = useState<FontLibraryArray>([]);
  const [fontLoadedSuccess, setFontLoadedSuccess] = useState<boolean>(true);
  const [showEmptyTips, setShowEmptyTips] = useState<boolean>(false);

  const resetState = () => {
    setIsModalOpen(false);
    setKey('');
    setInputWords('');
    setSearchData([]);
    setShowAllSearchData(false);
    setShowEmptyTips(false);
  };

  const initInputFontLibrary = () => {
    getWordsData()
      .then((res) => {
        setFontLoadedSuccess(true);
        setWordsLibrary(res.data);
      })
      .catch(() => {
        setFontLoadedSuccess(false);
      });
  };

  // 初始化字库
  useEffect(() => {
    initInputFontLibrary();
  }, []);

  useEffect(() => {
    if (key.length > 0) {
      const data = matchWordsRecommend(wordsLibrary, key);
      setShowEmptyTips(data.length === 0);

      if (data.length > 10) {
        data.splice(9, 0, {
          type: 'image',
          extInfo: 'down',
          unicodeChar: '',
          unicodeCodePoint: '',
          unicodeFont: '',
          pinYinChars: [],
          splitChars: [],
        });
      }
      setSearchData(data);
      return;
    }
    setShowEmptyTips(false);
    setSearchData([]);
    setShowAllSearchData(false);
  }, [key]);

  const renderSearchResult = () => {
    if (showEmptyTips) {
      return (
        // eslint-disable-next-line react/no-unescaped-entities
        <div className="search-result-empty">未找到和"{key}"匹配的生僻字</div>
      );
    }

    const onCellClick = (item: FontLibrary) => {
      if (item.type === 'image') {
        setShowAllSearchData(true);
        return;
      }
      setInputWords(item.unicodeChar);
      setIsModalOpen(false);
    };

    let result = [];
    const searchDataCopy = searchData.slice(
      0,
      showAllSearchData ? searchData.length : 10,
    );
    if (searchData.length > 10 && !showAllSearchData) {
      result = searchDataCopy.map((item) => {
        return (
          <div
            className={cls('font-cell', item.extInfo || '')}
            key={item.unicodeCodePoint}
            onClick={() => onCellClick(item)}
          >
            {item.type === 'image' && item.extInfo === 'down' ? (
              <img className="icon-down" src={ICON_DOWN} alt="" />
            ) : (
              item.unicodeChar
            )}
          </div>
        );
      });
      return <div className="flex-nowrap search-result">{result}</div>;
    }

    result = searchDataCopy
      .filter((item) => {
        return item.type !== 'image';
      })
      .map((item) => {
        return (
          <div
            className={cls('font-cell', item.extInfo || '')}
            key={item.unicodeCodePoint}
            onClick={() => onCellClick(item)}
          >
            {item.unicodeChar}
          </div>
        );
      });

    return <div className="search-result flex-wrap">{result}</div>;
  };

  return (
    <React.Fragment>
      <div
        className="rare-container"
        onClick={() => {
          setIsModalOpen(true);
        }}
      >
        {children || '请输入生僻字'}
      </div>

      <Modal
        className="rare-input-modal"
        title={
          <div className="title">
            正在保护您的信息安全
          </div>
        }
        open={isModalOpen}
        destroyOnClose
        maskClosable
        footer={null}
        closable={false}
        width={375}
        afterClose={() => {
          onFinish(inputWords);
          resetState();
        }}
        onCancel={() => {
          resetState();
        }}
        centered
      >
        {!fontLoadedSuccess ? (
          <Result
            subTitle="输入法初始化失败"
            status="error"
            extra={
              <Button
                block
                type="primary"
                onClick={() => {
                  initInputFontLibrary();
                }}
              >
                重试
              </Button>
            }
          />
        ) : (
          <div>
            <div className="search-bar">
              <Input
                placeholder="请输入拼音或拆字"
                className="search-bar-input"
                maxLength={10}
                size="large"
                onChange={(e) => {
                  setKey(e.target.value);
                }}
              />
            </div>
            {renderSearchResult()}
          </div>
        )}
      </Modal>
    </React.Fragment>
  );
};

export default RareWordsInput;
