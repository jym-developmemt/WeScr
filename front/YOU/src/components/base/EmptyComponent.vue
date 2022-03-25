<template>
  <div
    ref="elementRef"
    :class="this.props.className"
    :style="this.props.style"
  ></div>
</template>

<script>
import CommonUtil from "../../util/CommonUtil";
export default {
  name: "EmptyComponent",
  props: ["elementIndex", "props", "displayData", "detailIndex", "rowIndex", "detailModel"],
  beforeMount: function() {
    // ClassName
    if (this.props.className == null) this.$set(this.props, "className", null);
    // 样式
    if (this.props.style == null) this.$set(this.props, "style", null);
  },
  mounted: function() {
    // 保存控件实例
    CommonUtil.saveElementToStore(this);
  },
};
</script>

<style scoped>
div {
  height: 33px;
}
</style>