import moment from "moment";

/**
 * 日期处理工具类
 */
function DateUtil() {
  /** VUE实例 */
  this.vueinstance;

  /**
   * 日期格式化
   * 
   * @param {Date} dateVal 日期数据
   * @param {string} format 格式
   */
  this.dateVal = function (dateVal, format) {
    if (dateVal == null || dateVal == "") {
      return null;
    }

    if (format == null) {
      format = "YYYY/MM/DD HH:mm:ss"
    }
    return moment(dateVal).format(format);
  };

  /**
   * 转为原生 js Date 对象
   * 
   * @param {Date} dateVal 日期数据
   * @param {string} format 格式
   */
  this.toDate = function (dateVal, format) {
    if (dateVal == null || dateVal == "") {
      return null;
    }
    return moment(dateVal, format).toDate();
  };

  /**
   * 追加N天
   * 
   * @param {Date} dateVal 日期数据
   * @param {Number} day 追加日数
   */
  this.addDays = function (dateVal, day, format) {
    if (dateVal == null || dateVal == "") {
      return null;
    }
    if (format) {
      return moment(dateVal).add(day, 'days').format(format);
    } else {
      return moment(dateVal).add(day, 'days').toDate();
    }
  };

  /**
   * 追加N月
   * 
   * @param {Date} dateVal 日期数据
   * @param {Number} month 追加月数
   */
  this.addMonths = function (dateVal, month, format) {
    if (dateVal == null || dateVal == "") {
      return null;
    }
    if (format) {
      return moment(dateVal).add(month, 'months').format(format);
    } else {
      return moment(dateVal).add(month, 'months').toDate();
    }
  };

  /**
   * 追加N年
   * 
   * @param {Date} dateVal 日期数据
   * @param {Number} year 追加年数
   */
  this.addYears = function (dateVal, year, format) {
    if (dateVal == null || dateVal == "") {
      return null;
    }
    if (format) {
      return moment(dateVal).add(year, 'years').format(format);
    } else {
      return moment(dateVal).add(year, 'years').toDate();
    }
  };

  /**
   * 日期差值
   * 
   * @param {Date|String} dateVal1 日期数据1
   * @param {Date|String} dateVal2 日期数据2
   * @param {Number} unit 差值单位
   * @param {Boolean} precise 保留小数
   */
  this.diff = function (dateVal1, dateVal2, unit, precise) {
    if (dateVal1 == null || dateVal1 == "" || dateVal2 == null || dateVal2 == "") {
      return 0;
    }
    if (unit == 0) {
      unit = 'years';
    } else if (unit == 1) {
      unit = 'months';
    } else if (unit == 3) {
      unit = 'hours';
    } else if (unit == 4) {
      unit = 'minutes';
    } else if (unit == 5) {
      unit = 'seconds';
    } else if (unit == 6) {
      unit = 'weeks';
    } else {
      unit = 'days';
    }
    return moment(dateVal1).diff(dateVal2, unit, precise);
  };

  /**
   * 日期比较
   * 
   * @param {Date|String} dateVal1 日期数据1
   * @param {Date|String} dateVal2 日期数据2
   * @param {Number} checkLevel 比较级别
   */
  this.isBefore = function (dateVal1, dateVal2, checkLevel) {
    if (dateVal1 == null || dateVal1 == "" || dateVal2 == null || dateVal2 == "") {
      return false;
    }
    if (checkLevel == 0) {
      checkLevel = 'year';
    } else if (checkLevel == 1) {
      checkLevel = 'month';
    } else if (checkLevel == 3) {
      checkLevel = 'hour';
    } else if (checkLevel == 4) {
      checkLevel = 'minute';
    } else if (checkLevel == 5) {
      checkLevel = 'second';
    } else if (checkLevel == 6) {
      checkLevel = 'millisecond';
    } else {
      checkLevel = 'day';
    }
    return moment(dateVal1).isBefore(dateVal2, checkLevel);
  };

  /**
   * 日期比较
   * 
   * @param {Date|String} dateVal1 日期数据1
   * @param {Date|String} dateVal2 日期数据2
   * @param {Number} checkLevel 比较级别
   */
  this.isSame = function (dateVal1, dateVal2, checkLevel) {
    if (dateVal1 == null || dateVal1 == "" || dateVal2 == null || dateVal2 == "") {
      return false;
    }
    if (checkLevel == 0) {
      checkLevel = 'year';
    } else if (checkLevel == 1) {
      checkLevel = 'month';
    } else if (checkLevel == 3) {
      checkLevel = 'hour';
    } else if (checkLevel == 4) {
      checkLevel = 'minute';
    } else if (checkLevel == 5) {
      checkLevel = 'second';
    } else if (checkLevel == 6) {
      checkLevel = 'millisecond';
    } else {
      checkLevel = 'day';
    }
    return moment(dateVal1).isSame(dateVal2, checkLevel);
  };

  /**
   * 日期比较
   * 
   * @param {Date|String} dateVal1 日期数据1
   * @param {Date|String} dateVal2 日期数据2
   * @param {Number} checkLevel 比较级别
   */
  this.isAfter = function (dateVal1, dateVal2, checkLevel) {
    if (dateVal1 == null || dateVal1 == "" || dateVal2 == null || dateVal2 == "") {
      return false;
    }
    if (checkLevel == 0) {
      checkLevel = 'year';
    } else if (checkLevel == 1) {
      checkLevel = 'month';
    } else if (checkLevel == 3) {
      checkLevel = 'hour';
    } else if (checkLevel == 4) {
      checkLevel = 'minute';
    } else if (checkLevel == 5) {
      checkLevel = 'second';
    } else if (checkLevel == 6) {
      checkLevel = 'millisecond';
    } else {
      checkLevel = 'day';
    }
    return moment(dateVal1).isAfter(dateVal2, checkLevel);
  };

  /**
   * 日期比较
   * 
   * @param {Date|String} dateVal1 日期数据1
   * @param {Date|String} dateVal2 日期数据2
   * @param {Date|String} dateVal3 日期数据3
   * @param {Number} checkLevel 比较级别
   * @param {String} inclusivity 包含性
   */
  this.isBetween = function (dateVal1, dateVal2, dateVal3, checkLevel, inclusivity) {
    if (dateVal1 == null || dateVal1 == "" || dateVal2 == null || dateVal2 == "" || dateVal3 == null || dateVal3 == "") {
      return false;
    }
    if (checkLevel == 0) {
      checkLevel = 'year';
    } else if (checkLevel == 1) {
      checkLevel = 'month';
    } else if (checkLevel == 3) {
      checkLevel = 'hour';
    } else if (checkLevel == 4) {
      checkLevel = 'minute';
    } else if (checkLevel == 5) {
      checkLevel = 'second';
    } else if (checkLevel == 6) {
      checkLevel = 'millisecond';
    } else {
      checkLevel = 'day';
    }
    return moment(dateVal1).isBetween(dateVal2, dateVal3, checkLevel, inclusivity);
  };

  /**
   * 取最大日期
   * 
   * @param {Date|String} dateVal1 日期数据1
   * @param {Date|String} dateVal2 日期数据2
   * @param {string} format 格式
   */
  this.max = function (dateVal1, dateVal2, format) {
    if (dateVal1 == null || dateVal1 == "" || dateVal2 == null || dateVal2 == "") {
      return null;
    }
    if (format) {
      return moment.max(moment(dateVal1), moment(dateVal2)).format(format);
    } else {
      return moment.max(moment(dateVal1), moment(dateVal2)).toDate();
    }
  };

  /**
   * 取最小日期
   * 
   * @param {Date|String} dateVal1 日期数据1
   * @param {Date|String} dateVal2 日期数据2
   * @param {string} format 格式
   */
  this.min = function (dateVal1, dateVal2, format) {
    if (dateVal1 == null || dateVal1 == "" || dateVal2 == null || dateVal2 == "") {
      return null;
    }
    if (format) {
      return moment.min(moment(dateVal1), moment(dateVal2)).format(format);
    } else {
      return moment.min(moment(dateVal1), moment(dateVal2)).toDate();
    }
  };

  /**
   * 取当前周的第几天
   * 
   * @param {Date|String} dateVal 日期
   * @param {Number}} weekNum 第几天(1-7:周一-周日)
   * @param {string} format 格式
   */
  this.getWeekday = function(dateVal, weekNum, format) {
    if (dateVal == null || dateVal == "") {
      return null;
    }
    if (format) {
      return moment(dateVal).isoWeekday(weekNum).format(format);
    } else {
      return moment(dateVal).isoWeekday(weekNum).toDate();
    }
  };

  /**
   * 日期比较是否在同一周
   * 
   * @param {Date|String} dateVal1 日期数据1
   * @param {Date|String} dateVal2 日期数据2
   */
  this.isSameWeek = function (dateVal1, dateVal2) {
    if (dateVal1 == null || dateVal1 == "" || dateVal2 == null || dateVal2 == "") {
      return false;
    }
    return moment(this.getWeekday(dateVal1, 1)).isSame(this.getWeekday(dateVal2, 1));
  };
}

export default DateUtil;