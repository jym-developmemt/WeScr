<template></template>

<script>
import CommonUtil from "../../util/CommonUtil";
import ExpressionUtil from "../../util/ExpressionUtil";

export default {
  name: "FunctionComponent",
  props: ["elementIndex", "props", "displayData", "detailIndex", "detailModel"],
  beforeMount: function() {
    // 设置按钮为禁用状态
    if (this.props.disabled == null) this.$set(this.props, "disabled", false);
    // 处理
    if (this.props.function == null) this.$set(this.props, "function", null);
  },
  mounted: function() {
    // 保存控件实例
    CommonUtil.saveElementToStore(this);
  },
  computed: {
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
    // 触发事件
    invoke: function(param1, param2, param3, param4, param5) {
      if (this.disabled) {
        return;
      }

      if (this.props.function) {
        return ExpressionUtil.eval(
          this,
          this.props.function,
          param1,
          param2,
          param3,
          param4,
          param5
        );
      }
    }
  }
};
</script>
