<style lang="scss" scoped>
.el-header {
  padding: 0 !important;
}

.el-main {
  padding: 0 !important;
}

span {
  margin: 0px 5px;
}

.el-breadcrumb__item {
  margin: 0 !important;
}

.el-divider {
  margin: 0 !important;
}
</style>

<template>
  <el-dialog
    title="退出登录"
    width="30%"
    v-model="isLogOut"
    :align-center="true"
  >
    <span class="flex justify-center text-red-400">确定要退出登录吗？</span>
    <span slot="footer" class="flex justify-center pt-[20px]">
      <el-button type="danger" @click="toLogOut">确定</el-button>
      <el-button @click="isLogOut = false" type="primary">取消</el-button>
    </span>
  </el-dialog>

  <el-container direction="vertical" class="h-[100%]">
    <!-- 页首 -->
    <el-header height="10%">
      <div class="h-[100%] w-[100%] flex justify-between">
        <div
          class="h-[100%] text-[20px] items-center flex tracking-[1px] w-[20%] font-[600] justify-center"
        >
          资产管理系统
        </div>
        <div
          class="h-[100%] w-fit px-[30px] rounded-md flex items-center justify-center max-[600px]"
        >
          <el-switch
            v-model="themes"
            :active-value="true"
            :inactive-value="false"
            :active-action-icon="Sunny"
            :inactive-action-icon="Moon"
            size="large"
            class="mr-[20px]"
            style="
              --el-switch-on-color: #bbbec5;
              --el-switch-off-color: #202121;
            "
            @change=""
            @click="switchTheme"
          >
          </el-switch>
          <el-popover
            placement="top"
            title=""
            width=""
            trigger="click"
            content=""
          >
            <template #reference>
              <el-avatar
                icon="el-icon-user-solid"
                size="large"
                shape="circle"
                fit="fill"
                :src="initData.userHeader"
              >
              </el-avatar
            ></template>
          </el-popover>
          <div class="flex flex-col ml-[20px] text-[13px]">
            <p>欢迎您：{{ initData.userName }}</p>
            <p>
              <el-tag type="info" size="small">{{ initData.userLevel }}</el-tag>
            </p>
          </div>
          <el-button
            type="danger"
            size="small"
            class="ml-[30px]"
            @click="isLogOut = true"
            >退出登录</el-button
          >
        </div>
      </div>
    </el-header>
    <el-divider direction="horizontal" content-position="left"></el-divider>
    <el-container direction="horizontal" class="h-[80%]">
      <!-- 侧边栏菜单 -->
      <el-menu
        mode="vertical"
        @select="selectItem"
        :collapse="collapseFlag"
        :unique-opened="true"
      >
        <el-menu-item index="11" @click="collapseMenu">
          <el-icon><Menu /></el-icon>
          <span>折叠</span>
        </el-menu-item>
        <el-sub-menu
          :index="item.id.toString()"
          v-for="(item, itemIndex) in menuList"
        >
          <template #title class="menu-title">
            <el-icon v-if="item.icon == 'location'"><Location /></el-icon>
            <el-icon v-if="item.icon == 'TakeawayBox'"><TakeawayBox /></el-icon>
            <el-icon v-if="item.icon == 'User'"><User /></el-icon>
            <span> {{ item.title }}</span>
          </template>
          <el-menu-item
            v-for="(subItem, subItemIndex) in item.submenu"
            :index="item.id + '-' + (Number(subItemIndex) + 1)"
            @click="clickOption(item.id, item.title, subItem)"
            >{{ subItem }}</el-menu-item
          >
        </el-sub-menu>
      </el-menu>
      <!-- 页面内容 -->
      <el-main height="70%">
        <div class="h-[5%] flex items-center w-[100%] p-[5px] border-b-[1px]">
          <el-icon class="mx-[15px]"><HomeFilled /></el-icon>
          <el-breadcrumb :separator-icon="ArrowRight">
            <el-breadcrumb-item
              ><span class="text-xs">{{
                navBar.nowOneTitle
              }}</span></el-breadcrumb-item
            >
            <el-breadcrumb-item
              ><span class="text-xs">{{
                navBar.nowTwoTitle
              }}</span></el-breadcrumb-item
            >
          </el-breadcrumb>
        </div>

        <div class="w-[100%] h-[90%]" v-if="GroupIndex == '1'">
          <addPosition v-if="activeOption == '1-1'" />
          <delPosition v-if="activeOption == '1-2'" />
          <searchPosition v-if="activeOption == '1-3'" />
          <updatePosition v-if="activeOption == '1-4'" />
        </div>
        <div class="w-[100%] h-[100%]" v-if="GroupIndex == '2'">
          <addResource v-if="activeOption == '2-1'" />
          <delResource v-if="activeOption == '2-2'" />
          <searchResource v-if="activeOption == '2-3'" />
          <updateResource v-if="activeOption == '2-4'" />
        </div>
      </el-main>
    </el-container>
    <el-container class="h-[1%] w-[100%]">
      <el-footer height="100%" class="border-t-2"> </el-footer>
    </el-container>
  </el-container>
</template>
<script setup>
import { reactive, onMounted, ref } from "vue";
import {
  Location,
  TakeawayBox,
  Menu,
  User,
  ArrowRight,
  HomeFilled,
  Sunny,
  Moon,
} from "@element-plus/icons-vue";
import "../assets/jsLib/axios.min.js";
import { useDark, useToggle } from "@vueuse/core";

const activeOption = ref("");
const GroupIndex = ref("");
const collapseFlag = ref(true);
const isLogOut = ref(false);
const themes = ref(true);

const navBar = reactive({
  nowOneTitle: "",
  nowTwoTitle: "",
});

const initData = reactive({
  userHeader: "",
  userName: "",
  userLevel: "",
});

const menuList = reactive([
  {
    id: 1,
    title: "存放位置管理",
    submenu: ["添加存放位置", "删除存放位置", "查看存放位置", "修改存放位置"],
    icon: "location",
  },
  {
    id: 2,
    title: "资产管理",
    submenu: ["添加资产", "删除资产", "查看资产信息", "修改资产信息"],
    icon: "TakeawayBox",
  },
  {
    id: 3,
    title: "供应商管理",
    submenu: ["添加供应商", "删除供应商", "查看供应商", "修改供应商"],
    icon: "User",
  },
]);

const toLogOut = () => {
  sessionStorage.removeItem("token", null);
  location.reload();
};

onMounted(() => {
  let token = sessionStorage.getItem("token");
  if (token == null) {
    // location.href = "http://127.0.0.1:5173";
  } else {
    let params = new URLSearchParams();
    params.append("token", sessionStorage.getItem("token"));
    axios.post("http://127.0.0.1:8080/initCenterUser", params).then((data) => {
      initData.userHeader =
        "data:image/webp;base64," + data.data.header_img.toString();
      initData.userName = data.data.username;
      initData.userLevel = data.data.level;
    });
  }
});

const selectItem = (value) => {
  activeOption.value = value;
};

const clickOption = (index, title, subTitle) => {
  GroupIndex.value = index;
  navBar.nowOneTitle = title;
  navBar.nowTwoTitle = subTitle;
};

const collapseMenu = () => {
  if (collapseFlag.value) {
    collapseFlag.value = false;
  } else {
    collapseFlag.value = true;
  }
};

const isDark = useDark();

const switchTheme = () => {
  const toggleDark = useToggle(isDark);
  toggleDark();
};
</script>
