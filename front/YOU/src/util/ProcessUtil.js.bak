import ExpressionUtil from "./ExpressionUtil";
import APIUtil from "./APIUtil";

/**
 * 处理请求工具类
 */
function ProcessUtil() {
  /** VUE实例 */
  this.vueinstance;

  /**
   * 弹出普通消息
   * 
   * @param {string} message 消息
   * @param {Array} args 参数
   */
  this.info = function (message, args) {
    var msgArgs = [];
    if (args) {
      for (var i=0;i<args.length;i++) {
        msgArgs.push(this.vueinstance.$t(args[i]));
      }
    }
    this.vueinstance.$Message.info(this.vueinstance.$t(message, msgArgs));
  };

  /**
   * 弹出成功消息
   * 
   * @param {string} message 消息
   * @param {Array} args 参数
   */
  this.success = function (message, args) {
    var msgArgs = [];
    if (args) {
      for (var i=0;i<args.length;i++) {
        msgArgs.push(this.vueinstance.$t(args[i]));
      }
    }
    this.vueinstance.$Message.success(this.vueinstance.$t(message, msgArgs));
  };

  /**
   * 弹出警告消息
   * 
   * @param {string} message 消息
   * @param {Array} args 参数
   */
  this.warning = function (message, args) {
    var msgArgs = [];
    if (args) {
      for (var i=0;i<args.length;i++) {
        msgArgs.push(this.vueinstance.$t(args[i]));
      }
    }
    this.vueinstance.$Message.warning(this.vueinstance.$t(message, msgArgs));
  };

  /**
   * 弹出错误消息
   * 
   * @param {string} message 消息
   * @param {Array} args 参数
   */
  this.error = function (message, args) {
    var msgArgs = [];
    if (args) {
      for (var i=0;i<args.length;i++) {
        msgArgs.push(this.vueinstance.$t(args[i]));
      }
    }
    this.vueinstance.$Message.error(this.vueinstance.$t(message, msgArgs));
  };

  /**
   * 弹出确认消息
   * 
   * @param {string} message 消息
   * @param {Array} messageArgs 参数
   * @param {string} callback 确定时的回调表达式
   * @param {string} content 消息
   * @param {Array} contentArgs 参数
   * @param {*} addon1 附加信息1
   * @param {*} addon2 附加信息2
   * @param {*} addon3 附加信息3
   * @param {*} addon4 附加信息4
   * @param {*} addon5 附加信息5
   */
  this.confirm = function () {
    var idx = 0;
    var message = arguments[idx++];
    var messageArgs = [];
    if (Array.isArray(arguments[idx])) {
      var msgArgs = arguments[idx++];
      for (var i=0;i<msgArgs.length;i++) {
        messageArgs.push(this.vueinstance.$t(msgArgs[i]));
      }
    }
    var callback = arguments[idx++];
    var content = arguments[idx++];
    var contentArgs = [];
    if (Array.isArray(arguments[idx])) {
      var msgArgs = arguments[idx++];
      for (var i=0;i<msgArgs.length;i++) {
        contentArgs.push(this.vueinstance.$t(msgArgs[i]));
      }
    }
    var addon1 = arguments[idx++];
    var addon2 = arguments[idx++];
    var addon3 = arguments[idx++];
    var addon4 = arguments[idx++];
    var addon5 = arguments[idx++];

    var me = this.vueinstance;
    this.vueinstance.$Modal.confirm({
      title: this.vueinstance.$t(message, messageArgs),
      content: this.vueinstance.$t(content, contentArgs),
      onOk: () => {
        // 表达式实行
        ExpressionUtil.eval(me, callback, addon1, addon2, addon3, addon4, addon5);
      },
    });
  };

  /**
   * 页面跳转
   * 
   * @param {string} address 地址
   * @param {string} siteId 站点ID
   */
  this.redirect = function (address, siteId) {
    if (siteId == null) {
      siteId = this.vueinstance.$route.params.site_id;
    }
    if (siteId) {
      this.vueinstance.$router.push("/" + siteId + address);
    } else {
      this.vueinstance.$router.push(address);
    }
  };

  /**
   * 页面后退
   */
  this.backward = function () {
    this.vueinstance.$router.back();
  };

  /**
   * 对象复制
   * 
   * @param {*} data 复制对象
   */
  this.clone = function (data) {
    if (data) {
      return JSON.parse(JSON.stringify(data))
    }
    return data
  };

  /**
   * 对象复制
   * 
   * @param {*} data 复制对象
   */
  this.copy = function (src, target) {
    for (var key in src) {
      target[key] = src[key];
    }
  };

  /**
   * 数组合并
   * 
   * @param {*} key 合并Key
   */
  this.mergeArray = function (key) {
    var rtnList = [];
    var rtnData = {};

    for (let i=1;i<arguments.length;i++) {
      let array = arguments[i];
      for (let j=0;j<array.length;j++) {
        let src = array[j];
        let target = rtnData[src[key]];
        if (target == null) {
          target = {};
          rtnData[src[key]] = target;
          rtnList.push(target);
        }
        Object.assign(target, src);
      }
    }
    return rtnList;
  };

  /**
   * 对象复制
   * 
   * @param {*} data 复制对象
   */
  this.copyList = function (srcList, targetList, key) {
    for (let i = 0; i < srcList.length; i++) {
      targetList[i][key] = srcList[i][key];
    }
  };

  /**
   * 对象清空
   * 
   * @param {*} data 清空对象
   */
  this.clear = function (data) {
    for (var key in data) {
      data[key] = null;
    }
  };

  /**
   * 设置VUE对象
   * 
   * @param {*} obj 数组
   * @param {*} key 数组
   * @param {*} newObj 对象
   */
  this.set = function (obj, key, newObj) {
    this.vueinstance.$set(obj, key, newObj);
  };

  /**
   * 重置数组番号
   * 
   * @param {array} arr 数组
   * @param {string} indexKey 番号key
   */
  this.refreshIndex = function (arr, indexKey) {
    if (indexKey == null) {
      indexKey = "index";
    }
    for (var i = 0; i < arr.length; i++) {
      arr[i][indexKey] = (i + 1);
    }
  };

  /**
   * 从数组删除对象
   * 
   * @param {array} daarrayta 数组
   * @param {*} item 对象
   */
  this.delArrayItem = function (array, item) {
    var i = array.indexOf(item);
    if (i > -1) {
      this.vueinstance.$delete(array, i);
    }
    return array;
  };

  /**
   * 数组对象上移
   * 
   * @param {array} daarrayta 数组
   * @param {*} item 对象
   */
  this.upArrayItem = function (array, item) {
    var i = array.indexOf(item);
    if (i > 0) {
      array[i] = array.splice(i - 1, 1, array[i])[0];
    }
  };

  /**
   * 数组对象下移
   * 
   * @param {array} daarrayta 数组
   * @param {*} item 对象
   */
  this.downArrayItem = function (array, item) {
    var i = array.indexOf(item);
    if (i < array.length - 1) {
      array[i] = array.splice(i + 1, 1, array[i])[0];
    }
  };

  /**
   * 数组对象属性设定
   * 
   * @param {array} daarrayta 数组
   * @param {string} key key
   * @param {*} value 对象
   */
  this.setArrayItem = function (array, key, value) {
    if (array) {
      for (var i = 0; i < array.length; i++) {
        array[i][key] = value;
      }
    }
    return array;
  };

  /**
   * 数组对象属性设定
   * 
   * @param {array} daarrayta 数组
   * @param {string} key key
   * @param {*} value 对象
   */
  this.setArrayItemExp = function (array, exp, addon) {
    if (array) {
      for (var i = 0; i < array.length; i++) {
        ExpressionUtil.eval(this.vueinstance, exp, array[i], i, addon);
      }
    }
    return array;
  };

  /**
   * 数组对象属性设定
   * 
   * @param {array} array 数组
   * @param {string} key key
   * @param {*} value 对象
   * @param {Number} order 查找顺序
   */
  this.findArrayItem = function (array, key, value, order, valKey) {
    if (array && value) {
      if (order == 1) {
        for (var i = array.length - 1; i >= 0; i--) {
          if (array[i][key] == value) {
            if (valKey != null) {
              return array[i][valKey];
            } else {
              return array[i];
            }
          }
        }
      } else {
        for (var i = 0; i < array.length; i++) {
          if (array[i][key] == value) {
            if (valKey != null) {
              return array[i][valKey];
            } else {
              return array[i];
            }
          }
        }
      }
    }
    return null;
  };

  /**
   * 数组对象属性设定
   * 
   * @param {array} array 数组
   * @param {string} key key
   * @param {*} value 对象
   */
  this.findArrays = function (array, keyArray, valueArray) {

    var returnArray = [];
    for (var i = 0; i < array.length; i++) {
      var flg = true;
      for (var j = 0; j < keyArray.length; j++) {
        if (array[i][keyArray[j]] != valueArray[j]) {
          flg = false;
          break;
        }
      }
      if (flg == true) {
        returnArray.push(array[i]);
      }
    }

    return returnArray;
  };

  /**
   * 数组对象属性设定
   * 
   * @param {array} array 数组
   * @param {string} key key
   * @param {*} value 对象
   * @param {Number} order 查找顺序
   */
  this.findArrayItemByPart = function (array, key, value, order, valKey) {
    if (array && value) {
      if (order == 1) {
        for (var i = array.length - 1; i >= 0; i--) {
          if (array[i][key].indexOf(value) > -1) {
            if (valKey != null) {
              return array[i][valKey];
            } else {
              return array[i];
            }
          }
        }
      } else {
        for (var i = 0; i < array.length; i++) {
          if (array[i][key].indexOf(value) > -1) {
            if (valKey != null) {
              return array[i][valKey];
            } else {
              return array[i];
            }
          }
        }
      }
    }
    return null;
  };

  /**
   * 数组对象属性设定
   * 
   * @param {array} array 数组
   * @param {string} key key
   * @param {*} value 对象
   * @param {Number} order 查找顺序
   */
  this.generateClassify = function (array, classifyIdField, itemIdField, itemNameField) {
    var rtnVal = {};
    if (array) {
      for (var i = 0; i < array.length; i++) {
        var classifyId = array[i][classifyIdField];
        var itemId = array[i][itemIdField];
        var itemName = array[i][itemNameField];
        if (rtnVal[classifyId] == null) {
          rtnVal[classifyId] = [];
        }
        rtnVal[classifyId].push([itemId, itemName]);
      }
    }
    return rtnVal;
  };

  /**
   * 数组对象属性设定
   * 
   * @param {array} array 数组
   * @param {array} keys key
   * @param {array} values 对象
   */
  this.findArrayItems = function (array, keys, values) {
    let isFound = false;
    if (array) {
      for (var i = 0; i < array.length; i++) {
        isFound = true;
        for (var j = 0; j < keys.length; j++) {
          if (array[i][keys[j]] != values[j]) {
            isFound = false;
            break;
          }
        }
        if (isFound) {
          return array[i];
        }
      }
    }
    return null;
  };

  /**
   * 查找数组对象番号
   * 
   * @param {array} daarrayta 数组
   * @param {*} item 对象
   */
  this.findArrayIndex = function (array, item) {
    if (array) {
      for (var i = 0; i < array.length; i++) {
        if (array[i] == item) {
          return i;
        }
      }
    }
    return -1;
  };

  /**
   * 数组对象属性设定
   * 
   * @param {array} daarrayta 数组
   * @param {string} key key
   * @param {*} value 对象
   */
  this.findArrayList = function (array, key, value) {
    var rtnList = new Array();
    for (var i = 0; i < array.length; i++) {
      if (array[i][key] == value) {
        rtnList.push(array[i]);
      }
    }
    return rtnList;
  };

  /**
   * 数组对象属性设定
   * 
   * @param {array} daarrayta 数组
   * @param {string} key key
   * @param {*} value 对象
   */
  this.arrayToList = function (array, key) {
    var rtnList = new Array();
    for (var i = 0; i < array.length; i++) {
      var obj = new Object();
      obj[key] = array[i];
      rtnList.push(obj);
    }
    return rtnList;
  };

  /**
   * 数组对象属性设定
   * 
   * @param {array} daarrayta 数组
   * @param {string} key key
   * @param {*} value 对象
   */
  this.listToArray = function (list, key) {
    var rtnList = new Array();
    for (var i = 0; i < list.length; i++) {
      rtnList.push(list[i][key]);
    }
    return rtnList;
  };

  /**
   * 数组对象属性设定
   * 
   * @param {array} tree 树
   * @param {string} key key
   */
  this.treeToList = function (tree, key) {
    if (tree == null) {
      return [];
    }
    if (key == null) {
      key = '_children_';
    }
    if (Array.isArray(tree) == false) {
      tree = [tree];
    }
    var rtnList = new Array();
    for (var i = 0; i < tree.length; i++) {
      rtnList.push(tree[i]);
      if (tree[i][key]) {
        rtnList = rtnList.concat(this.treeToList(tree[i][key], key));
      }
    }
    return rtnList;
  };

  /**
   * 数组对象合计
   * 
   * @param {array} daarrayta 数组
   * @param {string} key key
   * @param {*} value 对象
   */
  this.arrayTotal = function (array, key) {
    var sum = 0;
    for (var i = 0; i < array.length; i++) {
      sum += array[i][key] * 1;
    }
    return sum;
  };

  /**
   * 读取资源数据
   * 
   * @param {string} resourceId 资源ID
   * @param {string} callback 确定时的回调表达式
   */
  this.loadResource = function (resourceId, callback, responseType, baseUrlIndex) {
    if (this.vueinstance.$store.getters.isLoading) {
      this.vueinstance.$Message.error(this.vueinstance.$t("common.error.processing"));
      return;
    }

    // API通信实行
    return APIUtil.loadResource(this.vueinstance, resourceId, callback, responseType, baseUrlIndex);
  };

  /**
   * 资源数据下载
   * 
   * @param {Blob} data 数据
   * @param {String} fileName 文件名
   * @param {String} type 类型 text/plain|application/json|application/json
   */
  this.download = function (data, fileName, type) {
    // 读取资源中的文件名
    if (typeof (fileName) == 'object' && fileName['content-disposition']) {
      fileName = fileName['content-disposition'].replace("attachment;fileName=", "");
      fileName = decodeURIComponent(fileName);
    }

    // 数据下载
    let blob;
    if (type) {
      blob = new Blob([data], { type: type + ";charset=utf-8" });
    } else {
      blob = new Blob([data], { type: "application/octet-stream;charset=utf-8" });
    }
    // let FileSaver = require('file-saver');
    // FileSaver.saveAs(blob, fileName);
  };

  /**
   * 发送请求
   * 
   * @param {*} param 请求信息
   * @param {string} callback 确定时的回调表达式
   */
  this.send = function (param, callback, hideLoading, baseUrlIndex) {
    if (this.vueinstance.$store.getters.isLoading) {
      this.vueinstance.$Message.error(this.vueinstance.$t("common.error.processing"));
      return;
    }

    // API通信实行
    var processList = new Array();
    processList.push(APIUtil.convertProcessParam(this.vueinstance, param));
    if (hideLoading) {
      return APIUtil.sendProcessRequestWithoutLoading(this.vueinstance, processList, callback, baseUrlIndex);
    } else {
      return APIUtil.sendProcessRequest(this.vueinstance, processList, callback, baseUrlIndex);
    }
  };

  /**
   * 发送请求列表(异步)
   * 
   * @param {*} paramArray 请求信息一览
   * @param {string} callback 确定时的回调表达式
   */
  this.sendL = function (paramArray, callback, hideLoading, baseUrlIndex) {
    if (this.vueinstance.$store.getters.isLoading) {
      this.vueinstance.$Message.error(this.vueinstance.$t("common.error.processing"));
      return;
    }

    // 请求列表
    var processList = new Array();
    for (var i = 0; i < paramArray.length; i++) {
      processList.push(APIUtil.convertProcessParam(this.vueinstance, paramArray[i]));
    }

    // API通信实行
    if (hideLoading) {
      return APIUtil.sendProcessRequestWithoutLoading(this.vueinstance, processList, callback, baseUrlIndex);
    } else {
      return APIUtil.sendProcessRequest(this.vueinstance, processList, callback, baseUrlIndex);
    }
  };

  this.redim = function (array, newLength) {
    if (array.length > newLength) {
      for (var i = array.length - 1; i >= newLength; i--) {
        array.splice(i, 1);
      }
      array.length = newLength;
    } else {
      for (var i = array.length; i < newLength; i++) {
        array.push({});
      }
    }
  };

  this.generateKey = function (length) {
    if (length == null) {
      length = 16;
    }

    var timestamp = new Date().getTime() + "";
    if (timestamp.length < length) {
      timestamp += this.generateRadom(length - timestamp.length);
    }
    return timestamp.substring(0, length);
  };

  this.generateRadom = function (length) {
    var t = Math.ceil(Math.random() * Math.pow(10, length)) + "";
    for (var i = t.length; i < length; i++) {
      t = "0" + t;
    }
    return t;
  };

  /**
   * 取得请求参数
   * 
   * @param {Date} dateVal 日期数据
   * @param {string} format 格式
   */
  this.getParameter = function (name) {
    return this.vueinstance.$route.query[name];
  }

  /**
   * 数字补0
   * 
   * @param {Date} dateVal 日期数据
   * @param {string} format 格式
   */
  this.lpad = function (num, length) {
    num = parseInt(num);
    return (Array(length).join('0') + num).slice(-length);
  }

  /**
   * 去空格
   * 
   * @param {string} str 文字
   */
  this.trim = function (str) {
    if (str == null) {
      return null;
    }
    return str.trim();
  }

  /**
   * i18n
   * 
   * @param {string} str key
   * @param {Array} args params
   */
  this.t = function (str, args) {
    return this.vueinstance.$t(str, args);
  }

  /**
   * 转文字
   * 
   * @param {any} obj 文字
   */
  this.toString = function (obj) {
    if (obj == null) {
      return null;
    }
    return '' + obj;
  }

  /**
   * 乘法运算（解决小数精度问题）
   */
  this.mul = function (arg1, arg2) {
    var m = 0, s1 = arg1.toString(), s2 = arg2.toString();
    try {
      m += s1.split(".")[1].length
    } catch (e) {
    }
    try {
      m += s2.split(".")[1].length
    } catch (e) {
    }
    return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m)
  }

  /**
   * 除法运算（解决小数精度问题）
   */
  this.div = function (arg1, arg2) {
    var t1 = 0, t2 = 0, r1, r2;
    try {
      t1 = arg1.toString().split(".")[1].length
    } catch (e) {
    }
    try {
      t2 = arg2.toString().split(".")[1].length
    } catch (e) {
    }
    r1 = Number(arg1.toString().replace(".", ""));
    r2 = Number(arg2.toString().replace(".", ""));
    return (r1 / r2) * Math.pow(10, t2 - t1);
  }

  

  /**
   * 数组对象属性设定
   * 
   * @param {array} array 数组
   * @param {string} key key
   */
  this.selectMany = function (array, key) {
    var rtnArray = [];
    if (array && key) {
      for (var i=0;i<array.length;i++) {
        rtnArray = rtnArray.concat(array[i][key]);
      }
    }
    return rtnArray;
  }
}

export default ProcessUtil;