import { createRouter, createWebHashHistory } from "vue-router";
import { getUserInfo } from "@utils/access"
import { ElMessage } from "element-plus"

import routes from "./routes"

const router = createRouter({
    // 内部提供了 history 模式的实现。为了简单起见，我们在这里使用 hash 模式。
    history: createWebHashHistory(),
    routes, // `routes: routes` 的缩写
})

router.beforeEach((to, from, next) => {
    console.log(`output-to.path`, to.path);

    const userInfo = getUserInfo(); // 提前获取用户信息

    if (to.name === "login") {
        next();
        return;
    }

    if (!userInfo) {
        ElMessage({
            message: "您还没有登录",
            type: 'warning'
        });
        next({ name: "login" });
        return;
    }

    const userRole = userInfo.role;

    if (userRole !== "admin" && to.path.startsWith("/admin")) {
        ElMessage({
            message: "您没有权限访问该页面",
            type: 'warning'
        });
        next(from);
        return;
    }

    if (userRole !== "teacher" && to.path.startsWith("/teacher")) {
        ElMessage({
            message: "您没有权限访问该页面",
            type: 'warning'
        });
        next(from);
        return;
    }

    if (userRole !== "student" && to.path.startsWith("/student")) {
        ElMessage({
            message: "您没有权限访问该页面",
            type: 'warning'
        });
        next(from);
        return;
    }

    next();


});

export default router;