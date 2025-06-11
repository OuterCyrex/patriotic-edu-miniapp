<template>
  <view class="question-page-container">
    <text class="question-title">单选题</text>
    <view class="question-block" v-if="!!questionArray">
      <view class="question-content">
        <text style="font-weight: bold;">问题{{questionIndex + 1}}：</text>
        {{questionArray[questionIndex].question}}
      </view>
      <view class="option-block">
        <OptionButton v-for="option in code2Option"
                      :key="option"
                      :label="option"
                      :content="questionArray[questionIndex]['option'+option]"
                      :correct="questionArray[questionIndex].answer === code2Option.indexOf(option) + 1"
                      @selected="selectedArray[questionIndex] = option"
                      :active="questionArray[questionIndex].done === 1 ? code2Option[questionArray[questionIndex].choice - 1] === option : selectedArray[questionIndex] === option"
        ></OptionButton>
      </view>
      <AnswerBox v-if="questionArray[questionIndex].done === 1"
                 :correct="questionArray[questionIndex].answer === questionArray[questionIndex].choice"
                 :answer="code2Option[questionArray[questionIndex].answer - 1]"
                 :explanation="questionArray[questionIndex].explanation"
                 class="answer-box"
      ></AnswerBox>
    </view>
    <ProgressBar @previous="questionIndex --" @next="questionIndex ++" @submit="handleAnswerSubmit"/>
  </view>
</template>

<script setup lang="ts">
// === import ===
import ProgressBar from "@/components/pal/ProgressBar.vue";
import OptionButton from "@/components/pal/OptionButton.vue";
import {ref} from "vue";
import {KnowledgeItem} from "@/types/forms/question";
import {question} from "@/API";
import {showToast, useDidShow} from "@tarojs/taro";
import AnswerBox from "@/components/pal/AnswerBox.vue";
import {useApi} from "@/API/handler";

// === define ===
definePageConfig({
  navigationBarTitleText: "知识竞赛"
})

// === constants ===
const questionIndex = ref<number>(0);

const selectedArray = ref<Array<string>>(['','','','','','','','','',''])

const questionArray = ref<Array<KnowledgeItem> | null>(null)

const code2Option = ['A','B','C','D']

// === methods ===
const handleAnswerSubmit = async () => {
  if (!questionArray.value) return

  if (questionArray.value[0].done === 1) {
    await showToast({title: '今日已提交', icon: 'error'})
    return
  }

  doSubmitKnowledge()
  doGetKnowledge()
}

// === hooks ===
useDidShow(() => {
  doGetKnowledge()
})

// === api ===
const doSubmitKnowledge = () => {
  useApi({
    api: question.SubmitKnowledge(questionArray.value!.map((value, index) => ({
        questionId: value.id, answer: code2Option.indexOf(selectedArray.value[index]) + 1
      }),
    )),
    onSuccess: () => {showToast({title: '提交成功', icon: 'success'})}
  })
}

const doGetKnowledge = () => {
  useApi({
    api: question.GetKnowledge(),
    onSuccess: resp => {questionArray.value = resp.data}
  })
}
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
.answer-box {
  margin: 20px 0;
}
</style>
