<template>
  <view class="post-view-container">
    <nut-searchbar  v-model="searchKeywords" @search="search">
      <template #rightout>
        <nut-button @click="search(searchKeywords ? searchKeywords : '')">搜索</nut-button>
      </template>
    </nut-searchbar>
    <WordCloud v-if="!!wordCloudData" :data="wordCloudData.map(item => ({content: item.content, frequency: item.frequency}))"/>
<!--    <nut-cell title="筛选主题" :desc="ActionSheetManager.val" @click="ActionSheetManager.show = true"></nut-cell>-->
<!--    <nut-action-sheet v-model:visible="ActionSheetManager.show" :menu-items="ActionSheetManager.menuItems" @choose="(item) => {ActionSheetManager.val = item.name}" />-->
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
                @click="Taro.navigateTo({url: `/pages/post/detail?id=${item.id}`})"
      />
    </view>
    <FixedButton icon="https://img.icons8.com/?size=100&id=Z0BQsNX1Xhfb&format=png&color=000000" @click="showOverLayer = true"/>
  </view>
  <nut-overlay class="overlay-container" v-model:visible="showOverLayer" :close-on-click-overlay="false">
    <NewPost class="new-post-container" @close="showOverLayer = false"/>
  </nut-overlay>
</template>

<script setup lang="ts">
import PostCard from "@/components/post/PostCard.vue";
import Taro from '@tarojs/taro'
import {onMounted, ref} from "vue";
import FixedButton from "@/components/FixedButton.vue";
import NewPost from "@/components/post/NewPost.vue";
import {PostList, WordFrequency} from "@/API/forms/post";
import WordCloud from "@/components/post/WordCloud.vue";
import {post} from "@/API";

const showOverLayer = ref(false);

// const ActionSheetManager = ref<{
//   show: boolean
//   val: string
//   menuItems: any[]
// }>({show: false, val: '任意主题', menuItems: [{name: '少年说国防'}, {name: '老兵回忆录'}, {name: '任意主题'}]})

const searchKeywords = ref<string> ('')

function search(text: string): void{
  keyword.value = text;
  GetPostList()
}

const pageNum = ref<number>(0)
const keyword = ref<string> ("")

const postList = ref<PostList | null>(null)

const GetPostList = () => {
  post.PostList({
    pageNum: pageNum.value,
    pageSize: 10,
    ...(keyword.value !== "" ? {key: keyword.value} : {}),
  }).then(res => {
    postList.value = res.data
  })
}

const wordCloudData = ref<Array<WordFrequency> | null>(null)

const GetWordFrequency = () => {
  post.GetWordFrequency({
    x: 10
  }).then(res => {
    wordCloudData.value = res.data
  })
}

onMounted(() => {
  GetPostList()
  GetWordFrequency()
})
</script>

<style lang="scss">
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
</style>



