<template>
  <Vue2OrgTree
    ref="elementRef"
    v-if="this.props.visible"
    :data="model[modelKey]"
    :props="this.props.props"
    :horizontal="this.props.horizontal"
    :labelWidth="this.props.labelWidth"
    :collapsable="this.props.collapsable"
    :renderContent="this.props.renderContent"
    :labelClassName="labelClassName"
    @on-expand="expandHandle"
    @on-node-click="nodeClickHandle"
  >
  </Vue2OrgTree>
</template>

<script>
import CommonUtil from "../../util/CommonUtil";
import ModelUtil from "../../util/ModelUtil";
import ExpressionUtil from "../../util/ExpressionUtil";

export default {
  name: "OrgTreeComponent",
  props: ["elementIndex", "props", "displayData", "detailIndex", "detailModel"],
  data: function() {
    return {
      treeStatus: {}
    };
  },
  beforeMount: function() {
    // 可见
    if (this.props.visible == null) this.$set(this.props, "visible", true);
    // configure props
    if (this.props.itemKey == null) this.$set(this.props, "itemKey", "id");
    var labelKey = "label";
    if (this.props.labelKey) labelKey = this.props.labelKey;
    var expand = "expand";
    if (this.props.expand) expand = this.props.expand;
    this.$set(this.props, "props", {"label": labelKey, "children": '_children_', "expand": expand});
    // node label width
    if (this.props.labelWidth == null) this.$set(this.props, "labelWidth", "auto");
    // children node is collapsable
    if (this.props.collapsable == null) this.$set(this.props, "collapsable", true);
    // how to render node label
    if (this.props.renderContent == null) this.$set(this.props, "renderContent", null);
    // node label class
    if (this.props.horizontal == null) this.$set(this.props, "horizontal", false);
    // node label class
    if (this.props.labelClassName == null) this.$set(this.props, "labelClassName", "bg-white");
    // The className of the selected node
    if (this.props.selectedClassName == null) this.$set(this.props, "selectedClassName", "bg-orange");
    // The key of the selected node
    this.$set(this.props, "selectedObject", null);
  },
  mounted: function() {
    var me = this;
    this.$refs.elementRef.saveExpand = function(){
      me.saveExpand(me.model[me.modelKey]);
    };
    this.$refs.elementRef.loadExpand = function(){
      me.loadExpand(me.model[me.modelKey]);
    };

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
    },
  },
  methods: {
    labelClassName: function(data) {
      if (data == this.props.selectedObject) {
        return this.props.selectedClassName;
      } else {
        return this.props.labelClassName;
      }
    },
    expandHandle: function(e, data) {
      if ('expand' in data) {
        data.expand = !data.expand

        if (!data.expand && data.children) {
          this.collapse(data.children)
        }
      } else {
        this.$set(data, 'expand', true)
      }
      if (this.props.onExpand) {
        ExpressionUtil.eval(this, this.props.onExpand, data);
      }
    },
    nodeClickHandle: function(e, data) {
      if (this.props.onNodeClick) {
        ExpressionUtil.eval(this, this.props.onNodeClick, data);
      }
    },
    collapse: function(list) {
      var _this = this
      list.forEach(function(child) {
        if (child.expand) {
          child.expand = false
        }

        child.children && _this.collapse(child.children)
      })
    },
    saveExpand: function(srcObj) {
      this.treeStatus[srcObj[this.props.itemKey]] = srcObj[this.props.props.expand];

      if (srcObj[this.props.props.children]) {
        for (let i=0;i<srcObj[this.props.props.children].length;i++) {
          this.saveExpand(srcObj[this.props.props.children][i]);
        }
      }
    },
    loadExpand: function(srcObj) {
      srcObj[this.props.props.expand] = this.treeStatus[srcObj[this.props.itemKey]];

      if (srcObj[this.props.props.children]) {
        for (let i=0;i<srcObj[this.props.props.children].length;i++) {
          this.loadExpand(srcObj[this.props.props.children][i]);
        }
      }
    },
  }
};
</script>

<style scoped>
/deep/ .bg-white {
  background-color: white;
  cursor: pointer;
}
/deep/ .bg-orange {
  background-color: orange;
  cursor: default;
}
</style>