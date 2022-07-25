import {getToken} from "@/utils/auth";

// 唯一公共数据源
const state = {
  userName: '',
  role: [{roleId: 2}]
}

// 对数据加工，包装，不会改变store，store的数据改变getters内的数据也会跟着改变
const getters= {
  // showNum(state) {
  //   return '当前最新的数量是【'+ state.count +'】'
  // }
}
// mutation 变更store中的数据
const mutations= {
  SET_USERNAME: (state, userName) => {
    state.userName = userName
  },
  SET_ROLE: (state, role) => {
    state.role = role
  },
}
// 处理异步任务
const actions= {
  // addAsync(context, step) {
  //   // 异步延时1秒，不要在mutations函数中执行异步操作，
  //   // 需要在actions处理异步任务
  //   // 通过mutation变更数据
  //   setTimeout(() => {
  //     context.commit('addN',step);
  //   }, 1000)
  // }
}

export default {
  namespaced: true,
  state,
  getters,
  actions,
  mutations,
  modules: {

  }
}