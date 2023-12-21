<template>
  <el-form
    :model="form"
    ref="addRef"
    :rules="rules"
    label-width="80px"
    :inline="false"
    size="normal"
  >
    <el-form-item label="">
      <el-input v-model="form.resourceName"></el-input>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="onSubmit">立即创建</el-button>
      <el-button>取消</el-button>
    </el-form-item>
  </el-form>
</template>
<script>
export default {
  components: {},
  data() {
    return {
      form: {
        resourceName: "",
      },
    };
  },
  methods: {
    onsubmit() {
      this.$refs.addRef.validate((valid) => {
        if (valid) {
          var params = new URLSearchParams();
          params.append("position_name", this.form.resourceName);
          this.$http
            .post("http://127.0.0.1:8080/selectByID", params)
            .then((data) => {
              console.log(data.data);
            });
        }
      });
    },
  },
};
</script>
<style></style>
