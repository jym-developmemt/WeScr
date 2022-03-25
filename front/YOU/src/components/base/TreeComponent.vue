<template>
  <Tree
    :key="elementKey"
    ref="elementRef"
    :class-name="this.props.className"
    :styles="this.props.styles"
    :data="treeData"
    :multiple="this.props.multiple"
    :show-checkbox="this.props.showCheckbox"
    :empty-text="this.props.emptyText"
    :load-data="this.props.loadData"
    :children-key="this.props.childrenKey"
    :check-strictly="this.props.checkStrictly"
    :check-directly="this.props.checkDirectly"
    @on-select-change="selectChangeHandle"
    @on-check-change="checkChangeHandle"
    @on-toggle-expand="toggleExpandHandle"
  />
</template>

<script>
import CommonUtil from "../../util/CommonUtil";
import ModelUtil from "../../util/ModelUtil";
import ExpressionUtil from "../../util/ExpressionUtil";
import ProcessUtil from "../../util/ProcessUtil";

export default {
  name: "TreeComponent",
  props: ["elementIndex", "props", "displayData", "detailIndex", "rowIndex", "detailModel"],
  data() {
    return {
      elementKey: "0",
      parentNodeMap: {},
      treeData: [],
    };
  },
  beforeMount: function () {
    // 自定义 class 名称
    if (this.props.className == null) this.$set(this.props, "className", null);
    // 自定义 style 样式
    if (this.props.styles == null) this.$set(this.props, "styles", null);
    // 可嵌套的节点属性的数组，生成 tree 的数据
    if (this.props.options == null) this.$set(this.props, "options", []);
    // 是否支持多选
    if (this.props.multiple == null) this.$set(this.props, "multiple", false);
    // 是否显示多选框
    if (this.props.showCheckbox == null)
      this.$set(this.props, "showCheckbox", false);
    // 没有数据时的提示
    if (this.props.emptyText == null)
      this.$set(this.props, "emptyText", "暂无数据");
    // 定义子节点键
    if (this.props.childrenKey == null)
      this.$set(this.props, "childrenKey", "_children_");
    // 在显示复选框的情况下，是否严格的遵循父子不互相关联的做法
    if (this.props.checkStrictly == null)
      this.$set(this.props, "checkStrictly", true);
    // 开启后，在 show-checkbox 模式下，select 的交互也将转为 check
    if (this.props.checkDirectly == null)
      this.$set(this.props, "checkDirectly", false);
    // 默认是否展开
    if (this.props.defaultExpand == null)
      this.$set(this.props, "defaultExpand", true);
    // 值Key
    if (this.props.valueKey == null) this.$set(this.props, "valueKey", "value");
    // 值Key
    if (this.props.labelKey == null) this.$set(this.props, "labelKey", "label");
    // 多语言
    if (this.props.i18n == null) this.$set(this.props, "i18n", true);
  },
  created: function () {
    this.$watch("model." + this.modelKey, function () {
      this.fetchData(this.treeData);
      this.elementKey = new ProcessUtil().generateKey(16);
    });
    this.$watch("options", function () {
      // 树设定
      this.treeData.splice(0, this.treeData.length);
      this.treeData = this.treeData.concat(this.options);
      // 初始化数据
      this.initDataList(this.model[this.modelKey], this.treeData);
      this.elementKey = new ProcessUtil().generateKey(16);
    });
  },
  mounted: function () {
    // 保存控件实例
    CommonUtil.saveElementToStore(this);
  },
  computed: {
    model: function () {
      if (this.detailModel) {
        return this.detailModel;
      }
      return ModelUtil.getModelObject(this, this.displayData);
    },
    modelKey: function () {
      if (this.detailModel) {
        return this.displayData;
      }
      return ModelUtil.getModelKey(this, this.displayData, []);
    },
    options: function () {
      if (typeof this.props.options == "string") {
        // 动态选项
        let dataOption = ExpressionUtil.createObject(
          this,
          this.props.options,
          this.model
        );
        if (dataOption == null) {
          this.$set(this.model, this.modelKey, []);
        }
        return [dataOption];
      } else {
        // 固定选项
        return this.props.options;
      }
    },
  },
  methods: {
    // 检索选中项
    initDataList: function (checkedList, dataList, parent) {
      var me = this;
      dataList.forEach((data) => {
        data.expand = me.props.defaultExpand;
        data.title = me.props.i18n?$t(data[me.props.labelKey]):data[me.props.labelKey];
        data.nodeKey = new ProcessUtil().generateKey(16);
        
        var dataVal = data[me.props.valueKey];
        if (parent) {
          me.parentNodeMap[dataVal] = parent;
        }
        if (checkedList) {
          for (var i = 0; i < checkedList.length; i++) {
            if (dataVal == checkedList[i]) {
              data.checked = true;
            }
          }
        }
        if (data[me.props.childrenKey]) {
          me.initDataList(checkedList, data[me.props.childrenKey], data);
        }
      });
    },
    // 初始化数据
    fetchData: function (dataList) {
      var me = this;

      dataList.forEach((data) => {
        data.checked = false;

        var dataVal = data[me.props.valueKey];
        if (this.model[this.modelKey]) {
          for (var i = 0; i < this.model[this.modelKey].length; i++) {
            if (dataVal == this.model[this.modelKey][i]) {
              data.checked = true;
              break;
            }
          }
        }
        if (data[me.props.childrenKey]) {
          me.fetchData(data[me.props.childrenKey]);
        }
      });
    },
    selectChangeHandle: function (values, curentItem) {
      // 点击树节点时触发
      if (this.props.onSelectChange) {
        ExpressionUtil.eval(this, this.props.onSelectChange, values, curentItem);
      }
    },
    checkChangeHandle: function (values, curentItem) {
      // 值设定
      var rtnList = new ProcessUtil().listToArray(
        this.$refs.elementRef.getCheckedNodes(),
        this.props.valueKey
      );
      this.$set(this.model, this.modelKey, rtnList);

      // 点击复选框时触发
      if (this.props.onCheckChange) {
        ExpressionUtil.eval(this, this.props.onCheckChange, values, curentItem);
      }
    },
    toggleExpandHandle: function (item) {
      // 展开和收起子列表时触发
      if (this.props.onToggleExpand) {
        ExpressionUtil.eval(this, this.props.onToggleExpand, item);
      }
      this.elementKey = new ProcessUtil().generateKey(16);
    },
  },
};
</script>
