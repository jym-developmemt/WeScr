<template>
  <Row>
    <Col span="12">
      <Input
        ref="elementRef"
        v-model="textValue"
        :class="this.props.className"
        :style="this.props.style"
        :type="this.props.type"
        :size="this.props.size"
        :placeholder="this.props.placeholder"
        :clearable="this.props.clearable"
        :disabled="disabled"
        :readonly="this.props.readonly"
        :maxlength="this.props.maxlength"
        :show-word-limit="this.props.showWordLimit"
        :password="this.props.password"
        :prefix="this.props.prefix"
        :suffix="this.props.suffix"
        :search="this.props.search"
        :enter-button="this.props.enterButton"
        :rows="this.props.rows"
        :autosize="this.props.autosize"
        :number="this.props.number"
        :autofocus="this.props.autofocus"
        :autocomplete="this.props.autocomplete"
        :element-id="this.props.elementId"
        :spellcheck="this.props.spellcheck"
        :wrap="this.props.wrap"
        @on-enter="enterHandle"
        @on-click="clickHandle"
        @on-change="changeHandle"
        @on-focus="focusHandle"
        @on-blur="blurHandle"
        @on-keyup="keyupHandle"
        @on-keydown="keydownHandle"
        @on-keypress="keypressHandle"
        @on-search="searchHandle"
        @on-clear="clearHandle"
      >
        <u-icon v-if="this.props.icon" slot="suffix" :displayData="this.props.icon" :props="{}"></u-icon>
      </Input>
    </Col>
    <Col span="12">
      <Alert style="margin-top:1px;margin-bottom:0px;padding-top:7px;padding-bottom:7px;">{{i18nTextValue}}</Alert>
    </Col>
  </Row>
</template>

<script>
import CommonUtil from "../../util/CommonUtil";
import ModelUtil from "../../util/ModelUtil";
import ExpressionUtil from "../../util/ExpressionUtil";

export default {
  name: "InputComponent",
  props: ["elementIndex", "props", "displayData", "detailIndex", "rowIndex", "detailModel"],
  beforeMount: function() {
    // ClassName
    if (this.props.className == null) this.$set(this.props, "className", "");
    // 样式
    if (this.props.style == null) this.$set(this.props, "style", null);
    // 输入框类型，可选值为 text、password、textarea、url、email、date、number、tel
    if (this.props.type == null) this.$set(this.props, "type", "text");
    // 输入框尺寸，可选值为large、small、default或者不设置
    if (this.props.size == null) this.$set(this.props, "size", "default");
    // 占位文本
    if (this.props.placeholder == null)
      this.$set(this.props, "placeholder", null);
    // 是否显示清空按钮
    if (this.props.clearable == null) this.$set(this.props, "clearable", false);
    // 设置输入框为禁用状态
    if (this.props.disabled == null) this.$set(this.props, "disabled", false);
    // 设置输入框为只读
    if (this.props.readonly == null) this.$set(this.props, "readonly", false);
    // 最大输入长度
    if (this.props.maxlength == null) this.$set(this.props, "maxlength", null);
    // 是否显示输入字数统计，可以配合 maxlength 使用
    if (this.props.showWordLimit == null)
      this.$set(this.props, "showWordLimit", false);
    // 是否显示切换密码图标
    if (this.props.password == null) this.$set(this.props, "password", false);
    // 输入框尾部图标，仅在 text 类型下有效
    if (this.props.icon == null) this.$set(this.props, "icon", "");
    // 输入框头部图标
    if (this.props.prefix == null) this.$set(this.props, "prefix", "");
    // 输入框尾部图标
    if (this.props.suffix == null) this.$set(this.props, "suffix", "");
    // 是否显示为搜索型输入框
    if (this.props.search == null) this.$set(this.props, "search", false);
    // 开启 search 时可用，是否有确认按钮，可设为按钮文字
    if (this.props.enterButton == null)
      this.$set(this.props, "enterButton", false);
    // 文本域默认行数，仅在 textarea 类型下有效
    if (this.props.rows == null) this.$set(this.props, "rows", 3);
    // 自适应内容高度，仅在 textarea 类型下有效，可传入对象，如 { minRows: 2, maxRows: 6 }
    if (this.props.autosize == null) this.$set(this.props, "autosize", false);
    // 将用户的输入转换为 Number 类型
    if (this.props.number == null) this.$set(this.props, "number", false);
    // 自动获取焦点
    if (this.props.autofocus == null) this.$set(this.props, "autofocus", false);
    // 原生的自动完成功能
    if (this.props.autocomplete == null)
      this.$set(this.props, "autocomplete", null);
    // 给表单元素设置 id，详见 Form 用法。
    if (this.props.elementId == null) this.$set(this.props, "elementId", null);
    // 原生的 spellcheck 属性
    if (this.props.spellcheck == null)
      this.$set(this.props, "spellcheck", false);
    // 原生的 wrap 属性，可选值为 hard 和 soft，仅在 textarea 下生效
    if (this.props.wrap == null) this.$set(this.props, "wrap", "soft");
  },
  mounted: function() {
    // 保存控件实例
    CommonUtil.saveElementToStore(this);
  },
  computed: {
    textValue: {
      get: function() {
        return this.model[this.modelKey];
      },
      set: function(val) {
        this.$set(this.model, this.modelKey, val);
      }
    },
    i18nTextValue: function() {
      return this.model[this.modelKey]?this.$t(this.model[this.modelKey]):"　";
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
      return ModelUtil.getModelKey(this, this.displayData, "");
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
    enterHandle: function() {
      //按下回车键时触发
      if (this.props.onEnter) {
        ExpressionUtil.eval(this, this.props.onEnter, this.detailModel);
      }
    },
    clickHandle: function() {
      //设置 icon 属性后，点击图标时触发
      if (this.props.onClick) {
        ExpressionUtil.eval(this, this.props.onClick, this.detailModel);
      }
    },
    changeHandle: function() {
      //数据改变时触发
      if (this.props.onChange) {
        ExpressionUtil.eval(this, this.props.onChange, this.detailModel);
      }
    },
    focusHandle: function() {
      //输入框聚焦时触发
      if (this.props.onFocus) {
        ExpressionUtil.eval(this, this.props.onFocus, this.detailModel);
      }
    },
    blurHandle: function() {
      //输入框失去焦点时触发
      if (this.props.onBlur) {
        ExpressionUtil.eval(this, this.props.onBlur, this.detailModel);
      }
    },
    keyupHandle: function() {
      //原生的 keyup 事件
      if (this.props.onKeyup) {
        ExpressionUtil.eval(this, this.props.onKeyup, this.detailModel);
      }
    },
    keydownHandle: function() {
      //原生的 keydown 事件
      if (this.props.onKeydown) {
        ExpressionUtil.eval(this, this.props.onKeydown, this.detailModel);
      }
    },
    keypressHandle: function() {
      //原生的 keypress 事件
      if (this.props.onKeypress) {
        ExpressionUtil.eval(this, this.props.onKeypress, this.detailModel);
      }
    },
    searchHandle: function() {
      //开启 search 时可用，点击搜索或按下回车键时触发
      if (this.props.onSearch) {
        ExpressionUtil.eval(this, this.props.onSearch, this.detailModel);
      }
    },
    clearHandle: function() {
      //开启 clearable 时可用，点击清空按钮时触发
      if (this.props.onClear) {
        ExpressionUtil.eval(this, this.props.onClear, this.detailModel);
      }
    }
  }
};
</script>
