import { createStore } from 'vuex'
import user from "./modules/user";


/*
* Vuex
* 大范围（全局）的组件之间共享数据
*
* */

export default createStore({
// 唯一公共数据源
  state: {
    count: 0,
  },
// 对数据加工，包装，不会改变store，store的数据改变getters内的数据也会跟着改变
  getters: {
    showNum(state) {
      return '当前最新的数量是【'+ state.count +'】'
    }
  },
// mutation 变更store中的数据
  mutations: {
    add(state) {
      state.count++;
    },
    addN(state, step) {
      state.count += step;
    },
    sub(state) {
      state.count--;
    },
    subN(state, step) {
      state.count -= step;
    }
  },
  // 处理异步任务
  actions: {
    addAsync(context, step) {
      // 异步延时1秒，不要在mutations函数中执行异步操作，
      // 需要在actions处理异步任务
      // 通过mutation变更数据
      setTimeout(() => {
        context.commit('addN',step);
      }, 1000)
    }
  },

  modules: {
    user
  }
})
