<template>
  <view class="main-container">
    <at-flex>
      <at-flex-item>
        <SwiperImage :images="urls"/>
        <NoticeBar :content="noticeBarContent" class="notice-container"/>
        <view class="buttonsCards-container">
          <view class="buttonsCards-title"> {{buttonsTitle}} </view>
          <at-flex v-for="(subList, i) of buttonCards" :key="i" >
            <at-flex-item v-for="(item, subIndex) of subList" :key="subIndex" style="margin: 10px auto">
              <ButtonCard :type="1" :title="item.title" :subtitle="item.subtitle" :icon="item.icon" @click="item.onClick" class="button-card"/>
            </at-flex-item>
          </at-flex>
        </view>
        <view class="article-cards">
          <view class="article-title">
            <text style="font-weight: bold">最新动态</text>
            <text style="font-size: 14px">查看更多 ></text>
          </view>
          <view v-for="(item, index) of articleCards" :key="index">
            <ArticleCard
              :title="item.title"
              :summary="item.summary"
              :cover="item.cover"
              @click="item.onclick"
            />
          </view>
        </view>
      </at-flex-item>
    </at-flex>
  </view>
</template>

<script setup lang="ts">
import SwiperImage from "@/components/index/SwiperImage.vue";
import NoticeBar from "@/components/index/NoticeBar.vue";
import ButtonCard from "@/components/index/ButtonCard.vue";
import { AtFlex, AtFlexItem } from 'taro-ui-vue3'

import {ref} from "vue";
import {ButtonCardData, ButtonCardProps} from "@/types/buttonCard";
import ArticleCard from "@/components/ArticleCard.vue";
import {ArticleCardData, ArticleCardProps} from "@/types/articleCard";
import Taro from "@tarojs/taro";

const urls = ref<Array<string>>([
  'https://i.ibb.co/rRDkdT3h/974e14c00fd1c9ce653ec65adf41eff6.png',
  'https://i.ibb.co/rRDkdT3h/974e14c00fd1c9ce653ec65adf41eff6.png',
])

const noticeBarContent = ref<string>('⭐ 红星耀国防平台通过数字化手段传承红色精神，普及国防知识，强化全民国防意识。平台以党史为脉络，融合互动体验、知识学习、情感交流和服务对接功能，打造具有时代特色的国防教育新模式。')

const buttonsTitle = "—— 导航 ——"

const buttonCards = ref<Array<Array<ButtonCardProps>>>([
  [
    new ButtonCardData("红星英雄谱", "国防英雄典范",
      "https://img.icons8.com/?size=100&id=8ggStxqyboK5&format=png&color=000000",
      () => {Taro.navigateTo({url: '/pages/hero/hero',})}
    ),
    new ButtonCardData("红星青年派", "虚拟伙伴助学",
      "https://img.icons8.com/?size=100&id=37000&format=png&color=000000",
      () => {Taro.navigateTo({url: '/pages/pal/pal',})}
    ),
  ],
  [
    new ButtonCardData("红星心声汇", "全民交流空间",
      "https://img.icons8.com/?size=100&id=122811&format=png&color=000000",
      () => {Taro.navigateTo({url: '/pages/post/post'})}
    ),
    new ButtonCardData("红星服务站", "国防教育资源",
      "https://img.icons8.com/?size=100&id=WV326xpsBMyb&format=png&color=000000",
      () => {Taro.navigateTo({url: '/pages/service/service'})}
    )]
])

const articleCards = ref<Array<ArticleCardProps>>([
  new ArticleCardData("https://i.ibb.co/rRDkdT3h/974e14c00fd1c9ce653ec65adf41eff6.png", "新民主主义革命先驱恽代英生平事迹", "恽代英，原籍江苏武进，出生于湖北武昌。恽代英是中国无产阶级革命家，中国共产党早期青年运动领导人之一"),
  new ArticleCardData("https://i.ibb.co/rRDkdT3h/974e14c00fd1c9ce653ec65adf41eff6.png", "建设年代守护者黄继光", "黄继光，男，汉族，原名黄积广，四川省中江县人，中国共产党党员"),
])
</script>

<style lang="scss">
@import "./index.scss";
</style>
