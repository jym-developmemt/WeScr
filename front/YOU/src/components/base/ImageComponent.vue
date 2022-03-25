<template>
  <div :class="this.props.className" :style="this.props.style">
    <img
      ref="elementRef"
      v-if="!this.props.loadingImage"
      :style="this.props.imageStyle"
      :src="model[modelKey]"
      :width="this.props.width"
      :height="this.props.height"
      @click="clickHandle"
    />
    <img
      v-if="this.props.loadingImage"
      :style="this.props.imageStyle"
      src="../../assets/loading.gif"
      width="150"
      height="80"
    />
  </div>
</template>

<script>
import CommonUtil from "../../util/CommonUtil";
import ModelUtil from "../../util/ModelUtil";
import ExpressionUtil from "../../util/ExpressionUtil";

export default {
  name: "ImageComponent",
  props: ["elementIndex", "props", "displayData", "detailIndex", "rowIndex", "detailModel"],
  beforeMount: function() {
    // ClassName
    if (this.props.className == null) this.$set(this.props, "className", null);
    // 样式
    if (this.props.style == null) this.$set(this.props, "style", null);
    // 样式
    if (this.props.imageStyle == null)
      this.$set(this.props, "imageStyle", null);
    // 宽度
    if (this.props.width == null) this.$set(this.props, "width", null);
    // 高度
    if (this.props.height == null) this.$set(this.props, "height", null);
    // 高度
    if (this.props.loadingImage == null)
      this.$set(this.props, "loadingImage", false);
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
    }
  },
  methods: {
    clickHandle: function() {
      // 点击标签时触发
      if (this.props.onClick) {
        ExpressionUtil.eval(this, this.props.onClick, this.detailModel);
      }
    }
  }
};
</script>