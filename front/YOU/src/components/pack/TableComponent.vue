<template>
  <Table
    ref="elementRef"
    :class="this.props.className"
    :style="this.props.style"
    :data="model[modelKey]"
    :columns="columns"
    :stripe="this.props.stripe"
    :border="this.props.border"
    :show-header="this.props.showHeader"
    :width="this.props.width"
    :height="this.props.height"
    :max-height="this.props.maxHeight"
    :loading="tableloading"
    :disabled-hover="this.props.disabledHover"
    :highlight-row="this.props.highlightRow"
    :row-class-name="rowClassName"
    :size="this.props.size"
    :no-data-text="this.props.noDataText"
    :no-filtered-data-text="this.props.noFilteredDataText"
    :draggable="this.props.draggable"
    :tooltip-theme="this.props.tooltipTheme"
    :row-key="this.props.rowKey"
    :span-method="spanMethod"
    :show-summary="this.props.showSummary"
    :sum-text="this.props.sumText"
    :summary-method="summaryMethod"
    @on-current-change="currentChangeHandle"
    @on-select="selectHandle"
    @on-select-cancel="selectCancelChangeHandle"
    @on-select-all="selectAllHandle"
    @on-select-all-cancel="selectAllCancelHandle"
    @on-selection-change="selectionChangeHandle"
    @on-sort-change="sortChangeHandle"
    @on-filter-change="filterChangeHandle"
    @on-row-click="rowClickHandle"
    @on-row-dblclick="rowDblclickHandle"
    @on-expand="expandHandle"
    @on-drag-drop="dragDropHandle"
    @on-column-width-resize="columnWidthResizeHandle"
  >
    <template v-for="columnInfo in this.columns" slot-scope="{ row, index }" :slot="columnInfo.key">
      <!-- 控件 -->
      <u-dispatcher
        :key="columnInfo.key"
        :element-info="columnInfo"
        :detailModel="model[modelKey][index]"
        :rowIndex="index"
      ></u-dispatcher>
    </template>
  </Table>
</template>

<script>
import CommonUtil from "../../util/CommonUtil";
import ModelUtil from "../../util/ModelUtil";
import ExpressionUtil from "../../util/ExpressionUtil";
import numeral from "numeral";

