<template>
  <Button
    ref="elementRef"
    :class="this.props.className"
    :style="this.props.style"
    :type="this.props.type"
    :ghost="this.props.ghost"
    :size="this.props.size"
    :shape="this.props.shape"
    :long="this.props.long"
    :html-type="this.props.htmlType"
    :disabled="disabled"
    :loading="this.props.loading"
    :to="this.props.to"
    :replace="this.props.replace"
    :target="this.props.target"
    :append="this.props.append"
    @click="clickHandle"
  >
    <u-icon v-if="this.props.icon" :displayData="this.props.icon" :props="{}"></u-icon>
    <span v-if="this.displayData">{{ this.props.i18n ? $t(this.displayData) : this.displayData }}</span>
    <u-icon v-if="this.props.iconLast" :displayData="this.props.iconLast" :props="{}"></u-icon>
  </Button>
</template>

<script>
import CommonUtil from "../../util/CommonUtil";
import ExpressionUtil from "../../util/ExpressionUtil";

export default {
  name: "ButtonComponent",
  props: ["elementIndex", "props", "displayData", "detailIndex", "rowIndex", "detailModel"],
  beforeMount: function() {
    // ClassName
    if (this.props.className == null) this.$set(this.props, "className", null);
    // 样式
    if (this.props.style == null) this.$set(this.props, "style", null);
    // 按钮类型，可选值为 default、primary、dashed、text、info、success、warning、error或者不设置
    if (this.props.type == null) this.$set(this.props, "type", "default");
    // 幽灵属性，使按钮背景透明
    if (this.props.ghost == null) this.$set(this.props, "ghost", false);
    // 按钮大小，可选值为large、small、default或者不设置
    if (this.props.size == null) this.$set(this.props, "size", "default");
    // 按钮形状，可选值为circle或者不设置
    if (this.props.shape == null) this.$set(this.props, "shape", null);
    // 开启后，按钮的长度为 100%
    if (this.props.long == null) this.$set(this.props, "long", false);
    // 设置button原生的type，可选值为button、submit、reset
    if (this.props.htmlType == null)
      this.$set(this.props, "htmlType", "button");
    // 设置按钮为禁用状态
    if (this.props.disabled == null) this.$set(this.props, "disabled", false);
    // 设置按钮为加载中状态
    if (this.props.loading == null) this.$set(this.props, "loading", false);
    // 设置按钮的图标类型
    if (this.props.icon == null) this.$set(this.props, "icon", null);
    if (this.props.iconLast == null) this.$set(this.props, "iconLast", null);
    // 跳转的链接，支持 vue-router 对象
    if (this.props.to == null) this.$set(this.props, "to", null);
    // 路由跳转时，开启 replace 将不会向 history 添加新记录
    if (this.props.replace == null) this.$set(this.props, "replace", false);
    // 相当于 a 链接的 target 属性
    if (this.props.target == null) this.$set(this.props, "target", "_self");
    // 同 vue-router append
    if (this.props.append == null) this.$set(this.props, "append", false);
    // 多语言
    if (this.props.i18n == null) this.$set(this.props, "i18n", true);
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
    clickHandle: function() {
      //点击时触发
      if (this.props.onClick) {
        ExpressionUtil.eval(this, this.props.onClick, this.detailModel);
      }
    }
  }
};
</script>
