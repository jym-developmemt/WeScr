<template>
  <TimePicker
    ref="elementRef"
    v-model="model[modelKey]"
    :class="this.props.className"
    :style="this.props.style"
    :type="this.props.type"
    :format="this.props.format"
    :steps="this.props.steps"
    :placement="this.props.placement"
    :placeholder="this.props.placeholder"
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
    :hide-disabled-options="this.props.hideDisabledOptions"
    :disabled-hours="this.props.disabledHours"
    :disabled-minutes="this.props.disabledMinutes" 
    :disabled-seconds="this.props.disabledSeconds"
    @on-change="changeHandle"
    @on-open-change="openChangeHandle"
    @on-ok="okHandle"
    @on-clear="clearHandle"
  />
</template>

<script>
import CommonUtil from "../../util/CommonUtil";
import ModelUtil from "../../util/ModelUtil";
import ExpressionUtil from "../../util/ExpressionUtil";
import moment from "moment";

export default {
  name: "TimePickerComponent",
  props: ["elementIndex", "props", "displayData", "detailIndex", "rowIndex", "detailModel"],
  beforeMount: function() {
    // ClassName
    if (this.props.className == null) this.$set(this.props, "className", null);
    // 样式
    if (this.props.style == null) this.$set(this.props, "style", null);
    // 显示类型，可选值为 time、timerange
    if (this.props.type == null) this.$set(this.props, "type", "time");
    // 展示的时间格式
    if (this.props.format == null)
      this.$set(this.props, "format", "HH:mm:ss");
    // 时间选择器出现的位置
    if (this.props.placement == null)
      this.$set(this.props, "placement", "bottom-start");
    // 占位文本
    if (this.props.placeholder == null)
      this.$set(this.props, "placeholder", "请选择时间");
    // 是否显示底部控制栏，开启后，选择完时间，选择器不会主动关闭，需用户确认后才可关闭
    if (this.props.confirm == null) this.$set(this.props, "confirm", false);
    // 手动控制时间选择器的显示状态，true 为显示，false 为收起。使用该属性后，选择器不会主动关闭。
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
    // 两个时间间的分隔符
    if (this.props.separator == null) this.$set(this.props, "separator", " - ");
    // 是否开启 capture 模式，也可通过全局配置
    if (this.props.capture == null) this.$set(this.props, "capture", true);
    // 下拉列表的时间间隔，数组的三项分别对应小时、分钟、秒。例如设置为 [1, 15] 时，分钟会显示：00、15、30、45。
    if (this.props.steps == null) this.$set(this.props, "steps", []);
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
      // 时间发生变化时触发
      if (this.props.onChange) {
        ExpressionUtil.eval(this, this.props.onChange, this.detailModel);
      }
    },
    openChangeHandle: function(st) {
      // 时间发生变化时触发
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
    }
  }
};
</script>
