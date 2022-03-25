<template>
  <span
    ref="elementRef"
    :class="this.props.className"
    :style="this.props.style"
    @click="clickHandle"
  >
    <Icon
      v-if="libraryType == 0"
      :type="iconType"
      :size="this.props.size"
      :color="this.props.color"
    />
    <FontAwesomeIcon v-if="libraryType == 1" :icon="faIconType" />
  </span>
</template>

<script>
import CommonUtil from "../../util/CommonUtil";
import ModelUtil from "../../util/ModelUtil";
import ExpressionUtil from "../../util/ExpressionUtil";
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'

export default {
  name: "LabelComponent",
  props: ["elementIndex", "props", "displayData", "detailIndex", "rowIndex", "detailModel"],
  components: {FontAwesomeIcon},
  beforeMount: function() {
    // ClassName
    if (this.props.className == null) this.$set(this.props, "className", null);
    // 样式
    if (this.props.style == null) this.$set(this.props, "style", null);
    // 图标的大小，单位是 px
    if (this.props.size == null) this.$set(this.props, "size", null);
    // 图标的颜色
    if (this.props.color == null) this.$set(this.props, "color", null);
    // 表达式
    if (this.props.expression == null) this.$set(this.props, "expression", "");
  },
  mounted: function() {
    // 保存控件实例
    CommonUtil.saveElementToStore(this);
  },
  computed: {
    // 图标库
    libraryType: function() {
      if (this.iconType) {
        if (this.iconType.startsWith("md-")) {
          return 0;
        } else if (this.iconType.startsWith("ios-")) {
          return 0;
        } else {
          return 1;
        }
        return -1;
      }
    },
    // 图标名称
    iconType: function() {
      if (this.props.expression) {
        return ExpressionUtil.eval(this, "return " + this.props.expression, this.detailModel);
      }
      // 固定文本
      return this.displayData;
    },
    // FontAwesome图标名称
    faIconType: function() {
      var iconInfo = this.iconType.split(' ');
      if (iconInfo.length == 0) {
        return iconInfo;
      }
      if (iconInfo.length == 1) {
        iconInfo.unshift('fas');
      }
      if (iconInfo[1].startsWith('fa-')) {
        iconInfo[1] = iconInfo[1].substring(3);
      }
      return iconInfo;
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
