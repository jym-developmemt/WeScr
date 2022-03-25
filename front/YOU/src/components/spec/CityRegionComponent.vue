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
import APIUtil from "../../util/APIUtil";
import { relativeTimeThreshold } from "moment";

export default {
  name: "CityRegionComponent",
  props: ["elementIndex", "props", "displayData", "detailIndex", "detailModel"],
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
      this.$set(this.props, "changeOnSelect", false);
    // 输入框大小，可选值为large和small或者不填
    if (this.props.size == null) this.$set(this.props, "size", null);
    // 当搜索列表为空时显示的内容
    if (this.props.notFoundText == null)
      this.$set(this.props, "notFoundText", "无匹配数据");
    // 是否将弹层放置于 body 内，在 Tabs、带有 fixed 的 Table 列内使用时，建议添加此属性，它将不受父级样式影响，从而达到更好的效果
    if (this.props.transfer == null) this.$set(this.props, "transfer", false);
    // 给表单元素设置 id，详见 Form 用法。
    if (this.props.elementId == null) this.$set(this.props, "elementId", null);
    // 最大层数
    if (this.props.maxLevel == null) this.$set(this.props, "maxLevel", 3);
  },
  mounted: function() {
    var me = this;

    // 保存控件实例
    CommonUtil.saveElementToStore(this);

    // 加载省信息
    APIUtil.searchCityRegion(this, "0").then(function(result) {
      result.forEach(adCodeInfo => {
        if (me.props.maxLevel > 0) {
          adCodeInfo.children = [];
          adCodeInfo.loading = false;
        }
        me.options.push(adCodeInfo);
      });
    });
  },
  data() {
    return {
      options: []
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
        let rtnData = [];
        if (this.model[this.modelKey]) {
          if (this.props.maxLevel >= 0) {
            rtnData.push(this.model[this.modelKey].substring(0, 2));
          }
          if (this.props.maxLevel >= 1) {
            rtnData.push(this.model[this.modelKey].substring(0, 4));
          }
          if (this.props.maxLevel >= 2) {
            rtnData.push(this.model[this.modelKey].substring(0, 6));
          }
          if (this.props.maxLevel >= 3) {
            rtnData.push(this.model[this.modelKey]);
          }
        }
        return rtnData;
      },
      // setter
      set: function(newValue) {
        if (newValue.length == 0) {
          this.model[this.modelKey] = null;
        } else {
          this.model[this.modelKey] = newValue[newValue.length - 1];
        }
      }
    },
    disabled: function() {
      // 设置输入框为只读
      return this.$store.getters.isLoading || this.props.disabled;
    }
  },
  methods: {
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
      } else {
        var me = this;
        item.loading = true;
        if (item.children == null) {
        }

        // 加载子信息信息
        APIUtil.searchCityRegion(this, item.level + 1, item.value).then(
          function(result) {
            result.forEach(adCodeInfo => {
              adCodeInfo.children = [];
              if (adCodeInfo.level < me.props.maxLevel) {
                adCodeInfo.loading = false;
              }
              item.children.push(adCodeInfo);
            });
            item.loading = false;
            callback();
          }
        );
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
          selectedData
        );
      }
    },
    visibleChangeHandle: function() {
      // 展开和关闭弹窗时触发
      if (this.props.onVisibleChange) {
        ExpressionUtil.eval(this, this.props.onVisibleChange, this.detailModel);
      }
    }
  }
};
</script>
