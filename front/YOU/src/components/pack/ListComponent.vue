<template>
  <Row
    ref="elementRef"
    :class="this.props.rootClassName"
    :style="this.props.rootStyle"
    :gutter="this.props.gutter"
  >
    <Col
      :class="props.className"
      :style="props.style"
      :key="index"
      :span="props.span"
      :xs="props.xs"
      :sm="props.sm"
      :md="props.md"
      :lg="props.lg"
      :xl="props.xl"
      :xxl="props.xxl"
      v-for="(item, index) in model[modelKey]"
    >
      <!-- 文本输出 -->
      <span v-if="!detailList || detailList.length == 0">{{ item }}</span>

      <!-- 控件输出 -->
      <Row
        v-if="detailList && detailList.length > 0"
        :class="props.itemClassName"
        :style="props.itemStyle"
        :type="props.itemType?props.itemType:''"
        :justify="props.itemJustify?props.itemJustify:'center'"
        :align="props.itemAlign?props.itemAlign:'top'"
      >
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
    </Col>
  </Row>
</template>

<script>
import CommonUtil from "../../util/CommonUtil";
import ModelUtil from "../../util/ModelUtil";
import ExpressionUtil from "../../util/ExpressionUtil";

export default {
  name: "ListComponent",
  props: [
    "elementIndex",
    "props",
    "displayData",
    "detailList",
    "detailIndex",
    "detailModel",
  ],
  beforeMount: function () {
    // ClassName
    if (this.props.rootClassName == null)
      this.$set(this.props, "rootClassName", null);
    // 样式
    if (this.props.rootStyle == null) this.$set(this.props, "rootStyle", null);
    // ClassName
    if (this.props.className == null) this.$set(this.props, "className", null);
    // 样式
    if (this.props.style == null) this.$set(this.props, "style", null);
    // ClassName
    if (this.props.itemClassName == null)
      this.$set(this.props, "itemClassName", null);
    // 样式
    if (this.props.itemStyle == null) this.$set(this.props, "itemStyle", null);
    // SPAN
    if (this.props.gutter == null) this.$set(this.props, "gutter", 0);
    // SPAN
    if (this.props.span == null) this.$set(this.props, "span", "");
    // <576px 响应式栅格，可为栅格数或一个包含其他属性的对象
    if (this.props.xs == null) this.$set(this.props, "xs", {});
    // ≥576px 响应式栅格，可为栅格数或一个包含其他属性的对象
    if (this.props.sm == null) this.$set(this.props, "sm", {});
    // ≥768px 响应式栅格，可为栅格数或一个包含其他属性的对象
    if (this.props.md == null) this.$set(this.props, "md", {});
    // ≥992px 响应式栅格，可为栅格数或一个包含其他属性的对象
    if (this.props.lg == null) this.$set(this.props, "lg", {});
    // ≥1200px 响应式栅格，可为栅格数或一个包含其他属性的对象
    if (this.props.xl == null) this.$set(this.props, "xl", {});
    // ≥1600px 响应式栅格，可为栅格数或一个包含其他属性的对象
    if (this.props.xxl == null) this.$set(this.props, "xxl", {});
  },
  mounted: function () {
    CommonUtil.saveElementToStore(this);
  },
  computed: {
    model: function () {
      if (this.detailModel) {
        return this.detailModel;
      }
      return ModelUtil.getModelObject(this, this.displayData);
    },
    modelKey: function () {
      if (this.detailModel) {
        return this.displayData;
      }
      return ModelUtil.getModelKey(this, this.displayData, []);
    },
  },
  methods: {
    formItems: function (dataIndex) {
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
  },
};
</script>
