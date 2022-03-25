<template>
  <Content id="layoutContainer">
    <!-- 页面标题 -->
    <u-page-title></u-page-title>

    <!-- 栅格行 -->
    <div
      :key="'c' + elementSetRow.key"
      v-for="elementSetRow in cardList"
      :class="elementSetRow.elementSetList[0].addon && elementSetRow.elementSetList[0].addon.containerClassName?elementSetRow.elementSetList[0].addon.containerClassName:''"
      :style="elementSetRow.elementSetList[0].addon && elementSetRow.elementSetList[0].addon.containerStyle?elementSetRow.elementSetList[0].addon.containerStyle:''"
    >
      <u-row
        :props="$store.getters.layoutPropData[elementSetRow.setType + 'r' + elementSetRow.rowIndex]"
        :id="elementSetRow.elementSetList[0].addon && elementSetRow.elementSetList[0].addon.id?elementSetRow.elementSetList[0].addon.id:''"
      >
        <!-- 栅格列 -->
        <u-cell
          v-bind:key="elementSet.rowIndex + elementSet.colIndex"
          v-for="elementSet in elementSetRow.elementSetList"
          :props="$store.getters.layoutPropData[elementSet.setType + 'r' + elementSet.rowIndex + 'c' + elementSet.colIndex]"
        >
          <Card
            :class="elementSet.addon && elementSet.addon.className?elementSet.addon.className:''"
            :style="elementSet.addon && elementSet.addon.style?elementSet.addon.style:''"
            :bordered="elementSet.addon && elementSet.addon.bordered?elementSet.addon.bordered:false"
            :dis-hover="elementSet.disHover && elementSet.addon.disHover?elementSet.addon.disHover:false"
            :shadow="elementSet.addon && elementSet.addon.shadow?elementSet.addon.shadow:false"
          >
            <p slot="title" v-if="elementSet.hiddenTitle != '1'">
              <u-icon v-if="elementSet.setIcon" :displayData="elementSet.setIcon" :props="{}"></u-icon>
              {{$t(elementSet.setName)}}
            </p>
            <router-link
              v-if="elementSet.addon && elementSet.addon.moreText"
              slot="extra"
              :to="elementSet.addon.moreLink"
            >{{$t(elementSet.addon.moreText)}}</router-link>
            <a
              v-if="elementSet.addon && elementSet.addon.extraText"
              slot="extra"
              @click="extraClick(elementSet.addon.onExtraClick)"
            >{{$t(elementSet.addon.extraText)}}</a>
            <!-- 栅格行 -->
            <u-row
              v-bind:key="rowElement.rowIndex"
              v-for="rowElement in elementSet.elementRowList"
              :props="$store.getters.layoutPropData['s' + elementSet.setId + 'r' + rowElement.rowIndex]"
            >
              <!-- 栅格列 -->
              <u-cell
                v-bind:key="cellElement.rowIndex + cellElement.colIndex"
                v-for="cellElement in rowElement.cellList"
                :props="$store.getters.layoutPropData['s' + elementSet.setId + 'r' + cellElement.rowIndex + 'c' + cellElement.colIndex]"
              >
                <!-- 控件 -->
                <u-dispatcher
                  v-bind:key="elementBase.elementIndex"
                  v-for="elementBase in cellElement.elementList"
                  :element-info="elementBase"
                ></u-dispatcher>
              </u-cell>
            </u-row>
          </Card>
        </u-cell>
      </u-row>
    </div>

    <Modal
      v-bind:key="'m' + modelInfo.key"
      v-for="modelInfo in modelList"
      v-model="$store.getters.elementInstance['m' + modelInfo.setId]"
      :title="$t(modelInfo.title)"
      :mask-closable="false"
      :footer-hide="true"
      :fullscreen="modelInfo.fullscreen"
      :closable="modelInfo.addon.closable"
      :draggable="modelInfo.draggable"
      :width="modelInfo.width"
      :styles="modelInfo.styles"
      @on-cancel="cancelHandle(modelInfo.addon.onCancel, modelInfo.setId)"
    >
      <!-- 栅格行 -->
      <u-row
        v-bind:key="rowElement.rowIndex"
        v-for="rowElement in modelInfo.elementRowList"
        :props="$store.getters.layoutPropData['s' + modelInfo.setId + 'r' + rowElement.rowIndex]"
      >
        <!-- 栅格列 -->
        <u-cell
          v-bind:key="cellElement.rowIndex + cellElement.colIndex"
          v-for="cellElement in rowElement.cellList"
          :props="$store.getters.layoutPropData['s' + modelInfo.setId + 'r' + cellElement.rowIndex + 'c' + cellElement.colIndex]"
        >
          <!-- 控件 -->
          <u-dispatcher
            v-bind:key="elementBase.elementIndex"
            v-for="elementBase in cellElement.elementList"
            :element-info="elementBase"
          ></u-dispatcher>
        </u-cell>
      </u-row>
    </Modal>
  </Content>
</template>

<script>
import ExpressionUtil from "../util/ExpressionUtil";

export default {
  computed: {
    cardList() {
      var me = this;
      var cardList = new Array();
      this.$store.getters.curPageDefine.elementSetRows.forEach(function (d) {
        if (d.setType == "card") {
          var prop =
            me.$store.getters.layoutPropData[d.setType + "r" + d.rowIndex];
          if (!prop.className) {
            prop.className = "default-card";
          }
          cardList.push(d);
        }
      });
      return cardList;
    },
    modelList() {
      var me = this;
      var modalList = new Array();
      this.$store.getters.curPageDefine.elementSetRows.forEach(function (d) {
        if (d.setType == "modal") {
          d.elementSetList.forEach(function (m) {
            var modalProp;
            if (m.colProp) {
              modalProp = JSON.parse(m.colProp);
            } else {
              modalProp = {};
            }

            // 主键
            modalProp.key = m.key;
            // 项目组ID
            modalProp.setId = m.setId;
            // 项目行一览
            modalProp.elementRowList = m.elementRowList;

            // 对话框标题
            if (m.setName && m.hiddenTitle != "1") {
              modalProp.title = m.setName;
            }
            // 是否全屏显示
            if (modalProp.fullscreen == null) {
              modalProp.fullscreen = false;
            }
            // 是否可以拖拽移动
            if (modalProp.draggable == null) {
              modalProp.draggable = false;
            }
            // 对话框宽度，对话框的宽度是响应式的，当屏幕尺寸小于 768px 时，宽度会变为自动auto。
            // 当其值不大于 100 时以百分比显示，大于 100 时为像素
            if (modalProp.width == null) {
              modalProp.width = 600;
            }
            // 是否可以拖拽移动
            if (modalProp.style != null) {
              modalProp.styles = ExpressionUtil.createObject(
                me,
                "{" + modalProp.style + "}"
              );
            }
            modalProp.addon = m.addon ? m.addon : {};

            modalList.push(modalProp);
          });
        }
      });
      return modalList;
    },
  },
  methods: {
    cancelHandle: function (onCancel, setId) {
      if (onCancel) {
        ExpressionUtil.eval(this, onCancel, setId);
      }
    },
    extraClick: function (onExtraClick) {
      if (onExtraClick) {
        ExpressionUtil.eval(this, onExtraClick);
      }
    },
  },
};
</script>

