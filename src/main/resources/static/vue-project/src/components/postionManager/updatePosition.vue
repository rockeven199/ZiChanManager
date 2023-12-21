<style>
.el-form-item__content {
  display: flex !important;
  justify-content: center !important;
}
</style>
<template>
  <div class="w-[100%] flex justify-center items-center flex-col mt-[10px]">
    <el-form
      :model="form"
      ref="updatePositionRef"
      :inline="false"
      class="w-[50%] max-w-[400px]"
    >
      <el-form-item prop="positionID" label="位置ID">
        <el-input v-model.trim="form.positionID" disabled></el-input>
      </el-form-item>
      <el-form-item prop="positionName" label="位置名称">
        <el-input v-model.trim="form.positionName"></el-input>
      </el-form-item>
      <el-form-item prop="positionDesc" label="位置描述">
        <el-input v-model.trim="form.positionDesc"></el-input>
      </el-form-item>
      <el-form-item prop="positionStatus" label="存放状态">
        <el-select
          v-model="form.positionStatus"
          clearable
          filterable
          placeholder=" "
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
        <el-button type="primary" @click="onSubmit" class="w-[80px] px-[30px]"
          >提交更改</el-button
        >
        <el-button type="info" @click="refreshData" class="w-[80px] px-[30px]"
          >刷新</el-button
        >
        <el-button type="danger" @click="resetData" class="w-[80px]"
          >清空</el-button
        >
      </el-form-item>
    </el-form>
    <el-table :data="resData" highlight-current-row border :height="tableSize">
      <el-table-column prop="position_id" label="id" width="100" fixed />
      <el-table-column prop="position_name" label="位置名称" width="150" />
      <el-table-column prop="position_status" label="状态" width="150" />
      <el-table-column prop="position_address" label="位置描述" />
      <el-table-column prop="temp" label="操作" width="100">
        <template v-slot="edit">
          <el-button type="primary" size="small" @click="editPosition(edit)"
            >修改</el-button
          >
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      :page-size="7"
      :page-count="pageCount"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      background="background"
      layout="prev, pager, next, jumper"
      class="mt-[20px]"
    />
  </div>
</template>
<script>
export default {
  data() {
    return {
      tableSize: 0,
      pageTotal: 0,
      page_Size: 10,
      pageCount: 0,
      resData: [],
      nowPages: 1,
      form: {
        positionName: "",
        positionStatus: "",
        positionDesc: "",
        positionID: "",
      },
      status: ["正常", "已满", "警告", "启用", "禁用"],
    };
  },

  methods: {
    editPosition(value) {
      this.form.positionName = value.row.position_name;
      this.form.positionDesc = value.row.position_address;
      this.form.positionStatus = value.row.position_status;
      this.form.positionID = value.row.position_id;
    },
    refreshData() {
      var param = new URLSearchParams();
      param.append("pages_num", this.nowPages);
      this.axios
        .post("http://127.0.0.1:8080/data_page_group", param)
        .then((res) => {
          if (res.data != false) {
            this.resData = res.data;
          } else {
            this.resData = [];
          }
        });
    },
    onSubmit() {
      if (this.form.positionID != "") {
        var params = new URLSearchParams();
        params.append("search_position_desc", this.form.positionDesc);
        params.append("search_position_name", this.form.positionName);
        params.append("search_position_status", this.form.positionStatus);
        params.append("search_position_id", this.form.positionID);
        this.axios
          .post("http://127.0.0.1:8080/update_Position", params)
          .then((data) => {
            if (data.status == 200) {
              this.$message({
                message: "修改成功！！",
                type: "success",
                showClose: true,
                duration: 3000,
              });
            }
          });
      } else {
        this.$message({
          message: "提交内容不能为空！！",
          type: "error",
          duration: 1000,
        });
      }
    },
    resetData() {
      this.form.positionName = "";
      this.form.positionStatus = "";
      this.form.positionDesc = "";
      this.form.positionID = "";
    },
    handleSizeChange(val) {
      this.nowPages = val;
      var param = new URLSearchParams();
      param.append("pages_num", val);
      this.axios
        .post("http://127.0.0.1:8080/data_page_group", param)
        .then((res) => {
          if (res.data != false) {
            this.resData = res.data;
          } else {
            this.resData = [];
          }
        });
    },

    handleCurrentChange(val) {
      this.nowPages = val;
      var param = new URLSearchParams();
      param.append("pages_num", val);
      this.axios
        .post("http://127.0.0.1:8080/data_page_group", param)
        .then((res) => {
          if (res.data != false) {
            this.resData = res.data;
          } else {
            this.resData = [];
          }
        });
    },
  },

  mounted() {
    this.tableSize = parseInt(window.screen.availHeight * 0.37);
    var param = new URLSearchParams();
    param.append("pages_num", 1);
    this.axios
      .post("http://127.0.0.1:8080/data_page_group", param)
      .then((res) => {
        if (res.data != false) {
          this.resData = res.data;
          this.pageCount = res.data.length;
        } else {
          this.resData = [];
        }
      });
  },
};
</script>
