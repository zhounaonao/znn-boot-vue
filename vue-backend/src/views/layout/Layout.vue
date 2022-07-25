<template>
  <div>
    <!--  头部  -->
    <Head />
    <!--  主体  -->
    <div style="display: flex">
      <!--   侧边栏   -->

      <!--   内容区域   -->
      <router-view style="flex: 1"/>
    </div>
  </div>
</template>

<script>

import Head from "@/views/components/Head";

export default {
  name: "Layout",
  components: {
    Head
  }
}
</script>

<style scoped>

</style>