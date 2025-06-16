<template>
  <view class="answer-block">
    <view class="answer-content">
      <text style="font-weight: bold">答案：</text>
      <text style="font-weight: bold" :class="correct ? 'green': 'red'">
        {{answer}}</text>
    </view>
    <view class="explanation-content">
      <text style="font-weight: bold" v-if="!!explanation">解析：</text>
      {{explanation}}
    </view>

    <view class="explanation-content">
      <text style="font-weight: bold; color: #151515" v-if="!!solution">处理方案：</text>
      {{solution}}</view>

    <view class="explanation-content">
    <text style="font-weight: bold; color: #151515" v-if="!!legalBasis">法律依据：</text>
    {{legalBasis}}</view>
  </view>
</template>

<script setup lang="ts">
defineProps<{
  correct: boolean
  answer: string
  explanation?: string
  legalBasis?: string
  solution?: string
}>()
</script>

<style lang="scss">

.answer-block {
  background-color: #f3dddd;
  border-left: 6px solid #e13939;
  border-radius: 12px;
  padding: 16px;
  margin: 20px 0;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
  line-height: 1.6;
}

.answer-content {
  margin-bottom: 12px;
  display: flex;
  align-items: center;
}

.answer-content text:first-child {
  margin-right: 10px;
  color: #333;
}

.answer-content .green {
  color: #1abc9c;
}

.answer-content .red {
  color: #e74c3c;
}

.explanation-content {
  margin-bottom: 15px;
}

.explanation-content text:first-child {
  display: block;
  margin-bottom: 6px;
  color: #444;
}
</style>
