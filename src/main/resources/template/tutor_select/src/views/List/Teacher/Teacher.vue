<template>
  <div class="teacher-list">
    <el-card class="box-card" v-for="(teacher,index) in teacherList" :key="teacher.teacherId">
      <div slot="header" class="clearfix">
        <span class="teacher-name">{{teacher.realname}}</span>
        <el-button style="float: right; padding: 3px 0;color:rgb(58, 153, 58);" type="text" @click="toDetail(index)"
          >查看详情</el-button
        >
      </div>
      <div class="text item">
        <div class="photo">
          <img :src="teacher.headImage" style="width:100%;height:100%;" alt="">
        </div>
        <div class="teacher-info-wrap">
          <div>性别:{{teacher.sex===0?'男':'女'}}</div>
          <div>年龄:{{teacher.age}}</div>
          <div>职称:{{teacher.teacherType}}</div>
        </div>
      </div>
    </el-card>
    
  </div>
</template>

<script>
export default {
  name: "Teacher",
  props:['teacherList'],
  methods: {
    toDetail(index){
      this.$router.push(`list/detail?index=${index}`)
    },
    open(){
      
    }
  },
};
</script>

<style scoped>
.teacher-list {
  display: grid;
  grid-template-columns: repeat(4, 300px);
  gap: 50px 50px;
}
.text {
  font-size: 14px;
}

.item {
  margin-bottom: 18px;
  display: flex;
  justify-content: space-between;
}

.photo {
  width: 130px;
  height: 180px;
  border: 1px solid rgb(241,241,241);
}

.teacher-info-wrap {
  font-size: 14px;
  padding-right: 30px;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  
}



.teacher-name {
  font-size: 24px;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}
.clearfix:after {
  clear: both;
}

.box-card {
  width: 300px;
  font-family: "songti";
}
</style>