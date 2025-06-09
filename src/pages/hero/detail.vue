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
definePageConfig({
  navigationBarTitleText: '英雄介绍',
})

import { ref } from 'vue'
import {HeroDetail} from "@/API/forms/hero";
import { hero } from "@/API";
import {useLoad} from "@tarojs/taro";

const heroDetail = ref<HeroDetail | null>(null)

useLoad((options) => {
  hero.HeroDetail({id: options.id}).then(resp => {
    heroDetail.value = resp.data
  })
})
</script>


