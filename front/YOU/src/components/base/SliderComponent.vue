<template>
  <Slider
    ref="elementRef"
    v-model="model[modelKey]"
    :class="this.props.className"
    :style="this.props.style"
    :min="this.props.min"
    :max="this.props.max"
    :step="this.props.step"
    :disabled="disabled"
    :range="this.props.range"
    :show-input="this.props.showInput"
    :show-stops="this.props.showStops"
    :show-tip="this.props.showTip"
    :tip-format="this.tipFormat"
    :input-size="this.props.inputSize"
    :active-change ="this.props.activeChange"
    :marks="this.props.marks"
    @on-change="changeHandle"
    @on-input="inputHandle"
  />
</template>

<script>
import CommonUtil from "../../util/CommonUtil";
import ModelUtil from "../../util/ModelUtil";
import ExpressionUtil from "../../util/ExpressionUtil";

export default {
  name: "InputComponent",
  props: ["elementIndex", "props", "displayData", "detailIndex", "rowIndex", "detailModel"],
  beforeMount: function() {
    // ClassName
    if (this.props.className == null) this.$set(this.props, "className", null);
    // 样式
    if (this.props.style == null) this.$set(this.props, "style", null);
    // 最小值
    if (this.props.min == null) this.$set(this.props, "min", 0);
    // 最大值
    if (this.props.max == null) this.$set(this.props, "max", 100);
    // 步长，取值建议能被（max - min）整除
    if (this.props.step == null) this.$set(this.props, "step", 1);
    // 设置输入框为禁用状态
    if (this.props.disabled == null) this.$set(this.props, "disabled", false);
    // 是否开启双滑块模式
    if (this.props.range == null) this.$set(this.props, "range", false);
    // 是否显示数字输入框，仅在单滑块模式下有效
    if (this.props.showInput == null) this.$set(this.props, "showInput", false);
    // 是否显示间断点，建议在 step 不密集时使用
    if (this.props.showStops == null) this.$set(this.props, "showStops", false);
    // 提示的显示控制，可选值为 hover（悬停，默认）、always（总是可见）、never（不可见）
    if (this.props.showTip == null) this.$set(this.props, "showTip", "hover");
    // Slider 会把当前值传给 tip-format，并在 Tooltip 中显示 tip-format 的返回值，若为 null，则隐藏 Tooltip
    if (this.props.tipFormat == null) this.$set(this.props, "tipFormat", "");
    // 数字输入框的尺寸，可选值为large、small、default或者不填，仅在开启 show-input 时有效
    if (this.props.inputSize == null) this.$set(this.props, "inputSize", "default");
    // 同 InputNumber 的 active-change
    if (this.props.activeChange == null) this.$set(this.props, "activeChange", true);
    // 标记， key 的类型必须为 number 且取值在闭区间 [min, max] 内，每个标记可以单独设置样式
    if (this.props.marks == null) this.$set(this.props, "marks", null);
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
      return this.$store.getters.isLoading || this.props.disabled;
    }
  },
  methods: {
    changeHandle: function() {
      // 在松开滑动时触发，返回当前的选值，在滑动过程中不会触发
      if (this.props.onChange) {
        ExpressionUtil.eval(this, this.props.onChange, this.detailModel);
      }
    },
    inputHandle: function() {
      // 滑动条数据变化时触发，返回当前的选值，在滑动过程中实时触发
      if (this.props.onInput) {
        ExpressionUtil.eval(this, this.props.onInput, this.detailModel);
      }
    },
    tipFormat: function(val) {
      // 滑动条数据变化时触发，返回当前的选值，在滑动过程中实时触发
      if (this.props.tipFormat) {
        ExpressionUtil.eval(this, this.props.tipFormat, this.detailModel);
      } else {
        return val;
      }
    },
  }
};
</script>
