<template>
  <div id="loginContainer">
    <div id="localeBtn">
      <u-locale></u-locale>
    </div>
    <Card shadow class="loginArea">
      <Row class="titleArea">
        <Col class="title">{{ $t(this.$store.getters.siteInfo.siteName) }}</Col>
        <Col class="subtitle">{{ $t(this.$store.getters.siteInfo.siteEngName) }}</Col>
      </Row>

      <Row class="loginTypeArea">
        <Col span="24">
          <Tabs class="loginTabs">
            <TabPane
              v-if="this.$store.getters.siteInfo.addon && this.$store.getters.siteInfo.addon.qywxLogin"
              :label="$t('s.login.type.qywx')"
            >
              <Row>
                <Col style="text-align: center">
                  <div id="wx_qrcode"></div>
                </Col>
              </Row>
            </TabPane>
            <TabPane :label="$t('s.login.type.userpass')">
              <Form class="loginFormArea" ref="loginForm" :model="formItem" :rules="validateRule">
                <FormItem prop="userId">
                  <Input
                    v-model="formItem.userId"
                    :placeholder="$t('s.user.login.user_id')"
                    :disabled="this.$store.getters.isLoading"
                    @on-enter="login"
                  >
                    <Icon class="icon" type="ios-person" slot="prepend"></Icon>
                  </Input>
                </FormItem>
                <FormItem prop="password">
                  <Input
                    type="password"
                    v-model="formItem.password"
                    :placeholder="$t('s.user.login.password')"
                    :disabled="this.$store.getters.isLoading"
                    @on-enter="login"
                  >
                    <Icon class="icon" type="ios-lock" slot="prepend"></Icon>
                  </Input>
                </FormItem>
                <Row>
                  <Col style="text-align: center">
                    <Button
                      type="primary"
                      @click="login"
                      :disabled="this.$store.getters.isLoading"
                    >{{ $t('s.common.btn.login') }}</Button>
                  </Col>
                </Row>
              </Form>
            </TabPane>
          </Tabs>
        </Col>
      </Row>
    </Card>
  </div>
</template>

<script>
import APIUtil from "../util/APIUtil";
import ProcessUtil from "../util/ProcessUtil";

export default {
  data() {
    return {
      formItem: {
        userId: "",
        password: ""
      },
      validateRule: {
        userId: [
          {
            required: true,
            message: [this.$t('s.user.login.user_id_required')]
          }
        ],
        password: [
          {
            required: true,
            message: [this.$t('s.user.login.password_required')]
          }
        ]
      }
    };
  },
  mounted: function() {
    // 清空当前页面定义
    this.$store.commit("clearCurPage");

    // 
    if (this.$route.query.code) {
      debugger;
      this.$router.replace(this.generateNextUrl());
      return;
    }

    // 生成企业微信登陆二维码
    if (
      this.$store.getters.siteInfo.addon &&
      this.$store.getters.siteInfo.addon.qywxLogin
    ) {
      window.WwLogin({
        id: "wx_qrcode",
        appid: this.$store.getters.externalInfo.qyapiCorpid,
        agentid: this.$store.getters.externalInfo.qyapiAgentid,
        redirect_uri: encodeURIComponent(window.location.origin + this.generateNextUrl()),
        state: "qywx"
      });
    }
  },
  methods: {
    // 用户登录处理
    login() {
      this.$refs["loginForm"].validate(valid => {
        if (valid) {
          // 处理开始
          var me = this;
          APIUtil.userAuth(
            this,
            this.formItem.userId,
            this.formItem.password
          ).then(result => {
            if (result) {
              me.$router.replace(this.generateNextUrl());
            }
          });
        }
      });
    },
    // 生成下一个页面
    generateNextUrl() {
      // 次页面
      var nextPage = this.$store.getters.siteInfo.defaultPageid;

      // 页面组ID
      var groupId = this.$route.params.group_id;
      // 页面ID
      var pageId = this.$route.params.page_id;
      // 站点ID
      var siteId = this.$route.params.site_id;
      if (groupId) {
        nextPage = "/" + groupId + "/" + pageId;
        if (siteId) {
          nextPage = "/" + siteId + nextPage;
        }
      }

      if (this.$route.query) {
        let isFirst = true;
        for (let key in this.$route.query) {
          if (isFirst) {
            nextPage = nextPage + "?";
          } else {
            nextPage = nextPage + "&";
          }
          nextPage = nextPage + key + "=" + this.$route.query[key];
          isFirst = false;
        }
      }

      return nextPage;
    }
  }
};
</script>

<style scoped>
#loginContainer {
  position: fixed;
  height: 100%;
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: Center;
  background-image: url("../assets/loginBack.webp");
  background-repeat: no-repeat;
  background-size: 100% 100%;
}

@media (max-width: 576px) {
  #loginContainer {
    background-image: url("../assets/loginBack2.jpg");
  }
}

#loginContainer #localeBtn {
  position: absolute;
  top: 32px;
  right: 32px;
}

#loginContainer .loginArea {
  background-color: rgba(0, 0, 0, 0.6);
  width: 420px;
  border-radius: 5px;
  padding: 0px;
}

#loginContainer .titleArea {
  margin: 5px 0px 20px 0px;
}

#loginContainer .titleArea .title {
  text-align: center;
  color: rgba(255, 255, 255, 0.8);
  font-size: 20px;
}

#loginContainer .titleArea .subtitle {
  text-align: center;
  color: rgba(255, 255, 255, 0.8);
  font-size: 14px;
}

#loginContainer .loginTypeArea {
  background-color: rgba(255, 255, 255, 0.8);
  border-radius: 5px;
  padding: 0px 5px;
  margin: 20px 0px 0px 0px;
}

#loginContainer .loginTabs /deep/ .ivu-tabs-nav {
  float: none;
  display: flex;
}

#loginContainer .loginTabs /deep/ .ivu-tabs-nav .ivu-tabs-tab {
  flex: 1;
  text-align: center;
}

#loginContainer .loginFormArea {
  padding: 15px 50px 25px 50px;
}

#loginContainer .ivu-input-group-prepend {
  background-color: rgba(0, 0, 0, 0.3);
  border: 1px solid rgba(255, 255, 255, 0.3);
  border-right: 0px;
  color: rgba(255, 255, 255, 0.8);
}

#loginContainer .icon {
  font-size: 16px;
}

#loginContainer input {
  background-color: rgba(0, 0, 0, 0.3);
  border: 1px solid rgba(255, 255, 255, 0.3);
  color: rgba(255, 255, 255, 0.8);
}

#loginContainer button {
  width: 100px;
}
</style>
