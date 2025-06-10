<template>
  <view
    class="option-button"
    :class="{ active }"
    @click="emits('selected')"
  >
    <text class="label">{{ label }}.</text>
    <text class="content">{{ content }}</text>
  </view>
</template>

<script setup lang="ts">
defineProps<{
  active?: boolean
  label: string
  content: string
}>()

const emits = defineEmits(['selected'])
</script>

<style lang="scss">
.option-button {
  display: flex;
  align-items: center;
  padding: 24rpx 32rpx;
  margin: 20rpx 0;
  border: 1px solid #ccc;
  border-radius: 16rpx;
  background-color: #fff;
  transition: all 0.2s ease;

  .label {
    font-weight: bold;
    font-size: 30rpx;
    color: #333;
  }

  .content {
    margin-left: 20rpx;
    font-size: 30rpx;
    color: #444;
  }

  &:active {
    background-color: #f5f5f5;
  }

  &.active {
    border-color: #d7000f;
    background-color: #fff1f1;

    .label,
    .content {
      color: #d7000f;
      font-weight: bold;
    }
  }
}
</style>

