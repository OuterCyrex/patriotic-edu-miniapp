<template>
  <view class="register-page">
    <nut-form class="register-form-container">
      <view class="title">用户注册</view>

      <nut-form-item label="邮箱">
        <nut-input
          v-model="form.username"
          placeholder="请输入邮箱"
          type="text"
          clearable
        />
      </nut-form-item>

      <nut-form-item label="验证码">
        <view class="code-wrapper">
          <nut-input
            v-model="form.code"
            placeholder="请输入验证码"
            type="text"
            clearable
          />
          <nut-button
            type="primary"
            size="small"
            :disabled="countdown > 0"
            @click="handleSendCode"
          >
            {{ countdown > 0 ? `${countdown}s后重试` : '获取验证码' }}
          </nut-button>
        </view>
      </nut-form-item>

      <nut-form-item label="密码">
        <nut-input
          v-model="form.password"
          placeholder="请输入密码"
          type="password"
          clearable
        />
      </nut-form-item>

      <nut-form-item label="确认密码">
        <nut-input
          v-model="form.rePassword"
          placeholder="请再次输入密码"
          type="password"
          clearable
        />
      </nut-form-item>

      <nut-form-item>
        <nut-button type="danger" block @click="handleRegister">
          注册
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
  navigationBarTitleText: "注册页"
})

// === constants ===
const form = ref({
  id: 0,
  username: '',
  password: '',
  rePassword: '',
  code: ''
})
const countdown = ref(0)
let timer: ReturnType<typeof setInterval> | null = null

// === methods ===
const handleSendCode = async  () => {
  if (!/^[\w.-]+@[a-zA-Z\d.-]+\.[a-zA-Z]{2,}$/.test(form.value.username)) {
    await showToast({ title: '无效邮箱', icon: "none" })
    return
  }
  doSendEmail()
}

const handleRegister = () => {
  const { username, password, code, rePassword } = form.value
  if (password !== rePassword) {
    showToast({ title: '两次密码不一致', icon: "none" })
    return
  }

  if (!username || !password || !rePassword || !code) {
    showToast({ title: '请正确填写信息', icon: "none" })
    return
  }

  if (password.length < 6 || password.length > 20) {
    showToast({ title: '密码长度应在 6-20', icon: "none" })
    return
  }

  doRegister()
}


// === api ===
const doRegister = () => {
  useApi({
    api: user.Register({
      username: form.value.username,
      password: form.value.password,
      nickname: form.value.username,
      avatarUrl: 'https://img.icons8.com/?size=100&id=4IZ8RiC9K8go&format=png&color=000000',
      region: '成都',
      code: form.value.code,
    }),
    onSuccess: resp => {
      if (resp.code === 200) {
        showToast({ title: '注册成功', icon: 'success' })
        setTimeout(() => {
          Taro.navigateBack()
        }, 2000)
      } else {
        showToast({ title: resp.message, icon: 'error' })
      }
    }
  })
}

const doSendEmail = () => {
  useApi({
    api: user.SendEmail({identifier: form.value.username}),
    onSuccess: () => {
      showToast({ title: '验证码已发送', icon: "none" })
      countdown.value = 60
      timer = setInterval(() => {
        countdown.value--
        if (countdown.value <= 0 && timer) {
          clearInterval(timer)
          timer = null
        }
      }, 1000)
    }
  })
}
</script>

<style lang="scss">
.register-page {
  padding: 0 40rpx;
}

.title {
  font-size: 40rpx;
  font-weight: bold;
  margin: 60rpx auto;
  color: #d7000f;
  text-align: center;
}

.code-wrapper {
  display: flex;
  align-items: center;
  gap: 20rpx;
}
</style>

