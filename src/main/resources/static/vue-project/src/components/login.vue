<style>
.el-form-item__content {
  margin: 0 !important;
}

p {
  letter-spacing: 1px;
}
</style>
<template>
  <el-switch
    v-model="themes"
    :active-value="true"
    :inactive-value="false"
    active-action-icon="Sunny"
    inactive-action-icon="Moon"
    size="large"
    class="mt-[10px] ml-[20px]"
    style="--el-switch-on-color: #bbbec5; --el-switch-off-color: #202121"
    @click="switchThemes"
  >
  </el-switch>

  <el-container class="flex flex-col">
    <el-row class="w-[100%] h-[100%] mt-[10%]">
      <el-col class="flex justify-center my-[30px] text-[20px] font-bold"
        ><p>系统登录</p></el-col
      >
      <el-col :span="24" class="flex justify-center items-center">
        <el-form
          :model="form"
          ref="loginRef"
          :rules="loginRules"
          label-width="80px"
          :inline="false"
        >
          <el-form-item label="" class="break-keep my-[10px]" prop="username">
            <el-input
              v-model="form.username"
              clearable
              size="large"
              placeholder="请输入用户名"
            >
            </el-input>
          </el-form-item>
          <el-form-item label="" class="break-keep my-[10px]" prop="password">
            <el-input
              v-model="form.password"
              clearable
              show-password
              size="large"
              placeholder="请输入密码"
            ></el-input>
          </el-form-item>
          <el-form-item label="" class="break-keep my-[10px]" prop="taskID">
            <el-input
              v-model="form.taskID"
              size="large"
              placeholder="请输入任务ID"
            ></el-input>
          </el-form-item>
          <el-form-item
            label=""
            class="break-keep my-[10px] w-[100%]"
            prop="authCode"
          >
            <div class="flex">
              <el-input
                v-model="form.authCode"
                size="large"
                placeholder="请输入验证码"
                class="w-[60%] max-[60%]"
              ></el-input>
              <img
                src="../assets/image/th.jpg"
                alt=""
                class="w-[100px] ml-[20px]"
              />
            </div>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" class="w-[100%]" @click="onSubmit"
              >登录</el-button
            >
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
  </el-container>
</template>
<script>
import { useDark, useToggle } from "@vueuse/core";
const isDark = useDark();
export default {
  data() {
    return {
      themes: true,
      loginTipsTitle: "",
      loginType: "",
      form: {
        username: "",
        password: "",
        taskID: "",
        authCode: "",
        authPath: "../assets/image/th.jpg",
      },
      loginRules: {
        username: [
          {
            required: true,
            trigger: "blur",
            message: "请输入用户名",
          },
        ],
        password: [
          {
            trigger: "blur",
            required: true,
            message: "请输入密码",
          },
        ],
        taskID: [
          {
            trigger: "blur",
            required: true,
            message: "请输入任务ID",
          },
        ],
        authCode: [
          {
            trigger: "blur",
            required: true,
            message: "请输入验证码",
          },
        ],
      },
    };
  },
  methods: {
    switchThemes() {
      const toggleDark = useToggle(isDark);
      toggleDark();
    },
    onSubmit() {
      this.$refs.loginRef.validate((valid) => {
        if (valid) {
          var params = new URLSearchParams();
          params.append("Username", this.form.username);
          params.append("Password", this.form.password);
          params.append("taskID", this.form.taskID);
          this.axios
            .post("http://127.0.0.1:8080/authUser", params)
            .then((data) => {
              let url = data.data.split(",");
              if (url.length == 2) {
                sessionStorage.setItem("token", url[1]);
                this.$message({
                  message: "登录成功！",
                  type: "success",
                  showClose: true,
                  duration: 3000,
                });
                setTimeout(() => {
                  location.href = url[0];
                }, 1000);
              } else {
                if (data.data == "No") {
                  this.$message({
                    message: "登录失败，用户名或密码错误！",
                    type: "error",
                    showClose: true,
                    duration: 3000,
                  });
                }
              }
            })
            .catch((err) => {
              this.$message({
                message: "登录失败，服务器错误！",
                type: "error",
                showClose: true,
                duration: 3000,
              });
            });
        }
      });
    },
  },
};
</script>
