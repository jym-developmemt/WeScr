<template>
  <div
    ref="elementRef"
    :class="this.props.className"
    :style="'display: inline-block;' + this.props.style"
  >
    <Tag
      :key="index"
      v-for="(item, index) in model[modelKey]"
      :closable="item.closable?item.closable:props.closable"
      :checkable="item.checkable?item.checkable:props.checkable"
      :checked="item.checked?item.checked:props.checked"
      :type="item.type?item.type:props.type"
      :color="item.color?item.color:props.color"
      :name="index"
      :fade="item.fade?item.fade:props.fade"
      :size="props.size"
      @on-close="closeHandle"
      @on-change="changeHandle"
    >
      <!-- 文本输出 -->
      <span @click="clickHandle(index)" v-if="detailList == null || detailList.length == 0">{{ item }}</span>

      <!-- 控件输出 -->
      <Row v-if="detailList && detailList.length > 0">
        <Col
          :key="formItem.key"
          v-for="formItem in formItems(index)"
          :span="formItem.span"
          :offset="formItem.offset"
          :class="formItem.className"
          :style="formItem.style"
        >
          <!-- 控件 -->
          <u-dispatcher :element-info="formItem"></u-dispatcher>
        </Col>
      </Row>
    </Tag>

    <!-- 追加按钮 -->
    <Button
      v-if="props.addType == 'button'"
      :disabled="disabled"
      :icon="props.addButtonIcon"
      type="dashed"
      :size="props.type=='dot'?'default':'small'"
      @click="addHandle"
    >{{props.addButtonText}}</Button>

    <!-- 级联选择按钮 -->
    <Cascader
      v-if="props.addType == 'cascader'"
      v-model="cascaderSelectedValue"
      :disabled="disabled"
      :data="options"
      :change-on-select="props.changeOnSelect"
      :render-format="renderFormat"
      style="display: inline-block"
      @on-change="changeCascaderHandle"
      @on-visible-change="addHandle"
    >
      <Button
        :icon="props.addButtonIcon"
        type="dashed"
        :size="props.type=='dot'?'default':'small'"
      >{{props.addButtonText}}</Button>
    </Cascader>
  </div>
</template>

<script>
import CommonUtil from "../../util/CommonUtil";
import ModelUtil from "../../util/ModelUtil";
import ExpressionUtil from "../../util/ExpressionUtil";

