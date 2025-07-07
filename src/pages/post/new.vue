<template>
    <nut-form :model-value="form" ref="formRef">
      <view class="form-container">
      <view class="form-header">
        <text class="form-title">我的心声</text>
      </view>

      <nut-form-item label="主题" prop="theme" :rules="[{ required: true }]">
        <picker :range="themesList" @change="confirmTheme">
          <view class="picker-content">{{form.theme || '请选择主题'}}</view>
        </picker>
      </nut-form-item>

      <nut-form-item label="作者" prop="authorName">
        <nut-input disabled v-model="form.authorName" />
      </nut-form-item>

      <nut-form-item label="城市" prop="region">
        <nut-input disabled v-model="form.region" />
      </nut-form-item>

      <nut-form-item label="学校" prop="identity" :rules="[{ required: true }]">
        <nut-input v-model="form.identity" placeholder="请输入学校" />
      </nut-form-item>

      <nut-form-item label="内容" prop="content"  :rules="[{ required: true }]">
        <nut-textarea v-model="form.content" placeholder="请输入内容" :rows="8" limit-show :max-length="300"/>
      </nut-form-item>

      <nut-button block type="primary" @click="submitPost">提交</nut-button>
      </view>
    </nut-form>
  <NutToast v-model:visible="showToast" type="text" :msg="toastText"/>
</template>

<script setup lang="ts">
// === import ===
import {onMounted, reactive, ref} from 'vue'
import {post, user} from "@/API";
import {useApi} from "@/API/handler";

// === define ===
definePageConfig({
  navigationBarTitleText: "发布心声"
})

// === constants ===
const form = reactive({
  content: '',
  region: '',
  identity: '',
  authorName: '',
  theme: ''
})
const themesList = ['少年说国防','老兵回忆录','任意主题']
const formRef = ref<HTMLFormElement | null>(null)
const showToast = ref(false);
const toastText = ref('')

// === methods ===
const confirmTheme = (event: any) => {
  form.theme = themesList[event.detail.value]
}

const openToast = (text: string): void => {
  showToast.value = true
  toastText.value = text
}

async function submitPost() {
  const { valid } = await formRef.value?.validate()
  if (!valid) {
    openToast("请正确填写数据")
    return
  }
  doNewPost()
}

// === hooks ===
onMounted(() => {
  user.GetUserInfo().then(resp => {
    if (!resp) {
      openToast('请先登录')
      return
    }
    form.region = resp!.region
    form.authorName = resp!.nickname
  })
})

// === api ===
const doNewPost = () => {
  useApi({
    api: post.NewPost({
      content: form.content,
      region: form.region,
      identity: form.identity,
      authorName: form.authorName,
      theme: form.theme,
    })
  })
}
</script>

<style lang="scss" scoped>
.form-header {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-bottom: 20px;
}

.form-title {
  font-weight: bold;
  color: #751f1f;
  font-size: 42rpx;
}

.form-container {
  padding: 30px;
}
.close-button-icon {
  height: 20px;
  width: 20px;
}

.close-button {
  height: 40px;
  width: 40px;
}
.picker-content {
  color: #4e4e4e;
}
</style>

