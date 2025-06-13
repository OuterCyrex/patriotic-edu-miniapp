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
                 :explanation="questionArray[questionIndex].legalBasis"
                 class="answer-box"
      ></AnswerBox>
      <nut-button block type="success"
                  :disabled="questionIndex === 9 && questionArray[questionIndex].done === 1"
                  @click="handleSubmitAndNext"
      >
        {{questionArray[questionIndex].done === 1? '下一题' : '提交'}}
      </nut-button>
    </view>
  </view>
  <nut-empty v-if="!questionArray" description="什么都没有哦" />
</template>

<script setup lang="ts">
// === import ===
import OptionButton from "@/components/pal/OptionButton.vue";
import {ref} from "vue";
import {ScenarioItem} from "@/types/forms/question";
import {question} from "@/API";
import {useDidShow} from "@tarojs/taro";
import AnswerBox from "@/components/pal/AnswerBox.vue";
import {useApi} from "@/API/handler";

// === define ===
definePageConfig({
  navigationBarTitleText: "情景问答"
})

// === constants ===
const questionIndex = ref<number>(0);
const selectedArray = ref<Array<string>>(['','','','','','','','','',''])
const questionArray = ref<Array<ScenarioItem> | null>(null)
const code2Option = ['A','B','C']

// === methods ===
function handleSubmitAndNext() {
  if (questionArray.value![questionIndex.value].done !== 1) {
    doSubmitScenario()
    doGetScenario()
  } else {
    questionIndex.value ++
  }
}


// === hooks ===
useDidShow(() => {
  doGetScenario()
})

// === api ===
const doGetScenario = () => {
  useApi({
    api: question.GetScenario(),
    onSuccess: resp => {questionArray.value = resp.data}
  })
}

const doSubmitScenario = () => {
  useApi({
    api: question.SubmitScenario({
      questionId: questionArray.value![questionIndex.value].id,
      answer: code2Option.indexOf(selectedArray.value![questionIndex.value]) + 1,
    })
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
