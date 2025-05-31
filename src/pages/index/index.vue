<template>
  <AtButton>{{msg}}</AtButton>
  <AtButton type='primary'>按钮文案</AtButton>
  <AtButton type='secondary'>按钮文案</AtButton>
</template>

<script>
import { ref } from 'vue'
import './index.scss'

export default {
  setup () {
    const msg = ref('Hello world')
    return {
      msg
    }
  }
}
</script>
