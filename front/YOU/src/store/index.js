import Vue from 'vue'
import Vuex from 'vuex'
import ViewUI from 'view-design'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    // 保存的定义信息
    savedDefine: {
      // 站点信息
      siteInfo: null,
      // 外部信息
      externalInfo: null,
      // 菜单信息
      menuInfo: [],
      // 页面组定义
      pageGroup: {},
      // 页面定义
      pageInfo: {},
      // 多语言信息
      languages: [],
      // 系统消息
      messages: {}
    },
    // 登陆信息
    loginInfo: {
      // Token
      accessToken: null,
      // 言语ID
      localeId: null,
      // 用户信息
      userInfo: null,
    },
    // 当前页面定义信息
    current: {
      // 菜单ID
      menuId: "",
      // 页面ID
      pageId: "",
      // 页面组定义
      pageGroup: {},
      // 页面定义
      pageInfo: null,
    },
    // 数据
    data: {
      // 全局数据
      global: {},
      // 页面组数据
      pageGroup: {},
      // 页面数据
      page: {},
      // 布局属性
      layoutProperty: {},
      // 控件实例
      componetInstance: {},
      // 控件属性
      elementProperty: {},
    },

    // 加载个数
    loading_cnt: 0,
    // 菜单展开状态
    sider_menu_collapsed: false,
    // 菜单展开状态
    drawer_menu_shown: false,
    // 响应式类型
    responsive_mode: null,
  },
  getters: {
    // 是否正在加载
    isLoading: (state) => {
      if (state.loading_cnt > 0) {
        return true;
      } else {
        return false;
      }
    },

    // 取得站点信息
    siteInfo: (state) => {
      return state.savedDefine.siteInfo;
    },
    // 取得外部信息
    externalInfo: (state) => {
      return state.savedDefine.externalInfo;
    },
    // 取得菜单信息
    menuInfo: (state) => {
      return state.savedDefine.menuInfo;
    },
    // 取得页面组定义信息
    pageGroupDefine: (state) => {
      return state.savedDefine.pageGroup;
    },
    // 取得页面定义信息
    pageInfoDefine: (state) => {
      return state.savedDefine.pageInfo;
    },
    // 取得言语信息
    languages: (state) => {
      return state.savedDefine.languages;
    },
    // 取得系统消息
    messages: (state) => {
      return state.savedDefine.messages;
    },

    // 取得Token信息
    accessToken: (state) => {
      return state.loginInfo.accessToken;
    },
    // 取得当前语言
    localeId: (state) => {
      if (state.loginInfo.localeId) {
        return state.loginInfo.localeId;
      }

      // 浏览器保存token
      if (window.localStorage && window.localStorage.getItem("locale")) {
        return window.localStorage.getItem("locale").split(",");
      }
      // APP保存token
      if (window.plus && window.plus.storage.getItem("locale")) {
        return window.plus.storage.getItem("locale").split(",");
      }
      return [];
    },
    // 取得用户信息
    userInfo: (state) => {
      return state.loginInfo.userInfo;
    },

    // 取得当前页面ID
    curMenuId: (state) => {
      return state.current.menuId;
    },
    // 取得当前页面ID
    curPageId: (state) => {
      return state.current.pageId;
    },
    // 取得当前页面组信息
    curPageGroupDefine: (state) => {
      return state.current.pageGroup;
    },
    // 取得当前页面信息
    curPageDefine: (state) => {
      return state.current.pageInfo;
    },

    // 取得全局变量
    globalData: (state) => {
      return state.data.global;
    },
    // 取得当前页面组数据
    pageGroupData: (state) => {
      return state.data.pageGroup;
    },
    // 取得当前页面数据
    pageData: (state) => {
      return state.data.page;
    },
    // 取得当前页面数据
    layoutPropData: (state) => {
      return state.data.layoutProperty;
    },
    // 取得当前页面数据
    elementInstance: (state) => {
      return state.data.componetInstance;
    },
    // 取得当前页面数据
    elementPropData: (state) => {
      return state.data.elementProperty;
    },
    // 取得响应式类型
    responsiveMode: (state) => {
      return state.responsive_mode;
    },
  },
  mutations: {
    // 站点信息保存
    siteInfo(state, siteInfo) {
      if (siteInfo && siteInfo.addon) {
        siteInfo.addon = JSON.parse(siteInfo.addon);
      }
      if (siteInfo) {
        document.title = siteInfo.siteName;
      }
      state.savedDefine.siteInfo = siteInfo;
    },
    // 外部信息保存
    externalInfo(state, externalInfo) {
      state.savedDefine.externalInfo = externalInfo;
    },
    // 菜单信息保存
    menuInfo(state, menuInfo) {
      state.savedDefine.menuInfo = menuInfo;
      if (state.current.pageGroup.menuId) {
        state.current.menuId = state.current.pageGroup.menuId;
      }
    },
    // 画面组定义保存
    pageGroupDefine(state, pageGroupInfo) {
      state.savedDefine.pageGroup[pageGroupInfo.groupId] = pageGroupInfo;
    },
    // 画面定义保存
    pageInfoDefine(state, data) {
      var pageInfo = data.pageInfo;
      // 页面附加信息
      if (pageInfo.pageAddon) {
        pageInfo.pageAddon = JSON.parse(pageInfo.pageAddon);
      } else {
        pageInfo.pageAddon = {};
      }
      state.savedDefine.pageInfo[pageInfo.groupId + "_" + pageInfo.pageId] = data;
    },
    // 言语信息保存
    languages(state, languages) {
      state.savedDefine.languages = languages;
    },
    // 系统消息保存
    messages(state, messageInfo) {
      var iviewMessage = require("view-design/dist/locale/" + messageInfo.locale).default;
      var vtableMessage = require("vxe-table/lib/locale/lang/" + messageInfo.locale).default;
      var siteMessage = messageInfo.messages;
      var messages = Object.assign(siteMessage, iviewMessage, vtableMessage)
      state.savedDefine.messages[messageInfo.locale] = messages;
    },
    // 保存用户登陆信息
    accessToken(state, accessToken) {
      state.loginInfo.accessToken = accessToken;
      // 浏览器保存token
      if (window.sessionStorage) {
        window.sessionStorage.setItem("accessToken", accessToken);
      }
      // APP保存token
      if (window.plus) {
        window.plus.storage.setItem("accessToken", accessToken);
      }
    },
    // 保存用户登陆信息
    loginInfo(state, loginInfo) {
      state.loginInfo.userInfo = loginInfo.userInfo;
      if (loginInfo.locale) {
        state.loginInfo.localeId = loginInfo.locale;
      }
      // 浏览器保存token
      if (window.sessionStorage) {
        window.sessionStorage.setItem("userInfo", JSON.stringify(loginInfo.userInfo));
        if (loginInfo.locale) {
          window.sessionStorage.setItem("locale", loginInfo.locale.join(","));
        }
      }
      // APP保存token
      if (window.plus) {
        window.plus.storage.setItem("userInfo", JSON.stringify(loginInfo.userInfo));
        if (loginInfo.locale) {
          window.plus.storage.setItem("locale", loginInfo.locale.join(","));
        }
      }
    },
    // 保存当前语言
    localeId: (state, localeId) => {
      state.loginInfo.localeId = localeId;
      // 浏览器保存locale
      if (window.sessionStorage) {
        window.sessionStorage.setItem("locale", localeId.join(","));
      }
      // APP保存locale
      if (window.plus) {
        window.plus.storage.setItem("locale", localeId.join(","));
      }
    },
    // 删除用户登陆信息
    logout(state) {
      state.loginInfo.accessToken = null;
      state.loginInfo.userInfo = null;
      // 浏览器保存token
      if (window.sessionStorage) {
        window.sessionStorage.removeItem("accessToken");
        window.sessionStorage.removeItem("userInfo");
        window.sessionStorage.removeItem("locale");
      }
      // APP保存token
      if (window.plus) {
        window.plus.storage.removeItem("accessToken");
        window.plus.storage.removeItem("userInfo");
      }
      // 清空页面缓存
      state.savedDefine.pageGroup = {};
      state.savedDefine.pageInfo = {};
    },

    // 当前请求页面ID
    requestPageId(state, pageId) {
      state.current.pageId = pageId;
    },

    // 清空当前页面定义
    clearCurPage(state) {
      // 页面组定义
      state.current.pageGroup = {};
      // 页面定义
      state.current.pageInfo = null;
    },

    // 页面切换
    changePage(state, pageParam) {
      var vueInstance = pageParam[2];

      // 页面组切换
      var groupId = pageParam[0];
      var preGroupId = state.current.pageGroup.groupId;
      if (preGroupId != groupId) {
        // 当前页面组定义
        state.current.pageGroup = state.savedDefine.pageGroup[groupId];
        // 页面组数据
        if (state.data.pageGroup == null || state.current.pageGroup.groupType == '0') {
          if (state.current.pageGroup.groupVariable) {
            state.data.pageGroup = JSON.parse(state.current.pageGroup.groupVariable);
          } else {
            state.data.pageGroup = {};
          }
        }

        // 转到页面组首页
        if (state.current.pageGroup.defaultPage != pageParam[1]) {
          var isAllowPage = false;

          if (state.current.pageGroup.groupAddon) {
            let allowPages = JSON.parse(state.current.pageGroup.groupAddon).allowPages;
            if (allowPages && allowPages.indexOf(pageParam[1]) > -1) {
              isAllowPage = true;
            }
          }
          if (isAllowPage == false) {
            state.current.pageInfo = null;

            var siteId = vueInstance.$route.params.site_id;
            if (siteId) {
              vueInstance.$router.replace("/" + siteId + "/" + groupId + "/" + state.current.pageGroup.defaultPage);
            } else {
              vueInstance.$router.replace("/" + groupId + "/" + state.current.pageGroup.defaultPage);
            }
            return;
          }
        }
      }

      // 菜单设定
      if (state.savedDefine.menuInfo.length > 0) {
        if (state.current.pageGroup.menuId) {
          state.current.menuId = state.current.pageGroup.menuId;
        }
      }

      // 页面切换
      var pageId = pageParam[1];
      // 当前页面定义
      state.current.pageInfo = state.savedDefine.pageInfo[groupId + "_" + pageId];
      // 页面数据
      if (state.current.pageInfo.pageInfo.pageVariable) {
        state.data.page = JSON.parse(state.current.pageInfo.pageInfo.pageVariable);
      } else {
        state.data.page = {};
      }

      // 清除数据
      state.data.elementProperty = {};
      state.data.layoutProperty = {};
      state.data.componetInstance = {};

      // 控件组
      let elementSetRows = state.current.pageInfo.elementSetRows;
      for (let s0 = 0; s0 < elementSetRows.length; s0++) {
        let elementSets = elementSetRows[s0].elementSetList;

        // 控件组行属性
        var setRowProp = {};
        if (elementSetRows[s0].rowProp != null) {
          setRowProp = JSON.parse(elementSetRows[s0].rowProp)
        }
        var setType = elementSetRows[s0].setType;
        vueInstance.$set(state.data.layoutProperty, setType + "r" + elementSetRows[s0].rowIndex, setRowProp);

        for (let s1 = 0; s1 < elementSets.length; s1++) {
          // 控件组
          let elementSet = elementSets[s1];
          let elementRows = elementSet.elementRowList;
          if (elementSet.setAddon) {
            elementSet.addon = JSON.parse(elementSet.setAddon);
          } else {
            elementSet.addon = {};
          }

          // 控件组行属性
          var setColProp = {};
          if (elementSet.colProp != null) {
            setColProp = JSON.parse(elementSet.colProp)
          }
          vueInstance.$set(state.data.layoutProperty, setType + "r" + elementSet.rowIndex + "c" + elementSet.colIndex, setColProp);

          if (setType == 'modal') {
            vueInstance.$set(state.data.componetInstance, 'm' + elementSet.setId, false);
          }

          for (let i = 0; i < elementRows.length; i++) {
            var rowElement = elementRows[i];

            // 行属性
            var rowProp = {};
            if (rowElement.rowProp) {
              rowProp = JSON.parse(rowElement.rowProp)
            }
            vueInstance.$set(state.data.layoutProperty, "s" + elementSet.setId + "r" + rowElement.rowIndex, rowProp);

            for (let j = 0; j < rowElement.cellList.length; j++) {
              var cellElement = rowElement.cellList[j];

              // 单元格属性
              var colProp = {};
              if (cellElement.colProp != null) {
                colProp = JSON.parse(cellElement.colProp)
              }
              vueInstance.$set(state.data.layoutProperty, "s" + elementSet.setId + "r" + cellElement.rowIndex + "c" + cellElement.colIndex, colProp);

              for (let k = 0; k < cellElement.elementList.length; k++) {
                var element = cellElement.elementList[k];

                // 控件属性
                var displayProp = {};
                if (element.displayProp != null) {
                  displayProp = JSON.parse(element.displayProp);
                }
                vueInstance.$set(state.data.elementProperty, 'e' + element.elementIndex, displayProp);

                // 明细控件
                for (let l = 0; l < element.detailList.length; l++) {
                  var elementDetail = element.detailList[l];

                  // 明细控件属性
                  var detailDisplayProp = {};
                  if (elementDetail.displayProp != null) {
                    detailDisplayProp = JSON.parse(elementDetail.displayProp);
                  }
                  vueInstance.$set(state.data.elementProperty, "e" + elementDetail.elementIndex + "d" + elementDetail.detailIndex, detailDisplayProp);
                }
              }
            }
          }
        }
      }
    },

    // 设定响应式类型
    responsiveMode(state, mode) {
      state.responsive_mode = mode;
    },

    // 开始加载
    loadingStart(state) {
      state.loading_cnt++;
      if (state.loading_cnt == 1) {
        ViewUI.LoadingBar.start();
      }
    },

    // 加载完成
    loadingFinish(state) {
      state.loading_cnt--;
      if (state.loading_cnt == 0) {
        ViewUI.LoadingBar.finish();
      }
    },

    // 加载错误
    loadingError(state) {
      state.loading_cnt--;
      if (state.loading_cnt == 0) {
        ViewUI.LoadingBar.error();
      }
    }
  }
})
