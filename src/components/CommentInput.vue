<template>
  <view class="comment-input-wrapper">
    <nut-input
      v-model="inputValue"
      placeholder="写下你的评论..."
      class="comment-input"
      type="text"
    />
    <nut-button type="primary" class="comment-button" @click="onSubmit">评论</nut-button>
  </view>
</template>

<script setup lang="ts">
import { ref } from 'vue'

const emit = defineEmits<{
  (e: 'submit', content: string): void
}>()

const inputValue = ref('')

function onSubmit() {
  if (inputValue.value.trim() !== '') {
    emit('submit', inputValue.value.trim())
    inputValue.value = ''
  }
}
</script>

<style lang="scss" scoped>
.comment-input-wrapper {
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  display: flex;
  padding: 10px;
  background-color: #ffffff;
  border-top: 1px solid #eee;
  z-index: 999;
}
</style>

