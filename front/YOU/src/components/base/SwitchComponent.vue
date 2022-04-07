<template>
  <i-switch
    ref="elementRef"
    v-model="model[modelKey]"
    :class="this.props.className"
    :size="this.props.size"
    :disabled="disabled"
    :true-value="this.props.trueValue"
    :false-value="this.props.falseValue"
    :true-color="this.props.trueColor"
    :false-color="this.props.falseColor"
    @on-change="changeHandle"
  ></i-switch>
</template>

<script>
import CommonUtil from "../../util/CommonUtil";
import ModelUtil from "../../util/ModelUtil";
import ExpressionUtil from "../../util/ExpressionUtil";

export default {
  name: "SwitchComponent",
  props: ["elementIndex", "props", "displayData", "detailIndex", "detailModel"],
  beforeMount: function() {
    // ClassName
    if (this.props.className == null) this.$set(this.props, "className", null);
	// 输入框尺寸，可选值为large、small、default或者不设置
    if (this.props.size == null) this.$set(this.props, "size", "default");
    // 设置输入框为禁用状态
    if (this.props.disabled == null) this.$set(this.props, "disabled", false);
    // 选中时的值，当使用类似 1 和 0 来判断是否选中时会很有用
    if (this.props.trueValue == null) this.$set(this.props, "trueValue", "1");
    // 没有选中时的值，当使用类似 1 和 0 来判断是否选中时会很有用
    if (this.props.falseValue == null) this.$set(this.props, "falseValue", "0");
	// 自定义打开时的背景色
    if (this.props.trueColor == null) this.$set(this.props, "trueColor", "");
    // 自定义关闭时的背景色
    if (this.props.falseColor == null) this.$set(this.props, "falseColor", "");
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
      return ModelUtil.getModelKey(this, this.displayData, []);
    },
    disabled: function() {
      // 设置输入框为只读
      return this.$store.getters.isLoading || this.props.disabled;
    }
  },
  methods: {
    changeHandle: function() {
      //数据改变时触发
      if (this.props.onChange) {
        ExpressionUtil.eval(this, this.props.onChange, this.detailModel);
      }
    },
    
  }
};
</script>
