import React from 'react';
import { FontLoader } from 'ant-rare-words-utils';
import './index.less';

export default () => {
  const [logs, setLogs] = React.useState<{ msg: string; time: number }[]>([]);
  const [loaded, setLoaded] = React.useState<boolean>(false);
  let startTime = Date.now();

  // 记录日志
  const printLog = (msg, showTime = true) => {
    setLogs((logs) => [
      ...logs,
      { msg, time: showTime ? Date.now() - startTime : 0 },
    ]);
  };

  const handleClick = () => {
    startTime = Date.now();
    printLog('开始加载字体');
    new FontLoader({
      fontName: 'rare-words-font',
      onSuccess: (fontFace) => {
        setLoaded(true);
        printLog('字体加载完成');
        const element = document.querySelector('.container.display .text') as HTMLElement;
        const curBodyFontAttr = getComputedStyle(element).fontFamily;
        const newBodyFontAttr = `${curBodyFontAttr}, '${fontFace.family}'`;
        element.style.fontFamily = newBodyFontAttr;
      },
      onError: (err) => {
        printLog('字体加载失败 - ' + err.message);
      },
    });
  };

  return (
    <div className="container display">
      <p className="title">没有加载字体的模块</p>
      <p className={`text ${loaded ? '' : 'old'}`}>
        𠡠 𡌶 𡛓 𡝗 𡵓 𡷫 𡸃 𡹇 𢒉 𣑮 𣛮 𣲗 𣲘 𤆬 𤆵 𤊟 𤎌 𤧞 𤧟 𤨾 𤩽 𤯥 𤰉 𥖄
        𨭉 𨰻 𨱇 𨱎 𨱑 𩇕 𪜔 𪠽 𪨧 𪨰 𪨶 𪩘 𪲔 𪸕 𪻐 𪿖 𫍽 𫓩 𫔭 𫖮 𫖯 𫗇 𫘛 𫘧
        𫝫 𫞡 𫞦 𫟹 𫢒 𫢸 𫰍 𫰡 𫲦 𫵳 𫵷 𬀩 𬀪 𬇕 𬇙 𬇹 𬈜 𬊈 𬊍 𬊤 𬍑 𬍛 𬍡 𬍤
        𬎆 𬘬 𬜬 𬞟 𬬬 𬬱 𬬲 𬬸 𬬺 𬬿 𬭎 𬭚 𬭣 𬮤 𬮦 𬮿 𬱖 𬱟 𬱵 𬳳 𬴀 𬴂 𮣳 𮧵
        𮭦 𰎐 𰎝 𰎠 𰙕 𰛥 𰞔 𰤕 𰬫 𰵝 𰵞 𰵧 𰽚 𰽥 𰽦 𰾫 𱂐 𱃚 𱉱 𪛞 𱎞 𱔐 𱣉 𱮺
        𱯍 𲇷 𲊿       
      </p>
      <button onClick={handleClick}>开始加载生僻字字体</button>
      <p className="title">字体加载日志</p>
      <div className="logs">
        {logs.map((log, index) => (
          <p key={`log-${index}`} className="log">{`${log.msg}${
            log.time ? `耗时 ${log.time} ms` : ''
          }`}</p>
        ))}
      </div>
    </div>
  );
};
