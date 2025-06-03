<template>
  <view class="accordion-list">
    <view
      class="group"
      v-for="(group, groupIndex) in options"
      :key="groupIndex"
    >
      <view class="group-header" @tap="toggleGroup(groupIndex)">
        <text class="group-title">{{ group.title }}</text>
        <text class="arrow">{{ openGroupIndex === groupIndex ? '▲' : '▼' }}</text>
      </view>
      <view v-if="openGroupIndex === groupIndex" class="group-items">
        <view
          v-for="(item, itemIndex) in group.items"
          :key="itemIndex"
          class="item"
          :class="{ selected: isSelected(groupIndex, itemIndex) }"
          @tap="selectItem(groupIndex, itemIndex, item)"
        >
          {{ item }}
        </view>
      </view>
    </view>
  </view>
</template>

<script setup lang="ts">
import { ref } from 'vue'

defineProps<{
  options: { title: string; items: string[] }[]
}>()

const emit = defineEmits<{
  (e: 'select', data: { groupIndex: number; itemIndex: number; item: string }): void
}>()

const openGroupIndex = ref<number | null>(null)
const selected = ref<{ groupIndex: number; itemIndex: number } | null>(null)

const toggleGroup = (index: number) => {
  openGroupIndex.value = openGroupIndex.value === index ? null : index
}

const selectItem = (groupIndex: number, itemIndex: number, item: string) => {
  selected.value = { groupIndex, itemIndex }
  emit('select', { groupIndex, itemIndex, item })
}

const isSelected = (groupIndex: number, itemIndex: number) => {
  return selected.value?.groupIndex === groupIndex && selected.value?.itemIndex === itemIndex
}
</script>

<style lang="scss">
.accordion-list {
  .group {
    margin-bottom: 20rpx;
    border-radius: 12rpx;
    overflow: hidden;
  }
  .group-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 24rpx;
    background-color: #f5f5f5;
    font-size: 30rpx;
    font-weight: bold;
  }
  .group-items {
    background-color: #fff;
  }
  .item {
    padding: 24rpx;
    border-top: 1px solid #eee;
    font-size: 28rpx;
  }
  .item.selected {
    background-color: #ffecec;
    color: #7a0d0d;
  }
}
</style>

