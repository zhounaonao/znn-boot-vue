<template>
  <div>
    欢迎
    <p>{{count}}</p>
    <el-button type="primary" @click="addCount"> count + 1 </el-button>
    <el-button type="primary" @click="addAsyncCount(1)"> count + 1 addAsync </el-button>
    <el-button type="primary" @click="addAsync(3)"> count + 3 addAsync </el-button>
    <el-button type="primary" @click="addCountN"> count + N </el-button>
    <el-button type="primary" @click="sub"> count - 1 </el-button>
    <el-button type="primary" @click="subN(3)"> count - N </el-button>
    <p>{{this.$store.getters.showNum}}</p>
    <p>{{showNum}}</p>
  </div>
</template>

<script>
import {mapActions, mapGetters, mapMutations, mapState} from "vuex";

export default {
  name: "Index",
  computed: {
    ...mapState(["count"]), // 调用state内的数据第二种方法，使用userName就能使用，当data中有userName则不会生效
    ...mapGetters(['showNum']) //getters第二种方式
  },
  data() {
    return {

    }
  },

  methods: {
    addCount() {
      // 调用 mutations 内的方法
      this.$store.commit('add')
    },
    addCountN() {
      // 传递参数
      this.$store.commit('addN', 3)
    },
    // 第二种方式
    ...mapMutations(['sub','subN']),

    // 异步count加1
    addAsyncCount(n){
      // dispatch 专门用来触发action
      this.$store.dispatch('addAsync',n)
    },
    // 第二种方式
    ...mapActions(['addAsync'])

  }
}
</script>

<style>

</style>