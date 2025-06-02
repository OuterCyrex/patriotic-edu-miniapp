<template>
  <view class="search-bar">
    <input
      class="search-input"
      :value="keyword"
      placeholder="请输入关键词"
      @input="onInput"
      @confirm="onSearch"
      confirm-type="search"
    />
    <button
      v-if="keyword"
      class="search-button"
      size="mini"
      @tap="onSearch"
    >
      搜索
    </button>
  </view>
</template>

<script setup lang="ts">
import { ref } from 'vue'
const emit = defineEmits(['search'])

const keyword = ref('')

const onInput = (e: any) => {
  keyword.value = e.detail.value
}

const onSearch = () => {
  if (!keyword.value.trim()) return
  emit('search', keyword.value)
}
</script>

<style lang="scss">
.search-bar {
  display: flex;
  align-items: center;
  padding: 20rpx;
  background-color: #f5f5f5;
}

.search-input {
  flex: 1;
  height: 70rpx;
  padding: 0 20rpx;
  border: 1px solid #ddd;
  border-radius: 35rpx;
  background-color: #fff;
  font-size: 28rpx;
}

.search-button {
  margin-left: 20rpx;
  height: 70rpx;
  line-height: 70rpx;
  border-radius: 35rpx;
  background-color: #6190e8;
  color: #fff;
}
</style>


