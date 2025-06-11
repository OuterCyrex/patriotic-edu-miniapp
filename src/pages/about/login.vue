<template>
  <view class="login-page">

    <nut-form>

      <view class="title">红星耀国防平台</view>

      <nut-form-item label="邮箱">
        <nut-input
          v-model="username"
          placeholder="请输入邮箱"
          type="text"
          clearable
        />
      </nut-form-item>

      <nut-form-item label="密码">
        <nut-input
          v-model="password"
          placeholder="请输入密码"
          type="password"
          clearable
        />
      </nut-form-item>

      <view @click="toRegister" class="register-button">
        <text>账号注册</text>
      </view>

      <nut-form-item>
        <nut-button type="danger" block @click="handleLogin">
          登录
        </nut-button>
      </nut-form-item>
    </nut-form>
  </view>
</template>

<script setup lang="ts">
// === import ===
import { ref } from 'vue'
import Taro, { showToast } from '@tarojs/taro'
import {user} from "@/API";
import {useApi} from "@/API/handler";

// === define ===
definePageConfig({
  navigationBarTitleText: '登陆页'
})

// === constants ===
const username = ref('')
const password = ref('')

// === methods ===
function toRegister() {
  Taro.navigateTo({
    url: '/pages/about/register',
  })
}

function handleLogin(): void {
  if (!username.value || !password.value) {
    showToast({ title: '邮箱或密码为空', icon: 'none' })
    return
  }

  if (password.value.length < 6 || password.value.length > 20) {
    showToast({ title: '密码长度应在 6-20', icon: "none" })
  }

  doLogin()
}

// === api ===
const doLogin = () => {
  useApi({
    api: user.Login({
      username: username.value,
      password: password.value
    }),
    onSuccess: resp => {
      Taro.setStorage({
        key: "user",
        data: resp.data
      })
      showToast({title: '登录成功', icon: 'success'})
      setTimeout(() => {
        Taro.navigateBack()
      }, 2000)
    }
  })
}
</script>

<style lang="scss">
.login-page {
  padding: 0 40rpx;
}

.title {
  font-size: 40rpx;
  font-weight: bold;
  margin: 60rpx auto;
  color: #d7000f;
  text-align: center;
}

.register-button {
  color: #71bcff;
  margin: 30px;
  width: 80vw;
}
</style>
