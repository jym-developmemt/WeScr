<template>
    <div>
        <div>404</div>
        <div>{{location}}</div>
    </div>
</template>

<script>
export default {
  computed: {
    location: function() {
      // 设置输入框为只读
      return window.location.href;
    }
  },
}
</script>