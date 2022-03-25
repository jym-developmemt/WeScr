<template>
  <ve-bar
    ref="elementRef"
    :class="this.props.className"
    :style="this.props.style"
    :data="chartData"
    :height="this.props.height"
    :settings="this.props.settings"
    :extend="this.props.extend"
    :events="chartEvents"
    :loading="this.$store.getters.isLoading"
    :tooltip-visible="this.$store.getters.tooltipVisible"
    :legend-visible="this.$store.getters.legendVisible"
  ></ve-bar>
</template>

<script>
import CommonUtil from "../../util/CommonUtil";
import ModelUtil from "../../util/ModelUtil";
import ExpressionUtil from "../../util/ExpressionUtil";
import jsonFormat from "json-format";

export default {
  name: "BarChartComponent",
  props: ["elementIndex", "props", "displayData", "detailIndex", "rowIndex", "detailModel"],

  data() {
    var me = this;

    var chartEvents = {};

    if (this.props.onClick) {
      chartEvents.click = this.clickHandle;
    }
    if (this.props.onDblclick) {
      chartEvents.dblclick = this.dblclickHandle;
    }
    if (this.props.onMouseDown) {
      chartEvents.mousedown = this.mouseDownHandle;
    }
    if (this.props.onMouseMove) {
      chartEvents.mousemove = this.mouseMoveHandle;
    }
    if (this.props.onMouseUp) {
      chartEvents.mouseup = this.mouseUpHandle;
    }
    if (this.props.onMouseOver) {
      chartEvents.mouseover = this.mouseOverHandle;
    }
    if (this.props.onMouseOut) {
      chartEvents.mouseout = this.mouseOutHandle;
    }
    if (this.props.onGlobalOut) {
      chartEvents.globalout = this.globalOutHandle;
    }
    if (this.props.onContextmenu) {
      chartEvents.contextmenu = this.contextmenuHandle;
    }
    if (this.props.onLegendSelectChanged) {
      chartEvents.legendselectchanged = this.legendSelectChangedHandle;
    }
    if (this.props.onLegendSelected) {
      chartEvents.legendselected = this.legendSelectedHandle;
    }
    if (this.props.onLegendUnselected) {
      chartEvents.legendunselected = this.legendUnselectedHandle;
    }
    if (this.props.onLegendScroll) {
      chartEvents.legendscroll = this.legendScrollHandle;
    }
    if (this.props.onDataZoom) {
      chartEvents.datazoom = this.dataZoomHandle;
    }
    if (this.props.onDataRangeSelected) {
      chartEvents.datarangeselected = this.dataRangeSelectedHandle;
    }
    if (this.props.onTimelineChanged) {
      chartEvents.timelinechanged = this.timelineChangedHandle;
    }
    if (this.props.onTimelinePlayChanged) {
      chartEvents.timelineplaychanged = this.timelinePlayChangedHandle;
    }
    if (this.props.onRestore) {
      chartEvents.restore = this.restoreHandle;
    }
    if (this.props.onDataviewChanged) {
      chartEvents.dataviewchanged = this.dataviewChangedHandle;
    }
    if (this.props.onMagicTypeChanged) {
      chartEvents.magictypechanged = this.magicTypeChangedHandle;
    }
    if (this.props.onGeoSelectChanged) {
      chartEvents.geoselectchanged = this.geoSelectChangedHandle;
    }
    if (this.props.onGeoSelected) {
      chartEvents.geoselected = this.geoSelectedHandle;
    }
    if (this.props.onGeoUnselected) {
      chartEvents.geounselected = this.geoUnselectedHandle;
    }
    if (this.props.onPieSelectChanged) {
      chartEvents.pieselectchanged = this.pieSelectChangedHandle;
    }
    if (this.props.onPieSelected) {
      chartEvents.pieselected = this.pieSelectedHandle;
    }
    if (this.props.onPieUnselected) {
      chartEvents.pieunselected = this.pieUnselectedHandle;
    }
    if (this.props.onMapSelectChanged) {
      chartEvents.mapselectchanged = this.mapSelectChangedHandle;
    }
    if (this.props.onMapSelected) {
      chartEvents.mapselected = this.mapSelectedHandle;
    }
    if (this.props.onMapUnselected) {
      chartEvents.mapunselected = this.mapUnselectedHandle;
    }
    if (this.props.onAxisAreaSelected) {
      chartEvents.axisareaselected = this.axisAreaSelectedHandle;
    }
    if (this.props.onFocusNodeAdjacency) {
      chartEvents.focusnodeadjacency = this.focusNodeAdjacencyHandle;
    }
    if (this.props.onUnfocusNodeAdjacency) {
      chartEvents.unfocusnodeadjacency = this.unfocusNodeAdjacencyHandle;
    }
    if (this.props.onBrush) {
      chartEvents.brush = this.brushHandle;
    }
    if (this.props.onBrushselected) {
      chartEvents.brushselected = this.brushSelectedHandle;
    }
    if (this.props.onGlobalCursorTaken) {
      chartEvents.globalcursortaken = this.globalCursorTakenHandle;
    }
    if (this.props.onRendered) {
      chartEvents.rendered = this.renderedHandle;
    }
    if (this.props.onFinished) {
      chartEvents.finished = this.finishedHandle;
    }

    return {
      chartEvents: chartEvents
    };
  },

  beforeMount: function() {
    // ClassName
    if (this.props.className == null) this.$set(this.props, "className", "");
    // 样式
    if (this.props.style == null) this.$set(this.props, "style", null);
    // 图表高度
    if (this.props.height == null) this.$set(this.props, "height", "300px");
    // 隐藏提示框
    if (this.props.tooltipVisible == null) this.$set(this.props, "tooltipVisible", true);
    // 隐藏图例
    if (this.props.legendVisible == null) this.$set(this.props, "legendVisible", true);
    // 图表列信息
    if (this.props.columns == null) this.$set(this.props, "columns", []);
    // 图表设定
    if (this.props.settings == null) this.$set(this.props, "settings", {});
    // 图表附加
    if (this.props.extend == null) this.$set(this.props, "extend", {});
  },
  mounted: function() {
    // 保存控件实例
    CommonUtil.saveElementToStore(this);
  },
  computed: {
    chartData: function() {
      return {
        columns: this.props.columns,
        rows: this.model[this.modelKey]
      };
    },
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
    }
  },
  methods: {
    clickHandle: function(e) {
      if (this.props.onClick) {
        ExpressionUtil.eval(this, this.props.onClick, e);
      }
    },
    dblclickHandle: function(e) {
      if (this.props.onDblclick) {
        ExpressionUtil.eval(this, this.props.onDblclick, e);
      }
    },
    mouseDownHandle: function(e) {
      if (this.props.onMouseDown) {
        ExpressionUtil.eval(this, this.props.onMouseDown, e);
      }
    },
    mouseMoveHandle: function(e) {
      if (this.props.onMouseMove) {
        ExpressionUtil.eval(this, this.props.onMouseMove, e);
      }
    },
    mouseUpHandle: function(e) {
      if (this.props.onMouseUp) {
        ExpressionUtil.eval(this, this.props.onMouseUp, e);
      }
    },
    mouseOverHandle: function(e) {
      if (this.props.onMouseOver) {
        ExpressionUtil.eval(this, this.props.onMouseOver, e);
      }
    },
    mouseOutHandle: function(e) {
      if (this.props.onMouseOut) {
        ExpressionUtil.eval(this, this.props.onMouseOut, e);
      }
    },
    globalOutHandle: function(e) {
      if (this.props.onGlobalOut) {
        ExpressionUtil.eval(this, this.props.onGlobalOut, e);
      }
    },
    contextmenuHandle: function(e) {
      if (this.props.onContextmenu) {
        ExpressionUtil.eval(this, this.props.onContextmenu, e);
      }
    },
    legendSelectChangedHandle: function(e) {
      if (this.props.onLegendSelectChanged) {
        ExpressionUtil.eval(this, this.props.onLegendSelectChanged, e);
      }
    },
    legendSelectedHandle: function(e) {
      if (this.props.onLegendSelected) {
        ExpressionUtil.eval(this, this.props.onLegendSelected, e);
      }
    },
    legendUnselectedHandle: function(e) {
      if (this.props.onLegendUnselected) {
        ExpressionUtil.eval(this, this.props.onLegendUnselected, e);
      }
    },
    legendScrollHandle: function(e) {
      if (this.props.onLegendScroll) {
        ExpressionUtil.eval(this, this.props.onLegendScroll, e);
      }
    },
    dataZoomHandle: function(e) {
      if (this.props.onDataZoom) {
        ExpressionUtil.eval(this, this.props.onDataZoom, e);
      }
    },
    dataRangeSelectedHandle: function(e) {
      if (this.props.onDataRangeSelected) {
        ExpressionUtil.eval(this, this.props.onDataRangeSelected, e);
      }
    },
    timelineChangedHandle: function(e) {
      if (this.props.onTimelineChanged) {
        ExpressionUtil.eval(this, this.props.onTimelineChanged, e);
      }
    },
    timelinePlayChangedHandle: function(e) {
      if (this.props.onTimelinePlayChanged) {
        ExpressionUtil.eval(this, this.props.onTimelinePlayChanged, e);
      }
    },
    restoreHandle: function(e) {
      if (this.props.onRestore) {
        ExpressionUtil.eval(this, this.props.onRestore, e);
      }
    },
    dataviewChangedHandle: function(e) {
      if (this.props.onDataviewChanged) {
        ExpressionUtil.eval(this, this.props.onDataviewChanged, e);
      }
    },
    magicTypeChangedHandle: function(e) {
      if (this.props.onMagicTypeChanged) {
        ExpressionUtil.eval(this, this.props.onMagicTypeChanged, e);
      }
    },
    geoSelectChangedHandle: function(e) {
      if (this.props.onGeoSelectChanged) {
        ExpressionUtil.eval(this, this.props.onGeoSelectChanged, e);
      }
    },
    geoSelectedHandle: function(e) {
      if (this.props.onGeoSelected) {
        ExpressionUtil.eval(this, this.props.onGeoSelected, e);
      }
    },
    geoUnselectedHandle: function(e) {
      if (this.props.onGeoUnselected) {
        ExpressionUtil.eval(this, this.props.onGeoUnselected, e);
      }
    },
    pieSelectChangedHandle: function(e) {
      if (this.props.onPieSelectChanged) {
        ExpressionUtil.eval(this, this.props.onPieSelectChanged, e);
      }
    },
    pieSelectedHandle: function(e) {
      if (this.props.onPieSelected) {
        ExpressionUtil.eval(this, this.props.onPieSelected, e);
      }
    },
    pieUnselectedHandle: function(e) {
      if (this.props.onPieUnselected) {
        ExpressionUtil.eval(this, this.props.onPieUnselected, e);
      }
    },
    mapSelectChangedHandle: function(e) {
      if (this.props.onMapSelectChanged) {
        ExpressionUtil.eval(this, this.props.onMapSelectChanged, e);
      }
    },
    mapSelectedHandle: function(e) {
      if (this.props.onMapSelected) {
        ExpressionUtil.eval(this, this.props.onMapSelected, e);
      }
    },
    mapUnselectedHandle: function(e) {
      if (this.props.onMapUnselected) {
        ExpressionUtil.eval(this, this.props.onMapUnselected, e);
      }
    },
    axisAreaSelectedHandle: function(e) {
      if (this.props.onAxisAreaSelected) {
        ExpressionUtil.eval(this, this.props.onAxisAreaSelected, e);
      }
    },
    focusNodeAdjacencyHandle: function(e) {
      if (this.props.onFocusNodeAdjacency) {
        ExpressionUtil.eval(this, this.props.onFocusNodeAdjacency, e);
      }
    },
    unfocusNodeAdjacencyHandle: function(e) {
      if (this.props.onUnfocusNodeAdjacency) {
        ExpressionUtil.eval(this, this.props.onUnfocusNodeAdjacency, e);
      }
    },
    brushHandle: function(e) {
      if (this.props.onBrush) {
        ExpressionUtil.eval(this, this.props.onBrush, e);
      }
    },
    brushSelectedHandle: function(e) {
      if (this.props.onBrushselected) {
        ExpressionUtil.eval(this, this.props.onBrushselected, e);
      }
    },
    globalCursorTakenHandle: function(e) {
      if (this.props.onGlobalCursorTaken) {
        ExpressionUtil.eval(this, this.props.onGlobalCursorTaken, e);
      }
    },
    renderedHandle: function(e) {
      if (this.props.onRendered) {
        ExpressionUtil.eval(this, this.props.onRendered, e);
      }
    },
    finishedHandle: function(e) {
      if (this.props.onFinished) {
        ExpressionUtil.eval(this, this.props.onFinished, e);
      }
    }
  }
};
</script>
