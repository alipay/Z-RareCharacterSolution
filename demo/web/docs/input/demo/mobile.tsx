import React from 'react';
import RareWordsInput from '../../../../../frontend/input/h5-react/src/index';
import { FontLoader } from '../../../../../frontend/utils';
import './mobile.less';

export default () => {
  const [logs, setLogs] = React.useState([]);
  const [value, setValue] = React.useState('');
  const [visible, setvisible] = React.useState(false);
  const startTime = Date.now();

  // 记录日志
  const printLog = (msg) => {
    setLogs((logs) => [...logs, { msg, time: Date.now() - startTime }]);
  };

  /**
   * 打开生僻字输入组件
   */
  const handleOpen = () => {
    setvisible(true);
  };

  /**
   * 监听输入事件
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
    <div className="h5-container mobile">
      <p className="title">姓名</p>
      <div className="wrap">
        <input
          className="input"
          type="text"
          value={value}
          onChange={handleChange}
        />
        <span className="tips" onClick={handleOpen}>
          录入生僻字
        </span>
        <RareWordsInput
          visible={visible}
          type="pinyin"
          onShow={() => {
            printLog('生僻字输入组件打开');
          }}
          onClose={() => {
            setvisible(false);
            printLog('生僻字输入组件关闭');
          }}
          onFinish={(curValue) => {
            printLog(`生僻字输入组件输入完成 ${curValue}`);
            setValue((value) => value + curValue);
          }}
        />
      </div>

      <p className="title">操作日志</p>
      <div className="logs">
        {logs.map((log, index) => (
          <p
            key={`log-${index}`}
            className="log"
          >{`${log.msg} 耗时 ${log.time} ms`}</p>
        ))}
      </div>
    </div>
  );
};
