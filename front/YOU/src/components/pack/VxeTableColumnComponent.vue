<template v-if="this.visible">
  <vxe-table-column
    :type="this.props.type"
    :field="this.props.field"
    :title="this.props.title"
    :width="this.props.width"
    :min-width="this.props.minWidth"
    :resizable="this.props.resizable"
    :fixed="this.props.fixed"
    :align="this.props.align"
    :header-align="this.props.headerAlign"
    :footer-align="this.props.footerAlign"
    :show-overflow="this.props.showOverflow"
    :show-header-overflow="this.props.showHeaderOverflow"
    :show-footer-overflow="this.props.showFooterOverflow"
    :sortable="this.props.sortable"
    :sort-by="this.props.sortBy"
    :remote-sort="this.props.remoteSort"
    :tree-node="this.props.treeNode"
    :edit-render="this.props.editRender"
    :class-name="this.props.className"
    :header-class-name="this.props.headerClassName"
    :footer-class-name="this.props.footerClassName"
    :formatter="this.props.formatter"
    :seq-method="this.props.seqMethod"
    :sort-method="this.props.sortMethod"
  >
    <!-- 表示用控件 -->
    <template v-if="props.elementInfo" v-slot="scope">
      <u-dispatcher
        :element-info="props.elementInfo"
        :detailModel="scope.row"
        :elementType="(elementType&&elementType[scope.rowIndex])?elementType[scope.rowIndex].type:null"
        :elementProps="(elementType&&elementType[scope.rowIndex])?elementType[scope.rowIndex].prop:null"
        :rowIndex="scope.rowIndex"
      ></u-dispatcher>
    </template>

    <!-- 编辑用控件 -->
    <template v-if="props.editRender && props.editRender.editType" v-slot:edit="scope">
      <u-dispatcher
        :element-info="props.elementInfo"
        :detailModel="scope.row"
        :elementType="(elementEditType&&elementEditType[scope.rowIndex])?elementEditType[scope.rowIndex].type:props.editRender.editType"
        :elementProps="(elementEditType&&elementEditType[scope.rowIndex])?elementEditType[scope.rowIndex].prop:props.editRender.editProp"
        :rowIndex="scope.rowIndex"
      ></u-dispatcher>
    </template>
  </vxe-table-column>
</template>

<script>
export default {
  name: "VxeTableColumnComponent",
  props: ["props", "elementType", "elementEditType"],
  beforeMount: function () {
    // 列的类型
    if (this.props.type == null) this.$set(this.props, "type", null);
    // 列字段名（注：属性层级越深，渲染性能就越差，例如：aa.bb.cc.dd.ee）
    if (this.props.field == null) this.$set(this.props, "field", null);
    // 列标题（支持开启国际化）
    if (this.props.title == null) this.$set(this.props, "title", null);
    // 列宽度（如果为空则均匀分配剩余宽度，如果全部列固定了，可能会存在宽屏下不会铺满，可以配合 "%" 或者 "min-width" 布局）
    if (this.props.width == null) this.$set(this.props, "width", null);
    // 最小列宽度；会自动将剩余空间按比例分配
    if (this.props.minWidth == null) this.$set(this.props, "minWidth", null);
    // 列是否允许拖动列宽调整大小
    if (this.props.resizable == null) this.$set(this.props, "resizable", null);
    // 将列固定在左侧或者右侧（注意：固定列应该放在左右两侧的位置）
    if (this.props.fixed == null) this.$set(this.props, "fixed", null);
    // 列对齐方式
    if (this.props.align == null) this.$set(this.props, "align", null);
    // 表头列的对齐方式
    if (this.props.headerAlign == null)
      this.$set(this.props, "headerAlign", null);
    // 表尾列的对齐方式
    if (this.props.footerAlign == null)
      this.$set(this.props, "footerAlign", null);
    // 当内容过长时显示为省略号
    if (this.props.showOverflow == null)
      this.$set(this.props, "showOverflow", null);
    // 当表头内容过长时显示为省略号
    if (this.props.showHeaderOverflow == null)
      this.$set(this.props, "showHeaderOverflow", null);
    // 当表尾内容过长时显示为省略号
    if (this.props.showFooterOverflow == null)
      this.$set(this.props, "showFooterOverflow", null);
    // 是否允许列排序
    if (this.props.sortable == null) this.$set(this.props, "sortable", false);
    // 只对 sortable 有效，自定义排序的属性
    if (this.props.sortBy == null) this.$set(this.props, "sortBy", null);
    // 是否使用服务端排序，如果设置为 true 则不会对数据进行处理
    if (this.props.remoteSort == null)
      this.$set(this.props, "remoteSort", null);
    // 只对 tree-config 配置时有效，指定为树节点
    if (this.props.treeNode == null) this.$set(this.props, "treeNode", false);
    // 只对 tree-config 配置时有效，指定为树节点
    if (this.props.editRender == null)
      this.$set(this.props, "editRender", null);
  },
  computed: {
    // 控件可见
    visible: function () {
      if (this.props) {
        switch (typeof this.props.visible) {
          case "string":
            return ExpressionUtil.eval(this, "return " + this.props.visible);
          case "boolean":
            return this.props.visible;
        }
      }
      return true;
    },
  },
  methods: {
    // 给单元格附加 className，也可以是函数 Function({row, rowIndex, $rowIndex, column, columnIndex, $columnIndex})
    className: function (param) {
      if (this.props.className) {
        return ExpressionUtil.eval(this, this.props.className, param);
      }
    },
    // 给表头的单元格附加 className，也可以是函数 Function({$rowIndex, column, columnIndex, $columnIndex})
    headerClassName: function (param) {
      if (this.props.headerClassName) {
        return ExpressionUtil.eval(this, this.props.headerClassName, param);
      }
    },
    // 给表尾的单元格附加 className，也可以是函数 Function({$rowIndex, column, columnIndex, $columnIndex})
    footerClassName: function (param) {
      if (this.props.footerClassName) {
        return ExpressionUtil.eval(this, this.props.footerClassName, param);
      }
    },
    // 格式化显示内容 Function({cellValue, row, column})
    formatter: function (param) {
      if (this.props.formatter) {
        return ExpressionUtil.eval(this, this.props.formatter, param);
      }
    },
    // 只对 type=seq 有效，自定义索引方法 Function({row, rowIndex, column, columnIndex})
    seqMethod: function (param) {
      if (this.props.seqMethod) {
        return ExpressionUtil.eval(this, this.props.seqMethod, param);
      }
    },
    // 只对 sortable 有效，列的排序方法，该方法 Function(prevRow, nexRow) 的返回值用来决定该行的排序规则
    sortMethod: function (param) {
      if (this.props.sortMethod) {
        return ExpressionUtil.eval(this, this.props.sortMethod, param);
      }
    },
  },
};
</script>
