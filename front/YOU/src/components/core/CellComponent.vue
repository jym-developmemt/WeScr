<template>
  <Col
    v-if="this.visible"
    :span="this.props.span"
    :order="this.props.order"
    :offset="this.props.offset"
    :push="this.props.push"
    :pull="this.props.pull"
    :class-name="this.props.className"
    :style="this.props.style"
    :xs="this.props.xs"
    :sm="this.props.sm"
    :md="this.props.md"
    :lg="this.props.lg"
    :xl="this.props.xl"
    :xxl="this.props.xxl"
  >
    <slot></slot>
  </Col>
</template>

<script>
export default {
  name: "CellComponent",
  props: ["props"],
  beforeMount: function() {
    // 可见
    if (this.props.visible == null) this.$set(this.props, "visible", true);
    // 栅格的顺序，在flex布局模式下有效
    if (this.props.span == null) this.$set(this.props, "span", "");
    // 栅格左侧的间隔格数，间隔内不可以有栅格
    if (this.props.order == null) this.$set(this.props, "order", "");
    // 栅格向右移动格数
    if (this.props.offset == null) this.$set(this.props, "offset", "");
    // 栅格向左移动格数
    if (this.props.push == null) this.$set(this.props, "push", "");
    // 自定义的class名称
    if (this.props.className == null) this.$set(this.props, "className", "");
    // 自定义的样式
    if (this.props.style == null) this.$set(this.props, "style", "");
    // <576px 响应式栅格，可为栅格数或一个包含其他属性的对象
    if (this.props.xs == null) this.$set(this.props, "xs", {});
    // ≥576px 响应式栅格，可为栅格数或一个包含其他属性的对象
    if (this.props.sm == null) this.$set(this.props, "sm", {});
    // ≥768px 响应式栅格，可为栅格数或一个包含其他属性的对象
    if (this.props.md == null) this.$set(this.props, "md", {});
    // ≥992px 响应式栅格，可为栅格数或一个包含其他属性的对象
    if (this.props.lg == null) this.$set(this.props, "lg", {});
    // ≥1200px 响应式栅格，可为栅格数或一个包含其他属性的对象
    if (this.props.xl == null) this.$set(this.props, "xl", {});
    // ≥1600px 响应式栅格，可为栅格数或一个包含其他属性的对象
    if (this.props.xxl == null) this.$set(this.props, "xxl", {});
  },
  computed: {
    // 控件可见
    visible: function() {
      if (this.props) {
        switch (typeof this.props.visible) {
          case "string":
            return ExpressionUtil.eval(
              this,
              "return " + this.props.visible
            );
          case "boolean":
            return this.props.visible;
        }
      }
      return true;
    }
  }
};
</script>
