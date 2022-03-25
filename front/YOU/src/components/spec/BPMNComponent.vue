<template>
  <div class="containers" ref="bpmnContainer">
    <div class="canvas" ref="bpmnCanvas"></div>
    <div id="js-properties-panel" ref="propertiesPanel" class="panel"></div>
    <div class="buttons" v-if="props.download">
      <div>
        <a ref="saveDiagram" href="javascript:">下载XML数据</a>
      </div>
      <div>
        <a ref="saveSvg" href="javascript:">下载SVG图片</a>
      </div>
    </div>
  </div>
</template>

<script>
import BpmnModeler from 'bpmn-js/lib/Modeler';
import propertiesPanelModule from 'bpmn-js-properties-panel';
import propertiesProviderModule from 'bpmn-js-properties-panel/lib/provider/camunda';
import camundaModdleDescriptor from 'camunda-bpmn-moddle/resources/camunda';

import 'bpmn-js/dist/assets/diagram-js.css';
import 'bpmn-js/dist/assets/bpmn-font/css/bpmn.css';
import 'bpmn-js/dist/assets/bpmn-font/css/bpmn-codes.css';
import 'bpmn-js/dist/assets/bpmn-font/css/bpmn-embedded.css';
import 'bpmn-js-properties-panel/dist/assets/bpmn-js-properties-panel.css';

import ModelUtil from "../../util/ModelUtil";
import ExpressionUtil from "../../util/ExpressionUtil";

export default {
  name: "BPMNComponent",
  props: ["elementIndex", "props", "displayData", "detailIndex", "detailModel"],
  beforeMount: function() {
    // 可见
    if (this.props.visible == null) this.$set(this.props, "visible", true);
    // ClassName
    if (this.props.className == null) this.$set(this.props, "className", "");
    // 样式
    if (this.props.style == null) this.$set(this.props, "style", "");
    // 下载按钮
    if (this.props.download == null) this.$set(this.props, "download", "false");
    // 高度
    if (this.props.height == null) this.$set(this.props, "height", 500);
  },
  data () {
    return {
      bpmnModeler: null,
      container: null,
      canvas: null,
      xmlStr: null,
    }
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
    xmlValue: function() {
      return this.model[this.modelKey];
    },
  },
  methods: {
    // 加载XML
    importXML(xmlValue) {
      var me = this;
      this.bpmnModeler.importXML(xmlValue, function(e){
        if (e) {
          me.$Message.error("XML读取错误");
        }
      });
    },

    // 保存XML
    saveXML(callback) {
      var me = this;
      this.bpmnModeler.saveXML({ format: true }, function (err, xml) {
        if (err) {
          me.$Message.error("XML保存错误");
        } else {
          ExpressionUtil.eval(me, callback, xml);
        }
      });
    },

    // 下载为SVG格式,done是个函数，调用的时候传入的
    saveSVG (done) {
      // 把传入的done再传给bpmn原型的saveSVG函数调用
      this.bpmnModeler.saveSVG(done)
    },

    // 下载为SVG格式,done是个函数，调用的时候传入的
    saveDiagram (done) {
      // 把传入的done再传给bpmn原型的saveXML函数调用
      this.bpmnModeler.saveXML({ format: true }, function (err, xml) {
        done(err, xml)
      })
    },

    // 当图发生改变的时候会调用这个函数，这个data就是图的xml
    setEncoded (link, name, data) {
      // 把xml转换为URI，下载要用到的
      const encodedData = encodeURIComponent(data)
      // 获取到图的xml，保存就是把这个xml提交给后台
      this.xmlStr = data
      // 下载图的具体操作,改变a的属性，className令a标签可点击，href令能下载，download是下载的文件的名字
      if (data) {
        link.className = 'active'
        link.href = 'data:application/bpmn20-xml;charset=UTF-8,' + encodedData
        link.download = name
      }
    }
  },

  mounted () {
    // 保存控件实例
    this.$set(this.$store.getters.elementInstance, 'e' + this.elementIndex, this);

    // 获取到属性ref为“content”的dom节点
    // this.container = this.$refs.bpmnContent
    // 获取到属性ref为“canvas”的dom节点
    const canvas = this.$refs.bpmnCanvas

    // 建模，官方文档这里讲的很详细
    this.bpmnModeler = new BpmnModeler({
      container: canvas,
      // 添加控制板
      propertiesPanel: {
        parent: '#js-properties-panel'
      },
      additionalModules: [
        // 左边工具栏以及节点
        propertiesProviderModule,
        // 右边的工具栏
        propertiesPanelModule,
      ],
      moddleExtensions: {
        camunda: camundaModdleDescriptor
      }
    });

    // 下载画图
    let _this = this
    // 获取a标签dom节点
    const downloadLink = this.$refs.saveDiagram
    const downloadSvgLink = this.$refs.saveSvg
    // 给图绑定事件，当图有发生改变就会触发这个事件
    this.bpmnModeler.on('commandStack.changed', function () {
      _this.saveDiagram(function (err, xml) {
        _this.setEncoded(downloadLink, 'diagram.bpmn', err ? null : xml)
      });
      _this.saveSVG(function (err, svg) {
        _this.setEncoded(downloadSvgLink, 'diagram.svg', err ? null : svg)
      });
    });

    // 高度设定
    const container = this.$refs.bpmnContainer;
    container.style.height = this.props.height + "px";
    const propertiesPanel = this.$refs.propertiesPanel
    propertiesPanel.style.height = this.props.height + "px";
  },
};
</script>

<!-- 左边工具栏以及编辑节点的样式 -->
<style scoped src="bpmn-js/dist/assets/diagram-js.css"></style>
<style scoped src="bpmn-js/dist/assets/bpmn-font/css/bpmn.css"></style>
<style scoped src="bpmn-js/dist/assets/bpmn-font/css/bpmn-codes.css"></style>
<style scoped src="bpmn-js/dist/assets/bpmn-font/css/bpmn-embedded.css"></style>
<!-- 右边工具栏样式 -->
<style scoped src="bpmn-js-properties-panel/dist/assets/bpmn-js-properties-panel.css"></style>
<style scoped>
  .containers{
    background-color: #ffffff;
    width: 100%;
  }
  .canvas{
    width: 100%;
    height: 100%;
  }
  .panel{
    position: absolute;
    right: 0;
    top: 0;
    width: 300px;
  }
  .buttons{
    position: absolute;
    left: 10px;
    bottom: 0px;
  }
  #js-properties-panel {
    overflow-y: scroll;
  }
</style>

<style>
  .bjs-powered-by {
    display: none;
  }
</style>
