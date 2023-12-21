import loginVue from "@/components/login.vue";
import mainPage from "@/components/controlCenter.vue";

import { createRouter, createWebHistory } from "vue-router";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      component: loginVue,
      path: "/",
      name: "登录",
    },
    {
      component: mainPage,
      path: "/controlCenter",
      name: "操作中心",
    }
  ],
});

router.afterEach((to, from, next) => {
  document.title = to.name;
});

export default router;
