<template>
<div>
     <!-- <Upload style="display:inline-block" action="/api/resource/upload" :data="{resourceType: 3}" :show-upload-list="false" :on-success="this.successHandle">
        <Button icon="ios-cloud-upload-outline">上传图片</Button>
    </Upload>
    &nbsp;&nbsp;&nbsp;&nbsp;
    <Button type="info" @click="modalClick">预览</Button>
    <br>
    <br> -->
    <div v-show="model[modelKey].length == 0" style="text-align:center">
暂无图片
    </div>
    <draggable
            class="syllable_ul"
            element="ul"
            v-show="model[modelKey].length > 0"
            :list="model[modelKey]"
            :options="{group:'title', animation:150}"
            :no-transition-on-drag="true"
            @change="change"
            @start="start"
            @end="end"
            :move="move"
          >
            <transition-group type="transition"  :name="!drag? 'syll_li' : null" :css="true">
              <li v-for="item in model[modelKey]" :key="item.resource_id" style="margin-right:50px" >
                  <Icon type="md-close" size="15" @click="closeShow?confirm(item.resource_id):confirmFales(item.resource_id)"/>
                  <img :src="item.resource_path_old || item.resource_path" :width="300" :height="200" :style="{border: item.temp_flg!=0?'1px solid #27ec27':'none'}">
              </li>
            </transition-group>
          </draggable>

          <!-- <Modal
        v-model="modal1"
        width="840"
        title="预览">
        <Carousel autoplay loop v-model="value2" :height="300" style="width:800px">
          <CarouselItem v-for="(item, index) in this.model[this.modelKey]" :key="index">
              <img :src="item.resource_path" style="height:300px;width:800px;clear: both;display: block;margin: auto;"/>  
          </CarouselItem>
      </Carousel>
        <div slot="footer">
            <Button  @click="modal1 = false">关闭</Button>
        </div>
    </Modal> -->
</div>
</template>
<style lang="less">
.syllable_ul {
    li {
        list-style: none;
        display: inline-block;
        position: relative;
        i {
            position: absolute;
            top:  0px;
            right: 0;
            color: red;
            cursor: pointer;
        }
        i:hover {
             background: #1b151573;
    border-radius: 15px;
        }
    }
}
</style>

<script>
import CommonUtil from "../../util/CommonUtil";
import ModelUtil from "../../util/ModelUtil";
import ExpressionUtil from "../../util/ExpressionUtil";
import draggable from "vuedraggable"
export default {
  name: "ImageComponent",
  props: ["elementIndex", "props", "displayData", "detailIndex", "detailModel"],
  components: {
     draggable
  },
  beforeMount: function() {
    // ClassName
    if (this.props.className == null) this.$set(this.props, "className", null);
    // 样式
    if (this.props.style == null) this.$set(this.props, "style", null);
    // 删除提示
    if (this.props.closeShow == null) this.$set(this.props, "closeShow", false);
  },
  data () {
      return {
          timer: '',
          drag: false,
          modal1: false,
          value2: 0
      }
  },
  mounted: function() {
    this.timer = setInterval(() => {
        if (this.model[this.modelKey]) {
            
            for (let i = 0; i < this.model[this.modelKey].length; i++) {
                this.model[this.modelKey][i].resource_id_old = this.model[this.modelKey][i].resource_id
            }
            clearInterval(this.timer);
            CommonUtil.saveElementToStore(this);

        }
    }, 100);
      
    // 保存控件实例
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
  },
  methods: {
    modalClick() {
      this.modal1 = true
    },
      confirm (a) {
                this.$Modal.confirm({
                    title: '确定删除此图片?',
                    content: '',
                    onOk: () => {
                      let As = {}
                      for (let i = 0; i < this.model[this.modelKey].length; i++) {
                          if (this.model[this.modelKey][i].resource_id == a) {
                            As = this.model[this.modelKey][i]
                            this.model[this.modelKey].splice(i, 1)
                          }
                      }
                      if (this.props.onClose) {
                          ExpressionUtil.eval(this, this.props.onClose, As);
                      }
                      CommonUtil.saveElementToStore(this);
                      this.$Message.info('删除成功');
                    },
                    onCancel: () => {
                        this.$Message.info('取消删除');
                    }
                });
            },
            confirmFales (a) {
                let As = {}
                      for (let i = 0; i < this.model[this.modelKey].length; i++) {
                          if (this.model[this.modelKey][i].resource_id == a) {
                            As = this.model[this.modelKey][i]
                            this.model[this.modelKey].splice(i, 1)
                          }
                      }
                      if (this.props.onClose) {
                          ExpressionUtil.eval(this, this.props.onClose, As);
                      }
                      CommonUtil.saveElementToStore(this);
                      this.$Message.info('删除成功');
            },
    successHandle: function(res, file,fileList) {
      if (this.props.onSuccess) {
        return ExpressionUtil.eval(this, this.props.onSuccess, res, file,fileList);
      }
    },
    change(evt) {
      console.log(evt , 'change...')
    },
    //start ,end ,add,update, sort, remove 得到的都差不多
    start(evt) {
      this.drag = true
      console.log(evt , 'start...')
    },
    end(evt) {
      for (let i = 0; i < this.model[this.modelKey].length; i++) {
                this.model[this.modelKey][i].resource_id = i + 1 + new Date().getTime()
            }
      console.log(this.model[this.modelKey])
      this.drag = true
      evt.item //可以知道拖动的本身
      evt.to    // 可以知道拖动的目标列表
      evt.from  // 可以知道之前的列表
      evt.oldIndex  // 可以知道拖动前的位置
      evt.newIndex  // 可以知道拖动后的位置
    },
    move(evt, originalEvent) {
      console.log(evt , 'move')
      console.log(originalEvent) //鼠标位置
    }
  }
};
</script>