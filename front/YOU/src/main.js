import 'babel-polyfill';
import Vue from 'vue'
import VueI18n from 'vue-i18n';
import ViewUI from 'view-design'
import axios from 'axios'
import VueAxios from 'vue-axios'
import Vue2OrgTree from 'vue2-org-tree'
import VCharts from 'v-charts'
import 'xe-utils'
import VXETable from 'vxe-table'
import App from './App.vue'
import router from './router'
import store from './store'
import YOUComponent from './components/YOUComponent'
import { library } from '@fortawesome/fontawesome-svg-core'
import { fas } from '@fortawesome/free-solid-svg-icons'
import { fab } from '@fortawesome/free-brands-svg-icons'
import { far } from '@fortawesome/free-regular-svg-icons'

import './css/iview.less'
import './css/common.css'

Vue.config.productionTip = false;

// 加载VUE插件
Vue.use(VueI18n);
Vue.use(ViewUI, {
  // iView模态框国际化对应
  i18n: function (path, options) {
    let value = i18n.t(path, options)
    if (value !== null && value !== undefined) {
      return value
    }
    return ''
  }
});
Vue.use(VueAxios, axios);
Vue.use(Vue2OrgTree)
Vue.use(VCharts);
Vue.use(VXETable, {
  // 可选，对参数中的列头、校验提示..等进行自动翻译（只对支持国际化的有效）
  translate (key, args) {
    if (key) {
      if (Array.isArray(key)) {
        args = [];
        for (var i=1;i<key.length;i++) {
          args.push(i18n.t(key[i]));
        }
        return i18n.t(key[0], args)
      } else {
        if (key.indexOf('s.') > -1) {
          return i18n.t(key, args)
        }
        if (key.indexOf('p.') > -1) {
          return i18n.t(key, args)
        }
      }
    }
    return key
  }
});
Vue.use(YOUComponent);
Vue.locale = () => { };

// 国际化设定
const i18n = new VueI18n();

// axios请求拦截器
axios.interceptors.request.use(
  config => {
    // 请求对象
    let baseUrlIndex = 2;
    if (config.data && config.data._bri != null) {
      baseUrlIndex = config.data._bri;
      delete config.data._bri;
    }
    
    // 请求设定
    config.withCredentials = true;
    config.method = 'post';

    // Token信息
    config.headers = {};
    let token = store.getters.accessToken;
    if (token) {
      config.headers['Authorization'] = "Bearer " + token;
    }
    if (store.getters.siteInfo) {
      config.headers['version-id'] = store.getters.siteInfo.versionId;
    }
    return config;
  },
  error => {
    return Promise.reject(error)
  }
);

// axios响应拦截器
axios.interceptors.response.use(
  response => {
    let resultHeader = response.headers;
    let resultData = response.data;
    store.getters.pageData.axiosHeader = resultHeader;

    if (resultData.result == null) {
      // let contentType = resultHeader['content-type'];
      // if (contentType && contentType.indexOf('application/json') > -1) {
      //   resultData = JSON.parse(String.fromCharCode.apply(null, new Int8Array(resultData)));
      // }
      return resultData;
    }

    // 正常响应返回响应数据
    if (resultData.result == '0') {
      return resultData.data;
    }

    // 登陆响应跳转到登陆页面
    if (resultData.result == '1') {
      let pageId = store.getters.curPageId;
      if (pageId) {
        if (store.getters.userInfo != null) {
          ViewUI.Message.error(i18n.t('s.common.error.auth'));
        } else {
          router.replace("/login" + pageId);
        }
      } else {
        router.replace("/login");
      }
      return resultData.data;
    }

    // 错误响应弹出错误提示
    if (resultData.result == '9') {
      ViewUI.Message.error(i18n.t(resultData.errorCode));
      return resultData.data;
    }

    return resultData;
  },
  error => {
    switch (error.response.status) {
      // 登陆响应跳转到登陆页面
      case 400:
      case 401:
        var msgCode = error.response.data.error_description;
        if (msgCode && msgCode.indexOf("s.") == 0) {
          ViewUI.Message.error(i18n.t(msgCode));
        } else if (msgCode && msgCode.indexOf("Invalid access token") == 0) {
          ViewUI.Message.error(i18n.t("s.common.error.invalid_token"));
        } else if (msgCode && msgCode == "User is disabled") {
          ViewUI.Message.error(i18n.t("s.common.error.user_disabled"));
        } else {
          ViewUI.Message.error(i18n.t("s.common.error.auth"));
        }

        let pageId = store.getters.curPageId;
        if (pageId) {
          router.push("/login" + pageId);
        } else {
          router.push("/login");
        }
        break;

      // 错误响应弹出错误提示
      default:
        ViewUI.Message.error(i18n.t('s.common.error.api'));
    }
    return Promise.reject(error)
  }
);

// 消息提示时间设定
ViewUI.Message.config({
  duration: 5
});

// FontAwesome设定
library.add(fas);
library.add(fab);
library.add(far);

new Vue({
  router,
  store,
  i18n,
  render: h => h(App)
}).$mount('#app')
