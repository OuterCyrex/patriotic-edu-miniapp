<template>
  <view class="card">
    <view class="card-left">
      <view class="card-title">{{ props.title }}</view>
      <view class="card-subtitle">{{ props.subtitle }}</view>
    </view>
    <view class="card-right">
      <image :src="icon" class="card-icon" mode="aspectFit" />
    </view>
  </view>
</template>


<script setup lang="ts">
const props = defineProps<{
  title: string
  subtitle: string
  icon: string
}>()
</script>

<style lang="scss">
.card {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background: linear-gradient(135deg, #ffe8df, #fff);
  border-radius: 20rpx;
  padding: 24rpx;
  width: 300rpx;
  height: 120rpx;
  box-shadow: 0 8rpx 16rpx rgba(0, 0, 0, 0.05);
}

.card-left {
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.card-title {
  font-size: 32rpx;
  font-weight: bold;
  color: #111;
}

.card-subtitle {
  font-size: 24rpx;
  color: #999;
  margin-top: 8rpx;
}

.card-right {
  width: 64rpx;
  height: 64rpx;
  background-color: #fff3e8;
  border-radius: 16rpx;
  display: flex;
  justify-content: center;
  align-items: center;
}

.card-icon {
  width: 80rpx;
  height: 80rpx;
}
</style>
