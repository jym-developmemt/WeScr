<template>
  <Carousel
    ref="elementRef"
    v-model="sdacsd"
    :class="this.props.className"
    :style="this.props.style"
    :height="this.props.height"
    :loop="this.props.loop"
    :autoplay="this.props.autoplay"
    :autoplay-speed="this.props.autoplaySpeed"
    :dots="this.props.dots"
    :radius-dot="this.props.radiusDot"
    :trigger="this.props.trigger"
    :arrow="this.props.arrow"
    :easing="this.props.easing"
    @on-change="changeHandle"
    @on-click="clickHandle"
  >
    <CarouselItem
      v-for="(option, index) in options"
      :key="index"
    >  
       <img :src="option[props.labelKey]" style="height:100%;width:100%;clear: both;display: block;margin: auto;"/>         
    </CarouselItem>
  </Carousel>
</template>
<style lang="less">

</style>
<script>
import CommonUtil from "../../util/CommonUtil";
import ModelUtil from "../../util/ModelUtil";
import ExpressionUtil from "../../util/ExpressionUtil";

export default {
  name: "CarouselComponent",
  props: ["elementIndex", "props", "displayData", "detailIndex", "detailModel"],
  beforeMount: function() {
    // ClassName
    if (this.props.className == null) this.$set(this.props, "className", null);
    // 样式
    if (this.props.style == null) this.$set(this.props, "style", null);
    // 走马灯的高度，可填 auto 或具体高度数值，单位 px
    if (this.props.height == null) this.$set(this.props, "height", "auto");
    // 是否开启循环
    if (this.props.loop == null) this.$set(this.props, "loop", false);
    // 是否自动切换
    if (this.props.autoplay == null) this.$set(this.props, "autoplay", false);
    // 自动切换的时间间隔，单位为毫秒
    if (this.props.autoplaySpeed == null) this.$set(this.props, "autoplaySpeed", 2000);
    // 指示器的位置，可选值为 inside （内部），outside（外部），none（不显示）
    if (this.props.dots == null) this.$set(this.props, "dots", "inside");
    // 是否显示圆形指示器
    if (this.props.radiusDot == null) this.$set(this.props, "radiusDot", false);
    // 指示器的触发方式，可选值为 click（点击），hover（悬停）
    if (this.props.trigger == null) this.$set(this.props, "trigger", "click");
    // 切换箭头的显示时机，可选值为 hover（悬停），always（一直显示），never（不显示）
    if (this.props.arrow == null) this.$set(this.props, "arrow", "hover");
    // 动画效果
    if (this.props.easing == null) this.$set(this.props, "easing", "ease");
    if (this.props.valueKey == null) this.$set(this.props, "valueKey", "0");
    if (this.props.labelKey == null) this.$set(this.props, "labelKey", "1");
  },
  mounted: function() {
    // 保存控件实例
    CommonUtil.saveElementToStore(this);
  },
  data() {
    return {
      sdacsd: 0
    }
  },
  computed: {
    // model: function() {
    //   if (this.detailModel) {
    //     return this.detailModel;
    //   }
    //   return ModelUtil.getModelObject(this, this.displayData);
    // },
    // modelKey: function() {
    //   if (this.detailModel) {
    //     return this.displayData;
    //   }
    //   return ModelUtil.getModelKey(this, this.displayData, "");
    // },
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
    changeHandle: function(oldValue, value) {
      // 幻灯片切换时触发，目前激活的幻灯片的索引，原幻灯片的索引
      if (this.props.onChange) {
        ExpressionUtil.eval(this, this.props.onChange,oldValue, value, this.detailModel);
      }
    },
    clickHandle: function(val) {
      // 点击幻灯片时触发，返回索引值
      if (this.props.onClick) {
        ExpressionUtil.eval(this, this.props.onClick, val,this.detailModel);
      }
    },
  }
};
</script>