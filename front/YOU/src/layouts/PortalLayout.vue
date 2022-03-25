<template>
  <Layout id="layout-container">
    <Header class="header-area">
      <div class="container header-container">
        <div class="logo">
          <img src="../assets/logo.png" height="70" />
        </div>
        <div class="title-area">
          <div id="mainTitle">{{ $t(this.$store.getters.siteInfo.siteName) }}</div>
          <div id="subTitle">{{ $t(this.$store.getters.siteInfo.siteSubName) }}</div>
        </div>
        <div
          v-if="this.$store.getters.siteInfo.addon"
          class="addon-area"
          v-html="this.$store.getters.siteInfo.addon.header"
        ></div>
        <div class="search-area">
          <Input
            v-if="this.$store.getters.siteInfo.addon.onSearch"
            search
            :placeholder="$t('s.common.btn.search')"
            on-search
            v-model="this.searchKey"
          />
        </div>
        <div class="menu-container">
          <u-menu
            ref="menu1"
            :data-list="this.$store.getters.menuInfo"
            :selected-menu-id="this.$store.getters.curMenuId"
            mode="horizontal"
            theme="light"
            @on-select="redirect"
          ></u-menu>
        </div>
      </div>
    </Header>
    <Content style="overflow:hidden">
      <!-- 栅格布局 -->
      <GridContent v-if="$store.getters.curPageDefine.pageInfo.pageType == '0'"></GridContent>
    </Content>
    <!-- 底部 -->
    <Footer v-if="this.$store.getters.siteInfo.addon" class="footer-area">
      <div class="container" v-html="this.$store.getters.siteInfo.addon.footer"></div>
    </Footer>
  </Layout>
</template>

<script>
import CommonUtil from "../util/CommonUtil";
import APIUtil from "../util/APIUtil";
import GridContent from "../contents/GridContent";

export default {
  components: {
    GridContent,
  },

  data: function () {
    return {
      searchKey: "",
    };
  },

  created() {
    // 初始化数据
    this.fetchData();
  },

  methods: {
    // 页面初期化
    fetchData() {
      APIUtil.findSiteMenu(this, 1);
    },
    // 菜单点击
    redirect: function (menuInfo) {
      CommonUtil.menuClickHandler(this, menuInfo);
    },
    // 检索处理
    search: function () {
      ExpressionUtil.eval(
        this,
        this.$store.getters.siteInfo.onSearch,
        this.searchKey
      );
    },
  },
};
</script>


<style scoped>
.container {
  margin: 0px auto;
  width: 1400px;
}

/deep/ .portal-card {
  width: 1400px;
  margin: 0px auto;
  padding: 16px 0px;
}

/deep/ .portal-card .ivu-card.portal {
  border-radius: 25px;
}

/deep/ .portal-card .ivu-card.portal .ivu-card-head {
  background-color: #e9e9e9;
  border-top-left-radius: 25px;
  border-top-right-radius: 25px;
}

/deep/ .portal-card .transparent-card.portal .ivu-card-head {
  background-color: transparent;
}

/deep/ .portal-card .ivu-card.portal .ivu-card-head p {
  font-size: 20px;
  color: #919191;
}

.header-area {
  background-color: #f3f3f3;
  padding: 0px;
  height: auto;
  line-height: normal;
}

.header-container {
  height: 100px;
}

.header-container .logo {
  float: left;
  padding-top: 15px;
  padding-left: 10px;
}

.header-container .title-area {
  float: left;
  margin-top: 29px;
  margin-left: 10px;
  padding-left: 10px;
  color: #2e89d1;
  border-left: 2px solid #dfe3e9;
}

.header-container .title-area #mainTitle {
  font-size: 24px;
  font-weight: bolder;
  font-family: "微软雅黑", Courier, monospace;
}

.header-container .title-area #subTitle {
  font-size: 15px;
  font-weight: bolder;
  font-family: "Courier New", Courier, monospace;
}

.header-container .menu-container {
  float: right;
  margin-top: 40px;
}

