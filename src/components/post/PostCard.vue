<template>
  <view class="post-card-container" :class="{ featured }">
    <view v-if="featured" class="badge">精选</view>

    <nut-row type="flex" justify="start" align="center" class="user-info">
      <nut-col :span="4">
        <nut-avatar>
          <image :src="avatar" />
        </nut-avatar>
      </nut-col>
      <nut-col :span="20">
        <view class="user-meta">
          <text class="user-name">{{author}}</text>
          <text class="user-sub">{{ region }} · {{ identity }}</text>
        </view>
      </nut-col>
    </nut-row>

    <view class="post-content">
      <nut-ellipsis direction="end" rows="3" :content="content" />
    </view>

    <view class="post-footer">
      <text class="footer-item">👍 {{ likes }}</text>
      <text class="footer-item">💬 {{ comments }}</text>
    </view>
  </view>
</template>


<script setup lang="ts">

withDefaults(defineProps<{
  author: string;
  theme: string
  content: string
  region: string
  identity: string
  likes: number
  comments: number
  featured?: boolean
  avatar: string
}>(), {
  featured: false
})
</script>

<style lang="scss">
.user-info {
  margin-bottom: 12px;
}

.user-meta {
  display: flex;
  flex-direction: column;
  justify-content: center;
  padding-left: 10px;
}

.user-name {
  font-weight: 600;
  font-size: 30px;
}

.user-sub {
  font-size: 24px;
  color: #999;
  margin-top: 4px;
}

.post-content {
  margin: 30px 0;
  font-size: 30px;
  color: #333;
}

.post-footer {
  display: flex;
  justify-content: space-around;
  align-items: center;
  gap: 20px;
  font-size: 26px;
  color: #666;
}

.footer-item {
  display: inline-flex;
  align-items: center;
}

.post-card-container {
  position: relative;
  background: #fff;
  padding: 24px;
  margin: 20px;
  border-radius: 20px;
  box-shadow: 0 6px 16px rgba(0, 0, 0, 0.04);
  transition: box-shadow 0.3s ease;
}

.badge {
  position: absolute;
  top: 32px;
  right: 24px;
  background: #f37b1d;
  color: #fff;
  padding: 6px 14px;
  font-size: 22px;
  border-radius: 999px;
  z-index: 1;
}
</style>

