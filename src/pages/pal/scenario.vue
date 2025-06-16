<template>
  <view v-if="!!questionArray" class="question-page-container">
    <text class="question-title">情景题</text>
    <view class="question-block" v-if="!!questionArray">
      <view class="question-content">
        <view>
          <text style="font-weight: bold;">情景：</text>
          {{questionArray[questionIndex].scenario}}
        </view>
        <nut-space />
        <view>
          <text style="font-weight: bold;">问题：</text>
          {{questionArray[questionIndex].question}}
        </view>
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
                 :solution="questionArray[questionIndex].solution"
                 :legal-basis="questionArray[questionIndex].legalBasis"
                 class="answer-box"
      ></AnswerBox>
      <nut-button block type="primary" :loading="isLoading"
                  v-if="!(questionIndex === 9 && questionArray[questionIndex].done === 1)"
                  @click="handleSubmitAndNext"
      >
        {{questionArray[questionIndex].done === 1? '下一题' : '提交'}}
      </nut-button>
    </view>
  </view>
  <LoadingRing v-if="!questionArray" description="加载中"/>
</template>

<script setup lang="ts">
// === import ===
import OptionButton from "@/components/pal/OptionButton.vue";
import {ref} from "vue";
import {ScenarioItem} from "@/types/forms/question";
import {question} from "@/API";
import {showToast, useDidShow} from "@tarojs/taro";
import AnswerBox from "@/components/pal/AnswerBox.vue";
import {useApi} from "@/API/handler";
import LoadingRing from "@/components/LoadingRing.vue";

// === define ===
definePageConfig({
  navigationBarTitleText: "情景问答"
})

// === constants ===
const questionIndex = ref<number>(0);
const selectedArray = ref<Array<string>>(['','','','','','','','','',''])
const questionArray = ref<Array<ScenarioItem> | null>(null)
const code2Option = ['A','B','C']
const isLoading = ref<boolean>(false);

// === methods ===
async function handleSubmitAndNext() {
  if (questionArray.value![questionIndex.value].done !== 1) {
    isLoading.value = true;
    await doSubmitScenario()
    isLoading.value = false;
  } else {
    questionIndex.value ++
  }
  if(questionIndex.value === 9 && questionArray.value![questionIndex.value].done === 1)
    await showToast({title: '今日题目已完成', icon: "success"})
}


// === hooks ===
useDidShow(() => {
  doGetScenario()
})

// === api ===
const doGetScenario = async () => {
  await useApi({
    api: question.GetScenario(),
    onSuccess: resp => {questionArray.value = resp.data}
  })
}

const doSubmitScenario = async () => {
  await useApi({
    api: question.SubmitScenario({
      questionId: questionArray.value![questionIndex.value].id,
      answer: code2Option.indexOf(selectedArray.value![questionIndex.value]) + 1,
    }),
    onSuccess: resp => {
      questionArray.value![questionIndex.value].done = 1
      questionArray.value![questionIndex.value].answer = resp.data.answer
      questionArray.value![questionIndex.value].legalBasis = resp.data.legalBasis
      questionArray.value![questionIndex.value].solution = resp.data.solution
      questionArray.value![questionIndex.value].choice = resp.data.userAnswer
      questionArray.value![questionIndex.value].correct = resp.data.result
    }
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
