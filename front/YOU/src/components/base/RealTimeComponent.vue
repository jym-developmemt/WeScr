<template>
    <div ref="elementRef" style="color:white">
            <!-- {{ nowDate + ' ' + nowTime + ' ' + nowWeek }}          -->
            <div class="date-top" style="display:flex;justify-content: space-between;padding-left: 8px;padding-right: 8px;">
                <span>
                    {{ nowDate }}
                </span>
                <span>
                    {{ nowWeek }}               
                </span>
            </div>
            <div class="date-bottom">{{nowTime}}</div>

            <!-- <Row :gutter="28" class="date-top">
                <Col span="12">
                    <div>{{ nowDate }}</div>
                </Col>
                <Col span="12">
                    <div>{{ nowWeek }}</div>
                </Col>               
            </Row>
            <Row class="date-bottom" gutter="28"> 
                <Col span="24">
                    <div>{{ nowDate }}</div>
                </Col>
            </Row> -->
    </div>
    
</template>

<script>
import CommonUtil from "../../util/CommonUtil";
import ExpressionUtil from "../../util/ExpressionUtil";

export default {
  name: "RealTimeComponent",
  props: ["elementIndex", "props", "displayData", "detailIndex", "detailModel"],
  beforeMount: function() {
    // // ClassName
    // if (this.props.className == null) this.$set(this.props, "className", "");
    // 样式
    // if (this.props.style == null) this.$set(this.props, "style", null);
  },
  mounted: function() {
      this.currentTime();
    // 保存控件实例
    CommonUtil.saveElementToStore(this);
  },
  data() {
        return {
            time: '',
            nowDate: "",    // 当前日期
            nowTime: "",    // 当前时间
            nowWeek: "" ,    // 当前星期
            hhmm: ''
        };
    },
  computed: {
    
  },
  methods: {
    currentTime() {
            setInterval(this.getDate, 500);
        },
        getDate: function() {
            var _this = this;
            var now = new Date();
            let yy = now.getFullYear();
            let MM = now.getMonth() + 1;
            let dd = now.getDate();
            let week = now.getDay();
            let hh = now.getHours() < 10
                    ? "0" + now.getHours()
                    : now.getHours();;
            let mm = now.getMinutes() < 10
                    ? "0" + now.getMinutes()
                    : now.getMinutes();
            let ss = now.getSeconds() < 10
                    ? "0" + now.getSeconds()
                    : now.getSeconds();;
            if (week == 1) {
                this.nowWeek = "星期一";
            } else if (week == 2) {
                this.nowWeek = "星期二";
            } else if (week == 3) {
                this.nowWeek = "星期三";
            } else if (week == 4) {
                this.nowWeek = "星期四";
            } else if (week == 5) {
                this.nowWeek = "星期五";
            } else if (week == 6) {
                this.nowWeek = "星期六";
            } else {
                this.nowWeek = "星期日";
            }
            _this.hhmm = hh + ":" + mm;
            _this.nowTime = hh + ":" + mm+ ":" +ss;
            _this.nowDate = yy + "/" + MM + "/" + dd;
            _this.time = yy + "/" + MM + "/" + dd + ' ' + hh + ":" + mm+ ":" +ss
        }
  },
  beforeDestroy: function() {
        if (this.getDate) {
            
            clearInterval(this.getDate); 
        }
    }
};
</script>