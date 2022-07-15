<template>
  <div class="teacher-list">
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <span class="teacher-name">白明泽</span>
        <el-button style="float: right; padding: 3px 0;color:rgb(58, 153, 58);" type="text" @click="toDetail"
          >查看详情</el-button
        >
      </div>
      <div class="text item">
        <div class="photo"></div>
        <div class="teacher-info-wrap">
          <span>性别:</span>
          <span>年龄:</span>
          <span>职称:</span>
        </div>
      </div>
    </el-card>
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <span class="teacher-name">白明泽</span>
        <el-button style="float: right; padding: 3px 0;color:rgb(58, 153, 58);" type="text"
          >查看详情</el-button
        >
      </div>
      <div class="text item">
        <div class="photo"></div>
        <div class="teacher-info-wrap">
          <span>性别:</span>
          <span>年龄:</span>
          <span>职称:</span>
        </div>
      </div>
    </el-card>
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <span class="teacher-name">白明泽</span>
        <el-button style="float: right; padding: 3px 0;color:rgb(58, 153, 58);" type="text"
          >查看详情</el-button
        >
      </div>
      <div class="text item">
        <div class="photo"></div>
        <div class="teacher-info-wrap">
          <span>性别:</span>
          <span>年龄:</span>
          <span>职称:</span>
        </div>
      </div>
    </el-card>
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <span class="teacher-name">白明泽</span>
        <el-button style="float: right; padding: 3px 0;color:rgb(58, 153, 58);" type="text"
          >查看详情</el-button
        >
      </div>
      <div class="text item">
        <div class="photo"></div>
        <div class="teacher-info-wrap">
          <span>性别:</span>
          <span>年龄:</span>
          <span>职称:</span>
        </div>
      </div>
    </el-card>
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <span class="teacher-name">白明泽</span>
        <el-button style="float: right; padding: 3px 0;color:rgb(58, 153, 58);" type="text"
          >查看详情</el-button
        >
      </div>
      <div class="text item">
        <div class="photo"></div>
        <div class="teacher-info-wrap">
          <span>性别:</span>
          <span>年龄:</span>
          <span>职称:</span>
        </div>
      </div>
    </el-card>
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <span class="teacher-name">白明泽</span>
        <el-button style="float: right; padding: 3px 0;color:rgb(58, 153, 58);" type="text"
          >查看详情</el-button
        >
      </div>
      <div class="text item">
        <div class="photo"></div>
        <div class="teacher-info-wrap">
          <span>性别:</span>
          <span>年龄:</span>
          <span>职称:</span>
        </div>
      </div>
    </el-card>
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <span class="teacher-name">白明泽</span>
        <el-button style="float: right; padding: 3px 0;color:rgb(58, 153, 58);" type="text"
          >查看详情</el-button
        >
      </div>
      <div class="text item">
        <div class="photo"></div>
        <div class="teacher-info-wrap">
          <span>性别:</span>
          <span>年龄:</span>
          <span>职称:</span>
        </div>
      </div>
    </el-card>
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <span class="teacher-name">白明泽</span>
        <el-button
          style="float: right; padding: 3px 0;color:rgb(58, 153, 58);"
          type="text"
          @click="open"
          >查看详情</el-button
        >
      </div>
      <div class="text item">
        <div class="photo"></div>
        <div class="teacher-info-wrap">
          <span>性别:</span>
          <span>年龄:</span>
          <span>职称:</span>
        </div>
      </div>
    </el-card>
  </div>
</template>

<script>
export default {
  name: "Teacher",
  methods: {
    toDetail(){
      this.$router.push('list/detail')
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
  margin-right: 70px;
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