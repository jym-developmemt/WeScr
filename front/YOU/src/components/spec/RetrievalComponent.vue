<template>
<div ref="elementRef" :style="props.style" :class="props.class">
        <div v-for="(item, index) in props.selectList" :key="index" style="display:inline-block;">
            <div v-if="!item.cascader&&!item.inputHidden">{{item.label}}:&nbsp;&nbsp;&nbsp;<Input type="text" v-model="demo[item.value]" :style="item.style" style="display:inline-block;"/></div>
            <div v-if="item.cascader">{{item.label}}:&nbsp;&nbsp;&nbsp;<Cascader :data="data" v-model="demo[item.value]" :style="item.style" style="display:inline-block;"></Cascader></div>
        </div>
        <Button type="primary" @click="onClick(1)">{{props.text}}</Button>
        <br><br>
        <Table :columns="props.columns" :data="list" :width="props.width" :loading="loading">
          <template slot-scope="{ row, index }" slot="action">
            <Icon :type="props.iconType" @click="iconClick(row, index)" :style="{color: props.iconColor}"/>
        </template>
        </Table>
        <br>
        <Page :total="total" @on-change="changeHandle"/>
</div>
</template>

<script>
import CommonUtil from "../../util/CommonUtil";
import ModelUtil from "../../util/ModelUtil";
import ExpressionUtil from "../../util/ExpressionUtil";

export default {
  name: "RetrievalComponent",
  props: ["elementIndex", "props", "displayData", "detailIndex", "detailModel"],
  data: function() {
    return {
      demo: {},
      data: [],
      timer: '',
      list: [],
      total: 10,
      loading: false
    };
  },
  beforeMount: function() {
    // ClassName
    if (this.props.className == null) this.$set(this.props, "className", "");
    // 样式
    if (this.props.style == null) this.$set(this.props, "style", "");
    // 查询按钮
    if (this.props.text == null) this.$set(this.props, "text", "检索");
    // 查询条件
    if (this.props.selectList == null) this.$set(this.props, "selectList", []);
    // 查询表格
    if (this.props.columns == null) this.$set(this.props, "columns", []);
    this.changeColumu(this.props.columns)
    // 查询数据源番号
    if (this.props.dsno == null) this.$set(this.props, "dsno", 1);
    // 表格宽
    if (this.props.width == null) this.$set(this.props, "width", 100);
    // 图标颜色
    if (this.props.iconColor == null) this.$set(this.props, "iconColor", "black");
    // 图标类型
    if (this.props.iconType == null) this.$set(this.props, "iconType", "");
    
  },
  mounted: function() {

            this.timer = setInterval(() => {
              if (this.props.selectList.length>0){
                  this.props.selectList.forEach(r => {
                    this.demo[r.value]= ''
                    if (r.inputDef) {
                      this.demo[r.value] = r.inputDef
                    }
                    if (r.cascader) {
                      this.demo[r.value]= []
                      if (typeof r.options == "string") {
                        // 动态选项
                        let A = ExpressionUtil.createObject(this, r.options);
                        this.data = this.createOption(A, "", r.valueKey, r.labelKey).children
                      } else {
                        // 固定选项
                        this.data = r.options;
                      }
                    }
                  });
                  this.onClick(1)
                  clearInterval(this.timer);
                }
            }, 2000);
    // 保存控件实例
    CommonUtil.saveElementToStore(this);
  },
  computed: {
    model: function() {
      return ModelUtil.getModelObject(this, this.displayData);
    },
    modelKey: function() {
      return ModelUtil.getModelKey(this, this.displayData, {});
    }
  },
  methods: {
    createOption: function(data, key, valueKey, labelKey) {
      var rtnData = {};
      rtnData.label = data[labelKey];
      rtnData.value = data[valueKey];
      key += rtnData.value + ",";

      if (data["_children_"]) {
        rtnData.children = [];
        for (let i = 0; i < data["_children_"].length; i++) {
          rtnData.children.push(this.createOption(data["_children_"][i], key, valueKey, labelKey));
        }
      }
      return rtnData;
    },
    onClick(val) {
      let ds = ''
      this.props.selectList.forEach(r => {
        if (r.cascader) {
          ds = r.value
        }
      })
      if (ds && this.demo[ds] && typeof this.demo[ds] != 'string') {
        this.demo[ds] = this.demo[ds][this.demo[ds].length - 1]
      }
      let s = {
        pageNo: val
      }
      this.loading = true
      let les = "u.send({t:'P00',a:'p',s3:" + this.props.dsno + ",o1:" + JSON.stringify(this.demo) +",o2:"+ JSON.stringify(s)+"},'vueinstance.loading= false;vueinstance.list = a1[0].dataList;vueinstance.total = a1[0].pageInfo.itemCount')"
      ExpressionUtil.eval(this, les, this.detailModel);
    },
    changeHandle: function(pageNo) {
      this.onClick(pageNo)
    },
    iconClick(row, index) {
      if (this.props.onClick) {
        ExpressionUtil.eval(this, this.props.onClick, row, index);
      }
    },
    changeColumu (list) {
      if (list.length > 0) {
        list.forEach(r => {
          if (r.timeFormat) {
            r.render = function (h, params) {
              return h('div', [h('span',params.row[r.key]?params.row[r.key].split('T')[0]:'')])
            }
          }
        })
      }
    }
  }
};
</script>
