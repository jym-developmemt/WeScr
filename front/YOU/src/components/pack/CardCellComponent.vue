<template>
  <CellGroup @on-click="clickHandle">
    <Cell
      ref="elementRef"
      :style="this.props.style"
      :class="this.props.className"
      :title="this.props.i18n?$t(this.displayData):this.displayData"
      :label="this.props.i18n?$t(this.props.label):this.props.label"
      :selected="this.props.selected"
      :disabled="disabled"
    >
      <!-- 控件 -->
      <u-dispatcher :key="cellItem.key" v-for="cellItem in cellItems" :element-info="cellItem"></u-dispatcher>
    </Cell>
  </CellGroup>
</template>

<script>
import CommonUtil from "../../util/CommonUtil";
import ModelUtil from "../../util/ModelUtil";
import ExpressionUtil from "../../util/ExpressionUtil";

export default {
  name: "CardCellComponent",
  props: ["elementIndex", "props", "displayData", "detailList"],
  beforeMount: function() {
    // ClassName
    if (this.props.className == null) this.$set(this.props, "className", null);
    // 样式
    if (this.props.style == null) this.$set(this.props, "style", null);
    // 是否禁用
    if (this.props.disabled == null) this.$set(this.props, "disabled", false);
    // 标题下方的描述信息
    if (this.props.label == null) this.$set(this.props, "label", null);
    // 标记该项为选中状态
    if (this.props.selected == null) this.$set(this.props, "selected", false);
    // 多语言
    if (this.props.i18n == null) this.$set(this.props, "i18n", true);
  },
  mounted: function() {
    // 保存控件实例
    CommonUtil.saveElementToStore(this);
  },
  computed: {
    cellItems: function() {
      var cellItems = new Array();
      if (this.detailList) {
        for (let i = 0; i < this.detailList.length; i++) {
          var itemDetail = this.detailList[i];

          // 列属性
          var itemProp = {};
          if (itemDetail.detailProp) {
            itemProp = JSON.parse(itemDetail.detailProp);
          }
          itemProp.label = itemDetail.detailName;
          itemProp.key = itemDetail.displayData;

          // 控件属性
          itemProp.elementIndex = itemDetail.elementIndex;
          itemProp.detailIndex = itemDetail.detailIndex;
          itemProp.displayType = itemDetail.displayType;
          itemProp.displayData = itemDetail.displayData;
          cellItems.push(itemProp);
        }
      }
      return cellItems;
    },
    disabled: function() {
      // 设置输入框为只读
      return this.$store.getters.isLoading || this.props.disabled;
    }
  },
  methods: {
    clickHandle: function() {
      // 点击标签时触发
      if (this.props.onClick) {
        ExpressionUtil.eval(this, this.props.onClick);
      }
    }
  }
};
</script>
