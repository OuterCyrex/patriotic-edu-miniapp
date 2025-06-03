<template>
  <view class="search-container">
    <nut-searchbar v-model="searchKeywords" @search="search">
      <template #rightout>
        <nut-button @click="search(searchKeywords ? searchKeywords : '')">搜索</nut-button>
      </template>
    </nut-searchbar>
    <AccordionList :options="listData" @select="onSelect" />
    <view class="article-cards" v-if="!!heroList">
      <ArticleCard
                   v-for="item of heroList.list"
                   :key="item.id"
                   :title="item.name" :cover="item.avatarUrl" :summary="item.summary"
                   @click="Taro.navigateTo({url: `/pages/hero/detail?id=${item.id}`})"
      ></ArticleCard>
    </view>
    <nut-pagination v-if="heroList && heroList?.list.length > 10"
                    class="d-flex"
                    v-model="page"
                    :total-items="25"
                    :items-per-page="10"
                    mode="simple" @change="change" />
  </view>
</template>

<script setup lang="ts">
import { onMounted, ref} from 'vue'
import Taro from "@tarojs/taro";

import AccordionList from '@/components/AccordionList.vue'
import {hero} from "@/API";
import ArticleCard from "@/components/ArticleCard.vue";
import {HeroList} from "@/API/forms/hero";

const page = ref<number>(1)

function change(value: number) {
  page.value = value
}

const listData = [
  {
    title: '时间轴',
    items: ['新民主主义革命先驱（1921-1949）', '建设年代守护者（1950-1978）', '改革浪潮弄潮儿（1979-2012）', '强国先锋时代篇（2012-至今）'],
  },
]

const periodSelected = ref<number | null>(null)
function onSelect({itemIndex}) {
  periodSelected.value = itemIndex + 1
  GetHeroList()
}

const searchKeywords = ref<string | null>(null)
const search = (text: string) => {
  searchKeywords.value = text
  GetHeroList()
}

let heroList = ref<HeroList | null>(null)

const GetHeroList = () => {
  hero.HeroList({
    pageNum: page.value,
    pageSize: 10,
    ...(periodSelected.value !== null ? { period: periodSelected.value } : {}),
    ...(searchKeywords.value !== null ? { name: searchKeywords.value } : {}),
  }).then(resp => {
    heroList.value = resp.data
  })
}

onMounted(() => {
  GetHeroList()
})
</script>

<style lang="scss">
@import "taro-ui-vue3/dist/style/components/pagination.scss";
.d-flex {
  display: flex;
  justify-content: center;
}
</style>


