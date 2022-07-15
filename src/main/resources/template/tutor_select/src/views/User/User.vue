<template>
  <div class="user-wrapper">
    <div class="user-wrap">
      <Info v-show="isStudent" :infoAttr="studentInfoAttr"/>
      <Info v-show="isTeacher" :infoAttr="teacherInfoAttr"/>
      <Select />
      
      <transition name="el-fade-in">
        <router-view></router-view>
      </transition>
    </div>
    <Paganation/>
  </div>
</template>

<script>
import Info from "./Info/Info.vue";
import Select from "./Select/Select.vue";
import {mapState} from 'vuex'
export default {
  name: "User",
  components: {
    Info,
    Select,
  },
  data() {
    return {
      studentInfoAttr:{
        name:'姓名',
        No:'学号',
        class:'班级',
        phone:'电话',
        email:'邮箱',
        status:'选取状态',
        address:'联系地址'
      },
      teacherInfoAttr:{
        name:'姓名',
        No:'职称',
        class:'专业',
        phone:'电话',
        email:'邮箱',
        status:'剩余名额',
        address:'联系地址'
      },
      studentInfo:{},
      teacherInfo:{}
    }
  },
  computed:{
    ...mapState({
      isStudent:state=>state.login.isStudent,
      isTeacher:state=>state.login.isTeacher
    })
  }
};
</script>

<style scoped>
.user-wrapper{
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
}
.user-wrap {
  width: 90%;
  height: 90%;
  display: flex;
  flex-direction: column;
  
}
</style>