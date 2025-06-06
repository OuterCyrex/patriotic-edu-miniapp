<template>
  <view class="page">
    <!-- 顶部红小星形象 -->
    <view class="header">
      <image class="avatar" src="/assets/red-star.png" mode="aspectFit" />
      <view class="title">欢迎来到红小星国防学习</view>
      <view class="subtitle">佩戴红领巾的虚拟伙伴，陪你趣味学国防！</view>
    </view>

    <!-- 语音交互按钮 -->
    <view class="voice-btn" @click="onVoiceClick">
      <image src="/assets/voice-icon.png" class="voice-icon" />
      <text>与红小星对话</text>
    </view>

    <!-- 核心功能介绍 -->
    <view class="features">
      <view class="feature-card" v-for="item in features" :key="item.title">
        <image :src="item.icon" class="feature-icon" />
        <view class="feature-title">{{ item.title }}</view>
        <view class="feature-desc">{{ item.desc }}</view>
      </view>
    </view>
  </view>
</template>

<script setup lang="ts">
import { ref } from 'vue'

const features = ref([
  {
    title: '知识闯关',
    desc: '每日10道国防常识题，答对解锁"星星勋章"',
    icon: '/assets/icon-quiz.png',
  },
  {
    title: '情景问答',
    desc: '模拟国防场景，多选答题，即时解析法律依据',
    icon: '/assets/icon-scenario.png',
  },
  {
    title: '勋章系统',
    desc: '激励用户持续学习，收集星星勋章',
    icon: '/assets/icon-medal.png',
  },
  {
    title: '进度追踪',
    desc: '记录学习轨迹，见证成长历程',
    icon: '/assets/icon-progress.png',
  },
])

function onVoiceClick() {
  // 这里模拟语音交互按钮点击事件
  alert('语音交互功能开发中，敬请期待！')
}
</script>

<style lang="scss">
.page {
  padding: 20px;
  background: #fff0f0;
  color: #b22222;
  min-height: 100vh;
  font-family: "Helvetica Neue", Arial, sans-serif;
}

.header {
  text-align: center;
  margin-bottom: 25px;
}

.avatar {
  width: 120px;
  height: 120px;
  margin: 0 auto 12px;
}

.title {
  font-size: 24px;
  font-weight: 700;
  margin-bottom: 6px;
  color: #d0021b;
}

.subtitle {
  font-size: 14px;
  color: #900000;
}

.voice-btn {
  background: #d0021b;
  color: white;
  padding: 12px 0;
  border-radius: 25px;
  width: 180px;
  margin: 0 auto 30px;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
}

.voice-icon {
  width: 24px;
  height: 24px;
  margin-right: 10px;
}

.features {
  display: flex;
  flex-wrap: wrap;
  gap: 18px;
  justify-content: center;
}

.feature-card {
  width: 160px;
  background: white;
  border-radius: 10px;
  padding: 15px;
  box-shadow: 0 2px 6px rgba(210, 2, 27, 0.2);
  text-align: center;
}

.feature-icon {
  width: 48px;
  height: 48px;
  margin-bottom: 10px;
}

.feature-title {
  font-weight: 600;
  font-size: 16px;
  margin-bottom: 6px;
  color: #b22222;
}

.feature-desc {
  font-size: 13px;
  color: #900000;
}
</style>


