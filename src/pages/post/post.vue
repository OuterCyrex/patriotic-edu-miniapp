<template>
  <view class="post-view-container">
    <nut-searchbar  v-model="searchKeywords" @search="search">
      <template #rightout>
        <nut-button @click="search(searchKeywords ? searchKeywords : '')">搜索</nut-button>
      </template>
    </nut-searchbar>
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
import {PostList} from "@/API/forms/post";
import {post} from "@/API";

const showOverLayer = ref(false);

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

onMounted(() => {
  GetPostList()
})
</script>

<style lang="scss">
.post-view-container {
  height: 100vh;
  background: #f4f4f4;
  padding: 20px 0;
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



