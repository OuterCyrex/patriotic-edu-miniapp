<template>
  <view class="article-card" @click="emit('click')">
    <image :src="cover" class="cover-image" mode="aspectFill" />

    <view class="content">
      <view class="title">{{ props.title }}</view>
      <view class="summary">{{ props.summary }}</view>
      <view class="meta">
        <text class="views" v-if="views">👁 {{ props.views }}</text>
      </view>
    </view>
  </view>
</template>

<script setup lang="ts">
const props = defineProps<{
  cover: string
  title: string
  summary: string
  views?: number | string
}>()
const emit = defineEmits(['click'])
</script>

<style lang="scss">
.article-card {
  display: flex;
  background-color: #fff;
  padding: 20rpx;
  border-radius: 16rpx;
  box-shadow: 0 4rpx 12rpx rgba(0, 0, 0, 0.05);
  margin: 20rpx 0;
  overflow: hidden;
  box-sizing: border-box;
}

.cover-image {
  width: 200rpx;
  height: 140rpx;
  border-radius: 8rpx;
  flex-shrink: 0;
  object-fit: cover;
}

.content {
  flex: 1;
  margin-left: 20rpx;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  overflow: hidden;
  box-sizing: border-box;
}

.title {
  font-size: 30rpx;
  font-weight: bold;
  color: #111;
  line-height: 40rpx;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.summary {
  font-size: 26rpx;
  color: #666;
  margin: 10rpx 0;
  line-height: 36rpx;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.meta {
  display: flex;
  justify-content: space-between;
  font-size: 24rpx;
  color: #999;
  white-space: nowrap;
}
</style>

