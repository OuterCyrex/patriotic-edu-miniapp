<template>
  <view class="main-container">
        <SwiperImage :images="urls"/>
        <nut-noticebar :text="noticeBarContent" class="notice-container"/>
        <view class="buttonsCards-container">
          <view class="buttonsCards-title">—— 导航 ——</view>
          <nut-row v-for="(subList, i) of buttonCards" :key="i" >
            <nut-col :span="12" v-for="(item, subIndex) of subList" :key="subIndex" style="margin: 10px auto">
              <ButtonCard :type="1" :title="item.title" :subtitle="item.subtitle" :icon="item.cover" @click="item.onClick" class="button-card"/>
            </nut-col>
          </nut-row>
        </view>
        <view class="article-cards">
          <view class="article-title" @click="Taro.navigateTo({url: '/pages/index/list'})">
            <text style="font-weight: bold">最新动态</text>
            <text style="font-size: 14px">查看更多 ></text>
          </view>
          <view v-if="!articleList">
            <nut-skeleton v-for="i of [1, 2, 3]"
                          style="margin: 20px"
                          :key="i"
                          height="15px"
                          width="300px"
                          animated avatar
                          avatar-size="60px"
                          row="3" />
          </view>
          <view class="articles" v-if="!!articleList">
              <ArticleCard
                v-for="(item, index) of articleList"
                :key="index"
                :title="item.title"
                :summary="item.summary"
                :cover="item.coverUrl"
                @click="Taro.navigateTo({
              url: '/pages/index/detail?id=' + item.id
              })"
              />
          </view>
        </view>
  </view>
</template>

<script setup lang="ts">
// === import ===
import SwiperImage from "@/components/index/SwiperImage.vue";
import ButtonCard from "@/components/index/ButtonCard.vue";

import {ref} from "vue";
import ArticleCard from "@/components/ArticleCard.vue";
import Taro, {useDidShow} from "@tarojs/taro";
import {AnnouncementItem} from "@/types/forms/system";
import {system} from "@/API";
import {useApi} from "@/API/handler";

// === define ===
definePageConfig({
  navigationBarTitleText: '首页'
})

// === constants ===
const urls = ref<Array<{url: string}>>([{url: ""}])
const noticeBarContent = ref<string>('⭐ 红星耀国防平台通过数字化手段传承红色精神，普及国防知识，强化全民国防意识。平台以党史为脉络，融合互动体验、知识学习、情感交流和服务对接功能，打造具有时代特色的国防教育新模式。')
const buttonCards = ref([
  [{
      title: "红星英雄谱",
      subtitle: "国防英雄典范",
      cover: "https://img.icons8.com/?size=100&id=8ggStxqyboK5&format=png&color=000000",
      onClick: () => {Taro.navigateTo({url: '/pages/hero/hero'})}
    },
    {
      title: "红星青年派",
      subtitle: "虚拟伙伴助学",
      cover: "https://img.icons8.com/?size=100&id=37000&format=png&color=000000",
      onClick: () => {Taro.navigateTo({url: '/pages/pal/pal'})}
    }],
  [{
      title: "红星心声汇",
      subtitle: "全民交流空间",
      cover: "https://img.icons8.com/?size=100&id=122811&format=png&color=000000",
      onClick: () => {Taro.navigateTo({url: '/pages/post/post'})}
    },
    {
      title: "红星服务站",
      subtitle: "国防教育资源",
      cover: "https://img.icons8.com/?size=100&id=WV326xpsBMyb&format=png&color=000000",
      onClick: () => {Taro.navigateTo({url: '/pages/service/service'})}
    }]
])
const articleList = ref<Array<AnnouncementItem> | null>(null)

// === hooks ===
useDidShow(() => {
  doGetSwiper()
  doGetAnnouncementList()
})

// === api ===
const doGetAnnouncementList = () => {
  useApi({
    api: system.GetAnnouncementList({
      pageNum: 0,
      pageSize: 5,
    }),
    onSuccess: resp => {
      articleList.value = resp.data.list
    }
  })
}

const doGetSwiper = () => {
  useApi({
    api: system.GetMisc({
      key: "swiper",
    }),
    onSuccess: resp => {
      urls.value = JSON.parse(resp.data) as Array<{url: string}>
    }
  })
}
</script>

<style lang="scss">
@import "./index.scss";
</style>
