<template>
  <view class="progress-bar-container">
    <view @click="toPrevious" :class="i === 1 ? 'disabled-pro-button' : ''">{{ '< 上一题' }}</view>
    <view class="progress-percent">{{ i }} / 10</view>
    <view @click="handleSubmitClick">{{ i !== 10 ? '下一题 >' : '提交' }}</view>
  </view>
</template>

<script setup lang="ts">
import {ref, toRefs} from "vue";
import {showToast} from "@tarojs/taro";

const props = defineProps<{ loading?: boolean }>()
const { loading } = toRefs(props)

const emits = defineEmits(['previous', 'next', 'submit'])

const i = ref<number>(1)

const handleSubmitClick = () => {
  if (loading.value) {
    showToast({title: '正在提交', icon: "none"})
    return
  }
  if (!loading.value) {
    if (i.value !== 10) toNext()
    else emits('submit')
  }
}

const toPrevious = () => {
  if (i.value === 1) return
  i.value --
  emits('previous')
}

const toNext = () => {
  if (i.value === 10) return
  i.value += 1
  emits('next')
}
</script>


<style lang="scss">
.progress-bar-container {
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  border-radius: 10px;
  display: flex;
  justify-content: space-around;
  padding: 30px 20px;
}
.disabled-pro-button {
  color: darkgrey;
}
.progress-percent {
  color: #8f2c2c;
}
</style>