.header-container .menu-container .ivu-menu-light,
.header-container .menu-container .ivu-menu-horizontal.ivu-menu-light:after {
  background: transparent;
}

.ivu-menu-horizontal {
  height: auto;
  line-height: normal;
}

.ivu-menu /deep/ > .ivu-menu-item,
.ivu-menu /deep/ > .ivu-menu-submenu {
  /* width: 100px; */
  padding: 0px 15px;
  text-align: center;
  font-size: 16px;
  font-weight: bolder;
  border-right: 1px solid #dddddd;
}

.ivu-menu /deep/ > li:last-child {
  border-right: 0px;
}

.menu-container /deep/ .ivu-menu-item-active,
.menu-container /deep/ .ivu-menu-item-selected,
.menu-container /deep/ .ivu-menu-item:hover,
.menu-container /deep/ .ivu-menu-submenu:hover {
  color: #597694 !important;
  border-bottom: 0px;
}

.menu-container /deep/ .ivu-menu-light.ivu-menu-horizontal .ivu-menu-item,
.menu-container /deep/ .ivu-menu-light.ivu-menu-horizontal .ivu-menu-submenu {
  color: #ababab;
  border-bottom: 0px;
}

/deep/ .ivu-menu-submenu-title span > i,
/deep/ .ivu-menu-submenu-title > i {
  margin-right: 0px;
}

.menu-container
  /deep/
  .ivu-menu-light.ivu-menu-horizontal
  .ivu-menu-drop-list
  .ivu-menu-item,
.menu-container
  /deep/
  .ivu-menu-light.ivu-menu-horizontal
  .ivu-menu-drop-list
  .ivu-menu-submenu {
  color: #597694;
  border-bottom: none;
  text-align: left;
  font-weight: normal;
}

.header-container .search-area {
  float: right;
  font-size: 16px;
  margin: 35px 25px 0px 10px;
}

.header-container .search-area /deep/ .ivu-input {
  border-radius: 16px;
  padding-left: 17px;
  padding-right: 37px;
}

.header-container .search-area /deep/ .ivu-icon-ios-search {
  right: 5px;
}

.header-container .addon-area {
  float: right;
  font-size: 16px;
  margin-top: 40px;
}

.header-container .addon-area /deep/ a {
  margin: 0px 25px 0px 5px;
  font-weight: bolder;
  color: #ababab;
}

.header-container .addon-area /deep/ a:last-child {
  margin: 0px 5px 0px 5px;
}

.header-container .addon-area /deep/ a:hover {
  color: #ababab;
}

.footer-area {
  background: #0b66ae;
  padding: 3px 0px;
  color: #fff;
  text-align: center;
}

/deep/ .ivu-cell-selected {
  border-left: 3px solid #0b66ae;
}

@media (max-width: 576px) {
  .container {
    width: 100%;
  }

  /deep/ .portal-card {
    width: 100%;
  }

  .header-container {
    height: auto;
  }

  .header-container .logo {
    float: none;
    padding: 10px 10px 0px 10px;
    width: 100%;
    text-align: left;
  }

  .header-container .logo img {
    height: 30px;
  }

  .header-container .title-area {
    float: none;
    margin: 0px;
    padding: 0px 0px 10px 0px;
    border: 0px;
    text-align: center;
  }

  .header-container .search-area {
    float: right;
    margin: 0px;
    width: 60%;
    text-align: center;
    padding: 6px 15px;
    height: 45px;
    background: #0a3963;
  }

  .header-container .addon-area {
    float: right;
    margin: 0px;
    width: 40%;
    text-align: center;
    padding-top: 9px;
    height: 45px;
    background: #0a3963;
    color: white;
  }

  .header-container .menu-container {
    float: none;
    height: 33px;
    margin: 45px 0px 00px 0px;
    padding-top: 4px;
    border-bottom: 1px solid #dddddd;
  }

  .header-container .addon-area /deep/ a {
    margin: 0px 25px 0px 5px;
    color: white;
  }

  .header-container .addon-area /deep/ a:hover {
    color: white;
  }

  .banner {
    display: none;
  }
}
</style>