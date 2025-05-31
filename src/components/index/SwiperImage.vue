<template>
  <swiper
    class='test-h'
    indicatorColor='#999'
    indicatorActiveColor='#333'
    current="current"
    :autoplay="true"
    :indicatorDots="true">
    <swiper-item v-for="(item, idx) in props.images" :key="idx" class="swiper-image">
      <Image :src="item" class="slide-image"/>
    </swiper-item>
  </swiper>
</template>

<script setup lang="ts">
const props = defineProps<{
  images: string[];
}>()
</script>

<style lang="scss">
.slide-image {
  height: 400px;
  width: 100vw;
  display: block;
}
</style>
