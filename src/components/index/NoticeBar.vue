<template>
  <view class="marquee-wrapper">
    <view class="marquee-track" :style="trackStyle">
      <text class="marquee-text">{{ props.content }}</text>
    </view>
  </view>
</template>


<script setup lang="ts">
import { onMounted, ref } from 'vue'
import Taro from '@tarojs/taro'

const props = withDefaults(defineProps<{
  duration?: number
  content: string
}>(), {
  duration: 20000
})

const trackStyle = ref('')

onMounted(() => {
  const { windowWidth } = Taro.getSystemInfoSync()
  trackStyle.value = `
    animation: scroll ${props.duration}ms linear infinite;
    padding-left: ${windowWidth}px;
  `
})
</script>

<style lang="scss">
.marquee-wrapper {
  overflow: hidden;
  white-space: nowrap;
  width: 100vw;
  height: 80rpx;
  line-height: 80rpx;
  background-color: #fff9e4;
  color: #333;
  font-size: 28rpx;
}

.marquee-track {
  display: inline-block;
}

@keyframes scroll {
  0% {
    transform: translateX(0);
  }
  100% {
    transform: translateX(-100%);
  }
}
</style>
