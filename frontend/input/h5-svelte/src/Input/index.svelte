<script>
  import { onMount } from 'svelte';
  import Popup from '../Components/Popup.svelte';
  import Keyboard from '../Components/Keyboard.svelte';
  import MatchWords from '../Components/MatchWords.svelte';
  import ErrorPage from '../Components/ErrorPage.svelte';
  // 调试时候使用
  // import { getWordsData, matchWordsRecommend } from '../../../../utils/dist/index';
  import { getWordsData, matchWordsRecommend } from 'ant-rare-words-utils';
  import '../styles/variable.css';
  import '../styles/iconfont.css';
  import '../styles/iphonex.css';

  export let visible = false; // 是否可见
  export let closeOnMaskClick = true; // 点击蒙层是否关闭
  export let type = 'pinyin'; // 键盘类型
  export let onReady = (fontUrl, fontFace) => {}; // 组件加载好之后的回调
  export let onFinish = (word) => {}; // 组件加载好之后的回调
  export let onError = (err) => {}; // 组件加载失败的回调
  export let onShow = (err) => {}; // 组件打开的回调
  export let onClose = (err) => {}; // 组件关闭的回调

  let inputValue = []; // 当前输入的值
  let wordsData = []; // Z字库数据
  let errorInfo = null; // 错误信息
  let hasFetchData = false; // 是否已经请求过字库数据了
  $: inputStr = Array.isArray(inputValue) ? inputValue.join('') : inputValue;
  $: matchWords = matchWordsRecommend(wordsData, inputStr);
  $: {
    if (visible) {
      if (onShow) onShow();
    } else {
      if (onClose) onClose();
    }
  }

  /**
   * 关闭键盘
   */
  const handleKeyboardClose = () => {
    visible = false;
    inputValue = [];
  };

  /**
   * 写入字符
   * @param char
   */
  const onAddInputChar = (char) => {
    inputValue = [...inputValue, char];
  };

  /**
   * 回退字符
   * @param char
   */
  const onRemoveInputChar = () => {
    inputValue.pop();
    inputValue = [...inputValue];
  };

  /**
   * 选择候选字
   */
  const handleWordSelect = (word) => {
    handleKeyboardClose();
    if (onFinish) onFinish(word);
  };

  /**
   * 加载字库数据
   */
  const fetchWordsData = () => {
    return getWordsData().then((res) => {
      const { data = [], fontFace, fontUrl } = res;
      wordsData = data;
      hasFetchData = true;
      if (onReady) onReady(fontUrl, fontFace);
    });
  };

  onMount(() => {
    if (hasFetchData) return;
    fetchWordsData().catch((err) => {
      if (onError) onError(err);
      errorInfo = err;
    });
  });
</script>

<Popup
  className="rare-word-input"
  {visible}
  {closeOnMaskClick}
  onClose={handleKeyboardClose}
>
  <div class="rare-words-input__inner" slot="content">
    <div class="rare-words-input__input-value" class:hidden={inputStr === ''}>
      {inputStr}
    </div>
    <div class="rare-words-input__header">
      <MatchWords
        {inputStr}
        words={matchWords}
        onClose={handleKeyboardClose}
        onSelect={handleWordSelect}
      />
    </div>
    <div class="rare-words-input__main">
      {#if errorInfo}
        <ErrorPage {...errorInfo} onRetry={fetchWordsData} />
      {/if}
      {#if !errorInfo}
        <Keyboard
          {type}
          onClick={onAddInputChar}
          onDelete={onRemoveInputChar}
        />
      {/if}
    </div>
    <div class="rare-words-input__footer" />
  </div>
</Popup>

<style>
  .rare-words-input__inner {
    position: relative;
  }

  .rare-words-input__header {
    background-color: var(--background);
  }

  .rare-words-input__main {
    background-color: var(--background);
  }

  .rare-words-input__footer {
    display: unset;
  }

  .rare-words-input__input-value {
    position: absolute;
    top: calc(-64 * var(--px, 1px));
    left: 0;
    display: inline-block;
    height: calc(64 * var(--px, 1px));
    line-height: calc(64 * var(--px, 1px));
    border-top-left-radius: calc(8 * var(--px, 1px));
    border-top-right-radius: calc(8 * var(--px, 1px));
    background-color: var(--background);
    padding-left: calc(20 * var(--px, 1px));
    padding-right: calc(20 * var(--px, 1px));
    border: calc(1 * var(--px, 1px)) solid #eee;
    border-bottom: 0;
    letter-spacing: calc(2 * var(--px, 1px));
    box-sizing: border-box;
    font-size: calc(24 * var(--px, 1px));
    color: #333;
  }

  .rare-words-input__input-value.hidden {
    display: none;
  }
</style>
