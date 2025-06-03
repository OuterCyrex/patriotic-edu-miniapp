<template>
  <view class="container">
    <view class="header">
      <text class="title">🛠️ 红星服务站</text>
      <text class="subtitle">搭建供需对接桥梁，提供精准化国防教育资源服务。</text>
    </view>

    <!-- 预约系统 -->
    <view class="section">
      <text class="section-title">预约系统</text>

      <view class="form-item">
        <text class="label">单位/学校名称</text>
        <input
          class="input"
          type="text"
          placeholder="请输入单位或学校名称"
          v-model="orgName"
        />
      </view>

      <view class="form-item">
        <text class="label">联系人</text>
        <input
          class="input"
          type="text"
          placeholder="请输入联系人姓名"
          v-model="contactName"
        />
      </view>

      <view class="form-item">
        <text class="label">需求类型</text>
        <picker mode="selector" :range="demandTypes" @change="onDemandTypeChange">
          <view class="picker">{{ selectedDemandType || '请选择需求类型' }}</view>
        </picker>
      </view>

      <view class="form-item">
        <text class="label">时间范围</text>
        <picker mode="date" @change="onDateChange">
          <view class="picker">{{ selectedDate || '请选择时间' }}</view>
        </picker>
      </view>

      <button class="submit-btn" hover-class="btn-hover" bindtap="onSubmit">提交预约</button>
    </view>

    <!-- 智能匹配 -->
    <view class="section">
      <text class="section-title">智能匹配推荐</text>
      <view class="recommendations">
        <view
          class="recommendation"
          v-for="(person, index) in recommendedPeople"
          :key="index"
        >
          {{ person }}
        </view>
      </view>
    </view>

    <!-- 服务类型 -->
    <view class="section">
      <text class="section-title">服务类型</text>
      <view class="service-list">
        <view class="service-item">英雄事迹宣讲</view>
        <view class="service-item">装备模型展览</view>
        <view class="service-item">军事体验活动</view>
      </view>
    </view>

    <!-- 联系通道 -->
    <view class="section">
      <text class="section-title">联系通道</text>
      <view class="contact-list">
        <view class="contact-item">📞 热线电话服务：400-123-4567</view>
        <view class="contact-item">💬 在线客服支持</view>
        <view class="contact-item">❓ 常见问题解答</view>
        <view class="contact-item">📄 服务流程指引</view>
      </view>
    </view>
  </view>
</template>

<script setup lang="ts">
import { ref } from 'vue'

const orgName = ref('')
const contactName = ref('')
const demandTypes = ['英雄宣讲', '模型展览', '军事体验']
const selectedDemandType = ref('')
const selectedDate = ref('')
const recommendedPeople = ref(['军事专家张三', '退役军人李四'])

const onDemandTypeChange = (e) => {
  const index = e.detail.value
  selectedDemandType.value = demandTypes[index]

  if (demandTypes[index] === '英雄宣讲') {
    recommendedPeople.value = ['雷锋精神讲解员', '英雄事迹代言人']
  } else if (demandTypes[index] === '模型展览') {
    recommendedPeople.value = ['装备讲解员王五']
  } else {
    recommendedPeople.value = ['军事体验教官赵六']
  }
}

const onDateChange = (e) => {
  selectedDate.value = e.detail.value
}

const onSubmit = () => {
  if (!orgName.value.trim()) {
    alert('请填写单位/学校名称')
    return
  }
  if (!contactName.value.trim()) {
    alert('请填写联系人姓名')
    return
  }
  if (!selectedDemandType.value) {
    alert('请选择需求类型')
    return
  }
  if (!selectedDate.value) {
    alert('请选择时间')
    return
  }

  alert(
    `提交成功！\n单位：${orgName.value}\n联系人：${contactName.value}\n需求类型：${selectedDemandType.value}\n时间：${selectedDate.value}`
  )
}
</script>

<style lang="scss">
.container {
  padding: 40rpx 32rpx;
  background-color: #f2f5f7;
  min-height: 100vh;
  font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif;
  color: #222;
}

.header {
  text-align: center;
  margin-bottom: 40rpx;

  .title {
    font-size: 48rpx;
    font-weight: 700;
    color: #d32f2f;
    margin-bottom: 12rpx;
  }
  .subtitle {
    font-size: 28rpx;
    color: #666;
  }
}

.section {
  background: #fff;
  padding: 32rpx;
  border-radius: 16rpx;
  margin-bottom: 32rpx;
  box-shadow: 0 4rpx 8rpx rgba(0, 0, 0, 0.05);

  .section-title {
    font-size: 36rpx;
    font-weight: 600;
    color: #d32f2f;
    border-bottom: 2rpx solid #d32f2f;
    padding-bottom: 12rpx;
    margin-bottom: 24rpx;
  }
}

.form-item {
  margin-bottom: 24rpx;

  .label {
    font-size: 28rpx;
    margin-bottom: 10rpx;
    display: block;
    color: #555;
  }

  .input,
  .picker {
    width: 100%;
    height: 64rpx;
    line-height: 64rpx;
    padding: 0 20rpx;
    font-size: 28rpx;
    color: #333;
    border: 1rpx solid #ccc;
    border-radius: 12rpx;
    background: #fafafa;
  }
  .picker {
    display: flex;
    align-items: center;
  }
}

.submit-btn {
  width: 100%;
  height: 72rpx;
  background-color: #d32f2f;
  color: #fff;
  font-size: 32rpx;
  font-weight: 600;
  border-radius: 14rpx;
  text-align: center;
  line-height: 72rpx;
  margin-top: 16rpx;
}

.btn-hover {
  background-color: #b02626 !important;
}

.recommendations {
  display: flex;
  flex-wrap: wrap;
  gap: 16rpx;
}

.recommendation {
  background-color: #ffebee;
  color: #c62828;
  padding: 12rpx 20rpx;
  border-radius: 14rpx;
  font-size: 28rpx;
  font-weight: 500;
}

.service-list {
  display: flex;
  gap: 24rpx;
  justify-content: space-around;
  flex-wrap: wrap;

  .service-item {
    background: #e3f2fd;
    color: #1565c0;
    padding: 18rpx 24rpx;
    border-radius: 20rpx;
    font-size: 28rpx;
    font-weight: 600;
    text-align: center;
    flex: 1 1 30%;
  }
}

.contact-list {
  display: flex;
  flex-direction: column;
  gap: 20rpx;

  .contact-item {
    font-size: 28rpx;
    color: #444;
    padding-left: 12rpx;
    border-left: 6rpx solid #d32f2f;
    line-height: 36rpx;
  }
}
</style>

