<script>
import ExpressionUtil from "../../util/ExpressionUtil";
export default {
  name: "Dispatcher",
  props: ["elementInfo", "detailModel", "elementType", "elementProps", "rowIndex"],
  computed: {
    // 控件属性
    displayProp: function() {
      if (this.elementProps) {
        return this.elementProps;
      } else if (this.elementInfo.detailIndex) {
        return this.$store.getters.elementPropData[
          "e" +
            this.elementInfo.elementIndex +
            "d" +
            this.elementInfo.detailIndex
        ];
      } else {
        return this.$store.getters.elementPropData[
          "e" + this.elementInfo.elementIndex
        ];
      }
    },
    // 明细信息
    displayType: function() {
      if (this.elementType) {
        return this.elementType;
      } else {
        return this.elementInfo.displayType;
      }
    },
    // 明细信息
    displayDetailModel: function() {
      if (this.detailModel) {
        return this.detailModel;
      } else {
        return this.elementInfo.detailModel;
      }
    },
    // 控件可见
    visible: function() {
      if (this.displayProp) {
        switch (typeof this.displayProp.visible) {
          case "string":
            return ExpressionUtil.eval(
              this,
              "return " + this.displayProp.visible,
              this.displayDetailModel
            );
          case "boolean":
            return this.displayProp.visible;
        }
      }
      return true;
    }
  },
  /**
   * 渲染函数
   */
  render(createElement) {
    if (this.visible == false) {
      return null;
    }

    if (this.displayType == null) {
      console.error("displayType is null!");
      return null;
    }

    // 控件类型
    var componentType = "u-" + this.displayType;

    // 创建控件
    var uComponent = createElement(componentType, {
      props: {
        "element-index": this.elementInfo.elementIndex,
        "detail-index": this.elementInfo.detailIndex,
        props: this.displayProp,
        "display-data": this.elementInfo.displayData,
        "detail-list": this.elementInfo.detailList,
        "detail-model": this.displayDetailModel,
        "row-index": this.rowIndex
      }
    });

    if (this.elementInfo.elementAddon) {
      var elementAddon = JSON.parse(this.elementInfo.elementAddon);

      // 文字提示
      if (elementAddon.tooltip) {
        if (!elementAddon.tooltip.placement) {
          elementAddon.tooltip.placement = "top-start";
        }
        uComponent = createElement(
          "Tooltip",
          {
            props: elementAddon.tooltip
          },
          [uComponent]
        );
      }
    }

    if (this.elementInfo.detailAddon) {
      var detailAddon = JSON.parse(this.elementInfo.detailAddon);

      // 文字提示
      if (detailAddon.tooltip) {
        if (!detailAddon.tooltip.placement) {
          detailAddon.tooltip.placement = "top-start";
        }
        uComponent = createElement(
          "Tooltip",
          {
            props: detailAddon.tooltip
          },
          [uComponent]
        );
      }
    }

    return uComponent;
  }
};
</script>
