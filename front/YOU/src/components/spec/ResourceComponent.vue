<template>
  <div :class="this.props.className" :style="this.props.style">
    <!-- 上传 -->
    <Upload
      ref="elementRef"
      :class="this.props.uploadClassName"
      :style="this.props.uploadStyle"
      :action="this.props.action"
      :headers="this.props.headers"
      :multiple="this.props.multiple"
      :paste="this.props.paste"
      :disabled="this.props.disabled"
      :data="this.props.data"
      :name="this.props.name"
      :with-credentials="this.props.withCredentials"
      :show-upload-list="this.props.showUploadList"
      :type="this.props.uploadType"
      :accept="this.props.accept"
      :format="this.props.format"
      :max-size="this.props.maxSize"
      :default-file-list="this.props.defaultFileList"
      :before-upload="this.beforeUploadHandle"
      :on-progress="this.progressHandle"
      :on-success="this.successHandle"
      :on-error="this.errorHandle"
      :on-preview="this.previewHandle"
      :on-remove="this.removeHandle"
      :on-format-error="this.formatErrorHandle"
      :on-exceeded-size="this.exceededSizeHandle"
    >
      <u-button :props="this.uploadProp" :displayData="this.props.uploadLabel"></u-button>
    </Upload>
        <!-- 下载 -->
    <!-- <u-button
      v-if="model[modelKey]"
      :props="this.downloadProp"
      :displayData="this.props.downloadLabel"
    ></u-button> -->
    <!-- 删除 -->
    <!-- <u-button
      v-if="model[modelKey]"
      :props="this.deleteProp"
      :displayData="this.props.deleteLabel"
    ></u-button> -->
  </div>
</template>

<script>
import CommonUtil from "../../util/CommonUtil";
import ModelUtil from "../../util/ModelUtil";
import ExpressionUtil from "../../util/ExpressionUtil";

