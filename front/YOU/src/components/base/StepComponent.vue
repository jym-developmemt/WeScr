<template>
  <Steps
    ref="elementRef"
    :class="this.props.className"
    :style="this.props.style"
    :current="model[modelKey]"
    :status="this.props.status"
    :size="this.props.size"
    :direction="this.props.direction"
  >
    <Step
      :key="index"
      v-for="(option, index) in options"
      :status="option.status"
      :title="props.i18n?$t(option.title):option.title"
      :content="props.i18n?$t(option.content):option.content"
      :icon="option.icon"
    >
      <u-icon v-if="option.icon" slot="icon" :displayData="option.icon" :props="{}"></u-icon>
    </Step>
  </Steps>
</template>

<script>
import CommonUtil from "../../util/CommonUtil";
import ModelUtil from "../../util/ModelUtil";
import ExpressionUtil from "../../util/ExpressionUtil";

export default {
  name: "StepComponent",
  props: ["elementIndex", "props", "displayData", "detailIndex", "rowIndex", "detailModel"],
  beforeMount: function() {
    // ClassName
    if (this.props.className == null) this.$set(this.props, "className", null);
    // 样式
    if (this.props.style == null) this.$set(this.props, "style", null);
    // 当前步骤的状态，可选值为wait、process、finish、error
    if (this.props.status == null) this.$set(this.props, "status", "process");
    // 步骤条的尺寸，可选值为small或者不写
    if (this.props.size == null) this.$set(this.props, "size", null);
    // 步骤条的方向，可选值为horizontal（水平）或vertical（垂直）
    if (this.props.direction == null) this.$set(this.props, "direction", "horizontal");
    // 多语言
    if (this.props.i18n == null) this.$set(this.props, "i18n", false);
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
      return ModelUtil.getModelKey(this, this.displayData, 0);
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
  },
  methods: {
  }
};
</script>
