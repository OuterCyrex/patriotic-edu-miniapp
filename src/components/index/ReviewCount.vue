<template>
  <view class="visit-count">
    小程序访问量：<text class="visit-number">{{ total }}</text>
  </view>
</template>

<script setup lang="ts">
import {onMounted, ref} from 'vue'
import {useApi} from "@/API/handler";
import {system} from "@/API";
const total = ref(0)

onMounted(async () => {
  await GetReviewCount()
  await UpdateReviewCount()
})

const GetReviewCount = async () => {
  await useApi({
    api: system.GetMisc({ key: "review-count" }),
    onSuccess: resp => {
      total.value = JSON.parse(resp.data as string).value as number
    }
  })
}

const UpdateReviewCount = async () => {
  await useApi({
    api: system.UpdateMisc({miscKey: 'review-count', value: `{"value": ${total.value + 1}}`}),
    onSuccess: () => {
      GetReviewCount()
    }
  })
}
</script>

<style lang="scss">
.visit-count {
  margin: 20px 0;
  padding: 6px 20px;
  background: white;
  border-left: 8px solid #f58484;
  border-radius: 6px;
  font-size: 26px;
  color: #333;
}

.visit-number {
  font-weight: bold;
  color: #d81e06;
}
</style>


