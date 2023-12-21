import { onMounted } from 'vue';
<style></style>
<template>
  <div class="w-[100%] flex justify-center h-[30%] items-center">
    <el-form
      :model="form"
      ref="searchPositionRef"
      :rules="rules"
      :inline="false"
      class="w-[50%] max-w-[400px]"
    >
      <el-form-item prop="searchName" label="位置名称">
        <el-input
          v-model.trim="form.searchName"
          placeholder="请输入查找位置名称"
        ></el-input>
      </el-form-item>
      <el-form-item prop="status" label="存放信息">
        <el-select
          v-model="form.status"
          placeholder="状态类型"
          clearable
          filterable
        >
          <el-option
            v-for="(item, index) in status"
            :key="index"
            :label="item"
            :value="item"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item class="w-[100%] mt-[20px]">
        <el-button type="danger" @click="onSubmit" class="w-[80px]"
          >查找</el-button
        >
        <el-button type="info" @click="checkAll">查看全部位置信息</el-button>
        <el-button
          type="primary"
          @click="(form.searchName = ''), (form.status = ''), (resData = [])"
          class="w-[80px]"
          >清空</el-button
        >
      </el-form-item>
    </el-form>
  </div>
  <div>
    <el-table :data="resData" :height="tableSize" highlight-current-row border>
      <el-table-column prop="position_id" label="id" width="100" fixed />
      <el-table-column prop="position_name" label="位置名称" width="150" />
      <el-table-column prop="position_status" label="状态" width="150" />
      <el-table-column prop="position_address" label="位置描述" />
    </el-table>
  </div>
</template>
<script>
export default {
  data() {
    return {
      tableSize: 0,
      pageTotal: 0,
      page_Size: 10,
      resData: [],
      nowPages: 1,
      url: "http://127.0.0.1:8080/search_all_Position",
      status: ["正常", "已满", "警告", "启用", "禁用"],
      form: {
        searchName: "",
        status: "",
      },
      rules: {
        searchName: [
          {
            required: false,
            trigger: "blur",
            message: "请输入要查找的位置名",
            axios,
          },
        ],
        status: [
          {
            required: false,
            trigger: "blur",
            message: "请输入要查找状态类型",
          },
        ],
      },
    };
  },
  methods: {
    checkAll() {
      this.axios
        .post("http://127.0.0.1:8080/search_all_Position")
        .then((data) => {
          this.resData = data.data;
        });
    },
    onSubmit() {
      this.$refs.searchPositionRef.validate((vaild) => {
        if (!this.form.searchName && !this.form.status) {
          this.$message({
            message: "查询条件至少选填一项！！！",
            type: "error",
            duration: 2000,
          });
        } else {
          if (this.form.searchName != "") {
            var params = new URLSearchParams();
            params.append("search_position_name", this.form.searchName);
            this.axios
              .post("http://127.0.0.1:8080/search_all_Position_By_Name", params)
              .then((data) => {
                this.resData = data.data;
              });
          } else if (this.form.status != "") {
            var params = new URLSearchParams();
            params.append("search_position_status", this.form.status);
            this.axios
              .post(
                "http://127.0.0.1:8080/search_all_Position_By_Status",
                params
              )
              .then((data) => {
                this.resData = data.data;
              });
          } else {
            var params = new URLSearchParams();
            params.append("search_position_name", this.form.searchName);
            params.append("search_position_status", this.form.status);
            this.axios
              .post("http://127.0.0.1:8080/search_all_Position", params)
              .then((data) => {
                this.resData = data.data;
              });
          }
        }
      });
    },
  },
  onMounted() {
    this.tableSize = parseInt(window.screen.availHeight * 0.55) - 3.3;
  },
};
</script>
