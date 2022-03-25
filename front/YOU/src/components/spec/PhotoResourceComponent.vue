<template>
  <div ref="elementRef" class="photoContainer" @mouseover="mouseOverHandle">
    <!-- 图片展示 -->
    <img
      :class="this.props.className"
      :style="this.props.style"
      :width="this.props.width"
      :height="this.props.height"
      :src="showImageData"
    />
    <!-- 上传按钮 -->
    <Upload
      v-if="(mouseOver || (showImageData == null)) && this.props.readonly==false"
      class="uploadArea"
      action="/"
      :accept="this.props.accept"
      :before-upload="this.beforeUploadHandle"
      @mouseout.native="mouseOutHandle"
    >
      <span class="fas fa-file-upload" />
    </Upload>
    <!-- 剪切窗口 -->
    <Modal
      v-model="cropperModal"
      title="图片裁切"
      @on-ok="modalOkHandle"
      @on-cancel="modalCancelHandle"
    >
      <img v-if="cropperModal" ref="editImage" width="100%" :src="editImageData" />
    </Modal>
  </div>
</template>

<script>
import APIUtil from "../../util/APIUtil";
import CommonUtil from "../../util/CommonUtil";
import ModelUtil from "../../util/ModelUtil";
import ExpressionUtil from "../../util/ExpressionUtil";
import Cropper from "cropperjs";
import "cropperjs/dist/cropper.css";

export default {
  name: "PhotoComponent",
  props: ["elementIndex", "props", "displayData", "detailIndex", "detailModel"],
  data: function() {
    return {
      currentResourceId: null,
      cropper: null,
      mouseOver: false,
      cropperModal: false,
      showImageData: null,
      editImageData: null,
      // 删除资源ID列表
      deleteResourceIdList: []
    };
  },
  beforeMount: function() {
    // ClassName
    if (this.props.className == null) this.$set(this.props, "className", null);
    // 样式
    if (this.props.style == null) this.$set(this.props, "style", null);

    // 宽度
    if (this.props.width == null) this.$set(this.props, "width", null);
    // 高度
    if (this.props.height == null) this.$set(this.props, "height", "150px");
    // 只读
    if (this.props.readonly == null) this.$set(this.props, "readonly", true);
    // 裁剪设定
    if (this.props.cropSettings == null)
      this.$set(this.props, "cropSettings", "{}");
    // 保存设定
    if (this.props.saveSettings == null)
      this.$set(this.props, "saveSettings", "{}");
  },
  mounted: function() {
    // 保存控件实例
    CommonUtil.saveElementToStore(this);
    // 加载图片
    this.loadResource();
  },
  watch: {
    model: "loadResource"
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
    }
  },
  methods: {
    loadResource: function() {
      var resourceId = this.model[this.modelKey];
      if (resourceId == null || resourceId == "") {
        this.showImageData = null;
      }

      if (this.currentResourceId == resourceId) {
        return;
      }
      this.currentResourceId = resourceId;

      if (resourceId) {
        var me = this;
        APIUtil.loadResource(this, resourceId).then(function(result) {
          let bytes = new Uint8Array(result);
          let storeData = "";
          let len = bytes.byteLength;
          for (let i = 0; i < len; i++) {
            storeData += String.fromCharCode(bytes[i]);
          }
          me.showImageData = "data:image/png;base64," + window.btoa(storeData);

          // me.showImageData = result;
        });
      }
    },

    mouseOverHandle: function() {
      this.mouseOver = true;
    },
    mouseOutHandle: function() {
      this.mouseOver = false;
    },
    beforeUploadHandle: function(file) {
      // 编辑图片
      this.editImageData = window.URL.createObjectURL(file);
      // 生成cropper
      this.createCropper();
      // 弹出窗口
      this.cropperModal = true;
      return false;
    },

    createCropper: function() {
      if (this.$refs.editImage) {
        var options = ExpressionUtil.createObject(
          this,
          this.props.cropSettings
        );
        this.cropper = new Cropper(this.$refs.editImage, options);
      } else {
        var me = this;
        setTimeout(function() {
          me.createCropper();
        }, 100);
      }
    },

    modalOkHandle: function() {
      var me = this;

      // 上传图片
      var options = ExpressionUtil.createObject(this, this.props.saveSettings);
      this.cropper.getCroppedCanvas(options).toBlob(function(blob) {
        APIUtil.uploadResource(me, blob).then(result => {
          // 删除原有资源数据
          if (me.model[me.modelKey]) {
            me.deleteResourceIdList.push({
              resource_id: me.model[me.modelKey]
            });
          }

          // 设定新资源数据
          me.$set(me.model, me.modelKey, result);
          me.loadResource();
        });
      });

      this.cropper.destroy();
    },

    modalCancelHandle: function() {
      this.cropper.destroy();
    }
  }
};
</script>

<style scoped>
.uploadArea {
  position: absolute;
  top: 0px;
  width: 100%;
  height: 100%;
  background-color: rgba(255, 255, 255, 0.6);
  display: flex;
  text-align: center;
  flex-direction: column;
  justify-content: center;
  font-size: 32px;
  cursor: pointer;
}

/deep/ .ivu-modal-body {
  padding: 0px;
}

img {
  display: block;
}
</style>