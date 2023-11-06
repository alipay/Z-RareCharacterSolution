<script>
  import { PINYIN_MAP } from '../contants/index';

  export let type = ''; // 拼音(pinyin)，拆字(split)
  export let visible = false;
  export let closeOnMaskClick = false;
  export let onClose = () => {};
  export let onClick = () => {};
  export let onDelete = () => {};

  /**
   * 关闭弹窗
   */
  // const handleClose = () => {
  //   visible = false;
  //   onClose();
  // };

  /**
   * 点击键盘按键
   */
  const handleClick = (e) => {
    const { value } = e.target.dataset;
    onClick(value);
  };

  /**
   * 点击蒙层
   */
  const handleMaskClick = (event) => {
    if (closeOnMaskClick) {
      visible = false;
      onClose();
    }
  };
</script>

<div class="rare-words-input__keyboard">
  {#if type === 'pinyin'}
    <div class="rare-words-input__pinyin">
      {#each PINYIN_MAP as row, rowIndex}
        <div class="rare-words-input__pinyin-row">
          {#each row as item, i (item.key)}
            <!-- svelte-ignore a11y-click-events-have-key-events -->
            <div
              class={`rare-words-input__pinyin-key${
                item.extraClassName ? ` ${item.extraClassName}` : ''
              }`}
              data-value={item.value}
              on:click={handleClick}
            >
              {item.label}
            </div>
          {/each}
          {#if rowIndex === PINYIN_MAP.length - 1}
            <!-- svelte-ignore a11y-click-events-have-key-events -->
            <div
              key="delete"
              class="rare-words-input__pinyin-key delete-btn"
              on:click={onDelete}
            >
              <i class="iconfont icon-delete" />
            </div>
          {/if}
        </div>
      {/each}
    </div>
  {/if}
</div>

<style>
  .rare-words-input__keyboard {
    position: relative;
  }

  .rare-words-input__pinyin {
    background: #f5f5f5;
    padding-top: calc(16 * var(--px, 1px));
    padding-bottom: calc(16 * var(--px, 1px));
  }

  .rare-words-input__pinyin-row {
    display: flex;
    flex-flow: row nowrap;
    justify-content: center;
    background: #f5f5f5;
    margin-bottom: calc(16 * var(--px, 1px));
  }

  .rare-words-input__pinyin-key {
    position: relative;
    display: flex;
    align-items: center;
    justify-content: center;
    height: calc(88 * var(--px, 1px));
    width: calc(61 * var(--px, 1px));
    background: #fff;
    font-size: calc(36 * var(--px, 1px));
    color: var(--text-color);
    box-sizing: border-box;
    border-radius: calc(8 * var(--px, 1px));
    margin-right: calc(12 * var(--px, 1px));
    user-select: none;
  }

  .rare-words-input__pinyin-key:active {
    background: #1677ff;
    color: #fff;
  }

  .rare-words-input__pinyin-key:last-child {
    margin-right: 0;
  }

  .rare-words-input__pinyin-key.delete-btn {
    width: calc(134 * var(--px, 1px));
    height: calc(88 * var(--px, 1px));
  }
</style>
