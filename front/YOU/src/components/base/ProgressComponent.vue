<template>
 
  <i-progress 
    ref="elementRef"
    v-model="model[modelKey]"
    :class="this.props.className"
    :style="this.props.style"
    :percent="percent"
    :status="this.props.status"
    :stroke-width="this.props.strokeWidth"
    :stroke-color="this.props.strokeColor"
    :hide-info="this.props.hideInfo"
    :vertical="this.props.vertical"
    :success-percent="this.props.successPercent"
    :text-inside="this.props.textInside"
   />
</template>

<script>
import CommonUtil from "../../util/CommonUtil";
import ModelUtil from "../../util/ModelUtil";
import ExpressionUtil from "../../util/ExpressionUtil";
export default {
  name: "ProgressComponent",
  props: ["elementIndex", "props", "displayData", "detailIndex", "detailModel"],
  beforeMount: function() {

    // ClassName
    if (this.props.className == null) this.$set(this.props, "className", null);
    // 样式
    if (this.props.style == null) this.$set(this.props, "style", null);
    // 百分比
    if (this.props.percent == null) this.$set(this.props, "percent", 0);
    //状态，可选值为normal、active、wrong、success
    if (this.props.status == null) this.$set(this.props, "status", "normal");
    //进度条的线宽，单位 px
    if (this.props.strokeWidth == null) this.$set(this.props, "strokeWidth", 10);
    //进度条的颜色，4.0.0 开始支持传入数组，显示为渐变色**
    if (this.props.strokeColor == null) this.$set(this.props, "strokeColor", null);
    //隐藏数值或状态图标
    if (this.props.hideInfo == null) this.$set(this.props, "hideInfo", false);
    //是否在垂直方向显示
    if (this.props.vertical == null) this.$set(this.props, "vertical", false);
    //已完成的分段百分比
    if (this.props.successPercent == null) this.$set(this.props, "successPercent", 0);
    //百分比是否置于进度条内
    if (this.props.textInside == null) this.$set(this.props, "textInside", false);
    //走数据源 获取 数据 下标
    if (this.props.number == null) this.$set(this.props, "number", 0);

  },
  mounted: function() {
    this.timer = setInterval(() => {
      this.getPer();
    }, 100);
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
      return ModelUtil.getModelKey(this, this.displayData, "");
    }
  },
  methods: {
    getPer() {
       var dataOption;
      if (typeof this.props.percent == "string") {
        dataOption = ExpressionUtil.createObject(this, this.props.percent)
        this.percent = dataOption[this.props.number]?dataOption[this.props.number].value:0
      } else{
        this.percent = this.props.percent
      }
      clearInterval(this.timer);
    }
  },
  data() {
    return {
      percent: 0,
      timer: ''
    }
  }
};
</script>
