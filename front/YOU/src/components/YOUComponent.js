import Dispatcher from './core/Dispatcher';
import MenuComponent from './core/MenuComponent';
import LocaleComponent from './core/LocaleComponent';
import PageTitleComponent from './core/PageTitleComponent';
import RowComponent from './core/RowComponent';
import CellComponent from './core/CellComponent';
import FunctionComponent from './core/FunctionComponent';

import EmptyComponent from './base/EmptyComponent';
import InputComponent from './base/InputComponent';
import LabelComponent from './base/LabelComponent';
import ButtonComponent from './base/ButtonComponent';
import PageComponent from './base/PageComponent';
import SelectComponent from './base/SelectComponent';
import RadioComponent from './base/RadioComponent';
import CheckboxComponent from './base/CheckboxComponent';
import CheckboxGroupComponent from './base/CheckboxGroupComponent';
import DateComponent from './base/DateComponent';
import TimePickerComponent from './base/TimePickerComponent';
import IconComponent from './base/IconComponent';
import StepComponent from './base/StepComponent';
import AlertComponent from './base/AlertComponent';
import DividerComponent from './base/DividerComponent';
import NumberComponent from './base/NumberComponent';
import TabComponent from './base/TabComponent';
import TransferComponent from './base/TransferComponent';
import ImageComponent from './base/ImageComponent';
import SliderComponent from './base/SliderComponent';
import CascaderComponent from './base/CascaderComponent';
import DateLabelComponent from './base/DateLabelComponent';
import AnchorComponent from './base/AnchorComponent';
import CircleComponent from './base/CircleComponent';
import TreeComponent from './base/TreeComponent';
import I18nInputComponent from './base/I18nInputComponent';

import FormComponent from './pack/FormComponent';
import TableComponent from './pack/TableComponent';
import CardCellComponent from './pack/CardCellComponent';
import UploadComponent from './pack/UploadComponent';
import TagGroupComponent from './pack/TagGroupComponent';
import ListComponent from './pack/ListComponent';
import PoptipComponent from './pack/PoptipComponent';
import VxeTableComponent from './pack/VxeTableComponent';
import VxeTableColumnComponent from './pack/VxeTableColumnComponent';

import LineChartComponent from './chart/LineChartComponent';
import HistogramChartComponent from './chart/HistogramChartComponent';
import BarChartComponent from './chart/BarChartComponent';
import PieChartComponent from './chart/PieChartComponent';
import RingChartComponent from './chart/RingChartComponent';
import WaterfallChartComponent from './chart/WaterfallChartComponent';
import FunnelChartComponent from './chart/FunnelChartComponent';
import RadarChartComponent from './chart/RadarChartComponent';
import MapChartComponent from './chart/MapChartComponent';
import SankeyChartComponent from './chart/SankeyChartComponent';
import HeatmapChartComponent from './chart/HeatmapChartComponent';
import ScatterChartComponent from './chart/ScatterChartComponent';
import CandleChartComponent from './chart/CandleChartComponent';
import GaugeChartComponent from './chart/GaugeChartComponent';
import TreeChartComponent from './chart/TreeChartComponent';
import LiquidFillChartComponent from './chart/LiquidFillChartComponent';
import WordCloudChartComponent from './chart/WordCloudChartComponent';

import BPMNComponent from './spec/BPMNComponent';
import OrgTreeComponent from './spec/OrgTreeComponent';
import ResourceComponent from './spec/ResourceComponent';
import CityRegionComponent from './spec/CityRegionComponent';

/**
 * YOU组件注册
 */
const YOUComponent = {
    install(Vue) {
        // 核心组件
        Vue.component("u-dispatcher", Dispatcher);
        Vue.component("u-menu", MenuComponent);
        Vue.component("u-locale", LocaleComponent);
        Vue.component("u-page-title", PageTitleComponent);
        Vue.component("u-row", RowComponent);
        Vue.component("u-cell", CellComponent);
        Vue.component("u-function", FunctionComponent);
        // 基本组件
        Vue.component("u-empty", EmptyComponent);
        Vue.component("u-input", InputComponent);
        Vue.component("u-label", LabelComponent);
        Vue.component("u-button", ButtonComponent);
        Vue.component("u-paging", PageComponent);
        Vue.component("u-select", SelectComponent);
        Vue.component("u-radio", RadioComponent);
        Vue.component("u-checkbox", CheckboxComponent);
        Vue.component("u-checkbox-group", CheckboxGroupComponent);
        Vue.component("u-date", DateComponent);
        Vue.component("u-time", TimePickerComponent);
        Vue.component("u-icon", IconComponent);
        Vue.component("u-step", StepComponent);
        Vue.component("u-alert", AlertComponent);
        Vue.component("u-divider", DividerComponent);
        Vue.component("u-number", NumberComponent);
        Vue.component("u-tab", TabComponent);
        Vue.component("u-transfer", TransferComponent);
        Vue.component("u-image", ImageComponent);
        Vue.component("u-slider", SliderComponent);
        Vue.component("u-cascader", CascaderComponent);
        Vue.component("u-date-label", DateLabelComponent);
        Vue.component("u-anchor", AnchorComponent);
		Vue.component("u-circle", CircleComponent);
        Vue.component("u-tree", TreeComponent);
        Vue.component("u-i18n-input", I18nInputComponent);
        // 容器组件
        Vue.component("u-form", FormComponent);
        Vue.component("u-table", TableComponent);
        Vue.component("u-card-cell", CardCellComponent);
        Vue.component("u-upload", UploadComponent);
        Vue.component("u-tag-group", TagGroupComponent);
        Vue.component("u-list", ListComponent);
        Vue.component("u-poptip", PoptipComponent);
        Vue.component("u-vxe-table", VxeTableComponent);
        Vue.component("u-vxe-table-column", VxeTableColumnComponent);
        // 图表组件
        Vue.component("u-chart-line", LineChartComponent);
        Vue.component("u-chart-histogram", HistogramChartComponent);
        Vue.component("u-chart-bar", BarChartComponent);
        Vue.component("u-chart-pie", PieChartComponent);
        Vue.component("u-chart-ring", RingChartComponent);
        Vue.component("u-chart-waterfall", WaterfallChartComponent);
        Vue.component("u-chart-funnel", FunnelChartComponent);
        Vue.component("u-chart-radar", RadarChartComponent);
        Vue.component("u-chart-map", MapChartComponent);
        Vue.component("u-chart-sankey", SankeyChartComponent);
        Vue.component("u-chart-heatmap", HeatmapChartComponent);
        Vue.component("u-chart-scatter", ScatterChartComponent);
        Vue.component("u-chart-candle", CandleChartComponent);
        Vue.component("u-chart-gauge", GaugeChartComponent);
        Vue.component("u-chart-tree", TreeChartComponent);
        Vue.component("u-chart-liquidfill", LiquidFillChartComponent);
        Vue.component("u-chart-wordcloud", WordCloudChartComponent);
        // 特殊组件
        Vue.component("u-bpmn", BPMNComponent);
        Vue.component("u-org-tree", OrgTreeComponent);
        Vue.component("u-resource", ResourceComponent);
        Vue.component("u-city", CityRegionComponent);
    }
};

export default YOUComponent;