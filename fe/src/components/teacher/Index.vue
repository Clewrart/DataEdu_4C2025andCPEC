<template>
    <div class="admin">
        <el-row>
            <div class="nav">
                <button style="height: 50px; width: 50px;font-size: 40px; margin: 10px;" class="btn1" @click="toggleNav">≡</button>
                <span class="welcome" style="margin-left: 30px;"><b>欢迎您!</b></span> <span class="name" style="color: #008c8c;"><b>{{ userInfo.name }}</b></span>
            </div>
            <el-col :span="isSmallScreen ? undefined : 4" class="nav1" v-show="isNavVisible">
                <el-menu :default-active="activeIndex" class="el-menu-vertical-demo" @select="handleSelect">
                    <div class="username"><span class="welcome">欢迎您!</span> <span class="name">{{ userInfo.name
                            }}</span><br><br>请在此功能区选择业务模块  </div>
                    <el-menu-item index="/teacher/student">
                        <el-icon>
                            <Avatar />
                        </el-icon>
                        学生管理
                    </el-menu-item>
                   
                    <el-menu-item index="/teacher/experiment">
                        <el-icon>
                            <DataAnalysis />
                        </el-icon>
                        我的实验
                    </el-menu-item>

                    <el-menu-item index="exit" style="background-color: red; color: white;">
                        <el-icon>
                            <SwitchButton />
                        </el-icon>
                        退出系统
                    </el-menu-item>

                </el-menu>
            </el-col>
            <el-col :span="isSmallScreen ? undefined : 20" class="nav2">
                <div class="container">
                    <router-view></router-view>
                </div>
            </el-col>
        </el-row>
    </div>
</template>

<script lang="js" setup>
import { useRouter } from "vue-router";
import { getUserInfo } from "@utils/access"
import {
    onBeforeUnmount,
    onMounted,
} from "vue";
import { ref } from "vue";
const userInfo = ref(getUserInfo());
const activeIndex = ref("/admin/user")
const isNavVisible = ref(false);
const isSmallScreen = ref(false);
const router = useRouter();

function handleSelect(index) {
    if (index === "exit") {
        localStorage.clear();
        router.push({
            name: "login",
        });
    } else {
        router.push(index);
    }
}

function toggleNav() {
    isNavVisible.value = !isNavVisible.value;
}

// 监听窗口大小变化
function handleResize() {
    if (window.innerWidth <= 768) {
        isSmallScreen.value = true; // 小屏幕时设置 isSmallScreen 为 true
        isNavVisible.value = false; // 小屏幕时默认隐藏菜单
    } else {
        isSmallScreen.value = false; // 大屏幕时设置 isSmallScreen 为 false
        isNavVisible.value = true; // 大屏幕时默认显示菜单
    }
}

onMounted(() => {
    window.addEventListener("resize", handleResize);
    handleResize(); // 初始化时检查宽度
    document.title = "教师主页_慧图工坊";
});

onBeforeUnmount(() => {
    window.removeEventListener("resize", handleResize);
});  
</script>

<style lang="less" scoped>
.admin {
    .username{
        text-align: center;
        padding: 10px 0;
        font-weight: bold;
        .name{
            color: #008c8c;
        }
    }
    .nav :deep(.el-menu) {
        height: 100vh;
        padding-right: 2vw;
        background-color: #f6f6f6;
        position: fixed;
        top: 0;
        left: 0;
        z-index: 999;
        transition: transform 0.3s ease-out;
        // border: none;
    }

    .container {
        padding: 1em;
    }
}

@media (max-width: 768px) {
    .admin .container {
        padding: 0;
    }
    .nav1{
        transition: transform 0.3s ease-out;
    }
    .nav1[style*="display: block"]{
        transform: translateX(0);
    }
    .nav2{
        margin-top: 0px;
    }
    .username{
        display: none;
    }
}

@media (min-width: 768px) {
    .nav1{
        transform: translateX(0);
    }
    .nav{
        display: none;
    }
}
</style>