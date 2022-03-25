<template>
  <router-view v-if="this.$store.getters.siteInfo" />
</template>

<script>
import APIUtil from "./util/APIUtil";

export default {
  name: "App",
  created() {
    // 站点信息取得
    this.searchSiteInfo();

    // 窗体大小变更
    let me = this;
    window.onresize = function() {
      me.onWindowResize();
    };
    me.onWindowResize();
  },
  watch: {
    // 站点切换
    "$route.params.site_id": "searchSiteInfo"
  },
  methods: {
    // 站点信息取得
    searchSiteInfo() {
      var me = this;

      // 清除原站点信息
      this.$store.commit("siteInfo", null);

      // 取得站点信息
      APIUtil.findSiteInfo(this);
    },

    // 响应式类型设定
    onWindowResize: function() {
      if (window.document.body.clientWidth >= 1600) {
        this.$store.commit("responsiveMode", "xxl");
      } else if (window.document.body.clientWidth >= 1200) {
        this.$store.commit("responsiveMode", "xl");
      } else if (window.document.body.clientWidth >= 992) {
        this.$store.commit("responsiveMode", "lg");
      } else if (window.document.body.clientWidth >= 768) {
        this.$store.commit("responsiveMode", "md");
      } else if (window.document.body.clientWidth >= 576) {
        this.$store.commit("responsiveMode", "sm");
      } else {
        this.$store.commit("responsiveMode", "xs");
      }
    }
  }
};
</script>

<style lang="scss">
@import "./css/vxe-table.scss";
</style>