import React from 'react';
import { Input, Button, Card, Alert } from 'antd';
import Icon from '@ant-design/icons';
import { isSameRareNameService } from '../../../../service';
import {
  KeyBoardIcon,
  SuccessIcon,
  ErrorIcon,
  QuestionIcon,
} from '../../../../components/icons';
import RareWordsInput from '../../../../../../frontend/input/pc-react/src/index';
import './index.less';

export default () => {
  const [logs, setLogs] = React.useState<{msg: string, time: number}[]>([]);
  const [nameOne, setNameOne] = React.useState('');
  const [nameTwo, setNameTwo] = React.useState('');
  const [loading, setLoading] = React.useState(false);
  const [inputError, setInputError] = React.useState(false);
  const [showSuccess, setShowSuccess] = React.useState(false);
  const [result, setResult] = React.useState('');
  const [showError, setShowError] = React.useState(false);
  const [errorMsg, setErrorMsg] = React.useState('');
  let startTime = Date.now();

  // 记录日志
  const printLog = (msg) => {
    setLogs((logs) => [...logs, { msg, time: Date.now() - startTime }]);
  };

  /**
   * 生僻字输入完成
   */
  const handleRareWordsOneInput = (value) => {
    const newValue = nameOne + value;
    setNameOne(newValue);
  };

  /**
   * 姓名发生变化
   */
  const handleNameOneChange = (e) => {
    const { value: inputValue } = e.target;
    if (inputValue) {
      if (showError) setShowError(false);
      if (inputError) setInputError(false);
      if (showSuccess) setShowSuccess(false);
    }
    setNameOne(inputValue);
  };

  /**
   * 生僻字输入完成
   */
  const handleRareWordsTwoInput = (value) => {
    const newValue = nameTwo + value;
    setNameTwo(newValue);
  };

  /**
   * 姓名发生变化
   */
  const handleNameTwoChange = (e) => {
    const { value: inputValue } = e.target;
    if (inputValue) {
      if (showError) setShowError(false);
      if (inputError) setInputError(false);
      if (showSuccess) setShowSuccess(false);
    }
    setNameTwo(inputValue);
  };

  /**
   * 表单填写完成处理事件
   */
  const onSubmit = () => {
    if (!nameOne || !nameTwo) {
      setInputError(true);
      return;
    }
    startTime = Date.now();
    printLog(`姓名判同 ${nameOne} 和 ${nameTwo}`);
    setLoading(true);
    isSameRareNameService({
      principalId: '2088',
      certType: '',
      sourceName: nameOne,
      targetName: nameTwo,
      serviceContext: null,
    },)
      .then((res) => {
        if (res?.success) {
          let result = '';
          switch (res?.retCode) {
            case 'IS_SAME_RARE_NAME':
              result = '相同';
              break;
            case 'POSSIBLE_SAME_RARE_NAME':
              result = '可能相同';
              break;
            case 'NOT_SAME_RARE_NAME':
              result = '不相同';
              break;
            default:
              result = '出现错误';
              throw new Error(`出现错误：${res?.retCode}`);
          }
          printLog(`判断结果: ${result}`);
          setShowError(false);
          setErrorMsg('');
          setShowSuccess(true);
          setResult(res?.retCode);
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
  };

  React.useEffect(() => {
    printLog('页面加载-用户姓名判同');
  }, []);

  return (
    <div className="pc-container same">
      <div className="wrap">
        <Card
          className="wrap"
          title={
            <div className="card-header">
              <div className="title">
                功能：判断两个生僻字姓名是否相同或可能相同
                <br />
                适用场景：需要校验两个生僻字姓名是否相同的场景，可先用姓名判断圈定生僻字姓名后使用
                <br />
                判同规则：
              </div>
              <ul>
                <li>
                  同一生僻字姓名的unicode正式码字和PUA码字，则相同，如张䶮和张ᵖᵘᵃ
                </li>
                <li>
                  除以上外，同一生僻字姓名的拼音，拆字，unicode码字，pua码字，繁简异体字，则可能相同，如张䶮和张yan，张ᵖᵘᵃ和张龑
                </li>
              </ul>
            </div>
          }
          style={{ width: '100%' }}
        >
          <div className="form">
            <div className="form__wrap">
              <div className="form__inner">
                <div className="form__item one">
                  <div className="form__item-label">姓名一：</div>
                  <div className="form__item-right">
                    <Input
                      value={nameOne}
                      placeholder="请填写名称"
                      onChange={handleNameOneChange}
                      className="form__item-input"
                      status={inputError ? 'error' : ''}
                    />
                    <RareWordsInput onFinish={handleRareWordsOneInput}>
                      <Icon
                        className="form__item-icon keyboard"
                        component={KeyBoardIcon}
                      />
                    </RareWordsInput>
                  </div>
                </div>
                <div className="form__item two">
                  <div className="form__item-label">姓名二：</div>
                  <div className="form__item-right">
                    <Input
                      value={nameTwo}
                      placeholder="请填写名称"
                      onChange={handleNameTwoChange}
                      className="form__item-input"
                      status={inputError ? 'error' : ''}
                    />
                    <RareWordsInput onFinish={handleRareWordsTwoInput}>
                      <Icon
                        className="form__item-icon keyboard"
                        component={KeyBoardIcon}
                      />
                    </RareWordsInput>
                  </div>
                </div>
              </div>
              {showSuccess && (
                <>
                  <img
                    src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAGMAAABICAYAAADiUEtgAAAAAXNSR0IArs4c6QAAAERlWElmTU0AKgAAAAgAAYdpAAQAAAABAAAAGgAAAAAAA6ABAAMAAAABAAEAAKACAAQAAAABAAAAY6ADAAQAAAABAAAASAAAAAALGGAPAAABr0lEQVR4Ae2dQQ6CMBREbcNd4RIqegq4LFj3k7jQONPmmbj5Gmd4jx9XDWXbtsdlwNeyLPfeLquWUo7R3k3CtcebbJrnebjN2Pf9cp5n7W4zeis8ct/u7h5kjEwg6NrYDGQEEQiqwmYgI4hAUBU2AxlBBIKqsBnICCIQVIXNQEYQgaAqbAYygggEVWEzkBFEIKgKm4GMIAJBVdgMZAQRCKrCZiAjiEBQFTYDGUEEgqqwGcgIIhBUhc1ARhCBoCpsBjKCCARVYTOQEUQgqEoJ6vKzKu3k0v04jvVnP/iHH6q1rtMfciwR7Zzis7dDlvxnWG4VHYoMzcUyRYYFuw5FhuZimSLDgl2HIkNzsUyRYcGuQ5GhuVimyLBg16HI0FwsU2RYsOtQZGgulikyLNh1KDI0F8sUGRbsOhQZmotligwLdh2KDM3FMkWGBbsORYbmYpkiw4JdhyJDc7FMkWHBrkORoblYpsiwYNehyNBcLFNkWLDrUGRoLpYpMizYdSgyNBfLFBkW7DoUGZqLZTr1+KCoT6TaqaXb+7DMp++lfT69n0SWVurbPu2a1h4f7PUCR7tVGFHiWzgAAAAASUVORK5CYII="
                    alt="------"
                  />
                  {result === 'IS_SAME_RARE_NAME' && (
                    <div className="result-text">
                      <Icon
                        className="form__item-icon success"
                        component={SuccessIcon}
                      />
                      <span>相同</span>
                    </div>
                  )}
                  {result === 'POSSIBLE_SAME_RARE_NAME' && (
                    <div className="result-text">
                      <Icon
                        className="form__item-icon question"
                        component={QuestionIcon}
                      />
                      <span>可能相同</span>
                    </div>
                  )}
                  {result === 'NOT_SAME_RARE_NAME' && (
                    <div className="result-text">
                      <Icon
                        className="form__item-icon error"
                        component={ErrorIcon}
                      />
                      <span>不相同</span>
                    </div>
                  )}
                </>
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
