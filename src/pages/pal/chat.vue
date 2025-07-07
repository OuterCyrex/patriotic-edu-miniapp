<template>
  <view class="chat-page">
    <view class="chat-container" scroll-y scroll-with-animation>
      <view v-for="(msg, index) in messages" :key="index" class="chat-item" :class="msg.role">
        <image
          v-if="msg.role === 'ai'"
          class="avatar"
          src="https://cdn.outercyrex.top/logo.png"
          mode="aspectFill"
        />
        <view class="bubble" :class="msg.role">
          <text class="text">{{ msg.content }}</text>
        </view>
      </view>
    </view>

    <view class="chat-input">
      <input v-model="inputValue" class="input" placeholder="请输入内容" @submit.prevent="handleSubmit"/>
      <button class="send-button" @click="handleSubmit">发送</button>
    </view>
  </view>
</template>

<script setup lang="ts">
// === import ===
import { ref } from 'vue'
import Taro from '@tarojs/taro'
import {BaseURL} from "@/API/request";
import {ArrayBuffer2Utf8} from "@/utils/decoder";

// === define ===
definePageConfig({
  navigationBarTitleText: "红小星"
})

// === constants ===
const inputValue = ref('')
const messages = ref([
  { role: 'ai', content: '你好，我是智能红小星，有什么可以帮您？' }
])

// === methods ===
function handleSubmit() {
  const text = inputValue.value.trim()
  if (!text) return
  messages.value.push({ role: 'user', content: text })
  inputValue.value = ''

  messages.value.push({ role: 'ai', content: '' })
  const currentAIMessageIndex = messages.value.length - 1

  const requestTask = GetStreamResponse(text)
  requestTask.onChunkReceived(res => {
    const chunkStr = ArrayBuffer2Utf8(res.data)
    messages.value[currentAIMessageIndex].content += chunkStr
  })
  requestTask.then(() => {
    console.log('流式请求结束')
  }).catch(err => {
    console.error('流式请求错误:', err)
  })
}

// === api ===
const GetStreamResponse = (text: string) => {
  return Taro.request({
    url: BaseURL + '/chat/stream',
    enableChunked: true,
    method: 'GET',
    data: {
      message: text,
    },
    header: {
      'content-type': 'application/json'
    }})
}
</script>

<style lang="scss" scoped>
.chat-page {
  display: flex;
  flex-direction: column;
  height: 100vh;
  background-color: #f9f9f9;
}

.chat-container {
  flex: 1;
  padding: 20rpx;
  overflow-y: auto;
}

.chat-item {
  display: flex;
  margin-bottom: 20rpx;
  align-items: flex-start;

  &.ai {
    flex-direction: row;
  }

  &.user {
    flex-direction: row-reverse;
  }
}

.avatar {
  object-fit: fill;
  width: 60rpx;
  height: 60rpx;
  border-radius: 50%;
  margin-right: 20rpx;
}

.bubble {
  user-select: all;
  max-width: 70%;
  padding: 10rpx 20rpx;
  border-radius: 16rpx;
  font-size: 28rpx;
  line-height: 1.6;

  &.ai {
    background-color: #d20000;
    color: #ffffff;
    border-top-left-radius: 0;
  }

  &.user {
    background-color: #ffffff;
    color: #333333;
    border: 1px solid #dddddd;
    border-top-right-radius: 0;
  }
}

.chat-input {
  display: flex;
  padding: 20rpx;
  border-top: 1px solid #eee;
  background-color: #ffffff;

  .input {
    flex: 1;
    padding: 10rpx 20rpx;
    font-size: 28rpx;
    border: 1px solid #ccc;
    border-radius: 8rpx;
    background-color: #fff;
  }

  .send-button {
    margin-left: 20rpx;
    padding: 0 30rpx;
    background-color: #d20000;
    color: #fff;
    font-size: 28rpx;
    border-radius: 8rpx;
  }
}
</style>


