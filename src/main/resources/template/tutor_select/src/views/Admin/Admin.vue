<template>
  <div class="admin-wrapper">
    <div class="admin-wrap">
      <div class="admin">
        <div class="set-time">
          <h1>设置第二轮选导师时间</h1>

          <div>第一轮导师选择将于xxxxxxx结束,请在结束前完成设置!</div>
          <div>
            <el-date-picker
              v-model="value2"
              type="datetimerange"
              :picker-options="pickerOptions"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              align="right"
            >
            
            </el-date-picker>
            <el-button type="primary" style="margin-left:1.5%">确定</el-button>
          </div>
        </div>
        <div class="up-down-load">
          <div class="upload">
            <h1>上传学生/导师信息</h1>
            <div style="margin-bottom:2%">
              <el-upload
                class="upload-demo"
                drag
                action="https://jsonplaceholder.typicode.com/posts/"
                multiple
              >
                <i class="el-icon-upload"></i>
                <div class="el-upload__text">
                  将文件拖到此处，或<em>点击上传</em>
                </div>
                <div class="el-upload__tip" slot="tip">
                  只能上传excel文件，且不超过500kb
                </div>
              </el-upload>
            </div>
          </div>
          <div class="download">
            <h1>下载最终选取结果</h1>
            <el-button type="primary" style="margin-top:11%">点击下载</el-button>
          </div>
        </div>

        <div class="change-pass">
          <h1>修改密码</h1>
          <div class="change-pass">
            <el-form
              :model="ruleForm"
              status-icon
              :rules="rules"
              ref="ruleForm"
              label-width="100px"
              class="demo-ruleForm"
            >
              <el-form-item label="原密码" prop="oldPass">
                <el-input v-model.number="ruleForm.oldPass"></el-input>
              </el-form-item>
              <el-form-item label="新密码" prop="pass">
                <el-input
                  type="password"
                  v-model="ruleForm.pass"
                  autocomplete="off"
                ></el-input>
              </el-form-item>
              <el-form-item label="确认新密码" prop="checkPass">
                <el-input
                  type="password"
                  v-model="ruleForm.checkPass"
                  autocomplete="off"
                ></el-input>
              </el-form-item>

              <el-form-item>
                <el-button type="primary" @click="submitForm('ruleForm')"
                  >提交</el-button
                >
                <el-button @click="resetForm('ruleForm')">重置</el-button>
              </el-form-item>
            </el-form>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Admin",
  data() {
    var checkOldPass = (rule, value, callback) => {
      if (!value) {
        return callback(new Error("旧密码不能为空"));
      }
      setTimeout(() => {
        if (value != this.prePass) {
          callback(new Error("原密码不正确"));
        } else {
          callback();
        }
      }, 1000);
    };
    var validatePass = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入密码"));
      } else if (value == this.prePass) {
        callback(new Error("请与原密码不同"));
      } else {
        if (this.ruleForm.checkPass !== "") {
          this.$refs.ruleForm.validateField("checkPass");
        }
        callback();
      }
    };
    var validatePass2 = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请再次输入密码"));
      } else if (value !== this.ruleForm.pass) {
        callback(new Error("两次输入密码不一致!"));
      } else {
        callback();
      }
    };
    return {
      pickerOptions: {
        shortcuts: [
          {
            text: "最近一周",
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
              picker.$emit("pick", [start, end]);
            },
          },
          {
            text: "最近一个月",
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
              picker.$emit("pick", [start, end]);
            },
          },
          {
            text: "最近三个月",
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
              picker.$emit("pick", [start, end]);
            },
          },
        ],
      },
      value1: [new Date(2000, 10, 10, 10, 10), new Date(2000, 10, 11, 10, 10)],
      value2: "",
      prePass: "111",
      ruleForm: {
        pass: "",
        checkPass: "",
        oldPass: "",
      },
      rules: {
        pass: [{ validator: validatePass, trigger: "blur" }],
        checkPass: [{ validator: validatePass2, trigger: "blur" }],
        oldPass: [{ validator: checkOldPass, trigger: "blur" }],
      },
    };
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          alert("submit!");
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
  },
};
</script>

<style>
.admin-wrapper {
  display: flex;
  justify-content: center;
  align-items: center;
}
.admin-wrap {
  width: 90%;
  height: 90%;
  display: flex;
  justify-content: center;
  align-items: center;
}
.admin {
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
}
.set-time {
  height: 20%;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
}
.up-down-load{
    height: 35%;
    display: flex;
    margin-top: 2%;
}
.upload {
  
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}
.download {
  margin-left: 4%;
  
  align-self: flex-start;
  height: 100%;
  
}
.change-pass {
  height: 45%;
}
h1 {
  font-size: 26px;
}
.change-pass{
    display: flex;
    flex-direction: column;
    justify-content: space-around;
}
</style>