<template>
  <view class="post-page" v-if="postDetail">
    <view class="post-card">
      <view class="post-header">
        <nut-avatar class="user-avatar">
          <My />
        </nut-avatar>
        <view class="author-info">
          <view class="author">{{ postDetail.authorName }}</view>
          <view class="meta">
            {{ postDetail.identity }} · {{ postDetail.region }}
          </view>
        </view>
      </view>
      <view class="post-content">
        <view class="text">{{ postDetail.content }}</view>
        <view class="theme">#{{ postDetail.theme }}</view>
      </view>
      <view class="post-footer">
        <view class="like-button" @click="PostLiked">
          <image class="like-icon" :src="liked ? 'https://img.icons8.com/?size=100&id=83166&format=png&color=000000': 'https://img.icons8.com/?size=100&id=82788&format=png&color=000000'"/>
          <text>{{ postDetail.likesCount }}</text>
        </view>
        <text style="margin-left: 32rpx">💬 {{ postDetail.commentsCount }}</text>
      </view>
    </view>
    <view class="comments" v-if="!!commentList">
      <Comment v-for="item of commentList.list" :key="item.id" :id="item.id" :content="item.content" />
    </view>
  </view>
</template>

<script setup lang="ts">
import { ref } from "vue"
import { useLoad } from "@tarojs/taro"
import {CommentList, PostInfo} from "@/API/forms/post"
import { post } from "@/API"
import {My} from "@nutui/icons-vue-taro";
import Comment from "@/components/post/Comment.vue";

const postId = ref<number>(0)
const postDetail = ref<PostInfo | null>(null)
const liked = ref<boolean>(false)
const commentList = ref<CommentList | null>(null)

useLoad((options) => {
  postId.value = Number(options.id)
  post.PostDetail({ id: postId.value }).then(res => {
    postDetail.value = res.data
  })
  post.CommentList({
    voiceId: postId.value,
    pageSize: 1000,
    pageNum: 0,
  }).then(res => {
    commentList.value = res.data
  })
})

function PostLiked() {
  liked.value = true
  postDetail.value!.likesCount = 1
}
</script>

<style lang="scss">
.comments {
  margin: 30px 0;
  padding: 0 30rpx;
}
.like-icon {
  height: 32px;
  width: 32px;
  margin-right: 10px;
  position: relative;
  top: 6px;
}
.user-avatar {
  margin-right: 20px;
}
.post-card {
  background-color: #fff;
  padding: 24rpx;
  box-shadow: 0 4rpx 10rpx rgba(0, 0, 0, 0.05);
}

.post-header {
  display: flex;
  align-items: center;
  margin-bottom: 20rpx;
}

.avatar {
  width: 88rpx;
  height: 88rpx;
  border-radius: 50%;
  margin-right: 20rpx;
}

.author-info .author {
  font-weight: bold;
  font-size: 30rpx;
}

.post-content .text {
  font-size: 30rpx;
  color: #333;
  line-height: 1.6;
  margin-bottom: 16rpx;
}

.theme {
  font-size: 26rpx;
  color: #c20000;
  background-color: #fcecec;
  padding: 4rpx 12rpx;
  border-radius: 8rpx;
  display: inline-block;
}

.post-footer {
  display: flex;
  align-items: center;
  font-size: 24rpx;
  color: #666;
  margin-top: 24rpx;
  justify-content: space-around;
}

.time {
  margin-left: auto;
  font-size: 22rpx;
  color: #999;
}

</style>
