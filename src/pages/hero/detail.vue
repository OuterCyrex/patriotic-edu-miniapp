<template>
  <view class="container" v-if="!!heroDetail">
    <image class="avatar" :src="heroDetail.avatarUrl" mode="aspectFill" />

    <view class="info">
      <view class="name">{{ heroDetail.name }}</view>
      <view class="title">称号：{{ heroDetail.title }}</view>
      <view class="period">所属时期：{{ heroDetail.periodYears }}</view>
      <view class="sacrifice">牺牲年份：{{ heroDetail.sacrificeYear }}</view>
      <view class="quote">“{{ heroDetail.famousQuote }}”</view>
      <view class="summary">{{ heroDetail.story }}</view>
    </view>
  </view>
</template>

<script setup lang="ts">
// === import ===
import {useApi} from "@/API/handler";
import { ref } from 'vue'
import {HeroDetail} from "@/types/forms/hero";
import { hero } from "@/API";
import {useLoad} from "@tarojs/taro";

// === define ===
definePageConfig({
  navigationBarTitleText: '英雄介绍',
})

// === constants ===
const heroDetail = ref<HeroDetail | null>(null)

// === hooks ===
useLoad((options) => {
  doGetHeroList(options)
})

// === api ===
const doGetHeroList = (options: Record<string, any>) => {
  useApi({
    api: hero.HeroDetail({id: options.id}),
    onSuccess: resp => {heroDetail.value = resp.data}
  })
}
</script>

<style lang="scss">
.container {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 40rpx;
  background: linear-gradient(to bottom, #fff5f5, #ffeaea);
  min-height: 100vh;
}

.avatar {
  width: 300rpx;
  height: 300rpx;
  border-radius: 24rpx;
  object-fit: cover;
  margin-bottom: 40rpx;
  border: 4rpx solid #c62828;
  box-shadow: 0 6rpx 12rpx rgba(198, 40, 40, 0.4);
}

.info {
  width: 100%;
  padding: 0 32rpx;
  background-color: #ffffff;
  border-radius: 20rpx;
  box-shadow: 0 4rpx 8rpx rgba(0, 0, 0, 0.1);
}

.name {
  font-size: 40rpx;
  font-weight: bold;
  margin-bottom: 24rpx;
  text-align: center;
  color: #b71c1c;
  border-bottom: 2rpx solid #b71c1c;
  padding-bottom: 8rpx;
}

.title,
.period,
.quote,
.summary,
.sacrifice {
  font-size: 30rpx;
  margin: 16rpx 0;
  line-height: 1.8;
  color: #333;
}

.title::before,
.period::before,
.quote::before,
.summary::before,
.sacrifice::before {
  color: #d32f2f;
}

.quote {
  font-style: italic;
  color: #880e4f;
  background-color: #fff0f0;
  padding: 16rpx;
  border-left: 6rpx solid #d32f2f;
  margin: 24rpx 0;
  border-radius: 8rpx;
}
</style>

