<script>
  import { slide, fade } from 'svelte/transition';

  export let className = '';
  export let visible = false;
  export let closeOnMaskClick = false;
  export let onClose = () => {};

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

{#if visible}
  <div class={`rare-words-input__popup${className ? ` ${className}` : ''}`}>
    <!-- svelte-ignore a11y-click-events-have-key-events -->
    <div
      class="rare-words-input__popup-mask"
      transition:fade={{ duration: 300 }}
      on:click={handleMaskClick}
    />
    <div
      class="rare-words-input__popup-content"
      transition:slide={{ y: [1000, 0], duration: 300 }}
    >
      <slot name="content" />
    </div>
  </div>
{/if}

<style>
  .rare-words-input__popup {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    z-index: 1000;
  }

  .rare-words-input__popup-mask {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.6);
    z-index: 1001;
  }

  .rare-words-input__popup-content {
    position: fixed;
    z-index: 1002;
    width: 100%;
    bottom: 0;
    left: 0;
    box-sizing: border-box;
    background-color: #fff;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
  }
</style>
