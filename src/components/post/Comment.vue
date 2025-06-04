<template>
  <view class="comment-container">
    <view class="comment-title">
      <text>评论 {{id}}</text>
    </view>
    <view class="comment-content">
      <text>{{content}}</text>
    </view>
  </view>
</template>

<script setup lang="ts">
defineProps<{
  id: number
  content: string
}>()
</script>

<style lang="scss">
.comment-container {
  background-color: #fff5f5;
  border-left: solid 4px #8f2c2c;
  padding: 20px 40px;
}
.comment-title {
  font-size: 32rpx;
  font-weight: bold;
  margin-bottom: 6px;
}
.comment-content {
  padding: 6px 0;
}
</style>
