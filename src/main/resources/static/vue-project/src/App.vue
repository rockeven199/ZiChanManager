<script>
import { RouterView } from "vue-router";
import test from "./components/controlCenter.vue";
import "./assets/css/tailwind.css";
import "element-plus/theme-chalk/dark/css-vars.css";
export default {
  components: { test },
  data() {
    return {};
  },
};
</script>

<template>
  <RouterView></RouterView>
  <!-- <test /> -->
</template>

<style>
#app,
html,
body {
  height: 100%;
  widows: 100%;
}
</style>
