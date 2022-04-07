<template>
  <div ref="elementRef" style="display: inline-block">
    <!-- 导入按钮 -->
    <Button
      :class="this.props.className"
      :style="this.props.style"
      :type="this.props.type"
      :ghost="this.props.ghost"
      :size="this.props.size"
      :shape="this.props.shape"
      :long="this.props.long"
      :html-type="this.props.htmlType"
      :disabled="disabled"
      :loading="this.props.loading"
      :to="this.props.to"
      :replace="this.props.replace"
      :target="this.props.target"
      :append="this.props.append"
      @click="clickHandle"
    >
      <u-icon v-if="this.props.icon" :displayData="this.props.icon" :props="{}"></u-icon>
      <span v-if="this.displayData">{{ this.props.i18n ? $t(this.displayData) : this.displayData }}</span>
      <u-icon v-if="this.props.iconLast" :displayData="this.props.iconLast" :props="{}"></u-icon>
    </Button>
    <!-- 导入对话框 -->
    <Modal
      v-model="this.dataModal"
      :title="this.modalTitle"
      :closable="false"
      :mask-closable="false"
      :footer-hide="true"
      :width="this.props.width"
      :resizable="true"
      @on-visible-change="refreshTable"
    >
      <Row>
        <Col :span="24">
          <vxe-table
            ref="dataTable"
            :cell-class-name="cellClassName"
            :data="importDataList"
            :height="438"
            border="full"
            size="small"
            :loading="this.$store.getters.isLoading"
            :editRules="this.editRules"
            :edit-config="this.editConfig"
            :valid-config="this.validConfig"
          >
            <!-- 序号列 -->
            <vxe-table-column width="45" align="center" type="seq" class-name="no-padding-cell" />
            <!-- 删除按钮 -->
            <vxe-table-column width="50" align="center" title="s.common.btn.del" header-class-name="no-padding-cell" class-name="no-padding-cell">
              <template v-slot="scope">
                <Icon class="clickable color-red" type="md-close" @click="removeData(scope.row)" />
              </template>
            </vxe-table-column>
            <!-- 数据列 -->
            <u-vxe-table-column :key="column.key" v-for="column in columns" :props="column"></u-vxe-table-column>
          </vxe-table>
        </Col>
      </Row>
      <Row style="margin-top: 16px">
        <Col :span="12" class="button-lead">
          <!-- 文件上传按钮 -->
          <Upload
            accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel"
            action=""
            :format="['xlsx', 'xls']"
            :before-upload="this.beforeUploadHandle"
            :disabled="$store.getters.isLoading"
            style="display: inline-block"
          >
            <Button type="warning" :disabled="$store.getters.isLoading">
              <u-icon displayData="fas file-import" :props="{}"></u-icon>
              {{ $t("s.common.btn.import") }}
            </Button>
          </Upload>
          <!-- 行追加按钮 -->
          <Button type="success" :disabled="$store.getters.isLoading" @click="importDataList.push({})">
            <u-icon displayData="md-add" :props="{}"></u-icon>
            {{ $t("s.common.btn.add") }}
          </Button>
          <!-- 模板下载链接 -->
          <Button v-if="this.props.templateId" type="text" :disabled="$store.getters.isLoading" @click="downloadTemplate()">
            {{ $t("s.common.btn.download_template") }}
          </Button>
        </Col>
        <Col :span="12" class="button-col">
          <!-- 保存按钮 -->
          <Button type="primary" :disabled="$store.getters.isLoading || importDataList.length == 0" @click="saveData()">
            {{ $t("s.common.btn.save") }}
          </Button>
          <!-- 关闭按钮 -->
          <Button type="default" :disabled="$store.getters.isLoading" @click="close()">
            {{ $t("s.common.btn.cancel") }}
          </Button>
        </Col>
      </Row>
    </Modal>
  </div>
</template>

<script>
import APIUtil from "../../util/APIUtil";
import CommonUtil from "../../util/CommonUtil";
import ExpressionUtil from "../../util/ExpressionUtil";
import XLSX from "xlsx";

