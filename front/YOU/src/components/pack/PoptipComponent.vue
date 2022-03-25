<template>
  <Poptip
    ref="elementRef"
    :class="this.props.className"
    :style="this.props.style"
    :trigger="this.props.trigger"
    :title="this.props.title"
    :placement="this.props.placement"
    :width="this.props.width"
    :confirm="this.props.confirm"
    :disabled="this.disabled"
    :ok-text="this.props.okText"
    :cancel-text="this.props.cancelText"
    :word-wrap="this.props.wordWrap"
    :offset="this.props.offset"
    @on-popper-show="this.popperShowHandle"
    @on-popper-hide="this.popperHideHandle"
    @on-ok="this.okHandle"
    @on-cancel="this.cancelHandle"
  >
    <!-- 弹出画面 -->
    <u-form
      v-if="this.props.containerType == 'form'"
      slot="content"
      :elementIndex="this.elementIndex + 'f'"
      :props="this.props.container"
      :displayData="this.displayData"
      :detailList="this.detailList"
    ></u-form>
    <!--
    <u-table
      v-if="this.props.containerType == 'table'"
      slot="content"
      :elementIndex="this.elementIndex + 't'"
      :props="this.props.container"
      :displayData="this.displayData"
      :detailList="this.detailList"
    ></u-table>
    <u-list
      v-else
      slot="content"
      :elementIndex="this.elementIndex + 'l'"
      :props="this.props.container"
      :displayData="this.displayData"
      :detailList="this.detailList"
    ></u-list>
    -->
    <!-- 按钮 -->
    <u-button
      :elementIndex="this.elementIndex"
      :detailIndex="99"
      :props="this.props.button"
      :displayData="this.props.buttonName"
    ></u-button>
  </Poptip>
</template>

<script>
import CommonUtil from "../../util/CommonUtil";
import ModelUtil from "../../util/ModelUtil";
import ExpressionUtil from "../../util/ExpressionUtil";

export default {
  name: "PoptipComponent",
  props: ["elementIndex", "props", "displayData", "detailList"],
  beforeMount: function () {
    // ClassName
    if (this.props.className == null) this.$set(this.props, "className", null);
    // 样式
    if (this.props.style == null) this.$set(this.props, "style", null);
    // 容器类型
    if (this.props.type == null) this.$set(this.props, "type", null);
    // 容器属性
    if (this.props.container == null) this.$set(this.props, "container", {});
    // 按钮属性
    if (this.props.button == null) this.$set(this.props, "button", {});
    // 按钮名称
    if (this.props.buttonName == null) this.$set(this.props, "buttonName", "");

    // 触发方式，可选值为hover（悬停）click（点击）focus（聚焦）,在 confirm 模式下，只有 click 有效
    if (this.props.trigger == null) this.$set(this.props, "trigger", "click");
    // 显示的标题
    if (this.props.title == null) this.$set(this.props, "title", null);
    // 提示框出现的位置，可选值为toptop-starttop-endbottombottom-startbottom-endleftleft-startleft-endrightright-startright-end
    if (this.props.placement == null) this.$set(this.props, "placement", "top");
    // 宽度，最小宽度为 150px，在 confirm 模式下，默认最大宽度为 300px
    if (this.props.width == null) this.$set(this.props, "width", 300);
    // 是否开启对话框模式
    if (this.props.confirm == null) this.$set(this.props, "confirm", false);
    // 确定按钮的文字，只在 confirm 模式下有效
    if (this.props.okText == null) this.$set(this.props, "okText", "确定");
    // 取消按钮的文字，只在 confirm 模式下有效
    if (this.props.cancelText == null)
      this.$set(this.props, "cancelText", "取消");
    // 开启后，超出指定宽度文本将自动换行，并两端对齐
    if (this.props.wordWrap == null) this.$set(this.props, "wordWrap", false);
    // 出现位置的偏移量
    if (this.props.offset == null) this.$set(this.props, "offset", 0);
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
  },
  methods: {
    popperShowHandle: function () {
      //在提示框显示时触发
      if (this.props.onPopperShow) {
        ExpressionUtil.eval(this, this.props.onPopperShow);
      }
    },
    popperHideHandle: function () {
      //在提示框消失时触发
      if (this.props.onPopperHide) {
        ExpressionUtil.eval(this, this.props.onPopperHide);
      }
    },
    okHandle: function () {
      //点击确定的回调，只在 confirm 模式下有效
      if (this.props.onOk) {
        ExpressionUtil.eval(this, this.props.onOk);
      }
    },
    cancelHandle: function () {
      //点击取消的回调，只在 confirm 模式下有效
      if (this.props.onCancel) {
        ExpressionUtil.eval(this, this.props.onCancel);
      }
    },
  },
};
</script>
