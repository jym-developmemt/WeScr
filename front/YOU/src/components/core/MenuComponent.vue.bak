<script>
export default {
  name: "MenuComponent",
  props: ["dataList", "selectedMenuId", "mode", "theme"],
  data() {
    return {
      dataMenuMap: {},
      propMode: this.mode,
      propTheme: this.theme
    };
  },

  watch: {
    // 初始化数据
    $route: "fetchData",
  },

  /**
   * 渲染函数
   */
  render(createElement) {
    var me = this;

    // 菜单一览
    var rootMenuList = new Array();

    // 默认属性
    if (this.propMode == null) {
      this.propMode = "vertical";
    }
    if (this.propTheme == null) {
      this.propTheme = "dark";
    }

    // 创建菜单
    for (var i = 0; i < this.dataList.length; i++) {
      var menuInfo = this.dataList[i];
      rootMenuList.push(this.createMenuList(createElement, menuInfo));
    }

    // 退出菜单
    if (this.$store.state.sider_menu_collapsed) {
      rootMenuList.push(
        this.createMenuList(createElement, {
          menuId: "_exit_",
          menuName: this.$t("common.btn.logout"),
          menuType: "2",
          pageId: "/",
          childMenuList: []
        })
      );
    }

    // 创建控件
    return createElement(
      "Menu",
      {
        ref: "elementRef",
        attrs: {
          mode: this.propMode,
          theme: this.propTheme,
          width: "auto",
          accordion: true,
          openNames: this.openNames,
          activeName: this.activeName
        },
        on: {
          "on-select": this.selectHandler
        }
      },
      rootMenuList
    );
  },
  computed: {
    openNames: function() {
      var rtnData = [];
      if (this.propMode == "vertical") {
        var parentMenuId = this.selectedMenuId;
        while (true) {
          if (this.dataMenuMap[parentMenuId]) {
            parentMenuId = this.dataMenuMap[parentMenuId].parentId;
            rtnData.push(parentMenuId);
          } else {
            break;
          }
        }
      }

      this.$nextTick(() => {
        if (this.$refs.elementRef) {
          this.$refs.elementRef.updateOpened();
        }
      });
      return rtnData;
    },
    activeName: function() {
      this.$nextTick(() => {
        if (this.$refs.elementRef) {
          this.$refs.elementRef.updateActiveName();
        }
      });

      return this.selectedMenuId;
    }
  },
  methods: {
    /**
     * 创建菜单
     */
    createMenuList(createElement, menuInfo) {
      // 菜单项目
      if (
        menuInfo.childMenuList == null ||
        menuInfo.childMenuList.length == 0
      ) {
        // 菜单项目保存
        this.dataMenuMap[menuInfo.menuId] = menuInfo;

        // 创建控件
        return createElement(
          "MenuItem",
          {
            attrs: {
              name: menuInfo.menuId
            }
          },
          this.createMenuTitle(createElement, menuInfo)
        );
      }

      // 子菜单一览
      var childMenuList = new Array();

      // 标题
      childMenuList.push(
        createElement(
          "template",
          {
            slot: "title"
          },
          this.createMenuTitle(createElement, menuInfo)
        )
      );

      // 创建子菜单
      for (var i = 0; i < menuInfo.childMenuList.length; i++) {
        var childMenuInfo = menuInfo.childMenuList[i];
        childMenuList.push(this.createMenuList(createElement, childMenuInfo));
      }

      // 创建控件
      return createElement(
        "Submenu",
        {
          attrs: {
            name: menuInfo.menuId
          }
        },
        childMenuList
      );
    },

    /**
     * 创建菜单标题
     */
    createMenuTitle(createElement, menuInfo) {
      var titleList = new Array();

      // 图片
      if (menuInfo.icon) {
        titleList.push(
          createElement("u-icon", {
            attrs: {
              displayData: menuInfo.icon,
              props: {}
            }
          })
        );
      }

      // 文字
      titleList.push(this.$t(menuInfo.menuName));

      return titleList;
    },

    selectHandler(name) {
      if (this.$store.getters.isLoading) {
        this.$Message.error(this.$t("s.common.error.processing"));
        return;
      }

      this.$emit("on-select", this.dataMenuMap[name]);
    },

    fetchData() {
      console.log(this.$route.path)
    }
  }
};
</script>
