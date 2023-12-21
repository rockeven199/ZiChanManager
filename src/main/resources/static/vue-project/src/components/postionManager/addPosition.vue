<template>
  <div class="w-[100%] h-[100%] items-center flex justify-center">
    <el-form
      :model="Form"
      ref="addPositionRef"
      :rules="Rules"
      :inline="false"
      class="w-[40%] mr-[8%] max-w-[400px]"
    >
      <el-form-item prop="positionName" label="位置名：">
        <el-input
          v-model="Form.positionName"
          placeholder="请输入存放位置"
        ></el-input>
      </el-form-item>
      <el-form-item prop="positionStatus" label="位置状态：">
        <el-input
          v-model="Form.positionStatus"
          placeholder="请输入存放位置状态"
        ></el-input>
      </el-form-item>
      <el-form-item class="flex justify-center items-center mt-[30px]">
        <el-button type="primary" @click="onSubmit" class="w-[40%] mx-[5%]"
          >添加位置</el-button
        >
        <el-button class="w-[40%] mx-[5%]" type="danger" @click="reset"
          >清空</el-button
        >
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
export default {
  components: {},
  data() {
    return {
      Title: "添加成功！",
      Form: {
        positionName: "",
        positionStatus: "",
      },
      Rules: {
        positionName: [
          {
            required: true,
            trigger: "blur",
            message: "请输入位置名",
          },
        ],
        positionStatus: [
          {
            required: true,
            trigger: "blur",
            message: "请输入存放位置状态",
          },
        ],
      },
    };
  },
  methods: {
    onSubmit() {
      this.$refs.addPositionRef.validate((valid) => {
        if (valid) {
          var params = new URLSearchParams();
          params.append("position_name", this.Form.positionName);
          params.append("position_status", this.Form.positionStatus);
          this.axios
            .post("http://127.0.0.1:8080/add_Position", params)
            .then((data) => {
              if (data.data == "success") {
                this.$message({
                  message: "添加成功！",
                  type: "success",
                  duration: 2000,
                });
              } else {
                this.$message({
                  message: "添加失败！",
                  type: "error",
                  duration: 2000,
                });
              }
            });
        }
      });
    },
    reset() {
      this.Form.positionName = "";
      this.Form.positionStatus = "";
    },
  },
};
</script>
