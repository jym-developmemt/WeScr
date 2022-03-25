<template>
  <!-- 页面标题 -->
  <div v-if="visible" class="page-header">
    <div class="page-header-title">{{ this.$t(this.$store.getters.curPageDefine.pageInfo.pageName) }}</div>
    <div class="page-header-content" v-html="this.$t(this.$store.getters.curPageDefine.pageInfo.pageDescription)"></div>
  </div>
</template>

<script>
export default {
  name: "PageTitleComponent",
  computed: {
    visible() {
      var pageAddon = this.$store.getters.curPageDefine.pageInfo.pageAddon;
      if (pageAddon) {
        if (pageAddon.hiddenTitle == true || pageAddon.hiddenTitle == '1') {
          return false;
        }
      }
      return true;
    }
  },
};
</script>

<style scoped>
.page-header {
  padding: 16px 32px 0 32px;
  background: #fff;
  border-bottom: 1px solid #e8eaec;
  margin-bottom: 8px;
}
.page-header-title {
  color: #17233d;
  font-weight: 500;
  font-size: 20px;
  margin-bottom: 16px;
}
.page-header-content {
  margin-bottom: 16px;
  font-size: 14px;
}
</style>