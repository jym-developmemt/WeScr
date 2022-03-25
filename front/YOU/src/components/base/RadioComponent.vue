<template>
  <RadioGroup
    ref="elementRef"
    v-model="model[modelKey]"
    :class="this.props.className"
    :style="this.props.style"
    :type="this.props.type"
    :size="this.props.size"
    :border="this.props.border"
    :vertical="this.props.vertical"
    @on-change="changeHandle"
  >
    <Radio
      v-for="(option, index) in options"
      :label="option[props.valueKey]"
      :key="index"
      :disabled="disabled"
    >{{ props.i18n?$t(option[props.labelKey]):option[props.labelKey] }}</Radio>
  </RadioGroup>
</template>

<script>
import CommonUtil from "../../util/CommonUtil";
import ModelUtil from "../../util/ModelUtil";
import ExpressionUtil from "../../util/ExpressionUtil";

export default {
  name: "RadioComponent",
  props: ["elementIndex", "props", "displayData", "detailIndex", "rowIndex", "detailModel"],
  beforeMount: function() {
    // ClassName
    if (this.props.className == null) this.$set(this.props, "className", null);
    // 样式
    if (this.props.style == null) this.$set(this.props, "style", null);
    // 可选值为 button 或不填，为 button 时使用按钮样式
    if (this.props.type == null) this.$set(this.props, "type", null);
    // 尺寸，可选值为large、small、default或者不设置
    if (this.props.size == null) this.$set(this.props, "size", "default");
    // 是否显示边框
    if (this.props.border == null) this.$set(this.props, "border", false);
    // 是否垂直排列，按钮样式下无效
    if (this.props.vertical == null) this.$set(this.props, "vertical", false);
    // 是否禁用当前项
    if (this.props.disabled == null) this.$set(this.props, "disabled", false);
    if (this.props.valueKey == null) this.$set(this.props, "valueKey", "0");
    if (this.props.labelKey == null) this.$set(this.props, "labelKey", "1");
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
      return ModelUtil.getModelKey(this, this.displayData, "");
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
      // 在选项状态发生改变时触发，返回当前选中的项。通过修改外部的数据改变时不会触发
      if (this.props.onChange) {
        ExpressionUtil.eval(this, this.props.onChange, this.detailModel);
      }
    },
  }
};
</script>
