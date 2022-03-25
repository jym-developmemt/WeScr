<template>
  <Form
    ref="elementRef"
    :class="this.props.className"
    :style="this.props.style"
    :model="model[modelKey]"
    :rules="formRules"
    :inline="this.props.inline"
    :label-position="this.props.labelPosition"
    :label-width="this.props.labelWidth"
    :show-message="this.props.showMessage"
    :autocomplete="this.props.autocomplete"
    :hide-required-mark="this.props.hideRequiredMark"
    :label-colon="this.props.labelColon"
    :disabled="disabled"
    @on-validate="validateHandle"
  >
    <Row :gutter="this.props.gutter != null ? this.props.gutter : 20">
      <Col
        :key="formItem.key"
        v-for="formItem in formItems"
        :span="formItem.span"
        :xs="formItem.xs"
        :sm="formItem.sm"
        :md="formItem.md"
        :lg="formItem.lg"
        :xl="formItem.xl"
        :xxl="formItem.xxl"
        :offset="formItem.offset"
      >
        <FormItem
          :prop="formItem.key"
          :label-width="formItem.labelWidth"
          :style="formItem.style"
        >
          <!-- 控件 -->
          <u-dispatcher :element-info="formItem"></u-dispatcher>
          <span
            v-if="(formItem.labelWidth!=null?formItem.labelWidth*1:props.labelWidth?props.labelWidth*1:0)>0"
            slot="label"
          >
            {{ formItem.label }}
          </span>
        </FormItem>
      </Col>
    </Row>
  </Form>
</template>

<script>
import CommonUtil from "../../util/CommonUtil";
import ModelUtil from "../../util/ModelUtil";
import ExpressionUtil from "../../util/ExpressionUtil";

