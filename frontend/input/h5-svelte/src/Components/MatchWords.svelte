<script>
  import { onMount } from 'svelte';

  export let words = []; // 候选字列表
  export let inputStr = ''; // 输入的值
  export let onClose = () => {};
  export let onSelect = () => {};

  let showMore = false;
  let maxDisplayNum = 0;
  $: hasNoMacthWods = words?.length === 0; // 匹配项为空
  $: isInputValueEmpty = inputStr === ''; // 输入值为空
  $: isWordsOverflow = words?.length > maxDisplayNum; // 候选字是否超过了一行
  $: iconName =
    words.length > maxDisplayNum
      ? showMore
        ? 'icon-arrow-up'
        : 'icon-arrow-right'
      : 'icon-keyboard-hide';

  /**
   * 点击加载更多
   */
  const hanleLookMore = () => {
    if (!isWordsOverflow) {
      if (onClose) onClose();
    }
    showMore = !showMore;
  };

  /**
   * 点击候选字
   */
  const handleWordClick = (event) => {
    const { value } = event.target.dataset;
    if (onSelect) onSelect(value);
  };

  onMount(() => {});
</script>

<div class="rare-words-input__match-words-wrap">
  <div class="rare-words-input__match-words" class:absolute={showMore}>
    <div class="rare-words-input__match-words-items" class:words-all={showMore}>
      <!-- 候选字为空 -->
      {#if hasNoMacthWods}
        <div class="rare-words-input__tips">
          {isInputValueEmpty
            ? '请输入生僻字对应的拼音'
            : '更多生僻字完善中，敬请期待'}
        </div>
      {/if}
      <!-- 候选字不为空 -->
      {#if !hasNoMacthWods}
        {#each words as word}
          <div
            class="rare-words-input__match-words-word rare-words"
            class:word-all={showMore}
          >
            <!-- svelte-ignore a11y-click-events-have-key-events -->
            <div
              class="rare-words-input__match-words-word-inner"
              data-value={word.unicodeChar}
              data-code={word.unicodeCodePoint}
              on:click={handleWordClick}
            >
              {word.unicodeChar}
            </div>
          </div>
        {/each}
      {/if}
    </div>
    <!-- svelte-ignore a11y-click-events-have-key-events -->
    <div
      class="rare-words-input__match-words-right"
      class:overflow={isWordsOverflow && !showMore}
      class:sticky={showMore}
      on:click={hanleLookMore}
    >
      <i class={`iconfont ${iconName}`} />
    </div>
  </div>
</div>

<style>
  .rare-words-input__match-words-wrap {
    min-height: calc(96 * var(--px, 1px));
  }

  .rare-words-input__match-words-wrap::before {
    content: '';
    display: block;
    position: absolute;
    top: calc(-1 * var(--px, 1px));
    left: 0;
    background: #ccc;
    width: 100%;
    height: calc(1 * var(--px, 1px));
    transform: scaleY(0.5);
    transform-origin: 0 0;
    font-family: 'rare-words-font';
  }

  .rare-words-input__match-words {
    display: flex;
    flex-flow: row nowrap;
    align-items: flex-start;
    justify-content: flex-start;
    padding: calc(20 * var(--px, 1px)) calc(24 * var(--px, 1px))
      calc(20 * var(--px, 1px)) calc(8 * var(--px, 1px));
    background: #f5f5f5;
    overflow: hidden;
  }

  .rare-words-input__match-words-items {
    flex: 1;
    display: flex;
    flex-flow: row wrap;
    align-items: center;
    justify-content: flex-start;
    overflow: hidden;
    height: calc(56 * var(--px, 1px));
    white-space: nowrap;
    box-sizing: border-box;
    margin-right: calc(-32 * var(--px, 1px));
    padding-right: calc(42 * var(--px, 1px));
  }

  .rare-words-input__match-words-items.words-all {
    height: auto;
    margin-top: calc(-20 * var(--px, 1px));
    position: relative;
    padding-left: calc(8 * var(--px, 1px));
    padding-right: calc(42 * var(--px, 1px));
  }

  .rare-words-input__match-words-items.words-all::after {
    content: '';
    display: block;
    position: absolute;
    left: calc(-64 * var(--px, 1px));
    bottom: 0;
    background: #ccc;
    width: 100%;
    height: calc(1 * var(--px, 1px));
    transform: scaleY(0.5);
    transform-origin: 0 0;
  }

  .rare-words-input__match-words.absolute {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    box-sizing: border-box;
    z-index: 10000;
    padding-left: 0;
    overflow: auto;
  }

  .rare-words-input__match-words-word {
    color: var(--text-color);
    width: calc(73 * var(--px, 1px));
    padding-left: calc(16 * var(--px, 1px));
    padding-right: calc(16 * var(--px, 1px));
    box-sizing: border-box;
  }

  .rare-words-input__match-words-word.word-all {
    padding-top: calc(20 * var(--px, 1px));
    padding-bottom: calc(20 * var(--px, 1px));
    position: relative;
  }

  .rare-words-input__match-words-word.word-all::after {
    content: '';
    display: block;
    position: absolute;
    left: calc(-20 * var(--px, 1px));
    bottom: 0;
    background: #ccc;
    width: 100%;
    height: calc(1 * var(--px, 1px));
    transform: scaleY(0.5);
    transform-origin: 0 0;
  }

  .rare-words-input__match-words-word-inner {
    font-size: calc(41 * var(--px, 1px));
    line-height: calc(57 * var(--px, 1px));
    user-select: none;
    color: var(--text-color);
  }

  .rare-words-input__match-words-word-inner:active {
    color: var(--active-color);
  }

  .rare-words-input__match-words-word-inner:hover {
    color: var(--active-color);
  }

  .rare-words-input__match-words-right {
    width: calc(48 * var(--px, 1px));
    height: calc(48 * var(--px, 1px));
    display: flex;
    flex-flow: row nowrap;
    align-items: center;
    justify-content: center;
    position: relative;
    margin-top: calc(4 * var(--px, 1px));
    margin-right: calc(40 * var(--px, 1px));
    color: #333;
  }

  .rare-words-input__match-words-right.overflow::before {
    content: '';
    display: block;
    width: calc(12 * var(--px, 1px));
    height: 100%;
    background: linear-gradient(to right, transparent, #d8d8d8);
    position: absolute;
    left: calc(-32 * var(--px, 1px));
  }

  .rare-words-input__match-words-right.sticky {
    position: sticky !important;
    top: 0 !important;
    left: 0 !important;
  }

  .rare-words-input__tips {
    padding-left: calc(8 * var(--px, 1px));
    font-size: calc(28 * var(--px, 1px));
    color: #666;
  }
</style>
