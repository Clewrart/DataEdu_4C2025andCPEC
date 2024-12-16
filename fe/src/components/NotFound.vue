<template>
    <div class="not-found">

        <h1>404 NOT FOUND</h1>
        <h2>页面未找到</h2>
        <el-button type="warning" @click="toLogin">前往登录页</el-button>
    </div>

</template>

<script setup>
import { useRouter } from 'vue-router'
const router = useRouter()
function toLogin() {
    router.push({ name: "login" })
}

</script>



<style lang="less" scoped>
.not-found {
    font-weight: bold;
    width: 50vw;
    height: 50vh;
    text-align: center;
    font-size: large;
    position: fixed;
    inset: 0;
    margin: auto;
}
</style>