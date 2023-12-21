<style>
.el-form-item__content {
  @apply flex justify-evenly;
}
</style>
<template>
  <div class="w-[100%] flex justify-center h-[100%] items-center">
    <el-form
      :model="form"
      ref="delPositionRef"
      :rules="rules"
      :inline="false"
      class="w-[50%] max-w-[400px]"
    >
      <el-form-item prop="delName" label="存放位置名称">
        <el-input
          v-model="form.delName"
          placeholder="请输入存放位置名称"
        ></el-input>
      </el-form-item>
      <el-form-item class="w-[100%]">
        <el-button type="danger" @click="onSubmit">删除位置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
export default {
  data() {
    return {
      form: {
        delName: [],
      },
      rules: {
        delName: [
          {
            required: true,
            trigger: "blur",
            message: "请输入要删除的位置名",
          },
        ],
      },
    };
  },
  methods: {
    onSubmit() {
      var parma = new URLSearchParams();
      parma.append("delName", this.form.delName);
      this.axios
        .post("http://127.0.0.1:8080/del_Position", parma)
        .then((data) => {
          if (data.data == "success") {
            this.$message({
              message: "删除成功！",
              type: "success",
              duration: 2000,
            });
          } else {
            this.$message({
              message: "删除失败！",
              type: "error",
              duration: 2000,
            });
          }
        });
    },
  },
};
</script>
<style></style>
