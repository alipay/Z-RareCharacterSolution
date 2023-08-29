import React from 'react';
import { Input, Button, Card, Alert, Tag, Empty, Tooltip } from 'antd';
import Icon, { CopyOutlined } from '@ant-design/icons';
import { CopyToClipboard } from 'react-copy-to-clipboard';
import { associateRareNameService } from '../../../../service';
import { WORD_TYPE_TO_COLOR } from '../../../../constants';
import { KeyBoardIcon } from '../../../../components/icons';
import RareWordsInput from '../../../../../../frontend/input/pc-react/src/index';
import './index.less';

export default () => {
  const [logs, setLogs] = React.useState<{msg: string, time: number}[]>([]);
  const [name, setName] = React.useState('');
  const [loading, setLoading] = React.useState(false);
  const [inputError, setInputError] = React.useState(false);
  const [showSuccess, setShowSuccess] = React.useState(false);
  const [result, setResult] = React.useState<any>([]);
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
    associateRareNameService({
      principalId: '2088',
      name,
      serviceContext: null,
    })
      .then((res) => {
        if (res?.success) {
          const result = res?.rareNameInfos || [];
          printLog(`判断结果: ${result?.map((item) => item.name).join('、')}`);
          setShowError(false);
          setErrorMsg('');
          setShowSuccess(true);
          setResult(result);
          return;
        }
        throw new Error(res?.message || `出现错误：${res?.retCode}`);
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
    printLog('页面加载-姓名联想');
  }, []);

  return (
    <div className="pc-container recommend">
      <div className="wrap">
        <Card
          className="wrap"
          title={
            <div className="card-header">
              <div className="title">
                功能：给定一个生僻字姓名，可能联想出其他样式的生僻字姓名
                <br />
                使用场景：当需要和外部机构（社保公积金、银行）比对姓名失败时，若姓名含生僻字，则可使用联想功能推荐给用户选择候选姓名重试或系统自动重试
                <br />
                联想规则
              </div>
              <ul>
                <li>输入unicode字姓名（如张䶮），返回各种拼音姓名（如张yan，张(yan)，张YAN，张YAN3等）</li>
                <li>输入PUA姓名（如张ᵖᵘᵃ），返回unicode码字姓名，各种拼音姓名（如张yan，张(yan)，张YAN，张YAN3等）</li>
                <li>输入拼音姓名（如张YAN），返回各种拼音姓名（如张yan，张YAN，张(yan)等）</li>
                <li>输入拆字姓名（如刘龙天、刘（龙天）），返回unicode码字姓名</li>
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
            {showError && <Alert className="result-tips" message={errorMsg} type="error" showIcon />}
          </div>

          {showSuccess && (
            <div className="recommend">
              <p className="title">推荐结果</p>
              <div className="recommend__item-wrap">
                {result?.length === 0 && <Empty />}
                {result?.length > 0 &&
                  result.map((item, index) => (
                    <Card
                      key={`item-${index}`}
                      style={{ width: 220 }}
                      actions={[
                        // <ShareAltOutlined key="edit" />,
                        <CopyToClipboard text={item.name}>
                          <Tooltip placement="bottom" title="Copied!">
                            <CopyOutlined key="copy" />
                          </Tooltip>
                        </CopyToClipboard>,
                      ]}
                    >
                      <div className="recommend__item">
                        <div className="recommend__item-text">{item.name}</div>
                        <Tag className="recommend__item-tag" color={WORD_TYPE_TO_COLOR[item.remarks]}>
                          {item.remarks}
                        </Tag>
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
              <p key={`log-${index}`} className="log">{`${log.msg}，耗时 ${log.time} ms`}</p>
            ))}
          </div>
        </Card>
      </div>
    </div>
  );
};
