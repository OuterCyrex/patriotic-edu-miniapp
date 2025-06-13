<template>
  <view class="page">
    <!-- 顶部欢迎区 -->
    <view class="hero-header">
      <image class="avatar" src="https://i.ibb.co/NgPDVbry/e6bd4ccfdbc4b17d3af6b739a0c17a34-removebg-preview.png" mode="aspectFit" />
      <view class="title">欢迎来到红小星国防学习</view>
      <view class="subtitle">趣味互动学知识，红小星陪你一起成长！</view>
      <nut-space />
      <view class="totalStars-container" v-if="!!userInfo">
        <text class="totalStarts">当前总星星数：</text>
        ⭐ × {{userInfo.totalStars}}
      </view>
    </view>

    <!-- 功能按钮区域 -->
    <view class="feature-section">
      <view class="feature-card" @click="toQuestion">
        <image class="feature-icon" src="https://img.icons8.com/?size=100&id=VJCVutzKX9zq&format=png" />
        <view class="feature-title">知识闯关</view>
        <view class="feature-desc">每日10道国防题，答对解锁勋章</view>
      </view>

      <view class="feature-card" @click="goToSceneQA">
        <image class="feature-icon" src="https://img.icons8.com/?size=100&id=2juNVZZCBjKu&format=png" />
        <view class="feature-title">情景问答</view>
        <view class="feature-desc">模拟国防场景，学习正确应对方法</view>
      </view>

      <view class="feature-card" @click="goToChat">
        <image class="feature-icon" src="https://img.icons8.com/?size=100&id=80457&format=png&color=000000" />
        <view class="feature-title">红小星对话</view>
        <view class="feature-desc">和红小星实时互动交流</view>
      </view>
    </view>
  </view>
</template>

<script setup lang="ts">
// === import ===
import Taro, {useDidShow} from '@tarojs/taro'
import {UserInfo} from "@/types/forms/user";
import {ref} from "vue";
import {user} from "@/API";

// === define ===
definePageConfig({
  navigationBarTitleText: '红星青年派'
})

// constants
const userInfo = ref<UserInfo | null>(null)

// === methods ===
function toQuestion() {
  Taro.navigateTo({ url: '/pages/pal/question' })
}

function goToSceneQA() {
  Taro.navigateTo({ url: '/pages/pal/scenario' })
}

function goToChat() {
  Taro.navigateTo({ url: '/pages/pal/chat' })
}

// === hooks ===
useDidShow(() => {
  user.GetUserInfo().then(info => {userInfo.value = info})
})
</script>

<style lang="scss">
.page {
  background-color: #ffffff;
  padding: 32rpx;
  min-height: 100vh;
  box-sizing: border-box;
}

.totalStarts {
  color: #3a3a3a;
}

.hero-header {
  text-align: center;
  margin-bottom: 40rpx;
  .avatar {
    width: 220rpx;
    height: 220rpx;
    margin: 0 auto 20rpx;
  }
  .title {
    font-size: 40rpx;
    font-weight: bold;
    color: #d0021b;
    margin-bottom: 12rpx;
  }
  .subtitle {
    font-size: 28rpx;
    color: #666;
  }
}

.feature-section {
  display: flex;
  flex-direction: column;
  gap: 24rpx;
  margin-bottom: 60rpx;

  .feature-card {
    background-color: #fff8f8;
    border: 2rpx solid #f0c0c0;
    border-radius: 24rpx;
    padding: 28rpx;
    display: flex;
    flex-direction: column;
    align-items: center;
    text-align: center;
    box-shadow: 0 6rpx 12rpx rgba(0, 0, 0, 0.05);
    cursor: pointer;
    transition: transform 0.2s ease;

    &:active {
      transform: scale(0.97);
    }

    .feature-icon {
      width: 72rpx;
      height: 72rpx;
      margin-bottom: 16rpx;
    }
    .feature-title {
      font-size: 34rpx;
      font-weight: 600;
      color: #b22222;
      margin-bottom: 8rpx;
    }
    .feature-desc {
      font-size: 26rpx;
      color: #444;
    }
  }
}
</style>
