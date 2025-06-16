<template>
  <view class="detail-container" v-if="article">
    <image v-if="article.coverUrl" :src="article.coverUrl" mode="widthFix" class="cover-image" />
    <view class="title">{{ article.title }}</view>
    <view class="date">发布时间：{{ formatDate(article.gmtModified) }}</view>
    <view class="content">{{ article.content }}</view>
  </view>

  <nut-empty v-else description="加载中..." />
</template>

<script setup lang="ts">
import { ref } from 'vue'
import Taro, { useLoad } from '@tarojs/taro'
import { useApi } from '@/API/handler'
import { system } from '@/API'
import {AnnouncementItem} from "@/types/forms/system";

const article = ref<AnnouncementItem | null>(null)

const formatDate = (dateStr: string) => {
  const date = new Date(dateStr)
  return date.toLocaleDateString() + ' ' + date.toLocaleTimeString()
}

useLoad(options => {
  const articleId = Number(options.id)
  if (!articleId) {
    Taro.navigateBack()
    return
  }
  doGetAnnouncementDetail(options.id)
})

const doGetAnnouncementDetail = (id: number) => {
  useApi({
    api: system.GetAnnouncementDetail({ id: id }),
    onSuccess: resp => {
      article.value = resp.data
    }
  })
}
</script>

<style lang="scss">
.detail-container {
  padding: 32px 24px;
  background-color: #fff;
  min-height: 100vh;
  color: #333;
}

.cover-image {
  width: 100%;
  max-height: 300px;
  object-fit: cover;
  border-radius: 10px;
  margin-bottom: 24px;
}

.title {
  font-size: 42px;
  font-weight: 700;
  color: #c40000;
  margin-bottom: 20px;
}

.date {
  font-size: 18px;
  color: #999;
  margin-bottom: 28px;
}

.content {
  font-size: 22px;
  line-height: 2;
  color: #444;
  white-space: pre-wrap;
}
</style>

