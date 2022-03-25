<template>
  <Divider
    ref="elementRef"
    :class="this.props.className"
    :style="this.props.style"
    :type="this.props.type"
    :orientation="this.props.orientation"
    :dashed="this.props.dashed"
    :size="this.props.size"
  ><div class="dividerText" v-if="text">{{ this.props.i18n?$t(text):text }}</div></Divider>
</template>

<script>
import CommonUtil from "../../util/CommonUtil";
import ModelUtil from "../../util/ModelUtil";
import ExpressionUtil from "../../util/ExpressionUtil";

export default {
  name: "DividerComponent",
  props: ["elementIndex", "props", "displayData", "detailIndex", "rowIndex", "detailModel"],
  beforeMount: function() {
    // ClassName
    if (this.props.className == null) this.$set(this.props, "className", null);
    // 样式
    if (this.props.style == null) this.$set(this.props, "style", null);
    // 水平还是垂直类型，可选值为 horizontal 或 vertical
    if (this.props.type == null) this.$set(this.props, "type", "horizontal");
    // 分割线标题的位置，可选值为 left、right 或 center
    if (this.props.orientation == null)
      this.$set(this.props, "orientation", "center");
    // 是否虚线
    if (this.props.dashed == null) this.$set(this.props, "dashed", false);
    // 尺寸，可选值为 small 或 default
    if (this.props.size == null) this.$set(this.props, "size", "default");
    // 多语言
    if (this.props.i18n == null) this.$set(this.props, "i18n", true);
  },
  mounted: function() {
    // 保存控件实例
    CommonUtil.saveElementToStore(this);
  },
  computed: {
    // 表示文本
    text: function() {
      if (this.detailModel) {
        return this.detailModel[this.displayData];
      }
      if (this.props.expression) {
        // 模型值
        var modelObj = ModelUtil.getModelObject(this, this.props.expression);
        var modelKey = ModelUtil.getModelKey(this, this.props.expression, "");
        return modelObj[modelKey];
      } else {
        // 固定文本
        return this.displayData;
      }
    }
  }
};
</script>

<style scoped>
/deep/ .dividerText {
  font-size: 12px;
  color: #515a6e;
}
</style>