import React from 'react';
import {
  Input,
  Button,
  Card,
  Alert,
  Radio,
  Select,
  Empty,
  Tooltip,
} from 'antd';
import Icon, { CopyOutlined } from '@ant-design/icons';
import { CopyToClipboard } from 'react-copy-to-clipboard';
import { transferRareNameService, transferUniAndPuaRareNameService } from '../../../../service';
import { KeyBoardIcon } from '../../../../components/icons';
import RareWordsInput from '../../../../../../frontend/input/pc-react/src/index';
import './index.less';

export default () => {
  const [logs, setLogs] = React.useState<{msg: string, time: number}[]>([]);
  const [name, setName] = React.useState('');
  const [transType, setTransType] = React.useState('unicodeAndPua');
  const [codeType, setCodeType] = React.useState('');
  const [loading, setLoading] = React.useState(false);
  const [inputError, setInputError] = React.useState(false);
  const [showSuccess, setShowSuccess] = React.useState(false);
  const [result, setResult] = React.useState([]);
  const [showError, setShowError] = React.useState(false);
  const [errorMsg, setErrorMsg] = React.useState('');
  let startTime = Date.now();

  /**
   * 是否为unicode和pua互转场景
   */
  const isUnicodeAndPuaTransType = React.useMemo(
    () => transType === 'unicodeAndPua',
    [transType],
  );

  // 记录日志
  const printLog = (msg) => {
    setLogs((logs) => [...logs, { msg, time: Date.now() - startTime }]);
  };

  /**
   * 生僻字输入完成
   */
  const handleRareWordsInput = (value) => {
    const newValue = name + value;
    setName(newValue);
  };

  /**
   * 姓名发生变化事件
   */
  const handleNameChange = (e) => {
    const { value: inputValue } = e.target;
    if (inputValue) {
      if (showError) setShowError(false);
      if (inputError) setInputError(false);
      if (showSuccess) setShowSuccess(false);
    }
    setName(inputValue);
  };

  /**
   * 处理转码方式选择事件
   */
  const handleTransTypeChange = (e) => {
    const { value: inputValue } = e.target;
    if (inputValue === 'unicodeAndPua') {
      setCodeType('');
    }
    setTransType(inputValue);
  };

  /**
   * 处理编码方式选择事件
   */
  const handleCodeTypeChange = (value) => {
    setCodeType(value);
  };

  /**
   * 表单填写完成处理事件
   */
  const onSubmit = React.useCallback(() => {
    if (!name) {
      setInputError(true);
      return;
    }
    startTime = Date.now();
    printLog(
      `编码转换，姓名：${name}，转码方式：${transType}${
        codeType ? `，编码方式：${codeType}` : ''
      }`,
    );
    setLoading(true);
    const service = isUnicodeAndPuaTransType ? transferUniAndPuaRareNameService : transferRareNameService;
    const params = isUnicodeAndPuaTransType
      ? { name }
      : { name, targetEncodeType: codeType };
    service({
      principalId: '2088',
      ...params,
      serviceContext: null,
    })
      .then((res) => {
        if (res?.success) {
          const result = (isUnicodeAndPuaTransType
            ? res?.extResult?.retName?.split(',')
            : res?.extResult?.retNameList?.split(',')) || [];
          printLog(`判断结果: ${result || '空'}`);
          setShowError(false);
          setErrorMsg('');
          setShowSuccess(true);
          setResult(result);
          return;
        }
        throw new Error(
          res?.message || `出现错误：${res?.retCode}`,
        );
      })
      .catch((err) => {
        const message = err.message || '服务器繁忙';
        printLog(`出现错误: ${message}`);
        setShowSuccess(false);
        setShowError(true);
        setErrorMsg(message);
      })
      .finally(() => {
        setLoading(false);
      });
  }, [name, transType, codeType, isUnicodeAndPuaTransType]);

  React.useEffect(() => {
    printLog('页面加载-用户姓名转码');
  }, []);

  return (
    <div className="pc-container transfer">
      <div className="wrap">
        <Card
          className="wrap"
          title={
            <div className="card-header">
              <div className="title">
                功能：给定生僻字姓名，转码成期望的编码姓名
                <br />
                使用场景：
                <br />
                pua和unicode正式码互转，适合联网核查，用输入的生僻字姓名联网核查不通过时，转为另一个码继续尝试联网核查
                <br />
                指定目标编码，适合业务方清楚比对方需要的目标编码，如和A银行进行姓名比对，A银行生僻字姓名都是unicode，则可指定目标编码为unicode进行转码后进行比对
                <br />
                转码规则：
              </div>
              <ul>
                <li>
                  pua和unicode正式码互转，给定pua码字返回unicode码字（如张䶮返回张ᵖᵘᵃ）;
                  给定unicode码字返回pua码字（如张ᵖᵘᵃ返回张䶮）
                </li>
                <li>
                  指定目标编码，给定拼音姓名，指定unicode码字（如张yan返回张䶮,张𰎠,张𪩘
                  ）
                </li>
              </ul>
            </div>
          }
          style={{ width: '100%' }}
        >
          <div className="form">
            <div className="form__item">
              <div className="form__item-label">姓名：</div>
              <div className="form__item-right">
                <Input
                  value={name}
                  placeholder="请填写名称"
                  onChange={handleNameChange}
                  className="form__item-input"
                  status={inputError ? 'error' : ''}
                />
                <RareWordsInput onFinish={handleRareWordsInput}>
                  <Icon
                    className="form__item-icon keyboard"
                    component={KeyBoardIcon}
                  />
                </RareWordsInput>
              </div>
            </div>
            <div className="form__item">
              <div className="form__item-label">转换类型：</div>
              <div className="form__item-right">
                <Radio.Group
                  defaultValue="unicodeAndPua"
                  onChange={handleTransTypeChange}
                >
                  <Radio.Button value="unicodeAndPua">
                    PUA和正式码互转
                  </Radio.Button>
                  <Radio.Button value="defined">指定目标编码</Radio.Button>
                </Radio.Group>
              </div>
            </div>
            {transType === 'defined' && (
              <div className="form__item">
                <div className="form__item-label">编码方式：</div>
                <div className="form__item-right">
                  <Select
                    placeholder="请选择编码方式"
                    style={{ width: 200 }}
                    status={inputError ? 'error' : ''}
                    options={[
                      { value: 'UNICODE', label: 'unicode' },
                      { value: 'PUA', label: 'pua' },
                      { value: 'PINYIN', label: '拼音' },
                      { value: 'SPLIT', label: '拆字' },
                    ]}
                    onChange={handleCodeTypeChange}
                  />
                </div>
              </div>
            )}
          </div>
          <div className="form__bottom">
            <Button
              className="form__bottom-btn"
              type="primary"
              htmlType="submit"
              loading={loading}
              onClick={onSubmit}
            >
              提交
            </Button>
          </div>
          <div className="result">
            {showError && (
              <Alert
                className="result-tips"
                message={errorMsg}
                type="error"
                showIcon
              />
            )}
          </div>

          {showSuccess && (
            <div className="recommend">
              <p className="title">转码结果</p>
              <div className="recommend__item-wrap">
                {result?.length === 0 && <Empty />}
                {result?.length > 0 &&
                  result.map((item, index) => (
                    <Card
                      key={`item-${index}`}
                      style={{ width: 220 }}
                      actions={[
                        // <ShareAltOutlined key="edit" />,
                        <CopyToClipboard text={item}>
                          <Tooltip placement="bottom" title="Copied!">
                            <CopyOutlined key="copy" />
                          </Tooltip>
                        </CopyToClipboard>,
                      ]}
                    >
                      <div className="recommend__item">
                        <div className="recommend__item-text">{item}</div>
                      </div>
                    </Card>
                  ))}
              </div>
            </div>
          )}

          <br />
          <div className="logs">
            <p className="title">操作日志</p>
            {logs.map((log, index) => (
              <p
                key={`log-${index}`}
                className="log"
              >{`${log.msg}，耗时 ${log.time} ms`}</p>
            ))}
          </div>
        </Card>
      </div>
    </div>
  );
};
