<template>
  <Tabs
    ref="elementRef"
    v-model="model[modelKey]"
    :class="this.props.className"
    :style="this.props.style"
    :size="this.props.size"
    :closable="this.props.closable"
    :type="this.props.type"
    :animated="this.props.animated"
    :capture-focus="this.props.captureFocus"
    :before-remove="this.props.beforeRemove"
    @on-click="clickHandle"
    @on-tab-remove="tabRemoveHandle"

  >
    <TabPane
      :key="index"
      v-for="(option, index) in options"
      :name="option.name"
      :label="props.i18n?$t(option.label):option.label"
      :icon="option.icon"
      :disabled="option.disabled"
      :closable="option.closable"
    ></TabPane>
  </Tabs>
</template>

<script>
import CommonUtil from "../../util/CommonUtil";
import ModelUtil from "../../util/ModelUtil";
import ExpressionUtil from "../../util/ExpressionUtil";

export default {
  name: "TabComponent",
  props: ["elementIndex", "props", "displayData", "detailIndex", "rowIndex", "detailModel"],
  beforeMount: function() {
    // ClassName
    if (this.props.className == null) this.$set(this.props, "className", null);
    // 样式
    if (this.props.style == null) this.$set(this.props, "style", null);
    // 页签的基本样式，可选值为 line 和 card
    if (this.props.type == null) this.$set(this.props, "type", "line");
    // 尺寸，可选值为 default 和 small，仅在 type="line" 时有效
    if (this.props.size == null) this.$set(this.props, "size", null);
    // 是否可以关闭页签，仅在 type="card" 时有效
    if (this.props.closable == null) this.$set(this.props, "closable", false);
    // 是否使用 CSS3 动画
    if (this.props.animated == null) this.$set(this.props, "animated", true);
    // Tabs 内的表单类组件是否自动获得焦点
    if (this.props.captureFocus == null) this.$set(this.props, "captureFocus", false);
    // 当嵌套使用 Tabs，指定 name 区分层级
    if (this.props.name == null) this.$set(this.props, "name", null);
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
      return ModelUtil.getModelKey(this, this.displayData, 0);
    },
    options: function() {
      if (typeof this.props.options == "string") {
        // 动态选项
        let dataOption = ExpressionUtil.createObject(this, this.props.options);
        if (Array.isArray(dataOption) == false) {
          return [];
        }
        return dataOption;
      } else {
        // 固定选项
        return this.props.options;
      }
    },
  },
  methods: {
    clickHandle: function(name) {
      // tab 被点击时触发
      if (this.props.onClick) {
        ExpressionUtil.eval(this, this.props.onClick, this.detailModel, name);
      }
    },
    tabRemoveHandle: function() {
      // tab 被关闭时触发
      if (this.props.onTabRemove) {
        ExpressionUtil.eval(this, this.props.onTabRemove, this.detailModel);
      }
    },
  }
};
</script>
