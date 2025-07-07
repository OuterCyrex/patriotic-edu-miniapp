<template>
  <view class="search-container">
    <nut-searchbar v-model="searchKeywords" @search="handleSearch">
      <template #rightout>
        <nut-button @click="handleSearch(searchKeywords ? searchKeywords : '')">搜索</nut-button>
      </template>
    </nut-searchbar>

    <view class="article-cards" v-if="articleList && articleList.length > 0">
      <ArticleCard
        v-for="(item, index) of articleList"
        :key="item.id || index"
        :title="item.title"
        :summary="item.summary"
        :cover="item.coverUrl"
        @click="goToDetail(item.id)"
      />
    </view>

    <nut-pagination
      v-if="total > 0"
      class="d-flex"
      v-model="page"
      :total-items="total"
      :items-per-page="10"
      mode="simple"
      @change="handleChange"
    />
    <LoadingRing v-if="!articleList" description="加载中"/>
    <nut-empty v-if="!!articleList && articleList.length === 0" description="暂无公告" />
  </view>
</template>

<script setup lang="ts">
// === import ===
import { ref } from 'vue'
import Taro, { useDidShow } from '@tarojs/taro'
import ArticleCard from '@/components/ArticleCard.vue'
import {system} from '@/API'
import { useApi } from '@/API/handler'
import {AnnouncementItem} from "@/types/forms/system";
import LoadingRing from "@/components/LoadingRing.vue";

// === define ===
definePageConfig({
  navigationBarTitleText: '公告列表'
})

// === constants ===
const searchKeywords = ref('')
const page = ref(1)
const articleList = ref<Array<AnnouncementItem> | null>(null)
const total = ref(0)

// === methods ===
const handleSearch = (text: string) => {
  searchKeywords.value = text
  page.value = 1
  doGetArticleList()
}

const handleChange = (newPage: number) => {
  page.value = newPage
  doGetArticleList()
}

const goToDetail = (id: number) => {
  Taro.navigateTo({
    url: `/pages/index/detail?id=${id}`
  })
}

// === hooks ===
useDidShow(() => {
  doGetArticleList()
})

// === api ===
function doGetArticleList() {
  useApi({
    api: system.GetAnnouncementList({
      pageNum: page.value,
      pageSize: 10,
      ...(searchKeywords.value ? { keyword: searchKeywords.value } : {})
    }),
    onSuccess: (resp) => {
      articleList.value = resp.data.list || []
      total.value = resp.data.total || 0
    }
  })
}
</script>

<style lang="scss" scoped>
.d-flex {
  display: flex;
  justify-content: center;
}

.article-cards {
  margin-top: 16px;
}
</style>

