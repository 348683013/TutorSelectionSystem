<template>
<div class="left-nav">
    <el-row>
    <el-col :span="24">
      <el-menu
        default-active="2"
        class="el-menu-vertical-demo height"
        background-color="#e6f7ed"
        text-color="#0b5126"
        active-text-color="#0b5126"
        :router="true"
      >
        
        <el-menu-item v-for="(item,index) in navList.list" :key="index" :index="item.routeTo" >
          <i :class="item.icon"></i>
          <span slot="title">{{item.title}}</span>
        </el-menu-item>
        <el-submenu index="导师选择" v-show="!isAdmin">
          <template slot="title">
            <i class="el-icon-s-custom"></i>
            <span>{{navList.choose}}</span>
          </template>
          <el-menu-item-group>
            <template slot="title">一志愿选择</template>
            <el-menu-item index="/pick1">进行中</el-menu-item>
            <!-- <el-menu-item index="1-2">选项2</el-menu-item> -->
          </el-menu-item-group>
          <el-menu-item-group title="二志愿选择">
            <el-menu-item index="/pick2" disabled>未开放</el-menu-item>
          </el-menu-item-group>
          <!-- <el-menu-item index="1-4">
            <template slot="title">选项4</template>
            <el-menu-item index="1-4-1">选项1</el-menu-item>
          </el-menu-item> -->
        </el-submenu>
        <el-submenu index="导师选择" v-show="isAdmin">
          <template slot="title">
            <i class="el-icon-s-custom"></i>
            <span>导师管理</span>
          </template>
          
            <el-menu-item index="/teacher">导师信息</el-menu-item>
          
            <el-menu-item index="/condition">学生选导师情况</el-menu-item>
        </el-submenu>
        <el-menu-item index="/user" v-show="!isAdmin">
          <i class="el-icon-setting"></i>
          <span slot="title">个人信息</span>
        </el-menu-item>
        <el-menu-item index="/admin" v-show="isAdmin">
          <i class="el-icon-setting"></i>
          <span slot="title">其他事务</span>
        </el-menu-item>
        <div class="current-time-wrap">
            <div class="current-time"></div>
            {{date}} <br>
            第17周 {{weekNo}}
        </div>
      </el-menu>
    </el-col>
  </el-row>
</div>
  
</template>

<script>
export default {
  name: "Nav",
  data() {
    return {
      date:'',
      weekNo:''
    }
  },
  props:['navList','isAdmin'],
  mounted(){
    this.nowDate()
  },
  methods: {
    nowDate(){
      let now = new Date()
      let year = now.getFullYear()
      let month = now.getMonth()+1
      month = month<10?'0'+month:month
      let day = now.getDate()
      day = day<10?'0'+day:day
      this.date = `${year}-${month}-${day}`
      let No = now.getDay()
      const week = ['星期日','星期一','星期二','星期三','星期四','星期五','星期六']
      No = week[No]
      this.weekNo = No
    }
  },
};
</script>

<style >
.left-nav{
    min-width: 210px;
    height: 826px;
    /* color: rgb(148, 240, 183); */
}
.height{
    height: 826px;
}
.current-time-wrap{
    width: 100%;
    height: 50px;
    text-align: center;
    color: white;
    background-color: green;
    /* background-color: red; */
    position: absolute;
    bottom: 0;
    display: flex;
    justify-content: center;
    align-items: center;
}

</style>