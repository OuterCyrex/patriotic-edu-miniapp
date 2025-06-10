<template>
    <nut-form :model-value="form" ref="formRef">
      <view class="form-container">
      <view class="form-header">
        <text class="form-title">我的心声</text>
        <view class="close-button" @click="emits('close')">
          <image class="close-button-icon" src="https://img.icons8.com/?size=100&id=tBs4jEKyshHO&format=png&color=000000"></image>
        </view>
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
        <nut-textarea v-model="form.content" placeholder="请输入内容" rows="3" />
      </nut-form-item>

      <nut-button block type="primary" @click="submitPost">提交</nut-button>
      </view>
    </nut-form>
  <NutToast v-model:visible="showToast" type="text" :msg="toastText"/>
</template>

<script setup lang="ts">
import {onMounted, reactive, ref} from 'vue'
import {post, user} from "@/API";

const emits = defineEmits(['close', 'submit'])

const form = reactive({
  content: '',
  region: '',
  identity: '',
  authorName: '',
  theme: ''
})

const themesList = ['少年说国防','老兵回忆录','任意主题']

const confirmTheme = (event: any) => {
  form.theme = themesList[event.detail.value]
}

const showToast = ref(false);
const toastText = ref('')
const openToast = (text: string): void => {
  showToast.value = true
  toastText.value = text
}

const formRef = ref<HTMLFormElement | null>(null)

async function submitPost() {
  const { valid } = await formRef.value?.validate()

  if (!valid) {
    openToast("请正确填写数据")
    return
  }

  post.NewPost({
    content: form.content,
    region: form.region,
    identity: form.identity,
    authorName: form.authorName,
    theme: form.theme,
  }).then(res => {
    if (res.code !== 200) openToast("服务器内部错误：" + res.message)
    emits('close')
  })

  emits('submit')
}

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
</script>

<style lang="scss">
.form-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.form-title {
  font-size: 32px;
  font-weight: bold;
  color: #333;
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

