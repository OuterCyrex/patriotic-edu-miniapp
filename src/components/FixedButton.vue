<template>
  <view class="fixed-button-container">
    <view class="fixed-button" @click="emits('click')">
      <image class="fixed-button-icon" :src="icon"></image>
    </view>
  </view>
</template>

<script setup lang="ts">
defineProps<{
  icon: string;
}>()
const emits = defineEmits(['click'])
</script>

<style lang="scss">
.fixed-button-container {
  height: 120px;
  width: 120px;
  background-color: #71bcff;
  border-radius: 50%;
  z-index: 1000;
  position: fixed;
  top: calc(100vh - 200px);
  left: calc(100vw - 160px);
  display: flex;
  align-items: center;
  justify-content: center;
}
.fixed-button-icon {
  height: 60px;
  width: 60px;
  object-fit: contain;
}
</style>
