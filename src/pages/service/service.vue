<template>
  <view class="red-service-container">
    <view class="header">
      <view class="title">红星服务站</view>
      <view class="subtitle">供需对接桥梁 · 精准国防教育资源</view>
    </view>

    <view class="form-card">
      <nut-form class="form-block" :model-value="form" @submit="submitForm">
        <nut-form-item label="单位/学校" prop="name" required>
          <nut-input v-model="form.name" placeholder="请输入单位或学校名称" />
        </nut-form-item>

        <nut-form-item label="联系人" prop="contact" required>
          <nut-input v-model="form.contact" placeholder="请输入联系人姓名" />
        </nut-form-item>

        <nut-form-item label="时间范围" prop="dateRange" required>
          <nut-input v-model="form.dateRange" placeholder="如：2025年7月 - 8月" />
        </nut-form-item>

        <nut-form-item label="服务类型" required>
          <picker :range="serviceOptions" @change="onServiceChange">
            <view class="picker">
              {{ selectedService || '请选择服务类型' }}
            </view>
          </picker>
        </nut-form-item>

        <nut-form-item label="需求类型" prop="requirement" required>
          <nut-textarea v-model="form.requirement" placeholder="请输入详细需求" @blur="matchExperts" />
        </nut-form-item>

        <view style="margin: 20px">
          <nut-button block type="danger" native-type="submit">提交预约</nut-button>
        </view>
      </nut-form>
    </view>

    <view v-if="matched.length" class="matched-card">
      <text class="section-title">🔍 推荐专家 / 英雄人物</text>
      <nut-cell-group>
        <nut-cell v-for="(person, index) in matched" :key="index" :title="person" icon="user" />
      </nut-cell-group>
    </view>

    <view class="contact-card">
      <text class="section-title">📞 联系通道</text>
      <nut-cell-group>
        <nut-cell title="热线电话服务" :desc="hotline" icon="phone" />
        <nut-cell title="在线客服支持" desc="请前往“我的”-“客服”" icon="service" />
        <nut-cell title="常见问题解答" desc="请访问“帮助中心”" icon="ask" />
        <nut-cell title="服务流程指引" desc="完成预约后将有专人对接" icon="description" />
      </nut-cell-group>
    </view>
  </view>
</template>

<script setup lang="ts">
// === import ===
import {onMounted, ref} from 'vue'
import {showToast} from '@tarojs/taro'
import {useApi} from "@/API/handler";
import {system} from "@/API";

// === define ===
definePageConfig({
  navigationBarTitleText: "红星服务站"
})

interface FormData {
  name: string
  contact: string
  requirement: string
  dateRange: string
}

// === constants ===
const form = ref<FormData>({
  name: '',
  contact: '',
  requirement: '',
  dateRange: '',
})

const hotline = ref<string>('400-1234-5678')
const serviceOptions = ['英雄事迹宣讲', '装备模型展览', '军事体验活动']
const selectedService = ref('')
const matched = ref<string[]>([])

// === methods ===
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
    showToast({ title: '请填写完整信息', icon: 'none' })
    return
  }
  showToast({ title: '预约成功', icon: 'success' })
}

// === hooks ===
onMounted(() => {
  doGetHotline()
})

// === api ===
const doGetHotline = () => {
  useApi({
    api: system.GetMisc({
      key: "hotline",
    }),
    onSuccess: resp => {
      const data = JSON.parse(resp.data as string) as {hotline: string}
      hotline.value = data.hotline
    }
  })
}
</script>

<style lang="scss">
.red-service-container {
  padding: 20px;
  background-color: #ffffff;
  color: #333;
  min-height: 100vh;
}

.header {
  text-align: center;

  .title {
    font-size: 40px;
    font-weight: 700;
    color: #c40000;
  }

  .subtitle {
    font-size: 26px;
    color: #666;
    margin-top: 8px;
  }
}

.form-card,
.matched-card,
.contact-card {
  border-radius: 10px;
  padding: 20px;
  margin-bottom: 24px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.03);
}

.section-title {
  font-weight: 600;
  color: #c40000;
  margin-bottom: 12px;
  display: block;
}

.picker {
  color: #707070;
}
</style>
