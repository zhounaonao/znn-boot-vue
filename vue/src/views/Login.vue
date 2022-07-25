<template>
  <div style="width: 100%; height: 100%; background-color: #ffffff; overflow: hidden">
    <div style="width: 400px; margin: 15% auto;">
      <div style="color: dodgerblue;font-size: 30px;text-align: center;margin:20px auto">欢迎登录</div>
      <el-form :model="form" size="large" :rules="rules" ref="form">
        <el-form-item prop="userName">
          <el-input
              prefix-icon="Avatar"
              v-model="form.userName"
              placeholder="请输入用户名" />
        </el-form-item>
        <el-form-item prop="password">
          <el-input
              prefix-icon="Lock"
              v-model="form.password"
              type="password"
              placeholder="请输入密码"
              show-password
          />
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="login" style="width: 100%">登录</el-button>
          <el-button @click="register" style="width: 100%;margin: 20px 0">注册</el-button>
        </el-form-item>
      </el-form>
    </div>

  </div>
</template>

<script>
import request from "@/utils/request";
import { reactive, ref } from 'vue'
import {mapMutations} from "vuex";
import {setToken} from "@/utils/auth";

export default {
  name: "Login",
  components: {},

  data() {
    return {
      form: {},
      rules : reactive({
        userName: [
          {required: true, message: '请输入用户名', trigger: 'blur'} // trigger: 'blur'失去焦点验证，change值发生变化验证
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'},
        ],
      }),
    }
  },
  methods: {
    login() {
      this.$refs['form'].validate((valid) => {
        if (valid){
          request.post("/login", this.form).then(res => {
            console.log(res)
            if (res.code === 200){
              this.$message({
                type: "success",
                message: "登录成功"
              })
              // localStorage.setItem("token", res.data.token)
              setToken(res.data.token)
              localStorage.setItem("userName", res.data.userName)
              localStorage.setItem("loginUser", JSON.stringify(res.data.loginUser))
              if (res.data.role){
                localStorage.setItem("role", JSON.stringify(res.data.role))
                this.$store.commit('user/SET_ROLE', res.data.role)
              }
              this.$store.commit('user/SET_USERNAME', res.data.userName)
              this.$router.push("/index") // 登陆成功之后，跳转到主页
            } else {
              this.$message({
                type: "error",
                message: res.msg
              })
            }
          })
        }
      })

    },
    register() {
      this.$router.push("/register") // 注册
    },
  }
}


</script>

<style scoped>

</style>