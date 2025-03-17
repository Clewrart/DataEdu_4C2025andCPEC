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
  document.title = "机器学习与图像处理综合教学实验平台使用帮助";
});

</script>

<style scoped lang="less">

</style>