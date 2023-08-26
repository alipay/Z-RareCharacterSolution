import React from 'react';
import RareWordsInput from '../../../../../frontend/input/pc-react/src/index';
import { FontLoader } from '../../../../../frontend/utils';
import './desktop.less';

export default () => {
  const [logs, setLogs] = React.useState<{msg: string, time: number}[]>([]);
  const [value, setValue] = React.useState('');
  let startTime = Date.now();

  // 记录日志
  const printLog = (msg) => {
    setLogs((logs) => [...logs, { msg, time: Date.now() - startTime }]);
  };

  /**
   * 组件打开事件
   */
  const handleOpen = () => {
    startTime = Date.now();
    printLog('开始显示组件');
  };

  /**
   * 生僻字输入完成
   */
  const handleChange = (e) => {
    const value = e.target.value;
    setValue(value);
  };

  // 开始加载字体
  React.useEffect(() => {
    printLog('开始加载组件');
    new FontLoader({
      // 字体文件放置在demo/web/public/fonts下，可以改成自定义的在线字体地址
      fontSrc: window.location.origin + '/fonts/RareWordsFonts.ttf',
      fontName: 'RareWordsFont',
      onError: (err) => {
        printLog('字体加载失败 - ' + err.message);
      },
    });
  }, []);

  return (
    <div className="pc-container input">
      <div className="wrap">
        <p className="title">姓名</p>
        <div className="form">
          <input
            className="input"
            type="text"
            value={value}
            onChange={handleChange}
          />
          <RareWordsInput
            onFinish={(curValue) => {
              printLog(`生僻字输入组件输入完成 ${curValue}`);
              setValue((value) => value + curValue);
            }}
          >
            <span className="tips" onClick={handleOpen}>
              录入生僻字
            </span>
          </RareWordsInput>
        </div>
        <br />
        <p className="title">操作日志</p>
        <div className="logs">
          {logs.map((log, index) => (
            <p key={`log-${index}`} className="log">{`${log.msg}${
              log.time ? `耗时 ${log.time} ms` : ''
            }`}</p>
          ))}
        </div>
      </div>
    </div>
  );
};
