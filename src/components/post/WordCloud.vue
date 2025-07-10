<template>
  <div class="word-cloud-container">
    <div class="title">词云展示</div>
    <div ref="chartRef" class="echarts"></div>
  </div>
</template>

<script setup lang="ts">
import { ref, watch, onMounted, onBeforeUnmount, computed } from 'vue'
import * as echarts from 'echarts'
import 'echarts-wordcloud'

interface WordData {
  content: string
  frequency: number
}

const props = defineProps<{ data?: WordData[] }>()

const chartRef = ref<HTMLDivElement | null>(null)
let chartInstance: echarts.ECharts | null = null

const defaultData: WordData[] = [
  { content: '国防', frequency: 120 },
  { content: '教育', frequency: 100 },
  { content: '科技', frequency: 90 },
]

const effectiveData = computed(() => {
  return props.data && props.data.length > 0 ? props.data : defaultData
})

const colors = [
  '#1890FF',
  '#91CB74',
  '#FAC858',
  '#EE6666',
  '#3CA272',
  '#FC8452',
  '#9A60B4',
  '#ea7ccc',
]

function formatData(data: WordData[]) {
  if (!data || data.length === 0) return []

  const filtered = data.filter(
    (item) =>
      item.content &&
      item.content.trim() !== '' &&
      typeof item.frequency === 'number' &&
      !isNaN(item.frequency) &&
      item.frequency > 0,
  )

  if (filtered.length === 0) return []

  const min = Math.min(...filtered.map((i) => i.frequency))
  const max = Math.max(...filtered.map((i) => i.frequency))
  const minSize = 14
  const maxSize = 42

  return filtered.map((item) => {
    let size = minSize
    if (max !== min) {
      size = minSize + ((item.frequency - min) / (max - min)) * (maxSize - minSize)
    }
    size = Math.round(size)
    if (size < 14) size = 14

    return {
      name: item.content,
      value: item.frequency,
      textStyle: {
        fontSize: size,
        fontFamily: 'Microsoft YaHei, Arial, sans-serif',
        color: colors[Math.floor(Math.random() * colors.length)],
      },
    }
  })
}

function initChart() {
  if (!chartRef.value) return

  if (chartInstance) {
    chartInstance.dispose()
    chartInstance = null
  }

  chartInstance = echarts.init(chartRef.value)

  chartInstance.setOption({
    tooltip: {
      show: true,
      formatter: (params) => `${params.name}: ${params.value}`,
    },
    series: [
      {
        type: 'wordCloud',
        gridSize: 8,
        sizeRange: [14, 42],
        rotationRange: [-45, 45],
        rotationStep: 45,
        shape: 'circle',
        drawOutOfBound: false,
        data: formatData(effectiveData.value),
      },
    ],
  })
}

function refreshChart() {
  setTimeout(() => {
    initChart()
  }, 50)
}

onMounted(() => {
  refreshChart()
  window.addEventListener('resize', () => {
    if (chartInstance) chartInstance.resize()
  })
})

watch(effectiveData, () => {
  refreshChart()
})

onBeforeUnmount(() => {
  window.removeEventListener('resize', () => {
    if (chartInstance) chartInstance.resize()
  })
  if (chartInstance) {
    chartInstance.dispose()
    chartInstance = null
  }
})
</script>

<style scoped>
.word-cloud-container {
  position: relative;
  width: 100%;
  height: 320px;
  border: 1px solid #eee;
  background: #fff;
}

.title {
  position: absolute;
  top: 10px;
  left: 10px;
  font-weight: 600;
  font-size: 18px;
  color: #333;
  z-index: 10;
}

.echarts {
  width: 100%;
  height: 100%;
}
</style>
