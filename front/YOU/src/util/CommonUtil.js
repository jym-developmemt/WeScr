import APIUtil from "./APIUtil";

export default {

  /**
   * 异常请求结果校验
   * 
   * @param {Array} resultDataArray 异常请求一览
   */
  checkProceeResult(resultDataArray) {
    var errorFlg = false;

    // 结果检验
    for (var i = 0; i < resultDataArray.length; i++) {
      if (resultDataArray[i]) {
      } else {
        errorFlg = true;
        break;
      }
    }

    return !errorFlg;
  },

  /**
   * 菜单点击处理
   * 
   * @param {*} vi VUE实例
   * @param {*} menuInfo 菜单信息
   */
  menuClickHandler(vi, menuInfo) {
    // 页面跳转
    if (menuInfo.menuType == "0") {
      if (menuInfo.pageId) {
        // 页面组ID
        var groupId = menuInfo.groupId;
        // 页面ID
        var pageId = menuInfo.pageId;
        // 站点ID
        var siteId = vi.$route.params.site_id;

        // 页面跳转
        if (siteId) {
          vi.$router.push("/" + siteId + "/" + groupId + "/" + pageId);
        } else {
          vi.$router.push("/" + groupId + "/" + pageId);
        }
      } else {
        // 地址未设定
        vi.$Message.error(vi.$t("common.error.param"));
      }
      return;
    } else if (menuInfo.menuType == "1") {
      window.open(menuInfo.pageId);
    } else if (menuInfo.menuType == "2") {
      window.location.href = menuInfo.pageId;
    }
  },

  /**
   * 保存控件
   * 
   * @param {any} vi VUE实例
   */
  saveElementToStore(vi) {
    if (vi.elementIndex) {
      // 控件实例ID
      var id = "e" + vi.elementIndex;
      if (vi.detailIndex) {
        id += "d" + vi.detailIndex;
        if (vi.rowIndex != null) {
          id += "r" + vi.rowIndex;
        }
      }

      // 保存控件实例
      vi.$set(
        vi.$store.getters.elementInstance,
        id,
        vi.$refs.elementRef
      );

      // 保存父控件实例
      vi.$set(
        vi.$store.getters.elementInstance,
        "$" + id,
        vi
      );
    }
  },

  replaceAll(str, oldString, newString) {
    return str.replace(new RegExp(oldString, "gm"), newString)
  },

  /**
   * 切换表示语言
   * 
   * @param {*} vi VUE实例
   * @param {*} locale1 言语ID
   * @param {*} locale2 言语ID
   */
  changeLocale(vi, locale1, locale2 ) {
    if (vi.$store.getters.messages[locale1]) {
      // 切换表示语言
      vi.$i18n.locale = locale1;
      // 保存表示语言
      vi.$store.commit("localeId", [locale1, locale2]);
      // 更新标题
      document.title = vi.$t("s.common.site.name");
    } else {
      APIUtil.loadMessage(vi, locale1).then(function () {
        // 切换表示语言
        vi.$i18n.locale = locale1;
        // 保存表示语言
        vi.$store.commit("localeId", [locale1, locale2]);
        // 更新标题
        document.title = vi.$t("s.common.site.name");
      });
    }
  },

  /**
   * 预授权登陆
   * 
   * @param {*} vi VUE实例
   */
  preAuth(vi) {
    // APP保存用户登陆
    if (window.sessionStorage) {
      let userInfo = window.sessionStorage.getItem("userInfo");
      if (userInfo) {
        let accessToken = window.sessionStorage.getItem("accessToken");
        vi.$store.commit("accessToken", accessToken);
        let locale = window.sessionStorage.getItem("locale");
        vi.$store.commit("loginInfo", {
          locale: locale.split(",", 2),
          userInfo: JSON.parse(userInfo),
        });
        return null;
      }
    }

    // APP保存用户登陆
    if (window.plus) {
      let userInfo = window.plus.storage.getItem("userInfo");
      if (userInfo) {
        let accessToken = window.plus.storage.getItem("accessToken");
        vi.$store.commit("accessToken", accessToken);
        let locale = window.plus.storage.getItem("locale");
        vi.$store.commit("loginInfo", {
          locale: locale.split(",", 2),
          userInfo: JSON.parse(userInfo),
        });
        return null;
      }
    }

    // 第三方认证
    if (vi.$route.query.state) {
      return APIUtil.extUserAuth(vi, vi.$route.query.code, vi.$route.query.state);
    }

    return null;
  },
}