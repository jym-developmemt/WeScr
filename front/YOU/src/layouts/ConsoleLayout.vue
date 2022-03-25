<template>
  <Layout id="layout-container">
    <Icon
      @click="showMenu()"
      v-if="this.$store.state.sider_menu_collapsed"
      type="ios-menu"
      class="clickable font-size-28"
      style="position:absolute;z-index:999;color:white;line-height:60px;left:20px;"
    />
    <Header class="header-area">
      <Menu mode="horizontal" theme="dark">
        <div class="header-container">
          <div class="logo" v-if="!this.$store.state.sider_menu_collapsed">
            <img src="../assets/logo.png" />
          </div>
          <div class="title-area" :style="this.$store.state.sider_menu_collapsed?'border:0px':''">
            <div id="mainTitle">{{ $t(this.$store.getters.siteInfo.siteName) }}</div>
            <div id="subTitle">{{ $t(this.$store.getters.siteInfo.siteSubName) }}</div>
          </div>
        </div>
        <div v-if="this.$store.getters.userInfo" class="layout-nav">
          <MenuItem name="1" v-if="this.$store.getters.siteInfo.messagePageid">
            <router-link :to="this.$store.getters.siteInfo.messagePageid">
              <Badge class="notifications" :count="messageCnt">
                <Icon type="ios-notifications" size="26" />
              </Badge>
            </router-link>
          </MenuItem>
          <MenuItem name="2" v-if="this.$store.getters.siteInfo.messagePageid">
            <Icon type="ios-contact" size="26" />
            {{ this.$store.getters.userInfo.userName }}
          </MenuItem>
          <MenuItem name="3" v-if="!this.$store.state.sider_menu_collapsed">
            <a class="exitlink" @click="logout()">{{ $t('s.common.btn.logout') }}</a>
          </MenuItem>
          <MenuItem name="4" v-if="!this.$store.state.sider_menu_collapsed">
            <u-locale></u-locale>
          </MenuItem>
        </div>
      </Menu>
    </Header>
    <Layout style="position: relative;">
      <!-- 用户菜单 -->
      <Sider
        :breakpoint="this.breakpoint"
        collapsible
        collapsed-width="0"
        class="menu-area"
        v-model="$store.state.sider_menu_collapsed"
      >
        <u-menu
          ref="menu1"
          :data-list="this.$store.getters.menuInfo"
          :selected-menu-id="this.$store.getters.curMenuId"
          @on-select="redirect"
        ></u-menu>
      </Sider>

      <!-- 用户菜单 -->
      <Drawer
        width="200"
        placement="left"
        :closable="false"
        v-model="$store.state.drawer_menu_shown"
        :styles="menuStyle"
      >
        <u-menu
          ref="menu2"
          :data-list="this.$store.getters.menuInfo"
          :selected-menu-id="this.$store.getters.curMenuId"
          @on-select="redirect"
        ></u-menu>
      </Drawer>

      <!-- 栅格布局 -->
      <GridContent v-if="$store.getters.curPageDefine.pageInfo.pageType == '0'"></GridContent>
    </Layout>
  </Layout>
</template>

<script>
import CommonUtil from "../util/CommonUtil";
import APIUtil from "../util/APIUtil";
import GridContent from "../contents/GridContent";
import SockJS from "sockjs-client";
import Stomp from "stompjs";

