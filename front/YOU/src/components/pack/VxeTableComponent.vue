<template>
  <vxe-table
    ref="elementRef"
    :data="model[modelKey]"
    :rowId="this.props.rowId"
    :height="this.props.height"
    :max-height="this.props.maxHeight"
    :resizable="this.props.resizable"
    :stripe="this.props.stripe"
    :border="this.props.border"
    :round="this.props.round"
    :size="this.props.size"
    :loading="tableloading"
    :align="this.props.align"
    :header-align="this.props.headerAlign"
    :footer-align="this.props.footerAlign"
    :show-header="this.props.showHeader"
    :highlight-current-row="this.props.highlightCurrentRow"
    :highlight-hover-row="this.props.highlightHoverRow"
    :highlight-current-column="this.props.highlightCurrentColumn"
    :highlight-hover-column="this.props.highlightHoverColumn"
    :show-footer="this.props.showFooter"
    :merge-cells="this.props.mergeCells"
    :merge-footer-items="this.props.mergeFooterItems"
    :span-method="this.props.spanMethod?this.spanMethod:null"
    :footer-span-method="this.props.footerSpanMethod?this.footerSpanMethod:null"
    :show-overflow="this.props.showOverflow"
    :show-header-overflow="this.props.showHeaderOverflow"
    :show-footer-overflow="this.props.showFooterOverflow"
    :empty-text="this.props.emptyText"
    :editRules="this.props.editRules"
    :keep-source="this.props.keepSource"
    :column-config="this.columnConfig"
    :seq-config="this.seqConfig"
    :radio-config="this.radioConfig"
    :checkbox-config="this.checkboxConfig"
    :tooltip-config="this.tooltipConfig"
    :expand-config="this.expandConfig"
    :tree-config="this.treeConfig"
    :edit-config="this.editConfig"
    :valid-config="this.validConfig"
    :row-class-name="this.rowClassName"
    :class-name="this.className"
    :header-row-class-name="this.headerRowClassName"
    :header-cell-class-name="this.headerCellClassName"
    :footer-row-class-name="this.footerRowClassName"
    :footer-cell-class-name="this.footerCellClassName"
    :footer-method="this.footerMethod"
    @keydown="this.keydownHandle"
    @current-change="this.currentChangeHandle"
    @radio-change="this.radioChangeHandle"
    @checkbox-change="this.checkboxChangeHandle"
    @checkbox-all="this.checkboxAllHandle"
    @checkbox-range-start="this.checkboxRangeStartHandle"
    @checkbox-range-change="this.checkboxRangeChangeHandle"
    @checkbox-range-end="this.checkboxRangeEndHandle"
    @cell-click="this.cellClickHandle"
    @cell-dblclick="this.cellDblclickHandle"
    @cell-context-menu="this.cellContextMenuHandle"
    @cell-mouseenter="this.cellMouseenterHandle"
    @cell-mouseleave="this.cellMouseleaveHandle"
    @header-cell-click="this.headerCellClickHandle"
    @header-cell-dblclick="this.headerCellDblclickHandle"
    @header-cell-context-menu="this.headerCellContextMenuHandle"
    @footer-cell-click="this.footerCellClickHandle"
    @footer-cell-dblclick="this.footerCellDblclickHandle"
    @footer-cell-context-menu="this.footerCellContextMenuHandle"
    @sort-change="this.sortChangeHandle"
    @filter-change="this.filterChangeHandle"
    @resizable-change="this.resizableChangeHandle"
    @toggle-row-expand="this.toggleRowExpandHandle"
    @toggle-tree-expand="this.toggleTreeExpandHandle"
    @context-menu-click="this.contextMenuClickHandle"
    @edit-closed="this.editClosedHandle"
    @edit-actived="this.editActivedHandle"
    @edit-disabled="this.editDisabledHandle"
    @valid-error="this.validErrorHandle"
    @scroll="this.scrollHandle"
    @custom="this.customHandle"
  >
    <u-vxe-table-column
      :key="column.key"
      v-for="column in columns"
      :props="column"
      :elementType="props.elementType[column.key]"
      :elementEditType="props.elementEditType[column.key]"
    ></u-vxe-table-column>
  </vxe-table>
