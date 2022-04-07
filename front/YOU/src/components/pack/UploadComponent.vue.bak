<template>
  <Upload
    ref="elementRef"
    :class="this.props.uploadClassName"
    :style="this.props.uploadStyle"
    :action="this.props.action"
    :headers="this.props.headers"
    :multiple="this.props.multiple"
    :paste="this.props.paste"
    :disabled ="disabled"
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
    <u-button v-if="this.detailList==null||this.detailList.length == 0"
      :elementIndex = "this.elementIndex"
      :props = "this.props"
      :displayData = "this.displayData"
    ></u-button>
    <Row v-if="this.detailList && this.detailList.length > 0">
      <Col
        :key="formItem.key"
        v-for="formItem in formItems"
        :span="formItem.span"
        :offset="formItem.offset"
      >
        <!-- 控件 -->
        <u-dispatcher :element-info="formItem"></u-dispatcher>
      </Col>
    </Row>
  </Upload>
</template>

<script>
import CommonUtil from "../../util/CommonUtil";
import ModelUtil from "../../util/ModelUtil";
import ExpressionUtil from "../../util/ExpressionUtil";

export default {
  name: "UploadComponent",
  props: ["elementIndex", "props", "displayData", "detailList"],
  beforeMount: function() {
    // ClassName
    if (this.props.uploadClassName == null) this.$set(this.props, "uploadClassName", null);
    // 样式
    if (this.props.uploadStyle == null) this.$set(this.props, "uploadStyle", null);
    // 上传的地址，必填
    if (this.props.action == null) this.$set(this.props, "action", "/api/resource/upload");
    // 设置上传的请求头部
    if (this.props.headers == null) this.$set(this.props, "headers", {
      Authorization: "Bearer " + this.$store.getters.accessToken
    });
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
    if (this.props.withCredentials == null) this.$set(this.props, "withCredentials", false);
    // 是否显示已上传文件列表
    if (this.props.showUploadList == null) this.$set(this.props, "showUploadList", false);
    // 上传控件的类型，可选值为 select（点击选择），drag（支持拖拽）
    if (this.props.uploadType == null) this.$set(this.props, "uploadType", "select");
    // 接受上传的文件类型
    if (this.props.accept == null) this.$set(this.props, "accept", null);
    // 支持的文件类型，与 accept 不同的是，format 是识别文件的后缀名，accept 为 input 标签原生的 accept 属性，会在选择文件时过滤，可以两者结合使用
    if (this.props.format == null) this.$set(this.props, "format", []);
    // 文件大小限制，单位 kb
    if (this.props.maxSize == null) this.$set(this.props, "maxSize", null);
    // 默认已上传的文件列表
    if (this.props.defaultFileList == null) this.$set(this.props, "defaultFileList", []);
  },
  mounted: function() {
    CommonUtil.saveElementToStore(this);
  },
  computed: {
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
    },
    formItems: function() {
      var formItems = new Array();
      if (this.detailList) {
        for (let i = 0; i < this.detailList.length; i++) {
          var itemDetail = this.detailList[i];
          
          var propData = this.$store.getters.elementPropData['e' + this.elementIndex + 'd' + itemDetail.detailIndex];
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
          // itemProp.detailModel = this.model[this.modelKey];
          itemProp.detailAddon = itemDetail.detailAddon;
          formItems.push(itemProp);
        }
      }
      return formItems;
    }
  },
  methods: {
    beforeUploadHandle: function(file) {
      // 上传文件之前的钩子，参数为上传的文件，若返回 false 或者 Promise 则停止上传
      if (this.props.beforeUpload) {
        return ExpressionUtil.eval(this, this.props.beforeUpload, file);
      }
    },
    progressHandle: function(file) {
      // 文件上传时的钩子，返回字段为 event, file, fileList
      if (this.props.onProgress) {
        return ExpressionUtil.eval(this, this.props.onProgress, file);
      }
    },
    successHandle: function(res, file) {
      // 文件上传成功时的钩子，返回字段为 response, file, fileList
      if (this.props.onSuccess) {
        return ExpressionUtil.eval(this, this.props.onSuccess, res, file);
      }
    },
    errorHandle: function(file) {
      // 文件上传失败时的钩子，返回字段为 error, file, fileList
      if (this.props.onError) {
        return ExpressionUtil.eval(this, this.props.onError, file);
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
  }
};
</script>