export default {
  name: "TableComponent",
  props: ["elementIndex", "props", "displayData", "detailList"],
  beforeMount: function () {
    // ClassName
    if (this.props.className == null) this.$set(this.props, "className", null);
    // 样式
    if (this.props.style == null) this.$set(this.props, "style", null);
    // 是否显示间隔斑马纹
    if (this.props.stripe == null) this.$set(this.props, "stripe", false);
    // 是否显示纵向边框
    if (this.props.border == null) this.$set(this.props, "border", true);
    // 是否显示表头
    if (this.props.showHeader == null)
      this.$set(this.props, "showHeader", true);
    // 表格宽度，单位 px
    if (this.props.width == null) this.$set(this.props, "width", "auto");
    // 表格高度，单位 px，设置后，如果表格内容大于此值，会固定表头
    if (this.props.height == null) this.$set(this.props, "height", 438);
    // 表格最大高度，单位 px，设置后，如果表格内容大于此值，会固定表头
    if (this.props.maxHeight == null) this.$set(this.props, "maxHeight", null);
    // 表格是否加载中
    if (this.props.loading == null) this.$set(this.props, "loading", false);
    // 禁用鼠标悬停时的高亮
    if (this.props.disabledHover == null)
      this.$set(this.props, "disabledHover", false);
    // 是否支持高亮选中的行，即单选
    if (this.props.highlightRow == null)
      this.$set(this.props, "highlightRow", false);
    // 行的 className 的回调方法，传入参数：row：当前行数据 index：当前行的索引
    if (this.props.rowClassName == null)
      this.$set(this.props, "rowClassName", null);
    // 表格尺寸，可选值为 large、small、default 或者不填
    if (this.props.size == null) this.$set(this.props, "size", "small");
    // 数据为空时显示的提示内容
    if (this.props.noDataText == null)
      this.$set(this.props, "noDataText", "暂无数据");
    // 筛选数据为空时显示的提示内容
    if (this.props.noFilteredDataText == null)
      this.$set(this.props, "noFilteredDataText", "暂无筛选结果");
    // 是否开启拖拽调整行顺序，需配合 @on-drag-drop 事件使用
    if (this.props.draggable == null) this.$set(this.props, "draggable", false);
    // 列使用 tooltip 时，配置它的主题，可选值为 dark 或 light
    if (this.props.tooltipTheme == null)
      this.$set(this.props, "tooltipTheme", "dark");
    // 是否强制使用内置的 row-key，开启后可能会影响性能
    if (this.props.rowKey == null) this.$set(this.props, "rowKey", false);
    // 是否在表尾显示合计行
    if (this.props.showSummary == null)
      this.$set(this.props, "showSummary", false);
    // 合计行第一列的文本
    if (this.props.sumText == null) this.$set(this.props, "sumText", "合计");
    // 合计行第一列的文本
    if (this.props.summaryColumns == null)
      this.$set(this.props, "summaryColumns", []);
    if (this.props.summaryFormat == null) 
      this.$set(this.props, "summaryFormat", {});
  },
  mounted: function () {
    // 保存控件实例
    CommonUtil.saveElementToStore(this);
  },
  computed: {
    tableloading: function () {
      // 设置输入框为只读
      return this.$store.getters.isLoading || this.props.loading;
    },
    model: function () {
      return ModelUtil.getModelObject(this, this.displayData);
    },
    modelKey: function () {
      return ModelUtil.getModelKey(this, this.displayData, []);
    },
    columns: function () {
      var tableColumn = new Array();
      if (this.detailList) {
        for (let i = 0; i < this.detailList.length; i++) {
          var columnDetail = this.detailList[i];

          // 列属性
          var columnProp = {};
          if (columnDetail.detailProp) {
            columnProp = JSON.parse(columnDetail.detailProp);
          }
          columnProp.title = columnDetail.detailName;
          columnProp.key = columnDetail.detailIndex;
          if (columnDetail.displayType) {
            // 控件属性
            columnProp.slot = columnProp.key;
            columnProp.elementIndex = columnDetail.elementIndex;
            columnProp.detailIndex = columnDetail.detailIndex;
            columnProp.displayType = columnDetail.displayType;
            columnProp.displayData = columnDetail.displayData;
            columnProp.detailModel = this.model[this.modelKey];
            columnProp.detailAddon = columnDetail.detailAddon;
          }

          tableColumn.push(columnProp);
        }
      }
      return tableColumn;
    },
  },
  methods: {
    rowClassName: function (row) {
      if (this.props.rowClassName) {
        return ExpressionUtil.eval(
          this,
          "return " + this.props.rowClassName,
          row
        );
      }
    },
    spanMethod: function (obj) {
      if (this.props.spanMethod) {
        return ExpressionUtil.eval(
          this,
          "return " + this.props.spanMethod,
          obj
        );
      } else {
        return {
          rowspan: 1,
          colspan: 1,
        };
      }
    },
    summaryMethod: function ({ columns, data }) {
      if (this.props.showSummary) {
        const sums = {};
        columns.forEach((column, index) => {
          const key = column.key;

          const keyIndex = this.props.summaryColumns.indexOf(key);

          if (keyIndex === 0) {
            sums[key] = {
              key,
              value: "总价",
            };
          } else if (keyIndex == -1) {
            sums[key] = {
              key,
              value: "",
            };
          } else {
            var value = 0;
            for (var i = 0; i < column.detailModel.length; i++) {
              var subValue = ModelUtil.getDetailModelValue(
                this,
                column.detailModel[i],
                column.displayData
              );
              if (subValue != null && Number.isNaN(subValue * 1) == false) {
                value += subValue * 1;
              }
            }
            var format = this.props.summaryFormat[column.key];
            sums[key] = {
              key,
              value: numeral(value).format(format?format:"0.00"),
            };
          }
        });
        return sums;
      } else {
        return null;
      }
    },
    currentChangeHandle: function (currentRow) {
      // 开启 highlight-row 后有效，当表格的当前行发生变化的时候会触发
      if (this.props.onCurrentChange) {
        ExpressionUtil.eval(this, this.props.onCurrentChange, currentRow);
      }
    },
    selectHandle: function (selection, row) {
      // 在多选模式下有效，选中某一项时触发
      if (this.props.onSelect) {
        ExpressionUtil.eval(this, this.props.onSelect, selection, row);
      }
    },
    selectCancelChangeHandle: function () {
      // 在多选模式下有效，取消选中某一项时触发
      if (this.props.selectCancelChange) {
        ExpressionUtil.eval(this, this.props.selectCancelChange);
      }
    },
    selectAllHandle: function (selection) {
      // 在多选模式下有效，点击全选时触发
      if (this.props.onSelectAll) {
        ExpressionUtil.eval(this, this.props.onSelectAll, selection);
      }
    },
    selectAllCancelHandle: function () {
      // 在多选模式下有效，点击取消全选时触发
      if (this.props.onSelectAllCancel) {
        ExpressionUtil.eval(this, this.props.onSelectAllCancel);
      }
    },
    selectionChangeHandle: function (selection) {
      // 在多选模式下有效，只要选中项发生变化时就会触发
      if (this.props.onSelectionChange) {
        ExpressionUtil.eval(this, this.props.onSelectionChange, selection);
      }
    },
    sortChangeHandle: function (arg) {
      // 排序时有效，当点击排序时触发
      if (this.props.onSortChange) {
        ExpressionUtil.eval(
          this,
          this.props.onSortChange,
          arg.column.displayData,
          arg.key,
          arg.order
        );
      }
    },
    filterChangeHandle: function () {
      // 筛选时有效，筛选条件发生变化时触发
      if (this.props.onFilterChange) {
        ExpressionUtil.eval(this, this.props.onFilterChange);
      }
    },
    rowClickHandle: function (data, index) {
      // 单击某一行时触发
      if (this.props.onRowClick) {
        ExpressionUtil.eval(this, this.props.onRowClick, data, index);
      }
    },
    rowDblclickHandle: function (data, index) {
      // 双击某一行时触发
      if (this.props.onRowDblclick) {
        ExpressionUtil.eval(this, this.props.onRowDblclick, data, index);
      }
    },
    expandHandle: function () {
      // 展开或收起某一行时触发
      if (this.props.onExpand) {
        ExpressionUtil.eval(this, this.props.onExpand);
      }
    },
    dragDropHandle: function () {
      // 拖拽排序松开时触发，返回置换的两行数据索引
      if (this.props.onDragDrop) {
        ExpressionUtil.eval(this, this.props.onDragDrop);
      }
    },
    columnWidthResizeHandle: function (newWidth, oldWidth, column, event) {
      // 拖拽调整列宽时触发
      if (this.props.onColumnWidthResize) {
        ExpressionUtil.eval(
          this,
          this.props.onColumnWidthResize,
          newWidth,
          oldWidth,
          column,
          event
        );
      }
    },
  },
};
</script>

<style scoped>
/deep/ .ivu-table-cell i {
  font-size: 20px;
}
</style>