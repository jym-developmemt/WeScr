import CommonUtil from "./CommonUtil";
import ExpressionUtil from "./ExpressionUtil";
import moment from "moment";

export default {

    /**
     * 站点信息取得
     * 
     * @param {*} vi VUE实例
     */
    findSiteInfo(vi) {
        // 加载开始
        vi.$store.commit("loadingStart");

        // 站点切换
        var param = {};
        param.siteId = process.env.VUE_APP_SITE_ID;
        if (vi.$route.params.site_id) {
            param.siteId = vi.$route.params.site_id;
        }
        param.localeId = vi.$store.getters.localeId[0];
        param.subLocaleId = vi.$store.getters.localeId[1];

        // 发送请求
        return vi.axios.post(process.env.VUE_APP_URL_API + "/siteInfo/find", param)
            .then(result => {
                // 站点信息保存
                if (result) {
                    // 保存多语言信息
                    vi.$store.commit("languages", result.languages);
                    // 保存消息
                    vi.$store.commit("messages", {
                        locale: result.locale1,
                        messages: result.msg1
                    });
                    vi.$i18n.setLocaleMessage(result.locale1, vi.$store.getters.messages[result.locale1]);
                    if (result.locale2) {
                        vi.$store.commit("messages", {
                            locale: result.locale2,
                            messages: result.msg2
                        });
                        vi.$i18n.setLocaleMessage(result.locale2, vi.$store.getters.messages[result.locale2]);
                    }
                    // 切换语言
                    CommonUtil.changeLocale(vi, result.locale1, result.locale2);
                    // 保存外部信息
                    vi.$store.commit("externalInfo", result.external);
                    // 保存站点信息
                    vi.$store.commit("siteInfo", result.siteInfo);
                    // 更新标题
                    document.title = vi.$t("s.common.site.name");
                }
                // 加载完成
                vi.$store.commit("loadingFinish");
                return result.siteInfo;
            })
            .catch(e => {
                // 加载错误
                vi.$store.commit("loadingError");
            }).finally(() => {
            });
    },

    /**
     * 站点菜单信息取得
     * 
     * @param {*} vi VUE实例
     * @param {number} type 菜单类型 ( 1:门户 2：管理 3:手机 )
     */
    findSiteMenu(vi, type) {
        // 清除当前菜单
        vi.$store.commit("menuInfo", []);
        var siteId = vi.$store.getters.siteInfo.siteId;

        // 发送请求
        return vi.axios.post(process.env.VUE_APP_URL_API + "/siteInfo/menu", { "siteId": siteId, "menuType": type })
            .then(result => {
                // 站点菜单信息保存
                if (result) {
                    vi.$store.commit("menuInfo", result);
                }
                return result;
            });
    },

    /**
     * 用户登录认证
     * 
     * @param {*} vi VUE实例
     * @param {string} userId 用户ID
     * @param {string} password 密码
     */
    userAuth(vi, userId, password) {
        // 加载开始
        vi.$store.commit("loadingStart");

        // 请求参数
        let data = new FormData();
        data.append("grant_type", "password");
        data.append("username", userId);
        data.append("password", password);
        data.append("client_id", vi.$store.getters.externalInfo.clientIdSite);
        data.append("client_secret", vi.$store.getters.externalInfo.clientSecretSite);

        // 发送请求
        return vi.axios.post(process.env.VUE_APP_URL_AUTH + "/oauth/token", data)
            .then(result => {
                // 用户认证信息保存
                vi.$store.commit("accessToken", result.access_token);
                return vi.axios.post(process.env.VUE_APP_URL_AUTH + "/userInfo/find").then((loginInfo) => {
                    if (loginInfo) {
                        // 切换语言
                        if (loginInfo.locale[0]) {
                            CommonUtil.changeLocale(vi, loginInfo.locale[0], loginInfo.locale[1]);
                        }

                        // 登陆用户信息取得成功
                        vi.$store.commit("loginInfo", loginInfo);
                    }

                    // 加载完成
                    vi.$store.commit("loadingFinish");

                    return loginInfo;
                });
            })
            .catch(() => {
                // 加载错误
                vi.$store.commit("loadingError");
            });
    },

    /**
     * 用户登录认证(外部)
     * 
     * @param {*} vi VUE实例
     * @param {string} code 授权码code
     * @param {string} state 来源
     */
    extUserAuth(vi, code, state) {
        // 加载开始
        vi.$store.commit("loadingStart");

        // 请求参数
        let data = new FormData();
        data.append("grant_type", "password");
        data.append("username", code);
        data.append("client_id", state);
        data.append("client_secret", vi.$store.getters.externalInfo[state + 'ClientSecret']);

        // 发送请求
        return vi.axios.post(process.env.VUE_APP_URL_AUTH + "/oauth/token", data)
            .then(result => {
                // 用户认证信息保存
                vi.$store.commit("accessToken", result.access_token);
                return vi.axios.post(process.env.VUE_APP_URL_AUTH + "/userInfo/find").then((loginInfo) => {
                    if (loginInfo) {
                        // 切换语言
                        if (loginInfo.locale[0]) {
                            CommonUtil.changeLocale(vi, loginInfo.locale[0], loginInfo.locale[1]);
                        }

                        // 登陆用户信息取得成功
                        vi.$store.commit("loginInfo", loginInfo);
                    }

                    // 加载完成
                    vi.$store.commit("loadingFinish");

                    return loginInfo;
                });
            })
            .catch(() => {
                // 加载错误
                vi.$store.commit("loadingError");
            });
    },

    /**
     * 页面组信息取得
     * 
     * @param {*} vi VUE实例
     * @param {string} groupId 页面组ID
     */
    findPageGroup(vi, groupId) {
        // 加载开始
        vi.$store.commit("loadingStart");

        // 发送请求
        return vi.axios.post(process.env.VUE_APP_URL_API + "/pageInfo/group", { "groupId": groupId })
            .then(result => {
                // 页面组信息保存
                if (result) {
                    vi.$store.commit("pageGroupDefine", result);
                }
                // 加载完成
                vi.$store.commit("loadingFinish");
                return result;
            })
            .catch(() => {
                // 加载错误
                vi.$store.commit("loadingError");
            });
    },

    /**
     * 页面信息取得
     * 
     * @param {*} vi VUE实例
     * @param {string} groupId 页面组ID
     * @param {string} pageId 页面ID
     */
    findPageInfo(vi, groupId, pageId) {
        // 加载开始
        vi.$store.commit("loadingStart");

        // 请求参数
        let data = new FormData();
        data.append("groupId", groupId);
        data.append("pageId", pageId);

        // 发送请求
        return vi.axios.post(process.env.VUE_APP_URL_API + "/pageInfo/find", data)
            .then(result => {
                // 页面信息保存
                if (result) {
                    vi.$store.commit("pageInfoDefine", result);
                }
                // 加载完成
                vi.$store.commit("loadingFinish");
                return result;
            })
            .catch(() => {
                // 加载错误
                vi.$store.commit("loadingError");
            });
    },

    /**
     * 请求参数转换
     * 
     * @param {*} param 
     */
    convertProcessParam(vi, param) {
        // 请求参数
        var process = new Object();
        // 日期型对数变换
        this.convertProcessParamDate(param);

        // 补足
        if (param.a) {
            if (param.a.indexOf("p") > -1) {
                let pageInfo = vi.$store.getters.curPageDefine.pageInfo;
                param.s1 = pageInfo.groupId;
                param.s2 = pageInfo.pageId;
            }
        }

        // 类型
        process.type = param.t;
        // 文字参数
        for (var i = 1; i <= 10; i++) {
            if (param['s' + i]) {
                process["stringData" + i] = param['s' + i];
            }
        }
        // 对象参数
        for (var i = 1; i <= 10; i++) {
            if (param['o' + i]) {
                process["objData" + i] = param['o' + i];
            }
        }
        // 列表参数
        for (var i = 1; i <= 10; i++) {
            if (param['l' + i]) {
                process["listData" + i] = param['l' + i];
            }
        }

        return process;
    },

    /**
     * 请求参数转换
     * 
     * @param {*} param 
     */
    convertProcessParamDate(param) {
        if (param == null) {
            return;
        }

        if (Array.isArray(param)) {
            for (var i = 0; i < param.length; i++) {
                this.convertProcessParamDate(param[i]);
            }
            return;
        }
        
        if (typeof param == 'object') {
            for (var key in param) {
                var val = param[key];
                if (val) {
                    if (moment.isDate(val)) {
                        param[key] = moment(val).format("YYYY/MM/DD HH:mm:ss");
                        continue;
                    }

                    if (Array.isArray(val) || typeof val == 'object') {
                        this.convertProcessParamDate(val);
                        continue;
                    }

                    if (typeof val == 'string' && /\d{4}(-\d{2}){2}T\d{2}(:\d{2}){2}[.]\d{3}[+]\d{2}:+\d{2}/.test(val)) {
                        // YYYY-MM-DDTHH:mm:ss.sss+0800
                        param[key] = moment(val).format("YYYY/MM/DD HH:mm:ss");
                        continue;
                    }
                }
            }
        }
    },

    /**
     * 发送请求
     * 
     * @param {*} vi VUE实例
     * @param {array} processList 请求一览
     * @param {string} callback 回调处理
     */
    sendProcessRequest(vi, processList, callback) {
        // 加载开始
        vi.$store.commit("loadingStart");

        // 发送请求
        return vi.axios.post(process.env.VUE_APP_URL_API + "/process/execute", processList)
            .then(result => {
                // 加载完成
                vi.$store.commit("loadingFinish");
                // 实行表达式
                if (result && callback) {
                    var callBackResult = ExpressionUtil.eval(vi, callback, result, processList);
                    if (callBackResult) {
                        result = callBackResult;
                    }
                }
                return result;
            })
            .catch(() => {
                // 加载错误
                vi.$store.commit("loadingError");
            });
    },

    /**
     * 发送请求
     * 
     * @param {*} vi VUE实例
     * @param {array} processList 请求一览
     * @param {string} callback 回调处理
     */
    sendProcessRequestWithoutLoading(vi, processList, callback) {
        // 发送请求
        return vi.axios.post(process.env.VUE_APP_URL_API + "/process/execute", processList)
            .then(result => {
                // 实行表达式
                if (result && callback) {
                    var callBackResult = ExpressionUtil.eval(vi, callback, result, processList);
                    if (callBackResult) {
                        result = callBackResult;
                    }
                }
                return result;
            });
    },

    /**
     * 读取消息数据
     * 
     * @param {*} vi VUE实例
     * @param {string} locale 言语ID
     */
    loadMessage(vi, locale) {
        // 加载开始
        vi.$store.commit("loadingStart");

        // 发送请求
        return vi.axios.post(process.env.VUE_APP_URL_API + '/siteInfo/message', { 'localeId': locale })
            .then(result => {
                // 页面信息保存
                if (result) {
                    vi.$store.commit("messages", { locale: locale, messages: result });
                    vi.$i18n.setLocaleMessage(locale, vi.$store.getters.messages[locale]);
                }
                // 加载完成
                vi.$store.commit("loadingFinish");
            })
            .catch(() => {
                // 加载错误
                vi.$store.commit("loadingError");
            });
    },

    /**
     * 读取资源数据
     * 
     * @param {*} vi VUE实例
     * @param {string} resourceId 资源ID
     * @param {string} callback 回调处理
     */
    loadResource(vi, resourceId, callback, responseType) {
        // 加载开始
        vi.$store.commit("loadingStart");

        if (responseType == null) {
            responseType = { 'responseType': 'arraybuffer' }
        } else {
            responseType = null;
        }

        // 发送请求
        return vi.axios.post(process.env.VUE_APP_URL_API + '/resource/download', { 'resourceId': resourceId }, responseType)
            .then(result => {
                if (result) {
                    ExpressionUtil.eval(vi, callback, result);
                }
                // 加载完成
                vi.$store.commit("loadingFinish");
            })
            .catch(() => {
                // 加载错误
                vi.$store.commit("loadingError");
            });
    },

    /**
     * 国际化消息取得
     * 
     * @param {*} vi VUE实例
     * @param {string} sourceId 消息来源ID
     */
    searchLocaleMessage(vi, sourceId) {
        // 加载开始
        vi.$store.commit("loadingStart");

        // 发送请求
        return vi.axios.post(process.env.VUE_APP_URL_API + "/localeMessage/search",
            {
                "sourceId": sourceId
            })
            .then(result => {
                // 加载完成
                vi.$store.commit("loadingFinish");
                return result;
            })
            .catch(() => {
                // 加载错误
                vi.$store.commit("loadingError");
            });
    },

    /**
     * 国际化消息保存
     * 
     * @param {*} vi VUE实例
     * @param {string} groupId 页面组ID
     */
    saveLocaleMessage(vi, sourceId, dataList) {
        // 加载开始
        vi.$store.commit("loadingStart");

        // 发送请求
        return vi.axios.post(process.env.VUE_APP_URL_API + "/localeMessage/search",
            {
                "sourceId": sourceId,
                "dataList": dataList
            })
            .then(result => {
                // 加载完成
                vi.$store.commit("loadingFinish");
                return result;
            })
            .catch(() => {
                // 加载错误
                vi.$store.commit("loadingError");
            });
    },
};