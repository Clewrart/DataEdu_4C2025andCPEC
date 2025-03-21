<script setup>

</script>

<template>
  66666帮助页面
</template>



<script>
  import {onMounted} from "vue";

  onMounted(() => {
  window.addEventListener("resize", handleResize);
  handleResize(); // 初始化时检查宽度
  document.title = "慧图工坊使用帮助";
});

</script>

<style scoped lang="less">

</style>