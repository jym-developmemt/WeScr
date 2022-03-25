import ExpressionUtil from "./ExpressionUtil";
import XLSX from 'xlsx';
import moment from "moment";

/**
 * 处理请求工具类
 */
function ExcelUtil() {
  /** VUE实例 */
  this.vueinstance;

  /**
   * Excel文件读取
   * 
   * @param {*} file 文件对象
   * @param {*} callback 回调方法
   */
  this.readXlsxFile = function (file, callback) {
    var vue = this.vueinstance;
    // 加载开始
    vue.$store.commit("loadingStart");

    var rABS = typeof FileReader !== 'undefined' && FileReader.prototype && FileReader.prototype.readAsBinaryString;

    var fixdata = function (data) {
      var o = "", l = 0, w = 10240;
      for (; l < data.byteLength / w; ++l)
        o += String.fromCharCode.apply(null, new Uint8Array(data.slice(l * w, l * w + w)));
      o += String.fromCharCode.apply(null, new Uint8Array(data.slice(o.length)));
      return o;
    }

    var reader = new FileReader();
    reader.onload = function (e) {
      vue.$store.commit("loadingFinish");

      var data = e.target.result;
      var wb, arr;
      var readtype = { type: rABS ? 'binary' : 'base64' };
      if (!rABS) {
        arr = fixdata(data);
        data = btoa(arr);
      }
      wb = XLSX.read(data, readtype);
      ExpressionUtil.eval(vue, callback, wb);
    };

    try {
      if (rABS) reader.readAsBinaryString(file);
      else reader.readAsArrayBuffer(file);
      return false;
    } catch (e) {
      vue.$store.commit("loadingError");
    }
  };

  /**
   * Workbook转Json数据
   * 
   * @param {*} wb Workbook
   */
  this.getAllData = function (wb) {
    var result = {};
    wb.SheetNames.forEach(function (sheetName) {
      var roa = XLSX.utils.sheet_to_json(wb.Sheets[sheetName], { raw: false, header: 1 });
      if (roa.length > 0) result[sheetName] = roa;
    });
    return result;
  };

  /**
   * Workbook转Json数据
   * 
   * @param {*} wb Workbook
   * @param {*} callback 回调方法
   */
  this.getJsonData = function (wb, keys, sheetIdx, rowStd, colStd) {

    if (sheetIdx == null) sheetIdx = 0;
    if (rowStd == null) rowStd = 1;
    if (colStd == null) colStd = 0;

    var sheet = wb.Sheets[wb.SheetNames[sheetIdx]];
    var rows = XLSX.utils.sheet_to_json(sheet, { raw: false, header: 1 });

    var rtnList = [];
    for (var i = rowStd; i < rows.length; i++) {
      var rtnData = {};
      for (var j = colStd; j < rows[i].length && j < keys.length; j++) {
        if (keys[j]) {
          if (typeof rows[i][j] == "string") {
            rtnData[keys[j]] = rows[i][j].trim();
          } else {
            rtnData[keys[j]] = rows[i][j];
          }
        }
      }
      rtnList.push(rtnData);
    }
    return rtnList;
  };

  /**
   * Workbook转Json数据
   * 
   * @param {*} wb Workbook
   */
  this.exportAllData = function (fileName, keys, arrayData) {
    if (arrayData == null || arrayData.length == 0) {
      this.vueinstance.$Message.error("数据不存在！");
      return;
    }

    var exportData = [];
    for (var i = 0; i < arrayData.length; i++) {
      if (i == 0) {
        var titles = [];
        for (var j = 0; j < keys.length; j++) {
          titles.push(keys[j][0]);
        }
        exportData.push(titles);
      }
      var datas = [];
      for (var j = 0; j < keys.length; j++) {
        var data = this.getItemValue(arrayData[i], keys[j][1]);

        if (keys[j][2] && data) {
          data = moment(data).format(keys[j][2])
        }

        if (keys[j][3] && data) {
          var options = keys[j][3];
          if (typeof options == "string") {
            // 动态选项
            options = ExpressionUtil.createObject(this.vueinstance, options);
            if (Array.isArray(options) == false) {
              options = [];
            }
          }
          for (var k = 0; k < options.length; k++) {
            if (options[k][0] == data) {
              data = options[k][1];
              break;
            }
          }
        }

        datas.push(data);
      }
      exportData.push(datas);
    }

    var wb = XLSX.utils.book_new();
    var ws = XLSX.utils.aoa_to_sheet(exportData);

    /* add worksheet to workbook */
    XLSX.utils.book_append_sheet(wb, ws, "sheet0");

    /* write workbook */
    XLSX.writeFile(wb, fileName);
  };

  this.getItemValue = function (item, key, i) {
    if (i == null) {
      i = 0;
    }
    var keys = key.split('.');
    if (keys.length - 1 > i) {
      return this.getItemValue(item[keys[i]], key, i + 1);
    } else {
      return item[keys[i]]
    }
  }
}

export default ExcelUtil;