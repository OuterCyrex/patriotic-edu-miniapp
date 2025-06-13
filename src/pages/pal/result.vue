<template>
  <view class="result-container">
    <view class="result-card">
      <view class="result-title">答题结果</view>

      <view class="result-item">
        <text class="label">正确题数：</text>
        <text :class="acNumber >= 5 ? 'green value' : 'red'">{{ acNumber }} / 10</text>
      </view>

      <view class="result-item">
        <text class="label">评价建议：</text>
        <text class="value">{{ commentText }}</text>
      </view>

      <view class="result-item">
        <text class="label">获得星星：</text>
        <view class="stars">
          <view v-for="n in starNumber" :key="n">
            <image class="star-icon" src="https://img.icons8.com/?size=100&id=8ggStxqyboK5&format=png&color=000000" />
          </view>
          <view  v-if="starNumber === 0">
            <text class="no-star">暂无</text>
          </view>
        </view>
      </view>
    </view>
  </view>
</template>

<script setup lang="ts">
// === import ===
import Taro, { useLoad } from "@tarojs/taro";
import { ref } from "vue";
import {UserInfo} from "@/types/forms/user";
import {user} from "@/API";

// === define ===
definePageConfig({
  navigationBarTitleText: "答题结果"
})

// === constants ===
const acNumber = ref<number>(0)
const commentText = ref<string>('')
const starNumber = ref<number>(0)
const userInfo = ref<UserInfo | null>(null)

// === hooks ===
useLoad((options) => {
  acNumber.value = Number(options.ac)
  commentText.value = options.comment
  starNumber.value = Number(options.stars)

  user.GetUserInfo().then(resp => {userInfo.value = resp})
  userInfo.value!.totalStars += starNumber.value
  Taro.setStorage({key: "user", data: userInfo})
})
</script>

<style lang="scss">
.result-container {
  padding: 40rpx 30rpx;
  display: flex;
  justify-content: center;
  align-items: center;
}

.result-card {
  background-color: #ffffff;
  border-radius: 24rpx;
  box-shadow: 0 8rpx 20rpx rgba(0, 0, 0, 0.05);
  padding: 40rpx;
  width: 100%;
}

.result-title {
  font-size: 40rpx;
  font-weight: bold;
  text-align: center;
  margin-bottom: 40rpx;
  color: #b51e1e;
}

.result-item {
  margin-bottom: 32rpx;
  display: flex;
  align-items: center;

  .red {
    color: red;
  }
  .green {
    color: green;
  }
}

.label {
  font-size: 30rpx;
  color: #666;
  width: 160rpx;
}

.value {
  font-size: 30rpx;
  font-weight: 500;
  color: #333;
  flex: 1;
  word-break: break-word;
}

.stars {
  display: flex;
  align-items: center;
  flex-wrap: wrap;
}

.star-icon {
  width: 40rpx;
  height: 40rpx;
  margin-right: 10rpx;
}

.no-star {
  font-size: 28rpx;
  color: #aaa;
}
</style>

