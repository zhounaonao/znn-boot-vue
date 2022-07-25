<template>
  <div style="width: 100%; height: 100%; background-color: #ffffff; overflow: hidden">
    <div style="width: 400px; margin: 15% auto;">
      <div style="color: dodgerblue;font-size: 30px;text-align: center;margin:20px auto">欢迎注册</div>
      <el-form ref="form" :model="form" size="large" :rules="rules">
        <el-form-item prop="userName">
          <!-- suffix-icon="User" 加在后面-->
          <el-input
              prefix-icon="Avatar"
              suffix-icon="User"
              v-model="form.userName"
              placeholder="请输入用户名">
          </el-input>
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
        <el-form-item prop="confirm">
          <el-input
              prefix-icon="Lock"
              v-model="form.confirm"
              type="password"
              placeholder="请确认密码"
              show-password
          />
        </el-form-item>
        <el-form-item prop="email">
          <svg-icon icon-class="email" />
          <el-input
              prefix-icon="Mail"
              v-model="form.email"
              type="email"
              placeholder="请输入邮箱"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="register" style="width: 100%">注册</el-button>

        </el-form-item>
      </el-form>
    </div>

  </div>
</template>

<script>
import request from "@/utils/request";
import {checkEmail, checkMobile, checkUserName} from "@/utils/rules";

export default {
  name: "Register",
  components:{
  },

  data() {
    return {
      form: {},
      rules: {
        userName: [
          // {required: true, message: '请输入用户名', trigger: 'change', validator: checkUserName}
          {required: false,  validator: checkUserName, trigger: 'blur' }
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'}
        ],
        confirm: [
          {required: true, message: '请确认密码', trigger: 'blur'}
        ],
        email: [
          // validator 自定义一个方法校验
          {required: true, message: '请输入正确的邮箱', trigger: 'blur', validator: checkEmail}
        ]
      }
    }
  },
  methods: {
    register() {
      this.$refs['form'].validate((valid) => {
        if (valid){ // 校验是否全部填写了
          if (this.form.password !== this.form.confirm){
            this.$message({
              type: "error",
              message: '两次密码输入不一致'
            })
            return
          }
          request.post("/register", this.form).then(res => {
            console.log(res)
            if (res.code === 200){
              this.$message({
                type: "success",
                message: "注册成功"
              })
              console.log("Aa")
              this.$router.push("/login") // 注册成功之后，跳转到注册
            } else {
              this.$message({
                type: "error",
                message: res.msg
              })
            }
          })
        }
      })

    }
  }
}


</script>

<style scoped>

</style>