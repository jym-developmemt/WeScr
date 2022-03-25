<template>
  <AutoComplete
    ref="elementRef"
    v-model="textValue"
    :class="className"
    :style="this.props.style"
    :data="this.datas"
    :clearable="this.props.clearable"
    :disabled="disabled"
    :placeholder="this.props.placeholder"
    :size="this.props.size"
    :filter-method="this.props.filterMethod"
    :placement="this.props.placement"
    :transfer="this.props.transfer"
    :element-id="this.props.elementId"
    @on-change="changeHandle"
    @on-select="selectHandle"
    @on-search="searchHandle"
    @on-focus="focusHandle"
    @on-blur="blurHandle"
    @on-clear="clearHandle"
  >
    <u-icon v-if="this.props.icon" slot="suffix" :displayData="this.props.icon" :props="{}"></u-icon>
  </AutoComplete>
</template>

<script>
import CommonUtil from "../../util/CommonUtil";
import ModelUtil from "../../util/ModelUtil";
import ExpressionUtil from "../../util/ExpressionUtil";
import jsonFormat from "json-format";

export default {
  name: "AutoCompleteComponent",
  props: ["elementIndex", "props", "displayData", "detailIndex", "rowIndex", "detailModel"],
  data: function() {
    return {
      displayValue: null,
      optionsMaxLength: 10,
    };
  },
  beforeMount: function() {
    // ClassName
    if (this.props.className == null) this.$set(this.props, "className", "");
    // 样式
    if (this.props.style == null) this.$set(this.props, "style", null);
    // 是否显示清空按
    if (this.props.clearable == null) this.$set(this.props, "clearable", false);
    // 设置输入框为禁用状态
    if (this.props.disabled == null) this.$set(this.props, "disabled", false);
    // 占位文本
    if (this.props.placeholder == null) this.$set(this.props, "placeholder", null);
    // 输入框尺寸，可选值为large、small、default或者不设置
    if (this.props.size == null) this.$set(this.props, "size", "default");
    // 输入框尾部图标，仅在 text 类型下有效
    if (this.props.icon == null) this.$set(this.props, "icon", "");
    // 是否根据输入项进行筛选
    if (this.props.filterMethod == null) this.$set(this.props, "filterMethod", false);
    // 弹窗的展开方向
    if (this.props.placement == null) this.$set(this.props, "placement", "bottom-start");
    // 是否将弹层放置于 body 内
    if (this.props.transfer == null) this.$set(this.props, "transfer", false);
    // 给表单元素设置 id，详见 Form 用法。
    if (this.props.elementId == null) this.$set(this.props, "elementId", null);
    if (this.props.optionsLength != null) this.optionsMaxLength = this.props.optionsLength;
  },

  created: function() {
    this.$watch("props", this.fetchData);
    this.$watch("model." + this.modelKey, this.fetchData);
  },

  mounted: function() {
    // 保存控件实例
    CommonUtil.saveElementToStore(this);
  },
  computed: {
    textValue: {
      get: function() {
        if (this.props.format == "json") {
          return this.displayValue;
        } else {
          return this.model[this.modelKey];
        }
      },
      set: function(val) {
        if (this.props.format == "json") {
          this.displayValue = val;
        } else {
          this.$set(this.model, this.modelKey, val);
        }
      }
    },
    errorFlg: function() {
      if (this.props.format == "json" && this.displayValue) {
        try {
          var str = "{" + this.displayValue + "}";
          str = CommonUtil.replaceAll(str, "\n", "");
          str = CommonUtil.replaceAll(str, "\t", "");
          str = JSON.stringify(JSON.parse(str));
          return false;
        } catch (e) {
          return true;
        }
      } else {
        return false;
      }
    },
    model: function() {
      if (this.detailModel) {
        return this.detailModel;
      }
      return ModelUtil.getModelObject(this, this.displayData);
    },
    modelKey: function() {
      if (this.detailModel) {
        return this.displayData;
      }
      return ModelUtil.getModelKey(this, this.displayData, "");
    },
    disabled: function() {
      // 设置输入框为只读
      return this.$store.getters.isLoading || this.props.disabled;
    },
    className: function() {
      var rtnVal = "";
      if (this.props.className) {
        rtnVal += this.props.className;
      }
      if (this.errorFlg) {
        rtnVal += " ivu-form-item-error";
      }
      return rtnVal;
    },
    datas: function() {
      if (typeof this.props.datas == "string") {
        // 动态选项
        let dataOption = ExpressionUtil.createObject(
          this,
          this.props.datas,
          this.model
        );
        if (Array.isArray(dataOption) == false) {
          return [];
        }
        var displayKey = this.modelKey;
        if (this.modelKey.indexOf(".") != -1) {
          displayKey = this.modelKey.split(".")[this.modelKey.split(".").length - 1];
        }
        var datas = [];
        for (var i = 0; i < dataOption.length; i++) {
          if (this.optionsMaxLength != 0 && i >= this.optionsMaxLength) {
            break;
          }
          datas.push(dataOption[i][displayKey]);
        }
        return datas;
      } else {
        // 固定选项
        var datas = [];
        for (var i = 0; i < this.props.datas.length; i++) {
          if (this.optionsMaxLength != 0 && i >= this.optionsMaxLength) {
            break;
          }
          datas.push(this.props.datas[i]);
        }
        return datas;
      }
    }
  },
  methods: {
    fetchData: function() {
      if (this.props.format == "json") {
        try {
          var str = jsonFormat(JSON.parse(this.model[this.modelKey]));
          str = str.substring(2, str.length - 2);
          str = CommonUtil.replaceAll(str, "^\t", "");
          str = CommonUtil.replaceAll(str, ';"', '"');
          str = CommonUtil.replaceAll(str, ";}", "}");
          str = CommonUtil.replaceAll(str, ";", ";\n");
          str = CommonUtil.replaceAll(str, "{\n*\t*\n*\t*\n*}", "{}");
          str = CommonUtil.replaceAll(str, '{([^\n"}])', "{\n$1");
          str = CommonUtil.replaceAll(str, "([^\t\n{])}", "$1\n}");
          this.displayValue = str;
        } catch (e) {
          var str = this.model[this.modelKey];
          this.displayValue =
            str == null ? "" : str.substring(1, str.length - 1);
        }
      }
    },
    changeHandle: function() {
      //数据改变时触发
      if (this.props.onChange) {
        ExpressionUtil.eval(this, this.props.onChange, this.detailModel);
      }
    },
    selectHandle: function() {
      //被选中时触发
      if (this.props.onSelect) {
        ExpressionUtil.eval(this, this.props.onSelect, this.detailModel);
      }
    },
    searchHandle: function() {

      //搜索补全项的时候调用
      if (this.props.onSearch) {
        ExpressionUtil.eval(this, this.props.onSearch, this.detailModel);
      }
    },
    focusHandle: function() {
      //输入框聚焦时触发
      if (this.props.onFocus) {
        ExpressionUtil.eval(this, this.props.onFocus, this.detailModel);
      }
    },
    blurHandle: function() {
      if (this.props.format == "json") {
        if (this.errorFlg == false) {
          try {
            var str = "{" + this.displayValue + "}";
            str = CommonUtil.replaceAll(str, "\n", "");
            str = CommonUtil.replaceAll(str, "\t", "");
            str = jsonFormat(JSON.parse(str));
            str = str.substring(2, str.length - 2);
            str = CommonUtil.replaceAll(str, "^\t", "");
            str = CommonUtil.replaceAll(str, ';"', '"');
            str = CommonUtil.replaceAll(str, ";}", "}");
            str = CommonUtil.replaceAll(str, ";", ";\n");
            str = CommonUtil.replaceAll(str, "{\n*\t*\n*\t*\n*}", "{}");
            str = CommonUtil.replaceAll(str, '{([^\n"}])', "{\n$1");
            str = CommonUtil.replaceAll(str, "([^\t\n{])}", "$1\n}");
            this.displayValue = str;
          } catch (Exception) {}
        }
        this.commitJsonValue();
      }
      //输入框失去焦点时触发
      if (this.props.onBlur) {
        ExpressionUtil.eval(this, this.props.onBlur, this.detailModel);
      }
    },
    clearHandle: function() {
      //开启 clearable 时可用，点击清空按钮时触发
      if (this.props.onClear) {
        ExpressionUtil.eval(this, this.props.onClear, this.detailModel);
      }
    },
    commitJsonValue: function() {
      var val = this.displayValue;
      if (val == "") {
        this.$set(this.model, this.modelKey, val);
        return;
      }

      val = "{" + val + "}";
      try {
        var str = CommonUtil.replaceAll(val, "\n", "");
        str = CommonUtil.replaceAll(str, "\t", "");
        str = JSON.stringify(JSON.parse(str));
        this.$set(this.model, this.modelKey, str);
      } catch (e) {
        this.$set(this.model, this.modelKey, val);
      }
    }
  }
};
</script>
