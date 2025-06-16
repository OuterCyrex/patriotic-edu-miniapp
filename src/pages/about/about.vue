<template>
  <view class="my-page-container">
    <!-- 用户信息区域 -->
    <view class="user-info">
      <image
        class="avatar"
        :src="userInfo.avatarUrl || defaultAvatar"
        mode="aspectFill"
      />
      <view class="nickname">{{ userInfo.nickname || '未登录' }}</view>
      <view class="email">{{ userInfo.username || '点击下方登录' }}</view>
    </view>

    <nut-cell-group class="button-group">
      <nut-cell v-if="userInfo.token === ''" is-link title="登录" @click="toLogin" >
        <template #icon>
          <My style="margin-right: 8px;"/>
        </template>
      </nut-cell>
      <nut-cell v-if="userInfo.token !== ''" is-link title="个人信息" @click="toProfile" >
        <template #icon>
          <My style="margin-right: 8px;"/>
        </template>
      </nut-cell>
      <nut-cell is-link title="设置" @click="toSetting">
        <template #icon>
          <Setting style="margin-right: 8px;"/>
        </template>
      </nut-cell>
      <nut-cell is-link title="关于我们" @click="toAboutUs">
        <template #icon>
          <Ask style="margin-right: 8px;"/>
        </template>
      </nut-cell>
      <nut-cell is-link title="联系客服" @click="toWaiter">
        <template #icon>
          <Message style="margin-right: 8px;"/>
        </template>
      </nut-cell>
    </nut-cell-group>

    <view class="button-group" v-if="userInfo.token !== ''">
      <nut-button block type="primary" @click="handleLogout">退出登录</nut-button>
    </view>
  </view>
</template>

<script setup lang="ts">
// === import ===
import { reactive } from 'vue'
import Taro, {showToast, useDidShow} from '@tarojs/taro'
import {My, Setting, Ask , Message} from '@nutui/icons-vue-taro'
import {user} from "@/API";
import {UserInfo} from "@/types/forms/user";

// === define ===
definePageConfig({
  navigationBarTitleText: '我的'
})

// === constants ===
const defaultAvatar = 'https://img.icons8.com/?size=100&id=4IZ8RiC9K8go&format=png&color=000000'
const userInfo = reactive<UserInfo>({
  id: 0,
  username: '',
  nickname: '',
  avatarUrl: '',
  region: '',
  type: 0,
  totalStars: 0,
  token: ''
})

// === methods ===
function toLogin() {
  Taro.navigateTo({ url: '/pages/about/login' })
}
function toProfile() {
  Taro.navigateTo({ url: '/pages/about/profile' })
}
function toSetting() {
  showToast({title: "该功能敬请期待", icon: "none"})
}
function toAboutUs() {
  showToast({title: "该功能敬请期待", icon: "none"})
}
function toWaiter() {
  showToast({title: "该功能敬请期待", icon: "none"})
}
function handleLogout() {
  Taro.removeStorageSync('user')
  userInfo.avatarUrl = ''
  userInfo.username = ''
  userInfo.nickname = ''
  userInfo.token = ''

  showToast(({title: '退出登录成功', icon: 'success'}))
}

// === hooks ===
useDidShow(() => {
  doGetUserInfo()
})

// === api ===
const doGetUserInfo = () => {
  user.GetUserInfo().then(resp => {
    if (resp) {
      userInfo.avatarUrl = resp.avatarUrl
      userInfo.username = resp.username
      userInfo.nickname = resp.nickname
      userInfo.token = resp.token
    }
  })
}
</script>

<style lang="scss">
.my-page-container {
  background-color: #f4f4f4;
  min-height: 100vh;
}

.button-group {
  margin: 40px 30px;
}

.user-info {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 80rpx 30rpx 40rpx;
  background-color: #683c3c;
  color: #fff;

  .avatar {
    width: 200rpx;
    height: 200rpx;
    border-radius: 50%;
    margin-bottom: 20rpx;
    background-color: #fff;
  }

  .nickname {
    font-size: 36rpx;
    font-weight: bold;
    margin-bottom: 10rpx;
  }

  .email {
    font-size: 26rpx;
    opacity: 0.9;
  }
}
</style>

