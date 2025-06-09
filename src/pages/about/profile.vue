<template>
  <view class="profile-page">
    <nut-form ref="formRef">
      <view class="title">红星耀国防平台</view>

      <nut-form-item label="账号">
        <nut-input v-model="form.username" disabled readonly />
      </nut-form-item>

      <nut-form-item label="昵称">
        <nut-input v-model="form.nickname" placeholder="请输入昵称" />
      </nut-form-item>

      <nut-form-item label="头像">
        <nut-input v-model="form.avatarUrl" placeholder="请输入头像链接" />
      </nut-form-item>

      <nut-form-item label="地区">
        <picker mode="selector" :range="cities" @change="onCityChange">
          <view class="picker-input">
            {{ form.region + " 市" || '请选择所在城市' }}
          </view>
        </picker>
      </nut-form-item>

      <nut-form-item label="星星数">
        <nut-input v-model="form.totalStars" disabled readonly />
      </nut-form-item>

      <nut-form-item>
        <nut-button type="danger" block @click="updateProfile">
          保存信息
        </nut-button>
      </nut-form-item>
    </nut-form>
  </view>
</template>

<script setup lang="ts">
import {ref, onMounted, reactive} from 'vue'
import Taro, {showToast} from '@tarojs/taro'
import {user} from "@/API";
import {UserInfo} from "@/API/forms/user";

definePageConfig({
  navigationBarTitleText: '个人信息'
})

const form = ref({
  username: '',
  nickname: '',
  avatarUrl: '',
  region: '',
  totalStars: 0
})

const userInfo = reactive<UserInfo>(Taro.getStorageSync('user'))

onMounted(() => {
  if (userInfo) {
    form.value = {
      username: userInfo.username || '',
      nickname: userInfo.nickname || '',
      avatarUrl: userInfo.avatarUrl || '',
      region: userInfo.region || '',
      totalStars: userInfo.totalStars || 0
    }
  }
})

function updateProfile() {
  user.UpdateUser({
    id: userInfo.id,
    nickname: form.value.nickname,
    avatarUrl: form.value.avatarUrl,
    region: form.value.region,
  }).then(resp => {
    if (resp.code === 200) {
      userInfo.nickname = form.value.nickname
      userInfo.avatarUrl = form.value.avatarUrl
      userInfo.region = form.value.region
      Taro.setStorage({
        key: "user",
        data: userInfo
      })
      showToast({ title: '更改成功', icon: 'success' })
    } else {
      showToast({ title: resp.message, icon: 'none' })
    }
  })
}

function onCityChange(e: any) {
  form.value.region = cities[e.detail.value]
}

const cities = [
  '成都',
  '绵阳',
  '德阳',
  '自贡',
  '攀枝花',
  '泸州',
  '内江',
  '乐山',
  '南充',
  '宜宾',
  '达州',
  '雅安',
  '眉山',
  '广元',
  '资阳',
  '广安',
  '巴中',
  '遂宁',
  '阿坝',
  '甘孜',
  '凉山'
]

</script>

<style lang="scss">
.profile-page {
  padding: 0 40rpx;
}

.picker-input {
  color: black;
}

.title {
  font-size: 40rpx;
  font-weight: bold;
  margin: 60rpx auto;
  color: #d7000f;
  text-align: center;
}
</style>

