<template>
  <!--顶部导航栏 -->
  <div class="top-nav">
    <div class="logo-container">
      <img src="./assets/logo.png" alt="系统logo" class="logo" />
      <span class="system-name">数据科学与机器学习实验系统</span>
    </div>
    <!--渲染a标实现新标签页打开-->
    <div class="nav-links">
      <router-link to="/help" class="nav-link" tag="a" target="_blank">使用帮助</router-link>
      <router-link to="/about" class="nav-link" tag="a" target="_blank">关于系统</router-link>
    </div>
    <div class="version-container">
      <span class="verspan">测试系统 v1.0 beta</span>
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
  width: 40px;
  height: 40px;
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