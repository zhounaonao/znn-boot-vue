<template>
  <div>
    <div>
      <h1 style="text-align: center;">个人简介</h1>
    </div>
    <div style="float: left; width: 30%;height: 450px;">
      <!--图片-->
      <div style="height: 400px; background-color: bisque">
        <el-upload
            action="/api/files/upload"
            :on-success="filesUploadSuccess"
        >
          <img v-if="imageUrl" :src="imageUrl" class="avatar" />
          <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
        </el-upload>
      </div>
      <div style="text-align: center">
        <el-button size="normal" text>
          Upload<el-icon class="el-icon--right"><Upload /></el-icon>
        </el-button>
      </div>
    </div>
    <div style="float: right; width: 70%; height: 450px;padding-top: 50px; text-align: center" >
      <el-form :model="form" size="large" label-width="120px" style="width: 100%" :disabled="disabled">
        <el-form-item label="用户名">
          <el-input style="width: 80%" v-model="user.username" :placeholder="user.username"/>
        </el-form-item>
        <el-form-item label="密码">
          <el-input type="password" style="width: 80%" v-model="user.password"/>
        </el-form-item>
        <el-form-item label="昵称">
          <el-input style="width: 80%" v-model="user.nickName"/>
        </el-form-item>
        <el-form-item label="年龄">
          <el-input style="width: 80%" v-model="user.age"/>
        </el-form-item>
        <el-form-item label="性别">
          <el-radio-group v-model="user.sex">
            <el-radio label="男" />
            <el-radio label="女" />
            <el-radio label="未知" />
          </el-radio-group>
        </el-form-item>
        <el-form-item label="地址">
          <el-input type="textarea" style="width: 80%" v-model="user.address"/>
        </el-form-item>
      </el-form>
    </div>
    <div style="text-align: center;">
      <el-button size="normal" type="primary" @click="edit">编辑</el-button>
      <el-button size="normal" type="primary" @click="save">保存</el-button>
    </div>
  </div>
</template>

<script>
export default {
  name: "Person",
  computed: {

  },
  data() {
    return {
      username: '',
      user: {},
      dialogFormVisible: true,
      form: {},
      disabled: true
    }
  },
  created() {
    let loginUser = sessionStorage.getItem("loginUser");
    if (loginUser) {
      this.username = JSON.parse(loginUser).username
      this.user = JSON.parse(loginUser)
    }
    console.log(this.username)
    console.log(this.user)
  },
  methods: {
    edit() {
      this.disabled = false
    },
    save() {
      this.disabled = true

    }
  }
}
</script>

<style scoped>

</style>