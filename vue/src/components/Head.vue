<template>
  <div style="height: 50px; line-height: 50px;border-bottom: 1px solid #ccc; display: flex">
    <div style="width: 200px; padding-left: 30px; font-weight: bold; color: dodgerblue">后台管理</div>
    <div style="flex: 1">
    </div>
    <div style="width: 100px; margin: 13px 0px">
      <el-dropdown>
        <span class="el-dropdown-link" style="font-size: 20px">
          {{ count }}
            <el-icon class="is-loading" color="#409EFC">
              <Loading />
            </el-icon>
          {{ userName }}
        </span>
        <el-icon><ArrowDownBold /></el-icon>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item @click="this.$router.push('/person')">个人信息</el-dropdown-item>
            <el-dropdown-item @click="exit">退出系统</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </div>
</template>

<script>
import {mapState} from "vuex";
import {removeToken} from "@/utils/auth";

export default {
  name: "Head",
  computed: {
    ...mapState(["count"]), // 调用state内的数据第二种方法，使用userName就能使用，当data中有userName则不会生效
    ...mapState({
      userName: state => state.user.userName
    }) // 调用state内的数据第二种方法，使用userName就能使用，当data中有userName则不会生效
  },
  data() {
    return {
      user: {},

    }
  },
  created() {
    let userName = localStorage.getItem("userName");
    if (userName){
      this.$store.commit('user/SET_USERNAME', userName)
    }
  },
  methods: {
    exit() {
      removeToken()
      localStorage.clear()
      this.$router.push('/login')
    }
  }
}
</script>

<style scoped>

</style>