<template>
  <Alert
    ref="elementRef"
    :class="this.props.className"
    :style="this.props.style"
    :type="this.props.type"
    :closable="this.props.closable"
    :show-icon="this.props.showIcon"
    @close="closeHandle"
  >
    <span v-html="text"></span>
    <template v-if="this.props.desc" slot="desc"><span v-html="this.props.desc"></span></template>
    <u-icon v-if="this.props.icon" slot="icon" :displayData="this.props.icon" :props="{}"></u-icon>
  </Alert>
</template>

<script>
import CommonUtil from "../../util/CommonUtil";
import ModelUtil from "../../util/ModelUtil";
import ExpressionUtil from "../../util/ExpressionUtil";

export default {
  name: "AlertComponent",
  props: ["elementIndex", "props", "displayData", "detailIndex", "rowIndex", "detailModel"],
  beforeMount: function() {
    // ClassName
    if (this.props.className == null) this.$set(this.props, "className", null);
    // 样式
    if (this.props.style == null) this.$set(this.props, "style", null);
    
    // 警告提示样式，可选值为info、success、warning、error
    if (this.props.type == null) this.$set(this.props, "type", "info");
    // 是否可关闭
    if (this.props.closable == null) this.$set(this.props, "closable", false);
    // 是否显示图标
    if (this.props.showIcon == null) this.$set(this.props, "showIcon", false);
    // 描述信息
    if (this.props.desc == null) this.$set(this.props, "desc", null);
    // 图标
    if (this.props.icon == null) this.$set(this.props, "icon", null);
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
  },
  methods: {
    closeHandle: function() {
      // 关闭时触发
      if (this.props.onClose) {
        ExpressionUtil.eval(this, this.props.onClose, this.detailModel);
      }
    }
  }
};
</script>