<template>
  <view class="card" @click="emit('click')" v-if="props.type === 1">
    <view class="card-left">
      <view class="card-title">{{ props.title }}</view>
      <view class="card-subtitle">{{ props.subtitle }}</view>
    </view>
    <view class="card-right">
      <image :src="icon" class="card-icon" mode="aspectFit" />
    </view>
  </view>
  <view class="card-2" @click="emit('click')"  v-if="props.type === 2">
    <view class="feature-card">
      <image :src="props.icon" class="feature-icon" />
      <view class="feature-title">{{ props.title }}</view>
      <view class="feature-desc">{{ props.subtitle }}</view>
    </view>
  </view>
</template>


<script setup lang="ts">
const props = defineProps<{
  title: string
  subtitle: string
  icon: string
  type: number
}>()
const emit = defineEmits(['click'])
</script>

<style lang="scss">
.feature-card {
  width: 240rpx;
  background: white;
  border-radius: 10px;
  padding: 15px;
  box-shadow: 0 2px 6px rgba(210, 2, 27, 0.2);
  text-align: center;
}

.feature-icon {
  width: 60px;
  height: 60rpx;
  margin-bottom: 10px;
}

.feature-title {
  font-weight: 600;
  font-size: 32rpx;
  margin-bottom: 6px;
  color: #b22222;
}

.feature-desc {
  font-size: 22rpx;
  color: #900000;
}

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
