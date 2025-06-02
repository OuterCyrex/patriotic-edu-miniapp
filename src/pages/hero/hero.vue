<template>
  <view class="search-container">
    <SearchBar @search="handleSearch" style="margin: 0"/>

    <AccordionList :options="listData" @select="onSelect" />
  </view>
</template>

<script setup lang="ts">
import AccordionList from '@/components/AccordionList.vue'
import SearchBar from "@/components/SearchBar.vue";

const listData = [
  {
    title: '时间轴',
    items: ['新民主主义革命先驱（1921-1949）', '建设年代守护者（1950-1978）', '改革浪潮弄潮儿（1979-2012）', '强国先锋时代篇（2012-至今）'],
  },
]

function onSelect({ groupIndex, itemIndex, item }) {
  console.log(`选中第 ${groupIndex} 组，第 ${itemIndex} 个：`, item)
}

function handleSearch(keyword: string) {
  console.log('搜索关键词：', keyword)
}
</script>

<style lang="scss">
@import "taro-ui-vue3/dist/style/components/search-bar.scss";

</style>