export default {
  components: {
    GridContent,
  },

  data() {
    return {
      messageCnt: 0,
      intervalId: null,
      menuStyle: {
        padding: "0px",
        backgroundColor: "#515a6e",
      },
      breakpoint: "sm",
      stompClient: null,
      noticeMethod: ["open", "info", "success", "warning", "error"],
      dimensionMap: {
        xs: "480px",
        sm: "576px",
        md: "768px",
        lg: "992px",
        xl: "1200px",
        xxl: "1600px",
      },
    };
  },

  created() {
    // 初始化数据
    this.fetchData();

    // 初始化WebSocket连接
    // this.initSocket();
  },

  beforeDestroy() {
    if (this.intervalId) {
      clearInterval(this.intervalId);
    }
    if (this.stompClient) {
      this.stompClient.disconnect();
    }
  },

  methods: {
    // 页面初期化
    fetchData() {
      APIUtil.findSiteMenu(this, 2);
    },

    // 初始化WebSocket连接
    initSocket: function () {
      // 创建WebSocket对像
      let socket = new SockJS(process.env.BASE_URL_NOTICE + "/ws");
      // 限制连接协议
      socket._transportsWhitelist = [
        "jsonp-polling",
        "xdr-polling",
        "xhr-polling",
        "xhr-streaming",
        "xdr-streaming",
      ];
      let stompClient = Stomp.over(socket);

      let header = {
        "access-token": this.$store.getters.accessToken,
      };

      // 创建WebSocket连接
      stompClient.connect(
        header,
        () => {
          // 保存连接
          this.stompClient = stompClient;

          // 当前用户信息
          let loginUser = this.$store.getters.userInfo;

          // 监听用户ID
          stompClient.subscribe("/topic/" + loginUser.userId, (response) => {
            if (response.body) {
              // 通知信息
              let jsonResult = JSON.parse(response.body);
              // 通知件数
              if (jsonResult.noticeCnt != null) {
                this.messageCnt = jsonResult.noticeCnt;
              }
              // 表示通知
              if (jsonResult.notice) {
                this.$Notice[this.noticeMethod[jsonResult.notice.iconType]]({
                  name: jsonResult.notice.noticeId,
                  title: jsonResult.notice.noticeTitle,
                  duration: 10,
                  render: (h) => {
                    // 通知内容
                    var noticeContent = [];
                    if (jsonResult.notice.noticeContent) {
                      noticeContent.push(
                        h("span", {
                          domProps: {
                            innerHTML: CommonUtil.replaceAll(
                              jsonResult.notice.noticeContent,
                              "\n",
                              "<br>"
                            ),
                          },
                        })
                      );
                      noticeContent.push(h("br"));
                    }
                    // 详细信息链接
                    if (jsonResult.notice.noticeLink) {
                      noticeContent.push(h("br"));
                      noticeContent.push(
                        h(
                          "a",
                          {
                            on: {
                              click: () => {
                                // 关闭当前通知
                                this.$Notice.close(jsonResult.notice.noticeId);

                                // 跳转到详情页面
                                let siteId = this.$route.params.site_id;
                                if (siteId) {
                                  this.$router.push(
                                    "/" + siteId + jsonResult.notice.noticeLink
                                  );
                                } else {
                                  this.$router.push(
                                    jsonResult.notice.noticeLink
                                  );
                                }
                              },
                            },
                          },
                          "详细信息"
                        )
                      );
                    }
                    return h("span", noticeContent);
                  },
                });
              }
            }
          });

          // 用户登陆通知
          stompClient.send("/app/list", header, loginUser.userId);
        },
        (err) => {
          // 10秒重连
          setTimeout(this.initSocket, 10000);
        }
      );
    },

    // 菜单点击
    redirect: function (menuInfo) {
      CommonUtil.menuClickHandler(this, menuInfo);
      this.$store.state.drawer_menu_shown = false;
    },
    // 显示菜单栏
    showMenu() {
      let matchMedia = window.matchMedia;
      if (matchMedia(`(max-width: ${this.dimensionMap[this.breakpoint]})`).matches) {
        this.$store.state.drawer_menu_shown = true;
      } else {
        this.$store.state.sider_menu_collapsed = false;
      }
    },
    // 退出
    logout() {
      if (this.intervalId) {
        clearInterval(this.intervalId);
      }
      if (this.stompClient) {
        this.stompClient.disconnect();
      }
      this.$store.commit("logout");

      let siteId = this.$route.params.site_id;
      if (siteId) {
        window.location.href = "/" + siteId;
      } else {
        window.location.href = "/";
      }
    },
  },
};
</script>


<style scoped>
#layout-container {
  position: fixed;
  display: flex;
  width: 100%;
  height: 100%;
}

.header-area {
  padding: 0px 25px;
}

.header-area .ivu-menu-dark {
  background: transparent;
}

/deep/ .ivu-badge-count {
  height: 15px;
  width: 15px;
  min-width: 15px;
  padding: 0px;
  line-height: 13px;
}

.header-container {
  float: left;
}

.header-container > div {
  float: left;
}

.header-container .logo {
  height: 55px;
  margin-top: 4px;
}

.header-container .logo img {
  height: 55px;
  line-height: normal;
}

.header-container .title-area {
  margin-top: 10px;
  margin-left: 15px;
  padding-left: 15px;
  border-left: 2px solid white;
}

.header-container .title-area #mainTitle {
  font-size: 20px;
  font-family: "Helvetica Neue", Helvetica, Arial, sans-serif;
  font-weight: bolder;
  color: white;
  line-height: normal;
}

.header-container .title-area #subTitle {
  font-size: 13px;
  color: white;
  line-height: normal;
}

.mainTitle {
  /* width: 100px; */
  height: 30px;
  line-height: 30px;
  /* background: #5b6270; */
  border-radius: 3px;
  float: left;
  position: relative;
  top: 15px;
  left: 20px;
  color: white;
  font-weight: bolder;
  font-size: 26px;
}

.layout-nav {
  float: right;
}

.ivu-layout {
  overflow: auto;
}

.notifications {
  display: inline;
}

.exitlink {
  color: rgba(255, 255, 255, 0.7);
}

.exitlink:hover {
  color: #fff;
  text-decoration: underline;
}

.menu-area {
  background: #515a6e;
  overflow: auto;
  margin-bottom: 18px;
}

.menu-area /deep/ .ivu-layout-sider-trigger {
  height: 18px;
  line-height: 18px;
}

.menu_btn {
  position: absolute;
  top: 15px;
  left: 15px;
}

.ivu-menu /deep/ .ivu-menu-item > span > i,
.ivu-menu /deep/ .ivu-menu-submenu > .ivu-menu-submenu-title > span > i {
  margin-right: 8px;
  width: 16px;
  text-align: center;
}

@media (max-width: 576px) {
  .header-container .title-area {
    padding-left: 0px;
    border-left: 0px;
  }
  .header-area {
    padding: 0px 50px;
  }
  .ivu-layout-header {
    padding: 0px 0px 0px 50px;
  }
}
</style>