<template>
  <Anchor
    ref="elementRef"
    :class="this.props.className"
    :style="this.props.style"
    :affix="this.props.affix"
    :offset-top="this.props.offsetTop"
    :offset-bottom="this.props.offsetBottom"
    :bounds="this.props.bounds"
    :scroll-offset="this.props.scrollOffset"
    :container="this.props.container"
    :show-ink="this.props.showInk"
    @on-select="selectHandle"
    @on-change="changeHandle"
  >
    <AnchorLink
      :key="index"
      v-for="(option, index) in options"
      :href="option.href"
      :title="option.title"
      :scroll-offset="option.scrollOffset?option.scrollOffset*1:0"
    ></AnchorLink>
  </Anchor>
</template>

<script>
import CommonUtil from "../../util/CommonUtil";
import ExpressionUtil from "../../util/ExpressionUtil";

export default {
  name: "AnchorComponent",
  props: ["elementIndex", "props", "displayData", "detailIndex", "rowIndex", "detailModel"],
  beforeMount: function() {
    // ClassName
    if (this.props.className == null) this.$set(this.props, "className", null);
    // 样式
    if (this.props.style == null) this.$set(this.props, "style", null);
    // 固定模式
    if (this.props.affix == null) this.$set(this.props, "affix", false);
    // 距离窗口顶部达到指定偏移量后触发
    if (this.props.offsetTop == null) this.$set(this.props, "offsetTop", 0);
    // 距离窗口底部达到指定偏移量后触发
    if (this.props.offsetBottom == null)
      this.$set(this.props, "offsetBottom", NaN);
    // 锚点区域边界，单位：px
    if (this.props.bounds == null) this.$set(this.props, "bounds", 5);
    // 点击滚动的额外距离
    if (this.props.scrollOffset == null)
      this.$set(this.props, "scrollOffset", 0);
    // 指定滚动的容器
    if (this.props.container == null) this.$set(this.props, "container", null);
    // 是否显示小圆点
    if (this.props.showInk == null) this.$set(this.props, "showInk", false);
  },
  mounted: function() {
    // 保存控件实例
    CommonUtil.saveElementToStore(this);
  },
  computed: {
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
    }
  },
  methods: {
    selectHandle: function(href) {
      // 点击锚点时触发，返回链接
      if (this.props.onSelect) {
        ExpressionUtil.eval(this, this.props.onClick, this.detailModel, href);
      }
    },
    changeHandle: function(newHref, oldHref) {
      // 链接改变时触发，返回新链接和旧链接
      if (this.props.onChange) {
        ExpressionUtil.eval(
          this,
          this.props.onClick,
          this.detailModel,
          newHref,
          oldHref
        );
      }
    }
  }
};
</script>
