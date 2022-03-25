<template>
  <Select
    ref="elementRef"
    v-model="dataValue"
    :class="this.props.className"
    :style="this.props.style"
    :multiple="this.props.multiple"
    :disabled="disabled"
    :clearable="this.props.clearable"
    :filterable="this.props.filterable"
    :filter-by-label="this.props.filterByLabel"
    :remote="this.props.remote"
    :remote-method="this.props.remoteMethod"
    :loading="this.props.loading"
    :loading-text="this.props.loadingText"
    :label="this.props.label"
    :size="this.props.size"
    :placeholder="this.props.placeholder"
    :not-found-text="this.props.notFoundText"
    :label-in-value="this.props.labelInValue"
    :placement="this.props.placement"
    :transfer="this.props.transfer"
    :element-id="this.props.elementId"
    :transfer-class-name="this.props.transferClassName"
    :prefix="this.props.prefix"
    :max-tag-count="this.props.maxTagCount"
    :max-tag-placeholder="this.props.maxTagPlaceholder"
    :allow-create="this.props.allowCreate"
    :capture="this.props.capture"
    @on-change="changeHandle"
    @on-query-change="queryChangeHandle"
    @on-clear="clearHandle"
    @on-open-change="openChangeHandle"
    @on-create="createHandle"
  >
    <Option
      v-for="(option, index) in options"
      :value="String(option[props.valueKey]?option[props.valueKey]:option)"
      :key="index"
      :disabled="props.disabledKey?option[props.disabledKey]:false"
      :label="props.i18n?$t(option[props.labelKey]):option[props.labelKey]"
    ></Option>
  </Select>
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
    // 是否支持多选
    if (this.props.multiple == null) this.$set(this.props, "multiple", false);
    // 是否禁用
    if (this.props.disabled == null) this.$set(this.props, "disabled", false);
    // 是否可以清空选项，只在单选时有效
    if (this.props.clearable == null) this.$set(this.props, "clearable", true);
    // 是否支持搜索
    if (this.props.filterable == null)
      this.$set(this.props, "filterable", false);
    // 在搜索时，是否只按照 label 进行搜索
    if (this.props.filterByLabel == null)
      this.$set(this.props, "filterByLabel", true);
    // 是否使用远程搜索
    if (this.props.remote == null) this.$set(this.props, "remote", false);
    // 远程搜索的方法
    if (this.props.remoteMethod == null)
      this.$set(this.props, "remoteMethod", null);
    // 当前是否正在远程搜索
    if (this.props.loading == null) this.$set(this.props, "loading", false);
    // 远程搜索中的文字提示
    if (this.props.loadingText == null)
      this.$set(this.props, "loadingText", "加载中");
    // 仅在 remote 模式下，初始化时使用。因为仅通过 value 无法得知选项的 label，需手动设置。
    if (this.props.label == null) this.$set(this.props, "label", "");
    // 选择框大小，可选值为large、small、default或者不填
    if (this.props.size == null) this.$set(this.props, "size", "default");
    // 选择框默认文字
    if (this.props.placeholder == null)
      this.$set(this.props, "placeholder", "请选择");
    // 当下拉列表为空时显示的内容
    if (this.props.notFoundText == null)
      this.$set(this.props, "notFoundText", "无匹配数据");
    // 在返回选项时，是否将 label 和 value 一并返回，默认只返回 value
    if (this.props.labelInValue == null)
      this.$set(this.props, "labelInValue", true);
    // 弹窗的展开方向，可选值为 top、bottom、top-start、bottom-start、top-end、bottom-end
    if (this.props.placement == null)
      this.$set(this.props, "placement", "bottom-start");
    // 是否将弹层放置于 body 内，在 Tabs、带有 fixed 的 Table 列内使用时，建议添加此属性，它将不受父级样式影响，从而达到更好的效果
    if (this.props.transfer == null) this.$set(this.props, "transfer", false);
    // 给表单元素设置 id，详见 Form 用法。
    if (this.props.elementId == null) this.$set(this.props, "elementId", null);
    // 开启 transfer 时，给浮层添加额外的 class 名称
    if (this.props.transferClassName == null)
      this.$set(this.props, "transferClassName", null);
    // 在 Select 内显示图标
    if (this.props.prefix == null) this.$set(this.props, "prefix", null);
    // 多选时最多显示多少个 tag
    if (this.props.maxTagCount == null)
      this.$set(this.props, "maxTagCount", 99);
    // 隐藏 tag 时显示的内容，参数是剩余项数量
    if (this.props.maxTagPlaceholder == null)
      this.$set(this.props, "maxTagPlaceholder", null);
    // 是否允许用户创建新条目，需开启 filterable
    if (this.props.allowCreate == null)
      this.$set(this.props, "allowCreate", false);
    // 多语言
    if (this.props.i18n == null) this.$set(this.props, "i18n", true);
    // 是否开启 capture 模式，也可通过全局配置
    if (this.props.capture == null) this.$set(this.props, "capture", true);
    if (this.props.valueKey == null) this.$set(this.props, "valueKey", "0");
    if (this.props.labelKey == null) this.$set(this.props, "labelKey", "1");
    if (this.props.disabledKey == null) this.$set(this.props, "disabledKey", null);
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
      return ModelUtil.getModelKey(this, this.displayData, "");
    },
    dataValue: {
      get: function() {
        return String(this.model[this.modelKey]);
      },
      set: function(val) {
        if (val == null) {
          val = "";
        }
        this.$set(this.model, this.modelKey, val);
      }
    },
    options: function() {
      if (typeof this.props.options == "string") {
        // 动态选项
        let dataOption = ExpressionUtil.createObject(
          this,
          this.props.options,
          this.model
        );
        if (Array.isArray(dataOption) == false) {
          return [];
        }
        if (dataOption.length == 0) {
          this.$set(this.model, this.modelKey, "");
        }
        return dataOption;
      } else {
        // 固定选项
        return this.props.options;
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
    changeHandle: function(data) {
      // 选中的Option变化时触发，默认返回 value，如需返回 label，详见 label-in-value 属性
      if (this.props.onChange) {
        ExpressionUtil.eval(this, this.props.onChange, this.detailModel, data);
      }
    },
    queryChangeHandle: function() {
      // 搜索词改变时触发
      if (this.props.onQueryChange) {
        ExpressionUtil.eval(this, this.props.onQueryChange, this.detailModel);
      }
    },
    clearHandle: function() {
      // 点击清空按钮时触发
      if (this.props.onClear) {
        ExpressionUtil.eval(this, this.props.onClear, this.detailModel);
      }
    },
    openChangeHandle: function() {
      // 下拉框展开或收起时触发
      if (this.props.onOpenChange) {
        ExpressionUtil.eval(this, this.props.onOpenChange, this.detailModel);
      }
    },
    createHandle: function(val) {
      // 新建条目时触发
      if (this.props.onCreate) {
        ExpressionUtil.eval(this, this.props.onCreate, val, this.detailModel);
      }
    }
  }
};
</script>
