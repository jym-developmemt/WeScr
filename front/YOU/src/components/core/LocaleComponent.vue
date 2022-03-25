<!-- 多语言切换控件 -->
<template>
  <Dropdown
    @on-click="changeLanguage"
    placement="bottom-end"
    v-if="this.$store.getters.languages.length > 1"
  >
    <img
      :src="require('@/assets/language/icon.png')"
      width="15"
      height="15"
      class="ivu-icon"
    />
    <DropdownMenu slot="list">
      <DropdownItem
        :name="index"
        :key="language.settingIndex"
        v-for="(language, index) in this.$store.getters.languages"
      >
        <div>
          <img
            class="countryImg"
            :src="require('@/assets/language/' + language.varchar2)"
          />
        </div>
        <div>{{ $t(language.varchar1) }}</div>
      </DropdownItem>
    </DropdownMenu>
  </Dropdown>
</template>

<script>
import CommonUtil from "../../util/CommonUtil";

export default {
  methods: {
    // 语言切换
    changeLanguage(index) {
      // 变更语言
      var language = this.$store.getters.languages[index];
      CommonUtil.changeLocale(this, language.varchar3, language.varchar4);
    },
  },
};
</script>

<style scoped>
.ivu-icon {
  font-size: 18px;
  cursor: pointer;
}

.countryImg {
  width: 30px;
  border-radius: 3px;
  box-shadow: 0 1px 1px 0 rgba(0, 0, 0, 0.2);
}

.ivu-dropdown-item {
  text-align: left;
}

.ivu-dropdown-item div {
  display: inline-block;
  vertical-align: top;
}

.ivu-dropdown-item div:nth-child(2) {
  padding: 1px 0px 0px 8px;
}
</style>