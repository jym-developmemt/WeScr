<template>
  <Row
    v-if="this.props.visible"
    :gutter="this.props.gutter"
    :type="this.props.type"
    :align="this.props.align"
    :justify="this.props.justify"
    :class-name="this.props.className"
    :style="this.props.style"
  >
    <slot></slot>
  </Row>
</template>

<script>
export default {
  name: "RowComponent",
  props: ["props"],
  beforeMount: function() {
    // 可见
    if (this.props.visible == null) this.$set(this.props, "visible", true);
    // 栅格间距，单位 px，左右平分
    if (this.props.gutter == null) this.$set(this.props, "gutter", 0);
    // 布局模式，可选值为flex或不选，在现代浏览器下有效
    if (this.props.type == null) this.$set(this.props, "type", "flex");
    // flex 布局下的垂直对齐方式，可选值为top、middle、bottom
    if (this.props.align == null) this.$set(this.props, "align", "top");
    // flex 布局下的水平排列方式，可选值为start、end、center、space-around、space-between
    if (this.props.justify == null) this.$set(this.props, "justify", "start");
    // 自定义的class名称
    if (this.props.className == null) this.$set(this.props, "className", null);
    // 自定义的style样式
    if (this.props.style == null) this.$set(this.props, "style", null);
  }
};
</script>
