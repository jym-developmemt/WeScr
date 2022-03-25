<template>
  <InputNumber
    ref="elementRef"
    v-model="numberVal"
    :class="this.props.className"
    :style="this.props.style"
    :max="this.props.max"
    :min="this.props.min"
    :step="this.props.step"
    :size="this.props.size"
    :disabled="disabled"
    :placeholder="this.props.placeholder"
    :formatter="formatter"
    :parser="parser"
    :readonly="this.props.readonly"
    :editable="this.props.editable"
    :precision="this.props.precision"
    :element-id="this.props.elementId"
    :active-change="this.props.activeChange"
    @on-change="changeHandle"
    @on-focus="focusHandle"
    @on-blur="blurHandle"
  />
</template>

<script>
import CommonUtil from "../../util/CommonUtil";
import ModelUtil from "../../util/ModelUtil";
import ExpressionUtil from "../../util/ExpressionUtil";

export default {
  name: "NumberComponent",
  props: ["elementIndex", "props", "displayData", "detailIndex", "rowIndex", "detailModel"],
  beforeMount: function() {
    // ClassName
    if (this.props.className == null) this.$set(this.props, "className", null);
    // 样式
    if (this.props.style == null) this.$set(this.props, "style", null);
    // 最大值
    if (this.props.max == null) this.$set(this.props, "max", NaN);
    // 最小值
    if (this.props.min == null) this.$set(this.props, "min", 0);
    // 每次改变的步伐，可以是小数
    if (this.props.step == null) this.$set(this.props, "step", 1);
    // 输入框尺寸，可选值为large、small、default或者不填
    if (this.props.size == null) this.$set(this.props, "size", null);
    // 设置输入框为禁用状态
    if (this.props.disabled == null) this.$set(this.props, "disabled", false);
    // 占位文本
    if (this.props.placeholder == null)
      this.$set(this.props, "placeholder", null);
    // 设置输入框为只读
    if (this.props.readonly == null) this.$set(this.props, "readonly", false);
    // 是否可编辑
    if (this.props.editable == null) this.$set(this.props, "editable", true);
    // 数值精度
    if (this.props.precision == null) this.$set(this.props, "precision", NaN);
    // 给表单元素设置 id，详见 Form 用法。
    if (this.props.elementId == null) this.$set(this.props, "elementId", null);
    // 是否实时响应数据，设置为 false 时，只会在失焦时更改数据
    if (this.props.activeChange == null)
      this.$set(this.props, "activeChange", true);
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
    numberVal: {
      get: function() {
        if (Number.isNaN(this.model[this.modelKey]) == false && this.model[this.modelKey] != null) {
          return this.model[this.modelKey] * 1;
        } else {
          return null;
        }
      },
      set: function(val) {
        this.$set(this.model, this.modelKey, val);
      }
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
    formatter: function(value) {
      //数据改变时触发
      if (this.props.formatter) {
        return ExpressionUtil.eval(
          this,
          "return " + this.props.formatter,
          value
        );
      } else {
        return value;
      }
    },
    parser: function(value) {
      //数据改变时触发
      if (this.props.parser) {
        return ExpressionUtil.eval(this, "return " + this.props.parser, value);
      } else {
        return value;
      }
    },
    changeHandle: function() {
      //数据改变时触发
      if (this.props.onChange) {
        ExpressionUtil.eval(this, this.props.onChange, this.detailModel);
      }
    },
    focusHandle: function() {
      //输入框聚焦时触发
      if (this.props.onFocus) {
        ExpressionUtil.eval(this, this.props.onFocus, this.detailModel);
      }
    },
    blurHandle: function() {
      //输入框失去焦点时触发
      if (this.props.onBlur) {
        ExpressionUtil.eval(this, this.props.onBlur, this.detailModel);
      }
    }
  }
};
</script>
