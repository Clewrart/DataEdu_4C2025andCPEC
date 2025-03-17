<template>
    <div class="bg">
      <div class="page">
          <div class="form">
            <div class="title">登录</div>
            <div class="input-group">
              <el-input class="input" size="large" v-model="username" placeholder="请输入用户账号" />
            </div>
            <div class="input-group">
              <el-input class="input" size="large" v-model="password" type="password" placeholder="请输入密码" />
            </div>

            <div class="tips">如果您不知道用户账号或者忘记了密码，请联系授课教师或系统管理员！</div>

            <div class="cb">
              <el-checkbox v-model="agreeTerms">我了解此系统使用规则和方法</el-checkbox>
            </div>
            <div class="cb">
              <el-checkbox v-model="agreeNoSharing">我保证不向他人传播题目和答案</el-checkbox>
            </div>

<!--登录防止多次请求耗资源，增加加载状态loading waitLogin函数-->
            <div class="button">
               <div class="button">
    <el-button :loading="loading" type="primary" size="large"  @click="waitLogin" :disabled="loading">登录</el-button>
            </div>
          </div>
        </div>
      </div>
    </div>
</template>


<script setup>
import { ref } from "vue";
import { login, getUserInfo } from "@api/user.js";
import { setAccess, setUserInfo } from "@utils/access.js";
import { ElMessage } from "element-plus";
import { useRouter } from "vue-router";

const router = useRouter();
const username = ref("");
const password = ref("");
const agreeTerms = ref(false);
const agreeNoSharing = ref(false);
const loading = ref(false);


async function waitLogin() {
  let status;

  switch (true) {
    case !username.value || !password.value:
      status = "EMPTY_CREDENTIALS"; // 用户名或密码为空
      break;
    case !agreeTerms.value || !agreeNoSharing.value:
      status = "AGREEMENT_REQUIRED"; // 未同意条款
      break;
    default:
      status = "VALID"; // 输入有效
  }

  switch (status) {
    case "EMPTY_CREDENTIALS":
      showMessage("warning", "用户名和密码不能为空！");
      return;
    case "AGREEMENT_REQUIRED":
      showMessage("warning", "请同意所有系统使用要求");
      return;
    case "VALID":
      break;
  }

  //登录操作
  loading.value = true; //开始加载
  try {
    console.log("尝试登录...");
    const res = await login(username.value, password.value);
    console.log("登录接口响应：", res);
    if (res.data.code === 200) {
      const token = res.data.data.token;
      console.log("获取到的 token：", token);
      setAccess({token});
      const infoRes = await getUserInfo(token);
      console.log("用户信息接口响应：", infoRes);
      const info = infoRes.data.data;
      setUserInfo(info);

      showMessage("success", "登录成功");
      router.push({name: info.role});
    } else {
      showMessage("error", "用户名或密码错误");
      console.log("登录失败响应：", res);
    }
  } catch (error) {
    console.error("请求过程中发生错误：", error);
    showMessage("error", "登录请求失败，请稍后再试");
  }
}

function showMessage(type, message) {
  ElMessage({
    type: type,
    message: message,
  });
}

  document.title = "欢迎使用机器学习与图像处理综合教学实验平台";
</script>


<style lang="less" scoped>
@primary-color: #409EFF;
@background-gradient: linear-gradient(135deg, #4A90E2, #9013FE);
@font-size-large: 40px;
@form-width: 90%;

body {
  background: @background-gradient;
  color: white;
  height: 100vh;
  margin: 0;
  display: flex;
  justify-content: center;
  align-items: center;
}

  .bg {
    height: 100%;
    display: flex;
    justify-content: center;
    background-size: cover;
    background-position: center;
  }

.page {
  display: inline-flex;
  height: auto;
  margin: auto;
  align-items: center;
  justify-content: center;
  background: rgba(255, 255, 255, 0.8);
  border-radius: 15px;
  padding: 1% 1% 2% 1%;
}

.logo {
  width: 250px;
  height: 250px;
  justify-content: center;
  align-items: center;
  margin: 2px 15px 2px 2px;

}

.cb {
  display: flex;
  align-items: center;
  margin: 5px 0;
}

.form {
  width: 100vh;
  margin: 0 auto;

  .title {
    font-weight: bold;
    font-size: @font-size-large;
    padding: 20px;
    text-align: center;
  }

  .input-group {
    margin: 1vh 0;
    width: 100%;
    border-radius: 40px;
    padding: 5px;
    box-sizing: border-box;
    font-size: 20px;
    font-weight: 200
  }

  .tips {
    color: #333333;
    font-size: medium;
    text-align: center;
    margin: 10px;
  }

  .button {
    display: flex;
    justify-content: center;

    button {
      background-color: darkblue;
      color: white;
      border: none;
      border-radius: 25px;
      width: 100%;
      padding: 10px 20px;
      font-size: 18px;
      cursor: pointer;
      transition: background-color 0.3s, transform 0.2s;

      &:hover {
        background-color: darken(@primary-color, 10%);
        transform: scale(1.05);
      }
    }
  }
}

@media (max-width: 768px) {
  .page {
    flex-direction: column;
    padding: 20px;
    height: 85%;
    align-items: center;
  }

  .logo {
    display: none;
  }

  .form {
    width: 90%;
    height:90%
  }

  .title {
    font-size: 30px;
  }
}
</style>

