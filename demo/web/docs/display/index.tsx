import React from 'react';
import { FontLoader } from 'ant-rare-words-utils';
import './index.less';

export default () => {
  const [logs, setLogs] = React.useState<{ msg: string; time: number }[]>([]);
  const [loaded, setLoaded] = React.useState<boolean>(false);
  let startTime = Date.now();

  // 记录日志
  const printLog = (msg, showTime = true) => {
    setLogs((logs) => [...logs, { msg, time: showTime ? Date.now() - startTime : 0 }]);
  };

  const handleClick = () => {
    if (loaded) {
      setLoaded(false);
    } else {
      startTime = Date.now();
      printLog('开始加载字体', false);
      new FontLoader({
        fontName: 'rare-words-font',
        onSuccess: () => {
          setLoaded(true);
          printLog('字体加载完成');
        },
        onError: (err) => {
          printLog('字体加载失败 - ' + err.message, false);
        },
      });
    }
  };

  return (
    <div className="container display">
      <p className="title">{loaded ? '已经' : '没有'}加载字体的模块</p>
      <p className={`text ${loaded ? '' : 'old'}`}>
        𡹇 𤊟 𨱑 𣲘 㗊 𬍡 䴙 𰞔 䒟 𥖄 𪲔 𢒉 𤧞 𡌶 𰛥 𤨾 璟 䄻 𰬫 𰽚 𫖮 㷧 𬬸 䵍 𤆬 㚢 堃 䴔 㐵 䝼 𬎆 𪸕 𬱖 𫢒 
        𬱟 𬴂 䦷 㛠 𬮦 𤰉 𬈜 𱯍 𪩘 㑇 㻌 琤 𱃚 𠅤 𤧟 𫵳 𫞦 𤆵 㑳 㛃 㞧 𬊍 𬇙 𫵷 𬱵 𬮿 𪻐 𫞡 𬍑 玥 𲊿 𫰡 𪠽 𣲗 彧 
        䶮 𬞟 𬬿 𫢸 㳠 𰎠  𬀩 𮭦 㻑 䫺 𰵧 𮣳 䴖 䴓 𨱇 𰵝 𪿖  䜣 焜 㻿 𤩽 𰎝 㛇 𱔐 禤 𨱎 𪨶 𬳳 𬬺 䦶 𰾫 𬭣 𬭚 
        𡸃 𡵓 𡷫 𫟹 𱪿 滢 𡛓 𠇔 𲇷 𫝫 𬘬 𫰍 垚 𬍛 𱮺 𫖯 𪜔 𡝗 㐱 䁎 煇  㶲 𬜬 𫘛 𠊟 𱂐 𰵞 𰤕 𪨧 𫔭 𣑮 㧏 𨭉 𬊈 㶥
        𣛮 㭎 晔 旻 𤎌 𬮤 𬬲 㻴 𫍽 𮧵  㞭 㙟 燚 𨰻 𫲦 竑 𰽦 䳟 𫘧 𬀪 樑 㢨 𬭎 㺭 𱣉 𬬬  𠡠 𩇕 㧑 䓫 㮾 
        𬍤 冄 𪛞 珣 𬬱 㳇 𬴀 𱎞 𬇕 𪨰 𰽥 𬊤  𰎐 𬇹 𫗇 𲂎 𡌴
      </p>
      <button onClick={handleClick}>{loaded ? '取消加载' : '开始加载'}生僻字字体</button>
      <p className="title">字体加载日志</p>
      <div className="logs">
        {logs.map((log, index) => (
          <p key={`log-${index}`} className="log">{`${log.msg}${log.time ? `，耗时 ${log.time} ms` : ''}`}</p>
        ))}
      </div>
    </div>
  );
};
