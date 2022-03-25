<template>
  <div
    ref="elementRef"
    :class="this.props.className"
    :style="this.props.style"
    @click="clickHandle"
  >
    <span v-if="this.props.icon" :class="this.props.icon"></span>
    <span v-html="text"></span>
  </div>
</template>

<script>
import CommonUtil from "../../util/CommonUtil";
import ModelUtil from "../../util/ModelUtil";
import ExpressionUtil from "../../util/ExpressionUtil";
import moment from "moment";
import numeral from "numeral";

export default {
  name: "LabelComponent",
  props: ["elementIndex", "props", "displayData", "detailIndex", "rowIndex", "detailModel"],
  beforeMount: function () {
    // ClassName
    if (this.props.className == null) this.$set(this.props, "className", null);
    // 样式
    if (this.props.style == null) this.$set(this.props, "style", null);
    // 表达式
    if (this.props.expression == null) this.$set(this.props, "expression", "");
    // 时间格式
    if (this.props.dateFormat == null)
      this.$set(this.props, "dateFormat", null);
    // 数字格式
    if (this.props.numberFormat == null)
      this.$set(this.props, "numberFormat", null);
    // 选项
    if (this.props.options == null) this.$set(this.props, "options", null);
    // 选项值
    if (this.props.valueKey == null) this.$set(this.props, "valueKey", "0");
    // 选项标签
    if (this.props.labelKey == null) this.$set(this.props, "labelKey", "1");
    // 换行方式
    if (this.props.wrap == null) this.$set(this.props, "wrap", null);
    // 多语言
    if (this.props.i18n == null) this.$set(this.props, "i18n", false);
  },
  mounted: function () {
    // 保存控件实例
    CommonUtil.saveElementToStore(this);
  },
  computed: {
    // 表示文本
    text: function () {
      let textData = "";
      if (this.props.expression) {
        textData = ExpressionUtil.eval(
          this,
          "return " + this.props.expression,
          this.detailModel
        );
      } else {
        if (this.detailModel) {
          textData = ModelUtil.getDetailModelValue(
            this,
            this.detailModel,
            this.displayData
          );
        } else {
          // 固定文本
          textData = this.displayData;
        }
      }

      if (textData && this.props.dateFormat) {
        textData = moment(textData).format(this.props.dateFormat);
      }

      if (textData && this.props.numberFormat) {
        textData = numeral(textData).format(this.props.numberFormat);
      }

      if ((textData || textData == '0') && this.props.options) {
        var ops = [];
        if (typeof this.props.options == "string") {
          // 动态选项
          ops = ExpressionUtil.createObject(
            this,
            this.props.options,
            this.detailModel
          );
        } else {
          // 固定选项
          ops = this.props.options;
        }
        if (Array.isArray(ops)) {
          for (let i = 0; i < ops.length; i++) {
            if (ops[i][this.props.valueKey] == textData) {
              textData = ops[i][this.props.labelKey];
              break;
            }
          }
        } else if (ops && ops[textData]) {
          textData = ops[textData];
        }
      }

      if (this.props.wrap == '0' && textData) {
        textData = CommonUtil.replaceAll(textData, '\n', '<br>');
      }

      if (this.props.i18n) {
        textData = this.$t(textData);
      }

      return textData;
    },
  },
  methods: {
    clickHandle: function () {
      // 点击标签时触发
      if (this.props.onClick) {
        ExpressionUtil.eval(this, this.props.onClick, this.detailModel);
      }
    },
  },
};
</script>