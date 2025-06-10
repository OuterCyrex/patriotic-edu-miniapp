<template>
  <view class="question-page-container">
    <text class="question-title">单选题</text>
    <view class="question-block" v-if="!!questionArray">
      <view class="question-content">
        <text style="font-weight: bold;">问题{{questionIndex + 1}}：</text>
        {{questionArray[questionIndex].question}}
      </view>
      <view class="option-block">
        <OptionButton label="A" :content="questionArray[questionIndex].optionA" @selected="selectedArray[questionIndex] = 'A'" :active="selectedArray[questionIndex] === 'A'"></OptionButton>
        <OptionButton label="B" :content="questionArray[questionIndex].optionB" @selected="selectedArray[questionIndex] = 'B'" :active="selectedArray[questionIndex] === 'B'"></OptionButton>
        <OptionButton label="C" :content="questionArray[questionIndex].optionC" @selected="selectedArray[questionIndex] = 'C'" :active="selectedArray[questionIndex] === 'C'"></OptionButton>
        <OptionButton label="D" :content="questionArray[questionIndex].optionD" @selected="selectedArray[questionIndex] = 'D'" :active="selectedArray[questionIndex] === 'D'"></OptionButton>
      </view>
    </view>
    <ProgressBar @previous="questionIndex --" @next="questionIndex ++"/>
  </view>
</template>

<script setup lang="ts">
import ProgressBar from "@/components/pal/ProgressBar.vue";
import OptionButton from "@/components/pal/OptionButton.vue";
import {onMounted, ref} from "vue";
import {KnowledgeItem} from "@/API/forms/question";
import {question} from "@/API";

definePageConfig({
  navigationBarTitleText: "知识竞赛"
})

const questionIndex = ref<number>(0);

const selectedArray = ref<Array<string>>(['','','','','','','','','',''])

const questionArray = ref<Array<KnowledgeItem> | null>(null)

onMounted(() => {
  question.GetKnowledge().then(resp => {
    questionArray.value = resp.data
  })
})
</script>

<style lang="scss">
.question-page-container {
  margin: 30px 20px;
}
.question-title {
  color: #7a2121;
  font-size: 32px;
}
.question-content {
  margin: 30px 20px;
}
.option-block {
  margin: 30px 10px;
}
</style>
