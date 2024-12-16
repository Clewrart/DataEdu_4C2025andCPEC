import axios from "axios"
import { getAccess, clearStorage } from "@utils/access";
import { ElMessage } from "element-plus";
import router from "@router";
const instance = axios.create({
    baseURL: '/api',
});

instance.interceptors.request.use(
    config => {
        console.log(config.url);
        if (!config.url.includes("login")) {
            const access = getAccess();
            if (access) {
                config.headers.Authorization = access.token;
            }
        }

        return config;
    },
    error => {
        return Promise.reject(error);
    }
);

instance.interceptors.response.use(
    response => {
        return response
    },
    error => {
        // 如果返回的响应数据中包含 code 为 401，表示登录信息已过期
        if (error.response.status === 401) {
            ElMessage({
                message: "登录信息已失效,请重新登录！",
                type: "warning"
            });
            // 清除本地存储中的登录信息
            clearStorage();
            console.log(`output-router`,router)
            // 重定向到登录页面
            router.push("/login");
            console.log(`output-router`,router)
            // 返回一个 rejected Promise，以便外部调用方能够捕获错误
            return Promise.reject("登录信息已过期,请重新登录！");
        }
        // 返回响应数据
        return Promise.reject(error);
    }
);

export default instance;