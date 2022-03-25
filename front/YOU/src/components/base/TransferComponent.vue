<template>
  <Transfer
    ref="elementRef"
    :data="options"
    :class="this.props.className"
    :style="this.props.style"
    :targetKeys="targetKeys"
    :render-format="renderFormat"
    :list-style="this.props.listStyle"
    :selected-keys="this.props.selectedKeys"
    :titles="this.props.titles"
    :operations="this.props.operations"
    :filterable="this.props.filterable"
    :filter-placeholder="this.props.filterPlaceholder"
    :filter-method="filterMethod"
    :not-found-text="this.props.notFoundText"
    @on-change="changeHandle"
    @on-selected-change="selectedChangeHandle"
  >
  </Transfer>
</template>

<script>
import CommonUtil from "../../util/CommonUtil";
import ModelUtil from "../../util/ModelUtil";
import ExpressionUtil from "../../util/ExpressionUtil";

export default {
  name: "SelectComponent",
  props: ["elementIndex", "props", "displayData", "detailIndex", "rowIndex", "detailModel"],
  beforeMount: function() {
    // ClassName
    if (this.props.className == null) this.$set(this.props, "className", null);
    // 样式
    if (this.props.style == null) this.$set(this.props, "style", null);
    // 每行数据渲染函数，该函数的入参为 data 中的项
    if (this.props.label == null) this.$set(this.props, "label", "a1.label");
    // 每行数据渲染函数，该函数的入参为 data 中的项
    if (this.props.key == null) this.$set(this.props, "key", "key");
    // 设置哪些项应该被选中
    if (this.props.selectedKeys == null) this.$set(this.props, "selectedKeys", []);
    // 两个穿梭框的自定义样式
    if (this.props.listStyle == null) this.$set(this.props, "listStyle", {});
    // 标题集合，顺序从左至右
    if (this.props.titles == null) this.$set(this.props, "titles", ['源列表', '目的列表']);
    // 操作文案集合，顺序从上至下
    if (this.props.operations == null) this.$set(this.props, "operations", []);
    // 是否显示搜索框
    if (this.props.filterable == null) this.$set(this.props, "filterable", false);
    // 搜索框的占位
    if (this.props.filterPlaceholder == null) this.$set(this.props, "filterPlaceholder", "请输入搜索内容");
    // 搜索框的占位
    if (this.props.notFoundText == null) this.$set(this.props, "notFoundText", "列表为空");
    if (this.props.valueKey == null) this.$set(this.props, "valueKey", "0");
    if (this.props.labelKey == null) this.$set(this.props, "labelKey", "1");
  },
  mounted: function() {
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
      return ModelUtil.getModelKey(this, this.displayData, []);
    },
    options: function() {
      var options = [];
      if (typeof this.props.options == "string") {
        // 动态选项
        options = ExpressionUtil.createObject(this, this.props.options);
        if (Array.isArray(options) == false) {
          options = [];
        }
      } else {
        // 固定选项
        options = this.props.options;
      }
      for (let i=0;i<options.length;i++) {
        options[i].key = options[i][this.props.valueKey];
        options[i].lab = options[i][this.props.labelKey];
      }
      return options;
    },
    targetKeys: function() {
      var targetKeys = [];
      for (let i=0;i<this.model[this.modelKey].length;i++) {
        targetKeys.push(this.model[this.modelKey][i][this.props.valueKey]);
      }
      return targetKeys;
    },
  },
  methods: {
    renderFormat: function(item) {
      if (this.props.renderFormat) {
        return ExpressionUtil.eval(this, "return " + this.props.renderFormat, item);
      } else {
        return item[this.props.labelKey];
      }
    },
    filterMethod: function(data, query) {
      if (this.props.filterMethod) {
        return ExpressionUtil.eval(this, "return " + this.props.filterMethod, data);
      } else if (data[this.props.labelKey]) {
        return data[this.props.labelKey].indexOf(query) > -1;
      } else {
	    return true;
	  }
    },
    changeHandle: function(newTargetKeys) {
      var rtnList = [];
      for (let i=0;i<this.options.length;i++) {
        if (newTargetKeys.indexOf(this.options[i].key) > -1) {
          rtnList.push(this.options[i]);
        }
      }
      this.model[this.modelKey] = rtnList;

      // 选项在两栏之间转移时的回调函数
      if (this.props.onChange) {
        ExpressionUtil.eval(this, this.props.onChange, this.detailModel, newTargetKeys);
      }
    },
    selectedChangeHandle: function(sourceSelectedKeys, targetSelectedKeys) {
      // 选中项发生变化时触发
      if (this.props.onSelectedChange) {
        ExpressionUtil.eval(this, this.props.onSelectedChange, this.detailModel, sourceSelectedKeys, targetSelectedKeys);
      }
    },
  }
};
</script>

<style scoped>
.ivu-transfer {
  text-align: center;
}

/deep/ .ivu-transfer-list {
  width: 300px;
  height: 300px;
}

/deep/ .ivu-transfer-list-content-item {
  text-align: left;
}
</style>
