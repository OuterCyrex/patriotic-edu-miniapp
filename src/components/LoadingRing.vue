<template>
  <view class="loading-page">
    <view class="ring"></view>
    <view class="description">{{ description }}</view>
  </view>
</template>

<script setup lang="ts">
defineProps<{
  description?: string
}>()
</script>

<style lang="scss" scoped>
.loading-page {
  height: 90vh;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  background-color: #ffffff;
}

.ring {
  width: 120px;
  height: 120px;
  border: 8px solid #f3f3f3;
  border-top: 8px solid #ff3d00;
  border-radius: 50%;
  animation: spin 1s linear infinite;
  margin-bottom: 20px;
}

.description {
  font-size: 32px;
  color: #666;
  text-align: center;
}

@keyframes spin {
  0% {
    transform: rotate(0deg);
  }
  100% {
    transform: rotate(360deg);
  }
}
</style>
