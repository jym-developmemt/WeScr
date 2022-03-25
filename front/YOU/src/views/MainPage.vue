<template>
  <div v-if="this.$store.getters.curPageDefine">
    <!-- 门户布局 -->
    <PortalLayout
      v-if="this.$store.getters.curPageGroupDefine.pageStyle == '1'"
    ></PortalLayout>
    <!-- 控制台布局 -->
    <ConsoleLayout
      v-if="this.$store.getters.curPageGroupDefine.pageStyle == '2'"
    ></ConsoleLayout>
  </div>
</template>

<script>
import APIUtil from "../util/APIUtil";
import CommonUtil from "../util/CommonUtil";
import ProcessUtil from "../util/ProcessUtil";
import PortalLayout from "../layouts/PortalLayout";
import ConsoleLayout from "../layouts/ConsoleLayout";
import ExpressionUtil from "../util/ExpressionUtil";

export default {
  name: "MainPage",
  components: {
    PortalLayout,
    ConsoleLayout,
  },
  data() {
    return {
      intervalId: null,
    };
  },
  created() {
    // 初始化数据
    this.fetchData();
  },
  beforeDestroy() {
    // 清除循环
    if (this.intervalId) {
      clearInterval(this.intervalId);
    }
  },
  watch: {
    // 初始化数据
    $route: "fetchData",
  },
  methods: {
    // 页面跳转处理
    fetchData(newRoute, oldRoute) {
      if (newRoute && oldRoute && newRoute.path == oldRoute.path) {
        return;
      }

      // 预授权处理
      var preauthProcess = CommonUtil.preAuth(this);
      if (preauthProcess == null) {
        // 无预授权时，直接加载页面
        this.getPageInfo();
      } else {
        // 认证成功后处理
        preauthProcess.then((result) => {
          // 认证成功时加载页面
          if (result) {
            this.getPageInfo();
          }
        });
      }
    },

    getPageInfo() {
      // 页面组ID
      var groupId = this.$route.params.group_id;
      // 页面ID
      var pageId = this.$route.params.page_id;
      // 站点ID
      var siteId = this.$route.params.site_id;

      // 页面ID未设定时
      if (pageId == null || pageId == "") {
        // 跳转到默认页面
        if (siteId) {
          this.$router.push(
            "/" + siteId + this.$store.getters.siteInfo.defaultPageid
          );
        } else {
          this.$router.push(this.$store.getters.siteInfo.defaultPageid);
        }
        return;
      }

      // 保存请求的页面ID
      let requestPageId = "/" + groupId + "/" + pageId;
      if (siteId) {
        requestPageId = "/" + siteId + requestPageId;
      }
      if (this.$route.query) {
        let isFirst = true;
        for (let key in this.$route.query) {
          if (isFirst) {
            requestPageId = requestPageId + "?";
          } else {
            requestPageId = requestPageId + "&";
          }
          requestPageId = requestPageId + key + "=" + this.$route.query[key];
          isFirst = false;
        }
      }
      this.$store.commit("requestPageId", requestPageId);

      // 请求一览
      var processList = new Array();

      // 页面组定义信息取得
      var groupInfo = this.$store.getters.pageGroupDefine[groupId];
      if (process.env.NODE_ENV == "development" || groupInfo == null) {
        processList.push(APIUtil.findPageGroup(this, groupId));
      }

      // 页面定义信息取得
      var pageInfo = this.$store.getters.pageInfoDefine[groupId + "_" + pageId];
      if (process.env.NODE_ENV == "development" || pageInfo == null) {
        processList.push(APIUtil.findPageInfo(this, groupId, pageId));
      }

      // 请求结果
      var me = this;
      Promise.all(processList).then(function (resultDataArray) {
        // 页面切换
        if (CommonUtil.checkProceeResult(resultDataArray)) {
          // 清除循环
          if (me.intervalId) {
            clearInterval(me.intervalId);
          }

          me.$store.commit("changePage", [groupId, pageId, me]);

          // 页面初始化
          me.pageInit(groupId, pageId);
        }
      }).catch(function(e){
        alert(e);
      });
    },

    /**
     * 页面初始化
     */
    pageInit(groupId, pageId) {
      // 处理开始
      var me = this;
      var processArray = new Array();
      
      var callback = "for(var i=0;a1&&i<a1.length;i++){"
      callback += "v.$set(pd,'d'+a2[i].stringData3,a1[i]);";

      // 页面数据源
      if (this.$store.getters.curPageDefine) {
        var pageDataSourceList = this.$store.getters.curPageDefine
          .dataSourceList;
        for (let i = 0; i < pageDataSourceList.length; i++) {
          var dsInfo = pageDataSourceList[i];
          if (dsInfo.initSearchFlg != "1") {
            continue;
          }

          var pageCond = null;
          var pageInfo = null;
          if (dsInfo.searchType == '2' && dsInfo.datasourceAddon.initPageInfo) {
            pageCond = ExpressionUtil.createObject(
              me,
              dsInfo.datasourceAddon.initPageCondition
            );

            pageInfo = ExpressionUtil.createObject(
              me,
              dsInfo.datasourceAddon.initPageInfo
            );

            callback += "if(i=="+i+") {";
            callback += dsInfo.datasourceAddon.initPageInfo + "=a1[i].pageInfo;";
            callback += "}";
          }

          processArray.push({
            t: "P00",
            s1: groupId,
            s2: pageId,
            s3: dsInfo.datasourceIndex,
            o1: pageCond,
            o2: pageInfo,
            o3: dsInfo.datasourceAddon,
          });
        }
      }

      callback += "}";

      if (processArray.length == 0) {
        me.executeInit();
      } else {
        // 数据取得
        var util = new ProcessUtil();
        util.vueinstance = this;
        util
          .sendL(
            processArray,
            callback
          )
          .then(() => {
            me.executeInit();
          });
      }
    },

    executeInit() {
      var pageAddon = this.$store.getters.curPageDefine.pageInfo.pageAddon;

      // 页面初始化表达式
      var initExpression = pageAddon.init;
      if (initExpression) {
        ExpressionUtil.eval(this, initExpression);
      }

      // 页面定时循环
      var intervalExpression = pageAddon.interval;
      if (intervalExpression) {
        var time = 10000;
        if (pageAddon.intervalTime) {
          time = pageAddon.intervalTime * 1000;
        }
        this.intervalId = setInterval(function () {
          ExpressionUtil.eval(this, intervalExpression);
        }, time);
      }
    },
  },
};
</script>