</template>

<script>
import CommonUtil from "../../util/CommonUtil";
import ModelUtil from "../../util/ModelUtil";
import ExpressionUtil from "../../util/ExpressionUtil";
import numeral from "numeral";

export default {
  name: "VxeTableComponent",
  props: ["elementIndex", "props", "displayData", "detailList"],
  beforeMount: function () {
    // 自定义行数据唯一主键的字段名
    if (this.props.rowId == null) this.$set(this.props, "rowId", "_XID");
    // 表格的高度；支持铺满父容器或者固定高度，如果设置 auto 为铺满父容器
    if (this.props.height == null) this.$set(this.props, "height", 438);
    // 表格的最大高度
    if (this.props.maxHeight == null) this.$set(this.props, "maxHeight", null);
    // 自动监听父元素的变化去重新计算表格
    if (this.props.resizable == null) this.$set(this.props, "resizable", false);
    // 是否带有斑马纹
    if (this.props.stripe == null) this.$set(this.props, "stripe", false);
    // 是否带有边框
    if (this.props.border == null) this.$set(this.props, "border", "full");
    // 是否为圆角边框
    if (this.props.round == null) this.$set(this.props, "round", false);
    // 表格的尺寸
    if (this.props.size == null) this.$set(this.props, "size", "small");
    // 表格是否显示加载中
    if (this.props.loading == null) this.$set(this.props, "loading", false);
    // 所有的列对齐方式
    if (this.props.align == null) this.$set(this.props, "align", "left");
    // 所有的表头列的对齐方式
    if (this.props.headerAlign == null)
      this.$set(this.props, "headerAlign", this.props.align);
    // 所有的表尾列的对齐方式
    if (this.props.footerAlign == null)
      this.$set(this.props, "footerAlign", this.props.align);
    // 是否显示表头
    if (this.props.showHeader == null)
      this.$set(this.props, "showHeader", true);
    // 是否要高亮当前行
    if (this.props.highlightCurrentRow == null)
      this.$set(this.props, "highlightCurrentRow", false);
    // 鼠标移到行是否要高亮显示
    if (this.props.highlightHoverRow == null)
      this.$set(this.props, "highlightHoverRow", true);
    // 是否要高亮当前列
    if (this.props.highlightCurrentColumn == null)
      this.$set(this.props, "highlightCurrentColumn", false);
    // 鼠标移到行是否要高亮显示
    if (this.props.highlightHoverColumn == null)
      this.$set(this.props, "highlightHoverColumn", false);
    // 是否显示表尾
    if (this.props.showFooter == null)
      this.$set(this.props, "showFooter", false);
    // 临时合并指定的单元格（不能用于树形结构、展开行，不建议用于固定列）
    if (this.props.mergeCells == null)
      this.$set(this.props, "mergeCells", null);
    // 临时合并表尾（不能用于树形结构、展开行，不建议用于固定列）
    if (this.props.mergeFooterItems == null)
      this.$set(this.props, "mergeFooterItems", null);
    // 设置所有内容过长时显示为省略号（如果是固定列建议设置该值，提升渲染速度）
    if (this.props.showOverflow == null)
      this.$set(this.props, "showOverflow", false);
    // 设置表头所有内容过长时显示为省略号
    if (this.props.showHeaderOverflow == null)
      this.$set(this.props, "showHeaderOverflow", false);
    // 设置表尾所有内容过长时显示为省略号
    if (this.props.showFooterOverflow == null)
      this.$set(this.props, "showFooterOverflow", false);
    // 保持原始值的状态，被某些功能所依赖，比如编辑状态、还原数据等（开启后影响性能，具体取决于数据量）
    if (this.props.keepSource == null)
      this.$set(this.props, "keepSource", false);
    // 空数据时显示的内容
    if (this.props.emptyText == null)
      this.$set(this.props, "emptyText", "暂无数据");
    // 校验规则配置项{[field: string]: Array<Object>}
    if (this.props.editRules == null) this.$set(this.props, "editRules", {});
    // 表示方式
    if (this.props.elementType == null)
      this.$set(this.props, "elementType", {});
    // 编辑方式
    if (this.props.elementEditType == null)
      this.$set(this.props, "elementEditType", {});
  },
  mounted: function () {
    // 保存控件实例
    CommonUtil.saveElementToStore(this);
  },
  computed: {
    // 加载中
    tableloading: function () {
      return this.$store.getters.isLoading || this.props.loading;
    },
    // 列的默认参数
    columnConfig: function () {
      var def = {
        // 列的默认宽度
        "min-width": "50px",
      };
      return Object.assign({}, def, this.props.columnConfig);
    },
    // 序号配置项
    seqConfig: function () {
      var def = {
        // 设置序号的起始值
        startIndex: 0,
      };
      var funDef = {};
      // 自定义序号的方法 Function({ row, rowIndex, column, columnIndex }) 返回处理后的值
      if (this.props.seqConfig && this.props.seqConfig.seqMethod) {
        funDef.seqMethod = this.seqConfig_seqMethod;
      }
      return Object.assign({}, def, this.props.seqConfig, funDef);
    },
    // 单选框配置项
    radioConfig: function () {
      var def = {
        // 是否保留勾选状态，对于某些场景下非常有用，比如数据被刷新之后还保留之前选中的状态（需要有 row-id）
        reserve: true,
        // 单选框显示的字段名，可以直接显示在单选框中
        labelField: null,
        // 触发方式
        trigger: "row",
        // 高亮选中行
        highlight: false,
      };
      var funDef = {};
      // 是否允许选中的方法，该方法 Function({row}) 的返回值用来决定这一行的 Radio 是否可以选中
      if (this.props.radioConfig && this.props.radioConfig.checkMethod) {
        funDef.checkMethod = this.radioConfig_checkMethod;
      }
      return Object.assign({}, def, this.props.radioConfig, funDef);
    },
    // 复选框配置项
    checkboxConfig: function () {
      var def = {
        // 复选框显示的字段名，可以直接显示在复选框中
        labelField: null,
        // 绑定选中属性，如果设置了该属性渲染速度更快（建议数据量大时使用，行数据中必须存在该字段，否则无效）
        checkField: null,
        // 是否显示全选按钮（如果 checkStrictly=true 则默认为 false）
        showHeader: true,
        // 默认勾选所有（只会在初始化时被触发一次）
        checkAll: false,
        // 严格模式，当数据为空或全部禁用时，列头的复选框为禁用状态
        strict: false,
        // 触发方式
        trigger: "row",
        // 高亮选中行
        highlight: false,
        // 是否保留勾选状态，对于某些场景可能会用到，比如数据被刷新之后还保留之前选中的状态（需要有 row-id）
        reserve: true,
        // 开启复选框范围选择功能（启用后通过鼠标在复选框的列内滑动选中或取消指定行）
        range: false,
      };
      var funDef = {};
      // 是否允许勾选的方法，该方法 Function({row}) 的返回值用来决定这一行的 checkbox 是否可以勾选
      if (this.props.checkboxConfig && this.props.checkboxConfig.checkMethod) {
        funDef.checkMethod = this.checkboxConfig_checkMethod;
      }
      return Object.assign({}, def, this.props.checkboxConfig, funDef);
    },
    // tooltip 配置项
    tooltipConfig: function () {
      if (this.props.tooltipConfig == null) {
        return null;
      }
      var def = {
        // 所有单元格开启 tooltip 显示
        enabled: false,
        // tooltip 的主题颜色
        theme: "dark",
        // 鼠标是否可进入到 tooltip 中
        enterable: false,
        // 鼠标移出后延时多少才隐藏 tooltip
        leaveDelay: 300,
      };
      var funDef = {};
      // 该方法 Function({items?, row?, rowIndex?, $rowIndex, column, columnIndex, $columnIndex, type, cell, $event}) 接收一个字符串，可以通过返回值来重写默认的提示内容
      if (this.props.tooltipConfig && this.props.tooltipConfig.contentMethod) {
        funDef.contentMethod = this.tooltipConfig_contentMethod;
      }
      return Object.assign({}, def, this.props.tooltipConfig, funDef);
    },
    // 展开行配置项
    expandConfig: function () {
      if (this.props.expandConfig == null) {
        return null;
      }
      var def = {
        // 默认展开所有行（只会在初始化时被触发一次）
        expandAll: false,
        // 每次只能展开一行
        accordion: false,
        // 触发方式
        trigger: "default",
        // 是否使用懒加载
        lazy: false,
        // 是否保留展开状态，对于某些场景可能会用到，比如数据被刷新之后还保留之前展开的状态（需要有 row-id）
        reserve: false,
        // 是否显示图标按钮
        showIcon: true,
      };
      var funDef = {};
      // 该方法 Function({row, rowIndex?, $rowIndex?}) 用于异步加载展开后的内容（必须返回 Promise<any[]> 对象）
      if (this.props.expandConfig && this.props.expandConfig.loadMethod) {
        funDef.loadMethod = this.expandConfig_loadMethod;
      }
      // 该方法 Function({expanded, column, columnIndex, row, rowIndex?}) 在展开或关闭触发之前调用，可以通过返回值来决定是否允许继续执行
      if (this.props.expandConfig && this.props.expandConfig.toggleMethod) {
        funDef.toggleMethod = this.expandConfig_toggleMethod;
      }
      // 该函数 Function({column, columnIndex, row, rowIndex?}) 的返回值用来决定是否允许显示展开按钮
      if (this.props.expandConfig && this.props.expandConfig.visibleMethod) {
        funDef.visibleMethod = this.expandConfig_visibleMethod;
      }
      return Object.assign({}, def, this.props.expandConfig, funDef);
    },
    // 树形结构配置项
    treeConfig: function () {
      if (this.props.treeConfig == null) {
        return null;
      }
      var def = {
        // 树子节点的属性
        children: "children",
        // 树节点的缩进
        indent: 20,
        // 树节点的连接线（启用连接线会降低渲染性能）
        line: false,
        // 默认展开所有子孙树节点（只会在初始化时被触发一次）
        expandAll: false,
        // 对于同一级的节点，每次只能展开一个
        accordion: false,
        // 触发方式
        trigger: "default",
        // 是否使用懒加载（启用后只有指定 hasChild 的节点才允许被点击）
        lazy: false,
        // 只对 lazy 启用后有效，标识是否存在子节点，从而控制是否允许被点击
        hasChild: "hasChild",
        // 是否保留展开状态，对于某些场景可能会用到，比如数据被刷新之后还保留之前展开的状态（需要有 row-id）
        reserve: false,
        // 是否显示图标按钮
        showIcon: true,
      };
      var funDef = {};
      // 该方法 Function({row}) 用于异步加载子节点（必须返回 Promise<any[]> 对象）
      if (this.props.treeConfig && this.props.treeConfig.loadMethod) {
        funDef.loadMethod = this.treeConfig_loadMethod;
      }
      // 该方法 Function({expanded, row, column, columnIndex}) 在展开或关闭触发之前调用，可以通过返回值来决定是否允许继续执行
      if (this.props.treeConfig && this.props.treeConfig.toggleMethod) {
        funDef.toggleMethod = this.treeConfig_toggleMethod;
      }
      return Object.assign({}, def, this.props.treeConfig, funDef);
    },
    // 可编辑配置项
    editConfig: function () {
      var def = {
        // 触发方式
        trigger: "click",
        // 编辑模式
        mode: "row",
        // 是否显示列头编辑图标
        showIcon: false,
        // 只对 keep-source 开启有效是否显示单元格值的修改状态
        showStatus: false,
        // 是否显示必填字段的红色星号
        showAsterisk: true,
        // 当点击非编辑列之后，是否自动清除单元格的激活状态
        autoClear: true,
        // 自定义可编辑列的状态图标
        icon: null,
      };
      var funDef = {};
      // 该方法 Function({row, rowIndex, column, columnIndex}) 的返回值用来决定该单元格是否允许编辑
      if (this.props.editConfig && this.props.editConfig.activeMethod) {
        funDef.activeMethod = this.editConfig_activeMethod;
      }
      return Object.assign({}, def, this.props.editConfig, funDef);
    },
    // 校验配置项
    validConfig: function () {
      var def = {
        // 是否自动定位到校验不通过的单元格
        autoPos: true,
        // 校验提示框的方式
        message: "default",
        // 校验提示框的最大宽度（对于某些特殊场景可能会用到）
        maxWidth: 320,
      };
      return Object.assign({}, def, this.props.validConfig);
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

          if (columnProp.loopItem) {
            // 动态列
            let dataOption = ExpressionUtil.createObject(
              this,
              columnProp.loopItem,
              this.model
            );
            if (Array.isArray(dataOption) == false) {
              continue;
            }

            for (let j=0;j<dataOption.length;j++) {
              columnProp = {};
              if (columnDetail.detailProp) {
                columnProp = JSON.parse(columnDetail.detailProp);
              }
              columnProp.title = dataOption[j][columnProp.loopLabelKey];
              columnProp.key = columnDetail.detailIndex + '.' + j;
              columnProp.field = dataOption[j][columnProp.loopValueKey];

              if (columnDetail.displayType) {
                // 控件属性
                columnProp.elementInfo = {
                  elementIndex: columnDetail.elementIndex,
                  detailIndex: columnDetail.detailIndex,
                  displayType: columnDetail.displayType,
                  displayData: columnProp.field,
                  detailAddon: columnDetail.detailAddon,
                };
              }

              tableColumn.push(columnProp);
            }

          } else {
            columnProp.title = columnDetail.detailName;
            columnProp.key = columnDetail.detailIndex;
            columnProp.field = columnDetail.displayData;

            if (columnDetail.displayType) {
              // 控件属性
              columnProp.elementInfo = {
                elementIndex: columnDetail.elementIndex,
                detailIndex: columnDetail.detailIndex,
                displayType: columnDetail.displayType,
                displayData: columnDetail.displayData,
                detailAddon: columnDetail.detailAddon,
              };
            }

            tableColumn.push(columnProp);
          }
        }
      }
      return tableColumn;
    },
  },
  methods: {
    // 给行附加 className，也可以是函数 Function({row, rowIndex, $rowIndex})
    rowClassName: function (param) {
      if (this.props.rowClassName) {
        return ExpressionUtil.eval(this, this.props.rowClassName, param);
      }
    },
    // 给单元格附加 className，也可以是函数 Function({row, rowIndex, $rowIndex, column, columnIndex, $columnIndex})
    className: function (param) {
      if (this.props.className) {
        return ExpressionUtil.eval(this, this.props.className, param);
      }
    },
    // 给表头的行附加 className，也可以是函数 Function({$rowIndex})
    headerRowClassName: function (param) {
      if (this.props.headerRowClassName) {
        return ExpressionUtil.eval(this, this.props.headerRowClassName, param);
      }
    },
    // 给表头的单元格附加 className，也可以是函数 Function({$rowIndex, column, columnIndex, $columnIndex})
    headerCellClassName: function (param) {
      if (this.props.headerCellClassName) {
        return ExpressionUtil.eval(this, this.props.headerCellClassName, param);
      }
    },
    // 给表尾的行附加 className，也可以是函数 Function({$rowIndex})
    footerRowClassName: function (param) {
      if (this.props.footerRowClassName) {
        return ExpressionUtil.eval(this, this.props.footerRowClassName, param);
      }
    },
    // 给表尾的单元格附加 className，也可以是函数 Function({$rowIndex, column, columnIndex, $columnIndex})
    footerCellClassName: function (param) {
      if (this.props.footerCellClassName) {
        return ExpressionUtil.eval(this, this.props.footerCellClassName, param);
      }
    },
    // 表尾的数据获取方法 Function({columns, data}) 返回一个二维数组
    footerMethod: function (param) {
      if (this.props.footerMethod) {
        return ExpressionUtil.eval(this, this.props.footerMethod, param);
      }
    },
    // 自定义序号的方法 Function({ row, rowIndex, column, columnIndex }) 返回处理后的值
    seqConfig_seqMethod: function (param) {
      return ExpressionUtil.eval(this, this.props.seqConfig.seqMethod, param);
    },
    // 是否允许选中的方法，该方法 Function({row}) 的返回值用来决定这一行的 Radio 是否可以选中
    radioConfig_checkMethod: function (param) {
      return ExpressionUtil.eval(
        this,
        this.props.radioConfig.checkMethod,
        param
      );
    },
    // 是否允许勾选的方法，该方法 Function({row}) 的返回值用来决定这一行的 checkbox 是否可以勾选
    checkboxConfig_checkMethod: function (param) {
      return ExpressionUtil.eval(
        this,
        this.props.checkboxConfig.checkMethod,
        param
      );
    },
    // 该方法 Function({items?, row?, rowIndex?, $rowIndex, column, columnIndex, $columnIndex, type, cell, $event}) 接收一个字符串，可以通过返回值来重写默认的提示内容
    tooltipConfig_contentMethod: function (param) {
      return ExpressionUtil.eval(
        this,
        this.props.tooltipConfig.contentMethod,
        param
      );
    },
    // 该方法 Function({row, rowIndex?, $rowIndex?}) 用于异步加载展开后的内容（必须返回 Promise<any[]> 对象）
    expandConfig_loadMethod: function (param) {
      return ExpressionUtil.eval(
        this,
        this.props.expandConfig.loadMethod,
        param
      );
    },
    // 该方法 Function({expanded, column, columnIndex, row, rowIndex?}) 在展开或关闭触发之前调用，可以通过返回值来决定是否允许继续执行
    expandConfig_toggleMethod: function (param) {
      return ExpressionUtil.eval(
        this,
        this.props.expandConfig.toggleMethod,
        param
      );
    },
    // 该函数 Function({column, columnIndex, row, rowIndex?}) 的返回值用来决定是否允许显示展开按钮
    expandConfig_visibleMethod: function (param) {
      return ExpressionUtil.eval(
        this,
        this.props.expandConfig.visibleMethod,
        param
      );
    },
    // 该方法 Function({row}) 用于异步加载子节点（必须返回 Promise<any[]> 对象）
    treeConfig_loadMethod: function (param) {
      return ExpressionUtil.eval(this, this.props.treeConfig.loadMethod, param);
    },
    // 该方法 Function({expanded, row, column, columnIndex}) 在展开或关闭触发之前调用，可以通过返回值来决定是否允许继续执行
    treeConfig_toggleMethod: function (param) {
      return ExpressionUtil.eval(
        this,
        this.props.treeConfig.toggleMethod,
        param
      );
    },
    // 该方法 Function({row, rowIndex, column, columnIndex}) 的返回值用来决定该单元格是否允许编辑
    editConfig_activeMethod: function (param) {
      return ExpressionUtil.eval(
        this,
        this.props.editConfig.activeMethod,
        param
      );
    },
    // 自定义合并函数，该方法 Function({row, rowIndex, $rowIndex, column, columnIndex, $columnIndex, data}) 返回计算后的值，不能用于树形结构、展开行、固定列
    spanMethod: function (param) {
      return ExpressionUtil.eval(this, this.props.spanMethod, param);
    },
    // 表尾合并行或列，该函数 Function({$rowIndex, column, columnIndex, $columnIndex, data}) 返回计算后的值，不能用于树形结构、展开行、固定列
    footerSpanMethod: function (param) {
      return ExpressionUtil.eval(this, this.props.footerSpanMethod, param);
    },
    // 当表格被激活且键盘被按下时会触发的事件
    keydownHandle: function (param) {
      if (this.props.keydown) {
        ExpressionUtil.eval(this, this.props.keydown, param);
      }
    },
    // 只对 highlightCurrentRow 有效，当手动选中行并且值发生改变时触发的事件
    currentChangeHandle: function (param) {
      if (this.props.currentChange) {
        ExpressionUtil.eval(this, this.props.currentChange, param);
      }
    },
    // 只对 type=radio 有效，当手动勾选并且值发生改变时触发的事件
    radioChangeHandle: function (param) {
      if (this.props.radioChange) {
        ExpressionUtil.eval(this, this.props.radioChange, param);
      }
    },
    // 只对 type=checkbox 有效，当手动勾选并且值发生改变时触发的事件
    checkboxChangeHandle: function (param) {
      if (this.props.checkboxChange) {
        ExpressionUtil.eval(this, this.props.checkboxChange, param);
      }
    },
    // 只对 type=checkbox 有效，当手动勾选全选时触发的事件
    checkboxAllHandle: function (param) {
      if (this.props.checkboxAll) {
        ExpressionUtil.eval(this, this.props.checkboxAll, param);
      }
    },
    // 只对 checkbox-config.range 有效，当鼠标范围选择开始时会触发的事件
    checkboxRangeStartHandle: function (param) {
      if (this.props.checkboxRangeStart) {
        ExpressionUtil.eval(this, this.props.checkboxRangeStart, param);
      }
    },
    // 只对 checkbox-config.range 有效，当鼠标范围选择内的行数发生变化时会触发的事件
    checkboxRangeChangeHandle: function (param) {
      if (this.props.checkboxRangeChange) {
        ExpressionUtil.eval(this, this.props.checkboxRangeChange, param);
      }
    },
    // 只对 checkbox-config.range 有效，当鼠标范围选择结束时会触发的事件
    checkboxRangeEndHandle: function (param) {
      if (this.props.checkboxRangeEnd) {
        ExpressionUtil.eval(this, this.props.checkboxRangeEnd, param);
      }
    },
    // 单元格被点击时会触发该事件
    cellClickHandle: function (param) {
      if (this.props.cellClick) {
        ExpressionUtil.eval(this, this.props.cellClick, param);
      }
    },
    // 单元格被双击时会触发该事件
    cellDblclickHandle: function (param) {
      if (this.props.cellDblclick) {
        ExpressionUtil.eval(this, this.props.cellDblclick, param);
      }
    },
    // 只对 context-menu 配置时有效，单元格被鼠标右键时触发该事件
    cellContextMenuHandle: function (param) {
      if (this.props.cellContextMenu) {
        ExpressionUtil.eval(this, this.props.cellContextMenu, param);
      }
    },
    // 当单元格 hover 进入时会触发该事件
    cellMouseenterHandle: function (param) {
      if (this.props.cellMouseenter) {
        ExpressionUtil.eval(this, this.props.cellMouseenter, param);
      }
    },
    // 当单元格 hover 退出时会触发该事件
    cellMouseleaveHandle: function (param) {
      if (this.props.cellMouseleave) {
        ExpressionUtil.eval(this, this.props.cellMouseleave, param);
      }
    },
    // 表头单元格被点击时会触发该事件
    headerCellClickHandle: function (param) {
      if (this.props.headerCellClick) {
        ExpressionUtil.eval(this, this.props.headerCellClick, param);
      }
    },
    // 表头单元格被双击时会触发该事件
    headerCellDblclickHandle: function (param) {
      if (this.props.headerCellDblclick) {
        ExpressionUtil.eval(this, this.props.headerCellDblclick, param);
      }
    },
    // 只对 context-menu 配置时有效，表头单元格被鼠标右键时触发该事件
    headerCellContextMenuHandle: function (param) {
      if (this.props.headerCellContextMenu) {
        ExpressionUtil.eval(this, this.props.headerCellContextMenu, param);
      }
    },
    // 表尾单元格被点击时会触发该事件
    footerCellClickHandle: function (param) {
      if (this.props.footerCellClick) {
        ExpressionUtil.eval(this, this.props.footerCellClick, param);
      }
    },
    // 表尾单元格被双击时会触发该事件
    footerCellDblclickHandle: function (param) {
      if (this.props.footerCellDblclick) {
        ExpressionUtil.eval(this, this.props.footerCellDblclick, param);
      }
    },
    // 只对 context-menu 配置时有效，表尾单元格被鼠标右键时触发该事件
    footerCellContextMenuHandle: function (param) {
      if (this.props.footerCellContextMenu) {
        ExpressionUtil.eval(this, this.props.footerCellContextMenu, param);
      }
    },
    // 当排序条件发生变化时会触发该事件
    sortChangeHandle: function (param) {
      if (this.props.sortChange) {
        ExpressionUtil.eval(this, this.props.sortChange, param);
      }
    },
    // 当筛选条件发生变化时会触发该事件
    filterChangeHandle: function (param) {
      if (this.props.filterChange) {
        ExpressionUtil.eval(this, this.props.filterChange, param);
      }
    },
    // 当列宽拖动发生变化时会触发该事件
    resizableChangeHandle: function (param) {
      if (this.props.resizableChange) {
        ExpressionUtil.eval(this, this.props.resizableChange, param);
      }
    },
    // 当行展开或收起时会触发该事件
    toggleRowExpandHandle: function (param) {
      if (this.props.toggleRowExpand) {
        ExpressionUtil.eval(this, this.props.toggleRowExpand, param);
      }
    },
    // 当树节点展开或收起时会触发该事件
    toggleTreeExpandHandle: function (param) {
      if (this.props.toggleTreeExpand) {
        ExpressionUtil.eval(this, this.props.toggleTreeExpand, param);
      }
    },
    // 只对 context-menu 配置时有效，当点击快捷菜单时会触发该事件
    contextMenuClickHandle: function (param) {
      if (this.props.contextMenuClick) {
        ExpressionUtil.eval(this, this.props.contextMenuClick, param);
      }
    },
    // 只对 edit-config 配置时有效，单元格编辑状态下被关闭时会触发该事件
    editClosedHandle: function (param) {
      if (this.props.editClosed) {
        ExpressionUtil.eval(this, this.props.editClosed, param);
      }
    },
    // 只对 edit-config 配置时有效，单元格被激活编辑时会触发该事件
    editActivedHandle: function (param) {
      if (this.props.editActived) {
        ExpressionUtil.eval(this, this.props.editActived, param);
      }
    },
    // 只对 edit-config 配置时有效，当单元格激活时如果是禁用状态时会触发该事件
    editDisabledHandle: function (param) {
      if (this.props.editDisabled) {
        ExpressionUtil.eval(this, this.props.editDisabled, param);
      }
    },
    // 只对 edit-rules 配置时有效，当数据校验不通过时会触发该事件
    validErrorHandle: function (param) {
      if (this.props.validError) {
        ExpressionUtil.eval(this, this.props.validError, param);
      }
    },
    // 表格滚动时会触发该事件
    scrollHandle: function (param) {
      if (this.props.scroll) {
        ExpressionUtil.eval(this, this.props.scroll, param);
      }
    },
    // 如果与工具栏关联，在自定义列按钮被手动点击后会触发该事件
    customHandle: function (param) {
      if (this.props.custom) {
        ExpressionUtil.eval(this, this.props.custom, param);
      }
    },
  },
};
</script>