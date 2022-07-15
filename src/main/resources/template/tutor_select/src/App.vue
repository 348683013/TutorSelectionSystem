<template>
  <div id="app">
    <Header v-show="$route.meta.isShow" />
    <Pwd v-show="$route.meta.isShow" />
    <div class="content-wrapper">
      <Nav :navList="studentNav" :isAdmin="isAdmin" v-show="$route.meta.isShow&&isStudent" />
      <Nav :navList="teacherNav" :isAdmin="isAdmin" v-show="$route.meta.isShow&&isTeacher"/>
      <Nav :navList="adminNav" :isAdmin="isAdmin" v-show="$route.meta.isShow&&isAdmin"/>

      <router-view class="content"></router-view>
    </div>
  </div>
</template>

<script>
import Header from "./components/Header/Header.vue";
import Nav from "./components/Nav/Nav.vue";
import Pwd from "./components/Pwd/Pwd.vue";
import {mapState} from "vuex"
export default {
  name: "App",
  data() {
    return {
      studentNav: {
        list: [
          {
            title: "选取流程",
            icon: "el-icon-menu",
            routeTo: "/process",
          },
          {
            title: "预览导师",
            icon: "el-icon-document",
            routeTo: "/list",
          },
        ],
        choose: "导师选择",
      },
      teacherNav: {
        list: [
          {
            title: "查看申请",
            icon: "el-icon-document",
            routeTo: "/apply",
          },
        ],
        choose:'选择学生',
      },
      adminNav: {
        list: [
          {
            title: "学生管理",
            icon: "el-icon-user-solid",
            routeTo: "/student",
          },
          // {
          //   title: "导师管理",
          //   icon: "el-icon-s-custom",
          //   routeTo: "/teacher",
          // }
        ],
      },
    };
  },
  computed:{
    ...mapState({
      isTeacher:state=>state.login.isTeacher,
      isStudent:state=>state.login.isStudent,
      isAdmin:state=>state.login.isAdmin
    })
  },
  mounted(){
    if(sessionStorage.getItem('isTeacher')==='true'){
      this.$store.dispatch('getIsTeacher',true)
    }else{
      this.$store.dispatch('getIsTeacher',false)
    }
    if(sessionStorage.getItem('isStudent')==='true'){
      this.$store.dispatch('getIsStudent',true)
    }else{
      this.$store.dispatch('getIsStudent',false)
    }
    if(sessionStorage.getItem('isAdmin')==='true'){
      this.$store.dispatch('getIsAdmin',true)
    }else{
      this.$store.dispatch('getIsAdmin',false)
    }
    
  },
  components: {
    Header,
    Nav,
    Pwd,
  },
};
</script>

<style scoped>
#app {
  height: 100%;
  font-family: "songti", "Times New Roman";
}
.content-wrapper {
  display: flex;
}
.content {
  width: 90%;
}
</style>
