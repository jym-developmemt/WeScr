<template>
  <Checkbox
    ref="elementRef"
    v-model="dataValue"
    :class="this.props.className"
    :style="this.props.style"
    :label="this.props.label"
    :disabled="disabled"
    :size="this.props.size"
    :border="this.props.border"
    :true-value="this.props.trueValue"
    :false-value="this.props.falseValue"
    @on-change="changeHandle"
  />
</template>

<script>
import CommonUtil from "../../util/CommonUtil";
import ModelUtil from "../../util/ModelUtil";
import ExpressionUtil from "../../util/ExpressionUtil";

export default {
  name: "CheckboxComponent",
  props: ["elementIndex", "props", "displayData", "detailIndex", "rowIndex", "detailModel"],
  beforeMount: function() {
    // ClassName
    if (this.props.className == null) this.$set(this.props, "className", null);
    // 样式
    if (this.props.style == null) this.$set(this.props, "style", null);
    // 只在组合使用时有效。指定当前选项的 value 值，组合会自动判断是否选中
    if (this.props.label == null) this.$set(this.props, "label", null);
    // 设置输入框为禁用状态
    if (this.props.disabled == null) this.$set(this.props, "disabled", false);
    // 输入框尺寸，可选值为large、small、default或者不设置
    if (this.props.size == null) this.$set(this.props, "size", "default");
    // 是否显示边框
    if (this.props.border == null) this.$set(this.props, "border", false);
    // 选中时的值，当使用类似 1 和 0 来判断是否选中时会很有用
    if (this.props.trueValue == null) this.$set(this.props, "trueValue", "1");
    // 没有选中时的值，当使用类似 1 和 0 来判断是否选中时会很有用
    if (this.props.falseValue == null) this.$set(this.props, "falseValue", "0");
  },
  mounted: function() {
    // 保存控件实例
    CommonUtil.saveElementToStore(this);
  },
  computed: {
    dataValue: {
      // getter
      get: function() {
        let data = this.model[this.modelKey];
        if (data) {
          return data;
        } else {
          return this.props.falseValue;
        }
      },
      // setter
      set: function(newValue) {
        this.$set(this.model, this.modelKey, newValue);
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
    }
  },
  methods: {
    changeHandle: function(val) {
      //数据改变时触发
      if (this.props.onChange) {
        ExpressionUtil.eval(this, this.props.onChange, val, this.detailModel);
      }
    },
  }
};
</script>
<style scoped>
.ivu-checkbox-wrapper {
  height: 33px;
}
</style>