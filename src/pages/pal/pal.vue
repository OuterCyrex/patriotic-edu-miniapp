<template>
  <view class="page">
    <view class="header">
      <image class="avatar" src="https://i.ibb.co/NgPDVbry/e6bd4ccfdbc4b17d3af6b739a0c17a34-removebg-preview.png" mode="aspectFit" />
      <view class="title">欢迎来到红小星国防学习</view>
      <view class="subtitle">佩戴红领巾的虚拟伙伴，陪你趣味学国防！</view>
    </view>

    <view class="voice-btn" @click="onVoiceClick">
      <text>与红小星对话</text>
    </view>

    <view class="features">
      <ButtonCard v-for="(item, index) of features" :key="index" :title="item.title" :subtitle="item.desc" :icon="item.icon" :type="2"></ButtonCard>
    </view>
  </view>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import ButtonCard from "@/components/index/ButtonCard.vue";

const features = ref([
  {
    title: '知识闯关',
    desc: '每日10道国防题',
    icon: 'https://img.icons8.com/?size=100&id=VJCVutzKX9zq&format=png&color=000000',
  },
  {
    title: '情景问答',
    desc: '模拟国防场景',
    icon: 'https://img.icons8.com/?size=100&id=2juNVZZCBjKu&format=png&color=000000',
  },
  {
    title: '勋章系统',
    desc: '收集星星勋章',
    icon: 'https://img.icons8.com/?size=100&id=KFssnxqXHKfA&format=png&color=000000',
  },
  {
    title: '进度追踪',
    desc: '记录成长历程',
    icon: 'https://img.icons8.com/?size=100&id=FpRBDCVNFXYv&format=png&color=000000',
  },
])

function onVoiceClick() {
  // 这里模拟语音交互按钮点击事件
  alert('语音交互功能开发中，敬请期待！')
}
</script>


