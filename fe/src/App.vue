<template>
  <div class="top-nav">
    <div class="logo-container">
      <img src="./assets/logo.png" alt="系统logo" class="logo" />
      <span class="system-name"> <router-link to="/about" style="color:dodgerblue;text-decoration: none;">慧图工坊</router-link>基于模块化架构的机器学习与图像处理实验课程自动评分系统</span>
    </div>
    <div class="nav-links">
      <router-link to="/help" class="nav-link" tag="a" target="_blank">使用帮助</router-link>
      <router-link to="/about" class="nav-link" tag="a" target="_blank">关于系统</router-link>
    </div>
    <div class="version-container">
      <img width="240px" height="60px" src="https://www.clewrart.top/content/uploadfile/202508/78181756438038.png">
    </div>

  </div>
  <router-view></router-view>

</template>



<style>
.top-nav {
  display: flex;
  align-items: center;
  justify-content: space-between; /* 让左右两部分元素分布两端 */
  padding: 0 20px;
  background-color: #f6f6f6;
  border-bottom: 1px solid #dcdcdc;
  height: 60px;
}

.logo-container {
  display: flex;
  align-items: center;
}

.logo {
  width: 60px;
  height: 60px;
  margin-right: 10px;
}

.system-name {
  font-size: 18px;
  font-weight: bold;
  color: #008c8c;
}

.nav-links {
  display: flex;
  align-items: center;
}

.nav-link {
  font-size: 16px;
  font-weight: 500;
  color: #333;
  margin-left: 20px;
  text-decoration: none;
  padding: 5px 10px;
}

.nav-link:hover {
  background-color: #008c8c;
  color: #fff;
  text-decoration: none;
}

.version-container {
  margin-left: auto;
}

.verspan {
  font-size: 14px;
  font-weight: normal;
  color: #666;
}

@media (max-width: 768px) {
  .nav-links {
    display: none;
  }
}
</style>
<script setup lang="ts">
</script>