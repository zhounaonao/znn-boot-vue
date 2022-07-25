<template>
  <div>
    <el-menu
        style="width: 160px; min-height: calc(100vh - 50px)"
        :default-active="this.$route.path"
        router
        class="el-menu-vertical-demo"
    >
      <el-sub-menu index="1">
        <template #title>
          <el-icon><Operation /></el-icon>
          <span>系统管理</span>
        </template>
          <el-menu-item index="/user" v-if="role[0].roleId === 1">
            <el-icon><Avatar /></el-icon>
            用户管理</el-menu-item>
          <el-menu-item index="/book">
            <el-icon><Notebook /></el-icon>
            书籍管理</el-menu-item>
      </el-sub-menu>
      <el-sub-menu index="2">
        <template #title>
          <el-icon><Operation /></el-icon>
          <span>个人信息</span>
        </template>
        <el-menu-item index="/person">
          <el-icon><Avatar /></el-icon>
          个人信息</el-menu-item>
      </el-sub-menu>
    </el-menu>

  </div>
</template>

<script>
import {mapState} from "vuex";

export default {
  name: "Aside",
  computed: {
    ...mapState({
      role: state => state.user.role
    })
  },
  created() {
    let role =  JSON.parse(localStorage.getItem("role"))
    this.$store.commit('user/SET_ROLE', role);
    console.log(this.role)

  },
  data() {
    return {
      user: {}
    }
  }
}
</script>

<style scoped>

</style>