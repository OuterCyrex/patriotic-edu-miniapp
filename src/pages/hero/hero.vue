<template>
  <view class="search-container">
    <nut-searchbar v-model="searchKeywords" @search="handleSearch">
      <template #rightout>
        <nut-button @click="handleSearch(searchKeywords ? searchKeywords : '')">搜索</nut-button>
      </template>
    </nut-searchbar>
    <AccordionList :options="listData" @select="handleSelect" />
    <view class="article-cards" v-if="!!heroList">
      <ArticleCard
                   v-for="item of heroList.list"
                   :key="item.id"
                   :title="item.name" :cover="item.avatarUrl" :summary="item.summary"
                   @click="Taro.navigateTo({url: `/pages/hero/detail?id=${item.id}`})"
      ></ArticleCard>
    </view>
    <nut-pagination v-if="heroList && heroList.list.length !== 0"
                    class="d-flex"
                    v-model="page"
                    :total-items="heroList.total"
                    :items-per-page="10"
                    mode="simple" @change="handleChange" />
    <LoadingRing v-if="!heroList" description="加载中"/>
    <nut-empty v-if="!!heroList && heroList.list.length === 0" description="什么都没有哦"></nut-empty>
  </view>
</template>

<script setup lang="ts">
// === import ===
import {ref} from 'vue'
import Taro, {useDidShow} from "@tarojs/taro";

import AccordionList from '@/components/AccordionList.vue'
import {hero} from "@/API";
import ArticleCard from "@/components/ArticleCard.vue";
import {HeroList} from "@/types/forms/hero";
import {useApi} from "@/API/handler";
import LoadingRing from "@/components/LoadingRing.vue";

// === define ===
definePageConfig({
  navigationBarTitleText: '红星英雄谱'
})

// === constants ===
const periodSelected = ref<number | null>(null)
const searchKeywords = ref<string>('')
let heroList = ref<HeroList | null>(null)
const page = ref<number>(1)
const listData = [
  {
    title: '时间轴',
    items: [
      '新民主主义革命时期（1921年—1949年）',
      '社会主义革命和建设时期（1949年—1978年）',
      '改革开放和社会主义现代化建设新时期（1978年—2012年）',
      '中国特色社会主义新时代（2012年至今）'
    ],
  },
]

// === methods ===
const handleSearch = (text: string) => {
  searchKeywords.value = text
  doGetHeroList()
}

function handleSelect({itemIndex}) {
  periodSelected.value = itemIndex + 1
  doGetHeroList()
}

function handleChange(value: number) {
  page.value = value
  doGetHeroList()
}


// === hooks ===
useDidShow(() => {
  doGetHeroList()
})

// === api ===
const doGetHeroList = () => {
  useApi({
    api: hero.HeroList({
      pageNum: page.value,
      pageSize: 10,
      ...(periodSelected.value !== null ? { period: periodSelected.value } : {}),
      ...(searchKeywords.value !== '' ? { name: searchKeywords.value } : {}),
    }),
    onSuccess: resp => {heroList.value = resp.data}
  })
}
</script>

<style lang="scss">
.d-flex {
  display: flex;
  justify-content: center;
}
</style>


