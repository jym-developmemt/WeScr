<template>
  <CheckboxGroup
    ref="elementRef"
    v-model="model[modelKey]"
    :class="this.props.className"
    :style="this.props.style"
    :size="this.props.size"
    @on-change="changeHandle"
  >
    <Checkbox 
      v-for="(option, index) in options"
      :label="option[props.valueKey]"
      :key="index"
      :disabled="disabled || itemDisabled(option[props.valueKey])"
    ><span>{{ props.i18n?$t(option[props.labelKey]):option[props.labelKey] }}</span></Checkbox>
  </CheckboxGroup>
</template>

<script>
import CommonUtil from "../../util/CommonUtil";
import ModelUtil from "../../util/ModelUtil";
import ExpressionUtil from "../../util/ExpressionUtil";

export default {
  name: "SelectComponent",
  props: ["elementIndex", "props", "displayData", "detailIndex", "rowIndex", "detailModel"],
  beforeMount: function() {
    // ClassName
    if (this.props.className == null) this.$set(this.props, "className", null);
    // 样式
    if (this.props.style == null) this.$set(this.props, "style", null);
    // 是否禁用
    if (this.props.disabled == null) this.$set(this.props, "disabled", false);
    // 选择框大小，可选值为large、small、default或者不填
    if (this.props.size == null) this.$set(this.props, "size", "default");
    if (this.props.valueKey == null) this.$set(this.props, "valueKey", "0");
    if (this.props.labelKey == null) this.$set(this.props, "labelKey", "1");
    // 是否禁用
    if (this.props.disabledValues == null) this.$set(this.props, "disabledValues", []);
    // 多语言
    if (this.props.i18n == null) this.$set(this.props, "i18n", true);
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
    options: function() {
      if (typeof this.props.options == "string") {
        // 动态选项
        let dataOption = ExpressionUtil.createObject(this, this.props.options);
        if (Array.isArray(dataOption) == false) {
          return [];
        }
        if (dataOption.length == 0) {
          this.model[this.modelKey] = null;
        }
        return dataOption;
      } else {
        // 固定选项
        return this.props.options;
      }
    },
    disabled: function() {
      // 设置输入框为只读
      return this.$store.getters.isLoading || this.props.disabled;
    }
  },
  methods: {
    changeHandle: function() {
      // 选中的Option变化时触发，默认返回 value，如需返回 label，详见 label-in-value 属性
      if (this.props.onChange) {
        ExpressionUtil.eval(this, this.props.onChange, this.detailModel);
      }
    },
    itemDisabled: function(value) {
      if (this.props.disabledValues.indexOf(value) > -1) {
        return true;
      }
      return false;
    }
  }
};
</script>
