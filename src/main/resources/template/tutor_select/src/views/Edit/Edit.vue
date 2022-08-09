<template>
  <div id="mask">
    
      <div class="edit-wrapper">
        <div class="edit" v-show="isStudent==='true'">
          <el-form ref="form" :model="form" label-width="80px">
            <el-form-item label="姓名">
              <el-input v-model="form.name"></el-input>
            </el-form-item>
            <el-form-item label="学号">
              <el-input v-model="form.number"></el-input>
            </el-form-item>
            <el-form-item label="班级">
              <el-input v-model="form.class"></el-input>
            </el-form-item>
            <el-form-item label="电话">
              <el-input v-model="form.phone"></el-input>
            </el-form-item>
            <el-form-item label="邮箱">
              <el-input v-model="form.email"></el-input>
            </el-form-item>
            <el-form-item label="联系地址">
              <el-input v-model="form.address"></el-input>
            </el-form-item>
            <el-form-item label="选取状态">
              <el-radio-group v-model="form.status">
                <el-radio :label="1">已选取</el-radio>
                <el-radio :label="0">未选取</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="科研班">
              <el-radio-group v-model="form.scienceClass">
                <el-radio :label="1">已加入</el-radio>
                <el-radio :label="0">未加入</el-radio>
              </el-radio-group>
            </el-form-item>

            <el-form-item>
              <el-button type="primary" @click="studentSubmit">完成</el-button>
              <el-button @click="gobackStudent">取消</el-button>
            </el-form-item>
          </el-form>
        </div>
        <div class="edit" v-show="isStudent==='false'">
          <el-form ref="form" :model="form" label-width="80px">
            <el-form-item label="姓名">
              <el-input v-model="form.name"></el-input>
            </el-form-item>
            <el-form-item label="职称">
              <el-input v-model="form.number"></el-input>
            </el-form-item>
            <el-form-item label="专业">
              <el-input v-model="form.class"></el-input>
            </el-form-item>
            <el-form-item label="电话">
              <el-input v-model="form.phone"></el-input>
            </el-form-item>
            <el-form-item label="邮箱">
              <el-input v-model="form.email"></el-input>
            </el-form-item>
            <el-form-item label="联系地址">
              <el-input v-model="form.address"></el-input>
            </el-form-item>
            <el-form-item label="剩余名额">
              <el-input v-model="form.status"></el-input>
            </el-form-item>
            <el-form-item label="履历">
              <el-input v-model="form.scienceClass" type="textarea"></el-input>
            </el-form-item>
            
            <el-form-item>
              <el-button type="primary" @click="teacherSubmit">完成</el-button>
              <el-button @click="gobackTeacher">取消</el-button>
            </el-form-item>
          </el-form>
        </div>
      </div>
    
  </div>
</template>

<script>

export default {
  name: "Edit",
  data() {
    return {
      form: {
        name: "",
        number:'',
        class:'',
        phone:'',
        email:'',
        address:'',
        status:0,
        scienceClass:0
      },
      
    };
  },
  methods: {
    teacherSubmit() {
      console.log("submit!");
    },
    studentSubmit() {
      console.log("submit!");
    },
    gobackStudent() {
      this.$router.push("/student");
    },
    gobackTeacher(){
      this.$router.push("/teacher")
    }
  },
  computed:{
    isStudent(){
      return this.$route.query.isStudent
    },
  },
  mounted(){
    this.form = this.$route.query.data
    // console.log(this.form);
  }
};
</script>

<style scoped>
#mask {
  position: absolute;
  width: 100%;
  height: 100%;
  top: 0;
  left: 0;
  background-color: rgba(0, 0, 0, 0.5);
  z-index: 100;
  display: flex;
  justify-content: center;
  align-items: center;
}
.edit-wrapper {
  width: 50%;
  height: 70%;
  background-color: white;
  border-radius: 10px;
  position: relative;
  display: flex;
  justify-content: center;
  align-items: center;
}
.back {
  position: absolute;
  top: 5%;
  right: 3%;
  font-size: 24px;
}
.edit {
  width: 80%;
  height: 80%;
}
</style>