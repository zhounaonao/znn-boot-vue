<template>
  <div class="header">
    <el-menu
        class="el-menu-header"
        mode="horizontal"
        :ellipsis="false"
        :default-active="activeIndex"
        @select="handleSelect"
    >
      <div class="search">
        <el-input
            v-model="searchAny"
            placeholder="搜索"
            class="input-with-button"
            size="large"
        >
          <!-- append 在后方加，prepend 在前方加        -->
          <template #append>
            <el-button @click="searchSome()">
              <el-icon><Search /></el-icon>
            </el-button>
          </template>
        </el-input>
      </div>
      <el-menu-item index="0" class="index">指南</el-menu-item>
      <div class="flex-grow" />
      <el-menu-item index="1" class="index">指北</el-menu-item>
      <el-sub-menu index="2" >
        <template #title>更多</template>
        <el-menu-item index="2-1">选项1</el-menu-item>
        <el-menu-item index="2-2">选项2</el-menu-item>
        <el-menu-item index="2-3">选项3</el-menu-item>
        <el-sub-menu index="2-4">
          <template #title>选项4</template>
          <el-menu-item index="2-4-1">4-1</el-menu-item>
          <el-menu-item index="2-4-2">4-2</el-menu-item>
          <el-menu-item index="2-4-3">4-3</el-menu-item>
        </el-sub-menu>
      </el-sub-menu>
      <div>
        <div class="demo-basic--circle">
          <div class="avatar">
            <a href="/person">
              <el-avatar :size="50" :src="avatar" />
            </a>
          </div>
        </div>
      </div>
    </el-menu>
    <div class="logo">
      <a href="/index">LOGO</a>
    </div>
  </div>

</template>

<script>
import {mapState} from "vuex";

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
      activeIndex: "0",
      searchAny: "",
      avatar: "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fc-ssl.duitang.com%2Fuploads%2Fitem%2F202005%2F31%2F20200531000950_yLHSt.thumb.1000_0.jpeg&refer=http%3A%2F%2Fc-ssl.duitang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1659882502&t=8fece08ac33ea011620d3a4d8b8e7170"
    }
  },
  created() {
    let userName = localStorage.getItem("userName");
    if (userName) {
      this.$store.commit('user/SET_USERNAME', userName)
    }
  },
  methods: {
    handleSelect(key, keyPath) {
      console.log(key, keyPath)
    },
    searchSome() {
      console.log(this.searchAny)
    }

  }
}
</script>

<style scoped>

.header {
  height: 80px; line-height: 80px;border-bottom: 1px solid #ccc;
  display: flex; flex-direction:row-reverse; justify-content: space-between;
}

.el-menu-header {

}

.index {
  font-size: 20px;
}

.logo {
  padding-left: 20px;
}

.logo a {
  text-decoration: none;
  color: dodgerblue;
  font-size: 40px;
}
.input-with-button {
  padding-right: 20px;
}

.avatar {
  padding: 15px 15px 0 0;
}

</style>