export default {
  name: "TagGroupComponent",
  props: [
    "elementIndex",
    "props",
    "displayData",
    "detailList",
    "detailIndex",
    "detailModel"
  ],
  data() {
    return {
      valueMap: {},
      cascaderSelectedValue: []
    };
  },
  beforeMount: function() {
    // ClassName
    if (this.props.className == null) this.$set(this.props, "className", null);
    // 样式
    if (this.props.style == null) this.$set(this.props, "style", null);
    // 是否禁用
    if (this.props.disabled == null) this.$set(this.props, "disabled", false);

    // 标签是否可以关闭
    if (this.props.closable == null) this.$set(this.props, "closable", false);
    // 标签是否可以选择
    if (this.props.checkable == null) this.$set(this.props, "checkable", false);
    // 标签的选中状态
    if (this.props.checked == null) this.$set(this.props, "checked", true);
    // 标签的样式类型，可选值为 border、dot或不填
    if (this.props.type == null) this.$set(this.props, "type", null);
    // 标签颜色，预设颜色值为default、primary、success、warning、error、blue、green、red、
    // yellow、pink、magenta、volcano、orange、gold、lime、cyan、geekblue、purple，你也可以自定义颜色值。
    if (this.props.color == null) this.$set(this.props, "color", "default");
    // 是否在出现和消失时使用渐变的动画，动画时长可能会引起占位的闪烁
    if (this.props.fade == null) this.$set(this.props, "fade", true);
    // 尺寸，可选值为 large、medium、default
    if (this.props.size == null) this.$set(this.props, "size", "default");
    // 追加按钮样式
    if (this.props.addType == null) this.$set(this.props, "addType", "");
    // 追加按钮标签
    if (this.props.addButtonText == null)
      this.$set(this.props, "addButtonText", "追加");
    // 追加按钮标签
    if (this.props.addButtonIcon == null)
      this.$set(this.props, "addButtonIcon", "ios-add");

    // 当此项为 true 时，点选每级菜单选项值都会发生变化，具体见上面的示例
    if (this.props.changeOnSelect == null)
      this.$set(this.props, "changeOnSelect", true);
    // 值类型
    if (this.props.valueType == null) this.$set(this.props, "valueType", "0");
    if (this.props.valueKey == null) this.$set(this.props, "valueKey", "0");
    if (this.props.labelKey == null) this.$set(this.props, "labelKey", "1");
  },
  mounted: function() {
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
      return ModelUtil.getModelKey(this, this.displayData, []);
    },
    options: function() {
      var dataOption;
      if (typeof this.props.options == "string") {
        // 动态选项
        dataOption = ExpressionUtil.createObject(this, this.props.options);
      } else {
        // 固定选项
        dataOption = this.props.options;
      }
      return this.createOption(dataOption, "").children;
    },
    disabled: function() {
      return this.$store.getters.isLoading || this.props.disabled;
    }
  },
  methods: {
    formItems: function(dataIndex) {
      var formItems = new Array();
      if (this.detailList) {
        for (let i = 0; i < this.detailList.length; i++) {
          var itemDetail = this.detailList[i];

          var propData = this.$store.getters.elementPropData[
            "e" + this.elementIndex + "d" + itemDetail.detailIndex
          ];
          if (propData && propData.visible == false) {
            continue;
          }

          // 列属性
          var itemProp = {};
          if (itemDetail.detailProp) {
            itemProp = JSON.parse(itemDetail.detailProp);
          }
          itemProp.label = itemDetail.detailName;
          itemProp.key = itemDetail.displayData;

          // 控件属性
          itemProp.elementIndex = itemDetail.elementIndex;
          itemProp.detailIndex = itemDetail.detailIndex;
          itemProp.displayType = itemDetail.displayType;
          itemProp.displayData = itemDetail.displayData;
          itemProp.detailModel = this.model[this.modelKey][dataIndex];
          itemProp.detailAddon = itemDetail.detailAddon;
          formItems.push(itemProp);
        }
      }
      return formItems;
    },
    createOption: function(data, key) {
      var rtnData = {};
      rtnData.label = data[this.props.labelKey];
      rtnData.value = data[this.props.valueKey];
      key += rtnData.value + ",";

      if (data["_children_"]) {
        rtnData.children = [];
        for (let i = 0; i < data["_children_"].length; i++) {
          rtnData.children.push(this.createOption(data["_children_"][i], key));
        }
      }
      this.valueMap[rtnData.value] = data;
      return rtnData;
    },
    renderFormat: function(labels) {
      return labels.join(" / ");
    },
    closeHandle: function(e, name) {
      // 关闭时触发
      if (this.props.onClose) {
        return ExpressionUtil.eval(this, this.props.onClose, name);
      }
    },
    changeHandle: function(e, name) {
      // 切换选中状态时触发
      if (this.props.onChange) {
        return ExpressionUtil.eval(this, this.props.onChange, name);
      }
    },
    clickHandle: function(name) {
      // 点击时触发
      if (this.props.onClick) {
        return ExpressionUtil.eval(this, this.props.onClick, name);
      }
    },
    changeCascaderHandle: function(val) {
      this.cascaderSelectedValue = val;
    },
    addHandle: function(visible) {
      // 点击追加按钮触发
      if (this.props.onAdd) {
        let selectedData = null;
        if (this.props.addType == "cascader") {
          if (visible == true) {
            return;
          }
          if (this.cascaderSelectedValue == null || this.cascaderSelectedValue.length == 0) {
            selectedData = null;
          } else {
            selectedData = this.valueMap[this.cascaderSelectedValue[this.cascaderSelectedValue.length - 1]];
          }
          this.cascaderSelectedValue = [];
        }
        return ExpressionUtil.eval(this, this.props.onAdd, selectedData);
      }
    }
  }
};
</script>
