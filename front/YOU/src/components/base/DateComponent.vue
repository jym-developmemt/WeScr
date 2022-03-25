<template>
  <DatePicker
    ref="elementRef"
    v-model="model[modelKey]"
    :class="this.props.className"
    :style="this.props.style"
    :type="this.props.type"
    :format="this.props.format"
    :placement="this.props.placement"
    :placeholder="this.props.placeholder"
    :split-panels="this.props.splitPanels"
    :multiple="this.props.multiple"
    :show-week-numbers="this.props.showWeekNumbers"
    :start-date="this.props.startDate"
    :confirm="this.props.confirm"
    :open="this.props.open"
    :size="this.props.size"
    :disabled="disabled"
    :clearable="this.props.clearable"
    :readonly="this.props.readonly"
    :editable="this.props.editable"
    :transfer="this.props.transfer"
    :element-id="this.props.elementId"
    :separator="this.props.separator"
    :capture="this.props.capture"
    :options="{disabledDate:this.checkDisabledDate}"
    @on-change="changeHandle"
    @on-open-change="openChangeHandle"
    @on-ok="okHandle"
    @on-clear="clearHandle"
    @on-clickoutside="clickoutsideHandle"
  />
</template>

<script>
import CommonUtil from "../../util/CommonUtil";
import ModelUtil from "../../util/ModelUtil";
import ExpressionUtil from "../../util/ExpressionUtil";
import moment from "moment";

export default {
  name: "DateComponent",
  props: ["elementIndex", "props", "displayData", "detailIndex", "rowIndex", "detailModel"],
  beforeMount: function() {
    // ClassName
    if (this.props.className == null) this.$set(this.props, "className", null);
    // 样式
    if (this.props.style == null) this.$set(this.props, "style", null);
    // 显示类型，可选值为 date、daterange、datetime、datetimerange、year、month
    if (this.props.type == null) this.$set(this.props, "type", "date");
    // 展示的日期格式
    if (this.props.format == null)
      this.$set(this.props, "format", "yyyy/MM/dd");
    // 日期选择器出现的位置
    if (this.props.placement == null)
      this.$set(this.props, "placement", "bottom-start");
    // 占位文本
    if (this.props.placeholder == null)
      this.$set(this.props, "placeholder", "请选择日期");
    // 开启后，左右面板不联动，仅在 daterange 和 datetimerange 下可用。
    if (this.props.splitPanels == null)
      this.$set(this.props, "splitPanels", false);
    // 开启后，可以选择多个日期，仅在 date 下可用。
    if (this.props.multiple == null) this.$set(this.props, "multiple", false);
    // 开启后，可以显示星期数。
    if (this.props.showWeekNumbers == null)
      this.$set(this.props, "showWeekNumbers", false);
    // 设置默认显示的起始日期。
    if (this.props.startDate == null) this.$set(this.props, "startDate", null);
    // 是否显示底部控制栏，开启后，选择完日期，选择器不会主动关闭，需用户确认后才可关闭
    if (this.props.confirm == null) this.$set(this.props, "confirm", false);
    // 手动控制日期选择器的显示状态，true 为显示，false 为收起。使用该属性后，选择器不会主动关闭。
    if (this.props.open == null) this.$set(this.props, "open", null);
    // 输入框尺寸，可选值为large、small、default或者不设置
    if (this.props.size == null) this.$set(this.props, "size", "default");
    // 是否禁用选择器
    if (this.props.disabled == null) this.$set(this.props, "disabled", false);
    // 是否显示清空按钮
    if (this.props.clearable == null) this.$set(this.props, "clearable", true);
    // 完全只读，开启后不会弹出选择器，只在没有设置 open 属性下生效
    if (this.props.readonly == null) this.$set(this.props, "readonly", false);
    // 文本框是否可以输入，只在没有使用 slot 时有效
    if (this.props.editable == null) this.$set(this.props, "editable", false);
    // 最大输入长度
    if (this.props.transfer == null) this.$set(this.props, "transfer", false);
    // 给表单元素设置 id，详见 Form 用法。
    if (this.props.elementId == null) this.$set(this.props, "elementId", null);
    // 两个日期间的分隔符
    if (this.props.separator == null) this.$set(this.props, "separator", " - ");
    // 是否开启 capture 模式，也可通过全局配置
    if (this.props.capture == null) this.$set(this.props, "capture", true);
    // 最大日期
    if (this.props.max == null) this.$set(this.props, "max", null);
    // 最小日期
    if (this.props.min == null) this.$set(this.props, "min", null);
  },
  mounted: function() {
    // 保存控件实例
    CommonUtil.saveElementToStore(this);
  },
  computed: {
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
      var disabled = this.$store.getters.isLoading;
      if (disabled) {
        return disabled;
      }
      if (typeof this.props.disabled == "string") {
        return ExpressionUtil.eval(
          this,
          "return " + this.props.disabled,
          this.detailModel
        );
      } else {
        return this.props.disabled;
      }
    }
  },
  methods: {
    changeHandle: function() {
      // 日期发生变化时触发
      if (this.props.onChange) {
        ExpressionUtil.eval(this, this.props.onChange, this.detailModel);
      }
    },
    openChangeHandle: function(st) {
      // 日期发生变化时触发
      if (this.props.onOpenChange) {
        ExpressionUtil.eval(
          this,
          this.props.onOpenChange,
          this.detailModel,
          st
        );
      }
    },
    okHandle: function() {
      // 在 confirm 模式下有效，点击确定按钮时触发
      if (this.props.onOk) {
        ExpressionUtil.eval(this, this.props.onOk, this.detailModel);
      }
    },
    clearHandle: function() {
      // 在 confirm 模式或 clearable = true 时有效，在清空日期时触发
      if (this.props.onClear) {
        ExpressionUtil.eval(this, this.props.onClear, this.detailModel);
      }
    },
    clickoutsideHandle: function() {
      // 点击外部关闭下拉菜单时触发
      if (this.props.onClickoutside) {
        ExpressionUtil.eval(this, this.props.onClickoutside, this.detailModel);
      }
    },
    checkDisabledDate: function(dateVal) {
      // 校验日期是否可以选择
      if (this.props.max) {
        if (
          moment(this.props.max, this.props.format.toUpperCase()).isBefore(
            moment(dateVal, this.props.format.toUpperCase())
          )
        ) {
          return true;
        }
      }
      if (this.props.min) {
        if (
          moment(this.props.min, this.props.format.toUpperCase()).isAfter(
            moment(dateVal, this.props.format.toUpperCase())
          )
        ) {
          return true;
        }
      }
      return false;
    }
  }
};
</script>
