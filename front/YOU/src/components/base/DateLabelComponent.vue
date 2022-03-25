<template>
  <div
    ref="elementRef"
    :class="this.props.className"
    :style="this.props.style"
    @click="clickHandle"
  >
    <div :class="'row1 ' + this.props.className1" :style="this.props.style1">{{text1}}</div>
    <div :class="'row2 ' + this.props.className2" :style="this.props.style2">{{text2}}</div>
  </div>
</template>

<script>
import CommonUtil from "../../util/CommonUtil";
import ModelUtil from "../../util/ModelUtil";
import ExpressionUtil from "../../util/ExpressionUtil";
import moment from "moment";

export default {
  name: "DateLabelComponent",
  props: ["elementIndex", "props", "displayData", "detailIndex", "rowIndex", "detailModel"],
  beforeMount: function() {
    // ClassName
    if (this.props.className == null) this.$set(this.props, "className", null);
    // 样式
    if (this.props.style == null) this.$set(this.props, "style", null);
    // ClassName
    if (this.props.className == null) this.$set(this.props, "className1", '');
    // 样式
    if (this.props.style == null) this.$set(this.props, "style1", null);
    // ClassName
    if (this.props.className == null) this.$set(this.props, "className2", '');
    // 样式
    if (this.props.style == null) this.$set(this.props, "style2", null);
    // 表达式
    if (this.props.expression == null) this.$set(this.props, "expression", "");
    // 时间格式1
    if (this.props.dateFormat1 == null)
      this.$set(this.props, "dateFormat1", "DD");
    // 时间格式2
    if (this.props.dateFormat2 == null)
      this.$set(this.props, "dateFormat2", "YYYY.MM");
  },
  mounted: function() {
    // 保存控件实例
    CommonUtil.saveElementToStore(this);
  },
  computed: {
    // 表示文本
    text1: function() {
      let textData = "";
      if (this.detailModel) {
        textData = ModelUtil.getDetailModelValue(
          this,
          this.detailModel,
          this.displayData
        );
      } else {
        if (this.props.expression) {
          // 模型值
          var modelObj = ModelUtil.getModelObject(this, this.props.expression);
          var modelKey = ModelUtil.getModelKey(this, this.props.expression, "");
          if (modelObj) {
            textData = modelObj[modelKey];
          } else {
            textData = "";
          }
        } else {
          // 固定文本
          textData = this.displayData;
        }
      }

      if (textData && this.props.dateFormat1) {
        textData = moment(textData).format(this.props.dateFormat1);
      }

      return textData;
    },
    // 表示文本
    text2: function() {
      let textData = "";
      if (this.detailModel) {
        textData = ModelUtil.getDetailModelValue(
          this,
          this.detailModel,
          this.displayData
        );
      } else {
        if (this.props.expression) {
          // 模型值
          var modelObj = ModelUtil.getModelObject(this, this.props.expression);
          var modelKey = ModelUtil.getModelKey(this, this.props.expression, "");
          if (modelObj) {
            textData = modelObj[modelKey];
          } else {
            textData = "";
          }
        } else {
          // 固定文本
          textData = this.displayData;
        }
      }

      if (textData && this.props.dateFormat2) {
        textData = moment(textData).format(this.props.dateFormat2);
      }

      return textData;
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

<style scoped>
.row1 {
  text-align: center;
  font-size: 30px;
  line-height: 30px;
  font-weight: bolder;
}

.row2 {
  text-align: center;
  font-size: 18px;
  line-height: 18px;
}
</style>