<template>
  <div>
    <Cascader
      ref="elementRef"
      v-model="dataValue"
      :class="this.props.className"
      :style="this.props.style"
      :data="options"
      :render-format="renderFormat"
      :disabled="disabled"
      :clearable="this.props.clearable"
      :placeholder="this.props.placeholder"
      :trigger="this.props.trigger"
      :change-on-select="this.props.changeOnSelect"
      :size="this.props.size"
      :load-data="loadData"
      :filterable="this.props.filterable"
      :not-found-text="this.props.notFoundText"
      :transfer="this.props.transfer"
      :element-id="this.props.elementId"
      @on-change="changeHandle"
      @on-visible-change="visibleChangeHandle"
    ></Cascader>
  </div>
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
    // 是否禁用
    if (this.props.disabled == null) this.$set(this.props, "disabled", false);
    // 是否支持清除
    if (this.props.clearable == null) this.$set(this.props, "clearable", true);
    // 选择框默认文字
    if (this.props.placeholder == null)
      this.$set(this.props, "placeholder", "请选择");
    // 次级菜单展开方式，可选值为 click 或 hover
    if (this.props.trigger == null) this.$set(this.props, "trigger", "click");
    // 当此项为 true 时，点选每级菜单选项值都会发生变化，具体见上面的示例
    if (this.props.changeOnSelect == null)
      this.$set(this.props, "changeOnSelect", true);
    // 输入框大小，可选值为large和small或者不填
    if (this.props.size == null) this.$set(this.props, "size", null);
    // 是否支持搜索
    if (this.props.filterable == null)
      this.$set(this.props, "filterable", false);
    // 当搜索列表为空时显示的内容
    if (this.props.notFoundText == null)
      this.$set(this.props, "notFoundText", "无匹配数据");
    // 是否将弹层放置于 body 内，在 Tabs、带有 fixed 的 Table 列内使用时，建议添加此属性，它将不受父级样式影响，从而达到更好的效果
    if (this.props.transfer == null) this.$set(this.props, "transfer", false);
    // 给表单元素设置 id，详见 Form 用法。
    if (this.props.elementId == null) this.$set(this.props, "elementId", null);
    // 多语言
    if (this.props.i18n == null) this.$set(this.props, "i18n", true);
    // 值类型
    if (this.props.valueType == null) this.$set(this.props, "valueType", "0");
    if (this.props.valueKey == null) this.$set(this.props, "valueKey", "0");
    if (this.props.labelKey == null) this.$set(this.props, "labelKey", "1");
    if (this.props.displayRoot == null)
      this.$set(this.props, "displayRoot", false);
  },
  mounted: function() {
    // 保存控件实例
    CommonUtil.saveElementToStore(this);
  },
  data() {
    return {
      valueMap: {},
      saveOptions: []
    };
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
      // getter
      get: function() {
        if (this.model[this.modelKey]) {
          if (this.props.valueType == "0") {
            if (this.valueMap[this.model[this.modelKey]]) {
              var data = this.valueMap[this.model[this.modelKey]];
              if (this.props.displayRoot) {
                return data;
              }
              var rtnData = [];
              for (var i = 1; i < data.length; i++) {
                rtnData.push(data[i]);
              }
              return rtnData;
            } else {
              return [];
            }
          } else if (this.props.valueType == "1") {
            return this.model[this.modelKey];
          }
        } else {
          return [];
        }
      },
      // setter
      set: function(newValue) {
        if (newValue.length == 0) {
          this.model[this.modelKey] = null;
        } else if (this.props.valueType == "0") {
          this.model[this.modelKey] = newValue[newValue.length - 1];
        } else if (this.props.valueType == "1") {
          this.model[this.modelKey] = newValue;
        }
      }
    },
    srcOptions: function() {
      var dataOption;
      if (typeof this.props.options == "string") {
        // 动态选项
        dataOption = ExpressionUtil.createObject(this, this.props.options);
      } else {
        // 固定选项
        dataOption = this.props.options;
      }
      if (this.props.displayRoot) {
        this.saveOptions = [this.createOption(dataOption, "")];
      } else {
        this.saveOptions = this.createOption(dataOption, "").children;
      }
      return this.saveOptions;
    },
    options: function() {
      if (this.saveOptions && this.saveOptions.length > 0) {
        return this.saveOptions;
      }

      return this.srcOptions;
    },
    disabled: function() {
      // 设置输入框为只读
      return this.$store.getters.isLoading || this.props.disabled;
    }
  },
  methods: {
    createOption: function(data, key) {
      var rtnData = {};
      if (this.props.i18n) {
        rtnData.label = this.$t(data[this.props.labelKey]);
      } else {
        rtnData.label = data[this.props.labelKey];
      }
      rtnData.value = data[this.props.valueKey];
      key += rtnData.value + ",";

      if (data["_children_"]) {
        rtnData.children = [];
        for (let i = 0; i < data["_children_"].length; i++) {
          rtnData.children.push(this.createOption(data["_children_"][i], key));
        }
      }
      this.$set(
        this.valueMap,
        rtnData.value,
        key.substring(0, key.length - 1).split(",")
      );
      return rtnData;
    },
    renderFormat: function(labels) {
      if (this.props.renderFormat) {
        return ExpressionUtil.eval(
          this,
          this.props.renderFormat,
          labels,
          this.detailModel
        );
      } else {
        return labels.join(" / ");
      }
    },
    loadData: function(item, callback) {
      if (this.props.loadData) {
        ExpressionUtil.eval(this, this.props.loadData, item, this.detailModel);
        callback();
      }
    },
    changeHandle: function(value, selectedData) {
      // 选择完成后的回调，返回值 value 即已选值 value，selectedData 为已选项的具体数据
      if (this.props.onChange) {
        ExpressionUtil.eval(
          this,
          this.props.onChange,
          this.detailModel,
          value,
          selectedData,
          this.valueMap[selectedData[selectedData.length - 1]]
        );
      }
    },
    visibleChangeHandle: function() {
      // 展开和关闭弹窗时触发
      if (this.props.onVisibleChange) {
        ExpressionUtil.eval(this, this.props.onVisibleChange, this.detailModel);
      }
    },
    refeshOptions: function() {
      // 更新选项
      if (this.saveOptions) {
        this.saveOptions.splice(0, this.saveOptions.length);
      }
    }
  }
};
</script>
