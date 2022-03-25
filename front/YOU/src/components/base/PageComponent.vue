<template>
  <Page
    ref="elementRef"
    :current="current"
    :total="total"
    :page-size="pageSize"
    :page-size-opts="this.props.pageSizeOpts"
    :placement="this.props.placement"
    :size="this.props.size"
    :simple="this.props.simple"
    :show-total="this.props.showTotal"
    :show-elevator="this.props.showElevator"
    :show-sizer="this.props.showSizer"
    :class-name="this.props.className"
    :styles="this.props.styles"
    :transfer="this.props.transfer"
    :prev-text="this.props.prevText"
    :next-text="this.props.nextText"
    :disabled="disabled"
    @on-change="changeHandle"
    @on-page-size-change="pageSizeChangeHandle"
  />
</template>

<script>
import CommonUtil from "../../util/CommonUtil";
import ModelUtil from "../../util/ModelUtil";
import ExpressionUtil from "../../util/ExpressionUtil";

export default {
  name: "PageComponent",
  props: ["elementIndex", "props", "displayData", "detailIndex", "rowIndex", "detailModel"],
  beforeMount: function() {
    // // 每页条数切换的配置
    // if (this.props.pageSizeOopts == null)
    //   this.$set(this.props, "pageSizeOopts", "");
    // 条数切换弹窗的展开方向，可选值为 bottom 和 top
    if (this.props.placement == null)
      this.$set(this.props, "placement", "bottom");
    // 可选值为small（迷你版）或不填（默认）
    if (this.props.size == null) this.$set(this.props, "size", null);
    // 简洁版
    if (this.props.simple == null) this.$set(this.props, "simple", false);
    // 显示总数
    if (this.props.showTotal == null) this.$set(this.props, "showTotal", true);
    // 显示电梯，可以快速切换到某一页
    if (this.props.showElevator == null)
      this.$set(this.props, "showElevator", false);
    // 显示分页，用来改变page-size
    if (this.props.showSizer == null) this.$set(this.props, "showSizer", false);
    // 自定义 class 名称
    if (this.props.className == null) this.$set(this.props, "className", null);
    // 自定义 style 样式
    if (this.props.styles == null) this.$set(this.props, "styles", null);
    // 是否将弹层放置于 body 内，在 Tabs、带有 fixed 的 Table 列内使用时，建议添加此属性，它将不受父级样式影响，从而达到更好的效果
    if (this.props.transfer == null) this.$set(this.props, "transfer", false);
    // 替代图标显示的上一页文字
    if (this.props.prevText == null) this.$set(this.props, "prevText", "");
    // 替代图标显示的下一页文字
    if (this.props.nextText == null) this.$set(this.props, "nextText", "");
    // 是否禁用
    if (this.props.disabled == null) this.$set(this.props, "disabled", false);
  },
  mounted: function() {
    // 保存控件实例
    CommonUtil.saveElementToStore(this);
  },
  computed: {
    model: function() {
      return ModelUtil.getModelObject(this, this.displayData);
    },
    modelKey: function() {
      return ModelUtil.getModelKey(this, this.displayData, {});
    },
    // 当前页
    current: function() {
      if (this.model[this.modelKey].pageNo) {
        return parseInt(this.model[this.modelKey].pageNo);
      } else {
        return 1;
      }
    },
    // 总件数
    total: function() {
      if (this.model[this.modelKey].itemCount) {
        return parseInt(this.model[this.modelKey].itemCount);
      } else {
        return 0;
      }
    },
    // 每页件数
    pageSize: function() {
      if (this.model[this.modelKey].pageSize) {
        return parseInt(this.model[this.modelKey].pageSize);
      } else {
        return 10;
      }
    },
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
    changeHandle: function(pageNo) {
      this.model[this.modelKey].pageNo = pageNo;

      // 页码改变的回调，返回改变后的页码
      if (this.props.onChange) {
        ExpressionUtil.eval(this, this.props.onChange, pageNo);
      }
    },
    pageSizeChangeHandle: function(pageSize) {
      this.model[this.modelKey].pageSize = pageSize;

      // 切换每页条数时的回调，返回切换后的每页条数
      if (this.props.onPageSizeChange) {
        ExpressionUtil.eval(this, this.props.onPageSizeChange, pageSize);
      }
    }
  }
};
</script>
