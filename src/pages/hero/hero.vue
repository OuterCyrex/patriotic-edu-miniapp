<template>
  <view class="timeline">
    <view class="section">
        <view class="card-list">
          <ArticleCard
            v-for="(item, i) in listData"
            :key="i"
            :title="item.title"
            :cover="coverList[item.id - 1].url "
            :summary="`时期: ${item.period}`"
            @click="handleClick(item.id)"
            title-wrap
          />
        </view>
    </view>
  </view>
</template>

<script lang="ts" setup>
import Taro from '@tarojs/taro'
import { onMounted, ref } from 'vue'
import { useApi } from '@/API/handler'
import { system } from '@/API'
import ArticleCard from '@/components/ArticleCard.vue'

definePageConfig({
  navigationBarTitleText: '红星英雄谱'
})

const listData = [
  { title: "新民主主义革命时期", period: "1921年 - 1949年", id: 1 },
  { title: "社会主义革命和建设时期", period: "1949年 - 1978年", id: 2 },
  { title: "改革开放和社会主义现代化建设新时期", period: "1978年 - 2012年", id: 3 },
  { title: "中国特色社会主义新时代", period: "2012年 至今", id: 4 },
]

const coverList = ref<Array<{ url: string }>>([
  { url: '' }, { url: '' }, { url: '' }, { url: '' }
])

function handleClick(id: number) {
  Taro.navigateTo({ url: `/pages/hero/list?id=${id}` })
}

onMounted(() => {
  doGetCoverList()
})

const doGetCoverList = () => {
  useApi({
    api: system.GetMisc({ key: "period-covers" }),
    onSuccess: resp => {
      coverList.value = JSON.parse(resp.data) as Array<{ url: string }>
    }
  })
}
</script>

<style lang="scss" scoped>
.timeline {
  padding: 24rpx;
  .card-list {
    display: flex;
    flex-direction: column;
  }
}
</style>
