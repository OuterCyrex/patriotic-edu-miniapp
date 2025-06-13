<template>
  <nut-swiper
    class='test-h'
    :init-page="1"
    pagination-visible
    pagination-color="#426543"
    pagination-unselected-color="#808080"
  >
    <nut-swiper-item v-for="(item, idx) in props.images" :key="idx" class="swiper-image">
      <image :src="item" class="slide-image" mode="aspectFill"/>
    </nut-swiper-item>
  </nut-swiper>
</template>

<script setup lang="ts">
const props = defineProps<{
  images: string[];
}>()
</script>

<style lang="scss">
.slide-image {
  height: 250px;
  width: 100vw;
}
</style>
