<script setup>
import {onMounted} from "vue";  
</script>

<template>
  <div class="container">
    <div class="card">
      <img src="../assets/logo.png" alt="系统logo" class="logo" />
      <h1 class="title">慧图工坊</h1>
      <h2 class="subtitle">基于模块化架构的机器学习与图像处理实验课程自动评分系统</h2>
      <h3 class="date">2025.04</h3>
      <div class="info">
        <p>院校：<strong>西南林业大学</strong></p>
        <p>论文题目：<strong>基于模块化架构的机器学习与图像处理实验课程自动评分系统</strong></p>
        <p>学生：<strong>郭思宇</strong></p>
        <p>教师：<strong>赵毅力</strong></p>

      </div>
    </div>
  </div>
</template>

<style scoped>
.container {
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: x-large;
  height: 90vh;
  background: linear-gradient(135deg, #f0f4f8, #d9e2ec);
}

.card {
  text-align: center;
  background: white;
  padding: 30px;
  border-radius: 12px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  max-width: 500px;
  width: 90%;
}

.logo {
  width: 80px;
  height: 80px;
  margin-bottom: 20px;
}

html, body {
  zoom: 100%;
  transform: scale(1); 
  transform-origin: top left;
  width: 100vw;
  height: 100vh;
  overflow: hidden;
}

.title {
  font-size: 24px;
  font-weight: bold;
  color: #2c3e50;
}

.subtitle {
  font-size: 18px;
  color: #34495e;
  margin-bottom: 10px;
}

.date {
  font-size: 16px;
  color: #777;
  margin-bottom: 20px;
}

.info p {
  font-size: 16px;
  color: #555;
  margin: 5px 0;
}

strong {
  color: #2c3e50;
}
</style>

  
<script>
export default {
  mounted() {
    document.title = "关于平台";
  }
}
</script>
<style scoped lang="less">

</style>