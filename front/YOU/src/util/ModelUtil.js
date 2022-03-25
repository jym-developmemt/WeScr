export default {

    /**
     * 通过简称取得模型数据
     * 
     * @param {*} vueinstance VUE实例
     * @param {string} modelName 模型名称
     */
    getBaseModel(vueinstance, modelName) {
      switch (modelName) {
        case 'lu':
          // 当前用户信息
          return vueinstance.$store.getters.userInfo;
        case 'dg':
          // 当前页面组定义信息
          return vueinstance.$store.getters.curPageGroupDefine;
        case 'dp':
          // 当前页面定义信息
          return vueinstance.$store.getters.curPageDefine;
  
        case 'gd':
          // 全局变量
          return vueinstance.$store.getters.globalData;
        case 'pg':
          // 当前页面组数据
          return vueinstance.$store.getters.pageGroupData;
        case 'pd':
          // 当前页面数据
          return vueinstance.$store.getters.pageData;
        case 'bd':
          // 当前流程数据
          return vueinstance.$store.getters.bpmData;
        case 'lp':
          // 页面布局属性
          return vueinstance.$store.getters.layoutPropData;
        case 'ep':
          // 控件及明细控件属性
          return vueinstance.$store.getters.elementPropData;
        case 'ei':
          // 控件实例
          return vueinstance.$store.getters.elementInstance;
      }
  
      throw new Error('Unkown Model Name:' + modelName);
    },
  
    /**
     * 取得模型对象
     * 
     * @param {*} vueinstance VUE实例
     * @param {string} modelName 名称
     */
    getModelObject(vueinstance, modelName) {
      if (modelName) {
        var modelNames = modelName.split('.');
  
        var rtnModel = null;
        for (var i = 0; i < modelNames.length - 1; i++) {
          if (i == 0) {
            rtnModel = this.getBaseModel(vueinstance, modelNames[i]);
            continue;
          }
          if (rtnModel[modelNames[i]] == null) {
            vueinstance.$set(rtnModel, modelNames[i], {});
          }
          rtnModel = rtnModel[modelNames[i]];
          if (rtnModel == null) {
            rtnModel = {};
          }
        }
        return rtnModel;
      }
      return null;
    },
  
    /**
     * 取得模型对象属性名
     * 
     * @param {*} vueinstance VUE实例
     * @param {string} modelName 名称
     * @param {string} defaultVal 属性默认值
     */
    getModelKey(vueinstance, modelName, defaultVal) {
      if (modelName) {
        var modelNames = modelName.split('.');
        var modelKey = modelNames[modelNames.length - 1];
  
        var model = this.getModelObject(vueinstance, modelName);
        if (model[modelKey] == null) {
          vueinstance.$set(model, modelKey, defaultVal);
        }
        return modelKey;
      }
    },
  
    /**
     * 取得模型对象属性名
     * 
     * @param {*} vueinstance VUE实例
     * @param {string} modelName 名称
     * @param {string} defaultVal 属性默认值
     */
    getDetailModelValue(vueinstance, detailModel, modelKey) {
      if (modelKey) {
        var modelKeys = modelKey.split('.');
        if (modelKeys.length == 1) {
          return detailModel[modelKey];
        }
  
        var subModelKey = modelKeys.shift();
        if (detailModel[subModelKey] == null) {
          vueinstance.$set(detailModel, subModelKey, {});
        }
  
        return this.getDetailModelValue(vueinstance, detailModel[subModelKey], modelKeys.join('.'));
      }
    },
  }