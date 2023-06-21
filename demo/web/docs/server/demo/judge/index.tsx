import React from 'react';
import { Input, Button, Card, Alert } from 'antd';
import Icon from '@ant-design/icons';
import RareWordsInput from '../../../../../../frontend/input/pc-react/src/index';
import { isRareNameService } from '../../../../service';
import { KeyBoardIcon } from '../../../../components/icons';
import './index.less';

export default () => {
  const [logs, setLogs] = React.useState([]);
  const [name, setName] = React.useState('');
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
  const handleRareWordsInput = (value) => {
    const newValue = name + value;
    setName(newValue);
  };

  /**
   * 姓名发生变化
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
   * 表单填写完成处理事件
   */
  const onSubmit = () => {
    if (!name) {
      setInputError(true);
      return;
    }
    startTime = Date.now();
    printLog(`输入姓名 ${name}`);
    setLoading(true);
    isRareNameService({
      principalId: '2088',
      certType: '',
      name,
      serviceContext: null,
    })
      .then((res) => {
        if (res?.success) {
          let result = '';
          switch (res?.retCode) {
            case 'IS_RARE_NAME':
              result = '是';
              break;
            case 'POSSIBLE_RARE_NAME':
              result = '可能是';
              break;
            case 'NOT_RARE_NAME':
              result = '不是';
              break;
            default:
              result = '出现错误';
              throw new Error(`出现错误：${res?.retCode}`);
          }
          printLog(`判断结果: ${result}`);
          setShowError(false);
          setErrorMsg('');
          setShowSuccess(true);
          setResult(result);
          return;
        }
        throw new Error(res?.message || `出现错误：${res?.retCode}`);
      })
      .catch((err) => {
        const message = err.toString() || '服务器繁忙';
        printLog(`出现错误: ${message}`);
        printLog(message);
        setShowSuccess(false);
        setShowError(true);
        setErrorMsg(message);
      })
      .finally(() => {
        setLoading(false);
      });
  };

  React.useEffect(() => {
    printLog('页面加载-判断用户姓名是否包含生僻字');
  }, []);

  return (
    <div className="judge-container">
      <div className="wrap">
        <Card
          className="wrap"
          title={
            <div className="card-header">
              <div className="title">
                功能：判断用户姓名是否包含生僻字
                <br />
                使用场景：圈定生僻字人群，做生僻字业务处理，不影响业务主流程
                <br />
                判断规则：
              </div>
              <ul>
                <li>姓名中包含GBK字符集以外的字符，如张䶮</li>
                <li>姓名中包含拼音，如张yan</li>
                <li>姓名中包含拆字，如张龙天</li>
                <li>姓名中包含问号，如张？</li>
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
                  <Icon className="form__item-icon" component={KeyBoardIcon} />
                </RareWordsInput>
              </div>
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
            {showSuccess && <Alert className="result-tips" message={`判断结果：${result}`} type="success" showIcon />}
            {showError && <Alert className="result-tips" message={errorMsg} type="error" showIcon />}
          </div>

          <br />
          <div className="logs">
            <p className="title">操作日志</p>
            {logs.map((log, index) => (
              <p key={`log-${index}`} className="log">{`${log.msg}，耗时 ${log.time} ms`}</p>
            ))}
          </div>
        </Card>
      </div>
    </div>
  );
};
