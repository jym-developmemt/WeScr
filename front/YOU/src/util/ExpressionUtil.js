import ProcessUtil from "./ProcessUtil";
import DateUtil from "./DateUtil";
import ExcelUtil from "./ExcelUtil";

export default {

  /**
   * 取得运行环境
   */
  getExecuteEnviron() {
    // 登录用户信息
    var lu = "var lu = vueinstance.$store.getters.userInfo;\r\n";
    // 当前页面组定义
    var dg = "var dg = vueinstance.$store.getters.curPageGroupDefine;\r\n";
    // 当前页面定义
    var dp = "var dp = vueinstance.$store.getters.curPageDefine;\r\n";

    // 全局数据
    var gd = "var gd = vueinstance.$store.getters.globalData;\r\n";
    // 页面组数据
    var pg = "var pg = vueinstance.$store.getters.pageGroupData;\r\n";
    // 页面数据
    var pd = "var pd = vueinstance.$store.getters.pageData;\r\n";
    // 流程数据
    var bd = "var bd = vueinstance.$store.getters.bpmData;\r\n";
    // 布局属性
    var lp = "var lp = vueinstance.$store.getters.layoutPropData;\r\n";
    // 控件属性
    var ep = "var ep = vueinstance.$store.getters.elementPropData;\r\n";
    // 控件实例
    var ei = "var ei = vueinstance.$store.getters.elementInstance;\r\n";

    // 工具类
    var u = "var u = processUtil;\r\n";
    var du = "var du = dateUtil;\r\n";
    var ex = "var ex = excelUtil;\r\n";
    // VUE实例
    var v = "var v = vueinstance;\r\n";
    // 附加信息
    var a1 = "var a1 = addon1;\r\n";
    var a2 = "var a2 = addon2;\r\n";
    var a3 = "var a3 = addon3;\r\n";
    var a4 = "var a4 = addon4;\r\n";
    var a5 = "var a5 = addon5;\r\n";

    return lu + dg + dp + gd + pg + pd + bd + lp + ep + ei + u + du + ex + v + a1 + a2 + a3 + a4 + a5;
  },

  /**
   * 执行表达式
   * 
   * @param {*} vueinstance VUE实例
   * @param {string} eventExpression 表达式
   * @param {*} addon1 附加信息1
   * @param {*} addon2 附加信息2
   * @param {*} addon3 附加信息3
   * @param {*} addon4 附加信息4
   * @param {*} addon5 附加信息5
   */
  eval(vueinstance, eventExpression, addon1, addon2, addon3, addon4, addon5) {
    // 处理请求工具类
    var processUtil = new ProcessUtil();
    processUtil.vueinstance = vueinstance;
    var excelUtil = new ExcelUtil();
    excelUtil.vueinstance = vueinstance;
    var dateUtil = new DateUtil();
    dateUtil.vueinstance = vueinstance;

    // 生成表达式
    var executeScript = this.getExecuteEnviron();
    executeScript += "var executeFunc = function(lu, dg, dp, gd, pg, pd, bd, lp, ep, ei, u, du, ex, v, a1, a2, a3, a4, a5) {"
      + eventExpression +
      "};\r\n";
    executeScript += "executeFunc(lu, dg, dp, gd, pg, pd, bd, lp, ep, ei, u, du, ex, v, a1, a2, a3, a4, a5);";

    // 解析运行表达式
    try {
      return eval(executeScript);
    } catch (e) {
      console.error("表达式解析错误:\r\n" + executeScript + '\r\n' + e);
    }
  },

  /**
   * 创建表达式对象
   * 
   * @param {*} vueinstance VUE实例
   * @param {string} objectExpression 表达式
   */
  createObject(vueinstance, objectExpression, addon1, addon2, addon3, addon4, addon5) {
    // 处理请求工具类
    var processUtil = new ProcessUtil();
    processUtil.vueinstance = vueinstance;
    var excelUtil = new ExcelUtil();
    excelUtil.vueinstance = vueinstance;
    var dateUtil = new DateUtil();
    dateUtil.vueinstance = vueinstance;

    // 生成表达式
    var objectScript = this.getExecuteEnviron();
    objectScript += "Object(" + objectExpression + ")";

    // 解析运行表达式
    try {
      return eval(objectScript);
    } catch (e) {
      console.error("表达式解析错误:\r\n" + objectScript + '\r\n' + e);
    }
  },
}