export default {
  name: "ResourceComponent",
  props: ["elementIndex", "props", "displayData", "detailIndex", "detailModel"],
  data() {
    return {
      // 删除资源ID列表
      deleteResourceIdList: []
    };
  },
  beforeMount: function() {
    // ClassName
    if (this.props.className == null) this.$set(this.props, "className", null);
    // 样式
    if (this.props.style == null) this.$set(this.props, "style", null);
    // ClassName
    if (this.props.uploadClassName == null)
      this.$set(this.props, "uploadClassName", null);
    // 样式
    if (this.props.uploadStyle == null)
      this.$set(this.props, "uploadStyle", "display:inline-block");
    // 上传的地址，必填
    if (this.props.action == null)
      this.$set(this.props, "action", "/api/resource/upload");
    // 设置上传的请求头部
    if (this.props.headers == null) this.$set(this.props, "headers", {});
    // 是否支持多选文件
    if (this.props.multiple == null) this.$set(this.props, "multiple", false);
    // 是否支持粘贴上传文件
    if (this.props.paste == null) this.$set(this.props, "paste", false);
    // 是否禁用
    if (this.props.disabled == null) this.$set(this.props, "disabled", false);
    // 上传时附带的额外参数
    if (this.props.data == null) this.$set(this.props, "data", null);
    // 上传的文件字段名
    if (this.props.name == null) this.$set(this.props, "name", "file");
    // 支持发送 cookie 凭证信息
    if (this.props.withCredentials == null)
      this.$set(this.props, "withCredentials", false);
    // 是否显示已上传文件列表
    if (this.props.showUploadList == null)
      this.$set(this.props, "showUploadList", false);
    // 上传控件的类型，可选值为 select（点击选择），drag（支持拖拽）
    if (this.props.uploadType == null)
      this.$set(this.props, "uploadType", "select");
    // 接受上传的文件类型
    if (this.props.accept == null) this.$set(this.props, "accept", null);
    // 支持的文件类型，与 accept 不同的是，format 是识别文件的后缀名，accept 为 input 标签原生的 accept 属性，会在选择文件时过滤，可以两者结合使用
    if (this.props.format == null) this.$set(this.props, "format", []);
    // 文件大小限制，单位 kb
    if (this.props.maxSize == null) this.$set(this.props, "maxSize", null);
    // 默认已上传的文件列表
    if (this.props.defaultFileList == null)
      this.$set(this.props, "defaultFileList", []);

    // 上传按钮显示名
    if (this.props.uploadLabel == null)
      this.$set(this.props, "uploadLabel", "上传");
    // 下载按钮显示名
    if (this.props.downloadLabel == null)
      this.$set(this.props, "downloadLabel", "下载");
    // 删除按钮显示名
    if (this.props.deleteLabel == null)
      this.$set(this.props, "deleteLabel", "删除");
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
      return ModelUtil.getModelKey(this, this.displayData, "");
    },
    disabled: function() {
      // 设置输入框为只读
      return this.$store.getters.isLoading || this.props.disabled;
    },
    uploadProp: function() {
      if (this.props.uploadProp) {
        return JSON.parse(this.props.uploadProp);
      }
      return {};
    },
    downloadProp: function() {
      var rtnData = {};
      if (this.props.downloadProp) {
        rtnData = JSON.parse(this.props.downloadProp);
      }
      if (rtnData.onClick == null) {
        // 控件实例ID
        var id = "e" + this.elementIndex;
        if (this.detailIndex) {
          id += "d" + this.detailIndex;
        }
        rtnData.onClick = "ei.$" + id + ".downloadHandle()";
      }
      return rtnData;
    },
    deleteProp: function() {
      var rtnData = {};
      if (this.props.deleteProp) {
        rtnData = JSON.parse(this.props.deleteProp);
      }
      if (rtnData.onClick == null) {
        // 控件实例ID
        var id = "e" + this.elementIndex;
        if (this.detailIndex) {
          id += "d" + this.detailIndex;
        }
        rtnData.onClick = "ei.$" + id + ".deleteHandle()";
      }
      return rtnData;
    }
  },
  methods: {
    beforeUploadHandle: function(file) {
      // 上传文件之前的钩子，参数为上传的文件，若返回 false 或者 Promise 则停止上传
      if (this.props.beforeUpload) {
        return ExpressionUtil.eval(this, this.props.beforeUpload, file);
      }
    },
    progressHandle: function(event) {
      // 文件上传时的钩子，返回字段为 event, file, fileList
      if (this.props.onProgress) {
        return ExpressionUtil.eval(this, this.props.onProgress, event);
      }
    },
    successHandle: function(res, file) {
      // 删除原有资源
      if (this.model[this.modelKey]) {
        this.deleteResourceIdList.push({
          resource_id: this.model[this.modelKey]
        });
      }
      // 上传资源信息
      this.$set(this.model, this.modelKey, file.response.data);

      // 文件上传成功时的钩子，返回字段为 response, file, fileList
      if (this.props.onSuccess) {
        return ExpressionUtil.eval(this, this.props.onSuccess, res, file);
      }
    },
    errorHandle: function(error) {
      // 文件上传失败时的钩子，返回字段为 error, file, fileList
      if (this.props.onError) {
        return ExpressionUtil.eval(this, this.props.onError, error);
      }
    },
    previewHandle: function(file) {
      // 点击已上传的文件链接时的钩子，返回字段为 file， 可以通过 file.response 拿到服务端返回数据
      if (this.props.onPreview) {
        return ExpressionUtil.eval(this, this.props.onPreview, file);
      }
    },
    removeHandle: function(file) {
      // 文件列表移除文件时的钩子，返回字段为 file, fileList
      if (this.props.onRemove) {
        return ExpressionUtil.eval(this, this.props.onRemove, file);
      }
    },
    formatErrorHandle: function(file) {
      // 文件格式验证失败时的钩子，返回字段为 file, fileList
      if (this.props.onFormatError) {
        return ExpressionUtil.eval(this, this.props.onFormatError, file);
      }
    },
    exceededSizeHandle: function(file) {
      // 文件超出指定大小限制时的钩子，返回字段为 file, fileList
      if (this.props.onExceededSize) {
        return ExpressionUtil.eval(this, this.props.onExceededSize, file);
      }
    },
    downloadHandle: function() {
      if (this.props.onDownload) {
        return ExpressionUtil.eval(
          this,
          this.props.onDownload,
          this.model[this.modelKey]
        );
      }

      // 默认处理
      var onDownload = "u.loadResource(a1, 'u.download(a1, pd.axiosHeader)')";
      ExpressionUtil.eval(this, onDownload, this.model[this.modelKey]);
    },
    deleteHandle: function() {
      // 删除原有资源
      if (this.model[this.modelKey]) {
        this.deleteResourceIdList.push({
          resource_id: this.model[this.modelKey]
        });
      }
      // 清除资源信息
      this.$set(this.model, this.modelKey, null);

      if (this.props.onDelete) {
        return ExpressionUtil.eval(
          this,
          this.props.onDelete,
          this.model[this.modelKey]
        );
      }
    }
  }
};
</script>
