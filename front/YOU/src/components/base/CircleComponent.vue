<template>
  <i-circle
    ref="elementRef"
    :percent="model[modelKey]"
    :class="this.props.className"
    :style="this.props.style"
    :disabled="this.props.disabled"
    :size="this.props.size"
    :stroke-linecap="this.props.strokeLinecap"
    :stroke-width="this.props.strokeWidth"
    :stroke-color="this.props.strokecolor"
    :trail-width="this.props.trailWidth"
    :trail-color="this.props.trailColor"
    :dashboard="this.props.dashboard"
  >
  <Icon v-if="model[modelKey] == 100" type="ios-checkmark" :size="this.props.iconSize" style="color:#5cb85c"></Icon>
  <Icon v-if="model[modelKey] == 0" type="ios-close" :size="this.props.iconSize" style="color:#ff5500"></Icon>
  <span v-if="model[modelKey] != 100 && model[modelKey] != 0" class="demo-Circle-inner" :style="this.props.spanSize">{{model[modelKey]}}%</span>
  </i-circle>
</template>

<script>
import CommonUtil from "../../util/CommonUtil";
import ModelUtil from "../../util/ModelUtil";
import ExpressionUtil from "../../util/ExpressionUtil";
import moment from "moment";

export default {
  name: "CircleComponent",
  props: ["elementIndex", "props", "displayData", "detailIndex", "rowIndex", "detailModel"],
  beforeMount: function() {
    // ClassName
    if (this.props.className == null) this.$set(this.props, "className", null);
    // 样式
    if (this.props.style == null) this.$set(this.props, "style", null);
    // 图表的宽度和高度，单位 px
    if (this.props.size == null) this.$set(this.props, "size", 35);
    // 是否禁用选择器
    if (this.props.disabled == null) this.$set(this.props, "disabled", false);
    // 进度环顶端的形状，可选值为square（方）和round（圆）
    if (this.props.strokeLinecap == null) this.$set(this.props, "strokeLinecap", "round");
    // 进度环的线宽，单位 px
    if (this.props.strokeWidth == null) this.$set(this.props, "strokeWidth", 15 );
    // 进度环的颜色，4.0.0 版本开始支持传入数组显示为渐变色
    if (this.props.strokecolor == null) this.$set(this.props, "strokecolor", "#2db7f5");
    // 进度环背景的线宽，单位 px
    if (this.props.trailWidth == null) this.$set(this.props, "trailWidth", 15 );
    // 进度环背景的颜色
    if (this.props.trailColor == null) this.$set(this.props, "trailColor", "#eaeef2");
    // 是否显示为仪表盘
    if (this.props.dashboard == null) this.$set(this.props, "dashboard", false);
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
  }
};
</script>
