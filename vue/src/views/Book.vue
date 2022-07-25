<template>
  <div class="home" style="padding: 10px">
    <!--  功能区域  -->
    <div style="margin: 10px 0">
      <el-button type="primary" @click="add" >
        <el-icon><Plus /></el-icon>
        添加</el-button>
      <el-button type="primary">导入</el-button>
      <el-button type="primary">导出</el-button>
    </div>
    <!--  搜索区域  -->
    <div style="margin: 10px 0">
      <el-input v-model="search" placeholder="请输入关键字" style="width: 20%" clearable/>
      <el-button type="primary" style="margin-left: 5px" @click="load">
        <el-icon style="vertical-align: middle">
          <Search />
        </el-icon>
        <span style="vertical-align: middle">查询</span>
        </el-button>
    </div>
    <el-table :data="tableData"
              border
              stripe
              style="width: 100%">
      <!--   el-table-column是列的定义{prop:传数据的名字; label:显示出来的名字; width:宽度，不写就自适应}   -->
      <el-table-column
          prop="bookId"
          label="编号"
          sortable />
      <el-table-column
          prop="bookName"
          label="书名" />
      <el-table-column
          prop="author"
          label="作者" />
      <el-table-column
          prop="cover"
          label="封面"
      >
        <template #default="scope">
          <el-image
            style="width: 50px; height: 50px;"
            :src="scope.row.cover"
            :preview-src-list="[scope.row.cover]"
            :preview-teleported="true"
          >
          </el-image>
        </template>
      </el-table-column>
      <el-table-column
          prop="publishingHouse"
          label="出版社" />
      <el-table-column
          prop="publicationTime"
          label="出版时间" />
      <el-table-column
          prop="createTime"
          label="创建时间" />
      <el-table-column
          prop="modifyTime"
          label="更新时间" />
      <el-table-column fixed="right" label="操作" width="130">
        <template #default="scope">
          <el-button type="primary" text size="small" @click="handleEdit(scope.row)">编辑</el-button>
          <el-popconfirm title="确认删除吗?" @confirm="handleDelete(scope.row.bookId)">
            <template #reference>
              <el-button type="danger" text size="small" >删除</el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <div style="margin: 10px 0">
      <el-pagination
          v-model:currentPage="currentPage"
          v-model:page-size="pageSize"
          :page-sizes="[10, 20, 30, 40]"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
      />

      <el-dialog v-model="dialogFormVisible" title="新增">
        <el-form :model="form" label-width="120px">
          <el-form-item label="书名">
            <el-input v-model="form.bookName" autocomplete="off" style="width: 80%" />
          </el-form-item>
          <el-form-item label="作者">
            <el-input v-model="form.author" autocomplete="off" style="width: 80%" />
          </el-form-item>
          <el-form-item label="封面">
            <el-upload ref="upload" action="/api/files/upload" :on-success="filesUploadSuccess">
              <el-button type="primary">点击上传</el-button>
            </el-upload>
          </el-form-item>
          <el-form-item label="出版社">
            <el-input v-model="form.publishingHouse" autocomplete="off" style="width: 80%" />
          </el-form-item>
          <el-form-item label="出版时间">
            <el-date-picker
                v-model="form.publicationTime"
                type="datetime"
                placeholder="Pick a Date"
                format="YYYY/MM/DD hh:mm:ss"
            />
          </el-form-item>
        </el-form>
        <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="save"
        >确定</el-button>
      </span>
        </template>
      </el-dialog>
    </div>

  </div>
</template>

<script>
import request from "@/utils/request";

const formLabelWidth = '140px'
export default {
  name: 'Book',
  components: {

  },
  data(){
    return {
      form: {},
      dialogFormVisible: false,
      search: '',
      currentPage: 1,
      pageSize: 10,
      total:10,
      tableData : [

      ]
    }
  },
  created() {
    this.load()
  },
  methods: {
    filesUploadSuccess(res) {
      console.log(res)
      this.form.cover = res.data
    },
    load() {
      request.get("/book", {
        params: { // 加上这个以保证我们的查询可以加入search
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          search: this.search
        }

      }).then(res => {
        console.log(res)
        this.tableData = res.data.records
        this.total = res.data.total
      })
    },
    add() {
      this.dialogFormVisible = true
      this.form = {}
      this.$refs['upload'].clearFiles() // 清楚历史文件列表

    },
    save() {
      if (this.form.bookId){ // 有id就更新
        request.put("/book", this.form).then(res => { // => es6的语法
          console.log(res)
          if (res.code === '0'){
            this.$message({
              type: "success",
              message: "更新成功"
            })
          } else {
            this.$message({
              type: "error",
              message: res.msg
            })
          }
          this.load()
        })

      } else { // 没id就新增
        request.post("/book", this.form).then(res => { // => es6的语法
          console.log(res)
          if (res.code === '0'){
            this.$message({
              type: "success",
              message: "新增成功"
            })
          } else {
            this.$message({
              type: "error",
              message: res.msg
            })
          }
          this.load()
        })

      }

      this.dialogFormVisible = false
    },
    handleEdit(row) {
      this.form = JSON.parse(JSON.stringify(row)) // 避免深拷贝，取消后还在
      this.dialogFormVisible = true
      this.$nextTick(() => { // 处理未来元素
        this.$refs['upload'].clearFiles() // 清楚历史文件列表
      })
    },
    handleDelete(bookId){
      console.log(bookId)
      request.delete("/book/" + bookId).then(res => {
        console.log(res)
        if (res.code === '0'){
          this.$message({
            type: "success",
            message: "删除成功"
          })
        } else {
          this.$message({
            type: "error",
            message: res.msg
          })
        }
        this.load()
      })

    },
    handleSizeChange() {
      this.load()
    },
    handleCurrentChange(currentPage) {
      this.currentPage = currentPage
      this.load()
    }
  },
}
</script>
