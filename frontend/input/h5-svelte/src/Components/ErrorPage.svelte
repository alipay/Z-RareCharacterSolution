<script>
  export let message = '';
  export let onRetry = () => {};

  let loading = false;

  /**
   * 点击重试
   */
  const handleRetry = () => {
    loading = true;
    if (onRetry) {
      onRetry().catch(() => {
        loading = false;
      });
    }
  };
</script>

<div class="rare-words-input__error">
  <img
    class="rare-words-input__error-img"
    src="https://gw.alipayobjects.com/mdn/rms_997765/afts/img/A*i4GUT4z04egAAAAAAAAAAAAAARQnAQ"
    alt="错误"
  />
  <p class="rare-words-input__error-desc">
    {message || '人气大爆发'}
  </p>
  <button
    class="rare-words-input__error-btn"
    class:loading
    on:click={handleRetry}
  >
    {loading ? '加载中' : '重试'}
  </button>
</div>

<style>
  .rare-words-input__error {
    background: var(--background);
    padding-top: calc(30 * var(--px, 1px));
    padding-bottom: calc(30 * var(--px, 1px));
  }

  .rare-words-input__error-img {
    display: block;
    width: calc(180 * var(--px, 1px));
    margin: 0 auto;
  }

  .rare-words-input__error-desc {
    text-align: center;
    color: #999;
    font-size: calc(26 * var(--px, 1px));
  }

  .rare-words-input__error-btn {
    display: block;
    margin: 0 auto;
    width: auto;
    padding: calc(6 * var(--px, 1px)) calc(24 * var(--px, 1px));
    border-color: #1677ff;
    background-color: #1677ff;
    color: #fff;
    font-size: calc(30 * var(--px, 1px));
    border: 1px solid #1677ff;
    outline: none;
    border-radius: calc(8 * var(--px, 1px));
    margin-top: calc(15 * var(--px, 1px));
  }

  .rare-words-input__error-btn:active {
    background: #145ec6;
  }

  .rare-words-input__error-btn.loading {
    opacity: 0.4;
  }
</style>
