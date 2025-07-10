<template>
  <view class="post-view-container">
    <nut-searchbar  v-model="searchKeywords" @search="handleSearch">
      <template #rightout>
        <nut-button @click="handleSearch(searchKeywords ? searchKeywords : '')">搜索</nut-button>
      </template>
    </nut-searchbar>
    <WordCloud class="word-cloud"
               v-if="!!wordCloudData"
               :data="wordCloudData.map(item => ({content: item.content, frequency: item.frequency}))"/>

    <view class="post-cards" v-if="!!postList">
      <PostCard v-for="item of postList.list"
                :key="item.id"
                :content="item.content"
                :theme="item.theme"
                :region="item.region"
                :identity="item.identity"
                :likes="item.likesCount"
                :comments="item.commentsCount"
                :author="item.authorName"
                :featured="item.isFeatured === 1"
                :avatar="item.avatar"
                @click="Taro.navigateTo({url: `/pages/post/detail?id=${item.id}`})"
      />
    </view>
    <LoadingRing v-if="!postList" description="加载中"/>
    <nut-empty v-if="!!postList && postList.list.length === 0" description="什么都没有哦"></nut-empty>
    <nut-pagination v-if="!!postList && postList.list.length !== 0"
                    class="d-flex"
                    v-model="pageNum"
                    :total-items="postList.total"
                    :items-per-page="10"
                    mode="simple" @change="handleChange" />
    <FixedButton icon="https://img.icons8.com/?size=100&id=Z0BQsNX1Xhfb&format=png&color=000000" @click="toNewPost"/>
  </view>
</template>

<script setup lang="ts">
// === import ===
import PostCard from "@/components/post/PostCard.vue";
import Taro, {useDidShow} from '@tarojs/taro'
import {ref} from "vue";
import FixedButton from "@/components/FixedButton.vue";
import {PostList, WordFrequency} from "@/types/forms/post";
import {post} from "@/API";
import {useApi} from "@/API/handler";
import LoadingRing from "@/components/LoadingRing.vue";
import WordCloud from "@/components/post/WordCloud.vue";

// === define ===
definePageConfig({
  navigationBarTitleText: '红星心声汇'
})

// === constants ===
const searchKeywords = ref<string> ('')
const pageNum = ref<number>(1)
const keyword = ref<string> ("")
const postList = ref<PostList | null>(null)
const wordCloudData = ref<Array<WordFrequency> | null>(null)

// === methods ===
function handleSearch(text: string): void{
  keyword.value = text;
  doGetPostList()
}

function handleChange(value: number) {
  pageNum.value = value
  doGetPostList()
}

function toNewPost(): void {
  Taro.navigateTo({url: '/pages/post/new'})
}

// === hooks ===
useDidShow(() => {
  doGetPostList()
  doGetWordFrequency()
})

// === api ===
const doGetPostList = () => {
  useApi({
    api: post.PostList({
      pageNum: pageNum.value,
      pageSize: 10,
      ...(keyword.value !== "" ? {key: keyword.value} : {}),
    }),
    onSuccess: res => {
      postList.value = res.data
    }
  })
}

const doGetWordFrequency = () => {
  useApi({
    api: post.GetWordFrequency({x: 10}),
    onSuccess: res => {wordCloudData.value = res.data}
  })
}
</script>

<style lang="scss" scoped>
.post-view-container {
  height: 100vh;
  background: #f4f4f4;
}
.post-cards-list {
  background-color: white;
  margin-top: 20rpx;
}
.overlay-container {
  display: flex;
  justify-content: center;
  align-items: center;
}
.word-cloud {
  margin: 30px 0;
}
.d-flex {
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>


