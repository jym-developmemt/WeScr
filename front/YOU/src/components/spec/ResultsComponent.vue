<template>
  <div :style="props.style">
    <Icon class="font-size-72 color-green" :type="props.iconType" />
    <br>
    <span class="font-title">{{props.title}}</span>
    <br>
    <Alert class="font-size-14" style="margin:20px auto;padding:24px 40px;text-align:left;width:50%;" :type="props.spanType">{{props.text}}</Alert>
    <br>
    <Button :type="props.butType1" @click="clickHandle1">{{props.butText1}}</Button>
    <br>
    <Button :type="props.butType2" style="float:left" @click="clickHandle2">{{props.butText2}}</Button>
  </div>
</template>

<script>
import CommonUtil from "../../util/CommonUtil";
import ModelUtil from "../../util/ModelUtil";
import ExpressionUtil from "../../util/ExpressionUtil";

export default {
  name: "ResultsComponent",
  props: ["elementIndex", "props", "displayData", "detailIndex", "detailModel"],
  beforeMount: function() {
    // iconType
    if (this.props.iconType == null) this.$set(this.props, "iconType", null);
    // style
    if (this.props.style == null) this.$set(this.props, "style", null);
    // title
    if (this.props.title == null) this.$set(this.props, "title", null);
    // text
    if (this.props.text == null) this.$set(this.props, "text", null);
     // spanType
    if (this.props.spanType == null) this.$set(this.props, "spanType", null);
    // butText1
    if (this.props.butText1 == null) this.$set(this.props, "butText1", null);
    // butText1
    if (this.props.butType1 == null) this.$set(this.props, "butType1", null);
     // butText2
    if (this.props.butText2 == null) this.$set(this.props, "butText2", null);
    // butText2
    if (this.props.butType2 == null) this.$set(this.props, "butType2", null);
  },
  mounted: function() {
    // 保存控件实例
    CommonUtil.saveElementToStore(this);
  },
  computed: {
    
  },
  methods: {
    clickHandle1: function() {
      //点击时触发
      if (this.props.onClick1) {
        ExpressionUtil.eval(this, this.props.onClick1, this.detailModel);
      }
      
    },
    clickHandle2: function() {
      //点击时触发
      if (this.props.onClick2) {
        ExpressionUtil.eval(this, this.props.onClick2, this.detailModel);
      }
      
    }
  }
};
</script>
