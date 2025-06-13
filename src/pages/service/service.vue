<template>
  <view class="red-service-container">
    <view class="header">
      <view class="d-flex">
        <text class="title">红星服务站</text>
      </view>
      <view class="d-flex">
        <text class="subtitle">搭建供需对接桥梁，提供精准化国防教育资源服务</text>
      </view>
    </view>

    <!-- NutUI 表单 -->
    <nut-form :model-value="form" @submit="submitForm">
      <nut-form-item label="单位/学校" prop="name" required>
        <nut-input v-model="form.name" placeholder="请输入单位或学校名称" />
      </nut-form-item>

      <nut-form-item label="联系人" prop="contact" required>
        <nut-input v-model="form.contact" placeholder="请输入联系人姓名" />
      </nut-form-item>

      <nut-form-item label="需求类型" prop="requirement" required>
        <nut-input v-model="form.requirement" placeholder="如：军事体验活动" @blur="matchExperts" />
      </nut-form-item>

      <nut-form-item label="时间范围" prop="dateRange" required>
        <nut-input v-model="form.dateRange" placeholder="如：2025年7月-8月" />
      </nut-form-item>

      <nut-form-item label="服务类型">
        <picker :range="serviceOptions" @change="onServiceChange">
          <view class="picker">
            {{ selectedService || '请选择服务类型' }}
          </view>
        </picker>
      </nut-form-item>

      <nut-button block type="danger" native-type="submit">提交预约</nut-button>
    </nut-form>

    <!-- 推荐结果 -->
    <view v-if="matched.length" class="matched">
      <view class="matched-title">🔍 推荐专家 / 英雄人物：</view>
      <view v-for="(person, index) in matched" :key="index" class="matched-card">
        {{ person }}
      </view>
    </view>

    <!-- 联系方式 -->
    <view class="contact-section">
      <view class="contact-title">📞 联系通道</view>
      <view class="contact-item">📱 热线电话服务：400-123-4567</view>
      <view class="contact-item">💬 在线客服支持：请前往“我的”-“客服”</view>
      <view class="contact-item">❓ 常见问题解答：请访问“帮助中心”</view>
      <view class="contact-item">📌 服务流程指引：完成预约后将有专人对接</view>
    </view>
  </view>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import Taro from '@tarojs/taro'

definePageConfig({
  navigationBarTitleText: '红星服务站'
})

interface FormData {
  name: string
  contact: string
  requirement: string
  dateRange: string
}

const form = ref<FormData>({
  name: '',
  contact: '',
  requirement: '',
  dateRange: '',
})

const serviceOptions = ['英雄事迹宣讲', '装备模型展览', '军事体验活动']
const selectedService = ref('')
const matched = ref<string[]>([])

const onServiceChange = (e: any) => {
  const index = e.detail.value
  selectedService.value = serviceOptions[index]
  matchExperts()
}

const matchExperts = () => {
  const keyword = selectedService.value
  if (keyword.includes('英雄')) {
    matched.value = ['雷锋同志', '黄继光烈士']
  } else if (keyword.includes('装备')) {
    matched.value = ['装甲模型专家李刚', '军事装备讲解员王军']
  } else if (keyword.includes('体验')) {
    matched.value = ['国防教育教官张伟']
  } else {
    matched.value = ['暂无匹配结果']
  }
}

const submitForm = () => {
  if (!form.value.name || !form.value.contact || !form.value.requirement || !form.value.dateRange) {
    Taro.showToast({ title: '请填写完整信息', icon: 'none' })
    return
  }

  Taro.showToast({ title: '预约成功', icon: 'success' })
  console.log('提交成功：', form.value, selectedService.value)
}
</script>

<style lang="scss">
.red-service-container {
  padding: 20px;
  background-color: #fff;
  min-height: 100vh;
  color: #333;
  font-family: "Microsoft Yahei", Arial, sans-serif;
}

.d-flex {
  display: flex;
  justify-content: center;
  align-items: center;
}

.header {
  margin-bottom: 24px;
  text-align: center;
}

.title {
  font-size: 38px;
  font-weight: 700;
  color: #c40000;
}

.subtitle {
  font-size: 24px;
  color: #a94442;
  margin-top: 8px;
  font-weight: 500;
}

nut-form,
nut-form-item {
  color: #333;
}

nut-input {
  color: #333;
  font-size: 18px;
  background-color: #fff;
  border-radius: 4px;
}
.picker {
  padding: 10px 14px;
  border-radius: 4px;
  color: #c40000;
  font-size: 28px;
  background-color: #fff;
}

nut-button {
  margin-top: 24px;
  font-weight: 600;
  font-size: 20px;
  border-radius: 6px;
}

.matched {
  margin-top: 24px;
  background-color: #fff0f0;
  border-radius: 6px;
  padding: 16px 20px;
}

.matched-title {
  font-weight: 700;
  font-size: 22px;
  margin-bottom: 12px;
  color: #c40000;
}

.matched-card {
  background-color: #ffe5e5;
  padding: 10px 14px;
  margin-bottom: 10px;
  border-left: 4px solid #c40000;
  border-radius: 4px;
  color: #900000;
}

.contact-section {
  margin-top: 32px;
  padding-top: 16px;
  border-top: 1px solid #f7c0c0;
}

.contact-title {
  font-size: 28px;
  font-weight: 700;
  color: #c40000;
  margin-bottom: 8px;
}

.contact-item {
  margin: 4px 0;
  color: #a94442;
  font-size: 24px;
}
</style>
