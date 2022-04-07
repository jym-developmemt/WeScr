
<template>
<!-- <Tooltip :disabled="this.props.disabled" :content="$t(this.props.toolText)" placement="bottom"> -->
  <Tooltip class="sTo" :disabled="this.props.disabled"  :placement="props.placement">
    <div slot="content">
      <span v-html="$t(this.props.toolText)"></span>
    </div>
<!-- <Tooltip class="sTo" :disabled="this.props.disabled" :content="$t(this.props.toolText)" :placement="props.placement"> -->
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
      :style="this.props.iconStyle"
    />
    
    <i
      v-if="libraryType == 1"
      :class="iconType"
    >
    </i> 
    
  </span> 
  <span :style="this.props.style">{{$t(this.props.textSpan)}}</span> 
  <!-- <span
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
    <i
      v-if="libraryType == 1"
      :class="iconType"
    >
    </i>
  </span> -->
</Tooltip>
</template>

<script>
import CommonUtil from "../../util/CommonUtil";
import ModelUtil from "../../util/ModelUtil";
import ExpressionUtil from "../../util/ExpressionUtil";

export default {
  name: "ToolTipIconComponent",
  props: ["elementIndex", "props", "displayData", "detailIndex", "detailModel"],
  beforeMount: function() {
    // ClassName
    if (this.props.className == null) this.$set(this.props, "className", null);
    // 样式
    if (this.props.style == null) this.$set(this.props, "style", null);
    // 图标的大小，单位是 px
    if (this.props.size == null) this.$set(this.props, "size", null);
    // 图标的颜色
    if (this.props.color == null) this.$set(this.props, "color", null);
    //图标名称
    if (this.props.text == null) this.$set(this.props, "text", null);
    // 图标名称样式
    if (this.props.textStyle == null) this.$set(this.props, "textStyle", null);

    if (this.props.iconStyle == null) this.$set(this.props, "iconStyle", null);

    if (this.props.toolText == null) this.$set(this.props, "toolText", null);

    if (this.props.disabled == null) this.$set(this.props, "disabled", false);
    if (this.props.placement == null) this.$set(this.props, "placement", 'bottom');
  },
  mounted: function() {
    // 保存控件实例
    CommonUtil.saveElementToStore(this);
  },
  computed: {
    // 图标库
    libraryType: function() {
      if (this.displayData) {
        if (this.displayData.startsWith("md-")) {
          return 0;
        } else if (this.displayData.startsWith("ios-")) {
          return 0;
        } else if (this.displayData.startsWith("fa")) {
          return 0;
        }
      }
    },
    // 图标名称
    iconType: function() {
      // 固定文本
      return this.displayData;
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
<style >
.sTo .ivu-tooltip-inner{
  max-width: 500px;
}
</style>
<style scoped>
    .top,.bottom{
        text-align: center;    
    }
    .center{
        width: 300px;
        margin: 10px auto;
        overflow: hidden;
    }
    .center-left{
        float: left;
    }
    .center-right{
        float: right;
    }
   
</style>