export default {
  name: "FormComponent",
  props: ["elementIndex", "props", "displayData", "detailList"],
  data() {
    return {
      validators: {
        equals: function (rule, value, callback) {
          var me = rule.vueinstance;
          var formModel = me.model[me.modelKey];
          if (value !== formModel[rule.target]) {
            callback(new Error(rule.message));
          } else {
            callback();
          }
        },
        bigthen: function (rule, value, callback) {
          var me = rule.vueinstance;
          var formModel = me.model[me.modelKey];
          if (value < formModel[rule.target]) {
            callback(new Error(rule.message));
          } else {
            callback();
          }
        },
        lessthen: function (rule, value, callback) {
          var me = rule.vueinstance;
          var formModel = me.model[me.modelKey];
          if (value > formModel[rule.target]) {
            callback(new Error(rule.message));
          } else {
            callback();
          }
        },
      },
    };
  },
  beforeMount: function () {
    var me = this;
    // ClassName
    if (this.props.className == null) this.$set(this.props, "className", null);
    // 样式
    if (this.props.style == null) this.$set(this.props, "style", null);
    // 表单验证规则，具体配置查看  async-validator
    if (this.props.rules == null) this.$set(this.props, "rules", null);
    // 是否开启行内表单模式
    if (this.props.inline == null) this.$set(this.props, "inline", false);
    // 表单域标签的位置，可选值为 left、right、top
    if (this.props.labelPosition == null)
      this.$set(this.props, "labelPosition", "right");
    // 表单域标签的宽度，所有的 FormItem 都会继承 Form 组件的 label-width 的值
    if (this.props.labelWidth == null) this.$set(this.props, "labelWidth", 100);
    if (this.props.labelWidth === "") this.$set(this.props, "labelWidth", null);
    // 是否显示校验错误信息
    if (this.props.showMessage == null)
      this.$set(this.props, "showMessage", true);
    // 原生的 autocomplete 属性，可选值为 off 或 on
    if (this.props.autocomplete == null)
      this.$set(this.props, "autocomplete", "off");
    // 是否隐藏所有表单项的必选标记
    if (this.props.hideRequiredMark == null)
      this.$set(this.props, "hideRequiredMark", false);
    // 是否自动在 label 名称后添加冒号
    if (this.props.labelColon == null)
      this.$set(this.props, "labelColon", true);
    // 是否禁用该表单内的所有组件（适用于具有 disabled 属性的表单类组件）
    if (this.props.disabled == null) this.$set(this.props, "disabled", false);
    // 多语言
    if (this.props.i18n == null) this.$set(this.props, "i18n", true);
  },
  mounted: function () {
    CommonUtil.saveElementToStore(this);
  },
  computed: {
    model: function () {
      return ModelUtil.getModelObject(this, this.displayData);
    },
    modelKey: function () {
      return ModelUtil.getModelKey(this, this.displayData, {});
    },
    disabled: function () {
      return this.$store.getters.isLoading || this.props.disabled;
    },
    formRules: function () {
      if (this.props.rules) {
        var rtnRules = JSON.parse(JSON.stringify(this.props.rules));
        for (var key in rtnRules) {
          if (Array.isArray(rtnRules[key])) {
            for (var idx in rtnRules[key]) {
              if (rtnRules[key][idx].validator) {
                rtnRules[key][idx].validator = this.validators[rtnRules[key][idx].validator];
                rtnRules[key][idx].vueinstance = this;
              }
              if (this.props.i18n && rtnRules[key][idx].message) {
                var msgCode = rtnRules[key][idx].message;
                var msgArgs = [];
                if (Array.isArray(msgCode)) {
                  for (var i=1;i<msgCode.length;i++) {
                    msgArgs.push(this.$t(msgCode[i]));
                  }
                  msgCode = msgCode[0];
                }
                rtnRules[key][idx].message = this.$t(msgCode, msgArgs);
              }
            }
          } else {
            if (rtnRules[key].validator) {
              rtnRules[key].validator = this.validators[rtnRules[key].validator];
              rtnRules[key].vueinstance = this;
            }
            if (this.props.i18n && rtnRules[key].message) {
              var msgCode = rtnRules[key].message;
              var msgArgs = [];
              if (Array.isArray(msgCode)) {
                for (var i=1;i<msgCode.length;i++) {
                  msgArgs.push(this.$t(msgCode[i]));
                }
                msgCode = msgCode[0];
              }
              rtnRules[key].message = this.$t(msgCode, msgArgs);
            }
          }
        }
        return rtnRules;
      }
      return {};
    },
    formItems: function () {
      var formItems = new Array();
      if (this.detailList) {
        for (let i = 0; i < this.detailList.length; i++) {
          var itemDetail = this.detailList[i];

          var propData = this.$store.getters.elementPropData[
            "e" + this.elementIndex + "d" + itemDetail.detailIndex
          ];
          if (propData && this.columnVisible(propData.visible) == false) {
            continue;
          }

          // 列属性
          var itemProp = {};
          if (itemDetail.detailProp) {
            itemProp = JSON.parse(itemDetail.detailProp);
          }
          if (this.props.i18n) {
            itemProp.label = this.$t(itemDetail.detailName);
          } else {
            itemProp.label = itemDetail.detailName;
          }
          itemProp.key = itemDetail.displayData;

          // 控件属性
          itemProp.elementIndex = itemDetail.elementIndex;
          itemProp.detailIndex = itemDetail.detailIndex;
          itemProp.displayType = itemDetail.displayType;
          itemProp.displayData = itemDetail.displayData;
          itemProp.detailModel = this.model[this.modelKey];
          itemProp.detailAddon = itemDetail.detailAddon;
          formItems.push(itemProp);
        }
      }
      return formItems;
    },
  },
  methods: {
    // 控件可见
    columnVisible: function (visible) {
      switch (typeof visible) {
        case "string":
          return ExpressionUtil.eval(this, "return " + visible);
        case "boolean":
          return visible;
        default:
          return true;
      }
    },
    validateHandle: function (prop, status, error) {
      // 任一表单项被校验后触发，返回表单项 prop、校验状态、错误消息
      if (this.props.onValidate) {
        ExpressionUtil.eval(this, this.props.onValidate, prop, status, error);
      }
    },
  },
};
</script>
