<template>
  <view class="word-cloud-container">
    <view class="title">词云展示</view>
    <canvas
      canvas-id="wordCloudCanvas"
      id="wordCloudCanvas"
      class="charts"
    />
  </view>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue'
import Taro from '@tarojs/taro'
import uCharts from '@qiun/ucharts'

const cWidth = ref(400)
const cHeight = ref(300)
let uChartsInstance: Record<string, any> = {}

const props = defineProps<{
  data: Array<{content: string, frequency: number}>,
}>()

const drawCharts = (id: string, data: any) => {
  const ctx = Taro.createCanvasContext(id)
  uChartsInstance[id] = new uCharts({
    type: 'word',
    context: ctx,
    width: cWidth.value,
    height: cHeight.value,
    series: data.series,
    animation: true,
    background: '#FFFFFF',
    color: [
      '#1890FF',
      '#91CB74',
      '#FAC858',
      '#EE6666',
      '#73C0DE',
      '#3CA272',
      '#FC8452',
      '#9A60B4',
      '#ea7ccc'
    ],
    padding: undefined,
    enableScroll: false,
    extra: {
      word: {
        type: 'normal',
        autoColors: false
      }
    }
  })
}

function Frequency2TextSize(data: Array<{content: string, frequency: number}>) {
  const minFreq = Math.min(...data.map(item => item.frequency))
  const maxFreq = Math.max(...data.map(item => item.frequency))
  const minSize = 12
  const maxSize = 28

  return data.map(item => {
    let size = minSize
    if (maxFreq !== minFreq) {
      size = minSize + ((item.frequency - minFreq) / (maxFreq - minFreq)) * (maxSize - minSize)
    }
    return {
      name: item.content,
      textSize: Math.round(size),
    }
  })
}


const getServerData = () => {
  setTimeout(() => {
    const res = {
      series: Frequency2TextSize(props.data)
    }
    drawCharts('wordCloudCanvas', res)
  }, 300)
}

onMounted(() => {
  const systemInfo = Taro.getSystemInfoSync()
  cWidth.value = systemInfo.windowWidth
  cHeight.value = systemInfo.windowWidth * 0.5
  getServerData()
})

</script>

<style lang="scss" scoped>
.word-cloud-container {
  margin-top: 8px;
  position: relative;
  width: 100vw;
  height: 50vw;
}

.title {
  position: absolute;
  top: 10rpx;
  left: 10rpx;
  font-size: 26rpx;
  font-weight: 600;
  color: #333;
  z-index: 10;
}

.charts {
  width: 100vw;
  height: 100%;
}
</style>