export default {
  name: "ButtonComponent",
  props: ["elementIndex", "props", "displayData", "detailList"],
  beforeMount: function () {
    // ClassName
    if (this.props.className == null) this.$set(this.props, "className", null);
    // 样式
    if (this.props.style == null) this.$set(this.props, "style", null);
    // 按钮类型，可选值为 default、primary、dashed、text、info、success、warning、error或者不设置
    if (this.props.type == null) this.$set(this.props, "type", "default");
    // 幽灵属性，使按钮背景透明
    if (this.props.ghost == null) this.$set(this.props, "ghost", false);
    // 按钮大小，可选值为large、small、default或者不设置
    if (this.props.size == null) this.$set(this.props, "size", "default");
    // 按钮形状，可选值为circle或者不设置
    if (this.props.shape == null) this.$set(this.props, "shape", null);
    // 开启后，按钮的长度为 100%
    if (this.props.long == null) this.$set(this.props, "long", false);
    // 设置button原生的type，可选值为button、submit、reset
    if (this.props.htmlType == null)
      this.$set(this.props, "htmlType", "button");
    // 设置按钮为禁用状态
    if (this.props.disabled == null) this.$set(this.props, "disabled", false);
    // 设置按钮为加载中状态
    if (this.props.loading == null) this.$set(this.props, "loading", false);
    // 设置按钮的图标类型
    if (this.props.icon == null) this.$set(this.props, "icon", null);
    if (this.props.iconLast == null) this.$set(this.props, "iconLast", null);
    // 跳转的链接，支持 vue-router 对象
    if (this.props.to == null) this.$set(this.props, "to", null);
    // 路由跳转时，开启 replace 将不会向 history 添加新记录
    if (this.props.replace == null) this.$set(this.props, "replace", false);
    // 相当于 a 链接的 target 属性
    if (this.props.target == null) this.$set(this.props, "target", "_self");
    // 同 vue-router append
    if (this.props.append == null) this.$set(this.props, "append", false);
    // 多语言
    if (this.props.i18n == null) this.$set(this.props, "i18n", true);
    // 页面宽度
    if (this.props.width == null) this.$set(this.props, "width", 1000);
    // 标题
    if (this.props.title == null) this.$set(this.props, "title", null);
    // 校验规则配置项{[field: string]: Array<Object>}
    if (this.props.editRules == null) this.$set(this.props, "editRules", {});
    // 数据读取
    if (this.props.dataPreLoad == null)
      this.$set(this.props, "dataPreLoad", []);
    // 模板ID
    if (this.props.templateId == null)
      this.$set(this.props, "templateId", null);
    // 开始行番号(0~)
    if (this.props.headerRowIndex == null)
      this.$set(this.props, "headerRowIndex", 0);
  },
  mounted: function() {
    // 保存控件实例
    CommonUtil.saveElementToStore(this);
  },
  data() {
    return {
      dataModal: false,
      importDataList: [],
      editConfig: {
        trigger: "click",
        mode: "row",
        showIcon: false,
        showAsterisk: true,
        autoClear: true,
      },
      validConfig: {
        autoPos: false,
      },
      preLoadErrors: {},
      errorCells: [],
    };
  },
  computed: {
    disabled: function () {
      // 设置输入框为只读
      var disabled = this.$store.getters.isLoading;
      if (disabled) {
        return disabled;
      }
      if (typeof this.props.disabled == "string") {
        return ExpressionUtil.eval(this, "return " + this.props.disabled, this.detailModel);
      } else {
        return this.props.disabled;
      }
    },
    modalTitle: function () {
      if (this.props.i18n) {
        return this.$t("s.common.label.import", [this.$t(this.props.title)]);
      } else {
        return this.props.title;
      }
    },
    editRules: function () {
      var me = this;
      var rtnEditRules = JSON.parse(JSON.stringify(this.props.editRules));
      for (var key in rtnEditRules) {
        rtnEditRules[key].push({
          validator: function ({cellValue, rule, rules, row, rowIndex, column, columnIndex}) {
            var columnDef = me.columns[columnIndex - 2];
            if (columnDef == null || cellValue == null) {
              return;
            }

            // 下拉框选项校验
            if (columnDef.import.options) {
              // 动态选项
              let dataOption = [];
              if (typeof columnDef.import.options == "string") {
                dataOption = ExpressionUtil.createObject(me, columnDef.import.options, this.model);
              } else {
                dataOption = columnDef.import.options;
              }
              if (Array.isArray(dataOption) == false) {
                dataOption = [];
              }

              var hasData = false;
              for (var k = 0; k < dataOption.length; k++) {
                var importValue = dataOption[k][columnDef.import.valueKey];
                if (importValue == cellValue) {
                  hasData = true;
                  break;
                }
              }
              if (hasData == false) {
                return new Error("s.common.error.no_option");
              }
            }

            // 数据重复校验
            var checkFields = columnDef.import.duplicationCheckFields;
            if (checkFields && checkFields.length > 0) {
              for (var i = 0; i < rowIndex; i++) {
                var data1 = me.importDataList[i];
                var data2 = me.importDataList[rowIndex];

                var errorFlg = true;
                for (var j = 0; j < checkFields.length; j++) {
                  if (data1[checkFields[j]] != data2[checkFields[j]]) {
                    errorFlg = false;
                    break;
                  }
                }
                if (errorFlg == true) {
                  return new Error("s.common.error.duplication");
                  break;
                }
              }
            }

            // DB存在校验
            var preLoadError = me.preLoadErrors[rowIndex + ":" + column.property];
            if (preLoadError == "1") {
              return new Error("s.common.error.exist");
            } else if (preLoadError == "2") {
              return new Error("s.common.error.not_exist");
            }
          },
        });
      }
      return rtnEditRules;
    },
    columns: function () {
      var tableColumn = new Array();
      if (this.detailList) {
        for (let i = 0; i < this.detailList.length; i++) {
          var columnDetail = this.detailList[i];

          // 列属性
          var columnProp = {};
          if (columnDetail.detailProp) {
            columnProp = JSON.parse(columnDetail.detailProp);
          }

          columnProp.title = columnDetail.detailName;
          columnProp.key = columnDetail.detailIndex;
          columnProp.field = columnDetail.displayData;

          if (columnDetail.displayType) {
            // 控件属性
            columnProp.elementInfo = {
              elementIndex: columnDetail.elementIndex,
              detailIndex: columnDetail.detailIndex,
              displayType: columnDetail.displayType,
              displayData: columnDetail.displayData,
              detailAddon: columnDetail.detailAddon,
            };
          }

          tableColumn.push(columnProp);
        }
      }
      return tableColumn;
    },
  },
  methods: {
    refreshTable: function (status) {
      if (status) {
        this.$refs.dataTable.refreshColumn();
      }
    },
    removeData: function (data) {
      var i = this.importDataList.indexOf(data);
      this.$delete(this.importDataList, i);
    },
    clickHandle: function () {
      this.importDataList.splice(0, this.importDataList.length);
      this.dataModal = true;
    },
    downloadTemplate: function () {
      var me = this;

      // 加载开始
      this.$store.commit("loadingStart");

      APIUtil.loadResource(this, this.props.templateId)
        .then(function (data) {
          var requestHeader = me.$store.getters.pageData.axiosHeader;
          var fileName = requestHeader["content-disposition"].replace("attachment;fileName=", "");
          fileName = decodeURIComponent(fileName);

          // 数据下载
          let blob = new Blob([data], { type: "application/octet-stream;charset=utf-8" });
          let FileSaver = require("file-saver");
          FileSaver.saveAs(blob, fileName);
        })
        .finally(function () {
          // 加载完成
          me.$store.commit("loadingFinish");
        });
    },
    beforeUploadHandle: function (file) {
      var me = this;

      // 加载开始
      this.$store.commit("loadingStart");

      var rABS = typeof FileReader !== "undefined" && FileReader.prototype && FileReader.prototype.readAsBinaryString;
      var fixdata = function (data) {
        var o = "", l = 0, w = 10240;
        for (; l < data.byteLength / w; ++l)
          o += String.fromCharCode.apply(null, new Uint8Array(data.slice(l * w, l * w + w)));
        o += String.fromCharCode.apply(null, new Uint8Array(data.slice(o.length)));
        return o;
      };

      var reader = new FileReader();
      reader.onload = function (e) {
        var data = e.target.result;
        var wb, arr;
        var readtype = { type: rABS ? "binary" : "base64" };
        if (!rABS) {
          arr = fixdata(data);
          data = btoa(arr);
        }
        wb = XLSX.read(data, readtype);
        me.importHandle(wb);
      };

      try {
        if (rABS) reader.readAsBinaryString(file);
        else reader.readAsArrayBuffer(file);
        return false;
      } catch (e) {
        this.$store.commit("loadingError");
      }

      return false;
    },
    cellClassName: function ({ row, rowIndex, column, columnIndex }) {
      if (this.errorCells[rowIndex] != null && this.errorCells[rowIndex][columnIndex] == 1) {
        return "error-cell";
      } else {
        return "";
      }
    },
    importHandle: function (workbook) {
      try {
        var sheet = workbook.Sheets[workbook.SheetNames[0]];
        var rows = XLSX.utils.sheet_to_json(sheet, { raw: false, header: 1, defval: "" });

        this.importDataList.splice(0, this.importDataList.length);

        for (var i = this.props.headerRowIndex + 1; i < rows.length; i++) {
          var rtnDataInfo = {};
          var hasData = false;

          var xlsData = rows[i];
          for (var j = 0; j < this.columns.length; j++) {
            var columnInfo = this.columns[j];
            if (columnInfo.import.colIndex == null && columnInfo.import.colName == null) {
              continue;
            }
            var colIndex = null;
            if (columnInfo.import.colName) {
              for (var k = 0; k < rows[this.props.headerRowIndex].length; k++) {
                var title = rows[this.props.headerRowIndex][k];
                if (title.replace("*", "").trim() == this.$t(columnInfo.import.colName)) {
                  colIndex = k;
                  break;
                }
              }
            }
            if (colIndex == null) {
              colIndex = columnInfo.import.colIndex;
            }
            var dataItemValue = null;
            if (colIndex != null) {
              dataItemValue = xlsData[colIndex];
            }

            if (dataItemValue && columnInfo.import.options) {
              // 动态选项
              let dataOption = [];
              if (typeof columnInfo.import.options == "string") {
                dataOption = ExpressionUtil.createObject(this, columnInfo.import.options, this.model);
              } else {
                dataOption = columnInfo.import.options;
              }
              if (Array.isArray(dataOption) == false) {
                dataOption = [];
              }

              for (var k = 0; k < dataOption.length; k++) {
                var importKeyValue = this.props.i18n ? this.$t(dataOption[k][columnInfo.import.importKey]) : dataOption[k][columnInfo.import.importKey];
                if (importKeyValue == dataItemValue) {
                  dataItemValue = dataOption[k][columnInfo.import.valueKey];
                  break;
                }
              }
            }

            if (dataItemValue) {
              hasData = true;
            } else {
              dataItemValue = null;
            }

            rtnDataInfo[columnInfo.field] = dataItemValue;
          }
          if (hasData == false) {
            break;
          } else {
            this.importDataList.push(rtnDataInfo);
          }
        }

        // 导入时触发
        if (this.props.onImport) {
          ExpressionUtil.eval(this, this.props.onImport, this.importDataList, workbook);
        }

        this.$refs.dataTable.loadData(this.importDataList);

        this.$store.commit("loadingFinish");

        this.fullValidEvent();
      } catch (e) {
        this.$store.commit("loadingError");
      }
    },
    fullValidEvent: function () {
      // 加载开始
      this.$store.commit("loadingStart");

      this.errorCells.splice(0, this.errorCells.length);
      var me = this;

      var processList = [];
      var dataIndex = [];
      this.preLoadErrors = {};

      // DB数据读取
      if (this.props.dataPreLoad.length > 0) {
        for (var i = 0; i < this.props.dataPreLoad.length; i++) {
          var preLoadInfo = this.props.dataPreLoad[i];
          if (preLoadInfo.dbIndex == null) {
            continue;
          }

          for (var j = 0; j < this.importDataList.length; j++) {
            var condition = {};
            for (var k = 0; k < preLoadInfo.conditionFields.length; k++) {
              var importDataValue = this.importDataList[j][preLoadInfo.conditionFields[k]];
              if (importDataValue == null || importDataValue == "") {
                continue;
              }
              condition[preLoadInfo.conditionFields[k]] = importDataValue;
            }
            if (JSON.stringify(condition) == "{}") {
              condition[preLoadInfo.conditionFields[0]] = "__null__";
            }

            dataIndex.push(preLoadInfo.dbIndex);
            processList.push(
              APIUtil.convertProcessParam(this, { t: "P00", a: "p", s3: preLoadInfo.dbIndex, o1: condition })
            );
          }
        }
      }
      return APIUtil.sendProcessRequest(this, processList).then(function (dataList) {
        if (me.props.dataPreLoad.length > 0) {
          var dataIndex = 0;

          for (var i = 0; i < me.props.dataPreLoad.length; i++) {
            var preLoadInfo = me.props.dataPreLoad[i];
            if (preLoadInfo.dbIndex == null) {
              continue;
            }

            for (var j = 0; j < me.importDataList.length; j++) {
              var dataInfo = dataList[dataIndex++];

              if (preLoadInfo.existError && dataInfo.length > 0) {
                for (var k = 0; k < preLoadInfo.errorFields.length; k++) {
                  me.preLoadErrors[j + ":" + preLoadInfo.errorFields[k]] = "1";
                }
              } else if (preLoadInfo.notExistError && dataInfo.length == 0) {
                for (var k = 0; k < preLoadInfo.errorFields.length; k++) {
                  me.preLoadErrors[j + ":" + preLoadInfo.errorFields[k]] = "2";
                }
              } else if (dataInfo.length > 0 && preLoadInfo.setFields != null) {
                for (var key in preLoadInfo.setFields) {
                  me.$set(me.importDataList[j], key, dataInfo[0][preLoadInfo.setFields[key]]);
                }
              }
            }
          }

          me.$refs.dataTable.loadData(me.importDataList);
        }

        return me.$refs.dataTable
          .fullValidate()
          .then(function () {
            return 1;
          })
          .catch((errMap) => {
            var errorRow = null;
            var errorColumn = null;
            Object.values(errMap).forEach((errList) => {
              errList.forEach((params) => {
                let { rowIndex, columnIndex, row, column, rules } = params;
                if (me.errorCells[rowIndex] == null) {
                  me.errorCells[rowIndex] = [];
                }
                me.errorCells[rowIndex][columnIndex] = 1;

                if (errorRow == null) {
                  errorRow = row;
                  errorColumn = column;
                }
              });
            });
            me.$Message.error(me.$t("s.common.error.data_check"));
            me.$refs.dataTable.scrollToRow(errorRow, errorColumn);
          })
          .finally(function () {
            me.$refs.dataTable.refreshColumn();
            me.$store.commit("loadingFinish");
          });
      });
    },
    saveData: function () {
      var me = this;
      this.fullValidEvent().then(function (result) {
        if (result == 1) {
          // 保存时触发
          if (me.props.onSave) {
            me.$Modal.confirm({
              title: me.$t("s.common.message.import_confirm"),
              onOk: () => {
                // 表达式实行
                ExpressionUtil.eval(me, me.props.onSave, me.importDataList);
              },
            });
          }
        }
      });
    },
    close: function() {
      this.dataModal = false
    }
  },
};
</script>

<style scoped>
.vxe-table /deep/ .vxe-cell--valid {
  width: auto !important;
  left: 8px !important;
  transform: none !important;
}

.vxe-table /deep/ .vxe-cell--valid .vxe-cell--valid-msg {
  white-space: nowrap !important;
}
.vxe-table /deep/ .error-cell {
  background-color: #fff0f0;
}

.vxe-table /deep/ .error-cell:before {
  content: "";
  top: -8px;
  left: -8px;
  position: absolute;
  border-width: 8px;
  border-style: solid;
  border-color: transparent #f56c6c transparent transparent;
  -webkit-transform: rotate(45deg);
  transform: rotate(45deg);
}
</style>